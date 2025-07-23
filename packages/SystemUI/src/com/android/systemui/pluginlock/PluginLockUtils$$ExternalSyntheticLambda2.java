package com.android.systemui.pluginlock;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockUtils$$ExternalSyntheticLambda2 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread lambda$getExecutor$1;
        lambda$getExecutor$1 = PluginLockUtils.lambda$getExecutor$1(runnable);
        return lambda$getExecutor$1;
    }
}
