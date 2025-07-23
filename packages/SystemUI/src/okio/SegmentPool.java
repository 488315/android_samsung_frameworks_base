package okio;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SegmentPool {
    public static final int HASH_BUCKET_COUNT;
    public static final AtomicReference[] hashBuckets;
    public static final SegmentPool INSTANCE = new SegmentPool();
    public static final int MAX_SIZE = 65536;
    public static final Segment LOCK = new Segment(new byte[0], 0, 0, false, false);

    static {
        int highestOneBit = Integer.highestOneBit((Runtime.getRuntime().availableProcessors() * 2) - 1);
        HASH_BUCKET_COUNT = highestOneBit;
        AtomicReference[] atomicReferenceArr = new AtomicReference[highestOneBit];
        for (int i = 0; i < highestOneBit; i++) {
            atomicReferenceArr[i] = new AtomicReference();
        }
        hashBuckets = atomicReferenceArr;
    }

    private SegmentPool() {
    }

    public static final void recycle(Segment segment) {
        if (segment.next != null || segment.prev != null) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (segment.shared) {
            return;
        }
        INSTANCE.getClass();
        AtomicReference atomicReference = hashBuckets[(int) (Thread.currentThread().getId() & (HASH_BUCKET_COUNT - 1))];
        Segment segment2 = LOCK;
        Segment segment3 = (Segment) atomicReference.getAndSet(segment2);
        if (segment3 == segment2) {
            return;
        }
        int i = segment3 != null ? segment3.limit : 0;
        if (i >= MAX_SIZE) {
            atomicReference.set(segment3);
            return;
        }
        segment.next = segment3;
        segment.pos = 0;
        segment.limit = i + 8192;
        atomicReference.set(segment);
    }

    public static final Segment take() {
        INSTANCE.getClass();
        AtomicReference atomicReference = hashBuckets[(int) (Thread.currentThread().getId() & (HASH_BUCKET_COUNT - 1))];
        Segment segment = LOCK;
        Segment segment2 = (Segment) atomicReference.getAndSet(segment);
        if (segment2 == segment) {
            return new Segment();
        }
        if (segment2 == null) {
            atomicReference.set(null);
            return new Segment();
        }
        atomicReference.set(segment2.next);
        segment2.next = null;
        segment2.limit = 0;
        return segment2;
    }
}
