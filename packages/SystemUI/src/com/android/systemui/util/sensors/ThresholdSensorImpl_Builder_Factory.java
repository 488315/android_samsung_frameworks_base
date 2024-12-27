package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;

public final class ThresholdSensorImpl_Builder_Factory implements Provider {
    private final javax.inject.Provider executionProvider;
    private final javax.inject.Provider resourcesProvider;
    private final javax.inject.Provider sensorManagerProvider;

    public ThresholdSensorImpl_Builder_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.resourcesProvider = provider;
        this.sensorManagerProvider = provider2;
        this.executionProvider = provider3;
    }

    public static ThresholdSensorImpl_Builder_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new ThresholdSensorImpl_Builder_Factory(provider, provider2, provider3);
    }

    public static ThresholdSensorImpl.Builder newInstance(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
        return new ThresholdSensorImpl.Builder(resources, asyncSensorManager, execution);
    }

    @Override // javax.inject.Provider
    public ThresholdSensorImpl.Builder get() {
        return newInstance((Resources) this.resourcesProvider.get(), (AsyncSensorManager) this.sensorManagerProvider.get(), (Execution) this.executionProvider.get());
    }
}
