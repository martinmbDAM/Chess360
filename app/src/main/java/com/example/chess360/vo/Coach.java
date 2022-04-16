package com.example.chess360.vo;

import java.util.ArrayList;

public class Coach extends Player {

    private ArrayList<Player> coachedPlayers;

    public Coach (String username, String email, String password){
        super(username, email, password);
        this.coachedPlayers = new ArrayList<>();
    }

    public ArrayList<Player> getCoachedPlayers() {
        return coachedPlayers;
    }

    public void setCoachedPlayers(ArrayList<Player> coachedPlayers) {
        this.coachedPlayers = coachedPlayers;
    }
}
