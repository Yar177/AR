package com.yar.ar;

import android.hardware.Camera;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inPreview = false;

        surfaceView = (SurfaceView) findViewById(R.id.cameraPreview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceCallback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);





    }


    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();
    }



    @Override
    protected void onStop() {
        if (inPreview){
            camera.stopPreview();
        }
        camera.release();
        camera = null;
        inPreview = false;
        super.onStop();

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
