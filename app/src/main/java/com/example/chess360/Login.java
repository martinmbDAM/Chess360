
package com.example.chess360;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chess360.dao.Dao;
import com.example.chess360.dialogs.*;
import com.example.chess360.vo.User;

public class Login extends AppCompatActivity implements ListenerSignUp, ListenerSignUpForm {

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

        // CÓDIGO TEMPORAL:
        Intent data = new Intent(Login.this, HomeActivity.class);
        data.putExtra("LOGIN", user.getText().toString());
        startActivity(data);
        finish();





/*
        User newUser = new User(user.getText().toString());

        int pos = Dao.getUsers().indexOf(newUser);

        // El usuario no existe:
        if (pos == -1){

            ErrorDialog message = new ErrorDialog(ErrorDialog.USER_DOESNT_EXIST);
            message.show(getSupportFragmentManager(), "AlertDialog");

        }
        else{

            // Contraseña encriptada:
            String encrypted = encryptPass(pass.getText().toString());

            // El usuario existe pero la contraseña es incorrecta:
            if (!Dao.getUsers().get(pos).getPassword().equals(encrypted)){

                ErrorDialog message = new ErrorDialog(ErrorDialog.INCORRECT_PASS);
                message.show(getSupportFragmentManager(), "AlertDialog");
            }
            // Usuario y contraseña coinciden:
            else{
                Intent data = new Intent(Login.this,HomeActivity.class);
                data.putExtra("LOGIN", user.getText().toString());
                startActivity(data);
                finish();
            }
        }

        user.setText("");
        pass.setText("");
*/
    }

    // Shows the user the form to introduce their data:
    public void registerUser(View view) {

        SignUpDialog message = new SignUpDialog();
        message.show(getSupportFragmentManager(), "AlertDialog");
/*
        OldSignUpDialog message = new OldSignUpDialog();
        message.show(getSupportFragmentManager(), "AlertDialog"); */

        user.setText("");
        pass.setText("");
    }

    /*Método al que se llama cuando el usuario completa el formulario de registro. 4
      escenarios posibles:

      1. El usuario no ha cubierto todos los campos -> Se muestra un mensaje de error
      2. Las contraseñas no coinciden -> Se muestra un mensaje de error
      3. El usuario ya existe -> Se muestra un mensaje de error
      4. El email no es válido -> Se muestra un mensaje de error
      5. La contraseña no tiene formato correcto (al menos 8 caracteres y debe contener
         minúsculas, mayúsculasy dígitos) -> Se muestra un mensaje de error
      6. Los datos son correctos -> Se registra al usuario

     */

    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSignUpClick(String[] data) {

        if (data[0].isEmpty() || data[1].isEmpty() || data[2].isEmpty() ||
                data[3].isEmpty() || data[4].isEmpty() || data[5].isEmpty()) {

            ErrorDialog message = new ErrorDialog(ErrorDialog.EMPTY_FIELDS);
            message.show(getSupportFragmentManager(), "AlertDialog");
        } else {
            // Las contraseñas no coinciden:
            if (!data[4].equals(data[5])) {

                ErrorDialog message = new ErrorDialog(ErrorDialog.PASS_DONT_MATCH);
                message.show(getSupportFragmentManager(), "AlertDialog");
            } else {

                // El usuario está repetido:
                //      if (Dao.getUsers().indexOf(new User(data[3])) != -1){
                if (true) {

                    ErrorDialog message = new ErrorDialog(ErrorDialog.USER_EXISTS);
                    message.show(getSupportFragmentManager(), "AlertDialog");
                } else {
                    // El email es incorrecto:
                    if (!data[2].contains("@")) {

                        ErrorDialog message = new ErrorDialog(ErrorDialog.INCORRECT_EMAIL);
                        message.show(getSupportFragmentManager(), "AlertDialog");
                    } else {
                        // La contraseña no tiene un formato válido:
                        if (!isValidPassword(data[4])) {

                            ErrorDialog message = new ErrorDialog(ErrorDialog.INVALID_PASSWORD);
                            message.show(getSupportFragmentManager(), "AlertDialog");
                        }
                        // Datos correctos. Se registra al usuario:
                        else {

                            // Se encripta la contraseña:
                            String encrypted = encryptPass(data[4]);

                            // Se añade el nuevo usuario:
                            //     Dao.addUser(new User(data[0], data[1], data[2], data[3],encrypted));

                            ConfirmationDialog message = new ConfirmationDialog(ConfirmationDialog.USER_ADDED);
                            message.show(getSupportFragmentManager(), "AlertDialog");
                        }
                    }

                }

            }
        }
    } */

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

    public void onSignUpClick(String input) {

        switch (input) {

            case "Player":
                SignUpPlayer dialogPlayer = new SignUpPlayer();
                dialogPlayer.show(getSupportFragmentManager(), "AlertDialog");
                break;
            case "Chess club":
                SignUpClub dialogClub = new SignUpClub();
                dialogClub.show(getSupportFragmentManager(), "AlertDialog");
                break;
            case "Tournament organizer":
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
        if (this.thereAreEmptyFields(data)) {

            ErrorDialog message = new ErrorDialog(ErrorDialog.EMPTY_FIELDS);
            message.show(getSupportFragmentManager(), "AlertDialog");
        } else {
            // Passwords don't match
            if (!this.passwordsMatch(pass1, pass2)) {

                ErrorDialog message = new ErrorDialog(ErrorDialog.PASS_DONT_MATCH);
                message.show(getSupportFragmentManager(), "AlertDialog");
            } else {

                // The user is already registered:
                if (this.userExists(user)) {

                    ErrorDialog message = new ErrorDialog(ErrorDialog.USER_EXISTS);
                    message.show(getSupportFragmentManager(), "AlertDialog");
                } else {
                    // The email has an incorrect format:
                    if (!this.emailHasValidFormat(email)) {

                        ErrorDialog message = new ErrorDialog(ErrorDialog.INCORRECT_EMAIL);
                        message.show(getSupportFragmentManager(), "AlertDialog");
                    } else {
                        // Password doesn't have a valid format:
                        if (!isValidPassword(pass1)) {

                            ErrorDialog message = new ErrorDialog(ErrorDialog.INVALID_PASSWORD);
                            message.show(getSupportFragmentManager(), "AlertDialog");
                        } else {

                            // The user hasn't accepted the legal agreement:
                            if (!this.hasAcceptedLegalAgreement(legal_agreement)) {

                                ErrorDialog message = new ErrorDialog(ErrorDialog.CHECKBOX_NOT_SELECTED);
                                message.show(getSupportFragmentManager(), "AlertDialog");
                            }
                            else {

                                // The phone has nonnumeric characters:
                                if (this.hasNonnumericCharacters(phone)){

                                    ErrorDialog message = new ErrorDialog(ErrorDialog.NONNUMERIC_CHARACTERS);
                                    message.show(getSupportFragmentManager(), "AlertDialog");
                                }
                                // Correct data. The password is registered:
                                else{

                                    // The password is encrypted:
                                    String encrypted = encryptPass(data[5]);

                                    // The user is added:
                                    // Dao.addUser(new User(data[0], data[1], data[2], data[3],encrypted));

                                    ConfirmationDialog message = new ConfirmationDialog(ConfirmationDialog.USER_ADDED);
                                    message.show(getSupportFragmentManager(), "AlertDialog");
                                }
                            }
                        }
                    }

                }

            }
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