package com.android.systemui.util.sensors;

import android.content.res.Resources;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SensorModule_ProvideProximitySensorFactory implements Provider {
    private final Provider postureDependentProximitySensorProvider;
    private final Provider proximitySensorProvider;
    private final Provider resourcesProvider;

    public SensorModule_ProvideProximitySensorFactory(Provider provider, Provider provider2, Provider provider3) {
        this.resourcesProvider = provider;
        this.postureDependentProximitySensorProvider = provider2;
        this.proximitySensorProvider = provider3;
    }

    public static SensorModule_ProvideProximitySensorFactory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new SensorModule_ProvideProximitySensorFactory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static ProximitySensor provideProximitySensor(Resources resources, Lazy lazy, Lazy lazy2) {
        ProximitySensor proximitySensorProvideProximitySensor = SensorModule.provideProximitySensor(resources, lazy, lazy2);
        proximitySensorProvideProximitySensor.getClass();
        return proximitySensorProvideProximitySensor;
    }

    public static SensorModule_ProvideProximitySensorFactory create(Provider provider, Provider provider2, Provider provider3) {
        return new SensorModule_ProvideProximitySensorFactory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public ProximitySensor get() {
        return provideProximitySensor((Resources) this.resourcesProvider.get(), DoubleCheck.lazy(this.postureDependentProximitySensorProvider), DoubleCheck.lazy(this.proximitySensorProvider));
    }
}
