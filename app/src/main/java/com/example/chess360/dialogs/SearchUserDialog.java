package com.example.chess360.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.chess360.R;

public class SearchUserDialog extends DialogFragment {

    ListenerSearch listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ListenerSearch) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    private EditText name;
    private EditText username;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(infService);
        View inflate = li.inflate(R.layout.search_user,null);

        builder.setIcon(R.drawable.pen);
        builder.setTitle(R.string.search_title);
        builder.setView(inflate);

        name = (EditText) inflate.findViewById(R.id.search_name_input);
        username = (EditText) inflate.findViewById(R.id.search_username_input);

        builder.setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String [] output = new String[2];
                output[0] = name.getText().toString();
                output[1] = username.getText().toString();
                listener.onSearchClick(output);
            }
        });

        builder.setNegativeButton(R.string.dialog_discard, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
            }
        });

        return builder.create();
    }
}
