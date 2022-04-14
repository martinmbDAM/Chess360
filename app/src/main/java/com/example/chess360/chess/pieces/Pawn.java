package com.example.chess360.chess.pieces;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.Move;
import com.example.chess360.chess.board.Board;

public class Pawn extends Piece {

    // Letter that represents the pawn:
    private final char PAWN_LETTER = 'P';

    public Pawn(int color) {
        super(color);

        // Letter:
        this.setLetter(PAWN_LETTER);
    }

    @Override
    public int movePiece(Move move, Board board) {

        // The origin and destination square can't be the same:
        boolean isValid = !move.getOrigin().equals(move.getDestination());

        if (isValid){

            // Coordinates:
            int row1 = move.getOrigin().getRow();
            int row2 = move.getDestination().getRow();
            int column1 = move.getOrigin().getColumn();
            int column2 = move.getDestination().getColumn();

            // We check wheter the pawn wants to capture or move forward:
            if (column1 == column2){

                // There can't be a piece in that square
                isValid = move.getDestination().getPiece() == null;

                if (isValid) {

                    // The difference between rows must be either 1 or 2:
                    isValid = (((row2 == (row1 + 1) && this.getColor() == Piece.WHITE)
                            || (row2 == (row1 - 1) && this.getColor() == Piece.BLACK))
                            || (row2 == (row1 + 2) && row1 == 1 && this.getColor() == Piece.WHITE
                            && board.getSquare(2, column1).getPiece() == null)
                            || row2 == (row1 - 2) && row1 == 6 && this.getColor() == Piece.BLACK
                            && board.getSquare(5, column1).getPiece() == null);
                }
            }
            else{

                // The destination square must be in the adjacent column, on the same diagonal:
                isValid = Math.abs(row1 - row2) == 1 && Math.abs(column1 - column2) == 1;

                if (isValid){

                    // There has to be a piece of the opposite color in the destination square:
                    Piece myPiece = move.getDestination().getPiece();

                    isValid = myPiece != null && myPiece.getColor() != this.getColor();
                }
            }
        }

        // Result output:
        int output;

        if (isValid){

            output = Chess.LEGAL_MOVE;
        }
        else{
            output = Chess.ILLEGAL_MOVE;
        }
        return (output);
    }
}
