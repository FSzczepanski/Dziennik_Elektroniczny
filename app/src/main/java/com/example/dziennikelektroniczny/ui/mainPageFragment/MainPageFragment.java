package com.example.dziennikelektroniczny.ui.mainPageFragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dziennikelektroniczny.R;
import com.example.dziennikelektroniczny.data.entities.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainPageFragment extends Fragment {

    private MainPageViewModel mViewModel;
    private RecyclerView subjectRecycler;
    private ArrayList<Subject> subjects;
    private MainPageSubjectsAdapter subjectsAdapter;

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_page_fragment, container, false);
        navigate(root);
        this.mViewModel = new ViewModelProvider(this).get(MainPageViewModel.class);
        subjectRecycler = root.findViewById(R.id.subjectsRecyclerView);




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mViewModel.getSubjects(getContext()).observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(List<Subject> subjects) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");

                //test
                Date date = new Date();
                //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                ArrayList grades = new ArrayList();
                grades.add(1);
                grades.add(3);
                grades.add(1);
                subjects = new ArrayList<Subject>();
                Subject subject = new Subject("Matematyka",date,grades);
                subjects.add(subject);
                subjects.add(subject);
                subjects.add(subject);
                //test

                subjectsAdapter = new MainPageSubjectsAdapter(getActivity(), (ArrayList<Subject>) subjects);

                subjectRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                subjectRecycler.setAdapter(subjectsAdapter);



                subjectsAdapter.notifyDataSetChanged(); //optional statement.
            }
        });

    }



    public void navigate(View root){
        View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_mainPageFragment_to_settingsFragment);
        ImageButton settingsButton = root.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(s);


    }

}