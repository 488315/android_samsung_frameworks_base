package okio;

import java.io.InputStream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class InputStreamSource implements Source {
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

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0062, code lost:
    
        if ((r5 != null ? kotlin.text.StringsKt__StringsKt.contains(r5, "getsockname failed", false) : false) != false) goto L27;
     */
    @Override // okio.Source
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long read(okio.Buffer r5, long r6) {
        /*
            r4 = this;
            r0 = 0
            int r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r2 != 0) goto L7
            return r0
        L7:
            if (r2 < 0) goto L6f
            r0 = 1
            okio.Timeout r1 = r4.timeout     // Catch: java.lang.AssertionError -> L3a
            r1.throwIfReached()     // Catch: java.lang.AssertionError -> L3a
            okio.Segment r1 = r5.writableSegment$external__okio__android_common__okio_lib(r0)     // Catch: java.lang.AssertionError -> L3a
            int r2 = r1.limit     // Catch: java.lang.AssertionError -> L3a
            int r2 = 8192 - r2
            long r2 = (long) r2     // Catch: java.lang.AssertionError -> L3a
            long r6 = java.lang.Math.min(r6, r2)     // Catch: java.lang.AssertionError -> L3a
            int r6 = (int) r6     // Catch: java.lang.AssertionError -> L3a
            java.io.InputStream r4 = r4.input     // Catch: java.lang.AssertionError -> L3a
            byte[] r7 = r1.data     // Catch: java.lang.AssertionError -> L3a
            int r2 = r1.limit     // Catch: java.lang.AssertionError -> L3a
            int r4 = r4.read(r7, r2, r6)     // Catch: java.lang.AssertionError -> L3a
            r6 = -1
            if (r4 != r6) goto L3f
            int r4 = r1.pos     // Catch: java.lang.AssertionError -> L3a
            int r6 = r1.limit     // Catch: java.lang.AssertionError -> L3a
            if (r4 != r6) goto L3c
            okio.Segment r4 = r1.pop()     // Catch: java.lang.AssertionError -> L3a
            r5.head = r4     // Catch: java.lang.AssertionError -> L3a
            okio.SegmentPool.recycle(r1)     // Catch: java.lang.AssertionError -> L3a
            goto L3c
        L3a:
            r4 = move-exception
            goto L4b
        L3c:
            r4 = -1
            return r4
        L3f:
            int r6 = r1.limit     // Catch: java.lang.AssertionError -> L3a
            int r6 = r6 + r4
            r1.limit = r6     // Catch: java.lang.AssertionError -> L3a
            long r6 = r5.size     // Catch: java.lang.AssertionError -> L3a
            long r1 = (long) r4     // Catch: java.lang.AssertionError -> L3a
            long r6 = r6 + r1
            r5.size = r6     // Catch: java.lang.AssertionError -> L3a
            return r1
        L4b:
            int r5 = okio.Okio__JvmOkioKt.$r8$clinit
            java.lang.Throwable r5 = r4.getCause()
            r6 = 0
            if (r5 == 0) goto L65
            java.lang.String r5 = r4.getMessage()
            if (r5 == 0) goto L61
            java.lang.String r7 = "getsockname failed"
            boolean r5 = kotlin.text.StringsKt__StringsKt.contains(r5, r7, r6)
            goto L62
        L61:
            r5 = r6
        L62:
            if (r5 == 0) goto L65
            goto L66
        L65:
            r0 = r6
        L66:
            if (r0 == 0) goto L6e
            java.io.IOException r5 = new java.io.IOException
            r5.<init>(r4)
            throw r5
        L6e:
            throw r4
        L6f:
            java.lang.String r4 = "byteCount < 0: "
            java.lang.String r4 = androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0.m(r4, r6)
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r4 = r4.toString()
            r5.<init>(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.InputStreamSource.read(okio.Buffer, long):long");
    }

    public final String toString() {
        return "source(" + this.input + ")";
    }
}
