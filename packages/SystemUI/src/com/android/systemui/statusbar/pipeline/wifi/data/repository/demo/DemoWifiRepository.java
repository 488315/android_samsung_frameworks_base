package com.android.systemui.statusbar.pipeline.wifi.data.repository.demo;

import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DemoWifiRepository implements WifiRepository {
    public final StateFlowImpl _isWifiDefault;
    public final StateFlowImpl _isWifiEnabled;
    public final StateFlowImpl _secondaryNetworks;
    public final StateFlowImpl _wifiActivity;
    public final StateFlowImpl _wifiNetwork;
    public final StateFlowImpl _wifiScanResults;
    public final DemoModeWifiDataSource dataSource;
    public final Job demoCommandJob;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DemoWifiRepository(DemoModeWifiDataSource demoModeWifiDataSource, CoroutineScope coroutineScope) {
        Boolean bool = Boolean.FALSE;
        this.isWifiEnabled = StateFlowKt.MutableStateFlow(bool);
        this.isWifiDefault = StateFlowKt.MutableStateFlow(bool);
        this.wifiNetwork = StateFlowKt.MutableStateFlow(WifiNetworkModel.Inactive.INSTANCE);
        EmptyList emptyList = EmptyList.INSTANCE;
        this.secondaryNetworks = StateFlowKt.MutableStateFlow(emptyList);
        this.wifiActivity = StateFlowKt.MutableStateFlow(new DataActivityModel(false, false));
        this.wifiScanResults = StateFlowKt.MutableStateFlow(emptyList);
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
