package kotlinx.coroutines.scheduling;

import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicRef;

/* loaded from: classes4.dex */
public final class WorkQueue {
    public final AtomicReferenceArray buffer = new AtomicReferenceArray(128);
    public final AtomicRef lastScheduledTask = AtomicFU.atomic((Object) null);
    public final AtomicInt producerIndex = AtomicFU.atomic(0);
    public final AtomicInt consumerIndex = AtomicFU.atomic(0);
    public final AtomicInt blockingTasksInBuffer = AtomicFU.atomic(0);

    public final Task addLast(Task task) {
        if (this.producerIndex.value - this.consumerIndex.value == 127) {
            return task;
        }
        if (task.taskContext) {
            this.blockingTasksInBuffer.incrementAndGet();
        }
        int i = this.producerIndex.value & 127;
        while (this.buffer.get(i) != null) {
            Thread.yield();
        }
        this.buffer.lazySet(i, task);
        this.producerIndex.incrementAndGet();
        return null;
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
                if (task.taskContext) {
                    this.blockingTasksInBuffer.decrementAndGet();
                }
                return task;
            }
        }
    }

    public final Task tryExtractFromTheMiddle(int i, boolean z) {
        int i2 = i & 127;
        Task task = (Task) this.buffer.get(i2);
        if (task == null || task.taskContext != z || !this.buffer.compareAndSet(i2, task, null)) {
            return null;
        }
        if (z) {
            this.blockingTasksInBuffer.decrementAndGet();
        }
        return task;
    }
}
