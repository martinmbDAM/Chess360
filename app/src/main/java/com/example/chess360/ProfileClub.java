package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chess360.dao.Dao;
import com.example.chess360.vo.Club;
import com.example.chess360.vo.Player;

public class ProfileClub extends AppCompatActivity {

    private String user;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_club);

        // User that has logged in:
        this.user = retrieveHomeData();

        this.username = findViewById(R.id.profile_club_username);

        this.setUsername();
    }

    private String retrieveHomeData(){
        Bundle extras = getIntent().getExtras();
        String  myUser = extras.getString("PROFILE_CLUB");

        return(myUser);
    }

    private void setUsername(){

        Club myClub = Dao.getClub(this.user);
        String output = "@";
        output += myClub.getUsername();
        this.username.setText(output);
    }
}