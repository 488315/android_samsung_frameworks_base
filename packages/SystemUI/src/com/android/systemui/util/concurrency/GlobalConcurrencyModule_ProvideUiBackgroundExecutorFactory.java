package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory implements Provider {

    final class InstanceHolder {
        static final GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory INSTANCE = new GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Executor provideUiBackgroundExecutor() {
        Executor executorProvideUiBackgroundExecutor = GlobalConcurrencyModule.provideUiBackgroundExecutor();
        executorProvideUiBackgroundExecutor.getClass();
        return executorProvideUiBackgroundExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideUiBackgroundExecutor();
    }
}
