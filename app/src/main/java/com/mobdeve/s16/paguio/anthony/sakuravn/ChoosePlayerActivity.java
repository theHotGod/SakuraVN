package com.mobdeve.s16.paguio.anthony.sakuravn;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChoosePlayerActivity extends AppCompatActivity {

    ImageView maleMC;
    ImageView femaleMC;
    Button confirmBtn;
    Button clearBtn;
    private String userEmail;
    boolean isMaleMCChosen = false;
    boolean isFemaleMCChosen = false;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Intent intent = new Intent(ChoosePlayerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            userEmail = currentUser.getEmail();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_player);

        // initialize the views
        maleMC = (ImageView) findViewById(R.id.maleMC);
        femaleMC = (ImageView) findViewById(R.id.feMC);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);

        confirmBtn.setEnabled(false);
        //Initiate FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // store the data of the chosen character in playerMC, then will be stored in the database


        maleMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enable the confirm button
                confirmBtn.setEnabled(true);
                // change maleMC drawable to indicate chosen character
                maleMC.setImageResource(R.drawable.chosen_malemc);

                // revert femaleMC drawable to indicate unchosen character
                femaleMC.setImageResource(R.drawable.not_chosen_femc);
                // show a toast message to indicate chosen character
                Toast.makeText(ChoosePlayerActivity.this, "You have chosen MaleMC", Toast.LENGTH_SHORT).show();
                isMaleMCChosen = true;

            }
        });

        femaleMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmBtn.setEnabled(true);
                // change femaleMC drawable to indicate chosen character
                femaleMC.setImageResource(R.drawable.chosen_femc);

                // revert maleMC drawable to indicate unchosen character
                maleMC.setImageResource(R.drawable.not_chosen_malemc);
                // show a toast message to indicate chosen character
                Toast.makeText(ChoosePlayerActivity.this, "You have chosen FeMC", Toast.LENGTH_SHORT).show();
                isFemaleMCChosen = true;
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleMC.setEnabled(true);
                femaleMC.setEnabled(true);
                confirmBtn.setEnabled(false);

                // change both MCs drawable to indicate unchosen character
                maleMC.setImageResource(R.drawable.not_chosen_malemc);
                femaleMC.setImageResource(R.drawable.not_chosen_femc);
                isFemaleMCChosen = false;
                isMaleMCChosen = false;
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFemaleMCChosen || isMaleMCChosen) {
                    // Update the user's gender in Firestore based on the chosen character
                    if (isFemaleMCChosen) {
                        updateUserGender(userEmail, "female");
                    } else if (isMaleMCChosen) {
                        updateUserGender(userEmail, "male");
                    }
                }
            }
        });


    }
    private void updateUserGender(String userEmail, String gender) {
        CollectionReference usersCollection = db.collection("users");
        DocumentReference userRef = usersCollection.document(userEmail);

        userRef.update("gender", gender)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "User gender updated successfully");
                    // Proceed to MainActivity or perform any other actions
                    Intent intent = new Intent(ChoosePlayerActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error updating user gender", e);
                    // Handle the failure, show a message, etc.
                });
    }
}