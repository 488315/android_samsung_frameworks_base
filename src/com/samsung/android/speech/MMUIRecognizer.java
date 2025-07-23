package com.samsung.android.speech;

import android.util.Log;

/* loaded from: classes6.dex */
public class MMUIRecognizer {
    private static final String TAG = "MMUIRecognizer";

    public native int RECThread(short[] sArr);

    public native int ResetFx();

    public native int SASRClose();

    public native int SASRDoRecognition(float[] fArr, String[] strArr, String str, short[] sArr, String[] strArr2);

    public native int SASRInit(String str);

    public native int SASRLoadModel(String str);

    public native int SASRReset();

    public native int SaveChnUpdate(String str);

    public native int SetSRLanguage(int i);

    public static int init() {
        try {
            String str = TAG;
            Log.i(str, "Trying to load libsasr-jni.so");
            System.loadLibrary("sasr-jni");
            Log.i(str, "Loading libsasr-jni.so done");
            return 0;
        } catch (Exception unused) {
            Log.e(TAG, "WARNING: Could not load libsasr-jni.so");
            return -1;
        } catch (UnsatisfiedLinkError unused2) {
            Log.e(TAG, "WARNING: Could not load libsasr-jni.so");
            return -1;
        }
    }
}
