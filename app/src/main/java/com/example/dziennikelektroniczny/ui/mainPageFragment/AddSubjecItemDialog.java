package com.example.dziennikelektroniczny.ui.mainPageFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dziennikelektroniczny.R;

public class AddSubjecItemDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();


        builder.setTitle("Add Subject");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_subject_item, null);
        builder.setView(view);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddSubjecItemDialog.this.getDialog().cancel();
            }
        });

        //TODO() ogarnięcie buttonów
       /* Button buttonAdd = view.findViewById(R.id.addNewButton);
        buttonAddSubject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new AddSubjecItemDialog();
                dialog.show(getFragmentManager(), "DialogFragment");
            }
        });*/


        return builder.create();
    }
}
