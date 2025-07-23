package com.android.systemui.util.sensors;

import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ThresholdSensor provideSecondaryProximitySensor = SensorModule.provideSecondaryProximitySensor(builder);
        provideSecondaryProximitySensor.getClass();
        return provideSecondaryProximitySensor;
    }

    public static SensorModule_ProvideSecondaryProximitySensorFactory create(Provider provider) {
        return new SensorModule_ProvideSecondaryProximitySensorFactory(provider);
    }

    @Override // javax.inject.Provider
    public ThresholdSensor get() {
        return provideSecondaryProximitySensor((ThresholdSensorImpl.Builder) this.thresholdSensorBuilderProvider.get());
    }
}
