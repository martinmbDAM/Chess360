package com.example.chess360.chess;

import com.example.chess360.Handler;
import com.example.chess360.chess.board.Board;
import com.example.chess360.chess.board.Square;
import com.example.chess360.chess.pieces.*;

import java.util.ArrayList;

public class Chess {

    public static final int LEGAL_MOVE = 0;
    public static final int ILLEGAL_MOVE = 1;
    public static final int CASTLE_SHORT = 2;
    public static final int CASTLE_LONG = 3;
    public static final int EN_PASSANT = 4;
    public static final int PROMOTION = 5;  // Maybe it's not useful
    public static final int PROMOTION_QUEEN = 6;
    public static final int PROMOTION_ROOK = 7;
    public static final int PROMOTION_BISHOP = 8;
    public static final int PROMOTION_KNIGHT = 9;

    private final String STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private Board board;
    private Handler handler;
    //   private final DAO dao; ---> Not implemented yet
    private int halfMoves;
    private int fullMoves;
    private String enPassant;

    private boolean whiteTurn;

    public Chess(Handler myHandler) {
        this.getInitialPosition();
        this.handler = myHandler;
        this.whiteTurn = true;
        this.enPassant = null;
        //      this.dao = new DAO(this.STARTING_POSITION);

        //     this.semiMovimientos = 0;
        //     this.movimientos = 1;
    }

    public Board getBoard() {
        return this.board;
    }

    public void getPositionFEN(String fen) {

        String[] fields = fen.split(" ");
        String[] position = fields[0].split("/");

        Board myBoard = new Board();

        int limit = Board.ROWS - 1;

        for (int row = limit; row >= 0; row--) {

            String data = position[limit - row];
            int column = 0;
            int index = 0;

            while (column < Board.COLUMNS && index < data.length()) {

                if (Character.isDigit(data.charAt(index))) {

                    int num = Character.getNumericValue(data.charAt(index));
                    column += num;
                } else {

                    char letter = data.charAt(index);

                    Piece myPiece = null;

                    switch (letter) {

                        case 'p':
                            myPiece = new Pawn(Piece.BLACK);
                            break;
                        case 'P':
                            myPiece = new Pawn(Piece.WHITE);
                            break;
                        case 'r':
                            myPiece = new Rook(Piece.BLACK);
                            break;
                        case 'R':
                            myPiece = new Rook(Piece.WHITE);
                            break;
                        case 'n':
                            myPiece = new Knight(Piece.BLACK);
                            break;
                        case 'N':
                            myPiece = new Knight(Piece.WHITE);
                            break;
                        case 'b':
                            myPiece = new Bishop(Piece.BLACK);
                            break;
                        case 'B':
                            myPiece = new Bishop(Piece.WHITE);
                            break;
                        case 'q':
                            myPiece = new Queen(Piece.BLACK);
                            break;
                        case 'Q':
                            myPiece = new Queen(Piece.WHITE);
                            break;
                        case 'k':
                            myPiece = new King(Piece.BLACK);
                            break;
                        case 'K':
                            myPiece = new King(Piece.WHITE);
                            break;
                    }

                    myBoard.getSquare(row, column).setPiece(myPiece);
                    myBoard.addPiece();
                    column++;
                }

                index++;
            }
        }

        this.board = myBoard;
    }

    private void getInitialPosition() {
        this.getPositionFEN(STARTING_POSITION);
    }

    public String[][] exportPosition() {

        Square[][] squares = this.board.getSquares();

        int size = this.board.getNumPieces();

        String[][] output = new String[size][size];

        int pos = 0;

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {

                if (this.board.getSquare(i, j).getPiece() != null) {

                    output[0][pos] = this.board.getSquare(i, j).getName();
                    output[1][pos] = Character.toString(this.board.getSquare(i, j).getPiece().getLetter());
                    pos++;
                }
            }
        }

        return (output);
    }

    public boolean importMove(String origin, String destination) {

        Move myMove = new Move(this.board.getSquare(origin),
                this.board.getSquare(destination));

        // If the move is valid, the board gets updated:
        boolean isValid = checkMove(myMove);

        if (isValid) {

            // En passant:
            if (this.canTakeEnPassant(myMove)){

                int row = myMove.getDestination().getRow();
                int column = myMove.getDestination().getColumn();

                if (myMove.getOrigin().getPiece().getColor() == Piece.WHITE){

                    this.enPassant = Square.translateCoordinates(row-1,column);
                }
                else{

                    this.enPassant = Square.translateCoordinates(row+1, column);
                }
            }
            else{
                this.enPassant = null;
            }

            this.board.makeMove(myMove);

            // If it's a pawn move or a capture, the number of halfmoves gets reseted:
            if ((myMove.getOrigin().getPiece() != null &&
                    myMove.getOrigin().getPiece() instanceof Pawn) ||
                    (myMove.getDestination().getPiece() != null)){

                this.resetHalfMoves();
            }
            else{

                this.increaseHalfMoves();
            }

            // If the black pieces have moved, the number of full moves is increased:
            if (!this.whiteTurn){
                this.increaseFullMoves();
            }

            this.changeTurn();
            //      this.dao.actualizarPosicion(this.exportarFEN()); ---> Not implemented yet
        }

        return isValid;
    }

    private boolean checkMove(Move movimiento) {

        boolean isValid = false;

        int originRow = movimiento.getOrigin().getRow();
        int originColumn = movimiento.getOrigin().getColumn();
        int destinationRow = movimiento.getDestination().getRow();
        int destinationColumn = movimiento.getDestination().getColumn();

        Piece myPiece = this.board.getSquare(originRow, originColumn).getPiece();

        Square originSquare = this.board.getSquare(originRow, originColumn);
        Square destinationSquare = this.board.getSquare(destinationRow, destinationColumn);

        Move myMove = new Move(originSquare, destinationSquare);

        if (myPiece != null) {

            int result = myPiece.movePiece(myMove, this.board);
            isValid = result != Chess.ILLEGAL_MOVE;
        }

        return isValid;
    }

    public String exportFEN() {

        String fen = new String();

        // Position of each piece:
        for (int i = Board.ROWS - 1; i >= 0; i--) {

            int empty = 0;

            for (int j = 0; j < Board.COLUMNS; j++) {

                Piece myPiece = this.board.getSquare(i, j).getPiece();

                if (myPiece == null) {
                    empty++;
                } else {
                    if (empty > 0) {
                        fen += empty;
                        empty = 0;
                    }

                    Character letter = myPiece.getLetter();

                    if (myPiece.getColor() == Piece.BLACK) {
                        letter = Character.toLowerCase(letter);
                    }

                    fen += letter;
                }

            }

            if (empty > 0) {
                fen += empty;
            }

            if (i > 0) {
                fen += '/';
            }
        }

        // Player to move:
        if (this.whiteTurn){
            fen += " w";
        }
        else{
            fen += " b";
        }

        // Castling:
        fen += " KQkq";

        // Square where the player can take en passant:
        if (this.enPassant == null){
            fen += " -";
        }
        else{
            fen += " " + this.enPassant;
        }

        // Half moves:
        fen += " " + this.halfMoves;

        // Full moves:
        fen += " " + this.fullMoves;

        return fen;
    }

    public ArrayList<String> getAvailableSquares(String square) {

        ArrayList<String> mySquares = new ArrayList<>();

        Square origin = this.board.getSquare(square);
        Piece myPiece = origin.getPiece();

        if (myPiece != null) {

            for (int i = 0; i < Board.ROWS; i++) {
                for (int j = 0; j < Board.COLUMNS; j++) {

                    Square destination = this.board.getSquare(i, j);
                    Move myMove = new Move(origin, destination);
                    boolean isValid = myPiece.movePiece(myMove, this.board) !=
                            Chess.ILLEGAL_MOVE;

                    if (isValid){

                        mySquares.add(destination.getName());
                    }

                }
            }
        }

        return (mySquares);
    }

    public boolean isOccupied(String square){
        return this.board.getSquare(square).getPiece() != null;
    }

    public boolean isPlayerTurn(String square){

        return ((this.whiteTurn && this.board.getSquare(square).getPiece().getColor() == Piece.WHITE) ||
                (!this.whiteTurn && this.board.getSquare(square).getPiece().getColor() == Piece.BLACK));
    }

    private void changeTurn(){
        this.whiteTurn = !this.whiteTurn;
    }

    /*          <----- NOT IMPLEMENTED YET ----->
    public String getApertura(){
        return this.dao.getApertura();
    } */

    private void increaseHalfMoves(){
        this.halfMoves++;
    }

    private void resetHalfMoves(){
        this.halfMoves = 0;
    }

    private void increaseFullMoves(){
        this.fullMoves++;
    }

    // Checks whether en passant is possible:
    private boolean canTakeEnPassant(Move myMove){

        boolean enPassant = false;

        // It must be a pawn move:
        if (myMove.getOrigin().getPiece() instanceof Pawn){

            int [] coordinatesOrigin = Square.translateName(myMove.getOrigin().getName());
            int [] coordinatesDestination = Square.translateName(myMove.getDestination().getName());

            int row1 = coordinatesOrigin[0];
            int row2 = coordinatesDestination[0];
            int column1 = coordinatesOrigin[1];
            int column2 = coordinatesDestination[1];

            // The pawn must have moved two squares forward:
            if (column1 == column2 && Math.abs(row1 - row2) == 2){

                int color = myMove.getOrigin().getPiece().getColor();
                Piece pieceLeft = null;
                Piece pieceRight = null;

                // Finally, there must be a pawn of the opposite color in an adjacent square:
                if (column2 > 0){
                    pieceLeft = this.board.getSquare(Square.translateCoordinates(row2,column2-1)).getPiece();
                }

                if (column2 < 7){
                    pieceRight = this.board.getSquare(Square.translateCoordinates(row2,column2+1)).getPiece();
                }

                if ((pieceLeft != null && pieceLeft instanceof Pawn && color != pieceLeft.getColor()) ||
                        (pieceRight != null && pieceRight instanceof Pawn && color != pieceRight.getColor())){

                    enPassant = true;
                }
            }
        }

        return enPassant;
    }
}