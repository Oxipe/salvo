package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long gameId;
    private Date date;
    private String gameName;

    public Game () {}

    public Game (String gameName) {
        this.date = new Date();
        this.gameName = gameName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        System.out.println("setDate: " + date);
        this.date = date;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void addTime(Date date, Integer seconds) {
        setDate(Date.from(date.toInstant().plusSeconds(seconds)));
    }
}
