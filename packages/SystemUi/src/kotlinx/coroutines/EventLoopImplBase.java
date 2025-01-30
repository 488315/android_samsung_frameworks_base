package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.internal.ArrayQueue;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.ThreadSafeHeap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class EventLoopImplBase extends EventLoopImplPlatform implements Delay {
    public final AtomicRef _queue = AtomicFU.atomic((Object) null);
    public final AtomicRef _delayed = AtomicFU.atomic((Object) null);
    public final AtomicBoolean _isCompleted = AtomicFU.atomic(false);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DelayedResumeTask extends DelayedTask {
        public final CancellableContinuation cont;

        public DelayedResumeTask(long j, CancellableContinuation cancellableContinuation) {
            super(j);
            this.cont = cancellableContinuation;
        }

        @Override // java.lang.Runnable
        public final void run() {
            ((CancellableContinuationImpl) this.cont).resumeUndispatched(EventLoopImplBase.this, Unit.INSTANCE);
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        public final String toString() {
            return super.toString() + this.cont;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        public final synchronized void dispose() {
            Object obj = this._heap;
            Symbol symbol = EventLoop_commonKt.DISPOSED_TASK;
            if (obj == symbol) {
                return;
            }
            DelayedTaskQueue delayedTaskQueue = obj instanceof DelayedTaskQueue ? (DelayedTaskQueue) obj : null;
            if (delayedTaskQueue != null) {
                synchronized (delayedTaskQueue) {
                    if (getHeap() != null) {
                        delayedTaskQueue.removeAtImpl(this.index);
                    }
                }
            }
            this._heap = symbol;
        }

        public final ThreadSafeHeap getHeap() {
            Object obj = this._heap;
            if (obj instanceof ThreadSafeHeap) {
                return (ThreadSafeHeap) obj;
            }
            return null;
        }

        public final synchronized int scheduleTask(long j, DelayedTaskQueue delayedTaskQueue, EventLoopImplBase eventLoopImplBase) {
            if (this._heap == EventLoop_commonKt.DISPOSED_TASK) {
                return 2;
            }
            synchronized (delayedTaskQueue) {
                try {
                    DelayedTask[] delayedTaskArr = delayedTaskQueue.f672a;
                    DelayedTask delayedTask = delayedTaskArr != null ? delayedTaskArr[0] : null;
                    if (eventLoopImplBase._isCompleted._value != 0) {
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

        public final void setHeap(DelayedTaskQueue delayedTaskQueue) {
            if (!(this._heap != EventLoop_commonKt.DISPOSED_TASK)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            this._heap = delayedTaskQueue;
        }

        public String toString() {
            return "Delayed[nanos=" + this.nanoTime + "]";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        if (!enqueueImpl(runnable)) {
            DefaultExecutor.INSTANCE.enqueue(runnable);
            return;
        }
        Thread thread = getThread();
        if (Thread.currentThread() != thread) {
            LockSupport.unpark(thread);
        }
    }

    public final boolean enqueueImpl(Runnable runnable) {
        AtomicRef atomicRef = this._queue;
        while (true) {
            Object obj = atomicRef.value;
            if (this._isCompleted._value != 0) {
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
        return Delay.DefaultImpls.invokeOnTimeout(j, runnable, coroutineContext);
    }

    public final boolean isEmpty() {
        ArrayQueue arrayQueue = this.unconfinedQueue;
        if (!(arrayQueue == null || arrayQueue.head == arrayQueue.tail)) {
            return false;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
        if (delayedTaskQueue != null) {
            if (!(delayedTaskQueue._size.value == 0)) {
                return false;
            }
        }
        Object obj = this._queue.value;
        if (obj != null) {
            if (obj instanceof LockFreeTaskQueueCore) {
                long j = ((LockFreeTaskQueueCore) obj)._state.value;
                if (((int) ((1073741823 & j) >> 0)) != ((int) ((j & 1152921503533105152L) >> 30))) {
                    return false;
                }
            } else if (obj != EventLoop_commonKt.CLOSED_EMPTY) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    @Override // kotlinx.coroutines.EventLoop
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long processNextEvent() {
        Runnable runnable;
        long j;
        DelayedTask delayedTask;
        DelayedTask removeAtImpl;
        if (processUnconfinedEvent()) {
            return 0L;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
        if (delayedTaskQueue != null) {
            if (!(delayedTaskQueue._size.value == 0)) {
                long nanoTime = System.nanoTime();
                do {
                    synchronized (delayedTaskQueue) {
                        DelayedTask[] delayedTaskArr = delayedTaskQueue.f672a;
                        DelayedTask delayedTask2 = delayedTaskArr != null ? delayedTaskArr[0] : null;
                        if (delayedTask2 == null) {
                            removeAtImpl = null;
                        } else {
                            removeAtImpl = ((nanoTime - delayedTask2.nanoTime) > 0L ? 1 : ((nanoTime - delayedTask2.nanoTime) == 0L ? 0 : -1)) >= 0 ? enqueueImpl(delayedTask2) : false ? delayedTaskQueue.removeAtImpl(0) : null;
                        }
                    }
                } while (removeAtImpl != null);
            }
        }
        AtomicRef atomicRef = this._queue;
        while (true) {
            Object obj = atomicRef.value;
            if (obj == null) {
                break;
            }
            if (obj instanceof LockFreeTaskQueueCore) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) obj;
                Object removeFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
                if (removeFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                    runnable = (Runnable) removeFirstOrNull;
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
        runnable = null;
        if (runnable != null) {
            runnable.run();
            return 0L;
        }
        ArrayQueue arrayQueue = this.unconfinedQueue;
        if (arrayQueue != null) {
            if (!(arrayQueue.head == arrayQueue.tail)) {
                j = 0;
                if (j != 0) {
                    return 0L;
                }
                Object obj2 = this._queue.value;
                if (obj2 != null) {
                    if (!(obj2 instanceof LockFreeTaskQueueCore)) {
                        if (obj2 != EventLoop_commonKt.CLOSED_EMPTY) {
                            return 0L;
                        }
                        return Long.MAX_VALUE;
                    }
                    long j2 = ((LockFreeTaskQueueCore) obj2)._state.value;
                    if (!(((int) ((1073741823 & j2) >> 0)) == ((int) ((j2 & 1152921503533105152L) >> 30)))) {
                        return 0L;
                    }
                }
                DelayedTaskQueue delayedTaskQueue2 = (DelayedTaskQueue) this._delayed.value;
                if (delayedTaskQueue2 != null) {
                    synchronized (delayedTaskQueue2) {
                        DelayedTask[] delayedTaskArr2 = delayedTaskQueue2.f672a;
                        delayedTask = delayedTaskArr2 != null ? delayedTaskArr2[0] : null;
                    }
                    if (delayedTask != null) {
                        long nanoTime2 = delayedTask.nanoTime - System.nanoTime();
                        if (nanoTime2 < 0) {
                            return 0L;
                        }
                        return nanoTime2;
                    }
                }
                return Long.MAX_VALUE;
            }
        }
        j = Long.MAX_VALUE;
        if (j != 0) {
        }
    }

    public final void schedule(long j, DelayedTask delayedTask) {
        int scheduleTask;
        Thread thread;
        if (this._isCompleted._value != 0) {
            scheduleTask = 1;
        } else {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed.value;
            if (delayedTaskQueue == null) {
                this._delayed.compareAndSet(null, new DelayedTaskQueue(j));
                Object obj = this._delayed.value;
                Intrinsics.checkNotNull(obj);
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
                    throw new IllegalStateException("unexpected result".toString());
                }
                return;
            }
        }
        DelayedTaskQueue delayedTaskQueue2 = (DelayedTaskQueue) this._delayed.value;
        if (delayedTaskQueue2 != null) {
            synchronized (delayedTaskQueue2) {
                DelayedTask[] delayedTaskArr = delayedTaskQueue2.f672a;
                r3 = delayedTaskArr != null ? delayedTaskArr[0] : null;
            }
        }
        if (!(r3 == delayedTask) || Thread.currentThread() == (thread = getThread())) {
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
            cancellableContinuationImpl.invokeOnCancellation(new DisposeOnCancel(delayedResumeTask));
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
