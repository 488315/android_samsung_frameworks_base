package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GlobalConcurrencyModule_ProvideMainLooperFactory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
