package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBgHandlerFactory implements Provider {
    private final Provider bgLooperProvider;

    public SysUIConcurrencyModule_ProvideBgHandlerFactory(Provider provider) {
        this.bgLooperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBgHandlerFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBgHandlerFactory(Providers.asDaggerProvider(provider));
    }

    public static Handler provideBgHandler(Looper looper) {
        Handler provideBgHandler = SysUIConcurrencyModule.INSTANCE.provideBgHandler(looper);
        provideBgHandler.getClass();
        return provideBgHandler;
    }

    public static SysUIConcurrencyModule_ProvideBgHandlerFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBgHandlerFactory(provider);
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideBgHandler((Looper) this.bgLooperProvider.get());
    }
}
