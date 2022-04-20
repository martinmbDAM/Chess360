package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chess360.dao.Dao;
import com.example.chess360.vo.Player;

public class ProfilePlayer extends AppCompatActivity {

    private String user;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_player);

        // User that has logged in:
        this.user = retrieveHomeData();

        this.username = findViewById(R.id.profile_player_username);

        this.setUsername();
    }

    private String retrieveHomeData(){
        Bundle extras = getIntent().getExtras();
        String  myUser = extras.getString("PROFILE_PLAYER");

        return(myUser);
    }

    private void setUsername(){

        Player myPlayer = Dao.getPlayer(this.user);
        String output = "@";
        output += myPlayer.getUsername();
        this.username.setText(output);
    }
}