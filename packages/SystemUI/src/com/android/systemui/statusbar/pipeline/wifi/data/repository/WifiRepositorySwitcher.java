package com.android.systemui.statusbar.pipeline.wifi.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoWifiRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WifiRepositorySwitcher implements WifiRepository {
    public final ReadonlyStateFlow activeRepo;
    public final DemoWifiRepository demoImpl;
    public final DemoModeController demoModeController;
    public final ReadonlyStateFlow hideDuringMobileSwitching;
    public final ReadonlyStateFlow isDemoMode;
    public final ReadonlyStateFlow isWifiDefault;
    public final ReadonlyStateFlow isWifiEnabled;
    public final RealWifiRepository realImpl;
    public final ReadonlyStateFlow receivedInetCondition;
    public final ReadonlyStateFlow secondaryNetworks;
    public final ReadonlyStateFlow wifiActivity;
    public final ReadonlyStateFlow wifiConnectivityTestReported;
    public final ReadonlyStateFlow wifiNetwork;
    public final ReadonlyStateFlow wifiScanResults;

    public WifiRepositorySwitcher(RealWifiRepository realWifiRepository, DemoWifiRepository demoWifiRepository, DemoModeController demoModeController, CoroutineScope coroutineScope) {
        this.realImpl = realWifiRepository;
        this.demoImpl = demoWifiRepository;
        this.demoModeController = demoModeController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        WifiRepositorySwitcher$isDemoMode$1 wifiRepositorySwitcher$isDemoMode$1 = new WifiRepositorySwitcher$isDemoMode$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(wifiRepositorySwitcher$isDemoMode$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(FlowKt.mapLatest(FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE), new WifiRepositorySwitcher$activeRepo$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository);
        this.isWifiEnabled = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.isWifiEnabled().getValue());
        this.isWifiDefault = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$2(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.isWifiDefault().getValue());
        this.wifiNetwork = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$3(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.getWifiNetwork().getValue());
        this.secondaryNetworks = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$4(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.getSecondaryNetworks().getValue());
        this.wifiActivity = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$5(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.getWifiActivity().getValue());
        this.wifiScanResults = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$6(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.getWifiScanResults().getValue());
        this.hideDuringMobileSwitching = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$7(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.getHideDuringMobileSwitching().getValue());
        this.wifiConnectivityTestReported = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$8(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.getWifiConnectivityTestReported().getValue());
        this.receivedInetCondition = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$9(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), realWifiRepository.getReceivedInetCondition().getValue());
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getHideDuringMobileSwitching() {
        return this.hideDuringMobileSwitching;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getReceivedInetCondition() {
        return this.receivedInetCondition;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getSecondaryNetworks() {
        return this.secondaryNetworks;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getWifiActivity() {
        return this.wifiActivity;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getWifiConnectivityTestReported() {
        return this.wifiConnectivityTestReported;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getWifiNetwork() {
        return this.wifiNetwork;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getWifiScanResults() {
        return this.wifiScanResults;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow isWifiDefault() {
        return this.isWifiDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow isWifiEnabled() {
        return this.isWifiEnabled;
    }

    public static /* synthetic */ void getActiveRepo$annotations() {
    }
}
