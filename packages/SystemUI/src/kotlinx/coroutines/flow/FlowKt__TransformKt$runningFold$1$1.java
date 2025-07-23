package kotlinx.coroutines.flow;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowKt__TransformKt$runningFold$1$1 implements FlowCollector {
    public final /* synthetic */ Ref$ObjectRef $accumulator;
    public final /* synthetic */ Function3 $operation;
    public final /* synthetic */ FlowCollector $this_flow;

    public FlowKt__TransformKt$runningFold$1$1(Ref$ObjectRef<Object> ref$ObjectRef, Function3 function3, FlowCollector flowCollector) {
        this.$accumulator = ref$ObjectRef;
        this.$operation = function3;
        this.$this_flow = flowCollector;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x006b, code lost:
    
        if (r6.emit(r7, r0) != r1) goto L23;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1$emit$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3e
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6e
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            java.lang.Object r6 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r6 = (kotlin.jvm.internal.Ref$ObjectRef) r6
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1 r7 = (kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L58
        L3e:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlin.jvm.internal.Ref$ObjectRef r8 = r6.$accumulator
            T r2 = r8.element
            r0.L$0 = r6
            r0.L$1 = r8
            r0.label = r4
            kotlin.jvm.functions.Function3 r4 = r6.$operation
            java.lang.Object r7 = r4.invoke(r2, r7, r0)
            if (r7 != r1) goto L54
            goto L6d
        L54:
            r5 = r7
            r7 = r6
            r6 = r8
            r8 = r5
        L58:
            r6.element = r8
            kotlinx.coroutines.flow.FlowCollector r6 = r7.$this_flow
            kotlin.jvm.internal.Ref$ObjectRef r7 = r7.$accumulator
            T r7 = r7.element
            r8 = 0
            r0.L$0 = r8
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r6 = r6.emit(r7, r0)
            if (r6 != r1) goto L6e
        L6d:
            return r1
        L6e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
