
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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chess360.dao.Dao;
import com.example.chess360.dialogs.*;
import com.example.chess360.vo.User;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements ListenerSignUp, ListenerSignUpForm {

    public static final int LOGIN_PLAYER = 0;
    public static final int LOGIN_CLUB = 1;
    public static final int LOGIN_ORGANIZER = 2;

    // Minimum number of charachters the password must have:
    private final int MIN_CHARACTERS = 8;

    // Encryption key:
    private final int KEY = 3;

    // Login data:
    private EditText user, pass;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.login_user);
        pass = (EditText) findViewById(R.id.login_pass);

        // Initialize database:
        Dao.initialize();
    }

    /* Method that is called when the user tries to log in. There are 3 possible scenarios:

       1. User doesn't exist ---> A message error is shown
       2. User and password don't match ---> A message error is shown
       3. User and password match ---> User has acces to the Home Activity

    */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void searchUser(View view) {

        User newUser = new User(user.getText().toString());

        int pos = Dao.getUsers().indexOf(newUser);

        // The user doesn't exist:
        if (pos == -1){

            ErrorDialog message = new ErrorDialog(ErrorDialog.USER_DOESNT_EXIST);
            message.show(getSupportFragmentManager(), "AlertDialog");

        }
        else{

            // Encrypted password:
            String encrypted = encryptPass(pass.getText().toString());

            // The user exists but the password is incorrect
            if (!Dao.getUsers().get(pos).getPassword().equals(encrypted)){

                ErrorDialog message = new ErrorDialog(ErrorDialog.INCORRECT_PASS);
                message.show(getSupportFragmentManager(), "AlertDialog");
            }
            // User and password match:
            else{

                Intent data = new Intent(Login.this,HomeActivity.class);
                data.putExtra("LOGIN", user.getText().toString());
                startActivity(data);
                finish();

            }
        }

        user.setText("");
        pass.setText("");

    }

    // Shows the user the form to introduce their data:
    public void registerUser(View view) {

        SignUpDialog message = new SignUpDialog();
        message.show(getSupportFragmentManager(), "AlertDialog");

        user.setText("");
        pass.setText("");
    }

    // Método que comprueba si una contraseña llega al mínimo de caracteres:
    private boolean isLongEnough(String pass) {
        return (pass.length() >= MIN_CHARACTERS);
    }

    // Método que comprueba si una contraseña contiene una mayúscula:
    private boolean hasUpperCase(String pass) {

        boolean found = false;
        int pos = 0;

        while (!found && pos < pass.length()) {

            found = Character.isUpperCase(pass.charAt(pos));
            pos++;
        }

        return (found);
    }

    // Método que comprueba si una contraseña contiene una minúscula:
    private boolean hasLowerCase(String pass) {

        boolean found = false;
        int pos = 0;

        while (!found && pos < pass.length()) {

            found = Character.isLowerCase(pass.charAt(pos));
            pos++;
        }

        return (found);
    }

    // Método que comprueba si una contraseña contiene un número:
    private boolean hasDigit(String pass) {

        boolean found = false;
        int pos = 0;

        while (!found && pos < pass.length()) {
            found = Character.isDigit(pass.charAt(pos));
            pos++;
        }

        return (found);
    }

    // Método que comprueba si una contraseña contiene caracteres no alfanuméricos:
    private boolean hasNonAlphaNumeric(String pass) {

        boolean found = false;
        int pos = 0;

        while (!found && pos < pass.length()) {

            found = !(Character.isDigit(pass.charAt(pos)) || Character.isAlphabetic(pass.charAt(pos)));
            pos++;
        }

        return (found);
    }

    // Método que comprueba si una contraseña es válida:
    private boolean isValidPassword(String pass) {

        return (isLongEnough(pass) && hasUpperCase(pass) &&
                hasLowerCase(pass) && hasDigit(pass) &&
                hasNonAlphaNumeric(pass));
    }

    // Algoritmo que encripta una contraseña:
    private String encryptPass(String pass) {

        char[] chars = pass.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            chars[i] += KEY;
        }

        return (String.valueOf(chars));
    }

    public void onSignUpClick(int option) {

        switch (option) {

            case Login.LOGIN_PLAYER:
                SignUpPlayer dialogPlayer = new SignUpPlayer();
                dialogPlayer.show(getSupportFragmentManager(), "AlertDialog");
                break;
            case Login.LOGIN_CLUB:
                SignUpClub dialogClub = new SignUpClub();
                dialogClub.show(getSupportFragmentManager(), "AlertDialog");
                break;
            case Login.LOGIN_ORGANIZER:
                SignUpOrganizer dialogOrganizer = new SignUpOrganizer();
                dialogOrganizer.show(getSupportFragmentManager(), "AlertDialog");
                break;
        }

    }

    public void onSignUpFormClick(String[] data, int code) {

        // Variables to check whether the data is correct:
        String pass1 = new String();
        String pass2 = new String();
        String user = new String();
        String email = new String();
        String phone = new String();
        String legal_agreement = new String();

        switch (code) {
            case ListenerSignUpForm.SIGN_UP_PLAYER:
                pass1 = data[5];
                pass2 = data[6];
                user = data[3];
                email = data[2];
                legal_agreement = data[7];
                break;
            case ListenerSignUpForm.SIGN_UP_CLUB:
            case ListenerSignUpForm.SIGN_UP_ORGANIZER:
                pass1 = data[5];
                pass2 = data[6];
                user = data[1];
                email = data[3];
                phone = data[2];
                legal_agreement = data[7];
                break;
        }

        // The user has left empty fields:
        if (this.thereAreEmptyFields(data)){

            ErrorDialog message = new ErrorDialog(ErrorDialog.EMPTY_FIELDS);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        // Passwords don't match
        else if (!this.passwordsMatch(pass1, pass2)){

            ErrorDialog message = new ErrorDialog(ErrorDialog.PASS_DONT_MATCH);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        // The user is already registered:
        else if (this.userExists(user)){

            ErrorDialog message = new ErrorDialog(ErrorDialog.USER_EXISTS);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        // The email has already been used:
        else if (this.emailExists(email)){

            ErrorDialog message = new ErrorDialog(ErrorDialog.EMAIL_IN_USE);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        // The email has an incorrect format:
        else if (!this.emailHasValidFormat(email)){

            ErrorDialog message = new ErrorDialog(ErrorDialog.INCORRECT_EMAIL);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        // Password doesn't have a valid format:
        else if (!isValidPassword(pass1)){

            ErrorDialog message = new ErrorDialog(ErrorDialog.INVALID_PASSWORD);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        // The phone has nonnumeric characters:
        else if (this.hasNonnumericCharacters(phone)){

            ErrorDialog message = new ErrorDialog(ErrorDialog.NONNUMERIC_CHARACTERS);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        // The user hasn't accepted the legal agreement:
        else if (!this.hasAcceptedLegalAgreement(legal_agreement)){

            ErrorDialog message = new ErrorDialog(ErrorDialog.CHECKBOX_NOT_SELECTED);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }
        // The data is correct. The user is registered.
        else{

            // The password is encrypted:
            String encrypted = encryptPass(data[5]);

            // The user is added:
            // Dao.addUser(new User(data[0], data[1], data[2], data[3],encrypted));

            ConfirmationDialog message = new ConfirmationDialog(ConfirmationDialog.USER_ADDED);
            message.show(getSupportFragmentManager(), "AlertDialog");
        }

    }

    private boolean thereAreEmptyFields(String[] data) {

        boolean empty = false;
        int pos = 0;

        while (!empty && pos < data.length) {

            empty = data[pos].isEmpty();

            if (!empty) {
                pos++;
            }
        }

        return empty;
    }

    private boolean passwordsMatch(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

    private boolean userExists(String user) {
        return Dao.getUsers().indexOf(new User(user)) != -1;
    }

    private boolean emailExists(String email){

        ArrayList<User> users = Dao.getUsers();

        boolean found = false;
        int pos = 0;

        while (!found && pos<users.size()){

            found = users.get(pos).getEmail().equals(email);

            if (!found){
                pos++;
            }
        }

        return found;
    }

    private boolean emailHasValidFormat(String email) {
        return email.contains("@");
    }

    private boolean hasNonnumericCharacters(String phone) {

        boolean nonnumeric = false;
        int pos = 0;

        if (!nonnumeric && pos < phone.length()) {
            char c = phone.charAt(pos);

            nonnumeric = !Character.isDigit(c);

            if (!nonnumeric) {
                pos++;
            }
        }

        return nonnumeric;
    }

    private boolean hasAcceptedLegalAgreement(String input) {
        return input.equals("YES");
    }
}