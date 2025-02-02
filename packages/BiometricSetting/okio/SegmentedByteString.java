package okio;

import java.nio.charset.Charset;
import java.util.Arrays;

/* loaded from: classes.dex */
final class SegmentedByteString extends ByteString {
    final transient int[] directory;
    final transient byte[][] segments;

    SegmentedByteString(Buffer buffer, int i) {
        super(null);
        Util.checkOffsetAndCount(buffer.size, 0L, i);
        Segment segment = buffer.head;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            int i5 = segment.limit;
            int i6 = segment.pos;
            if (i5 == i6) {
                throw new AssertionError("s.limit == s.pos");
            }
            i3 += i5 - i6;
            i4++;
            segment = segment.next;
        }
        this.segments = new byte[i4][];
        this.directory = new int[i4 * 2];
        Segment segment2 = buffer.head;
        int i7 = 0;
        while (i2 < i) {
            byte[][] bArr = this.segments;
            bArr[i7] = segment2.data;
            int i8 = segment2.limit;
            int i9 = segment2.pos;
            int i10 = (i8 - i9) + i2;
            i2 = i10 > i ? i : i10;
            int[] iArr = this.directory;
            iArr[i7] = i2;
            iArr[bArr.length + i7] = i9;
            segment2.shared = true;
            i7++;
            segment2 = segment2.next;
        }
    }

    private int segment(int i) {
        int binarySearch = Arrays.binarySearch(this.directory, 0, this.segments.length, i + 1);
        return binarySearch >= 0 ? binarySearch : ~binarySearch;
    }

    private Object writeReplace() {
        return new ByteString(toByteArray());
    }

    @Override // okio.ByteString
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == size() && rangeEquals(byteString, size())) {
                return true;
            }
        }
        return false;
    }

    @Override // okio.ByteString
    public final byte getByte(int i) {
        Util.checkOffsetAndCount(this.directory[this.segments.length - 1], i, 1L);
        int segment = segment(i);
        int i2 = segment == 0 ? 0 : this.directory[segment - 1];
        int[] iArr = this.directory;
        byte[][] bArr = this.segments;
        return bArr[segment][(i - i2) + iArr[bArr.length + segment]];
    }

    @Override // okio.ByteString
    public final int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int length = this.segments.length;
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        while (i2 < length) {
            byte[] bArr = this.segments[i2];
            int[] iArr = this.directory;
            int i5 = iArr[length + i2];
            int i6 = iArr[i2];
            int i7 = (i6 - i4) + i5;
            while (i5 < i7) {
                i3 = (i3 * 31) + bArr[i5];
                i5++;
            }
            i2++;
            i4 = i6;
        }
        this.hashCode = i3;
        return i3;
    }

    @Override // okio.ByteString
    public final String hex() {
        return new ByteString(toByteArray()).hex();
    }

    @Override // okio.ByteString
    public final boolean rangeEquals(ByteString byteString, int i) {
        if (size() - i < 0) {
            return false;
        }
        int segment = segment(0);
        int i2 = 0;
        int i3 = 0;
        while (i > 0) {
            int i4 = segment == 0 ? 0 : this.directory[segment - 1];
            int min = Math.min(i, ((this.directory[segment] - i4) + i4) - i2);
            int[] iArr = this.directory;
            byte[][] bArr = this.segments;
            if (!byteString.rangeEquals(i3, bArr[segment], (i2 - i4) + iArr[bArr.length + segment], min)) {
                return false;
            }
            i2 += min;
            i3 += min;
            i -= min;
            segment++;
        }
        return true;
    }

    @Override // okio.ByteString
    public final int size() {
        return this.directory[this.segments.length - 1];
    }

    @Override // okio.ByteString
    public final ByteString substring() {
        return new ByteString(toByteArray()).substring();
    }

    public final byte[] toByteArray() {
        int[] iArr = this.directory;
        byte[][] bArr = this.segments;
        byte[] bArr2 = new byte[iArr[bArr.length - 1]];
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int[] iArr2 = this.directory;
            int i3 = iArr2[length + i];
            int i4 = iArr2[i];
            System.arraycopy(this.segments[i], i3, bArr2, i2, i4 - i2);
            i++;
            i2 = i4;
        }
        return bArr2;
    }

    @Override // okio.ByteString
    public final String toString() {
        return new ByteString(toByteArray()).toString();
    }

    @Override // okio.ByteString
    public final String utf8() {
        return new ByteString(toByteArray()).utf8();
    }

    @Override // okio.ByteString
    public final boolean rangeEquals(int i, byte[] bArr, int i2, int i3) {
        if (i < 0 || i > size() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int segment = segment(i);
        while (true) {
            boolean z = true;
            if (i3 <= 0) {
                return true;
            }
            int i4 = segment == 0 ? 0 : this.directory[segment - 1];
            int min = Math.min(i3, ((this.directory[segment] - i4) + i4) - i);
            int[] iArr = this.directory;
            byte[][] bArr2 = this.segments;
            int i5 = (i - i4) + iArr[bArr2.length + segment];
            byte[] bArr3 = bArr2[segment];
            Charset charset = Util.UTF_8;
            int i6 = 0;
            while (true) {
                if (i6 >= min) {
                    break;
                }
                if (bArr3[i6 + i5] != bArr[i6 + i2]) {
                    z = false;
                    break;
                }
                i6++;
            }
            if (!z) {
                return false;
            }
            i += min;
            i2 += min;
            i3 -= min;
            segment++;
        }
    }
}
