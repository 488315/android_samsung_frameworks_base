package com.android.server.knox.zt.devicetrust.task;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SchedulableMonitoringTask$$ExternalSyntheticLambda0
        implements ThreadFactory {
    public final /* synthetic */ SchedulableMonitoringTask f$0;

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        return this.f$0.lambda$createThreadFactory$1(runnable);
    }
}
