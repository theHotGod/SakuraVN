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

public class MainActivity extends AppCompatActivity implements ActionBar.OnPauseGameListener {
    GameLayout gameLayout;
    GameThread gameThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameLayout = new GameLayout(this);
        setContentView(gameLayout);
    }

    @Override
    public void onPauseGame() {
        // Pause the game
        if (gameThread != null) {
            gameThread.setIsRunning(false);
        }
    }

    public DialogueFragment getDialogueFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogueFragment dialogueFragment = (DialogueFragment) fragmentManager.findFragmentById(R.id.dialogueFragmentContainer);
        return dialogueFragment;
    }
}