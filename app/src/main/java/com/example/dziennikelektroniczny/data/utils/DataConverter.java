package com.example.dziennikelektroniczny.data.utils;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public List<Integer> gettingListFromString(String genreIds) {
        List<Integer> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Integer> subjects) {
        String genreIds = "";
        for (int i : subjects) {
            genreIds += "," + i;
        }
        return genreIds;
    }
    @TypeConverter
    public ArrayList<Float> gettingFloatListFromString(String genreIds) {
        ArrayList<Float> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Float.parseFloat(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingFloatStringFromList(ArrayList<Float> subjects) {
        String genreIds = "";
        for (float i : subjects) {
            genreIds += "," + i;
        }
        return genreIds;
    }
}
