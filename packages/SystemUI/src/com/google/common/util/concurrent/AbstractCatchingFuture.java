package com.google.common.util.concurrent;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.google.common.base.Function;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import java.util.concurrent.ExecutionException;

/* loaded from: classes4.dex */
public abstract class AbstractCatchingFuture extends FluentFuture.TrustedFuture implements Runnable {
    public Class exceptionType;
    public Object fallback;
    public ListenableFuture inputFuture;

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
        String strPendingToString = super.pendingToString();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = "";
        }
        if (cls == null || obj == null) {
            if (strPendingToString != null) {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, strPendingToString);
            }
            return null;
        }
        return str + "exceptionType=[" + cls + "], fallback=[" + obj + "]";
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() {
        ListenableFuture listenableFuture = this.inputFuture;
        Class cls = this.exceptionType;
        Object obj = this.fallback;
        if (((obj == null) || ((listenableFuture == 0) | (cls == null))) || (this.value instanceof AbstractFuture.Cancellation)) {
            return;
        }
        this.inputFuture = null;
        try {
            th = listenableFuture instanceof InternalFutureFailureAccess ? ((InternalFutureFailureAccess) listenableFuture).tryInternalFastPathGetFailure() : null;
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                cause = new NullPointerException("Future type " + listenableFuture.getClass() + " threw " + e.getClass() + " without a cause");
            }
            th = cause;
        } catch (Throwable th) {
            th = th;
        }
        Object done = th == null ? Futures.getDone(listenableFuture) : null;
        if (th == null) {
            set(done);
            return;
        }
        if (!cls.isInstance(th)) {
            setFuture(listenableFuture);
            return;
        }
        try {
            Object objDoFallback = doFallback(obj, th);
            this.exceptionType = null;
            this.fallback = null;
            setResult(objDoFallback);
        } catch (Throwable th2) {
            try {
                if (th2 instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                setException(th2);
            } finally {
                this.exceptionType = null;
                this.fallback = null;
            }
        }
    }

    public abstract void setResult(Object obj);
}
