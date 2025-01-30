package okio;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.sec.ims.presence.ServiceTuple;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
    
        if (r9 == r11.pos) goto L20;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00d2  */
    @Override // okio.Source
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long read(Buffer buffer, long j) {
        Segment segment;
        boolean z = false;
        if (!(j >= 0)) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("byteCount < 0: ", j).toString());
        }
        if (!(!this.closed)) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED.toString());
        }
        Segment segment2 = this.expectedSegment;
        if (segment2 != null) {
            Segment segment3 = this.buffer.head;
            if (segment2 == segment3) {
                int i = this.expectedPos;
                if (segment3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
            if (z) {
                throw new IllegalStateException("Peek source is invalid because upstream source was used".toString());
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
            long min = Math.min(j, this.buffer.size - this.pos);
            Buffer buffer2 = this.buffer;
            long j2 = this.pos;
            Util.checkOffsetAndCount(buffer2.size, j2, min);
            if (min != 0) {
                buffer.size += min;
                Segment segment4 = buffer2.head;
                while (segment4 != null) {
                    long j3 = segment4.limit - segment4.pos;
                    if (j2 >= j3) {
                        j2 -= j3;
                        segment4 = segment4.next;
                    } else {
                        long j4 = min;
                        while (j4 > 0) {
                            if (segment4 == null) {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                            Segment sharedCopy = segment4.sharedCopy();
                            int i2 = sharedCopy.pos + ((int) j2);
                            sharedCopy.pos = i2;
                            sharedCopy.limit = Math.min(i2 + ((int) j4), sharedCopy.limit);
                            Segment segment5 = buffer.head;
                            if (segment5 == null) {
                                sharedCopy.prev = sharedCopy;
                                sharedCopy.next = sharedCopy;
                                buffer.head = sharedCopy;
                            } else {
                                Segment segment6 = segment5.prev;
                                if (segment6 == null) {
                                    Intrinsics.throwNpe();
                                    throw null;
                                }
                                segment6.push(sharedCopy);
                            }
                            j4 -= sharedCopy.limit - sharedCopy.pos;
                            segment4 = segment4.next;
                            j2 = 0;
                        }
                    }
                }
                Intrinsics.throwNpe();
                throw null;
            }
            this.pos += min;
            return min;
        }
        z = true;
        if (z) {
        }
    }
}
