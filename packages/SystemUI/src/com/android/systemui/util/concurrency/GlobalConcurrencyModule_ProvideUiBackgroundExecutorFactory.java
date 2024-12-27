package com.android.systemui.util.concurrency;

import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

public final class GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory implements Provider {

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
