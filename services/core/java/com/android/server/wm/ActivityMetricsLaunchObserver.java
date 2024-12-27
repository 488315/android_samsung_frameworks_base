package com.android.server.wm;

import android.content.ComponentName;
import android.content.Intent;

public abstract class ActivityMetricsLaunchObserver {
    public void onActivityLaunchCancelled(long j) {}

    public void onActivityLaunchFinished(long j, ComponentName componentName, long j2, int i) {}

    public void onActivityLaunched(int i, int i2, long j, ComponentName componentName) {}

    public void onIntentFailed(long j) {}

    public abstract void onIntentStarted(Intent intent, long j);

    public void onReportFullyDrawn(long j, long j2) {}
}
