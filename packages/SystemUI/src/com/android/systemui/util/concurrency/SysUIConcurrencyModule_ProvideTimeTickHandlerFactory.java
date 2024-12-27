package com.android.systemui.util.concurrency;

import android.os.Handler;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvideTimeTickHandlerFactory implements Provider {

    final class InstanceHolder {
        private static final SysUIConcurrencyModule_ProvideTimeTickHandlerFactory INSTANCE = new SysUIConcurrencyModule_ProvideTimeTickHandlerFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideTimeTickHandlerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Handler provideTimeTickHandler() {
        Handler provideTimeTickHandler = SysUIConcurrencyModule.INSTANCE.provideTimeTickHandler();
        Preconditions.checkNotNullFromProvides(provideTimeTickHandler);
        return provideTimeTickHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideTimeTickHandler();
    }
}
