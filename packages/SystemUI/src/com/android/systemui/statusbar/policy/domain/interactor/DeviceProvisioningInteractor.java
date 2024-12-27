package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepository;
import com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceProvisioningInteractor {
    public final Flow isDeviceProvisioned;

    public DeviceProvisioningInteractor(DeviceProvisioningRepository deviceProvisioningRepository) {
        this.isDeviceProvisioned = ((DeviceProvisioningRepositoryImpl) deviceProvisioningRepository).isDeviceProvisioned;
    }
}
