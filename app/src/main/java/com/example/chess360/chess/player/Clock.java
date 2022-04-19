package com.example.chess360.chess.player;

import android.os.Handler;

public class Clock {

    // Time in seconds
    private int time;

    // The clock is running:
    private boolean running;

    public Clock(int myTime){
        this.time = myTime;
        this.running = false;
        this.startChessClock();
    }

    private void startChessClock() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {

                if (running){

                    time--;

                    if (time==0){

                        running = false;
                    }

                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    public String getFormattedTime(){
        int minutes = this.time / 60;
        int seconds = this.time % 60;

        String output = String.format("%02d:%02d", minutes, seconds);

        return (output);
    }

    public void startTime(){
        this.running = true;
    }

    public void stopTime(){
        this.running = false;
    }
}

