package com.samsung.voicebargein;

import android.util.Log;

public class BargeInEngine {
    private static final String TAG = BargeInEngine.class.getSimpleName();

    public native void phrasespotClose(long j);

    public native long phrasespotInit(String str, String str2);

    public native String phrasespotPipe(long j, short[] sArr, long j2, long j3, float[] fArr);

    public void asyncPrint(String s) {}

    public static int init() {
        try {
            Log.i(TAG, "Trying to load libVoiceCommandEngine.so");
            System.loadLibrary("VoiceCommandEngine");
            Log.i(TAG, "Loading libVoiceCommandEngine.so");
            return 0;
        } catch (Exception e) {
            Log.e(TAG, "WARNING: Could not load libVoiceCommandEngine.so");
            return -1;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(TAG, "WARNING: Could not load libVoiceCommandEngine.so");
            return -1;
        }
    }
}
