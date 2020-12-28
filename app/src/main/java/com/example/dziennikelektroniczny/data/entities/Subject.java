package com.example.dziennikelektroniczny.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.common.collect.BiMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "subjects")
public class Subject {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Date updatedAt;
    private ArrayList<Float> grades;
    private int image;

    public Subject(String name, Date updatedAt, ArrayList<Float> grades,int image) {
        this.id=id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.grades = grades;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public ArrayList<Float> getGrades() {
        return grades;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setGrades(ArrayList<Float> grades) {
        this.grades = grades;
    }

    public void setId(int id) {
        this.id = id;
    }
}
