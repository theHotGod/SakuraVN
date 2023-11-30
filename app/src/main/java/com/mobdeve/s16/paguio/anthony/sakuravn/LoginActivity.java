package com.mobdeve.s16.paguio.anthony.sakuravn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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
        Intent intent = new Intent(this, ChoosePlayerActivity.class);
        startActivity(intent);
        finish();
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