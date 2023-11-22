package com.example.suworkout.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suworkout.activity.MainActivity;
import com.example.suworkout.databinding.WorkoutRowBinding;
import com.example.suworkout.databinding.WorkouttypeRowBinding;
import com.example.suworkout.model.WorkoutDetailModel;
import com.example.suworkout.model.WorkoutModel;

import java.util.List;

public class WorkoutDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public WorkoutDetailAdapter(List<WorkoutDetailModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    WorkoutRowBinding binding;
    List<WorkoutDetailModel> list;
    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = WorkoutRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).setupView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        WorkoutRowBinding binding;

        public ViewHolder(@NonNull WorkoutRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(WorkoutDetailModel model){
            //setting up data in view
            binding.txtWorkout.setText(model.getName() + " - " + model.getDuration() + " minutes Calories Burned: " + (int)model.getCalory() + " Cal.");
        }
    }
}
