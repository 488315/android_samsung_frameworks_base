package okio;

import java.util.zip.Inflater;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final void close() {
        if (this.closed) {
            return;
        }
        this.inflater.end();
        this.closed = true;
        this.source.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x008c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x008d  */
    @Override // okio.Source
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long read(okio.Buffer r9, long r10) {
        /*
            r8 = this;
        L0:
            r0 = 0
            int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r2 < 0) goto Lc2
            boolean r3 = r8.closed
            if (r3 != 0) goto Lba
            if (r2 != 0) goto Lf
        Lc:
            r2 = r0
            goto L88
        Lf:
            r2 = 1
            okio.Segment r2 = r9.writableSegment$external__okio__android_common__okio_lib(r2)     // Catch: java.util.zip.DataFormatException -> Lb3
            int r3 = r2.limit     // Catch: java.util.zip.DataFormatException -> Lb3
            int r3 = 8192 - r3
            long r3 = (long) r3     // Catch: java.util.zip.DataFormatException -> Lb3
            long r3 = java.lang.Math.min(r10, r3)     // Catch: java.util.zip.DataFormatException -> Lb3
            int r3 = (int) r3     // Catch: java.util.zip.DataFormatException -> Lb3
            java.util.zip.Inflater r4 = r8.inflater     // Catch: java.util.zip.DataFormatException -> Lb3
            boolean r4 = r4.needsInput()     // Catch: java.util.zip.DataFormatException -> Lb3
            if (r4 != 0) goto L27
            goto L49
        L27:
            okio.BufferedSource r4 = r8.source     // Catch: java.util.zip.DataFormatException -> Lb3
            boolean r4 = r4.exhausted()     // Catch: java.util.zip.DataFormatException -> Lb3
            if (r4 == 0) goto L30
            goto L49
        L30:
            okio.BufferedSource r4 = r8.source     // Catch: java.util.zip.DataFormatException -> Lb3
            okio.Buffer r4 = r4.getBuffer()     // Catch: java.util.zip.DataFormatException -> Lb3
            okio.Segment r4 = r4.head     // Catch: java.util.zip.DataFormatException -> Lb3
            r4.getClass()     // Catch: java.util.zip.DataFormatException -> Lb3
            int r5 = r4.limit     // Catch: java.util.zip.DataFormatException -> Lb3
            int r6 = r4.pos     // Catch: java.util.zip.DataFormatException -> Lb3
            int r5 = r5 - r6
            r8.bufferBytesHeldByInflater = r5     // Catch: java.util.zip.DataFormatException -> Lb3
            java.util.zip.Inflater r7 = r8.inflater     // Catch: java.util.zip.DataFormatException -> Lb3
            byte[] r4 = r4.data     // Catch: java.util.zip.DataFormatException -> Lb3
            r7.setInput(r4, r6, r5)     // Catch: java.util.zip.DataFormatException -> Lb3
        L49:
            java.util.zip.Inflater r4 = r8.inflater     // Catch: java.util.zip.DataFormatException -> Lb3
            byte[] r5 = r2.data     // Catch: java.util.zip.DataFormatException -> Lb3
            int r6 = r2.limit     // Catch: java.util.zip.DataFormatException -> Lb3
            int r3 = r4.inflate(r5, r6, r3)     // Catch: java.util.zip.DataFormatException -> Lb3
            int r4 = r8.bufferBytesHeldByInflater     // Catch: java.util.zip.DataFormatException -> Lb3
            if (r4 != 0) goto L58
            goto L6a
        L58:
            java.util.zip.Inflater r5 = r8.inflater     // Catch: java.util.zip.DataFormatException -> Lb3
            int r5 = r5.getRemaining()     // Catch: java.util.zip.DataFormatException -> Lb3
            int r4 = r4 - r5
            int r5 = r8.bufferBytesHeldByInflater     // Catch: java.util.zip.DataFormatException -> Lb3
            int r5 = r5 - r4
            r8.bufferBytesHeldByInflater = r5     // Catch: java.util.zip.DataFormatException -> Lb3
            okio.BufferedSource r5 = r8.source     // Catch: java.util.zip.DataFormatException -> Lb3
            long r6 = (long) r4     // Catch: java.util.zip.DataFormatException -> Lb3
            r5.skip(r6)     // Catch: java.util.zip.DataFormatException -> Lb3
        L6a:
            if (r3 <= 0) goto L78
            int r4 = r2.limit     // Catch: java.util.zip.DataFormatException -> Lb3
            int r4 = r4 + r3
            r2.limit = r4     // Catch: java.util.zip.DataFormatException -> Lb3
            long r4 = r9.size     // Catch: java.util.zip.DataFormatException -> Lb3
            long r2 = (long) r3     // Catch: java.util.zip.DataFormatException -> Lb3
            long r4 = r4 + r2
            r9.size = r4     // Catch: java.util.zip.DataFormatException -> Lb3
            goto L88
        L78:
            int r3 = r2.pos     // Catch: java.util.zip.DataFormatException -> Lb3
            int r4 = r2.limit     // Catch: java.util.zip.DataFormatException -> Lb3
            if (r3 != r4) goto Lc
            okio.Segment r3 = r2.pop()     // Catch: java.util.zip.DataFormatException -> Lb3
            r9.head = r3     // Catch: java.util.zip.DataFormatException -> Lb3
            okio.SegmentPool.recycle(r2)     // Catch: java.util.zip.DataFormatException -> Lb3
            goto Lc
        L88:
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 <= 0) goto L8d
            return r2
        L8d:
            java.util.zip.Inflater r0 = r8.inflater
            boolean r0 = r0.finished()
            if (r0 != 0) goto Lb0
            java.util.zip.Inflater r0 = r8.inflater
            boolean r0 = r0.needsDictionary()
            if (r0 == 0) goto L9e
            goto Lb0
        L9e:
            okio.BufferedSource r0 = r8.source
            boolean r0 = r0.exhausted()
            if (r0 != 0) goto La8
            goto L0
        La8:
            java.io.EOFException r8 = new java.io.EOFException
            java.lang.String r9 = "source exhausted prematurely"
            r8.<init>(r9)
            throw r8
        Lb0:
            r8 = -1
            return r8
        Lb3:
            r8 = move-exception
            java.io.IOException r9 = new java.io.IOException
            r9.<init>(r8)
            throw r9
        Lba:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "closed"
            r8.<init>(r9)
            throw r8
        Lc2:
            java.lang.String r8 = "byteCount < 0: "
            java.lang.String r8 = androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0.m(r8, r10)
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r8 = r8.toString()
            r9.<init>(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.InflaterSource.read(okio.Buffer, long):long");
    }

    public InflaterSource(Source source, Inflater inflater) {
        this((BufferedSource) new RealBufferedSource(source), inflater);
    }
}
