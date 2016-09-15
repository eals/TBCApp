package com.azwraith.apps.tbcapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 7/18/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<String> notes = new ArrayList<String>();

    public CardAdapter(List<String> notesList) {
        super();
        notes = notesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String note = notes.get(i);
        viewHolder.noteView.setText(note);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteView;

        public ViewHolder(View itemView) {
            super(itemView);
            noteView = (TextView)itemView.findViewById(R.id.noteText);


        }




    }
}
