package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import android.content.Context;
import android.content.res.Resources;
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
import com.android.systemui.R;
import com.android.systemui.TaskbarIndicatorController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.Icon;
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
import kotlin.jvm.functions.Function6;
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

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$isVisible$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function6 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        /* synthetic */ boolean Z$2;
        /* synthetic */ boolean Z$3;
        /* synthetic */ boolean Z$4;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(6, continuation);
        }

        @Override // kotlin.jvm.functions.Function6
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
            boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
            boolean zBooleanValue5 = ((Boolean) obj5).booleanValue();
            AnonymousClass1 anonymousClass1 = CellularIconViewModel.this.new AnonymousClass1((Continuation) obj6);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.Z$1 = zBooleanValue2;
            anonymousClass1.Z$2 = zBooleanValue3;
            anonymousClass1.Z$3 = zBooleanValue4;
            anonymousClass1.Z$4 = zBooleanValue5;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x0016  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
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
            if (!z3) {
                if (!z) {
                    z2 = true;
                    if (CellularIconViewModel.this.slotId == 0) {
                        z4 = !z5 || z4;
                    }
                    if (!z4) {
                        z2 = false;
                    }
                }
            }
            return Boolean.valueOf(z2);
        }
    }

    public CellularIconViewModel(int i, MobileIconInteractor mobileIconInteractor, AirplaneModeInteractor airplaneModeInteractor, ConnectivityConstants connectivityConstants, CoroutineScope coroutineScope, TaskbarIndicatorController taskbarIndicatorController, String str) {
        this.subscriptionId = i;
        this.taskbarIndicatorController = taskbarIndicatorController;
        int slotIndex = SubscriptionManager.getSlotIndex(i) == -1 ? 0 : SubscriptionManager.getSlotIndex(i);
        this.slotId = slotIndex;
        MobileSimpleLogger mobileSimpleLogger = new MobileSimpleLogger(slotIndex, i, str, false, false, new NetworkTypeIconModel.DefaultIcon(TelephonyIcons.G), 0, 0, false, "DEFAULT");
        this.simpleLogger = mobileSimpleLogger;
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(!((ConnectivityConstantsImpl) connectivityConstants).hasDataCapabilities ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : FlowKt.combine(airplaneModeInteractor.isAirplaneMode, mobileIconInteractor.isAllowedDuringAirplaneMode(), mobileIconInteractor.isForceHidden(), mobileIconInteractor.isSimOn(), mobileIconInteractor.isSim1On(), new AnonymousClass1(null))), mobileIconInteractor.getTableLogBuffer(), "", "visible", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        this.isVisible = readonlyStateFlowStateIn;
        Flow signalLevelIcon = mobileIconInteractor.getSignalLevelIcon();
        this.icon = signalLevelIcon;
        this.contentDescription = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileIconInteractor.getSignalLevelIcon(), mobileIconInteractor.getNetworkName(), new CellularIconViewModel$contentDescription$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        final Flow[] flowArr = {mobileIconInteractor.isDataConnected(), mobileIconInteractor.isDataEnabled(), mobileIconInteractor.getAlwaysShowDataRatIcon(), mobileIconInteractor.getMobileIsDefault(), mobileIconInteractor.getCarrierNetworkChangeActive(), mobileIconInteractor.isVoWifiConnected()};
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$combine$1

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
                        boolean zBooleanValue = boolArr[0].booleanValue();
                        boolean zBooleanValue2 = boolArr[1].booleanValue();
                        boolean zBooleanValue3 = boolArr[2].booleanValue();
                        boolean zBooleanValue4 = boolArr[3].booleanValue();
                        boolean zBooleanValue5 = boolArr[4].booleanValue();
                        boolean zBooleanValue6 = boolArr[5].booleanValue();
                        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("showNetworkTypeIcon - dataConnected: ", ", dataEnabled: ", ", mobileIsDefault: ", zBooleanValue, zBooleanValue2);
                        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, zBooleanValue4, ", isVoWifiConnected: ", zBooleanValue6, ", carrierNetworkChange: ");
                        ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, zBooleanValue5, "MobileIconViewModel");
                        if (zBooleanValue3 || (!zBooleanValue5 && zBooleanValue && zBooleanValue4 && !zBooleanValue6)) {
                            z = true;
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
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Boolean[flowArr2.length];
                    }
                }, new AnonymousClass3(null), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        }), mobileIconInteractor.getTableLogBuffer(), "", "showNetworkTypeIcon", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.showNetworkTypeIcon = readonlyStateFlowStateIn2;
        ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(mobileIconInteractor.getNetworkTypeIconGroup(), readonlyStateFlowStateIn2, mobileIconInteractor.getDisabledDataIcon(), new CellularIconViewModel$networkTypeIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.networkTypeIcon = readonlyStateFlowStateIn3;
        final Flow showSliceAttribution = mobileIconInteractor.getShowSliceAttribution();
        this.networkTypeBackground = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$1

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
                        Icon.Resource resource = ((Boolean) obj).booleanValue() ? new Icon.Resource(R.drawable.mobile_network_type_background, null) : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(resource, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = showSliceAttribution.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        ReadonlyStateFlow readonlyStateFlowStateIn4 = FlowKt.stateIn(DiffableKt.logDiffsForTable(mobileIconInteractor.isRoaming(), mobileIconInteractor.getTableLogBuffer(), "", "roaming", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.roaming = readonlyStateFlowStateIn4;
        ReadonlyStateFlow readonlyStateFlowStateIn5 = FlowKt.stateIn(FlowKt.combine(readonlyStateFlowStateIn4, mobileIconInteractor.getRoamingId(), mobileIconInteractor.getDisabledDataIcon(), mobileIconInteractor.getOtherSlotInCallState(), new CellularIconViewModel$roamingIcon$1(null)), coroutineScope, SharingStarted.Companion.Eagerly, null);
        this.roamingIcon = readonlyStateFlowStateIn5;
        final Flow activity = mobileIconInteractor.getActivity();
        this.activityInVisible = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$2

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
                        Boolean bool = Boolean.FALSE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(bool, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = activity.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.activityOutVisible = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$special$$inlined$map$3

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
                        Boolean bool = Boolean.FALSE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(bool, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = activity.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.activityContainerVisible = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        ReadonlyStateFlow readonlyStateFlowStateIn6 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(activity, mobileIconInteractor.getNetworkTypeIconGroup(), readonlyStateFlowStateIn2, mobileIconInteractor.getDisabledActivityIcon(), new CellularIconViewModel$activityIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.activityIcon = readonlyStateFlowStateIn6;
        this.voiceNoServiceIcon = FlowKt.stateIn(mobileIconInteractor.getVoiceNoServiceIcon(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.anyChanges = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(mobileIconInteractor.isInService(), mobileIconInteractor.isDataConnected(), mobileIconInteractor.getNetworkTypeIconGroup(), mobileIconInteractor.getMobileServiceState(), new CellularIconViewModel$anyChanges$1(this, null)), new CellularIconViewModel$anyChanges$2(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), mobileSimpleLogger);
        this.updateDeXStatusBarIconModel = FlowKt.stateIn(FlowKt.combine(readonlyStateFlowStateIn, signalLevelIcon, readonlyStateFlowStateIn3, readonlyStateFlowStateIn6, readonlyStateFlowStateIn5, new CellularIconViewModel$updateDeXStatusBarIconModel$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), DeXStatusBarIconModelKt.DEFAULT_DEX_STATUS_BAR_ICON_MODEL);
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
                Parcel parcelObtain = Parcel.obtain(iTaskbarStatusIconListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
                    parcelObtain.writeTypedObject(bundle, 0);
                    iTaskbarStatusIconListener$Stub$Proxy.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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
    public final DoubleShadowStatusBarIconDrawable getShadowDrawable(View view, int i) throws Resources.NotFoundException {
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
