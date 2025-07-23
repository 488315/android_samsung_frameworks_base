package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.telephony.satellite.SatelliteManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBuffer$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.satellite.data.RealDeviceBasedSatelliteRepository;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Optional;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteRepositoryImpl implements RealDeviceBasedSatelliteRepository {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher bgDispatcher;
    public final ReadonlyStateFlow connectionState;
    public final boolean isOpportunisticSatelliteIconEnabled;
    public final ReadonlyStateFlow isSatelliteAllowedForCurrentLocation;
    public final ReadonlyStateFlow isSatelliteProvisioned;
    public final LogBuffer logBuffer;
    public final ReadonlyStateFlow radioPowerState;
    public final Flow satelliteIsSupportedCallback;
    public final SatelliteManager satelliteManager;
    public final StateFlowImpl satelliteSupport;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow signalStrength;
    public final SystemClock systemClock;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 telephonyProcessCrashedEvent;
    public final LogBuffer verboseLogBuffer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = deviceBasedSatelliteRepositoryImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
                    SatelliteManager satelliteManager = deviceBasedSatelliteRepositoryImpl.satelliteManager;
                    this.label = 1;
                    Object collectLatest = FlowKt.collectLatest(deviceBasedSatelliteRepositoryImpl.telephonyProcessCrashedEvent, new DeviceBasedSatelliteRepositoryImpl$listenForChangesToSatelliteSupport$2(deviceBasedSatelliteRepositoryImpl, satelliteManager, null), this);
                    if (collectLatest != obj2) {
                        collectLatest = Unit.INSTANCE;
                    }
                    if (collectLatest == obj2) {
                        return obj2;
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

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DeviceBasedSatelliteRepositoryImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            MutableStateFlow mutableStateFlow;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow satelliteSupport = DeviceBasedSatelliteRepositoryImpl.this.getSatelliteSupport();
                DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = DeviceBasedSatelliteRepositoryImpl.this;
                SatelliteManager satelliteManager = deviceBasedSatelliteRepositoryImpl.satelliteManager;
                this.L$0 = satelliteSupport;
                this.label = 1;
                Object access$checkSatelliteSupportAfterMinUptime = DeviceBasedSatelliteRepositoryImpl.access$checkSatelliteSupportAfterMinUptime(deviceBasedSatelliteRepositoryImpl, satelliteManager, this);
                if (access$checkSatelliteSupportAfterMinUptime == coroutineSingletons) {
                    return coroutineSingletons;
                }
                mutableStateFlow = satelliteSupport;
                obj = access$checkSatelliteSupportAfterMinUptime;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                mutableStateFlow = (MutableStateFlow) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            mutableStateFlow.setValue(obj);
            Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
            DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl2 = DeviceBasedSatelliteRepositoryImpl.this;
            LogBuffer logBuffer = deviceBasedSatelliteRepositoryImpl2.logBuffer;
            DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 = new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(2);
            companion.getClass();
            LogMessage obtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.INFO, deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = deviceBasedSatelliteRepositoryImpl2.getSatelliteSupport().getValue().toString();
            Unit unit = Unit.INSTANCE;
            logBuffer.commit(obtain);
            DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl3 = DeviceBasedSatelliteRepositoryImpl.this;
            CoroutineTracingKt.launchTraced$default(deviceBasedSatelliteRepositoryImpl3.scope, deviceBasedSatelliteRepositoryImpl3.bgDispatcher, null, new AnonymousClass3(deviceBasedSatelliteRepositoryImpl3, null), 5);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$e(Companion companion, LogBuffer logBuffer, String str, Throwable th) {
            companion.getClass();
            LogMessage obtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.ERROR, new LogBuffer$$ExternalSyntheticLambda0(0), th);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
        }

        public static void i$default(Companion companion, LogBuffer logBuffer, Function1 function1) {
            companion.getClass();
            LogMessage obtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.INFO, function1, null);
            Unit unit = Unit.INSTANCE;
            logBuffer.commit(obtain);
        }

        private Companion() {
        }
    }

    public DeviceBasedSatelliteRepositoryImpl(Optional<SatelliteManager> optional, TelephonyManager telephonyManager, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, LogBuffer logBuffer, LogBuffer logBuffer2, SystemClock systemClock, Resources resources) {
        this.bgDispatcher = coroutineDispatcher;
        this.scope = coroutineScope;
        this.logBuffer = logBuffer;
        this.verboseLogBuffer = logBuffer2;
        this.systemClock = systemClock;
        this.isOpportunisticSatelliteIconEnabled = resources.getBoolean(R.bool.config_showOpportunisticSatelliteIcon);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(SatelliteSupport.Unknown.INSTANCE);
        this.satelliteSupport = MutableStateFlow;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositoryImpl$radioPowerState$1(telephonyManager, this, null)), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 2);
        this.radioPowerState = stateIn;
        final Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(stateIn);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceBasedSatelliteRepositoryImpl$telephonyProcessCrashedEvent$2(null), new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.util.kotlin.WithPrev r5 = (com.android.systemui.util.kotlin.WithPrev) r5
                        java.lang.Object r6 = r5.component1()
                        java.lang.Number r6 = (java.lang.Number) r6
                        int r6 = r6.intValue()
                        java.lang.Object r5 = r5.component2()
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        if (r6 != r3) goto L4f
                        if (r5 == r3) goto L4f
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        goto L50
                    L4f:
                        r5 = 0
                    L50:
                        if (r5 == 0) goto L5d
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5d
                        return r1
                    L5d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.telephonyProcessCrashedEvent = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        SatelliteManager orElse = optional.orElse(null);
        this.satelliteManager = orElse;
        if (orElse != null) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, coroutineDispatcher, null, new AnonymousClass1(null), 5);
        } else {
            Companion.i$default(Companion, logBuffer, new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(0));
            MutableStateFlow.setValue(SatelliteSupport.NotSupported.INSTANCE);
        }
        SatelliteSupport.Companion companion = SatelliteSupport.Companion;
        DeviceBasedSatelliteRepositoryImpl$isSatelliteAllowedForCurrentLocation$1 deviceBasedSatelliteRepositoryImpl$isSatelliteAllowedForCurrentLocation$1 = new DeviceBasedSatelliteRepositoryImpl$isSatelliteAllowedForCurrentLocation$1(this);
        Boolean bool = Boolean.FALSE;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        companion.getClass();
        this.isSatelliteAllowedForCurrentLocation = FlowKt.stateIn(FlowKt.transformLatest(MutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, deviceBasedSatelliteRepositoryImpl$isSatelliteAllowedForCurrentLocation$1)), coroutineScope, SharingStarted.Companion.Lazily, bool);
        this.satelliteIsSupportedCallback = orElse == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool) : FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1(this, null));
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(MutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool), new DeviceBasedSatelliteRepositoryImpl$isSatelliteProvisioned$1(this)));
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.isSatelliteProvisioned = FlowKt.stateIn(transformLatest, coroutineScope, startedEagerly, bool);
        DeviceBasedSatelliteRepositoryImpl$connectionState$1 deviceBasedSatelliteRepositoryImpl$connectionState$1 = new DeviceBasedSatelliteRepositoryImpl$connectionState$1(this);
        SatelliteConnectionState satelliteConnectionState = SatelliteConnectionState.Off;
        this.connectionState = FlowKt.stateIn(FlowKt.transformLatest(MutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(satelliteConnectionState), deviceBasedSatelliteRepositoryImpl$connectionState$1)), coroutineScope, startedEagerly, satelliteConnectionState);
        this.signalStrength = FlowKt.stateIn(FlowKt.transformLatest(MutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0), new DeviceBasedSatelliteRepositoryImpl$signalStrength$1(this))), coroutineScope, startedEagerly, 0);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:3|(8:5|6|(1:(1:(2:10|11)(2:13|14))(1:15))(2:27|(2:29|(1:31)))|16|17|18|19|(1:23)(2:21|22)))|32|6|(0)(0)|16|17|18|19|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00a9, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00aa, code lost:
    
        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(r4, r11.logBuffer, "Exception when checking for satellite support. Assuming it is not supported for this device.", r12);
        r11 = kotlin.Result.$r8$clinit;
        r13.resumeWith(com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport.NotSupported.INSTANCE);
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$checkSatelliteSupportAfterMinUptime(final com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r11, final android.telephony.satellite.SatelliteManager r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            r0 = 1
            r11.getClass()
            boolean r1 = r13 instanceof com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1
            if (r1 == 0) goto L17
            r1 = r13
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1 r1 = (com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L17
            int r2 = r2 - r3
            r1.label = r2
            goto L1c
        L17:
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1 r1 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1
            r1.<init>(r11, r13)
        L1c:
            java.lang.Object r13 = r1.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r3 = r1.label
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r4 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion
            r5 = 2
            r6 = 0
            if (r3 == 0) goto L45
            if (r3 == r0) goto L38
            if (r3 != r5) goto L30
            kotlin.ResultKt.throwOnFailure(r13)
            return r13
        L30:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L38:
            java.lang.Object r11 = r1.L$1
            r12 = r11
            android.telephony.satellite.SatelliteManager r12 = (android.telephony.satellite.SatelliteManager) r12
            java.lang.Object r11 = r1.L$0
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r11 = (com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl) r11
            kotlin.ResultKt.throwOnFailure(r13)
            goto L85
        L45:
            kotlin.ResultKt.throwOnFailure(r13)
            r4.getClass()
            com.android.systemui.util.time.SystemClock r13 = r11.systemClock
            long r7 = r13.uptimeMillis()
            long r9 = android.os.Process.getStartUptimeMillis()
            long r7 = r7 - r9
            r9 = 60000(0xea60, double:2.9644E-319)
            long r9 = r9 - r7
            r7 = 0
            int r13 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r13 <= 0) goto L85
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 r13 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0
            r13.<init>(r0)
            com.android.systemui.log.core.LogLevel r3 = com.android.systemui.log.core.LogLevel.INFO
            java.lang.String r7 = "DeviceBasedSatelliteRepo"
            com.android.systemui.log.LogBuffer r8 = r11.logBuffer
            com.android.systemui.log.core.LogMessage r13 = r8.obtain(r7, r3, r13, r6)
            com.android.systemui.log.LogMessageImpl r13 = (com.android.systemui.log.LogMessageImpl) r13
            r13.long1 = r9
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            r8.commit(r13)
            r1.L$0 = r11
            r1.L$1 = r12
            r1.label = r0
            java.lang.Object r13 = kotlinx.coroutines.DelayKt.delay(r9, r1)
            if (r13 != r2) goto L85
            goto Lc2
        L85:
            r1.L$0 = r6
            r1.L$1 = r6
            r1.label = r5
            r11.getClass()
            kotlinx.coroutines.CancellableContinuationImpl r13 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r1 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r1)
            r13.<init>(r1, r0)
            r13.initCancellability()
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupported$2$cb$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupported$2$cb$1
            r0.<init>()
            kotlinx.coroutines.CoroutineDispatcher r1 = r11.bgDispatcher     // Catch: java.lang.Exception -> La9
            java.util.concurrent.Executor r1 = kotlinx.coroutines.ExecutorsKt.asExecutor(r1)     // Catch: java.lang.Exception -> La9
            r12.requestIsSupported(r1, r0)     // Catch: java.lang.Exception -> La9
            goto Lb8
        La9:
            r12 = move-exception
            java.lang.String r0 = "Exception when checking for satellite support. Assuming it is not supported for this device."
            com.android.systemui.log.LogBuffer r11 = r11.logBuffer
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(r4, r11, r0, r12)
            int r11 = kotlin.Result.$r8$clinit
            com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport$NotSupported r11 = com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport.NotSupported.INSTANCE
            r13.resumeWith(r11)
        Lb8:
            java.lang.Object r11 = r13.getResult()
            kotlin.coroutines.intrinsics.CoroutineSingletons r12 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r11 != r2) goto Lc1
            goto Lc2
        Lc1:
            r2 = r11
        Lc2:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.access$checkSatelliteSupportAfterMinUptime(com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl, android.telephony.satellite.SatelliteManager, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow getConnectionState() {
        return this.connectionState;
    }

    public final MutableStateFlow getSatelliteSupport() {
        return this.satelliteSupport;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow getSignalStrength() {
        return this.signalStrength;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final boolean isOpportunisticSatelliteIconEnabled() {
        return this.isOpportunisticSatelliteIconEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow isSatelliteAllowedForCurrentLocation() {
        return this.isSatelliteAllowedForCurrentLocation;
    }

    @Override // com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository
    public final StateFlow isSatelliteProvisioned() {
        return this.isSatelliteProvisioned;
    }
}
