package com.example.sagarassignment2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sagarassignment2.model.TaskModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PreferenceUtil {
    public static final String NAME = "SagarAssignment1";
    public static final String LIST_NAME = "my_tasks";

    public static void saveList(List<TaskModel> list, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_NAME, new Gson().toJson(list));
        editor.apply();
    }

    public static List<TaskModel> getList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(LIST_NAME, null);
        Type type = new TypeToken<List<TaskModel>>(){}.getType();
        return new Gson().fromJson(json, type);
    }
}
