package com.google.common.util.concurrent;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.google.common.base.Function;
import com.google.common.util.concurrent.FluentFuture;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractCatchingFuture extends FluentFuture.TrustedFuture implements Runnable {
    public Class exceptionType;
    public Object fallback;
    public ListenableFuture inputFuture;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CatchingFuture extends AbstractCatchingFuture {
        public CatchingFuture(ListenableFuture listenableFuture, Class<Throwable> cls, Function function) {
            super(listenableFuture, cls, function);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        public final Object doFallback(Object obj, Throwable th) {
            return ((Function) obj).apply(th);
        }

        @Override // com.google.common.util.concurrent.AbstractCatchingFuture
        public final void setResult(Object obj) {
            set(obj);
        }
    }

    public AbstractCatchingFuture(ListenableFuture listenableFuture, Class<Throwable> cls, Object obj) {
        listenableFuture.getClass();
        this.inputFuture = listenableFuture;
        cls.getClass();
        this.exceptionType = cls;
        obj.getClass();
        this.fallback = obj;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
    }

    public abstract Object doFallback(Object obj, Throwable th);

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String pendingToString() {
        String str;
        ListenableFuture listenableFuture = this.inputFuture;
        Class cls = this.exceptionType;
        Object obj = this.fallback;
        String pendingToString = super.pendingToString();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = "";
        }
        if (cls == null || obj == null) {
            if (pendingToString != null) {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, pendingToString);
            }
            return null;
        }
        return str + "exceptionType=[" + cls + "], fallback=[" + obj + "]";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0074  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r8 = this;
            com.google.common.util.concurrent.ListenableFuture r0 = r8.inputFuture
            java.lang.Class r1 = r8.exceptionType
            java.lang.Object r2 = r8.fallback
            r3 = 0
            r4 = 1
            if (r0 != 0) goto Lc
            r5 = r4
            goto Ld
        Lc:
            r5 = r3
        Ld:
            if (r1 != 0) goto L11
            r6 = r4
            goto L12
        L11:
            r6 = r3
        L12:
            r5 = r5 | r6
            if (r2 != 0) goto L16
            r3 = r4
        L16:
            r3 = r3 | r5
            if (r3 != 0) goto La4
            java.lang.Object r3 = r8.value
            boolean r3 = r3 instanceof com.google.common.util.concurrent.AbstractFuture.Cancellation
            if (r3 == 0) goto L21
            goto La4
        L21:
            r3 = 0
            r8.inputFuture = r3
            boolean r4 = r0 instanceof com.google.common.util.concurrent.internal.InternalFutureFailureAccess     // Catch: java.lang.Throwable -> L30 java.util.concurrent.ExecutionException -> L32
            if (r4 == 0) goto L34
            r4 = r0
            com.google.common.util.concurrent.internal.InternalFutureFailureAccess r4 = (com.google.common.util.concurrent.internal.InternalFutureFailureAccess) r4     // Catch: java.lang.Throwable -> L30 java.util.concurrent.ExecutionException -> L32
            java.lang.Throwable r4 = r4.tryInternalFastPathGetFailure()     // Catch: java.lang.Throwable -> L30 java.util.concurrent.ExecutionException -> L32
            goto L35
        L30:
            r4 = move-exception
            goto L3c
        L32:
            r4 = move-exception
            goto L3e
        L34:
            r4 = r3
        L35:
            if (r4 != 0) goto L3c
            java.lang.Object r5 = com.google.common.util.concurrent.Futures.getDone(r0)     // Catch: java.lang.Throwable -> L30 java.util.concurrent.ExecutionException -> L32
            goto L6e
        L3c:
            r5 = r3
            goto L6e
        L3e:
            java.lang.Throwable r5 = r4.getCause()
            if (r5 != 0) goto L6c
            java.lang.NullPointerException r5 = new java.lang.NullPointerException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Future type "
            r6.<init>(r7)
            java.lang.Class r7 = r0.getClass()
            r6.append(r7)
            java.lang.String r7 = " threw "
            r6.append(r7)
            java.lang.Class r4 = r4.getClass()
            r6.append(r4)
            java.lang.String r4 = " without a cause"
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            r5.<init>(r4)
        L6c:
            r4 = r5
            goto L3c
        L6e:
            if (r4 != 0) goto L74
            r8.set(r5)
            return
        L74:
            boolean r1 = r1.isInstance(r4)
            if (r1 != 0) goto L7e
            r8.setFuture(r0)
            return
        L7e:
            java.lang.Object r0 = r8.doFallback(r2, r4)     // Catch: java.lang.Throwable -> L8a
            r8.exceptionType = r3
            r8.fallback = r3
            r8.setResult(r0)
            return
        L8a:
            r0 = move-exception
            boolean r1 = r0 instanceof java.lang.InterruptedException     // Catch: java.lang.Throwable -> L9e
            if (r1 == 0) goto L96
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L9e
            r1.interrupt()     // Catch: java.lang.Throwable -> L9e
        L96:
            r8.setException(r0)     // Catch: java.lang.Throwable -> L9e
            r8.exceptionType = r3
            r8.fallback = r3
            return
        L9e:
            r0 = move-exception
            r8.exceptionType = r3
            r8.fallback = r3
            throw r0
        La4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractCatchingFuture.run():void");
    }

    public abstract void setResult(Object obj);
}
