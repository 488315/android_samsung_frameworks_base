package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SensorModule_ProvideProximityCheckFactory implements Provider {
    private final Provider delayableExecutorProvider;
    private final Provider proximitySensorProvider;

    public SensorModule_ProvideProximityCheckFactory(Provider provider, Provider provider2) {
        this.proximitySensorProvider = provider;
        this.delayableExecutorProvider = provider2;
    }

    public static SensorModule_ProvideProximityCheckFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SensorModule_ProvideProximityCheckFactory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static ProximityCheck provideProximityCheck(ProximitySensor proximitySensor, DelayableExecutor delayableExecutor) {
        ProximityCheck proximityCheckProvideProximityCheck = SensorModule.provideProximityCheck(proximitySensor, delayableExecutor);
        proximityCheckProvideProximityCheck.getClass();
        return proximityCheckProvideProximityCheck;
    }

    public static SensorModule_ProvideProximityCheckFactory create(Provider provider, Provider provider2) {
        return new SensorModule_ProvideProximityCheckFactory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ProximityCheck get() {
        return provideProximityCheck((ProximitySensor) this.proximitySensorProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get());
    }
}
