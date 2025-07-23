package com.android.systemui.settings.multisim.ui.viewmodel;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.SystemProperties;
import android.os.UserManager;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Operator;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.settings.multisim.data.repository.SimInfoRepository;
import com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl;
import com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$registerSimCardManagerCallback$1;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView.PrefferedSlotPopupWindow;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
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
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiSIMViewModelImpl implements MultiSIMViewModel, Button.ClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 barUpdateEvents;
    public SlotsView bindedView;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 buttonLayout;
    public final Flow buttonLayoutForData;
    public final Flow buttonLayoutForVoice;
    public final Map carrierNameFlows;
    public final Map iconResIdFlows;
    public final StateFlowImpl isBarShowing;
    public final ReadonlyStateFlow isButtonDisabled;
    public final Flow isButtonDisabledForData;
    public final StateFlowImpl isSecondaryUser;
    public final KeyguardStateController keyguardStateController;
    public final KnoxStateMonitor knoxStateMonitor;
    public final Context mContext;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserManager mUserManager;
    private final SettingsHelper settingsHelper;
    public final ReadonlyStateFlow simInfoPrimaryId;
    public final SimInfoRepository simInfoRepository;
    public final Map simNameFlows;
    public final ReadonlyStateFlow slots;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ButtonType.values().length];
            try {
                iArr[ButtonType.VOICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ButtonType.SMS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ButtonType.DATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ButtonType.SIMINFO1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ButtonType.SIMINFO2.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public MultiSIMViewModelImpl(Context context, CoroutineScope coroutineScope, Handler handler, ActivityStarter activityStarter, SettingsHelper settingsHelper, KeyguardStateController keyguardStateController, KnoxStateMonitor knoxStateMonitor, SimInfoRepository simInfoRepository, UserTracker userTracker) {
        this.mContext = context;
        this.activityStarter = activityStarter;
        this.settingsHelper = settingsHelper;
        this.keyguardStateController = keyguardStateController;
        this.knoxStateMonitor = knoxStateMonitor;
        this.simInfoRepository = simInfoRepository;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(userTrackerImpl.getUserId() != 0));
        this.isSecondaryUser = MutableStateFlow;
        this.mUserManager = (UserManager) context.getSystemService("user");
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$mUserChangedCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                MultiSIMViewModelImpl.this.isSecondaryUser.updateState(null, Boolean.valueOf(!r0.mUserManager.getUserInfo(i).isAdmin()));
            }
        };
        this.mUserChangedCallback = callback;
        SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) simInfoRepository;
        final Flow[] flowArr = {simInfoRepositoryImpl.sim1Name, simInfoRepositoryImpl.sim2Name, simInfoRepositoryImpl.sim1CarrierName, simInfoRepositoryImpl.sim2CarrierName, simInfoRepositoryImpl.sim1PhoneNumber, simInfoRepositoryImpl.sim2PhoneNumber, simInfoRepositoryImpl.sim1IconIndex, simInfoRepositoryImpl.sim2IconIndex, simInfoRepositoryImpl.isESim1, simInfoRepositoryImpl.isESim2, simInfoRepositoryImpl.isSlotReversed};
        Flow flow = new Flow() { // from class: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$combine$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ MultiSIMViewModelImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, MultiSIMViewModelImpl multiSIMViewModelImpl) {
                    super(3, continuation);
                    this.this$0 = multiSIMViewModelImpl;
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
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        String str = (String) objArr[0];
                        String str2 = (String) objArr[1];
                        String str3 = (String) objArr[2];
                        String str4 = (String) objArr[3];
                        String str5 = (String) objArr[4];
                        String str6 = (String) objArr[5];
                        int intValue = ((Integer) objArr[6]).intValue();
                        int intValue2 = ((Integer) objArr[7]).intValue();
                        boolean booleanValue = ((Boolean) objArr[8]).booleanValue();
                        boolean booleanValue2 = ((Boolean) objArr[9]).booleanValue();
                        boolean booleanValue3 = ((Boolean) objArr[10]).booleanValue();
                        int simIcon = ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).getSimIcon(intValue, booleanValue);
                        int simIcon2 = ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).getSimIcon(intValue2, booleanValue2);
                        List asList = booleanValue3 ? Arrays.asList(new SlotItem(str2, simIcon2, str4, str6), new SlotItem(str, simIcon, str3, str5)) : Arrays.asList(new SlotItem(str, simIcon, str3, str5), new SlotItem(str2, simIcon2, str4, str6));
                        this.label = 1;
                        if (flowCollector.emit(asList, this) == coroutineSingletons) {
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
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Arrays.asList(new SlotItem(UniversalCredentialManager.APPLET_FORM_FACTOR_SIM1, 0, simInfoRepositoryImpl._networkNameDefault, simInfoRepositoryImpl._unknownPhoneNumber), new SlotItem(UniversalCredentialManager.APPLET_FORM_FACTOR_SIM2, 1, simInfoRepositoryImpl._networkNameDefault, simInfoRepositoryImpl._unknownPhoneNumber)));
        this.slots = stateIn;
        this.simNameFlows = new LinkedHashMap();
        this.carrierNameFlows = new LinkedHashMap();
        this.iconResIdFlows = new LinkedHashMap();
        this.buttonLayout = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Button.Layout.NORMAL);
        final ReadonlyStateFlow readonlyStateFlow = simInfoRepositoryImpl.defaultVoiceSimId;
        this.buttonLayoutForVoice = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        int r5 = r5 - r3
                        if (r5 >= 0) goto L3e
                        com.android.systemui.settings.multisim.ui.viewmodel.Button$Layout r5 = com.android.systemui.settings.multisim.ui.viewmodel.Button.Layout.TEXTONLY_1
                        goto L45
                    L3e:
                        if (r5 <= r3) goto L43
                        com.android.systemui.settings.multisim.ui.viewmodel.Button$Layout r5 = com.android.systemui.settings.multisim.ui.viewmodel.Button.Layout.TEXTONLY_2
                        goto L45
                    L43:
                        com.android.systemui.settings.multisim.ui.viewmodel.Button$Layout r5 = com.android.systemui.settings.multisim.ui.viewmodel.Button.Layout.NORMAL
                    L45:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.buttonLayoutForData = FlowKt.distinctUntilChanged(FlowKt.combine(simInfoRepositoryImpl.isDataEnabled, simInfoRepositoryImpl.defaultDataSimId, stateIn, new MultiSIMViewModelImpl$buttonLayoutForData$1(this, null)));
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.combine(simInfoRepositoryImpl.isAirplaneMode, simInfoRepositoryImpl.isSatelliteMode, simInfoRepositoryImpl.isCalling, simInfoRepositoryImpl.isSRoaming, simInfoRepositoryImpl.isRestrictionsForMmsUse, new MultiSIMViewModelImpl$isButtonDisabled$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.TRUE);
        this.isButtonDisabled = stateIn2;
        this.isButtonDisabledForData = FlowKt.distinctUntilChanged(FlowKt.combine(stateIn2, simInfoRepositoryImpl.isDataSimSwitching, simInfoRepositoryImpl.isNetModeChanging, new MultiSIMViewModelImpl$isButtonDisabledForData$1(null)));
        this.simInfoPrimaryId = FlowKt.stateIn(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(simInfoRepositoryImpl.defaultDataSimId, simInfoRepositoryImpl.isSlotReversed, new MultiSIMViewModelImpl$simInfoPrimaryId$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.barUpdateEvents = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(simInfoRepositoryImpl.isMultiSIMReady, MutableStateFlow, new MultiSIMViewModelImpl$barUpdateEvents$1(null));
        this.isBarShowing = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        userTrackerImpl.addCallback(callback, new HandlerExecutor(handler));
    }

    public final Flow getButtonCarrierName(ButtonType buttonType) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.carrierNameFlows;
        Object obj = linkedHashMap.get(buttonType);
        if (obj == null) {
            ReadonlyStateFlow defaultSimId = toDefaultSimId(buttonType);
            SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) this.simInfoRepository;
            obj = FlowKt.distinctUntilChanged(FlowKt.combine(defaultSimId, simInfoRepositoryImpl.sim1CarrierName, simInfoRepositoryImpl.sim2CarrierName, simInfoRepositoryImpl.isSlotReversed, new MultiSIMViewModelImpl$getButtonCarrierName$1$1(buttonType, null)));
            linkedHashMap.put(buttonType, obj);
        }
        return (Flow) obj;
    }

    public final Flow getButtonIconResId(final ButtonType buttonType) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.iconResIdFlows;
        Object obj = linkedHashMap.get(buttonType);
        if (obj == null) {
            ReadonlyStateFlow defaultSimId = toDefaultSimId(buttonType);
            SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) this.simInfoRepository;
            final Flow[] flowArr = {defaultSimId, simInfoRepositoryImpl.sim1IconIndex, simInfoRepositoryImpl.sim2IconIndex, simInfoRepositoryImpl.isESim1, simInfoRepositoryImpl.isESim2, simInfoRepositoryImpl.isSlotReversed};
            obj = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$getButtonIconResId$lambda$4$$inlined$combine$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$getButtonIconResId$lambda$4$$inlined$combine$1$3, reason: invalid class name */
                public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                    final /* synthetic */ ButtonType $type$inlined;
                    private /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;
                    final /* synthetic */ MultiSIMViewModelImpl this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass3(Continuation continuation, ButtonType buttonType, MultiSIMViewModelImpl multiSIMViewModelImpl) {
                        super(3, continuation);
                        this.$type$inlined = buttonType;
                        this.this$0 = multiSIMViewModelImpl;
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$type$inlined, this.this$0);
                        anonymousClass3.L$0 = (FlowCollector) obj;
                        anonymousClass3.L$1 = (Object[]) obj2;
                        return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        Integer num;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowCollector flowCollector = (FlowCollector) this.L$0;
                            Object[] objArr = (Object[]) this.L$1;
                            Object obj2 = objArr[0];
                            Object obj3 = objArr[1];
                            Object obj4 = objArr[2];
                            Object obj5 = objArr[3];
                            Object obj6 = objArr[4];
                            boolean booleanValue = ((Boolean) objArr[5]).booleanValue();
                            boolean booleanValue2 = ((Boolean) obj6).booleanValue();
                            boolean booleanValue3 = ((Boolean) obj5).booleanValue();
                            int intValue = ((Number) obj4).intValue();
                            int intValue2 = ((Number) obj3).intValue();
                            int intValue3 = ((Number) obj2).intValue();
                            ButtonType buttonType = this.$type$inlined;
                            if (buttonType == ButtonType.VOICE) {
                                intValue3--;
                            }
                            if (buttonType == ButtonType.SIMINFO1) {
                                num = new Integer(booleanValue ? ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).getSimIcon(intValue, booleanValue2) : ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).getSimIcon(intValue2, booleanValue3));
                            } else if (buttonType == ButtonType.SIMINFO2) {
                                num = new Integer(booleanValue ? ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).getSimIcon(intValue2, booleanValue3) : ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).getSimIcon(intValue, booleanValue2));
                            } else {
                                num = new Integer(intValue3 == 1 ? ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).getSimIcon(intValue, booleanValue2) : ((SimInfoRepositoryImpl) this.this$0.simInfoRepository).getSimIcon(intValue2, booleanValue3));
                            }
                            this.label = 1;
                            if (flowCollector.emit(num, this) == coroutineSingletons) {
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
                    Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl$getButtonIconResId$lambda$4$$inlined$combine$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return new Object[flowArr2.length];
                        }
                    }, new AnonymousClass3(null, buttonType, this), flowCollector, continuation);
                    return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                }
            });
            linkedHashMap.put(buttonType, obj);
        }
        return (Flow) obj;
    }

    public final Flow getButtonSimName(ButtonType buttonType) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.simNameFlows;
        Object obj = linkedHashMap.get(buttonType);
        if (obj == null) {
            ReadonlyStateFlow defaultSimId = toDefaultSimId(buttonType);
            SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) this.simInfoRepository;
            obj = FlowKt.distinctUntilChanged(FlowKt.combine(defaultSimId, simInfoRepositoryImpl.sim1Name, simInfoRepositoryImpl.sim2Name, simInfoRepositoryImpl.isSlotReversed, new MultiSIMViewModelImpl$getButtonSimName$1$1(buttonType, null)));
            linkedHashMap.put(buttonType, obj);
        }
        return (Flow) obj;
    }

    public final int getDefaultSlotIndex(ButtonType buttonType) {
        int intValue = ((Number) toDefaultSimId(buttonType).$$delegate_0.getValue()).intValue();
        ButtonType buttonType2 = ButtonType.VOICE;
        SimInfoRepository simInfoRepository = this.simInfoRepository;
        if (buttonType != buttonType2) {
            if (((Boolean) ((SimInfoRepositoryImpl) simInfoRepository).isSlotReversed.$$delegate_0.getValue()).booleanValue()) {
                return 1 - intValue;
            }
        } else if ((intValue == 1 || intValue == 2) && ((Boolean) ((SimInfoRepositoryImpl) simInfoRepository).isSlotReversed.$$delegate_0.getValue()).booleanValue()) {
            return 3 - intValue;
        }
        return intValue;
    }

    public final boolean isAvailable() {
        if (((Boolean) ((SimInfoRepositoryImpl) this.simInfoRepository).isMultiSIMReady.$$delegate_0.getValue()).booleanValue()) {
            boolean z = DeviceType.isLDUSKU() || DeviceType.isLDUOLDModel();
            boolean booleanValue = ((Boolean) this.isSecondaryUser.getValue()).booleanValue();
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("isLDUModel = ", " isSecondaryUser = ", "MULTISIM-VM", z, booleanValue);
            if (!z && !booleanValue && !this.settingsHelper.isEmergencyMode()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBlockedAllMultiSimBar() {
        if (Operator.QUICK_IS_XNX_BRANDING && SystemProperties.get("ril.lockpolicy", "0").equals("1")) {
            return true;
        }
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.knoxStateMonitor;
        EdmMonitor edmMonitor = knoxStateMonitorImpl.mEdmMonitor;
        return !(edmMonitor == null || edmMonitor.mSettingsChangesAllowed) || knoxStateMonitorImpl.isUserMobileDataRestricted();
    }

    public final void launchSimManager() {
        if (isBlockedAllMultiSimBar()) {
            return;
        }
        Intent intent = new Intent();
        Log.w("MULTISIM-VM", "onClick()");
        try {
            intent.setClassName("com.samsung.android.app.telephonyui", "com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.SimCardMgrActivity");
            intent.addFlags(268468224);
            this.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
        } catch (ActivityNotFoundException unused) {
            Log.e("MULTISIM-VM", "activity not found");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onSlotButtonClick(ButtonType buttonType, View view) {
        SemBlurInfo.Builder builder;
        Bitmap bitmap;
        SimInfoRepository simInfoRepository = this.simInfoRepository;
        SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) simInfoRepository;
        if (((Boolean) simInfoRepositoryImpl.isAirplaneMode.$$delegate_0.getValue()).booleanValue()) {
            SlotsView slotsView = this.bindedView;
            if (slotsView != null) {
                Toast.makeText(((MultiSIMPreferredSlotView) slotsView).mContext, this.mContext.getString(R.string.qs_multisim_airplanemode_on), 0).show();
                return;
            }
            return;
        }
        if (((Boolean) simInfoRepositoryImpl.isSatelliteMode.$$delegate_0.getValue()).booleanValue()) {
            return;
        }
        ButtonType buttonType2 = ButtonType.DATA;
        if (buttonType == buttonType2 && ((Boolean) simInfoRepositoryImpl.isDataSimSwitching.$$delegate_0.getValue()).booleanValue()) {
            SlotsView slotsView2 = this.bindedView;
            if (slotsView2 != null) {
                Toast.makeText(((MultiSIMPreferredSlotView) slotsView2).mContext, this.mContext.getString(R.string.qs_multisim_data_switching), 0).show();
                return;
            }
            return;
        }
        if ((buttonType == buttonType2 && ((Boolean) simInfoRepositoryImpl.isNetModeChanging.$$delegate_0.getValue()).booleanValue()) || ((Boolean) simInfoRepositoryImpl.isCalling.$$delegate_0.getValue()).booleanValue() || ((Boolean) simInfoRepositoryImpl.isSRoaming.$$delegate_0.getValue()).booleanValue() || ((Boolean) simInfoRepositoryImpl.isRestrictionsForMmsUse.$$delegate_0.getValue()).booleanValue()) {
            return;
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if (keyguardStateControllerImpl.mShowing && keyguardStateControllerImpl.mSecure && !keyguardStateControllerImpl.mCanDismissLockScreen && this.settingsHelper.isLockFunctionsEnabled()) {
            launchSimManager();
            return;
        }
        if (MultiSIMViewModelKt.isSIMINFO(buttonType)) {
            launchSimManager();
            return;
        }
        if (isBlockedAllMultiSimBar()) {
            return;
        }
        SlotsView slotsView3 = this.bindedView;
        if (slotsView3 != null) {
            MultiSIMPreferredSlotView multiSIMPreferredSlotView = (MultiSIMPreferredSlotView) slotsView3;
            if (multiSIMPreferredSlotView.mPopupWindow == null) {
                multiSIMPreferredSlotView.mPopupWindow = multiSIMPreferredSlotView.new PrefferedSlotPopupWindow(multiSIMPreferredSlotView.mContext);
            }
            MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow = multiSIMPreferredSlotView.mPopupWindow;
            prefferedSlotPopupWindow.mSlotListButton1Group.setVisibility(0);
            prefferedSlotPopupWindow.mSlotListButton2Group.setVisibility(0);
            int i = MultiSIMPreferredSlotView.AnonymousClass2.$SwitchMap$com$android$systemui$settings$multisim$ui$viewmodel$ButtonType[buttonType.ordinal()];
            if (i == 1) {
                prefferedSlotPopupWindow.mSlotListAskButtonGroup.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(prefferedSlotPopupWindow, this, 1));
                prefferedSlotPopupWindow.mSlotListButton1Group.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(prefferedSlotPopupWindow, this, 2));
                prefferedSlotPopupWindow.mSlotListButton2Group.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(prefferedSlotPopupWindow, this, 3));
                prefferedSlotPopupWindow.mSlotListOthersButtonGroup.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(prefferedSlotPopupWindow, this, 4));
                int defaultSlotIndex = getDefaultSlotIndex(ButtonType.VOICE);
                prefferedSlotPopupWindow.setSlotListMenuFont(buttonType, defaultSlotIndex);
                prefferedSlotPopupWindow.mSlotListAskButtonText.setTextColor(defaultSlotIndex == 0 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                prefferedSlotPopupWindow.setSlotListMenuColor(0, defaultSlotIndex == 1 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                prefferedSlotPopupWindow.setSlotListMenuColor(1, defaultSlotIndex == 2 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                prefferedSlotPopupWindow.mSlotListOthersButtonText.setTextColor(defaultSlotIndex == 3 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                prefferedSlotPopupWindow.mSlotListAskCheckedImage.setVisibility(defaultSlotIndex == 0 ? 0 : 8);
                prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(defaultSlotIndex == 1 ? 0 : 8);
                prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(defaultSlotIndex == 2 ? 0 : 8);
                prefferedSlotPopupWindow.mSlotListOthersCheckedImage.setVisibility(defaultSlotIndex == 3 ? 0 : 8);
                SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MULTISIM_BAR_VALUE_OF_EACH_SLOT, "isChanged", "calls", SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
            } else if (i == 3) {
                prefferedSlotPopupWindow.mSlotListButton1Group.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(prefferedSlotPopupWindow, this, 5));
                prefferedSlotPopupWindow.mSlotListButton2Group.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(prefferedSlotPopupWindow, this, 6));
                int defaultSlotIndex2 = getDefaultSlotIndex(ButtonType.SMS);
                prefferedSlotPopupWindow.setSlotListMenuColor(0, defaultSlotIndex2 == 1 ? prefferedSlotPopupWindow.mPopupNormalTextColor : prefferedSlotPopupWindow.mPopupSelectedTextColor);
                prefferedSlotPopupWindow.setSlotListMenuColor(1, defaultSlotIndex2 == 1 ? prefferedSlotPopupWindow.mPopupSelectedTextColor : prefferedSlotPopupWindow.mPopupNormalTextColor);
                prefferedSlotPopupWindow.setSlotListMenuFont(buttonType, defaultSlotIndex2);
                prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(defaultSlotIndex2 == 0 ? 0 : 8);
                prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(defaultSlotIndex2 == 1 ? 0 : 8);
                SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MULTISIM_BAR_VALUE_OF_EACH_SLOT, "isChanged", "text messages", SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
            } else if (i == 4) {
                prefferedSlotPopupWindow.mSlotListButton1Group.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(prefferedSlotPopupWindow, this, 7));
                prefferedSlotPopupWindow.mSlotListButton2Group.setOnClickListener(new MultiSIMPreferredSlotView$PrefferedSlotButton$$ExternalSyntheticLambda0(prefferedSlotPopupWindow, this, 8));
                int defaultSlotIndex3 = getDefaultSlotIndex(buttonType2);
                if (!((Boolean) ((SimInfoRepositoryImpl) simInfoRepository).isDataEnabled.$$delegate_0.getValue()).booleanValue()) {
                    prefferedSlotPopupWindow.setSlotListMenuColor(0, prefferedSlotPopupWindow.mPopupNormalTextColor);
                    prefferedSlotPopupWindow.setSlotListMenuColor(1, prefferedSlotPopupWindow.mPopupNormalTextColor);
                    prefferedSlotPopupWindow.mSlotListButtonText1.setTypeface(prefferedSlotPopupWindow.mPopupNonSelectedFont);
                    prefferedSlotPopupWindow.mSlotListButtonText2.setTypeface(prefferedSlotPopupWindow.mPopupNonSelectedFont);
                    prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(8);
                } else if (defaultSlotIndex3 == 0) {
                    prefferedSlotPopupWindow.setSlotListMenuColor(0, prefferedSlotPopupWindow.mPopupSelectedTextColor);
                    prefferedSlotPopupWindow.setSlotListMenuColor(1, prefferedSlotPopupWindow.mPopupNormalTextColor);
                    prefferedSlotPopupWindow.setSlotListMenuFont(buttonType, defaultSlotIndex3);
                    prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(0);
                    prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(8);
                } else if (defaultSlotIndex3 == 1) {
                    prefferedSlotPopupWindow.setSlotListMenuColor(0, prefferedSlotPopupWindow.mPopupNormalTextColor);
                    prefferedSlotPopupWindow.setSlotListMenuColor(1, prefferedSlotPopupWindow.mPopupSelectedTextColor);
                    prefferedSlotPopupWindow.setSlotListMenuFont(buttonType, defaultSlotIndex3);
                    prefferedSlotPopupWindow.mSlotListButtonCheckedImage1.setVisibility(8);
                    prefferedSlotPopupWindow.mSlotListButtonCheckedImage2.setVisibility(0);
                }
                SimInfoRepositoryImpl simInfoRepositoryImpl2 = (SimInfoRepositoryImpl) simInfoRepository;
                if (simInfoRepositoryImpl2.isDataBlocked(((Boolean) simInfoRepositoryImpl2.isSlotReversed.$$delegate_0.getValue()).booleanValue() ? 1 : 0)) {
                    prefferedSlotPopupWindow.mSlotListButton1Group.setVisibility(8);
                }
                SimInfoRepositoryImpl simInfoRepositoryImpl3 = (SimInfoRepositoryImpl) simInfoRepository;
                if (simInfoRepositoryImpl3.isDataBlocked(!((Boolean) simInfoRepositoryImpl3.isSlotReversed.$$delegate_0.getValue()).booleanValue() ? 1 : 0)) {
                    prefferedSlotPopupWindow.mSlotListButton2Group.setVisibility(8);
                }
                SystemUIAnalytics.sendRunestoneEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MULTISIM_BAR_VALUE_OF_EACH_SLOT, "isChanged", "mobile data", SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
            }
            int dimensionPixelSize = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_bg_divider_top_padding);
            int dimensionPixelSize2 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_slot_top_padding);
            int dimensionPixelSize3 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_bg_horizontal_padding);
            int dimensionPixelSize4 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_bg_divider_bottom_padding);
            int dimensionPixelSize5 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_slot_bottom_padding);
            ButtonType buttonType3 = ButtonType.VOICE;
            if (buttonType == buttonType3) {
                prefferedSlotPopupWindow.mSlotListAskButtonGroup.setVisibility(0);
                prefferedSlotPopupWindow.mSlotListButton1Group.setPaddingRelative(dimensionPixelSize3, dimensionPixelSize, dimensionPixelSize3, dimensionPixelSize4);
                prefferedSlotPopupWindow.mSlotListButton1Group.setBackground(prefferedSlotPopupWindow.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_middle_ripple_bg));
            } else {
                prefferedSlotPopupWindow.mSlotListAskButtonGroup.setVisibility(8);
                prefferedSlotPopupWindow.mSlotListButton1Group.setPaddingRelative(dimensionPixelSize3, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize4);
                prefferedSlotPopupWindow.mSlotListButton1Group.setBackground(prefferedSlotPopupWindow.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_top_ripple_bg));
            }
            if (buttonType == buttonType3) {
                TelecomManager from = TelecomManager.from(this.mContext);
                Iterator it = from.getCallCapablePhoneAccounts(true).iterator();
                while (it.hasNext()) {
                    PhoneAccount phoneAccount = from.getPhoneAccount((PhoneAccountHandle) it.next());
                    if (phoneAccount != null && (phoneAccount.getCapabilities() & 4) == 0) {
                        Log.d("MULTISIM-VM", "Support Call preferred Others");
                        prefferedSlotPopupWindow.mSlotListOthersButtonGroup.setVisibility(0);
                        prefferedSlotPopupWindow.mSlotListButton2Group.setPaddingRelative(dimensionPixelSize3, dimensionPixelSize, dimensionPixelSize3, dimensionPixelSize4);
                        prefferedSlotPopupWindow.mSlotListButton2Group.setBackground(prefferedSlotPopupWindow.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_middle_ripple_bg));
                        break;
                    }
                }
            }
            prefferedSlotPopupWindow.mSlotListOthersButtonGroup.setVisibility(8);
            prefferedSlotPopupWindow.mSlotListButton2Group.setPaddingRelative(dimensionPixelSize3, dimensionPixelSize, dimensionPixelSize3, dimensionPixelSize5);
            prefferedSlotPopupWindow.mSlotListButton2Group.setBackground(prefferedSlotPopupWindow.mContext.getResources().getDrawable(R.drawable.qs_panel_multi_sim_menu_item_bottom_ripple_bg));
            if (view != null) {
                View contentView = prefferedSlotPopupWindow.getContentView();
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                int height = view.getHeight();
                int width = view.getWidth();
                contentView.measure(0, 0);
                int measuredHeight = contentView.getMeasuredHeight();
                int measuredWidth = contentView.getMeasuredWidth();
                int i2 = prefferedSlotPopupWindow.mContext.getResources().getDisplayMetrics().heightPixels;
                int i3 = prefferedSlotPopupWindow.mContext.getResources().getDisplayMetrics().widthPixels;
                int i4 = prefferedSlotPopupWindow.mPopupWindowTopMargin;
                int i5 = iArr[1];
                byte b = (i2 - i5) - i4 < measuredHeight;
                int i6 = iArr[0];
                if (i3 - i6 < measuredWidth) {
                    i6 = (i6 + width) - measuredWidth;
                }
                if (b != false) {
                    i4 = height - measuredHeight;
                }
                int[] iArr2 = {i6, i5 + i4};
                MultiSIMPreferredSlotView multiSIMPreferredSlotView2 = MultiSIMPreferredSlotView.this;
                multiSIMPreferredSlotView2.getClass();
                prefferedSlotPopupWindow.showAtLocation(view, (!DeviceState.isTablet() || DeviceState.isVoiceCapable(multiSIMPreferredSlotView2.mContext)) ? 8388659 : 49, iArr2[0], iArr2[1]);
                float dimensionPixelSize6 = prefferedSlotPopupWindow.mContext.getResources().getDimensionPixelSize(R.dimen.qs_multisim_popup_menu_bg_radius);
                int color = prefferedSlotPopupWindow.mContext.getResources().getColor(R.color.sec_qs_multisim_preffered_slot_background);
                if (QpRune.QUICK_PANEL_BLUR_DEFAULT) {
                    builder = new SemBlurInfo.Builder(0).setRadius(200).setBackgroundColor(color).setBackgroundCornerRadius(dimensionPixelSize6);
                } else {
                    if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                        int i7 = iArr2[0];
                        int i8 = iArr2[1];
                        int measuredWidth2 = prefferedSlotPopupWindow.mPopupContentView.getMeasuredWidth();
                        int measuredHeight2 = prefferedSlotPopupWindow.mPopupContentView.getMeasuredHeight();
                        try {
                            bitmap = SemWindowManager.getInstance().screenshot(((WindowManager) prefferedSlotPopupWindow.mContext.getSystemService("window")).getDefaultDisplay().getDisplayId(), 2036, true, new Rect(i7, i8, i7 + measuredWidth2, i8 + measuredHeight2), measuredWidth2, measuredHeight2, false, 0, true);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                            bitmap = null;
                        }
                        if (bitmap == null) {
                            bitmap = null;
                        }
                        if (bitmap != null) {
                            builder = new SemBlurInfo.Builder(1).setRadius(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend).setBitmap(bitmap);
                        }
                    }
                    builder = null;
                }
                if (builder != null) {
                    prefferedSlotPopupWindow.mPopupContentView.semSetBlurInfo(builder.build());
                }
            }
            multiSIMPreferredSlotView.mPopupWindow.updateSlotListPopupContents((List) this.slots.$$delegate_0.getValue());
        }
        simInfoRepositoryImpl.updatePhoneNumberWhenNeeded();
    }

    public final boolean onSlotsListItemClick(ButtonType buttonType, int i) {
        int defaultSlotIndex = getDefaultSlotIndex(buttonType);
        int i2 = WhenMappings.$EnumSwitchMapping$0[buttonType.ordinal()];
        if (i2 == 1) {
            if (i == defaultSlotIndex) {
                return false;
            }
            setDefaultSlot(ButtonType.VOICE, i);
            return true;
        }
        if (i2 == 2) {
            if (i == defaultSlotIndex) {
                return false;
            }
            setDefaultSlot(ButtonType.SMS, i);
            return true;
        }
        if (i2 != 3) {
            return false;
        }
        SimInfoRepository simInfoRepository = this.simInfoRepository;
        if (i == defaultSlotIndex && ((Boolean) ((SimInfoRepositoryImpl) simInfoRepository).isDataEnabled.$$delegate_0.getValue()).booleanValue()) {
            return false;
        }
        SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) simInfoRepository;
        if (simInfoRepositoryImpl.isDataBlocked(((Boolean) simInfoRepositoryImpl.isSlotReversed.$$delegate_0.getValue()).booleanValue() ? 1 - i : i)) {
            return false;
        }
        setDefaultSlot(ButtonType.DATA, i);
        return true;
    }

    public final void register(SlotsView slotsView) {
        this.bindedView = slotsView;
        MultiSIMViewModelImpl$$ExternalSyntheticLambda0 multiSIMViewModelImpl$$ExternalSyntheticLambda0 = new MultiSIMViewModelImpl$$ExternalSyntheticLambda0(slotsView);
        SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) this.simInfoRepository;
        simInfoRepositoryImpl.needUpdatePhoneNumber = multiSIMViewModelImpl$$ExternalSyntheticLambda0;
        if (simInfoRepositoryImpl.simCardManagerService == null) {
            SimCardManagerServiceProvider service = SimCardManagerServiceProvider.getService(simInfoRepositoryImpl.mContext);
            simInfoRepositoryImpl.simCardManagerService = service;
            Log.d("MULTISIM-PROD-REPO", "registerSimCardManagerCallback SimCardManagerService " + service);
        }
        SimInfoRepositoryImpl$registerSimCardManagerCallback$1 simInfoRepositoryImpl$registerSimCardManagerCallback$1 = SimCardManagerServiceProvider.sSimCardManagerServiceCallback;
        simInfoRepositoryImpl.simCardCallback = simInfoRepositoryImpl$registerSimCardManagerCallback$1;
        if (simInfoRepositoryImpl$registerSimCardManagerCallback$1 == null) {
            SimInfoRepositoryImpl$registerSimCardManagerCallback$1 simInfoRepositoryImpl$registerSimCardManagerCallback$12 = new SimInfoRepositoryImpl$registerSimCardManagerCallback$1(simInfoRepositoryImpl);
            simInfoRepositoryImpl.simCardCallback = simInfoRepositoryImpl$registerSimCardManagerCallback$12;
            if (simInfoRepositoryImpl.simCardManagerService != null) {
                try {
                    SimCardManagerServiceProvider.sSimCardManagerServiceCallback = simInfoRepositoryImpl$registerSimCardManagerCallback$12;
                } catch (Exception e) {
                    Log.d("MULTISIM-PROD-REPO", "Caught exception from registerSimCardManagerCallback", e);
                }
            } else {
                Log.d("MULTISIM-PROD-REPO", "registerSimCardManagerCallback : mSimCardManagerService is null ");
            }
        } else {
            Log.d("MULTISIM-PROD-REPO", "registerSimCardManagerCallback : mSimCardCallback is not null ");
        }
        simInfoRepositoryImpl.isRegistered = true;
        Log.d("MULTISIM-PROD-REPO", "updateCurrentDefaultSlot list");
        List list = CollectionsKt___CollectionsKt.toList(simInfoRepositoryImpl.mDefaultIdUpdateList);
        ((ArrayList) simInfoRepositoryImpl.mDefaultIdUpdateList).clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            simInfoRepositoryImpl.updateCurrentDefaultSlot((ButtonType) it.next());
        }
    }

    public final void setDefaultSlot(ButtonType buttonType, int i) {
        int i2 = i;
        ButtonType buttonType2 = ButtonType.VOICE;
        SimInfoRepository simInfoRepository = this.simInfoRepository;
        if (buttonType != buttonType2) {
            if (((Boolean) ((SimInfoRepositoryImpl) simInfoRepository).isSlotReversed.$$delegate_0.getValue()).booleanValue()) {
                i2 = 1 - i2;
            }
        } else if ((i2 == 1 || i2 == 2) && ((Boolean) ((SimInfoRepositoryImpl) simInfoRepository).isSlotReversed.$$delegate_0.getValue()).booleanValue()) {
            i2 = 3 - i2;
        }
        int i3 = i2;
        SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) simInfoRepository;
        simInfoRepositoryImpl.getClass();
        Log.w("MULTISIM-PROD-REPO", "setDefaultSlot : type = " + buttonType + ", slotId = " + i3);
        String str = "PREFERRED_MOBILE_DATA";
        if (SimCardManagerServiceProvider.isServiceRunningCheck(simInfoRepositoryImpl.mContext)) {
            int index = buttonType.getIndex();
            try {
                Bundle bundle = new Bundle();
                if (index == 0) {
                    bundle.putString("changeType", "PREFERRED_VOICE_CALLS");
                } else if (index == 1) {
                    bundle.putString("changeType", "PREFERRED_TEXT_MESSAGES");
                } else if (index == 2) {
                    bundle.putString("changeType", "PREFERRED_MOBILE_DATA");
                    SimCardManagerServiceProvider.mIsRemainCallbackCall = true;
                }
                Log.d("SimCardManagerServiceProvider", "setChangeSimCardManagerSlot : mIsRemainCallbackCall = " + SimCardManagerServiceProvider.mIsRemainCallbackCall);
                bundle.putInt("selectItem", i3);
                Bundle call = SimCardManagerServiceProvider.mContext.getContentResolver().call(SimCardManagerServiceProvider.INTERNAL_URI, "quickpanel_simcard_change", (String) null, bundle);
                if (call == null) {
                    Log.d("SimCardManagerServiceProvider", "bundle is null : quickpanel_simcard_change");
                } else {
                    call.getBoolean("success");
                }
            } catch (Throwable th) {
                Log.e("SimCardManagerServiceProvider", th.toString());
            }
        } else {
            Log.e("MULTISIM-PROD-REPO", "setDefaultSlotByMethodCall");
            if (buttonType == buttonType2) {
                str = "PREFERRED_VOICE_CALLS";
            } else if (buttonType == ButtonType.SMS) {
                str = "PREFERRED_TEXT_MESSAGES";
            } else if (buttonType != ButtonType.DATA) {
                str = null;
            }
            try {
                Bundle bundle2 = new Bundle();
                bundle2.putString("changeType", str);
                bundle2.putInt("selectItem", i3);
                Bundle call2 = simInfoRepositoryImpl.mContext.getContentResolver().call(SimInfoRepositoryImpl.INTERNAL_URI, "quickpanel_simcard_change", (String) null, bundle2);
                if (call2 == null) {
                    Log.d("MULTISIM-PROD-REPO", "bundle is null : quickpanel_simcard_change");
                } else {
                    Log.d("MULTISIM-PROD-REPO", "quickpanel_simcard_change, " + call2.getBoolean("success") + ", " + ((Throwable) call2.getParcelable("error")));
                }
            } catch (Throwable th2) {
                Log.e("MULTISIM-PROD-REPO", "quickpanel_simcard_change, " + th2);
            }
            simInfoRepositoryImpl.simCardManagerService = SimCardManagerServiceProvider.getService(simInfoRepositoryImpl.mContext);
        }
        if (buttonType != ButtonType.DATA || i3 == ((Number) simInfoRepositoryImpl._defaultDataSimId.getValue()).intValue()) {
            return;
        }
        simInfoRepositoryImpl._isDataSimSwitching.updateState(null, Boolean.TRUE);
    }

    public final ReadonlyStateFlow toDefaultSimId(ButtonType buttonType) {
        int i = WhenMappings.$EnumSwitchMapping$0[buttonType.ordinal()];
        SimInfoRepository simInfoRepository = this.simInfoRepository;
        if (i == 1) {
            return ((SimInfoRepositoryImpl) simInfoRepository).defaultVoiceSimId;
        }
        if (i == 2) {
            return ((SimInfoRepositoryImpl) simInfoRepository).defaultSmsSimId;
        }
        if (i == 3 || i == 4 || i == 5) {
            return ((SimInfoRepositoryImpl) simInfoRepository).defaultDataSimId;
        }
        throw new NoWhenBranchMatchedException();
    }
}
