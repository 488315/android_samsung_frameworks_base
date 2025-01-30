package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class OneDimensionalCodeWriter implements Writer {
    public static int appendPattern(boolean[] zArr, int i, int[] iArr, boolean z) {
        int i2 = 0;
        for (int i3 : iArr) {
            int i4 = 0;
            while (i4 < i3) {
                zArr[i] = z;
                i4++;
                i++;
            }
            i2 += i3;
            z = !z;
        }
        return i2;
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }
        if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("Negative size is not allowed. Input: " + i + 'x' + i2);
        }
        int defaultMargin = getDefaultMargin();
        Integer num = (Integer) ((HashMap) map).get(EncodeHintType.MARGIN);
        if (num != null) {
            defaultMargin = num.intValue();
        }
        boolean[] encode = encode(str);
        int length = encode.length;
        int i3 = defaultMargin + length;
        int max = Math.max(i, i3);
        int max2 = Math.max(1, i2);
        int i4 = max / i3;
        int i5 = (max - (length * i4)) / 2;
        BitMatrix bitMatrix = new BitMatrix(max, max2);
        int i6 = 0;
        while (i6 < length) {
            if (encode[i6]) {
                bitMatrix.setRegion(i5, 0, i4, max2);
            }
            i6++;
            i5 += i4;
        }
        return bitMatrix;
    }

    public abstract boolean[] encode(String str);

    public int getDefaultMargin() {
        return 10;
    }
}
