package com.example.todowaerapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.todowaerapp.model.Task;

public class TaskUtils {
    public static void saveTask(Task task, Context context){
        if (task!=null){
            SharedPreferences preferences = context.getSharedPreferences("Task_details",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =  preferences.edit();
            editor.putString(task.getId(),task.getTaskDetails());
            editor.commit();
        }
    }

    public static  void  deleteTask(Task task,Context context){
        if (task!=null){
            SharedPreferences preferences = context.getSharedPreferences("task_detials",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(task.getId());
            editor.commit();
        }
    }
}
