package com.android.systemui.usb;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UsbAccessoryUriActivity_Factory implements Provider {
    public final Provider deviceProvisionedControllerProvider;

    public UsbAccessoryUriActivity_Factory(Provider provider) {
        this.deviceProvisionedControllerProvider = provider;
    }

    public static UsbAccessoryUriActivity newInstance(DeviceProvisionedController deviceProvisionedController) {
        return new UsbAccessoryUriActivity(deviceProvisionedController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new UsbAccessoryUriActivity((DeviceProvisionedController) this.deviceProvisionedControllerProvider.get());
    }
}
