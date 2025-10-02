package com.example.uzstopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvTimer;
    Button btnStart, btnPause, btnReset;

    Handler handler = new Handler();
    long startTime = 0L, timeInMillis = 0L, pauseTime = 0L;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);

        // Start
        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = System.currentTimeMillis() - pauseTime;
                handler.postDelayed(updateTimer, 0);
                isRunning = true;
            }
        });

        // Pause
        btnPause.setOnClickListener(v -> {
            if (isRunning) {
                pauseTime = System.currentTimeMillis() - startTime;
                handler.removeCallbacks(updateTimer);
                isRunning = false;
            }
        });

        // Reset
        btnReset.setOnClickListener(v -> {
            handler.removeCallbacks(updateTimer);
            isRunning = false;
            startTime = 0L;
            pauseTime = 0L;
            tvTimer.setText("00:00:000");
        });
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeInMillis = System.currentTimeMillis() - startTime;

            int minutes = (int) (timeInMillis / 1000) / 60;
            int seconds = (int) (timeInMillis / 1000) % 60;
            int milliseconds = (int) (timeInMillis % 1000);

            tvTimer.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));

            handler.postDelayed(this, 10); // update every 10 ms
        }
    };
}
