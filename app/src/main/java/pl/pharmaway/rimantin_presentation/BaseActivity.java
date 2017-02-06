package pl.pharmaway.rimantin_presentation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import pl.pharmaway.rimantin_presentation.view.AngleFrameLayout;

public class BaseActivity extends AppCompatActivity implements SensorEventListener {

    private static boolean FAKE = true;
    private Sensor senAccelerometer;
    SensorManager senSensorManager;

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        initSensor();
    }

    private void initSensor() {
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onPause() {
        super.onPause();

        if(FAKE) {
//            timer.cancel();
        } else {
            senSensorManager.unregisterListener(this);
        }
    }
    //Timer timer;
    protected void onResume() {
        super.onResume();

        if(FAKE) {
//            timer = new Timer();
//            timer.scheduleAtFixedRate( new TimerTask() {
//
//                @Override
//                public void run() {
//                    float length= 1000;
//                    long t = System.currentTimeMillis() % ((long)(20 * length*Math.PI));
//                    final double x = ((float)t)/length;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            onAngleChanged((float) (Math.sin(x)));
//                        }
//                    });
//
//                }
//            } , 250, 33 );
        } else {
            senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            onAccelerometerChanged(x,y,z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    float first = Float.NaN;
    private void onAccelerometerChanged(float x, float y, float z) {
        boolean skip = first == Float.NaN;
        first = y;
        if(skip) {
            return;
        }
        float v = (first+y)*0.5f;
        first = v;
        float MAX = -4;
        v = v/MAX;
        if(v>1)v=1;
        if(v<-1)v=-1;

        onAngleChanged(v);
    }

    private void onAngleChanged(float v) {
        for (AngleChangedListener mAngleChangedListener : mAngleChangedListeners) {
            mAngleChangedListener.onAngleChanged(v);
        }
    }

    List<AngleChangedListener> mAngleChangedListeners = new ArrayList<>();

    public void register(AngleChangedListener angleChangedListener) {
        mAngleChangedListeners.add(angleChangedListener);
    }

    public void unregister(AngleChangedListener angleChangedListener) {
        mAngleChangedListeners.remove(angleChangedListener);
    }

    public interface AngleChangedListener {
        void onAngleChanged(float a);
    }

}
