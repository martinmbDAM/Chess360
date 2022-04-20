package com.example.chess360.chess.player;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.board.Board;
import com.example.chess360.chess.board.Square;

import java.util.ArrayList;

public class ChessPlayer {

    // Static constants for the color
    public final static int WHITE = 0;
    public final static int BLACK = 1;

    // Static constants for the player state:
    public final static int CHECK_MATE = 2;
  //  public final static int STALEMATE = 3;
    public final static int PLAYING = 4;

    // Name of the player:
    private String name;

    // Color of the pieces:
    private int color;

    // Clock:
 //   private Clock clock;

    // Chess:
    private Chess chess;

    public ChessPlayer(String myName, int myColor, int myTime, Chess chess){
        this.name = myName;
        this.color = myColor;
     //   this.clock = new Clock(myTime);
        this.chess = chess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object myObject){
        boolean output = false;

        if (myObject instanceof ChessPlayer){
            ChessPlayer myChessPlayer = (ChessPlayer) myObject;
            output = myChessPlayer.getName().equals(this.name);
        }

        return(output);
    }

    private boolean hasMoves(Board myBoard){

        boolean output = false;

        // Pieces of the player:
        ArrayList<Square> myPieces = this.getPlayerPieces(myBoard);

        // For each piece we check whether it has at least one available square:
        if (myPieces != null){

            int pos=0;

            while(pos<myPieces.size() && !output){

                String square = myPieces.get(pos).getName();
                ArrayList<String> availableSquares = this.chess.getAvailableSquares(square);

                output = availableSquares.size()>0;

                if (!output){
                    pos++;
                }
            }
        }

        return(output);
    }

    private ArrayList<Square> getPlayerPieces(Board myBoard){

        ArrayList<Square> myPieces = new ArrayList<>();
        Square [][] squares = myBoard.getSquares();

        for (Square[] mySquares : squares) {
            for (Square mySquare : mySquares) {
                if (!mySquare.isEmpty() && mySquare.getPiece().getColor() == this.color) {
                    myPieces.add(mySquare);
                }
            }
        }

        return(myPieces);
    }

    public int getPlayerState(Board myBoard){

        // We check whether the player has available moves:
        boolean hasOptions = this.hasMoves(myBoard);

        // We check whether the player is in check:
        boolean inCheck = this.chess.isInCheck(this.chess.isWhiteTurn(), this.chess.getBoard());

        int output;

        if (hasOptions){
            output = ChessPlayer.PLAYING;
        }
        else{
            if (inCheck){
                output = ChessPlayer.CHECK_MATE;
            }
            // Temporal:
            else{
                output = ChessPlayer.PLAYING;
            }
        /*    else{
                output = ChessPlayer.STALEMATE;
            } */
        }

        return(output);
    }
}