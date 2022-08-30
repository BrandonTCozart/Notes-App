package com.example.weather;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class noteAdapter extends BaseAdapter {

    Context mactivity;
    List<noteClass> notes;

    public noteAdapter(Context mactivity, List<noteClass> notes) {
        this.mactivity = mactivity;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        LayoutInflater inflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.adapter_view_layout, null, false);

        TextView noteName = v.findViewById(R.id.textViewNoteName);
        TextView noteDate = v.findViewById(R.id.textViewNoteDate);

        noteClass note = (noteClass) this.getItem(i);

        noteName.setText(note.getNoteTitle());
        noteDate.setText(note.getNoteCreationDate());
        return v;
    }
}
