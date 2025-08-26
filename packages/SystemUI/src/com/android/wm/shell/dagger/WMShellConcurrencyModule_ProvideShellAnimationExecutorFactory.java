package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.HandlerExecutor;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory implements Provider {
    public final Provider animHandlerProvider;

    public WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory(Provider provider) {
        this.animHandlerProvider = provider;
    }

    public static HandlerExecutor provideShellAnimationExecutor(Handler handler) {
        return new HandlerExecutor(handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HandlerExecutor((Handler) this.animHandlerProvider.get());
    }
}
