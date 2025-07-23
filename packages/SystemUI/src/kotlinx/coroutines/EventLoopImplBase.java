package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.CoroutineContext;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.ThreadSafeHeap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class EventLoopImplBase extends EventLoopImplPlatform implements Delay {
    public final AtomicRef _queue = AtomicFU.atomic((Object) null);
    public final AtomicRef _delayed = AtomicFU.atomic((Object) null);
    public final AtomicBoolean _isCompleted = AtomicFU.atomic(false);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DelayedResumeTask extends DelayedTask {
        public final CancellableContinuation cont;

        public DelayedResumeTask(long j, CancellableContinuation cancellableContinuation) {
            super(j);
            this.cont = cancellableContinuation;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.cont.resumeUndispatched(EventLoopImplBase.this, Unit.INSTANCE);
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        public final String toString() {
            return super.toString() + this.cont;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DelayedRunnableTask extends DelayedTask {
        public final Runnable block;

        public DelayedRunnableTask(long j, Runnable runnable) {
            super(j);
            this.block = runnable;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.block.run();
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        public final String toString() {
            return super.toString() + this.block;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class DelayedTask implements Runnable, Comparable, DisposableHandle {
        private volatile Object _heap;
        public int index = -1;
        public long nanoTime;

        public DelayedTask(long j) {
            this.nanoTime = j;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            long j = this.nanoTime - ((DelayedTask) obj).nanoTime;
            if (j > 0) {
                return 1;
            }
            return j < 0 ? -1 : 0;
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public final void dispose() {
            synchronized (this) {
                try {
                    Object obj = this._heap;
                    Symbol symbol = EventLoop_commonKt.DISPOSED_TASK;
                    if (obj == symbol) {
                        return;
                    }
                    DelayedTaskQueue delayedTaskQueue = obj instanceof DelayedTaskQueue ? (DelayedTaskQueue) obj : null;
                    if (delayedTaskQueue != null) {
                        synchronized (delayedTaskQueue) {
                            Object obj2 = this._heap;
                            if ((obj2 instanceof ThreadSafeHeap ? (ThreadSafeHeap) obj2 : null) != null) {
                                delayedTaskQueue.removeAtImpl(this.index);
                            }
                        }
                    }
                    this._heap = symbol;
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int scheduleTask(long j, DelayedTaskQueue delayedTaskQueue, EventLoopImplBase eventLoopImplBase) {
            synchronized (this) {
                if (this._heap == EventLoop_commonKt.DISPOSED_TASK) {
                    return 2;
                }
                synchronized (delayedTaskQueue) {
                    try {
                        DelayedTask[] delayedTaskArr = delayedTaskQueue.a;
                        DelayedTask delayedTask = delayedTaskArr != null ? delayedTaskArr[0] : null;
                        if (eventLoopImplBase._isCompleted.getValue()) {
                            return 1;
                        }
                        if (delayedTask == null) {
                            delayedTaskQueue.timeNow = j;
                        } else {
                            long j2 = delayedTask.nanoTime;
                            if (j2 - j < 0) {
                                j = j2;
                            }
                            if (j - delayedTaskQueue.timeNow > 0) {
                                delayedTaskQueue.timeNow = j;
                            }
                        }
                        long j3 = this.nanoTime;
                        long j4 = delayedTaskQueue.timeNow;
                        if (j3 - j4 < 0) {
                            this.nanoTime = j4;
                        }
                        delayedTaskQueue.addImpl(this);
                        return 0;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void setHeap(DelayedTaskQueue delayedTaskQueue) {
            if (this._heap == EventLoop_commonKt.DISPOSED_TASK) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            this._heap = delayedTaskQueue;
        }

        public String toString() {
            return "Delayed[nanos=" + this.nanoTime + "]";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DelayedTaskQueue extends ThreadSafeHeap {
        public long timeNow;

        public DelayedTaskQueue(long j) {
            this.timeNow = j;
        }
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        enqueue(runnable);
    }

    public void enqueue(Runnable runnable) {
        enqueueDelayedTasks();
        if (!enqueueImpl(runnable)) {
            DefaultExecutor.INSTANCE.enqueue(runnable);
            return;
        }
        Thread thread = getThread();
        if (Thread.currentThread() != thread) {
            LockSupport.unpark(thread);
        }
    }

    public final void enqueueDelayedTasks() {
        DelayedTask delayedTask;
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
        if (delayedTaskQueue == null || delayedTaskQueue._size.value == 0) {
            return;
        }
        long nanoTime = System.nanoTime();
        do {
            synchronized (delayedTaskQueue) {
                try {
                    DelayedTask[] delayedTaskArr = delayedTaskQueue.a;
                    DelayedTask delayedTask2 = delayedTaskArr != null ? delayedTaskArr[0] : null;
                    if (delayedTask2 != null) {
                        delayedTask = ((nanoTime - delayedTask2.nanoTime) > 0L ? 1 : ((nanoTime - delayedTask2.nanoTime) == 0L ? 0 : -1)) >= 0 ? enqueueImpl(delayedTask2) : false ? delayedTaskQueue.removeAtImpl(0) : null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } while (delayedTask != null);
    }

    public final boolean enqueueImpl(Runnable runnable) {
        AtomicRef atomicRef = this._queue;
        while (true) {
            Object obj = atomicRef.value;
            if (this._isCompleted.getValue()) {
                return false;
            }
            if (obj == null) {
                if (this._queue.compareAndSet(null, runnable)) {
                    return true;
                }
            } else if (obj instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) obj;
                int addLast = lockFreeTaskQueueCore.addLast(runnable);
                if (addLast == 0) {
                    return true;
                }
                if (addLast == 1) {
                    this._queue.compareAndSet(obj, lockFreeTaskQueueCore.next());
                } else if (addLast == 2) {
                    return false;
                }
            } else {
                if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                    return false;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore2 = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore2.addLast((Runnable) obj);
                lockFreeTaskQueueCore2.addLast(runnable);
                if (this._queue.compareAndSet(obj, lockFreeTaskQueueCore2)) {
                    return true;
                }
            }
        }
    }

    public DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
        return DefaultExecutorKt.DefaultDelay.invokeOnTimeout(j, runnable, coroutineContext);
    }

    public final boolean isEmpty() {
        DelayedTaskQueue delayedTaskQueue;
        ArrayDeque arrayDeque = this.unconfinedQueue;
        if (!(arrayDeque != null ? arrayDeque.isEmpty() : true) || ((delayedTaskQueue = (DelayedTaskQueue) this._delayed.value) != null && delayedTaskQueue._size.value != 0)) {
            return false;
        }
        Object obj = this._queue.value;
        if (obj != null) {
            if (obj instanceof LockFreeTaskQueueCore) {
                long j = ((LockFreeTaskQueueCore) obj)._state.value;
                return ((int) (1073741823 & j)) == ((int) ((j & 1152921503533105152L) >> 30));
            }
            if (obj != EventLoop_commonKt.CLOSED_EMPTY) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0014, code lost:
    
        r6 = null;
     */
    @Override // kotlinx.coroutines.EventLoop
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long processNextEvent() {
        /*
            r11 = this;
            boolean r0 = r11.processUnconfinedEvent()
            r1 = 0
            if (r0 == 0) goto La
            goto La7
        La:
            r11.enqueueDelayedTasks()
            kotlinx.atomicfu.AtomicRef r0 = r11._queue
        Lf:
            java.lang.Object r3 = r0.value
            r4 = 0
            if (r3 != 0) goto L16
        L14:
            r6 = r4
            goto L42
        L16:
            boolean r5 = r3 instanceof kotlinx.coroutines.internal.LockFreeTaskQueueCore
            if (r5 == 0) goto L32
            r5 = r3
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r5 = (kotlinx.coroutines.internal.LockFreeTaskQueueCore) r5
            java.lang.Object r6 = r5.removeFirstOrNull()
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.REMOVE_FROZEN
            if (r6 == r7) goto L28
            java.lang.Runnable r6 = (java.lang.Runnable) r6
            goto L42
        L28:
            kotlinx.atomicfu.AtomicRef r4 = r11._queue
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r5 = r5.next()
            r4.compareAndSet(r3, r5)
            goto Lf
        L32:
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.EventLoop_commonKt.CLOSED_EMPTY
            if (r3 != r5) goto L37
            goto L14
        L37:
            kotlinx.atomicfu.AtomicRef r5 = r11._queue
            boolean r5 = r5.compareAndSet(r3, r4)
            if (r5 == 0) goto Lf
            r6 = r3
            java.lang.Runnable r6 = (java.lang.Runnable) r6
        L42:
            if (r6 == 0) goto L48
            r6.run()
            return r1
        L48:
            kotlin.collections.ArrayDeque r0 = r11.unconfinedQueue
            r5 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            if (r0 != 0) goto L53
        L51:
            r7 = r5
            goto L5b
        L53:
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L5a
            goto L51
        L5a:
            r7 = r1
        L5b:
            int r0 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r0 != 0) goto L60
            goto La7
        L60:
            kotlinx.atomicfu.AtomicRef r0 = r11._queue
            java.lang.Object r0 = r0.value
            if (r0 == 0) goto L88
            boolean r3 = r0 instanceof kotlinx.coroutines.internal.LockFreeTaskQueueCore
            if (r3 == 0) goto L83
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r0 = (kotlinx.coroutines.internal.LockFreeTaskQueueCore) r0
            kotlinx.atomicfu.AtomicLong r0 = r0._state
            long r7 = r0.value
            r9 = 1073741823(0x3fffffff, double:5.304989472E-315)
            long r9 = r9 & r7
            int r0 = (int) r9
            r9 = 1152921503533105152(0xfffffffc0000000, double:1.2882296003504729E-231)
            long r7 = r7 & r9
            r3 = 30
            long r7 = r7 >> r3
            int r3 = (int) r7
            if (r0 != r3) goto L82
            goto L88
        L82:
            return r1
        L83:
            kotlinx.coroutines.internal.Symbol r11 = kotlinx.coroutines.EventLoop_commonKt.CLOSED_EMPTY
            if (r0 != r11) goto La7
            goto Lac
        L88:
            kotlinx.atomicfu.AtomicRef r11 = r11._delayed
            java.lang.Object r11 = r11.value
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r11 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r11
            if (r11 == 0) goto Lac
            monitor-enter(r11)
            kotlinx.coroutines.EventLoopImplBase$DelayedTask[] r0 = r11.a     // Catch: java.lang.Throwable -> La9
            if (r0 == 0) goto L98
            r3 = 0
            r4 = r0[r3]     // Catch: java.lang.Throwable -> La9
        L98:
            monitor-exit(r11)
            if (r4 != 0) goto L9c
            goto Lac
        L9c:
            long r3 = r4.nanoTime
            long r5 = java.lang.System.nanoTime()
            long r3 = r3 - r5
            int r11 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r11 >= 0) goto La8
        La7:
            return r1
        La8:
            return r3
        La9:
            r0 = move-exception
            monitor-exit(r11)
            throw r0
        Lac:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.EventLoopImplBase.processNextEvent():long");
    }

    public final void schedule(long j, DelayedTask delayedTask) {
        int scheduleTask;
        Thread thread;
        if (this._isCompleted.getValue()) {
            scheduleTask = 1;
        } else {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
            if (delayedTaskQueue == null) {
                this._delayed.compareAndSet(null, new DelayedTaskQueue(j));
                Object obj = this._delayed.value;
                obj.getClass();
                delayedTaskQueue = (DelayedTaskQueue) obj;
            }
            scheduleTask = delayedTask.scheduleTask(j, delayedTaskQueue, this);
        }
        if (scheduleTask != 0) {
            if (scheduleTask == 1) {
                reschedule(j, delayedTask);
                return;
            } else {
                if (scheduleTask != 2) {
                    throw new IllegalStateException("unexpected result");
                }
                return;
            }
        }
        DelayedTaskQueue delayedTaskQueue2 = (DelayedTaskQueue) this._delayed.value;
        if (delayedTaskQueue2 != null) {
            synchronized (delayedTaskQueue2) {
                DelayedTask[] delayedTaskArr = delayedTaskQueue2.a;
                r1 = delayedTaskArr != null ? delayedTaskArr[0] : null;
            }
        }
        if (r1 != delayedTask || Thread.currentThread() == (thread = getThread())) {
            return;
        }
        LockSupport.unpark(thread);
    }

    @Override // kotlinx.coroutines.Delay
    public final void scheduleResumeAfterDelay(long j, CancellableContinuationImpl cancellableContinuationImpl) {
        Symbol symbol = EventLoop_commonKt.DISPOSED_TASK;
        long j2 = j > 0 ? j >= 9223372036854L ? Long.MAX_VALUE : 1000000 * j : 0L;
        if (j2 < 4611686018427387903L) {
            long nanoTime = System.nanoTime();
            DelayedResumeTask delayedResumeTask = new DelayedResumeTask(j2 + nanoTime, cancellableContinuationImpl);
            schedule(nanoTime, delayedResumeTask);
            cancellableContinuationImpl.invokeOnCancellationImpl(new DisposeOnCancel(delayedResumeTask));
        }
    }

    @Override // kotlinx.coroutines.EventLoop
    public void shutdown() {
        DelayedTask removeAtImpl;
        ThreadLocalEventLoop.INSTANCE.getClass();
        ThreadLocalEventLoop.ref.set(null);
        AtomicBoolean atomicBoolean = this._isCompleted;
        atomicBoolean._value = 1;
        TraceBase traceBase = atomicBoolean.trace;
        if (traceBase != TraceBase.None.INSTANCE) {
            traceBase.getClass();
        }
        AtomicRef atomicRef = this._queue;
        while (true) {
            Object obj = atomicRef.value;
            if (obj == null) {
                if (this._queue.compareAndSet(null, EventLoop_commonKt.CLOSED_EMPTY)) {
                    break;
                }
            } else if (obj instanceof LockFreeTaskQueueCore) {
                ((LockFreeTaskQueueCore) obj).close();
                break;
            } else {
                if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                    break;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
                lockFreeTaskQueueCore.addLast((Runnable) obj);
                if (this._queue.compareAndSet(obj, lockFreeTaskQueueCore)) {
                    break;
                }
            }
        }
        while (processNextEvent() <= 0) {
        }
        long nanoTime = System.nanoTime();
        while (true) {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
            if (delayedTaskQueue == null) {
                return;
            }
            synchronized (delayedTaskQueue) {
                removeAtImpl = delayedTaskQueue._size.value > 0 ? delayedTaskQueue.removeAtImpl(0) : null;
            }
            if (removeAtImpl == null) {
                return;
            } else {
                reschedule(nanoTime, removeAtImpl);
            }
        }
    }
}
