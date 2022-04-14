package com.example.chess360.chess.pieces;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.Move;
import com.example.chess360.chess.board.Board;

public class Knight extends Piece {

    // Letter that represents the Knight:
    private final char KNIGHT_LETTER = 'N';

    public Knight(int color){
        super(color);

        // Letter:
        this.setLetter(KNIGHT_LETTER);
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

            // The absolute value of the difference of rows must be 2 and the absolute value of the
            // difference of columns must be 1 or the other way around:
            isValid = (Math.abs(row1 - row2) == 2 && Math.abs(column1 - column2) == 1)
                    || (Math.abs(row1 - row2) == 1 && Math.abs(column1 - column2) == 2);

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
