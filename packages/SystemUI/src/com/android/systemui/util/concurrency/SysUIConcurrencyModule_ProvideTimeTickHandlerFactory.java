package com.android.systemui.util.concurrency;

import android.os.Handler;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideTimeTickHandlerFactory implements Provider {

    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideTimeTickHandlerFactory INSTANCE = new SysUIConcurrencyModule_ProvideTimeTickHandlerFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideTimeTickHandlerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Handler provideTimeTickHandler() {
        Handler handlerProvideTimeTickHandler = SysUIConcurrencyModule.INSTANCE.provideTimeTickHandler();
        handlerProvideTimeTickHandler.getClass();
        return handlerProvideTimeTickHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideTimeTickHandler();
    }
}
