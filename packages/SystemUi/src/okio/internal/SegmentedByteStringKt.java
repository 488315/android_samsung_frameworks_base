package okio.internal;

import okio.SegmentedByteString;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SegmentedByteStringKt {
    public static final int segment(SegmentedByteString segmentedByteString, int i) {
        int i2;
        int[] iArr = segmentedByteString.directory;
        int i3 = i + 1;
        int length = segmentedByteString.segments.length - 1;
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
