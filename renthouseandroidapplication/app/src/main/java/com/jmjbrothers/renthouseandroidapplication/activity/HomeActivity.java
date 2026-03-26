package com.jmjbrothers.renthouseandroidapplication.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.jmjbrothers.renthouseandroidapplication.adopter.PropertyAdapter;
import com.jmjbrothers.renthouseandroidapplication.model.GetPostedProperty;
import com.jmjbrothers.renthouseandroidapplication.network.ApiClient;
import com.jmjbrothers.renthouseandroidapplication.service.PropertyApi;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PropertyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PropertyApi api = ApiClient.getClient().create(PropertyApi.class);
        api.getAllPostedProperties().enqueue(new Callback<List<GetPostedProperty>>() {
            @Override
            public void onResponse(Call<List<GetPostedProperty>> call, Response<List<GetPostedProperty>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new PropertyAdapter(HomeActivity.this, response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetPostedProperty>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
