package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideMainHandlerFactory implements Provider {
    private final Provider mainLooperProvider;

    public GlobalConcurrencyModule_ProvideMainHandlerFactory(Provider provider) {
        this.mainLooperProvider = provider;
    }

    public static GlobalConcurrencyModule_ProvideMainHandlerFactory create(javax.inject.Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainHandlerFactory(Providers.asDaggerProvider(provider));
    }

    public static Handler provideMainHandler(Looper looper) {
        Handler handlerProvideMainHandler = GlobalConcurrencyModule.provideMainHandler(looper);
        handlerProvideMainHandler.getClass();
        return handlerProvideMainHandler;
    }

    public static GlobalConcurrencyModule_ProvideMainHandlerFactory create(Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainHandlerFactory(provider);
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideMainHandler((Looper) this.mainLooperProvider.get());
    }
}
