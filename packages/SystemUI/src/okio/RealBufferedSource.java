package okio;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.sec.ims.presence.ServiceTuple;
import java.io.EOFException;
import java.nio.ByteBuffer;
import kotlin.text.Charsets;

/* loaded from: classes4.dex */
public final class RealBufferedSource implements BufferedSource {
    public final Buffer bufferField = new Buffer();
    public boolean closed;
    public final Source source;

    public RealBufferedSource(Source source) {
        this.source = source;
    }

    @Override // okio.BufferedSource
    public final Buffer buffer() {
        return this.bufferField;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
    public final void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.source.close();
        Buffer buffer = this.bufferField;
        buffer.skip(buffer.size);
    }

    @Override // okio.BufferedSource
    public final boolean exhausted() {
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        return this.bufferField.exhausted() && this.source.read(this.bufferField, 8192L) == -1;
    }

    @Override // okio.BufferedSource
    public final Buffer getBuffer() {
        return this.bufferField;
    }

    @Override // okio.BufferedSource
    public final long indexOfElement(ByteString byteString) {
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        long jMax = 0;
        while (true) {
            long jIndexOfElement = this.bufferField.indexOfElement(byteString, jMax);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            Buffer buffer = this.bufferField;
            long j = buffer.size;
            if (this.source.read(buffer, 8192L) == -1) {
                return -1L;
            }
            jMax = Math.max(jMax, j);
        }
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return !this.closed;
    }

    @Override // okio.Source
    public final long read(Buffer buffer, long j) {
        if (j < 0) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
        }
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        Buffer buffer2 = this.bufferField;
        if (buffer2.size == 0) {
            if (j == 0) {
                return 0L;
            }
            if (this.source.read(buffer2, 8192L) == -1) {
                return -1L;
            }
        }
        return this.bufferField.read(buffer, Math.min(j, this.bufferField.size));
    }

    public final byte readByte() throws EOFException {
        require(1L);
        return this.bufferField.readByte();
    }

    public final int readIntLe() throws EOFException {
        require(4L);
        int i = this.bufferField.readInt();
        int i2 = SegmentedByteString.DEFAULT__ByteString_size;
        return ((i & 255) << 24) | (((-16777216) & i) >>> 24) | ((16711680 & i) >>> 8) | ((65280 & i) << 8);
    }

    public final long readLongLe() throws EOFException {
        char c;
        char c2;
        char c3;
        char c4;
        long j;
        require(8L);
        Buffer buffer = this.bufferField;
        if (buffer.size < 8) {
            throw new EOFException();
        }
        Segment segment = buffer.head;
        segment.getClass();
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 8) {
            j = ((buffer.readInt() & 4294967295L) << 32) | (4294967295L & buffer.readInt());
            c3 = '(';
            c4 = '8';
            c = '\b';
            c2 = 24;
        } else {
            byte[] bArr = segment.data;
            c = '\b';
            c2 = 24;
            c3 = '(';
            c4 = '8';
            int i3 = i + 7;
            long j2 = ((bArr[i] & 255) << 56) | ((bArr[i + 1] & 255) << 48) | ((bArr[i + 2] & 255) << 40) | ((bArr[i + 3] & 255) << 32) | ((bArr[i + 4] & 255) << 24) | ((bArr[i + 5] & 255) << 16) | ((bArr[i + 6] & 255) << 8);
            int i4 = i + 8;
            long j3 = j2 | (bArr[i3] & 255);
            buffer.size -= 8;
            if (i4 == i2) {
                buffer.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = i4;
            }
            j = j3;
        }
        int i5 = SegmentedByteString.DEFAULT__ByteString_size;
        return ((j & 255) << c4) | (((-72057594037927936L) & j) >>> c4) | ((71776119061217280L & j) >>> c3) | ((280375465082880L & j) >>> c2) | ((1095216660480L & j) >>> c) | ((4278190080L & j) << c) | ((16711680 & j) << c2) | ((65280 & j) << c3);
    }

    public final short readShortLe() throws EOFException {
        short s;
        require(2L);
        Buffer buffer = this.bufferField;
        if (buffer.size < 2) {
            throw new EOFException();
        }
        Segment segment = buffer.head;
        segment.getClass();
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 2) {
            s = (short) ((buffer.readByte() & 255) | ((buffer.readByte() & 255) << 8));
        } else {
            int i3 = i + 1;
            byte[] bArr = segment.data;
            int i4 = (bArr[i] & 255) << 8;
            int i5 = i + 2;
            int i6 = (bArr[i3] & 255) | i4;
            buffer.size -= 2;
            if (i5 == i2) {
                buffer.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = i5;
            }
            s = (short) i6;
        }
        int i7 = SegmentedByteString.DEFAULT__ByteString_size;
        return (short) (((s & 255) << 8) | ((65280 & s) >>> 8));
    }

    public final String readUtf8(long j) throws EOFException {
        require(j);
        Buffer buffer = this.bufferField;
        buffer.getClass();
        return buffer.readString(j, Charsets.UTF_8);
    }

    @Override // okio.BufferedSource
    public final boolean request(long j) {
        Buffer buffer;
        if (j < 0) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
        }
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        do {
            buffer = this.bufferField;
            if (buffer.size >= j) {
                return true;
            }
        } while (this.source.read(buffer, 8192L) != -1);
        return false;
    }

    public final void require(long j) throws EOFException {
        if (!request(j)) {
            throw new EOFException();
        }
    }

    @Override // okio.BufferedSource
    public final int select(Options options) throws EOFException {
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        while (true) {
            int iSelectPrefix = okio.internal.Buffer.selectPrefix(this.bufferField, options, true);
            if (iSelectPrefix != -2) {
                if (iSelectPrefix != -1) {
                    this.bufferField.skip(options.byteStrings[iSelectPrefix].getSize$external__okio__android_common__okio_lib());
                    return iSelectPrefix;
                }
            } else if (this.source.read(this.bufferField, 8192L) == -1) {
                break;
            }
        }
        return -1;
    }

    @Override // okio.BufferedSource
    public final void skip(long j) throws EOFException {
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        while (j > 0) {
            Buffer buffer = this.bufferField;
            if (buffer.size == 0 && this.source.read(buffer, 8192L) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(j, this.bufferField.size);
            this.bufferField.skip(jMin);
            j -= jMin;
        }
    }

    public final String toString() {
        return "buffer(" + this.source + ")";
    }

    @Override // java.nio.channels.ReadableByteChannel
    public final int read(ByteBuffer byteBuffer) {
        Buffer buffer = this.bufferField;
        if (buffer.size == 0 && this.source.read(buffer, 8192L) == -1) {
            return -1;
        }
        return this.bufferField.read(byteBuffer);
    }
}
