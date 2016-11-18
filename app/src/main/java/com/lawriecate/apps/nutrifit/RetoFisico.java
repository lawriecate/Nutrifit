package com.lawriecate.apps.nutrifit;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;


public class RetoFisico extends YouTubeBaseActivity
            implements YouTubePlayer.OnInitializedListener{

    //Esta es la clave API que Alan obtuvo: AIzaSyDPGVtlnWHSikl4fQHSJPJBtk_tn6kXD7Y
    //Esta es la clave API que Bárbara obtuvo: AIzaSyBl-hy5XCGztqIT67TgemvqoG98ih7-gAs

        private String API_KEY
                = "AIzaSyDPGVtlnWHSikl4fQHSJPJBtk_tn6kXD7Y";
        private String idyoutube = "p0nQWTlTQf0";
        private YouTubePlayerFragment fragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reto_fisico);
            fragment  = (YouTubePlayerFragment)
                    getFragmentManager()
                            .findFragmentById(R.id.fragment);
            Video v = (Video) getIntent()
                    .getSerializableExtra("video");
            idyoutube = v.id_video;
            fragment.initialize(API_KEY, this);
        }

        @Override
        public void onInitializationSuccess(
                YouTubePlayer.Provider provider,
                YouTubePlayer youTubePlayer, boolean b) {

            if(!b){
                youTubePlayer.cueVideo(idyoutube);
                youTubePlayer.play();
                youTubePlayer.setFullscreen(true);
            }else {
                youTubePlayer.play();
            }

        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        }
    }
