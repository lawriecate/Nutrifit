package com.lawriecate.apps.nutrifit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FoodChallengeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_challenge_list);

        ProgressService progressService = new ProgressService(this);
        DatabaseHandler db = new DatabaseHandler(this);

        final ArrayList<FoodChallenge> foodChallengesArrayList = (ArrayList) db.getFoodChallengeByLevel(progressService.getLevel());

        FoodChallengeAdapter adapter = new FoodChallengeAdapter(this,foodChallengesArrayList);
        ListView  mListView = (ListView) findViewById(R.id.food_challenge_list_view);
        mListView.setAdapter(adapter);
        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FoodChallenge selectedChallenge = foodChallengesArrayList.get(position);

                Intent detailIntent = new Intent(context, SingleFoodActivity.class);
                detailIntent.putExtra("challenge", selectedChallenge);
                startActivity(detailIntent);
            }

        });
    }
}
