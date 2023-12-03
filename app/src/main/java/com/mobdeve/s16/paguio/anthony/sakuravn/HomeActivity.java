package com.mobdeve.s16.paguio.anthony.sakuravn;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView contGame, uName, signout, exit;
    private TextView newGame;
    private ImageView DP;
    GameThread gameThread;
    public String email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        uName = findViewById(R.id.uName);
        signout = findViewById(R.id.logout);
        exit = findViewById(R.id.exit);
        DP = findViewById(R.id.DP);
        contGame = findViewById(R.id.contGame);
        newGame = findViewById(R.id.newGame);
        CollectionReference usersCollection = db.collection("users");
//        email = getIntent().getStringExtra("email");

        //Get info on user's gender from DB
//        usersCollection.document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()) {
//                        String gender = document.getString("gender");
//
//                        if(gender.equals("male")) {
//                            DP.setImageResource(R.drawable.malemc);
//                        }
//                        else if(gender.equals("female")) {
//                            DP.setImageResource(R.drawable.femc);
//                        }
//                        uName.setText(document.getString("username"));
//                    }
//                } Log.d(TAG, "User does not exist!");
//            }
//        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

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

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                Intent intent = new Intent (HomeActivity.this, MainActivity.class);
                intent.putExtra("index", index);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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