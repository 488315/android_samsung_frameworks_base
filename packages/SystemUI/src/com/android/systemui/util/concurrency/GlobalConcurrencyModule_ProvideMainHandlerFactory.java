package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Handler provideMainHandler = GlobalConcurrencyModule.provideMainHandler(looper);
        provideMainHandler.getClass();
        return provideMainHandler;
    }

    public static GlobalConcurrencyModule_ProvideMainHandlerFactory create(Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainHandlerFactory(provider);
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideMainHandler((Looper) this.mainLooperProvider.get());
    }
}
