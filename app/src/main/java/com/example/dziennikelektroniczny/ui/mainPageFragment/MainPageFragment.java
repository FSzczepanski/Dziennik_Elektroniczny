package com.example.dziennikelektroniczny.ui.mainPageFragment;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dziennikelektroniczny.R;
import com.example.dziennikelektroniczny.data.entities.Subject;
import com.example.dziennikelektroniczny.ui.mainPageFragment.AddSubjectDialog.AddSubjecItemDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import static android.content.ContentValues.TAG;

public class MainPageFragment extends Fragment {

    private MainPageViewModel mViewModel;
    private RecyclerView subjectRecycler;
    private MainPageSubjectsAdapter subjectsAdapter;
    private DialogFragment dialog;
    private ArrayList myList;

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_page_fragment, container, false);
        navigateToSettingsFragment(root);
        this.mViewModel = new ViewModelProvider(this).get(MainPageViewModel.class);
        subjectRecycler = root.findViewById(R.id.subjectsRecyclerView);
        showAddSubjectDialog(root);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();

        this.mViewModel.getSubjects(userId,getContext()).observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(List<Subject> subjects) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");


                subjectsAdapter = new MainPageSubjectsAdapter(getActivity(), (ArrayList<Subject>) subjects, mViewModel);

                subjectRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                subjectRecycler.setAdapter(subjectsAdapter);
                // countAvg((ArrayList<Subject>) subjects);

                subjectsAdapter.notifyDataSetChanged(); //optional statement.
                myList = new ArrayList<Subject>();
                myList = (ArrayList) subjects;

            }
        });

        View viewCountAvg = root.findViewById(R.id.countAvg);
        viewCountAvg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countAvg(myList,root);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    public void showAddSubjectDialog(View view) {
        Button buttonAddSubject = view.findViewById(R.id.addNewButton);
        buttonAddSubject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog = new AddSubjecItemDialog(mViewModel);
                dialog.show(getFragmentManager(), "DialogFragment");
                TextView uczen = view.findViewById(R.id.uczenText);

            }
        });
    }

    public void navigateToSettingsFragment(View root){
        View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_mainPageFragment_to_settingsFragment);
        ImageButton settingsButton = root.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(s);

    }

    public void countAvg(ArrayList<Subject> subjects, View view) {

        if (subjects.size() > 0) {
            int size = subjects.size();
            //avg

            double sumOfAvg = 0;
            for (int i = 0; i < size; i++) {
                float sumOfGrades = 0;
                int countGrades = subjects.get(i).getGrades().size();
                for (int j = 0; j < countGrades; j++) {
                    sumOfGrades += subjects.get(i).getGrades().get(j);
                }
                double subjectAvg = sumOfGrades / countGrades;
                sumOfAvg += subjectAvg;
            }
            double studentsGeneralAvg = sumOfAvg / size;

            TextView avgText = view.findViewById(R.id.sredniaText);
            DecimalFormat df = new DecimalFormat("##.###");
            avgText.setText("Your average:  " + df.format(studentsGeneralAvg));

            //median
            List<Float> allGrades = new ArrayList<Float>();
            for (int i = 0; i < size; i++) {
                int countGrades = subjects.get(i).getGrades().size();
                for (int j = 0; j < countGrades; j++) {
                    allGrades.add(subjects.get(i).getGrades().get(j));
                }
            }
            Collections.sort(allGrades);
            int countAllGrades = allGrades.size();
            float median = 0;
            if (countAllGrades % 2 == 0) {
                int srodkowa = (countAllGrades - 2) / 2;
                float first = allGrades.get(srodkowa);
                float second = allGrades.get(srodkowa + 1);

                median = (first + second) / 2;
            } else {
                int srodkowa = (countAllGrades - 1) / 2;
                median = allGrades.get(srodkowa);
            }

            TextView medianText = view.findViewById(R.id.medianaText);
            medianText.setText("Your median:  " + median);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "You need subjects to calculate", Toast.LENGTH_SHORT).show();
        }
    }

}