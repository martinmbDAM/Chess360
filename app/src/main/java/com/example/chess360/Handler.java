package com.example.chess360;

import com.example.chess360.chess.Chess;

import java.util.ArrayList;

public class Handler {

    private PlayZone playzone;
    private Chess chess;

    public void initializeBoard(PlayZone myPlayZone, Chess myChess){

        this.playzone = myPlayZone;
        this.chess = myChess;

        String [][] initialPosition = this.chess.exportPosition();
        //   this.playzone.setPosition(initialPosition);
        //   this.playzone.setApertura(this.chess.getApertura());
    }

    public void getMove(String origin, String destination){

        boolean isValid = this.chess.importMove(origin, destination);

        if (isValid){

            String [][] position = this.chess.exportPosition();
            //    this.playzone.setPosition(position);
            //     this.playzone.setApertura(this.chess.getApertura());
        }
    }

    public ArrayList<String> requestAvailableSquares(String square){
        return this.chess.getAvailableSquares(square);
    }

    public boolean isOccupied(String square){
        return this.chess.isOccupied(square);
    }

    public boolean isPlayerTurn(String square){
        return this.chess.isPlayerTurn(square);
    }

    public void reset(){
        this.chess = new Chess(this);
        this.initializeBoard(playzone, chess);
    }
}