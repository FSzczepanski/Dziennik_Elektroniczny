package com.example.dziennikelektroniczny.ui.settingsFragment.ThemeSettings;

import androidx.appcompat.app.AppCompatDelegate;
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

import com.example.dziennikelektroniczny.R;

public class ThemeSettingsFragment extends Fragment {

    private ThemeSettingsViewModel mViewModel;

    public static ThemeSettingsFragment newInstance() {
        return new ThemeSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.theme_settings_fragment, container, false);
        navigateBack(root);
        changeTheme(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ThemeSettingsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void changeTheme(View root){
        Button button = root.findViewById(R.id.themeChangeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("Enable dark theme")){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    button.setText("Enable light theme");
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    button.setText("Enable dark theme");
                }
            }
        });
    }

    public void navigateBack(View root) {

        Button backButton = root.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);
                navController.navigateUp();
            }
        });
    }

}