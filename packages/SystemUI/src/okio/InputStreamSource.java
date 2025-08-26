package okio;

import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.io.InputStream;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes4.dex */
public class InputStreamSource implements Source {
    public final InputStream input;
    public final Timeout timeout;

    public InputStreamSource(InputStream inputStream, Timeout timeout) {
        this.input = inputStream;
        this.timeout = timeout;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.input.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0065  */
    @Override // okio.Source
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long read(Buffer buffer, long j) throws IOException {
        if (j == 0) {
            return 0L;
        }
        if (j < 0) {
            throw new IllegalArgumentException(ValueAnimator$$ExternalSyntheticOutline0.m("byteCount < 0: ", j).toString());
        }
        boolean z = true;
        try {
            this.timeout.throwIfReached();
            Segment segmentWritableSegment$external__okio__android_common__okio_lib = buffer.writableSegment$external__okio__android_common__okio_lib(1);
            int i = this.input.read(segmentWritableSegment$external__okio__android_common__okio_lib.data, segmentWritableSegment$external__okio__android_common__okio_lib.limit, (int) Math.min(j, 8192 - segmentWritableSegment$external__okio__android_common__okio_lib.limit));
            if (i != -1) {
                segmentWritableSegment$external__okio__android_common__okio_lib.limit += i;
                long j2 = i;
                buffer.size += j2;
                return j2;
            }
            if (segmentWritableSegment$external__okio__android_common__okio_lib.pos != segmentWritableSegment$external__okio__android_common__okio_lib.limit) {
                return -1L;
            }
            buffer.head = segmentWritableSegment$external__okio__android_common__okio_lib.pop();
            SegmentPool.recycle(segmentWritableSegment$external__okio__android_common__okio_lib);
            return -1L;
        } catch (AssertionError e) {
            int i2 = Okio__JvmOkioKt.$r8$clinit;
            if (e.getCause() == null) {
                z = false;
            } else {
                String message = e.getMessage();
                if (!(message != null ? StringsKt__StringsKt.contains(message, "getsockname failed", false) : false)) {
                }
            }
            if (z) {
                throw new IOException(e);
            }
            throw e;
        }
    }

    public final String toString() {
        return "source(" + this.input + ")";
    }
}
