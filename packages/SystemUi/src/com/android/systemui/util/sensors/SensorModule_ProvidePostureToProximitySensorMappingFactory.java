package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SensorModule_ProvidePostureToProximitySensorMappingFactory implements Provider {
    public final Provider resourcesProvider;
    public final Provider thresholdSensorImplBuilderFactoryProvider;

    public SensorModule_ProvidePostureToProximitySensorMappingFactory(Provider provider, Provider provider2) {
        this.thresholdSensorImplBuilderFactoryProvider = provider;
        this.resourcesProvider = provider2;
    }

    public static ThresholdSensor[] providePostureToProximitySensorMapping(ThresholdSensorImpl.BuilderFactory builderFactory, Resources resources) {
        return SensorModule.createPostureToSensorMapping(builderFactory, resources.getStringArray(R.array.proximity_sensor_posture_mapping), R.dimen.proximity_sensor_threshold, R.dimen.proximity_sensor_threshold_latch);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePostureToProximitySensorMapping((ThresholdSensorImpl.BuilderFactory) this.thresholdSensorImplBuilderFactoryProvider.get(), (Resources) this.resourcesProvider.get());
    }
}
