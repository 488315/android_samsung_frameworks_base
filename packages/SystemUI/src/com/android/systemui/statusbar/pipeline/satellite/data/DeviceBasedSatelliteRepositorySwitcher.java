package com.android.systemui.statusbar.pipeline.satellite.data;

import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.satellite.data.demo.DemoDeviceBasedSatelliteRepository;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteRepositorySwitcher implements DeviceBasedSatelliteRepository {
    public final ReadonlyStateFlow activeRepo;
    public final ReadonlyStateFlow connectionState;
    public final DemoDeviceBasedSatelliteRepository demoImpl;
    public final DemoModeController demoModeController;
    public final ReadonlyStateFlow isDemoMode;
    public final ReadonlyStateFlow isSatelliteAllowedForCurrentLocation;
    public final ReadonlyStateFlow isSatelliteProvisioned;
    public final RealDeviceBasedSatelliteRepository realImpl;
    public final ReadonlyStateFlow signalStrength;

    public DeviceBasedSatelliteRepositorySwitcher(RealDeviceBasedSatelliteRepository realDeviceBasedSatelliteRepository, DemoDeviceBasedSatelliteRepository demoDeviceBasedSatelliteRepository, DemoModeController demoModeController, CoroutineScope coroutineScope) {
        this.realImpl = realDeviceBasedSatelliteRepository;
        this.demoImpl = demoDeviceBasedSatelliteRepository;
        this.demoModeController = demoModeController;
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositorySwitcher$isDemoMode$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        demoModeController.getClass();
        ReadonlyStateFlow stateIn = FlowKt.stateIn(conflatedCallbackFlow, coroutineScope, WhileSubscribed$default, Boolean.FALSE);
        this.isDemoMode = stateIn;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.mapLatest(stateIn, new DeviceBasedSatelliteRepositorySwitcher$activeRepo$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realDeviceBasedSatelliteRepository);
        this.activeRepo = stateIn2;
        DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = (DeviceBasedSatelliteRepositoryImpl) realDeviceBasedSatelliteRepository;
        this.isSatelliteProvisioned = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new DeviceBasedSatelliteRepositorySwitcher$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), deviceBasedSatelliteRepositoryImpl.isSatelliteProvisioned.$$delegate_0.getValue());
        this.connectionState = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new DeviceBasedSatelliteRepositorySwitcher$special$$inlined$flatMapLatest$2(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), deviceBasedSatelliteRepositoryImpl.connectionState.$$delegate_0.getValue());
        this.signalStrength = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new DeviceBasedSatelliteRepositorySwitcher$special$$inlined$flatMapLatest$3(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), deviceBasedSatelliteRepositoryImpl.signalStrength.$$delegate_0.getValue());
        this.isSatelliteAllowedForCurrentLocation = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new DeviceBasedSatelliteRepositorySwitcher$special$$inlined$flatMapLatest$4(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), deviceBasedSatelliteRepositoryImpl.isSatelliteAllowedForCurrentLocation.$$delegate_0.getValue());
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow getConnectionState() {
        return this.connectionState;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow getSignalStrength() {
        return this.signalStrength;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final boolean isOpportunisticSatelliteIconEnabled() {
        return ((DeviceBasedSatelliteRepository) this.activeRepo.$$delegate_0.getValue()).isOpportunisticSatelliteIconEnabled();
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow isSatelliteAllowedForCurrentLocation() {
        return this.isSatelliteAllowedForCurrentLocation;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow isSatelliteProvisioned() {
        return this.isSatelliteProvisioned;
    }

    public static /* synthetic */ void getActiveRepo$annotations() {
    }
}
