package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.DummyMobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CarrierMergedConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl;
import com.sec.ims.IMSParameter;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
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
public final class FullMobileConnectionRepository implements MobileConnectionRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isCarrierMerged;
    public final ReadonlyStateFlow activeRepo;
    public final ReadonlyStateFlow carrierId;
    public final Lazy carrierMergedRepo$delegate;
    public final CarrierMergedConnectionRepository.Factory carrierMergedRepoFactory;
    public final ReadonlyStateFlow carrierNetworkChangeActive;
    public final ReadonlyStateFlow cdmaLevel;
    public final ReadonlyStateFlow cdmaRoaming;
    public final ReadonlyStateFlow dataActivityDirection;
    public final ReadonlyStateFlow dataConnectionState;
    public final ReadonlyStateFlow dataEnabled;
    public final NetworkNameModel defaultNetworkName;
    public final DummyMobileConnectionRepository.Factory dummyRepoFactory;
    public final ReadonlyStateFlow imsRegState;
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
    public final ReadonlyStateFlow sim1On;
    public final ReadonlyStateFlow simCardInfo;
    public final int slotId;
    public final int subId;
    public final ReadonlyStateFlow swRoaming;
    public final TableLogBuffer tableLogBuffer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public static final Companion Companion = new Companion(null);
        public final CarrierMergedConnectionRepository.Factory carrierMergedRepoFactory;
        public final DummyMobileConnectionRepository.Factory dummyRepoFactory;
        public final TableLogBufferFactory logFactory;
        public final MobileConnectionRepositoryImpl.Factory mobileRepoFactory;
        public final CoroutineScope scope;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public FullMobileConnectionRepository(int i, boolean z, TableLogBuffer tableLogBuffer, NetworkNameModel networkNameModel, String str, CoroutineScope coroutineScope, MobileConnectionRepositoryImpl.Factory factory, CarrierMergedConnectionRepository.Factory factory2, DummyMobileConnectionRepository.Factory factory3) {
        this.subId = i;
        this.tableLogBuffer = tableLogBuffer;
        this.defaultNetworkName = networkNameModel;
        this.networkNameSeparator = str;
        this.mobileRepoFactory = factory;
        this.carrierMergedRepoFactory = factory2;
        this.dummyRepoFactory = factory3;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(z));
        this._isCarrierMerged = MutableStateFlow;
        SafeFlow logDiffsForTable = DiffableKt.logDiffsForTable(MutableStateFlow, tableLogBuffer, "", "isCarrierMerged", z);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.valueOf(z));
        this.isCarrierMerged = stateIn;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository$mobileRepo$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                FullMobileConnectionRepository fullMobileConnectionRepository = FullMobileConnectionRepository.this;
                int i2 = fullMobileConnectionRepository.subId;
                if (i2 == Integer.MAX_VALUE) {
                    TableLogBuffer tableLogBuffer2 = fullMobileConnectionRepository.tableLogBuffer;
                    NetworkNameModel networkNameModel2 = fullMobileConnectionRepository.defaultNetworkName;
                    String str2 = fullMobileConnectionRepository.networkNameSeparator;
                    DummyMobileConnectionRepository.Factory factory4 = fullMobileConnectionRepository.dummyRepoFactory;
                    return new DummyMobileConnectionRepository(i2, factory4.scope, factory4.broadcastDispatcher, networkNameModel2, str2, factory4.logger, tableLogBuffer2, factory4.carrierInfraMediator, factory4.phone);
                }
                TableLogBuffer tableLogBuffer3 = fullMobileConnectionRepository.tableLogBuffer;
                NetworkNameModel networkNameModel3 = fullMobileConnectionRepository.defaultNetworkName;
                String str3 = fullMobileConnectionRepository.networkNameSeparator;
                MobileConnectionRepositoryImpl.Factory factory5 = fullMobileConnectionRepository.mobileRepoFactory;
                return new MobileConnectionRepositoryImpl(factory5.context, i2, networkNameModel3, str3, factory5.telephonyManager.createForSubscriptionId(i2), factory5.carrierConfigRepository.getOrCreateConfigForSubId(i2), factory5.broadcastDispatcher, factory5.mobileMappingsProxy, factory5.bgDispatcher, factory5.logger, tableLogBuffer3, factory5.scope, factory5.carrierInfraMediator, factory5.simCardInfoUtil, factory5.globalSettings, factory5.imsRegStateUtil);
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
                int i2 = fullMobileConnectionRepository.subId;
                TableLogBuffer tableLogBuffer2 = fullMobileConnectionRepository.tableLogBuffer;
                factory4.getClass();
                return new CarrierMergedConnectionRepository(i2, tableLogBuffer2, factory4.telephonyManager.createForSubscriptionId(i2), factory4.scope, factory4.wifiRepository);
            }
        });
        this.carrierMergedRepo$delegate = lazy2;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.mapLatest(stateIn, new FullMobileConnectionRepository$activeRepo$1$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), z ? (MobileConnectionRepository) lazy2.getValue() : (MobileConnectionRepository) lazy.getValue());
        this.activeRepo = stateIn2;
        this.slotId = ((MobileConnectionRepository) stateIn2.getValue()).getSlotId();
        this.carrierId = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getCarrierId().getValue());
        this.cdmaRoaming = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$2(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getCdmaRoaming().getValue());
        this.swRoaming = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$3(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getSwRoaming().getValue());
        this.isEmergencyOnly = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$4(null)), tableLogBuffer, "", "emergencyOnly", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).isEmergencyOnly().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).isEmergencyOnly().getValue());
        this.isRoaming = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$5(null)), tableLogBuffer, "", "roaming", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).isRoaming().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).isRoaming().getValue());
        this.operatorAlphaShort = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$6(null)), tableLogBuffer, "operatorName", (String) ((MobileConnectionRepository) stateIn2.getValue()).getOperatorAlphaShort().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getOperatorAlphaShort().getValue());
        this.isInService = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$7(null)), tableLogBuffer, "", "isInService", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).isInService().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).isInService().getValue());
        this.isNonTerrestrial = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$8(null)), tableLogBuffer, "", "isNtn", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).isNonTerrestrial().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).isNonTerrestrial().getValue());
        this.isGsm = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$9(null)), tableLogBuffer, "", "isGsm", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).isGsm().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).isGsm().getValue());
        this.cdmaLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$10(null)), tableLogBuffer, "", "cdmaLevel", ((Number) ((MobileConnectionRepository) stateIn2.getValue()).getCdmaLevel().getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getCdmaLevel().getValue());
        this.primaryLevel = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$11(null)), tableLogBuffer, "", "primaryLevel", ((Number) ((MobileConnectionRepository) stateIn2.getValue()).getPrimaryLevel().getValue()).intValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getPrimaryLevel().getValue());
        this.dataConnectionState = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$12(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateIn2.getValue()).getDataConnectionState().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getDataConnectionState().getValue());
        this.dataActivityDirection = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$13(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateIn2.getValue()).getDataActivityDirection().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getDataActivityDirection().getValue());
        this.carrierNetworkChangeActive = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$14(null)), tableLogBuffer, "", "carrierNetworkChangeActive", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).getCarrierNetworkChangeActive().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getCarrierNetworkChangeActive().getValue());
        this.resolvedNetworkType = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$15(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateIn2.getValue()).getResolvedNetworkType().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getResolvedNetworkType().getValue());
        this.dataEnabled = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$16(null)), tableLogBuffer, "", IMSParameter.GENERAL.DATA_ENABLED, ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).getDataEnabled().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getDataEnabled().getValue());
        this.numberOfLevels = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$17(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getNumberOfLevels().getValue());
        this.networkName = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$18(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateIn2.getValue()).getNetworkName().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getNetworkName().getValue());
        this.simCardInfo = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$19(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateIn2.getValue()).getSimCardInfo().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getSimCardInfo().getValue());
        this.sim1On = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$20(null)), tableLogBuffer, "", "sim1On", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).getSim1On().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getSim1On().getValue());
        this.onTheCall = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$21(null)), tableLogBuffer, "", "onTheCall", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).getOnTheCall().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getOnTheCall().getValue());
        this.mobileServiceState = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$22(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateIn2.getValue()).getMobileServiceState().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getMobileServiceState().getValue());
        this.imsRegState = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$23(null)), tableLogBuffer, "", (Diffable) ((MobileConnectionRepository) stateIn2.getValue()).getImsRegState().getValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getImsRegState().getValue());
        this.mobileDataEnabledChanged = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.transformLatest(stateIn2, new FullMobileConnectionRepository$special$$inlined$flatMapLatest$24(null)), tableLogBuffer, "", "", ((Boolean) ((MobileConnectionRepository) stateIn2.getValue()).getMobileDataEnabledChanged().getValue()).booleanValue()), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), ((MobileConnectionRepository) stateIn2.getValue()).getMobileDataEnabledChanged().getValue());
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
        ((MobileConnectionRepository) this.activeRepo.getValue()).setSim1On(z);
    }

    /* renamed from: getActiveRepo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m208x3eb26c54() {
    }
}
