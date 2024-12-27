package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory implements Provider {
    private final javax.inject.Provider looperProvider;

    public GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory(javax.inject.Provider provider) {
        this.looperProvider = provider;
    }

    public static GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory create(javax.inject.Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory(provider);
    }

    public static DelayableExecutor provideMainDelayableExecutor(Looper looper) {
        DelayableExecutor provideMainDelayableExecutor = GlobalConcurrencyModule.provideMainDelayableExecutor(looper);
        Preconditions.checkNotNullFromProvides(provideMainDelayableExecutor);
        return provideMainDelayableExecutor;
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideMainDelayableExecutor((Looper) this.looperProvider.get());
    }
}
