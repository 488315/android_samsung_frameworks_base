package com.android.systemui.communal.posturing.domain.interactor;

import android.hardware.Sensor;
import com.android.systemui.communal.posturing.data.repository.NoOpPosturingRepository;
import com.android.systemui.communal.posturing.data.repository.PosturingRepository;
import com.android.systemui.communal.posturing.shared.model.PosturedState;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.util.kotlin.AsyncSensorManagerExtKt;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PosturingInteractor {
    public static final long SLIDING_WINDOW_DURATION;
    public static final long STOP_TIMEOUT_AFTER_UNSUBSCRIBE;
    public final AsyncSensorManager asyncSensorManager;
    public final StateFlowImpl debugPostured;
    public final Logger logger;
    public final PosturingInteractor$special$$inlined$map$8 orientationSmoothed;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 postured;
    public final ReadonlyStateFlow posturedSmoothed;
    public final PosturingInteractor$special$$inlined$map$5 stationarySmoothed;

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
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.SECONDS;
        SLIDING_WINDOW_DURATION = DurationKt.toDuration(10, durationUnit);
        STOP_TIMEOUT_AFTER_UNSUBSCRIBE = DurationKt.toDuration(5, durationUnit);
    }

    /* JADX WARN: Type inference failed for: r7v5, types: [com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r8v9, types: [com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5, kotlinx.coroutines.flow.Flow] */
    public PosturingInteractor(PosturingRepository posturingRepository, AsyncSensorManager asyncSensorManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, LogBuffer logBuffer, SystemClock systemClock) {
        this.asyncSensorManager = asyncSensorManager;
        this.logger = new Logger(logBuffer, "PosturingInteractor");
        PosturedState.Unknown unknown = PosturedState.Unknown.INSTANCE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(unknown);
        this.debugPostured = MutableStateFlow;
        Sensor defaultSensor = asyncSensorManager.getDefaultSensor(25);
        final Flow observeTriggerSensor = defaultSensor == null ? EmptyFlow.INSTANCE : AsyncSensorManagerExtKt.observeTriggerSensor(asyncSensorManager, defaultSensor);
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        java.lang.Float r5 = new java.lang.Float
                        r6 = -8388608(0xffffffffff800000, float:-Infinity)
                        r5.<init>(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new PosturingInteractor$stationarySmoothed$2(this, null));
        Sensor defaultSensor2 = asyncSensorManager.getDefaultSensor(17);
        final Flow observeTriggerSensor2 = defaultSensor2 == null ? EmptyFlow.INSTANCE : AsyncSensorManagerExtKt.observeTriggerSensor(asyncSensorManager, defaultSensor2);
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$12 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        java.lang.Float r5 = new java.lang.Float
                        r6 = -8388608(0xffffffffff800000, float:-Infinity)
                        r5.<init>(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new PosturingInteractor$stationarySmoothed$4(this, null));
        NoOpPosturingRepository noOpPosturingRepository = (NoOpPosturingRepository) posturingRepository;
        final ReadonlyStateFlow readonlyStateFlow = noOpPosturingRepository.positionState;
        final Flow flow = new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3$2$1
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
                        com.android.systemui.communal.posturing.data.model.PositionState r5 = (com.android.systemui.communal.posturing.data.model.PositionState) r5
                        com.android.systemui.communal.posturing.data.model.PositionState$StationaryState r5 = r5.stationary
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.communal.posturing.data.model.PositionState$StationaryState r6 = (com.android.systemui.communal.posturing.data.model.PositionState.StationaryState) r6
                        boolean r6 = r6 instanceof com.android.systemui.communal.posturing.data.model.PositionState.StationaryState.Unknown
                        if (r6 != 0) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ChannelLimitedFlowMerge merge = FlowKt.merge(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, flowKt__TransformKt$onEach$$inlined$unsafeTransform$12, new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4$2$1
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
                        com.android.systemui.communal.posturing.data.model.PositionState$StationaryState r5 = (com.android.systemui.communal.posturing.data.model.PositionState.StationaryState) r5
                        boolean r6 = r5 instanceof com.android.systemui.communal.posturing.data.model.PositionState.StationaryState.Stationary
                        if (r6 == 0) goto L3d
                        com.android.systemui.communal.posturing.data.model.PositionState$StationaryState$Stationary r5 = (com.android.systemui.communal.posturing.data.model.PositionState.StationaryState.Stationary) r5
                        float r5 = r5.confidence
                        goto L3f
                    L3d:
                        r5 = -8388608(0xffffffffff800000, float:-Infinity)
                    L3f:
                        java.lang.Float r6 = new java.lang.Float
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        long j = SLIDING_WINDOW_DURATION;
        final Flow m3149slidingWindow8Mi8wO0 = com.android.systemui.util.kotlin.FlowKt.m3149slidingWindow8Mi8wO0(merge, j, systemClock);
        final Flow flow3 = new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.util.List r6 = (java.util.List) r6
                        boolean r6 = r6.isEmpty()
                        if (r6 != 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ?? r8 = new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ PosturingInteractor this$0;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, PosturingInteractor posturingInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = posturingInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        boolean r0 = r13 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r13
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5$2$1
                        r0.<init>(r13)
                    L18:
                        java.lang.Object r13 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L88
                    L27:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r13)
                        java.util.List r12 = (java.util.List) r12
                        r4 = r12
                        java.lang.Iterable r4 = (java.lang.Iterable) r4
                        double r12 = kotlin.collections.CollectionsKt___CollectionsKt.averageOfFloat(r4)
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor r2 = r11.this$0
                        com.android.systemui.log.core.Logger r2 = r2.logger
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$stationarySmoothed$9$1 r5 = com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$stationarySmoothed$9$1.INSTANCE
                        com.android.systemui.log.core.LogLevel r6 = com.android.systemui.log.core.LogLevel.INFO
                        com.android.systemui.log.core.MessageBuffer r7 = r2.getBuffer()
                        java.lang.String r8 = r2.getTag()
                        r9 = 0
                        com.android.systemui.log.core.LogMessage r10 = r7.obtain(r8, r6, r5, r9)
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractorKt$$ExternalSyntheticLambda0 r8 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractorKt$$ExternalSyntheticLambda0
                        r8.<init>()
                        r5 = 0
                        r9 = 25
                        java.lang.String r6 = "["
                        java.lang.String r7 = "]"
                        java.lang.String r4 = kotlin.collections.CollectionsKt___CollectionsKt.joinToString$default(r4, r5, r6, r7, r8, r9)
                        r10.setStr1(r4)
                        r10.setDouble1(r12)
                        com.android.systemui.log.core.MessageBuffer r2 = r2.getBuffer()
                        r2.commit(r10)
                        r4 = 4605380979056443392(0x3fe99999a0000000, double:0.800000011920929)
                        int r12 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
                        if (r12 <= 0) goto L78
                        r12 = r3
                        goto L79
                    L78:
                        r12 = 0
                    L79:
                        java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                        java.lang.Object r11 = r11.emit(r12, r0)
                        if (r11 != r1) goto L88
                        return r1
                    L88:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.stationarySmoothed = r8;
        final ReadonlyStateFlow readonlyStateFlow2 = noOpPosturingRepository.positionState;
        final Flow flow4 = new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6$2$1
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
                        com.android.systemui.communal.posturing.data.model.PositionState r5 = (com.android.systemui.communal.posturing.data.model.PositionState) r5
                        com.android.systemui.communal.posturing.data.model.PositionState$OrientationState r5 = r5.orientation
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow5 = new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.communal.posturing.data.model.PositionState$OrientationState r6 = (com.android.systemui.communal.posturing.data.model.PositionState.OrientationState) r6
                        boolean r6 = r6 instanceof com.android.systemui.communal.posturing.data.model.PositionState.OrientationState.Unknown
                        if (r6 != 0) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow m3149slidingWindow8Mi8wO02 = com.android.systemui.util.kotlin.FlowKt.m3149slidingWindow8Mi8wO0(new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7$2$1
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
                        com.android.systemui.communal.posturing.data.model.PositionState$OrientationState r5 = (com.android.systemui.communal.posturing.data.model.PositionState.OrientationState) r5
                        boolean r6 = r5 instanceof com.android.systemui.communal.posturing.data.model.PositionState.OrientationState.Postured
                        if (r6 == 0) goto L3d
                        com.android.systemui.communal.posturing.data.model.PositionState$OrientationState$Postured r5 = (com.android.systemui.communal.posturing.data.model.PositionState.OrientationState.Postured) r5
                        float r5 = r5.confidence
                        goto L3f
                    L3d:
                        r5 = -8388608(0xffffffffff800000, float:-Infinity)
                    L3f:
                        java.lang.Float r6 = new java.lang.Float
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, j, systemClock);
        final Flow flow6 = new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.util.List r6 = (java.util.List) r6
                        boolean r6 = r6.isEmpty()
                        if (r6 != 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$filterNot$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ?? r7 = new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ PosturingInteractor this$0;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, PosturingInteractor posturingInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = posturingInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        boolean r0 = r13 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r13
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8$2$1
                        r0.<init>(r13)
                    L18:
                        java.lang.Object r13 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L88
                    L27:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r13)
                        java.util.List r12 = (java.util.List) r12
                        r4 = r12
                        java.lang.Iterable r4 = (java.lang.Iterable) r4
                        double r12 = kotlin.collections.CollectionsKt___CollectionsKt.averageOfFloat(r4)
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor r2 = r11.this$0
                        com.android.systemui.log.core.Logger r2 = r2.logger
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$orientationSmoothed$5$1 r5 = com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$orientationSmoothed$5$1.INSTANCE
                        com.android.systemui.log.core.LogLevel r6 = com.android.systemui.log.core.LogLevel.INFO
                        com.android.systemui.log.core.MessageBuffer r7 = r2.getBuffer()
                        java.lang.String r8 = r2.getTag()
                        r9 = 0
                        com.android.systemui.log.core.LogMessage r10 = r7.obtain(r8, r6, r5, r9)
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractorKt$$ExternalSyntheticLambda0 r8 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractorKt$$ExternalSyntheticLambda0
                        r8.<init>()
                        r5 = 0
                        r9 = 25
                        java.lang.String r6 = "["
                        java.lang.String r7 = "]"
                        java.lang.String r4 = kotlin.collections.CollectionsKt___CollectionsKt.joinToString$default(r4, r5, r6, r7, r8, r9)
                        r10.setStr1(r4)
                        r10.setDouble1(r12)
                        com.android.systemui.log.core.MessageBuffer r2 = r2.getBuffer()
                        r2.commit(r10)
                        r4 = 4605380979056443392(0x3fe99999a0000000, double:0.800000011920929)
                        int r12 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
                        if (r12 <= 0) goto L78
                        r12 = r3
                        goto L79
                    L78:
                        r12 = 0
                    L79:
                        java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                        java.lang.Object r11 = r11.emit(r12, r0)
                        if (r11 != r1) goto L88
                        return r1
                    L88:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$8.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.orientationSmoothed = r7;
        final Flow allOf = BooleanFlowOperators.INSTANCE.allOf(r8, r7);
        Flow flowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9$2$1 r0 = (com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9$2$1 r0 = new com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L3d
                        com.android.systemui.communal.posturing.shared.model.PosturedState$Postured r5 = com.android.systemui.communal.posturing.shared.model.PosturedState.Postured.INSTANCE
                        goto L3f
                    L3d:
                        com.android.systemui.communal.posturing.shared.model.PosturedState$NotPostured r5 = com.android.systemui.communal.posturing.shared.model.PosturedState.NotPostured.INSTANCE
                    L3f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        SharingStarted.Companion companion = SharingStarted.Companion;
        long m3437getInWholeMillisecondsimpl = Duration.m3437getInWholeMillisecondsimpl(STOP_TIMEOUT_AFTER_UNSUBSCRIBE);
        companion.getClass();
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowOn, coroutineScope, new StartedWhileSubscribed(m3437getInWholeMillisecondsimpl, 0L), unknown);
        this.posturedSmoothed = stateIn;
        this.postured = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, MutableStateFlow, new PosturingInteractor$postured$1(null));
    }
}
