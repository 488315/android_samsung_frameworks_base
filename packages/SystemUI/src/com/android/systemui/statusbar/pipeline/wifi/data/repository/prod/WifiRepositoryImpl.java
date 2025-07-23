package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.connectivity.WifiPickerTrackerFactory;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.RealWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WifiRepositoryImpl implements RealWifiRepository, LifecycleOwner {
    public final StateFlowImpl _wifiConnectivityTestReported;
    public final StateFlowImpl _wifiReceivedInetCondition;
    public final CoroutineDispatcher bgDispatcher;
    public final WifiPickerTrackerInfo current;
    public final ReadonlyStateFlow hideDuringMobileSwitching;
    public final LogBuffer inputLogger;
    public final ReadonlyStateFlow isWifiDefault;
    public final ReadonlyStateFlow isWifiEnabled;
    public final LifecycleRegistry lifecycle;
    public final Executor mainExecutor;
    public final ReadonlyStateFlow receivedInetCondition;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow secondaryNetworks;
    public final WifiRepositoryImpl$special$$inlined$map$1 selectedUserContext;
    public final ReadonlyStateFlow wifiActivity;
    public final ReadonlyStateFlow wifiConnectivityTestReported;
    public final ReadonlyStateFlow wifiConnectivityTestReportedChanged;
    public final WifiManager wifiManager;
    public final ReadonlyStateFlow wifiNetwork;
    public WifiPickerTracker wifiPickerTracker;
    public final WifiPickerTrackerFactory wifiPickerTrackerFactory;
    public final ReadonlyStateFlow wifiPickerTrackerInfo;
    public final ReadonlyStateFlow wifiScanResults;
    public static final Companion Companion = new Companion(null);
    public static final WifiNetworkModel.Inactive WIFI_NETWORK_DEFAULT = new WifiNetworkModel.Inactive(null, 1, null);
    public static final DataActivityModel ACTIVITY_DEFAULT = new DataActivityModel(false, false);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getWIFI_NETWORK_DEFAULT$annotations() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
        public final Context applicationContext;
        public final CoroutineDispatcher bgDispatcher;
        public final BroadcastDispatcher broadcastDispatcher;
        public final LogBuffer inputLogger;
        public final Executor mainExecutor;
        public final CoroutineScope scope;
        public final SemWifiManager semWifiManager;
        public final TableLogBuffer tableLogger;
        public final UserRepository userRepository;
        public final WifiPickerTrackerFactory wifiPickerTrackerFactory;

        public Factory(Context context, UserRepository userRepository, CoroutineScope coroutineScope, Executor executor, CoroutineDispatcher coroutineDispatcher, WifiPickerTrackerFactory wifiPickerTrackerFactory, LogBuffer logBuffer, TableLogBuffer tableLogBuffer, SemWifiManager semWifiManager, BroadcastDispatcher broadcastDispatcher) {
            this.applicationContext = context;
            this.userRepository = userRepository;
            this.scope = coroutineScope;
            this.mainExecutor = executor;
            this.bgDispatcher = coroutineDispatcher;
            this.wifiPickerTrackerFactory = wifiPickerTrackerFactory;
            this.inputLogger = logBuffer;
            this.tableLogger = tableLogBuffer;
            this.semWifiManager = semWifiManager;
            this.broadcastDispatcher = broadcastDispatcher;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WifiPickerTrackerInfo {
        public final boolean isDefault;
        public final WifiNetworkModel primaryNetwork;
        public final List secondaryNetworks;
        public final int state;

        public WifiPickerTrackerInfo(int i, boolean z, WifiNetworkModel wifiNetworkModel, List<? extends WifiNetworkModel> list) {
            this.state = i;
            this.isDefault = z;
            this.primaryNetwork = wifiNetworkModel;
            this.secondaryNetworks = list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WifiPickerTrackerInfo)) {
                return false;
            }
            WifiPickerTrackerInfo wifiPickerTrackerInfo = (WifiPickerTrackerInfo) obj;
            return this.state == wifiPickerTrackerInfo.state && this.isDefault == wifiPickerTrackerInfo.isDefault && Intrinsics.areEqual(this.primaryNetwork, wifiPickerTrackerInfo.primaryNetwork) && Intrinsics.areEqual(this.secondaryNetworks, wifiPickerTrackerInfo.secondaryNetworks);
        }

        public final int hashCode() {
            return this.secondaryNetworks.hashCode() + ((this.primaryNetwork.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.state) * 31, 31, this.isDefault)) * 31);
        }

        public final String toString() {
            return "WifiPickerTrackerInfo(state=" + this.state + ", isDefault=" + this.isDefault + ", primaryNetwork=" + this.primaryNetwork + ", secondaryNetworks=" + this.secondaryNetworks + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r10v0, types: [T, com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$WifiPickerTrackerInfo] */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1] */
    public WifiRepositoryImpl(final Context context, UserRepository userRepository, CoroutineScope coroutineScope, Executor executor, CoroutineDispatcher coroutineDispatcher, WifiPickerTrackerFactory wifiPickerTrackerFactory, WifiManager wifiManager, LogBuffer logBuffer, TableLogBuffer tableLogBuffer, SemWifiManager semWifiManager, BroadcastDispatcher broadcastDispatcher) {
        this.scope = coroutineScope;
        this.mainExecutor = executor;
        this.bgDispatcher = coroutineDispatcher;
        this.wifiPickerTrackerFactory = wifiPickerTrackerFactory;
        this.wifiManager = wifiManager;
        this.inputLogger = logBuffer;
        final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$lifecycle$1$2
            @Override // java.lang.Runnable
            public final void run() {
                LifecycleRegistry.this.setCurrentState(Lifecycle.State.CREATED);
            }
        });
        this.lifecycle = lifecycleRegistry;
        final UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) userRepository).selectedUserInfo;
        this.selectedUserContext = new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Context $applicationContext$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, Context context) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$applicationContext$inlined = context;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.content.pm.UserInfo r5 = (android.content.pm.UserInfo) r5
                        android.content.Context r6 = r4.$applicationContext$inlined
                        int r5 = r5.id
                        android.os.UserHandle r5 = android.os.UserHandle.of(r5)
                        r2 = 0
                        android.content.Context r5 = r6.createContextAsUser(r5, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, context), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        EmptyList emptyList = EmptyList.INSTANCE;
        WifiNetworkModel.Inactive inactive = WIFI_NETWORK_DEFAULT;
        this.current = new WifiPickerTrackerInfo(1, false, inactive, emptyList);
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = new WifiPickerTrackerInfo(1, false, inactive, emptyList);
        CallbackFlowBuilder callbackFlow = FlowKt.callbackFlow(new WifiRepositoryImpl$wifiPickerTrackerInfo$2$1(this, context, ref$ObjectRef, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(callbackFlow, coroutineScope, startedEagerly, ref$ObjectRef.element);
        this.wifiPickerTrackerInfo = stateIn;
        Flow logDiffsForTable = DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$WifiPickerTrackerInfo r5 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl.WifiPickerTrackerInfo) r5
                        int r5 = r5.state
                        r6 = 3
                        if (r5 != r6) goto L3b
                        r5 = r3
                        goto L3c
                    L3b:
                        r5 = 0
                    L3c:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4b
                        return r1
                    L4b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", "isEnabled", false);
        Boolean bool = Boolean.FALSE;
        this.isWifiEnabled = FlowKt.stateIn(logDiffsForTable, coroutineScope, startedEagerly, bool);
        this.wifiNetwork = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3$2$1
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
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$WifiPickerTrackerInfo r5 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl.WifiPickerTrackerInfo) r5
                        com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel r5 = r5.primaryNetwork
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", inactive), coroutineScope, startedEagerly, inactive);
        this.secondaryNetworks = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4$2$1
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
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$WifiPickerTrackerInfo r5 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl.WifiPickerTrackerInfo) r5
                        java.util.List r5 = r5.secondaryNetworks
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", "secondaryNetworks", emptyList), coroutineScope, startedEagerly, emptyList);
        this.isWifiDefault = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$WifiPickerTrackerInfo r5 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl.WifiPickerTrackerInfo) r5
                        boolean r5 = r5.isDefault
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), tableLogBuffer, "", Account.IS_DEFAULT, false), coroutineScope, startedEagerly, bool);
        StateFlowImpl MutableStateFlow = semWifiManager.getWcmEverQualityTested() == 1 ? StateFlowKt.MutableStateFlow(Boolean.TRUE) : StateFlowKt.MutableStateFlow(bool);
        this._wifiConnectivityTestReported = MutableStateFlow;
        this._wifiReceivedInetCondition = StateFlowKt.MutableStateFlow(-1);
        this.wifiActivity = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new WifiRepositoryImpl$wifiActivity$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ACTIVITY_DEFAULT);
        this.wifiScanResults = FlowKt.stateIn(FlowConflatedKt.conflatedCallbackFlow(new WifiRepositoryImpl$wifiScanResults$1(this, null)), coroutineScope, startedEagerly, emptyList);
        final Flow broadcastFlow$default = BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.sec.android.WIFI_ICON_HIDE_ACTION"), null, new WifiRepositoryImpl$$ExternalSyntheticLambda0(), 14);
        this.hideDuringMobileSwitching = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6$2$1
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
                        android.content.Intent r5 = (android.content.Intent) r5
                        java.lang.String r6 = "visible"
                        int r5 = r5.getIntExtra(r6, r3)
                        if (r5 != 0) goto L3f
                        r5 = r3
                        goto L40
                    L3f:
                        r5 = 0
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new WifiRepositoryImpl$hideDuringMobileSwitching$3(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(semWifiManager.getWifiIconVisibility() == 0));
        final int i = 0;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.sec.android.WIFI_CONNECTIVITY_ACTION"), null, new Function2(this) { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ WifiRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                WifiRepositoryImpl wifiRepositoryImpl = this.f$0;
                Intent intent = (Intent) obj;
                switch (i) {
                    case 0:
                        Boolean bool2 = Boolean.TRUE;
                        wifiRepositoryImpl._wifiConnectivityTestReported.updateState(null, bool2);
                        return bool2;
                    default:
                        WifiRepositoryImpl.Companion companion2 = WifiRepositoryImpl.Companion;
                        int intExtra = intent.getIntExtra("valid", -1);
                        wifiRepositoryImpl._wifiReceivedInetCondition.updateState(null, Integer.valueOf(intExtra));
                        return Integer.valueOf(intExtra);
                }
            }
        }, 14), new WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(semWifiManager.getWcmEverQualityTested() == 1));
        this.wifiConnectivityTestReportedChanged = stateIn2;
        this.wifiConnectivityTestReported = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.merge(stateIn2, MutableStateFlow)), tableLogBuffer, "", "testReported", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(semWifiManager.getWcmEverQualityTested() == 1));
        final int i2 = 1;
        this.receivedInetCondition = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.sec.android.WIFI_CONNECTIVITY_ACTION"), null, new Function2(this) { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$$ExternalSyntheticLambda1
            public final /* synthetic */ WifiRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                WifiRepositoryImpl wifiRepositoryImpl = this.f$0;
                Intent intent = (Intent) obj;
                switch (i2) {
                    case 0:
                        Boolean bool2 = Boolean.TRUE;
                        wifiRepositoryImpl._wifiConnectivityTestReported.updateState(null, bool2);
                        return bool2;
                    default:
                        WifiRepositoryImpl.Companion companion2 = WifiRepositoryImpl.Companion;
                        int intExtra = intent.getIntExtra("valid", -1);
                        wifiRepositoryImpl._wifiReceivedInetCondition.updateState(null, Integer.valueOf(intExtra));
                        return Integer.valueOf(intExtra);
                }
            }
        }, 14), new WifiRepositoryImpl$receivedInetCondition$2(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), -1);
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getHideDuringMobileSwitching() {
        return this.hideDuringMobileSwitching;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.lifecycle;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getReceivedInetCondition() {
        return this.receivedInetCondition;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow getSecondaryNetworks() {
        return this.secondaryNetworks;
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
    public final StateFlow getWifiScanResults() {
        return this.wifiScanResults;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow isWifiDefault() {
        return this.isWifiDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepository
    public final StateFlow isWifiEnabled() {
        return this.isWifiEnabled;
    }

    public final WifiNetworkModel toWifiNetworkModel(WifiEntry wifiEntry) {
        WifiNetworkModel.HotspotDeviceType hotspotDeviceType;
        WifiNetworkModel.WifiNetworkType wifiNetworkType;
        WifiInfo wifiInfo;
        int deviceType;
        boolean z = true;
        if (wifiEntry instanceof MergedCarrierEntry) {
            MergedCarrierEntry mergedCarrierEntry = (MergedCarrierEntry) wifiEntry;
            WifiNetworkModel.CarrierMerged.Companion companion = WifiNetworkModel.CarrierMerged.Companion;
            int i = mergedCarrierEntry.mSubscriptionId;
            int level = mergedCarrierEntry.getLevel();
            int maxSignalLevel = this.wifiManager.getMaxSignalLevel() + 1;
            companion.getClass();
            return i != -1 ? (level == -1 || level < 0 || level > maxSignalLevel) ? new WifiNetworkModel.Invalid(ListImplementation$$ExternalSyntheticOutline0.m(maxSignalLevel, level, "Wifi network was carrier merged but had invalid level. 0 <= wifi level <= ", " required; level was ")) : new WifiNetworkModel.CarrierMerged(i, level, maxSignalLevel, null) : new WifiNetworkModel.Invalid("Wifi network was carrier merged but had invalid sub ID");
        }
        if (wifiEntry instanceof HotspotNetworkEntry) {
            WifiNetworkModel.Unavailable unavailable = WifiNetworkModel.Unavailable.INSTANCE;
            HotspotNetworkEntry hotspotNetworkEntry = (HotspotNetworkEntry) wifiEntry;
            synchronized (hotspotNetworkEntry) {
                HotspotNetwork hotspotNetwork = hotspotNetworkEntry.mHotspotNetworkData;
                deviceType = hotspotNetwork == null ? 0 : hotspotNetwork.getNetworkProviderInfo().getDeviceType();
            }
            unavailable.getClass();
            hotspotDeviceType = deviceType != 0 ? deviceType != 1 ? deviceType != 2 ? deviceType != 3 ? deviceType != 4 ? deviceType != 5 ? WifiNetworkModel.HotspotDeviceType.INVALID : WifiNetworkModel.HotspotDeviceType.AUTO : WifiNetworkModel.HotspotDeviceType.WATCH : WifiNetworkModel.HotspotDeviceType.LAPTOP : WifiNetworkModel.HotspotDeviceType.TABLET : WifiNetworkModel.HotspotDeviceType.PHONE : WifiNetworkModel.HotspotDeviceType.UNKNOWN;
        } else {
            hotspotDeviceType = WifiNetworkModel.HotspotDeviceType.NONE;
        }
        WifiNetworkModel.HotspotDeviceType hotspotDeviceType2 = hotspotDeviceType;
        WifiNetworkModel.Active.Companion companion2 = WifiNetworkModel.Active.Companion;
        boolean hasInternetAccess = wifiEntry.hasInternetAccess();
        int level2 = wifiEntry.getLevel();
        String title = wifiEntry.getTitle();
        WifiNetworkModel.Unavailable.INSTANCE.getClass();
        SemWifiEntryFlags semWifiEntryFlags = wifiEntry.mSemFlags;
        if (semWifiEntryFlags.isSupportedWifi7 && ((wifiInfo = wifiEntry.mWifiInfo) == null ? semWifiEntryFlags.wifiStandard >= 8 : wifiInfo.getWifiStandard() == 8)) {
            wifiNetworkType = WifiNetworkModel.WifiNetworkType.SEVENG;
        } else {
            WifiInfo wifiInfo2 = wifiEntry.mWifiInfo;
            if (wifiInfo2 != null ? wifiEntry.checkWifi6EStandard(wifiInfo2.getFrequency(), wifiInfo2.getWifiStandard()) : wifiEntry.mSemFlags.has6EStandard) {
                wifiNetworkType = WifiNetworkModel.WifiNetworkType.SIXGE;
            } else {
                WifiInfo wifiInfo3 = wifiEntry.mWifiInfo;
                if (wifiInfo3 == null ? wifiEntry.mSemFlags.wifiStandard >= 6 : wifiInfo3.getWifiStandard() == 6) {
                    wifiNetworkType = WifiNetworkModel.WifiNetworkType.SIXG;
                } else {
                    WifiInfo wifiInfo4 = wifiEntry.mWifiInfo;
                    if (wifiInfo4 == null ? wifiEntry.mSemFlags.wifiStandard < 5 : wifiInfo4.getWifiStandard() != 5) {
                        z = false;
                    }
                    wifiNetworkType = z ? WifiNetworkModel.WifiNetworkType.FIVEG : WifiNetworkModel.WifiNetworkType.NONE;
                }
            }
        }
        WifiNetworkModel.WifiNetworkType wifiNetworkType2 = wifiNetworkType;
        companion2.getClass();
        return (level2 == -1 || level2 < 0 || level2 >= 5) ? new WifiNetworkModel.Inactive(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(level2, "Wifi network was active but had invalid level. 0 <= wifi level <= 4 required; level was ")) : new WifiNetworkModel.Active(hasInternetAccess, level2, title, hotspotDeviceType2, wifiNetworkType2, 0, 32, null);
    }
}
