package okio;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.sec.ims.presence.ServiceTuple;

/* loaded from: classes4.dex */
public final class PeekSource implements Source {
    public final Buffer buffer;
    public boolean closed;
    public int expectedPos;
    public Segment expectedSegment;
    public long pos;
    public final BufferedSource upstream;

    public PeekSource(BufferedSource bufferedSource) {
        this.upstream = bufferedSource;
        Buffer buffer = bufferedSource.getBuffer();
        this.buffer = buffer;
        Segment segment = buffer.head;
        this.expectedSegment = segment;
        this.expectedPos = segment != null ? segment.pos : -1;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.closed = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001b, code lost:
    
        if (r3 == r4.pos) goto L15;
     */
    @Override // okio.Source
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long read(Buffer buffer, long j) {
        Segment segment;
        if (j < 0) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
        }
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        Segment segment2 = this.expectedSegment;
        if (segment2 != null) {
            Segment segment3 = this.buffer.head;
            if (segment2 == segment3) {
                int i = this.expectedPos;
                segment3.getClass();
            }
            throw new IllegalStateException("Peek source is invalid because upstream source was used");
        }
        if (j == 0) {
            return 0L;
        }
        if (!this.upstream.request(this.pos + 1)) {
            return -1L;
        }
        if (this.expectedSegment == null && (segment = this.buffer.head) != null) {
            this.expectedSegment = segment;
            this.expectedPos = segment.pos;
        }
        long jMin = Math.min(j, this.buffer.size - this.pos);
        Buffer buffer2 = this.buffer;
        long j2 = this.pos;
        SegmentedByteString.checkOffsetAndCount(buffer2.size, j2, jMin);
        if (jMin != 0) {
            buffer.size += jMin;
            Segment segment4 = buffer2.head;
            while (true) {
                segment4.getClass();
                long j3 = segment4.limit - segment4.pos;
                if (j2 < j3) {
                    break;
                }
                j2 -= j3;
                segment4 = segment4.next;
            }
            long j4 = jMin;
            while (j4 > 0) {
                segment4.getClass();
                Segment segmentSharedCopy = segment4.sharedCopy();
                int i2 = segmentSharedCopy.pos + ((int) j2);
                segmentSharedCopy.pos = i2;
                segmentSharedCopy.limit = Math.min(i2 + ((int) j4), segmentSharedCopy.limit);
                Segment segment5 = buffer.head;
                if (segment5 == null) {
                    segmentSharedCopy.prev = segmentSharedCopy;
                    segmentSharedCopy.next = segmentSharedCopy;
                    buffer.head = segmentSharedCopy;
                } else {
                    Segment segment6 = segment5.prev;
                    segment6.getClass();
                    segment6.push(segmentSharedCopy);
                }
                j4 -= segmentSharedCopy.limit - segmentSharedCopy.pos;
                segment4 = segment4.next;
                j2 = 0;
            }
        }
        this.pos += jMin;
        return jMin;
    }
}
