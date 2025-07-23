package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Parcel;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.TaskbarIndicatorController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener$Stub$Proxy;
import com.android.systemui.statusbar.phone.DoubleShadowStatusBarIconDrawable;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DeXStatusBarIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DeXStatusBarIconModelKt;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstantsImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CellularIconViewModel implements MobileIconViewModelCommon {
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 activityContainerVisible;
    public final ReadonlyStateFlow activityIcon;
    public final ReadonlyStateFlow activityInVisible;
    public final ReadonlyStateFlow activityOutVisible;
    public final ReadonlyStateFlow anyChanges;
    public final ReadonlyStateFlow contentDescription;
    public boolean dataServiceAcquired;
    public final Flow dexStatusBarIcon;
    public final Flow icon;
    public final ReadonlyStateFlow isVisible;
    public final ReadonlyStateFlow networkTypeBackground;
    public final ReadonlyStateFlow networkTypeIcon;
    public final ReadonlyStateFlow roaming;
    public final ReadonlyStateFlow roamingIcon;
    public final ReadonlyStateFlow showNetworkTypeIcon;
    public final MobileSimpleLogger simpleLogger;
    public final int slotId;
    public final int subscriptionId;
    public final TaskbarIndicatorController taskbarIndicatorController;
    public final ReadonlyStateFlow updateDeXStatusBarIconModel;
    public final ReadonlyStateFlow voiceNoServiceIcon;
    public boolean voiceServiceAcquired;

    public CellularIconViewModel(int i, MobileIconInteractor mobileIconInteractor, AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, CoroutineScope coroutineScope, TaskbarIndicatorController taskbarIndicatorController, String str) {
        this.subscriptionId = i;
        this.taskbarIndicatorController = taskbarIndicatorController;
        int slotIndex = SubscriptionManager.getSlotIndex(i) == -1 ? 0 : SubscriptionManager.getSlotIndex(i);
        this.slotId = slotIndex;
        MobileSimpleLogger mobileSimpleLogger = new MobileSimpleLogger(slotIndex, i, str, false, false, new NetworkTypeIconModel.DefaultIcon(TelephonyIcons.G), 0, 0, false, "DEFAULT");
        this.simpleLogger = mobileSimpleLogger;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(!((ConnectivityConstantsImpl) connectivityConstants).hasDataCapabilities ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : FlowKt.combine(airplaneModeInteractor.isAirplaneMode, mobileIconInteractor.isAllowedDuringAirplaneMode(), mobileIconInteractor.isForceHidden(), mobileIconInteractor.isSimOn(), mobileIconInteractor.isSim1On(), new CellularIconViewModel$isVisible$1(this, null))), mobileIconInteractor.getTableLogBuffer(), "", "visible", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, WhileSubscribed$default, bool);
        this.isVisible = stateIn;
        Flow signalLevelIcon = mobileIconInteractor.getSignalLevelIcon();
        this.icon = signalLevelIcon;
        this.contentDescription = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileIconInteractor.getSignalLevelIcon(), mobileIconInteractor.getNetworkName(), new CellularIconViewModel$contentDescription$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        final Flow[] flowArr = {mobileIconInteractor.isDataConnected(), mobileIconInteractor.isDataEnabled(), mobileIconInteractor.getAlwaysShowDataRatIcon(), mobileIconInteractor.getMobileIsDefault(), mobileIconInteractor.getCarrierNetworkChangeActive(), mobileIconInteractor.isVoWifiConnected()};
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$combine$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                public AnonymousClass3(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
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
                        Boolean[] boolArr = (Boolean[]) ((Object[]) this.L$1);
                        boolean z = false;
                        boolean booleanValue = boolArr[0].booleanValue();
                        boolean booleanValue2 = boolArr[1].booleanValue();
                        boolean booleanValue3 = boolArr[2].booleanValue();
                        boolean booleanValue4 = boolArr[3].booleanValue();
                        boolean booleanValue5 = boolArr[4].booleanValue();
                        boolean booleanValue6 = boolArr[5].booleanValue();
                        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("showNetworkTypeIcon - dataConnected: ", ", dataEnabled: ", ", mobileIsDefault: ", booleanValue, booleanValue2);
                        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, booleanValue4, ", isVoWifiConnected: ", booleanValue6, ", carrierNetworkChange: ");
                        ActionBarContextView$$ExternalSyntheticOutline0.m(m, booleanValue5, "MobileIconViewModel");
                        if (booleanValue3 || (!booleanValue5 && booleanValue && booleanValue4 && !booleanValue6)) {
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
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Boolean[flowArr2.length];
                    }
                }, new AnonymousClass3(null), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }), mobileIconInteractor.getTableLogBuffer(), "", "showNetworkTypeIcon", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.showNetworkTypeIcon = stateIn2;
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(mobileIconInteractor.getNetworkTypeIconGroup(), stateIn2, mobileIconInteractor.getDisabledDataIcon(), new CellularIconViewModel$networkTypeIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.networkTypeIcon = stateIn3;
        final Flow showSliceAttribution = mobileIconInteractor.getShowSliceAttribution();
        this.networkTypeBackground = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        r6 = 0
                        if (r5 == 0) goto L44
                        com.android.systemui.common.shared.model.Icon$Resource r5 = new com.android.systemui.common.shared.model.Icon$Resource
                        r2 = 2131234151(0x7f080d67, float:1.808446E38)
                        r5.<init>(r2, r6)
                        r6 = r5
                    L44:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        ReadonlyStateFlow stateIn4 = FlowKt.stateIn(DiffableKt.logDiffsForTable(mobileIconInteractor.isRoaming(), mobileIconInteractor.getTableLogBuffer(), "", "roaming", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.roaming = stateIn4;
        ReadonlyStateFlow stateIn5 = FlowKt.stateIn(FlowKt.combine(stateIn4, mobileIconInteractor.getRoamingId(), mobileIconInteractor.getDisabledDataIcon(), mobileIconInteractor.getOtherSlotInCallState(), new CellularIconViewModel$roamingIcon$1(null)), coroutineScope, SharingStarted.Companion.Eagerly, null);
        this.roamingIcon = stateIn5;
        final Flow activity = mobileIconInteractor.getActivity();
        this.activityInVisible = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel r5 = (com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel) r5
                        java.lang.Boolean r5 = java.lang.Boolean.FALSE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.activityOutVisible = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel r5 = (com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel) r5
                        java.lang.Boolean r5 = java.lang.Boolean.FALSE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.activityContainerVisible = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        ReadonlyStateFlow stateIn6 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(activity, mobileIconInteractor.getNetworkTypeIconGroup(), stateIn2, mobileIconInteractor.getDisabledActivityIcon(), new CellularIconViewModel$activityIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.activityIcon = stateIn6;
        this.voiceNoServiceIcon = FlowKt.stateIn(mobileIconInteractor.getVoiceNoServiceIcon(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.anyChanges = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(mobileIconInteractor.isInService(), mobileIconInteractor.isDataConnected(), mobileIconInteractor.getNetworkTypeIconGroup(), mobileIconInteractor.getMobileServiceState(), new CellularIconViewModel$anyChanges$1(this, null)), new CellularIconViewModel$anyChanges$2(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileSimpleLogger);
        this.updateDeXStatusBarIconModel = FlowKt.stateIn(FlowKt.combine(stateIn, signalLevelIcon, stateIn3, stateIn6, stateIn5, new CellularIconViewModel$updateDeXStatusBarIconModel$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), DeXStatusBarIconModelKt.DEFAULT_DEX_STATUS_BAR_ICON_MODEL);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        CellularIconViewModel$dexStatusBarIcon$1 cellularIconViewModel$dexStatusBarIcon$1 = new CellularIconViewModel$dexStatusBarIcon$1(this, null);
        conflatedCallbackFlow.getClass();
        this.dexStatusBarIcon = FlowConflatedKt.conflatedCallbackFlow(cellularIconViewModel$dexStatusBarIcon$1);
    }

    public static final void access$sendDeXStatusBarIconModel(CellularIconViewModel cellularIconViewModel, DeXStatusBarIconModel deXStatusBarIconModel) {
        cellularIconViewModel.getClass();
        Bundle bundle = new Bundle();
        bundle.putBoolean("visible", deXStatusBarIconModel != null ? deXStatusBarIconModel.isVisible : false);
        bundle.putInt("slotId", deXStatusBarIconModel != null ? deXStatusBarIconModel.slotId : -1);
        bundle.putInt("subId", deXStatusBarIconModel != null ? deXStatusBarIconModel.subId : -1);
        bundle.putInt("strengthId", deXStatusBarIconModel != null ? deXStatusBarIconModel.strengthId : 0);
        bundle.putInt("typeId", deXStatusBarIconModel != null ? deXStatusBarIconModel.netwotkTypeId : 0);
        bundle.putBoolean("showTriangle", deXStatusBarIconModel != null ? deXStatusBarIconModel.isVisible : false);
        bundle.putInt("activityId", deXStatusBarIconModel != null ? deXStatusBarIconModel.activityId : 0);
        bundle.putInt("roamingId", deXStatusBarIconModel != null ? deXStatusBarIconModel.roamingId : 0);
        TaskbarIndicatorController taskbarIndicatorController = cellularIconViewModel.taskbarIndicatorController;
        taskbarIndicatorController.getClass();
        try {
            ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = taskbarIndicatorController.taskbarStatusIconListener;
            if (iTaskbarStatusIconListener$Stub$Proxy != null) {
                Parcel obtain = Parcel.obtain(iTaskbarStatusIconListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
                    obtain.writeTypedObject(bundle, 0);
                    iTaskbarStatusIconListener$Stub$Proxy.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        } catch (DeadObjectException unused) {
            Log.e(taskbarIndicatorController.TAG, "setMobileIcon taskbarStatusIconListener was dead, but non-null");
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
    public final StateFlow getNetworkTypeBackground() {
        return this.networkTypeBackground;
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
        int height = view.getHeight() != 0 ? view.getHeight() : drawable.getIntrinsicHeight();
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
