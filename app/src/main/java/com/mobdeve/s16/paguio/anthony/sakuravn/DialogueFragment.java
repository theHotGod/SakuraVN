package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogueFragment extends Fragment {
    private GameEngine gameEngine;
    private TextView tvDialogue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogue, container, false);
        tvDialogue = view.findViewById(R.id.contentTxt);
        gameEngine = GameManager.getInstance().getGameEngine();
        updateDialogue();
        return view;
    }

    public void updateDialogue() {
        if (tvDialogue != null && gameEngine != null) {
            tvDialogue.setText(gameEngine.getDialogue());
        }
    }
}