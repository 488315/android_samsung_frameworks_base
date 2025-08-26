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

/* loaded from: classes4.dex */
public abstract class EventLoopImplBase extends EventLoopImplPlatform implements Delay {
    public final AtomicRef _queue = AtomicFU.atomic((Object) null);
    public final AtomicRef _delayed = AtomicFU.atomic((Object) null);
    public final AtomicBoolean _isCompleted = AtomicFU.atomic(false);

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
        DelayedTask delayedTaskRemoveAtImpl;
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
        if (delayedTaskQueue == null || delayedTaskQueue._size.value == 0) {
            return;
        }
        long jNanoTime = System.nanoTime();
        do {
            synchronized (delayedTaskQueue) {
                try {
                    DelayedTask[] delayedTaskArr = delayedTaskQueue.a;
                    DelayedTask delayedTask = delayedTaskArr != null ? delayedTaskArr[0] : null;
                    if (delayedTask != null) {
                        delayedTaskRemoveAtImpl = ((jNanoTime - delayedTask.nanoTime) > 0L ? 1 : ((jNanoTime - delayedTask.nanoTime) == 0L ? 0 : -1)) >= 0 ? enqueueImpl(delayedTask) : false ? delayedTaskQueue.removeAtImpl(0) : null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } while (delayedTaskRemoveAtImpl != null);
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
                int iAddLast = lockFreeTaskQueueCore.addLast(runnable);
                if (iAddLast == 0) {
                    return true;
                }
                if (iAddLast == 1) {
                    this._queue.compareAndSet(obj, lockFreeTaskQueueCore.next());
                } else if (iAddLast == 2) {
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

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0014, code lost:
    
        r6 = null;
     */
    @Override // kotlinx.coroutines.EventLoop
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long processNextEvent() {
        Runnable runnable;
        DelayedTask delayedTask;
        if (!processUnconfinedEvent()) {
            enqueueDelayedTasks();
            AtomicRef atomicRef = this._queue;
            while (true) {
                Object obj = atomicRef.value;
                if (obj == null) {
                    break;
                }
                if (obj instanceof LockFreeTaskQueueCore) {
                    LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) obj;
                    Object objRemoveFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
                    if (objRemoveFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                        runnable = (Runnable) objRemoveFirstOrNull;
                        break;
                    }
                    this._queue.compareAndSet(obj, lockFreeTaskQueueCore.next());
                } else {
                    if (obj == EventLoop_commonKt.CLOSED_EMPTY) {
                        break;
                    }
                    if (this._queue.compareAndSet(obj, null)) {
                        runnable = (Runnable) obj;
                        break;
                    }
                }
            }
            if (runnable != null) {
                runnable.run();
                return 0L;
            }
            ArrayDeque arrayDeque = this.unconfinedQueue;
            if (((arrayDeque == null || arrayDeque.isEmpty()) ? Long.MAX_VALUE : 0L) != 0) {
                Object obj2 = this._queue.value;
                if (obj2 != null) {
                    if (obj2 instanceof LockFreeTaskQueueCore) {
                        long j = ((LockFreeTaskQueueCore) obj2)._state.value;
                        if (((int) (1073741823 & j)) != ((int) ((j & 1152921503533105152L) >> 30))) {
                            return 0L;
                        }
                    } else if (obj2 == EventLoop_commonKt.CLOSED_EMPTY) {
                        return Long.MAX_VALUE;
                    }
                }
                DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
                if (delayedTaskQueue != null) {
                    synchronized (delayedTaskQueue) {
                        DelayedTask[] delayedTaskArr = delayedTaskQueue.a;
                        delayedTask = delayedTaskArr != null ? delayedTaskArr[0] : null;
                    }
                    if (delayedTask != null) {
                        long jNanoTime = delayedTask.nanoTime - System.nanoTime();
                        if (jNanoTime >= 0) {
                            return jNanoTime;
                        }
                    }
                }
                return Long.MAX_VALUE;
            }
        }
        return 0L;
    }

    public final void schedule(long j, DelayedTask delayedTask) {
        int iScheduleTask;
        Thread thread;
        if (this._isCompleted.getValue()) {
            iScheduleTask = 1;
        } else {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
            if (delayedTaskQueue == null) {
                this._delayed.compareAndSet(null, new DelayedTaskQueue(j));
                Object obj = this._delayed.value;
                obj.getClass();
                delayedTaskQueue = (DelayedTaskQueue) obj;
            }
            iScheduleTask = delayedTask.scheduleTask(j, delayedTaskQueue, this);
        }
        if (iScheduleTask != 0) {
            if (iScheduleTask == 1) {
                reschedule(j, delayedTask);
                return;
            } else {
                if (iScheduleTask != 2) {
                    throw new IllegalStateException("unexpected result");
                }
                return;
            }
        }
        DelayedTaskQueue delayedTaskQueue2 = (DelayedTaskQueue) this._delayed.value;
        if (delayedTaskQueue2 != null) {
            synchronized (delayedTaskQueue2) {
                DelayedTask[] delayedTaskArr = delayedTaskQueue2.a;
                delayedTask = delayedTaskArr != null ? delayedTaskArr[0] : null;
            }
        }
        if (delayedTask != delayedTask || Thread.currentThread() == (thread = getThread())) {
            return;
        }
        LockSupport.unpark(thread);
    }

    @Override // kotlinx.coroutines.Delay
    public final void scheduleResumeAfterDelay(long j, CancellableContinuationImpl cancellableContinuationImpl) {
        Symbol symbol = EventLoop_commonKt.DISPOSED_TASK;
        long j2 = j > 0 ? j >= 9223372036854L ? Long.MAX_VALUE : 1000000 * j : 0L;
        if (j2 < 4611686018427387903L) {
            long jNanoTime = System.nanoTime();
            DelayedResumeTask delayedResumeTask = new DelayedResumeTask(j2 + jNanoTime, cancellableContinuationImpl);
            schedule(jNanoTime, delayedResumeTask);
            cancellableContinuationImpl.invokeOnCancellationImpl(new DisposeOnCancel(delayedResumeTask));
        }
    }

    @Override // kotlinx.coroutines.EventLoop
    public void shutdown() {
        DelayedTask delayedTaskRemoveAtImpl;
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
        long jNanoTime = System.nanoTime();
        while (true) {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
            if (delayedTaskQueue == null) {
                return;
            }
            synchronized (delayedTaskQueue) {
                delayedTaskRemoveAtImpl = delayedTaskQueue._size.value > 0 ? delayedTaskQueue.removeAtImpl(0) : null;
            }
            if (delayedTaskRemoveAtImpl == null) {
                return;
            } else {
                reschedule(jNanoTime, delayedTaskRemoveAtImpl);
            }
        }
    }
}
