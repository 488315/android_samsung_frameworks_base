package kotlinx.coroutines.flow;

import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowKt__LimitKt$take$2$1 implements FlowCollector {
    public final /* synthetic */ Ref$IntRef $consumed;
    public final /* synthetic */ int $count;
    public final /* synthetic */ Object $ownershipMarker;
    public final /* synthetic */ FlowCollector $this_flow;

    public FlowKt__LimitKt$take$2$1(Ref$IntRef ref$IntRef, int i, FlowCollector flowCollector, Object obj) {
        this.$consumed = ref$IntRef;
        this.$count = i;
        this.$this_flow = flowCollector;
        this.$ownershipMarker = obj;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x004c, code lost:
    
        if (r5.emit(r7, r0) == r1) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005a, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt__LimitKt.access$emitAbort$FlowKt__LimitKt(r5, r7, r6.$ownershipMarker, r0) == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1$emit$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5d
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L4f
        L36:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlin.jvm.internal.Ref$IntRef r8 = r6.$consumed
            int r2 = r8.element
            int r2 = r2 + r4
            r8.element = r2
            int r8 = r6.$count
            kotlinx.coroutines.flow.FlowCollector r5 = r6.$this_flow
            if (r2 >= r8) goto L52
            r0.label = r4
            java.lang.Object r6 = r5.emit(r7, r0)
            if (r6 != r1) goto L4f
            goto L5c
        L4f:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L52:
            r0.label = r3
            java.lang.Object r6 = r6.$ownershipMarker
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlinx.coroutines.flow.FlowKt__LimitKt.access$emitAbort$FlowKt__LimitKt(r5, r7, r6, r0)
            if (r6 != r1) goto L5d
        L5c:
            return r1
        L5d:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$take$2$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
