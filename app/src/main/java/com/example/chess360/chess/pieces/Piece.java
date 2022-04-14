package com.example.chess360.chess.pieces;

import com.example.chess360.chess.Move;
import com.example.chess360.chess.board.Board;

public abstract class Piece {

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    private final int color;
    private char letter;

    public Piece(int myColor) {
        this.color = myColor;
    }

    public int getColor(){
        return this.color;
    }

    public char getLetter(){
        return this.letter;
    }

    public void setLetter(char myLetter){
        this.letter = myLetter;
    }

    @Override
    public boolean equals(Object object) {

        boolean output = false;

        if (object instanceof Piece) {

            Piece myPiece = (Piece) object;
            output = this.letter == myPiece.getLetter()
                    && this.color == myPiece.getColor();
        }

        return (output);
    }

    public abstract int movePiece(Move move, Board board);
}
