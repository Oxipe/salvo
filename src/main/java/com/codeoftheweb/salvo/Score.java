package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    private Date finishDate;
    private Double score;

    //Constructors

    public Score () {}

    public Score (Game game, Player player, Double score) {
        this.finishDate = new Date();
        this.game = game;
        this.player = player;
        this.score = score;
    }

    //Getters & setters


    public Long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}



//package com.codeoftheweb.salvo;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.Set;
//
//@Entity
//public class Score {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
//    private Long id;
//
//    @OneToMany(mappedBy = "scores", fetch = FetchType.EAGER)
//    private Set<Game> game;
//
//    private Long gameId;
//
//
//    @OneToMany (mappedBy = "scores", fetch = FetchType.EAGER)
//    private Set<Player> players;
//
//    private Long playerId;
//    private Long opponentId;
//
//    private Double score;
//    private Date finishDate;
//
//    //Constructors
//
//    public Score () {}
//
//    //For the win
//    public Score (Long gameId, Long playerId) {
//        this.gameId = gameId;
//        this.playerId = playerId;
//        this.score = 1.0;
//        this.finishDate = new Date();
//    }
//
//    //For the tie
//    public Score (Long gameId, Long playerId, Long opponentId) {
//        this.gameId = gameId;
//        this.playerId = playerId;
//        this.opponentId = opponentId;
//        this.score = 0.5;
//        this.finishDate = new Date();
//    }
//
//
//
//
//
//
//    //Getters & setters
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public Set<Game> getGame() {
//        return game;
//    }
//
//    public void setGame(Set<Game> game) {
//        this.game = game;
//    }
//
//    public Long getGameId() {
//        return gameId;
//    }
//
//    public void setGameId(Long gameId) {
//        this.gameId = gameId;
//    }
//
//    public Long getPlayerId() {
//        return playerId;
//    }
//
//    public void setPlayerId(Long playerId) {
//        this.playerId = playerId;
//    }
//
//    public Long getOpponentId() {
//        return opponentId;
//    }
//
//    public void setOpponentId(Long opponentId) {
//        this.opponentId = opponentId;
//    }
//
//    public Double getScore() {
//        return score;
//    }
//
//    public void setScore(Double score) {
//        this.score = score;
//    }
//
//    public Date getFinishDate() {
//        return finishDate;
//    }
//
//    public void setFinishDate(Date finishDate) {
//        this.finishDate = finishDate;
//    }
//
//    public Set<Player> getPlayer() {
//        return players;
//    }
//
//    public void setPlayer(Set<Player> player) {
//        this.players = player;
//    }
//
//}
