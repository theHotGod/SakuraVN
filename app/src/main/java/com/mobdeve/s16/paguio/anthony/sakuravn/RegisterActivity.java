package com.mobdeve.s16.paguio.anthony.sakuravn;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobdeve.s16.paguio.anthony.sakuravn.models.UserAccount;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLogin, userInput, userPass, userPassword, confirmPassword;
    Button registerBtn;
    //Firebase Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        tvLogin = findViewById(R.id.tvLogin);
        registerBtn = findViewById(R.id.registerBtn);
        userInput = findViewById(R.id.userInput);
        userPass = findViewById(R.id.userPass);
        userPassword = findViewById(R.id.userPassword);
        confirmPassword = findViewById(R.id.confirmPassword);

        registerBtn.setOnClickListener(v -> register(v));
        tvLogin.setOnClickListener(v -> login(v));
    }

    public void register(View v) {
        String input = userInput.getText().toString();
        String email = userPass.getText().toString();
        String password = userPassword.getText().toString();
        String confirm = confirmPassword.getText().toString();
        int index = 0;
        String gender = "none";

        // Just to validate user input so empty fields won't enter the DB
        if (input.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if password and confirm password are equal
        if (!password.equals(confirm)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        // register the user via authentication
        // add the user to the database
            CollectionReference usersCollection = db.collection("users");
            usersCollection.document(email).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        UserAccount user = new UserAccount(input, email, password, index, gender);
                        DocumentReference userRef = usersCollection.document(email);

                        userRef.set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + userRef.getId());
                                    Intent intent = new Intent(RegisterActivity.this, ChoosePlayerActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Log.w(TAG, "Error adding document", e);
                                    // Handle the failure, show a message, etc.
                                });
                    }
                }
            });
    }

    public void login(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}