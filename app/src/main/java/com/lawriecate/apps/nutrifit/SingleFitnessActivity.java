package com.lawriecate.apps.nutrifit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class SingleFitnessActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    private String API_KEY
            = "AIzaSyDPGVtlnWHSikl4fQHSJPJBtk_tn6kXD7Y";
    private YouTubePlayerFragment fragment;
    private String youtube_video_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fitness);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        fragment  = (YouTubePlayerFragment)
                getFragmentManager()
                        .findFragmentById(R.id.yt_fitness_activity_video);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final FitnessChallenge fitnessChallenge = this.getIntent().getExtras().getParcelable("challenge");
        youtube_video_id = fitnessChallenge.getVideo();
        TextView txtTitle = (TextView)findViewById(R.id.text_fitness_activity_title);
        txtTitle.setText(fitnessChallenge.getName());
        TextView txtDescription = (TextView)findViewById(R.id.text_fitness_activity_description);
        txtDescription.setText(fitnessChallenge.getDescription());

        final Switch switchCompleted = (Switch)findViewById( R.id.switch_fitness_activity_completed);
        switchCompleted.setChecked(fitnessChallenge.getCompleted());

        switchCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(view.getContext());
                fitnessChallenge.setCompleted(switchCompleted.isChecked());
                db.updateFitnessChallenge(fitnessChallenge);
                if(switchCompleted.isChecked())
                {
                    ProgressService progressService = new ProgressService(view.getContext());
                    progressService.addPoints(100);
                }
            }
        });

        fragment.initialize(API_KEY, this);
    }


    @Override
    public void onInitializationSuccess(
            YouTubePlayer.Provider provider,
            YouTubePlayer youTubePlayer, boolean b) {

        if(!b){
            youTubePlayer.cueVideo(youtube_video_id);
            //youTubePlayer.play();
           // youTubePlayer.setFullscreen(true);
        }else {
            //youTubePlayer.play();
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
