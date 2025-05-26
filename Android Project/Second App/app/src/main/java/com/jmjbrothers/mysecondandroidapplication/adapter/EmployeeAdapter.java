package com.jmjbrothers.mysecondandroidapplication.adapter;


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
import com.jmjbrothers.mysecondandroidapplication.R;
import com.jmjbrothers.mysecondandroidapplication.activity.AddEmployeeActivity;
import com.jmjbrothers.mysecondandroidapplication.model.Employee;
import com.jmjbrothers.mysecondandroidapplication.service.ApiService;
import com.jmjbrothers.mysecondandroidapplication.util.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private final Context context;
    private List<Employee> employeeList;
    private ApiService apiService;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
        this.apiService = ApiClient.getApiService();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_employee, parent, false);

        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.nameText.setText(employee.getName());
        holder.emailText.setText(employee.getEmail());
        holder.designationText.setText(employee.getDesignation());

        holder.updateButton.setOnClickListener(v -> {
            Log.d("Update", "Update clicked for " + employee.getName());
            Intent intent = new Intent(context, AddEmployeeActivity.class);
            intent.putExtra("employee", new Gson().toJson(employee));
            context.startActivity(intent);


        });

        holder.deleteButton.setOnClickListener(v -> {
            Log.d("Delete", "Delete clicked for " + employee.getName());
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete " + employee.getName()+"?")
                    .setPositiveButton("Yes",
                            ((dialog, which) -> apiService.deleteEmployee(employee.getId())
                                    .enqueue(new Callback<>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccessful()){
                                                int adapterPosition = holder.getAdapterPosition();
                                                if (adapterPosition != RecyclerView.NO_POSITION){
                                                    employeeList.remove(adapterPosition);
                                                    notifyItemRemoved(adapterPosition);
                                                    notifyItemRangeChanged(adapterPosition, employeeList.size());
                                                    Toast.makeText(context, "Deleted successfully",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }))).setNegativeButton("Cancel", null).show();
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, emailText, designationText;
        Button updateButton, deleteButton;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            emailText = itemView.findViewById(R.id.emailText);
            designationText = itemView.findViewById(R.id.designationText);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}