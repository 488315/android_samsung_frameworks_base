package com.android.systemui.statusbar.pipeline.wifi.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoWifiRepository;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final ReadonlyStateFlow wifiActivity;
    public final ReadonlyStateFlow wifiConnectivityTestReported;
    public final ReadonlyStateFlow wifiNetwork;

    public WifiRepositorySwitcher(RealWifiRepository realWifiRepository, DemoWifiRepository demoWifiRepository, DemoModeController demoModeController, CoroutineScope coroutineScope) {
        this.realImpl = realWifiRepository;
        this.demoImpl = demoWifiRepository;
        this.demoModeController = demoModeController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        WifiRepositorySwitcher$isDemoMode$1 wifiRepositorySwitcher$isDemoMode$1 = new WifiRepositorySwitcher$isDemoMode$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(wifiRepositorySwitcher$isDemoMode$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        demoModeController.getClass();
        ReadonlyStateFlow stateIn = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, WhileSubscribed$default, Boolean.FALSE);
        this.isDemoMode = stateIn;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.mapLatest(stateIn, new WifiRepositorySwitcher$activeRepo$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), realWifiRepository);
        this.activeRepo = stateIn2;
        this.isWifiEnabled = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), realWifiRepository.isWifiEnabled().getValue());
        this.isWifiDefault = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$2(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), realWifiRepository.isWifiDefault().getValue());
        this.wifiNetwork = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$3(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), realWifiRepository.getWifiNetwork().getValue());
        this.wifiActivity = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$4(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), realWifiRepository.getWifiActivity().getValue());
        this.hideDuringMobileSwitching = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$5(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), realWifiRepository.getHideDuringMobileSwitching().getValue());
        this.wifiConnectivityTestReported = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$6(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), realWifiRepository.getWifiConnectivityTestReported().getValue());
        this.receivedInetCondition = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new WifiRepositorySwitcher$special$$inlined$flatMapLatest$7(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), realWifiRepository.getReceivedInetCondition().getValue());
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
    public final boolean isWifiConnectedWithValidSsid() {
        return WifiRepository.DefaultImpls.isWifiConnectedWithValidSsid(this);
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
