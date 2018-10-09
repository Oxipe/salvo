package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long gameId;
    private Date date;
    private String gameName;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;


    //Constructors

    public Game () {}

    public Game (String gameName) {
        this.date = new Date();
        this.gameName = gameName;
    }

    //Getters

    public String getGameName() {
        return gameName;
    }

    public Long getGameId() { return gameId; }

    //Setters

    public void setGameName(String gameName) { this.gameName = gameName; }

    public void  setGameId(Long gameId) { this.gameId = gameId; }




    public void addGame (GamePlayer gamePlayer) {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        System.out.println("setDate: " + date);
        this.date = date;
    }

    public void addTime(Date date, Integer seconds) {
        setDate(Date.from(date.toInstant().plusSeconds(seconds)));
    }

    public Set<GamePlayer> getGamePlayers () {
        return gamePlayers;
    }
}
