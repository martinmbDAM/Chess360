package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    // Creates the option menu:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.full_menu, menu);
        return true;
    }

    // Sets the functionality of the options menu:
    @Override
    public boolean
    onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.full_menu_go_back_button:
                Intent i = new Intent(this, HomeActivity.class);
                i.putExtra("LOGOUT","NO");
                setResult(RESULT_OK, i);
                finish();
                return true;
            case R.id.full_menu_log_out_button:
                i = new Intent(this, HomeActivity.class);
                i.putExtra("LOGOUT","YES");
                setResult(RESULT_OK, i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}