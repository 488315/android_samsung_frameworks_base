package com.android.systemui.util.sensors;

import android.hardware.SensorManager;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

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
        ThresholdSensor thresholdSensorProvidePrimaryProximitySensor = SensorModule.providePrimaryProximitySensor(sensorManager, builder);
        thresholdSensorProvidePrimaryProximitySensor.getClass();
        return thresholdSensorProvidePrimaryProximitySensor;
    }

    public static SensorModule_ProvidePrimaryProximitySensorFactory create(Provider provider, Provider provider2) {
        return new SensorModule_ProvidePrimaryProximitySensorFactory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public ThresholdSensor get() {
        return providePrimaryProximitySensor((SensorManager) this.sensorManagerProvider.get(), (ThresholdSensorImpl.Builder) this.thresholdSensorBuilderProvider.get());
    }
}
