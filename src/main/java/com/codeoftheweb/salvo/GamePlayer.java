package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private Date date;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Game game;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "game_id")
    private Player player;


    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ship;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvos;


    //Constructors

    public GamePlayer () {}

    public GamePlayer (Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    //getters & setters

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Ship> getShips () {
        return ship;
    }

    public Set<Salvo> getSalvos () {return salvos; }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}