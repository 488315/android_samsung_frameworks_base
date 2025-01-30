package com.android.systemui.statusbar.pipeline.airplane.data.repository;

import android.os.Handler;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.settings.GlobalSettings;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AirplaneModeRepositoryImpl implements AirplaneModeRepository {
    public final Handler bgHandler;
    public final GlobalSettings globalSettings;
    public final ReadonlyStateFlow isAirplaneMode;

    public AirplaneModeRepositoryImpl(Handler handler, GlobalSettings globalSettings, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope) {
        this.bgHandler = handler;
        this.globalSettings = globalSettings;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AirplaneModeRepositoryImpl$isAirplaneMode$1 airplaneModeRepositoryImpl$isAirplaneMode$1 = new AirplaneModeRepositoryImpl$isAirplaneMode$1(this, null);
        conflatedCallbackFlow.getClass();
        this.isAirplaneMode = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(airplaneModeRepositoryImpl$isAirplaneMode$1)), tableLogBuffer, "", "isAirplaneMode", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion), Boolean.FALSE);
    }
}
