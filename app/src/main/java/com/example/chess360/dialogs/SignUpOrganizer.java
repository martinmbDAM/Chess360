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

public class SignUpOrganizer extends DialogFragment {

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
        View inflate = li.inflate(R.layout.signup_organizer,null);

        builder.setIcon(R.drawable.pen);
        builder.setTitle(R.string.signup_title);
        builder.setView(inflate);

        EditText name = (EditText) inflate.findViewById(R.id.signup_name_organizer);
        EditText phone = (EditText) inflate.findViewById(R.id.signup_phone_organizer);
        EditText email = (EditText) inflate.findViewById(R.id.signup_email_organizer);
        EditText location = (EditText) inflate.findViewById(R.id.signup_location_organizer);
        EditText pass = (EditText) inflate.findViewById(R.id.signup_password_organizer);
        EditText pass2 = (EditText) inflate.findViewById(R.id.signup_confirm_password_organizer);
        CheckBox check = (CheckBox) inflate.findViewById(R.id.signup_checkbox_organizer);

        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String outputCheckbox = new String();

                if (check.isSelected()){
                    outputCheckbox = "YES";
                }
                else{
                    outputCheckbox = "NO";
                }

                String [] userData = {name.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        location.getText().toString(),
                        pass.getText().toString(),
                        pass2.getText().toString(),
                        outputCheckbox};

                listener.onSignUpFormClick(userData,ListenerSignUpForm.SIGN_UP_ORGANIZER);

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
