package com.android.systemui.util.concurrency;

import android.os.Handler;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class GlobalConcurrencyModule_ProvideHandlerFactory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final GlobalConcurrencyModule_ProvideHandlerFactory INSTANCE = new GlobalConcurrencyModule_ProvideHandlerFactory();

        private InstanceHolder() {
        }
    }

    public static GlobalConcurrencyModule_ProvideHandlerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Handler provideHandler() {
        Handler provideHandler = GlobalConcurrencyModule.provideHandler();
        Preconditions.checkNotNullFromProvides(provideHandler);
        return provideHandler;
    }

    @Override // javax.inject.Provider
    public Handler get() {
        return provideHandler();
    }
}
