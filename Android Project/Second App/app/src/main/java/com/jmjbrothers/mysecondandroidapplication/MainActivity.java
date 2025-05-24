package com.jmjbrothers.mysecondandroidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jmjbrothers.mysecondandroidapplication.model.Employee;
import com.jmjbrothers.mysecondandroidapplication.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText textName,
            textEmail,
            textDesignation,
            multilineAddress,
            decimalSalary;

    private Button btnSave;

    private ApiService apiService;



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

        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        textDesignation = findViewById(R.id.textDesignation);
        multilineAddress = findViewById(R.id.textAddress);
        decimalSalary = findViewById(R.id.textSalary);
        btnSave = findViewById(R.id.button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnSave.setOnClickListener( v -> saveEmployee());
    }

    private void saveEmployee() {
        String name = textName.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String designation = textDesignation.getText().toString().trim();
        String address = multilineAddress.getText().toString().trim();
        int salary = Integer.parseInt(decimalSalary.getText().toString().trim());

        //Create Employee object
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setDesignation(designation);
        employee.setAddress(address);
        employee.setSalary(salary);

        //Make API call
        Call<Employee> call = apiService.saveEmployee(employee);
        String string = call.toString();
        System.out.println(string);
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Employee saved successfully!",
                            Toast.LENGTH_SHORT).show();
                    clearForm();
                    Intent intent = new Intent(MainActivity.this, Employee.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save employe"
                            + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Employee> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Error" + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void clearForm() {
        textName.setText("");
        textEmail.setText("");
        textDesignation.setText("");
        multilineAddress.setText("");
        decimalSalary.setText("");
    }
}