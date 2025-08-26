package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory implements Provider {
    private final Provider looperProvider;

    public SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static DelayableExecutor provideLongRunningDelayableExecutor(Looper looper) {
        DelayableExecutor delayableExecutorProvideLongRunningDelayableExecutor = SysUIConcurrencyModule.INSTANCE.provideLongRunningDelayableExecutor(looper);
        delayableExecutorProvideLongRunningDelayableExecutor.getClass();
        return delayableExecutorProvideLongRunningDelayableExecutor;
    }

    public static SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideLongRunningDelayableExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideLongRunningDelayableExecutor((Looper) this.looperProvider.get());
    }
}
