package okio;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.sec.ims.presence.ServiceTuple;
import java.io.EOFException;
import java.nio.ByteBuffer;
import okio.internal.BufferKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
    public final Buffer getBuffer() {
        return this.bufferField;
    }

    @Override // okio.BufferedSource
    public final long indexOfElement(ByteString byteString) {
        if (!(!this.closed)) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED.toString());
        }
        long j = 0;
        while (true) {
            long indexOfElement = this.bufferField.indexOfElement(byteString, j);
            if (indexOfElement != -1) {
                return indexOfElement;
            }
            Buffer buffer = this.bufferField;
            long j2 = buffer.size;
            if (this.source.read(buffer, 8192) == -1) {
                return -1L;
            }
            j = Math.max(j, j2);
        }
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return !this.closed;
    }

    public final RealBufferedSource peek() {
        return new RealBufferedSource(new PeekSource(this));
    }

    @Override // okio.Source
    public final long read(Buffer buffer, long j) {
        if (!(j >= 0)) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("byteCount < 0: ", j).toString());
        }
        if (!(!this.closed)) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED.toString());
        }
        Buffer buffer2 = this.bufferField;
        if (buffer2.size == 0 && this.source.read(buffer2, 8192) == -1) {
            return -1L;
        }
        return this.bufferField.read(buffer, Math.min(j, this.bufferField.size));
    }

    public final byte readByte() {
        if (request(1L)) {
            return this.bufferField.readByte();
        }
        throw new EOFException();
    }

    @Override // okio.BufferedSource
    public final boolean request(long j) {
        Buffer buffer;
        if (!(j >= 0)) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("byteCount < 0: ", j).toString());
        }
        if (!(!this.closed)) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED.toString());
        }
        do {
            buffer = this.bufferField;
            if (buffer.size >= j) {
                return true;
            }
        } while (this.source.read(buffer, 8192) != -1);
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:?, code lost:
    
        return -1;
     */
    @Override // okio.BufferedSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int select(Options options) {
        if (!(!this.closed)) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED.toString());
        }
        while (true) {
            int selectPrefix = BufferKt.selectPrefix(this.bufferField, options, true);
            if (selectPrefix != -2) {
                if (selectPrefix != -1) {
                    this.bufferField.skip(options.byteStrings[selectPrefix].getSize$okio());
                    return selectPrefix;
                }
            } else if (this.source.read(this.bufferField, 8192) == -1) {
                break;
            }
        }
    }

    public final String toString() {
        return "buffer(" + this.source + ')';
    }

    @Override // java.nio.channels.ReadableByteChannel
    public final int read(ByteBuffer byteBuffer) {
        Buffer buffer = this.bufferField;
        if (buffer.size == 0 && this.source.read(buffer, 8192) == -1) {
            return -1;
        }
        return this.bufferField.read(byteBuffer);
    }
}
