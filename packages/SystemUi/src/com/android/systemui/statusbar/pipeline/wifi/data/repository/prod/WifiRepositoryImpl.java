package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.RealWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.wifi.SemWifiManager;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiRepositoryImpl implements RealWifiRepository {
    public final StateFlowImpl _wifiConnectivityTestReported;
    public final StateFlowImpl _wifiReceivedInetCondition;
    public final ReadonlyStateFlow hideDuringMobileSwitching;
    public final ReadonlyStateFlow isWifiDefault;
    public final ReadonlyStateFlow isWifiEnabled;
    public final ReadonlyStateFlow receivedInetCondition;
    public final ReadonlyStateFlow wifiActivity;
    public final ReadonlyStateFlow wifiConnectivityTestReported;
    public final ReadonlyStateFlow wifiConnectivityTestReportedChanged;
    public final WifiManager wifiManager;
    public final ReadonlyStateFlow wifiNetwork;
    public final SharedFlowImpl wifiNetworkChangeEvents;
    public final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 wifiStateChangeEvents;
    public static final Companion Companion = new Companion(null);
    public static final DataActivityModel ACTIVITY_DEFAULT = new DataActivityModel(false, false);
    public static final WifiNetworkModel.Inactive WIFI_NETWORK_DEFAULT = WifiNetworkModel.Inactive.INSTANCE;
    public static final NetworkRequest WIFI_NETWORK_CALLBACK_REQUEST = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).addTransportType(0).build();
    public final String TAG = "WifiRepo";
    public final int EID_VSA = 221;
    public final int BAND_5_GHZ_START_FREQ = 5160;
    public final int BAND_5_GHZ_END_FREQ = 5885;
    public final int KTT_VSI_VSD_OUI = 297998080;
    public final byte KT_VSI_VSD_26 = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final BroadcastDispatcher broadcastDispatcher;
        public final ConnectivityManager connectivityManager;
        public final ConnectivityRepository connectivityRepository;
        public final WifiInputLogger logger;
        public final Executor mainExecutor;
        public final CoroutineScope scope;
        public final SemWifiManager semWifiManager;
        public final TableLogBuffer wifiTableLogBuffer;

        public Factory(BroadcastDispatcher broadcastDispatcher, ConnectivityManager connectivityManager, ConnectivityRepository connectivityRepository, WifiInputLogger wifiInputLogger, TableLogBuffer tableLogBuffer, Executor executor, CoroutineScope coroutineScope, SemWifiManager semWifiManager) {
            this.broadcastDispatcher = broadcastDispatcher;
            this.connectivityManager = connectivityManager;
            this.connectivityRepository = connectivityRepository;
            this.logger = wifiInputLogger;
            this.wifiTableLogBuffer = tableLogBuffer;
            this.mainExecutor = executor;
            this.scope = coroutineScope;
            this.semWifiManager = semWifiManager;
        }
    }

    public WifiRepositoryImpl(BroadcastDispatcher broadcastDispatcher, ConnectivityManager connectivityManager, ConnectivityRepository connectivityRepository, WifiInputLogger wifiInputLogger, TableLogBuffer tableLogBuffer, Executor executor, CoroutineScope coroutineScope, WifiManager wifiManager, SemWifiManager semWifiManager) {
        this.wifiManager = wifiManager;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"), null, 0, null, 14), new WifiRepositoryImpl$wifiStateChangeEvents$1(wifiInputLogger, null));
        this.wifiStateChangeEvents = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
        this.wifiNetworkChangeEvents = MutableSharedFlow$default;
        SafeFlow logDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.mapLatest(FlowKt.merge(MutableSharedFlow$default, flowKt__TransformKt$onEach$$inlined$unsafeTransform$1), new WifiRepositoryImpl$isWifiEnabled$1(this, null))), tableLogBuffer, "", "isEnabled", wifiManager.isWifiEnabled());
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.isWifiEnabled = FlowKt.stateIn(logDiffsForTable, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.valueOf(wifiManager.isWifiEnabled()));
        final ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) connectivityRepository).defaultConnections;
        SafeFlow logDiffsForTable2 = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1$2 */
            public final class C33562 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1$2", m277f = "WifiRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        return C33562.this.emit(null, this);
                    }
                }

                public C33562(FlowCollector flowCollector) {
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
                                DefaultConnectionModel defaultConnectionModel = (DefaultConnectionModel) obj;
                                Boolean valueOf = Boolean.valueOf(defaultConnectionModel.wifi.isDefault || defaultConnectionModel.carrierMerged.isDefault);
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
                Object collect = Flow.this.collect(new C33562(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", Account.IS_DEFAULT, false);
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        Boolean bool = Boolean.FALSE;
        this.isWifiDefault = FlowKt.stateIn(logDiffsForTable2, coroutineScope, WhileSubscribed$default, bool);
        StateFlowImpl MutableStateFlow = semWifiManager.getWcmEverQualityTested() == 1 ? StateFlowKt.MutableStateFlow(Boolean.TRUE) : StateFlowKt.MutableStateFlow(bool);
        this._wifiConnectivityTestReported = MutableStateFlow;
        this._wifiReceivedInetCondition = StateFlowKt.MutableStateFlow(-1);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        WifiRepositoryImpl$wifiNetwork$1 wifiRepositoryImpl$wifiNetwork$1 = new WifiRepositoryImpl$wifiNetwork$1(connectivityManager, wifiInputLogger, this, null);
        conflatedCallbackFlow.getClass();
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(wifiRepositoryImpl$wifiNetwork$1));
        WifiNetworkModel.Inactive inactive = WIFI_NETWORK_DEFAULT;
        this.wifiNetwork = FlowKt.stateIn(DiffableKt.logDiffsForTable(distinctUntilChanged, tableLogBuffer, "", inactive), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), inactive);
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(new WifiRepositoryImpl$wifiActivity$1(this, executor, wifiInputLogger, null));
        DataActivityModel dataActivityModel = ACTIVITY_DEFAULT;
        this.wifiActivity = FlowKt.stateIn(DiffableKt.logDiffsForTable(conflatedCallbackFlow2, tableLogBuffer, "wifiActivity", dataActivityModel), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), dataActivityModel);
        final Flow broadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.sec.android.WIFI_ICON_HIDE_ACTION"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$hideDuringMobileSwitching$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (Intent) obj;
            }
        }, 14);
        this.hideDuringMobileSwitching = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2$2 */
            public final class C33572 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2$2", m277f = "WifiRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        return C33572.this.emit(null, this);
                    }
                }

                public C33572(FlowCollector flowCollector) {
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
                                Boolean valueOf = Boolean.valueOf(((Intent) obj).getIntExtra("visible", 1) == 0);
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
                Object collect = Flow.this.collect(new C33572(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new WifiRepositoryImpl$hideDuringMobileSwitching$3(wifiInputLogger, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.valueOf(semWifiManager.getWifiIconVisibility() == 0));
        ReadonlyStateFlow stateIn = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.sec.android.WIFI_CONNECTIVITY_ACTION"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiConnectivityTestReportedChanged$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                StateFlowImpl stateFlowImpl = WifiRepositoryImpl.this._wifiConnectivityTestReported;
                Boolean bool2 = Boolean.TRUE;
                stateFlowImpl.setValue(bool2);
                return bool2;
            }
        }, 14), new WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2(wifiInputLogger, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.valueOf(semWifiManager.getWcmEverQualityTested() == 1));
        this.wifiConnectivityTestReportedChanged = stateIn;
        this.wifiConnectivityTestReported = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.merge(stateIn, MutableStateFlow)), tableLogBuffer, "", "testReported", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), Boolean.valueOf(semWifiManager.getWcmEverQualityTested() == 1));
        this.receivedInetCondition = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.sec.android.WIFI_CONNECTIVITY_ACTION"), null, new Function2() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$receivedInetCondition$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int intExtra = ((Intent) obj).getIntExtra("valid", -1);
                WifiRepositoryImpl.this._wifiReceivedInetCondition.setValue(Integer.valueOf(intExtra));
                return Integer.valueOf(intExtra);
            }
        }, 14), new WifiRepositoryImpl$receivedInetCondition$2(wifiInputLogger, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), -1);
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getHideDuringMobileSwitching() {
        return this.hideDuringMobileSwitching;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getReceivedInetCondition() {
        return this.receivedInetCondition;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getWifiActivity() {
        return this.wifiActivity;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getWifiConnectivityTestReported() {
        return this.wifiConnectivityTestReported;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getWifiNetwork() {
        return this.wifiNetwork;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final boolean isWifiConnectedWithValidSsid() {
        return WifiRepository.DefaultImpls.isWifiConnectedWithValidSsid(this);
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow isWifiDefault() {
        return this.isWifiDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow isWifiEnabled() {
        return this.isWifiEnabled;
    }
}
