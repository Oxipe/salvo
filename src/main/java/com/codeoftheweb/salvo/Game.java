package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Date date;
    private String gameName;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    //Constructors

    public Game () {}

    public Game (String gameName) {
        this.date = new Date();
        this.gameName = gameName;
    }

    //Getters & setters

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) { this.gameName = gameName; }

    public Long getId() { return id; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<GamePlayer> getGamePlayers () {
        return gamePlayers;
    }

    //Methods

    public void addGame (GamePlayer gamePlayer) {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }

    public void addTime(Date date, Integer seconds) {
        setDate(Date.from(date.toInstant().plusSeconds(seconds)));
    }

}
