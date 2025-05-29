package com.jmjbrothers.studentcrudapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jmjbrothers.studentcrudapplication.R;
import com.jmjbrothers.studentcrudapplication.activity.AddStudentActivity;
import com.jmjbrothers.studentcrudapplication.model.Student;
import com.jmjbrothers.studentcrudapplication.service.ApiService;
import com.jmjbrothers.studentcrudapplication.util.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context context;
    private ApiService apiService;
    private List<Student> studentList;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.apiService = ApiClient.getApiService();
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);

        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {

        Student student = studentList.get(position);
        holder.nameText.setText(student.getName());
        holder.subjectText.setText(student.getSubject());
        holder.classText.setText(student.getClazz());
        holder.schoolText.setText(student.getSchool());

        holder.updateButton.setOnClickListener(v -> {
            Log.d("update", "update clicked for "+ student.getName());
            Intent intent = new Intent(context, AddStudentActivity.class);
            intent.putExtra("student", new Gson().toJson(student));
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            Log.d("Delete", "Delete clicked for " + student.getName());
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete " + student.getName() + "?")
                    .setPositiveButton("Yes",
                            (dialog, which) -> apiService.deleteStudent(student.getId())
                                    .enqueue(new Callback<>() {
                                        @Override
                                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                int adapterPosition = holder.getAdapterPosition();
                                                if (adapterPosition != RecyclerView.NO_POSITION) {
                                                    studentList.remove(adapterPosition);
                                                    notifyItemRemoved(adapterPosition);
                                                    notifyItemRangeChanged(adapterPosition, studentList.size());
                                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }))
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView nameText, subjectText, classText, schoolText;
        Button updateButton, deleteButton;
        public StudentViewHolder(@NonNull View view) {
            super(view);
            nameText = view.findViewById(R.id.textName);
            subjectText = view.findViewById(R.id.textSubject);
            classText =  view.findViewById(R.id.textClass);
            schoolText = view.findViewById(R.id.textSchool);

            updateButton = view.findViewById(R.id.updateButton);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}
