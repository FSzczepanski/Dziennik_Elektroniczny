package com.example.dziennikelektroniczny.ui.mainPageFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikelektroniczny.R;
import com.example.dziennikelektroniczny.data.entities.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageSubjectsAdapter extends RecyclerView.Adapter<MainPageSubjectsAdapter.MyViewHolder> {
    Context context;
    private List<Subject> subjects;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subject_row,parent,false);
        return new MainPageSubjectsAdapter.MyViewHolder(view);
    }

    public MainPageSubjectsAdapter(Context context, ArrayList<Subject> subjects) {
        this.context = context;
        this.subjects = subjects;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Subject currentItem = subjects.get(position);
        holder.name.setText(currentItem.getName());
        holder.UpdatedAt.setText(currentItem.getUpdatedAt().toString());
        holder.grades.setText(currentItem.getGrades().toString());

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, UpdatedAt, grades;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            UpdatedAt = itemView.findViewById(R.id.updatedAt);
            grades = itemView.findViewById(R.id.grades);
            myImage = itemView.findViewById(R.id.myImageview);
        }
    }
}
