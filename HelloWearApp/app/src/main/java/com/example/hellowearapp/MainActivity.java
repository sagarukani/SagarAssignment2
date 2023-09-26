package com.example.hellowearapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTime = findViewById(R.id.tvTime);

        tvTime.setText(String.format("Today's Date is%s", new SimpleDateFormat("EEEE, MMMM d - yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime())));

    }
}