package com.android.app.displaylib;

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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DisplayRepositoryKt$pairwiseBy$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_pairwiseBy;
    final /* synthetic */ Function3 $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ Object $noVal;
        public final /* synthetic */ Ref$ObjectRef $previousValue;
        public final /* synthetic */ Function3 $transform;

        public AnonymousClass1(Ref$ObjectRef<Object> ref$ObjectRef, Object obj, FlowCollector flowCollector, Function3 function3) {
            this.$previousValue = ref$ObjectRef;
            this.$noVal = obj;
            this.$$this$flow = flowCollector;
            this.$transform = function3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0049  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
            /*
                r7 = this;
                boolean r0 = r9 instanceof com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1$emit$1
                if (r0 == 0) goto L13
                r0 = r9
                com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1$emit$1 r0 = (com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1$emit$1 r0 = new com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1$emit$1
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
                com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1 r8 = (com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1.AnonymousClass1) r8
                kotlin.ResultKt.throwOnFailure(r9)
                r6 = r8
                r8 = r7
                r7 = r6
                goto L81
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
                com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1 r2 = (com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1.AnonymousClass1) r2
                kotlin.ResultKt.throwOnFailure(r9)
                goto L70
            L49:
                kotlin.ResultKt.throwOnFailure(r9)
                kotlin.jvm.internal.Ref$ObjectRef r9 = r7.$previousValue
                T r2 = r9.element
                java.lang.Object r5 = r7.$noVal
                boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r5)
                if (r2 != 0) goto L81
                T r9 = r9.element
                r0.L$0 = r7
                r0.L$1 = r8
                kotlinx.coroutines.flow.FlowCollector r2 = r7.$$this$flow
                r0.L$2 = r2
                r0.label = r4
                kotlin.jvm.functions.Function3 r4 = r7.$transform
                java.lang.Object r9 = r4.invoke(r9, r8, r0)
                if (r9 != r1) goto L6d
                goto L7f
            L6d:
                r6 = r2
                r2 = r7
                r7 = r6
            L70:
                r0.L$0 = r2
                r0.L$1 = r8
                r4 = 0
                r0.L$2 = r4
                r0.label = r3
                java.lang.Object r7 = r7.emit(r9, r0)
                if (r7 != r1) goto L80
            L7f:
                return r1
            L80:
                r7 = r2
            L81:
                kotlin.jvm.internal.Ref$ObjectRef r7 = r7.$previousValue
                r7.element = r8
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayRepositoryKt$pairwiseBy$1(Flow flow, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_pairwiseBy = flow;
        this.$transform = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplayRepositoryKt$pairwiseBy$1 displayRepositoryKt$pairwiseBy$1 = new DisplayRepositoryKt$pairwiseBy$1(this.$this_pairwiseBy, this.$transform, continuation);
        displayRepositoryKt$pairwiseBy$1.L$0 = obj;
        return displayRepositoryKt$pairwiseBy$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayRepositoryKt$pairwiseBy$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Object] */
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
}
