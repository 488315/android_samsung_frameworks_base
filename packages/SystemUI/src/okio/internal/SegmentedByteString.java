package okio.internal;

import okio.C1238SegmentedByteString;

/* renamed from: okio.internal.-SegmentedByteString, reason: invalid class name */
/* loaded from: classes4.dex */
public abstract class SegmentedByteString {
    public static final int segment(C1238SegmentedByteString c1238SegmentedByteString, int i) {
        int i2;
        int[] iArr = c1238SegmentedByteString.directory;
        int i3 = i + 1;
        int length = c1238SegmentedByteString.segments.length - 1;
        int i4 = 0;
        while (true) {
            if (i4 <= length) {
                i2 = (i4 + length) >>> 1;
                int i5 = iArr[i2];
                if (i5 >= i3) {
                    if (i5 <= i3) {
                        break;
                    }
                    length = i2 - 1;
                } else {
                    i4 = i2 + 1;
                }
            } else {
                i2 = (-i4) - 1;
                break;
            }
        }
        return i2 >= 0 ? i2 : ~i2;
    }
}
