package kotlinx.coroutines.flow;

import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowKt__ErrorsKt$catchImpl$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $collector;
    public final /* synthetic */ Ref$ObjectRef $fromDownstream;

    public FlowKt__ErrorsKt$catchImpl$2(FlowCollector flowCollector, Ref$ObjectRef<Throwable> ref$ObjectRef) {
        this.$collector = flowCollector;
        this.$fromDownstream = ref$ObjectRef;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.lang.Object, kotlin.Unit] */
    /* JADX WARN: Type inference failed for: r5v1, types: [T, java.lang.Throwable] */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1
            if (r0 == 0) goto L13
            r0 = r6
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2$emit$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2 r4 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2) r4
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L2b
            goto L45
        L2b:
            r5 = move-exception
            goto L48
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.FlowCollector r6 = r4.$collector     // Catch: java.lang.Throwable -> L2b
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L2b
            r0.label = r3     // Catch: java.lang.Throwable -> L2b
            java.lang.Object r4 = r6.emit(r5, r0)     // Catch: java.lang.Throwable -> L2b
            if (r4 != r1) goto L45
            return r1
        L45:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L48:
            kotlin.jvm.internal.Ref$ObjectRef r4 = r4.$fromDownstream
            r4.element = r5
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
