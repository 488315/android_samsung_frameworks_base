package com.google.common.util.concurrent;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.Objects;
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
import sun.misc.Unsafe;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractFuture extends InternalFutureFailureAccess implements ListenableFuture {
    public static final AtomicHelper ATOMIC_HELPER;
    public static final boolean GENERATE_CANCELLATION_CAUSES;
    public static final Object NULL;
    public static final LazyLogger log;
    public volatile Listener listeners;
    public volatile Object value;
    public volatile Waiter waiters;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class AtomicHelper {
        private AtomicHelper() {
        }

        public abstract boolean casListeners(AbstractFuture abstractFuture, Listener listener, Listener listener2);

        public abstract boolean casValue(AbstractFuture abstractFuture, Object obj, Object obj2);

        public abstract boolean casWaiters(AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2);

        public abstract Listener gasListeners(AbstractFuture abstractFuture, Listener listener);

        public abstract Waiter gasWaiters(AbstractFuture abstractFuture, Waiter waiter);

        public abstract void putNext(Waiter waiter, Waiter waiter2);

        public abstract void putThread(Waiter waiter, Thread thread);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Cancellation {
        public static final Cancellation CAUSELESS_CANCELLED;
        public static final Cancellation CAUSELESS_INTERRUPTED;
        public final Throwable cause;
        public final boolean wasInterrupted;

        static {
            if (AbstractFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
            } else {
                CAUSELESS_CANCELLED = new Cancellation(false, null);
                CAUSELESS_INTERRUPTED = new Cancellation(true, null);
            }
        }

        public Cancellation(boolean z, Throwable th) {
            this.wasInterrupted = z;
            this.cause = th;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Failure {
        public static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") { // from class: com.google.common.util.concurrent.AbstractFuture.Failure.1
            @Override // java.lang.Throwable
            public final synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        public final Throwable exception;

        public Failure(Throwable th) {
            th.getClass();
            this.exception = th;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SafeAtomicHelper extends AtomicHelper {
        public final AtomicReferenceFieldUpdater listenersUpdater;
        public final AtomicReferenceFieldUpdater valueUpdater;
        public final AtomicReferenceFieldUpdater waiterNextUpdater;
        public final AtomicReferenceFieldUpdater waiterThreadUpdater;
        public final AtomicReferenceFieldUpdater waitersUpdater;

        public SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<? super AbstractFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<? super AbstractFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<? super AbstractFuture, Object> atomicReferenceFieldUpdater5) {
            super();
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casListeners(AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            return this.listenersUpdater.compareAndSet(abstractFuture, listener, listener2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casValue(AbstractFuture abstractFuture, Object obj, Object obj2) {
            return this.valueUpdater.compareAndSet(abstractFuture, obj, obj2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casWaiters(AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            return this.waitersUpdater.compareAndSet(abstractFuture, waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Listener gasListeners(AbstractFuture abstractFuture, Listener listener) {
            return (Listener) this.listenersUpdater.getAndSet(abstractFuture, listener);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Waiter gasWaiters(AbstractFuture abstractFuture, Waiter waiter) {
            return (Waiter) this.waitersUpdater.getAndSet(abstractFuture, waiter);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putThread(Waiter waiter, Thread thread) {
            this.waiterThreadUpdater.lazySet(waiter, thread);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SetFuture implements Runnable {
        public final ListenableFuture future;
        public final AbstractFuture owner;

        public SetFuture(AbstractFuture abstractFuture, ListenableFuture listenableFuture) {
            this.owner = abstractFuture;
            this.future = listenableFuture;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.owner.value != this) {
                return;
            }
            if (AbstractFuture.ATOMIC_HELPER.casValue(this.owner, this, AbstractFuture.getFutureValue(this.future))) {
                AbstractFuture.complete(this.owner, false);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SynchronizedHelper extends AtomicHelper {
        private SynchronizedHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casListeners(AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.listeners != listener) {
                        return false;
                    }
                    abstractFuture.listeners = listener2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casValue(AbstractFuture abstractFuture, Object obj, Object obj2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.value != obj) {
                        return false;
                    }
                    abstractFuture.value = obj2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casWaiters(AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.waiters != waiter) {
                        return false;
                    }
                    abstractFuture.waiters = waiter2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Listener gasListeners(AbstractFuture abstractFuture, Listener listener) {
            Listener listener2;
            synchronized (abstractFuture) {
                listener2 = abstractFuture.listeners;
                if (listener2 != listener) {
                    abstractFuture.listeners = listener;
                }
            }
            return listener2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Waiter gasWaiters(AbstractFuture abstractFuture, Waiter waiter) {
            Waiter waiter2;
            synchronized (abstractFuture) {
                waiter2 = abstractFuture.waiters;
                if (waiter2 != waiter) {
                    abstractFuture.waiters = waiter;
                }
            }
            return waiter2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putThread(Waiter waiter, Thread thread) {
            waiter.thread = thread;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Trusted extends ListenableFuture {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class TrustedFuture extends AbstractFuture implements Trusted {
        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public final boolean isCancelled() {
            return this.value instanceof Cancellation;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UnsafeAtomicHelper extends AtomicHelper {
        public static final long LISTENERS_OFFSET;
        public static final Unsafe UNSAFE;
        public static final long VALUE_OFFSET;
        public static final long WAITERS_OFFSET;
        public static final long WAITER_NEXT_OFFSET;
        public static final long WAITER_THREAD_OFFSET;

        static {
            Unsafe unsafe;
            try {
                try {
                    unsafe = Unsafe.getUnsafe();
                } catch (SecurityException unused) {
                    unsafe = (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: com.google.common.util.concurrent.AbstractFuture.UnsafeAtomicHelper.1
                        @Override // java.security.PrivilegedExceptionAction
                        public final Object run() {
                            for (Field field : Unsafe.class.getDeclaredFields()) {
                                field.setAccessible(true);
                                Object obj = field.get(null);
                                if (Unsafe.class.isInstance(obj)) {
                                    return (Unsafe) Unsafe.class.cast(obj);
                                }
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    });
                }
                try {
                    WAITERS_OFFSET = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField("waiters"));
                    LISTENERS_OFFSET = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField("listeners"));
                    VALUE_OFFSET = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField("value"));
                    WAITER_THREAD_OFFSET = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("thread"));
                    WAITER_NEXT_OFFSET = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("next"));
                    UNSAFE = unsafe;
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            } catch (PrivilegedActionException e2) {
                throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
            }
        }

        private UnsafeAtomicHelper() {
            super();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casListeners(AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, LISTENERS_OFFSET, listener, listener2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casValue(AbstractFuture abstractFuture, Object obj, Object obj2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, VALUE_OFFSET, obj, obj2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casWaiters(AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, WAITERS_OFFSET, waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Listener gasListeners(AbstractFuture abstractFuture, Listener listener) {
            Listener listener2;
            do {
                listener2 = abstractFuture.listeners;
                if (listener == listener2) {
                    break;
                }
            } while (!casListeners(abstractFuture, listener2, listener));
            return listener2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Waiter gasWaiters(AbstractFuture abstractFuture, Waiter waiter) {
            Waiter waiter2;
            do {
                waiter2 = abstractFuture.waiters;
                if (waiter == waiter2) {
                    break;
                }
            } while (!casWaiters(abstractFuture, waiter2, waiter));
            return waiter2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putNext(Waiter waiter, Waiter waiter2) {
            UNSAFE.putObject(waiter, WAITER_NEXT_OFFSET, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putThread(Waiter waiter, Thread thread) {
            UNSAFE.putObject(waiter, WAITER_THREAD_OFFSET, thread);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Waiter {
        public static final Waiter TOMBSTONE = new Waiter(false);
        public volatile Waiter next;
        public volatile Thread thread;

        public Waiter(boolean z) {
        }

        public Waiter() {
            AbstractFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16, types: [java.util.logging.Logger] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.google.common.util.concurrent.AbstractFuture$1] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r2v4 */
    static {
        boolean z;
        Throwable th;
        AtomicHelper atomicHelper;
        try {
            z = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
        } catch (SecurityException unused) {
            z = false;
        }
        GENERATE_CANCELLATION_CAUSES = z;
        log = new LazyLogger(AbstractFuture.class);
        ?? r2 = 0;
        r2 = 0;
        try {
            atomicHelper = new UnsafeAtomicHelper();
            th = null;
        } catch (Error | Exception e) {
            th = e;
            try {
                atomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "thread"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "next"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Waiter.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Listener.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Object.class, "value"));
            } catch (Error | Exception e2) {
                SynchronizedHelper synchronizedHelper = new SynchronizedHelper();
                r2 = e2;
                atomicHelper = synchronizedHelper;
            }
        }
        ATOMIC_HELPER = atomicHelper;
        if (r2 != 0) {
            LazyLogger lazyLogger = log;
            Logger logger = lazyLogger.get();
            Level level = Level.SEVERE;
            logger.log(level, "UnsafeAtomicHelper is broken!", th);
            lazyLogger.get().log(level, "SafeAtomicHelper is broken!", r2);
        }
        NULL = new Object();
    }

    public static void complete(AbstractFuture abstractFuture, boolean z) {
        Listener listener = null;
        while (true) {
            abstractFuture.getClass();
            for (Waiter gasWaiters = ATOMIC_HELPER.gasWaiters(abstractFuture, Waiter.TOMBSTONE); gasWaiters != null; gasWaiters = gasWaiters.next) {
                Thread thread = gasWaiters.thread;
                if (thread != null) {
                    gasWaiters.thread = null;
                    LockSupport.unpark(thread);
                }
            }
            if (z) {
                z = false;
            }
            abstractFuture.afterDone();
            Listener listener2 = listener;
            Listener gasListeners = ATOMIC_HELPER.gasListeners(abstractFuture, Listener.TOMBSTONE);
            Listener listener3 = listener2;
            while (gasListeners != null) {
                Listener listener4 = gasListeners.next;
                gasListeners.next = listener3;
                listener3 = gasListeners;
                gasListeners = listener4;
            }
            while (listener3 != null) {
                listener = listener3.next;
                Runnable runnable = listener3.task;
                Objects.requireNonNull(runnable);
                if (runnable instanceof SetFuture) {
                    SetFuture setFuture = (SetFuture) runnable;
                    abstractFuture = setFuture.owner;
                    if (abstractFuture.value == setFuture) {
                        if (ATOMIC_HELPER.casValue(abstractFuture, setFuture, getFutureValue(setFuture.future))) {
                            break;
                        }
                    } else {
                        continue;
                    }
                } else {
                    Executor executor = listener3.executor;
                    Objects.requireNonNull(executor);
                    executeListener(runnable, executor);
                }
                listener3 = listener;
            }
            return;
        }
    }

    public static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (Exception e) {
            log.get().log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e);
        }
    }

    public static Object getDoneValue(Object obj) {
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

    /* JADX WARN: Multi-variable type inference failed */
    public static Object getFutureValue(ListenableFuture listenableFuture) {
        Throwable tryInternalFastPathGetFailure;
        if (listenableFuture instanceof Trusted) {
            Object obj = ((AbstractFuture) listenableFuture).value;
            if (obj instanceof Cancellation) {
                Cancellation cancellation = (Cancellation) obj;
                if (cancellation.wasInterrupted) {
                    obj = cancellation.cause != null ? new Cancellation(false, cancellation.cause) : Cancellation.CAUSELESS_CANCELLED;
                }
            }
            Objects.requireNonNull(obj);
            return obj;
        }
        if ((listenableFuture instanceof InternalFutureFailureAccess) && (tryInternalFastPathGetFailure = ((InternalFutureFailureAccess) listenableFuture).tryInternalFastPathGetFailure()) != null) {
            return new Failure(tryInternalFastPathGetFailure);
        }
        boolean isCancelled = listenableFuture.isCancelled();
        if ((!GENERATE_CANCELLATION_CAUSES) && isCancelled) {
            Cancellation cancellation2 = Cancellation.CAUSELESS_CANCELLED;
            Objects.requireNonNull(cancellation2);
            return cancellation2;
        }
        try {
            Object uninterruptibly = getUninterruptibly(listenableFuture);
            if (!isCancelled) {
                return uninterruptibly == null ? NULL : uninterruptibly;
            }
            return new Cancellation(false, new IllegalArgumentException("get() did not throw CancellationException, despite reporting isCancelled() == true: " + listenableFuture));
        } catch (Error | Exception e) {
            return new Failure(e);
        } catch (CancellationException e2) {
            if (isCancelled) {
                return new Cancellation(false, e2);
            }
            return new Failure(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: " + listenableFuture, e2));
        } catch (ExecutionException e3) {
            if (!isCancelled) {
                return new Failure(e3.getCause());
            }
            return new Cancellation(false, new IllegalArgumentException("get() did not throw CancellationException, despite reporting isCancelled() == true: " + listenableFuture, e3));
        }
    }

    public static Object getUninterruptibly(ListenableFuture listenableFuture) {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                obj = listenableFuture.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return obj;
    }

    public final void addDoneString(StringBuilder sb) {
        try {
            Object uninterruptibly = getUninterruptibly(this);
            sb.append("SUCCESS, result=[");
            appendResultObject(sb, uninterruptibly);
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (ExecutionException e) {
            sb.append("FAILURE, cause=[");
            sb.append(e.getCause());
            sb.append("]");
        } catch (Exception e2) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e2.getClass());
            sb.append(" thrown from get()]");
        }
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        Listener listener;
        executor.getClass();
        if (!isDone() && (listener = this.listeners) != Listener.TOMBSTONE) {
            Listener listener2 = new Listener(runnable, executor);
            do {
                listener2.next = listener;
                if (ATOMIC_HELPER.casListeners(this, listener, listener2)) {
                    return;
                } else {
                    listener = this.listeners;
                }
            } while (listener != Listener.TOMBSTONE);
        }
        executeListener(runnable, executor);
    }

    public final void appendResultObject(StringBuilder sb, Object obj) {
        if (obj == null) {
            sb.append("null");
        } else {
            if (obj == this) {
                sb.append("this future");
                return;
            }
            sb.append(obj.getClass().getName());
            sb.append("@");
            sb.append(Integer.toHexString(System.identityHashCode(obj)));
        }
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        Cancellation cancellation;
        Object obj = this.value;
        if (!(obj == null) && !(obj instanceof SetFuture)) {
            return false;
        }
        if (GENERATE_CANCELLATION_CAUSES) {
            cancellation = new Cancellation(z, new CancellationException("Future.cancel() was called."));
        } else {
            cancellation = z ? Cancellation.CAUSELESS_INTERRUPTED : Cancellation.CAUSELESS_CANCELLED;
            Objects.requireNonNull(cancellation);
        }
        boolean z2 = false;
        while (true) {
            if (ATOMIC_HELPER.casValue(this, obj, cancellation)) {
                complete(this, z);
                if (!(obj instanceof SetFuture)) {
                    break;
                }
                ListenableFuture listenableFuture = ((SetFuture) obj).future;
                if (!(listenableFuture instanceof Trusted)) {
                    listenableFuture.cancel(z);
                    break;
                }
                this = (AbstractFuture) listenableFuture;
                obj = this.value;
                if (!(obj == null) && !(obj instanceof SetFuture)) {
                    break;
                }
                z2 = true;
            } else {
                obj = this.value;
                if (!(obj instanceof SetFuture)) {
                    return z2;
                }
            }
        }
        return true;
    }

    @Override // java.util.concurrent.Future
    public Object get(long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.value;
        if ((obj != null) && (!(obj instanceof SetFuture))) {
            return getDoneValue(obj);
        }
        long nanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
        if (nanos >= 1000) {
            Waiter waiter = this.waiters;
            if (waiter != Waiter.TOMBSTONE) {
                Waiter waiter2 = new Waiter();
                do {
                    AtomicHelper atomicHelper = ATOMIC_HELPER;
                    atomicHelper.putNext(waiter2, waiter);
                    if (atomicHelper.casWaiters(this, waiter, waiter2)) {
                        do {
                            LockSupport.parkNanos(this, Math.min(nanos, 2147483647999999999L));
                            if (Thread.interrupted()) {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.value;
                            if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                                return getDoneValue(obj2);
                            }
                            nanos = nanoTime - System.nanoTime();
                        } while (nanos >= 1000);
                        removeWaiter(waiter2);
                    } else {
                        waiter = this.waiters;
                    }
                } while (waiter != Waiter.TOMBSTONE);
            }
            Object obj3 = this.value;
            Objects.requireNonNull(obj3);
            return getDoneValue(obj3);
        }
        while (nanos > 0) {
            Object obj4 = this.value;
            if ((obj4 != null) && (!(obj4 instanceof SetFuture))) {
                return getDoneValue(obj4);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            nanos = nanoTime - System.nanoTime();
        }
        String abstractFuture = toString();
        String timeUnit2 = timeUnit.toString();
        Locale locale = Locale.ROOT;
        String lowerCase = timeUnit2.toLowerCase(locale);
        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Waited ", j, " ");
        m.append(timeUnit.toString().toLowerCase(locale));
        String sb = m.toString();
        if (nanos + 1000 < 0) {
            String m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, " (plus ");
            long j2 = -nanos;
            long convert = timeUnit.convert(j2, TimeUnit.NANOSECONDS);
            long nanos2 = j2 - timeUnit.toNanos(convert);
            boolean z = convert == 0 || nanos2 > 1000;
            if (convert > 0) {
                String str = m2 + convert + " " + lowerCase;
                if (z) {
                    str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ",");
                }
                m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " ");
            }
            if (z) {
                m2 = m2 + nanos2 + " nanoseconds ";
            }
            sb = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m2, "delay)");
        }
        if (isDone()) {
            throw new TimeoutException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, " but future completed as timeout expired"));
        }
        throw new TimeoutException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sb, " for ", abstractFuture));
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return (!(r2 instanceof SetFuture)) & (this.value != null);
    }

    public final void maybePropagateCancellationTo(Future future) {
        if ((future != null) && isCancelled()) {
            future.cancel(wasInterrupted());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String pendingToString() {
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
    }

    public final void removeWaiter(Waiter waiter) {
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

    public boolean set(Object obj) {
        if (obj == null) {
            obj = NULL;
        }
        if (!ATOMIC_HELPER.casValue(this, null, obj)) {
            return false;
        }
        complete(this, false);
        return true;
    }

    public boolean setException(Throwable th) {
        th.getClass();
        if (!ATOMIC_HELPER.casValue(this, null, new Failure(th))) {
            return false;
        }
        complete(this, false);
        return true;
    }

    public final void setFuture(ListenableFuture listenableFuture) {
        Failure failure;
        listenableFuture.getClass();
        Object obj = this.value;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (ATOMIC_HELPER.casValue(this, null, getFutureValue(listenableFuture))) {
                    complete(this, false);
                    return;
                }
                return;
            }
            SetFuture setFuture = new SetFuture(this, listenableFuture);
            if (ATOMIC_HELPER.casValue(this, null, setFuture)) {
                try {
                    listenableFuture.addListener(setFuture, DirectExecutor.INSTANCE);
                    return;
                } catch (Throwable th) {
                    try {
                        failure = new Failure(th);
                    } catch (Error | Exception unused) {
                        failure = Failure.FALLBACK_INSTANCE;
                    }
                    ATOMIC_HELPER.casValue(this, setFuture, failure);
                    return;
                }
            }
            obj = this.value;
        }
        if (obj instanceof Cancellation) {
            listenableFuture.cancel(((Cancellation) obj).wasInterrupted);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x009d, code lost:
    
        if (r3.isEmpty() != false) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String toString() {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.Class r1 = r6.getClass()
            java.lang.String r1 = r1.getName()
            java.lang.String r2 = "com.google.common.util.concurrent."
            boolean r1 = r1.startsWith(r2)
            if (r1 == 0) goto L21
            java.lang.Class r1 = r6.getClass()
            java.lang.String r1 = r1.getSimpleName()
            r0.append(r1)
            goto L2c
        L21:
            java.lang.Class r1 = r6.getClass()
            java.lang.String r1 = r1.getName()
            r0.append(r1)
        L2c:
            r1 = 64
            r0.append(r1)
            int r1 = java.lang.System.identityHashCode(r6)
            java.lang.String r1 = java.lang.Integer.toHexString(r1)
            r0.append(r1)
            java.lang.String r1 = "[status="
            r0.append(r1)
            boolean r1 = r6.isCancelled()
            java.lang.String r2 = "]"
            if (r1 == 0) goto L50
            java.lang.String r6 = "CANCELLED"
            r0.append(r6)
            goto Lcf
        L50:
            boolean r1 = r6.isDone()
            if (r1 == 0) goto L5b
            r6.addDoneString(r0)
            goto Lcf
        L5b:
            int r1 = r0.length()
            java.lang.String r3 = "PENDING"
            r0.append(r3)
            java.lang.Object r3 = r6.value
            boolean r4 = r3 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            java.lang.String r5 = "Exception thrown from implementation: "
            if (r4 == 0) goto L91
            java.lang.String r4 = ", setFuture=["
            r0.append(r4)
            com.google.common.util.concurrent.AbstractFuture$SetFuture r3 = (com.google.common.util.concurrent.AbstractFuture.SetFuture) r3
            com.google.common.util.concurrent.ListenableFuture r3 = r3.future
            if (r3 != r6) goto L7f
            java.lang.String r3 = "this future"
            r0.append(r3)     // Catch: java.lang.Throwable -> L7d
            goto L8d
        L7d:
            r3 = move-exception
            goto L83
        L7f:
            r0.append(r3)     // Catch: java.lang.Throwable -> L7d
            goto L8d
        L83:
            r0.append(r5)
            java.lang.Class r3 = r3.getClass()
            r0.append(r3)
        L8d:
            r0.append(r2)
            goto Lbf
        L91:
            java.lang.String r3 = r6.pendingToString()     // Catch: java.lang.Throwable -> La1
            int r4 = com.google.common.base.Platform.$r8$clinit     // Catch: java.lang.Throwable -> La1
            if (r3 == 0) goto L9f
            boolean r4 = r3.isEmpty()     // Catch: java.lang.Throwable -> La1
            if (r4 == 0) goto Lb2
        L9f:
            r3 = 0
            goto Lb2
        La1:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r5)
            java.lang.Class r3 = r3.getClass()
            r4.append(r3)
            java.lang.String r3 = r4.toString()
        Lb2:
            if (r3 == 0) goto Lbf
            java.lang.String r4 = ", info=["
            r0.append(r4)
            r0.append(r3)
            r0.append(r2)
        Lbf:
            boolean r3 = r6.isDone()
            if (r3 == 0) goto Lcf
            int r3 = r0.length()
            r0.delete(r1, r3)
            r6.addDoneString(r0)
        Lcf:
            r0.append(r2)
            java.lang.String r6 = r0.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.toString():java.lang.String");
    }

    @Override // com.google.common.util.concurrent.internal.InternalFutureFailureAccess
    public final Throwable tryInternalFastPathGetFailure() {
        if (!(this instanceof Trusted)) {
            return null;
        }
        Object obj = this.value;
        if (obj instanceof Failure) {
            return ((Failure) obj).exception;
        }
        return null;
    }

    public final boolean wasInterrupted() {
        Object obj = this.value;
        return (obj instanceof Cancellation) && ((Cancellation) obj).wasInterrupted;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Listener {
        public static final Listener TOMBSTONE = new Listener();
        public final Executor executor;
        public Listener next;
        public final Runnable task;

        public Listener(Runnable runnable, Executor executor) {
            this.task = runnable;
            this.executor = executor;
        }

        public Listener() {
            this.task = null;
            this.executor = null;
        }
    }

    public void afterDone() {
    }

    @Override // java.util.concurrent.Future
    public Object get() {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.value;
            if ((obj2 != null) & (!(obj2 instanceof SetFuture))) {
                return getDoneValue(obj2);
            }
            Waiter waiter = this.waiters;
            if (waiter != Waiter.TOMBSTONE) {
                Waiter waiter2 = new Waiter();
                do {
                    AtomicHelper atomicHelper = ATOMIC_HELPER;
                    atomicHelper.putNext(waiter2, waiter);
                    if (atomicHelper.casWaiters(this, waiter, waiter2)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.value;
                            } else {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof SetFuture))));
                        return getDoneValue(obj);
                    }
                    waiter = this.waiters;
                } while (waiter != Waiter.TOMBSTONE);
            }
            Object obj3 = this.value;
            Objects.requireNonNull(obj3);
            return getDoneValue(obj3);
        }
        throw new InterruptedException();
    }
}
