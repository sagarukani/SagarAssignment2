package com.example.sagarassignment2.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sagarassignment2.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        onClick();


    }

    private void onClick() {
        mainBinding.tvAddTask.setOnClickListener(v -> startActivity(new Intent(this, AddTaskActivity.class)));
        mainBinding.tvViewAll.setOnClickListener(v -> startActivity(new Intent(this, ListTaskActivity.class)));
        mainBinding.tvUpcoming.setOnClickListener(v -> startActivity(new Intent(this, ListTaskActivity.class)
                .putExtra(ListTaskActivity.IS_UPCOMING, true)));
    }
}