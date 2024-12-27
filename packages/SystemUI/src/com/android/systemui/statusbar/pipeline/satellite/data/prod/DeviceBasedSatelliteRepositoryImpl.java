package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.telephony.satellite.SatelliteManager;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.log.LogBuffer;
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
import kotlinx.coroutines.BuildersKt;
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

public final class DeviceBasedSatelliteRepositoryImpl implements RealDeviceBasedSatelliteRepository {
    public static final Companion Companion = new Companion(null);
    public final CoroutineDispatcher bgDispatcher;
    public final ReadonlyStateFlow connectionState;
    public final boolean isOpportunisticSatelliteIconEnabled;
    public final StateFlowImpl isSatelliteAllowedForCurrentLocation;
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

    /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

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
                    this.label = 1;
                    Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
                    deviceBasedSatelliteRepositoryImpl.getClass();
                    SatelliteSupport.Companion companion2 = SatelliteSupport.Companion;
                    DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$2 deviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$2 = new DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$2(deviceBasedSatelliteRepositoryImpl);
                    FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                    companion2.getClass();
                    Object collectLatest = FlowKt.collectLatest(FlowKt.transformLatest(deviceBasedSatelliteRepositoryImpl.satelliteSupport, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, deviceBasedSatelliteRepositoryImpl.telephonyProcessCrashedEvent, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, deviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$2)), new DeviceBasedSatelliteRepositoryImpl$pollForAvailabilityBasedOnLocation$3(deviceBasedSatelliteRepositoryImpl, null), this);
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

        /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = deviceBasedSatelliteRepositoryImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            ((StateFlowImpl) mutableStateFlow).setValue(obj);
            Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl2 = DeviceBasedSatelliteRepositoryImpl.this;
            LogBuffer logBuffer = deviceBasedSatelliteRepositoryImpl2.logBuffer;
            Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.1.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    ((LogMessage) obj2).setStr1(((StateFlowImpl) DeviceBasedSatelliteRepositoryImpl.this.getSatelliteSupport()).getValue().toString());
                    return Unit.INSTANCE;
                }
            };
            AnonymousClass2 anonymousClass2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.1.2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Checked for system support. support=", ((LogMessage) obj2).getStr1());
                }
            };
            companion.getClass();
            Companion.i(logBuffer, function1, anonymousClass2);
            DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl3 = DeviceBasedSatelliteRepositoryImpl.this;
            BuildersKt.launch$default(deviceBasedSatelliteRepositoryImpl3.scope, null, null, new AnonymousClass3(deviceBasedSatelliteRepositoryImpl3, null), 3);
            DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl4 = DeviceBasedSatelliteRepositoryImpl.this;
            BuildersKt.launch$default(deviceBasedSatelliteRepositoryImpl4.scope, null, null, new AnonymousClass4(deviceBasedSatelliteRepositoryImpl4, null), 3);
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        private Companion() {
        }

        public static final void access$e(Companion companion, LogBuffer logBuffer, String str, Throwable th) {
            companion.getClass();
            logBuffer.log("DeviceBasedSatelliteRepo", LogLevel.ERROR, str, th);
        }

        public static void i(LogBuffer logBuffer, Function1 function1, Function1 function12) {
            LogMessage obtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.INFO, function12, null);
            function1.invoke(obtain);
            logBuffer.commit(obtain);
        }

        public static /* synthetic */ void i$default(Companion companion, LogBuffer logBuffer, Function1 function1) {
            DeviceBasedSatelliteRepositoryImpl$Companion$i$1 deviceBasedSatelliteRepositoryImpl$Companion$i$1 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion$i$1
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return Unit.INSTANCE;
                }
            };
            companion.getClass();
            i(logBuffer, deviceBasedSatelliteRepositoryImpl$Companion$i$1, function1);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DeviceBasedSatelliteRepositoryImpl$radioPowerState$1 deviceBasedSatelliteRepositoryImpl$radioPowerState$1 = new DeviceBasedSatelliteRepositoryImpl$radioPowerState$1(telephonyManager, this, null);
        conflatedCallbackFlow.getClass();
        final Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(FlowKt.stateIn(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(deviceBasedSatelliteRepositoryImpl$radioPowerState$1), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 2));
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceBasedSatelliteRepositoryImpl$telephonyProcessCrashedEvent$2(null), new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$special$$inlined$mapNotNull$1

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
        Boolean bool = Boolean.FALSE;
        this.isSatelliteAllowedForCurrentLocation = StateFlowKt.MutableStateFlow(bool);
        if (orElse != null) {
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        } else {
            Companion.i$default(Companion, logBuffer, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.2
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "Satellite manager is null";
                }
            });
            MutableStateFlow.setValue(SatelliteSupport.NotSupported.INSTANCE);
        }
        this.satelliteIsSupportedCallback = orElse == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool) : FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1(this, null));
        SatelliteSupport.Companion companion = SatelliteSupport.Companion;
        DeviceBasedSatelliteRepositoryImpl$isSatelliteProvisioned$1 deviceBasedSatelliteRepositoryImpl$isSatelliteProvisioned$1 = new DeviceBasedSatelliteRepositoryImpl$isSatelliteProvisioned$1(this);
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        companion.getClass();
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(MutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, deviceBasedSatelliteRepositoryImpl$isSatelliteProvisioned$1));
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.isSatelliteProvisioned = FlowKt.stateIn(transformLatest, coroutineScope, startedEagerly, bool);
        DeviceBasedSatelliteRepositoryImpl$connectionState$1 deviceBasedSatelliteRepositoryImpl$connectionState$1 = new DeviceBasedSatelliteRepositoryImpl$connectionState$1(this);
        SatelliteConnectionState satelliteConnectionState = SatelliteConnectionState.Off;
        this.connectionState = FlowKt.stateIn(FlowKt.transformLatest(MutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(satelliteConnectionState), deviceBasedSatelliteRepositoryImpl$connectionState$1)), coroutineScope, startedEagerly, satelliteConnectionState);
        this.signalStrength = FlowKt.stateIn(FlowKt.transformLatest(MutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0), new DeviceBasedSatelliteRepositoryImpl$signalStrength$1(this))), coroutineScope, startedEagerly, 0);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:0|1|(2:3|(10:5|6|(1:(1:(3:10|11|12)(2:14|15))(1:16))(2:26|(2:28|(1:30)))|17|18|19|20|(1:22)|11|12))|31|6|(0)(0)|17|18|19|20|(0)|11|12) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x009e, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x009f, code lost:
    
        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(r3, r10.logBuffer, "Exception when checking for satellite support. Assuming it is not supported for this device.", r11);
        r10 = kotlin.Result.$r8$clinit;
        r12.resumeWith(com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport.NotSupported.INSTANCE);
     */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$checkSatelliteSupportAfterMinUptime(final com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r10, final android.telephony.satellite.SatelliteManager r11, kotlin.coroutines.Continuation r12) {
        /*
            r10.getClass()
            boolean r0 = r12 instanceof com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1
            if (r0 == 0) goto L16
            r0 = r12
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1 r0 = (com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1
            r0.<init>(r10, r12)
        L1b:
            java.lang.Object r12 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$Companion r3 = com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L45
            if (r2 == r5) goto L38
            if (r2 != r4) goto L30
            kotlin.ResultKt.throwOnFailure(r12)
            goto Lb6
        L30:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L38:
            java.lang.Object r10 = r0.L$1
            r11 = r10
            android.telephony.satellite.SatelliteManager r11 = (android.telephony.satellite.SatelliteManager) r11
            java.lang.Object r10 = r0.L$0
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl r10 = (com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl) r10
            kotlin.ResultKt.throwOnFailure(r12)
            goto L79
        L45:
            kotlin.ResultKt.throwOnFailure(r12)
            r3.getClass()
            com.android.systemui.util.time.SystemClock r12 = r10.systemClock
            long r6 = r12.uptimeMillis()
            long r8 = android.os.Process.getStartUptimeMillis()
            long r6 = r6 - r8
            r8 = 60000(0xea60, double:2.9644E-319)
            long r8 = r8 - r6
            r6 = 0
            int r12 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r12 <= 0) goto L79
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$2 r12 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$2
            r12.<init>()
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3 r2 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3
                static {
                    /*
                        com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3 r0 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3
                        r0.<init>()
                        
                        // error: 0x0005: SPUT 
  (r0 I:com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3)
 com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3.INSTANCE com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r3) {
                    /*
                        r2 = this;
                        com.android.systemui.log.core.LogMessage r3 = (com.android.systemui.log.core.LogMessage) r3
                        long r2 = r3.getLong1()
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder
                        java.lang.String r1 = "Waiting "
                        r0.<init>(r1)
                        r0.append(r2)
                        java.lang.String r2 = " ms before checking for satellite support"
                        r0.append(r2)
                        java.lang.String r2 = r0.toString()
                        return r2
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$3.invoke(java.lang.Object):java.lang.Object");
                }
            }
            com.android.systemui.log.LogBuffer r6 = r10.logBuffer
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.i(r6, r12, r2)
            r0.L$0 = r10
            r0.L$1 = r11
            r0.label = r5
            java.lang.Object r12 = kotlinx.coroutines.DelayKt.delay(r8, r0)
            if (r12 != r1) goto L79
            goto Lb7
        L79:
            r12 = 0
            r0.L$0 = r12
            r0.L$1 = r12
            r0.label = r4
            r10.getClass()
            kotlinx.coroutines.CancellableContinuationImpl r12 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
            r12.<init>(r0, r5)
            r12.initCancellability()
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupported$2$cb$1 r0 = new com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupported$2$cb$1
            r0.<init>()
            kotlinx.coroutines.CoroutineDispatcher r2 = r10.bgDispatcher     // Catch: java.lang.Exception -> L9e
            java.util.concurrent.Executor r2 = kotlinx.coroutines.ExecutorsKt.asExecutor(r2)     // Catch: java.lang.Exception -> L9e
            r11.requestIsSupported(r2, r0)     // Catch: java.lang.Exception -> L9e
            goto Lad
        L9e:
            r11 = move-exception
            java.lang.String r0 = "Exception when checking for satellite support. Assuming it is not supported for this device."
            com.android.systemui.log.LogBuffer r10 = r10.logBuffer
            com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.Companion.access$e(r3, r10, r0, r11)
            int r10 = kotlin.Result.$r8$clinit
            com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport$NotSupported r10 = com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport.NotSupported.INSTANCE
            r12.resumeWith(r10)
        Lad:
            java.lang.Object r12 = r12.getResult()
            kotlin.coroutines.intrinsics.CoroutineSingletons r10 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r12 != r1) goto Lb6
            goto Lb7
        Lb6:
            r1 = r12
        Lb7:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl.access$checkSatelliteSupportAfterMinUptime(com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl, android.telephony.satellite.SatelliteManager, kotlin.coroutines.Continuation):java.lang.Object");
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
