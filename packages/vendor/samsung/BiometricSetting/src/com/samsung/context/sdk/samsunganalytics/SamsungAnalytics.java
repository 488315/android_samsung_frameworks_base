package com.samsung.context.sdk.samsunganalytics;

import android.app.Application;

import com.samsung.context.sdk.samsunganalytics.internal.Tracker;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class SamsungAnalytics {
    public static SamsungAnalytics instance;
    public final Tracker tracker;

    /* JADX WARN: Removed duplicated region for block: B:124:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0186 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01bd  */
    /* JADX WARN: Type inference failed for: r11v6, types: [com.samsung.context.sdk.samsunganalytics.internal.Tracker$4] */
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
