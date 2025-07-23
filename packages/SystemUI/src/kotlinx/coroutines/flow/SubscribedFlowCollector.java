package kotlinx.coroutines.flow;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SubscribedFlowCollector implements FlowCollector {
    public final Function2 action;
    public final FlowCollector collector;

    public SubscribedFlowCollector(FlowCollector flowCollector, Function2 function2) {
        this.collector = flowCollector;
        this.action = function2;
    }

    @Override // kotlinx.coroutines.flow.FlowCollector
    public final Object emit(Object obj, Continuation continuation) {
        return this.collector.emit(obj, continuation);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0075, code lost:
    
        if (((kotlinx.coroutines.flow.SubscribedFlowCollector) r7).onSubscription(r0) == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onSubscription(kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1
            if (r0 == 0) goto L13
            r0 = r8
            kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1 r0 = (kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1 r0 = new kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1
            r0.<init>(r7, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r8)
            goto L78
        L2a:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L32:
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.flow.internal.SafeCollector r7 = (kotlinx.coroutines.flow.internal.SafeCollector) r7
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.flow.SubscribedFlowCollector r2 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L3e
            goto L5f
        L3e:
            r8 = move-exception
            goto L82
        L40:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.flow.internal.SafeCollector r8 = new kotlinx.coroutines.flow.internal.SafeCollector
            kotlinx.coroutines.flow.FlowCollector r2 = r7.collector
            kotlin.coroutines.CoroutineContext r5 = r0.getContext()
            r8.<init>(r2, r5)
            kotlin.jvm.functions.Function2 r2 = r7.action     // Catch: java.lang.Throwable -> L7e
            r0.L$0 = r7     // Catch: java.lang.Throwable -> L7e
            r0.L$1 = r8     // Catch: java.lang.Throwable -> L7e
            r0.label = r4     // Catch: java.lang.Throwable -> L7e
            java.lang.Object r2 = r2.invoke(r8, r0)     // Catch: java.lang.Throwable -> L7e
            if (r2 != r1) goto L5d
            goto L77
        L5d:
            r2 = r7
            r7 = r8
        L5f:
            r7.releaseIntercepted()
            kotlinx.coroutines.flow.FlowCollector r7 = r2.collector
            boolean r8 = r7 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector
            if (r8 == 0) goto L7b
            kotlinx.coroutines.flow.SubscribedFlowCollector r7 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r7
            r8 = 0
            r0.L$0 = r8
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r7 = r7.onSubscription(r0)
            if (r7 != r1) goto L78
        L77:
            return r1
        L78:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L7b:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L7e:
            r7 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
        L82:
            r7.releaseIntercepted()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SubscribedFlowCollector.onSubscription(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
