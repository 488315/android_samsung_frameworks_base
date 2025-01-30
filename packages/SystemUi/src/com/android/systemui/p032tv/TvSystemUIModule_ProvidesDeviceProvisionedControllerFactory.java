package com.android.systemui.p032tv;

import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvSystemUIModule_ProvidesDeviceProvisionedControllerFactory implements Provider {
    public final Provider deviceProvisionedControllerProvider;

    public TvSystemUIModule_ProvidesDeviceProvisionedControllerFactory(Provider provider) {
        this.deviceProvisionedControllerProvider = provider;
    }

    public static void providesDeviceProvisionedController(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl) {
        deviceProvisionedControllerImpl.init();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.deviceProvisionedControllerProvider.get();
        deviceProvisionedControllerImpl.init();
        return deviceProvisionedControllerImpl;
    }
}
