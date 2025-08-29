package kotlinx.coroutines.sync;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectImplementation;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.sync.MutexImpl;

/* loaded from: classes4.dex */
public class SemaphoreAndMutexImpl {
    public final AtomicInt _availablePermits;
    public final AtomicLong deqIdx = AtomicFU.atomic(0L);
    public final AtomicLong enqIdx = AtomicFU.atomic(0L);
    public final AtomicRef head;
    public final SemaphoreAndMutexImpl$$ExternalSyntheticLambda0 onCancellationRelease;
    public final int permits;
    public final AtomicRef tail;

    /* JADX WARN: Type inference failed for: r6v5, types: [kotlinx.coroutines.sync.SemaphoreAndMutexImpl$$ExternalSyntheticLambda0] */
    public SemaphoreAndMutexImpl(int i, int i2) {
        this.permits = i;
        if (i <= 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Semaphore should have at least 1 permit, but had ").toString());
        }
        if (i2 < 0 || i2 > i) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "The number of acquired permits should be in 0..").toString());
        }
        SemaphoreSegment semaphoreSegment = new SemaphoreSegment(0L, null, 2);
        this.head = AtomicFU.atomic(semaphoreSegment);
        this.tail = AtomicFU.atomic(semaphoreSegment);
        this._availablePermits = AtomicFU.atomic(i - i2);
        this.onCancellationRelease = new Function3() { // from class: kotlinx.coroutines.sync.SemaphoreAndMutexImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                this.f$0.release();
                return Unit.INSTANCE;
            }
        };
    }

    public final void acquire(MutexImpl.CancellableContinuationWithOwner cancellableContinuationWithOwner) {
        Object objFindSegmentInternal;
        while (true) {
            AtomicInt atomicInt = this._availablePermits;
            atomicInt.getClass();
            int andDecrement = AtomicInt.FU.getAndDecrement(atomicInt);
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = atomicInt.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
            if (andDecrement <= this.permits) {
                if (andDecrement > 0) {
                    cancellableContinuationWithOwner.resume(Unit.INSTANCE, this.onCancellationRelease);
                    return;
                }
                SemaphoreSegment semaphoreSegment = (SemaphoreSegment) this.tail.value;
                long andIncrement = this.enqIdx.getAndIncrement();
                SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1 semaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1 = SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1.INSTANCE;
                AtomicRef atomicRef = this.tail;
                long j = andIncrement / SemaphoreKt.SEGMENT_SIZE;
                while (true) {
                    objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(semaphoreSegment, j, semaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1);
                    if (!SegmentOrClosed.m3485isClosedimpl(objFindSegmentInternal)) {
                        Segment segmentM3484getSegmentimpl = SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
                        while (true) {
                            Segment segment = (Segment) atomicRef.value;
                            if (segment.id >= segmentM3484getSegmentimpl.id) {
                                break;
                            }
                            if (!segmentM3484getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                break;
                            }
                            if (atomicRef.compareAndSet(segment, segmentM3484getSegmentimpl)) {
                                if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                    segment.remove();
                                }
                            } else if (segmentM3484getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                segmentM3484getSegmentimpl.remove();
                            }
                        }
                    } else {
                        break;
                    }
                }
                SemaphoreSegment semaphoreSegment2 = (SemaphoreSegment) SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
                int i = (int) (andIncrement % SemaphoreKt.SEGMENT_SIZE);
                if (semaphoreSegment2.acquirers.array[i].compareAndSet(null, cancellableContinuationWithOwner)) {
                    cancellableContinuationWithOwner.invokeOnCancellation(semaphoreSegment2, i);
                    return;
                }
                if (semaphoreSegment2.acquirers.array[i].compareAndSet(SemaphoreKt.PERMIT, SemaphoreKt.TAKEN)) {
                    cancellableContinuationWithOwner.resume(Unit.INSTANCE, this.onCancellationRelease);
                    return;
                }
            }
        }
    }

    public final void release() {
        int i;
        int i2;
        Object objFindSegmentInternal;
        int i3;
        do {
            AtomicInt atomicInt = this._availablePermits;
            atomicInt.getClass();
            int andIncrement = AtomicInt.FU.getAndIncrement(atomicInt);
            TraceBase.None none = TraceBase.None.INSTANCE;
            TraceBase traceBase = atomicInt.trace;
            if (traceBase != none) {
                traceBase.getClass();
            }
            if (andIncrement >= this.permits) {
                do {
                    i = this._availablePermits.value;
                    i2 = this.permits;
                    if (i <= i2) {
                        break;
                    }
                } while (!this._availablePermits.compareAndSet(i, i2));
                throw new IllegalStateException(("The number of released permits cannot be greater than " + this.permits).toString());
            }
            if (andIncrement >= 0) {
                return;
            }
            SemaphoreSegment semaphoreSegment = (SemaphoreSegment) this.head.value;
            long andIncrement2 = this.deqIdx.getAndIncrement();
            long j = andIncrement2 / SemaphoreKt.SEGMENT_SIZE;
            SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1 semaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1 = SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1.INSTANCE;
            AtomicRef atomicRef = this.head;
            while (true) {
                objFindSegmentInternal = ConcurrentLinkedListKt.findSegmentInternal(semaphoreSegment, j, semaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1);
                if (!SegmentOrClosed.m3485isClosedimpl(objFindSegmentInternal)) {
                    Segment segmentM3484getSegmentimpl = SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
                    while (true) {
                        Segment segment = (Segment) atomicRef.value;
                        if (segment.id >= segmentM3484getSegmentimpl.id) {
                            break;
                        }
                        if (!segmentM3484getSegmentimpl.tryIncPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            break;
                        }
                        if (atomicRef.compareAndSet(segment, segmentM3484getSegmentimpl)) {
                            if (segment.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                                segment.remove();
                            }
                        } else if (segmentM3484getSegmentimpl.decPointers$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()) {
                            segmentM3484getSegmentimpl.remove();
                        }
                    }
                } else {
                    break;
                }
            }
            SemaphoreSegment semaphoreSegment2 = (SemaphoreSegment) SegmentOrClosed.m3484getSegmentimpl(objFindSegmentInternal);
            semaphoreSegment2.cleanPrev();
            i3 = 0;
            if (semaphoreSegment2.id <= j) {
                int i4 = (int) (andIncrement2 % SemaphoreKt.SEGMENT_SIZE);
                Object andSet = semaphoreSegment2.acquirers.array[i4].getAndSet(SemaphoreKt.PERMIT);
                if (andSet == null) {
                    int i5 = SemaphoreKt.MAX_SPIN_CYCLES;
                    while (i3 < i5) {
                        if (semaphoreSegment2.acquirers.array[i4].value == SemaphoreKt.TAKEN) {
                            i3 = 1;
                            break;
                        }
                        i3++;
                    }
                    i3 = !semaphoreSegment2.acquirers.array[i4].compareAndSet(SemaphoreKt.PERMIT, SemaphoreKt.BROKEN) ? 1 : 0;
                } else if (andSet != SemaphoreKt.CANCELLED) {
                    if (andSet instanceof CancellableContinuation) {
                        CancellableContinuation cancellableContinuation = (CancellableContinuation) andSet;
                        Symbol symbolTryResume = cancellableContinuation.tryResume(Unit.INSTANCE, this.onCancellationRelease);
                        if (symbolTryResume != null) {
                            cancellableContinuation.completeResume(symbolTryResume);
                            i3 = 1;
                            break;
                            break;
                        }
                    } else {
                        if (!(andSet instanceof SelectInstance)) {
                            throw new IllegalStateException(("unexpected: " + andSet).toString());
                        }
                        if (((SelectImplementation) ((SelectInstance) andSet)).trySelectInternal(this, Unit.INSTANCE) == 0) {
                            i3 = 1;
                            break;
                            break;
                        }
                    }
                }
            }
        } while (i3 == 0);
    }
}
