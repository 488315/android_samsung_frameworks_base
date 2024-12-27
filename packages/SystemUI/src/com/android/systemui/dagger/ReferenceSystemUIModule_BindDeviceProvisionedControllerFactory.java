package com.android.systemui.dagger;

import android.net.Uri;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl$observer$1;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Provider;

public final class ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory implements Provider {
    public final javax.inject.Provider deviceProvisionedControllerProvider;

    public ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory(javax.inject.Provider provider) {
        this.deviceProvisionedControllerProvider = provider;
    }

    public static void bindDeviceProvisionedController(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl) {
        if (deviceProvisionedControllerImpl.initted.compareAndSet(false, true)) {
            deviceProvisionedControllerImpl.dumpManager.registerDumpable(deviceProvisionedControllerImpl);
            deviceProvisionedControllerImpl.updateValues(-1, true);
            ((UserTrackerImpl) deviceProvisionedControllerImpl.userTracker).addCallback(deviceProvisionedControllerImpl.userChangedCallback, deviceProvisionedControllerImpl.backgroundExecutor);
            Uri uri = deviceProvisionedControllerImpl.deviceProvisionedUri;
            GlobalSettings globalSettings = deviceProvisionedControllerImpl.globalSettings;
            DeviceProvisionedControllerImpl$observer$1 deviceProvisionedControllerImpl$observer$1 = deviceProvisionedControllerImpl.observer;
            globalSettings.registerContentObserverSync(uri, deviceProvisionedControllerImpl$observer$1);
            deviceProvisionedControllerImpl.secureSettings.registerContentObserverForUserSync(deviceProvisionedControllerImpl.userSetupUri, deviceProvisionedControllerImpl$observer$1, -1);
        }
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) this.deviceProvisionedControllerProvider.get();
        bindDeviceProvisionedController(deviceProvisionedControllerImpl);
        return deviceProvisionedControllerImpl;
    }
}
