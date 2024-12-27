package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory implements Provider {
    private final javax.inject.Provider looperProvider;

    public SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory(javax.inject.Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory(provider);
    }

    public static DelayableExecutor provideLongRunningDelayableExecutor(Looper looper) {
        DelayableExecutor provideLongRunningDelayableExecutor = SysUIConcurrencyModule.INSTANCE.provideLongRunningDelayableExecutor(looper);
        Preconditions.checkNotNullFromProvides(provideLongRunningDelayableExecutor);
        return provideLongRunningDelayableExecutor;
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideLongRunningDelayableExecutor((Looper) this.looperProvider.get());
    }
}
