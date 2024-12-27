package com.android.systemui.util.concurrency;

import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory implements Provider {
    private final javax.inject.Provider execProvider;

    public SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory(javax.inject.Provider provider) {
        this.execProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory(provider);
    }

    public static RepeatableExecutor provideBackgroundRepeatableExecutor(DelayableExecutor delayableExecutor) {
        RepeatableExecutor provideBackgroundRepeatableExecutor = SysUIConcurrencyModule.INSTANCE.provideBackgroundRepeatableExecutor(delayableExecutor);
        Preconditions.checkNotNullFromProvides(provideBackgroundRepeatableExecutor);
        return provideBackgroundRepeatableExecutor;
    }

    @Override // javax.inject.Provider
    public RepeatableExecutor get() {
        return provideBackgroundRepeatableExecutor((DelayableExecutor) this.execProvider.get());
    }
}
