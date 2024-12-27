package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class GlobalConcurrencyModule_ProvideMainLooperFactory implements Provider {

    final class InstanceHolder {
        private static final GlobalConcurrencyModule_ProvideMainLooperFactory INSTANCE = new GlobalConcurrencyModule_ProvideMainLooperFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideMainLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideMainLooper() {
        Looper provideMainLooper = GlobalConcurrencyModule.provideMainLooper();
        Preconditions.checkNotNullFromProvides(provideMainLooper);
        return provideMainLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideMainLooper();
    }
}
