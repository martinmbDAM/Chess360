package com.example.chess360.chess.board;

import com.example.chess360.chess.Chess;
import com.example.chess360.chess.Move;
import com.example.chess360.chess.pieces.Bishop;
import com.example.chess360.chess.pieces.King;
import com.example.chess360.chess.pieces.Knight;
import com.example.chess360.chess.pieces.Piece;
import com.example.chess360.chess.pieces.Queen;
import com.example.chess360.chess.pieces.Rook;

public class Board {

    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    private int numPieces;
    private Chess chess;

    // Squares:
    private final Square[][] squares;

    public Board(Chess chess){
        this.squares = new Square[ROWS][COLUMNS];
        this.chess = chess;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                this.squares[i][j] = new Square(i,j);
            }
        }
    }

    public Board(Board board){
        this.squares = new Square[ROWS][COLUMNS];
        Square[][] squaresCopy = board.getSquares();

        for (int i = 0; i < squaresCopy.length; i++) {
            for (int j = 0; j < squaresCopy[i].length; j++) {
                this.squares[i][j] = new Square(squaresCopy[i][j]);
            }
        }
    }

    public Square[][] getSquares(){
        return this.squares;
    }

    public Square getSquare(int row, int column){
        return this.squares[row][column];
    }

    public void addPiece(){
        this.numPieces++;
    }

    public int getNumPieces(){
        return this.numPieces;
    }

    public void emptyBoard(){
        this.numPieces = 0;
    }

    public void makeMove(Move myMove, int code){

        int row1 = myMove.getOrigin().getRow();
        int row2 = myMove.getDestination().getRow();
        int column1 = myMove.getOrigin().getColumn();
        int column2 = myMove.getDestination().getColumn();

        Piece pieceOrigin = myMove.getOrigin().getPiece();
        Piece pieceDestination = myMove.getDestination().getPiece();

        // If the piece is a King or a Rook, the pice is set to have been moved and castling rights
        // are removed:
        if (pieceOrigin instanceof King){

            ((King) pieceOrigin).setMoved();

            if (pieceOrigin.getColor() == Piece.WHITE){
                this.chess.revokeCastleRights(0);
                this.chess.revokeCastleRights(1);
            }
            else{
                this.chess.revokeCastleRights(2);
                this.chess.revokeCastleRights(3);
            }
        }
        else if (pieceOrigin instanceof Rook){

            ((Rook) pieceOrigin).setMoved();

            if (pieceOrigin.getColor() == Piece.WHITE){

                if (myMove.getOrigin().getName().equals("a1")){
                    this.chess.revokeCastleRights(1);
                }
                else if (myMove.getOrigin().getName().equals("h1")){
                    this.chess.revokeCastleRights(0);
                }

            }
            else{

                if (myMove.getOrigin().getName().equals("a8")){
                    this.chess.revokeCastleRights(3);
                }
                else if (myMove.getOrigin().getName().equals("h8")){
                    this.chess.revokeCastleRights(2);
                }
            }
        }

        switch (code) {

            case Chess.LEGAL_MOVE:

                // The piece is set at the destination square:
                this.squares[row2][column2].setPiece(pieceOrigin);

                // The origin square is emptied
                this.squares[row1][column1].emptySquare();

                // We check whether a piece has been captured:
                if (pieceDestination != null){

                    this.numPieces--;
                }

                break;

            case Chess.EN_PASSANT:

                // The other pawn is removed:
                if (myMove.getOrigin().getPiece().getColor() == Piece.WHITE){

                    this.squares[row2-1][column2].emptySquare();
                }
                else{

                    this.squares[row2+1][column2].emptySquare();
                }

                // The piece is set at the destination square:
                this.squares[row2][column2].setPiece(pieceOrigin);

                // The origin square is emptied
                this.squares[row1][column1].emptySquare();

                // A piece has been captured:
                this.numPieces--;

                break;

            case Chess.CASTLE_LONG:

                // The piece is set at the destination square:
                this.squares[row2][column2].setPiece(pieceOrigin);

                // The origin square is emptied
                this.squares[row1][column1].emptySquare();

                if (myMove.getOrigin().getPiece().getColor() == Piece.WHITE){

                    Piece myRook = this.getSquare("a1").getPiece();
                    this.squares[row2][column2+1].setPiece(myRook);
                    this.squares[row2][0].emptySquare();
                }
                else{

                    Piece myRook = this.getSquare("a8").getPiece();
                    this.squares[row2][column2+1].setPiece(myRook);
                    this.squares[row2][0].emptySquare();
                }

                break;

            case Chess.CASTLE_SHORT:

                // The piece is set at the destination square:
                this.squares[row2][column2].setPiece(pieceOrigin);

                // The origin square is emptied
                this.squares[row1][column1].emptySquare();

                if (myMove.getOrigin().getPiece().getColor() == Piece.WHITE){

                    Piece myRook = this.getSquare("h1").getPiece();
                    this.squares[row2][column2-1].setPiece(myRook);
                    this.squares[row2][7].emptySquare();
                }
                else{

                    Piece myRook = this.getSquare("h8").getPiece();
                    this.squares[row2][column2-1].setPiece(myRook);
                    this.squares[row2][7].emptySquare();
                }

                break;

            case Chess.PROMOTION_QUEEN:

                // The piece is set at the destination square, getting replaced by a queen:
                this.squares[row2][column2].setPiece(new Queen(pieceOrigin.getColor()));

                // The origin square is emptied
                this.squares[row1][column1].emptySquare();

                // We check whether a piece has been captured:
                if (pieceDestination != null){

                    this.numPieces--;
                }

                break;

            case Chess.PROMOTION_ROOK:

                // The piece is set at the destination square, getting replaced by a rook:
                this.squares[row2][column2].setPiece(new Rook(pieceOrigin.getColor()));

                // The origin square is emptied
                this.squares[row1][column1].emptySquare();

                // We check whether a piece has been captured:
                if (pieceDestination != null){

                    this.numPieces--;
                }

                break;

            case Chess.PROMOTION_KNIGHT:

                // The piece is set at the destination square, getting replaced by a knight:
                this.squares[row2][column2].setPiece(new Knight(pieceOrigin.getColor()));

                // The origin square is emptied
                this.squares[row1][column1].emptySquare();

                // We check whether a piece has been captured:
                if (pieceDestination != null){

                    this.numPieces--;
                }

                break;

            case Chess.PROMOTION_BISHOP:

                // The piece is set at the destination square, getting replaced by a bishop:
                this.squares[row2][column2].setPiece(new Bishop(pieceOrigin.getColor()));

                // The origin square is emptied
                this.squares[row1][column1].emptySquare();

                // We check whether a piece has been captured:
                if (pieceDestination != null){

                    this.numPieces--;
                }

                break;
        }
    }

    public Square getSquare(String name){

        boolean found = false;
        int i = 0;
        int j = 0;

        while (!found && i < Board.ROWS){

            j = 0;

            while (!found && j < Board.COLUMNS){
                found = this.squares[i][j].getName().equals(name);

                if (!found){
                    j++;
                }
            }

            if (!found){
                i++;
            }

        }

        return this.squares[i][j];
    }

}