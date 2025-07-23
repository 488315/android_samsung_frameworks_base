package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.HandlerExecutor;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellConcurrencyModule_ProvideSysUIMainExecutorFactory implements Provider {
    public final Provider sysuiMainHandlerProvider;

    public WMShellConcurrencyModule_ProvideSysUIMainExecutorFactory(Provider provider) {
        this.sysuiMainHandlerProvider = provider;
    }

    public static HandlerExecutor provideSysUIMainExecutor(Handler handler) {
        return new HandlerExecutor(handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HandlerExecutor((Handler) this.sysuiMainHandlerProvider.get());
    }
}
