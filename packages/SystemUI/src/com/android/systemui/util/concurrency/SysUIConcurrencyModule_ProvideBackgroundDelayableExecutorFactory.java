package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory implements Provider {
    private final Provider looperProvider;

    public SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static DelayableExecutor provideBackgroundDelayableExecutor(Looper looper) {
        DelayableExecutor delayableExecutorProvideBackgroundDelayableExecutor = SysUIConcurrencyModule.INSTANCE.provideBackgroundDelayableExecutor(looper);
        delayableExecutorProvideBackgroundDelayableExecutor.getClass();
        return delayableExecutorProvideBackgroundDelayableExecutor;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideBackgroundDelayableExecutor((Looper) this.looperProvider.get());
    }
}
