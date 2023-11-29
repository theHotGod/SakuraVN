package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActionBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionBar extends Fragment {
    private ImageButton homeBtn;
    private ImageButton dialogueBtn;
    private ImageButton fastBtn;
    private ImageButton autoBtn;

    public ActionBar() {
        // Required empty public constructor
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

        // Log the ImageButton references
        Log.d("ActionBar", "homeBtn: " + homeBtn);
        Log.d("ActionBar", "dialogueBtn: " + dialogueBtn);
        Log.d("ActionBar", "fastBtn: " + fastBtn);
        Log.d("ActionBar", "autoBtn: " + autoBtn);

        return view;
    }
}