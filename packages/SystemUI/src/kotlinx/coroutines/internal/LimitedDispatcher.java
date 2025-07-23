package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DefaultExecutorKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LimitedDispatcher extends CoroutineDispatcher implements Delay {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ Delay $$delegate_0;
    public final CoroutineDispatcher dispatcher;
    public final String name;
    public final int parallelism;
    public final LockFreeTaskQueue queue;
    public final AtomicInt runningWorkers;
    public final Object workerAllocationLock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Worker implements Runnable {
        public Runnable currentTask;

        public Worker(Runnable runnable) {
            this.currentTask = runnable;
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i = 0;
            while (true) {
                try {
                    this.currentTask.run();
                } catch (Throwable th) {
                    CoroutineExceptionHandlerKt.handleCoroutineException(th, EmptyCoroutineContext.INSTANCE);
                }
                LimitedDispatcher limitedDispatcher = LimitedDispatcher.this;
                int i2 = LimitedDispatcher.$r8$clinit;
                Runnable obtainTaskOrDeallocateWorker = limitedDispatcher.obtainTaskOrDeallocateWorker();
                if (obtainTaskOrDeallocateWorker == null) {
                    return;
                }
                this.currentTask = obtainTaskOrDeallocateWorker;
                i++;
                if (i >= 16) {
                    LimitedDispatcher limitedDispatcher2 = LimitedDispatcher.this;
                    if (DispatchedContinuationKt.safeIsDispatchNeeded(limitedDispatcher2.dispatcher, limitedDispatcher2)) {
                        LimitedDispatcher limitedDispatcher3 = LimitedDispatcher.this;
                        DispatchedContinuationKt.safeDispatch(limitedDispatcher3.dispatcher, limitedDispatcher3, this);
                        return;
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LimitedDispatcher(CoroutineDispatcher coroutineDispatcher, int i, String str) {
        Delay delay = coroutineDispatcher instanceof Delay ? (Delay) coroutineDispatcher : null;
        this.$$delegate_0 = delay == null ? DefaultExecutorKt.DefaultDelay : delay;
        this.dispatcher = coroutineDispatcher;
        this.parallelism = i;
        this.name = str;
        this.runningWorkers = AtomicFU.atomic(0);
        this.queue = new LockFreeTaskQueue(false);
        this.workerAllocationLock = new Object();
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        Runnable obtainTaskOrDeallocateWorker;
        this.queue.addLast(runnable);
        if (this.runningWorkers.value >= this.parallelism || !tryAllocateWorker() || (obtainTaskOrDeallocateWorker = obtainTaskOrDeallocateWorker()) == null) {
            return;
        }
        DispatchedContinuationKt.safeDispatch(this.dispatcher, this, new Worker(obtainTaskOrDeallocateWorker));
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        Runnable obtainTaskOrDeallocateWorker;
        this.queue.addLast(runnable);
        if (this.runningWorkers.value >= this.parallelism || !tryAllocateWorker() || (obtainTaskOrDeallocateWorker = obtainTaskOrDeallocateWorker()) == null) {
            return;
        }
        this.dispatcher.dispatchYield(this, new Worker(obtainTaskOrDeallocateWorker));
    }

    @Override // kotlinx.coroutines.Delay
    public final DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
        return this.$$delegate_0.invokeOnTimeout(j, runnable, coroutineContext);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final CoroutineDispatcher limitedParallelism(int i) {
        LimitedDispatcherKt.checkParallelism(i);
        return i >= this.parallelism ? this : super.limitedParallelism(i);
    }

    public final Runnable obtainTaskOrDeallocateWorker() {
        while (true) {
            Runnable runnable = (Runnable) this.queue.removeFirstOrNull();
            if (runnable != null) {
                return runnable;
            }
            synchronized (this.workerAllocationLock) {
                this.runningWorkers.decrementAndGet();
                if (this.queue.getSize() == 0) {
                    return null;
                }
                this.runningWorkers.incrementAndGet();
            }
        }
    }

    @Override // kotlinx.coroutines.Delay
    public final void scheduleResumeAfterDelay(long j, CancellableContinuationImpl cancellableContinuationImpl) {
        this.$$delegate_0.scheduleResumeAfterDelay(j, cancellableContinuationImpl);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final String toString() {
        String str = this.name;
        if (str != null) {
            return str;
        }
        return this.dispatcher + ".limitedParallelism(" + this.parallelism + ")";
    }

    public final boolean tryAllocateWorker() {
        synchronized (this.workerAllocationLock) {
            if (this.runningWorkers.value >= this.parallelism) {
                return false;
            }
            this.runningWorkers.incrementAndGet();
            return true;
        }
    }
}
