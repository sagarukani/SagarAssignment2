package com.example.dishantkumarassignment1;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dishantkumarassignment1.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Handler handler;

    Integer second = 0;

    Boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void timer(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                String time = String.format( "%d:%02d:%02d", (second / 3600), ((second % 3600) / 60), (second % 60));
                binding.tvTime.setText(time);
                if (started) {
                    second++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void init() {
        handler = new Handler();

        binding.btStart.setOnClickListener(v -> {

            if (!started) {
                started = true;
                timer();
            }
            binding.btStart.setEnabled(false);
            binding.btStop.setEnabled(true);
            binding.btReset.setEnabled(false);
        });
        binding.btStop.setOnClickListener(v -> {
             started=false;
            binding.btStop.setEnabled(false);
            binding.btReset.setEnabled(true);
        });
        binding.btReset.setOnClickListener(v -> {
            second=0;
            binding.tvTime.setText(getString(R.string.hint));
            binding.btStart.setEnabled(true);
            binding.btReset.setEnabled(false);
        });
    }
}