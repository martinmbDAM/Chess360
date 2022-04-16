package com.example.chess360.vo;

import com.example.chess360.dao.Dao;

import java.time.LocalDate;

public class Tournament {

    private int id;
    private String location;
    private LocalDate begin_date;
    private LocalDate end_date;
    private int max_players;
    private Organizer organizer;

    public Tournament(String location, LocalDate begin_date, LocalDate end_date, int max_players, Organizer organizer) {
        this.location = location;
        this.begin_date = begin_date;
        this.end_date = end_date;
        this.max_players = max_players;
        this.organizer = organizer;
        this.id = Dao.generateID_Tournament();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(LocalDate begin_date) {
        this.begin_date = begin_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }
}
