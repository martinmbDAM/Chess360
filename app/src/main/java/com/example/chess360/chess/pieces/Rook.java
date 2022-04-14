package com.example.chess360.chess.pieces;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.Move;
import com.example.chess360.chess.board.Board;

public class Rook extends Piece{

    // Letter that reprsents a rook:
    private final char ROOK_LETTER = 'R';

    // Checks whether the rook has been moved:
    private boolean isMoved;

    public Rook(int color) {
        super(color);

        // Letter:
        this.setLetter(ROOK_LETTER);

        // The rook hasn't been moved:
        this.isMoved = false;
    }

    public boolean isMoved(){
        return this.isMoved;
    }

    public void setMoved(){
        this.isMoved = true;
    }

    public int movePiece(Move move, Board board) {

        // The origin and destination square can't be the same
        boolean isValid = !move.getOrigin().equals(move.getDestination());

        if (isValid){

            // Rows and columns:
            int row1 = move.getOrigin().getRow();
            int row2 = move.getDestination().getRow();
            int column1 = move.getOrigin().getColumn();
            int column2 = move.getDestination().getColumn();

            // The rook must remain in the same row or column:
            isValid = row1 == row2 || column1 == column2;

            if (isValid){

                // There can't be a piece of the same color in the destination square:
                Piece myPiece = move.getDestination().getPiece();

                isValid = myPiece == null || myPiece.getColor()!=this.getColor();

                if (isValid){

                    // There can't be a piece in between:

                    // Scenario 1: The rook moves through the column:
                    if (row1 == row2) {

                        if (column1 > column2) {
                            int temp = column1;
                            column1 = column2;
                            column2 = temp;
                        }

                        int pos = column1 + 1;

                        while (pos < column2 && isValid) {
                            isValid = board.getSquare(row1, pos).getPiece() == null;
                            pos++;
                        }
                    }
                    // Scenario 2: The rook moves through the row:
                    else if (column1 == column2) {

                        if (row1 > row2) {
                            int temp = row1;
                            row1 = row2;
                            row2 = temp;
                        }

                        int pos = row1 + 1;

                        while (pos < row2 && isValid) {
                            isValid = board.getSquare(pos, column1).getPiece() == null;
                            pos++;
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
