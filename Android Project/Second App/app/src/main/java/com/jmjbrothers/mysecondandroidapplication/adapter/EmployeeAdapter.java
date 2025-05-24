package com.jmjbrothers.mysecondandroidapplication.adapter;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jmjbrothers.mysecondandroidapplication.R;
import com.jmjbrothers.mysecondandroidapplication.model.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee>  employeeList;

    public EmployeeAdapter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeAdapter.EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_employee, parent, false);

        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, int position) {

        Employee employee = new Employee();

        holder.nameText.setText(employee.getName());
        holder.emailText.setText(employee.getEmail());
        holder.designationText.setText(employee.getDesignation());


        holder.updateButton.setOnClickListener(View -> {
            Log.d("Update", "Update clicked for "+employee.getName());
        });

        holder.deleteButton.setOnClickListener(View ->{
            Log.d("Dalete", "Delete clicked for "+ employee.getName());
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
