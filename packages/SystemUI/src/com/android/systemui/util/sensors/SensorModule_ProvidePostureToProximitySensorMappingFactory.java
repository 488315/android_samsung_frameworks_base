package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SensorModule_ProvidePostureToProximitySensorMappingFactory implements Provider {
    private final javax.inject.Provider resourcesProvider;
    private final javax.inject.Provider thresholdSensorImplBuilderFactoryProvider;

    public SensorModule_ProvidePostureToProximitySensorMappingFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.thresholdSensorImplBuilderFactoryProvider = provider;
        this.resourcesProvider = provider2;
    }

    public static SensorModule_ProvidePostureToProximitySensorMappingFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SensorModule_ProvidePostureToProximitySensorMappingFactory(provider, provider2);
    }

    public static ThresholdSensor[] providePostureToProximitySensorMapping(ThresholdSensorImpl.BuilderFactory builderFactory, Resources resources) {
        ThresholdSensor[] providePostureToProximitySensorMapping = SensorModule.providePostureToProximitySensorMapping(builderFactory, resources);
        Preconditions.checkNotNullFromProvides(providePostureToProximitySensorMapping);
        return providePostureToProximitySensorMapping;
    }

    @Override // javax.inject.Provider
    public ThresholdSensor[] get() {
        return providePostureToProximitySensorMapping((ThresholdSensorImpl.BuilderFactory) this.thresholdSensorImplBuilderFactoryProvider.get(), (Resources) this.resourcesProvider.get());
    }
}
