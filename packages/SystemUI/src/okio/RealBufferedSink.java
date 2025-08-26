package okio;

import com.sec.ims.presence.ServiceTuple;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes4.dex */
public final class RealBufferedSink implements Sink, WritableByteChannel {
    public final Buffer bufferField = new Buffer();
    public boolean closed;
    public final Sink sink;

    public RealBufferedSink(Sink sink) {
        this.sink = sink;
    }

    @Override // okio.Sink, java.lang.AutoCloseable, java.nio.channels.Channel
    public final void close() throws Throwable {
        if (this.closed) {
            return;
        }
        try {
            Buffer buffer = this.bufferField;
            long j = buffer.size;
            if (j > 0) {
                this.sink.write(buffer, j);
            }
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.sink.close();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        this.closed = true;
        if (th != null) {
            throw th;
        }
    }

    public final void emitCompleteSegments() {
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        Buffer buffer = this.bufferField;
        long j = buffer.size;
        if (j == 0) {
            j = 0;
        } else {
            Segment segment = buffer.head;
            segment.getClass();
            Segment segment2 = segment.prev;
            segment2.getClass();
            if (segment2.limit < 8192 && segment2.owner) {
                j -= r5 - segment2.pos;
            }
        }
        if (j > 0) {
            this.sink.write(this.bufferField, j);
        }
    }

    @Override // okio.Sink, java.io.Flushable
    public final void flush() {
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        Buffer buffer = this.bufferField;
        long j = buffer.size;
        if (j > 0) {
            this.sink.write(buffer, j);
        }
        this.sink.flush();
    }

    @Override // java.nio.channels.Channel
    public final boolean isOpen() {
        return !this.closed;
    }

    public final String toString() {
        return "buffer(" + this.sink + ")";
    }

    @Override // java.nio.channels.WritableByteChannel
    public final int write(ByteBuffer byteBuffer) {
        if (this.closed) {
            throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
        }
        int iWrite = this.bufferField.write(byteBuffer);
        emitCompleteSegments();
        return iWrite;
    }

    @Override // okio.Sink
    public final void write(Buffer buffer, long j) {
        if (!this.closed) {
            this.bufferField.write(buffer, j);
            emitCompleteSegments();
            return;
        }
        throw new IllegalStateException(ServiceTuple.BASIC_STATUS_CLOSED);
    }
}
