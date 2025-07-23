package com.samsung.voicebargein;

import android.util.Log;

/* loaded from: classes6.dex */
public class BargeInEngineWrapper {
    private static final String TAG = "BargeInEngineWrapper";
    private static BargeInEngine uniqueInstance;

    private BargeInEngineWrapper() {
    }

    public static synchronized BargeInEngine getInstance() {
        synchronized (BargeInEngineWrapper.class) {
            if (uniqueInstance == null) {
                String str = TAG;
                Log.i(str, "getInstance() : make new libVoiceCommandEngine");
                if (BargeInEngine.init() == 0) {
                    uniqueInstance = new BargeInEngine();
                } else {
                    Log.e(str, "cannot load libVoiceCommandEngine.so");
                    return null;
                }
            } else {
                Log.i(TAG, "getInstance() : get existed libVoiceCommandEngine");
            }
            return uniqueInstance;
        }
    }
}
