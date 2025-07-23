package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory implements Provider {
    private final Provider executorProvider;

    public SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory(Provider provider) {
        this.executorProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory(Providers.asDaggerProvider(provider));
    }

    public static MessageRouter providesBackgroundMessageRouter(DelayableExecutor delayableExecutor) {
        MessageRouter providesBackgroundMessageRouter = SysUIConcurrencyModule.INSTANCE.providesBackgroundMessageRouter(delayableExecutor);
        providesBackgroundMessageRouter.getClass();
        return providesBackgroundMessageRouter;
    }

    public static SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory(provider);
    }

    @Override // javax.inject.Provider
    public MessageRouter get() {
        return providesBackgroundMessageRouter((DelayableExecutor) this.executorProvider.get());
    }
}
