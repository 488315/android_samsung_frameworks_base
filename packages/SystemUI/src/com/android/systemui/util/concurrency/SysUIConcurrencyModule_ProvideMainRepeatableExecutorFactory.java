package com.android.systemui.util.concurrency;

import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory implements Provider {
    private final javax.inject.Provider execProvider;

    public SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory(javax.inject.Provider provider) {
        this.execProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideMainRepeatableExecutorFactory(provider);
    }

    public static RepeatableExecutor provideMainRepeatableExecutor(DelayableExecutor delayableExecutor) {
        RepeatableExecutor provideMainRepeatableExecutor = SysUIConcurrencyModule.INSTANCE.provideMainRepeatableExecutor(delayableExecutor);
        Preconditions.checkNotNullFromProvides(provideMainRepeatableExecutor);
        return provideMainRepeatableExecutor;
    }

    @Override // javax.inject.Provider
    public RepeatableExecutor get() {
        return provideMainRepeatableExecutor((DelayableExecutor) this.execProvider.get());
    }
}
