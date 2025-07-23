package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideLongRunningExecutorFactory implements Provider {
    private final Provider looperProvider;

    public SysUIConcurrencyModule_ProvideLongRunningExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideLongRunningExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideLongRunningExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static Executor provideLongRunningExecutor(Looper looper) {
        Executor provideLongRunningExecutor = SysUIConcurrencyModule.INSTANCE.provideLongRunningExecutor(looper);
        provideLongRunningExecutor.getClass();
        return provideLongRunningExecutor;
    }

    public static SysUIConcurrencyModule_ProvideLongRunningExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideLongRunningExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideLongRunningExecutor((Looper) this.looperProvider.get());
    }
}
