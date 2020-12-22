package com.example.dziennikelektroniczny.ui.mainPageFragment;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dziennikelektroniczny.data.database.Database;
import com.example.dziennikelektroniczny.data.entities.Subject;
import com.example.dziennikelektroniczny.data.repository.SubjectsRepository;

import java.util.List;

public class MainPageViewModel extends ViewModel {
    private LiveData<List<Subject>> subjects;
    private SubjectsRepository subjectsRepository;
    private Database database;
    private Application application;

    public MainPageViewModel() {

    }

    public void insertSubject(Subject subject) {
        subjectsRepository.insertSubject(subject);
    }

    public LiveData<List<Subject>> getSubjects(Context context) {
        subjectsRepository = new SubjectsRepository(context);
        subjects = subjectsRepository.getloadAllSubjects();
        return subjects;
    }

    public void updateSubject(Subject subject) {
        subjectsRepository.updateSubject(subject);
    }

    public void deleteSubject(Subject subject) {
        subjectsRepository.deleteSubject(subject);
    }

    public void deleteAllSubjects() {
        subjectsRepository.deleteAllSubjects();
    }
}