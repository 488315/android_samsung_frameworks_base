package com.android.internal.util;

import android.os.SystemClock;
import android.util.Log;
import android.util.SparseLongArray;
import java.io.IOException;

/* loaded from: classes4.dex */
public class PerfettoTrigger {
    private static final String TAG = "PerfettoTrigger";
    private static final long THROTTLE_MILLIS = 300000;
    private static final String TRIGGER_COMMAND = "/system/bin/trigger_perfetto";
    private static final SparseLongArray sLastInvocationPerTrigger = new SparseLongArray(100);
    private static final Object sLock = new Object();

    public static void trigger(String str) throws IOException {
        synchronized (sLock) {
            SparseLongArray sparseLongArray = sLastInvocationPerTrigger;
            if (SystemClock.elapsedRealtime() - sparseLongArray.get(str.hashCode()) < 300000) {
                Log.v(TAG, "Not triggering " + str + " - not enough time since last trigger");
                return;
            }
            sparseLongArray.put(str.hashCode(), SystemClock.elapsedRealtime());
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(TRIGGER_COMMAND, str);
                Log.v(TAG, "Triggering " + String.join(" ", processBuilder.command()));
                processBuilder.start();
            } catch (IOException e) {
                Log.w(TAG, "Failed to trigger " + str, e);
            }
        }
    }
}
