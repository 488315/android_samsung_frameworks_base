package androidx.compose.ui.spatial;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class RectList {
    public int itemsSize;
    public long[] items = new long[192];
    public long[] stack = new long[192];

    public static void insert$default(RectList rectList, int i, int i2, int i3, int i4, int i5, int i6) {
        long[] jArr = rectList.items;
        int i7 = rectList.itemsSize;
        int i8 = i7 + 3;
        rectList.itemsSize = i8;
        int length = jArr.length;
        if (length <= i8) {
            int iMax = Math.max(length * 2, i8);
            rectList.items = Arrays.copyOf(jArr, iMax);
            rectList.stack = Arrays.copyOf(rectList.stack, iMax);
        }
        long[] jArr2 = rectList.items;
        jArr2[i7] = (i2 << 32) | (i3 & 4294967295L);
        jArr2[i7 + 1] = (i5 & 4294967295L) | (i4 << 32);
        int i9 = i6 & 67108863;
        jArr2[i7 + 2] = (0 << 63) | (0 << 62) | (1 << 61) | (0 << 52) | (i9 << 26) | (i & 67108863);
        if (i6 < 0) {
            return;
        }
        for (int i10 = i7 - 3; i10 >= 0; i10 -= 3) {
            int i11 = i10 + 2;
            long j = jArr2[i11];
            if ((((int) j) & 67108863) == i9) {
                jArr2[i11] = (j & (-2301339409586323457L)) | (((i7 - i10) & 511) << 52);
                return;
            }
        }
    }
}
