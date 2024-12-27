package com.android.systemui.util.sensors;

import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class SensorModule_ProvideSecondaryProximitySensorFactory implements Provider {
    private final javax.inject.Provider thresholdSensorBuilderProvider;

    public SensorModule_ProvideSecondaryProximitySensorFactory(javax.inject.Provider provider) {
        this.thresholdSensorBuilderProvider = provider;
    }

    public static SensorModule_ProvideSecondaryProximitySensorFactory create(javax.inject.Provider provider) {
        return new SensorModule_ProvideSecondaryProximitySensorFactory(provider);
    }

    public static ThresholdSensor provideSecondaryProximitySensor(ThresholdSensorImpl.Builder builder) {
        ThresholdSensor provideSecondaryProximitySensor = SensorModule.provideSecondaryProximitySensor(builder);
        Preconditions.checkNotNullFromProvides(provideSecondaryProximitySensor);
        return provideSecondaryProximitySensor;
    }

    @Override // javax.inject.Provider
    public ThresholdSensor get() {
        return provideSecondaryProximitySensor((ThresholdSensorImpl.Builder) this.thresholdSensorBuilderProvider.get());
    }
}
