package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EAN13Writer extends UPCEANWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public final BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map map) {
        if (barcodeFormat == BarcodeFormat.EAN_13) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_13, but got " + barcodeFormat);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00aa A[Catch: FormatException -> 0x00b2, TRY_ENTER, TryCatch #0 {FormatException -> 0x00b2, blocks: (B:7:0x0008, B:29:0x00aa, B:30:0x00b1, B:31:0x0013, B:34:0x001a, B:37:0x0024, B:39:0x0028, B:40:0x002a, B:43:0x002b, B:45:0x0030, B:48:0x003a, B:50:0x003e, B:51:0x0040, B:54:0x0041), top: B:6:0x0008 }] */
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean[] encode(String str) {
        boolean z;
        if (str.length() == 13) {
            try {
                int[] iArr = UPCEANReader.START_END_PATTERN;
                int length = str.length();
                if (length != 0) {
                    int i = 0;
                    for (int i2 = length - 2; i2 >= 0; i2 -= 2) {
                        int charAt = str.charAt(i2) - '0';
                        if (charAt < 0 || charAt > 9) {
                            throw FormatException.instance;
                        }
                        i += charAt;
                    }
                    int i3 = i * 3;
                    for (int i4 = length - 1; i4 >= 0; i4 -= 2) {
                        int charAt2 = str.charAt(i4) - '0';
                        if (charAt2 < 0 || charAt2 > 9) {
                            throw FormatException.instance;
                        }
                        i3 += charAt2;
                    }
                    if (i3 % 10 == 0) {
                        z = true;
                        if (!z) {
                            int i5 = EAN13Reader.FIRST_DIGIT_ENCODINGS[Integer.parseInt(str.substring(0, 1))];
                            boolean[] zArr = new boolean[95];
                            int appendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, UPCEANReader.START_END_PATTERN, true) + 0;
                            int i6 = 1;
                            while (i6 <= 6) {
                                int i7 = i6 + 1;
                                int parseInt = Integer.parseInt(str.substring(i6, i7));
                                if (((i5 >> (6 - i6)) & 1) == 1) {
                                    parseInt += 10;
                                }
                                appendPattern += OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, UPCEANReader.L_AND_G_PATTERNS[parseInt], false);
                                i6 = i7;
                            }
                            int appendPattern2 = OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, UPCEANReader.MIDDLE_PATTERN, false) + appendPattern;
                            int i8 = 7;
                            while (i8 <= 12) {
                                int i9 = i8 + 1;
                                appendPattern2 += OneDimensionalCodeWriter.appendPattern(zArr, appendPattern2, UPCEANReader.L_PATTERNS[Integer.parseInt(str.substring(i8, i9))], true);
                                i8 = i9;
                            }
                            OneDimensionalCodeWriter.appendPattern(zArr, appendPattern2, UPCEANReader.START_END_PATTERN, true);
                            return zArr;
                        }
                        throw new IllegalArgumentException("Contents do not pass checksum");
                    }
                }
                z = false;
                if (!z) {
                }
            } catch (FormatException unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        } else {
            throw new IllegalArgumentException("Requested contents should be 13 digits long, but got " + str.length());
        }
    }
}
