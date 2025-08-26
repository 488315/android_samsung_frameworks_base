package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory implements Provider {
    private final Provider looperProvider;

    public SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static Executor provideBroadcastRunningExecutor(Looper looper) {
        Executor executorProvideBroadcastRunningExecutor = SysUIConcurrencyModule.INSTANCE.provideBroadcastRunningExecutor(looper);
        executorProvideBroadcastRunningExecutor.getClass();
        return executorProvideBroadcastRunningExecutor;
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideBroadcastRunningExecutor((Looper) this.looperProvider.get());
    }
}
