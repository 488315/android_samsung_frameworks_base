package com.android.internal.util;

import android.os.SystemClock;
import android.util.Log;
import android.util.SparseLongArray;

import java.io.IOException;

public class PerfettoTrigger {
    private static final String TAG = "PerfettoTrigger";
    private static final long THROTTLE_MILLIS = 300000;
    private static final String TRIGGER_COMMAND = "/system/bin/trigger_perfetto";
    private static final SparseLongArray sLastInvocationPerTrigger = new SparseLongArray(100);
    private static final Object sLock = new Object();

    public static void trigger(String triggerName) {
        synchronized (sLock) {
            long lastTrigger = sLastInvocationPerTrigger.get(triggerName.hashCode());
            long sinceLastTrigger = SystemClock.elapsedRealtime() - lastTrigger;
            if (sinceLastTrigger < 300000) {
                Log.v(
                        TAG,
                        "Not triggering " + triggerName + " - not enough time since last trigger");
                return;
            }
            sLastInvocationPerTrigger.put(triggerName.hashCode(), SystemClock.elapsedRealtime());
            try {
                ProcessBuilder pb = new ProcessBuilder(TRIGGER_COMMAND, triggerName);
                Log.v(TAG, "Triggering " + String.join(" ", pb.command()));
                pb.start();
            } catch (IOException e) {
                Log.w(TAG, "Failed to trigger " + triggerName, e);
            }
        }
    }
}
