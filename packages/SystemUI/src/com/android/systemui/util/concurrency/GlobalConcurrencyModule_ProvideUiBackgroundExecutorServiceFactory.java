package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideUiBackgroundExecutorServiceFactory implements Provider {

    final class InstanceHolder {
        static final GlobalConcurrencyModule_ProvideUiBackgroundExecutorServiceFactory INSTANCE = new GlobalConcurrencyModule_ProvideUiBackgroundExecutorServiceFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideUiBackgroundExecutorServiceFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ExecutorService provideUiBackgroundExecutorService() {
        ExecutorService executorServiceProvideUiBackgroundExecutorService = GlobalConcurrencyModule.provideUiBackgroundExecutorService();
        executorServiceProvideUiBackgroundExecutorService.getClass();
        return executorServiceProvideUiBackgroundExecutorService;
    }

    @Override // javax.inject.Provider
    public ExecutorService get() {
        return provideUiBackgroundExecutorService();
    }
}
