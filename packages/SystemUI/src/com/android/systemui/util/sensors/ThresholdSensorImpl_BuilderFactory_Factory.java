package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class ThresholdSensorImpl_BuilderFactory_Factory implements Provider {
    private final Provider executionProvider;
    private final Provider resourcesProvider;
    private final Provider sensorManagerProvider;

    public ThresholdSensorImpl_BuilderFactory_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.resourcesProvider = provider;
        this.sensorManagerProvider = provider2;
        this.executionProvider = provider3;
    }

    public static ThresholdSensorImpl_BuilderFactory_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new ThresholdSensorImpl_BuilderFactory_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static ThresholdSensorImpl.BuilderFactory newInstance(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
        return new ThresholdSensorImpl.BuilderFactory(resources, asyncSensorManager, execution);
    }

    public static ThresholdSensorImpl_BuilderFactory_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ThresholdSensorImpl_BuilderFactory_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public ThresholdSensorImpl.BuilderFactory get() {
        return newInstance((Resources) this.resourcesProvider.get(), (AsyncSensorManager) this.sensorManagerProvider.get(), (Execution) this.executionProvider.get());
    }
}
