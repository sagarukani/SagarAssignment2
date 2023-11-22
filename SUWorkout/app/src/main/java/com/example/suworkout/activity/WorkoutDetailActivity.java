package com.example.suworkout.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.suworkout.R;
import com.example.suworkout.adapter.AdapterWorkout;
import com.example.suworkout.adapter.WorkoutDetailAdapter;
import com.example.suworkout.databinding.ActivityMainBinding;
import com.example.suworkout.databinding.ActivityWorkoutDetailBinding;
import com.example.suworkout.model.WorkoutDetailModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailActivity extends AppCompatActivity {

    ActivityWorkoutDetailBinding mainBinding;

    private WorkoutDetailAdapter adapterWorkout;
    Gson gson;

    List<WorkoutDetailModel> detailModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityWorkoutDetailBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        gson = new Gson();
        handleIntent();

        init();
    }

    //view initilizqtion
    private void init() {
        adapterWorkout = new WorkoutDetailAdapter(detailModels, this);
        mainBinding.wrcWorkouts.setHasFixedSize(true);
        mainBinding.wrcWorkouts.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.wrcWorkouts.setAdapter(adapterWorkout);
        adapterWorkout.notifyDataSetChanged();

        Log.d("TAG", String.valueOf(adapterWorkout.getItemCount()));

        Log.d("COUNT", String.valueOf(adapterWorkout.getItemCount()));

    }

    //getting data from intent
    public void handleIntent(){
        String data = this.getIntent().getStringExtra("data");
        Log.d("DATA",data);


        detailModels = gson.fromJson(data, new TypeToken<ArrayList<WorkoutDetailModel>>(){}.getType());
        Log.d("SIZE",String.valueOf(detailModels.size()));

    }
}