package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory implements Provider {
    private final Provider mainExecutorProvider;
    private final Provider mainHandlerProvider;
    private final Provider mainLooperProvider;

    public SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory(Provider provider, Provider provider2, Provider provider3) {
        this.mainLooperProvider = provider;
        this.mainHandlerProvider = provider2;
        this.mainExecutorProvider = provider3;
    }

    public static SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static UiThreadContext provideBackPanelUiThreadContext(Looper looper, Handler handler, Executor executor) {
        UiThreadContext provideBackPanelUiThreadContext = SysUIConcurrencyModule.INSTANCE.provideBackPanelUiThreadContext(looper, handler, executor);
        provideBackPanelUiThreadContext.getClass();
        return provideBackPanelUiThreadContext;
    }

    public static SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory create(Provider provider, Provider provider2, Provider provider3) {
        return new SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public UiThreadContext get() {
        return provideBackPanelUiThreadContext((Looper) this.mainLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}
