package com.android.systemui.statusbar.policy.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeviceProvisioningRepositoryImpl implements DeviceProvisioningRepository {
    public final DeviceProvisionedController deviceProvisionedController;
    public final Flow isDeviceProvisioned;

    public DeviceProvisioningRepositoryImpl(DeviceProvisionedController deviceProvisionedController) {
        this.deviceProvisionedController = deviceProvisionedController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1 deviceProvisioningRepositoryImpl$isDeviceProvisioned$1 = new DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1(this, null);
        conflatedCallbackFlow.getClass();
        this.isDeviceProvisioned = FlowConflatedKt.conflatedCallbackFlow(deviceProvisioningRepositoryImpl$isDeviceProvisioned$1);
    }
}
