package com.example.chess360.vo;

import java.util.ArrayList;

public class Coach extends Player {

    private ArrayList<Player> coachedPlayers;

    public Coach (String name, String surname, String username, String email, int elo, String password){
        super(name, surname, username, email, elo, password);
        this.coachedPlayers = new ArrayList<>();
    }

    public ArrayList<Player> getCoachedPlayers() {
        return coachedPlayers;
    }

    public void setCoachedPlayers(ArrayList<Player> coachedPlayers) {
        this.coachedPlayers = coachedPlayers;
    }
}
