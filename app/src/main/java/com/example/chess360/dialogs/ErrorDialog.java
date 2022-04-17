package com.example.chess360.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.chess360.R;

public class ErrorDialog extends DialogFragment {

    // The user has left empty fields in the Sign Up form:
    public static final int EMPTY_FIELDS = 0;

    // The user has introduced two different passwords in the Sign Up form:
    public static final int PASS_DONT_MATCH = 1;

    // The user has tried to register an already existing user:
    public static final int USER_EXISTS = 2;

    // The email doesn't have a valid format:
    public static final int INCORRECT_EMAIL = 3;

    // The password doesn't fulfill all the requirements:
    public static final int INVALID_PASSWORD = 4;

    // The user doesn't exist:
    public static final int USER_DOESNT_EXIST = 5;

    // The user exists, but the password is incorrect:
    public static final int INCORRECT_PASS = 6;

    // The phone introuduced in the Sign Up Form contains nonnumeric characters:
    public static final int NONNUMERIC_CHARACTERS = 7;

    // The legal agreement hasn't been accepted:
    public static final int CHECKBOX_NOT_SELECTED = 8;

    // There's already an account with that email:
    public static final int EMAIL_IN_USE = 9;

    private String message;
    private int option;

    public ErrorDialog(int i){

        option = i;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        switch(option){
            case ErrorDialog.EMPTY_FIELDS:
                message = getActivity().getString(R.string.error_empty);
                break;
            case ErrorDialog.INCORRECT_PASS:
                message = getActivity().getString(R.string.error_login);
                break;
            case ErrorDialog.USER_EXISTS:
                message = getActivity().getString(R.string.error_user_exists);
                break;
            case ErrorDialog.PASS_DONT_MATCH:
                message = getActivity().getString(R.string.error_pass_dont_match);
                break;
            case ErrorDialog.USER_DOESNT_EXIST:
                message = getActivity().getString(R.string.error_missing_user);
                break;
            case ErrorDialog.INCORRECT_EMAIL:
                message = getActivity().getString(R.string.error_email_invalid);
                break;
            case ErrorDialog.NONNUMERIC_CHARACTERS:
                message = getActivity().getString(R.string.error_non_numeric_characters);
                break;
            case ErrorDialog.INVALID_PASSWORD:
                message = getResources().getString(R.string.error_invalid_pass);
                break;
            case ErrorDialog.CHECKBOX_NOT_SELECTED:
                message = getResources().getString(R.string.error_checkbox);
                break;
            case ErrorDialog.EMAIL_IN_USE:
                message = getResources().getString(R.string.error_email_in_use);
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setIcon(R.drawable.error);
        builder.setTitle(R.string.error);

        builder.setPositiveButton(R.string.dialog_continue, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
            }
        });

        return builder.create();
    }
}
