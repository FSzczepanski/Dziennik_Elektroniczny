package com.example.dziennikelektroniczny.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dziennikelektroniczny.data.dao.SubjectDao;
import com.example.dziennikelektroniczny.data.entities.Subject;
import com.example.dziennikelektroniczny.data.utils.DataConverter;

@androidx.room.Database(entities = {Subject.class}, version = 6, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "subjects_database";
    private static final String LOG_TAG = Database.class.getSimpleName();
    private static Database instance = null;
    public abstract SubjectDao subjectDao();

    public static Database getInstance(Context context) {
        if (instance == null) {
            Log.d(LOG_TAG, "Creating new database instance");
            instance = Room.databaseBuilder(context,
                    Database.class, Database.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return instance;
    }




}
