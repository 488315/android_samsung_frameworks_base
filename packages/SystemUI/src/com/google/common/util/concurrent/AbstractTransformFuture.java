package com.google.common.util.concurrent;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.google.common.base.Function;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.FluentFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes4.dex */
public abstract class AbstractTransformFuture extends FluentFuture.TrustedFuture implements Runnable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Object function;
    public ListenableFuture inputFuture;

    public final class AsyncTransformFuture extends AbstractTransformFuture {
        public AsyncTransformFuture(ListenableFuture listenableFuture, AsyncFunction asyncFunction) {
            super(listenableFuture, asyncFunction);
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final Object doTransform(Object obj, Object obj2) {
            ListenableFuture listenableFutureApply = ((AsyncFunction) obj).apply(obj2);
            listenableFutureApply.getClass();
            return listenableFutureApply;
        }

        @Override // com.google.common.util.concurrent.AbstractTransformFuture
        public final void setResult(Object obj) {
            setFuture((ListenableFuture) obj);
        }
    }

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
        String strPendingToString = super.pendingToString();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = "";
        }
        if (obj == null) {
            if (strPendingToString != null) {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, strPendingToString);
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
                Object objDoTransform = doTransform(obj, Futures.getDone(listenableFuture));
                this.function = null;
                setResult(objDoTransform);
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
