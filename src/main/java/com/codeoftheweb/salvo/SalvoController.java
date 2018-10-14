package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        dto.put("score", game.getScore().stream().map(score -> makeScoreDTO(score)).collect(toList()));

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

    private Map<String, Object> makeScoreDTO(Score score) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("score", score.getScore());
        System.out.println(score.getScore());
        if (score.getScore() == 0.5) {
            dto.put("players", score.getGame().getGamePlayers().stream().map(gamePlayer -> gamePlayer.getPlayer().getUserName()));
        } else {
            dto.put("winner", score.getPlayer().getUserName());
        }
        return dto;
    }

}