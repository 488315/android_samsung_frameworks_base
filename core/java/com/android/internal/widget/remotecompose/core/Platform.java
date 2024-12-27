package com.android.internal.widget.remotecompose.core;

public interface Platform {
    int getImageHeight(Object obj);

    int getImageWidth(Object obj);

    byte[] imageToByteArray(Object obj);

    float[] pathToFloatArray(Object obj);
}
