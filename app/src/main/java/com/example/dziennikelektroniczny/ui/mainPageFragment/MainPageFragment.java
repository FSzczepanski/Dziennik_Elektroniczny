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

import com.example.dziennikelektroniczny.R;
import com.example.dziennikelektroniczny.data.entities.Subject;
import com.example.dziennikelektroniczny.ui.mainPageFragment.AddSubjectDialog.AddSubjecItemDialog;

import java.util.ArrayList;
import java.util.List;


import static android.content.ContentValues.TAG;

public class MainPageFragment extends Fragment {

    private MainPageViewModel mViewModel;
    private RecyclerView subjectRecycler;
    private MainPageSubjectsAdapter subjectsAdapter;
    private DialogFragment dialog;

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

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mViewModel.getSubjects(getContext()).observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(List<Subject> subjects) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");


                subjectsAdapter = new MainPageSubjectsAdapter(getActivity(), (ArrayList<Subject>) subjects, mViewModel);

                subjectRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                subjectRecycler.setAdapter(subjectsAdapter);

                subjectsAdapter.notifyDataSetChanged(); //optional statement.
            }
        });

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

}