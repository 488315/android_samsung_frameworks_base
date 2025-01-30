package com.google.zxing.oned;

import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CodaBarWriter extends OneDimensionalCodeWriter {
    public static final char[] START_END_CHARS = {'A', 'B', 'C', 'D'};
    public static final char[] ALT_START_END_CHARS = {'T', 'N', '*', 'E'};

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        int i;
        if (str.length() < 2) {
            throw new IllegalArgumentException("Codabar should start/end with start/stop symbols");
        }
        char upperCase = Character.toUpperCase(str.charAt(0));
        char upperCase2 = Character.toUpperCase(str.charAt(str.length() - 1));
        char[] cArr = START_END_CHARS;
        boolean z = CodaBarReader.arrayContains(upperCase, cArr) && CodaBarReader.arrayContains(upperCase2, cArr);
        char[] cArr2 = ALT_START_END_CHARS;
        boolean z2 = CodaBarReader.arrayContains(upperCase, cArr2) && CodaBarReader.arrayContains(upperCase2, cArr2);
        if (!z && !z2) {
            throw new IllegalArgumentException("Codabar should start/end with " + Arrays.toString(cArr) + ", or start/end with " + Arrays.toString(cArr2));
        }
        char[] cArr3 = {'/', ':', '+', '.'};
        int i2 = 20;
        for (int i3 = 1; i3 < str.length() - 1; i3++) {
            if (Character.isDigit(str.charAt(i3)) || str.charAt(i3) == '-' || str.charAt(i3) == '$') {
                i2 += 9;
            } else {
                if (!CodaBarReader.arrayContains(str.charAt(i3), cArr3)) {
                    throw new IllegalArgumentException("Cannot encode : '" + str.charAt(i3) + '\'');
                }
                i2 += 10;
            }
        }
        boolean[] zArr = new boolean[(str.length() - 1) + i2];
        int i4 = 0;
        for (int i5 = 0; i5 < str.length(); i5++) {
            char upperCase3 = Character.toUpperCase(str.charAt(i5));
            if (i5 == str.length() - 1) {
                if (upperCase3 == '*') {
                    upperCase3 = 'C';
                } else if (upperCase3 == 'E') {
                    upperCase3 = 'D';
                } else if (upperCase3 == 'N') {
                    upperCase3 = 'B';
                } else if (upperCase3 == 'T') {
                    upperCase3 = 'A';
                }
            }
            int i6 = 0;
            while (true) {
                char[] cArr4 = CodaBarReader.ALPHABET;
                if (i6 >= cArr4.length) {
                    i = 0;
                    break;
                }
                if (upperCase3 == cArr4[i6]) {
                    i = CodaBarReader.CHARACTER_ENCODINGS[i6];
                    break;
                }
                i6++;
            }
            int i7 = 0;
            int i8 = 0;
            boolean z3 = true;
            while (i7 < 7) {
                zArr[i4] = z3;
                i4++;
                if (((i >> (6 - i7)) & 1) == 0 || i8 == 1) {
                    z3 = !z3;
                    i7++;
                    i8 = 0;
                } else {
                    i8++;
                }
            }
            if (i5 < str.length() - 1) {
                zArr[i4] = false;
                i4++;
            }
        }
        return zArr;
    }
}
