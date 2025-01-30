package androidx.concurrent.futures;

import androidx.concurrent.futures.AbstractResolvableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CallbackToFutureAdapter {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Completer {
        public boolean attemptedSetting;
        public ResolvableFuture cancellationFuture = ResolvableFuture.create();
        public SafeFuture future;
        public Object tag;

        public final void finalize() {
            ResolvableFuture resolvableFuture;
            SafeFuture safeFuture = this.future;
            if (safeFuture != null && !safeFuture.isDone()) {
                FutureGarbageCollectedException futureGarbageCollectedException = new FutureGarbageCollectedException("The completer object was garbage collected - this future would otherwise never complete. The tag was: " + this.tag);
                SafeFuture.C01131 c01131 = safeFuture.delegate;
                c01131.getClass();
                if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(c01131, null, new AbstractResolvableFuture.Failure(futureGarbageCollectedException))) {
                    AbstractResolvableFuture.complete(c01131);
                }
            }
            if (this.attemptedSetting || (resolvableFuture = this.cancellationFuture) == null) {
                return;
            }
            resolvableFuture.set(null);
        }

        /* JADX WARN: Code restructure failed: missing block: B:9:0x0020, code lost:
        
            if (r6 != false) goto L14;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void set(Object obj) {
            boolean z;
            boolean z2 = true;
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.future;
            if (safeFuture != null) {
                SafeFuture.C01131 c01131 = safeFuture.delegate;
                c01131.getClass();
                if (obj == null) {
                    obj = AbstractResolvableFuture.NULL;
                }
                if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(c01131, null, obj)) {
                    AbstractResolvableFuture.complete(c01131);
                    z = true;
                } else {
                    z = false;
                }
            }
            z2 = false;
            if (z2) {
                this.tag = null;
                this.future = null;
                this.cancellationFuture = null;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:6:0x0021, code lost:
        
            if (r6 != false) goto L11;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void setException(Throwable th) {
            boolean z;
            boolean z2 = true;
            this.attemptedSetting = true;
            SafeFuture safeFuture = this.future;
            if (safeFuture != null) {
                SafeFuture.C01131 c01131 = safeFuture.delegate;
                c01131.getClass();
                if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(c01131, null, new AbstractResolvableFuture.Failure(th))) {
                    AbstractResolvableFuture.complete(c01131);
                    z = true;
                } else {
                    z = false;
                }
            }
            z2 = false;
            if (z2) {
                this.tag = null;
                this.future = null;
                this.cancellationFuture = null;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    final class FutureGarbageCollectedException extends Throwable {
        public FutureGarbageCollectedException(String str) {
            super(str);
        }

        @Override // java.lang.Throwable
        public final synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Resolver {
        Object attachCompleter(Completer completer);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SafeFuture implements ListenableFuture {
        public final WeakReference completerWeakReference;
        public final C01131 delegate = new AbstractResolvableFuture() { // from class: androidx.concurrent.futures.CallbackToFutureAdapter.SafeFuture.1
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
            }
        } catch (Exception e) {
            SafeFuture.C01131 c01131 = safeFuture.delegate;
            c01131.getClass();
            if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(c01131, null, new AbstractResolvableFuture.Failure(e))) {
                AbstractResolvableFuture.complete(c01131);
            }
        }
        return safeFuture;
    }
}
