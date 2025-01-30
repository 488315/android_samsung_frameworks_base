package com.android.p038wm.shell.dagger;

import android.os.Handler;
import com.android.p038wm.shell.common.HandlerExecutor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
