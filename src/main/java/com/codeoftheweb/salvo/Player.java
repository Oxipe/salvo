package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long userId;
    private String userName;
    private String userMail;
    private String userPassWord;

    @OneToMany (mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    //Constructors

    public Player() { }

    public Player(String name, String mail, String passWord) {
        this.userName = name;
        this.userMail = mail;
        this.userPassWord = passWord;
    }

    //Getters & setters

    public String getUserName() { return userName; }

    public void setUserName (String userName) { this.userName = userName; }

    public String getUserMail() { return userMail; }

    public void setUserMail (String userMail) { this.userMail = userMail; }

    public String getUserPassWord() { return userPassWord; }

    public void setUserPassWord(String passWord) { this.userPassWord = passWord; }

    public Long getUserId () { return userId; }

    public Set<GamePlayer> getGames() { return gamePlayers; }

    //Methods

    public void addPlayer (GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gamePlayers.add(gamePlayer);
    }







}