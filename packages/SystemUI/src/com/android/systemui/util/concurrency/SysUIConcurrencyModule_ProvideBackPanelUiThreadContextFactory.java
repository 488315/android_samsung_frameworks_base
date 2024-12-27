package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

public final class SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory implements Provider {
    private final javax.inject.Provider mainExecutorProvider;
    private final javax.inject.Provider mainHandlerProvider;
    private final javax.inject.Provider mainLooperProvider;

    public SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.mainLooperProvider = provider;
        this.mainHandlerProvider = provider2;
        this.mainExecutorProvider = provider3;
    }

    public static SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SysUIConcurrencyModule_ProvideBackPanelUiThreadContextFactory(provider, provider2, provider3);
    }

    public static UiThreadContext provideBackPanelUiThreadContext(Looper looper, Handler handler, Executor executor) {
        UiThreadContext provideBackPanelUiThreadContext = SysUIConcurrencyModule.INSTANCE.provideBackPanelUiThreadContext(looper, handler, executor);
        Preconditions.checkNotNullFromProvides(provideBackPanelUiThreadContext);
        return provideBackPanelUiThreadContext;
    }

    @Override // javax.inject.Provider
    public UiThreadContext get() {
        return provideBackPanelUiThreadContext((Looper) this.mainLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}
