package com.lawriecate.apps.nutrifit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SugarCalculatorActivity extends AppCompatActivity {
    public Integer dailySugarTotal = 0;
    public Double sugarIntake = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar_calculator);

        TextView txtStatus = (TextView) findViewById(R.id.scStatus);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String heightSetting = prefs.getString("height","180");
        Double height = Double.parseDouble(heightSetting) / 100;
        String weightSetting = prefs.getString("weight","75");
        Double weight =Double.parseDouble(weightSetting);
        Double bmi = weight / (height*height);
        txtStatus.setText("BMI: " +  String.format( "%.2f", bmi ) );

         sugarIntake = (height*height) * bmi * 30 * 0.05;
        TextView scTotal = (TextView) findViewById(R.id.scTotal);
        scTotal.setText("0g / " + sugarIntake + "g");

        Button addButton = (Button) findViewById(R.id.sc_add_btton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem("Coke",50);
            }
        });
    }

    public void addItem(String title, Integer sugar) {
        dailySugarTotal+=sugar;
        TextView scTotal = (TextView) findViewById(R.id.scTotal);
        scTotal.setText(dailySugarTotal + "g / " + sugarIntake + "g");
        if(dailySugarTotal > sugarIntake) {
            scTotal.setTextColor(Color.RED);
            createGreatNotification();
        }

    }


    public void createGreatNotification(){
        Intent intent = new Intent(this, SugarCalculatorActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                intent, 0);

        Notification notification = new Notification.Builder(this).setContentTitle(getResources().getString(R.string.notification_over_sugar_limit_title))
                .setContentText(getResources().getString(R.string.notification_over_sugar_limit)).setSmallIcon(R.mipmap.ic_launcher)
                .addAction(R.mipmap.ic_launcher, getResources().getString(R.string.notification_over_sugar_limit_action), pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

    }


}
