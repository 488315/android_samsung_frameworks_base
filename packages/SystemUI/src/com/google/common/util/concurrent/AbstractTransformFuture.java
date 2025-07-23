package com.google.common.util.concurrent;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.google.common.base.Function;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.FluentFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractTransformFuture extends FluentFuture.TrustedFuture implements Runnable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Object function;
    public ListenableFuture inputFuture;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AsyncTransformFuture extends AbstractTransformFuture {
        public AsyncTransformFuture(ListenableFuture listenableFuture, AsyncFunction asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final Object doTransform(Object obj, Object obj2) {
            ListenableFuture apply = ((AsyncFunction) obj).apply(obj2);
            apply.getClass();
            return apply;
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final void setResult(Object obj) {
            setFuture((ListenableFuture) obj);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TransformFuture extends AbstractTransformFuture {
        public TransformFuture(ListenableFuture listenableFuture, Function function) {
            super(listenableFuture, function);
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final Object doTransform(Object obj, Object obj2) {
            return ((Function) obj).apply(obj2);
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final void setResult(Object obj) {
            set(obj);
        }
    }

    public AbstractTransformFuture(ListenableFuture listenableFuture, Object obj) {
        listenableFuture.getClass();
        this.inputFuture = listenableFuture;
        obj.getClass();
        this.function = obj;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.function = null;
    }

    public abstract Object doTransform(Object obj, Object obj2);

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String pendingToString() {
        String str;
        ListenableFuture listenableFuture = this.inputFuture;
        Object obj = this.function;
        String pendingToString = super.pendingToString();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = "";
        }
        if (obj == null) {
            if (pendingToString != null) {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, pendingToString);
            }
            return null;
        }
        return str + "function=[" + obj + "]";
    }

    @Override // java.lang.Runnable
    public final void run() {
        ListenableFuture listenableFuture = this.inputFuture;
        Object obj = this.function;
        if (((this.value instanceof AbstractFuture.Cancellation) | (listenableFuture == null)) || (obj == null)) {
            return;
        }
        this.inputFuture = null;
        if (listenableFuture.isCancelled()) {
            setFuture(listenableFuture);
            return;
        }
        try {
            try {
                Object doTransform = doTransform(obj, Futures.getDone(listenableFuture));
                this.function = null;
                setResult(doTransform);
            } catch (Throwable th) {
                try {
                    if (th instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                    }
                    setException(th);
                } finally {
                    this.function = null;
                }
            }
        } catch (Error e) {
            setException(e);
        } catch (CancellationException unused) {
            cancel(false);
        } catch (ExecutionException e2) {
            setException(e2.getCause());
        } catch (Exception e3) {
            setException(e3);
        }
    }

    public abstract void setResult(Object obj);
}
