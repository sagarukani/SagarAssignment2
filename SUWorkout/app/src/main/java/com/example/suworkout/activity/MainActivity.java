package com.example.suworkout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.suworkout.R;
import com.example.suworkout.adapter.AdapterWorkout;
import com.example.suworkout.databinding.ActivityMainBinding;
import com.example.suworkout.databinding.WorkouttypeRowBinding;
import com.example.suworkout.model.WorkoutDetailModel;
import com.example.suworkout.model.WorkoutModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding mainBinding;

    String selectedModel = "";

    List<WorkoutModel> models = new ArrayList<>();

    List<WorkoutDetailModel> detailModels = new ArrayList<>();

    private AdapterWorkout adapterWorkout;

    Double MET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


        setupList();
        init();
        onClick();

    }

    private void onClick() {
        mainBinding.btnAddWorkout.setOnClickListener(this);
    }

    //view initilization

    private void init() {
        adapterWorkout = new AdapterWorkout(models, this);
        mainBinding.wrcWorkoutTypes.setHasFixedSize(true);
        mainBinding.wrcWorkoutTypes.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.wrcWorkoutTypes.setAdapter(adapterWorkout);
        adapterWorkout.notifyDataSetChanged();

        Log.d("COUNT", String.valueOf(adapterWorkout.getItemCount()));

    }

    //manual list creation
    private void setupList() {
        models.clear();
        models.add(new WorkoutModel(R.drawable.walking_img, getText(R.string.walking).toString()));
        models.add(new WorkoutModel(R.drawable.running_img, getText(R.string.running).toString()));
        models.add(new WorkoutModel(R.drawable.cycling_img, getText(R.string.cycling).toString()));
        Log.d("COUNT", String.valueOf(models.size()));
    }

    //getting selected item
    public void isSelected(WorkoutModel model, Integer pos, WorkouttypeRowBinding view) {
        for (int i = 0; i < models.size(); i++) {
            models.get(i).setSelected(false);
        }
        models.get(pos).setSelected(true);

        selectedModel = models.get(pos).getName();

        //selecting MET value
        if (selectedModel == getString(R.string.cycling)) {
            MET = 11.5;
        } else if (selectedModel == getString(R.string.walking)) {
            MET = 3.5;
        } else if (selectedModel == getString(R.string.running)) {
            MET = 9.8;
        }
        adapterWorkout.changeDataset(models);
    }

    //Onclick of buttons
    @Override
    public void onClick(View v) {
        if (v.getId() == mainBinding.btnAddWorkout.getId()) {
            if (!Objects.equals(selectedModel, ""))
                if (verifyInt(mainBinding.edtDuration.getText().toString())) {
                    //Calory calculation

                    double caloryInMinute = MET * 3.5 * 0.35;
                    double total = caloryInMinute * Double.parseDouble(mainBinding.edtDuration.getText().toString());
                    detailModels.add(new WorkoutDetailModel(selectedModel, Integer.parseInt(mainBinding.edtDuration.getText().toString()), total));

                    Intent intent = new Intent(MainActivity.this, WorkoutDetailActivity.class);
                    intent.putExtra("data", new Gson().toJson(detailModels));
                    startActivity(intent);
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainBinding.edtDuration.setHint(getString(R.string.enter_duration));
    }

    //checking for valid data entry
    private Boolean verifyInt(String s) {
        try {
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}