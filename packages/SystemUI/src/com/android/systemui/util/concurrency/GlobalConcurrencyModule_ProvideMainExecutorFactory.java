package com.android.systemui.util.concurrency;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

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
        Executor executorProvideMainExecutor = GlobalConcurrencyModule.provideMainExecutor(context);
        executorProvideMainExecutor.getClass();
        return executorProvideMainExecutor;
    }

    public static GlobalConcurrencyModule_ProvideMainExecutorFactory create(Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideMainExecutor((Context) this.contextProvider.get());
    }
}
