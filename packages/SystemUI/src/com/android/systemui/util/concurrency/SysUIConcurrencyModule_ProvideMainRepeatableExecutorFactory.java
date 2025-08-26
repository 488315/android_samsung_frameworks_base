package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory implements Provider {
    private final Provider execProvider;

    public SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory(Provider provider) {
        this.execProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory(Providers.asDaggerProvider(provider));
    }

    public static RepeatableExecutor provideMainRepeatableExecutor(DelayableExecutor delayableExecutor) {
        RepeatableExecutor repeatableExecutorProvideMainRepeatableExecutor = SysUIConcurrencyModule.INSTANCE.provideMainRepeatableExecutor(delayableExecutor);
        repeatableExecutorProvideMainRepeatableExecutor.getClass();
        return repeatableExecutorProvideMainRepeatableExecutor;
    }

    public static SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public RepeatableExecutor get() {
        return provideMainRepeatableExecutor((DelayableExecutor) this.execProvider.get());
    }
}
