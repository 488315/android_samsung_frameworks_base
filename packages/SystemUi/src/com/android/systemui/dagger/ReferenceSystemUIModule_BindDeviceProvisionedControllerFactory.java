package com.android.systemui.dagger;

import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory implements Provider {
    public final Provider deviceProvisionedControllerProvider;

    public ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory(Provider provider) {
        this.deviceProvisionedControllerProvider = provider;
    }

    public static void bindDeviceProvisionedController(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl) {
        deviceProvisionedControllerImpl.init();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.deviceProvisionedControllerProvider.get();
        deviceProvisionedControllerImpl.init();
        return deviceProvisionedControllerImpl;
    }
}
