package com.example.sagarassignment1.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sagarassignment1.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding mainBinding;
    private Handler mHandler;
    private int sec = 0;
    private boolean isTimerStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting content view
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        initOnClick();
    }

    //button click event listeners
    private void initOnClick() {
        mainBinding.btnReset.setOnClickListener(this);
        mainBinding.btnStop.setOnClickListener(this);
        mainBinding.btnStart.setOnClickListener(this);
    }

    //timer function
    private void timer() {
        mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // seconds to minutes and hours calculation
                int hours = sec / 3600;
                int minutes = (sec % 3600) / 60;
                int secs = sec % 60;
                mainBinding.tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs));
                if (isTimerStarted) {
                    sec++;
                }
                mHandler.postDelayed(this, 1000);
            }
        });
    }

    //On click listeners of all buttons
    @Override
    public void onClick(View v) {
        if (v.getId() == mainBinding.btnStart.getId()) {
            if (!isTimerStarted) {
                isTimerStarted = true;
                timer();
                mainBinding.btnStart.setEnabled(false);
                mainBinding.btnReset.setEnabled(false);
                mainBinding.btnStop.setEnabled(true);
            }

        } else if (v.getId() == mainBinding.btnStop.getId()) {
            isTimerStarted = false;
            mainBinding.btnReset.setEnabled(true);
            mainBinding.btnStop.setEnabled(false);
        } else if (v.getId() == mainBinding.btnReset.getId()) {
            isTimerStarted = false;
            mainBinding.btnStart.setEnabled(true);
            mainBinding.btnStop.setEnabled(false);
            mainBinding.btnReset.setEnabled(false);
            sec = 0;
            mainBinding.tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", 0, 0, 0));
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}