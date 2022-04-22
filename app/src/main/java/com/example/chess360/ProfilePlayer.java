package com.example.chess360;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.chess360.dao.Dao;
import com.example.chess360.dialogs.ConfirmationDialog;
import com.example.chess360.dialogs.ListenerPost;
import com.example.chess360.dialogs.MakePostDialog;
import com.example.chess360.vo.Player;
import com.example.chess360.vo.Post;
import com.example.chess360.vo.User;

import java.time.LocalDateTime;

public class ProfilePlayer extends AppCompatActivity implements ListenerPost {

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onPostClick(String post){

        // User:
        User myUser = Dao.getUser(this.username.getText().toString());

        // Post:
        Post myPost = new Post(post,myUser);

        ConfirmationDialog message = new ConfirmationDialog(ConfirmationDialog.POST_PUBLISHED);
        message.show(getSupportFragmentManager(), "AlertDialog");
    }
    
    public void makePost(View view){

        MakePostDialog myDialog = new MakePostDialog();
        myDialog.show(getSupportFragmentManager(), "AlertDialog");
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