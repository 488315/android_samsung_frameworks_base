package okio;

import javax.annotation.Nullable;

/* loaded from: classes.dex */
final class SegmentPool {
    static long byteCount;

    @Nullable
    static Segment next;

    private SegmentPool() {
    }

    static void recycle(Segment segment) {
        if (segment.next != null || segment.prev != null) {
            throw new IllegalArgumentException();
        }
        if (segment.shared) {
            return;
        }
        synchronized (SegmentPool.class) {
            long j = byteCount + 8192;
            if (j > 65536) {
                return;
            }
            byteCount = j;
            segment.next = next;
            segment.limit = 0;
            segment.pos = 0;
            next = segment;
        }
    }

    static Segment take() {
        synchronized (SegmentPool.class) {
            Segment segment = next;
            if (segment == null) {
                return new Segment();
            }
            next = segment.next;
            segment.next = null;
            byteCount -= 8192;
            return segment;
        }
    }
}
