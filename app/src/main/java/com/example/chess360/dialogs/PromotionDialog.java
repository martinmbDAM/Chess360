package com.example.chess360.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.fragment.app.DialogFragment;

import com.example.chess360.R;

public class PromotionDialog extends DialogFragment {

    ListenerPromotion listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ListenerPromotion) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.icon);
        builder.setTitle(R.string.promotion_title);
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.promotion,
                android.R.layout.simple_list_item_1);
        builder.setAdapter(myAdapter, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int option){
     //           CharSequence strName = myAdapter.getItem(option);
     //           listener.onPromotionClick((String) strName);
                listener.onPromotionClick(option);
            }
        });
        return builder.create();
    }
}
