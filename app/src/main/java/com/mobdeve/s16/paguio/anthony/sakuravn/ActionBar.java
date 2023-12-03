package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActionBar extends Fragment {
    private ImageButton homeBtn;
    private ImageButton dialogueBtn;
    private ImageButton fastBtn;
    private ImageButton autoBtn;

    private OnPauseGameListener onPauseGameListener;

    GameView gameView;

    GameThread gameThread;
    GameEngine gameEngine;
    Integer counter;

    public boolean isAuto = false;
    public Handler handler = new Handler();

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
        counter = 1;

        autoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAuto){
                    stop();
                    Log.d("counter", String.valueOf(isAuto));
                }
                else{
                    autoPlay();
                    Log.d("counter", String.valueOf(isAuto));
                }
            }
        });

        fastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment1 = getActivity().getSupportFragmentManager().findFragmentById(R.id.dialogueFragmentContainer);
                Fragment fragment2 = getActivity().getSupportFragmentManager().findFragmentById(R.id.innerDialogueFragmentContainer);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(fragment1)
                        .remove(fragment2)
                        .commit();

                choiceFragment ChoiceFragment = new choiceFragment();

                FragmentManager m = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = m.beginTransaction();

                ft.replace(R.id.choiceFragmentContainer, ChoiceFragment, "two");
                ft.commit();
            }
        });

        dialogueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter == 1) {
                    counter = 0;
                    BackreadFragment backreadFragment = new BackreadFragment();

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();

                    ft.replace(R.id.backreadFragmentContainer, backreadFragment, "one");
                    ft.commit();
                }
                else {
                    counter = 1;
                    closeFragment();
                }

            }

        });

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

    private void closeFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("one");

        if (fragment != null) {
            FragmentTransaction ft = manager.beginTransaction();

            ft.remove(fragment);

            ft.commit();
        }
    }

    private void autoPlay(){
        isAuto = true;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        DialogueFragment dialogueFragment = (DialogueFragment) manager.findFragmentById(R.id.dialogueFragmentContainer);

        if (dialogueFragment != null) {
            dialogueFragment.auto();
        }
    }

    private void stop(){
        isAuto = false;
        handler.removeCallbacksAndMessages(null);
    }
}