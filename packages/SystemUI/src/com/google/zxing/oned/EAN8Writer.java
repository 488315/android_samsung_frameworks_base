package com.google.zxing.oned;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;
import java.util.Collection;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EAN8Writer extends UPCEANWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        int length = str.length();
        if (length == 7) {
            try {
                str = str + UPCEANReader.getStandardUPCEANChecksum(str);
            } catch (FormatException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            if (length != 8) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(length, "Requested contents should be 7 or 8 digits long, but got "));
            }
            try {
                if (!UPCEANReader.checkStandardUPCEANChecksum(str)) {
                    throw new IllegalArgumentException("Contents do not pass checksum");
                }
            } catch (FormatException unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        }
        OneDimensionalCodeWriter.checkNumeric(str);
        boolean[] zArr = new boolean[67];
        int appendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, UPCEANReader.START_END_PATTERN, true);
        for (int i = 0; i <= 3; i++) {
            appendPattern += OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, UPCEANReader.L_PATTERNS[Character.digit(str.charAt(i), 10)], false);
        }
        int appendPattern2 = OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, UPCEANReader.MIDDLE_PATTERN, false) + appendPattern;
        for (int i2 = 4; i2 <= 7; i2++) {
            appendPattern2 += OneDimensionalCodeWriter.appendPattern(zArr, appendPattern2, UPCEANReader.L_PATTERNS[Character.digit(str.charAt(i2), 10)], true);
        }
        OneDimensionalCodeWriter.appendPattern(zArr, appendPattern2, UPCEANReader.START_END_PATTERN, true);
        return zArr;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final Collection getSupportedWriteFormats() {
        return Collections.singleton(BarcodeFormat.EAN_8);
    }
}
