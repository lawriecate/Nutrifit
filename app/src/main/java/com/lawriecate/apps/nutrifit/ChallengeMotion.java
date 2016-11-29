package com.lawriecate.apps.nutrifit;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChallengeMotion extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean init;
    private static final float ERROR = (float) 7.0;
    private float x1, x2, x3;
    float diffX, diffY, diffZ;
    private Integer timesLeft;
    long lastUpdate = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_motion);

        final FitnessChallenge fitnessChallenge = this.getIntent().getExtras().getParcelable("challenge");
        timesLeft = fitnessChallenge.getPerform_no();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);   //Crear instancia para Manejador de sensores
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  //Crear instancia para acelerÛmetro

        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_GAME);
    }

    public void onShake() {
        if(timesLeft > 0) {
            timesLeft--;
            TextView statusTxt = (TextView) findViewById(R.id.text_times_left);
            statusTxt.setText(timesLeft.toString());
        }
        else {
            //completed
            final FitnessChallenge fitnessChallenge = this.getIntent().getExtras().getParcelable("challenge");
            DatabaseHandler db = new DatabaseHandler(this);
            fitnessChallenge.setCompleted(true);
            db.updateFitnessChallenge(fitnessChallenge);

            ProgressService progressService = new ProgressService(this);
            progressService.addPoints(100);
            TextView statusTxt = (TextView) findViewById(R.id.text_times_left);
            statusTxt.setText("completed");
            statusTxt.setTextSize(16);
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {    //Cada que valor de sensor sea modificado
        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > 500) { //No permite shakes menores a 100 ms
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            float[] data = event.values;
            float x = data[0];  //Obtener valores en eje X
            float y = data[1];  //Obtener valores en eje Y
            float z = data[2];  //Obtener vaores en eje Z

            if (!init) { //Si variable init es falsa
                x1 = x; //x1 toma el valor de x
                x2 = y; //x2 toma el valor de y
                x3 = z; //x3 toma el valor de z
                init = true;    //Variable init es verdadera
            } else {
                diffX = Math.abs(x1 - x);   //Diferencia entre mediciÛn inmediatamente anterior de x y x actual
                diffY = Math.abs(x2 - y);   //Diferencia entre mediciÛn inmediatamente anterior de y y y actual
                diffZ = Math.abs(x3 - z);   //Diferencia entre mediciÛn inmediatamente anterior de z y z actual

                //Handling ACCELEROMETER Noise
                if (diffX < ERROR) {    //Si diferencia en valores de eje X es menor a 7
                    diffX = (float) 0.0;    //diffX es cero
                }
                if (diffY < ERROR) {        //Si diferencia en valores de eje Y es menor a 7
                    diffY = (float) 0.0;    //diffY es cero
                }
                if (diffZ < ERROR) {        //Si diferencia en valores de eje Z es menor a 7
                    diffZ = (float) 0.0;    //diffZ es cero
                }

                x1 = x;     //Almacenar valor actual de x en x1
                x2 = y;     //Almacenar valor actual de y en x2
                x3 = z;     //Almacenar valor actual de z en x3

                //Horizontal Shake Detected!
                if (diffX > diffY && diffX > diffZ) {
                  onShake();
                } else if (diffY > diffX && diffY > diffZ) {
                   onShake();
                } else if (diffZ > diffX && diffZ > diffY) {
                   onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
}
