package com.example.chess360.chess.pieces;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.Move;
import com.example.chess360.chess.board.Board;

public class King extends Piece {

    // Letter that represents the King:
    private final char KING_LETTER = 'K';

    private boolean isMoved;

    public King(int color) {
        super(color);

        // Letter:
        this.setLetter(KING_LETTER);

        // The king hasn't moved:
        this.isMoved = false;
    }

    public boolean isMoved() {
        return (this.isMoved);
    }

    public void setMovido(){
        this.isMoved = true;
    }

    public int movePiece(Move move, Board board) {

        // The origin and destination square can't be the same:
        boolean isValid = !move.getOrigin().equals(move.getDestination());

        if (isValid){

            // Coordinates:
            int row1 = move.getOrigin().getRow();
            int row2 = move.getDestination().getRow();
            int column1 = move.getOrigin().getColumn();
            int column2 = move.getDestination().getColumn();

            // The absolute value of the difference of rows must be either 0 or 1. The same applies
            // to the columns:
            isValid = (Math.abs(row1 - row2) == 0 || Math.abs(row1 - row2) == 1)
                    && (Math.abs(column1 - column2) == 0 || Math.abs(column1 - column2) == 1);

            if (isValid){

                // There can't be a piece of the same color in the destination square:
                Piece myPiece = move.getDestination().getPiece();

                isValid = myPiece == null || myPiece.getColor() != this.getColor();
            }
        }

        // Result output:
        int output;
        if (isValid) {
            output = Chess.LEGAL_MOVE;
        } else {
            output = Chess.ILLEGAL_MOVE;
        }
        return (output);
    }
}
