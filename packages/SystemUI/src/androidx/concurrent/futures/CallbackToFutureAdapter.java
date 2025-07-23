package androidx.concurrent.futures;

import androidx.concurrent.futures.AbstractResolvableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CallbackToFutureAdapter {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Completer {
        public boolean attemptedSetting;
        public ResolvableFuture cancellationFuture = ResolvableFuture.create();
        public SafeFuture future;
        public Object tag;

        public final void finalize() {
            ResolvableFuture resolvableFuture;
            SafeFuture safeFuture = this.future;
            if (safeFuture != null && !safeFuture.delegate.isDone()) {
                safeFuture.delegate.setException(new FutureGarbageCollectedException("The completer object was garbage collected - this future would otherwise never complete. The tag was: " + this.tag));
            }
            if (this.attemptedSetting || (resolvableFuture = this.cancellationFuture) == null) {
                return;
            }
            resolvableFuture.set(null);
        }

        public final void set(Object obj) {
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.future;
            if (safeFuture != null) {
                SafeFuture.AnonymousClass1 anonymousClass1 = safeFuture.delegate;
                anonymousClass1.getClass();
                if (obj == null) {
                    obj = AbstractResolvableFuture.NULL;
                }
                if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(anonymousClass1, null, obj)) {
                    AbstractResolvableFuture.complete(anonymousClass1);
                    this.tag = null;
                    this.future = null;
                    this.cancellationFuture = null;
                }
            }
        }

        public final void setException(Throwable th) {
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.future;
            if (safeFuture == null || !safeFuture.delegate.setException(th)) {
                return;
            }
            this.tag = null;
            this.future = null;
            this.cancellationFuture = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class FutureGarbageCollectedException extends Throwable {
        public FutureGarbageCollectedException(String str) {
            super(str);
        }

        @Override // java.lang.Throwable
        public final synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Resolver {
        Object attachCompleter(Completer completer);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SafeFuture implements ListenableFuture {
        public final WeakReference completerWeakReference;
        public final AnonymousClass1 delegate = new AbstractResolvableFuture() { // from class: androidx.concurrent.futures.CallbackToFutureAdapter.SafeFuture.1
            @Override // androidx.concurrent.futures.AbstractResolvableFuture
            public final String pendingToString() {
                Completer completer = (Completer) SafeFuture.this.completerWeakReference.get();
                if (completer == null) {
                    return "Completer object has been garbage collected, future will fail soon";
                }
                return "tag=[" + completer.tag + "]";
            }
        };

        /* JADX WARN: Type inference failed for: r0v0, types: [androidx.concurrent.futures.CallbackToFutureAdapter$SafeFuture$1] */
        public SafeFuture(Completer completer) {
            this.completerWeakReference = new WeakReference(completer);
        }

        @Override // com.google.common.util.concurrent.ListenableFuture
        public final void addListener(Runnable runnable, Executor executor) {
            addListener(runnable, executor);
        }

        @Override // java.util.concurrent.Future
        public final boolean cancel(boolean z) {
            Completer completer = (Completer) this.completerWeakReference.get();
            boolean cancel = cancel(z);
            if (cancel && completer != null) {
                completer.tag = null;
                completer.future = null;
                completer.cancellationFuture.set(null);
            }
            return cancel;
        }

        @Override // java.util.concurrent.Future
        public final Object get() {
            return get();
        }

        @Override // java.util.concurrent.Future
        public final boolean isCancelled() {
            return this.delegate.value instanceof AbstractResolvableFuture.Cancellation;
        }

        @Override // java.util.concurrent.Future
        public final boolean isDone() {
            return isDone();
        }

        public final String toString() {
            return toString();
        }

        @Override // java.util.concurrent.Future
        public final Object get(long j, TimeUnit timeUnit) {
            return get(j, timeUnit);
        }
    }

    private CallbackToFutureAdapter() {
    }

    public static SafeFuture getFuture(Resolver resolver) {
        Completer completer = new Completer();
        SafeFuture safeFuture = new SafeFuture(completer);
        completer.future = safeFuture;
        completer.tag = resolver.getClass();
        try {
            Object attachCompleter = resolver.attachCompleter(completer);
            if (attachCompleter != null) {
                completer.tag = attachCompleter;
                return safeFuture;
            }
        } catch (Exception e) {
            safeFuture.delegate.setException(e);
        }
        return safeFuture;
    }
}
