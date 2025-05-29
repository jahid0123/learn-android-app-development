package com.jmjbrothers.studentcrudapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.jmjbrothers.studentcrudapplication.R;
import com.jmjbrothers.studentcrudapplication.model.Student;
import com.jmjbrothers.studentcrudapplication.service.ApiService;
import com.jmjbrothers.studentcrudapplication.util.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentActivity extends AppCompatActivity {

    private EditText textName, textSubject, textClass, textSchoor;
    private Button btnSave;

    private ApiService apiService = ApiClient.getApiService();
    private boolean isEditMode = false;
    private long studentId = -1;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
            textName = findViewById(R.id.textName);
            textSubject = findViewById(R.id.textSubject);
            textClass = findViewById(R.id.textClass);
            textSchoor = findViewById(R.id.textSchool);

            Intent intent = getIntent();
            if (getIntent().hasExtra("student")){
                Student student = new Gson().fromJson(intent.getStringExtra("student"), Student.class);

                studentId = student.getId();

                textName.setText(student.getName());
                textSubject.setText(student.getSubject());
                textClass.setText(student.getClazz());
                textSchoor.setText(student.getSchool());

                btnSave.setText(R.string.update);
                isEditMode = true;
            }

            btnSave.setOnClickListener(v -> saveOrUpdateStudent());

    }

    private void saveOrUpdateStudent() {
        String name = textName.getText().toString().trim();
        String subject = textSubject.getText().toString().trim();
        String clazz = textClass.getText().toString().trim();
        String schoor =  textSchoor.getText().toString().trim();


        Student student = new Student();
        if (isEditMode) {
            student.setId(studentId);
        }
        student.setName(name);
        student.setSubject(subject);
        student.setClazz(clazz);
        student.setSchool(schoor);


        Call<Student> call;
        if (isEditMode) {
            call = apiService.updateStudent(studentId, student);
        } else {
            call = apiService.saveStudent(student);
        }

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                if (response.isSuccessful()) {
                    String message;
                    if (isEditMode)
                        message = "Student Updated Successfully!";
                    else
                        message = "Student Saved Successfully!";

                    Toast.makeText(AddStudentActivity.this, message, Toast.LENGTH_SHORT).show();
                    clearForm();
                    Intent intent = new Intent(AddStudentActivity.this, StudentListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddStudentActivity.this, "Failed to save student "
                            + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                Toast.makeText(AddStudentActivity.this, "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        textName.setText("");
        textSubject.setText("");
        textClass.setText("");
        textSchoor.setText("");

    }
}