package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideLongRunningExecutorFactory implements Provider {
    private final Provider looperProvider;

    public SysUIConcurrencyModule_ProvideLongRunningExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideLongRunningExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideLongRunningExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static Executor provideLongRunningExecutor(Looper looper) {
        Executor executorProvideLongRunningExecutor = SysUIConcurrencyModule.INSTANCE.provideLongRunningExecutor(looper);
        executorProvideLongRunningExecutor.getClass();
        return executorProvideLongRunningExecutor;
    }

    public static SysUIConcurrencyModule_ProvideLongRunningExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideLongRunningExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideLongRunningExecutor((Looper) this.looperProvider.get());
    }
}
