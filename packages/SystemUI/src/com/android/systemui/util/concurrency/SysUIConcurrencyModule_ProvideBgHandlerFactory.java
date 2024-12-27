package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
