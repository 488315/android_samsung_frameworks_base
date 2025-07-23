package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        DelayableExecutor provideMainDelayableExecutor = GlobalConcurrencyModule.provideMainDelayableExecutor(looper);
        provideMainDelayableExecutor.getClass();
        return provideMainDelayableExecutor;
    }

    public static GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory create(Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideMainDelayableExecutor((Looper) this.looperProvider.get());
    }
}
