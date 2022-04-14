package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlayZone extends AppCompatActivity {

    public ImageButton [][] squares = new ImageButton[8][8];
    public TextView casilla;
    private String chosenSquare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_zone);

        // No square has been selected so far:
        this.chosenSquare = null;

        // Row 8:
        squares[0][0] = findViewById(R.id.a8_square);
        squares[0][1] = findViewById(R.id.b8_square);
        squares[0][2] = findViewById(R.id.c8_square);
        squares[0][3] = findViewById(R.id.d8_square);
        squares[0][4] = findViewById(R.id.e8_square);
        squares[0][5] = findViewById(R.id.f8_square);
        squares[0][6] = findViewById(R.id.g8_square);
        squares[0][7] = findViewById(R.id.h8_square);

        // Row 7:
        squares[1][0] = findViewById(R.id.a7_square);
        squares[1][1] = findViewById(R.id.b7_square);
        squares[1][2] = findViewById(R.id.c7_square);
        squares[1][3] = findViewById(R.id.d7_square);
        squares[1][4] = findViewById(R.id.e7_square);
        squares[1][5] = findViewById(R.id.f7_square);
        squares[1][6] = findViewById(R.id.g7_square);
        squares[1][7] = findViewById(R.id.h7_square);

        // Row 6:
        squares[2][0] = findViewById(R.id.a6_square);
        squares[2][1] = findViewById(R.id.b6_square);
        squares[2][2] = findViewById(R.id.c6_square);
        squares[2][3] = findViewById(R.id.d6_square);
        squares[2][4] = findViewById(R.id.e6_square);
        squares[2][5] = findViewById(R.id.f6_square);
        squares[2][6] = findViewById(R.id.g6_square);
        squares[2][7]= findViewById(R.id.h6_square);

        // Row 5:
        squares[3][0] = findViewById(R.id.a5_square);
        squares[3][1] = findViewById(R.id.b5_square);
        squares[3][2] = findViewById(R.id.c5_square);
        squares[3][3] = findViewById(R.id.d5_square);
        squares[3][4] = findViewById(R.id.e5_square);
        squares[3][5] = findViewById(R.id.f5_square);
        squares[3][6] = findViewById(R.id.g5_square);
        squares[3][7] = findViewById(R.id.h5_square);

        // Row 4:
        squares[4][0] = findViewById(R.id.a4_square);
        squares[4][1] = findViewById(R.id.b4_square);
        squares[4][2] = findViewById(R.id.c4_square);
        squares[4][3] = findViewById(R.id.d4_square);
        squares[4][4] = findViewById(R.id.e4_square);
        squares[4][5] = findViewById(R.id.f4_square);
        squares[4][6] = findViewById(R.id.g4_square);
        squares[4][7] = findViewById(R.id.h4_square);

        // Row 3:
        squares[5][0] = findViewById(R.id.a3_square);
        squares[5][1] = findViewById(R.id.b3_square);
        squares[5][2] = findViewById(R.id.c3_square);
        squares[5][3] = findViewById(R.id.d3_square);
        squares[5][4] = findViewById(R.id.e3_square);
        squares[5][5] = findViewById(R.id.f3_square);
        squares[5][6] = findViewById(R.id.g3_square);
        squares[5][7] = findViewById(R.id.h3_square);

        // Row 2:
        squares[6][0] = findViewById(R.id.a2_square);
        squares[6][1] = findViewById(R.id.b2_square);
        squares[6][2] = findViewById(R.id.c2_square);
        squares[6][3] = findViewById(R.id.d2_square);
        squares[6][4] = findViewById(R.id.e2_square);
        squares[6][5] = findViewById(R.id.f2_square);
        squares[6][6] = findViewById(R.id.g2_square);
        squares[6][7] = findViewById(R.id.h2_square);

        // Row 1:
        squares[7][0] = findViewById(R.id.a1_square);
        squares[7][1] = findViewById(R.id.b1_square);
        squares[7][2] = findViewById(R.id.c1_square);
        squares[7][3] = findViewById(R.id.d1_square);
        squares[7][4] = findViewById(R.id.e1_square);
        squares[7][5] = findViewById(R.id.f1_square);
        squares[7][6] = findViewById(R.id.g1_square);
        squares[7][7] = findViewById(R.id.h1_square);

        this.setInitialPosition();

        // Esto es una prueba:
        casilla = findViewById(R.id.nombre_casilla);
    }

    // Sets the initial position of a game of chess
    private void setInitialPosition(){

        // Black pieces:
        squares[0][0].setImageResource(R.drawable.black_rook);
        squares[0][1].setImageResource(R.drawable.black_knight);
        squares[0][2].setImageResource(R.drawable.black_bishop);
        squares[0][3].setImageResource(R.drawable.black_queen);
        squares[0][4].setImageResource(R.drawable.black_king);
        squares[0][5].setImageResource(R.drawable.black_bishop);
        squares[0][6].setImageResource(R.drawable.black_knight);
        squares[0][7].setImageResource(R.drawable.black_rook);
        squares[1][0].setImageResource(R.drawable.black_pawn);
        squares[1][1].setImageResource(R.drawable.black_pawn);
        squares[1][2].setImageResource(R.drawable.black_pawn);
        squares[1][3].setImageResource(R.drawable.black_pawn);
        squares[1][4].setImageResource(R.drawable.black_pawn);
        squares[1][5].setImageResource(R.drawable.black_pawn);
        squares[1][6].setImageResource(R.drawable.black_pawn);
        squares[1][7].setImageResource(R.drawable.black_pawn);

        // White pieces:
        squares[7][0].setImageResource(R.drawable.white_rook);
        squares[7][1].setImageResource(R.drawable.white_knight);
        squares[7][2].setImageResource(R.drawable.white_bishop);
        squares[7][3].setImageResource(R.drawable.white_queen);
        squares[7][4].setImageResource(R.drawable.white_king);
        squares[7][5].setImageResource(R.drawable.white_bishop);
        squares[7][6].setImageResource(R.drawable.white_knight);
        squares[7][7].setImageResource(R.drawable.white_rook);
        squares[6][0].setImageResource(R.drawable.white_pawn);
        squares[6][1].setImageResource(R.drawable.white_pawn);
        squares[6][2].setImageResource(R.drawable.white_pawn);
        squares[6][3].setImageResource(R.drawable.white_pawn);
        squares[6][4].setImageResource(R.drawable.white_pawn);
        squares[6][5].setImageResource(R.drawable.white_pawn);
        squares[6][6].setImageResource(R.drawable.white_pawn);
        squares[6][7].setImageResource(R.drawable.white_pawn);
    }

    public void selectSquare(View view){

        ImageButton myButton = (ImageButton) view;
        String name = new String();

        switch(myButton.getId()){

            case R.id.a1_square:
                name = "a1";
                break;
            case R.id.a2_square:
                name = "a2";
                break;
            case R.id.a3_square:
                name = "a3";
                break;
            case R.id.a4_square:
                name = "a4";
                break;
            case R.id.a5_square:
                name = "a5";
                break;
            case R.id.a6_square:
                name = "a6";
                break;
            case R.id.a7_square:
                name = "a7";
                break;
            case R.id.a8_square:
                name = "a8";
                break;
            case R.id.b1_square:
                name = "b1";
                break;
            case R.id.b2_square:
                name = "b2";
                break;
            case R.id.b3_square:
                name = "b3";
                break;
            case R.id.b4_square:
                name = "b4";
                break;
            case R.id.b5_square:
                name = "b5";
                break;
            case R.id.b6_square:
                name = "b6";
                break;
            case R.id.b7_square:
                name = "b7";
                break;
            case R.id.b8_square:
                name = "b8";
                break;
            case R.id.c1_square:
                name = "c1";
                break;
            case R.id.c2_square:
                name = "c2";
                break;
            case R.id.c3_square:
                name = "c3";
                break;
            case R.id.c4_square:
                name = "c4";
                break;
            case R.id.c5_square:
                name = "c5";
                break;
            case R.id.c6_square:
                name = "c6";
                break;
            case R.id.c7_square:
                name = "c7";
                break;
            case R.id.c8_square:
                name = "c8";
                break;
            case R.id.d1_square:
                name = "d1";
                break;
            case R.id.d2_square:
                name = "d2";
                break;
            case R.id.d3_square:
                name = "d3";
                break;
            case R.id.d4_square:
                name = "d4";
                break;
            case R.id.d5_square:
                name = "d5";
                break;
            case R.id.d6_square:
                name = "d6";
                break;
            case R.id.d7_square:
                name = "d7";
                break;
            case R.id.d8_square:
                name = "d8";
                break;
            case R.id.e1_square:
                name = "e1";
                break;
            case R.id.e2_square:
                name = "e2";
                break;
            case R.id.e3_square:
                name = "e3";
                break;
            case R.id.e4_square:
                name = "e4";
                break;
            case R.id.e5_square:
                name = "e5";
                break;
            case R.id.e6_square:
                name = "e6";
                break;
            case R.id.e7_square:
                name = "e7";
                break;
            case R.id.e8_square:
                name = "e8";
                break;
            case R.id.f1_square:
                name = "f1";
                break;
            case R.id.f2_square:
                name = "f2";
                break;
            case R.id.f3_square:
                name = "f3";
                break;
            case R.id.f4_square:
                name = "f4";
                break;
            case R.id.f5_square:
                name = "f5";
                break;
            case R.id.f6_square:
                name = "f6";
                break;
            case R.id.f7_square:
                name = "f7";
                break;
            case R.id.f8_square:
                name = "f8";
                break;
            case R.id.g1_square:
                name = "g1";
                break;
            case R.id.g2_square:
                name = "g2";
                break;
            case R.id.g3_square:
                name = "g3";
                break;
            case R.id.g4_square:
                name = "g4";
                break;
            case R.id.g5_square:
                name = "g5";
                break;
            case R.id.g6_square:
                name = "g6";
                break;
            case R.id.g7_square:
                name = "g7";
                break;
            case R.id.g8_square:
                name = "g8";
                break;
            case R.id.h1_square:
                name = "h1";
                break;
            case R.id.h2_square:
                name = "h2";
                break;
            case R.id.h3_square:
                name = "h3";
                break;
            case R.id.h4_square:
                name = "h4";
                break;
            case R.id.h5_square:
                name = "h5";
                break;
            case R.id.h6_square:
                name = "h6";
                break;
            case R.id.h7_square:
                name = "h7";
                break;
            case R.id.h8_square:
                name = "h8";
                break;
        }

        this.registerSquare(name);
    }

    private void registerSquare(String squareName){

        if (this.chosenSquare == null){
            this.chosenSquare = squareName;
            casilla.setText(squareName);
        }
        else{
            casilla.setText("From " + this.chosenSquare + " to " + squareName);
            this.chosenSquare = null;
        }
    }

    public void fakeMethod(){

    }
}