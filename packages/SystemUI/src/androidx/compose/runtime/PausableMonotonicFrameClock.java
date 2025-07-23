package androidx.compose.runtime;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PausableMonotonicFrameClock implements MonotonicFrameClock {
    public final MonotonicFrameClock frameClock;
    public final Latch latch = new Latch();

    public PausableMonotonicFrameClock(MonotonicFrameClock monotonicFrameClock) {
        this.frameClock = monotonicFrameClock;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final Object fold(Object obj, Function2 function2) {
        return function2.invoke(obj, this);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        return CoroutineContext.DefaultImpls.get(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext minusKey(CoroutineContext.Key key) {
        return CoroutineContext.DefaultImpls.minusKey(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x007d, code lost:
    
        if (r8 == r1) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0090 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // androidx.compose.runtime.MonotonicFrameClock
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object withFrameNanos(kotlin.jvm.functions.Function1 r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof androidx.compose.runtime.PausableMonotonicFrameClock$withFrameNanos$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.runtime.PausableMonotonicFrameClock$withFrameNanos$1 r0 = (androidx.compose.runtime.PausableMonotonicFrameClock$withFrameNanos$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.runtime.PausableMonotonicFrameClock$withFrameNanos$1 r0 = new androidx.compose.runtime.PausableMonotonicFrameClock$withFrameNanos$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3f
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r8)
            return r8
        L2a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L32:
            java.lang.Object r6 = r0.L$1
            r7 = r6
            kotlin.jvm.functions.Function1 r7 = (kotlin.jvm.functions.Function1) r7
            java.lang.Object r6 = r0.L$0
            androidx.compose.runtime.PausableMonotonicFrameClock r6 = (androidx.compose.runtime.PausableMonotonicFrameClock) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L80
        L3f:
            kotlin.ResultKt.throwOnFailure(r8)
            androidx.compose.runtime.Latch r8 = r6.latch
            r0.L$0 = r6
            r0.L$1 = r7
            r0.label = r4
            java.lang.Object r2 = r8.lock
            monitor-enter(r2)
            boolean r5 = r8._isOpen     // Catch: java.lang.Throwable -> L94
            monitor-exit(r2)
            if (r5 == 0) goto L55
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            goto L7d
        L55:
            kotlinx.coroutines.CancellableContinuationImpl r2 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r5 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)
            r2.<init>(r5, r4)
            r2.initCancellability()
            java.lang.Object r4 = r8.lock
            monitor-enter(r4)
            java.util.List r5 = r8.awaiters     // Catch: java.lang.Throwable -> L91
            java.util.ArrayList r5 = (java.util.ArrayList) r5     // Catch: java.lang.Throwable -> L91
            r5.add(r2)     // Catch: java.lang.Throwable -> L91
            monitor-exit(r4)
            androidx.compose.runtime.Latch$await$2$2 r4 = new androidx.compose.runtime.Latch$await$2$2
            r4.<init>()
            r2.invokeOnCancellation(r4)
            java.lang.Object r8 = r2.getResult()
            if (r8 != r1) goto L7b
            goto L7d
        L7b:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
        L7d:
            if (r8 != r1) goto L80
            goto L8f
        L80:
            androidx.compose.runtime.MonotonicFrameClock r6 = r6.frameClock
            r8 = 0
            r0.L$0 = r8
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r6 = r6.withFrameNanos(r7, r0)
            if (r6 != r1) goto L90
        L8f:
            return r1
        L90:
            return r6
        L91:
            r6 = move-exception
            monitor-exit(r4)
            throw r6
        L94:
            r6 = move-exception
            monitor-exit(r2)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.PausableMonotonicFrameClock.withFrameNanos(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
