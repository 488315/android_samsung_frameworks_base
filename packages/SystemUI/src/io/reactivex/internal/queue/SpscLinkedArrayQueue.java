package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.SimpleQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes4.dex */
public final class SpscLinkedArrayQueue implements SimpleQueue {
    public AtomicReferenceArray consumerBuffer;
    public final AtomicLong consumerIndex;
    public final int consumerMask;
    public AtomicReferenceArray producerBuffer;
    public final AtomicLong producerIndex;
    public long producerLookAhead;
    public final int producerLookAheadStep;
    public final int producerMask;
    public static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    public static final Object HAS_NEXT = new Object();

    public SpscLinkedArrayQueue(int i) {
        AtomicLong atomicLong = new AtomicLong();
        this.producerIndex = atomicLong;
        this.consumerIndex = new AtomicLong();
        int iNumberOfLeadingZeros = 1 << (32 - Integer.numberOfLeadingZeros(Math.max(8, i) - 1));
        int i2 = iNumberOfLeadingZeros - 1;
        AtomicReferenceArray atomicReferenceArray = new AtomicReferenceArray(iNumberOfLeadingZeros + 1);
        this.producerBuffer = atomicReferenceArray;
        this.producerMask = i2;
        this.producerLookAheadStep = Math.min(iNumberOfLeadingZeros / 4, MAX_LOOK_AHEAD_STEP);
        this.consumerBuffer = atomicReferenceArray;
        this.consumerMask = i2;
        this.producerLookAhead = iNumberOfLeadingZeros - 2;
        atomicLong.lazySet(0L);
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean isEmpty() {
        return this.producerIndex.get() == this.consumerIndex.get();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean offer(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        AtomicReferenceArray atomicReferenceArray = this.producerBuffer;
        long j = this.producerIndex.get();
        int i = this.producerMask;
        int i2 = ((int) j) & i;
        if (j < this.producerLookAhead) {
            atomicReferenceArray.lazySet(i2, obj);
            this.producerIndex.lazySet(j + 1);
            return true;
        }
        long j2 = this.producerLookAheadStep + j;
        if (atomicReferenceArray.get(((int) j2) & i) == null) {
            this.producerLookAhead = j2 - 1;
            atomicReferenceArray.lazySet(i2, obj);
            this.producerIndex.lazySet(j + 1);
            return true;
        }
        long j3 = j + 1;
        if (atomicReferenceArray.get(((int) j3) & i) == null) {
            atomicReferenceArray.lazySet(i2, obj);
            this.producerIndex.lazySet(j3);
            return true;
        }
        AtomicReferenceArray atomicReferenceArray2 = new AtomicReferenceArray(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        this.producerLookAhead = (j + i) - 1;
        atomicReferenceArray2.lazySet(i2, obj);
        atomicReferenceArray.lazySet(atomicReferenceArray.length() - 1, atomicReferenceArray2);
        atomicReferenceArray.lazySet(i2, HAS_NEXT);
        this.producerIndex.lazySet(j3);
        return true;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final Object poll() {
        AtomicReferenceArray atomicReferenceArray = this.consumerBuffer;
        long j = this.consumerIndex.get();
        int i = this.consumerMask;
        int i2 = ((int) j) & i;
        Object obj = atomicReferenceArray.get(i2);
        boolean z = obj == HAS_NEXT;
        if (obj != null && !z) {
            atomicReferenceArray.lazySet(i2, null);
            this.consumerIndex.lazySet(j + 1);
            return obj;
        }
        if (!z) {
            return null;
        }
        int i3 = i + 1;
        AtomicReferenceArray atomicReferenceArray2 = (AtomicReferenceArray) atomicReferenceArray.get(i3);
        atomicReferenceArray.lazySet(i3, null);
        this.consumerBuffer = atomicReferenceArray2;
        Object obj2 = atomicReferenceArray2.get(i2);
        if (obj2 != null) {
            atomicReferenceArray2.lazySet(i2, null);
            this.consumerIndex.lazySet(j + 1);
        }
        return obj2;
    }
}
