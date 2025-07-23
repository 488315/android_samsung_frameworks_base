package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeEventCoordinator_Factory implements Provider {
    private final Provider mLoggerProvider;
    private final Provider mMainExecutorProvider;

    public ShadeEventCoordinator_Factory(Provider provider, Provider provider2) {
        this.mMainExecutorProvider = provider;
        this.mLoggerProvider = provider2;
    }

    public static ShadeEventCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new ShadeEventCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static ShadeEventCoordinator newInstance(Executor executor, ShadeEventCoordinatorLogger shadeEventCoordinatorLogger) {
        return new ShadeEventCoordinator(executor, shadeEventCoordinatorLogger);
    }

    public static ShadeEventCoordinator_Factory create(Provider provider, Provider provider2) {
        return new ShadeEventCoordinator_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ShadeEventCoordinator get() {
        return newInstance((Executor) this.mMainExecutorProvider.get(), (ShadeEventCoordinatorLogger) this.mLoggerProvider.get());
    }
}
