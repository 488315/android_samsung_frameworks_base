package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

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
