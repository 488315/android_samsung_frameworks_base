package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

public final class SysUIConcurrencyModule_ProvideBackgroundExecutorFactory implements Provider {
    private final javax.inject.Provider looperProvider;

    public SysUIConcurrencyModule_ProvideBackgroundExecutorFactory(javax.inject.Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundExecutorFactory(provider);
    }

    public static Executor provideBackgroundExecutor(Looper looper) {
        Executor provideBackgroundExecutor = SysUIConcurrencyModule.INSTANCE.provideBackgroundExecutor(looper);
        Preconditions.checkNotNullFromProvides(provideBackgroundExecutor);
        return provideBackgroundExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideBackgroundExecutor((Looper) this.looperProvider.get());
    }
}
