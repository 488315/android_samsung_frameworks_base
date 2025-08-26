package com.android.systemui.bixby2.controller;

import android.content.Context;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.RotationLockController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class DeviceController_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider flashlightControllerProvider;
    private final Provider rotationLockControllerProvider;

    public DeviceController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.flashlightControllerProvider = provider2;
        this.rotationLockControllerProvider = provider3;
    }

    public static DeviceController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new DeviceController_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3));
    }

    public static DeviceController newInstance(Context context, FlashlightController flashlightController, RotationLockController rotationLockController) {
        return new DeviceController(context, flashlightController, rotationLockController);
    }

    public static DeviceController_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new DeviceController_Factory(provider, provider2, provider3);
    }

    @Override // javax.inject.Provider
    public DeviceController get() {
        return newInstance((Context) this.contextProvider.get(), (FlashlightController) this.flashlightControllerProvider.get(), (RotationLockController) this.rotationLockControllerProvider.get());
    }
}
