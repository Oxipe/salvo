package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping("/game_id")
    public List<Object> getAllGames() {
        System.out.println(gameRepository);
        System.out.println(gameRepository.findAll());
        return gameRepository.findAll().stream().map(game -> makeGameDTO(game)).collect(toList());

    }

    private Map<String, Object> makeGameDTO (Game game) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getGameId());
        return dto;
    }
}
