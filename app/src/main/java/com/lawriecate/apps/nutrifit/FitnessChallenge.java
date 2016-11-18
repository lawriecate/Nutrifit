package com.lawriecate.apps.nutrifit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by lawrie on 18/11/2016.
 */

public class FitnessChallenge implements Challenge, Parcelable {
    private String name;
    private String description;
    private int level;
    private String image;
    private String video;
    private int perform_no;
    private Boolean completed;
    private int id;
    public FitnessChallenge(int id,String name, String description, int level, String image, String video, int perform_no, boolean completed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.image = image;
        this.video = video;
        this.perform_no = perform_no;
        this.completed = completed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public String getImage() {
        return image;
    }

    public String getVideo() {
        return video;
    }

    public int getPerform_no() {
        return perform_no;
    }

    @Override
    public Boolean getCompleted() { return  completed; }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.id);
        out.writeString(this.name);
        out.writeString(this.description);
        out.writeInt(this.level);
        out.writeString(this.image);
        out.writeString(this.video);
        out.writeInt(this.perform_no);
        out.writeInt(this.completed==true ? 1 : 0);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<FitnessChallenge> CREATOR = new Parcelable.Creator<FitnessChallenge>() {
        public FitnessChallenge createFromParcel(Parcel in) {
            return new FitnessChallenge(in);
        }

        public FitnessChallenge[] newArray(int size) {
            return new FitnessChallenge[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private FitnessChallenge(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.level = in.readInt();
        this.image = in.readString();
        this.video = in.readString();
        this.perform_no = in.readInt();
        this.completed = in.readInt()==1;

    }
}
