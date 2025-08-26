package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import android.content.Context;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.TaskbarIndicatorController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.airplane.ui.viewmodel.AirplaneModeViewModel;
import com.android.systemui.statusbar.pipeline.airplane.ui.viewmodel.AirplaneModeViewModelImpl;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstantsImpl;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3;
import com.android.systemui.statusbar.pipeline.wifi.shared.WifiConstants;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.DeXStatusBarWifiIconModelKt;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.WifiIcon;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.SamsungWifiIcons;
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

/* loaded from: classes3.dex */
public final class WifiViewModel implements WifiViewModelCommon {
    public final Flow DeXWifiIcon;
    public final ReadonlyStateFlow activity;
    public final ReadonlyStateFlow activityIcon;
    public final Context context;
    public final ReadonlyStateFlow updateDeXWifiIconModel;
    public final ReadonlyStateFlow wifiIcon;

    public WifiViewModel(AirplaneModeViewModel airplaneModeViewModel, Supplier<Flow> supplier, final ConnectivityConstants connectivityConstants, Context context, TableLogBuffer tableLogBuffer, WifiInteractor wifiInteractor, CoroutineScope coroutineScope, final WifiConstants wifiConstants, TaskbarIndicatorController taskbarIndicatorController, final SemWifiManager semWifiManager) {
        this.context = context;
        WifiInteractorImpl wifiInteractorImpl = (WifiInteractorImpl) wifiInteractor;
        StateFlow stateFlow = wifiInteractorImpl.isEnabled;
        StateFlow stateFlow2 = wifiInteractorImpl.isDefault;
        StateFlow stateFlow3 = wifiInteractorImpl.hideDuringMobileSwitching;
        StateFlow stateFlow4 = wifiInteractorImpl.wifiConnectivityTestReported;
        WifiInteractorImpl$special$$inlined$map$3 wifiInteractorImpl$special$$inlined$map$3 = wifiInteractorImpl.isForceHidden;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = wifiInteractorImpl.wifiNetwork;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = wifiInteractorImpl.wifiIconGroup;
        final Flow[] flowArr = {stateFlow, stateFlow2, wifiInteractorImpl$special$$inlined$map$3, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, stateFlow3, stateFlow4};
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$combine$1

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
                    Object objFromModel;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean zBooleanValue = ((Boolean) objArr[0]).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) objArr[1]).booleanValue();
                        boolean zBooleanValue3 = ((Boolean) objArr[2]).booleanValue();
                        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) objArr[3];
                        SignalIcon$IconGroup signalIcon$IconGroup = (SignalIcon$IconGroup) objArr[4];
                        boolean zBooleanValue4 = ((Boolean) objArr[5]).booleanValue();
                        boolean zBooleanValue5 = ((Boolean) objArr[6]).booleanValue();
                        if (!zBooleanValue || zBooleanValue3 || (wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) || zBooleanValue4) {
                            objFromModel = WifiIcon.Hidden.INSTANCE;
                        } else {
                            boolean z = BasicRune.STATUS_NETWORK_WIFI_FLASHING;
                            if (z && !zBooleanValue5 && (wifiNetworkModel instanceof WifiNetworkModel.Active) && ((WifiNetworkModel.Active) wifiNetworkModel).isValidated && this.$semWifiManager$inlined.getWcmEverQualityTested() == 1) {
                                zBooleanValue5 = true;
                            }
                            if (z && (wifiNetworkModel instanceof WifiNetworkModel.Active) && !zBooleanValue5) {
                                objFromModel = new WifiIcon.Visible(R.drawable.stat_sys_wifi_signal_flashing, new ContentDescription.Loaded(null));
                            } else if (signalIcon$IconGroup != null) {
                                WifiIcon.Companion companion = WifiIcon.Companion;
                                Context context = this.this$0.context;
                                companion.getClass();
                                objFromModel = WifiIcon.Companion.fromModel(wifiNetworkModel, context, false, signalIcon$IconGroup);
                            } else {
                                objFromModel = WifiIcon.Hidden.INSTANCE;
                            }
                            if ((!z || zBooleanValue5 || !(wifiNetworkModel instanceof WifiNetworkModel.Active)) && !zBooleanValue2 && !this.$wifiConstants$inlined.alwaysShowIconIfEnabled && ((ConnectivityConstantsImpl) this.$connectivityConstants$inlined).hasDataCapabilities && !(wifiNetworkModel instanceof WifiNetworkModel.Active)) {
                                objFromModel = WifiIcon.Hidden.INSTANCE;
                            }
                        }
                        this.label = 1;
                        if (flowCollector.emit(objFromModel, this) == coroutineSingletons) {
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
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, semWifiManager, this, wifiConstants, connectivityConstants), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
        WifiIcon.Hidden hidden = WifiIcon.Hidden.INSTANCE;
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable(flow, tableLogBuffer, "", hidden);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), hidden);
        this.wifiIcon = readonlyStateFlowStateIn;
        new DataActivityModel(false, false);
        connectivityConstants.getClass();
        final ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(wifiInteractorImpl.activity, wifiInteractorImpl.ssid, new WifiViewModel$activity$1$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.activity = readonlyStateFlowStateIn2;
        ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlowStateIn2, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, new WifiViewModel$activityIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new Icon.Resource(SamsungWifiIcons.WIFI_ACTIVITY[0], null));
        this.activityIcon = readonlyStateFlowStateIn3;
        Flow flow2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        DataActivityModel dataActivityModel = (DataActivityModel) obj;
                        Boolean boolValueOf = Boolean.valueOf(dataActivityModel != null ? dataActivityModel.hasActivityIn : false);
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
                Object objCollect = readonlyStateFlowStateIn2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        FlowKt.stateIn(flow2, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        DataActivityModel dataActivityModel = (DataActivityModel) obj;
                        Boolean boolValueOf = Boolean.valueOf(dataActivityModel != null ? dataActivityModel.hasActivityOut : false);
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
                Object objCollect = readonlyStateFlowStateIn2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        DataActivityModel dataActivityModel = (DataActivityModel) obj;
                        Boolean boolValueOf = Boolean.valueOf(dataActivityModel != null && (dataActivityModel.hasActivityIn || dataActivityModel.hasActivityOut));
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
                Object objCollect = readonlyStateFlowStateIn2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        ReadonlyStateFlow readonlyStateFlow = ((AirplaneModeViewModelImpl) airplaneModeViewModel).isAirplaneModeIconVisible;
        supplier.get();
        this.updateDeXWifiIconModel = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn, readonlyStateFlowStateIn3, new WifiViewModel$updateDeXWifiIconModel$1(taskbarIndicatorController, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), DeXStatusBarWifiIconModelKt.DEFAULT_DEX_STATUS_BAR_WIFI_ICON_MODEL);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        WifiViewModel$DeXWifiIcon$1 wifiViewModel$DeXWifiIcon$1 = new WifiViewModel$DeXWifiIcon$1(taskbarIndicatorController, this, null);
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
