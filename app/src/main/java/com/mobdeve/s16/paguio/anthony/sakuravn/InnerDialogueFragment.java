package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InnerDialogueFragment extends Fragment {
    GameEngine gameEngine;
    private TextView contentTxt;
    private Handler handler;
    private int charIndex;
    private LinearLayout txtBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inner_dialogue, container, false);
        contentTxt = view.findViewById(R.id.contentTxt);
        txtBox = view.findViewById(R.id.txtBox);
        gameEngine = GameManager.getInstance().getGameEngine();
        handler = new Handler(Looper.getMainLooper());
        charIndex = 0;


        updateDialogueWithTypewriterEffect();
        return view;
    }

    public void updateDialogueWithTypewriterEffect() {
        charIndex = 0;
        if (contentTxt != null) {
            contentTxt.setText(""); // Clear existing text
            handler.postDelayed(typewriterRunnable, 35); // Delay between characters (adjust as needed)
        }
    }

    public void hideCurrentDialogueBox() {
        // Hide txtBox when calling from DialogueFragment
        if (txtBox != null) {
            txtBox.setVisibility(View.GONE);
        }
    }

    public void showCurrentDialogueBox() {
        // Show txtBox when calling from DialogueFragment
        if (txtBox != null) {
            txtBox.setVisibility(View.VISIBLE);
        }
    }

    public void updateDialogue() {
        if (contentTxt != null && gameEngine != null) {
            updateDialogueWithTypewriterEffect();
        }
    }
    private Runnable typewriterRunnable = new Runnable() {
        @Override
        public void run() {
            if (charIndex < gameEngine.getDialogue().length()) {
                // Append the next character
                contentTxt.append(String.valueOf(gameEngine.getDialogue().charAt(charIndex++)));

                // Schedule the next character after the delay
                handler.postDelayed(this, 50); // Delay between characters (adjust as needed)
            } else {
                // Typewriter effect finished
                charIndex = 0; // Reset for the next update
            }
        }
    };
}