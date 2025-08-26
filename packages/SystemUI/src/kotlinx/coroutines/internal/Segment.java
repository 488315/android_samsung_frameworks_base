package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.NotCompleted;

/* loaded from: classes4.dex */
public abstract class Segment extends ConcurrentLinkedListNode implements NotCompleted {
    public final AtomicInt cleanedAndPointers;
    public final long id;

    public Segment(long j, Segment segment, int i) {
        super(segment);
        this.id = j;
        this.cleanedAndPointers = AtomicFU.atomic(i << 16);
    }

    public final boolean decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        AtomicInt atomicInt = this.cleanedAndPointers;
        atomicInt.getClass();
        int iAddAndGet = AtomicInt.FU.addAndGet(atomicInt, -65536);
        TraceBase.None none = TraceBase.None.INSTANCE;
        TraceBase traceBase = atomicInt.trace;
        if (traceBase != none) {
            traceBase.getClass();
        }
        return iAddAndGet == getNumberOfSlots() && getNext() != null;
    }

    public abstract int getNumberOfSlots();

    @Override // kotlinx.coroutines.internal.ConcurrentLinkedListNode
    public final boolean isRemoved() {
        return this.cleanedAndPointers.value == getNumberOfSlots() && getNext() != null;
    }

    public abstract void onCancellation(CoroutineContext coroutineContext, int i);

    public final void onSlotCleaned() {
        if (this.cleanedAndPointers.incrementAndGet() == getNumberOfSlots()) {
            remove();
        }
    }

    public final boolean tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host() {
        int i;
        AtomicInt atomicInt = this.cleanedAndPointers;
        do {
            i = atomicInt.value;
            if (i == getNumberOfSlots() && getNext() != null) {
                return false;
            }
        } while (!atomicInt.compareAndSet(i, 65536 + i));
        return true;
    }
}
