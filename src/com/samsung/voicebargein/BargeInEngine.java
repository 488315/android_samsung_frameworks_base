package com.samsung.voicebargein;

import android.util.Log;

/* loaded from: classes6.dex */
public class BargeInEngine {
    private static final String TAG = "BargeInEngine";

    public void asyncPrint(String str) {
    }

    public native void phrasespotClose(long j);

    public native long phrasespotInit(String str, String str2);

    public native String phrasespotPipe(long j, short[] sArr, long j2, long j3, float[] fArr);

    public static int init() {
        try {
            String str = TAG;
            Log.i(str, "Trying to load libVoiceCommandEngine.so");
            System.loadLibrary("VoiceCommandEngine");
            Log.i(str, "Loading libVoiceCommandEngine.so");
            return 0;
        } catch (Exception unused) {
            Log.e(TAG, "WARNING: Could not load libVoiceCommandEngine.so");
            return -1;
        } catch (UnsatisfiedLinkError unused2) {
            Log.e(TAG, "WARNING: Could not load libVoiceCommandEngine.so");
            return -1;
        }
    }
}
