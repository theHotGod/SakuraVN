package com.mobdeve.s16.paguio.anthony.sakuravn;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BackreadFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    public BackreadFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_backread, container, false);

        ArrayList<Dialogue> dataList = DialogueGet.getDataList();

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize Adapter and set it to RecyclerView
        recyclerAdapter = new RecyclerAdapter(dataList);
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

}
