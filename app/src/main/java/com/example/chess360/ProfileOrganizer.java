package com.example.chess360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.chess360.vo.Organizer;
import com.example.chess360.vo.Relationship;
import com.example.chess360.vo.User;

public class ProfileOrganizer extends AppCompatActivity implements ListenerDeleteAccount {

    private String userProfile;
    private String userSearch;
    private TextView username;
    private boolean searchingUser;
    private boolean isFollowing;

    private Button post, follow, delete;

    private TextView name, location, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_organizer);

        // Buttons:
        post = findViewById(R.id.profile_organizer_post_button);
        follow = findViewById(R.id.profile_organizer_follow_button);
        delete = findViewById(R.id.profile_organizer_delete_button);

        // User data:
        this.username = findViewById(R.id.profile_organizer_username);
        this.name = findViewById(R.id.profile_organizer_name_input);
        this.location = findViewById(R.id.profile_organizer_location_input);
        this.phone = findViewById(R.id.profile_organizer_phone_input);

        // User input:
        String [] userInput = retrieveHomeData();
        this.userProfile = userInput[0];
        this.userSearch = userInput[1];

        // Searching user?:
        this.searchingUser = this.userSearch==null? false:true;

        this.showUserData();
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

        Organizer myOrganizer;

        if (!this.searchingUser){
            myOrganizer = Dao.getOrganizer(this.userProfile);
        }
        else{
            myOrganizer = Dao.getOrganizer(this.userSearch);
        }

        // Username:
        String output = "@";
        output += myOrganizer.getUsername();
        this.username.setText(output);

        // Name:
        this.name.setText(myOrganizer.getName());

        // Location:
        this.location.setText(myOrganizer.getLocation());

        // Phone:
        this.phone.setText(myOrganizer.getPhone());
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

    public void followOrganizer(View view){

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

            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("LOGOUT","YES");
            setResult(RESULT_OK, i);
            Dao.deleteUser(this.userProfile);
            finish();

            ConfirmationDialog message = new ConfirmationDialog(ConfirmationDialog.USER_DELETED);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
    }
}