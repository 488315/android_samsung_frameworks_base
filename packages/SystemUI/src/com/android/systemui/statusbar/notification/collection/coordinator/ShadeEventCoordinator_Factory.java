package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShadeEventCoordinator_Factory implements Provider {
    private final javax.inject.Provider mLoggerProvider;
    private final javax.inject.Provider mMainExecutorProvider;

    public ShadeEventCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.mMainExecutorProvider = provider;
        this.mLoggerProvider = provider2;
    }

    public static ShadeEventCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new ShadeEventCoordinator_Factory(provider, provider2);
    }

    public static ShadeEventCoordinator newInstance(Executor executor, ShadeEventCoordinatorLogger shadeEventCoordinatorLogger) {
        return new ShadeEventCoordinator(executor, shadeEventCoordinatorLogger);
    }

    @Override // javax.inject.Provider
    public ShadeEventCoordinator get() {
        return newInstance((Executor) this.mMainExecutorProvider.get(), (ShadeEventCoordinatorLogger) this.mLoggerProvider.get());
    }
}
