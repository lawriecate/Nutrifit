package com.lawriecate.apps.nutrifit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lawrie on 18/11/2016.
 */

public class FoodChallenge implements Challenge, Parcelable {
    private String name;
    private String description;
    private int level;
    private String image;
    private Boolean completed;
    private int id;
    public FoodChallenge(int id,String name, String description, int level, String image, boolean completed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.image = image;
        this.completed = completed;
    }

    @Override
    public String getImage() {
        return image;
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

        out.writeInt(this.completed==true ? 1 : 0);

    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<FoodChallenge> CREATOR = new Parcelable.Creator<FoodChallenge>() {
        public FoodChallenge createFromParcel(Parcel in) {
            return new FoodChallenge(in);
        }

        public FoodChallenge[] newArray(int size) {
            return new FoodChallenge[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private FoodChallenge(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.level = in.readInt();
        this.image = in.readString();

        this.completed = in.readInt()==1;

    }

}
