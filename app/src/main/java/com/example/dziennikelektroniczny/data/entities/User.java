package com.example.dziennikelektroniczny.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String email;
    private double average;
    private float median;

    public User(int id, String email, double average, float median) {
        this.id = id;
        this.email = email;
        this.average = average;
        this.median = median;
    }
}
