package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory implements Provider {
    private final Provider looperProvider;

    public SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static Executor provideNotifInflationExecutor(Looper looper) {
        Executor provideNotifInflationExecutor = SysUIConcurrencyModule.INSTANCE.provideNotifInflationExecutor(looper);
        provideNotifInflationExecutor.getClass();
        return provideNotifInflationExecutor;
    }

    public static SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideNotifInflationExecutor((Looper) this.looperProvider.get());
    }
}
