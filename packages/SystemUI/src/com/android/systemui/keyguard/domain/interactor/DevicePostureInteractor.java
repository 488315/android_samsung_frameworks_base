package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.DevicePostureRepository;
import com.android.systemui.keyguard.data.repository.DevicePostureRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DevicePostureInteractor {
    public final Flow posture;

    public DevicePostureInteractor(DevicePostureRepository devicePostureRepository) {
        this.posture = ((DevicePostureRepositoryImpl) devicePostureRepository).getCurrentDevicePosture();
    }
}
