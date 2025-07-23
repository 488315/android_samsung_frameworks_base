package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Executor provideBackgroundExecutor = SysUIConcurrencyModule.INSTANCE.provideBackgroundExecutor(looper);
        provideBackgroundExecutor.getClass();
        return provideBackgroundExecutor;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideBackgroundExecutor((Looper) this.looperProvider.get());
    }
}
