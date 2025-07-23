package com.android.systemui.util.sensors;

import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ProximityCheck provideProximityCheck = SensorModule.provideProximityCheck(proximitySensor, delayableExecutor);
        provideProximityCheck.getClass();
        return provideProximityCheck;
    }

    public static SensorModule_ProvideProximityCheckFactory create(Provider provider, Provider provider2) {
        return new SensorModule_ProvideProximityCheckFactory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ProximityCheck get() {
        return provideProximityCheck((ProximitySensor) this.proximitySensorProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get());
    }
}
