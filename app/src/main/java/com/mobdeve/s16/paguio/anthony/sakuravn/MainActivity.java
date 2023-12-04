package com.mobdeve.s16.paguio.anthony.sakuravn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

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
        return (DialogueFragment) fragmentManager.findFragmentById(R.id.dialogueFragmentContainer);
    }

    public InnerDialogueFragment getInnerDialogueFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (InnerDialogueFragment) fragmentManager.findFragmentById(R.id.innerDialogueFragmentContainer);
    }

    public choiceFragment getChoiceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (choiceFragment) fragmentManager.findFragmentById(R.id.choiceFragmentContainer);
    }
}