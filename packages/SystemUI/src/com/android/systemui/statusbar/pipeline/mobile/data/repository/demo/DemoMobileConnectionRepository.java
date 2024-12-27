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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public DemoMobileConnectionRepository(int i, TableLogBuffer tableLogBuffer, CoroutineScope coroutineScope) {
        this.subId = i;
        this.tableLogBuffer = tableLogBuffer;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(-1);
        Flow logDiffsForTable = DiffableKt.logDiffsForTable(MutableStateFlow, tableLogBuffer, "", "carrierId", ((Number) MutableStateFlow.getValue()).intValue());
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.carrierId = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow.getValue());
        Boolean bool = Boolean.FALSE;
        final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this.inflateSignalStrength = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow2, tableLogBuffer, "", "inflate", ((Boolean) MutableStateFlow2.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow2.getValue());
        Boolean bool2 = Boolean.TRUE;
        this.allowNetworkSliceIndicator = StateFlowKt.MutableStateFlow(bool2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this.isEmergencyOnly = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow3, tableLogBuffer, "", "emergencyOnly", ((Boolean) MutableStateFlow3.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow3.getValue());
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this.isRoaming = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow4, tableLogBuffer, "", "roaming", ((Boolean) MutableStateFlow4.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow4.getValue());
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(null);
        this.operatorAlphaShort = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow5, tableLogBuffer, "operatorName", (String) MutableStateFlow5.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow5.getValue());
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this.isInService = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow6, tableLogBuffer, "", "isInService", ((Boolean) MutableStateFlow6.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow6.getValue());
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this.isNonTerrestrial = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow7, tableLogBuffer, "", "isNtn", ((Boolean) MutableStateFlow7.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow7.getValue());
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this.isGsm = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow8, tableLogBuffer, "", "isGsm", ((Boolean) MutableStateFlow8.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow8.getValue());
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(0);
        this.cdmaLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow9, tableLogBuffer, "", "cdmaLevel", ((Number) MutableStateFlow9.getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow9.getValue());
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(0);
        this.primaryLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow10, tableLogBuffer, "", "primaryLevel", ((Number) MutableStateFlow10.getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow10.getValue());
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(0);
        this.satelliteLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow11, tableLogBuffer, "", "satelliteLevel", ((Number) MutableStateFlow11.getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow11.getValue());
        StateFlowImpl MutableStateFlow12 = StateFlowKt.MutableStateFlow(DataConnectionState.Disconnected);
        this.dataConnectionState = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow12, tableLogBuffer, "", (Diffable) MutableStateFlow12.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow12.getValue());
        StateFlowImpl MutableStateFlow13 = StateFlowKt.MutableStateFlow(new DataActivityModel(false, false));
        this.dataActivityDirection = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow13, tableLogBuffer, "", (Diffable) MutableStateFlow13.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow13.getValue());
        StateFlowImpl MutableStateFlow14 = StateFlowKt.MutableStateFlow(bool);
        this.carrierNetworkChangeActive = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow14, tableLogBuffer, "", "carrierNetworkChangeActive", ((Boolean) MutableStateFlow14.getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow14.getValue());
        StateFlowImpl MutableStateFlow15 = StateFlowKt.MutableStateFlow(ResolvedNetworkType.UnknownNetworkType.INSTANCE);
        this.resolvedNetworkType = FlowKt.stateIn(DiffableKt.logDiffsForTable(MutableStateFlow15, tableLogBuffer, "", (Diffable) MutableStateFlow15.getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MutableStateFlow15.getValue());
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L43
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository$Companion r5 = com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository.Companion
                        r5.getClass()
                        int r5 = com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS
                        int r5 = r5 + r3
                        goto L4a
                    L43:
                        com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository$Companion r5 = com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository.Companion
                        r5.getClass()
                        int r5 = com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS
                    L4a:
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        MobileConnectionRepository.Companion.getClass();
        this.numberOfLevels = FlowKt.stateIn(flow, coroutineScope, WhileSubscribed$default, Integer.valueOf(MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS));
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
}
