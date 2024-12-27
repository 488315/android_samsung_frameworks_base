package com.android.server.location.injector;

import android.util.Log;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.clipboard.ClipboardService;

import java.time.Instant;

public final class LocationUsageLogger {
    public int mApiUsageLogHourlyCount;
    public long mLastApiUsageLogHour;

    public static int bucketizeProvider(String str) {
        if ("network".equals(str)) {
            return 1;
        }
        if ("gps".equals(str)) {
            return 2;
        }
        if ("passive".equals(str)) {
            return 3;
        }
        return "fused".equals(str) ? 4 : 0;
    }

    public final synchronized boolean hitApiUsageLogCap() {
        long epochMilli =
                Instant.now().toEpochMilli() / ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        if (epochMilli > this.mLastApiUsageLogHour) {
            this.mLastApiUsageLogHour = epochMilli;
            this.mApiUsageLogHourlyCount = 0;
            return false;
        }
        int min = Math.min(this.mApiUsageLogHourlyCount + 1, 60);
        this.mApiUsageLogHourlyCount = min;
        return min >= 60;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void logLocationApiUsage(
            int r27,
            int r28,
            java.lang.String r29,
            java.lang.String r30,
            java.lang.String r31,
            android.location.LocationRequest r32,
            boolean r33,
            boolean r34,
            android.location.Geofence r35,
            boolean r36) {
        /*
            Method dump skipped, instructions count: 325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.injector.LocationUsageLogger.logLocationApiUsage(int,"
                    + " int, java.lang.String, java.lang.String, java.lang.String,"
                    + " android.location.LocationRequest, boolean, boolean,"
                    + " android.location.Geofence, boolean):void");
    }

    public final void logLocationApiUsage(int i, String str) {
        try {
            if (hitApiUsageLogCap()) {
                return;
            }
            FrameworkStatsLog.write(
                    210,
                    i,
                    5,
                    (String) null,
                    bucketizeProvider(str),
                    0,
                    0,
                    0,
                    0L,
                    0,
                    1,
                    0,
                    0,
                    (String) null);
        } catch (Exception e) {
            Log.w("LocationManagerService", "Failed to log API usage to statsd.", e);
        }
    }
}
