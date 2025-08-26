package com.android.systemui.util.sensors;

import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class SensorModule_ProvideSecondaryProximitySensorFactory implements Provider {
    private final Provider thresholdSensorBuilderProvider;

    public SensorModule_ProvideSecondaryProximitySensorFactory(Provider provider) {
        this.thresholdSensorBuilderProvider = provider;
    }

    public static SensorModule_ProvideSecondaryProximitySensorFactory create(javax.inject.Provider provider) {
        return new SensorModule_ProvideSecondaryProximitySensorFactory(Providers.asDaggerProvider(provider));
    }

    public static ThresholdSensor provideSecondaryProximitySensor(ThresholdSensorImpl.Builder builder) {
        ThresholdSensor thresholdSensorProvideSecondaryProximitySensor = SensorModule.provideSecondaryProximitySensor(builder);
        thresholdSensorProvideSecondaryProximitySensor.getClass();
        return thresholdSensorProvideSecondaryProximitySensor;
    }

    public static SensorModule_ProvideSecondaryProximitySensorFactory create(Provider provider) {
        return new SensorModule_ProvideSecondaryProximitySensorFactory(provider);
    }

    @Override // javax.inject.Provider
    public ThresholdSensor get() {
        return provideSecondaryProximitySensor((ThresholdSensorImpl.Builder) this.thresholdSensorBuilderProvider.get());
    }
}
