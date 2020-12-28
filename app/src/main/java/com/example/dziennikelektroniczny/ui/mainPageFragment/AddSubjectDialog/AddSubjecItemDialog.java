package com.example.dziennikelektroniczny.ui.mainPageFragment.AddSubjectDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;

import com.example.dziennikelektroniczny.R;
import com.example.dziennikelektroniczny.ui.mainPageFragment.MainPageViewModel;

public class AddSubjecItemDialog extends DialogFragment {
    private MainPageViewModel mViewModel;
    private String name ="";
    private String grades= "";
    private DialogFragment dialog;


    public AddSubjecItemDialog(MainPageViewModel mViewModel) {
        this.mViewModel =mViewModel;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add Subject");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_subject_item, null);
        builder.setView(view);


        EditText editTextname = view.findViewById(R.id.etName);
        EditText editTextgrades = view.findViewById(R.id.etGrades);

        TextView textViewAdd = view.findViewById(R.id.tvAdd);
        TextView textViewCancel = view.findViewById(R.id.tvCancel);
        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextname.getText().toString();
                grades = editTextgrades.getText().toString();
                String[] tab = grades.split(",");

                if ((!name.matches("")) && tab.length>0) {
                    loadNextDialogAndPassData();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Enter correct data ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSubjecItemDialog.this.getDialog().cancel();
            }
        });



        return builder.create();
    }

    private void loadNextDialogAndPassData(){
        dialog = new AddSubjectPictureDialog(mViewModel);
        Bundle bundle = new Bundle();
        bundle.putString("aName", name);
        bundle.putString("aGrades", grades);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "DialogFragment");
        AddSubjecItemDialog.this.getDialog().cancel();
    }



}
