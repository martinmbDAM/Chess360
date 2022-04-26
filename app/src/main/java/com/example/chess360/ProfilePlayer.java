package com.example.chess360;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chess360.dao.Dao;
import com.example.chess360.dialogs.ConfirmationDialog;
import com.example.chess360.dialogs.DialogDeleteAccount;
import com.example.chess360.dialogs.ListenerDeleteAccount;
import com.example.chess360.dialogs.ListenerPost;
import com.example.chess360.dialogs.MakePostDialog;
import com.example.chess360.vo.Player;
import com.example.chess360.vo.Post;
import com.example.chess360.vo.Relationship;
import com.example.chess360.vo.User;

public class ProfilePlayer extends AppCompatActivity implements ListenerPost, ListenerDeleteAccount {

    public static final int DELETE_ACCOUNT = 0;

    private String userProfile;
    private String userSearch;
    private TextView username;
    private boolean searchingUser;
    private boolean isFollowing;

    private Button post, follow, delete;

    private TextView name, surname, elo, club, coach;

    // ActivityResultLauncher to launch several activities:
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(

            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();

                        if (data.hasExtra("LOGOUT")){
                            if (data.getExtras().getString("LOGOUT").equals("YES")){

                                Intent i = new Intent(ProfilePlayer.this, HomeActivity.class);
                                i.putExtra("LOGOUT","YES");
                                setResult(RESULT_OK, i);
                                finish();
                            }
                        }

                    } else
                        Log.d("tag", String.valueOf(R.string.home_intent_text));
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_player);

        // Buttons:
        post = findViewById(R.id.profile_player_post_button);
        follow = findViewById(R.id.profile_player_follow_button);
        delete = findViewById(R.id.profile_player_delete_button);

        // User data:
        this.username = findViewById(R.id.profile_player_username);
        this.name = findViewById(R.id.profile_player_name_input);
        this.surname = findViewById(R.id.profile_player_surname_input);
        this.elo = findViewById(R.id.profile_player_elo_input);
        this.club = findViewById(R.id.profile_player_club_input);
        this.coach = findViewById(R.id.profile_player_coach_input);

        // User input:
        String [] userInput = retrieveHomeData();
        this.userProfile = userInput[0];
        this.userSearch = userInput[1];

        // Searching user?:
        this.searchingUser = this.userSearch==null? false:true;

        this.showUserData();
        this.isFollowing();
        this.showButtons();
    }

    private void showButtons(){

        if (this.searchingUser && !this.userProfile.equals(this.userSearch)){
            post.setVisibility(View.GONE);
            follow.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
        }
        else{
            post.setVisibility(View.VISIBLE);
            follow.setVisibility(View.GONE);
            delete.setVisibility(View.VISIBLE);
        }
    }

    private String[] retrieveHomeData(){

        String [] output = new String[2];
        Bundle extras = getIntent().getExtras();
        output[0] = extras.getString("CURRENT_USER");
        output[1] = extras.getString("SEARCHED_USER");

        return(output);
    }

    private void showUserData(){

        Player myPlayer;

        if (!this.searchingUser){
            myPlayer = Dao.getPlayer(this.userProfile);
        }
        else{
            myPlayer = Dao.getPlayer(this.userSearch);
        }

        // Username:
        String output = "@";
        output += myPlayer.getUsername();
        this.username.setText(output);

        // Name:
        this.name.setText(myPlayer.getName());

        // Surname:
        this.surname.setText(myPlayer.getSurname());

        // ELO:
        this.elo.setText(Integer.toString(myPlayer.getElo()));

        // Club:
        if (myPlayer.getClub() != null){
            this.club.setText(myPlayer.getClub().getName());
        }
        else{
            this.club.setText(" - ");
        }

        // Coach:
        if (myPlayer.getCoach() != null){
            this.coach.setText(myPlayer.getCoach().getUsername());
        }
        else{
            this.coach.setText(" - ");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onPostClick(String post){

        // User:
        User myUser = Dao.getUser(this.username.getText().toString().substring(1));

        // Post:
        Post myPost = new Post(post,myUser);

        // Post is added:
        Dao.addPost(myPost);

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

    public void followPlayer(View view){

        User myUserProfile = Dao.getUser(this.userProfile);
        User myUserSearch = Dao.getUser(this.userSearch);
        Relationship newRelationship = new Relationship(myUserProfile, myUserSearch);

        if (!this.isFollowing){
            Dao.addRelationship(newRelationship);
        }
        else{
            Dao.deleteRelationship(newRelationship);
        }

        isFollowing();
    }

    private void isFollowing(){

        if (this.searchingUser){

            User myUserProfile = Dao.getUser(this.userProfile);
            User myUserSearch = Dao.getUser(this.userSearch);

            // We check whether the user follows this player:
            Relationship myRelationship = new Relationship(myUserProfile,myUserSearch);
            int index = Dao.getRelationshipIndex(myRelationship);

            if (index != -1){
                follow.setText(getResources().getString(R.string.profile_unfollow));
                this.isFollowing = true;
            }
            else{
                follow.setText(getResources().getString(R.string.profile_follow));
                this.isFollowing = false;
            }
        }

    }

    public void deleteAccount(View view){

        DialogDeleteAccount myDialog = new DialogDeleteAccount();
        myDialog.show(getSupportFragmentManager(), "AlertDialog");

    }

    public void onDeleteClick(int option){

        if (option == ProfilePlayer.DELETE_ACCOUNT){


            Dao.deleteUser(this.userProfile);
            Intent i = new Intent(ProfilePlayer.this, AccountDeleted.class);
            i.putExtra("PROFILE","PLAYER");
            launcher.launch(i);
        }
    }

    public void seeStatistics(View view){

    }

    public void seeAchievements(View view){

    }
}