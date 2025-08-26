package com.android.systemui.common.data.repository;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.util.kotlin.BatteryControllerExtKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class BatteryRepositoryImpl implements BatteryRepository {
    public final ReadonlyStateFlow isDevicePluggedIn;

    public BatteryRepositoryImpl(CoroutineScope coroutineScope, BatteryController batteryController) {
        this.isDevicePluggedIn = FlowKt.stateIn(BatteryControllerExtKt.isDevicePluggedIn(batteryController), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(((BatteryControllerImpl) batteryController).mPluggedIn));
    }
}
