package com.mobdeve.s16.paguio.anthony.sakuravn;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobdeve.s16.paguio.anthony.sakuravn.models.UserAccount;

public class LoginActivity extends AppCompatActivity {


    TextView tvRegister;
    TextView userInput;
    TextView userPassword;
    Button loginBtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollection = db.collection("users");
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            //check if gender has been set
            String userEmail = currentUser.getEmail();
            usersCollection.document(userEmail).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Document exists, check if gender is set
                            String gender = document.getString("gender");

                            if (gender != null && (gender.equals("male") || gender.equals("female"))) {
                                // Gender is set to male or female, go to HomeActivity
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish(); // Optional: Finish the current activity to prevent going back to it using the back button
                            } else {
                                // Gender is not set or set to none, go to ChoosePlayerActivity
                                Intent intent = new Intent(LoginActivity.this, ChoosePlayerActivity.class);
                                startActivity(intent);
                                finish(); // Optional: Finish the current activity to prevent going back to it using the back button
                            }
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Something went wrong.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        mAuth = FirebaseAuth.getInstance();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    public void login(View v) {

        String email = userInput.getText().toString();
        String password = userPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    usersCollection.document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    // Document exists, check if gender is set
                                    String gender = document.getString("gender");

                                    if (gender != null && (gender.equals("male") || gender.equals("female"))) {
                                        // Gender is set to male or female, go to HomeActivity
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish(); // Optional: Finish the current activity to prevent going back to it using the back button
                                    } else {
                                        // Gender is not set or set to none, go to ChoosePlayerActivity
                                        Intent intent = new Intent(LoginActivity.this, ChoosePlayerActivity.class);
                                        startActivity(intent);
                                        finish(); // Optional: Finish the current activity to prevent going back to it using the back button
                                    }
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Something went wrong.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }); // end of DB authentication
                } else {
                    //Authentication failed
                    Toast
                    .makeText(LoginActivity.this,
                        "Wrong email or password.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}