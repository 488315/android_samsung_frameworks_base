package com.android.wm.shell.dagger;

import android.os.Handler;
import com.android.wm.shell.common.HandlerExecutor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellConcurrencyModule_ProvideSharedBackgroundExecutorFactory implements Provider {
    public final Provider handlerProvider;

    public WMShellConcurrencyModule_ProvideSharedBackgroundExecutorFactory(Provider provider) {
        this.handlerProvider = provider;
    }

    public static HandlerExecutor provideSharedBackgroundExecutor(Handler handler) {
        return new HandlerExecutor(handler);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new HandlerExecutor((Handler) this.handlerProvider.get());
    }
}
