package com.lawriecate.apps.nutrifit;

import java.io.Serializable;

public class Video implements Serializable {

        public String videoName, id_video;

        public Video(String videoName, String id_video){
            this.videoName = videoName;
            this.id_video = id_video;
        }
    }
