package com.android.systemui.util.concurrency;

import android.os.Handler;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class GlobalConcurrencyModule_ProvideHandlerFactory implements Provider {

    final class InstanceHolder {
        private static final GlobalConcurrencyModule_ProvideHandlerFactory INSTANCE = new GlobalConcurrencyModule_ProvideHandlerFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideHandlerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Handler provideHandler() {
        Handler provideHandler = GlobalConcurrencyModule.provideHandler();
        Preconditions.checkNotNullFromProvides(provideHandler);
        return provideHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideHandler();
    }
}
