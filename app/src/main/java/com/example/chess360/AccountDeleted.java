package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chess360.dao.Dao;

public class AccountDeleted extends AppCompatActivity {

    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_deleted);

        // Data from the profile:
        Bundle extras = getIntent().getExtras();
        this.userType = extras.getString("PROFILE");
    }

    public void logOut(View view){

        Intent i;

        switch(this.userType){
            case "PLAYER":
                i = new Intent(this, ProfilePlayer.class);
                i.putExtra("LOGOUT","YES");
                setResult(RESULT_OK, i);
                finish();
                break;
            case "CLUB":
                i = new Intent(this, ProfileClub.class);
                i.putExtra("LOGOUT","YES");
                setResult(RESULT_OK, i);
                finish();
                break;
            case "ORGANIZER":
                i = new Intent(this, ProfileOrganizer.class);
                i.putExtra("LOGOUT","YES");
                setResult(RESULT_OK, i);
                finish();
                break;
        }
    }
}