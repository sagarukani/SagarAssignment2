package com.example.suworkout.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suworkout.R;
import com.example.suworkout.activity.MainActivity;
import com.example.suworkout.databinding.WorkouttypeRowBinding;
import com.example.suworkout.model.WorkoutModel;

import java.util.List;

public class AdapterWorkout extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public AdapterWorkout(List<WorkoutModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    WorkouttypeRowBinding binding;
    List<WorkoutModel> list;
    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = WorkouttypeRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    //for changing list in adapter

    public void  changeDataset(List<WorkoutModel> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).setupView(list.get(position),context,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        WorkouttypeRowBinding binding;

        public ViewHolder(@NonNull WorkouttypeRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(WorkoutModel model,Context context,Integer pos){
            binding.txtWorkoutType.setText(model.getName());
            binding.workoutIcon.setImageResource(model.getResource());

            //changing background based on selection
            if(model.getSelected()){
                Log.d("TAG","Selected is"+model.getName());
                this.binding.llRoot.setBackground(context.getDrawable(R.drawable.rounded_corners_line));
            }else{
                Log.d("TAG","Not Selected is"+model.getName());
                this.binding.llRoot.setBackground(null);
            }

            Log.d("SELECTED pos",String.valueOf(model.getSelected())+String.valueOf(model.getName()));


            //on click of root view
            this.binding.llRoot.setOnClickListener(v -> {
                Log.d("CLICK","clicked on "+pos.toString());
                ((MainActivity)context).isSelected(model,pos,binding);
            });
        }
    }
}
