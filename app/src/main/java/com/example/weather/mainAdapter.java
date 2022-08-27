package com.example.weather;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class mainAdapter extends BaseAdapter {
    Context context;
    String noteName;
    Image image;
    LayoutInflater inflater;

    public mainAdapter(Context context, String noteName) {
        this.context = context;
        this.noteName = noteName;
    }

    public mainAdapter(Context context, String noteName, Image image) {
        this.context = context;
        this.noteName = noteName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
            view = inflater.inflate(R.layout.gridviewlayout, null);
        }

        ImageView imageView = view.findViewById(R.id.grid_image);
        TextView textview = view.findViewById(R.id.note_name);

        imageView.setImageResource(i);
        textview.setText(noteName);

        return view;
    }
}
