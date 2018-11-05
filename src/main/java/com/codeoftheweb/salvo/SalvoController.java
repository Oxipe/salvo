package com.codeoftheweb.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private ScoreRepository scoreRepo;
    @Autowired
    private ShipRepository shipRepo;
    private PasswordEncoder passwordEncoder;



    /*
    * Register a new player
    * */

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public Map<String, Object> register(
            @RequestParam String userName,
            @RequestParam String userMail,
            @RequestParam String userPassWord) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if(userName.isEmpty()) {
            dto.put("status", new ResponseEntity<>("User name is required", HttpStatus.CONFLICT));
            return dto;
        } else if(userMail.isEmpty()) {
            dto.put("status", new ResponseEntity<>("User mail address is required", HttpStatus.CONFLICT));
            return dto;
        } else if(userPassWord.isEmpty()) {
            dto.put("status", new ResponseEntity<>("User password is required", HttpStatus.CONFLICT));
            return dto;
        }

        if (playerRepo.findByUserName(userName) !=  null) {
            dto.put("status", new ResponseEntity<>("User name already in use", HttpStatus.CONFLICT));
            return dto;
        }

        if (playerRepo.findByUserMail(userMail) != null) {
            dto.put("status", new ResponseEntity<>("Mail address already in use", HttpStatus.CONFLICT));
            return dto;
        }

        playerRepo.save(new Player(userName, userMail, userPassWord)); //passwordEncoder.encode() <== to use to encrypt the password
        dto.put("status", new ResponseEntity<>("Player created", HttpStatus.CREATED));
        return dto;
    }

    /*
     * Get the current logged in player
     * */

    @RequestMapping("/player")
    public Object getCurrentPlayer(Authentication authentication) {
        if (!isGuest(authentication)) {
            return makeCurrentPlayerDTO(playerRepo.findByUserName(authentication.getName()));
        } else {
            return "Not a valid user";
        }
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    public Map<String, Object> makeCurrentPlayerDTO (Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("user_name", player.getUserName());
        dto.put("user_mail", player.getUserMail());
        dto.put("games", player.getGames().stream().map(gamePlayer -> makeGameDTO(gamePlayer.getGame())).collect(toList()));

        return dto;
    }

    /*
    * Create a new game
    * */

    @RequestMapping(path = "/currentGame")
    public Map<String, Object> createNewGame(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if(isGuest(authentication)) {
            dto.put("error", new ResponseEntity<>("You are not allowed here.", HttpStatus.FORBIDDEN));
            return dto;
        } else {
            Game game = new Game("Game: " + (gameRepo.findAll().size() + 1), playerRepo.findByUserName(authentication.getName()).getUserName());
            GamePlayer gamePlayer = new GamePlayer(game, playerRepo.findByUserName(authentication.getName()));

            gameRepo.save(game);
            gameplayerRepo.save(gamePlayer);


            dto.put("gamePlayerId", gamePlayer.getId());
            dto.put("created", new ResponseEntity<>("Game created", HttpStatus.CREATED));

            return dto;
        }
    }

    /*
    * Able to join a game
    * */

    @RequestMapping("/joinGame")
    public ResponseEntity<Map<String, Object>> joinGame(
            Authentication authentication,
            @RequestParam Long gameId) {

        Map<String, Object> dto = new LinkedHashMap<>();

        if(gameId == null) {
            dto.put("Game not found", gameId);
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
        } else {
            Game game = gameRepo.findOne(gameId);
            game.setOpponent(authentication.getName());
            GamePlayer gamePlayer = new GamePlayer(game, playerRepo.findByUserName(authentication.getName()));

            gameplayerRepo.save(gamePlayer);


            dto.put("gamePlayerId", gamePlayer.getId());

            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }
    }


    /*
    * Create a list of games
    * */

    @RequestMapping("/games")
    public List<Object> getAllGames() {
        return gameRepo.findAll().stream().map(game -> makeGameDTO(game)).collect(toList());
    }

    private Map<String, Object> makeGameDTO (Game game) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getDate());
        dto.put("creator", game.getCreator());
        dto.put("opponent", game.getOpponent());
        dto.put("game_name", game.getGameName());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(toList()));
        if(game.getScore().isEmpty()) {
            dto.put("has_finished", false);
        } else {
            dto.put("has_finished", true);
        }

        return dto;
    }

    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        dto.put("ships", gamePlayer.getShips().stream().map(ship -> makeShipDTO(ship)).collect(toList()));

        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("user_name", player.getUserName());

        return dto;
    }

    private Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", ship.getShipType());
        dto.put("location", ship.getLocations());
        dto.put("length", ship.getShipLenght());

        return dto;
    }

    /*
    * Create the game view data
    * */

    @RequestMapping("/gp/{id}")
    public Map<String, Object> getGameView (@PathVariable("id") Long id, Authentication authentication) {
        if (gameplayerRepo.getOne(id).getPlayer().getUserName() == authentication.getName()) {
            return makeGameViewDTO(gameplayerRepo.getOne(id).getGame(), id, authentication);
        } else {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("error", new ResponseEntity<>("You are not allowed to enter this game", HttpStatus.FORBIDDEN));
            return dto;
        }
    }

    private Map<String, Object> makeGameViewDTO(Game game, Long id,  Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getDate().getTime());
        dto.put("creator", game.getCreator());
        dto.put("opponent", game.getOpponent());
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

    /*
    * Create a list of scores
    * */

    @RequestMapping("/scores")
    public List<Object> scoreList () {
        List<Object> listOfScores = new ArrayList<>();
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

    @RequestMapping("/ships")
    private List<Object> allShips() {
        List<Object> ships = new ArrayList<>();

        ships.add(createShipDetails("Carrier", 5));
        ships.add(createShipDetails("Battle Ship", 4));
        ships.add(createShipDetails("Submarine", 3));
        ships.add(createShipDetails("Destroyer", 3));
        ships.add(createShipDetails("Patrol Boat", 2));

        return ships;
    }

    private Map<String, Object> createShipDetails(String type, Integer length) {
        Map<String, Object> dto = new LinkedHashMap<>();

        dto.put("type", type);
        dto.put("length", length);
        dto.put("image", "images/" + type + ".png");
        dto.put("isPlaced", false);
        dto.put("location", "");

        return dto;
    }

    @RequestMapping("/games/players/{gamePlayerId}/ships")
    private Map<String, Object> deployShips (@PathVariable("gamePlayerId") Long gamePlayerId,
                                             @RequestParam String[][] details,
                                             Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if (isGuest(authentication)) {
            dto.put("status", new ResponseEntity<>("You are not allowed here.", HttpStatus.UNAUTHORIZED));
            return dto;
        } else if (gameplayerRepo.findOne(gamePlayerId) == null) {
            dto.put("status", new ResponseEntity<>("Game player ID does not exist.", HttpStatus.UNAUTHORIZED));
            return dto;
        } else if (gameplayerRepo.findOne(gamePlayerId).getPlayer() != playerRepo.findByUserName(authentication.getName())) {
            dto.put("status", new ResponseEntity<>("You don't have access to this game player.", HttpStatus.UNAUTHORIZED));
            return dto;
        } else if (!gameplayerRepo.findOne(gamePlayerId).getShips().isEmpty()) {
            dto.put("status", new ResponseEntity<>("You already placed your ships.", HttpStatus.FORBIDDEN));
            return dto;
        } else {
            for (String[] detail: details) {
                List<String> locations = new ArrayList<>();

                for (Integer i = 1; i < detail.length; i++) {
                    locations.add(detail[i]);
                }

                shipRepo.save(new Ship(detail[0], gameplayerRepo.findOne(gamePlayerId), locations));
            }

            dto.put("status", new ResponseEntity<>("Ships added.", HttpStatus.CREATED));
            return dto;
        }
    }
}