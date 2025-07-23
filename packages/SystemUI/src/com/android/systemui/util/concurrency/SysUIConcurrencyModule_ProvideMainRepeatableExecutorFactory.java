package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        RepeatableExecutor provideMainRepeatableExecutor = SysUIConcurrencyModule.INSTANCE.provideMainRepeatableExecutor(delayableExecutor);
        provideMainRepeatableExecutor.getClass();
        return provideMainRepeatableExecutor;
    }

    public static SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory(provider);
    }

    @Override // javax.inject.Provider
    public RepeatableExecutor get() {
        return provideMainRepeatableExecutor((DelayableExecutor) this.execProvider.get());
    }
}
