package com.android.systemui.bixby2.controller;

import android.content.Context;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.RotationLockController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceController_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider flashlightControllerProvider;
    private final javax.inject.Provider rotationLockControllerProvider;

    public DeviceController_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.contextProvider = provider;
        this.flashlightControllerProvider = provider2;
        this.rotationLockControllerProvider = provider3;
    }

    public static DeviceController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new DeviceController_Factory(provider, provider2, provider3);
    }

    public static DeviceController newInstance(Context context, FlashlightController flashlightController, RotationLockController rotationLockController) {
        return new DeviceController(context, flashlightController, rotationLockController);
    }

    @Override // javax.inject.Provider
    public DeviceController get() {
        return newInstance((Context) this.contextProvider.get(), (FlashlightController) this.flashlightControllerProvider.get(), (RotationLockController) this.rotationLockControllerProvider.get());
    }
}
