package com.android.systemui.util.concurrency;

import android.content.Context;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

public final class GlobalConcurrencyModule_ProvideMainExecutorFactory implements Provider {
    private final javax.inject.Provider contextProvider;

    public GlobalConcurrencyModule_ProvideMainExecutorFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static GlobalConcurrencyModule_ProvideMainExecutorFactory create(javax.inject.Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainExecutorFactory(provider);
    }

    public static Executor provideMainExecutor(Context context) {
        Executor provideMainExecutor = GlobalConcurrencyModule.provideMainExecutor(context);
        Preconditions.checkNotNullFromProvides(provideMainExecutor);
        return provideMainExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideMainExecutor((Context) this.contextProvider.get());
    }
}
