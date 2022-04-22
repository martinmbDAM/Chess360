package com.example.chess360;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
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
}