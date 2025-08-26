package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory implements Provider {
    private final Provider looperProvider;

    public GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory create(javax.inject.Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static DelayableExecutor provideMainDelayableExecutor(Looper looper) {
        DelayableExecutor delayableExecutorProvideMainDelayableExecutor = GlobalConcurrencyModule.provideMainDelayableExecutor(looper);
        delayableExecutorProvideMainDelayableExecutor.getClass();
        return delayableExecutorProvideMainDelayableExecutor;
    }

    public static GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory create(Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideMainDelayableExecutor((Looper) this.looperProvider.get());
    }
}
