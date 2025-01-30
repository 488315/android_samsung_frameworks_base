package com.google.zxing.oned;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ITFWriter extends OneDimensionalCodeWriter {
    public static final int[] START_PATTERN = {1, 1, 1, 1};
    public static final int[] END_PATTERN = {3, 1, 1};

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (barcodeFormat == BarcodeFormat.ITF) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode ITF, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("The lenght of the input should be even");
        }
        if (length <= 80) {
            boolean[] zArr = new boolean[(length * 9) + 9];
            int appendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, START_PATTERN, true);
            for (int i = 0; i < length; i += 2) {
                int digit = Character.digit(str.charAt(i), 10);
                int digit2 = Character.digit(str.charAt(i + 1), 10);
                int[] iArr = new int[18];
                for (int i2 = 0; i2 < 5; i2++) {
                    int i3 = i2 << 1;
                    int[][] iArr2 = ITFReader.PATTERNS;
                    iArr[i3] = iArr2[digit][i2];
                    iArr[i3 + 1] = iArr2[digit2][i2];
                }
                appendPattern += OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, iArr, true);
            }
            OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, END_PATTERN, true);
            return zArr;
        }
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Requested contents should be less than 80 digits long, but got ", length));
    }
}
