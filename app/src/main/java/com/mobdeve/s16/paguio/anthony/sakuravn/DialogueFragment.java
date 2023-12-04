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
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        if (gameEngine.shouldTransition() && !gameEngine.isCanvasLocked()) {
            txtBox.setVisibility(View.GONE);
            MC.setVisibility(View.GONE);
            showInnerDialogueFragment();
        }
        else if (!gameEngine.shouldTransition() && !gameEngine.isCanvasLocked()) {
            txtBox.setVisibility(View.VISIBLE);
            MC.setVisibility(View.VISIBLE);
            hideInnerDialogueFragment();
            handler.postDelayed(typewriterRunnable, 50);
        }
        else {
            showChoicesFragment();
            Log.e("DialogueFragment", "Canvas is locked");
        }

    }

    private Runnable typewriterRunnable = new Runnable() {
        @Override
        public void run() {
            if (charIndex < gameEngine.getDialogue().length()) {
                // Append the next character
                tvDialogue.append(String.valueOf(gameEngine.getDialogue().charAt(charIndex++)));

                // Schedule the next character after the delay
                handler.postDelayed(this, 17); // Delay between characters (adjust as needed)
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
    public void hideCurrentDialogueBox() {
        // Hide txtBox when calling from DialogueFragment
        if (txtBox != null) {
            txtBox.setVisibility(View.GONE);
        }
    }

    public void showChoicesFragment() {
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

    public void auto(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                userCollection.document(email).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        index = task.getResult().getLong("currentIndex").intValue();
                        // if the current index is not locked, then go to the next dialogue
                        if (!gameEngine.isCanvasLocked()) {
                            gameEngine.next(index);
                            if (gameEngine.checkIfLastDialogue(index)) {
                                gameEngine.setCanvasLocked(true);
                                index -= 1;
                                userCollection.document(email).update("currentIndex", index);
                            }
                            else {
                                index++; // increment the index
                                userCollection.document(email).update("currentIndex", index);
                                updateDialogue();
                                auto();
                            }

                        }
                    }
                });
            }
        }, 4500);

    }

}
