package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chess360.dao.Dao;
import com.example.chess360.vo.Club;
import com.example.chess360.vo.Organizer;

public class ProfileOrganizer extends AppCompatActivity {

    private String user;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_organizer);

        // User that has logged in:
        this.user = retrieveHomeData();

        this.username = findViewById(R.id.profile_organizer_username);

        this.setUsername();
    }

    private String retrieveHomeData(){
        Bundle extras = getIntent().getExtras();
        String  myUser = extras.getString("PROFILE_ORGANIZER");

        return(myUser);
    }

    private void setUsername(){

        Organizer myOrganizer = Dao.getOrganizer(this.user);
        String output = "@";
        output += myOrganizer.getUsername();
        this.username.setText(output);
    }
}