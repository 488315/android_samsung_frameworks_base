package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvidesMainMessageRouterFactory implements Provider {
    private final Provider executorProvider;

    public SysUIConcurrencyModule_ProvidesMainMessageRouterFactory(Provider provider) {
        this.executorProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvidesMainMessageRouterFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvidesMainMessageRouterFactory(Providers.asDaggerProvider(provider));
    }

    public static MessageRouter providesMainMessageRouter(DelayableExecutor delayableExecutor) {
        MessageRouter providesMainMessageRouter = SysUIConcurrencyModule.INSTANCE.providesMainMessageRouter(delayableExecutor);
        providesMainMessageRouter.getClass();
        return providesMainMessageRouter;
    }

    public static SysUIConcurrencyModule_ProvidesMainMessageRouterFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvidesMainMessageRouterFactory(provider);
    }

    @Override // javax.inject.Provider
    public MessageRouter get() {
        return providesMainMessageRouter((DelayableExecutor) this.executorProvider.get());
    }
}
