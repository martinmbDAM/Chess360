package com.example.chess360.vo;

import com.example.chess360.dao.Dao;

import java.time.LocalDate;
import java.time.LocalTime;

public class Game {

    private int id;
    private Player playerWhite;
    private Player playerBlack;
    private LocalDate date;
    private LocalTime time;
    private Tournament tournament;
    private String result;

    public Game(Player playerWhite, Player playerBlack, LocalDate date, LocalTime time, Tournament tournament, String result) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
        this.date = date;
        this.time = time;
        this.tournament = tournament;
        this.result = result;
        this.id = Dao.generateID_Game();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(Player playerWhite) {
        this.playerWhite = playerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
