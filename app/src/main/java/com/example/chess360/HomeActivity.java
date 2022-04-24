package com.example.chess360;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.chess360.dao.Dao;
import com.example.chess360.dialogs.ConfirmationDialog;
import com.example.chess360.dialogs.ErrorDialog;
import com.example.chess360.dialogs.ListenerSearch;
import com.example.chess360.dialogs.SearchUserDialog;
import com.example.chess360.lists.PostList;
import com.example.chess360.vo.Post;
import com.example.chess360.vo.User;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements ListenerSearch {

    private String user;
    private ListView postsList;
    private PostList lAdapter;
    private ArrayList<Post> availablePosts;

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
                                launchLogin();
                            }
                            else{
                                showPosts();
                            }
                        }

                    } else
                        Log.d("tag", String.valueOf(R.string.home_intent_text));
                }
            }
    );

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postsList = findViewById(R.id.user_post_list);

        // User that has logged in:
        this.user = retrieveLoginData();

        // Show posts of the users this user follows:
        this.showPosts();

    }

    public void launchPlayZone(View view){
        Intent intent = new Intent(HomeActivity.this, PlayZone.class);
        startActivity(intent);
        finish();
    }

    public void launchProfile(View view){

        this.launchProfile(this.user, null);
    }

    public void launchProfile(String currentUser, String searchedUser){

        // We check whether the user is a player, a club or an organizer:
        if ((searchedUser!=null && Dao.isPlayer(searchedUser)) || (searchedUser==null && Dao.isPlayer(currentUser))){
            Intent data = new Intent(HomeActivity.this,ProfilePlayer.class);
            data.putExtra("CURRENT_USER", currentUser);
            data.putExtra("SEARCHED_USER", searchedUser);
            launcher.launch(data);
        }
        else if ((searchedUser!=null && Dao.isClub(searchedUser)) || (searchedUser==null && Dao.isClub(currentUser))){
            Intent data = new Intent(HomeActivity.this,ProfileClub.class);
            data.putExtra("CURRENT_USER", currentUser);
            data.putExtra("SEARCHED_USER", searchedUser);
            launcher.launch(data);
        }
        else if ((searchedUser!=null && Dao.isOrganizer(searchedUser)) || (searchedUser==null && Dao.isOrganizer(currentUser))){
            Intent data = new Intent(HomeActivity.this,ProfileOrganizer.class);
            data.putExtra("CURRENT_USER", currentUser);
            data.putExtra("SEARCHED_USER", searchedUser);
            launcher.launch(data);
        }

    }

    private String retrieveLoginData(){
        Bundle extras = getIntent().getExtras();
        String  myUser = extras.getString("LOGIN");

        return(myUser);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showPosts(){

        this.availablePosts = this.getAvailablePosts();

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

    public void searchUser(View view){

        SearchUserDialog myDialog = new SearchUserDialog();
        myDialog.show(getSupportFragmentManager(), "AlertDialog");
    }

    public void onSearchClick(String [] input){

        User myUser = null;
        String name = input[0];
        String username = input[1];

        if (name.equals("") && username.equals("")){

            ErrorDialog message = new ErrorDialog(ErrorDialog.BOTH_FIELDS_EMPTY);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        else{
            // First, we search by username:
            if (username != ""){
                myUser = Dao.getUser(input[1]);
            }

            // If the search by username wasn't succesful, we search by name:
            if (myUser == null && !name.equals("")){
                myUser = Dao.getUserByName(name);
            }

            // The user exists:
            if (myUser != null){

                this.launchProfile(this.user, myUser.getUsername());
            }
            // The user doesn't exist:
            else{
                ErrorDialog message = new ErrorDialog(ErrorDialog.USER_DOESNT_EXIST);
                message.show(getSupportFragmentManager(), "AlertDialog");
            }
        }

    }

    private ArrayList<Post> getAvailablePosts(){

        return Dao.getAvailablePosts(this.user);
    }
}