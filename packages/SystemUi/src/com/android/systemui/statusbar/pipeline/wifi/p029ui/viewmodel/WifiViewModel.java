package com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel;

import android.content.Context;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.airplane.p025ui.viewmodel.AirplaneModeViewModel;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$3;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.model.DeXStatusBarWifiIconModelKt;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.model.WifiIcon;
import com.android.systemui.statusbar.pipeline.wifi.p029ui.util.SamsungWifiIcons;
import com.android.systemui.statusbar.pipeline.wifi.shared.WifiConstants;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.wifi.SemWifiManager;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiViewModel implements WifiViewModelCommon {
    public static final int NO_INTERNET;
    public final Flow DeXWifiIcon;
    public final ReadonlyStateFlow activity;
    public final ReadonlyStateFlow activityIcon;
    public final Context context;
    public final ReadonlyStateFlow isActivityInViewVisible;
    public final ReadonlyStateFlow isActivityOutViewVisible;
    public final ReadonlyStateFlow updateDeXWifiIconModel;
    public final ReadonlyStateFlow wifiIcon;

    static {
        new Companion(null);
        NO_INTERNET = R.string.data_connection_no_internet;
    }

    public WifiViewModel(AirplaneModeViewModel airplaneModeViewModel, Supplier<Flow> supplier, final ConnectivityConstants connectivityConstants, Context context, TableLogBuffer tableLogBuffer, WifiInteractor wifiInteractor, CoroutineScope coroutineScope, final WifiConstants wifiConstants, DesktopManager desktopManager, final SemWifiManager semWifiManager) {
        this.context = context;
        WifiInteractorImpl wifiInteractorImpl = (WifiInteractorImpl) wifiInteractor;
        StateFlow stateFlow = wifiInteractorImpl.isEnabled;
        StateFlow stateFlow2 = wifiInteractorImpl.isDefault;
        WifiInteractorImpl$special$$inlined$map$3 wifiInteractorImpl$special$$inlined$map$3 = wifiInteractorImpl.isForceHidden;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = wifiInteractorImpl.wifiNetwork;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = wifiInteractorImpl.wifiIconGroup;
        final Flow[] flowArr = {stateFlow, stateFlow2, wifiInteractorImpl$special$$inlined$map$3, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, wifiInteractorImpl.hideDuringMobileSwitching, wifiInteractorImpl.wifiConnectivityTestReported};
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$combine$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$combine$1$3", m277f = "WifiViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeFavoriteApp}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$combine$1$3 */
            public final class C33673 extends SuspendLambda implements Function3 {
                final /* synthetic */ ConnectivityConstants $connectivityConstants$inlined;
                final /* synthetic */ SemWifiManager $semWifiManager$inlined;
                final /* synthetic */ WifiConstants $wifiConstants$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ WifiViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C33673(Continuation continuation, SemWifiManager semWifiManager, WifiViewModel wifiViewModel, WifiConstants wifiConstants, ConnectivityConstants connectivityConstants) {
                    super(3, continuation);
                    this.$semWifiManager$inlined = semWifiManager;
                    this.this$0 = wifiViewModel;
                    this.$wifiConstants$inlined = wifiConstants;
                    this.$connectivityConstants$inlined = connectivityConstants;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C33673 c33673 = new C33673((Continuation) obj3, this.$semWifiManager$inlined, this.this$0, this.$wifiConstants$inlined, this.$connectivityConstants$inlined);
                    c33673.L$0 = (FlowCollector) obj;
                    c33673.L$1 = (Object[]) obj2;
                    return c33673.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object obj2;
                    boolean z;
                    char c;
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
                        if (!booleanValue || booleanValue3 || ((z = wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged)) || booleanValue4) {
                            obj2 = WifiIcon.Hidden.INSTANCE;
                        } else {
                            boolean z2 = (!booleanValue5 && (wifiNetworkModel instanceof WifiNetworkModel.Active) && ((WifiNetworkModel.Active) wifiNetworkModel).isValidated && this.$semWifiManager$inlined.getWcmEverQualityTested() == 1) ? true : booleanValue5;
                            boolean z3 = BasicRune.STATUS_NETWORK_WIFI_FLASHING;
                            if (z3 && (wifiNetworkModel instanceof WifiNetworkModel.Active) && !z2) {
                                obj2 = new WifiIcon.Visible(R.drawable.stat_sys_wifi_signal_flashing, new ContentDescription.Loaded(null));
                            } else if (signalIcon$IconGroup != null) {
                                WifiViewModel wifiViewModel = this.this$0;
                                int i2 = WifiViewModel.NO_INTERNET;
                                wifiViewModel.getClass();
                                if (wifiNetworkModel instanceof WifiNetworkModel.Unavailable) {
                                    obj2 = WifiIcon.Hidden.INSTANCE;
                                } else if (wifiNetworkModel instanceof WifiNetworkModel.Invalid) {
                                    obj2 = WifiIcon.Hidden.INSTANCE;
                                } else if (z) {
                                    obj2 = WifiIcon.Hidden.INSTANCE;
                                } else {
                                    boolean z4 = wifiNetworkModel instanceof WifiNetworkModel.Inactive;
                                    int i3 = WifiViewModel.NO_INTERNET;
                                    Context context = wifiViewModel.context;
                                    if (z4) {
                                        obj2 = new WifiIcon.Visible(android.R.drawable.ic_voice_search_api_holo_light, new ContentDescription.Loaded(AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(context.getString(R.string.accessibility_no_wifi), ",", context.getString(i3))));
                                    } else {
                                        if (!(wifiNetworkModel instanceof WifiNetworkModel.Active)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        WifiNetworkModel.Active active = (WifiNetworkModel.Active) wifiNetworkModel;
                                        int[] iArr = signalIcon$IconGroup.contentDesc;
                                        int i4 = active.level;
                                        String string = context.getString(iArr[i4]);
                                        boolean equals = Integer.valueOf(active.receivedInetCondition).equals(-1);
                                        int[][] iArr2 = signalIcon$IconGroup.sbIcons;
                                        if (equals && active.isValidated) {
                                            c = 1;
                                        } else if (Integer.valueOf(active.receivedInetCondition).equals(1)) {
                                            c = 1;
                                        } else {
                                            obj2 = new WifiIcon.Visible(iArr2[0][i4], new ContentDescription.Loaded(AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(string, ",", context.getString(i3))));
                                        }
                                        obj2 = new WifiIcon.Visible(iArr2[c][i4], new ContentDescription.Loaded(string));
                                    }
                                }
                            } else {
                                obj2 = WifiIcon.Hidden.INSTANCE;
                            }
                            if ((!z3 || z2 || !(wifiNetworkModel instanceof WifiNetworkModel.Active)) && !booleanValue2 && !this.$wifiConstants$inlined.alwaysShowIconIfEnabled && this.$connectivityConstants$inlined.hasDataCapabilities && !(wifiNetworkModel instanceof WifiNetworkModel.Active)) {
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
                }, new C33673(null, semWifiManager, this, wifiConstants, connectivityConstants), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        WifiIcon.Hidden hidden = WifiIcon.Hidden.INSTANCE;
        SafeFlow logDiffsForTable = DiffableKt.logDiffsForTable(flow, tableLogBuffer, "", hidden);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), hidden);
        this.wifiIcon = stateIn;
        DataActivityModel dataActivityModel = new DataActivityModel(false, false);
        final ReadonlyStateFlow stateIn2 = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(wifiInteractorImpl.activity, wifiInteractorImpl.ssid, new WifiViewModel$activity$1$1(dataActivityModel, null))), tableLogBuffer, "VM.activity", dataActivityModel), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), dataActivityModel);
        this.activity = stateIn2;
        ReadonlyStateFlow stateIn3 = FlowKt.stateIn(FlowKt.distinctUntilChanged(FlowKt.combine(stateIn2, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, new WifiViewModel$activityIcon$1(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), new Icon.Resource(SamsungWifiIcons.WIFI_ACTIVITY[0], null));
        this.activityIcon = stateIn3;
        Flow flow2 = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1$2 */
            public final class C33682 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$1$2", m277f = "WifiViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33682.this.emit(null, this);
                    }
                }

                public C33682(FlowCollector flowCollector) {
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
                                Boolean valueOf = Boolean.valueOf(((DataActivityModel) obj).hasActivityIn);
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
                Object collect = Flow.this.collect(new C33682(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn4 = FlowKt.stateIn(flow2, coroutineScope, WhileSubscribed$default, bool);
        this.isActivityInViewVisible = stateIn4;
        ReadonlyStateFlow stateIn5 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2$2 */
            public final class C33692 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$special$$inlined$map$2$2", m277f = "WifiViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33692.this.emit(null, this);
                    }
                }

                public C33692(FlowCollector flowCollector) {
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
                                Boolean valueOf = Boolean.valueOf(((DataActivityModel) obj).hasActivityOut);
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
                Object collect = Flow.this.collect(new C33692(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        this.isActivityOutViewVisible = stateIn5;
        FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn4, stateIn5, new WifiViewModel$isActivityContainerVisible$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        supplier.get();
        this.updateDeXWifiIconModel = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, stateIn3, new WifiViewModel$updateDeXWifiIconModel$1(desktopManager, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), DeXStatusBarWifiIconModelKt.DEFAULT_DEX_STATUS_BAR_WIFI_ICON_MODEL);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        WifiViewModel$DeXWifiIcon$1 wifiViewModel$DeXWifiIcon$1 = new WifiViewModel$DeXWifiIcon$1(desktopManager, this, null);
        conflatedCallbackFlow.getClass();
        this.DeXWifiIcon = ConflatedCallbackFlow.conflatedCallbackFlow(wifiViewModel$DeXWifiIcon$1);
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.WifiViewModelCommon
    public final StateFlow getActivityIcon() {
        return this.activityIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.WifiViewModelCommon
    public final Flow getDeXWifiIcon() {
        return this.DeXWifiIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.WifiViewModelCommon
    public final StateFlow getUpdateDeXWifiIconModel() {
        return this.updateDeXWifiIconModel;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.p029ui.viewmodel.WifiViewModelCommon
    public final StateFlow getWifiIcon() {
        return this.wifiIcon;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getNO_INTERNET$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m214x271d1af() {
        }
    }
}
