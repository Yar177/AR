package com.yar.ar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;
import edu.dhbw.andar.pub.CustomObject;
import edu.dhbw.andar.pub.CustomRenderer;

public class MarkerActivity extends AndARActivity {

    private static final String TAG = "MarkerActivity";


    private ARObject arObject;
    private ARToolkit arToolkit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomRenderer renderer = new CustomRenderer();
        setNonARRenderer(renderer);

        try {

        arToolkit = getArtoolkit();

        arObject = new CustomObject1
                ("test", "marker_at16.patt", 80.0, new double[]{0,0});

            arToolkit.registerARObject(arObject);


        arObject = new CustomObject2
                ("test", "marker_peace16.patt", 80.0, new double[]{0,0});
        arToolkit.registerARObject(arObject);

        arObject = new CustomObject3
                ("test", "marker_rupee16.patt", 80.0, new double[]{0,0});
        arToolkit.registerARObject(arObject);

        arObject = new CustomObject4
                ("test", "marker_hand16.patt", 80.0, new double[]{0,0});
        arToolkit.registerARObject(arObject);

        } catch (AndARException e) {
            e.printStackTrace();
        }

        startPreview();

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e(TAG, "uncaughtException: " + e.getMessage() );
        finish();

    }
}
