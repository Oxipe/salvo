package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    private String userMail;
    private String userPassWord;

    private Double points = 0.0;
    private Integer wins = 0;
    private Integer loses = 0;
    private Integer ties = 0;

    @OneToMany (mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    @OneToMany (mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> scores;

    //Constructors

    public Player() { }

    public Player(String userName, String mail, String userPassWord) {
        this.userName = userName;
        this.userMail = mail;
        this.userPassWord = userPassWord;
    }

    //Getters & setters

    public String getUserName() { return userName; }

    public void setUserName (String userName) { this.userName = userName; }

    public String getUserMail() { return userMail; }

    public void setUserMail (String userMail) { this.userMail = userMail; }

    public String getUserPassWord() { return userPassWord; }

    public void setUserPassWord(String passWord) { this.userPassWord = passWord; }

    public Long getId() { return id; }

    public Set<GamePlayer> getGames() { return gamePlayers; }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    public Integer getTies() {
        return ties;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }

    //Methods

    public void addPlayer (GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gamePlayers.add(gamePlayer);
    }

    public void setWin() {
        this.points += 1.0;
        this.wins++;
    }

    public void setLose() {
        this.loses++;
    }

    public void setTie()
    {
        this.points += 0.5;
        this.ties++;
    }



}