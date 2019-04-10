package com.yar.ar;

import javax.microedition.khronos.opengles.GL10;

import edu.dhbw.andar.ARObject;

class CustomObject1 extends ARObject {
    public CustomObject1(String name, String patternName, double markerWidth, double[] markerCenter) {
        super(name, patternName, markerWidth, markerCenter);
    }

    @Override
    public void init(GL10 gl10) {

    }
}
