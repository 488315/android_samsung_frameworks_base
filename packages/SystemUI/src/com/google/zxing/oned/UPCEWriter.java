package com.google.zxing.oned;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;
import java.util.Collection;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class UPCEWriter extends UPCEANWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        int length = str.length();
        if (length == 7) {
            try {
                str = str + UPCEANReader.getStandardUPCEANChecksum(UPCEReader.convertUPCEtoUPCA(str));
            } catch (FormatException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            if (length != 8) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(length, "Requested contents should be 7 or 8 digits long, but got "));
            }
            try {
                if (!UPCEANReader.checkStandardUPCEANChecksum(UPCEReader.convertUPCEtoUPCA(str))) {
                    throw new IllegalArgumentException("Contents do not pass checksum");
                }
            } catch (FormatException unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        }
        OneDimensionalCodeWriter.checkNumeric(str);
        int digit = Character.digit(str.charAt(0), 10);
        if (digit != 0 && digit != 1) {
            throw new IllegalArgumentException("Number system must be 0 or 1");
        }
        int i = UPCEReader.NUMSYS_AND_CHECK_DIGIT_PATTERNS[digit][Character.digit(str.charAt(7), 10)];
        boolean[] zArr = new boolean[51];
        int appendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, UPCEANReader.START_END_PATTERN, true);
        for (int i2 = 1; i2 <= 6; i2++) {
            int digit2 = Character.digit(str.charAt(i2), 10);
            if (((i >> (6 - i2)) & 1) == 1) {
                digit2 += 10;
            }
            appendPattern += OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, UPCEANReader.L_AND_G_PATTERNS[digit2], false);
        }
        OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, UPCEANReader.END_PATTERN, false);
        return zArr;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final Collection getSupportedWriteFormats() {
        return Collections.singleton(BarcodeFormat.UPC_E);
    }
}
