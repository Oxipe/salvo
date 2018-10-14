package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Integer turn;
    private Long playerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameplayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @JoinColumn(name = "locations")
    private List<String> locations;

    //Constructors

    public Salvo() {}

    public Salvo (Integer turn, GamePlayer gamePlayer, Long playerId, List<String> location) {
        this.turn = turn;
        this.gamePlayer = gamePlayer;
        this.playerId = playerId;
        this.locations = location;
    }

    //Getters & setters


    public long getId() {
        return id;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
