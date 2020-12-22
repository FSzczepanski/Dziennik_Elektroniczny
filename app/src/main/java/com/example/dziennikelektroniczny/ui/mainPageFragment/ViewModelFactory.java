package com.example.dziennikelektroniczny.ui.mainPageFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dziennikelektroniczny.data.repository.SubjectsRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final SubjectsRepository repository;

    public ViewModelFactory(SubjectsRepository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MainPageViewModel.class)) {

            return (T) new MainPageViewModel();

        }

        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
