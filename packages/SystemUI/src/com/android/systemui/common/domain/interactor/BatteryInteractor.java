package com.android.systemui.common.domain.interactor;

import com.android.systemui.common.data.repository.BatteryRepository;
import com.android.systemui.common.data.repository.BatteryRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes.dex */
public final class BatteryInteractor {
    public final ReadonlyStateFlow isDevicePluggedIn;

    public BatteryInteractor(BatteryRepository batteryRepository) {
        this.isDevicePluggedIn = ((BatteryRepositoryImpl) batteryRepository).isDevicePluggedIn;
    }
}
