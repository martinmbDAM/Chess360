package com.example.chess360.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.chess360.R;

public class ErrorDialog extends DialogFragment {

    public static final int EMPTY_FIELDS = 1;
    public static final int PASS_DONT_MATCH = 2;
    public static final int USER_EXISTS = 3;
    public static final int INCORRECT_EMAIL = 4;
    public static final int INVALID_PASSWORD = 5;
    public static final int USER_DOESNT_EXIST = 6;
    public static final int INCORRECT_PASS = 7;
    public static final int EMPTY_STOPS = 8;
    public static final int EMPTY_PASSENGERS = 9;
    public static final int INVALID_ROUTE = 10;
    public static final int PASSED_DATE = 11;
    public static final int REVERSED_DATES = 12;
    public static final int FUTURE_DATE = 13;
    public static final int FULL_FLIGHT = 14;
    public static final int NOT_ENOUGH_SEATS = 15;
    public static final int PASSED_FLIGHT = 16;
    public static final int INMINENT_FLIGHT = 17;
    public static final int NONNUMERIC_CHARACTERS = 18;
    public static final int OUT_OF_RANGE = 19;
    public static final int PASSED_HOUR = 20;
    public static final int FUTURE_HOUR = 21;

    private String message;
    private int option;
    private int numPassengers = -1;

    public ErrorDialog(int i){

        option = i;
    }

    public ErrorDialog(int i, int num){

        option = i;
        numPassengers = num;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        switch(option){
            case EMPTY_FIELDS:
                message = getActivity().getString(R.string.error_empty);
                break;
            case INCORRECT_PASS:
                message = getActivity().getString(R.string.error_login);
                break;
            case USER_EXISTS:
                message = getActivity().getString(R.string.error_user_exists);
                break;
            case PASS_DONT_MATCH:
                message = getActivity().getString(R.string.error_pass_dont_match);
                break;
            case USER_DOESNT_EXIST:
                message = getActivity().getString(R.string.error_missing_user);
                break;
            case INCORRECT_EMAIL:
                message = getActivity().getString(R.string.error_email);
                break;
            case EMPTY_STOPS:
                message = getActivity().getString(R.string.error_num_stops);
                break;
            case EMPTY_PASSENGERS:
                message = getActivity().getString(R.string.error_passengers);
                break;
            case PASSED_DATE:
                message = getActivity().getString(R.string.error_passed_dates);
                break;
            case FUTURE_DATE:
                message = getActivity().getString(R.string.error_future_dates);
                break;
            case INVALID_ROUTE:
                message = getActivity().getString(R.string.error_invalid_route);
                break;
            case REVERSED_DATES:
                message = getActivity().getString(R.string.error_switched_dates);
                break;
            case NOT_ENOUGH_SEATS:
                message = getActivity().getString(R.string.error_not_enough_seats_1) +
                        " " + numPassengers + " " +
                        getActivity().getString(R.string.error_not_enough_seats_2);
                break;
            case FULL_FLIGHT:
                message = getActivity().getString(R.string.error_full_flight);
                break;
            case NONNUMERIC_CHARACTERS:
                message = getActivity().getString(R.string.error_non_numeric_characters);
                break;
            case OUT_OF_RANGE:
                message = getActivity().getString(R.string.error_invalid_number);
                break;
            case PASSED_FLIGHT:
                message = getActivity().getString(R.string.error_passed_flight);
                break;
            case PASSED_HOUR:
                message = getActivity().getString(R.string.error_passed_hour);
                break;
            case FUTURE_HOUR:
                message = getActivity().getString(R.string.error_future_hour);
                break;
            case INVALID_PASSWORD:
                message = getResources().getString(R.string.error_invalid_pass);
                break;
            case INMINENT_FLIGHT:
                message = getResources().getString(R.string.error_48_hours);
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
