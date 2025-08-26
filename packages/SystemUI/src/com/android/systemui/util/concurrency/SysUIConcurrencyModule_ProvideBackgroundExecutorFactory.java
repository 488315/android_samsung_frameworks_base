package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBackgroundExecutorFactory implements Provider {
    private final Provider looperProvider;

    public SysUIConcurrencyModule_ProvideBackgroundExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static Executor provideBackgroundExecutor(Looper looper) {
        Executor executorProvideBackgroundExecutor = SysUIConcurrencyModule.INSTANCE.provideBackgroundExecutor(looper);
        executorProvideBackgroundExecutor.getClass();
        return executorProvideBackgroundExecutor;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideBackgroundExecutor((Looper) this.looperProvider.get());
    }
}
