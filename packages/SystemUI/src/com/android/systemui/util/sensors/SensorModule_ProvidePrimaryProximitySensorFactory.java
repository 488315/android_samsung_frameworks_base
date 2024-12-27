package com.android.systemui.util.sensors;

import android.hardware.SensorManager;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SensorModule_ProvidePrimaryProximitySensorFactory implements Provider {
    private final javax.inject.Provider sensorManagerProvider;
    private final javax.inject.Provider thresholdSensorBuilderProvider;

    public SensorModule_ProvidePrimaryProximitySensorFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.sensorManagerProvider = provider;
        this.thresholdSensorBuilderProvider = provider2;
    }

    public static SensorModule_ProvidePrimaryProximitySensorFactory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SensorModule_ProvidePrimaryProximitySensorFactory(provider, provider2);
    }

    public static ThresholdSensor providePrimaryProximitySensor(SensorManager sensorManager, ThresholdSensorImpl.Builder builder) {
        ThresholdSensor providePrimaryProximitySensor = SensorModule.providePrimaryProximitySensor(sensorManager, builder);
        Preconditions.checkNotNullFromProvides(providePrimaryProximitySensor);
        return providePrimaryProximitySensor;
    }

    @Override // javax.inject.Provider
    public ThresholdSensor get() {
        return providePrimaryProximitySensor((SensorManager) this.sensorManagerProvider.get(), (ThresholdSensorImpl.Builder) this.thresholdSensorBuilderProvider.get());
    }
}
