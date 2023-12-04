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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DialogueFragment extends Fragment{
    private GameEngine gameEngine;
    private TextView tvDialogue;
    private LinearLayout txtBox;
    private ImageView MC;
    private TextView speakerTxt;

    private Handler handler;
    private int charIndex;
    private String gender;
    private String username;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth currentUser = FirebaseAuth.getInstance();
    String email = currentUser.getCurrentUser().getEmail();
    CollectionReference userCollection = db.collection("users");
    int index;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogue, container, false);

        tvDialogue = view.findViewById(R.id.contentTxt);
        txtBox = view.findViewById(R.id.txtBox);
        speakerTxt = view.findViewById(R.id.speakerTxt);
        MC = view.findViewById(R.id.MC);
        userCollection.document(email).get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                gender = documentSnapshot.getString("gender");
                username = documentSnapshot.getString("username");
                index = documentSnapshot.getLong("currentIndex").intValue();

                if (gender.equals("male")) {
                    // set to drawable malemc.png
                    MC.setImageResource(R.drawable.malemc);
                }
                else if (gender.equals("female")) {
                    // set to drawable femc.webp
                    MC.setImageResource(R.drawable.femc);
                }

                // set speakerTxt to username
                speakerTxt.setText(username);

            }
        });


        gameEngine = GameManager.getInstance().getGameEngine();
        handler = new Handler(Looper.getMainLooper());
        charIndex = 0;

        view.setVisibility(View.GONE);

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

    public void auto(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEngine.next(index);
                updateDialogue();
                auto();
            }
        }, 3000);
    }

}
