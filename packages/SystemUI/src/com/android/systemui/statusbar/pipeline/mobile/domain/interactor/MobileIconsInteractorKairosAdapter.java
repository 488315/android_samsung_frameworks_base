package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.Incremental;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.ToColdFlowKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepositoryKairos;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.FullMobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class MobileIconsInteractorKairosAdapter implements MobileIconsInteractor, KairosBuilder {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final ReadonlyStateFlow activeDataIconInteractor;
    public final Flow filteredSubscriptions;
    public final ReadonlyStateFlow icons;
    public final ReadonlyStateFlow interactorsBySubId;
    public final ReadonlyStateFlow isSingleCarrier;
    public final ReadonlyStateFlow isStackable;
    public final MobileIconsInteractorKairos kairosInteractor;
    public final TableLogBufferFactory logFactory;
    public final MobileConnectionsRepository repo;

    public final class Module {
        public static final Module INSTANCE = new Module();

        private Module() {
        }
    }

    public MobileIconsInteractorKairosAdapter(MobileIconsInteractorKairos mobileIconsInteractorKairos, MobileConnectionsRepository mobileConnectionsRepository, MobileConnectionsRepositoryKairos mobileConnectionsRepositoryKairos, KairosNetwork kairosNetwork, CoroutineScope coroutineScope, Context context, MobileMappingsProxy mobileMappingsProxy, UserSetupRepository userSetupRepository, TableLogBufferFactory tableLogBufferFactory) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        this.kairosInteractor = mobileIconsInteractorKairos;
        this.repo = mobileConnectionsRepository;
        this.logFactory = tableLogBufferFactory;
        Incremental incrementalBuildIncremental = kairosBuilderImpl.buildIncremental(new MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda4(this, 1));
        Flow coldConflatedFlow = ToColdFlowKt.toColdConflatedFlow(incrementalBuildIncremental, kairosNetwork);
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        this.interactorsBySubId = FlowKt.stateIn(coldConflatedFlow, coroutineScope, SharingStarted.Companion.Eagerly, MapsKt__MapsKt.emptyMap());
        MobileIconsInteractorKairosImpl mobileIconsInteractorKairosImpl = (MobileIconsInteractorKairosImpl) mobileIconsInteractorKairos;
        FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.mobileIsDefault, kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileConnectionsRepository.getMobileIsDefault().getValue());
        this.filteredSubscriptions = ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.filteredSubscriptions, kairosNetwork);
        this.icons = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(StateKt.map(incrementalBuildIncremental, new MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda1(0)), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), EmptyList.INSTANCE);
        Flow coldConflatedFlow2 = ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.isStackable, kairosNetwork);
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.isStackable = FlowKt.stateIn(coldConflatedFlow2, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.activeDataConnectionHasDataEnabled, kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.activeDataIconInteractor = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(CombineKt.combine(mobileConnectionsRepositoryKairos.getActiveMobileDataSubscriptionId(), incrementalBuildIncremental, new MobileIconsInteractorKairosAdapter$$ExternalSyntheticLambda2()), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.alwaysShowDataRatIcon, kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.alwaysUseCdmaLevel, kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isSingleCarrier = FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.isSingleCarrier, kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.mobileConnectionsRepo.getDefaultMobileIconMapping(), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MapsKt__MapsKt.emptyMap());
        FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.mobileConnectionsRepo.getDefaultMobileIconGroup(), kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((MobileMappingsProxyImpl) mobileMappingsProxy).getDefaultIcons(MobileMappings.Config.readConfig(context)));
        FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.isDefaultConnectionFailed, kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        FlowKt.stateIn(ToColdFlowKt.toColdConflatedFlow(mobileIconsInteractorKairosImpl.isForceHidden, kairosNetwork), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final ReadonlyStateFlow getActiveDataIconInteractor() {
        return this.activeDataIconInteractor;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow getActiveMobileDataSubscriptionId() {
        return this.repo.getActiveMobileDataSubscriptionId();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow getDefaultDataSubId$1() {
        return this.repo.getDefaultDataSubId();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final Flow getFilteredSubscriptions() {
        return this.filteredSubscriptions;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow getIcons() {
        return this.icons;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final MobileIconInteractor getMobileConnectionInteractorForSubId(int i) {
        return new MobileIconInteractor(i) { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter.getMobileConnectionInteractorForSubId.1
            public final /* synthetic */ int $subId;
            public final Flow activity;
            public final Flow alwaysShowDataRatIcon;
            public final Flow carrierName;
            public final Flow carrierNetworkChangeActive;
            public final Flow disabledActivityIcon;
            public final Flow disabledDataIcon;
            public final Flow imsRegState;
            public final Flow isAllowedDuringAirplaneMode;
            public final Flow isDataConnected;
            public final Flow isDataEnabled;
            public final Flow isEmergencyOnly;
            public final Flow isForceHidden;
            public final Flow isInService;
            public final Flow isNonTerrestrial;
            public final Flow isRoaming;
            public final Flow isSim1On;
            public final Flow isSimOn;
            public final Flow isSingleCarrier;
            public final Flow isVoWifiConnected;
            public final Flow mobileIsDefault;
            public final Flow mobileServiceState;
            public final Flow networkName;
            public final Flow networkTypeIconGroup;
            public final Flow otherSlotInCallState;
            public final Flow roamingId;
            public final Flow showSliceAttribution;
            public final Flow signalLevelIcon;
            public final TableLogBuffer tableLogBuffer;
            public final Flow voiceNoServiceIcon;

            {
                this.$subId = i;
                TableLogBufferFactory tableLogBufferFactory = MobileIconsInteractorKairosAdapter.this.logFactory;
                FullMobileConnectionRepository.Factory.Companion.getClass();
                this.tableLogBuffer = tableLogBufferFactory.getOrCreate(100, "MobileConnectionLog[" + i + "]");
                final int i2 = 0;
                this.activity = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i2) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i3 = 2;
                this.mobileIsDefault = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i3) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i4 = 12;
                this.isDataConnected = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i4) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i5 = 13;
                this.isInService = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i5) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i6 = 14;
                this.isEmergencyOnly = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i6) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i7 = 15;
                this.isDataEnabled = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i7) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i8 = 16;
                this.alwaysShowDataRatIcon = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i8) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i9 = 17;
                this.signalLevelIcon = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i9) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i10 = 18;
                this.networkTypeIconGroup = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i10) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i11 = 19;
                this.showSliceAttribution = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i11) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i12 = 11;
                this.isNonTerrestrial = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i12) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i13 = 20;
                this.networkName = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i13) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i14 = 21;
                this.carrierName = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i14) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i15 = 22;
                this.isSingleCarrier = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i15) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i16 = 23;
                this.isRoaming = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i16) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i17 = 24;
                this.roamingId = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i17) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i18 = 25;
                this.isForceHidden = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i18) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i19 = 26;
                this.isAllowedDuringAirplaneMode = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i19) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i20 = 27;
                this.carrierNetworkChangeActive = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i20) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i21 = 1;
                this.isSimOn = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i21) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i22 = 3;
                this.isSim1On = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i22) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i23 = 4;
                this.mobileServiceState = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i23) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i24 = 5;
                this.disabledDataIcon = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i24) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i25 = 6;
                this.disabledActivityIcon = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i25) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i26 = 7;
                this.otherSlotInCallState = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i26) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i27 = 8;
                this.voiceNoServiceIcon = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i27) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i28 = 9;
                this.imsRegState = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i28) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
                final int i29 = 10;
                this.isVoWifiConnected = latest(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MobileIconInteractor mobileIconInteractor = (MobileIconInteractor) obj;
                        switch (i29) {
                            case 0:
                                return mobileIconInteractor.getActivity();
                            case 1:
                                return mobileIconInteractor.isSimOn();
                            case 2:
                                return mobileIconInteractor.getMobileIsDefault();
                            case 3:
                                return mobileIconInteractor.isSim1On();
                            case 4:
                                return mobileIconInteractor.getMobileServiceState();
                            case 5:
                                return mobileIconInteractor.getDisabledDataIcon();
                            case 6:
                                return mobileIconInteractor.getDisabledActivityIcon();
                            case 7:
                                return mobileIconInteractor.getOtherSlotInCallState();
                            case 8:
                                return mobileIconInteractor.getVoiceNoServiceIcon();
                            case 9:
                                return mobileIconInteractor.getImsRegState();
                            case 10:
                                return mobileIconInteractor.isVoWifiConnected();
                            case 11:
                                return mobileIconInteractor.isNonTerrestrial();
                            case 12:
                                return mobileIconInteractor.isDataConnected();
                            case 13:
                                return mobileIconInteractor.isInService();
                            case 14:
                                return mobileIconInteractor.isEmergencyOnly();
                            case 15:
                                return mobileIconInteractor.isDataEnabled();
                            case 16:
                                return mobileIconInteractor.getAlwaysShowDataRatIcon();
                            case 17:
                                return mobileIconInteractor.getSignalLevelIcon();
                            case 18:
                                return mobileIconInteractor.getNetworkTypeIconGroup();
                            case 19:
                                return mobileIconInteractor.getShowSliceAttribution();
                            case 20:
                                return mobileIconInteractor.getNetworkName();
                            case 21:
                                return mobileIconInteractor.getCarrierName();
                            case 22:
                                return mobileIconInteractor.isSingleCarrier();
                            case 23:
                                return mobileIconInteractor.isRoaming();
                            case 24:
                                return mobileIconInteractor.getRoamingId();
                            case 25:
                                return mobileIconInteractor.isForceHidden();
                            case 26:
                                return mobileIconInteractor.isAllowedDuringAirplaneMode();
                            default:
                                return mobileIconInteractor.getCarrierNetworkChangeActive();
                        }
                    }
                });
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getActivity() {
                return this.activity;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getAlwaysShowDataRatIcon() {
                return this.alwaysShowDataRatIcon;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getCarrierName() {
                return this.carrierName;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getCarrierNetworkChangeActive() {
                return this.carrierNetworkChangeActive;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getDisabledActivityIcon() {
                return this.disabledActivityIcon;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getDisabledDataIcon() {
                return this.disabledDataIcon;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getImsRegState() {
                return this.imsRegState;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getMobileIsDefault() {
                return this.mobileIsDefault;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getMobileServiceState() {
                return this.mobileServiceState;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getNetworkName() {
                return this.networkName;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getNetworkTypeIconGroup() {
                return this.networkTypeIconGroup;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getOtherSlotInCallState() {
                return this.otherSlotInCallState;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getRoamingId() {
                return this.roamingId;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getShowSliceAttribution() {
                return this.showSliceAttribution;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getSignalLevelIcon() {
                return this.signalLevelIcon;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final int getSlotId() {
                return 0;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final TableLogBuffer getTableLogBuffer() {
                return this.tableLogBuffer;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow getVoiceNoServiceIcon() {
                return this.voiceNoServiceIcon;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isAllowedDuringAirplaneMode() {
                return this.isAllowedDuringAirplaneMode;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isDataConnected() {
                return this.isDataConnected;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isDataEnabled() {
                return this.isDataEnabled;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isEmergencyOnly() {
                return this.isEmergencyOnly;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isForceHidden() {
                return this.isForceHidden;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isInService() {
                return this.isInService;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isNonTerrestrial() {
                return this.isNonTerrestrial;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isRoaming() {
                return this.isRoaming;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isSim1On() {
                return this.isSim1On;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isSimOn() {
                return this.isSimOn;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isSingleCarrier() {
                return this.isSingleCarrier;
            }

            @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
            public final Flow isVoWifiConnected() {
                return this.isVoWifiConnected;
            }

            public final Flow latest(Function1 function1) {
                return LatestConflatedKt.flatMapLatestConflated(MobileIconsInteractorKairosAdapter.this.interactorsBySubId, new MobileIconsInteractorKairosAdapter$getMobileConnectionInteractorForSubId$1$latest$1(this.$subId, function1, null));
            }
        };
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow isDeviceInEmergencyCallsOnlyMode() {
        return this.repo.isDeviceEmergencyCallCapable();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final StateFlow isSingleCarrier() {
        return this.isSingleCarrier;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor
    public final Flow isStackable() {
        return this.isStackable;
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
