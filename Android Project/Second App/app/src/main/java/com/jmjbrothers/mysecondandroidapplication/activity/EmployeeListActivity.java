package com.jmjbrothers.mysecondandroidapplication.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jmjbrothers.mysecondandroidapplication.R;
import com.jmjbrothers.mysecondandroidapplication.adapter.EmployeeAdapter;
import com.jmjbrothers.mysecondandroidapplication.model.Employee;
import com.jmjbrothers.mysecondandroidapplication.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeListActivity extends AppCompatActivity {

    private static final String TAG = "EmployeeListActivity";

    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        employeeAdapter = new EmployeeAdapter(employeeList);
        recyclerView = findViewById(R.id.employeeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(employeeAdapter);

        fetchEmployees();

    }


    private void fetchEmployees(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Employee>> call = apiService.getAllEmployee();

        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(@NonNull Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()){
                    List<Employee> employees = response.body();
                    for (Employee emp: employees){
                        Log.d(TAG, "ID: " +emp.getId()+", Name: "+emp.getName()+", Designation: "+emp.getDesignation());
                    }
                    employeeList.clear();
                    employeeList.addAll(employees);
                    employeeAdapter.notifyDataSetChanged();
                }else {
                    Log.e(TAG, "Api response error: "+response.code());
                }
            }


            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.e(TAG, "Api call failed: "+t.getMessage());
            }
        });
    }
}