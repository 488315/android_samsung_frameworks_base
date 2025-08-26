package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import dagger.internal.Providers;

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
        MessageRouter messageRouterProvidesBackgroundMessageRouter = SysUIConcurrencyModule.INSTANCE.providesBackgroundMessageRouter(delayableExecutor);
        messageRouterProvidesBackgroundMessageRouter.getClass();
        return messageRouterProvidesBackgroundMessageRouter;
    }

    public static SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory create(Provider provider) {
        return new SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory(provider);
    }

    @Override // javax.inject.Provider
    public MessageRouter get() {
        return providesBackgroundMessageRouter((DelayableExecutor) this.executorProvider.get());
    }
}
