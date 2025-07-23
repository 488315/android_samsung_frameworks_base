package com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.accessibility.hearingaid.HearingDevicesChecker;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HearingDevicesTileDataInteractor implements QSTileDataInteractor {
    public final CoroutineContext backgroundContext;
    public final BluetoothController bluetoothController;
    public final HearingDevicesChecker hearingDevicesChecker;

    public HearingDevicesTileDataInteractor(CoroutineContext coroutineContext, BluetoothController bluetoothController, HearingDevicesChecker hearingDevicesChecker) {
        this.backgroundContext = coroutineContext;
        this.bluetoothController = bluetoothController;
        this.hearingDevicesChecker = hearingDevicesChecker;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.distinctUntilChanged(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new HearingDevicesTileDataInteractor$tileData$1(this, null)), this.backgroundContext));
    }
}
