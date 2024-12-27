package com.android.systemui.statusbar.pipeline.airplane.ui.viewmodel;

import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AirplaneModeViewModelImpl implements AirplaneModeViewModel {
    public final ReadonlyStateFlow isAirplaneModeIconVisible;

    public AirplaneModeViewModelImpl(AirplaneModeInteractor airplaneModeInteractor, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope) {
        FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(airplaneModeInteractor.isAirplaneMode, airplaneModeInteractor.isForceHidden, new AirplaneModeViewModelImpl$isAirplaneModeIconVisible$1(null))), tableLogBuffer, "", "isAirplaneModeIconVisible", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }
}
