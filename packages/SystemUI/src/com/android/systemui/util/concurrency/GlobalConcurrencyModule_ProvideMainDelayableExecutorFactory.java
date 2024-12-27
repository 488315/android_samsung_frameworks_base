package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory implements Provider {
    private final javax.inject.Provider looperProvider;

    public GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory(javax.inject.Provider provider) {
        this.looperProvider = provider;
    }

    public static GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory create(javax.inject.Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory(provider);
    }

    public static DelayableExecutor provideMainDelayableExecutor(Looper looper) {
        DelayableExecutor provideMainDelayableExecutor = GlobalConcurrencyModule.provideMainDelayableExecutor(looper);
        Preconditions.checkNotNullFromProvides(provideMainDelayableExecutor);
        return provideMainDelayableExecutor;
    }

    @Override // javax.inject.Provider
    public DelayableExecutor get() {
        return provideMainDelayableExecutor((Looper) this.looperProvider.get());
    }
}
