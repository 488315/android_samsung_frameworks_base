package com.android.systemui.bixby2.controller;

import android.content.Context;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.RotationLockController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public static DeviceController_Factory create(Provider provider, Provider provider2, Provider provider3) {
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
