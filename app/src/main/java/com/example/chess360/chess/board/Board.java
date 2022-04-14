package com.example.chess360.chess.board;

import com.example.chess360.chess.Move;
import com.example.chess360.chess.pieces.Piece;

public class Board {

    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    private int numPieces;

    // Squares:
    private final Square[][] squares;

    public Board(){
        this.squares = new Square[ROWS][COLUMNS];

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

    public void makeMove(Move myMove){

        int row1 = myMove.getOrigin().getRow();
        int row2 = myMove.getDestination().getRow();
        int column1 = myMove.getOrigin().getColumn();
        int column2 = myMove.getDestination().getColumn();

        Piece pieceOrigin = myMove.getOrigin().getPiece();
        Piece pieceDestination = myMove.getDestination().getPiece();

        // The piece is set at the destination square:
        this.squares[row2][column2].setPiece(pieceOrigin);

        // The origin square is emptied
        this.squares[row1][column1].emptySquare();

        // We check whether a piece has been captured:
        if (pieceDestination != null){

            this.numPieces--;
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