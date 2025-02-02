package okio;

import java.io.IOException;

/* loaded from: classes.dex */
final class PeekSource implements Source {
    private final Buffer buffer;
    private boolean closed;
    private int expectedPos;
    private Segment expectedSegment;
    private long pos;
    private final BufferedSource upstream;

    PeekSource(BufferedSource bufferedSource) {
        this.upstream = bufferedSource;
        Buffer buffer = bufferedSource.buffer();
        this.buffer = buffer;
        Segment segment = buffer.head;
        this.expectedSegment = segment;
        this.expectedPos = segment != null ? segment.pos : -1;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.closed = true;
    }

    @Override // okio.Source
    public final long read(Buffer buffer, long j) throws IOException {
        Segment segment;
        Segment segment2;
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        Segment segment3 = this.expectedSegment;
        if (segment3 != null && (segment3 != (segment2 = this.buffer.head) || this.expectedPos != segment2.pos)) {
            throw new IllegalStateException("Peek source is invalid because upstream source was used");
        }
        if (!this.upstream.request(this.pos + 1)) {
            return -1L;
        }
        if (this.expectedSegment == null && (segment = this.buffer.head) != null) {
            this.expectedSegment = segment;
            this.expectedPos = segment.pos;
        }
        long min = Math.min(8192L, this.buffer.size - this.pos);
        Buffer buffer2 = this.buffer;
        long j2 = this.pos;
        if (buffer == null) {
            buffer2.getClass();
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(buffer2.size, j2, min);
        if (min != 0) {
            buffer.size += min;
            Segment segment4 = buffer2.head;
            while (true) {
                long j3 = segment4.limit - segment4.pos;
                if (j2 < j3) {
                    break;
                }
                j2 -= j3;
                segment4 = segment4.next;
            }
            long j4 = min;
            while (j4 > 0) {
                Segment sharedCopy = segment4.sharedCopy();
                int i = (int) (sharedCopy.pos + j2);
                sharedCopy.pos = i;
                sharedCopy.limit = Math.min(i + ((int) j4), sharedCopy.limit);
                Segment segment5 = buffer.head;
                if (segment5 == null) {
                    sharedCopy.prev = sharedCopy;
                    sharedCopy.next = sharedCopy;
                    buffer.head = sharedCopy;
                } else {
                    segment5.prev.push(sharedCopy);
                }
                j4 -= sharedCopy.limit - sharedCopy.pos;
                segment4 = segment4.next;
                j2 = 0;
            }
        }
        this.pos += min;
        return min;
    }
}
