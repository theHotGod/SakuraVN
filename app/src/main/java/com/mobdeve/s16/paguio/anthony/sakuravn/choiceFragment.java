package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class choiceFragment extends Fragment {

    private Button option1, option2;
    private View view;

    public choiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GameEngine gameEngine = new GameEngine();

        view = inflater.inflate(R.layout.fragment_choice, container, false);

        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);

        option1.setText(gameEngine.getOption());
        option2.setText(gameEngine.getOption());

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodEnding.class);
                startActivity(intent);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BadEnding.class);
                startActivity(intent);
            }
        });

        return view;
    }

}