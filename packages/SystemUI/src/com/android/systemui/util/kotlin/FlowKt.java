package com.android.systemui.util.kotlin;

import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.Set;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlinx.coroutines.flow.ChannelFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FlowKt {
    public static final <T1, T2, T3, T4, T5, T6, R> Flow combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Function7 function7) {
        return new FlowKt$combine$$inlined$combine$1(new Flow[]{flow, flow2, flow3, flow4, flow5, flow6}, function7);
    }

    public static final Flow emitOnStart(Flow flow) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), flow);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Flow onSubscriberAdded(MutableSharedFlow mutableSharedFlow) {
        final Flow pairwise = pairwise(((AbstractSharedFlow) mutableSharedFlow).getSubscriptionCount(), 0);
        final Flow flow = new Flow() { // from class: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1$2$1 r0 = (com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1$2$1 r0 = new com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        r6 = r5
                        com.android.systemui.util.kotlin.WithPrev r6 = (com.android.systemui.util.kotlin.WithPrev) r6
                        java.lang.Object r2 = r6.component1()
                        java.lang.Number r2 = (java.lang.Number) r2
                        int r2 = r2.intValue()
                        java.lang.Object r6 = r6.component2()
                        java.lang.Number r6 = (java.lang.Number) r6
                        int r6 = r6.intValue()
                        if (r6 <= r2) goto L56
                        r0.label = r3
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        return new Flow() { // from class: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1$2$1 r0 = (com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1$2$1 r0 = new com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1$2$1
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
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        com.android.systemui.util.kotlin.WithPrev r5 = (com.android.systemui.util.kotlin.WithPrev) r5
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$onSubscriberAdded$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    public static final <T> Flow pairwise(Flow flow) {
        return pairwiseBy(flow, FlowKt$pairwise$2.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object pairwise$lambda$0(Object obj, Object obj2, Continuation continuation) {
        return new WithPrev(obj, obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object pairwise$lambda$1(Object obj, Object obj2, Continuation continuation) {
        return new WithPrev(obj, obj2);
    }

    public static final <T, R> Flow pairwiseBy(Flow flow, Function3 function3) {
        return new SafeFlow(new FlowKt$pairwiseBy$1(flow, function3, null));
    }

    public static final <A, B, C> Flow sample(Flow flow, Flow flow2, Function3 function3) {
        return new SafeFlow(new FlowKt$sample$1(flow, flow2, function3, null));
    }

    public static final <T> Flow setChanges(Flow flow, boolean z) {
        return setChangesBy(flow, FlowKt$setChanges$2.INSTANCE, z);
    }

    public static /* synthetic */ Flow setChanges$default(Flow flow, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return setChanges(flow, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ Object setChanges$lambda$4(Set set, Set set2, Continuation continuation) {
        return new SetChanges(set, set2);
    }

    public static final <T, R> Flow setChangesBy(Flow flow, Function3 function3, boolean z) {
        if (z) {
            flow = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$setChangesBy$1(null), flow);
        }
        return pairwiseBy(kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(flow), new FlowKt$setChangesBy$2(function3, null));
    }

    public static /* synthetic */ Flow setChangesBy$default(Flow flow, Function3 function3, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return setChangesBy(flow, function3, z);
    }

    public static final <T> Flow throttle(Flow flow, long j, SystemClock systemClock) {
        return new ChannelFlowBuilder(new FlowKt$throttle$1(flow, systemClock, j, null), null, 0, null, 14, null);
    }

    public static /* synthetic */ Flow throttle$default(Flow flow, long j, SystemClock systemClock, int i, Object obj) {
        if ((i & 2) != 0) {
            systemClock = new SystemClockImpl();
        }
        return throttle(flow, j, systemClock);
    }

    public static final <S, T extends S> Flow pairwise(Flow flow, S s) {
        return pairwiseBy(flow, s, FlowKt$pairwise$4.INSTANCE);
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, R> Flow combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Flow flow7, Function8 function8) {
        return new FlowKt$combine$$inlined$combine$3(new Flow[]{flow, flow2, flow3, flow4, flow5, flow6, flow7}, function8);
    }

    public static final <S, T extends S, R> Flow pairwiseBy(Flow flow, S s, Function3 function3) {
        return pairwiseBy(flow, (Function1) new FlowKt$pairwiseBy$2(s, null), function3);
    }

    public static final <A> Flow sample(Flow flow, Flow flow2) {
        return sample(flow, flow2, new FlowKt$sample$2(null));
    }

    public static final <S, T extends S, R> Flow pairwiseBy(Flow flow, Function1 function1, Function3 function3) {
        return new SafeFlow(new FlowKt$pairwiseBy$3(function1, flow, function3, null));
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, R> Flow combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Flow flow7, Flow flow8, Function9 function9) {
        return new FlowKt$combine$$inlined$combine$2(new Flow[]{flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8}, function9);
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Flow combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Flow flow6, Flow flow7, Flow flow8, Flow flow9, Function10 function10) {
        return new FlowKt$combine$$inlined$combine$4(new Flow[]{flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9}, function10);
    }
}
