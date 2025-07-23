package com.android.systemui.qs.tiles.impl.battery.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.kotlin.BatteryControllerExtKt;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class BatterySaverTileDataInteractor implements QSTileDataInteractor {
    public final BatteryController batteryController;
    public final CoroutineContext bgCoroutineContext;

    public BatterySaverTileDataInteractor(CoroutineContext coroutineContext, BatteryController batteryController) {
        this.bgCoroutineContext = coroutineContext;
        this.batteryController = batteryController;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        BatteryController batteryController = this.batteryController;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(BatteryControllerExtKt.isDevicePluggedIn(batteryController));
        CoroutineContext coroutineContext = this.bgCoroutineContext;
        return FlowKt.combine(FlowKt.flowOn(distinctUntilChanged, coroutineContext), FlowKt.flowOn(FlowKt.distinctUntilChanged(BatteryControllerExtKt.isBatteryPowerSaveEnabled(batteryController)), coroutineContext), FlowKt.flowOn(FlowKt.distinctUntilChanged(BatteryControllerExtKt.getBatteryLevel(batteryController)), coroutineContext), new BatterySaverTileDataInteractor$tileData$1(null));
    }
}
