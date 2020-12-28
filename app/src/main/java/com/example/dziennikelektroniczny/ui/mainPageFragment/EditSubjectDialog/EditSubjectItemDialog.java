package com.example.dziennikelektroniczny.ui.mainPageFragment.EditSubjectDialog;

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
import com.example.dziennikelektroniczny.data.entities.Subject;
import com.example.dziennikelektroniczny.ui.mainPageFragment.AddSubjectDialog.AddSubjectPictureDialog;
import com.example.dziennikelektroniczny.ui.mainPageFragment.MainPageViewModel;

import java.util.ArrayList;
import java.util.Date;

public class EditSubjectItemDialog extends DialogFragment{

    private MainPageViewModel mViewModel;
    private String name;
    private String grades;
    private DialogFragment dialog;
    private Subject subject;
    private EditText editTextname;
    private EditText editTextgrades;


    public EditSubjectItemDialog(MainPageViewModel mViewModel, Subject subject) {
        this.mViewModel =mViewModel;
        this.subject = subject;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Edit Subject");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_subject_item, null);
        builder.setView(view);
        editTextname = view.findViewById(R.id.etName);
        editTextgrades = view.findViewById(R.id.etGrades);


        loadSubjectsDataAndDisplay();
        TextView textViewAdd = view.findViewById(R.id.tvAdd);
        textViewAdd.setText("EDIT");
        TextView textViewCancel = view.findViewById(R.id.tvCancel);


        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextname.getText().toString();
                grades = editTextgrades.getText().toString();
                if (!name.matches("")) {
                    editSubject();
                    EditSubjectItemDialog.this.getDialog().cancel();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Enter correct data ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditSubjectItemDialog.this.getDialog().cancel();
            }
        });



        return builder.create();
    }

    private void loadSubjectsDataAndDisplay(){
        editTextname.setText(subject.getName());
        String gradesStr = "";
        ArrayList<Float> gradesArray = subject.getGrades();

        for (int i = 0; i < gradesArray.size(); i++) {
            gradesStr+=Float.toString(gradesArray.get(i))+",";
        }
        editTextgrades.setText(gradesStr);
    }

    private void editSubject(){
        Date date = new Date();
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        ArrayList<Float> gradesArray = new ArrayList<Float>();
        String[]tab=grades.split(",");
        if (tab.length >= 2) {
            for(String a: tab){
                gradesArray.add(Float.parseFloat(a));
            }
        }
        int image = subject.getImage();

        subject.setName(name);
        subject.setGrades(gradesArray);
        mViewModel.updateSubject(subject);

    }



}
