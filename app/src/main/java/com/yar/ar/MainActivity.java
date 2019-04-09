package com.yar.ar;

import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Camera camera;
    boolean inPreview;

    SensorManager sensorManager;
    int orientationSensor;
    float headingAngel;
    float pitchAngel;
    float rollAngel;

    int accelerometerSensor;
    float xAxis;
    float yAxis;
    float zAxis;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inPreview = false;

        surfaceView = (SurfaceView) findViewById(R.id.cameraPreview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceCallback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        orientationSensor = Sensor.TYPE_ORIENTATION;
        accelerometerSensor = Sensor.TYPE_ACCELEROMETER;




    }



    final SensorEventListener sensorEvenetListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION){
                headingAngel = event.values[0];
                pitchAngel = event.values[1];
                rollAngel = event.values[2];

//                Log.d(TAG, "headingAngel: " + String.valueOf(headingAngel));
//                Log.d(TAG, "pitchAngel: " + String.valueOf(pitchAngel));
//                Log.d(TAG, "rollAngel: " + String.valueOf(rollAngel));

            }else  if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                xAxis = event.values[0];
                yAxis = event.values[1];
                zAxis = event.values[2];
                Log.d(TAG, "xAxis: " + xAxis);
                Log.d(TAG, "yAxis: " + yAxis);
                Log.d(TAG, "zAxis: " + zAxis);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();

        sensorManager.registerListener(sensorEvenetListener, sensorManager.getDefaultSensor(orientationSensor),
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(sensorEvenetListener, sensorManager.getDefaultSensor(accelerometerSensor),
                SensorManager.SENSOR_DELAY_NORMAL);
    };



    @Override
    protected void onPause() {

        sensorManager.unregisterListener(sensorEvenetListener);

        if (inPreview){
            camera.stopPreview();
        }
        camera.release();
        camera = null;
        inPreview = false;
        super.onPause();

    }



    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "surfaceCreated: ", e.getCause());
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = getBestPreviewSize(width, height, parameters);

            if (size!=null){
                parameters.setPreviewSize(size.width, size.height);
                camera.setParameters(parameters);
                camera.startPreview();
                inPreview = true;
            }

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size results = null;

        for (Camera.Size size: parameters.getSupportedPreviewSizes()){
            if (size.width<=width && size.height<=height){
                if (results == null) {
                    results = size;
                }else {
                    int resultArea = results.width*results.height;
                    int newArea = size.width*size.height;
                    if (newArea>resultArea){
                        results = size;
                    }
                }


            }
        }


        return results;
    }


}
