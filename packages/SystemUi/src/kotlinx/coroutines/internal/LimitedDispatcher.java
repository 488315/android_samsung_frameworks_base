package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DefaultExecutorKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LimitedDispatcher extends CoroutineDispatcher implements Runnable, Delay {
    public final /* synthetic */ Delay $$delegate_0;
    public final CoroutineDispatcher dispatcher;
    public final int parallelism;
    public final LockFreeTaskQueue queue;
    private volatile int runningWorkers;
    public final Object workerAllocationLock;

    /* JADX WARN: Multi-variable type inference failed */
    public LimitedDispatcher(CoroutineDispatcher coroutineDispatcher, int i) {
        this.dispatcher = coroutineDispatcher;
        this.parallelism = i;
        Delay delay = coroutineDispatcher instanceof Delay ? (Delay) coroutineDispatcher : null;
        this.$$delegate_0 = delay == null ? DefaultExecutorKt.DefaultDelay : delay;
        this.queue = new LockFreeTaskQueue(false);
        this.workerAllocationLock = new Object();
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        this.queue.addLast(runnable);
        boolean z = true;
        if (this.runningWorkers >= this.parallelism) {
            return;
        }
        synchronized (this.workerAllocationLock) {
            if (this.runningWorkers >= this.parallelism) {
                z = false;
            } else {
                this.runningWorkers++;
            }
        }
        if (z) {
            this.dispatcher.dispatch(this, this);
        }
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        this.queue.addLast(runnable);
        boolean z = true;
        if (this.runningWorkers >= this.parallelism) {
            return;
        }
        synchronized (this.workerAllocationLock) {
            if (this.runningWorkers >= this.parallelism) {
                z = false;
            } else {
                this.runningWorkers++;
            }
        }
        if (z) {
            this.dispatcher.dispatchYield(this, this);
        }
    }

    @Override // kotlinx.coroutines.Delay
    public final DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
        return this.$$delegate_0.invokeOnTimeout(j, runnable, coroutineContext);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0029, code lost:
    
        r0 = r3.workerAllocationLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x002b, code lost:
    
        monitor-enter(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x002c, code lost:
    
        r3.runningWorkers--;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0038, code lost:
    
        if (r3.queue.getSize() != 0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003c, code lost:
    
        r3.runningWorkers++;
        r1 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x003a, code lost:
    
        monitor-exit(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x003b, code lost:
    
        return;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        Object obj;
        while (true) {
            int i = 0;
            while (true) {
                Runnable runnable = (Runnable) this.queue.removeFirstOrNull();
                if (runnable == null) {
                    break;
                }
                try {
                    runnable.run();
                } catch (Throwable th) {
                    CoroutineExceptionHandlerKt.handleCoroutineException(th, EmptyCoroutineContext.INSTANCE);
                }
                i++;
                if (i >= 16 && this.dispatcher.isDispatchNeeded()) {
                    this.dispatcher.dispatch(this, this);
                    return;
                }
            }
        }
    }

    @Override // kotlinx.coroutines.Delay
    public final void scheduleResumeAfterDelay(long j, CancellableContinuationImpl cancellableContinuationImpl) {
        this.$$delegate_0.scheduleResumeAfterDelay(j, cancellableContinuationImpl);
    }
}
