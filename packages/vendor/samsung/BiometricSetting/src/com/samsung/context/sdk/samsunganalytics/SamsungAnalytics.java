package com.samsung.context.sdk.samsunganalytics;

import android.app.Application;

import com.samsung.context.sdk.samsunganalytics.internal.Tracker;

public final class SamsungAnalytics {
    public static SamsungAnalytics instance;
    public final Tracker tracker;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SamsungAnalytics(
            final android.app.Application r22,
            final com.samsung.context.sdk.samsunganalytics.Configuration r23) {
        /*
            Method dump skipped, instructions count: 1027
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.<init>(android.app.Application,"
                    + " com.samsung.context.sdk.samsunganalytics.Configuration):void");
    }

    public static SamsungAnalytics getInstanceAndConfig(
            Application application, Configuration configuration) {
        SamsungAnalytics samsungAnalytics = instance;
        if (samsungAnalytics == null || samsungAnalytics.tracker == null) {
            synchronized (SamsungAnalytics.class) {
                instance = new SamsungAnalytics(application, configuration);
            }
        }
        return instance;
    }
}
