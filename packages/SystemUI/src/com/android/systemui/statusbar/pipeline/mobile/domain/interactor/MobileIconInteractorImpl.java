package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import android.os.Handler;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.internal.telephony.flags.Flags;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileIconCarrierIdOverrides;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DisabledDataIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DisabledDataIconModelKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.MobileSignalIconResource;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

public final class MobileIconInteractorImpl implements MobileIconInteractor {
    public final StateFlow activeDataSubId;
    public final StateFlow activity;
    public final StateFlow alwaysShowDataRatIcon;
    public final Handler bgHandler;
    public final boolean bootstrapProfile;
    public final ReadonlyStateFlow carrierIdIconOverrideExists;
    public final CarrierInfraMediator carrierInfraMediator;
    public final ReadonlyStateFlow carrierName;
    public final StateFlow carrierNetworkChangeActive;
    public final MobileIconInteractorImpl$special$$inlined$combine$4 cellularIcon;
    public final ReadonlyStateFlow cellularShownLevel;
    public final FakeMobileConnectionRepository connectionRepository;
    public final ReadonlyStateFlow contentDescription;
    public final MobileDataIconResource dataIconResource;
    public final ReadonlyStateFlow defaultNetworkType;
    public final ReadonlyStateFlow disabledActivityIcon;
    public final ReadonlyStateFlow disabledDataIcon;
    public final MobileDisabledDataIconResource disabledDataIconResource;
    public final StateFlow isAllowedDuringAirplaneMode;
    public final ReadonlyStateFlow isDataConnected;
    public final StateFlow isDataEnabled;
    public final boolean isDummySubId;
    public final StateFlow isEmergencyOnly;
    public final Flow isForceHidden;
    public final StateFlow isInService;
    public final ReadonlyStateFlow isNonTerrestrial;
    public final ReadonlyStateFlow isRoaming;
    public final ReadonlyStateFlow isSim1On;
    public final ReadonlyStateFlow isSimOn;
    public final StateFlow isSingleCarrier;
    public final ReadonlyStateFlow isUserSetup;
    public final ReadonlyStateFlow isVoWifiConnected;
    public final ReadonlyStateFlow level;
    public final StateFlow mobileDataEnabledChanged;
    public final StateFlow mobileIsDefault;
    public final StateFlow mobileServiceState;
    public final MobileSignalTransitionManager mobileSignalTransition;
    public final ReadonlyStateFlow networkName;
    public final ReadonlyStateFlow networkTypeIconGroup;
    public final StateFlow numberOfLevels;
    public final ReadonlyStateFlow otherSlotInCallState;
    public final MobileRoamingIconResource roamingIconResource;
    public final ReadonlyStateFlow roamingId;
    public final MobileIconInteractorImpl$special$$inlined$map$3 satelliteIcon;
    public final ReadonlyStateFlow satelliteShownLevel;
    public final ReadonlyStateFlow shouldShowDisabledDataIcon;
    public final ReadonlyStateFlow showExclamationMark;
    public final ReadonlyStateFlow showSliceAttribution;
    public final MobileSignalIconResource signalIconResource;
    public final ReadonlyStateFlow signalLevelIcon;
    public final ReadonlyStateFlow signalLevelUpdate;
    public final int slotId;
    public final TableLogBuffer tableLogBuffer;
    public final ReadonlyStateFlow updateSignalTransition;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 updatedMobileIconMapping;
    public final ReadonlyStateFlow voiceNoServiceIcon;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 wifiConnected;

    /* JADX WARN: Type inference failed for: r3v19, types: [com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3] */
    /* JADX WARN: Type inference failed for: r7v30, types: [com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4] */
    public MobileIconInteractorImpl(CoroutineScope coroutineScope, StateFlow stateFlow, StateFlow stateFlow2, StateFlow stateFlow3, StateFlow stateFlow4, StateFlow stateFlow5, StateFlow stateFlow6, Flow flow, StateFlow stateFlow7, StateFlow stateFlow8, StateFlow stateFlow9, Flow flow2, MobileConnectionRepository mobileConnectionRepository, Context context, UserSetupRepository userSetupRepository, WifiRepository wifiRepository, MobileDataIconResource mobileDataIconResource, MobileSignalIconResource mobileSignalIconResource, MobileRoamingIconResource mobileRoamingIconResource, MobileDisabledDataIconResource mobileDisabledDataIconResource, CarrierInfraMediator carrierInfraMediator, MobileMappingsProxy mobileMappingsProxy, StateFlow stateFlow10, boolean z, Handler handler, MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides) {
        this.alwaysShowDataRatIcon = stateFlow2;
        this.isSingleCarrier = stateFlow4;
        this.mobileIsDefault = stateFlow5;
        this.isForceHidden = flow2;
        this.dataIconResource = mobileDataIconResource;
        this.signalIconResource = mobileSignalIconResource;
        this.roamingIconResource = mobileRoamingIconResource;
        this.disabledDataIconResource = mobileDisabledDataIconResource;
        this.carrierInfraMediator = carrierInfraMediator;
        this.bootstrapProfile = z;
        this.bgHandler = handler;
        FakeMobileConnectionRepository fakeMobileConnectionRepository = new FakeMobileConnectionRepository(mobileConnectionRepository, mobileMappingsProxy);
        this.connectionRepository = fakeMobileConnectionRepository;
        int i = fakeMobileConnectionRepository.slotId;
        this.slotId = i;
        TableLogBuffer tableLogBuffer = fakeMobileConnectionRepository.tableLogBuffer;
        this.tableLogBuffer = tableLogBuffer;
        this.activity = fakeMobileConnectionRepository.dataActivityDirection;
        this.isDataEnabled = fakeMobileConnectionRepository.dataEnabled;
        StateFlow stateFlow11 = fakeMobileConnectionRepository.carrierNetworkChangeActive;
        this.carrierNetworkChangeActive = stateFlow11;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(fakeMobileConnectionRepository.carrierId, fakeMobileConnectionRepository.resolvedNetworkType, fakeMobileConnectionRepository.mobileServiceState, flow, new MobileIconInteractorImpl$carrierIdIconOverrideExists$1(this, null)));
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(distinctUntilChanged, coroutineScope, WhileSubscribed$default, bool);
        MobileIconInteractorImpl$networkName$1 mobileIconInteractorImpl$networkName$1 = new MobileIconInteractorImpl$networkName$1(null);
        StateFlow stateFlow12 = fakeMobileConnectionRepository.operatorAlphaShort;
        StateFlow stateFlow13 = fakeMobileConnectionRepository.networkName;
        this.networkName = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow12, stateFlow13, mobileIconInteractorImpl$networkName$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlow13.getValue());
        MobileIconInteractorImpl$carrierName$1 mobileIconInteractorImpl$carrierName$1 = new MobileIconInteractorImpl$carrierName$1(null);
        StateFlow stateFlow14 = fakeMobileConnectionRepository.carrierName;
        this.carrierName = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow12, stateFlow14, mobileIconInteractorImpl$carrierName$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((NetworkNameModel) stateFlow14.getValue()).getName());
        this.mobileSignalTransition = new MobileSignalTransitionManager();
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.combine(fakeMobileConnectionRepository.carrierNetworkChangeActive, fakeMobileConnectionRepository.isGsm, fakeMobileConnectionRepository.isRoaming, fakeMobileConnectionRepository.cdmaRoaming, fakeMobileConnectionRepository.swRoaming, new MobileIconInteractorImpl$isRoaming$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isRoaming = stateIn2;
        final StateFlow stateFlow15 = fakeMobileConnectionRepository.mobileServiceState;
        this.mobileServiceState = stateFlow15;
        MobileIconInteractorImpl$updatedMobileIconMapping$1 mobileIconInteractorImpl$updatedMobileIconMapping$1 = new MobileIconInteractorImpl$updatedMobileIconMapping$1(this, null);
        final StateFlow stateFlow16 = fakeMobileConnectionRepository.simCardInfo;
        final Flow[] flowArr = {fakeMobileConnectionRepository.resolvedNetworkType, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, stateFlow16, mobileIconInteractorImpl$updatedMobileIconMapping$1), new MobileIconInteractorImpl$updatedMobileIconMapping$2(this, null)), stateFlow8, fakeMobileConnectionRepository.simCardInfo, fakeMobileConnectionRepository.onTheCall, stateIn2, stateFlow15};
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MobileIconInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    super(3, continuation);
                    this.this$0 = mobileIconInteractorImpl;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:29:0x00c0, code lost:
                
                    if ((com.android.systemui.BasicRune.STATUS_NETWORK_MULTI_SIM ? "VZW".equals((java.lang.String) r5.get(com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Values.ICON_BRANDING_FROM_CARRIER_FEATURE, r8, new java.lang.Object[0])) : r10 == com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.VZW) == false) goto L30;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r19) {
                    /*
                        Method dump skipped, instructions count: 690
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlow8.getValue()), stateIn, new MobileIconInteractorImpl$networkTypeIconGroup$1(this, null))), tableLogBuffer, "", new NetworkTypeIconModel.DefaultIcon((SignalIcon$MobileIconGroup) stateFlow8.getValue())), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new NetworkTypeIconModel.DefaultIcon((SignalIcon$MobileIconGroup) stateFlow8.getValue()));
        this.networkTypeIconGroup = stateIn3;
        this.showSliceAttribution = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.allowNetworkSliceIndicator, fakeMobileConnectionRepository.hasPrioritizedNetworkCapabilities, new MobileIconInteractorImpl$showSliceAttribution$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        ReadonlyStateFlow stateIn4 = FlowKt.stateIn(DiffableKt.logDiffsForTable(Flags.carrierEnabledSatelliteFlag() ? new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.isNonTerrestrial, fakeMobileConnectionRepository.semSatelliteEnabled, new MobileIconInteractorImpl$isNonTerrestrial$1(this, null)) : FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(bool)), tableLogBuffer, "Intr", LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "(", ")isNonTerrestrial"), false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isNonTerrestrial = stateIn4;
        ReadonlyStateFlow stateIn5 = FlowKt.stateIn(FlowKt.combine(fakeMobileConnectionRepository.isGsm, fakeMobileConnectionRepository.primaryLevel, fakeMobileConnectionRepository.cdmaLevel, stateFlow3, new MobileIconInteractorImpl$level$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        StateFlow stateFlow17 = fakeMobileConnectionRepository.numberOfLevels;
        this.numberOfLevels = stateFlow17;
        final StateFlow stateFlow18 = fakeMobileConnectionRepository.dataConnectionState;
        ReadonlyStateFlow stateIn6 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState r5 = (com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState) r5
                        com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState r6 = com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState.Connected
                        if (r5 != r6) goto L3a
                        r5 = r3
                        goto L3b
                    L3a:
                        r5 = 0
                    L3b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isDataConnected = stateIn6;
        StateFlow stateFlow19 = fakeMobileConnectionRepository.isInService;
        this.isInService = stateFlow19;
        StateFlow stateFlow20 = fakeMobileConnectionRepository.isEmergencyOnly;
        this.isEmergencyOnly = stateFlow20;
        this.isAllowedDuringAirplaneMode = fakeMobileConnectionRepository.isAllowedDuringAirplaneMode;
        this.isSimOn = FlowKt.stateIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SimCardModel r5 = (com.android.systemui.statusbar.pipeline.mobile.data.model.SimCardModel) r5
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SimType r5 = r5.simType
                        com.android.systemui.statusbar.pipeline.mobile.data.model.SimType r6 = com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.OFF
                        if (r5 == r6) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "Intr", LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "(", ")isSimOn"), false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isSim1On = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) fakeMobileConnectionRepository.sim1On, tableLogBuffer, "Intr", "isSim1On", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isDummySubId = fakeMobileConnectionRepository.subId == Integer.MAX_VALUE;
        this.roamingId = FlowKt.stateIn(FlowKt.combine(stateFlow19, stateIn2, stateFlow15, fakeMobileConnectionRepository.swRoaming, fakeMobileConnectionRepository.semOMCChangedEvent, new MobileIconInteractorImpl$roamingId$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        ReadonlyStateFlow stateIn7 = FlowKt.stateIn(FlowKt.combine(stateFlow, stateFlow9, stateFlow19, new MobileIconInteractorImpl$showExclamationMark$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.TRUE);
        ReadonlyStateFlow stateIn8 = FlowKt.stateIn(FlowKt.combine(stateFlow19, stateIn5, stateFlow17, new MobileIconInteractorImpl$updateSignalTransition$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Unit.INSTANCE);
        ReadonlyStateFlow stateIn9 = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new MobileIconInteractorImpl$signalLevelUpdate$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        ReadonlyStateFlow stateIn10 = FlowKt.stateIn(FlowKt.combine(stateIn5, stateFlow19, fakeMobileConnectionRepository.inflateSignalStrength, stateFlow20, new MobileIconInteractorImpl$cellularShownLevel$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.cellularShownLevel = stateIn10;
        this.contentDescription = FlowKt.stateIn(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn10, stateFlow17, new MobileIconInteractorImpl$contentDescription$1(this, null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new ContentDescription.Resource(AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH[0]));
        ReadonlyStateFlow readonlyStateFlow = ((UserSetupRepositoryImpl) userSetupRepository).isUserSetUp;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(wifiRepository.getWifiNetwork(), wifiRepository.isWifiDefault(), new MobileIconInteractorImpl$wifiConnected$1(null));
        ReadonlyStateFlow stateIn11 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.imsRegState, stateFlow15, new MobileIconInteractorImpl$isVoWifiConnected$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isVoWifiConnected = stateIn11;
        final Flow[] flowArr2 = {stateIn6, stateFlow5, stateFlow19, stateFlow20, readonlyStateFlow, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, stateFlow6, stateIn11};
        ReadonlyStateFlow stateIn12 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MobileIconInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    super(3, continuation);
                    this.this$0 = mobileIconInteractorImpl;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:28:0x00bd, code lost:
                
                    if (r11 == false) goto L30;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:29:0x00bf, code lost:
                
                    r5 = true;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:35:0x00df, code lost:
                
                    if (r12.intValue() == r16.this$0.connectionRepository.subId) goto L30;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:36:0x00e2, code lost:
                
                    if (r9 == false) goto L30;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r17) {
                    /*
                        Method dump skipped, instructions count: 246
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr3 = flowArr2;
                Object combineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr3.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }), tableLogBuffer, "Intr", "showDisabledData", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        ReadonlyStateFlow stateIn13 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.onTheCall, stateFlow10, new MobileIconInteractorImpl$otherSlotInCallState$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.otherSlotInCallState = stateIn13;
        final Flow[] flowArr3 = {stateIn12, stateIn3, stateFlow19, stateIn2, stateIn13, stateFlow15};
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$3

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$3$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MobileIconInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    super(3, continuation);
                    this.this$0 = mobileIconInteractorImpl;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    DisabledDataIconModel disabledDataIconModel;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
                        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) objArr[1];
                        boolean booleanValue2 = ((Boolean) objArr[2]).booleanValue();
                        boolean booleanValue3 = ((Boolean) objArr[3]).booleanValue();
                        boolean booleanValue4 = ((Boolean) objArr[4]).booleanValue();
                        MobileServiceState mobileServiceState = (MobileServiceState) objArr[5];
                        if (booleanValue) {
                            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                            disabledDataIconModel = mobileIconInteractorImpl.disabledDataIconResource.getTypeIcon(mobileIconInteractorImpl.slotId, networkTypeIconModel, booleanValue2, booleanValue3, booleanValue4, mobileServiceState.mSimSubmode == 1);
                        } else {
                            MobileIconInteractorImpl mobileIconInteractorImpl2 = this.this$0;
                            disabledDataIconModel = mobileIconInteractorImpl2.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DISABLED_ICON, mobileIconInteractorImpl2.slotId, new Object[0]) ? DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ROAMING_ICON : DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ICON;
                        }
                        this.label = 1;
                        if (flowCollector.emit(disabledDataIconModel, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr4 = flowArr3;
                Object combineInternal = CombineKt.combineInternal(flowArr4, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$3.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr4.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        });
        DisabledDataIconModel disabledDataIconModel = DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ICON;
        this.disabledDataIcon = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged2, tableLogBuffer, "Intr", disabledDataIconModel), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), disabledDataIconModel);
        this.disabledActivityIcon = FlowKt.stateIn(DiffableKt.logDiffsForTable(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn12, fakeMobileConnectionRepository.mobileDataEnabledChanged, new MobileIconInteractorImpl$disabledActivityIcon$1(this, null)), tableLogBuffer, "Intr", "disabledActivityIcon", 0), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        ReadonlyStateFlow stateIn14 = FlowKt.stateIn(Flags.carrierRoamingNbIotNtn() ? fakeMobileConnectionRepository.satelliteLevel : new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn5, stateFlow19, new MobileIconInteractorImpl$satelliteShownLevel$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        final Flow[] flowArr4 = {stateIn10, stateFlow17, stateIn7, stateFlow11, stateFlow19, stateFlow20, stateIn12, stateFlow15, fakeMobileConnectionRepository.imsRegState, stateIn8, stateIn9};
        this.cellularIcon = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MobileIconInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    super(3, continuation);
                    this.this$0 = mobileIconInteractorImpl;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Removed duplicated region for block: B:18:0x01cb A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:67:0x01ab  */
                /* JADX WARN: Removed duplicated region for block: B:68:0x01b2  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r21) {
                    /*
                        Method dump skipped, instructions count: 463
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr5 = flowArr4;
                Object combineInternal = CombineKt.combineInternal(flowArr5, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr5.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn14, fakeMobileConnectionRepository.semSatelliteSignalStrength, new MobileIconInteractorImpl$satelliteIcon$1(this, null));
        this.satelliteIcon = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconInteractorImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconInteractorImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3$2$1
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto L9e
                    L28:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L30:
                        kotlin.ResultKt.throwOnFailure(r11)
                        java.lang.Number r10 = (java.lang.Number) r10
                        int r10 = r10.intValue()
                        com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel$Satellite r11 = new com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel$Satellite
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl r2 = r9.this$0
                        com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator r4 = r2.carrierInfraMediator
                        com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator$Conditions r5 = com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Conditions.IS_CHINA_DEVICE
                        r6 = 0
                        java.lang.Object[] r7 = new java.lang.Object[r6]
                        int r8 = r2.slotId
                        boolean r4 = r4.isEnabled(r5, r8, r7)
                        if (r4 == 0) goto L5f
                        com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.INSTANCE
                        r2.getClass()
                        com.android.systemui.common.shared.model.Icon$Resource r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.fromSignalStrengthCN(r10)
                        if (r2 != 0) goto L90
                        com.android.systemui.common.shared.model.Icon$Resource r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.fromSignalStrengthCN(r6)
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
                        goto L90
                    L5f:
                        com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator$Conditions r4 = com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Conditions.IS_USA_VZW
                        java.lang.Object[] r5 = new java.lang.Object[r6]
                        com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator r2 = r2.carrierInfraMediator
                        boolean r2 = r2.isEnabled(r4, r8, r5)
                        if (r2 == 0) goto L7e
                        com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.INSTANCE
                        r2.getClass()
                        com.android.systemui.common.shared.model.Icon$Resource r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.fromSignalStrengthVZW(r10)
                        if (r2 != 0) goto L90
                        com.android.systemui.common.shared.model.Icon$Resource r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.fromSignalStrengthVZW(r6)
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
                        goto L90
                    L7e:
                        com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.INSTANCE
                        r2.getClass()
                        com.android.systemui.common.shared.model.Icon$Resource r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.fromSignalStrength(r10)
                        if (r2 != 0) goto L90
                        com.android.systemui.common.shared.model.Icon$Resource r2 = com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel.fromSignalStrength(r6)
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
                    L90:
                        r11.<init>(r10, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                        java.lang.Object r9 = r9.emit(r11, r0)
                        if (r9 != r1) goto L9e
                        return r1
                    L9e:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SignalIconModel.Cellular cellular = new SignalIconModel.Cellular(((Number) stateIn10.$$delegate_0.getValue()).intValue(), ((Number) stateFlow17.getValue()).intValue(), ((Boolean) stateIn7.$$delegate_0.getValue()).booleanValue(), ((Boolean) stateFlow11.getValue()).booleanValue(), 0, 16, null);
        this.signalLevelIcon = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.transformLatest(stateIn4, new MobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1(null, this))), tableLogBuffer, "icon", cellular), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), cellular);
        this.voiceNoServiceIcon = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4

            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconInteractorImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconInteractorImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L6e
                    L27:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r9)
                        com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState r8 = (com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState) r8
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl r9 = r7.this$0
                        com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator r2 = r9.carrierInfraMediator
                        com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator$Conditions r4 = com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Conditions.USE_VOICE_NO_SERVICE_ICON
                        r5 = 0
                        java.lang.Object[] r6 = new java.lang.Object[r5]
                        boolean r2 = r2.isEnabled(r4, r5, r6)
                        if (r2 == 0) goto L5e
                        boolean r2 = r8.vioceCallAvailable
                        if (r2 != 0) goto L5e
                        int r8 = r8.dataRegState
                        if (r8 != 0) goto L5e
                        com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator$Conditions r8 = com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Conditions.IS_VOICE_CAPABLE
                        java.lang.Object[] r2 = new java.lang.Object[r5]
                        com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator r9 = r9.carrierInfraMediator
                        boolean r8 = r9.isEnabled(r8, r5, r2)
                        if (r8 == 0) goto L5e
                        com.android.systemui.statusbar.pipeline.mobile.ui.util.SamsungMobileIcons$Companion r8 = com.android.systemui.statusbar.pipeline.mobile.ui.util.SamsungMobileIcons.Companion
                        r8.getClass()
                        int r5 = com.android.systemui.statusbar.pipeline.mobile.ui.util.SamsungMobileIcons.VOICE_NO_SERVICE
                    L5e:
                        java.lang.Integer r8 = new java.lang.Integer
                        r8.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r8, r0)
                        if (r7 != r1) goto L6e
                        return r1
                    L6e:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
    }

    public /* synthetic */ MobileIconInteractorImpl(CoroutineScope coroutineScope, StateFlow stateFlow, StateFlow stateFlow2, StateFlow stateFlow3, StateFlow stateFlow4, StateFlow stateFlow5, StateFlow stateFlow6, Flow flow, StateFlow stateFlow7, StateFlow stateFlow8, StateFlow stateFlow9, Flow flow2, MobileConnectionRepository mobileConnectionRepository, Context context, UserSetupRepository userSetupRepository, WifiRepository wifiRepository, MobileDataIconResource mobileDataIconResource, MobileSignalIconResource mobileSignalIconResource, MobileRoamingIconResource mobileRoamingIconResource, MobileDisabledDataIconResource mobileDisabledDataIconResource, CarrierInfraMediator carrierInfraMediator, MobileMappingsProxy mobileMappingsProxy, StateFlow stateFlow10, boolean z, Handler handler, MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, stateFlow, stateFlow2, stateFlow3, stateFlow4, stateFlow5, stateFlow6, flow, stateFlow7, stateFlow8, stateFlow9, flow2, mobileConnectionRepository, context, userSetupRepository, wifiRepository, mobileDataIconResource, mobileSignalIconResource, mobileRoamingIconResource, mobileDisabledDataIconResource, carrierInfraMediator, mobileMappingsProxy, stateFlow10, z, handler, (i & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) != 0 ? new MobileIconCarrierIdOverridesImpl() : mobileIconCarrierIdOverrides);
    }
}
