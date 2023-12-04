package com.mobdeve.s16.paguio.anthony.sakuravn;

import static android.app.PendingIntent.getActivity;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GameEngine {
    // the game's dialogue and choices are stored here
    final private ArrayList<String> dialogues;
    final private ArrayList<String> choice;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userCollection = db.collection("users");
    private FirebaseAuth currentUser = FirebaseAuth.getInstance();

    private int currentDialogue;
    private boolean isCanvasLocked;
    private int index;

    public GameEngine() {
        this.dialogues = new ArrayList<String>();
        this.choice = new ArrayList<String>();
        this.isCanvasLocked = false;
        // get the this.currentDialogue from the firestore database to reflect the current progress of the user
        String email = currentUser.getCurrentUser().getEmail();
        userCollection.document(email).get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                index = documentSnapshot.getLong("currentIndex").intValue();
                setDialogue(index);
            }
        });

        initializeDialoguesAndChoices();



    }


    public void next(int currentIndex) {
        if (currentIndex >= 0 && currentIndex < dialogues.size()) {
            currentDialogue = currentIndex;

        } else {
            Log.e("GameEngine", "Invalid dialogue index");
            isCanvasLocked = true;
        }
    }


    private void initializeDialoguesAndChoices() {
        // first set of dialogues
        dialogues.add("Ever since I had been assigned to tend to this garden, I've been talking to these cherry blossoms.");
        dialogues.add("It's been fun these past couple of weeks, but I've come to a realization that people may look at me stupid. I think it's time for me to ask why this is so.");
        dialogues.add("I know I should've asked you sooner, but are you really capable of communicating with me? Or is it all in my head?");
        dialogues.add("There was nothing but pure silence. I should've known better. I knew that this was too good to be true. I should just leave.");
        dialogues.add("Got you there for a second, huh? I'm surprised that it took you this long to question all this.");
        dialogues.add("In all honesty, it is the latter. But who is to say that our imagination is fake? And who is to say that our conversations are fake as well?");
        dialogues.add("What do you mean?");
        dialogues.add("Alright. I need to tell you a secret. Have you ever had a moment in life wherein your senses failed to live up to your expectations? Something you were so sure about but it just wasn't so?");
        dialogues.add("I think I know what you mean. I've had a couple of those moments.");
        dialogues.add("I think that's what's happening to you right now. You're so sure that you're talking to me, but you're not. You're just talking to yourself. Because the world is just a mere reflection of self.");
        dialogues.add("I don't understand. What do you mean?");
        dialogues.add("I'm saying that you're not talking to me. You're talking to yourself. You're just talking to your inner self. I trust that you and others will know of this eventually to exit the wheel of recurrence.");
        dialogues.add("No. This can't be true. This is all fake.");
        dialogues.add("I know what you're saying. Besides, I'm just a mirror who's eventually going to mirror out your expressions in life.");

        dialogues.add("How can that be? And, how can you be so sure of all of this?!?");

        // second set of dialogues
        dialogues.add("I told you about the senses and how sometimes they fail to live up your expectations, correct? Stay calm. I know this is a lot to take in, but you'll soon understand.");
        dialogues.add("I'm going to need an example for me to understand this at any moment now.");
        dialogues.add("I can still feel what you're feeling at the moment. Here, let me just explain it this way. Remember what Seneca said about we often suffer in imagination more than in reality? Or the fact that Einstein said that imagination is more important than knowledge?");

        dialogues.add("Yes. I remember those quotes. After all, I love philosophy.");

        // third set of dialogues 19
        dialogues.add("I'm glad you do. Now, let me ask you this. What do you think they mean by that?");
        dialogues.add("I don't know. But if I were to guess, we shouldn't belittle our imagination?");
        dialogues.add("That's a good guess. But I think it's more than that. I think it's about how we should trust our imagination more than our senses. Because our senses can be deceiving.");
        dialogues.add("Well, here's the thing. We often imagine ourselves in negative scenario after negative scenario, thinking it is all real. But whenever we think of something positive, we don't credit it reality. See the pattern?");
        dialogues.add("We often disregard positivity in our lives due to believing torturing \"self\" is most beneficial for us to live amongst people, but it doesn't have to be that way.");
        dialogues.add("I think I understand now. But how does this all relate to the world being a mere reflection of self?");
        dialogues.add("I'm glad you asked. You see, the world is just a mere reflection of self. It is a reflection of how we see ourselves. If we see ourselves as a failure, then the world will be a failure. If we see ourselves as a success, then the world will be a success.");
        dialogues.add("I am overwhelmed by this information. I don't know what to do with this.");
        dialogues.add("The cherry blossoms then instruct me to do something with this principle and some past events I did in the past. I am shocked by the results.");
        dialogues.add("Trust me, I know that most people would be blown away by this, too. Now, do you believe in what I'm saying is true?");

        // ENDING CHOICE 1
        // GOOD ENDING:
        choice.add("Yes. Even if it still hard for me to grasp, I will continue on this journey to find out more about this principle.");
        // BAD ENDING:
        choice.add("No. Believe me when I say that it would've happened anyway. It's pure coincidence.");
    }

    // function for ending the game

    public boolean shouldTransition() {
        switch (currentDialogue) {
            case 4:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 22:
            case 23:
            case 25:
            case 28:
                return true;
            default: return false;
        }
    }

    public boolean isCanvasLocked() {
        return isCanvasLocked;
    }

    public String getDialogue() {
        return this.dialogues.get(this.currentDialogue);
    }



    public void setDialogue(int index) {
        this.currentDialogue = index;
    }



    public String getOption() {
        return this.choice.get(this.currentDialogue++);
    }
}
