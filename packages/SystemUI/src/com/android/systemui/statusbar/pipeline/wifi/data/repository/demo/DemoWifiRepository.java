package com.android.systemui.statusbar.pipeline.wifi.data.repository.demo;

import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class DemoWifiRepository implements WifiRepository {
    public final StateFlowImpl _isWifiDefault;
    public final StateFlowImpl _isWifiEnabled;
    public final StateFlowImpl _secondaryNetworks;
    public final StateFlowImpl _wifiActivity;
    public final StateFlowImpl _wifiNetwork;
    public final StateFlowImpl _wifiScanResults;
    public final DemoModeWifiDataSource dataSource;
    public final StandaloneCoroutine demoCommandJob;
    public final StateFlowImpl hideDuringMobileSwitching;
    public final StateFlowImpl isWifiDefault;
    public final StateFlowImpl isWifiEnabled;
    public final StateFlowImpl receivedInetCondition;
    public final CoroutineScope scope;
    public final StateFlowImpl secondaryNetworks;
    public final StateFlowImpl wifiActivity;
    public final StateFlowImpl wifiConnectivityTestReported;
    public final StateFlowImpl wifiNetwork;
    public final StateFlowImpl wifiScanResults;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DemoWifiRepository(DemoModeWifiDataSource demoModeWifiDataSource, CoroutineScope coroutineScope) {
        this.dataSource = demoModeWifiDataSource;
        this.scope = coroutineScope;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isWifiEnabled = stateFlowImplMutableStateFlow;
        this.isWifiEnabled = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isWifiDefault = stateFlowImplMutableStateFlow2;
        this.isWifiDefault = stateFlowImplMutableStateFlow2;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(new WifiNetworkModel.Inactive(null, 1, null));
        this._wifiNetwork = stateFlowImplMutableStateFlow3;
        this.wifiNetwork = stateFlowImplMutableStateFlow3;
        EmptyList emptyList = EmptyList.INSTANCE;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(emptyList);
        this._secondaryNetworks = stateFlowImplMutableStateFlow4;
        this.secondaryNetworks = stateFlowImplMutableStateFlow4;
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(new DataActivityModel(false, false));
        this._wifiActivity = stateFlowImplMutableStateFlow5;
        this.wifiActivity = stateFlowImplMutableStateFlow5;
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(emptyList);
        this._wifiScanResults = stateFlowImplMutableStateFlow6;
        this.wifiScanResults = stateFlowImplMutableStateFlow6;
        this.hideDuringMobileSwitching = StateFlowKt.MutableStateFlow(bool);
        this.wifiConnectivityTestReported = StateFlowKt.MutableStateFlow(bool);
        this.receivedInetCondition = StateFlowKt.MutableStateFlow(-1);
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
}
