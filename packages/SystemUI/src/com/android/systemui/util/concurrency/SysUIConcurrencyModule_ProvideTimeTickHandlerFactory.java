package com.android.systemui.util.concurrency;

import android.os.Handler;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideTimeTickHandlerFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final SysUIConcurrencyModule_ProvideTimeTickHandlerFactory INSTANCE = new SysUIConcurrencyModule_ProvideTimeTickHandlerFactory();

        private InstanceHolder() {
        }
    }

    public static SysUIConcurrencyModule_ProvideTimeTickHandlerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Handler provideTimeTickHandler() {
        Handler provideTimeTickHandler = SysUIConcurrencyModule.INSTANCE.provideTimeTickHandler();
        provideTimeTickHandler.getClass();
        return provideTimeTickHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideTimeTickHandler();
    }
}
