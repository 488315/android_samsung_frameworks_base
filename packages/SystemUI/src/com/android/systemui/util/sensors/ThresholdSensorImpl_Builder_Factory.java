package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ThresholdSensorImpl_Builder_Factory implements Provider {
    private final Provider executionProvider;
    private final Provider resourcesProvider;
    private final Provider sensorManagerProvider;

    public ThresholdSensorImpl_Builder_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.resourcesProvider = provider;
        this.sensorManagerProvider = provider2;
        this.executionProvider = provider3;
    }

    public static ThresholdSensorImpl_Builder_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new ThresholdSensorImpl_Builder_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static ThresholdSensorImpl.Builder newInstance(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
        return new ThresholdSensorImpl.Builder(resources, asyncSensorManager, execution);
    }

    public static ThresholdSensorImpl_Builder_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ThresholdSensorImpl_Builder_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public ThresholdSensorImpl.Builder get() {
        return newInstance((Resources) this.resourcesProvider.get(), (AsyncSensorManager) this.sensorManagerProvider.get(), (Execution) this.executionProvider.get());
    }
}
