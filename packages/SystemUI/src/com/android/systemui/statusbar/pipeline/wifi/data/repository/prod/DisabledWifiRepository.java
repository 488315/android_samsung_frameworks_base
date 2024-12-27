package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.RealWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class DisabledWifiRepository implements RealWifiRepository {
    public static final DataActivityModel ACTIVITY;
    public static final WifiNetworkModel.Unavailable NETWORK;
    public final StateFlowImpl hideDuringMobileSwitching;
    public final ReadonlyStateFlow isWifiDefault;
    public final ReadonlyStateFlow isWifiEnabled;
    public final StateFlowImpl receivedInetCondition;
    public final ReadonlyStateFlow secondaryNetworks;
    public final ReadonlyStateFlow wifiActivity;
    public final StateFlowImpl wifiConnectivityTestReported;
    public final ReadonlyStateFlow wifiNetwork;
    public final ReadonlyStateFlow wifiScanResults;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        NETWORK = WifiNetworkModel.Unavailable.INSTANCE;
        ACTIVITY = new DataActivityModel(false, false);
    }

    public DisabledWifiRepository() {
        Boolean bool = Boolean.FALSE;
        this.isWifiEnabled = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(bool));
        this.isWifiDefault = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(bool));
        this.wifiNetwork = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(NETWORK));
        EmptyList emptyList = EmptyList.INSTANCE;
        this.secondaryNetworks = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(emptyList));
        this.wifiActivity = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(ACTIVITY));
        this.wifiScanResults = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(emptyList));
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
