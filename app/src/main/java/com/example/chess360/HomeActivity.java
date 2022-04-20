package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chess360.dao.Dao;

public class HomeActivity extends AppCompatActivity {

    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // User that has logged in:
        this.user = retrieveLoginData();
    }

    public void launchPlayZone(View view){
        Intent intent = new Intent(HomeActivity.this, PlayZone.class);
        startActivity(intent);
        finish();
    }

    public void launchProfile(View view){

        // We check whether the user is a player, a club or an organizer:
        if (Dao.isPlayer(this.user)){

            Intent data = new Intent(HomeActivity.this,ProfilePlayer.class);
            data.putExtra("PROFILE_PLAYER", user);
            startActivity(data);
            finish();
        }
        else if (Dao.isClub(this.user)){

            Intent data = new Intent(HomeActivity.this,ProfileClub.class);
            data.putExtra("PROFILE_CLUB", user);
            startActivity(data);
            finish();
        }
        else if (Dao.isOrganizer(this.user)){

            Intent data = new Intent(HomeActivity.this,ProfileOrganizer.class);
            data.putExtra("PROFILE_ORGANIZER", user);
            startActivity(data);
            finish();
        }
    }

    private String retrieveLoginData(){
        Bundle extras = getIntent().getExtras();
        String  myUser = extras.getString("LOGIN");

        return(myUser);
    }
}