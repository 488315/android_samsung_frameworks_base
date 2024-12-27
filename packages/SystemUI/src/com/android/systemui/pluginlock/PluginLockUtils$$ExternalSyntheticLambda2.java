package com.android.systemui.pluginlock;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class PluginLockUtils$$ExternalSyntheticLambda2 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread lambda$getExecutor$1;
        lambda$getExecutor$1 = PluginLockUtils.lambda$getExecutor$1(runnable);
        return lambda$getExecutor$1;
    }
}
