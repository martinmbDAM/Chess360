package com.example.chess360.chess.board;

import com.example.chess360.chess.pieces.Piece;

public class Square {

    private final int row;
    private final int column;
    private final String name;
    private Piece piece;

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public String getName() {
        return this.name;
    }

    public Piece getPiece(){
        return this.piece;
    }

    public void setPiece(Piece miPiece){
        this.piece = miPiece;
    }

    public Square(int row, int column){
        this.row = row;
        this.column = column;
        this.name = this.coordinatesToName(row, column);
        this.piece = null;
    }

    public Square(Square square){
        this.row = square.getRow();
        this.column = square.getColumn();
        this.name = square.getName();
        this.piece = square.getPiece();
    }

    public Square(String myName){
        int [] coordinates = this.nameToCoordinates(myName);
        this.row = coordinates[0];
        this.column = coordinates[1];
        this.name = myName;
        this.piece = null;
    }

    // Translates coordinates to a name in algebraic notation:
    private String coordinatesToName(int x, int y) {

        String myName;

        int r = x+1;
        char c = (char) ('a' + y);

        myName = Character.toString(c) + Integer.toString(r);

        return (myName);
    }

    // Translates a name in algebraic notation to coordinates:
    private int [] nameToCoordinates(String name){

        int [] output = new int [2];

        output[0] = Integer.parseInt(Character.toString(name.charAt(1)))-1;
        output[1] = name.charAt(0) - 'a';

        return output;
    }

    @Override
    public boolean equals(Object object){

        boolean output = false;

        if (object instanceof Square){

            Square mySquare = (Square) object;
            output = this.row == mySquare.getRow() &&
                    this.column == mySquare.getColumn() &&
                    this.name.equals(mySquare.getName()) &&
                    this.piece.equals(mySquare.getPiece());
        }

        return output;
    }

    // Removes a piece from a square:
    public void emptySquare(){
        this.piece = null;
    }
}
