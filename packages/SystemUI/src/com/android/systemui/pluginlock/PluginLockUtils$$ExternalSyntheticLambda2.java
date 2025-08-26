package com.android.systemui.pluginlock;

import java.util.concurrent.ThreadFactory;

/* loaded from: classes2.dex */
public final /* synthetic */ class PluginLockUtils$$ExternalSyntheticLambda2 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        return PluginLockUtils.lambda$getExecutor$1(runnable);
    }
}
