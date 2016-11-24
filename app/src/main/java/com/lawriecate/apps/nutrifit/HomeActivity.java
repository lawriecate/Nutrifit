package com.lawriecate.apps.nutrifit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ProgressService progressService = new ProgressService(this);


        new AlertDialog.Builder(this)
                .setTitle("Testing")
                .setMessage("Do you want to reset all challenges?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {

                        Dialog dialog  = (Dialog) dialogInterface;
                        Context context = dialog.getContext();
                        progressService.loadChallenges();
                        updatePoints();
                        Toast.makeText(HomeActivity.this, "Challenges Reset", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

       /* Button faqButton = (Button) findViewById(R.id.faq_button);
        faqButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(HomeActivity.this, Preguntas.class);
                HomeActivity.this.startActivity(mainIntent);
            }
        });*/


        Button foodChallengeButton = (Button) findViewById(R.id.food_challenge_button);
        foodChallengeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(HomeActivity.this, FoodChallengeList.class);
                HomeActivity.this.startActivity(mainIntent);
            }
        });

        Button fitChallengeButton = (Button) findViewById(R.id.fitness_challenge_button);
        fitChallengeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(HomeActivity.this, FitnessChallengeList.class);
                HomeActivity.this.startActivity(mainIntent);
            }
        });

        Button rankingButton = (Button) findViewById(R.id.ranking_button);
        rankingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(HomeActivity.this, SocialActivity.class);
                HomeActivity.this.startActivity(mainIntent);
            }
        });

        Button signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent mainIntent = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivity(mainIntent);
                HomeActivity.this.finish();
            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.levelSeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressService.setLevel(i+1);
                updatePoints();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });


       updatePoints();

    }

    private void updatePoints()
    {
        TextView level_text = (TextView) findViewById(R.id.level_text);
        TextView points_text = (TextView) findViewById(R.id.text_points);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        level_text.setText("Level " + databaseHandler.getLevel().toString());
        points_text.setText(databaseHandler.getPoints().toString() + " points");
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
    updatePoints();
    }

}
