package com.mobdeve.s16.paguio.anthony.sakuravn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView mcImage;
    LinearLayout txtBox;
    RelativeLayout mainLayout;
    TextView tvContent;
    ImageButton homePage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mc_talking);

        // get data from choose mc activity

        // set the mc image to the chosen mc
        // how do i go about doing this?
        // i need to get the data from the choose mc activity
        // and then set the image to the chosen mc

        Intent getValues = getIntent();
        boolean maleMC = false;
        boolean feMC = false;
        ArrayList<String> dialogue = new ArrayList<String>();

        maleMC = getValues.getBooleanExtra("isMaleMCChosen", false);
        feMC = getValues.getBooleanExtra("isFemaleMCChosen", false);

        String tempDialogue1 = "I just woke up. I should get ready for school.";
        String tempDialogue2 = "I think my friends are waiting for me there.";
        String tempDialogue3 = "I should go to school now.";
        String tempDialogue4 = "Ok. I shall head to the station now.";
        dialogue.add(tempDialogue1);
        dialogue.add(tempDialogue2);
        dialogue.add(tempDialogue3);
        dialogue.add(tempDialogue4);



        mcImage = findViewById(R.id.MC);
        txtBox = findViewById(R.id.txtBox);
        mainLayout = findViewById(R.id.dialogue);
        tvContent = findViewById(R.id.contentTxt);
        homePage = findViewById(R.id.homeBtn);

        tvContent.setText(dialogue.get(0));
        if (maleMC) {
            mcImage.setImageResource(R.drawable.malemc);
        } else if (feMC) {
            mcImage.setImageResource(R.drawable.femc);
        }

        // if mainlayout or txtbox is clicked, go to next dialogue


        mainLayout.setOnClickListener(v -> {
            // temporary set to HomeActivity once dialogue is finished.
            if (dialogue.size() == 0) {
                txtBox.setVisibility(v.INVISIBLE);
                mcImage.setVisibility(v.INVISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                NoTalkingFragment noTalkingFragment = new NoTalkingFragment();
                fragmentTransaction.replace(R.id.dialogue, noTalkingFragment);
                fragmentTransaction.commit();
            }
            else {
                tvContent.setText(dialogue.get(0));
                dialogue.remove(0);
            }
        });



        homePage.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });






    }
}