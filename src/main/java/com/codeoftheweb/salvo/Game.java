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
    private String creator;
    private String opponent;
    private Integer turn;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany (mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Score> scores;

    //Constructors

    public Game () {}

    public Game (String gameName, String creator) {
        this.date = new Date();
        this.gameName = gameName;
        this.creator = creator;
        this.turn = 0;
    }

    public Game (String gameName, String creator, String opponent) {
        this.date = new Date();
        this.gameName = gameName;
        this.creator = creator;
        this.opponent = opponent;
        this.turn = 0;
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

    public Set<Score> getScore() {
        return scores;
    }

    public void setScore(Set<Score> score) {
        this.scores = score;
    }

    public String getCreator() { return creator; }

    public void setCreator(String creator) { this.creator = creator; }

    public String getOpponent() { return opponent; }

    public void setOpponent(String opponent) { this.opponent = opponent; }

    public Integer getTurn() { return turn; }

    public void setTurn(Integer turn) {  this.turn = turn; }

    //Methods

    public void addGame (GamePlayer gamePlayer) {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }

    public void addTime(Date date, Integer seconds) {
        setDate(Date.from(date.toInstant().plusSeconds(seconds)));
    }

}
