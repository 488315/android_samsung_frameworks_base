package com.android.server.wm;

import android.content.ComponentName;
import android.content.Intent;

/* loaded from: classes3.dex */
public abstract class ActivityMetricsLaunchObserver {
    public void onActivityLaunchCancelled(long j) {
    }

    public void onActivityLaunchFinished(long j, ComponentName componentName, long j2) {
    }

    public void onActivityLaunched(long j, ComponentName componentName, int i) {
    }

    public void onIntentFailed(long j) {
    }

    public void onIntentStarted(Intent intent, long j) {
    }

    public void onReportFullyDrawn(long j, long j2) {
    }
}
