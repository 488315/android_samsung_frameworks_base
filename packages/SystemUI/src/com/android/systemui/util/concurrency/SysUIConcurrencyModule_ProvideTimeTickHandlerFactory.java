package com.android.systemui.util.concurrency;

import android.os.Handler;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SysUIConcurrencyModule_ProvideTimeTickHandlerFactory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
