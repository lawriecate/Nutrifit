package com.lawriecate.apps.nutrifit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lawrie on 18/11/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "challengeManager";

    private static final String TABLE_FITNESS_CHALLENGES = "fitness_challenges";
    private static final String TABLE_FOOD_CHALLENGES = "food_challenges";
    private static final String TABLE_SETTINGS = "user_settings";

    // 
    /*
   
      private String name;
    private String description;
    private int level;
    private String image;
    private Boolean completed;
    private int id;*/

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_VIDEO = "video";
    private static final String KEY_PERFORM_NO = "perform_no";
    private static final String KEY_COMPLETED = "completed";


    private static final String KEY_POINTS = "points";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FITNESS_CHALLENGE_TABLE = "CREATE TABLE " + TABLE_FITNESS_CHALLENGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_LEVEL + " INTEGER,"
                + KEY_IMAGE + " TEXT,"
                + KEY_VIDEO + " TEXT,"
                + KEY_PERFORM_NO + " INTEGER,"
                + KEY_COMPLETED + " BOOLEAN" +
                ")";
        db.execSQL(CREATE_FITNESS_CHALLENGE_TABLE);
        String CREATE_FOOD_CHALLENGE_TABLE = "CREATE TABLE " + TABLE_FOOD_CHALLENGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_LEVEL + " INTEGER,"
                + KEY_IMAGE + " TEXT,"
                + KEY_COMPLETED + " BOOLEAN" +
                ")";
        db.execSQL(CREATE_FOOD_CHALLENGE_TABLE);


        String CREATE_USER_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + "("
                + KEY_LEVEL + " INTEGER,"
                + KEY_POINTS + " INTEGER"+
                ")";

        db.execSQL(CREATE_USER_SETTINGS_TABLE);
        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL,1);
        values.put(KEY_POINTS,0);

        // Inserting Row
       //  db = this.getWritableDatabase();
        db.insert(TABLE_SETTINGS, null, values);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FITNESS_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        // Create tables again
        onCreate(db);
    }

    public Integer getPoints()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SETTINGS, new String[] {
                        KEY_POINTS}, null, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public Integer getLevel()
    {
       // return 1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SETTINGS, new String[] {
                KEY_LEVEL}, null, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public int updatePoints(Integer points) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POINTS, points);

        // updating row

        return db.update(TABLE_SETTINGS, values, null, null);
    }

    public int updateLevel(Integer level) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL, level);

        // updating row
        return db.update(TABLE_SETTINGS, values, null, null);
    }



    // Adding new contact
    public void addFitnessChallenge(FitnessChallenge fitnessChallenge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, fitnessChallenge.getName());
        values.put(KEY_DESCRIPTION, fitnessChallenge.getDescription());
        values.put(KEY_LEVEL, fitnessChallenge.getLevel());
        values.put(KEY_IMAGE, fitnessChallenge.getImage());
        values.put(KEY_VIDEO, fitnessChallenge.getVideo());
        values.put(KEY_PERFORM_NO, fitnessChallenge.getPerform_no());
        values.put(KEY_COMPLETED, fitnessChallenge.getCompleted());

        // Inserting Row
        db.insert(TABLE_FITNESS_CHALLENGES, null, values);
        db.close(); // Closing database connection
        Log.i("DB","Challenge added " + fitnessChallenge.getName());
    }


    public FitnessChallenge getFitnessChallenge(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FITNESS_CHALLENGES, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION, KEY_LEVEL, KEY_IMAGE, KEY_VIDEO, KEY_PERFORM_NO, KEY_COMPLETED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        FitnessChallenge fitnessChallenge = new FitnessChallenge(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getInt(6),
                (cursor.getInt(7)==1)
                );

        return fitnessChallenge;
    }


    public List<FitnessChallenge> getAllFitnessChallenge() {
        List<FitnessChallenge> fitnessChallengeList = new ArrayList<FitnessChallenge>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FITNESS_CHALLENGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FitnessChallenge fitnessChallenge = new FitnessChallenge(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        (cursor.getInt(7)==1)
                );


                fitnessChallengeList.add(fitnessChallenge);
            } while (cursor.moveToNext());
        }


        return fitnessChallengeList;
    }

    public List<FitnessChallenge> getFitnessChallengeByLevel(int level) {
        List<FitnessChallenge> fitnessChallengeList = new ArrayList<FitnessChallenge>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FITNESS_CHALLENGES + " WHERE " + KEY_LEVEL + " = " + level;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FitnessChallenge fitnessChallenge = new FitnessChallenge(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        (cursor.getInt(7)==1)
                );


                fitnessChallengeList.add(fitnessChallenge);
            } while (cursor.moveToNext());
        }


        return fitnessChallengeList;
    }


    public int getFitnessChallengeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FITNESS_CHALLENGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateFitnessChallenge(FitnessChallenge fitnessChallenge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, fitnessChallenge.getName());
        values.put(KEY_DESCRIPTION, fitnessChallenge.getDescription());
        values.put(KEY_LEVEL, fitnessChallenge.getLevel());

        values.put(KEY_COMPLETED, fitnessChallenge.getCompleted());
        values.put(KEY_PERFORM_NO, fitnessChallenge.getPerform_no());
        values.put(KEY_VIDEO, fitnessChallenge.getVideo());
        values.put(KEY_IMAGE, fitnessChallenge.getImage());


        // updating row
        return db.update(TABLE_FITNESS_CHALLENGES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(fitnessChallenge.getId())});
    }

    // Adding new contact
    public void addFoodChallenge(FoodChallenge foodChallenge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, foodChallenge.getName());
        values.put(KEY_DESCRIPTION, foodChallenge.getDescription());
        values.put(KEY_LEVEL, foodChallenge.getLevel());
        values.put(KEY_IMAGE, foodChallenge.getImage());

        values.put(KEY_COMPLETED, foodChallenge.getCompleted());

        // Inserting Row
        db.insert(TABLE_FOOD_CHALLENGES, null, values);
        db.close(); // Closing database connection
        Log.i("DB","Challenge added " + foodChallenge.getName());
    }


    public FoodChallenge getFoodChallenge(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOOD_CHALLENGES, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION, KEY_LEVEL, KEY_IMAGE, KEY_VIDEO, KEY_PERFORM_NO, KEY_COMPLETED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        FoodChallenge foodChallenge = new FoodChallenge(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                (cursor.getInt(5)==1)
        );

        return foodChallenge;
    }


    public List<FoodChallenge> getAllFoodChallenge() {
        List<FoodChallenge> foodChallengeList = new ArrayList<FoodChallenge>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD_CHALLENGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FoodChallenge foodChallenge = new FoodChallenge(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),

                        (cursor.getInt(5)==1)
                );


                foodChallengeList.add(foodChallenge);
            } while (cursor.moveToNext());
        }


        return foodChallengeList;
    }

    public List<FoodChallenge> getFoodChallengeByLevel(int level) {
        List<FoodChallenge> foodChallengeList = new ArrayList<FoodChallenge>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD_CHALLENGES + " WHERE " + KEY_LEVEL + " = " + level;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FoodChallenge foodChallenge = new FoodChallenge(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),

                        (cursor.getInt(5)==1)
                );


                foodChallengeList.add(foodChallenge);
            } while (cursor.moveToNext());
        }


        return foodChallengeList;
    }


    public int getFoodChallengeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FOOD_CHALLENGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateFoodChallenge(FoodChallenge foodChallenge) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, foodChallenge.getName());
        values.put(KEY_DESCRIPTION, foodChallenge.getDescription());
        values.put(KEY_LEVEL, foodChallenge.getLevel());

        values.put(KEY_COMPLETED, foodChallenge.getCompleted());

        values.put(KEY_IMAGE, foodChallenge.getImage());


        // updating row
        return db.update(TABLE_FOOD_CHALLENGES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(foodChallenge.getId())});
    }


    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FITNESS_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        // Create tables again
        onCreate(db);
        db.close();
    }

}
