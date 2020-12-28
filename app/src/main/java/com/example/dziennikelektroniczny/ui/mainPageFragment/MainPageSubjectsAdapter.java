package com.example.dziennikelektroniczny.ui.mainPageFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dziennikelektroniczny.R;
import com.example.dziennikelektroniczny.data.entities.Subject;
import com.example.dziennikelektroniczny.ui.mainPageFragment.EditSubjectDialog.EditSubjectItemDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainPageSubjectsAdapter extends RecyclerView.Adapter<MainPageSubjectsAdapter.MyViewHolder> {
    Context context;
    private List<Subject> subjects;
    private MainPageViewModel mViewModel;
    private DialogFragment dialog;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subject_row,parent,false);
        return new MainPageSubjectsAdapter.MyViewHolder(view);
    }

    public MainPageSubjectsAdapter(Context context, ArrayList<Subject> subjects, MainPageViewModel mViewModel) {
        this.context = context;
        this.subjects = subjects;
        this.mViewModel = mViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Subject currentItem = subjects.get(position);
        holder.name.setText(currentItem.getName());

        String myDate = convertDate(currentItem);
        holder.UpdatedAt.setText(myDate);

        String gradesConverted = convertGrades(currentItem);
        holder.grades.setText(gradesConverted);



        switch(currentItem.getImage()) {
            case 1:
                holder.myImage.setImageResource(R.drawable.z1);
                break;
            case 2:
                holder.myImage.setImageResource(R.drawable.z2);
                break;
            case 3:
                holder.myImage.setImageResource(R.drawable.z3);
                break;
            case 4:
                holder.myImage.setImageResource(R.drawable.z4);
                break;
            default:
                // code block
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deleteSubject(currentItem);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new EditSubjectItemDialog(mViewModel,currentItem);
                dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "DialogFragment");
            }
        });
    }


    @Override
    public int getItemCount() {
        return subjects.size();
    }

    private String convertDate(Subject currentItem){
        String date = currentItem.getUpdatedAt().toString();
        String[] tab = date.split("G");
        return tab[0];
    }

    private String convertGrades(Subject currentItem){
        String gradesConverted = "";

        for(float g: currentItem.getGrades()){
            String[] tab = Float.toString(g).split("");
            if (tab[3].equals("0")){
                gradesConverted +=tab[1]+"  ";
            }
            else{
                gradesConverted += g+"  ";
            }

        }
        return gradesConverted;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, UpdatedAt, grades;
        ImageView myImage;
        ImageButton deleteButton;
        FloatingActionButton editButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            UpdatedAt = itemView.findViewById(R.id.updatedAt);
            grades = itemView.findViewById(R.id.grades);
            myImage = itemView.findViewById(R.id.myImageview);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
