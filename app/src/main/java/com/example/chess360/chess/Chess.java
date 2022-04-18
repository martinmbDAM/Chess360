package com.example.chess360.chess;

import com.example.chess360.Handler;
import com.example.chess360.chess.board.Board;
import com.example.chess360.chess.board.Square;
import com.example.chess360.chess.pieces.*;
import com.example.chess360.chess.player.Player;

import java.util.ArrayList;

public class Chess {

    public static final int PROMOTION_QUEEN = 0;
    public static final int PROMOTION_ROOK = 1;
    public static final int PROMOTION_BISHOP = 2;
    public static final int PROMOTION_KNIGHT = 3;
    public static final int PROMOTION = 4;
    public static final int LEGAL_MOVE = 5;
    public static final int ILLEGAL_MOVE = 6;
    public static final int CASTLE_SHORT = 7;
    public static final int CASTLE_LONG = 8;
    public static final int EN_PASSANT = 9;

    private final String STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private Board board;
    private Handler handler;
    //   private final DAO dao; ---> Not implemented yet
    private int halfMoves;
    private int fullMoves;
    private String enPassant;

    private boolean whiteTurn;

    // Players:
    private Player playerWhite, playerBlack;

    public Chess(Handler myHandler) {
        this.getInitialPosition();
        this.handler = myHandler;
        this.whiteTurn = true;
        this.enPassant = null;
        this.playerWhite = new Player("Player1", Player.WHITE, 180, this);
        this.playerBlack = new Player("Player2", Player.BLACK, 180, this);

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

        Board myBoard = new Board(this);

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
                            myPiece = new Pawn(Piece.BLACK, this);
                            break;
                        case 'P':
                            myPiece = new Pawn(Piece.WHITE, this);
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
                            myPiece = new King(Piece.BLACK, this);
                            break;
                        case 'K':
                            myPiece = new King(Piece.WHITE, this);
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
        int result = this.checkMove(myMove);

        // Even if the move is valid, we have to consider the possibility of leaving the King in check:
        boolean inCheck = this.leavesKingInCheck(myMove, result);

        if (inCheck){
            result = Chess.ILLEGAL_MOVE;


        }

        if (result != Chess.ILLEGAL_MOVE) {

            if (result == Chess.PROMOTION) {
                this.handler.requestPromotion(myMove);
            } else {
                // En passant:
                if (this.canTakeEnPassant(myMove)) {

                    int row = myMove.getDestination().getRow();
                    int column = myMove.getDestination().getColumn();

                    if (myMove.getOrigin().getPiece().getColor() == Piece.WHITE) {

                        this.enPassant = Square.translateCoordinates(row - 1, column);
                    } else {

                        this.enPassant = Square.translateCoordinates(row + 1, column);
                    }
                } else {
                    this.enPassant = null;
                }

                this.board.makeMove(myMove, result);
            }

            // If it's a pawn move or a capture, the number of halfmoves gets reseted:
            if ((myMove.getOrigin().getPiece() != null &&
                    myMove.getOrigin().getPiece() instanceof Pawn) ||
                    (myMove.getDestination().getPiece() != null)) {

                this.resetHalfMoves();
            } else {

                this.increaseHalfMoves();
            }

            // If the black pieces have moved, the number of full moves is increased:
            if (!this.whiteTurn) {
                this.increaseFullMoves();
            }

            this.changeTurn();
            //      this.dao.actualizarPosicion(this.exportarFEN()); ---> Not implemented yet
        }

        return result != Chess.ILLEGAL_MOVE;
    }

    private int checkMove(Move myMove) {

        int result = -1;

        Piece myPiece = myMove.getOrigin().getPiece();

        if (myPiece != null) {

            result = myPiece.movePiece(myMove,this.board);
        }

        return result;
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
        if (this.whiteTurn) {
            fen += " w";
        } else {
            fen += " b";
        }

        // Castling:
        if (this.getCastlingRights(0) || this.getCastlingRights(1) ||
                this.getCastlingRights(2) || this.getCastlingRights(3)){
            fen += " ";

            if (this.getCastlingRights(0)){
                fen += "K";
            }

            if (this.getCastlingRights(1)){
                fen += "Q";
            }

            if (this.getCastlingRights(2)){
                fen += "k";
            }

            if (this.getCastlingRights(3)){
                fen += "q";
            }
        }
        else{
            fen += " -";
        }

        // Square where the player can take en passant:
        if (this.enPassant == null) {
            fen += " -";
        } else {
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
                    int result = myPiece.movePiece(myMove,this.board);

                    if (result != Chess.ILLEGAL_MOVE) {

                        // Even if the move is valid, we have to make sure it doesn't leave the King in check:
                        boolean inCheck = this.leavesKingInCheck(myMove,result);

                        if (!inCheck){
                            mySquares.add(destination.getName());
                        }

                    }

                }
            }
        }

        return (mySquares);
    }

    public boolean isOccupied(String square) {
        return this.board.getSquare(square).getPiece() != null;
    }

    public boolean isPlayerTurn(String square) {

        return ((this.whiteTurn && this.board.getSquare(square).getPiece().getColor() == Piece.WHITE) ||
                (!this.whiteTurn && this.board.getSquare(square).getPiece().getColor() == Piece.BLACK));
    }

    private void changeTurn() {
        this.whiteTurn = !this.whiteTurn;
    }

    /*          <----- NOT IMPLEMENTED YET ----->
    public String getApertura(){
        return this.dao.getApertura();
    } */

    private void increaseHalfMoves() {
        this.halfMoves++;
    }

    private void resetHalfMoves() {
        this.halfMoves = 0;
    }

    private void increaseFullMoves() {
        this.fullMoves++;
    }

    // Checks whether en passant is possible:
    private boolean canTakeEnPassant(Move myMove) {

        boolean enPassant = false;

        // It must be a pawn move:
        if (myMove.getOrigin().getPiece() instanceof Pawn) {

            int[] coordinatesOrigin = Square.translateName(myMove.getOrigin().getName());
            int[] coordinatesDestination = Square.translateName(myMove.getDestination().getName());

            int row1 = coordinatesOrigin[0];
            int row2 = coordinatesDestination[0];
            int column1 = coordinatesOrigin[1];
            int column2 = coordinatesDestination[1];

            // The pawn must have moved two squares forward:
            if (column1 == column2 && Math.abs(row1 - row2) == 2) {

                int color = myMove.getOrigin().getPiece().getColor();
                Piece pieceLeft = null;
                Piece pieceRight = null;

                // Finally, there must be a pawn of the opposite color in an adjacent square:
                if (column2 > 0) {
                    pieceLeft = this.board.getSquare(Square.translateCoordinates(row2, column2 - 1)).getPiece();
                }

                if (column2 < 7) {
                    pieceRight = this.board.getSquare(Square.translateCoordinates(row2, column2 + 1)).getPiece();
                }

                if ((pieceLeft != null && pieceLeft instanceof Pawn && color != pieceLeft.getColor()) ||
                        (pieceRight != null && pieceRight instanceof Pawn && color != pieceRight.getColor())) {

                    enPassant = true;
                }
            }
        }

        return enPassant;
    }

    public void promotePiece(Move promotionMove, int promotionCode) {

        this.board.makeMove(promotionMove, promotionCode);
    }

    public boolean getCastlingRights(int index){
        return this.board.getCastlingRights(index);
    }

    // Method that obtains the king of a player:
    public Square getKing(Board myBoard){

        int color;

        if (this.whiteTurn){
            color = Piece.WHITE;
        }
        else{
            color = Piece.BLACK;
        }

        Square [][] squares = myBoard.getSquares();

        boolean found = false;
        int i=0, j=0;

        while (!found && i<squares.length){

            j = 0;

            while(!found && j<squares[i].length){

                Piece myPiece = squares[i][j].getPiece();

                found = myPiece != null && myPiece instanceof King && myPiece.getColor() == color;

                if (!found){
                    j++;
                }
            }

            if (!found){
                i++;
            }
        }

        return squares[i][j];
    }

    // Checks wether the King is in check:
    public boolean isInCheck(boolean isWhiteTurn, Board myBoard){

        int color;

        if (isWhiteTurn){
            color = Piece.WHITE;
        }
        else{
            color = Piece.BLACK;
        }

        Square[][] squares = myBoard.getSquares();

        // Square where the King is:
        Square squareKing = this.getKing(myBoard);

        // Squares where the enemy pieces are:
        ArrayList<Square> enemyPieces = new ArrayList<>();

        for (int i=0; i<squares.length; i++){
            for (int j=0; j<squares[i].length; j++){

                Piece myPiece = squares[i][j].getPiece();

                if (myPiece != null && myPiece.getColor() != color){
                    enemyPieces.add(squares[i][j]);
                }
            }
        }

        // We check whether any of the pieces can take the King:
        boolean inCheck = false;
        int pos=0;

        while(!inCheck && pos<enemyPieces.size()){

            Move myMove = new Move(enemyPieces.get(pos),squareKing);

            inCheck = enemyPieces.get(pos).getPiece().movePiece(myMove,myBoard) != Chess.ILLEGAL_MOVE;

            if (!inCheck){
                pos++;
            }
        }

        return inCheck;
    }

    public boolean isWhiteTurn(){
        return whiteTurn;
    }

    // Checks whether a move would leave the King in check:
    public boolean leavesKingInCheck(Move move, int code){

        // Copy of the board:
        Board boardCopy = new Board(this.board);

        // We make the move:
        boardCopy.makeMove(move,code);

        // We check whether the King is in check:
        boolean inCheck = this.isInCheck(this.whiteTurn,boardCopy);

        return inCheck;
    }

    public Player getWhitePlayer(){
        return this.playerWhite;
    }

    public Player getBlackPlayer(){
        return this.playerBlack;
    }
}