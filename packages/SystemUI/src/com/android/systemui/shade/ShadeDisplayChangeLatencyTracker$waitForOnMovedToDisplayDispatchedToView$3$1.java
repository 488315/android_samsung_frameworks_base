package com.android.systemui.shade;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $newDisplayId;
    int label;
    final /* synthetic */ ShadeDisplayChangeLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1(ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeDisplayChangeLatencyTracker;
        this.$newDisplayId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1(this.this$0, this.$newDisplayId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        final ReadonlyStateFlow readonlyStateFlow = this.this$0.onMovedToDisplayFlow;
        final int i2 = this.$newDisplayId;
        Flow flow = new Flow() { // from class: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $newDisplayId$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$newDisplayId$inlined = i;
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Number r6 = (java.lang.Number) r6
                        int r6 = r6.intValue()
                        int r2 = r4.$newDisplayId$inlined
                        if (r6 != r2) goto L48
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitForOnMovedToDisplayDispatchedToView$3$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, i2), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.label = 1;
        Object first = FlowKt.first(flow, this);
        return first == coroutineSingletons ? coroutineSingletons : first;
    }
}
