package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceStateKt;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimCardModelKt;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegStateKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DemoMobileConnectionRepository implements MobileConnectionRepository {
    public final StateFlowImpl _carrierId;
    public final StateFlowImpl _carrierNetworkChangeActive;
    public final StateFlowImpl _cdmaLevel;
    public final StateFlowImpl _dataActivityDirection;
    public final StateFlowImpl _dataConnectionState;
    public final StateFlowImpl _isEmergencyOnly;
    public final StateFlowImpl _isGsm;
    public final StateFlowImpl _isInService;
    public final StateFlowImpl _isNonTerrestrial;
    public final StateFlowImpl _isRoaming;
    public final StateFlowImpl _operatorAlphaShort;
    public final StateFlowImpl _primaryLevel;
    public final StateFlowImpl _resolvedNetworkType;
    public final ReadonlyStateFlow carrierId;
    public final ReadonlyStateFlow carrierNetworkChangeActive;
    public final ReadonlyStateFlow cdmaLevel;
    public final StateFlowImpl cdmaRoaming;
    public final ReadonlyStateFlow dataActivityDirection;
    public final ReadonlyStateFlow dataConnectionState;
    public final StateFlowImpl dataEnabled;
    public final StateFlowImpl imsRegState;
    public final ReadonlyStateFlow isEmergencyOnly;
    public final ReadonlyStateFlow isGsm;
    public final ReadonlyStateFlow isInService;
    public final ReadonlyStateFlow isNonTerrestrial;
    public final ReadonlyStateFlow isRoaming;
    public final StateFlowImpl mobileDataEnabledChanged;
    public final StateFlowImpl mobileServiceState;
    public final StateFlowImpl networkName;
    public final StateFlowImpl numberOfLevels;
    public final StateFlowImpl onTheCall;
    public final ReadonlyStateFlow operatorAlphaShort;
    public final ReadonlyStateFlow primaryLevel;
    public final ReadonlyStateFlow resolvedNetworkType;
    public final StateFlowImpl sim1On;
    public final StateFlowImpl simCardInfo;
    public final int subId;
    public final StateFlowImpl swRoaming;
    public final TableLogBuffer tableLogBuffer;

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

    public DemoMobileConnectionRepository(int i, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope) {
        this.subId = i;
        this.tableLogBuffer = tableLogBuffer;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(-1);
        this._carrierId = MutableStateFlow;
        SafeFlow logDiffsForTable = DiffableKt.logDiffsForTable(MutableStateFlow, tableLogBuffer, "", "carrierId", ((Number) MutableStateFlow.getValue()).intValue());
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.carrierId = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow.getValue());
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isEmergencyOnly = MutableStateFlow2;
        this.isEmergencyOnly = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow2, tableLogBuffer, "", "emergencyOnly", ((Boolean) MutableStateFlow2.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow2.getValue());
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isRoaming = MutableStateFlow3;
        this.isRoaming = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow3, tableLogBuffer, "", "roaming", ((Boolean) MutableStateFlow3.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow3.getValue());
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._operatorAlphaShort = MutableStateFlow4;
        this.operatorAlphaShort = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow4, tableLogBuffer, "operatorName", (String) MutableStateFlow4.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow4.getValue());
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._isInService = MutableStateFlow5;
        this.isInService = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow5, tableLogBuffer, "", "isInService", ((Boolean) MutableStateFlow5.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow5.getValue());
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._isNonTerrestrial = MutableStateFlow6;
        this.isNonTerrestrial = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow6, tableLogBuffer, "", "isNtn", ((Boolean) MutableStateFlow6.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow6.getValue());
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._isGsm = MutableStateFlow7;
        this.isGsm = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow7, tableLogBuffer, "", "isGsm", ((Boolean) MutableStateFlow7.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow7.getValue());
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(0);
        this._cdmaLevel = MutableStateFlow8;
        this.cdmaLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow8, tableLogBuffer, "", "cdmaLevel", ((Number) MutableStateFlow8.getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow8.getValue());
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(0);
        this._primaryLevel = MutableStateFlow9;
        this.primaryLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow9, tableLogBuffer, "", "primaryLevel", ((Number) MutableStateFlow9.getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow9.getValue());
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(DataConnectionState.Disconnected);
        this._dataConnectionState = MutableStateFlow10;
        this.dataConnectionState = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow10, tableLogBuffer, "", (Diffable) MutableStateFlow10.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow10.getValue());
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(new DataActivityModel(false, false));
        this._dataActivityDirection = MutableStateFlow11;
        this.dataActivityDirection = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow11, tableLogBuffer, "", (Diffable) MutableStateFlow11.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow11.getValue());
        StateFlowImpl MutableStateFlow12 = StateFlowKt.MutableStateFlow(bool);
        this._carrierNetworkChangeActive = MutableStateFlow12;
        this.carrierNetworkChangeActive = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow12, tableLogBuffer, "", "carrierNetworkChangeActive", ((Boolean) MutableStateFlow12.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow12.getValue());
        StateFlowImpl MutableStateFlow13 = StateFlowKt.MutableStateFlow(ResolvedNetworkType.UnknownNetworkType.INSTANCE);
        this._resolvedNetworkType = MutableStateFlow13;
        this.resolvedNetworkType = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow13, tableLogBuffer, "", (Diffable) MutableStateFlow13.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), MutableStateFlow13.getValue());
        this.numberOfLevels = StateFlowKt.MutableStateFlow(4);
        this.dataEnabled = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this.cdmaRoaming = StateFlowKt.MutableStateFlow(bool);
        this.swRoaming = StateFlowKt.MutableStateFlow(bool);
        this.networkName = StateFlowKt.MutableStateFlow(new NetworkNameModel.IntentDerived("demo network"));
        this.simCardInfo = StateFlowKt.MutableStateFlow(SimCardModelKt.NO_SIM_MODEL);
        this.sim1On = StateFlowKt.MutableStateFlow(bool);
        this.onTheCall = StateFlowKt.MutableStateFlow(bool);
        this.mobileServiceState = StateFlowKt.MutableStateFlow(MobileServiceStateKt.DEFAULT_SERVICE_STATE);
        this.imsRegState = StateFlowKt.MutableStateFlow(ImsRegStateKt.DEFAULT_IMS_REG_STATE);
        this.mobileDataEnabledChanged = StateFlowKt.MutableStateFlow(bool);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCarrierId() {
        return this.carrierId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCarrierNetworkChangeActive() {
        return this.carrierNetworkChangeActive;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCdmaLevel() {
        return this.cdmaLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCdmaRoaming() {
        return this.cdmaRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getDataActivityDirection() {
        return this.dataActivityDirection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getDataConnectionState() {
        return this.dataConnectionState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getDataEnabled() {
        return this.dataEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getImsRegState() {
        return this.imsRegState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getMobileDataEnabledChanged() {
        return this.mobileDataEnabledChanged;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getMobileServiceState() {
        return this.mobileServiceState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getNetworkName() {
        return this.networkName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getNumberOfLevels() {
        return this.numberOfLevels;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getOnTheCall() {
        return this.onTheCall;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getOperatorAlphaShort() {
        return this.operatorAlphaShort;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getPrimaryLevel() {
        return this.primaryLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getResolvedNetworkType() {
        return this.resolvedNetworkType;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSim1On() {
        return this.sim1On;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSimCardInfo() {
        return this.simCardInfo;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final int getSlotId() {
        return 0;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final int getSubId() {
        return this.subId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSwRoaming() {
        return this.swRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final TableLogBuffer getTableLogBuffer() {
        return this.tableLogBuffer;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isEmergencyOnly() {
        return this.isEmergencyOnly;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isGsm() {
        return this.isGsm;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isInService() {
        return this.isInService;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isNonTerrestrial() {
        return this.isNonTerrestrial;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isRoaming() {
        return this.isRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final void setSim1On(boolean z) {
    }
}
