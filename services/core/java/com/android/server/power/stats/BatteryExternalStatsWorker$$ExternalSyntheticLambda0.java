package com.android.server.power.stats;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryExternalStatsWorker$$ExternalSyntheticLambda0 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(new BatteryExternalStatsWorker$$ExternalSyntheticLambda1(3, runnable), "batterystats-worker");
        thread.setPriority(5);
        return thread;
    }
}
