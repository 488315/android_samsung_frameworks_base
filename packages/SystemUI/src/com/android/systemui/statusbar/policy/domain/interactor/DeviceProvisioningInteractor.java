package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepository;
import com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

public final class DeviceProvisioningInteractor {
    public final Flow isDeviceProvisioned;

    public DeviceProvisioningInteractor(DeviceProvisioningRepository deviceProvisioningRepository) {
        this.isDeviceProvisioned = ((DeviceProvisioningRepositoryImpl) deviceProvisioningRepository).isDeviceProvisioned;
    }
}
