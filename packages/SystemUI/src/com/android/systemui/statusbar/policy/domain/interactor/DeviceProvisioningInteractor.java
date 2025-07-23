package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepository;
import com.android.systemui.statusbar.policy.data.repository.DeviceProvisioningRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceProvisioningInteractor {
    public final Flow isDeviceProvisioned;

    public DeviceProvisioningInteractor(DeviceProvisioningRepository deviceProvisioningRepository) {
        this.isDeviceProvisioned = ((DeviceProvisioningRepositoryImpl) deviceProvisioningRepository).isDeviceProvisioned;
    }
}
