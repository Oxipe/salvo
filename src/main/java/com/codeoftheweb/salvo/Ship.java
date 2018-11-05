package com.codeoftheweb.salvo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String shipType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameplayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @JoinColumn(name = "locations")
    private List<String> locations;

    private Integer shipLenght;

    //Constructors

    public Ship() {}

    public Ship(String shipType, GamePlayer gamePlayer, List<String> locations) {
        this.shipType = shipType;
        this.gamePlayer = gamePlayer;
        this.locations = locations;
        this.shipLenght = setShipLenght(shipType);
    }

    //Getters & setters

    public long getId() {
        return id;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public Integer getShipLenght() { return shipLenght; }

    private Integer setShipLenght(String type) {
        Integer length = 0;
        switch (type) {
            case "Carrier": length = 5;
                break;
            case "Battle Ship": length = 4;
                break;
            case "Submarine": length = 3;
                break;
            case "Destroyer": length = 3;
                break;
            case "Patrol Boat": length = 2;
                break;
        }

        return length;
    }
}
