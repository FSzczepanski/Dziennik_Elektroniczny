package com.example.dziennikelektroniczny.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.List;

@Entity(tableName = "subjects")
public class Subject {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Date updatedAt;
    private List<Integer> grades;

    public Subject(String name, Date updatedAt, List<Integer> grades) {
        this.id=id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.grades = grades;
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

    public List<Integer> getGrades() {
        return grades;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }

    public void setId(int id) {
        this.id = id;
    }
}
