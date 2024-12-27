package com.android.systemui.display.data.repository;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import com.android.app.tracing.FlowTracing;
import com.android.app.tracing.TraceStateLogger;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.Flags;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class DisplayRepositoryImpl implements DisplayRepository {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = Log.isLoggable("DisplayRepository", 3);
    public final StateFlowImpl _ignoredDisplayIds;
    public final ReadonlyStateFlow connectedDisplayIds;
    public final DisplayRepositoryImpl$special$$inlined$map$9 defaultDisplayOff;
    public final DisplayRepositoryImpl$special$$inlined$map$2 displayAdditionEvent;
    public final DisplayRepositoryImpl$special$$inlined$map$1 displayChangeEvent;
    public final DisplayManager displayManager;
    public final ReadonlySharedFlow displays;
    public final ReadonlySharedFlow enabledDisplays;
    public final ReadonlySharedFlow oldEnabledDisplays;
    public final Flow pendingDisplay;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DisplayRepositoryImpl(DisplayManager displayManager, Handler handler, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.displayManager = displayManager;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DisplayRepositoryImpl$allDisplayEvents$1 displayRepositoryImpl$allDisplayEvents$1 = new DisplayRepositoryImpl$allDisplayEvents$1(this, handler, null);
        conflatedCallbackFlow.getClass();
        final Flow flowOn = FlowKt.flowOn(debugLog(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DisplayRepositoryImpl$allDisplayEvents$2(null), FlowConflatedKt.conflatedCallbackFlow(displayRepositoryImpl$allDisplayEvents$1)), "allDisplayEvents"), coroutineDispatcher);
        final Flow flow = new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1$2$1
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
                        boolean r6 = r5 instanceof com.android.systemui.display.data.DisplayEvent.Changed
                        if (r6 == 0) goto L41
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.displayChangeEvent = new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1$2$1
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
                        com.android.systemui.display.data.DisplayEvent$Changed r5 = (com.android.systemui.display.data.DisplayEvent.Changed) r5
                        int r5 = r5.displayId
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2$2$1
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
                        boolean r6 = r5 instanceof com.android.systemui.display.data.DisplayEvent.Added
                        if (r6 == 0) goto L41
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.displayAdditionEvent = new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DisplayRepositoryImpl this$0;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DisplayRepositoryImpl displayRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = displayRepositoryImpl;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.display.data.DisplayEvent$Added r5 = (com.android.systemui.display.data.DisplayEvent.Added) r5
                        int r5 = r5.displayId
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl r6 = r4.this$0
                        android.view.Display r5 = com.android.systemui.display.data.repository.DisplayRepositoryImpl.access$getDisplay(r5, r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow3 = new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DisplayRepositoryImpl this$0;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DisplayRepositoryImpl displayRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = displayRepositoryImpl;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L69
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.display.data.DisplayEvent r5 = (com.android.systemui.display.data.DisplayEvent) r5
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$Companion r5 = com.android.systemui.display.data.repository.DisplayRepositoryImpl.Companion
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl r5 = r4.this$0
                        r5.getClass()
                        boolean r6 = android.os.Trace.isEnabled()
                        if (r6 == 0) goto L46
                        java.lang.String r2 = "DisplayRepository#getDisplays()"
                        com.android.app.tracing.TraceUtilsKt.beginSlice(r2)
                    L46:
                        android.hardware.display.DisplayManager r5 = r5.displayManager     // Catch: java.lang.Throwable -> L55
                        android.view.Display[] r5 = r5.getDisplays()     // Catch: java.lang.Throwable -> L55
                        if (r5 == 0) goto L57
                        java.util.Set r5 = kotlin.collections.ArraysKt___ArraysKt.toSet(r5)     // Catch: java.lang.Throwable -> L55
                        if (r5 != 0) goto L59
                        goto L57
                    L55:
                        r4 = move-exception
                        goto L6c
                    L57:
                        kotlin.collections.EmptySet r5 = kotlin.collections.EmptySet.INSTANCE     // Catch: java.lang.Throwable -> L55
                    L59:
                        if (r6 == 0) goto L5e
                        com.android.app.tracing.TraceUtilsKt.endSlice()
                    L5e:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L69
                        return r1
                    L69:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L6c:
                        if (r6 == 0) goto L71
                        com.android.app.tracing.TraceUtilsKt.endSlice()
                    L71:
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlySharedFlow shareIn = FlowKt.shareIn(flow3, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 1);
        Flags.FEATURE_FLAGS.getClass();
        Flow debugLog = debugLog(new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L6d
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.Set r6 = (java.util.Set) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.ArrayList r7 = new java.util.ArrayList
                        r2 = 10
                        int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r6, r2)
                        r7.<init>(r2)
                        java.util.Iterator r6 = r6.iterator()
                    L45:
                        boolean r2 = r6.hasNext()
                        if (r2 == 0) goto L5e
                        java.lang.Object r2 = r6.next()
                        android.view.Display r2 = (android.view.Display) r2
                        int r2 = r2.getDisplayId()
                        java.lang.Integer r4 = new java.lang.Integer
                        r4.<init>(r2)
                        r7.add(r4)
                        goto L45
                    L5e:
                        java.util.Set r6 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r7)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L6d
                        return r1
                    L6d:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "enabledDisplayIds");
        Flags.FEATURE_FLAGS.getClass();
        this.displays = shareIn;
        EmptySet emptySet = EmptySet.INSTANCE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(emptySet);
        this._ignoredDisplayIds = MutableStateFlow;
        Flow debugLog2 = debugLog(MutableStateFlow, "ignoredDisplayIds");
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(debugLog(FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new DisplayRepositoryImpl$connectedDisplayIds$1(this, handler, null))), "connectedDisplayIds"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptySet);
        final Flow debugLog3 = debugLog(FlowKt.combine(debugLog, debugLog(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DisplayRepositoryImpl this$0;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DisplayRepositoryImpl displayRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = displayRepositoryImpl;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5$2$1
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto Lb1
                    L28:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L30:
                        kotlin.ResultKt.throwOnFailure(r11)
                        java.util.Set r10 = (java.util.Set) r10
                        boolean r11 = android.os.Trace.isEnabled()
                        if (r11 == 0) goto L40
                        java.lang.String r2 = "DisplayRepository#filteringExternalDisplays"
                        com.android.app.tracing.TraceUtilsKt.beginSlice(r2)
                    L40:
                        java.lang.Iterable r10 = (java.lang.Iterable) r10     // Catch: java.lang.Throwable -> L95
                        java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L95
                        r2.<init>()     // Catch: java.lang.Throwable -> L95
                        java.util.Iterator r10 = r10.iterator()     // Catch: java.lang.Throwable -> L95
                    L4b:
                        boolean r4 = r10.hasNext()     // Catch: java.lang.Throwable -> L95
                        if (r4 == 0) goto L9d
                        java.lang.Object r4 = r10.next()     // Catch: java.lang.Throwable -> L95
                        r5 = r4
                        java.lang.Number r5 = (java.lang.Number) r5     // Catch: java.lang.Throwable -> L95
                        int r5 = r5.intValue()     // Catch: java.lang.Throwable -> L95
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl r6 = r9.this$0     // Catch: java.lang.Throwable -> L95
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$Companion r7 = com.android.systemui.display.data.repository.DisplayRepositoryImpl.Companion     // Catch: java.lang.Throwable -> L95
                        r6.getClass()     // Catch: java.lang.Throwable -> L95
                        boolean r7 = android.os.Trace.isEnabled()     // Catch: java.lang.Throwable -> L95
                        if (r7 == 0) goto L6e
                        java.lang.String r8 = "DisplayRepository#getDisplayType"
                        com.android.app.tracing.TraceUtilsKt.beginSlice(r8)     // Catch: java.lang.Throwable -> L95
                    L6e:
                        android.hardware.display.DisplayManager r6 = r6.displayManager     // Catch: java.lang.Throwable -> L7f
                        android.view.Display r5 = r6.getDisplay(r5)     // Catch: java.lang.Throwable -> L7f
                        if (r5 == 0) goto L81
                        int r5 = r5.getType()     // Catch: java.lang.Throwable -> L7f
                        java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L7f
                        goto L82
                    L7f:
                        r9 = move-exception
                        goto L97
                    L81:
                        r5 = 0
                    L82:
                        if (r7 == 0) goto L87
                        com.android.app.tracing.TraceUtilsKt.endSlice()     // Catch: java.lang.Throwable -> L95
                    L87:
                        if (r5 != 0) goto L8a
                        goto L4b
                    L8a:
                        int r5 = r5.intValue()     // Catch: java.lang.Throwable -> L95
                        r6 = 2
                        if (r5 != r6) goto L4b
                        r2.add(r4)     // Catch: java.lang.Throwable -> L95
                        goto L4b
                    L95:
                        r9 = move-exception
                        goto Lb4
                    L97:
                        if (r7 == 0) goto L9c
                        com.android.app.tracing.TraceUtilsKt.endSlice()     // Catch: java.lang.Throwable -> L95
                    L9c:
                        throw r9     // Catch: java.lang.Throwable -> L95
                    L9d:
                        java.util.Set r10 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r2)     // Catch: java.lang.Throwable -> L95
                        if (r11 == 0) goto La6
                        com.android.app.tracing.TraceUtilsKt.endSlice()
                    La6:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                        java.lang.Object r9 = r9.emit(r10, r0)
                        if (r9 != r1) goto Lb1
                        return r1
                    Lb1:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    Lb4:
                        if (r11 == 0) goto Lb9
                        com.android.app.tracing.TraceUtilsKt.endSlice()
                    Lb9:
                        throw r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher), "connectedExternalDisplayIds"), debugLog2, new DisplayRepositoryImpl$pendingDisplayIds$1(null)), "allPendingDisplayIds");
        final Flow debugLog4 = debugLog(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6$2$1
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
                        java.util.Set r5 = (java.util.Set) r5
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        java.lang.Comparable r5 = kotlin.collections.CollectionsKt___CollectionsKt.maxOrNull(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), "pendingDisplayId");
        this.pendingDisplay = debugLog(new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DisplayRepositoryImpl this$0;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DisplayRepositoryImpl displayRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = displayRepositoryImpl;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Integer r5 = (java.lang.Integer) r5
                        if (r5 == 0) goto L42
                        int r5 = r5.intValue()
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$pendingDisplay$1$1 r6 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$pendingDisplay$1$1
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl r2 = r4.this$0
                        r6.<init>(r5, r2)
                        goto L43
                    L42:
                        r6 = 0
                    L43:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$7.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, "pendingDisplay");
        final Flow flow4 = new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.Set r5 = (java.util.Set) r5
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        java.util.Iterator r5 = r5.iterator()
                    L3a:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L4e
                        java.lang.Object r6 = r5.next()
                        r2 = r6
                        android.view.Display r2 = (android.view.Display) r2
                        int r2 = r2.getDisplayId()
                        if (r2 != 0) goto L3a
                        goto L4f
                    L4e:
                        r6 = 0
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$8.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.defaultDisplayOff = new Flow() { // from class: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9

            /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9$2$1 r0 = (com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9$2$1 r0 = new com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.view.Display r5 = (android.view.Display) r5
                        r6 = 0
                        if (r5 == 0) goto L3e
                        int r5 = r5.getState()
                        if (r5 != r3) goto L3e
                        r6 = r3
                    L3e:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    public static final Display access$getDisplay(int i, DisplayRepositoryImpl displayRepositoryImpl) {
        displayRepositoryImpl.getClass();
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("DisplayRepository#getDisplay");
        }
        try {
            return displayRepositoryImpl.displayManager.getDisplay(i);
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public static Flow debugLog(Flow flow, String str) {
        if (!DEBUG) {
            return flow;
        }
        FlowTracing flowTracing = FlowTracing.INSTANCE;
        TraceStateLogger traceStateLogger = new TraceStateLogger(str, false, false, true, 6, null);
        flowTracing.getClass();
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowTracing.traceEmissionCount(flow, str), new DisplayRepositoryImpl$debugLog$$inlined$traceEach$default$1(traceStateLogger, null));
    }
}
