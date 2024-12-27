package com.android.server.wm;

import android.content.ComponentName;
import android.content.Intent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ActivityMetricsLaunchObserver {
    public void onActivityLaunchCancelled(long j) {
    }

    public void onActivityLaunchFinished(long j, ComponentName componentName, long j2, int i) {
    }

    public void onActivityLaunched(int i, int i2, long j, ComponentName componentName) {
    }

    public void onIntentFailed(long j) {
    }

    public abstract void onIntentStarted(Intent intent, long j);

    public void onReportFullyDrawn(long j, long j2) {
    }
}
