package com.mobdeve.s16.paguio.anthony.sakuravn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ChoosePlayerActivity extends AppCompatActivity {

    ImageView maleMC;
    ImageView femaleMC;
    Button confirmBtn;
    Button clearBtn;
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

        // set the onClickListeners
        // q: what is a good approach to have the player choose only one character from the ImageView?
        // a: use the setOnClickListener() method of the ImageView class
        // q: will i have one setOnClickListener for each or one for both?
        // a: one for both

        // setOnClickListener() function
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
            }
        });
        // q: what if i want the toast to appear if the user tries to pick another character?

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleMC.setEnabled(true);
                femaleMC.setEnabled(true);
                confirmBtn.setEnabled(false);

                // change both MCs drawable to indicate unchosen character
                maleMC.setImageResource(R.drawable.not_chosen_malemc);
                femaleMC.setImageResource(R.drawable.not_chosen_femc);
            }
        });


    }

}