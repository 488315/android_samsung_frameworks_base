package okio;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.sec.ims.presence.ServiceTuple;
import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* loaded from: classes4.dex */
public final class InflaterSource implements Source {
    public int bufferBytesHeldByInflater;
    public boolean closed;
    public final Inflater inflater;
    public final BufferedSource source;

    public InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        this.source = bufferedSource;
        this.inflater = inflater;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.inflater.end();
        this.closed = true;
        this.source.close();
    }

    @Override // okio.Source
    public final long read(Buffer buffer, long j) throws DataFormatException, IOException {
        long j2;
        while (j >= 0) {
            if (this.closed) {
                throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
            }
            if (j == 0) {
                j2 = 0;
            } else {
                try {
                    Segment segmentWritableSegment$external__okio__android_common__okio_lib = buffer.writableSegment$external__okio__android_common__okio_lib(1);
                    int iMin = (int) Math.min(j, 8192 - segmentWritableSegment$external__okio__android_common__okio_lib.limit);
                    if (this.inflater.needsInput() && !this.source.exhausted()) {
                        Segment segment = this.source.getBuffer().head;
                        segment.getClass();
                        int i = segment.limit;
                        int i2 = segment.pos;
                        int i3 = i - i2;
                        this.bufferBytesHeldByInflater = i3;
                        this.inflater.setInput(segment.data, i2, i3);
                    }
                    int iInflate = this.inflater.inflate(segmentWritableSegment$external__okio__android_common__okio_lib.data, segmentWritableSegment$external__okio__android_common__okio_lib.limit, iMin);
                    int i4 = this.bufferBytesHeldByInflater;
                    if (i4 != 0) {
                        int remaining = i4 - this.inflater.getRemaining();
                        this.bufferBytesHeldByInflater -= remaining;
                        this.source.skip(remaining);
                    }
                    if (iInflate > 0) {
                        segmentWritableSegment$external__okio__android_common__okio_lib.limit += iInflate;
                        j2 = iInflate;
                        buffer.size += j2;
                    } else {
                        if (segmentWritableSegment$external__okio__android_common__okio_lib.pos == segmentWritableSegment$external__okio__android_common__okio_lib.limit) {
                            buffer.head = segmentWritableSegment$external__okio__android_common__okio_lib.pop();
                            SegmentPool.recycle(segmentWritableSegment$external__okio__android_common__okio_lib);
                        }
                        j2 = 0;
                    }
                } catch (DataFormatException e) {
                    throw new IOException(e);
                }
            }
            if (j2 > 0) {
                return j2;
            }
            if (this.inflater.finished() || this.inflater.needsDictionary()) {
                return -1L;
            }
            if (this.source.exhausted()) {
                throw new EOFException("source exhausted prematurely");
            }
        }
        throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
    }

    public InflaterSource(Source source, Inflater inflater) {
        this((BufferedSource) new RealBufferedSource(source), inflater);
    }
}
