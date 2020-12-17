package com.example.dziennikelektroniczny.ui.mainPageFragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dziennikelektroniczny.R;

public class MainPageFragment extends Fragment {

    private MainPageViewModel mViewModel;

    public static MainPageFragment newInstance() {
        return new MainPageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_page_fragment, container, false);
        navigate(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainPageViewModel.class);
        // TODO: Use the ViewModel
    }

    public void navigate(View root){
        View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_mainPageFragment_to_settingsFragment);
        ImageButton settingsButton = root.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(s);

        Button logoutButton = root.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigateUp();
            }
        });
    }

}