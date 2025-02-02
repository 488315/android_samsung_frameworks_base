package androidx.concurrent.futures;

import androidx.profileinstaller.ProfileVerifier;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public abstract class AbstractResolvableFuture<V> implements Future {
    static final AtomicHelper ATOMIC_HELPER;
    private static final Object NULL;
    volatile Listener listeners;
    volatile Object value;
    volatile Waiter waiters;
    static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Logger log = Logger.getLogger(AbstractResolvableFuture.class.getName());

    private static abstract class AtomicHelper {
        abstract boolean casListeners(AbstractResolvableFuture abstractResolvableFuture, Listener listener);

        abstract boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2);

        abstract boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2);

        abstract void putNext(Waiter waiter, Waiter waiter2);

        abstract void putThread(Waiter waiter, Thread thread);
    }

    private static final class Cancellation {
        static final Cancellation CAUSELESS_CANCELLED;
        static final Cancellation CAUSELESS_INTERRUPTED;
        final Throwable cause;

        static {
            if (AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
            } else {
                CAUSELESS_CANCELLED = new Cancellation(false, null);
                CAUSELESS_INTERRUPTED = new Cancellation(true, null);
            }
        }

        Cancellation(boolean z, Throwable th) {
            this.cause = th;
        }
    }

    private static final class Failure {
        final Throwable exception;

        static {
            new Failure(new Throwable("Failure occurred while trying to finish a future.") { // from class: androidx.concurrent.futures.AbstractResolvableFuture.Failure.1
                @Override // java.lang.Throwable
                public final synchronized Throwable fillInStackTrace() {
                    return this;
                }
            });
        }

        Failure(Throwable th) {
            boolean z = AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES;
            th.getClass();
            this.exception = th;
        }
    }

    private static final class Listener {
        static final Listener TOMBSTONE = new Listener();
        Listener next;
        final Runnable task = null;
        final Executor executor = null;

        Listener() {
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> waitersUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> atomicReferenceFieldUpdater5) {
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final boolean casListeners(AbstractResolvableFuture abstractResolvableFuture, Listener listener) {
            return this.listenersUpdater.compareAndSet(abstractResolvableFuture, listener, Listener.TOMBSTONE);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2) {
            return this.valueUpdater.compareAndSet(abstractResolvableFuture, obj, obj2);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2) {
            return this.waitersUpdater.compareAndSet(abstractResolvableFuture, waiter, waiter2);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final void putThread(Waiter waiter, Thread thread) {
            this.waiterThreadUpdater.lazySet(waiter, thread);
        }
    }

    private static final class SetFuture<V> implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            throw null;
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final boolean casListeners(AbstractResolvableFuture abstractResolvableFuture, Listener listener) {
            Listener listener2 = Listener.TOMBSTONE;
            synchronized (abstractResolvableFuture) {
                if (abstractResolvableFuture.listeners != listener) {
                    return false;
                }
                abstractResolvableFuture.listeners = listener2;
                return true;
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2) {
            synchronized (abstractResolvableFuture) {
                if (abstractResolvableFuture.value != obj) {
                    return false;
                }
                abstractResolvableFuture.value = obj2;
                return true;
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractResolvableFuture) {
                if (abstractResolvableFuture.waiters != waiter) {
                    return false;
                }
                abstractResolvableFuture.waiters = waiter2;
                return true;
            }
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        @Override // androidx.concurrent.futures.AbstractResolvableFuture.AtomicHelper
        final void putThread(Waiter waiter, Thread thread) {
            waiter.thread = thread;
        }
    }

    private static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(0);
        volatile Waiter next;
        volatile Thread thread;

        Waiter(int i) {
        }

        Waiter() {
            AbstractResolvableFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }
    }

    static {
        AtomicHelper synchronizedHelper;
        try {
            synchronizedHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "thread"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "next"), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Waiter.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Listener.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(AbstractResolvableFuture.class, Object.class, "value"));
            th = null;
        } catch (Throwable th) {
            th = th;
            synchronizedHelper = new SynchronizedHelper();
        }
        ATOMIC_HELPER = synchronizedHelper;
        if (th != null) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
        NULL = new Object();
    }

    protected AbstractResolvableFuture() {
    }

    private void addDoneString(StringBuilder sb) {
        V v;
        boolean z = false;
        while (true) {
            try {
                try {
                    v = get();
                    break;
                } catch (InterruptedException unused) {
                    z = true;
                } catch (Throwable th) {
                    if (z) {
                        Thread.currentThread().interrupt();
                    }
                    throw th;
                }
            } catch (CancellationException unused2) {
                sb.append("CANCELLED");
                return;
            } catch (RuntimeException e) {
                sb.append("UNKNOWN, cause=[");
                sb.append(e.getClass());
                sb.append(" thrown from get()]");
                return;
            } catch (ExecutionException e2) {
                sb.append("FAILURE, cause=[");
                sb.append(e2.getCause());
                sb.append("]");
                return;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        sb.append("SUCCESS, result=[");
        sb.append(v == this ? "this future" : String.valueOf(v));
        sb.append("]");
    }

    static void complete(AbstractResolvableFuture<?> abstractResolvableFuture) {
        Waiter waiter;
        Listener listener;
        do {
            waiter = abstractResolvableFuture.waiters;
        } while (!ATOMIC_HELPER.casWaiters(abstractResolvableFuture, waiter, Waiter.TOMBSTONE));
        while (waiter != null) {
            Thread thread = waiter.thread;
            if (thread != null) {
                waiter.thread = null;
                LockSupport.unpark(thread);
            }
            waiter = waiter.next;
        }
        do {
            listener = abstractResolvableFuture.listeners;
        } while (!ATOMIC_HELPER.casListeners(abstractResolvableFuture, listener));
        Listener listener2 = null;
        while (listener != null) {
            Listener listener3 = listener.next;
            listener.next = listener2;
            listener2 = listener;
            listener = listener3;
        }
        while (listener2 != null) {
            Listener listener4 = listener2.next;
            Runnable runnable = listener2.task;
            if (runnable instanceof SetFuture) {
                ((SetFuture) runnable).getClass();
                throw null;
            }
            Executor executor = listener2.executor;
            try {
                executor.execute(runnable);
            } catch (RuntimeException e) {
                log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e);
            }
            listener2 = listener4;
        }
    }

    private static Object getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            Throwable th = ((Cancellation) obj).cause;
            CancellationException cancellationException = new CancellationException("Task was cancelled.");
            cancellationException.initCause(th);
            throw cancellationException;
        }
        if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).exception);
        }
        if (obj == NULL) {
            return null;
        }
        return obj;
    }

    private void removeWaiter(Waiter waiter) {
        waiter.thread = null;
        while (true) {
            Waiter waiter2 = this.waiters;
            if (waiter2 == Waiter.TOMBSTONE) {
                return;
            }
            Waiter waiter3 = null;
            while (waiter2 != null) {
                Waiter waiter4 = waiter2.next;
                if (waiter2.thread != null) {
                    waiter3 = waiter2;
                } else if (waiter3 != null) {
                    waiter3.next = waiter4;
                    if (waiter3.thread == null) {
                        break;
                    }
                } else if (!ATOMIC_HELPER.casWaiters(this, waiter2, waiter4)) {
                    break;
                }
                waiter2 = waiter4;
            }
            return;
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        Object obj = this.value;
        if ((obj == null) | (obj instanceof SetFuture)) {
            Cancellation cancellation = GENERATE_CANCELLATION_CAUSES ? new Cancellation(z, new CancellationException("Future.cancel() was called.")) : z ? Cancellation.CAUSELESS_INTERRUPTED : Cancellation.CAUSELESS_CANCELLED;
            while (!ATOMIC_HELPER.casValue(this, obj, cancellation)) {
                obj = this.value;
                if (!(obj instanceof SetFuture)) {
                }
            }
            complete(this);
            if (!(obj instanceof SetFuture)) {
                return true;
            }
            ((SetFuture) obj).getClass();
            throw null;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c5  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00b6 -> B:33:0x00bc). Please report as a decompilation issue!!! */
    @Override // java.util.concurrent.Future
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final V get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException, ExecutionException {
        long j2;
        long j3;
        long j4;
        boolean z;
        long j5;
        TimeUnit timeUnit2;
        TimeUnit timeUnit3;
        long j6;
        AbstractResolvableFuture<V> abstractResolvableFuture;
        long nanos = timeUnit.toNanos(j);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.value;
        if ((obj != null) && (!(obj instanceof SetFuture))) {
            return (V) getDoneValue(obj);
        }
        long nanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
        if (nanos >= 1000) {
            Waiter waiter = this.waiters;
            Waiter waiter2 = Waiter.TOMBSTONE;
            if (waiter != waiter2) {
                Waiter waiter3 = new Waiter();
                do {
                    AtomicHelper atomicHelper = ATOMIC_HELPER;
                    atomicHelper.putNext(waiter3, waiter);
                    if (atomicHelper.casWaiters(this, waiter, waiter3)) {
                        do {
                            LockSupport.parkNanos(this, nanos);
                            if (Thread.interrupted()) {
                                removeWaiter(waiter3);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.value;
                            if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                                return (V) getDoneValue(obj2);
                            }
                            nanos = nanoTime - System.nanoTime();
                        } while (nanos >= 1000);
                        removeWaiter(waiter3);
                        z = true;
                        j2 = 1000;
                        j5 = nanos;
                        j3 = nanoTime;
                        timeUnit2 = timeUnit;
                        timeUnit3 = timeUnit2;
                        j6 = j;
                        abstractResolvableFuture = this;
                        j4 = 0;
                        if (j5 <= j4) {
                            String abstractResolvableFuture2 = toString();
                            String timeUnit4 = timeUnit2.toString();
                            Locale locale = Locale.ROOT;
                            String lowerCase = timeUnit4.toLowerCase(locale);
                            String str = "Waited " + j6 + " " + timeUnit2.toString().toLowerCase(locale);
                            if (j5 + j2 < 0) {
                                String m1m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str, " (plus ");
                                long j7 = -j5;
                                long convert = timeUnit3.convert(j7, TimeUnit.NANOSECONDS);
                                long nanos2 = j7 - timeUnit3.toNanos(convert);
                                if (convert != 0 && nanos2 <= j2) {
                                    z = false;
                                }
                                if (convert > 0) {
                                    String str2 = m1m + convert + " " + lowerCase;
                                    if (z) {
                                        str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str2, ",");
                                    }
                                    m1m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str2, " ");
                                }
                                if (z) {
                                    m1m = m1m + nanos2 + " nanoseconds ";
                                }
                                str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(m1m, "delay)");
                            }
                            if (isDone()) {
                                throw new TimeoutException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str, " but future completed as timeout expired"));
                            }
                            throw new TimeoutException(str + " for " + abstractResolvableFuture2);
                        }
                        Object obj3 = abstractResolvableFuture.value;
                        if ((obj3 != null ? z : false) && ((obj3 instanceof SetFuture) ^ z)) {
                            return (V) getDoneValue(obj3);
                        }
                        if (Thread.interrupted()) {
                            throw new InterruptedException();
                        }
                        j5 = j3 - System.nanoTime();
                        j4 = 0;
                        if (j5 <= j4) {
                        }
                    } else {
                        waiter = this.waiters;
                    }
                } while (waiter != waiter2);
            }
            return (V) getDoneValue(this.value);
        }
        j2 = 1000;
        j3 = nanoTime;
        j4 = 0;
        z = true;
        j5 = nanos;
        timeUnit2 = timeUnit;
        timeUnit3 = timeUnit2;
        j6 = j;
        abstractResolvableFuture = this;
        if (j5 <= j4) {
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return (!(r2 instanceof SetFuture)) & (this.value != null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final String pendingToString() {
        Object obj = this.value;
        if (obj instanceof SetFuture) {
            StringBuilder sb = new StringBuilder("setFuture=[");
            ((SetFuture) obj).getClass();
            sb.append("null");
            sb.append("]");
            return sb.toString();
        }
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
    }

    protected boolean set(ProfileVerifier.CompilationStatus compilationStatus) {
        if (!ATOMIC_HELPER.casValue(this, null, compilationStatus)) {
            return false;
        }
        complete(this);
        return true;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (this.value instanceof Cancellation) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(sb);
        } else {
            try {
                str = pendingToString();
            } catch (RuntimeException e) {
                str = "Exception thrown from implementation: " + e.getClass();
            }
            if (str != null && !str.isEmpty()) {
                sb.append("PENDING, info=[");
                sb.append(str);
                sb.append("]");
            } else if (isDone()) {
                addDoneString(sb);
            } else {
                sb.append("PENDING");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // java.util.concurrent.Future
    public final V get() throws InterruptedException, ExecutionException {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.value;
            if ((obj2 != null) & (!(obj2 instanceof SetFuture))) {
                return (V) getDoneValue(obj2);
            }
            Waiter waiter = this.waiters;
            Waiter waiter2 = Waiter.TOMBSTONE;
            if (waiter != waiter2) {
                Waiter waiter3 = new Waiter();
                do {
                    AtomicHelper atomicHelper = ATOMIC_HELPER;
                    atomicHelper.putNext(waiter3, waiter);
                    if (atomicHelper.casWaiters(this, waiter, waiter3)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.value;
                            } else {
                                removeWaiter(waiter3);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof SetFuture))));
                        return (V) getDoneValue(obj);
                    }
                    waiter = this.waiters;
                } while (waiter != waiter2);
            }
            return (V) getDoneValue(this.value);
        }
        throw new InterruptedException();
    }
}
