package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.RealWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisabledWifiRepository implements RealWifiRepository {
    public static final DataActivityModel ACTIVITY;
    public static final WifiNetworkModel.Unavailable NETWORK;
    public final StateFlowImpl hideDuringMobileSwitching;
    public final ReadonlyStateFlow isWifiDefault;
    public final ReadonlyStateFlow isWifiEnabled;
    public final StateFlowImpl receivedInetCondition;
    public final ReadonlyStateFlow wifiActivity;
    public final StateFlowImpl wifiConnectivityTestReported;
    public final ReadonlyStateFlow wifiNetwork;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.wifiActivity = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(ACTIVITY));
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
}
