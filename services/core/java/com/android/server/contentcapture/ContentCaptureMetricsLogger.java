package com.android.server.contentcapture;

import android.content.ComponentName;
import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ContentCaptureMetricsLogger {
    public static void writeServiceEvent(int i, ComponentName componentName) {
        FrameworkStatsLog.write(207, i, ComponentName.flattenToShortString(componentName), (String) null, 0, 0);
    }

    public static void writeSessionEvent(int i, int i2, int i3, ComponentName componentName) {
        FrameworkStatsLog.write(208, i, i2, i3, ComponentName.flattenToShortString(componentName), (String) null, false);
    }
}
