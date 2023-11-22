package com.example.sagarassignment2.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.wear.widget.WearableLinearLayoutManager;

import com.example.sagarassignment2.adapter.TaskAdapter;
import com.example.sagarassignment2.databinding.ActivityListTaskBinding;
import com.example.sagarassignment2.model.TaskModel;
import com.example.sagarassignment2.utils.CustomScrollingLayoutCallback;
import com.example.sagarassignment2.utils.PreferenceUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ListTaskActivity extends AppCompatActivity {

    public static final String IS_UPCOMING = "upcoming";

    private ActivityListTaskBinding mainBinding;
    private TaskAdapter adapter;

    private boolean isUpcoming = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityListTaskBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        handleIntent();
        initView();
    }

    private void handleIntent() {
        if (getIntent().hasExtra(IS_UPCOMING)) {
            isUpcoming = getIntent().getBooleanExtra(IS_UPCOMING, false);
        }
    }

    private void initView() {

        List<TaskModel> list = PreferenceUtil.getList(this);

        final CustomScrollingLayoutCallback scrollingLayoutCallback =
                new CustomScrollingLayoutCallback();

        if (isUpcoming) {
            List<TaskModel> upcomingList = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime i = now.plusHours(1);

            for (TaskModel task : list) {
                Log.d("list date is : ",task.getDueDate() + " " + task.getDueTime());
                LocalDateTime taskDate = LocalDateTime.parse(task.getDueDate() + " " + task.getDueTime(), DateTimeFormatter.ofPattern("yyyy-MM-d HH-mm"));
                Log.d("taskDate is : ",task.getDueDate() + " " + task.getDueTime());
                Log.d("now is : ",now.toString());
                Log.d("taskDate.isAfter(now) is : ", String.valueOf(taskDate.isAfter(now)));
                Log.d("taskDate.isBefore(i) is : ", String.valueOf(taskDate.isBefore(i)));
                if (taskDate.isAfter(now) && taskDate.isBefore(i)) {
                    upcomingList.add(task);
                }
            }
            adapter = new TaskAdapter(upcomingList, this);
            mainBinding.wrvTaskList.setEdgeItemsCenteringEnabled(true);
            mainBinding.wrvTaskList.setCircularScrollingGestureEnabled(true);
            mainBinding.wrvTaskList.setLayoutManager(new WearableLinearLayoutManager(this, scrollingLayoutCallback));
            mainBinding.wrvTaskList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            adapter = new TaskAdapter(list, this);
            mainBinding.wrvTaskList.setEdgeItemsCenteringEnabled(true);
            mainBinding.wrvTaskList.setCircularScrollingGestureEnabled(true);
            mainBinding.wrvTaskList.setLayoutManager(new WearableLinearLayoutManager(this, scrollingLayoutCallback));
            mainBinding.wrvTaskList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        if (adapter.getItemCount()==0){
            mainBinding.tvNoData.setVisibility(View.VISIBLE);
            mainBinding.wrvTaskList.setVisibility(View.GONE);
        }else {
            mainBinding.tvNoData.setVisibility(View.GONE);
            mainBinding.wrvTaskList.setVisibility(View.VISIBLE);
        }
    }
}