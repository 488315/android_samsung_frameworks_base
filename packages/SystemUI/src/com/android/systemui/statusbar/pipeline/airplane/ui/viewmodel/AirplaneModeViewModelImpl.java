package com.android.systemui.statusbar.pipeline.airplane.ui.viewmodel;

import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AirplaneModeViewModelImpl implements AirplaneModeViewModel {
    public final ReadonlyStateFlow isAirplaneModeIconVisible;

    public AirplaneModeViewModelImpl(AirplaneModeInteractor airplaneModeInteractor, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope) {
        this.isAirplaneModeIconVisible = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(airplaneModeInteractor.isAirplaneMode, airplaneModeInteractor.isForceHidden, new AirplaneModeViewModelImpl$isAirplaneModeIconVisible$1(null))), tableLogBuffer, "", "isAirplaneModeIconVisible", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
    }
}
