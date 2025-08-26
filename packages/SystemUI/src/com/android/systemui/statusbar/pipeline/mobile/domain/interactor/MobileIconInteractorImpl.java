package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import android.os.Handler;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileIconCarrierIdOverrides;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.BasicRune;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimCardModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DisabledDataIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DisabledDataIconModelKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.MobileSignalIconResource;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.SamsungMobileIcons;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes3.dex */
public final class MobileIconInteractorImpl implements MobileIconInteractor {
    public final StateFlow activeDataSubId;
    public final StateFlow activity;
    public final StateFlow alwaysShowDataRatIcon;
    public final Handler bgHandler;
    public final boolean bootstrapProfile;
    public final ReadonlyStateFlow carrierIdIconOverrideExists;
    public final Integer[] carrierIdOfVzwMVNO;
    public final CarrierInfraMediator carrierInfraMediator;
    public final ReadonlyStateFlow carrierName;
    public final StateFlow carrierNetworkChangeActive;
    public final MobileIconInteractorImpl$special$$inlined$combine$4 cellularIcon;
    public final ReadonlyStateFlow cellularShownLevel;
    public final FakeMobileConnectionRepository connectionRepository;
    public final MobileDataIconResource dataIconResource;
    public final ReadonlyStateFlow defaultNetworkType;
    public final ReadonlyStateFlow disabledActivityIcon;
    public final ReadonlyStateFlow disabledDataIcon;
    public final MobileDisabledDataIconResource disabledDataIconResource;
    public final StateFlow imsRegState;
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
    public final Flow satelliteIcon;
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

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$isNonTerrestrial$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            AnonymousClass1 anonymousClass1 = MobileIconInteractorImpl.this.new AnonymousClass1((Continuation) obj3);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.Z$1 = zBooleanValue2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            boolean z;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z2 = this.Z$0;
            boolean z3 = this.Z$1;
            if (!z2) {
                MobileIconInteractorImpl mobileIconInteractorImpl = MobileIconInteractorImpl.this;
                z = false;
                if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DEVICE, mobileIconInteractorImpl.slotId, new Object[0]) && z3) {
                    z = true;
                }
            }
            return Boolean.valueOf(z);
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$isRoaming$1, reason: invalid class name and case insensitive filesystem */
    final class C11041 extends SuspendLambda implements Function6 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        /* synthetic */ boolean Z$2;
        /* synthetic */ boolean Z$3;
        /* synthetic */ boolean Z$4;
        int label;

        public C11041(Continuation continuation) {
            super(6, continuation);
        }

        @Override // kotlin.jvm.functions.Function6
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
            boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
            boolean zBooleanValue5 = ((Boolean) obj5).booleanValue();
            C11041 c11041 = new C11041((Continuation) obj6);
            c11041.Z$0 = zBooleanValue;
            c11041.Z$1 = zBooleanValue2;
            c11041.Z$2 = zBooleanValue3;
            c11041.Z$3 = zBooleanValue4;
            c11041.Z$4 = zBooleanValue5;
            return c11041.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            boolean z3 = this.Z$2;
            boolean z4 = this.Z$3;
            boolean z5 = this.Z$4;
            boolean z6 = false;
            if (!z && (z5 || (!z2 ? z4 : z3))) {
                z6 = true;
            }
            return Boolean.valueOf(z6);
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$isVoWifiConnected$1, reason: invalid class name and case insensitive filesystem */
    final class C11051 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C11051(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C11051 c11051 = new C11051((Continuation) obj3);
            c11051.L$0 = (ImsRegState) obj;
            c11051.L$1 = (MobileServiceState) obj2;
            return c11051.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(((ImsRegState) this.L$0).voWifiRegState && ((MobileServiceState) this.L$1).telephonyDisplayInfo.getNetworkType() == 18);
        }
    }

    /* JADX WARN: Type inference failed for: r10v38, types: [com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4] */
    public MobileIconInteractorImpl(CoroutineScope coroutineScope, StateFlow stateFlow, StateFlow stateFlow2, StateFlow stateFlow3, StateFlow stateFlow4, StateFlow stateFlow5, StateFlow stateFlow6, Flow flow, StateFlow stateFlow7, StateFlow stateFlow8, StateFlow stateFlow9, Flow flow2, MobileConnectionRepository mobileConnectionRepository, Context context, UserSetupRepository userSetupRepository, WifiRepository wifiRepository, MobileDataIconResource mobileDataIconResource, MobileSignalIconResource mobileSignalIconResource, MobileRoamingIconResource mobileRoamingIconResource, MobileDisabledDataIconResource mobileDisabledDataIconResource, CarrierInfraMediator carrierInfraMediator, MobileMappingsProxy mobileMappingsProxy, StateFlow stateFlow10, boolean z, Handler handler, MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides) {
        Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.alwaysShowDataRatIcon = stateFlow2;
        this.isSingleCarrier = stateFlow4;
        this.mobileIsDefault = stateFlow5;
        this.activeDataSubId = stateFlow6;
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
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(fakeMobileConnectionRepository.carrierId, fakeMobileConnectionRepository.resolvedNetworkType, fakeMobileConnectionRepository.mobileServiceState, flow, new MobileIconInteractorImpl$carrierIdIconOverrideExists$1(this, null)));
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowDistinctUntilChanged, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        this.carrierIdIconOverrideExists = readonlyStateFlowStateIn;
        MobileIconInteractorImpl$networkName$1 mobileIconInteractorImpl$networkName$1 = new MobileIconInteractorImpl$networkName$1(null);
        StateFlow stateFlow12 = fakeMobileConnectionRepository.operatorAlphaShort;
        StateFlow stateFlow13 = fakeMobileConnectionRepository.networkName;
        this.networkName = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow12, stateFlow13, mobileIconInteractorImpl$networkName$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlow13.getValue());
        MobileIconInteractorImpl$carrierName$1 mobileIconInteractorImpl$carrierName$1 = new MobileIconInteractorImpl$carrierName$1(null);
        StateFlow stateFlow14 = fakeMobileConnectionRepository.carrierName;
        this.carrierName = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow12, stateFlow14, mobileIconInteractorImpl$carrierName$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ((NetworkNameModel) stateFlow14.getValue()).getName());
        this.mobileSignalTransition = new MobileSignalTransitionManager();
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(FlowKt.combine(fakeMobileConnectionRepository.carrierNetworkChangeActive, fakeMobileConnectionRepository.isGsm, fakeMobileConnectionRepository.isRoaming, fakeMobileConnectionRepository.cdmaRoaming, fakeMobileConnectionRepository.swRoaming, new C11041(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isRoaming = readonlyStateFlowStateIn2;
        final StateFlow stateFlow15 = fakeMobileConnectionRepository.mobileServiceState;
        this.mobileServiceState = stateFlow15;
        StateFlow stateFlow16 = fakeMobileConnectionRepository.imsRegState;
        this.imsRegState = stateFlow16;
        MobileIconInteractorImpl$updatedMobileIconMapping$1 mobileIconInteractorImpl$updatedMobileIconMapping$1 = new MobileIconInteractorImpl$updatedMobileIconMapping$1(this, null);
        final StateFlow stateFlow17 = fakeMobileConnectionRepository.simCardInfo;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, stateFlow17, mobileIconInteractorImpl$updatedMobileIconMapping$1), new MobileIconInteractorImpl$updatedMobileIconMapping$2(this, null));
        this.updatedMobileIconMapping = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        final Flow[] flowArr = {fakeMobileConnectionRepository.resolvedNetworkType, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, stateFlow8, fakeMobileConnectionRepository.simCardInfo, fakeMobileConnectionRepository.onTheCall, readonlyStateFlowStateIn2, stateFlow15};
        ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1

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

                /* JADX WARN: Removed duplicated region for block: B:101:0x022d  */
                /* JADX WARN: Removed duplicated region for block: B:110:0x0257  */
                /* JADX WARN: Removed duplicated region for block: B:29:0x00ca  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    int i;
                    String lookupKey;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = this.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean zEquals = false;
                        ResolvedNetworkType resolvedNetworkType = (ResolvedNetworkType) objArr[0];
                        Map map = (Map) objArr[1];
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = (SignalIcon$MobileIconGroup) objArr[2];
                        SimCardModel simCardModel = (SimCardModel) objArr[3];
                        boolean zBooleanValue = ((Boolean) objArr[4]).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) objArr[5]).booleanValue();
                        MobileServiceState mobileServiceState = (MobileServiceState) objArr[6];
                        if (resolvedNetworkType instanceof ResolvedNetworkType.CarrierMergedNetworkType) {
                            ((ResolvedNetworkType.CarrierMergedNetworkType) resolvedNetworkType).getClass();
                            signalIcon$MobileIconGroup = ResolvedNetworkType.CarrierMergedNetworkType.iconGroupOverride;
                            i = 1;
                        } else {
                            if (map != null) {
                                MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                                MobileDataIconResource mobileDataIconResource = mobileIconInteractorImpl.dataIconResource;
                                SimType simType = simCardModel.simType;
                                mobileDataIconResource.getClass();
                                CarrierInfraMediator carrierInfraMediator = mobileDataIconResource.carrierInfraMediator;
                                int i3 = mobileIconInteractorImpl.slotId;
                                boolean zIsEnabled = carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, i3, new Object[0]);
                                MobileMappingsProxy mobileMappingsProxy = mobileDataIconResource.mobileMappingsProxy;
                                if (zIsEnabled) {
                                    if (!zBooleanValue || mobileDataIconResource.mTelephonyManager.hasCall("volte")) {
                                        lookupKey = resolvedNetworkType.getLookupKey();
                                    } else {
                                        int i4 = mobileServiceState.voiceNetworkType;
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                        lookupKey = Integer.toString(i4);
                                    }
                                    ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                    if (Intrinsics.areEqual(lookupKey, MobileMappings.toDisplayIconKey(5))) {
                                        if (zBooleanValue2) {
                                            lookupKey = Integer.toString(20);
                                        } else {
                                            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                                                zEquals = "VZW".equals((String) carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING_FROM_CARRIER_FEATURE, i3, new Object[0]));
                                            } else if (simType == SimType.VZW) {
                                                zEquals = true;
                                            }
                                            if (!zEquals) {
                                            }
                                        }
                                    }
                                } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_TMOBILE_FAMILY, i3, new Object[0])) {
                                    lookupKey = resolvedNetworkType.getLookupKey();
                                    if (zBooleanValue2) {
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                        if (Intrinsics.areEqual(lookupKey, Integer.toString(3)) || Intrinsics.areEqual(lookupKey, Integer.toString(17)) || Intrinsics.areEqual(lookupKey, Integer.toString(8)) || Intrinsics.areEqual(lookupKey, Integer.toString(9))) {
                                            lookupKey = Integer.toString(5);
                                        } else if (Intrinsics.areEqual(lookupKey, MobileMappings.toDisplayIconKey(5))) {
                                            lookupKey = Integer.toString(20);
                                        }
                                    }
                                } else {
                                    CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
                                    if (Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "TMB_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "TMK_OPEN")) {
                                        lookupKey = resolvedNetworkType.getLookupKey();
                                        if (zBooleanValue2) {
                                            ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                            if (Intrinsics.areEqual(lookupKey, MobileMappings.toDisplayIconKey(5))) {
                                                lookupKey = Integer.toString(20);
                                            }
                                        }
                                    } else if (Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "INU_4G")) {
                                        if ((mobileServiceState.optionalRadioTech == 1) && simType == SimType.AIRTEL) {
                                            zEquals = true;
                                        }
                                        lookupKey = resolvedNetworkType.getLookupKey();
                                        if (zEquals) {
                                            ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                            if (Intrinsics.areEqual(lookupKey, Integer.toString(3)) || Intrinsics.areEqual(lookupKey, Integer.toString(10)) || Intrinsics.areEqual(lookupKey, Integer.toString(15))) {
                                                lookupKey = Integer.toString(13);
                                            }
                                        }
                                    } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_AMX_FAMILY, i3, new Object[0])) {
                                        int i5 = mobileServiceState.optionalRadioTech;
                                        boolean z = i5 == 4;
                                        boolean z2 = i5 == 3;
                                        String lookupKey2 = resolvedNetworkType.getLookupKey();
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                        if (!Intrinsics.areEqual(lookupKey2, Integer.toString(13))) {
                                            lookupKey = lookupKey2;
                                        } else if (Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "CDR") || Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "AMX") || Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "PCT")) {
                                            if (z) {
                                                lookupKey = MobileMappings.toDisplayIconKey(2);
                                            } else if (z2) {
                                                lookupKey = MobileMappings.toDisplayIconKey(1);
                                            }
                                        } else if ((Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "TCE") || Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "CHL")) && (z || z2)) {
                                            lookupKey = MobileMappings.toDisplayIconKey(1);
                                        }
                                    } else if (!carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON, i3, new Object[0])) {
                                        lookupKey = resolvedNetworkType.getLookupKey();
                                    } else if (mobileServiceState.dataRegState == 0) {
                                        String lookupKey3 = resolvedNetworkType.getLookupKey();
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                        if (Intrinsics.areEqual(lookupKey3, Integer.toString(0))) {
                                            int i6 = mobileServiceState.voiceNetworkType;
                                            if (i6 == 16) {
                                                ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                                lookupKey = Integer.toString(1);
                                            } else {
                                                ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                                lookupKey = Integer.toString(i6);
                                            }
                                        } else {
                                            lookupKey = resolvedNetworkType.getLookupKey();
                                        }
                                    }
                                }
                                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = (SignalIcon$MobileIconGroup) map.get(lookupKey);
                                if (signalIcon$MobileIconGroup2 != null) {
                                    signalIcon$MobileIconGroup = signalIcon$MobileIconGroup2;
                                }
                            }
                            i = 1;
                        }
                        this.label = i;
                        if (flowCollector.emit(signalIcon$MobileIconGroup, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlow8.getValue());
        this.defaultNetworkType = readonlyStateFlowStateIn3;
        ReadonlyStateFlow readonlyStateFlowStateIn4 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn3, readonlyStateFlowStateIn, new MobileIconInteractorImpl$networkTypeIconGroup$1(this, null))), tableLogBuffer, "", new NetworkTypeIconModel.DefaultIcon((SignalIcon$MobileIconGroup) stateFlow8.getValue())), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new NetworkTypeIconModel.DefaultIcon((SignalIcon$MobileIconGroup) stateFlow8.getValue()));
        this.networkTypeIconGroup = readonlyStateFlowStateIn4;
        this.showSliceAttribution = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.allowNetworkSliceIndicator, fakeMobileConnectionRepository.hasPrioritizedNetworkCapabilities, new MobileIconInteractorImpl$showSliceAttribution$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        ReadonlyStateFlow readonlyStateFlowStateIn5 = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.isNonTerrestrial, fakeMobileConnectionRepository.semSatelliteEnabled, new AnonymousClass1(null)), tableLogBuffer, "Intr", ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "(", ")isNonTerrestrial"), false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isNonTerrestrial = readonlyStateFlowStateIn5;
        ReadonlyStateFlow readonlyStateFlowStateIn6 = FlowKt.stateIn(FlowKt.combine(fakeMobileConnectionRepository.isGsm, fakeMobileConnectionRepository.primaryLevel, fakeMobileConnectionRepository.cdmaLevel, stateFlow3, new MobileIconInteractorImpl$level$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.level = readonlyStateFlowStateIn6;
        StateFlow stateFlow18 = fakeMobileConnectionRepository.numberOfLevels;
        this.numberOfLevels = stateFlow18;
        final StateFlow stateFlow19 = fakeMobileConnectionRepository.dataConnectionState;
        ReadonlyStateFlow readonlyStateFlowStateIn7 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((DataConnectionState) obj) == DataConnectionState.Connected);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = stateFlow19.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isDataConnected = readonlyStateFlowStateIn7;
        StateFlow stateFlow20 = fakeMobileConnectionRepository.isInService;
        this.isInService = stateFlow20;
        StateFlow stateFlow21 = fakeMobileConnectionRepository.isEmergencyOnly;
        this.isEmergencyOnly = stateFlow21;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((SimCardModel) obj).simType != SimType.OFF);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = stateFlow17.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "Intr", ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "(", ")isSimOn"), false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isSim1On = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) fakeMobileConnectionRepository.sim1On, tableLogBuffer, "Intr", "isSim1On", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isDummySubId = fakeMobileConnectionRepository.subId == Integer.MAX_VALUE;
        this.roamingId = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.combine(stateFlow20, readonlyStateFlowStateIn2, stateFlow15, fakeMobileConnectionRepository.swRoaming, fakeMobileConnectionRepository.semOMCChangedEvent, new MobileIconInteractorImpl$roamingId$1(this, null)), tableLogBuffer, "Intr", "roamingId", 0), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        ReadonlyStateFlow readonlyStateFlowStateIn8 = FlowKt.stateIn(FlowKt.combine(stateFlow, stateFlow9, stateFlow20, new MobileIconInteractorImpl$showExclamationMark$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.TRUE);
        this.showExclamationMark = readonlyStateFlowStateIn8;
        ReadonlyStateFlow readonlyStateFlowStateIn9 = FlowKt.stateIn(FlowKt.combine(stateFlow20, readonlyStateFlowStateIn6, stateFlow18, new MobileIconInteractorImpl$updateSignalTransition$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Unit.INSTANCE);
        this.updateSignalTransition = readonlyStateFlowStateIn9;
        ReadonlyStateFlow readonlyStateFlowStateIn10 = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new MobileIconInteractorImpl$signalLevelUpdate$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.signalLevelUpdate = readonlyStateFlowStateIn10;
        ReadonlyStateFlow readonlyStateFlowStateIn11 = FlowKt.stateIn(FlowKt.combine(readonlyStateFlowStateIn6, stateFlow20, fakeMobileConnectionRepository.inflateSignalStrength, stateFlow21, new MobileIconInteractorImpl$cellularShownLevel$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.cellularShownLevel = readonlyStateFlowStateIn11;
        ReadonlyStateFlow readonlyStateFlow = ((UserSetupRepositoryImpl) userSetupRepository).isUserSetUp;
        this.isUserSetup = readonlyStateFlow;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(wifiRepository.getWifiNetwork(), wifiRepository.isWifiDefault(), new MobileIconInteractorImpl$wifiConnected$1(null));
        this.wifiConnected = flowKt__ZipKt$combine$$inlined$unsafeFlow$12;
        ReadonlyStateFlow readonlyStateFlowStateIn12 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow16, stateFlow15, new C11051(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isVoWifiConnected = readonlyStateFlowStateIn12;
        final Flow[] flowArr2 = {readonlyStateFlowStateIn7, stateFlow5, stateFlow20, stateFlow21, readonlyStateFlow, flowKt__ZipKt$combine$$inlined$unsafeFlow$12, stateFlow6, readonlyStateFlowStateIn12};
        ReadonlyStateFlow readonlyStateFlowStateIn13 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2

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

                /* JADX WARN: Removed duplicated region for block: B:30:0x00bf  */
                /* JADX WARN: Removed duplicated region for block: B:38:0x00e2  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean z = false;
                        boolean zBooleanValue = ((Boolean) objArr[0]).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) objArr[1]).booleanValue();
                        boolean zBooleanValue3 = ((Boolean) objArr[2]).booleanValue();
                        boolean zBooleanValue4 = ((Boolean) objArr[3]).booleanValue();
                        boolean zBooleanValue5 = ((Boolean) objArr[4]).booleanValue();
                        boolean zBooleanValue6 = ((Boolean) objArr[5]).booleanValue();
                        Integer num = (Integer) objArr[6];
                        boolean zBooleanValue7 = ((Boolean) objArr[7]).booleanValue();
                        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                        if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_DISABLED_DATA_ICON, mobileIconInteractorImpl.slotId, new Object[0])) {
                            MobileIconInteractorImpl mobileIconInteractorImpl2 = this.this$0;
                            if (!mobileIconInteractorImpl2.bootstrapProfile) {
                                boolean z2 = zBooleanValue && zBooleanValue2;
                                if (zBooleanValue5 && zBooleanValue3 && (!z2 || zBooleanValue7)) {
                                    if (!mobileIconInteractorImpl2.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DISABLED_ICON, mobileIconInteractorImpl2.slotId, new Object[0])) {
                                        MobileIconInteractorImpl mobileIconInteractorImpl3 = this.this$0;
                                        if (!mobileIconInteractorImpl3.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON, mobileIconInteractorImpl3.slotId, new Object[0])) {
                                            MobileIconInteractorImpl mobileIconInteractorImpl4 = this.this$0;
                                            if (!mobileIconInteractorImpl4.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_HKTW_DISABLED_ICON, mobileIconInteractorImpl4.slotId, new Object[0])) {
                                                MobileIconInteractorImpl mobileIconInteractorImpl5 = this.this$0;
                                                if (mobileIconInteractorImpl5.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, mobileIconInteractorImpl5.slotId, new Object[0]) && !zBooleanValue6 && num != null) {
                                                    if (num.intValue() == this.this$0.connectionRepository.subId) {
                                                    }
                                                }
                                            } else if (!zBooleanValue4 && !zBooleanValue6) {
                                            }
                                        } else if (!zBooleanValue4) {
                                            z = true;
                                        }
                                    }
                                }
                            }
                        }
                        Boolean boolValueOf = Boolean.valueOf(z);
                        this.label = 1;
                        if (flowCollector.emit(boolValueOf, this) == coroutineSingletons) {
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
                final Flow[] flowArr3 = flowArr2;
                Object objCombineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr3.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        }), tableLogBuffer, "Intr", "showDisabledData", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.shouldShowDisabledDataIcon = readonlyStateFlowStateIn13;
        ReadonlyStateFlow readonlyStateFlowStateIn14 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.onTheCall, stateFlow10, new MobileIconInteractorImpl$otherSlotInCallState$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.otherSlotInCallState = readonlyStateFlowStateIn14;
        final Flow[] flowArr3 = {readonlyStateFlowStateIn13, readonlyStateFlowStateIn4, stateFlow20, readonlyStateFlowStateIn2, readonlyStateFlowStateIn14, stateFlow15};
        Flow flowDistinctUntilChanged2 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$3

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
                    DisabledDataIconModel typeIcon;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean zBooleanValue = ((Boolean) objArr[0]).booleanValue();
                        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) objArr[1];
                        boolean zBooleanValue2 = ((Boolean) objArr[2]).booleanValue();
                        boolean zBooleanValue3 = ((Boolean) objArr[3]).booleanValue();
                        boolean zBooleanValue4 = ((Boolean) objArr[4]).booleanValue();
                        MobileServiceState mobileServiceState = (MobileServiceState) objArr[5];
                        if (zBooleanValue) {
                            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                            typeIcon = mobileIconInteractorImpl.disabledDataIconResource.getTypeIcon(mobileIconInteractorImpl.slotId, networkTypeIconModel, zBooleanValue2, zBooleanValue3, zBooleanValue4, mobileServiceState.mSimSubmode == 1);
                        } else {
                            MobileIconInteractorImpl mobileIconInteractorImpl2 = this.this$0;
                            typeIcon = mobileIconInteractorImpl2.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DISABLED_ICON, mobileIconInteractorImpl2.slotId, new Object[0]) ? DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ROAMING_ICON : DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ICON;
                        }
                        this.label = 1;
                        if (flowCollector.emit(typeIcon, this) == coroutineSingletons) {
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
                Object objCombineInternal = CombineKt.combineInternal(flowArr4, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$3.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr4.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        });
        DisabledDataIconModel disabledDataIconModel = DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ICON;
        this.disabledDataIcon = FlowKt.stateIn(DiffableKt.logDiffsForTable(flowDistinctUntilChanged2, tableLogBuffer, "Intr", disabledDataIconModel), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), disabledDataIconModel);
        StateFlow stateFlow22 = fakeMobileConnectionRepository.mobileDataEnabledChanged;
        this.mobileDataEnabledChanged = stateFlow22;
        this.disabledActivityIcon = FlowKt.stateIn(DiffableKt.logDiffsForTable(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn13, stateFlow22, new MobileIconInteractorImpl$disabledActivityIcon$1(this, null)), tableLogBuffer, "Intr", "disabledActivityIcon", 0), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        ReadonlyStateFlow readonlyStateFlowStateIn15 = FlowKt.stateIn(DiffableKt.logDiffsForTable(fakeMobileConnectionRepository.satelliteLevel, tableLogBuffer, "Intr", "satelliteShownLevel", 0), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.satelliteShownLevel = readonlyStateFlowStateIn15;
        final Flow[] flowArr4 = {readonlyStateFlowStateIn11, stateFlow18, readonlyStateFlowStateIn8, stateFlow11, stateFlow20, stateFlow21, readonlyStateFlowStateIn13, stateFlow15, fakeMobileConnectionRepository.imsRegState, readonlyStateFlowStateIn9, readonlyStateFlowStateIn10};
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

                /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
                    java.lang.NullPointerException
                    */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final java.lang.Object invokeSuspend(java.lang.Object r22) {
                    /*
                        Method dump skipped, instructions count: 473
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr5 = flowArr4;
                Object objCombineInternal = CombineKt.combineInternal(flowArr5, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr5.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
        this.carrierIdOfVzwMVNO = new Integer[]{1839, 2032, 2126, 2146, 2556, 10008, 2022, 1847, 1848};
        if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DEVICE, i, new Object[0])) {
            final StateFlow stateFlow23 = fakeMobileConnectionRepository.semSatelliteSignalStrength;
            flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            int iIntValue = ((Number) obj).intValue();
                            SatelliteIconModel.INSTANCE.getClass();
                            Icon.Resource resourceFromSignalStrengthCN = SatelliteIconModel.fromSignalStrengthCN(iIntValue);
                            if (resourceFromSignalStrengthCN == null) {
                                resourceFromSignalStrengthCN = SatelliteIconModel.fromSignalStrengthCN(0);
                                resourceFromSignalStrengthCN.getClass();
                            }
                            SignalIconModel.Satellite satellite = new SignalIconModel.Satellite(iIntValue, resourceFromSignalStrengthCN);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(satellite, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = stateFlow23.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
        } else {
            flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn15, fakeMobileConnectionRepository.isUsingNonTerrestrialNetwork, new MobileIconInteractorImpl$satelliteIcon$2(this, null));
        }
        this.satelliteIcon = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        SignalIconModel.Cellular cellular = new SignalIconModel.Cellular(((Number) readonlyStateFlowStateIn11.$$delegate_0.getValue()).intValue(), ((Number) stateFlow18.getValue()).intValue(), ((Boolean) readonlyStateFlowStateIn8.$$delegate_0.getValue()).booleanValue(), ((Boolean) stateFlow11.getValue()).booleanValue(), 0, 16, null);
        this.signalLevelIcon = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.transformLatest(readonlyStateFlowStateIn5, new MobileIconInteractorImpl$signalLevelIcon$lambda$8$$inlined$flatMapLatest$1(null, this))), tableLogBuffer, "icon", cellular), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), cellular);
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        MobileServiceState mobileServiceState = (MobileServiceState) obj;
                        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                        int i3 = 0;
                        if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_VOICE_NO_SERVICE_ICON, 0, new Object[0]) && !mobileServiceState.vioceCallAvailable && mobileServiceState.dataRegState == 0 && mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_VOICE_CAPABLE, 0, new Object[0])) {
                            SamsungMobileIcons.Companion.getClass();
                            i3 = SamsungMobileIcons.VOICE_NO_SERVICE;
                        }
                        Integer num = new Integer(i3);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = stateFlow15.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
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
        return this.slotId;
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

    public /* synthetic */ MobileIconInteractorImpl(CoroutineScope coroutineScope, StateFlow stateFlow, StateFlow stateFlow2, StateFlow stateFlow3, StateFlow stateFlow4, StateFlow stateFlow5, StateFlow stateFlow6, Flow flow, StateFlow stateFlow7, StateFlow stateFlow8, StateFlow stateFlow9, Flow flow2, MobileConnectionRepository mobileConnectionRepository, Context context, UserSetupRepository userSetupRepository, WifiRepository wifiRepository, MobileDataIconResource mobileDataIconResource, MobileSignalIconResource mobileSignalIconResource, MobileRoamingIconResource mobileRoamingIconResource, MobileDisabledDataIconResource mobileDisabledDataIconResource, CarrierInfraMediator carrierInfraMediator, MobileMappingsProxy mobileMappingsProxy, StateFlow stateFlow10, boolean z, Handler handler, MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, stateFlow, stateFlow2, stateFlow3, stateFlow4, stateFlow5, stateFlow6, flow, stateFlow7, stateFlow8, stateFlow9, flow2, mobileConnectionRepository, context, userSetupRepository, wifiRepository, mobileDataIconResource, mobileSignalIconResource, mobileRoamingIconResource, mobileDisabledDataIconResource, carrierInfraMediator, mobileMappingsProxy, stateFlow10, z, handler, (i & 33554432) != 0 ? new MobileIconCarrierIdOverridesImpl() : mobileIconCarrierIdOverrides);
    }
}
