package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import dagger.internal.Providers;

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
        MessageRouter messageRouterProvidesMainMessageRouter = SysUIConcurrencyModule.INSTANCE.providesMainMessageRouter(delayableExecutor);
        messageRouterProvidesMainMessageRouter.getClass();
        return messageRouterProvidesMainMessageRouter;
    }

    public static SysUIConcurrencyModule_ProvidesMainMessageRouterFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvidesMainMessageRouterFactory(provider);
    }

    @Override // javax.inject.Provider
    public MessageRouter get() {
        return providesMainMessageRouter((DelayableExecutor) this.executorProvider.get());
    }
}
