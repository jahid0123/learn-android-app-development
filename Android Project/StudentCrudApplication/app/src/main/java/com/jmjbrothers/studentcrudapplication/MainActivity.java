package com.jmjbrothers.studentcrudapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jmjbrothers.studentcrudapplication.activity.AddStudentActivity;
import com.jmjbrothers.studentcrudapplication.activity.StudentListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnAddStudent = findViewById(R.id.btnAddStudent);
        Button btnListStudent = findViewById(R.id.btnListStudent);

        btnAddStudent.setOnClickListener(v -> navigateToAddStudent());
        btnListStudent.setOnClickListener(v -> navigateToStudentListPage());
    }

    private void navigateToAddStudent() {
        Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
        startActivity(intent);
    }

    private void navigateToStudentListPage() {
        Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
        startActivity(intent);
    }
}