package com.android.internal.graphics.palette;

public interface PointProvider {
    float distance(float[] fArr, float[] fArr2);

    float[] fromInt(int i);

    int toInt(float[] fArr);
}
