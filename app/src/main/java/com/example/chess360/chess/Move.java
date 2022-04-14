package com.example.chess360.chess;

import com.example.chess360.chess.board.Square;

public class Move {

    private final Square origin;
    private final Square destination;

    public Move(Square myOrigin, Square myDestination){
        origin = new Square(myOrigin);
        destination = new Square(myDestination);
    }

    public Square getOrigin(){
        return this.origin;
    }

    public Square getDestination(){
        return this.destination;
    }
}