package com.android.systemui.pluginlock;

import java.util.concurrent.ThreadFactory;

public final /* synthetic */ class PluginLockUtils$$ExternalSyntheticLambda2 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread lambda$getExecutor$1;
        lambda$getExecutor$1 = PluginLockUtils.lambda$getExecutor$1(runnable);
        return lambda$getExecutor$1;
    }
}
