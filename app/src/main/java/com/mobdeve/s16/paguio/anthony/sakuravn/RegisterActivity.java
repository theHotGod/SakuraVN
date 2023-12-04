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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobdeve.s16.paguio.anthony.sakuravn.models.UserAccount;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLogin, userInput, userPass, userPassword, confirmPassword;
    Button registerBtn;
    //Firebase Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private CollectionReference usersCollection = db.collection("users");
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

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(v -> register(v));
        tvLogin.setOnClickListener(v -> login(v));
    }


    public void register(View v) {
        String input = userInput.getText().toString();
        String email = userPass.getText().toString();
        String password = userPassword.getText().toString();
        String confirm = confirmPassword.getText().toString();
        int index = 0;
        String gender = "none"; // To not make the gender field as null


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

        if(password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Passwords should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        UserAccount user = new UserAccount(input, email, password, index, gender);
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Created user on Auth");
                            currentUser = mAuth.getCurrentUser();
                            usersCollection.document(currentUser.getEmail()).set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "User has been added to the DB");
                                        }
                                    }); // end of userCollection.doc.....
                            Toast.makeText(RegisterActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, ChoosePlayerActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            if (task.getException() != null && task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this, "Email already exists. Try a different email.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }); //end of mAuth
    }

    public void login(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}