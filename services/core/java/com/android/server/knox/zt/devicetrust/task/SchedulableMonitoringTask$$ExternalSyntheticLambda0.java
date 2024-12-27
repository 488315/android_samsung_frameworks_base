package com.android.server.knox.zt.devicetrust.task;

import java.util.concurrent.ThreadFactory;

public final /* synthetic */ class SchedulableMonitoringTask$$ExternalSyntheticLambda0
        implements ThreadFactory {
    public final /* synthetic */ SchedulableMonitoringTask f$0;

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        return this.f$0.lambda$createThreadFactory$1(runnable);
    }
}
