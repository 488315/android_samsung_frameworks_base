package com.android.systemui.common.data.repository;

import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.util.kotlin.BatteryControllerExtKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BatteryRepositoryImpl implements BatteryRepository {
    public final ReadonlyStateFlow isDevicePluggedIn;

    public BatteryRepositoryImpl(CoroutineScope coroutineScope, BatteryController batteryController) {
        this.isDevicePluggedIn = FlowKt.stateIn(BatteryControllerExtKt.isDevicePluggedIn(batteryController), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(((BatteryControllerImpl) batteryController).mPluggedIn));
    }
}
