package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.content.Context;
import android.os.Handler;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileIconCarrierIdOverrides;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.BasicRune;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimCardModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SimType;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.model.DisabledDataIconModel;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.model.DisabledDataIconModelKt;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.model.IconLocation;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.util.MobileSignalIconResource;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.util.SamsungMobileIcons;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl;
import com.android.systemui.statusbar.pipeline.satellite.p027ui.model.SatelliteIconModel;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
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
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileIconInteractorImpl implements MobileIconInteractor {
    public final StateFlow activeDataSubId;
    public final StateFlow activity;
    public final StateFlow alwaysShowDataRatIcon;
    public final Handler bgHandler;
    public final boolean bootstrapProfile;
    public final ReadonlyStateFlow carrierIdIconOverrideExists;
    public final CarrierInfraMediator carrierInfraMediator;
    public final StateFlow carrierNetworkChangeActive;
    public final MobileIconInteractorImpl$special$$inlined$combine$4 cellularIcon;
    public final FakeMobileConnectionRepository connectionRepository;
    public final ReadonlyStateFlow contentDescription;
    public final MobileDataIconResource dataIconResource;
    public final ReadonlyStateFlow defaultNetworkType;
    public final ReadonlyStateFlow disabledActivityIcon;
    public final ReadonlyStateFlow disabledDataIcon;
    public final MobileDisabledDataIconResource disabledDataIconResource;
    public final ReadonlyStateFlow femtoCellIndicatorId;
    public final ReadonlyStateFlow icon;
    public final ReadonlyStateFlow isDataConnected;
    public final StateFlow isDataEnabled;
    public final boolean isDummySubId;
    public final Flow isForceHidden;
    public final StateFlow isInService;
    public final ReadonlyStateFlow isNonTerrestrial;
    public final ReadonlyStateFlow isRoaming;
    public final ReadonlyStateFlow isSim1On;
    public final ReadonlyStateFlow isSimOff;
    public final ReadonlyStateFlow isUserSetup;
    public final ReadonlyStateFlow isVoWifiConnected;
    public final ReadonlyStateFlow level;
    public final StateFlow mobileIsDefault;
    public final StateFlow mobileServiceState;
    public final MobileSignalTransitionManager mobileSignalTransition;
    public final ReadonlyStateFlow networkTypeIconGroup;
    public final ReadonlyStateFlow numberOfLevels;
    public final ReadonlyStateFlow otherSlotInCallState;
    public final MobileRoamingIconResource roamingIconResource;
    public final ReadonlyStateFlow roamingId;
    public final MobileIconInteractorImpl$special$$inlined$map$4 satelliteIcon;
    public final ReadonlyStateFlow shouldShowDisabledDataIcon;
    public final ReadonlyStateFlow showExclamationMark;
    public final ReadonlyStateFlow shownLevel;
    public final MobileSignalIconResource signalIconResource;
    public final ReadonlyStateFlow signalLevelUpdate;
    public final int slotId;
    public final TableLogBuffer tableLogBuffer;
    public final ReadonlyStateFlow updateSignalTransition;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 updatedMobileIconMapping;
    public final ReadonlyStateFlow voiceNoServiceIcon;
    public final MobileIconInteractorImpl$special$$inlined$map$3 wifiConnected;

    /* JADX WARN: Type inference failed for: r2v18, types: [com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4] */
    /* JADX WARN: Type inference failed for: r3v32, types: [com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4] */
    /* JADX WARN: Type inference failed for: r8v33, types: [com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3, kotlinx.coroutines.flow.Flow] */
    public MobileIconInteractorImpl(CoroutineScope coroutineScope, StateFlow stateFlow, StateFlow stateFlow2, StateFlow stateFlow3, StateFlow stateFlow4, StateFlow stateFlow5, Flow flow, StateFlow stateFlow6, StateFlow stateFlow7, StateFlow stateFlow8, Flow flow2, MobileConnectionRepository mobileConnectionRepository, Context context, UserSetupRepository userSetupRepository, WifiRepository wifiRepository, MobileDataIconResource mobileDataIconResource, MobileSignalIconResource mobileSignalIconResource, MobileRoamingIconResource mobileRoamingIconResource, MobileDisabledDataIconResource mobileDisabledDataIconResource, CarrierInfraMediator carrierInfraMediator, MobileMappingsProxy mobileMappingsProxy, SettingsHelper settingsHelper, StateFlow stateFlow9, boolean z, Handler handler, MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides) {
        this.alwaysShowDataRatIcon = stateFlow2;
        this.mobileIsDefault = stateFlow4;
        this.activeDataSubId = stateFlow5;
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
        StateFlow stateFlow10 = fakeMobileConnectionRepository.isInService;
        this.isInService = stateFlow10;
        StateFlow stateFlow11 = fakeMobileConnectionRepository.carrierNetworkChangeActive;
        this.carrierNetworkChangeActive = stateFlow11;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.combine(fakeMobileConnectionRepository.carrierId, fakeMobileConnectionRepository.resolvedNetworkType, fakeMobileConnectionRepository.mobileServiceState, new MobileIconInteractorImpl$carrierIdIconOverrideExists$1(this, null)));
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(distinctUntilChanged, coroutineScope, WhileSubscribed$default, bool);
        this.carrierIdIconOverrideExists = stateIn;
        MobileIconInteractorImpl$networkName$1 mobileIconInteractorImpl$networkName$1 = new MobileIconInteractorImpl$networkName$1(null);
        StateFlow stateFlow12 = fakeMobileConnectionRepository.operatorAlphaShort;
        StateFlow stateFlow13 = fakeMobileConnectionRepository.networkName;
        FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow12, stateFlow13, mobileIconInteractorImpl$networkName$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), stateFlow13.getValue());
        this.mobileSignalTransition = new MobileSignalTransitionManager();
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.combine(fakeMobileConnectionRepository.carrierNetworkChangeActive, fakeMobileConnectionRepository.isGsm, fakeMobileConnectionRepository.isRoaming, fakeMobileConnectionRepository.cdmaRoaming, fakeMobileConnectionRepository.swRoaming, new MobileIconInteractorImpl$isRoaming$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.isRoaming = stateIn2;
        final StateFlow stateFlow14 = fakeMobileConnectionRepository.mobileServiceState;
        this.mobileServiceState = stateFlow14;
        this.roamingId = FlowKt.stateIn(FlowKt.combine(stateFlow10, stateIn2, stateFlow14, fakeMobileConnectionRepository.swRoaming, new MobileIconInteractorImpl$roamingId$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        MobileIconInteractorImpl$updatedMobileIconMapping$1 mobileIconInteractorImpl$updatedMobileIconMapping$1 = new MobileIconInteractorImpl$updatedMobileIconMapping$1(this, null);
        final StateFlow stateFlow15 = fakeMobileConnectionRepository.simCardInfo;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, stateFlow15, mobileIconInteractorImpl$updatedMobileIconMapping$1), new MobileIconInteractorImpl$updatedMobileIconMapping$2(this, null));
        this.updatedMobileIconMapping = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        final Flow[] flowArr = {fakeMobileConnectionRepository.resolvedNetworkType, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, stateFlow7, fakeMobileConnectionRepository.simCardInfo, fakeMobileConnectionRepository.onTheCall, stateIn2, stateFlow14};
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1$3", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$1$3 */
            public final class C33073 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MobileIconInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C33073(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    super(3, continuation);
                    this.this$0 = mobileIconInteractorImpl;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C33073 c33073 = new C33073((Continuation) obj3, this.this$0);
                    c33073.L$0 = (FlowCollector) obj;
                    c33073.L$1 = (Object[]) obj2;
                    return c33073.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:29:0x00c2, code lost:
                
                    if ((com.android.systemui.BasicRune.STATUS_NETWORK_MULTI_SIM ? kotlin.jvm.internal.Intrinsics.areEqual("VZW", (java.lang.String) r5.get(com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator.Values.ICON_BRANDING_FROM_CARRIER_FEATURE, r8, new java.lang.Object[0])) : r10 == com.android.systemui.statusbar.pipeline.mobile.data.model.SimType.VZW) == false) goto L30;
                 */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup;
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2;
                    SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3;
                    String lookupKey;
                    String num;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    int i2 = 1;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        ResolvedNetworkType resolvedNetworkType = (ResolvedNetworkType) objArr[0];
                        Map map = (Map) objArr[1];
                        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = (SignalIcon$MobileIconGroup) objArr[2];
                        SimCardModel simCardModel = (SimCardModel) objArr[3];
                        boolean booleanValue = ((Boolean) objArr[4]).booleanValue();
                        boolean booleanValue2 = ((Boolean) objArr[5]).booleanValue();
                        MobileServiceState mobileServiceState = (MobileServiceState) objArr[6];
                        if (resolvedNetworkType instanceof ResolvedNetworkType.CarrierMergedNetworkType) {
                            signalIcon$MobileIconGroup3 = ResolvedNetworkType.CarrierMergedNetworkType.iconGroupOverride;
                        } else {
                            if (map != null) {
                                MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                                MobileDataIconResource mobileDataIconResource = mobileIconInteractorImpl.dataIconResource;
                                mobileDataIconResource.getClass();
                                CarrierInfraMediator carrierInfraMediator = mobileDataIconResource.carrierInfraMediator;
                                int i3 = mobileIconInteractorImpl.slotId;
                                boolean isEnabled = carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, i3, new Object[0]);
                                SimType simType = simCardModel.simType;
                                int i4 = mobileServiceState.voiceNetworkType;
                                MobileMappingsProxy mobileMappingsProxy = mobileDataIconResource.mobileMappingsProxy;
                                if (isEnabled) {
                                    if (!booleanValue || mobileDataIconResource.mTelephonyManager.hasCall("volte")) {
                                        lookupKey = resolvedNetworkType.getLookupKey();
                                    } else {
                                        ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                        lookupKey = Integer.toString(i4);
                                    }
                                    ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                    if (Intrinsics.areEqual(lookupKey, MobileMappings.toDisplayIconKey(5))) {
                                        if (!booleanValue2) {
                                        }
                                        lookupKey = Integer.toString(20);
                                    }
                                    signalIcon$MobileIconGroup = signalIcon$MobileIconGroup4;
                                } else {
                                    signalIcon$MobileIconGroup = signalIcon$MobileIconGroup4;
                                    if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_TMOBILE_FAMILY, i3, new Object[0])) {
                                        lookupKey = resolvedNetworkType.getLookupKey();
                                        if (booleanValue2) {
                                            ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                            if (Intrinsics.areEqual(lookupKey, Integer.toString(3)) ? true : Intrinsics.areEqual(lookupKey, Integer.toString(17)) ? true : Intrinsics.areEqual(lookupKey, Integer.toString(8)) ? true : Intrinsics.areEqual(lookupKey, Integer.toString(9))) {
                                                lookupKey = Integer.toString(5);
                                            } else if (Intrinsics.areEqual(lookupKey, MobileMappings.toDisplayIconKey(5))) {
                                                lookupKey = mobileDataIconResource.useGlobal5gIcon(i3) ? MobileMappings.toDisplayIconKey(5) : Integer.toString(20);
                                            }
                                        }
                                    } else {
                                        CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
                                        if (Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "TMB_OPEN") || Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "TMK_OPEN")) {
                                            lookupKey = resolvedNetworkType.getLookupKey();
                                            if (booleanValue2) {
                                                ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                                if (Intrinsics.areEqual(lookupKey, MobileMappings.toDisplayIconKey(5))) {
                                                    lookupKey = Integer.toString(20);
                                                }
                                            }
                                        } else {
                                            boolean areEqual = Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "INU_4G");
                                            int i5 = mobileServiceState.optionalRadioTech;
                                            if (areEqual) {
                                                boolean z = (i5 == 1) && simType == SimType.AIRTEL;
                                                lookupKey = resolvedNetworkType.getLookupKey();
                                                if (z) {
                                                    ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                                    if (Intrinsics.areEqual(lookupKey, Integer.toString(3)) ? true : Intrinsics.areEqual(lookupKey, Integer.toString(10)) ? true : Intrinsics.areEqual(lookupKey, Integer.toString(15))) {
                                                        lookupKey = Integer.toString(13);
                                                    }
                                                }
                                            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_AMX_FAMILY, i3, new Object[0])) {
                                                boolean z2 = i5 == 4;
                                                boolean z3 = i5 == 3;
                                                String lookupKey2 = resolvedNetworkType.getLookupKey();
                                                ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                                if (Intrinsics.areEqual(lookupKey2, Integer.toString(13))) {
                                                    if (Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "CDR") || Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "AMX") || Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "PCT")) {
                                                        if (z2) {
                                                            num = MobileMappings.toDisplayIconKey(2);
                                                        } else if (z3) {
                                                            num = MobileMappings.toDisplayIconKey(1);
                                                        }
                                                        lookupKey = num;
                                                    } else if ((Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "TCE") || Intrinsics.areEqual(carrierInfraMediator.get(values, i3, new Object[0]), "CHL")) && (z2 || z3)) {
                                                        num = MobileMappings.toDisplayIconKey(1);
                                                        lookupKey = num;
                                                    }
                                                }
                                                lookupKey = lookupKey2;
                                            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON, i3, new Object[0])) {
                                                if (mobileServiceState.dataRegState == 0) {
                                                    String lookupKey3 = resolvedNetworkType.getLookupKey();
                                                    ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                                    if (!Intrinsics.areEqual(lookupKey3, Integer.toString(0))) {
                                                        lookupKey = resolvedNetworkType.getLookupKey();
                                                    }
                                                }
                                                if (i4 == 16) {
                                                    ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                                    num = Integer.toString(1);
                                                    lookupKey = num;
                                                } else {
                                                    ((MobileMappingsProxyImpl) mobileMappingsProxy).getClass();
                                                    lookupKey = Integer.toString(i4);
                                                }
                                            } else {
                                                lookupKey = resolvedNetworkType.getLookupKey();
                                            }
                                        }
                                    }
                                }
                                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = (SignalIcon$MobileIconGroup) map.get(lookupKey);
                                if (signalIcon$MobileIconGroup5 != null) {
                                    signalIcon$MobileIconGroup2 = signalIcon$MobileIconGroup5;
                                    signalIcon$MobileIconGroup3 = signalIcon$MobileIconGroup2;
                                    i2 = 1;
                                }
                            } else {
                                signalIcon$MobileIconGroup = signalIcon$MobileIconGroup4;
                            }
                            signalIcon$MobileIconGroup2 = signalIcon$MobileIconGroup;
                            signalIcon$MobileIconGroup3 = signalIcon$MobileIconGroup2;
                            i2 = 1;
                        }
                        this.label = i2;
                        if (flowCollector.emit(signalIcon$MobileIconGroup3, this) == coroutineSingletons) {
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
                }, new C33073(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), stateFlow7.getValue());
        this.defaultNetworkType = stateIn3;
        ReadonlyStateFlow stateIn4 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn3, stateIn, new MobileIconInteractorImpl$networkTypeIconGroup$1(this, null))), tableLogBuffer, "Intr", new NetworkTypeIconModel.DefaultIcon((SignalIcon$MobileIconGroup) stateFlow7.getValue())), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), new NetworkTypeIconModel.DefaultIcon((SignalIcon$MobileIconGroup) stateFlow7.getValue()));
        this.networkTypeIconGroup = stateIn4;
        ReadonlyStateFlow stateIn5 = FlowKt.stateIn(DiffableKt.logDiffsForTable(carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SUPPORT_CARRIER_ENABLED_SATELLITE, i, new Object[0]) ? fakeMobileConnectionRepository.isNonTerrestrial : FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(bool)), tableLogBuffer, "Intr", "isNonTerrestrial", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.isNonTerrestrial = stateIn5;
        StateFlow stateFlow16 = fakeMobileConnectionRepository.isEmergencyOnly;
        ReadonlyStateFlow stateIn6 = FlowKt.stateIn(FlowKt.combine(fakeMobileConnectionRepository.isGsm, fakeMobileConnectionRepository.primaryLevel, fakeMobileConnectionRepository.cdmaLevel, stateFlow3, new MobileIconInteractorImpl$level$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        this.level = stateIn6;
        StartedWhileSubscribed WhileSubscribed$default2 = SharingStarted.Companion.WhileSubscribed$default(companion);
        StateFlow stateFlow17 = fakeMobileConnectionRepository.numberOfLevels;
        ReadonlyStateFlow stateIn7 = FlowKt.stateIn(stateFlow17, coroutineScope, WhileSubscribed$default2, stateFlow17.getValue());
        this.numberOfLevels = stateIn7;
        final StateFlow stateFlow18 = fakeMobileConnectionRepository.dataConnectionState;
        ReadonlyStateFlow stateIn8 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1$2 */
            public final class C33142 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$1$2", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33142.this.emit(null, this);
                    }
                }

                public C33142(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
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
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(((DataConnectionState) obj) == DataConnectionState.Connected);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C33142(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.isDataConnected = stateIn8;
        this.isSimOff = FlowKt.stateIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2$2 */
            public final class C33152 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$2$2", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33152.this.emit(null, this);
                    }
                }

                public C33152(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
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
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(((SimCardModel) obj).simType == SimType.OFF);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C33152(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, tableLogBuffer, "Intr", LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("(", i, ")isSimOff"), false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.isSim1On = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) fakeMobileConnectionRepository.sim1On, tableLogBuffer, "Intr", "isSim1On", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.isDummySubId = fakeMobileConnectionRepository.subId == Integer.MAX_VALUE;
        ReadonlyStateFlow stateIn9 = FlowKt.stateIn(FlowKt.combine(stateFlow, stateFlow8, stateFlow10, new MobileIconInteractorImpl$showExclamationMark$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.TRUE);
        this.showExclamationMark = stateIn9;
        ReadonlyStateFlow stateIn10 = FlowKt.stateIn(FlowKt.combine(stateFlow10, stateIn6, stateIn7, new MobileIconInteractorImpl$updateSignalTransition$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Unit.INSTANCE);
        this.updateSignalTransition = stateIn10;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        MobileIconInteractorImpl$signalLevelUpdate$1 mobileIconInteractorImpl$signalLevelUpdate$1 = new MobileIconInteractorImpl$signalLevelUpdate$1(this, null);
        conflatedCallbackFlow.getClass();
        ReadonlyStateFlow stateIn11 = FlowKt.stateIn(ConflatedCallbackFlow.conflatedCallbackFlow(mobileIconInteractorImpl$signalLevelUpdate$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        this.signalLevelUpdate = stateIn11;
        final ReadonlyStateFlow stateIn12 = FlowKt.stateIn(FlowKt.combine(stateIn6, stateFlow10, stateFlow16, stateIn5, new MobileIconInteractorImpl$shownLevel$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        this.shownLevel = stateIn12;
        this.contentDescription = FlowKt.stateIn(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn12, stateIn7, new MobileIconInteractorImpl$contentDescription$1(this, null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), new ContentDescription.Resource(AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH[0]));
        ReadonlyStateFlow readonlyStateFlow = ((UserSetupRepositoryImpl) userSetupRepository).isUserSetupFlow;
        this.isUserSetup = readonlyStateFlow;
        final StateFlow wifiNetwork = wifiRepository.getWifiNetwork();
        ?? r8 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3$2 */
            public final class C33162 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$3$2", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33162.this.emit(null, this);
                    }
                }

                public C33162(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
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
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                Boolean valueOf = Boolean.valueOf(((WifiNetworkModel) obj) instanceof WifiNetworkModel.Active);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C33162(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.wifiConnected = r8;
        ReadonlyStateFlow stateIn13 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.imsRegState, stateFlow14, new MobileIconInteractorImpl$isVoWifiConnected$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.isVoWifiConnected = stateIn13;
        final Flow[] flowArr2 = {stateIn8, stateFlow4, stateFlow10, stateFlow16, readonlyStateFlow, r8, stateFlow5, stateIn13};
        ReadonlyStateFlow stateIn14 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2$3", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2$3 */
            public final class C33093 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MobileIconInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C33093(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    super(3, continuation);
                    this.this$0 = mobileIconInteractorImpl;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C33093 c33093 = new C33093((Continuation) obj3, this.this$0);
                    c33093.L$0 = (FlowCollector) obj;
                    c33093.L$1 = (Object[]) obj2;
                    return c33093.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:28:0x00bd, code lost:
                
                    if (r11 == false) goto L39;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:29:0x00e3, code lost:
                
                    r5 = true;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:35:0x00de, code lost:
                
                    if (r12.intValue() == r16.this$0.connectionRepository.subId) goto L39;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:36:0x00e1, code lost:
                
                    if (r9 == false) goto L39;
                 */
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
                        boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
                        boolean booleanValue2 = ((Boolean) objArr[1]).booleanValue();
                        boolean booleanValue3 = ((Boolean) objArr[2]).booleanValue();
                        boolean booleanValue4 = ((Boolean) objArr[3]).booleanValue();
                        boolean booleanValue5 = ((Boolean) objArr[4]).booleanValue();
                        boolean booleanValue6 = ((Boolean) objArr[5]).booleanValue();
                        Integer num = (Integer) objArr[6];
                        boolean booleanValue7 = ((Boolean) objArr[7]).booleanValue();
                        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                        if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_DISABLED_DATA_ICON, mobileIconInteractorImpl.slotId, new Object[0])) {
                            MobileIconInteractorImpl mobileIconInteractorImpl2 = this.this$0;
                            if (!mobileIconInteractorImpl2.bootstrapProfile) {
                                boolean z2 = booleanValue && booleanValue2;
                                if (booleanValue5 && booleanValue3 && (!z2 || booleanValue7)) {
                                    if (!mobileIconInteractorImpl2.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DISABLED_ICON, mobileIconInteractorImpl2.slotId, new Object[0])) {
                                        MobileIconInteractorImpl mobileIconInteractorImpl3 = this.this$0;
                                        if (!mobileIconInteractorImpl3.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON, mobileIconInteractorImpl3.slotId, new Object[0])) {
                                            MobileIconInteractorImpl mobileIconInteractorImpl4 = this.this$0;
                                            if (!mobileIconInteractorImpl4.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_HKTW_DISABLED_ICON, mobileIconInteractorImpl4.slotId, new Object[0])) {
                                                MobileIconInteractorImpl mobileIconInteractorImpl5 = this.this$0;
                                                if (mobileIconInteractorImpl5.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, mobileIconInteractorImpl5.slotId, new Object[0]) && !booleanValue6 && num != null) {
                                                }
                                            } else if (!booleanValue4) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Boolean valueOf = Boolean.valueOf(z);
                        this.label = 1;
                        if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
                Object combineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$2.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr3.length];
                    }
                }, new C33093(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }), tableLogBuffer, "Intr", "showDisabledData", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.shouldShowDisabledDataIcon = stateIn14;
        ReadonlyStateFlow stateIn15 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(fakeMobileConnectionRepository.onTheCall, stateFlow9, new MobileIconInteractorImpl$otherSlotInCallState$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.otherSlotInCallState = stateIn15;
        final Flow[] flowArr3 = {stateIn14, stateIn4, stateFlow10, stateIn2, stateIn15, stateFlow14};
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$3$3", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$3$3 */
            public final class C33113 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MobileIconInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C33113(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    super(3, continuation);
                    this.this$0 = mobileIconInteractorImpl;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C33113 c33113 = new C33113((Continuation) obj3, this.this$0);
                    c33113.L$0 = (FlowCollector) obj;
                    c33113.L$1 = (Object[]) obj2;
                    return c33113.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:26:0x0096, code lost:
                
                    if ((r1.get("data_roaming").getIntValue() == 1) == false) goto L30;
                 */
                /* JADX WARN: Removed duplicated region for block: B:30:0x009e  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x00cf  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    DisabledDataIconModel disabledDataIconModel;
                    boolean z;
                    String name;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        int i2 = 0;
                        boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
                        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) objArr[1];
                        boolean booleanValue2 = ((Boolean) objArr[2]).booleanValue();
                        boolean booleanValue3 = ((Boolean) objArr[3]).booleanValue();
                        boolean booleanValue4 = ((Boolean) objArr[4]).booleanValue();
                        MobileServiceState mobileServiceState = (MobileServiceState) objArr[5];
                        if (booleanValue) {
                            MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                            MobileDisabledDataIconResource mobileDisabledDataIconResource = mobileIconInteractorImpl.disabledDataIconResource;
                            boolean z2 = mobileServiceState.mSimSubmode == 1;
                            mobileDisabledDataIconResource.getClass();
                            CarrierInfraMediator carrierInfraMediator = mobileDisabledDataIconResource.carrierInfraMediator;
                            int i3 = mobileIconInteractorImpl.slotId;
                            if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, i3, new Object[0])) {
                                SettingsHelper settingsHelper = mobileDisabledDataIconResource.settingsHelper;
                                boolean z3 = settingsHelper.mItemLists.get("mobile_data").getIntValue() == 1;
                                SettingsHelper.ItemMap itemMap = settingsHelper.mItemLists;
                                if (z3) {
                                    if (booleanValue3) {
                                    }
                                    z = false;
                                    if (z) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m59m("Use slash icon since data=", itemMap.get("mobile_data").getIntValue() == 1, " roaming=", itemMap.get("data_roaming").getIntValue() == 1, "DisabledDataIconResource");
                                    }
                                    name = networkTypeIconModel.getName();
                                    if (!Intrinsics.areEqual(name, TelephonyIcons.UNKNOWN.name)) {
                                        if (Intrinsics.areEqual(name, TelephonyIcons.E_VZW.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_E_VZW : SamsungMobileIcons.DISABLED_E_VZW;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.THREE_G_VZW.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_3G_VZW : SamsungMobileIcons.DISABLED_3G_VZW;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.H_VZW.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_H_VZW : SamsungMobileIcons.DISABLED_H_VZW;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.H_PLUS_VZW.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_H_PLUS_VZW : SamsungMobileIcons.DISABLED_H_PLUS_VZW;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.ONE_X_VZW.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_1X_VZW : SamsungMobileIcons.DISABLED_1X_VZW;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.FOUR_G_VZW.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_4G_VZW : SamsungMobileIcons.DISABLED_4G_VZW;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.NR_5G_VZW.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_5G_VZW : SamsungMobileIcons.DISABLED_5G_VZW;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.NR_5G_CONNECTED.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_5G_CONNECTED : SamsungMobileIcons.DISABLED_5G_CONNECTED;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.NR_5G_VZW_UWB.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_5G_VZW_UWB : SamsungMobileIcons.DISABLED_5G_VZW_UWB;
                                        } else if (Intrinsics.areEqual(name, TelephonyIcons.G_VZW.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_G_VZW : SamsungMobileIcons.DISABLED_G_VZW;
                                        } else {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = z ? SamsungMobileIcons.DISABLED_SLASH_G_VZW : SamsungMobileIcons.DISABLED_G_VZW;
                                        }
                                    }
                                    disabledDataIconModel = new DisabledDataIconModel(IconLocation.DATA_ICON, i2);
                                }
                                z = true;
                                if (z) {
                                }
                                name = networkTypeIconModel.getName();
                                if (!Intrinsics.areEqual(name, TelephonyIcons.UNKNOWN.name)) {
                                }
                                disabledDataIconModel = new DisabledDataIconModel(IconLocation.DATA_ICON, i2);
                            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_LATIN_DISABLED_ICON, i3, new Object[0])) {
                                String name2 = networkTypeIconModel.getName();
                                if (Intrinsics.areEqual(name2, TelephonyIcons.f221G.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_G;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.f220E.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_E;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.THREE_G.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_3G;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.f222H.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_H;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.H_PLUS.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_H_PLUS;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.FOUR_G.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_4G;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.FOUR_G_PLUS.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_4G_PLUS;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.FOUR_HALF_G.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_4_HALF_G;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.NR_5G_AVAILABLE.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_5G_AVAILABLE;
                                } else if (Intrinsics.areEqual(name2, TelephonyIcons.NR_5G_CONNECTED.name)) {
                                    SamsungMobileIcons.Companion.getClass();
                                    i2 = SamsungMobileIcons.DISABLED_5G_CONNECTED;
                                }
                                disabledDataIconModel = new DisabledDataIconModel(IconLocation.DATA_ICON, i2);
                            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_HKTW_DISABLED_ICON, i3, new Object[0])) {
                                disabledDataIconModel = new DisabledDataIconModel(IconLocation.DATA_ICON, networkTypeIconModel.getIconId());
                            } else if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA, i3, new Object[0]) && booleanValue2) {
                                if (carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_CHINA_DISABLED_ICON, i3, new Object[0])) {
                                    if (booleanValue3) {
                                        boolean z4 = BasicRune.STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL;
                                        if (!z4 || (z4 && !booleanValue4)) {
                                            disabledDataIconModel = new DisabledDataIconModel(IconLocation.DATA_ICON, networkTypeIconModel.getIconId());
                                        }
                                    } else {
                                        String name3 = networkTypeIconModel.getName();
                                        if (Intrinsics.areEqual(name3, TelephonyIcons.ONE_X_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_1X;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.G_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_G;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.E_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_E;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.TWO_G_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_2G;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.THREE_G_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_3G;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.THREE_G_PLUS_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_3G_PLUS;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.H_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_H;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.H_PLUS_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_H_PLUS;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.FOUR_G_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_4G;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.FOUR_G_PLUS_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_4G_PLUS;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.NR_5G_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_5G;
                                        } else if (Intrinsics.areEqual(name3, TelephonyIcons.NR_5GA_CHN.name)) {
                                            SamsungMobileIcons.Companion.getClass();
                                            i2 = SamsungMobileIcons.DISABLED_AT_SIGNAL_5GA;
                                        }
                                    }
                                }
                                if (BasicRune.STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL && booleanValue4) {
                                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("Display limited icon? ", z2, "DisabledDataIconResource");
                                    if (!z2) {
                                        SamsungMobileIcons.Companion.getClass();
                                        i2 = SamsungMobileIcons.SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL;
                                    }
                                }
                                disabledDataIconModel = new DisabledDataIconModel(IconLocation.ROAMING_ICON, i2);
                            } else {
                                disabledDataIconModel = DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ICON;
                            }
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
                }, new C33113(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        });
        DisabledDataIconModel disabledDataIconModel = DisabledDataIconModelKt.EMPTY_DISABLED_DATA_ICON;
        this.disabledDataIcon = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged2, tableLogBuffer, "Intr", disabledDataIconModel), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), disabledDataIconModel);
        this.disabledActivityIcon = FlowKt.stateIn(DiffableKt.logDiffsForTable(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn14, fakeMobileConnectionRepository.mobileDataEnabledChanged, new MobileIconInteractorImpl$disabledActivityIcon$1(this, null)), tableLogBuffer, "Intr", "disabledActivityIcon", 0), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        final Flow[] flowArr4 = {stateIn12, stateIn7, stateIn9, stateFlow10, stateFlow16, stateIn14, stateFlow14, fakeMobileConnectionRepository.imsRegState, stateIn10, stateIn11};
        this.cellularIcon = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4$3", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$combine$4$3 */
            public final class C33133 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MobileIconInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C33133(Continuation continuation, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    super(3, continuation);
                    this.this$0 = mobileIconInteractorImpl;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C33133 c33133 = new C33133((Continuation) obj3, this.this$0);
                    c33133.L$0 = (FlowCollector) obj;
                    c33133.L$1 = (Object[]) obj2;
                    return c33133.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:60:0x0188, code lost:
                
                    if (r13.voWifiRegState != false) goto L60;
                 */
                /* JADX WARN: Removed duplicated region for block: B:18:0x01b0 A[RETURN] */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    SignalIconModel.Cellular cellular;
                    boolean z;
                    int i;
                    int i2;
                    int noServiceIcon;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i3 = this.label;
                    if (i3 == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        int intValue = ((Integer) objArr[0]).intValue();
                        int intValue2 = ((Integer) objArr[1]).intValue();
                        boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
                        boolean booleanValue2 = ((Boolean) objArr[3]).booleanValue();
                        boolean booleanValue3 = ((Boolean) objArr[4]).booleanValue();
                        boolean booleanValue4 = ((Boolean) objArr[5]).booleanValue();
                        MobileServiceState mobileServiceState = (MobileServiceState) objArr[6];
                        ImsRegState imsRegState = (ImsRegState) objArr[7];
                        int intValue3 = ((Integer) objArr[9]).intValue();
                        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                        if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, mobileIconInteractorImpl.slotId, new Object[0])) {
                            MobileIconInteractorImpl mobileIconInteractorImpl2 = this.this$0;
                            if (mobileIconInteractorImpl2.bootstrapProfile) {
                                noServiceIcon = mobileIconInteractorImpl2.signalIconResource.getNoServiceIcon(mobileIconInteractorImpl2.slotId);
                                i2 = noServiceIcon;
                                z = booleanValue;
                                cellular = new SignalIconModel.Cellular(intValue, intValue2, z, false, i2);
                                this.label = 1;
                                if (flowCollector.emit(cellular, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                        }
                        MobileIconInteractorImpl mobileIconInteractorImpl3 = this.this$0;
                        if (mobileIconInteractorImpl3.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.NO_SERVICE_WHEN_NO_SIM, mobileIconInteractorImpl3.slotId, new Object[0])) {
                            MobileIconInteractorImpl mobileIconInteractorImpl4 = this.this$0;
                            if (mobileIconInteractorImpl4.isDummySubId) {
                                noServiceIcon = mobileIconInteractorImpl4.signalIconResource.getNoServiceIcon(mobileIconInteractorImpl4.slotId);
                                i2 = noServiceIcon;
                                z = booleanValue;
                                cellular = new SignalIconModel.Cellular(intValue, intValue2, z, false, i2);
                                this.label = 1;
                                if (flowCollector.emit(cellular, this) == coroutineSingletons) {
                                }
                            }
                        }
                        MobileIconInteractorImpl mobileIconInteractorImpl5 = this.this$0;
                        CarrierInfraMediator carrierInfraMediator = mobileIconInteractorImpl5.carrierInfraMediator;
                        CarrierInfraMediator.Conditions conditions = CarrierInfraMediator.Conditions.ZERO_SIGNAL_LEVEL_ON_VOWIFI;
                        if (carrierInfraMediator.isEnabled(conditions, mobileIconInteractorImpl5.slotId, new Object[0])) {
                            MobileIconInteractorImpl mobileIconInteractorImpl6 = this.this$0;
                            z = booleanValue;
                            if (Intrinsics.areEqual(mobileIconInteractorImpl6.carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, mobileIconInteractorImpl6.slotId, new Object[0]), "TMB") && mobileServiceState.telephonyDisplayInfo.getNetworkType() == 18) {
                                MobileIconInteractorImpl mobileIconInteractorImpl7 = this.this$0;
                                int[] mobileSignalIconGroup = mobileIconInteractorImpl7.signalIconResource.getMobileSignalIconGroup(mobileIconInteractorImpl7.slotId, intValue2, booleanValue4);
                                i = mobileSignalIconGroup.length <= 0 ? mobileSignalIconGroup[mobileSignalIconGroup.length - 1] : mobileSignalIconGroup[0];
                                i2 = i;
                                cellular = new SignalIconModel.Cellular(intValue, intValue2, z, false, i2);
                                this.label = 1;
                                if (flowCollector.emit(cellular, this) == coroutineSingletons) {
                                }
                            }
                        } else {
                            z = booleanValue;
                        }
                        MobileIconInteractorImpl mobileIconInteractorImpl8 = this.this$0;
                        if (mobileIconInteractorImpl8.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.CHANGE_SIGNAL_ONE_LEVEL_PER_SEC, mobileIconInteractorImpl8.slotId, new Object[0])) {
                            MobileIconInteractorImpl mobileIconInteractorImpl9 = this.this$0;
                            MobileSignalIconResource mobileSignalIconResource = mobileIconInteractorImpl9.signalIconResource;
                            boolean z2 = mobileIconInteractorImpl9.mobileSignalTransition.isTransition;
                            mobileSignalIconResource.getClass();
                            int i4 = mobileIconInteractorImpl9.slotId;
                            if (booleanValue2) {
                                if (!z2) {
                                    intValue3 = intValue;
                                } else if (intValue3 <= 0) {
                                    intValue3 = 0;
                                }
                                i = mobileSignalIconResource.getMobileSignalIconGroup(i4, intValue2, false)[intValue3];
                            } else if (z2) {
                                i = mobileSignalIconResource.getMobileSignalIconGroup(i4, intValue2, false)[intValue3 > 0 ? intValue3 : 0];
                            } else {
                                i = mobileSignalIconResource.getNoServiceIcon(i4);
                            }
                        } else {
                            if (!booleanValue2) {
                                MobileIconInteractorImpl mobileIconInteractorImpl10 = this.this$0;
                                if (!mobileIconInteractorImpl10.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.SIGNAL_BAR_WHEN_EMERGENCY, mobileIconInteractorImpl10.slotId, new Object[0]) || !booleanValue3) {
                                    MobileIconInteractorImpl mobileIconInteractorImpl11 = this.this$0;
                                    i = mobileIconInteractorImpl11.signalIconResource.getNoServiceIcon(mobileIconInteractorImpl11.slotId);
                                }
                            }
                            MobileIconInteractorImpl mobileIconInteractorImpl12 = this.this$0;
                            MobileSignalIconResource mobileSignalIconResource2 = mobileIconInteractorImpl12.signalIconResource;
                            int i5 = mobileIconInteractorImpl12.slotId;
                            if (mobileIconInteractorImpl12.carrierInfraMediator.isEnabled(conditions, i5, new Object[0])) {
                                MobileIconInteractorImpl mobileIconInteractorImpl13 = this.this$0;
                                if (Intrinsics.areEqual(mobileIconInteractorImpl13.carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, mobileIconInteractorImpl13.slotId, new Object[0]), "XFA")) {
                                }
                            }
                            r5 = intValue;
                            int[] mobileSignalIconGroup2 = mobileSignalIconResource2.getMobileSignalIconGroup(i5, intValue2, booleanValue4);
                            i = r5 >= mobileSignalIconGroup2.length ? mobileSignalIconGroup2[mobileSignalIconGroup2.length - 1] : mobileSignalIconGroup2[r5];
                        }
                        i2 = i;
                        cellular = new SignalIconModel.Cellular(intValue, intValue2, z, false, i2);
                        this.label = 1;
                        if (flowCollector.emit(cellular, this) == coroutineSingletons) {
                        }
                    } else {
                        if (i3 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
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
                }, new C33133(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        this.satelliteIcon = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4$2 */
            public final class C33172 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$4$2", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33172.this.emit(null, this);
                    }
                }

                public C33172(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
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
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                int intValue = ((Number) obj).intValue();
                                SatelliteIconModel.INSTANCE.getClass();
                                Icon.Resource fromSignalStrength = SatelliteIconModel.fromSignalStrength(intValue);
                                if (fromSignalStrength == null) {
                                    fromSignalStrength = SatelliteIconModel.fromSignalStrength(0);
                                    Intrinsics.checkNotNull(fromSignalStrength);
                                }
                                SignalIconModel.Satellite satellite = new SignalIconModel.Satellite(intValue, fromSignalStrength);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(satellite, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C33172(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SignalIconModel.Cellular cellular = new SignalIconModel.Cellular(((Number) stateIn12.getValue()).intValue(), ((Number) stateIn7.getValue()).intValue(), ((Boolean) stateIn9.getValue()).booleanValue(), ((Boolean) stateFlow11.getValue()).booleanValue(), 0, 16, null);
        this.icon = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.transformLatest(stateIn5, new MobileIconInteractorImpl$icon$lambda$9$$inlined$flatMapLatest$1(null, this))), tableLogBuffer, "icon", cellular), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), cellular);
        this.voiceNoServiceIcon = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$5

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$5$2 */
            public final class C33182 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MobileIconInteractorImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$5$2", m277f = "MobileIconInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33182.this.emit(null, this);
                    }
                }

                public C33182(FlowCollector flowCollector, MobileIconInteractorImpl mobileIconInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mobileIconInteractorImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
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
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                MobileServiceState mobileServiceState = (MobileServiceState) obj;
                                MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
                                int i3 = 0;
                                if (mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_VOICE_NO_SERVICE_ICON, 0, new Object[0])) {
                                    if ((!mobileServiceState.vioceCallAvailable && mobileServiceState.dataRegState == 0) && mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_VOICE_CAPABLE, 0, new Object[0])) {
                                        SamsungMobileIcons.Companion.getClass();
                                        i3 = SamsungMobileIcons.VOICE_NO_SERVICE;
                                    }
                                }
                                Integer num = new Integer(i3);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C33182(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        this.femtoCellIndicatorId = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow10, stateFlow14, new MobileIconInteractorImpl$femtoCellIndicatorId$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
    }

    public /* synthetic */ MobileIconInteractorImpl(CoroutineScope coroutineScope, StateFlow stateFlow, StateFlow stateFlow2, StateFlow stateFlow3, StateFlow stateFlow4, StateFlow stateFlow5, Flow flow, StateFlow stateFlow6, StateFlow stateFlow7, StateFlow stateFlow8, Flow flow2, MobileConnectionRepository mobileConnectionRepository, Context context, UserSetupRepository userSetupRepository, WifiRepository wifiRepository, MobileDataIconResource mobileDataIconResource, MobileSignalIconResource mobileSignalIconResource, MobileRoamingIconResource mobileRoamingIconResource, MobileDisabledDataIconResource mobileDisabledDataIconResource, CarrierInfraMediator carrierInfraMediator, MobileMappingsProxy mobileMappingsProxy, SettingsHelper settingsHelper, StateFlow stateFlow9, boolean z, Handler handler, MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, stateFlow, stateFlow2, stateFlow3, stateFlow4, stateFlow5, flow, stateFlow6, stateFlow7, stateFlow8, flow2, mobileConnectionRepository, context, userSetupRepository, wifiRepository, mobileDataIconResource, mobileSignalIconResource, mobileRoamingIconResource, mobileDisabledDataIconResource, carrierInfraMediator, mobileMappingsProxy, settingsHelper, stateFlow9, z, handler, (i & QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING) != 0 ? new MobileIconCarrierIdOverridesImpl() : mobileIconCarrierIdOverrides);
    }
}
