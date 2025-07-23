package com.android.systemui.statusbar.policy.data.repository;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceProvisioningRepositoryImpl implements DeviceProvisioningRepository {
    public final DeviceProvisionedController deviceProvisionedController;
    public final Flow isDeviceProvisioned = FlowConflatedKt.conflatedCallbackFlow(new DeviceProvisioningRepositoryImpl$isDeviceProvisioned$1(this, null));

    public DeviceProvisioningRepositoryImpl(DeviceProvisionedController deviceProvisionedController) {
        this.deviceProvisionedController = deviceProvisionedController;
    }
}
