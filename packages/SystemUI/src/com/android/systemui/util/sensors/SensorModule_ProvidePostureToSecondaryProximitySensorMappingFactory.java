package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory implements Provider {
    private final Provider resourcesProvider;
    private final Provider thresholdSensorImplBuilderFactoryProvider;

    public SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory(Provider provider, Provider provider2) {
        this.thresholdSensorImplBuilderFactoryProvider = provider;
        this.resourcesProvider = provider2;
    }

    public static SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static ThresholdSensor[] providePostureToSecondaryProximitySensorMapping(ThresholdSensorImpl.BuilderFactory builderFactory, Resources resources) {
        ThresholdSensor[] providePostureToSecondaryProximitySensorMapping = SensorModule.providePostureToSecondaryProximitySensorMapping(builderFactory, resources);
        providePostureToSecondaryProximitySensorMapping.getClass();
        return providePostureToSecondaryProximitySensorMapping;
    }

    public static SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory create(Provider provider, Provider provider2) {
        return new SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ThresholdSensor[] get() {
        return providePostureToSecondaryProximitySensorMapping((ThresholdSensorImpl.BuilderFactory) this.thresholdSensorImplBuilderFactoryProvider.get(), (Resources) this.resourcesProvider.get());
    }
}
