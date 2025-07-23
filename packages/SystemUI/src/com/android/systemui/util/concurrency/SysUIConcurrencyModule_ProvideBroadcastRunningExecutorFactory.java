package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory implements Provider {
    private final Provider looperProvider;

    public SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory(Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static Executor provideBroadcastRunningExecutor(Looper looper) {
        Executor provideBroadcastRunningExecutor = SysUIConcurrencyModule.INSTANCE.provideBroadcastRunningExecutor(looper);
        provideBroadcastRunningExecutor.getClass();
        return provideBroadcastRunningExecutor;
    }

    public static SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideBroadcastRunningExecutor((Looper) this.looperProvider.get());
    }
}
