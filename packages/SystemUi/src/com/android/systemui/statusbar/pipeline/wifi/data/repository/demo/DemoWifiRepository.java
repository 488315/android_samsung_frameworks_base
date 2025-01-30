package com.android.systemui.statusbar.pipeline.wifi.data.repository.demo;

import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DemoWifiRepository implements WifiRepository {
    public final StateFlowImpl _isWifiDefault;
    public final StateFlowImpl _isWifiEnabled;
    public final StateFlowImpl _wifiActivity;
    public final StateFlowImpl _wifiNetwork;
    public final DemoModeWifiDataSource dataSource;
    public StandaloneCoroutine demoCommandJob;
    public final StateFlowImpl hideDuringMobileSwitching;
    public final StateFlowImpl isWifiDefault;
    public final StateFlowImpl isWifiEnabled;
    public final StateFlowImpl receivedInetCondition;
    public final CoroutineScope scope;
    public final StateFlowImpl wifiActivity;
    public final StateFlowImpl wifiConnectivityTestReported;
    public final StateFlowImpl wifiNetwork;

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
    }

    public DemoWifiRepository(DemoModeWifiDataSource demoModeWifiDataSource, CoroutineScope coroutineScope) {
        this.dataSource = demoModeWifiDataSource;
        this.scope = coroutineScope;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isWifiEnabled = MutableStateFlow;
        this.isWifiEnabled = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isWifiDefault = MutableStateFlow2;
        this.isWifiDefault = MutableStateFlow2;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(WifiNetworkModel.Inactive.INSTANCE);
        this._wifiNetwork = MutableStateFlow3;
        this.wifiNetwork = MutableStateFlow3;
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(new DataActivityModel(false, false));
        this._wifiActivity = MutableStateFlow4;
        this.wifiActivity = MutableStateFlow4;
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
