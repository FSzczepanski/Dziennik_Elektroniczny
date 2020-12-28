package com.example.dziennikelektroniczny.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dziennikelektroniczny.data.entities.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Subject subject);

    @Delete
    void delete(Subject subject);

    @Query("DELETE FROM subjects")
    void deleteAllSubjects();

    @Query("SELECT * FROM subjects where userId=:userId")
    LiveData<List<Subject>> loadAllSubjects(String userId);
}
