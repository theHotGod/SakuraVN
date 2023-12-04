package com.mobdeve.s16.paguio.anthony.sakuravn;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView contGame, uName, signout, exit;
    private TextView newGame;
    GameThread gameThread;
    public String email;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference userCollection = db.collection("users");
    FirebaseAuth auth;
    FirebaseUser user;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        uName = findViewById(R.id.uName);
        signout = findViewById(R.id.logout);
        exit = findViewById(R.id.exit);
        contGame = findViewById(R.id.contGame);
        newGame = findViewById(R.id.newGame);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null) {
            Intent intent = new Intent (HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            uName.setText(user.getEmail());
        }

        builder = new AlertDialog.Builder(this);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        contGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameRunning(MainActivity.class)) {
                    // Start the MainActivity to continue the game
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;

                userCollection.document(user.getEmail()).get().addOnCompleteListener(task ->  {
                    if (task.isSuccessful()) {
                        int progress = task.getResult().getLong("currentIndex").intValue();

                        if (progress != 0) {
                            builder.setMessage("Are you sure you want to start a new game?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            userCollection.document(user.getEmail()).update("currentIndex", index, "gender", "none")
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()) {
                                                                Log.d(TAG, "Updated user data on the database.");
                                                                Toast.makeText(HomeActivity.this, "Starting a new game", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent (HomeActivity.this, ChoosePlayerActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                Log.w(TAG, "Error updating user data", task.getException());
                                                            }
                                                        }
                                                    }); //end of userCollection.document.....
                                        }
                                    }) //End of setPositiveButton
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }).show();
                        } /*end of if (progress)*/ else {
                            Log.d(TAG, "You did not even start the game...");
                            Toast.makeText(HomeActivity.this, "Starting a new game", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this, ChoosePlayerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } // end of if task.isSuccessful
                });
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
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