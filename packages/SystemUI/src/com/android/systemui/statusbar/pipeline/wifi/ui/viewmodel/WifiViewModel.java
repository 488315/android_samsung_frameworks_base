package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import android.content.Context;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.BasicRune;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.airplane.ui.viewmodel.AirplaneModeViewModel;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3;
import com.android.systemui.statusbar.pipeline.wifi.shared.WifiConstants;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.DeXStatusBarWifiIconModelKt;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.WifiIcon;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.SamsungWifiIcons;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.samsung.android.wifi.SemWifiManager;
import java.util.function.Supplier;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WifiViewModel implements WifiViewModelCommon {
    public final Flow DeXWifiIcon;
    public final ReadonlyStateFlow activity;
    public final ReadonlyStateFlow activityIcon;
    public final Context context;
    public final ReadonlyStateFlow updateDeXWifiIconModel;
    public final ReadonlyStateFlow wifiIcon;

    public WifiViewModel(AirplaneModeViewModel airplaneModeViewModel, Supplier<Flow> supplier, final ConnectivityConstants connectivityConstants, Context context, TableLogBuffer tableLogBuffer, WifiInteractor wifiInteractor, CoroutineScope coroutineScope, final WifiConstants wifiConstants, DesktopManager desktopManager, final SemWifiManager semWifiManager) {
        this.context = context;
        WifiInteractorImpl wifiInteractorImpl = (WifiInteractorImpl) wifiInteractor;
        StateFlow stateFlow = wifiInteractorImpl.isEnabled;
        StateFlow stateFlow2 = wifiInteractorImpl.hideDuringMobileSwitching;
        StateFlow stateFlow3 = wifiInteractorImpl.wifiConnectivityTestReported;
        StateFlow stateFlow4 = wifiInteractorImpl.isDefault;
        WifiInteractorImpl$special$$inlined$map$3 wifiInteractorImpl$special$$inlined$map$3 = wifiInteractorImpl.isForceHidden;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = wifiInteractorImpl.wifiNetwork;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = wifiInteractorImpl.wifiIconGroup;
        final Flow[] flowArr = {stateFlow, stateFlow4, wifiInteractorImpl$special$$inlined$map$3, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, stateFlow2, stateFlow3};
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$combine$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ ConnectivityConstants $connectivityConstants$inlined;
                final /* synthetic */ SemWifiManager $semWifiManager$inlined;
                final /* synthetic */ WifiConstants $wifiConstants$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ WifiViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, SemWifiManager semWifiManager, WifiViewModel wifiViewModel, WifiConstants wifiConstants, ConnectivityConstants connectivityConstants) {
                    super(3, continuation);
                    this.$semWifiManager$inlined = semWifiManager;
                    this.this$0 = wifiViewModel;
                    this.$wifiConstants$inlined = wifiConstants;
                    this.$connectivityConstants$inlined = connectivityConstants;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$semWifiManager$inlined, this.this$0, this.$wifiConstants$inlined, this.$connectivityConstants$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object obj2;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
                        boolean booleanValue2 = ((Boolean) objArr[1]).booleanValue();
                        boolean booleanValue3 = ((Boolean) objArr[2]).booleanValue();
                        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) objArr[3];
                        SignalIcon$IconGroup signalIcon$IconGroup = (SignalIcon$IconGroup) objArr[4];
                        boolean booleanValue4 = ((Boolean) objArr[5]).booleanValue();
                        boolean booleanValue5 = ((Boolean) objArr[6]).booleanValue();
                        if (!booleanValue || booleanValue3 || (wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) || booleanValue4) {
                            obj2 = WifiIcon.Hidden.INSTANCE;
                        } else {
                            boolean z = BasicRune.STATUS_NETWORK_WIFI_FLASHING;
                            if (z && !booleanValue5 && (wifiNetworkModel instanceof WifiNetworkModel.Active) && ((WifiNetworkModel.Active) wifiNetworkModel).isValidated && this.$semWifiManager$inlined.getWcmEverQualityTested() == 1) {
                                booleanValue5 = true;
                            }
                            if (z && (wifiNetworkModel instanceof WifiNetworkModel.Active) && !booleanValue5) {
                                obj2 = new WifiIcon.Visible(R.drawable.stat_sys_wifi_signal_flashing, new ContentDescription.Loaded(null));
                            } else if (signalIcon$IconGroup != null) {
                                WifiIcon.Companion companion = WifiIcon.Companion;
                                Context context = this.this$0.context;
                                companion.getClass();
                                obj2 = WifiIcon.Companion.fromModel(wifiNetworkModel, context, false, signalIcon$IconGroup);
                            } else {
                                obj2 = WifiIcon.Hidden.INSTANCE;
                            }
                            if ((!z || booleanValue5 || !(wifiNetworkModel instanceof WifiNetworkModel.Active)) && !booleanValue2 && !this.$wifiConstants$inlined.alwaysShowIconIfEnabled && this.$connectivityConstants$inlined.hasDataCapabilities && !(wifiNetworkModel instanceof WifiNetworkModel.Active)) {
                                obj2 = WifiIcon.Hidden.INSTANCE;
                            }
                        }
                        this.label = 1;
                        if (flowCollector.emit(obj2, this) == coroutineSingletons) {
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
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, semWifiManager, this, wifiConstants, connectivityConstants), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        WifiIcon.Hidden hidden = WifiIcon.Hidden.INSTANCE;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable(flow, tableLogBuffer, "", hidden);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), hidden);
        this.wifiIcon = stateIn;
        new DataActivityModel(false, false);
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(wifiInteractorImpl.activity, wifiInteractorImpl.ssid, new WifiViewModel$activity$1$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(stateIn2, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, new WifiViewModel$activityIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new Icon.Resource(SamsungWifiIcons.WIFI_ACTIVITY[0], null));
        this.activityIcon = stateIn3;
        Flow flow2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel r5 = (com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel) r5
                        if (r5 == 0) goto L39
                        boolean r5 = r5.hasActivityIn
                        goto L3a
                    L39:
                        r5 = 0
                    L3a:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        FlowKt.stateIn(flow2, coroutineScope, WhileSubscribed$default, bool);
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel r5 = (com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel) r5
                        if (r5 == 0) goto L39
                        boolean r5 = r5.hasActivityOut
                        goto L3a
                    L39:
                        r5 = 0
                    L3a:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        Flags.FEATURE_FLAGS.getClass();
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3$2$1
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
                        com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel r5 = (com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel) r5
                        if (r5 == 0) goto L40
                        boolean r6 = r5.hasActivityIn
                        if (r6 != 0) goto L3e
                        boolean r5 = r5.hasActivityOut
                        if (r5 == 0) goto L40
                    L3e:
                        r5 = r3
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        supplier.get();
        this.updateDeXWifiIconModel = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, stateIn3, new WifiViewModel$updateDeXWifiIconModel$1(desktopManager, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), DeXStatusBarWifiIconModelKt.DEFAULT_DEX_STATUS_BAR_WIFI_ICON_MODEL);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        WifiViewModel$DeXWifiIcon$1 wifiViewModel$DeXWifiIcon$1 = new WifiViewModel$DeXWifiIcon$1(desktopManager, this, null);
        conflatedCallbackFlow.getClass();
        this.DeXWifiIcon = FlowConflatedKt.conflatedCallbackFlow(wifiViewModel$DeXWifiIcon$1);
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModelCommon
    public final StateFlow getActivityIcon() {
        return this.activityIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModelCommon
    public final Flow getDeXWifiIcon() {
        return this.DeXWifiIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModelCommon
    public final StateFlow getUpdateDeXWifiIconModel() {
        return this.updateDeXWifiIconModel;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModelCommon
    public final StateFlow getWifiIcon() {
        return this.wifiIcon;
    }
}
