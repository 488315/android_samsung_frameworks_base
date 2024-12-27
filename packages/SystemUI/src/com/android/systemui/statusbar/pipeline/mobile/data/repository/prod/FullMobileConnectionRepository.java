package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyManager;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.DummyMobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl;
import com.sec.ims.IMSParameter;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class FullMobileConnectionRepository implements MobileConnectionRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isCarrierMerged;
    public final ReadonlyStateFlow activeRepo;
    public final ReadonlyStateFlow allowNetworkSliceIndicator;
    public final ReadonlyStateFlow carrierId;
    public final Lazy carrierMergedRepo$delegate;
    public final CarrierMergedConnectionRepository.Factory carrierMergedRepoFactory;
    public final ReadonlyStateFlow carrierName;
    public final ReadonlyStateFlow carrierNetworkChangeActive;
    public final ReadonlyStateFlow cdmaLevel;
    public final ReadonlyStateFlow cdmaRoaming;
    public final ReadonlyStateFlow dataActivityDirection;
    public final ReadonlyStateFlow dataConnectionState;
    public final ReadonlyStateFlow dataEnabled;
    public final NetworkNameModel defaultNetworkName;
    public final DummyMobileConnectionRepository.Factory dummyRepoFactory;
    public final ReadonlyStateFlow hasPrioritizedNetworkCapabilities;
    public final ReadonlyStateFlow imsRegState;
    public final ReadonlyStateFlow inflateSignalStrength;
    public final ReadonlyStateFlow isAllowedDuringAirplaneMode;
    public final ReadonlyStateFlow isCarrierMerged;
    public final ReadonlyStateFlow isEmergencyOnly;
    public final ReadonlyStateFlow isGsm;
    public final ReadonlyStateFlow isInService;
    public final ReadonlyStateFlow isNonTerrestrial;
    public final ReadonlyStateFlow isRoaming;
    public final ReadonlyStateFlow mobileDataEnabledChanged;
    public final Lazy mobileRepo$delegate;
    public final MobileConnectionRepositoryImpl.Factory mobileRepoFactory;
    public final ReadonlyStateFlow mobileServiceState;
    public final ReadonlyStateFlow networkName;
    public final String networkNameSeparator;
    public final ReadonlyStateFlow numberOfLevels;
    public final ReadonlyStateFlow onTheCall;
    public final ReadonlyStateFlow operatorAlphaShort;
    public final ReadonlyStateFlow primaryLevel;
    public final ReadonlyStateFlow resolvedNetworkType;
    public final ReadonlyStateFlow satelliteLevel;
    public final ReadonlyStateFlow semOMCChangedEvent;
    public final ReadonlyStateFlow semSatelliteEnabled;
    public final ReadonlyStateFlow semSatelliteServiceState;
    public final ReadonlyStateFlow semSatelliteSignalStrength;
    public final ReadonlyStateFlow sim1On;
    public final ReadonlyStateFlow simCardInfo;
    public final int slotId;
    public final int subId;
    public final ReadonlyStateFlow swRoaming;
    public final TableLogBuffer tableLogBuffer;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class Factory {
        public static final Companion Companion = new Companion(null);
        public final CarrierMergedConnectionRepository.Factory carrierMergedRepoFactory;
        public final DummyMobileConnectionRepository.Factory dummyRepoFactory;
        public final TableLogBufferFactory logFactory;
        public final MobileConnectionRepositoryImpl.Factory mobileRepoFactory;
        public final CoroutineScope scope;

        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Factory(CoroutineScope coroutineScope, TableLogBufferFactory tableLogBufferFactory, MobileConnectionRepositoryImpl.Factory factory, CarrierMergedConnectionRepository.Factory factory2, DummyMobileConnectionRepository.Factory factory3) {
            this.scope = coroutineScope;
            this.logFactory = tableLogBufferFactory;
            this.mobileRepoFactory = factory;
            this.carrierMergedRepoFactory = factory2;
            this.dummyRepoFactory = factory3;
        }
    }

    static {
        new Companion(null);
    }

    public FullMobileConnectionRepository(int i, boolean z, TableLogBuffer tableLogBuffer, final Flow flow, NetworkNameModel networkNameModel, String str, CoroutineScope coroutineScope, MobileConnectionRepositoryImpl.Factory factory, CarrierMergedConnectionRepository.Factory factory2, DummyMobileConnectionRepository.Factory factory3) {
        this.subId = i;
        this.tableLogBuffer = tableLogBuffer;
        this.defaultNetworkName = networkNameModel;
        this.networkNameSeparator = str;
        this.mobileRepoFactory = factory;
        this.carrierMergedRepoFactory = factory2;
        this.dummyRepoFactory = factory3;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(z));
        this._isCarrierMerged = MutableStateFlow;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable(MutableStateFlow, tableLogBuffer, "", "isCarrierMerged", z);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(z));
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository$mobileRepo$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                FullMobileConnectionRepository fullMobileConnectionRepository = FullMobileConnectionRepository.this;
                int i2 = fullMobileConnectionRepository.subId;
                if (i2 != Integer.MAX_VALUE) {
                    return fullMobileConnectionRepository.mobileRepoFactory.build(i2, fullMobileConnectionRepository.tableLogBuffer, flow, fullMobileConnectionRepository.defaultNetworkName, fullMobileConnectionRepository.networkNameSeparator);
                }
                DummyMobileConnectionRepository.Factory factory4 = fullMobileConnectionRepository.dummyRepoFactory;
                CarrierInfraMediator carrierInfraMediator = factory4.carrierInfraMediator;
                TelephonyManager telephonyManager = factory4.phone;
                return new DummyMobileConnectionRepository(i2, factory4.scope, factory4.broadcastDispatcher, fullMobileConnectionRepository.defaultNetworkName, fullMobileConnectionRepository.networkNameSeparator, factory4.logger, fullMobileConnectionRepository.tableLogBuffer, carrierInfraMediator, telephonyManager);
            }
        });
        this.mobileRepo$delegate = lazy;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository$carrierMergedRepo$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                FullMobileConnectionRepository fullMobileConnectionRepository = FullMobileConnectionRepository.this;
                CarrierMergedConnectionRepository.Factory factory4 = fullMobileConnectionRepository.carrierMergedRepoFactory;
                factory4.getClass();
                TelephonyManager telephonyManager = factory4.telephonyManager;
                int i2 = fullMobileConnectionRepository.subId;
                return new CarrierMergedConnectionRepository(i2, fullMobileConnectionRepository.tableLogBuffer, telephonyManager.createForSubscriptionId(i2), factory4.bgContext, factory4.scope, factory4.wifiRepository);
            }
        });
        this.carrierMergedRepo$delegate = lazy2;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.mapLatest(stateIn, new FullMobileConnectionRepository$activeRepo$1$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), z ? (MobileConnectionRepository) lazy2.getValue() : (MobileConnectionRepository) lazy.getValue());
        this.activeRepo = stateIn2;
        StateFlow stateFlow = stateIn2.$$delegate_0;
        this.slotId = ((MobileConnectionRepository) stateFlow.getValue()).getSlotId();
        this.carrierId = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getCarrierId().getValue());
        this.cdmaRoaming = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$2(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getCdmaRoaming().getValue());
        this.swRoaming = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$3(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getSwRoaming().getValue());
        this.isEmergencyOnly = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$4(null)), tableLogBuffer, "", "emergencyOnly", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).isEmergencyOnly().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).isEmergencyOnly().getValue());
        this.isRoaming = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$5(null)), tableLogBuffer, "", "roaming", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).isRoaming().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).isRoaming().getValue());
        this.operatorAlphaShort = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$6(null)), tableLogBuffer, "operatorName", (String) ((MobileConnectionRepository) stateFlow.getValue()).getOperatorAlphaShort().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getOperatorAlphaShort().getValue());
        this.isInService = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$7(null)), tableLogBuffer, "", "isInService", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).isInService().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).isInService().getValue());
        this.isNonTerrestrial = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$8(null)), tableLogBuffer, "", "isNtn", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).isNonTerrestrial().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).isNonTerrestrial().getValue());
        this.isGsm = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$9(null)), tableLogBuffer, "", "isGsm", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).isGsm().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).isGsm().getValue());
        this.cdmaLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$10(null)), tableLogBuffer, "", "cdmaLevel", ((Number) ((MobileConnectionRepository) stateFlow.getValue()).getCdmaLevel().getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getCdmaLevel().getValue());
        this.primaryLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$11(null)), tableLogBuffer, "", "primaryLevel", ((Number) ((MobileConnectionRepository) stateFlow.getValue()).getPrimaryLevel().getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getPrimaryLevel().getValue());
        this.satelliteLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$12(null)), tableLogBuffer, "", "satelliteLevel", ((Number) ((MobileConnectionRepository) stateFlow.getValue()).getSatelliteLevel().getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getSatelliteLevel().getValue());
        this.dataConnectionState = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$13(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateFlow.getValue()).getDataConnectionState().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getDataConnectionState().getValue());
        this.dataActivityDirection = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$14(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateFlow.getValue()).getDataActivityDirection().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getDataActivityDirection().getValue());
        this.carrierNetworkChangeActive = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$15(null)), tableLogBuffer, "", "carrierNetworkChangeActive", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).getCarrierNetworkChangeActive().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getCarrierNetworkChangeActive().getValue());
        this.resolvedNetworkType = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$16(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateFlow.getValue()).getResolvedNetworkType().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getResolvedNetworkType().getValue());
        this.dataEnabled = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$17(null)), tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED, ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).getDataEnabled().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getDataEnabled().getValue());
        this.inflateSignalStrength = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$18(null)), tableLogBuffer, "", "inflate", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).getInflateSignalStrength().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getInflateSignalStrength().getValue());
        this.allowNetworkSliceIndicator = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$19(null)), tableLogBuffer, "", "allowSlice", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).getAllowNetworkSliceIndicator().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getAllowNetworkSliceIndicator().getValue());
        this.numberOfLevels = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$20(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getNumberOfLevels().getValue());
        this.networkName = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$21(null)), tableLogBuffer, "intent", (Diffable) ((MobileConnectionRepository) stateFlow.getValue()).getNetworkName().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getNetworkName().getValue());
        this.carrierName = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$22(null)), tableLogBuffer, "sub", (Diffable) ((MobileConnectionRepository) stateFlow.getValue()).getCarrierName().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getCarrierName().getValue());
        this.isAllowedDuringAirplaneMode = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$23(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).isAllowedDuringAirplaneMode().getValue());
        this.hasPrioritizedNetworkCapabilities = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$24(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getHasPrioritizedNetworkCapabilities().getValue());
        this.simCardInfo = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$25(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateFlow.getValue()).getSimCardInfo().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getSimCardInfo().getValue());
        this.sim1On = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$26(null)), tableLogBuffer, "", "sim1On", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).getSim1On().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getSim1On().getValue());
        this.onTheCall = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$27(null)), tableLogBuffer, "", "onTheCall", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).getOnTheCall().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getOnTheCall().getValue());
        this.mobileServiceState = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$28(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateFlow.getValue()).getMobileServiceState().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getMobileServiceState().getValue());
        this.semOMCChangedEvent = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$29(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Unit.INSTANCE);
        this.imsRegState = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$30(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateFlow.getValue()).getImsRegState().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getImsRegState().getValue());
        this.mobileDataEnabledChanged = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$31(null)), tableLogBuffer, "", "", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).getMobileDataEnabledChanged().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getMobileDataEnabledChanged().getValue());
        this.semSatelliteServiceState = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$32(null)), tableLogBuffer, "", "", ((Number) ((MobileConnectionRepository) stateFlow.getValue()).getSemSatelliteServiceState().getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getSemSatelliteServiceState().getValue());
        this.semSatelliteSignalStrength = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$33(null)), tableLogBuffer, "", "", ((Number) ((MobileConnectionRepository) stateFlow.getValue()).getSemSatelliteSignalStrength().getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getSemSatelliteSignalStrength().getValue());
        this.semSatelliteEnabled = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$34(null)), tableLogBuffer, "", "", ((Boolean) ((MobileConnectionRepository) stateFlow.getValue()).getSemSatelliteEnabled().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileConnectionRepository) stateFlow.getValue()).getSemSatelliteEnabled().getValue());
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

    public final boolean getIsCarrierMerged() {
        return ((Boolean) this._isCarrierMerged.getValue()).booleanValue();
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
        return this.slotId;
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
        return ((MobileConnectionRepository) this.activeRepo.$$delegate_0.getValue()).isInEcmMode(continuation);
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

    public static /* synthetic */ void getActiveRepo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
