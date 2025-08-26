package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.IPackageManager;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class DeviceProvisionedCoordinator_Factory implements Provider {
    private final Provider deviceProvisionedControllerProvider;
    private final Provider packageManagerProvider;

    public DeviceProvisionedCoordinator_Factory(Provider provider, Provider provider2) {
        this.deviceProvisionedControllerProvider = provider;
        this.packageManagerProvider = provider2;
    }

    public static DeviceProvisionedCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new DeviceProvisionedCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static DeviceProvisionedCoordinator newInstance(DeviceProvisionedController deviceProvisionedController, IPackageManager iPackageManager) {
        return new DeviceProvisionedCoordinator(deviceProvisionedController, iPackageManager);
    }

    public static DeviceProvisionedCoordinator_Factory create(Provider provider, Provider provider2) {
        return new DeviceProvisionedCoordinator_Factory(provider, provider2);
    }

    @Override // javax.inject.Provider
    public DeviceProvisionedCoordinator get() {
        return newInstance((DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (IPackageManager) this.packageManagerProvider.get());
    }
}
