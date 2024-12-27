package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory implements Provider {
    private final javax.inject.Provider looperProvider;

    public SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory(javax.inject.Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory(provider);
    }

    public static Executor provideBroadcastRunningExecutor(Looper looper) {
        Executor provideBroadcastRunningExecutor = SysUIConcurrencyModule.INSTANCE.provideBroadcastRunningExecutor(looper);
        Preconditions.checkNotNullFromProvides(provideBroadcastRunningExecutor);
        return provideBroadcastRunningExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideBroadcastRunningExecutor((Looper) this.looperProvider.get());
    }
}
