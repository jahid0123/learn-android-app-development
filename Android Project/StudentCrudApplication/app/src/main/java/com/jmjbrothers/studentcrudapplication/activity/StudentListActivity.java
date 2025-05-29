package com.jmjbrothers.studentcrudapplication.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jmjbrothers.studentcrudapplication.R;
import com.jmjbrothers.studentcrudapplication.adapter.StudentAdapter;
import com.jmjbrothers.studentcrudapplication.model.Student;
import com.jmjbrothers.studentcrudapplication.service.ApiService;
import com.jmjbrothers.studentcrudapplication.util.ApiClient;
import com.jmjbrothers.studentcrudapplication.util.StudentDiffCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity {

    private static final String TAG = "StudentListActivity";
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Enable the Up button (back arrow) in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        studentAdapter = new StudentAdapter(this, studentList);
        recyclerView = findViewById(R.id.employeeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentAdapter);

        fetchStudent();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void fetchStudent() {
        ApiService apiService = ApiClient.getApiService();
        Call<List<Student>> call = apiService.getAllStudent();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Student>> call, @NonNull Response<List<Student>> response) {
                if (response.isSuccessful()) {
                    List<Student> students = response.body();
                    assert students != null;
                    for (Student emp : students) {
                        Log.d(TAG, "ID: " + emp.getId() + ", Name: "
                                + emp.getName());
                    }
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new StudentDiffCallback(studentList, students));
                    studentList.clear();
                    studentList.addAll(students);

                    result.dispatchUpdatesTo(studentAdapter);
                } else {
                    Log.e(TAG, "API Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Student>> call, @NonNull Throwable t) {
                Log.e(TAG, "API Call Failed: " + t.getMessage());
            }
        });
    }
}