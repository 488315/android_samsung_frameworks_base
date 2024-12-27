package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

public final class SysUIConcurrencyModule_ProvideLongRunningExecutorFactory implements Provider {
    private final javax.inject.Provider looperProvider;

    public SysUIConcurrencyModule_ProvideLongRunningExecutorFactory(javax.inject.Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideLongRunningExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideLongRunningExecutorFactory(provider);
    }

    public static Executor provideLongRunningExecutor(Looper looper) {
        Executor provideLongRunningExecutor = SysUIConcurrencyModule.INSTANCE.provideLongRunningExecutor(looper);
        Preconditions.checkNotNullFromProvides(provideLongRunningExecutor);
        return provideLongRunningExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideLongRunningExecutor((Looper) this.looperProvider.get());
    }
}
