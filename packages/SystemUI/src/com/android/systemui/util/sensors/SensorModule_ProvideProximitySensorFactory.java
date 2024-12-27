package com.android.systemui.util.sensors;

import android.content.res.Resources;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SensorModule_ProvideProximitySensorFactory implements Provider {
    private final javax.inject.Provider postureDependentProximitySensorProvider;
    private final javax.inject.Provider proximitySensorProvider;
    private final javax.inject.Provider resourcesProvider;

    public SensorModule_ProvideProximitySensorFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.resourcesProvider = provider;
        this.postureDependentProximitySensorProvider = provider2;
        this.proximitySensorProvider = provider3;
    }

    public static SensorModule_ProvideProximitySensorFactory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SensorModule_ProvideProximitySensorFactory(provider, provider2, provider3);
    }

    public static ProximitySensor provideProximitySensor(Resources resources, Lazy lazy, Lazy lazy2) {
        ProximitySensor provideProximitySensor = SensorModule.provideProximitySensor(resources, lazy, lazy2);
        Preconditions.checkNotNullFromProvides(provideProximitySensor);
        return provideProximitySensor;
    }

    @Override // javax.inject.Provider
    public ProximitySensor get() {
        return provideProximitySensor((Resources) this.resourcesProvider.get(), DoubleCheck.lazy(this.postureDependentProximitySensorProvider), DoubleCheck.lazy(this.proximitySensorProvider));
    }
}
