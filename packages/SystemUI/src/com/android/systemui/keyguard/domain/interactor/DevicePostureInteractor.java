package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.DevicePostureRepository;
import com.android.systemui.keyguard.data.repository.DevicePostureRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

public final class DevicePostureInteractor {
    public final Flow posture;

    public DevicePostureInteractor(DevicePostureRepository devicePostureRepository) {
        this.posture = ((DevicePostureRepositoryImpl) devicePostureRepository).getCurrentDevicePosture();
    }
}
