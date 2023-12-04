package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class choiceFragment extends Fragment {

    public choiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GameEngine gameEngine = new GameEngine();

        View view = inflater.inflate(R.layout.fragment_choice, container, false);

        Button option1 = view.findViewById(R.id.option1);
        Button option2 = view.findViewById(R.id.option2);

        option1.setText(gameEngine.getOption());
        option2.setText(gameEngine.getOption());

        view.setVisibility(View.GONE);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodEnding.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BadEnding.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}