package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class MobileRepositorySwitcher implements MobileConnectionsRepository {
    public final ReadonlyStateFlow activeMobileDataRepository;
    public final ReadonlyStateFlow activeMobileDataSubscriptionId;
    public final ReadonlyStateFlow activeRepo;
    public final ChannelFlowTransformLatest activeSubChangedInGroupEvent;
    public final ReadonlyStateFlow defaultConnectionIsValidated;
    public final ReadonlyStateFlow defaultDataSubId;
    public final ReadonlyStateFlow defaultDataSubRatConfig;
    public final ChannelFlowTransformLatest defaultMobileIconGroup;
    public final ChannelFlowTransformLatest defaultMobileIconMapping;
    public final ChannelFlowTransformLatest defaultMobileIconMappingTable;
    public final DemoMobileConnectionsRepository demoMobileConnectionsRepository;
    public final ReadonlyStateFlow deviceOnTheCall;
    public final ReadonlyStateFlow deviceServiceState;
    public final ReadonlyStateFlow hasCarrierMergedConnection;
    public final ChannelFlowTransformLatest isAnySimSecure;
    public final ReadonlyStateFlow isDemoMode;
    public final ReadonlyStateFlow mobileIsDefault;
    public final MobileConnectionsRepositoryImpl realRepository;
    public final ReadonlyStateFlow subscriptions;

    public MobileRepositorySwitcher(CoroutineScope coroutineScope, MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, DemoMobileConnectionsRepository demoMobileConnectionsRepository, DemoModeController demoModeController) {
        this.realRepository = mobileConnectionsRepositoryImpl;
        this.demoMobileConnectionsRepository = demoMobileConnectionsRepository;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        MobileRepositorySwitcher$isDemoMode$1 mobileRepositorySwitcher$isDemoMode$1 = new MobileRepositorySwitcher$isDemoMode$1(demoModeController, this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(mobileRepositorySwitcher$isDemoMode$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        this.isDemoMode = stateIn;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.mapLatest(stateIn, new MobileRepositorySwitcher$activeRepo$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl);
        this.activeRepo = stateIn2;
        this.subscriptions = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.subscriptions.$$delegate_0.getValue());
        this.activeMobileDataSubscriptionId = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$2(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.activeMobileDataSubscriptionId.$$delegate_0.getValue());
        this.activeMobileDataRepository = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$3(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.activeMobileDataRepository.$$delegate_0.getValue());
        this.activeSubChangedInGroupEvent = FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$4(null));
        this.defaultDataSubRatConfig = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$5(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.defaultDataSubRatConfig.$$delegate_0.getValue());
        this.defaultMobileIconMappingTable = FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$6(null));
        this.defaultMobileIconMapping = FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$7(null));
        this.defaultMobileIconGroup = FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$8(null));
        this.deviceServiceState = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$9(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.deviceServiceState.$$delegate_0.getValue());
        this.isAnySimSecure = FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$10(null));
        this.defaultDataSubId = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$11(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.defaultDataSubId.$$delegate_0.getValue());
        this.mobileIsDefault = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$12(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.mobileIsDefault.$$delegate_0.getValue());
        this.hasCarrierMergedConnection = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$13(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.hasCarrierMergedConnection.$$delegate_0.getValue());
        this.defaultConnectionIsValidated = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$14(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.defaultConnectionIsValidated.$$delegate_0.getValue());
        this.deviceOnTheCall = FlowKt.stateIn(FlowKt.transformLatest(stateIn2, new MobileRepositorySwitcher$special$$inlined$flatMapLatest$15(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepositoryImpl.deviceOnTheCall.$$delegate_0.getValue());
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final boolean bootstrapProfile(int i) {
        return ((MobileConnectionsRepository) this.activeRepo.$$delegate_0.getValue()).bootstrapProfile(i);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getActiveMobileDataRepository() {
        return this.activeMobileDataRepository;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getActiveMobileDataSubscriptionId() {
        return this.activeMobileDataSubscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getActiveSubChangedInGroupEvent() {
        return this.activeSubChangedInGroupEvent;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDefaultConnectionIsValidated() {
        return this.defaultConnectionIsValidated;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDefaultDataSubId() {
        return this.defaultDataSubId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDefaultDataSubRatConfig() {
        return this.defaultDataSubRatConfig;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getDefaultMobileIconGroup() {
        return this.defaultMobileIconGroup;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getDefaultMobileIconMapping() {
        return this.defaultMobileIconMapping;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getDefaultMobileIconMappingTable() {
        return this.defaultMobileIconMappingTable;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDeviceOnTheCall() {
        return this.deviceOnTheCall;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDeviceServiceState() {
        return this.deviceServiceState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getHasCarrierMergedConnection() {
        return this.hasCarrierMergedConnection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final boolean getIsAnySimSecure() {
        return ((MobileConnectionsRepository) this.activeRepo.$$delegate_0.getValue()).getIsAnySimSecure();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getMobileIsDefault() {
        return this.mobileIsDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final CoverScreenNetworkSignalModel getNoServiceInfo() {
        return this.realRepository.getNoServiceInfo();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final MobileConnectionRepository getRepoForSubId(int i) {
        return ((Boolean) this.isDemoMode.$$delegate_0.getValue()).booleanValue() ? this.demoMobileConnectionsRepository.getRepoForSubId(i) : this.realRepository.getOrCreateRepoForSubId(i);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getSubscriptions() {
        return this.subscriptions;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow isAnySimSecure() {
        return this.isAnySimSecure;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Object isInEcmMode(Continuation continuation) {
        if (!((Boolean) this.isDemoMode.$$delegate_0.getValue()).booleanValue()) {
            return this.realRepository.isInEcmMode(continuation);
        }
        this.demoMobileConnectionsRepository.getClass();
        return Boolean.FALSE;
    }

    public static /* synthetic */ void getActiveRepo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
