package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.os.OutcomeReceiver;
import android.os.Process;
import android.telephony.satellite.SatelliteManager;
import android.util.Log;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepository;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Optional;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.SubscriptionCountStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteRepositoryImpl implements DeviceBasedSatelliteRepository {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl isSatelliteAllowedForCurrentLocation;
    public final SatelliteManager satelliteManager;
    public final StateFlowImpl satelliteSupport;
    public final SystemClock systemClock;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1", m277f = "DeviceBasedSatelliteRepositoryImpl.kt", m278l = {163, 165, 178}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1 */
    public static final class C33391 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1$2", m277f = "DeviceBasedSatelliteRepositoryImpl.kt", m278l = {192}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            /* synthetic */ boolean Z$0;
            int label;

            public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
                anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
                return anonymousClass2;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    if (!this.Z$0) {
                        return Unit.INSTANCE;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                do {
                    Log.i("DeviceBasedSatelliteRepo", "requestIsCommunicationAllowedForCurrentLocation");
                    this.label = 1;
                } while (DelayKt.delay(3600000L, this) != coroutineSingletons);
                return coroutineSingletons;
            }
        }

        public C33391(Continuation<? super C33391> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DeviceBasedSatelliteRepositoryImpl.this.new C33391(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C33391) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x00c5  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            MutableStateFlow satelliteSupport;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
                SystemClock systemClock = DeviceBasedSatelliteRepositoryImpl.this.systemClock;
                companion.getClass();
                ((SystemClockImpl) systemClock).getClass();
                long uptimeMillis = 60000 - (android.os.SystemClock.uptimeMillis() - Process.getStartUptimeMillis());
                if (uptimeMillis > 0) {
                    Log.i("DeviceBasedSatelliteRepo", "Waiting " + uptimeMillis + " ms before checking for satellite support");
                    this.label = 1;
                    if (DelayKt.delay(uptimeMillis, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    satelliteSupport = (MutableStateFlow) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    ((StateFlowImpl) satelliteSupport).setValue(obj);
                    Log.i("DeviceBasedSatelliteRepo", "Checked for system support. support=" + ((StateFlowImpl) DeviceBasedSatelliteRepositoryImpl.this.getSatelliteSupport()).getValue());
                    if (((StateFlowImpl) DeviceBasedSatelliteRepositoryImpl.this.getSatelliteSupport()).getValue() instanceof SatelliteSupport.Supported) {
                        final SubscriptionCountStateFlow subscriptionCount = DeviceBasedSatelliteRepositoryImpl.this.isSatelliteAllowedForCurrentLocation.getSubscriptionCount();
                        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1$invokeSuspend$$inlined$map$1

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1$invokeSuspend$$inlined$map$1$2", m277f = "DeviceBasedSatelliteRepositoryImpl.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                                /* renamed from: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                                Boolean valueOf = Boolean.valueOf(((Number) obj).intValue() > 0);
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
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        });
                        AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
                        this.L$0 = null;
                        this.label = 3;
                        if (FlowKt.collectLatest(distinctUntilChanged, anonymousClass2, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            satelliteSupport = DeviceBasedSatelliteRepositoryImpl.this.getSatelliteSupport();
            final SatelliteManager satelliteManager = DeviceBasedSatelliteRepositoryImpl.this.satelliteManager;
            this.L$0 = satelliteSupport;
            this.label = 2;
            final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
            cancellableContinuationImpl.initCancellability();
            new OutcomeReceiver() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupported$2$cb$1
                @Override // android.os.OutcomeReceiver
                public final void onError(Throwable th) {
                    Log.e("DeviceBasedSatelliteRepo", "Exception when checking for satellite support. Assuming it is not supported for this device.");
                    CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                    int i2 = Result.$r8$clinit;
                    ((CancellableContinuationImpl) cancellableContinuation).resumeWith(SatelliteSupport.NotSupported.INSTANCE);
                }

                @Override // android.os.OutcomeReceiver
                public final void onResult(Object obj2) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                    int i2 = Result.$r8$clinit;
                    ((CancellableContinuationImpl) cancellableContinuation).resumeWith(booleanValue ? new SatelliteSupport.Supported(satelliteManager) : SatelliteSupport.NotSupported.INSTANCE);
                }
            };
            obj = cancellableContinuationImpl.getResult();
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            ((StateFlowImpl) satelliteSupport).setValue(obj);
            Log.i("DeviceBasedSatelliteRepo", "Checked for system support. support=" + ((StateFlowImpl) DeviceBasedSatelliteRepositoryImpl.this.getSatelliteSupport()).getValue());
            if (((StateFlowImpl) DeviceBasedSatelliteRepositoryImpl.this.getSatelliteSupport()).getValue() instanceof SatelliteSupport.Supported) {
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DeviceBasedSatelliteRepositoryImpl(Optional<SatelliteManager> optional, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, LogBuffer logBuffer, SystemClock systemClock) {
        this.systemClock = systemClock;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(SatelliteSupport.Unknown.INSTANCE);
        this.satelliteSupport = MutableStateFlow;
        SatelliteManager orElse = optional.orElse(null);
        this.satelliteManager = orElse;
        this.isSatelliteAllowedForCurrentLocation = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        if (orElse != null) {
            BuildersKt.launch$default(coroutineScope, null, null, new C33391(null), 3);
        } else {
            Log.i("DeviceBasedSatelliteRepo", "Satellite manager is null");
            MutableStateFlow.setValue(SatelliteSupport.NotSupported.INSTANCE);
        }
        SatelliteSupport.Companion companion = SatelliteSupport.Companion;
        SatelliteConnectionState satelliteConnectionState = SatelliteConnectionState.Off;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(satelliteConnectionState);
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(satelliteConnectionState);
        companion.getClass();
        FlowKt.transformLatest(MutableStateFlow, new C3341x625b3972(null, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22));
        FlowKt.transformLatest(MutableStateFlow, new C3341x625b3972(null, new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0), new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(0)));
    }

    public final MutableStateFlow getSatelliteSupport() {
        return this.satelliteSupport;
    }
}
