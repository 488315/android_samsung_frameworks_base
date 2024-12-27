package com.android.systemui.util.concurrency;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory INSTANCE = new GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Executor provideUiBackgroundExecutor() {
        Executor provideUiBackgroundExecutor = GlobalConcurrencyModule.provideUiBackgroundExecutor();
        Preconditions.checkNotNullFromProvides(provideUiBackgroundExecutor);
        return provideUiBackgroundExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideUiBackgroundExecutor();
    }
}
