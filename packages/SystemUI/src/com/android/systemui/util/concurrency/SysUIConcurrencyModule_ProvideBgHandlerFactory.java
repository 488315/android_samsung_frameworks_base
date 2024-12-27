package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvideBgHandlerFactory implements Provider {
    private final javax.inject.Provider bgLooperProvider;

    public SysUIConcurrencyModule_ProvideBgHandlerFactory(javax.inject.Provider provider) {
        this.bgLooperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBgHandlerFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBgHandlerFactory(provider);
    }

    public static Handler provideBgHandler(Looper looper) {
        Handler provideBgHandler = SysUIConcurrencyModule.INSTANCE.provideBgHandler(looper);
        Preconditions.checkNotNullFromProvides(provideBgHandler);
        return provideBgHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideBgHandler((Looper) this.bgLooperProvider.get());
    }
}
