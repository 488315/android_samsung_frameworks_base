package okio;

import okio.internal.SegmentedByteStringKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SegmentedByteString extends ByteString {
    public final transient int[] directory;
    public final transient byte[][] segments;

    public SegmentedByteString(byte[][] bArr, int[] iArr) {
        super(ByteString.EMPTY.getData$okio());
        this.segments = bArr;
        this.directory = iArr;
    }

    private final Object writeReplace() {
        return new ByteString(toByteArray());
    }

    @Override // okio.ByteString
    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj instanceof ByteString) {
                ByteString byteString = (ByteString) obj;
                if (byteString.getSize$okio() != getSize$okio() || !rangeEquals(byteString, getSize$okio())) {
                }
            }
            return false;
        }
        return true;
    }

    @Override // okio.ByteString
    public final int getSize$okio() {
        return this.directory[this.segments.length - 1];
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
            int[] iArr = this.directory;
            int i5 = iArr[length + i2];
            int i6 = iArr[i2];
            byte[] bArr = this.segments[i2];
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
    public final byte[] internalArray$okio() {
        return toByteArray();
    }

    @Override // okio.ByteString
    public final byte internalGet$okio(int i) {
        Util.checkOffsetAndCount(this.directory[this.segments.length - 1], i, 1L);
        int segment = SegmentedByteStringKt.segment(this, i);
        int i2 = segment == 0 ? 0 : this.directory[segment - 1];
        int[] iArr = this.directory;
        byte[][] bArr = this.segments;
        return bArr[segment][(i - i2) + iArr[bArr.length + segment]];
    }

    @Override // okio.ByteString
    public final boolean rangeEquals(int i, int i2, int i3, byte[] bArr) {
        if (i < 0 || i > getSize$okio() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int i4 = i3 + i;
        int segment = SegmentedByteStringKt.segment(this, i);
        while (true) {
            boolean z = true;
            if (i >= i4) {
                return true;
            }
            int i5 = segment == 0 ? 0 : this.directory[segment - 1];
            int[] iArr = this.directory;
            int i6 = iArr[segment] - i5;
            int i7 = iArr[this.segments.length + segment];
            int min = Math.min(i4, i6 + i5) - i;
            int i8 = (i - i5) + i7;
            byte[] bArr2 = this.segments[segment];
            int i9 = 0;
            while (true) {
                if (i9 >= min) {
                    break;
                }
                if (bArr2[i9 + i8] != bArr[i9 + i2]) {
                    z = false;
                    break;
                }
                i9++;
            }
            if (!z) {
                return false;
            }
            i2 += min;
            i += min;
            segment++;
        }
    }

    public final byte[] toByteArray() {
        byte[] bArr = new byte[getSize$okio()];
        int length = this.segments.length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            int[] iArr = this.directory;
            int i4 = iArr[length + i];
            int i5 = iArr[i];
            int i6 = i5 - i2;
            System.arraycopy(this.segments[i], i4, bArr, i3, (i4 + i6) - i4);
            i3 += i6;
            i++;
            i2 = i5;
        }
        return bArr;
    }

    @Override // okio.ByteString
    public final String toString() {
        return new ByteString(toByteArray()).toString();
    }

    @Override // okio.ByteString
    public final boolean rangeEquals(ByteString byteString, int i) {
        if (getSize$okio() - i < 0) {
            return false;
        }
        int i2 = i + 0;
        int segment = SegmentedByteStringKt.segment(this, 0);
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = segment == 0 ? 0 : this.directory[segment - 1];
            int[] iArr = this.directory;
            int i6 = iArr[segment] - i5;
            int i7 = iArr[this.segments.length + segment];
            int min = Math.min(i2, i6 + i5) - i3;
            if (!byteString.rangeEquals(i4, (i3 - i5) + i7, min, this.segments[segment])) {
                return false;
            }
            i4 += min;
            i3 += min;
            segment++;
        }
        return true;
    }
}
