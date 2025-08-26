package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.content.res.Resources;
import android.os.OutcomeReceiver;
import android.os.Process;
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
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Optional;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.ExecutorsKt;
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
                    SatelliteManager satelliteManager = deviceBasedSatelliteRepositoryImpl.satelliteManager;
                    this.label = 1;
                    Object objCollectLatest = FlowKt.collectLatest(deviceBasedSatelliteRepositoryImpl.telephonyProcessCrashedEvent, new DeviceBasedSatelliteRepositoryImpl$listenForChangesToSatelliteSupport$2(deviceBasedSatelliteRepositoryImpl, satelliteManager, null), this);
                    if (objCollectLatest != obj2) {
                        objCollectLatest = Unit.INSTANCE;
                    }
                    if (objCollectLatest == obj2) {
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
                Object objAccess$checkSatelliteSupportAfterMinUptime = DeviceBasedSatelliteRepositoryImpl.access$checkSatelliteSupportAfterMinUptime(deviceBasedSatelliteRepositoryImpl, satelliteManager, this);
                if (objAccess$checkSatelliteSupportAfterMinUptime == coroutineSingletons) {
                    return coroutineSingletons;
                }
                mutableStateFlow = satelliteSupport;
                obj = objAccess$checkSatelliteSupportAfterMinUptime;
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
            LogMessage logMessageObtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.INFO, deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = deviceBasedSatelliteRepositoryImpl2.getSatelliteSupport().getValue().toString();
            Unit unit = Unit.INSTANCE;
            logBuffer.commit(logMessageObtain);
            DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl3 = DeviceBasedSatelliteRepositoryImpl.this;
            CoroutineTracingKt.launchTraced$default(deviceBasedSatelliteRepositoryImpl3.scope, deviceBasedSatelliteRepositoryImpl3.bgDispatcher, null, new AnonymousClass3(deviceBasedSatelliteRepositoryImpl3, null), 5);
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$e(Companion companion, LogBuffer logBuffer, String str, Throwable th) {
            companion.getClass();
            LogMessage logMessageObtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.ERROR, new LogBuffer$$ExternalSyntheticLambda0(0), th);
            ((LogMessageImpl) logMessageObtain).str1 = str;
            logBuffer.commit(logMessageObtain);
        }

        public static void i$default(Companion companion, LogBuffer logBuffer, Function1 function1) {
            companion.getClass();
            LogMessage logMessageObtain = logBuffer.obtain("DeviceBasedSatelliteRepo", LogLevel.INFO, function1, null);
            Unit unit = Unit.INSTANCE;
            logBuffer.commit(logMessageObtain);
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$isSatelliteAllowedForCurrentLocation$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11061 extends FunctionReferenceImpl implements Function1 {
        public C11061(Object obj) {
            super(1, obj, DeviceBasedSatelliteRepositoryImpl.class, "isSatelliteAvailableFlow", "isSatelliteAvailableFlow(Landroid/telephony/satellite/SatelliteManager;)Lkotlinx/coroutines/flow/Flow;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
        public final Flow mo781invoke(SatelliteManager satelliteManager) {
            DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = (DeviceBasedSatelliteRepositoryImpl) this.receiver;
            Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
            deviceBasedSatelliteRepositoryImpl.getClass();
            return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositoryImpl$isSatelliteAvailableFlow$1(deviceBasedSatelliteRepositoryImpl, satelliteManager, null)), deviceBasedSatelliteRepositoryImpl.bgDispatcher);
        }
    }

    /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$isSatelliteProvisioned$1, reason: invalid class name and case insensitive filesystem */
    final /* synthetic */ class C11071 extends FunctionReferenceImpl implements Function1 {
        public C11071(Object obj) {
            super(1, obj, DeviceBasedSatelliteRepositoryImpl.class, "satelliteProvisioned", "satelliteProvisioned(Landroid/telephony/satellite/SatelliteManager;)Lkotlinx/coroutines/flow/Flow;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
        public final Flow mo781invoke(SatelliteManager satelliteManager) {
            DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = (DeviceBasedSatelliteRepositoryImpl) this.receiver;
            Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
            deviceBasedSatelliteRepositoryImpl.getClass();
            return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositoryImpl$satelliteProvisioned$1(deviceBasedSatelliteRepositoryImpl, satelliteManager, null)), deviceBasedSatelliteRepositoryImpl.bgDispatcher);
        }
    }

    public DeviceBasedSatelliteRepositoryImpl(Optional<SatelliteManager> optional, TelephonyManager telephonyManager, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, LogBuffer logBuffer, LogBuffer logBuffer2, SystemClock systemClock, Resources resources) {
        this.bgDispatcher = coroutineDispatcher;
        this.scope = coroutineScope;
        this.logBuffer = logBuffer;
        this.verboseLogBuffer = logBuffer2;
        this.systemClock = systemClock;
        this.isOpportunisticSatelliteIconEnabled = resources.getBoolean(R.bool.config_showOpportunisticSatelliteIcon);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(SatelliteSupport.Unknown.INSTANCE);
        this.satelliteSupport = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositoryImpl$radioPowerState$1(telephonyManager, this, null)), coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 2);
        this.radioPowerState = readonlyStateFlowStateIn;
        final Flow flowPairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(readonlyStateFlowStateIn);
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
                        WithPrev withPrev = (WithPrev) obj;
                        Unit unit = (((Number) withPrev.component1()).intValue() != 1 || ((Number) withPrev.component2()).intValue() == 1) ? null : Unit.INSTANCE;
                        if (unit != null) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.telephonyProcessCrashedEvent = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        SatelliteManager satelliteManagerOrElse = optional.orElse(null);
        this.satelliteManager = satelliteManagerOrElse;
        if (satelliteManagerOrElse != null) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, coroutineDispatcher, null, new AnonymousClass1(null), 5);
        } else {
            Companion.i$default(Companion, logBuffer, new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(0));
            stateFlowImplMutableStateFlow.setValue(SatelliteSupport.NotSupported.INSTANCE);
        }
        SatelliteSupport.Companion companion = SatelliteSupport.Companion;
        C11061 c11061 = new C11061(this);
        Boolean bool = Boolean.FALSE;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        companion.getClass();
        this.isSatelliteAllowedForCurrentLocation = FlowKt.stateIn(FlowKt.transformLatest(stateFlowImplMutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, c11061)), coroutineScope, SharingStarted.Companion.Lazily, bool);
        this.satelliteIsSupportedCallback = satelliteManagerOrElse == null ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool) : FlowConflatedKt.conflatedCallbackFlow(new DeviceBasedSatelliteRepositoryImpl$satelliteIsSupportedCallback$1(this, null));
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(stateFlowImplMutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool), new C11071(this)));
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.isSatelliteProvisioned = FlowKt.stateIn(channelFlowTransformLatestTransformLatest, coroutineScope, startedEagerly, bool);
        DeviceBasedSatelliteRepositoryImpl$connectionState$1 deviceBasedSatelliteRepositoryImpl$connectionState$1 = new DeviceBasedSatelliteRepositoryImpl$connectionState$1(this);
        SatelliteConnectionState satelliteConnectionState = SatelliteConnectionState.Off;
        this.connectionState = FlowKt.stateIn(FlowKt.transformLatest(stateFlowImplMutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(satelliteConnectionState), deviceBasedSatelliteRepositoryImpl$connectionState$1)), coroutineScope, startedEagerly, satelliteConnectionState);
        this.signalStrength = FlowKt.stateIn(FlowKt.transformLatest(stateFlowImplMutableStateFlow, new SatelliteSupport$Companion$whenSupported$$inlined$flatMapLatest$1(null, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0), new DeviceBasedSatelliteRepositoryImpl$signalStrength$1(this))), coroutineScope, startedEagerly, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$checkSatelliteSupportAfterMinUptime(final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, final SatelliteManager satelliteManager, ContinuationImpl continuationImpl) {
        DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1 deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1;
        deviceBasedSatelliteRepositoryImpl.getClass();
        if (continuationImpl instanceof DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1) {
            deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1 = (DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1) continuationImpl;
            int i = deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.label = i - Integer.MIN_VALUE;
            } else {
                deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1 = new DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1(deviceBasedSatelliteRepositoryImpl, continuationImpl);
            }
        }
        Object obj = deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.label;
        Companion companion = Companion;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            companion.getClass();
            long jUptimeMillis = 60000 - (deviceBasedSatelliteRepositoryImpl.systemClock.uptimeMillis() - Process.getStartUptimeMillis());
            if (jUptimeMillis > 0) {
                DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 = new DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0(1);
                LogLevel logLevel = LogLevel.INFO;
                LogBuffer logBuffer = deviceBasedSatelliteRepositoryImpl.logBuffer;
                LogMessageImpl logMessageImpl = (LogMessageImpl) logBuffer.obtain("DeviceBasedSatelliteRepo", logLevel, deviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0, null);
                logMessageImpl.long1 = jUptimeMillis;
                Unit unit = Unit.INSTANCE;
                logBuffer.commit(logMessageImpl);
                deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.L$0 = deviceBasedSatelliteRepositoryImpl;
                deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.L$1 = satelliteManager;
                deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.label = 1;
                if (DelayKt.delay(jUptimeMillis, deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            satelliteManager = (SatelliteManager) deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.L$1;
            deviceBasedSatelliteRepositoryImpl = (DeviceBasedSatelliteRepositoryImpl) deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.L$0 = null;
        deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.L$1 = null;
        deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1.label = 2;
        deviceBasedSatelliteRepositoryImpl.getClass();
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(deviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1), 1);
        cancellableContinuationImpl.initCancellability();
        try {
            satelliteManager.requestIsSupported(ExecutorsKt.asExecutor(deviceBasedSatelliteRepositoryImpl.bgDispatcher), new OutcomeReceiver() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupported$2$cb$1
                @Override // android.os.OutcomeReceiver
                public final void onError(Throwable th) {
                    DeviceBasedSatelliteRepositoryImpl.Companion.access$e(DeviceBasedSatelliteRepositoryImpl.Companion, deviceBasedSatelliteRepositoryImpl.logBuffer, "Exception when checking for satellite support. Assuming it is not supported for this device.", (SatelliteManager.SatelliteException) th);
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    int i3 = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(SatelliteSupport.NotSupported.INSTANCE);
                }

                @Override // android.os.OutcomeReceiver
                public final void onResult(Object obj2) {
                    boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                    CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                    int i3 = Result.$r8$clinit;
                    cancellableContinuation.resumeWith(zBooleanValue ? new SatelliteSupport.Supported(satelliteManager) : SatelliteSupport.NotSupported.INSTANCE);
                }
            });
        } catch (Exception e) {
            Companion.access$e(companion, deviceBasedSatelliteRepositoryImpl.logBuffer, "Exception when checking for satellite support. Assuming it is not supported for this device.", e);
            int i3 = Result.$r8$clinit;
            cancellableContinuationImpl.resumeWith(SatelliteSupport.NotSupported.INSTANCE);
        }
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result == coroutineSingletons ? coroutineSingletons : result;
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
