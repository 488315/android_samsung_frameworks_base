package com.android.systemui.util.concurrency;

import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory implements Provider {
    private final javax.inject.Provider executorProvider;

    public SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory(javax.inject.Provider provider) {
        this.executorProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory(provider);
    }

    public static MessageRouter providesBackgroundMessageRouter(DelayableExecutor delayableExecutor) {
        MessageRouter providesBackgroundMessageRouter = SysUIConcurrencyModule.INSTANCE.providesBackgroundMessageRouter(delayableExecutor);
        Preconditions.checkNotNullFromProvides(providesBackgroundMessageRouter);
        return providesBackgroundMessageRouter;
    }

    @Override // javax.inject.Provider
    public MessageRouter get() {
        return providesBackgroundMessageRouter((DelayableExecutor) this.executorProvider.get());
    }
}
