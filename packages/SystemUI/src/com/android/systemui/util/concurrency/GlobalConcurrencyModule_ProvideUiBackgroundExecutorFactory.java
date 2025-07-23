package com.android.systemui.util.concurrency;

import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory INSTANCE = new GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Executor provideUiBackgroundExecutor() {
        Executor provideUiBackgroundExecutor = GlobalConcurrencyModule.provideUiBackgroundExecutor();
        provideUiBackgroundExecutor.getClass();
        return provideUiBackgroundExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideUiBackgroundExecutor();
    }
}
