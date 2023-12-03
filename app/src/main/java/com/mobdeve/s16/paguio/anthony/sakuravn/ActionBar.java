package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ActionBar extends Fragment {
    private ImageButton homeBtn;
    private ImageButton dialogueBtn;
    private ImageButton fastBtn;
    private ImageButton autoBtn;

    private OnPauseGameListener onPauseGameListener;

    GameView gameView;

    GameThread gameThread;

    public ActionBar() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            // Set the listener when the fragment is attached to the activity
            onPauseGameListener = (OnPauseGameListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPauseGameListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action_bar, container, false);

        // Log the contents of the inflated view
        Log.d("ActionBar", "Inflated view: " + view);

        // Retrieve ImageButton references
        homeBtn = view.findViewById(R.id.homeBtn);
        dialogueBtn = view.findViewById(R.id.dialogueBtn);
        fastBtn = view.findViewById(R.id.fastBtn);
        autoBtn = view.findViewById(R.id.autoBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public interface OnPauseGameListener {
        void onPauseGame();
    }

    private void pauseGame() {
        if (onPauseGameListener != null) {
            onPauseGameListener.onPauseGame();
        }
    }

}