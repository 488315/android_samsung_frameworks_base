package com.android.systemui.util.concurrency;

import android.content.Context;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideMainExecutorFactory implements Provider {
    private final javax.inject.Provider contextProvider;

    public GlobalConcurrencyModule_ProvideMainExecutorFactory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static GlobalConcurrencyModule_ProvideMainExecutorFactory create(javax.inject.Provider provider) {
        return new GlobalConcurrencyModule_ProvideMainExecutorFactory(provider);
    }

    public static Executor provideMainExecutor(Context context) {
        Executor provideMainExecutor = GlobalConcurrencyModule.provideMainExecutor(context);
        Preconditions.checkNotNullFromProvides(provideMainExecutor);
        return provideMainExecutor;
    }

    @Override // javax.inject.Provider
    public Executor get() {
        return provideMainExecutor((Context) this.contextProvider.get());
    }
}
