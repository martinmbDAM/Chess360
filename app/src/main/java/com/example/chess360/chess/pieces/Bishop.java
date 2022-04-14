package com.example.chess360.chess.pieces;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.Move;
import com.example.chess360.chess.board.Board;

public class Bishop extends Piece{

    // Letter that represents the bishop:
    private final char BISHOP_LETTER = 'B';

    public Bishop(int color){

        super(color);

        // Letter:
        this.setLetter(BISHOP_LETTER);
    }

    public int movePiece(Move move, Board board) {

        // The origin and destination square can't be the same
        boolean isValid = !move.getOrigin().equals(move.getDestination());

        if (isValid){

            // Coordinates:
            int row1 = move.getOrigin().getRow();
            int row2 = move.getDestination().getRow();
            int column1 = move.getOrigin().getColumn();
            int column2 = move.getDestination().getColumn();

            // The absolute value of the difference of rows must be the same as the absolute value of
            // the difference of columns:
            isValid = Math.abs(row1-row2) == Math.abs(column1-column2);

            if (isValid){

                // There can't be a piece of the same color in the destination square:
                Piece myPiece = move.getDestination().getPiece();

                isValid = myPiece == null || myPiece.getColor() != this.getColor();

                if (isValid){

                    // There can't be a piece in between:

                    if (row2 > row1 && column2 > column1) {

                        int m = row1 + 1;
                        int p = column1 + 1;

                        while (m < row2&& p < column2 && isValid) {
                            isValid = board.getSquare(m,p).getPiece() == null;
                            m++;
                            p++;
                        }
                    } else if (row2 > row1 && column2 < column1) {

                        int m = row1 + 1;
                        int p = column1 - 1;

                        while (m < row2 && p > column2 && isValid) {
                            isValid = board.getSquare(m,p).getPiece() == null;
                            m++;
                            p--;
                        }
                    } else if (row2 < row1 && column2 > column1) {

                        int m = row1 - 1;
                        int p = column1 + 1;

                        while (m > row2 && p < column2 && isValid) {
                            isValid = board.getSquare(m,p).getPiece() == null;
                            m--;
                            p++;
                        }
                    } else if (row2 < row1 && column2 < column1) {

                        int m = row1 - 1;
                        int p = column1 - 1;

                        while (m > row2 && p > column2 && isValid) {
                            isValid = board.getSquare(m,p).getPiece() == null;
                            m--;
                            p--;
                        }
                    }

                }
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