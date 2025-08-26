package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

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
        Handler handlerProvideBgHandler = SysUIConcurrencyModule.INSTANCE.provideBgHandler(looper);
        handlerProvideBgHandler.getClass();
        return handlerProvideBgHandler;
    }

    public static SysUIConcurrencyModule_ProvideBgHandlerFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBgHandlerFactory(provider);
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideBgHandler((Looper) this.bgLooperProvider.get());
    }
}
