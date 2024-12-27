package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.IPackageManager;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.internal.Provider;

public final class DeviceProvisionedCoordinator_Factory implements Provider {
    private final javax.inject.Provider deviceProvisionedControllerProvider;
    private final javax.inject.Provider packageManagerProvider;

    public DeviceProvisionedCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.deviceProvisionedControllerProvider = provider;
        this.packageManagerProvider = provider2;
    }

    public static DeviceProvisionedCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DeviceProvisionedCoordinator_Factory(provider, provider2);
    }

    public static DeviceProvisionedCoordinator newInstance(DeviceProvisionedController deviceProvisionedController, IPackageManager iPackageManager) {
        return new DeviceProvisionedCoordinator(deviceProvisionedController, iPackageManager);
    }

    @Override // javax.inject.Provider
    public DeviceProvisionedCoordinator get() {
        return newInstance((DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (IPackageManager) this.packageManagerProvider.get());
    }
}
