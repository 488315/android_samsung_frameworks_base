package com.samsung.android.media.heif.jni;

import android.util.Log;

/* loaded from: classes5.dex */
public class HeifCaptureJNI {
    private static final String TAG = "HeifCaptureJNI";
    private long mNativeHandle;

    private native void nativeFinalize();

    private native void nativeSetup();

    public native int nativeStart(AMessageJNI aMessageJNI);

    public native int nativeStop();

    public native int nativeStore(AMessageJNI aMessageJNI);

    public HeifCaptureJNI() {
        nativeSetup();
        Log.m98i(TAG, "setup : " + this.mNativeHandle);
    }

    protected void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    static {
        System.loadLibrary("heifcapture_jni.media.samsung");
    }
}
