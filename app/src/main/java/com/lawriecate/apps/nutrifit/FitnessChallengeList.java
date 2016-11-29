package com.lawriecate.apps.nutrifit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FitnessChallengeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_challenge_list);

        ProgressService progressService = new ProgressService(this);
        DatabaseHandler db = new DatabaseHandler(this);

        final ArrayList<FitnessChallenge> fitnessChallengesArrayList = (ArrayList) db.getFitnessChallengeByLevel(progressService.getLevel());

        FitnessChallengeAdapter adapter = new FitnessChallengeAdapter(this,fitnessChallengesArrayList);
        ListView  mListView = (ListView) findViewById(R.id.fitness_challenge_list_view);
        mListView.setAdapter(adapter);
        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FitnessChallenge selectedChallenge = fitnessChallengesArrayList.get(position);

                Intent detailIntent = new Intent(context, SingleFitnessActivity.class);
                detailIntent.putExtra("challenge", selectedChallenge);
                startActivity(detailIntent);
            }

        });


    }
}
