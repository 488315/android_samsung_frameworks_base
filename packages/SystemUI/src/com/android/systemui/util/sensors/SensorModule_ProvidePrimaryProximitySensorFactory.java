package com.android.systemui.util.sensors;

import android.hardware.SensorManager;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SensorModule_ProvidePrimaryProximitySensorFactory implements Provider {
    private final Provider sensorManagerProvider;
    private final Provider thresholdSensorBuilderProvider;

    public SensorModule_ProvidePrimaryProximitySensorFactory(Provider provider, Provider provider2) {
        this.sensorManagerProvider = provider;
        this.thresholdSensorBuilderProvider = provider2;
    }

    public static SensorModule_ProvidePrimaryProximitySensorFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SensorModule_ProvidePrimaryProximitySensorFactory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static ThresholdSensor providePrimaryProximitySensor(SensorManager sensorManager, ThresholdSensorImpl.Builder builder) {
        ThresholdSensor providePrimaryProximitySensor = SensorModule.providePrimaryProximitySensor(sensorManager, builder);
        providePrimaryProximitySensor.getClass();
        return providePrimaryProximitySensor;
    }

    public static SensorModule_ProvidePrimaryProximitySensorFactory create(Provider provider, Provider provider2) {
        return new SensorModule_ProvidePrimaryProximitySensorFactory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ThresholdSensor get() {
        return providePrimaryProximitySensor((SensorManager) this.sensorManagerProvider.get(), (ThresholdSensorImpl.Builder) this.thresholdSensorBuilderProvider.get());
    }
}
