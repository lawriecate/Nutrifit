package com.lawriecate.apps.nutrifit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class VideoAdaptacion extends ArrayAdapter<Video> {

    public VideoAdaptacion(Context ct,int layout, ArrayList<Video> videos){
        super(ct, layout, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video v = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_video,
                            parent, false);
        }
        TextView textView = (TextView)
                convertView.findViewById(R.id.textView);
        TextView textView02 = (TextView)
                convertView.findViewById(R.id.textView2);
        textView.setText(v.id_video);
        textView02.setText(v.videoName);
        return convertView;
    }
}
