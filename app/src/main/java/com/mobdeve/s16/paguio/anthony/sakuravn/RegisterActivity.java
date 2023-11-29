package com.mobdeve.s16.paguio.anthony.sakuravn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobdeve.s16.paguio.anthony.sakuravn.models.UserAccount;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLogin, userInput, userPass, userPassword, confirmPassword;
    Button registerBtn;

    UserAccount.Gender gender;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

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
        DatabaseReference myRef = database.getReference("UserAccount");

        // Just to validate user input so empty fields won't enter the DB
        if (input.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (password != confirm) {
//            //Toast.makeText(RegisterActivity.this, "Password and Confirm password do not match!", Toast.LENGTH_SHORT).show();
//            Toast.makeText(RegisterActivity.this, "Password: " + password, Toast.LENGTH_SHORT).show();
//            Toast.makeText(RegisterActivity.this, "Confirm password: " + confirm, Toast.LENGTH_SHORT).show();
//            return;
//        }
            UserAccount user = new UserAccount(input, email, password);

            myRef.push().setValue(user);


            Intent intent = new Intent(this, ChoosePlayerActivity.class);
            startActivity(intent);
            finish();
    }

    public void login(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}