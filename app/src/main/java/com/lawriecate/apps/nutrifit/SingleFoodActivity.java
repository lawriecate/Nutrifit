package com.lawriecate.apps.nutrifit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.squareup.picasso.Picasso;

public class SingleFoodActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FoodChallenge foodChallenge = this.getIntent().getExtras().getParcelable("challenge");

        TextView txtTitle = (TextView)findViewById(R.id.text_food_activity_title);
        txtTitle.setText(foodChallenge.getName());
        TextView txtDescription = (TextView)findViewById(R.id.text_food_activity_description);
        txtDescription.setText(foodChallenge.getDescription());

        final Switch switchCompleted = (Switch)findViewById( R.id.switch_food_activity_completed);
        switchCompleted.setChecked(foodChallenge.getCompleted());

        switchCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(view.getContext());
                foodChallenge.setCompleted(switchCompleted.isChecked());
                db.updateFoodChallenge(foodChallenge);
                if(switchCompleted.isChecked())
                {
                    ProgressService progressService = new ProgressService(view.getContext());
                    progressService.addPoints(100);
                }
            }
        });

        ImageView thumbnailImageView =
                (ImageView) findViewById(R.id.image_food_activity);

        Picasso.with(this).load(foodChallenge.getImage()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);


    }

}
