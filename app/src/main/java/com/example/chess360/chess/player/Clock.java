package com.example.chess360.chess.player;

public class Clock {

    // Time in seconds
    private int time;

    public Clock(int myTime){
        this.time = myTime;
    }

    public void reduceTime(){
        if (time >0){
            time--;
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getFormattedTime(){
        int minutes = this.time / 60;
        int seconds = this.time % 60;

        String output = String.format("%02d:%02d", minutes, seconds);

        return (output);
    }

    public boolean isActive(){
        return(this.time >0);
    }
}

