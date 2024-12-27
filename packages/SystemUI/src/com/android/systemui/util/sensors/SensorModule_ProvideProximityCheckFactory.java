package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SensorModule_ProvideProximityCheckFactory implements Provider {
    private final javax.inject.Provider delayableExecutorProvider;
    private final javax.inject.Provider proximitySensorProvider;

    public SensorModule_ProvideProximityCheckFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.proximitySensorProvider = provider;
        this.delayableExecutorProvider = provider2;
    }

    public static SensorModule_ProvideProximityCheckFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SensorModule_ProvideProximityCheckFactory(provider, provider2);
    }

    public static ProximityCheck provideProximityCheck(ProximitySensor proximitySensor, DelayableExecutor delayableExecutor) {
        ProximityCheck provideProximityCheck = SensorModule.provideProximityCheck(proximitySensor, delayableExecutor);
        Preconditions.checkNotNullFromProvides(provideProximityCheck);
        return provideProximityCheck;
    }

    @Override // javax.inject.Provider
    public ProximityCheck get() {
        return provideProximityCheck((ProximitySensor) this.proximitySensorProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get());
    }
}
