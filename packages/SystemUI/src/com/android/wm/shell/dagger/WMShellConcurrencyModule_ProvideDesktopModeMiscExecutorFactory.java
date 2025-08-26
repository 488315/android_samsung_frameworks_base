package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.HandlerExecutor;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellConcurrencyModule_ProvideDesktopModeMiscExecutorFactory implements Provider {
    public final Provider handlerProvider;

    public WMShellConcurrencyModule_ProvideDesktopModeMiscExecutorFactory(Provider provider) {
        this.handlerProvider = provider;
    }

    public static HandlerExecutor provideDesktopModeMiscExecutor(Handler handler) {
        return new HandlerExecutor(handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HandlerExecutor((Handler) this.handlerProvider.get());
    }
}
