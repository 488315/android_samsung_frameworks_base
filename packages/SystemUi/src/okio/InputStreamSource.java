package okio;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.InputStream;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class InputStreamSource implements Source {
    public final InputStream input;
    public final Timeout timeout;

    public InputStreamSource(InputStream inputStream, Timeout timeout) {
        this.input = inputStream;
        this.timeout = timeout;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.input.close();
    }

    @Override // okio.Source
    public final long read(Buffer buffer, long j) {
        if (j == 0) {
            return 0L;
        }
        boolean z = false;
        if (!(j >= 0)) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m25m("byteCount < 0: ", j).toString());
        }
        try {
            this.timeout.throwIfReached();
            Segment writableSegment$okio = buffer.writableSegment$okio(1);
            int read = this.input.read(writableSegment$okio.data, writableSegment$okio.limit, (int) Math.min(j, 8192 - writableSegment$okio.limit));
            if (read != -1) {
                writableSegment$okio.limit += read;
                long j2 = read;
                buffer.size += j2;
                return j2;
            }
            if (writableSegment$okio.pos != writableSegment$okio.limit) {
                return -1L;
            }
            buffer.head = writableSegment$okio.pop();
            SegmentPool.INSTANCE.recycle(writableSegment$okio);
            return -1L;
        } catch (AssertionError e) {
            if (e.getCause() != null) {
                String message = e.getMessage();
                if (message != null ? StringsKt__StringsKt.contains(message, "getsockname failed", false) : false) {
                    z = true;
                }
            }
            if (z) {
                throw new IOException(e);
            }
            throw e;
        }
    }

    public final String toString() {
        return "source(" + this.input + ')';
    }
}
