package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.content.Context;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.ToColdFlowKt;
import com.android.systemui.kairos.TransactionScope;
import com.android.systemui.statusbar.phone.CoverScreenNetworkSignalModel;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import kotlin.NotImplementedError;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileConnectionsRepositoryKairosAdapter implements MobileConnectionsRepository, KairosBuilder {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0 = new KairosBuilderImpl();
    public final ReadonlyStateFlow activeMobileDataSubscriptionId;
    public final Flow activeSubChangedInGroupEvent;
    public final ReadonlyStateFlow defaultConnectionIsValidated;
    public final ReadonlyStateFlow defaultDataSubId;
    public final ReadonlyStateFlow defaultDataSubRatConfig;
    public final Flow defaultMobileIconGroup;
    public final Flow defaultMobileIconMapping;
    public final Flow hasCarrierMergedConnection;
    public final ReadonlyStateFlow isAnySimSecure;
    public final ReadonlyStateFlow isDeviceEmergencyCallCapable;
    public final KairosNetwork kairosNetwork;
    public final MobileConnectionsRepositoryKairos kairosRepo;
    public final ReadonlyStateFlow mobileIsDefault;
    public final ReadonlyStateFlow subscriptions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Module {
        public static final Module INSTANCE = new Module();

        private Module() {
        }
    }

    public MobileConnectionsRepositoryKairosAdapter(MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, ConnectivityRepository connectivityRepository, Context context, CarrierConfigRepository carrierConfigRepository) {
        this.kairosRepo = mobileConnectionsRepositoryKairos;
        this.kairosNetwork = kairosNetwork;
        Flow coldConflatedFlow = ToColdFlowKt.toColdConflatedFlow(StateKt.map(mobileConnectionsRepositoryKairos.getSubscriptions(), new MobileConnectionsRepositoryKairosAdapter$$ExternalSyntheticLambda1()), kairosNetwork);
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.subscriptions = FlowKt.stateIn(coldConflatedFlow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), EmptyList.INSTANCE);
        this.activeMobileDataSubscriptionId = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId(), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.activeSubChangedInGroupEvent = ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getActiveSubChangedInGroupEvent(), kairosNetwork);
        this.defaultDataSubId = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getDefaultDataSubId(), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        ConnectivityRepositoryImpl connectivityRepositoryImpl = (ConnectivityRepositoryImpl) connectivityRepository;
        this.mobileIsDefault = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getMobileIsDefault(), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(((DefaultConnectionModel) connectivityRepositoryImpl.defaultConnections.$$delegate_0.getValue()).mobile.isDefault));
        this.hasCarrierMergedConnection = ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getHasCarrierMergedConnection(), kairosNetwork);
        this.defaultConnectionIsValidated = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getDefaultConnectionIsValidated(), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(((DefaultConnectionModel) connectivityRepositoryImpl.defaultConnections.$$delegate_0.getValue()).isValidated));
        this.defaultDataSubRatConfig = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getDefaultDataSubRatConfig(), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MobileMappings.Config.readConfig(context));
        this.defaultMobileIconMapping = ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getDefaultMobileIconMapping(), kairosNetwork);
        this.defaultMobileIconGroup = ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.getDefaultMobileIconGroup(), kairosNetwork);
        Flow coldConflatedFlow2 = ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.isDeviceEmergencyCallCapable(), kairosNetwork);
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        this.isDeviceEmergencyCallCapable = FlowKt.stateIn(coldConflatedFlow2, coroutineScope, startedEagerly, bool);
        this.isAnySimSecure = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileConnectionsRepositoryKairos.isAnySimSecure(), kairosNetwork), coroutineScope, startedEagerly, bool);
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final boolean bootstrapProfile(int i) {
        return false;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getActiveMobileDataRepository() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
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
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getDeviceOnTheCall() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Flow getHasCarrierMergedConnection() {
        return this.hasCarrierMergedConnection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final boolean getIsAnySimSecure() {
        return ((Boolean) this.isAnySimSecure.$$delegate_0.getValue()).booleanValue();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final StateFlow getMobileIsDefault() {
        return this.mobileIsDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final CoverScreenNetworkSignalModel getNoServiceInfo() {
        return new CoverScreenNetworkSignalModel(false, 0);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final MobileConnectionRepository getRepoForSubId(int i) {
        throw new IllegalStateException(("Unknown subscription id: " + i).toString());
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
    public final StateFlow isDeviceEmergencyCallCapable() {
        return this.isDeviceEmergencyCallCapable;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository
    public final Object isInEcmMode(Continuation continuation) {
        return this.kairosNetwork.transact(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairosAdapter$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Boolean bool = (Boolean) ((TransactionScope) obj).sample(MobileConnectionsRepositoryKairosAdapter.this.kairosRepo.isInEcmMode());
                bool.getClass();
                return bool;
            }
        }, continuation);
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
