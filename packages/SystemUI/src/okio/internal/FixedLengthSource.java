package okio.internal;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import java.io.IOException;
import okio.Buffer;
import okio.ForwardingSource;
import okio.Source;

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
    public final long read(Buffer buffer, long j) throws IOException {
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
        long j5 = this.delegate.read(buffer, j);
        if (j5 != -1) {
            this.bytesReceived += j5;
        }
        long j6 = this.bytesReceived;
        long j7 = this.size;
        if ((j6 >= j7 || j5 != -1) && j6 <= j7) {
            return j5;
        }
        if (j5 > 0 && j6 > j7) {
            long j8 = buffer.size - (j6 - j7);
            Buffer buffer2 = new Buffer();
            buffer2.writeAll(buffer);
            buffer.write(buffer2, j8);
            buffer2.skip(buffer2.size);
        }
        long j9 = this.size;
        long j10 = this.bytesReceived;
        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("expected ", j9, " bytes but got ");
        sbM.append(j10);
        throw new IOException(sbM.toString());
    }
}
