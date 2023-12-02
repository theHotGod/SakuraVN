package com.mobdeve.s16.paguio.anthony.sakuravn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView contGame;
    private TextView newGame;
    GameThread gameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        contGame = findViewById(R.id.contGame);
        contGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameRunning(MainActivity.class)) {
                    // Start the MainActivity to continue the game
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Check if a specific activity is running
    private boolean isGameRunning(Class<?> activityClass) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo taskInfo : runningTasks) {
            if (taskInfo.topActivity != null && taskInfo.topActivity.getClassName().equals(activityClass.getName())) {
                return true;
            }
        }

        return false;
    }
}