package kotlinx.coroutines.channels;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ProduceKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object awaitClose(kotlinx.coroutines.channels.ProducerScope r4, kotlin.jvm.functions.Function0 r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.channels.ProduceKt$awaitClose$1
            if (r0 == 0) goto L13
            r0 = r6
            kotlinx.coroutines.channels.ProduceKt$awaitClose$1 r0 = (kotlinx.coroutines.channels.ProduceKt$awaitClose$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.channels.ProduceKt$awaitClose$1 r0 = new kotlinx.coroutines.channels.ProduceKt$awaitClose$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r4 = r0.L$1
            r5 = r4
            kotlin.jvm.functions.Function0 r5 = (kotlin.jvm.functions.Function0) r5
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L30
            goto L6c
        L30:
            r4 = move-exception
            goto L72
        L32:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlin.coroutines.CoroutineContext r6 = r0.getContext()
            kotlinx.coroutines.Job$Key r2 = kotlinx.coroutines.Job.Key
            kotlin.coroutines.CoroutineContext$Element r6 = r6.get(r2)
            if (r6 != r4) goto L76
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L30
            r0.L$1 = r5     // Catch: java.lang.Throwable -> L30
            r0.label = r3     // Catch: java.lang.Throwable -> L30
            kotlinx.coroutines.CancellableContinuationImpl r6 = new kotlinx.coroutines.CancellableContinuationImpl     // Catch: java.lang.Throwable -> L30
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)     // Catch: java.lang.Throwable -> L30
            r6.<init>(r0, r3)     // Catch: java.lang.Throwable -> L30
            r6.initCancellability()     // Catch: java.lang.Throwable -> L30
            kotlinx.coroutines.channels.ProduceKt$awaitClose$4$1 r0 = new kotlinx.coroutines.channels.ProduceKt$awaitClose$4$1     // Catch: java.lang.Throwable -> L30
            r0.<init>(r6)     // Catch: java.lang.Throwable -> L30
            kotlinx.coroutines.channels.ChannelCoroutine r4 = (kotlinx.coroutines.channels.ChannelCoroutine) r4     // Catch: java.lang.Throwable -> L30
            r4.invokeOnClose(r0)     // Catch: java.lang.Throwable -> L30
            java.lang.Object r4 = r6.getResult()     // Catch: java.lang.Throwable -> L30
            if (r4 != r1) goto L6c
            return r1
        L6c:
            r5.invoke()
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L72:
            r5.invoke()
            throw r4
        L76:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "awaitClose() can only be invoked from the producer context"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ProduceKt.awaitClose(kotlinx.coroutines.channels.ProducerScope, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static ProducerCoroutine produce$default(CoroutineScope coroutineScope, Function2 function2) {
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        BufferOverflow bufferOverflow = BufferOverflow.SUSPEND;
        CoroutineStart coroutineStart = CoroutineStart.DEFAULT;
        ProducerCoroutine producerCoroutine = new ProducerCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, emptyCoroutineContext), ChannelKt.Channel$default(0, bufferOverflow, null, 4));
        producerCoroutine.start(coroutineStart, producerCoroutine, function2);
        return producerCoroutine;
    }
}
