package com.example.chess360.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.chess360.R;

public class SignUpPlayer extends DialogFragment {

    ListenerSignUpForm listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ListenerSignUpForm) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(infService);
        View inflate = li.inflate(R.layout.signup_player,null);

        builder.setIcon(R.drawable.pen);
        builder.setTitle(R.string.signup_title);
        builder.setView(inflate);

        EditText firstName = inflate.findViewById(R.id.signup_first_name);
        EditText lastName = inflate.findViewById(R.id.signup_last_name);
        EditText user = inflate.findViewById(R.id.signup_username_player);
        EditText email = inflate.findViewById(R.id.signup_email_player);
        EditText elo = inflate.findViewById(R.id.signup_elo);
        EditText pass = inflate.findViewById(R.id.signup_password_player);
        EditText pass2 = inflate.findViewById(R.id.signup_confirm_password_player);
        CheckBox check = inflate.findViewById(R.id.signup_checkbox_player);

        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String outputCheckbox;

                if (check.isChecked()){
                    outputCheckbox = "YES";
                }
                else{
                    outputCheckbox = "NO";
                }

                String [] userData = {firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        user.getText().toString(),
                        elo.getText().toString(),
                        pass.getText().toString(),
                        pass2.getText().toString(),
                        outputCheckbox};

                listener.onSignUpFormClick(userData, ListenerSignUpForm.SIGN_UP_PLAYER);

            }
        });

        builder.setNegativeButton(R.string.dialog_discard, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
