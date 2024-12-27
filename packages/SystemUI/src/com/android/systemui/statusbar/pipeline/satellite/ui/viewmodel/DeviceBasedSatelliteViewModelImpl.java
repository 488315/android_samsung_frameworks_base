package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import android.content.Context;
import com.android.systemui.log.LogBuffer;
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
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

public final class DeviceBasedSatelliteViewModelImpl implements DeviceBasedSatelliteViewModel {
    public static final long DELAY_DURATION;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 canShowIcon;
    public final ReadonlyStateFlow carrierText;
    public final ReadonlyStateFlow icon;
    public final ReadonlyStateFlow shouldShowIconForOosAfterHysteresis;
    public final ReadonlyStateFlow showIcon;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        DELAY_DURATION = DurationKt.toDuration(10, DurationUnit.SECONDS);
    }

    public DeviceBasedSatelliteViewModelImpl(Context context, DeviceBasedSatelliteInteractor deviceBasedSatelliteInteractor, CoroutineScope coroutineScope, AirplaneModeRepository airplaneModeRepository, LogBuffer logBuffer) {
        boolean z = deviceBasedSatelliteInteractor.isOpportunisticSatelliteIconEnabled;
        Flow distinctUntilChanged = z ? FlowKt.distinctUntilChanged(FlowKt.transformLatest(deviceBasedSatelliteInteractor.areAllConnectionsOutOfService, new DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1(null, logBuffer))) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.shouldShowIconForOosAfterHysteresis = FlowKt.stateIn(distinctUntilChanged, coroutineScope, WhileSubscribed$default, bool);
        ReadonlyStateFlow stateIn = FlowKt.stateIn(z ? FlowKt.distinctUntilChanged(FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(deviceBasedSatelliteInteractor.isSatelliteAllowed, deviceBasedSatelliteInteractor.isSatelliteProvisioned, new DeviceBasedSatelliteViewModelImpl$canShowIcon$1(null)), new DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2(null, this, deviceBasedSatelliteInteractor, airplaneModeRepository))) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        DeviceBasedSatelliteViewModelImpl$icon$1 deviceBasedSatelliteViewModelImpl$icon$1 = new DeviceBasedSatelliteViewModelImpl$icon$1(null);
        ReadonlyStateFlow readonlyStateFlow = deviceBasedSatelliteInteractor.connectionState;
        this.icon = FlowKt.stateIn(FlowKt.combine(stateIn, readonlyStateFlow, deviceBasedSatelliteInteractor.signalStrength, deviceBasedSatelliteViewModelImpl$icon$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.carrierText = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, readonlyStateFlow, new DeviceBasedSatelliteViewModelImpl$carrierText$1(logBuffer, context, null))), new DeviceBasedSatelliteViewModelImpl$carrierText$2(logBuffer, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
    }
}
