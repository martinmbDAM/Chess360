package com.example.chess360.vo;

public class TournamentRecord {

    private Player player;
    private Tournament tournament;
    private String score;

    public TournamentRecord(Player player, Tournament tournament, String score) {
        this.player = player;
        this.tournament = tournament;
        this.score = score;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
