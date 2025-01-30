package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.view.View;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DeXStatusBarIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DeXStatusBarIconModelKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.SignalIconModelKt;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CellularIconViewModel implements MobileIconViewModelCommon {
    public final StateFlow activity;
    public final ReadonlyStateFlow activityContainerVisible;
    public final ReadonlyStateFlow activityIcon;
    public final ReadonlyStateFlow activityInVisible;
    public final ReadonlyStateFlow activityOutVisible;
    public final ReadonlyStateFlow anyChanges;
    public final ReadonlyStateFlow contentDescription;
    public boolean dataServiceAcquired;
    public final DesktopManager desktopManager;
    public final Flow dexStatusBarIcon;
    public final ReadonlyStateFlow icon;
    public final ReadonlyStateFlow isVisible;
    public final ReadonlyStateFlow networkTypeIcon;
    public final ReadonlyStateFlow roaming;
    public final ReadonlyStateFlow roamingIcon;
    public final ReadonlyStateFlow showNetworkTypeIcon;
    public final MobileSimpleLogger simpleLogger;
    public final int slotId;
    public final int subscriptionId;
    public final ReadonlyStateFlow updateDeXStatusBarIconModel;
    public final ReadonlyStateFlow voiceNoServiceIcon;
    public boolean voiceServiceAcquired;

    public CellularIconViewModel(int i, MobileIconInteractor mobileIconInteractor, AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, CoroutineScope coroutineScope, DesktopManager desktopManager, String str) {
        this.subscriptionId = i;
        this.desktopManager = desktopManager;
        int slotIndex = i == Integer.MAX_VALUE ? 0 : SubscriptionManager.getSlotIndex(i);
        this.slotId = slotIndex;
        MobileIconInteractorImpl mobileIconInteractorImpl = (MobileIconInteractorImpl) mobileIconInteractor;
        StateFlow stateFlow = mobileIconInteractorImpl.isInService;
        boolean booleanValue = ((Boolean) stateFlow.getValue()).booleanValue();
        ReadonlyStateFlow readonlyStateFlow = mobileIconInteractorImpl.isDataConnected;
        boolean booleanValue2 = ((Boolean) readonlyStateFlow.getValue()).booleanValue();
        ReadonlyStateFlow readonlyStateFlow2 = mobileIconInteractorImpl.networkTypeIconGroup;
        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) readonlyStateFlow2.getValue();
        StateFlow stateFlow2 = mobileIconInteractorImpl.mobileServiceState;
        MobileSimpleLogger mobileSimpleLogger = new MobileSimpleLogger(slotIndex, i, str, booleanValue, booleanValue2, networkTypeIconModel, ((MobileServiceState) stateFlow2.getValue()).telephonyDisplayInfo.getNetworkType(), ((MobileServiceState) stateFlow2.getValue()).telephonyDisplayInfo.getOverrideNetworkType(), ((MobileServiceState) stateFlow2.getValue()).telephonyDisplayInfo.is5gAvailable(), ((MobileServiceState) stateFlow2.getValue()).simCardInfo.simType.toString());
        this.simpleLogger = mobileSimpleLogger;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(!connectivityConstants.hasDataCapabilities ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : FlowKt.combine(airplaneModeInteractor.isAirplaneMode, mobileIconInteractorImpl.isForceHidden, mobileIconInteractorImpl.isSimOff, mobileIconInteractorImpl.isSim1On, new CellularIconViewModel$isVisible$1(this, null)));
        TableLogBuffer tableLogBuffer = mobileIconInteractorImpl.tableLogBuffer;
        SafeFlow logDiffsForTable = DiffableKt.logDiffsForTable(distinctUntilChanged, tableLogBuffer, "VM", "visible", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, WhileSubscribed$default, bool);
        this.isVisible = stateIn;
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(mobileIconInteractorImpl.icon);
        SignalIconModel.Cellular cellular = SignalIconModelKt.DEFAULT_SIGNAL_ICON;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged2, tableLogBuffer, "VM.signalIcon", cellular), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), cellular);
        this.icon = stateIn2;
        this.contentDescription = mobileIconInteractorImpl.contentDescription;
        final Flow[] flowArr = {readonlyStateFlow, mobileIconInteractorImpl.isDataEnabled, mobileIconInteractorImpl.alwaysShowDataRatIcon, mobileIconInteractorImpl.mobileIsDefault, mobileIconInteractorImpl.carrierNetworkChangeActive, mobileIconInteractorImpl.isVoWifiConnected};
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$combine$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$combine$1$3", m277f = "MobileIconViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$combine$1$3 */
            public final class C33303 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                public C33303(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C33303 c33303 = new C33303((Continuation) obj3);
                    c33303.L$0 = (FlowCollector) obj;
                    c33303.L$1 = (Object[]) obj2;
                    return c33303.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Boolean[] boolArr = (Boolean[]) ((Object[]) this.L$1);
                        boolean z = false;
                        boolean booleanValue = boolArr[0].booleanValue();
                        boolArr[1].booleanValue();
                        boolean booleanValue2 = boolArr[2].booleanValue();
                        boolean booleanValue3 = boolArr[3].booleanValue();
                        boolean booleanValue4 = boolArr[4].booleanValue();
                        boolean booleanValue5 = boolArr[5].booleanValue();
                        if (booleanValue2 || (!booleanValue4 && booleanValue && booleanValue3 && !booleanValue5)) {
                            z = true;
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
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Boolean[flowArr2.length];
                    }
                }, new C33303(null), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }), tableLogBuffer, "VM", "showNetworkTypeIcon", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.showNetworkTypeIcon = stateIn3;
        ReadonlyStateFlow readonlyStateFlow3 = mobileIconInteractorImpl.disabledDataIcon;
        ReadonlyStateFlow stateIn4 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlow2, stateIn3, readonlyStateFlow3, new CellularIconViewModel$networkTypeIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
        this.networkTypeIcon = stateIn4;
        ReadonlyStateFlow stateIn5 = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) mobileIconInteractorImpl.isRoaming, tableLogBuffer, "VM", "roaming", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.roaming = stateIn5;
        ReadonlyStateFlow stateIn6 = FlowKt.stateIn(FlowKt.combine(stateIn5, mobileIconInteractorImpl.roamingId, readonlyStateFlow3, mobileIconInteractorImpl.otherSlotInCallState, mobileIconInteractorImpl.femtoCellIndicatorId, new CellularIconViewModel$roamingIcon$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
        this.roamingIcon = stateIn6;
        final StateFlow stateFlow3 = mobileIconInteractorImpl.activity;
        this.activity = stateFlow3;
        this.activityInVisible = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1$2 */
            public final class C33312 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1$2", m277f = "MobileIconViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C33312.this.emit(null, this);
                    }
                }

                public C33312(FlowCollector flowCollector) {
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
                                DataActivityModel dataActivityModel = (DataActivityModel) obj;
                                Boolean valueOf = Boolean.valueOf(dataActivityModel != null ? dataActivityModel.hasActivityIn : false);
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
                Object collect = Flow.this.collect(new C33312(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "VM", "activityInVisible", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.activityOutVisible = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2$2 */
            public final class C33322 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2$2", m277f = "MobileIconViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        return C33322.this.emit(null, this);
                    }
                }

                public C33322(FlowCollector flowCollector) {
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
                                DataActivityModel dataActivityModel = (DataActivityModel) obj;
                                Boolean valueOf = Boolean.valueOf(dataActivityModel != null ? dataActivityModel.hasActivityOut : false);
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
                Object collect = Flow.this.collect(new C33322(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "VM", "activityOutVisible", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.activityContainerVisible = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3$2 */
            public final class C33332 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3$2", m277f = "MobileIconViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        return C33332.this.emit(null, this);
                    }
                }

                public C33332(FlowCollector flowCollector) {
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
                                DataActivityModel dataActivityModel = (DataActivityModel) obj;
                                Boolean valueOf = Boolean.valueOf(dataActivityModel != null && (dataActivityModel.hasActivityIn || dataActivityModel.hasActivityOut));
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
                Object collect = Flow.this.collect(new C33332(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", "activityContainerVisible", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        ReadonlyStateFlow stateIn7 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(stateFlow3, readonlyStateFlow2, stateIn3, mobileIconInteractorImpl.disabledActivityIcon, new CellularIconViewModel$activityIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), null);
        this.activityIcon = stateIn7;
        this.voiceNoServiceIcon = FlowKt.stateIn(mobileIconInteractorImpl.voiceNoServiceIcon, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        this.anyChanges = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(stateFlow, readonlyStateFlow, readonlyStateFlow2, stateFlow2, new CellularIconViewModel$anyChanges$1(this, null)), new CellularIconViewModel$anyChanges$2(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), mobileSimpleLogger);
        this.updateDeXStatusBarIconModel = FlowKt.stateIn(FlowKt.combine(stateIn, stateIn2, stateIn4, stateIn7, stateIn6, new CellularIconViewModel$updateDeXStatusBarIconModel$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), DeXStatusBarIconModelKt.DEFAULT_DEX_STATUS_BAR_ICON_MODEL);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        CellularIconViewModel$dexStatusBarIcon$1 cellularIconViewModel$dexStatusBarIcon$1 = new CellularIconViewModel$dexStatusBarIcon$1(this, null);
        conflatedCallbackFlow.getClass();
        this.dexStatusBarIcon = ConflatedCallbackFlow.conflatedCallbackFlow(cellularIconViewModel$dexStatusBarIcon$1);
    }

    public static final void access$sendDeXStatusBarIconModel(CellularIconViewModel cellularIconViewModel, DeXStatusBarIconModel deXStatusBarIconModel) {
        cellularIconViewModel.getClass();
        boolean z = deXStatusBarIconModel != null ? deXStatusBarIconModel.isVisible : false;
        int i = deXStatusBarIconModel != null ? deXStatusBarIconModel.slotId : -1;
        int i2 = deXStatusBarIconModel != null ? deXStatusBarIconModel.subId : -1;
        int i3 = deXStatusBarIconModel != null ? deXStatusBarIconModel.strengthId : 0;
        int i4 = deXStatusBarIconModel != null ? deXStatusBarIconModel.netwotkTypeId : 0;
        boolean z2 = deXStatusBarIconModel != null ? deXStatusBarIconModel.isVisible : false;
        int i5 = deXStatusBarIconModel != null ? deXStatusBarIconModel.activityId : 0;
        int i6 = deXStatusBarIconModel != null ? deXStatusBarIconModel.roamingId : 0;
        DesktopManagerImpl desktopManagerImpl = (DesktopManagerImpl) cellularIconViewModel.desktopManager;
        if (desktopManagerImpl.isDesktopMode()) {
            StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("setMobileIcon - visible:", z, ",subId:", i2, ",stengthId:");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, i3, ",typeId:", i4, ",showTriangle:");
            m66m.append(z2);
            m66m.append(",activityId:");
            m66m.append(i5);
            m66m.append(",roamingId:");
            m66m.append(i6);
            desktopManagerImpl.mIndicatorLogger.log(m66m.toString());
            Bundle bundle = new Bundle();
            bundle.putBoolean("visible", z);
            bundle.putInt("slotId", i);
            bundle.putInt("subId", i2);
            bundle.putInt("strengthId", i3);
            bundle.putInt("typeId", i4);
            bundle.putBoolean("showTriangle", z2);
            bundle.putInt("activityId", i5);
            bundle.putInt("roamingId", i6);
            ((DesktopSystemUiBinder) desktopManagerImpl.mDesktopSystemUiBinderLazy.get()).setMobileIcon(bundle);
        }
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityContainerVisible() {
        return this.activityContainerVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityIcon() {
        return this.activityIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityInVisible() {
        return this.activityInVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getActivityOutVisible() {
        return this.activityOutVisible;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getAnyChanges() {
        return this.anyChanges;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getContentDescription() {
        return this.contentDescription;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getDexStatusBarIcon() {
        return this.dexStatusBarIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getNetworkTypeIcon() {
        return this.networkTypeIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoaming() {
        return this.roaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final Flow getRoamingIcon() {
        return this.roamingIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i) {
        if (i == 0) {
            return null;
        }
        Context context = view.getContext();
        Drawable drawable = context.getResources().getDrawable(i, null);
        int height = view.getHeight();
        return new DoubleShadowStatusBarIconDrawable(drawable, context, MathKt__MathJVMKt.roundToInt((drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight()) * height), height);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final int getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getUpdateDeXStatusBarIconModel() {
        return this.updateDeXStatusBarIconModel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow getVoiceNoServiceIcon() {
        return this.voiceNoServiceIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconViewModelCommon
    public final StateFlow isVisible() {
        return this.isVisible;
    }
}
