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
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        ReadonlyStateFlow stateIn = FlowKt.stateIn(FlowKt.mapLatest(FlowKt.stateIn(conflatedCallbackFlow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE), new DeviceBasedSatelliteRepositorySwitcher$activeRepo$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realDeviceBasedSatelliteRepository);
        this.activeRepo = stateIn;
        DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = (DeviceBasedSatelliteRepositoryImpl) realDeviceBasedSatelliteRepository;
        this.isSatelliteProvisioned = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new DeviceBasedSatelliteRepositorySwitcher$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), deviceBasedSatelliteRepositoryImpl.isSatelliteProvisioned.$$delegate_0.getValue());
        this.connectionState = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new DeviceBasedSatelliteRepositorySwitcher$special$$inlined$flatMapLatest$2(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), deviceBasedSatelliteRepositoryImpl.connectionState.$$delegate_0.getValue());
        this.signalStrength = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new DeviceBasedSatelliteRepositorySwitcher$special$$inlined$flatMapLatest$3(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), deviceBasedSatelliteRepositoryImpl.signalStrength.$$delegate_0.getValue());
        this.isSatelliteAllowedForCurrentLocation = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new DeviceBasedSatelliteRepositorySwitcher$special$$inlined$flatMapLatest$4(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), deviceBasedSatelliteRepositoryImpl.isSatelliteAllowedForCurrentLocation.getValue());
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
