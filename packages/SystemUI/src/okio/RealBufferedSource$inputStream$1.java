package okio;

import com.sec.ims.presence.ServiceTuple;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class RealBufferedSource$inputStream$1 extends InputStream {
    public final /* synthetic */ RealBufferedSource this$0;

    public RealBufferedSource$inputStream$1(RealBufferedSource realBufferedSource) {
        this.this$0 = realBufferedSource;
    }

    @Override // java.io.InputStream
    public final int available() {
        RealBufferedSource realBufferedSource = this.this$0;
        if (realBufferedSource.closed) {
            throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        return (int) Math.min(realBufferedSource.bufferField.size, Integer.MAX_VALUE);
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.this$0.close();
    }

    @Override // java.io.InputStream
    public final int read() {
        RealBufferedSource realBufferedSource = this.this$0;
        if (realBufferedSource.closed) {
            throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        Buffer buffer = realBufferedSource.bufferField;
        if (buffer.size == 0 && realBufferedSource.source.read(buffer, 8192L) == -1) {
            return -1;
        }
        return this.this$0.bufferField.readByte() & 255;
    }

    public final String toString() {
        return this.this$0 + ".inputStream()";
    }

    @Override // java.io.InputStream
    public final long transferTo(OutputStream outputStream) {
        if (this.this$0.closed) {
            throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        long j = 0;
        while (true) {
            RealBufferedSource realBufferedSource = this.this$0;
            Buffer buffer = realBufferedSource.bufferField;
            if (buffer.size == 0 && realBufferedSource.source.read(buffer, 8192L) == -1) {
                return j;
            }
            Buffer buffer2 = this.this$0.bufferField;
            long j2 = buffer2.size;
            j += j2;
            SegmentedByteString.checkOffsetAndCount(j2, 0L, j2);
            Segment segment = buffer2.head;
            while (j2 > 0) {
                segment.getClass();
                int min = (int) Math.min(j2, segment.limit - segment.pos);
                outputStream.write(segment.data, segment.pos, min);
                int i = segment.pos + min;
                segment.pos = i;
                long j3 = min;
                buffer2.size -= j3;
                j2 -= j3;
                if (i == segment.limit) {
                    Segment pop = segment.pop();
                    buffer2.head = pop;
                    SegmentPool.recycle(segment);
                    segment = pop;
                }
            }
        }
    }

    @Override // java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) {
        if (!this.this$0.closed) {
            SegmentedByteString.checkOffsetAndCount(bArr.length, i, i2);
            RealBufferedSource realBufferedSource = this.this$0;
            Buffer buffer = realBufferedSource.bufferField;
            if (buffer.size == 0 && realBufferedSource.source.read(buffer, 8192L) == -1) {
                return -1;
            }
            return this.this$0.bufferField.read(bArr, i, i2);
        }
        throw new IOException(ServiceTuple.BASIC_STATUS_CLOSED);
    }
}
