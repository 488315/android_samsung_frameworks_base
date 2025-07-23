package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        DelayableExecutor provideBackgroundDelayableExecutor = SysUIConcurrencyModule.INSTANCE.provideBackgroundDelayableExecutor(looper);
        provideBackgroundDelayableExecutor.getClass();
        return provideBackgroundDelayableExecutor;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideBackgroundDelayableExecutor((Looper) this.looperProvider.get());
    }
}
