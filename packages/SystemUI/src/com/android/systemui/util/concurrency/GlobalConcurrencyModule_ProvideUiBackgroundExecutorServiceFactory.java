package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import java.util.concurrent.ExecutorService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideUiBackgroundExecutorServiceFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final GlobalConcurrencyModule_ProvideUiBackgroundExecutorServiceFactory INSTANCE = new GlobalConcurrencyModule_ProvideUiBackgroundExecutorServiceFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideUiBackgroundExecutorServiceFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ExecutorService provideUiBackgroundExecutorService() {
        ExecutorService provideUiBackgroundExecutorService = GlobalConcurrencyModule.provideUiBackgroundExecutorService();
        provideUiBackgroundExecutorService.getClass();
        return provideUiBackgroundExecutorService;
    }

    @Override // javax.inject.Provider
    public ExecutorService get() {
        return provideUiBackgroundExecutorService();
    }
}
