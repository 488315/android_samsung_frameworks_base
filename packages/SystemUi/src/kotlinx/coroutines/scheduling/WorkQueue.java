package kotlinx.coroutines.scheduling;

import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class WorkQueue {
    public final AtomicReferenceArray buffer = new AtomicReferenceArray(128);
    public final AtomicRef lastScheduledTask = AtomicFU.atomic((Object) null);
    public final AtomicInt producerIndex = AtomicFU.atomic();
    public final AtomicInt consumerIndex = AtomicFU.atomic();
    public final AtomicInt blockingTasksInBuffer = AtomicFU.atomic();

    public final Task add(Task task, boolean z) {
        if (z) {
            return addLast(task);
        }
        Task task2 = (Task) this.lastScheduledTask.getAndSet(task);
        if (task2 == null) {
            return null;
        }
        return addLast(task2);
    }

    public final Task addLast(Task task) {
        if (((TaskContextImpl) task.taskContext).taskMode == 1) {
            AtomicInt atomicInt = this.blockingTasksInBuffer;
            atomicInt.getClass();
            AtomicInt.f663FU.incrementAndGet(atomicInt);
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = atomicInt.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
        }
        if (m298x84356e59() == 127) {
            return task;
        }
        int i = this.producerIndex.value & 127;
        while (this.buffer.get(i) != null) {
            Thread.yield();
        }
        this.buffer.lazySet(i, task);
        AtomicInt atomicInt2 = this.producerIndex;
        atomicInt2.getClass();
        AtomicInt.f663FU.incrementAndGet(atomicInt2);
        TraceBase.None none2 = TraceBase.None.INSTANCE;
        TraceBase traceBase2 = atomicInt2.trace;
        if (traceBase2 == none2) {
            return null;
        }
        traceBase2.getClass();
        return null;
    }

    /* renamed from: getBufferSize$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public final int m298x84356e59() {
        return this.producerIndex.value - this.consumerIndex.value;
    }

    public final Task pollBuffer() {
        Task task;
        while (true) {
            int i = this.consumerIndex.value;
            if (i - this.producerIndex.value == 0) {
                return null;
            }
            int i2 = i & 127;
            if (this.consumerIndex.compareAndSet(i, i + 1) && (task = (Task) this.buffer.getAndSet(i2, null)) != null) {
                if (((TaskContextImpl) task.taskContext).taskMode == 1) {
                    AtomicInt atomicInt = this.blockingTasksInBuffer;
                    atomicInt.getClass();
                    AtomicInt.f663FU.decrementAndGet(atomicInt);
                    TraceBase.None none = TraceBase.None.INSTANCE;
                    TraceBase traceBase = atomicInt.trace;
                    if (traceBase != none) {
                        traceBase.getClass();
                    }
                }
                return task;
            }
        }
    }

    public final long tryStealLastScheduled(WorkQueue workQueue, boolean z) {
        Task task;
        do {
            task = (Task) workQueue.lastScheduledTask.value;
            if (task == null) {
                return -2L;
            }
            if (z) {
                if (!(((TaskContextImpl) task.taskContext).taskMode == 1)) {
                    return -2L;
                }
            }
            TasksKt.schedulerTimeSource.getClass();
            long nanoTime = System.nanoTime() - task.submissionTime;
            long j = TasksKt.WORK_STEALING_TIME_RESOLUTION_NS;
            if (nanoTime < j) {
                return j - nanoTime;
            }
        } while (!workQueue.lastScheduledTask.compareAndSet(task, null));
        add(task, false);
        return -1L;
    }
}
