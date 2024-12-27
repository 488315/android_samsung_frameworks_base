package com.android.systemui.util.concurrency;

import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
