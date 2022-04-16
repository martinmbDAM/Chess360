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

    public static final int USER_ADDED = 1;
    public static final int BOOKED_FLIGHT = 2;
    public static final int RESERVATION_MODIFIED = 3;
    public static final int CANCELED_FLIGHT = 4;
    public static final int NOTIFICATION_SET = 5;

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
           /* case NOTIFICATION_SET:
                message = getResources().getString(R.string.notification_1) + " " +
                        date + " " + getResources().getString(R.string.notification_2) +
                        " " + hour;
                title = getResources().getString(R.string.notification_title);
                break;
            case RESERVATION_MODIFIED:
                message = getActivity().getString(R.string.modify_success);
                title = getActivity().getString(R.string.modify_success_title);
                break; */
            case USER_ADDED:
                message = getActivity().getString(R.string.dialog_user_added);
                title = getActivity().getString(R.string.dialog_user_added_title);
                break;
          /*  case CANCELED_FLIGHT:
                message = getActivity().getString(R.string.cancelled_message);
                title = getActivity().getString(R.string.cancelled_title);
                break;
            case BOOKED_FLIGHT:
                message = getActivity().getString(R.string.booked_flight);
                title = getActivity().getString(R.string.booked_fligth_title);
                break; */
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

