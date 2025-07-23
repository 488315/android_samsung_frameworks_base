package okio;

import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import okio.Buffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* renamed from: okio.-SegmentedByteString, reason: invalid class name */
/* loaded from: classes4.dex */
public abstract class SegmentedByteString {
    public static final int DEFAULT__ByteString_size;

    static {
        new Buffer.UnsafeCursor();
        DEFAULT__ByteString_size = -1234567890;
    }

    public static final boolean arrayRangeEquals(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            if (bArr[i4 + i] != bArr2[i4 + i2]) {
                return false;
            }
        }
        return true;
    }

    public static final void checkOffsetAndCount(long j, long j2, long j3) {
        if ((j2 | j3) < 0 || j2 > j || j - j2 < j3) {
            StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("size=", j, " offset=");
            m.append(j2);
            m.append(" byteCount=");
            m.append(j3);
            throw new ArrayIndexOutOfBoundsException(m.toString());
        }
    }
}
