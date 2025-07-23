package com.android.systemui.statusbar.pipeline.satellite.domain.interactor;

import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractor;
import com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteInteractor {
    public final ReadonlyStateFlow areAllConnectionsOutOfService;
    public final ReadonlyStateFlow connectionState;
    public final ChannelFlowTransformLatest isAnyConnectionNtn;
    public final boolean isOpportunisticSatelliteIconEnabled;
    public final ReadonlyStateFlow isSatelliteAllowed;
    public final StateFlow isSatelliteProvisioned;
    public final DeviceBasedSatelliteInteractor$special$$inlined$map$1 isWifiActive;
    public final LogBuffer logBuffer;
    public final ReadonlyStateFlow signalStrength;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r11v2, types: [com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1] */
    public DeviceBasedSatelliteInteractor(DeviceBasedSatelliteRepository deviceBasedSatelliteRepository, MobileIconsInteractor mobileIconsInteractor, WifiInteractor wifiInteractor, CoroutineScope coroutineScope, LogBuffer logBuffer, TableLogBuffer tableLogBuffer) {
        this.logBuffer = logBuffer;
        this.isOpportunisticSatelliteIconEnabled = deviceBasedSatelliteRepository.isOpportunisticSatelliteIconEnabled();
        Flow logDiffsForTable = DiffableKt.logDiffsForTable((Flow) deviceBasedSatelliteRepository.isSatelliteAllowedForCurrentLocation(), tableLogBuffer, "", "allowed", false);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.isSatelliteAllowed = FlowKt.stateIn(logDiffsForTable, coroutineScope, WhileSubscribed$default, bool);
        StateFlow connectionState = deviceBasedSatelliteRepository.getConnectionState();
        SatelliteConnectionState satelliteConnectionState = SatelliteConnectionState.Off;
        this.connectionState = FlowKt.stateIn(DiffableKt.logDiffsForTable(connectionState, tableLogBuffer, "", satelliteConnectionState), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), satelliteConnectionState);
        this.signalStrength = FlowKt.stateIn(DiffableKt.logDiffsForTable(deviceBasedSatelliteRepository.getSignalStrength(), tableLogBuffer, "", ActionResults.RESULT_SET_VOLUME_SUCCESS, 0), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.isSatelliteProvisioned = deviceBasedSatelliteRepository.isSatelliteProvisioned();
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = ((WifiInteractorImpl) wifiInteractor).wifiNetwork;
        this.isWifiActive = new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1$2$1
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
                        com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel r5 = (com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel) r5
                        boolean r5 = r5 instanceof com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.Active
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final StateFlow icons = mobileIconsInteractor.getIcons();
        Boolean bool2 = Boolean.TRUE;
        this.areAllConnectionsOutOfService = FlowKt.stateIn(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(DiffableKt.logDiffsForTable(FlowKt.distinctUntilChanged(FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L76
                    L27:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r10)
                        java.util.List r9 = (java.util.List) r9
                        java.lang.Iterable r9 = (java.lang.Iterable) r9
                        java.util.ArrayList r10 = new java.util.ArrayList
                        r2 = 10
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r9, r2)
                        r10.<init>(r2)
                        java.util.Iterator r9 = r9.iterator()
                    L45:
                        boolean r2 = r9.hasNext()
                        if (r2 == 0) goto L6b
                        java.lang.Object r2 = r9.next()
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor r2 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor) r2
                        kotlinx.coroutines.flow.Flow r4 = r2.isInService()
                        kotlinx.coroutines.flow.Flow r5 = r2.isEmergencyOnly()
                        kotlinx.coroutines.flow.Flow r2 = r2.isNonTerrestrial()
                        com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$allConnectionsOos$1$1 r6 = new com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$allConnectionsOos$1$1
                        r7 = 0
                        r6.<init>(r7)
                        kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 r2 = kotlinx.coroutines.flow.FlowKt.combine(r4, r5, r2, r6)
                        r10.add(r2)
                        goto L45
                    L6b:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r10, r0)
                        if (r8 != r1) goto L76
                        return r1
                    L76:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$2(null, bool2))), tableLogBuffer, "", "allConnsOOS", true), mobileIconsInteractor.isDeviceInEmergencyCallsOnlyMode(), new DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1(this, null))), tableLogBuffer, "", "allOosAndNoEmer", true), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool2);
        final StateFlow icons2 = mobileIconsInteractor.getIcons();
        this.isAnyConnectionNtn = FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3$2$1 r0 = (com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3$2$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L64
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        java.util.ArrayList r6 = new java.util.ArrayList
                        r2 = 10
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r5, r2)
                        r6.<init>(r2)
                        java.util.Iterator r5 = r5.iterator()
                    L45:
                        boolean r2 = r5.hasNext()
                        if (r2 == 0) goto L59
                        java.lang.Object r2 = r5.next()
                        com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor r2 = (com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor) r2
                        kotlinx.coroutines.flow.Flow r2 = r2.isNonTerrestrial()
                        r6.add(r2)
                        goto L45
                    L59:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L64
                        return r1
                    L64:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4(null, bool));
    }
}
