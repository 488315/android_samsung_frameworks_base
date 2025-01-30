package com.android.systemui.statusbar.pipeline.satellite.domain.interactor;

import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteInteractor {
    public final ReadonlyStateFlow areAllConnectionsOutOfService;
    public final ReadonlyStateFlow connectionState;
    public final ReadonlyStateFlow isSatelliteAllowed;
    public final ReadonlyStateFlow signalStrength;

    public DeviceBasedSatelliteInteractor(DeviceBasedSatelliteRepository deviceBasedSatelliteRepository, MobileIconsInteractor mobileIconsInteractor, CoroutineScope coroutineScope) {
        Boolean bool = Boolean.FALSE;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.isSatelliteAllowed = FlowKt.stateIn(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        SatelliteConnectionState satelliteConnectionState = SatelliteConnectionState.Off;
        this.connectionState = FlowKt.stateIn(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(satelliteConnectionState), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), satelliteConnectionState);
        this.signalStrength = FlowKt.stateIn(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        this.areAllConnectionsOutOfService = FlowKt.stateIn(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.TRUE);
    }
}
