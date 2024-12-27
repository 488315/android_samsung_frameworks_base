package com.android.systemui.util.kotlin;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

final class FlowKt$pairwiseBy$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_pairwiseBy;
    final /* synthetic */ Function3 $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1, reason: invalid class name */
    final class AnonymousClass1<T> implements FlowCollector {
        final /* synthetic */ FlowCollector $$this$flow;
        final /* synthetic */ Object $noVal;
        final /* synthetic */ Ref$ObjectRef<Object> $previousValue;
        final /* synthetic */ Function3 $transform;

        public AnonymousClass1(Ref$ObjectRef<Object> ref$ObjectRef, Object obj, FlowCollector flowCollector, Function3 function3) {
            this.$previousValue = ref$ObjectRef;
            this.$noVal = obj;
            this.$$this$flow = flowCollector;
            this.$transform = function3;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(T r8, kotlin.coroutines.Continuation r9) {
            /*
                r7 = this;
                boolean r0 = r9 instanceof com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1
                if (r0 == 0) goto L13
                r0 = r9
                com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1 r0 = (com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1 r0 = new com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1$emit$1
                r0.<init>(r7, r9)
            L18:
                java.lang.Object r9 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 2
                r4 = 1
                if (r2 == 0) goto L49
                if (r2 == r4) goto L3b
                if (r2 != r3) goto L33
                java.lang.Object r7 = r0.L$1
                java.lang.Object r8 = r0.L$0
                com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1 r8 = (com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1.AnonymousClass1) r8
                kotlin.ResultKt.throwOnFailure(r9)
                r6 = r8
                r8 = r7
                r7 = r6
                goto L84
            L33:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L3b:
                java.lang.Object r7 = r0.L$2
                kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                java.lang.Object r8 = r0.L$1
                java.lang.Object r2 = r0.L$0
                com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1 r2 = (com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1.AnonymousClass1) r2
                kotlin.ResultKt.throwOnFailure(r9)
                goto L73
            L49:
                kotlin.ResultKt.throwOnFailure(r9)
                kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r9 = r7.$previousValue
                T r9 = r9.element
                java.lang.Object r2 = r7.$noVal
                boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r2)
                if (r9 != 0) goto L84
                kotlinx.coroutines.flow.FlowCollector r9 = r7.$$this$flow
                kotlin.jvm.functions.Function3 r2 = r7.$transform
                kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r5 = r7.$previousValue
                T r5 = r5.element
                r0.L$0 = r7
                r0.L$1 = r8
                r0.L$2 = r9
                r0.label = r4
                java.lang.Object r2 = r2.invoke(r5, r8, r0)
                if (r2 != r1) goto L6f
                return r1
            L6f:
                r6 = r2
                r2 = r7
                r7 = r9
                r9 = r6
            L73:
                r0.L$0 = r2
                r0.L$1 = r8
                r4 = 0
                r0.L$2 = r4
                r0.label = r3
                java.lang.Object r7 = r7.emit(r9, r0)
                if (r7 != r1) goto L83
                return r1
            L83:
                r7 = r2
            L84:
                kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r7 = r7.$previousValue
                r7.element = r8
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public FlowKt$pairwiseBy$1(Flow flow, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_pairwiseBy = flow;
        this.$transform = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt$pairwiseBy$1 flowKt$pairwiseBy$1 = new FlowKt$pairwiseBy$1(this.$this_pairwiseBy, this.$transform, continuation);
        flowKt$pairwiseBy$1.L$0 = obj;
        return flowKt$pairwiseBy$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ?? obj2 = new Object();
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = obj2;
            Flow flow = this.$this_pairwiseBy;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(ref$ObjectRef, obj2, flowCollector, this.$transform);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
        return ((FlowKt$pairwiseBy$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
