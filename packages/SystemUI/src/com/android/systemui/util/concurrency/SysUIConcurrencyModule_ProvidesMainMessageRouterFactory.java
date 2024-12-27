package com.android.systemui.util.concurrency;

import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvidesMainMessageRouterFactory implements Provider {
    private final javax.inject.Provider executorProvider;

    public SysUIConcurrencyModule_ProvidesMainMessageRouterFactory(javax.inject.Provider provider) {
        this.executorProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvidesMainMessageRouterFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvidesMainMessageRouterFactory(provider);
    }

    public static MessageRouter providesMainMessageRouter(DelayableExecutor delayableExecutor) {
        MessageRouter providesMainMessageRouter = SysUIConcurrencyModule.INSTANCE.providesMainMessageRouter(delayableExecutor);
        Preconditions.checkNotNullFromProvides(providesMainMessageRouter);
        return providesMainMessageRouter;
    }

    @Override // javax.inject.Provider
    public MessageRouter get() {
        return providesMainMessageRouter((DelayableExecutor) this.executorProvider.get());
    }
}
