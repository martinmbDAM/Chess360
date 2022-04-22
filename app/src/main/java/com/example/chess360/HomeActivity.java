package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chess360.dao.Dao;
import com.example.chess360.lists.PostList;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private String user;
    private ListView postsList;
    private PostList lAdapter;
    private ArrayList availablePosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postsList = findViewById(R.id.user_post_list);

        // User that has logged in:
        this.user = retrieveLoginData();

        this.availablePosts = Dao.getPosts();

        // Show posts of the users this user follows:
        this.showPosts();

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

    private void showPosts(){

        // We fill the list with the available posts:
        lAdapter = new PostList(HomeActivity.this, availablePosts);
        postsList.setAdapter(lAdapter);
        postsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // chosenFlight = i;
               // bookFlight();
            }
        });

    }

    // Creates the option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    // Método que da funcionalidad al menú de opciones:
    @Override
    public boolean
    onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home_log_out_button:
                launchLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Log out:
    private void launchLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
   //     reset();
        finish();
    }
}