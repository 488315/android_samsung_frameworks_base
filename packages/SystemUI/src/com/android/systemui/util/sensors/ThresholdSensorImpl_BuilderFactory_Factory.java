package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ThresholdSensorImpl_BuilderFactory_Factory implements Provider {
    private final javax.inject.Provider executionProvider;
    private final javax.inject.Provider resourcesProvider;
    private final javax.inject.Provider sensorManagerProvider;

    public ThresholdSensorImpl_BuilderFactory_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.resourcesProvider = provider;
        this.sensorManagerProvider = provider2;
        this.executionProvider = provider3;
    }

    public static ThresholdSensorImpl_BuilderFactory_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new ThresholdSensorImpl_BuilderFactory_Factory(provider, provider2, provider3);
    }

    public static ThresholdSensorImpl.BuilderFactory newInstance(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
        return new ThresholdSensorImpl.BuilderFactory(resources, asyncSensorManager, execution);
    }

    @Override // javax.inject.Provider
    public ThresholdSensorImpl.BuilderFactory get() {
        return newInstance((Resources) this.resourcesProvider.get(), (AsyncSensorManager) this.sensorManagerProvider.get(), (Execution) this.executionProvider.get());
    }
}
