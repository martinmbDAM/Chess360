package com.example.chess360.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.chess360.R;

public class ConfirmationDialog extends DialogFragment {

    private int option;
    private String date, hour;
    private String message, title;

    public static final int USER_ADDED = 0;
    public static final int POST_PUBLISHED = 1;

    public ConfirmationDialog(int option){
        this.option = option;
    }

    public ConfirmationDialog(int option, String date, String hour){
        this.option = option;
        this.date = date;
        this.hour = hour;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        switch(option){
            case USER_ADDED:
                message = getActivity().getString(R.string.dialog_user_added);
                title = getActivity().getString(R.string.dialog_user_added_title);
                break;
            case POST_PUBLISHED:
                message = getResources().getString(R.string.post_confirmation);
                title = getResources().getString(R.string.post_confirmation_title);
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setIcon(R.drawable.tick);
        builder.setTitle(title);

        builder.setPositiveButton(R.string.dialog_continue, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
            }
        });

        return builder.create();
    }
}

