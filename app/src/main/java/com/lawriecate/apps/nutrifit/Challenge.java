package com.lawriecate.apps.nutrifit;

/**
 * Created by lawrie on 18/11/2016.
 */

public interface Challenge {
    public String getName();
    public int getLevel();
    public String getDescription();
    public String getImage();
    public Boolean getCompleted();
    public int getId();
}
