package com.samsung.android.media;

import android.os.PersistableBundle;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemMediaCapabilities {
    public static final String FEATURE_HARDWARE_C2 = "feature.device.hw.c2";
    public static final String FEATURE_RECORDER_BitSavingMode = "feature.recorder.bitsaving.mode";
    public static final String FEATURE_RECORDER_HierBFrame = "feature.recorder.hierarchical.bframe";
    public static final String FEATURE_RECORDER_OneBFrame = "feature.recorder.one.bframe";
    public static final String KEY_QUERY_MAX_BITRATE = "max.bitrate";
    public static final String KEY_QUERY_MAX_FPS = "max.fps";
    public static final String KEY_QUERY_MAX_HEIGHT = "max.height";
    public static final String KEY_QUERY_MAX_WIDTH = "max.width";
    public static final String QUERY_RECORDER_HierBFrame = "query.recorder.hierarchical.bframe";
    public static final String QUERY_RECORDER_OneBFrame = "query.recorder.adaptive.bframe";
    private static final String TAG = "SemMediaCapabilities";

    private static native void nativeInit();

    private static native boolean nativeIsFeatureSupported(String str, String str2);

    private static native PersistableBundle nativeQuery(String str, String str2);

    static {
        System.loadLibrary("secrecorder_jni");
        nativeInit();
    }

    public static boolean isFeatureSupported(String str) {
        Log.d(TAG, "feature: " + str);
        return nativeIsFeatureSupported(null, str);
    }

    public static boolean isFeatureSupportedForMime(String str, String str2) {
        Log.d(TAG, str + ": feature: " + str2);
        return nativeIsFeatureSupported(str, str2);
    }

    public static PersistableBundle query(String str) {
        Log.d(TAG, "query: " + str);
        return nativeQuery(null, str);
    }

    public static PersistableBundle query(String str, String str2) {
        Log.d(TAG, str + ": query: " + str2);
        return nativeQuery(str, str2);
    }
}
