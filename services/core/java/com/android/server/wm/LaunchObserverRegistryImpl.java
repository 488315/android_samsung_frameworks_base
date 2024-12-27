package com.android.server.wm;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LaunchObserverRegistryImpl extends ActivityMetricsLaunchObserver implements ActivityMetricsLaunchObserverRegistry {
    public final Handler mHandler;
    public final ArrayList mList = new ArrayList();

    public LaunchObserverRegistryImpl(Looper looper) {
        this.mHandler = new Handler(looper);
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public final void onActivityLaunchCancelled(long j) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new LaunchObserverRegistryImpl$$ExternalSyntheticLambda0(2), this, Long.valueOf(j)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public final void onActivityLaunchFinished(long j, ComponentName componentName, long j2, int i) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new LaunchObserverRegistryImpl$$ExternalSyntheticLambda2(0), this, Long.valueOf(j), componentName, Long.valueOf(j2), Integer.valueOf(i)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public final void onActivityLaunched(int i, int i2, long j, ComponentName componentName) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new LaunchObserverRegistryImpl$$ExternalSyntheticLambda2(1), this, Long.valueOf(j), componentName, Integer.valueOf(i), Integer.valueOf(i2)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public final void onIntentFailed(long j) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new LaunchObserverRegistryImpl$$ExternalSyntheticLambda0(3), this, Long.valueOf(j)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public final void onIntentStarted(Intent intent, long j) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new LaunchObserverRegistryImpl$$ExternalSyntheticLambda3(0), this, intent, Long.valueOf(j)));
    }

    @Override // com.android.server.wm.ActivityMetricsLaunchObserver
    public final void onReportFullyDrawn(long j, long j2) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new LaunchObserverRegistryImpl$$ExternalSyntheticLambda3(1), this, Long.valueOf(j), Long.valueOf(j2)));
    }
}
