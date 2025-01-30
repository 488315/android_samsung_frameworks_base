package okio;

import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SegmentPool {
    public static final SegmentPool INSTANCE = new SegmentPool();
    public static long byteCount;
    public static Segment next;

    private SegmentPool() {
    }

    public final void recycle(Segment segment) {
        if (!(segment.next == null && segment.prev == null)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (segment.shared) {
            return;
        }
        synchronized (this) {
            long j = byteCount + 8192;
            if (j > 65536) {
                return;
            }
            byteCount = j;
            segment.next = next;
            segment.limit = 0;
            segment.pos = 0;
            next = segment;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final Segment take() {
        synchronized (this) {
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
