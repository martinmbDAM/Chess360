package com.example.chess360.chess.pieces;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.Move;
import com.example.chess360.chess.board.Board;

public class Queen extends Piece{

    // Letter that represents the Queen:
    private final char QUEEN_LETTER = 'Q';

    public Queen(int color){
        super(color);

        // Letter:
        if (color == Piece.BLACK){
            this.setLetter(Character.toLowerCase(QUEEN_LETTER));
        }
        else{
            this.setLetter(QUEEN_LETTER);
        }
    }

    public int movePiece(Move move, Board board) {

        // A queen move is valid if it is also valid either for a rook or a bishop:
        Rook myRook = new Rook(this.getColor());
        Bishop myBishop = new Bishop(this.getColor());

        boolean validRook = myRook.movePiece(move,board) == Chess.LEGAL_MOVE;
        boolean validBishop = myBishop.movePiece(move,board) == Chess.LEGAL_MOVE;

        boolean validQueen = validRook || validBishop;

        // Result outptu:
        int output;
        if (validQueen){
            output = Chess.LEGAL_MOVE;
        }
        else{
            output = Chess.ILLEGAL_MOVE;
        }

        return(output);
    }
}