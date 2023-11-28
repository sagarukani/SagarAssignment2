package com.example.sagarassignment2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sagarassignment2.databinding.ListItemTaskBinding;
import com.example.sagarassignment2.model.TaskModel;

import java.util.List;

/**
 * Created by Sagar Ukani
 * Adapter for the task recyclerview
 */
public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public TaskAdapter(List<TaskModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    ListItemTaskBinding binding;
    List<TaskModel> list;
    Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ListItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setupView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ListItemTaskBinding binding;

        public ViewHolder(@NonNull ListItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setupView(TaskModel model) {
            //setting up data in view
            binding.tvTaskName.setText(model.getName());
            binding.tvTaskDue.setText(String.format("Due on: %s %s", model.getDueDate(), model.getDueTime()));
        }
    }
}
