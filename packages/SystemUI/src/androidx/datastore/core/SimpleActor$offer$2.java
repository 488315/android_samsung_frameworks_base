package androidx.datastore.core;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SimpleActor$offer$2 extends SuspendLambda implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ SimpleActor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleActor$offer$2(SimpleActor simpleActor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simpleActor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimpleActor$offer$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimpleActor$offer$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0054, code lost:
    
        if (r1.invoke(r6, r5) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0056, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0048, code lost:
    
        if (r6 != r0) goto L15;
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0054 -> B:6:0x0057). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L57
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            java.lang.Object r1 = r5.L$0
            kotlin.jvm.functions.Function2 r1 = (kotlin.jvm.functions.Function2) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4b
        L20:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.datastore.core.SimpleActor r6 = r5.this$0
            androidx.datastore.core.AtomicInt r6 = r6.remainingMessages
            java.util.concurrent.atomic.AtomicInteger r6 = r6.delegate
            int r6 = r6.get()
            if (r6 <= 0) goto L66
        L2f:
            androidx.datastore.core.SimpleActor r6 = r5.this$0
            kotlinx.coroutines.CoroutineScope r6 = r6.scope
            kotlin.coroutines.CoroutineContext r6 = r6.getCoroutineContext()
            kotlinx.coroutines.JobKt.ensureActive(r6)
            androidx.datastore.core.SimpleActor r6 = r5.this$0
            kotlin.jvm.functions.Function2 r1 = r6.consumeMessage
            kotlinx.coroutines.channels.BufferedChannel r6 = r6.messageQueue
            r5.L$0 = r1
            r5.label = r3
            java.lang.Object r6 = r6.receive(r5)
            if (r6 != r0) goto L4b
            goto L56
        L4b:
            r4 = 0
            r5.L$0 = r4
            r5.label = r2
            java.lang.Object r6 = r1.invoke(r6, r5)
            if (r6 != r0) goto L57
        L56:
            return r0
        L57:
            androidx.datastore.core.SimpleActor r6 = r5.this$0
            androidx.datastore.core.AtomicInt r6 = r6.remainingMessages
            java.util.concurrent.atomic.AtomicInteger r6 = r6.delegate
            int r6 = r6.decrementAndGet()
            if (r6 != 0) goto L2f
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L66:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Check failed."
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SimpleActor$offer$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
