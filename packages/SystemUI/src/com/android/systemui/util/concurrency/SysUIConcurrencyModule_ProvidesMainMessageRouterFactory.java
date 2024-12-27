package com.android.systemui.util.concurrency;

import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
