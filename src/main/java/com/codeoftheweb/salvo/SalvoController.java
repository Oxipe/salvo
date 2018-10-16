package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private PlayerRepository playerRepo;
    @Autowired
    private GamePlayerRepository gameplayerRepo;
    @Autowired
    private SalvoRepository salvoRepo;
    @Autowired
    private ScoreRepository scoreRepo;

    @RequestMapping("/games")
    public List<Object> getAllGames() {
        return gameRepo.findAll().stream().map(game -> makeGameDTO(game)).collect(toList());
    }

    @RequestMapping("/gp/{id}")
    public Map<String, Object> getGameView (@PathVariable("id") Long id) {
        return makeGameViewDTO(gameplayerRepo.getOne(id).getGame(), id);
    }

    private Map<String, Object> makeGameDTO (Game game) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getDate().getTime());
        dto.put("game_name", game.getGameName());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(toList()));
        //You still need this?
        //dto.put("score", game.getScore().stream().map(score -> makeScoreDTO(score)).collect(toList()));

        return dto;
    }

    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));

        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("user_name", player.getUserName());

        return dto;
    }

    private Map<String, Object> makeGameViewDTO(Game game, Long id) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getDate().getTime());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(toList()));
        dto.put("ships", game.getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getId() == id).findFirst().map(gamePlayer -> gamePlayer.getShips()).get());
        dto.put("salvoes", game.getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getId() == id).findFirst().get().getSalvos().stream().map(salvo -> makeSalvoDTO(salvo)).collect(toList()));

        return dto;
    }

    private Map<String, Object> makeSalvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", salvo.getTurn());
        dto.put("player", salvo.getPlayerId());
        dto.put("locations", salvo.getLocations());

        return dto;
    }

    @RequestMapping("/scores")
    public List<Object> scoreList () {
        List<Object> listOfScores = new ArrayList<>();
        Map<String, Object> dto = new LinkedHashMap<>();;
        List<String> listOfPlayers = new ArrayList<>();

        for (long i = 1; i < playerRepo.findAll().size(); i++) {
                listOfPlayers.add(playerRepo.getOne(i).getUserName());
        }

        for (int j = 0; j < listOfPlayers.size(); j++) {
            Double points = 0.0;
            Integer wins = 0;
            Integer loses = 0;
            Integer ties = 0;

            for(long k = 1; k <= scoreRepo.findAll().size(); k++) {

                if (listOfPlayers.get(j) == scoreRepo.getOne(k).getPlayer().getUserName()) {
                    if (scoreRepo.getOne(k).getScore() == 1.0) {
                        wins++;
                        points += 1.0;
                    } else if (scoreRepo.getOne(k).getScore() == 0.5) {
                        ties++;
                        points += 0.5;
                    } else {
                        loses++;
                    }
                }
            }

            listOfScores.add(makeScoreDTO(listOfPlayers.get(j), points, wins, loses, ties));
        }

        return listOfScores;
    }

    private Map<String, Object> makeScoreDTO(String player, Double points, Integer wins, Integer loses, Integer ties) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player", player);
        dto.put("points", points);
        dto.put("wins", wins);
        dto.put ("loses", loses);
        dto.put("ties", ties);

        return dto;
    }

}