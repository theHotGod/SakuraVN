package com.mobdeve.s16.paguio.anthony.sakuravn;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobdeve.s16.paguio.anthony.sakuravn.models.UserAccount;

public class LoginActivity extends AppCompatActivity {


    TextView tvRegister;
    // what do i declare here before i find by id the plain text
    TextView userInput;
    TextView userPassword;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        tvRegister = findViewById(R.id.tvRegister);
        userInput = findViewById(R.id.userInput);
        userPassword = findViewById(R.id.userPass);
        loginBtn = findViewById(R.id.loginBtn);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    // TODO: create function for login button
    public void login(View v) {

        String email = userInput.getText().toString();
        String password = userPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        usersCollection.document(email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    // Email exists, check if the password matches
                    UserAccount user = task.getResult().toObject(UserAccount.class);
                    if (user != null && user.getPassword().equals(password)) {
                        // Password matches, login successful
                        Intent intent = new Intent(LoginActivity.this, ChoosePlayerActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Password does not match
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Email does not exist
                    Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.w(TAG, "Error checking document existence", task.getException());
                // Handle the error, show a message, etc.
            }
        });

    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // TODO: create a visual novel game
    // q: what are the essentials of a visual novel game?
    // a: 1. story
    // a: 2. choices
    // a: 3. endings
    // a: 4. save/load
    // a: 5. settings
    // a: 6. achievements
    // a: 7. gallery


}