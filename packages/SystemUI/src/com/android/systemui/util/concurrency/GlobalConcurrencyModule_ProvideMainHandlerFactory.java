package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class GlobalConcurrencyModule_ProvideMainHandlerFactory implements Provider {
    private final javax.inject.Provider mainLooperProvider;

    public GlobalConcurrencyModule_ProvideMainHandlerFactory(javax.inject.Provider provider) {
        this.mainLooperProvider = provider;
    }

    public static GlobalConcurrencyModule_ProvideMainHandlerFactory create(javax.inject.Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainHandlerFactory(provider);
    }

    public static Handler provideMainHandler(Looper looper) {
        Handler provideMainHandler = GlobalConcurrencyModule.provideMainHandler(looper);
        Preconditions.checkNotNullFromProvides(provideMainHandler);
        return provideMainHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideMainHandler((Looper) this.mainLooperProvider.get());
    }
}
