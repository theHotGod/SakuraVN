package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DialogueFragment extends Fragment {
    private GameEngine gameEngine;
    private TextView tvDialogue;
    private LinearLayout txtBox;
    private ImageView MC;

    private Handler handler;
    private int charIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogue, container, false);
        tvDialogue = view.findViewById(R.id.contentTxt);
        txtBox = view.findViewById(R.id.txtBox);
        MC = view.findViewById(R.id.MC);
        gameEngine = GameManager.getInstance().getGameEngine();
        handler = new Handler(Looper.getMainLooper());
        charIndex = 0;
        updateDialogueWithTypewriterEffect();
        return view;
    }

    private void updateDialogueWithTypewriterEffect() {
        charIndex = 0;
        tvDialogue.setText(""); // Clear existing text

        if (gameEngine.shouldTransition()) {
            txtBox.setVisibility(View.GONE);
            MC.setVisibility(View.GONE);
            showInnerDialogueFragment();
        }
        else {
            txtBox.setVisibility(View.VISIBLE);
            MC.setVisibility(View.VISIBLE);
            hideInnerDialogueFragment();
            handler.postDelayed(typewriterRunnable, 50);
        }

        // Delay between characters (adjust as needed)
    }

    private Runnable typewriterRunnable = new Runnable() {
        @Override
        public void run() {
            if (charIndex < gameEngine.getDialogue().length()) {
                // Append the next character
                tvDialogue.append(String.valueOf(gameEngine.getDialogue().charAt(charIndex++)));

                // Schedule the next character after the delay
                handler.postDelayed(this, 50); // Delay between characters (adjust as needed)
            } else {
                // Typewriter effect finished
                charIndex = 0; // Reset for the next update
            }
        }
    };

    public void updateDialogue() {
        if (tvDialogue != null && gameEngine != null) {
            updateDialogueWithTypewriterEffect();
        }
    }
    public void showInnerDialogueFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        InnerDialogueFragment innerDialogueFragment = (InnerDialogueFragment) fragmentManager.findFragmentById(R.id.innerDialogueFragmentContainer);
        if (innerDialogueFragment != null) {
            innerDialogueFragment.showCurrentDialogueBox();
            innerDialogueFragment.updateDialogue();
        }
    }

    public void hideInnerDialogueFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        InnerDialogueFragment innerDialogueFragment = (InnerDialogueFragment) fragmentManager.findFragmentById(R.id.innerDialogueFragmentContainer);
        if (innerDialogueFragment != null) {
            innerDialogueFragment.hideCurrentDialogueBox();
        }
    }

}
