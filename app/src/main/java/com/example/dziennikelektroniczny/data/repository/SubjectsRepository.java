package com.example.dziennikelektroniczny.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dziennikelektroniczny.data.dao.SubjectDao;
import com.example.dziennikelektroniczny.data.database.Database;
import com.example.dziennikelektroniczny.data.entities.Subject;

import java.util.List;

public class SubjectsRepository {
    private LiveData<List<Subject>> subjects;
    private SubjectDao subjectDao;
    private Database database;
    private Context context;

    public SubjectsRepository(Context context) {
        this.context = context;
        database = Database.getInstance(context);
    }
    public void insertSubject(Subject subject){
        database.subjectDao().insert(subject);
    }
    public LiveData<List<Subject>> getloadAllSubjects(){
        subjects = database.subjectDao().loadAllSubjects();
        return subjects;
    }

    public void updateSubject(Subject subject){
        database.subjectDao().update(subject);
    }

    public void deleteSubject(Subject subject){
        database.subjectDao().delete(subject);
    }
    public void deleteAllSubjects(){
        database.subjectDao().deleteAllSubjects();
    }




}
