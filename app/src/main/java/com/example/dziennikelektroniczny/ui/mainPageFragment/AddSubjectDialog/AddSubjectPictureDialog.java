package com.example.dziennikelektroniczny.ui.mainPageFragment.AddSubjectDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dziennikelektroniczny.R;
import com.example.dziennikelektroniczny.data.entities.Subject;
import com.example.dziennikelektroniczny.ui.mainPageFragment.MainPageSubjectsAdapter;
import com.example.dziennikelektroniczny.ui.mainPageFragment.MainPageViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;

public class AddSubjectPictureDialog extends DialogFragment {
    private String name;
    private String grades;
    private MainPageViewModel mViewModel;

    public AddSubjectPictureDialog(MainPageViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_subject_image, null);
        builder.setView(view);

        if (getArguments() != null) {
            name = getArguments().getString("aName","");
            grades = getArguments().getString("aGrades","");
        }


        choosePicture(view);


        return builder.create();
    }

    private void choosePicture(View view){
        ImageButton button1 = view.findViewById(R.id.subjectPicture1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject(1);
                AddSubjectPictureDialog.this.getDialog().cancel();
            }
        });
        ImageButton button2 = view.findViewById(R.id.subjectPicture2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject(2);
                AddSubjectPictureDialog.this.getDialog().cancel();
            }
        });
        ImageButton button3 = view.findViewById(R.id.subjectPicture3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject(3);
                AddSubjectPictureDialog.this.getDialog().cancel();
            }
        });
        ImageButton button4 = view.findViewById(R.id.subjectPicture4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject(4);
                AddSubjectPictureDialog.this.getDialog().cancel();
            }
        });
    }

    private void createSubject(int image){
        Date date = new Date();

        ArrayList<Float> gradesArray = new ArrayList<Float>();
        String[]tab=grades.split(",");
        if (tab.length >= 2) {
            for(String a: tab){
                gradesArray.add(Float.parseFloat(a));
            }
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();

        Subject subject = new Subject(name,date,gradesArray,image,userId);
        mViewModel.insertSubject(subject);

    }

}
