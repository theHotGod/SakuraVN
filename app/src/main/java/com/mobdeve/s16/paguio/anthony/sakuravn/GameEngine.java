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
        if (currentIndex >= 0 && currentIndex <= dialogues.size()) {
            currentDialogue = currentIndex;

        }
    }

    // check if the current dialogue is the last dialogue
    public boolean checkIfLastDialogue(int currentIndex) {
        if (currentIndex == dialogues.size()) {
            return true;
        }
        return false;
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
        dialogues.add("Alright. Have you ever had a moment in life wherein your senses failed you? Something you were so sure about but it just wasn't so?");
        dialogues.add("I think I know what you mean. I've had a couple of those moments.");
        dialogues.add("You're so sure that you're talking to me, but you're actually not. You're just talking to yourself. Because the world is just a mere reflection of self.");
        dialogues.add("I don't understand. What do you mean?");
        dialogues.add("I'm saying that you're not talking to me. You're talking to yourself. I trust that you and others will know of this eventually to exit the wheel of recurrence.");
        dialogues.add("No. This can't be true. This is all fake.");
        dialogues.add("I know what you're saying. Besides, I'm just a mirror who's eventually going to mirror out your expressions in life.");

        dialogues.add("How can that be? And, how can you be so sure of all of this?!?");

        // second set of dialogues
        dialogues.add("I told you about the senses and how sometimes they fail to live up your expectations, correct? Stay calm. I know this is a lot to take in, but you'll soon understand.");
        dialogues.add("I'm going to need an example for me to understand this at any moment now.");
        dialogues.add("Here, let me just explain it this way. Remember what Seneca said about we often suffer in imagination more than in reality?");

        dialogues.add("Yes. I remember that. I heard it in class earlier.");

        // third set of dialogues 19
        dialogues.add("I'm glad you were listening. Now, let me ask you this. What does Seneca mean by that?");
        dialogues.add("I don't know. But if I were to guess, we shouldn't belittle our imagination?");
        dialogues.add("That's a good guess. But I think it's more than that. I think it's about how we should ourselves more with care from within.");
        dialogues.add("We often imagine ourselves in negative scenarios, affirming its realness. But whenever it's positive, we don't credit it reality. See the pattern?");
        dialogues.add("We often disregard how our imagination can be a powerful tool. We often use it to our disadvantage, but we can use it to our advantage as well.");
        dialogues.add("I think I understand now. But how does this all relate to the world being a mere reflection of self?");
        dialogues.add("I'm glad you asked. You see, the world is just a mere reflection of self. It is a reflection of how we see ourselves.");
        dialogues.add("I am overwhelmed by this information. I don't know what to do with this.");
        dialogues.add("The cherry blossoms then instruct me to do something with this principle. I am shocked by the results.");
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

    public void setCanvasLocked(boolean isCanvasLocked) {
        this.isCanvasLocked = isCanvasLocked;
    }



    public String getOption() {
        return this.choice.get(this.currentDialogue++);
    }
}
