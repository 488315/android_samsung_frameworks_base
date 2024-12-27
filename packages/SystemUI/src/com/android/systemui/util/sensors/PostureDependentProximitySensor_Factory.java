package com.android.systemui.util.sensors;

import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import dagger.internal.Provider;

public final class PostureDependentProximitySensor_Factory implements Provider {
    private final javax.inject.Provider delayableExecutorProvider;
    private final javax.inject.Provider devicePostureControllerProvider;
    private final javax.inject.Provider executionProvider;
    private final javax.inject.Provider postureToPrimaryProxSensorMapProvider;
    private final javax.inject.Provider postureToSecondaryProxSensorMapProvider;

    public PostureDependentProximitySensor_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.postureToPrimaryProxSensorMapProvider = provider;
        this.postureToSecondaryProxSensorMapProvider = provider2;
        this.delayableExecutorProvider = provider3;
        this.executionProvider = provider4;
        this.devicePostureControllerProvider = provider5;
    }

    public static PostureDependentProximitySensor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new PostureDependentProximitySensor_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static PostureDependentProximitySensor newInstance(ThresholdSensor[] thresholdSensorArr, ThresholdSensor[] thresholdSensorArr2, DelayableExecutor delayableExecutor, Execution execution, DevicePostureController devicePostureController) {
        return new PostureDependentProximitySensor(thresholdSensorArr, thresholdSensorArr2, delayableExecutor, execution, devicePostureController);
    }

    @Override // javax.inject.Provider
    public PostureDependentProximitySensor get() {
        return newInstance((ThresholdSensor[]) this.postureToPrimaryProxSensorMapProvider.get(), (ThresholdSensor[]) this.postureToSecondaryProxSensorMapProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get(), (Execution) this.executionProvider.get(), (DevicePostureController) this.devicePostureControllerProvider.get());
    }
}
