package com.lawriecate.apps.nutrifit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by lawrie on 18/11/2016.
 */

public class ProgressService {
    private int points;
    private int level;
    private DatabaseHandler db;
    private Context context;

    public ProgressService(Context _context)
    {
        context = _context;

    }

    public Integer getLevel()
    {
        DatabaseHandler db = new DatabaseHandler(context);
        return db.getLevel();
    }

    public Integer getPoints()
    {
        DatabaseHandler db = new DatabaseHandler(context);
        return db.getPoints();
    }

    public void addPoints(int add) {
        DatabaseHandler db = new DatabaseHandler(context);
        db.updatePoints((db.getPoints()+add));
    }

    public void setLevel(int level) {
        DatabaseHandler db = new DatabaseHandler(context);
        db.updateLevel(level);
    }

   /* public FitnessChallenge getNextFitnessChallenge()
    {
        //FitnessChallenge fitnessChallenge = new FitnessChallenge;
        //return fitnessChallenge;
    }

    public FoodChallenge getNextFoodChallenge()
    {
       // FoodChallenge foodChallenge = new FoodChallenge;
        //return foodChallenge;
*/
    public void loadChallenges()
    {
        DatabaseHandler db = new DatabaseHandler(context);
        db.deleteAll();

        final ArrayList<FitnessChallenge> fitnessChallengeArrayList = new ArrayList<>();

        try {
            // Load data
            String jsonString = readFile("retos_fisicos_es.json");
            JSONObject json = new JSONObject(jsonString);
            JSONArray challenges = json.getJSONArray("challenges");

            // Get Recipe objects from data
            for(int i = 0; i < challenges.length(); i++){
                Log.d("JSON",challenges.getJSONObject(i).getString("name"));

                FitnessChallenge fitnessChallenge = new FitnessChallenge(
                        0,
                        challenges.getJSONObject(i).getString("name"),
                        challenges.getJSONObject(i).getString("description"),
                        challenges.getJSONObject(i).getInt("level"),
                        challenges.getJSONObject(i).getString("image"),
                        challenges.getJSONObject(i).getString("video"),
                        challenges.getJSONObject(i).getInt("perform_no"),
                        false
                        );

                fitnessChallengeArrayList.add(fitnessChallenge);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < fitnessChallengeArrayList.size(); i++) {
            db.addFitnessChallenge(fitnessChallengeArrayList.get(i));
        }

    }

    private String readFile(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
