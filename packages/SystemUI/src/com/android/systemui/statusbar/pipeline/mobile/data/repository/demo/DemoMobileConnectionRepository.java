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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class DemoMobileConnectionRepository implements MobileConnectionRepository {
    public final StateFlowImpl _carrierId;
    public final StateFlowImpl _carrierNetworkChangeActive;
    public final StateFlowImpl _cdmaLevel;
    public final StateFlowImpl _dataActivityDirection;
    public final StateFlowImpl _dataConnectionState;
    public final StateFlowImpl _inflateSignalStrength;
    public final StateFlowImpl _isEmergencyOnly;
    public final StateFlowImpl _isGsm;
    public final StateFlowImpl _isInService;
    public final StateFlowImpl _isNonTerrestrial;
    public final StateFlowImpl _isRoaming;
    public final StateFlowImpl _operatorAlphaShort;
    public final StateFlowImpl _primaryLevel;
    public final StateFlowImpl _resolvedNetworkType;
    public final StateFlowImpl _satelliteLevel;
    public final StateFlowImpl allowNetworkSliceIndicator;
    public final ReadonlyStateFlow carrierId;
    public final StateFlowImpl carrierName;
    public final ReadonlyStateFlow carrierNetworkChangeActive;
    public final ReadonlyStateFlow cdmaLevel;
    public final StateFlowImpl cdmaRoaming;
    public final ReadonlyStateFlow dataActivityDirection;
    public final ReadonlyStateFlow dataConnectionState;
    public final StateFlowImpl dataEnabled;
    public final StateFlowImpl hasPrioritizedNetworkCapabilities;
    public final StateFlowImpl imsRegState;
    public final ReadonlyStateFlow inflateSignalStrength;
    public final StateFlowImpl isAllowedDuringAirplaneMode;
    public final ReadonlyStateFlow isEmergencyOnly;
    public final ReadonlyStateFlow isGsm;
    public final ReadonlyStateFlow isInService;
    public final ReadonlyStateFlow isNonTerrestrial;
    public final ReadonlyStateFlow isRoaming;
    public final StateFlowImpl isUsingNonTerrestrialNetwork;
    public final StateFlowImpl mobileDataEnabledChanged;
    public final StateFlowImpl mobileServiceState;
    public final StateFlowImpl networkName;
    public final ReadonlyStateFlow numberOfLevels;
    public final StateFlowImpl onTheCall;
    public final ReadonlyStateFlow operatorAlphaShort;
    public final ReadonlyStateFlow primaryLevel;
    public final ReadonlyStateFlow resolvedNetworkType;
    public final ReadonlyStateFlow satelliteLevel;
    public final StateFlowImpl semOMCChangedEvent;
    public final StateFlowImpl semSatelliteEnabled;
    public final StateFlowImpl semSatelliteServiceState;
    public final StateFlowImpl semSatelliteSignalStrength;
    public final StateFlowImpl sim1On;
    public final StateFlowImpl simCardInfo;
    public final int subId;
    public final StateFlowImpl swRoaming;
    public final TableLogBuffer tableLogBuffer;

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

    public DemoMobileConnectionRepository(int i, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope) {
        this.subId = i;
        this.tableLogBuffer = tableLogBuffer;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(-1);
        this._carrierId = stateFlowImplMutableStateFlow;
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow, tableLogBuffer, "", "carrierId", ((Number) stateFlowImplMutableStateFlow.getValue()).intValue());
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.carrierId = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow.getValue());
        Boolean bool = Boolean.FALSE;
        final StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._inflateSignalStrength = stateFlowImplMutableStateFlow2;
        this.inflateSignalStrength = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow2, tableLogBuffer, "", "inflate", ((Boolean) stateFlowImplMutableStateFlow2.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow2.getValue());
        Boolean bool2 = Boolean.TRUE;
        this.allowNetworkSliceIndicator = StateFlowKt.MutableStateFlow(bool2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isEmergencyOnly = stateFlowImplMutableStateFlow3;
        this.isEmergencyOnly = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow3, tableLogBuffer, "", "emergencyOnly", ((Boolean) stateFlowImplMutableStateFlow3.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow3.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._isRoaming = stateFlowImplMutableStateFlow4;
        this.isRoaming = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow4, tableLogBuffer, "", "roaming", ((Boolean) stateFlowImplMutableStateFlow4.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow4.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(null);
        this._operatorAlphaShort = stateFlowImplMutableStateFlow5;
        this.operatorAlphaShort = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow5, tableLogBuffer, "", "operatorName", (String) stateFlowImplMutableStateFlow5.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow5.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._isInService = stateFlowImplMutableStateFlow6;
        this.isInService = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow6, tableLogBuffer, "", "isInService", ((Boolean) stateFlowImplMutableStateFlow6.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow6.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._isNonTerrestrial = stateFlowImplMutableStateFlow7;
        this.isNonTerrestrial = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow7, tableLogBuffer, "", "isNtn", ((Boolean) stateFlowImplMutableStateFlow7.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow7.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._isGsm = stateFlowImplMutableStateFlow8;
        this.isGsm = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow8, tableLogBuffer, "", "isGsm", ((Boolean) stateFlowImplMutableStateFlow8.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow8.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(0);
        this._cdmaLevel = stateFlowImplMutableStateFlow9;
        this.cdmaLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow9, tableLogBuffer, "", "cdmaLevel", ((Number) stateFlowImplMutableStateFlow9.getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow9.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(0);
        this._primaryLevel = stateFlowImplMutableStateFlow10;
        this.primaryLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow10, tableLogBuffer, "", "primaryLevel", ((Number) stateFlowImplMutableStateFlow10.getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow10.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow11 = StateFlowKt.MutableStateFlow(0);
        this._satelliteLevel = stateFlowImplMutableStateFlow11;
        this.satelliteLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow11, tableLogBuffer, "", "satelliteLevel", ((Number) stateFlowImplMutableStateFlow11.getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow11.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow12 = StateFlowKt.MutableStateFlow(DataConnectionState.Disconnected);
        this._dataConnectionState = stateFlowImplMutableStateFlow12;
        this.dataConnectionState = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow12, tableLogBuffer, "", (Diffable) stateFlowImplMutableStateFlow12.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow12.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow13 = StateFlowKt.MutableStateFlow(new DataActivityModel(false, false));
        this._dataActivityDirection = stateFlowImplMutableStateFlow13;
        this.dataActivityDirection = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow13, tableLogBuffer, "", (Diffable) stateFlowImplMutableStateFlow13.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow13.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow14 = StateFlowKt.MutableStateFlow(bool);
        this._carrierNetworkChangeActive = stateFlowImplMutableStateFlow14;
        this.carrierNetworkChangeActive = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow14, tableLogBuffer, "", "carrierNetworkChangeActive", ((Boolean) stateFlowImplMutableStateFlow14.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow14.getValue());
        StateFlowImpl stateFlowImplMutableStateFlow15 = StateFlowKt.MutableStateFlow(ResolvedNetworkType.UnknownNetworkType.INSTANCE);
        this._resolvedNetworkType = stateFlowImplMutableStateFlow15;
        this.resolvedNetworkType = FlowKt.stateIn(DiffableKt.logDiffsForTable(stateFlowImplMutableStateFlow15, tableLogBuffer, "", (Diffable) stateFlowImplMutableStateFlow15.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlowImplMutableStateFlow15.getValue());
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i2 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i3 = anonymousClass1.label;
                    if (i3 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((Boolean) obj).booleanValue()) {
                            MobileConnectionRepository.Companion.getClass();
                            i = MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS + 1;
                        } else {
                            MobileConnectionRepository.Companion.getClass();
                            i = MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS;
                        }
                        Integer num = new Integer(i);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i3 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = stateFlowImplMutableStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        MobileConnectionRepository.Companion.getClass();
        this.numberOfLevels = FlowKt.stateIn(flow, coroutineScope, startedWhileSubscribedWhileSubscribed$default, Integer.valueOf(MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS));
        this.dataEnabled = StateFlowKt.MutableStateFlow(bool2);
        this.cdmaRoaming = StateFlowKt.MutableStateFlow(bool);
        this.networkName = StateFlowKt.MutableStateFlow(new NetworkNameModel.IntentDerived("Demo Carrier"));
        this.carrierName = StateFlowKt.MutableStateFlow(new NetworkNameModel.SubscriptionDerived("Demo Carrier"));
        this.isAllowedDuringAirplaneMode = StateFlowKt.MutableStateFlow(bool);
        this.hasPrioritizedNetworkCapabilities = StateFlowKt.MutableStateFlow(bool);
        this.swRoaming = StateFlowKt.MutableStateFlow(bool);
        this.simCardInfo = StateFlowKt.MutableStateFlow(SimCardModelKt.NO_SIM_MODEL);
        this.sim1On = StateFlowKt.MutableStateFlow(bool);
        this.onTheCall = StateFlowKt.MutableStateFlow(bool);
        this.isUsingNonTerrestrialNetwork = StateFlowKt.MutableStateFlow(bool);
        this.mobileServiceState = StateFlowKt.MutableStateFlow(MobileServiceStateKt.DEFAULT_SERVICE_STATE);
        this.semOMCChangedEvent = StateFlowKt.MutableStateFlow(Unit.INSTANCE);
        this.imsRegState = StateFlowKt.MutableStateFlow(ImsRegStateKt.DEFAULT_IMS_REG_STATE);
        this.mobileDataEnabledChanged = StateFlowKt.MutableStateFlow(bool);
        this.semSatelliteServiceState = StateFlowKt.MutableStateFlow(2);
        this.semSatelliteSignalStrength = StateFlowKt.MutableStateFlow(0);
        this.semSatelliteEnabled = StateFlowKt.MutableStateFlow(bool);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getAllowNetworkSliceIndicator() {
        return this.allowNetworkSliceIndicator;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCarrierId() {
        return this.carrierId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCarrierName() {
        return this.carrierName;
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
    public final StateFlow getHasPrioritizedNetworkCapabilities() {
        return this.hasPrioritizedNetworkCapabilities;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getImsRegState() {
        return this.imsRegState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getInflateSignalStrength() {
        return this.inflateSignalStrength;
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
    public final StateFlow getSatelliteLevel() {
        return this.satelliteLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSemOMCChangedEvent() {
        return this.semOMCChangedEvent;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSemSatelliteEnabled() {
        return this.semSatelliteEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSemSatelliteServiceState() {
        return this.semSatelliteServiceState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSemSatelliteSignalStrength() {
        return this.semSatelliteSignalStrength;
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
    public final StateFlow isAllowedDuringAirplaneMode() {
        return this.isAllowedDuringAirplaneMode;
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
    public final Object isInEcmMode(Continuation continuation) {
        return Boolean.FALSE;
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
    public final StateFlow isUsingNonTerrestrialNetwork() {
        return this.isUsingNonTerrestrialNetwork;
    }
}
