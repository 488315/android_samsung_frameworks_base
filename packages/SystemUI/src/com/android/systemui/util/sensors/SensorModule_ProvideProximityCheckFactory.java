package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
