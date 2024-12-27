package com.android.server;

import android.util.LocalLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SystemClockTime {
    public static final LocalLog sTimeDebugLog = new LocalLog(30, false);
    public static int sTimeConfidence = 0;
    public static final long sNativeData = init();

    private static native long init();

    private static native int setTime(long j, long j2);

    public static void setTimeAndConfidence(int i, String str, long j) {
        synchronized (SystemClockTime.class) {
            setTime(sNativeData, j);
            sTimeConfidence = i;
            sTimeDebugLog.log(str);
        }
    }
}
