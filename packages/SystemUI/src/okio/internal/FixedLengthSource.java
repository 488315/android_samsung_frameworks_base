package okio.internal;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import java.io.IOException;
import okio.Buffer;
import okio.ForwardingSource;
import okio.Source;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FixedLengthSource extends ForwardingSource {
    public long bytesReceived;
    public final long size;
    public final boolean truncate;

    public FixedLengthSource(Source source, long j, boolean z) {
        super(source);
        this.size = j;
        this.truncate = z;
    }

    @Override // okio.ForwardingSource, okio.Source
    public final long read(Buffer buffer, long j) {
        long j2 = this.bytesReceived;
        long j3 = this.size;
        if (j2 > j3) {
            j = 0;
        } else if (this.truncate) {
            long j4 = j3 - j2;
            if (j4 == 0) {
                return -1L;
            }
            j = Math.min(j, j4);
        }
        long read = this.delegate.read(buffer, j);
        if (read != -1) {
            this.bytesReceived += read;
        }
        long j5 = this.bytesReceived;
        long j6 = this.size;
        if ((j5 >= j6 || read != -1) && j5 <= j6) {
            return read;
        }
        if (read > 0 && j5 > j6) {
            long j7 = buffer.size - (j5 - j6);
            Buffer buffer2 = new Buffer();
            buffer2.writeAll(buffer);
            buffer.write(buffer2, j7);
            buffer2.skip(buffer2.size);
        }
        long j8 = this.size;
        long j9 = this.bytesReceived;
        StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("expected ", j8, " bytes but got ");
        m.append(j9);
        throw new IOException(m.toString());
    }
}
