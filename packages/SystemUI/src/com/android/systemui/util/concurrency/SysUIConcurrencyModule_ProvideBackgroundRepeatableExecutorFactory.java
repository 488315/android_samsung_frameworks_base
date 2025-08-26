package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory implements Provider {
    private final Provider execProvider;

    public SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory(Provider provider) {
        this.execProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static RepeatableExecutor provideBackgroundRepeatableExecutor(DelayableExecutor delayableExecutor) {
        RepeatableExecutor repeatableExecutorProvideBackgroundRepeatableExecutor = SysUIConcurrencyModule.INSTANCE.provideBackgroundRepeatableExecutor(delayableExecutor);
        repeatableExecutorProvideBackgroundRepeatableExecutor.getClass();
        return repeatableExecutorProvideBackgroundRepeatableExecutor;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public RepeatableExecutor get() {
        return provideBackgroundRepeatableExecutor((DelayableExecutor) this.execProvider.get());
    }
}
