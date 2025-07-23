package com.android.app.tracing;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FlowTracing$tracedConflatedCallbackFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ String $name;
    int I$0;
    long J$0;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowTracing$tracedConflatedCallbackFlow$1(String str, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$name = str;
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowTracing$tracedConflatedCallbackFlow$1 flowTracing$tracedConflatedCallbackFlow$1 = new FlowTracing$tracedConflatedCallbackFlow$1(this.$name, this.$block, continuation);
        flowTracing$tracedConflatedCallbackFlow$1.L$0 = obj;
        return flowTracing$tracedConflatedCallbackFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowTracing$tracedConflatedCallbackFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0075, code lost:
    
        if (r4.invoke(r9, r8) == r0) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L27
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r9)
            goto L78
        L11:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L19:
            int r0 = r8.I$0
            long r1 = r8.J$0
            java.lang.Object r8 = r8.L$0
            java.lang.String r8 = (java.lang.String) r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L25
            goto L61
        L25:
            r9 = move-exception
            goto L6b
        L27:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.channels.ProducerScope r9 = (kotlinx.coroutines.channels.ProducerScope) r9
            java.lang.String r1 = r8.$name
            kotlin.jvm.functions.Function2 r4 = r8.$block
            boolean r5 = android.os.Trace.isEnabled()
            if (r5 == 0) goto L6f
            int r2 = com.android.app.tracing.TraceUtils.$r8$clinit
            java.lang.String r2 = "#CallbackFlowBlock"
            java.lang.String r1 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r1, r2)
            java.util.concurrent.ThreadLocalRandom r2 = java.util.concurrent.ThreadLocalRandom.current()
            int r2 = r2.nextInt()
            r5 = 4096(0x1000, double:2.0237E-320)
            java.lang.String r7 = "FlowTracing"
            android.os.Trace.asyncTraceForTrackBegin(r5, r7, r1, r2)
            r8.L$0 = r7     // Catch: java.lang.Throwable -> L67
            r8.J$0 = r5     // Catch: java.lang.Throwable -> L67
            r8.I$0 = r2     // Catch: java.lang.Throwable -> L67
            r8.label = r3     // Catch: java.lang.Throwable -> L67
            java.lang.Object r8 = r4.invoke(r9, r8)     // Catch: java.lang.Throwable -> L67
            if (r8 != r0) goto L5e
            goto L77
        L5e:
            r0 = r2
            r1 = r5
            r8 = r7
        L61:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L25
            android.os.Trace.asyncTraceForTrackEnd(r1, r8, r0)
            goto L78
        L67:
            r9 = move-exception
            r0 = r2
            r1 = r5
            r8 = r7
        L6b:
            android.os.Trace.asyncTraceForTrackEnd(r1, r8, r0)
            throw r9
        L6f:
            r8.label = r2
            java.lang.Object r8 = r4.invoke(r9, r8)
            if (r8 != r0) goto L78
        L77:
            return r0
        L78:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.app.tracing.FlowTracing$tracedConflatedCallbackFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
