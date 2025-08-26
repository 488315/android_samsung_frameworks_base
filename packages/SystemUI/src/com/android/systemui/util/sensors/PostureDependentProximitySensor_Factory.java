package com.android.systemui.util.sensors;

import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class PostureDependentProximitySensor_Factory implements Provider {
    private final Provider delayableExecutorProvider;
    private final Provider devicePostureControllerProvider;
    private final Provider executionProvider;
    private final Provider postureToPrimaryProxSensorMapProvider;
    private final Provider postureToSecondaryProxSensorMapProvider;

    public PostureDependentProximitySensor_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.postureToPrimaryProxSensorMapProvider = provider;
        this.postureToSecondaryProxSensorMapProvider = provider2;
        this.delayableExecutorProvider = provider3;
        this.executionProvider = provider4;
        this.devicePostureControllerProvider = provider5;
    }

    public static PostureDependentProximitySensor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new PostureDependentProximitySensor_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static PostureDependentProximitySensor newInstance(ThresholdSensor[] thresholdSensorArr, ThresholdSensor[] thresholdSensorArr2, DelayableExecutor delayableExecutor, Execution execution, DevicePostureController devicePostureController) {
        return new PostureDependentProximitySensor(thresholdSensorArr, thresholdSensorArr2, delayableExecutor, execution, devicePostureController);
    }

    public static PostureDependentProximitySensor_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new PostureDependentProximitySensor_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public PostureDependentProximitySensor get() {
        return newInstance((ThresholdSensor[]) this.postureToPrimaryProxSensorMapProvider.get(), (ThresholdSensor[]) this.postureToSecondaryProxSensorMapProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get(), (Execution) this.executionProvider.get(), (DevicePostureController) this.devicePostureControllerProvider.get());
    }
}
