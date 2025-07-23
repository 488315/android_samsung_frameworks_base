package kotlinx.coroutines.flow;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowKt__LimitKt$dropWhile$1$1 implements FlowCollector {
    public final /* synthetic */ Ref$BooleanRef $matched;
    public final /* synthetic */ Function2 $predicate;
    public final /* synthetic */ FlowCollector $this_flow;

    public FlowKt__LimitKt$dropWhile$1$1(Ref$BooleanRef ref$BooleanRef, FlowCollector flowCollector, Function2 function2) {
        this.$matched = ref$BooleanRef;
        this.$this_flow = flowCollector;
        this.$predicate = function2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0082, code lost:
    
        if (r6.$this_flow.emit(r7, r0) == r1) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0054, code lost:
    
        if (r6.$this_flow.emit(r7, r0) == r1) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0066, code lost:
    
        if (r8 == r1) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L43
            if (r2 == r5) goto L3f
            if (r2 == r4) goto L35
            if (r2 != r3) goto L2d
            kotlin.ResultKt.throwOnFailure(r8)
            goto L85
        L2d:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L35:
            java.lang.Object r7 = r0.L$1
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1 r6 = (kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L69
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L57
        L43:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlin.jvm.internal.Ref$BooleanRef r8 = r6.$matched
            boolean r8 = r8.element
            if (r8 == 0) goto L5a
            r0.label = r5
            kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_flow
            java.lang.Object r6 = r6.emit(r7, r0)
            if (r6 != r1) goto L57
            goto L84
        L57:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L5a:
            r0.L$0 = r6
            r0.L$1 = r7
            r0.label = r4
            kotlin.jvm.functions.Function2 r8 = r6.$predicate
            java.lang.Object r8 = r8.invoke(r7, r0)
            if (r8 != r1) goto L69
            goto L84
        L69:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L88
            kotlin.jvm.internal.Ref$BooleanRef r8 = r6.$matched
            r8.element = r5
            r8 = 0
            r0.L$0 = r8
            r0.L$1 = r8
            r0.label = r3
            kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_flow
            java.lang.Object r6 = r6.emit(r7, r0)
            if (r6 != r1) goto L85
        L84:
            return r1
        L85:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L88:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
