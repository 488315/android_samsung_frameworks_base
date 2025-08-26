package kotlinx.coroutines.sync;

import kotlin.coroutines.CoroutineContext;
import kotlinx.atomicfu.AtomicArray;
import kotlinx.coroutines.internal.Segment;

/* loaded from: classes4.dex */
public final class SemaphoreSegment extends Segment {
    public final AtomicArray acquirers;

    public SemaphoreSegment(long j, SemaphoreSegment semaphoreSegment, int i) {
        super(j, semaphoreSegment, i);
        this.acquirers = new AtomicArray(SemaphoreKt.SEGMENT_SIZE);
    }

    @Override // kotlinx.coroutines.internal.Segment
    public final int getNumberOfSlots() {
        return SemaphoreKt.SEGMENT_SIZE;
    }

    @Override // kotlinx.coroutines.internal.Segment
    public final void onCancellation(CoroutineContext coroutineContext, int i) {
        this.acquirers.array[i].setValue(SemaphoreKt.CANCELLED);
        onSlotCleaned();
    }

    public final String toString() {
        return "SemaphoreSegment[id=" + this.id + ", hashCode=" + hashCode() + "]";
    }
}
