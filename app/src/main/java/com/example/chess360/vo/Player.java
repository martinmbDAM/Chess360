package com.example.chess360.vo;

public class Player extends User{

    private String name;
    private String surname;
    private int elo;
    private Club club;
    private Player coach;

    public Player(String name, String surname, String username, String email, int elo, String password){
        super(username, email,password);
        this.name = name;
        this.surname = surname;
        this.elo = elo;
        this.club = null;
        this.coach = null;
    }

    public Player(String name){
        super(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Player getCoach() {
        return coach;
    }

    public void setCoach(Player coach) {
        this.coach = coach;
    }
}
