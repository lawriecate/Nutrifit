package com.lawriecate.apps.nutrifit;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MenuVideos extends ListActivity {

    //Método para crear configuraciones iniciales
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_menu_videos);  //Plantilla activity_menu_videos
        ArrayList<Video> videos = new ArrayList<Video>();

        String jsonFile = loadJsonFile("videos.json");

        try {

            JSONObject obj = new JSONObject(jsonFile);
            JSONArray locations = obj.getJSONArray("Videos");

            for (int i = 0; i < locations.length(); i++) {
                JSONObject c = locations.getJSONObject(i);


                String videoName = "Challenge " + (i+1) + " " + c.getString("VideoName");
                String idvideo = c.getString("IDVideo");
                videos.add(new Video(videoName, idvideo));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        VideoAdaptacion videoAdaptacion = new VideoAdaptacion(this,
                R.layout.activity_video,videos);
        setListAdapter(videoAdaptacion);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent it = new Intent(this, RetoFisico.class);
        Video vi = (Video) getListAdapter().getItem(position);
        it.putExtra("video", vi);
        startActivity(it);
    }

    public String loadJsonFile(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
