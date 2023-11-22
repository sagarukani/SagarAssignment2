package com.example.sagarassignment2.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sagarassignment2.R;
import com.example.sagarassignment2.databinding.ActivityAddTaskBinding;
import com.example.sagarassignment2.model.TaskModel;
import com.example.sagarassignment2.utils.NotificationClass;
import com.example.sagarassignment2.utils.PreferenceUtil;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AddTaskActivity extends AppCompatActivity {

    private ActivityAddTaskBinding mainBinding;
    private String name = "";

    private String due = "";
    private Calendar calendar;

    private List<TaskModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        initView();
        onClick();

    }

    private void initView() {
        calendar = Calendar.getInstance();
        createNotificationChannel();
    }

    private void onClick() {
        mainBinding.tvTaskName.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    .putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Task Name");
            speechResultLauncher.launch(intent);
        });
        mainBinding.tvTaskDue.setOnClickListener(v -> showDateTimePickerDialog());
        mainBinding.btnSave.setOnClickListener(v -> {
            if (!name.isEmpty() && !due.isEmpty()) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        TaskModel model = new TaskModel();
        model.setId(UUID.randomUUID().toString());
        model.setName(name);
        model.setDueDate(DateFormat.format("yyyy-MM-dd", calendar).toString());
        model.setDueTime(DateFormat.format("hh-mm", calendar).toString());
        if (PreferenceUtil.getList(this) != null) {
            list.clear();
            list.addAll(PreferenceUtil.getList(this));
        }
        list.add(model);
        PreferenceUtil.saveList(list, this);
        scheduleNotification(model);
        Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show();
        getOnBackPressedDispatcher().onBackPressed();
    }

    private void scheduleNotification(TaskModel model) {
        Intent intent = new Intent(getApplicationContext(), NotificationClass.class);
        String title = model.getName();
        String message = "This task is due within one hour";
        intent.putExtra(NotificationClass.titleExtra, title);
        intent.putExtra(NotificationClass.messageExtra, message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                NotificationClass.notificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Long time = getTime(model.getDueDate() + " " + model.getDueTime());
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    //getting time in milliseconds from date and time
    private Long getTime(String s) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm", Locale.getDefault());
            Date date = format.parse(s);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Log.d("TIME", new Gson().toJson(date.getTime()));
            return calendar.getTimeInMillis();
        } catch (Exception e) {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTimeInMillis();
        }
    }

    //Creating notification channel
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NotificationClass.channelID, getString(R.string.channel_name), NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(getString(R.string.channel_description));

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void showDateTimePickerDialog() {

        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    showTimePickerDialog();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePicker = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    due = DateFormat.format("yyyy-MM-dd hh-mm", calendar).toString();
                    mainBinding.tvTaskDue.setText(due);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePicker.show();
    }

    ActivityResultLauncher<Intent> speechResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    ArrayList<String> matches = null;
                    if (data != null) {
                        matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    }
                    if (matches != null && !matches.isEmpty()) {
                        name = matches.get(0);
                        mainBinding.tvTaskName.setText(name);
                    }
                }
            });
}