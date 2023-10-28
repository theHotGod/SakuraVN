package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoTalkingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoTalkingFragment extends Fragment {

    public NoTalkingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.no_talking, container, false);
    }
}