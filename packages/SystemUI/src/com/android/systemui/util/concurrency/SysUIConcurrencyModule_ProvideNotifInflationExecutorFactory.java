package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory implements Provider {
    private final javax.inject.Provider looperProvider;

    public SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory(javax.inject.Provider provider) {
        this.looperProvider = provider;
    }

    public static SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory create(javax.inject.Provider provider) {
        return new SysUIConcurrencyModule_ProvideNotifInflationExecutorFactory(provider);
    }

    public static Executor provideNotifInflationExecutor(Looper looper) {
        Executor provideNotifInflationExecutor = SysUIConcurrencyModule.INSTANCE.provideNotifInflationExecutor(looper);
        Preconditions.checkNotNullFromProvides(provideNotifInflationExecutor);
        return provideNotifInflationExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideNotifInflationExecutor((Looper) this.looperProvider.get());
    }
}
