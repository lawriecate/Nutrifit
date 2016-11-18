package com.lawriecate.apps.nutrifit;

/**
 * Created by lawrie on 18/11/2016.
 */

public class FoodChallenge implements Challenge {
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

}
