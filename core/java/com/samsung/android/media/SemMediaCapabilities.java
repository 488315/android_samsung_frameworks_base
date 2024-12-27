package com.samsung.android.media;

import android.util.Log;

public class SemMediaCapabilities {
    public static final String FEATURE_HARDWARE_C2 = "sec.media.feature.c2";
    private static final String TAG = "SemMediaCapabilities";

    private static final native void nativeInit();

    private static native boolean nativeIsFeatureSupported(String str);

    static {
        System.loadLibrary("secrecorder_jni");
        nativeInit();
    }

    public static boolean isFeatureSupported(String feature) {
        Log.d(TAG, "feature: " + feature);
        return nativeIsFeatureSupported(feature);
    }
}
