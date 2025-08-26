package com.android.systemui.statusbar.policy.data.repository;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class DeviceProvisioningRepositoryImpl implements DeviceProvisioningRepository {
    public final DeviceProvisionedController deviceProvisionedController;
    public final Flow isDeviceProvisioned = FlowConflatedKt.conflatedCallbackFlow(new DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1(this, null));

    public DeviceProvisioningRepositoryImpl(DeviceProvisionedController deviceProvisionedController) {
        this.deviceProvisionedController = deviceProvisionedController;
    }
}
