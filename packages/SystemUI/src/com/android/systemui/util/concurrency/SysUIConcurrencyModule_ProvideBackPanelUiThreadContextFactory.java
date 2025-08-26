package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

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
        UiThreadContext uiThreadContextProvideBackPanelUiThreadContext = SysUIConcurrencyModule.INSTANCE.provideBackPanelUiThreadContext(looper, handler, executor);
        uiThreadContextProvideBackPanelUiThreadContext.getClass();
        return uiThreadContextProvideBackPanelUiThreadContext;
    }

    public static SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory create(Provider provider, Provider provider2, Provider provider3) {
        return new SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public UiThreadContext get() {
        return provideBackPanelUiThreadContext((Looper) this.mainLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}
