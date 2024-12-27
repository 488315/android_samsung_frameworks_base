package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory implements Provider {
    private final javax.inject.Provider resourcesProvider;
    private final javax.inject.Provider thresholdSensorImplBuilderFactoryProvider;

    public SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.thresholdSensorImplBuilderFactoryProvider = provider;
        this.resourcesProvider = provider2;
    }

    public static SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory(provider, provider2);
    }

    public static ThresholdSensor[] providePostureToSecondaryProximitySensorMapping(ThresholdSensorImpl.BuilderFactory builderFactory, Resources resources) {
        ThresholdSensor[] providePostureToSecondaryProximitySensorMapping = SensorModule.providePostureToSecondaryProximitySensorMapping(builderFactory, resources);
        Preconditions.checkNotNullFromProvides(providePostureToSecondaryProximitySensorMapping);
        return providePostureToSecondaryProximitySensorMapping;
    }

    @Override // javax.inject.Provider
    public ThresholdSensor[] get() {
        return providePostureToSecondaryProximitySensorMapping((ThresholdSensorImpl.BuilderFactory) this.thresholdSensorImplBuilderFactoryProvider.get(), (Resources) this.resourcesProvider.get());
    }
}
