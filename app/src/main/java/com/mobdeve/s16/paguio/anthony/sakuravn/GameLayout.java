package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GameLayout extends RelativeLayout {

    private GameView gameView;
    private ImageView imageView;
    SurfaceHolder surfaceHolder;
    GameEngine gameEngine;

    public GameLayout(Context context) {
        super(context);
        init(context);
    }

    public GameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // Create GameView
        gameView = new GameView(context);
        gameEngine = new GameEngine();

        FrameLayout fragmentContainer = new FrameLayout(context);
        RelativeLayout.LayoutParams fragmentParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        fragmentContainer.setId(View.generateViewId());
        addView(fragmentContainer, fragmentParams);

        View fragmentView = inflate(context, R.layout.actionbar_dialogue_fragment, fragmentContainer);

        FrameLayout actionBarContainer = fragmentView.findViewById(R.id.actionBarContainer);
        FrameLayout dialogueFragmentContainer = fragmentView.findViewById(R.id.dialogueFragmentContainer);
        FrameLayout innerDialogueFragmentContainer = fragmentView.findViewById(R.id.innerDialogueFragmentContainer);

// creating the actionbar and dialogue fragment
        ActionBar actionBar = new ActionBar();
        DialogueFragment dialogueFragment = new DialogueFragment();
        InnerDialogueFragment innerDialogueFragment = new InnerDialogueFragment();
        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(actionBarContainer.getId(), actionBar)
                .replace(dialogueFragmentContainer.getId(), dialogueFragment)
                .replace(innerDialogueFragmentContainer.getId(), innerDialogueFragment);
        fragmentTransaction.commit();
    }


}