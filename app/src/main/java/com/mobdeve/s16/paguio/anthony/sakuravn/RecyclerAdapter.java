package com.mobdeve.s16.paguio.anthony.sakuravn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter {

    ArrayList<Dialogue> listOfDialogue;

    public RecyclerAdapter(ArrayList<Dialogue> listOfDialogue){
        this.listOfDialogue = listOfDialogue;
    }

    public class ListLayoutHolder extends RecyclerView.ViewHolder {
        TextView speakerTxt;
        TextView contentTxt;

        public ListLayoutHolder(@NonNull View v) {
            super(v);
            speakerTxt = v.findViewById(R.id.speakerTxt);
            contentTxt = v.findViewById(R.id.contentTxt);
        }

        private void setViews(String title, String content) {
            speakerTxt.setText(title);
            contentTxt.setText(content);
        }
    }

    @Override
    public ListLayoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ListLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_backread_list, parent, false);
        return new ListLayoutHolder(ListLayout);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        listOfNotes.get(position).getViewType();
        String speakerTxt = listOfDialogue.get(position).getSpeakerTxt();
        String contentTxt = listOfDialogue.get(position).getContentTxt();
        ((ListLayoutHolder)holder).setViews(speakerTxt, contentTxt);
    }

    @Override
    public int getItemCount() {
        return listOfDialogue.size();
    }

}
