package com.example.chess360.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.chess360.R;

public class SignUpDialog extends DialogFragment {

    ListenerLogin listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ListenerLogin) context;
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
        View inflate = li.inflate(R.layout.signup,null);

        builder.setIcon(R.drawable.pen);
        builder.setTitle(R.string.signup_title);
        builder.setView(inflate);


        EditText firstName = (EditText) inflate.findViewById(R.id.signup_first_name);
        EditText lastName = (EditText) inflate.findViewById(R.id.signup_last_name);
        EditText user = (EditText) inflate.findViewById(R.id.signup_username);
        EditText email = (EditText) inflate.findViewById(R.id.signup_email);
        EditText pass = (EditText) inflate.findViewById(R.id.signup_password);
        EditText pass2 = (EditText) inflate.findViewById(R.id.signup_confirm_password);

        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String [] userData = {firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        user.getText().toString(),
                        pass.getText().toString(),
                        pass2.getText().toString()};

                listener.onSignUpClick(userData);

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


