package com.example.chess360.chess.pieces;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.Move;
import com.example.chess360.chess.board.Board;

public class King extends Piece {

    // Letter that represents the King:
    private final char KING_LETTER = 'K';

    private boolean isMoved;

    private Chess chess;

    public King(int color, Chess chess) {
        super(color);

        // Letter:
        if (color == Piece.BLACK){
            this.setLetter(Character.toLowerCase(KING_LETTER));
        }
        else{
            this.setLetter(KING_LETTER);
        }

        // The king hasn't moved:
        this.isMoved = false;

        this.chess = chess;
    }

    public Chess getChess(){
        return this.chess;
    }

    public King(King king){
        super(((Piece)king).getColor());
        this.setLetter(king.getLetter());
        this.isMoved = king.isMoved();
        this.chess = king.getChess();
    }

    public boolean isMoved() {
        return (this.isMoved);
    }

    public void setMoved(){
        this.isMoved = true;
    }

    public int movePiece(Move move, Board board) {

        boolean castleLong = false, castleShort = false;

        // The origin and destination square can't be the same:
        boolean isValid = !move.getOrigin().equals(move.getDestination());

        if (isValid){

            // Coordinates:
            int row1 = move.getOrigin().getRow();
            int row2 = move.getDestination().getRow();
            int column1 = move.getOrigin().getColumn();
            int column2 = move.getDestination().getColumn();

            // We check whether the king wants to move or to castle:
            if (!((King) move.getOrigin().getPiece()).isMoved() &&
                row1 == row2 && Math.abs(column1 - column2) == 2 &&
               ((move.getOrigin().getPiece().getColor() == Piece.WHITE && row1 == 0) ||
               (move.getOrigin().getPiece().getColor() == Piece.BLACK && row1 == 7))){

                // The King can't be in check:
                isValid = !this.chess.isInCheck(this.chess.isWhiteTurn(), board);

                if (isValid){

                    // Short castle:
                    if (column1 == 4 && column2 == 6){

                        // The rook can't have been moved, the destination square must be empty and the
                        // square in between must be empty as well:
                        isValid = !((Rook) board.getSquare(row1, column2+1).getPiece()).isMoved() &&
                                move.getDestination().isEmpty() &&
                                board.getSquare(row1, column2-1).isEmpty();

                        castleShort = isValid;
                    }
                    // Long castle:
                    else if (column1 == 4 && column2 == 2){

                        // The rook can't have been moved, the destination square must be empty and the
                        // square in between must be empty as well:
                        isValid = !((Rook) board.getSquare(row1, column2-2).getPiece()).isMoved() &&
                                move.getDestination().isEmpty() &&
                                board.getSquare(row1, column2+1).isEmpty();

                        castleLong = isValid;
                    }
                }

            }
            else{

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

        }

        // Result output:
        int output;
        if (isValid) {

            if (castleLong){
                output = Chess.CASTLE_LONG;
            }
            else if (castleShort){
                output = Chess.CASTLE_SHORT;
            }
            else{
                output = Chess.LEGAL_MOVE;
            }

        } else {
            output = Chess.ILLEGAL_MOVE;
        }

        return (output);
    }
}
