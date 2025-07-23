package com.android.systemui.util.concurrency;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideMainExecutorFactory implements Provider {
    private final Provider contextProvider;

    public GlobalConcurrencyModule_ProvideMainExecutorFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static GlobalConcurrencyModule_ProvideMainExecutorFactory create(javax.inject.Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static Executor provideMainExecutor(Context context) {
        Executor provideMainExecutor = GlobalConcurrencyModule.provideMainExecutor(context);
        provideMainExecutor.getClass();
        return provideMainExecutor;
    }

    public static GlobalConcurrencyModule_ProvideMainExecutorFactory create(Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideMainExecutor((Context) this.contextProvider.get());
    }
}
