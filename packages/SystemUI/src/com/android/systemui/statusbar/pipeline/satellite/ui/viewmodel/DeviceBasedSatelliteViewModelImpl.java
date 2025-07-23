package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import android.content.Context;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteViewModelImpl implements DeviceBasedSatelliteViewModel {
    public static final long DELAY_DURATION;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 canShowIcon;
    public final ReadonlyStateFlow carrierText;
    public final ReadonlyStateFlow icon;
    public final ReadonlyStateFlow shouldShowIconForOosAfterHysteresis;
    public final ReadonlyStateFlow showIcon;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        DELAY_DURATION = DurationKt.toDuration(10, DurationUnit.SECONDS);
    }

    public DeviceBasedSatelliteViewModelImpl(Context context, DeviceBasedSatelliteInteractor deviceBasedSatelliteInteractor, CoroutineScope coroutineScope, AirplaneModeRepository airplaneModeRepository, LogBuffer logBuffer, TableLogBuffer tableLogBuffer) {
        Flow logDiffsForTable = deviceBasedSatelliteInteractor.isOpportunisticSatelliteIconEnabled ? DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.transformLatest(deviceBasedSatelliteInteractor.areAllConnectionsOutOfService, new DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1(null, logBuffer))), tableLogBuffer, "vm", "visibleForOos", false) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.shouldShowIconForOosAfterHysteresis = FlowKt.stateIn(logDiffsForTable, coroutineScope, WhileSubscribed$default, bool);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(deviceBasedSatelliteInteractor.isSatelliteAllowed, deviceBasedSatelliteInteractor.isSatelliteProvisioned, new DeviceBasedSatelliteViewModelImpl$canShowIcon$1(null));
        this.canShowIcon = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(deviceBasedSatelliteInteractor.isOpportunisticSatelliteIconEnabled ? DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2(null, this, deviceBasedSatelliteInteractor, airplaneModeRepository))), tableLogBuffer, "vm", "visible", false) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.showIcon = stateIn;
        DeviceBasedSatelliteViewModelImpl$icon$1 deviceBasedSatelliteViewModelImpl$icon$1 = new DeviceBasedSatelliteViewModelImpl$icon$1(null);
        ReadonlyStateFlow readonlyStateFlow = deviceBasedSatelliteInteractor.connectionState;
        this.icon = FlowKt.stateIn(FlowKt.combine(stateIn, readonlyStateFlow, deviceBasedSatelliteInteractor.signalStrength, deviceBasedSatelliteViewModelImpl$icon$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.carrierText = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, readonlyStateFlow, new DeviceBasedSatelliteViewModelImpl$carrierText$1(logBuffer, context, null))), tableLogBuffer, "vm", "carrierText", (String) null), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
    }
}
