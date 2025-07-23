package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.DevicePostureRepository;
import com.android.systemui.keyguard.data.repository.DevicePostureRepositoryImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DevicePostureInteractor {
    public final Flow posture;

    public DevicePostureInteractor(DevicePostureRepository devicePostureRepository) {
        this.posture = ((DevicePostureRepositoryImpl) devicePostureRepository).getCurrentDevicePosture();
    }
}
