package com.google.zxing.oned;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import java.util.Collection;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Code93Writer extends OneDimensionalCodeWriter {
    public static void appendPattern(boolean[] zArr, int i, int i2) {
        for (int i3 = 0; i3 < 9; i3++) {
            boolean z = true;
            int i4 = i + i3;
            if (((1 << (8 - i3)) & i2) == 0) {
                z = false;
            }
            zArr[i4] = z;
        }
    }

    public static int computeChecksumIndex(int i, String str) {
        int i2 = 0;
        int i3 = 1;
        for (int length = str.length() - 1; length >= 0; length--) {
            i2 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(length)) * i3;
            i3++;
            if (i3 > i) {
                i3 = 1;
            }
        }
        return i2 % 47;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(length * 2);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == 0) {
                sb.append("bU");
            } else if (charAt <= 26) {
                sb.append('a');
                sb.append((char) (charAt + '@'));
            } else if (charAt <= 31) {
                sb.append('b');
                sb.append((char) (charAt + '&'));
            } else if (charAt == ' ' || charAt == '$' || charAt == '%' || charAt == '+') {
                sb.append(charAt);
            } else if (charAt <= ',') {
                sb.append('c');
                sb.append((char) (charAt + ' '));
            } else if (charAt <= '9') {
                sb.append(charAt);
            } else if (charAt == ':') {
                sb.append("cZ");
            } else if (charAt <= '?') {
                sb.append('b');
                sb.append((char) (charAt + 11));
            } else if (charAt == '@') {
                sb.append("bV");
            } else if (charAt <= 'Z') {
                sb.append(charAt);
            } else if (charAt <= '_') {
                sb.append('b');
                sb.append((char) (charAt - 16));
            } else if (charAt == '`') {
                sb.append("bW");
            } else if (charAt <= 'z') {
                sb.append('d');
                sb.append((char) (charAt - ' '));
            } else {
                if (charAt > 127) {
                    throw new IllegalArgumentException("Requested content contains a non-encodable character: '" + charAt + "'");
                }
                sb.append('b');
                sb.append((char) (charAt - '+'));
            }
        }
        String sb2 = sb.toString();
        int length2 = sb2.length();
        if (length2 > 80) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(length2, "Requested contents should be less than 80 digits long after converting to extended encoding, but got "));
        }
        int i2 = 9;
        boolean[] zArr = new boolean[((sb2.length() + 4) * 9) + 1];
        appendPattern(zArr, 0, Code93Reader.ASTERISK_ENCODING);
        for (int i3 = 0; i3 < length2; i3++) {
            appendPattern(zArr, i2, Code93Reader.CHARACTER_ENCODINGS["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(sb2.charAt(i3))]);
            i2 += 9;
        }
        int computeChecksumIndex = computeChecksumIndex(20, sb2);
        int[] iArr = Code93Reader.CHARACTER_ENCODINGS;
        appendPattern(zArr, i2, iArr[computeChecksumIndex]);
        StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(sb2);
        m.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".charAt(computeChecksumIndex));
        appendPattern(zArr, i2 + 9, iArr[computeChecksumIndex(15, m.toString())]);
        appendPattern(zArr, i2 + 18, Code93Reader.ASTERISK_ENCODING);
        zArr[i2 + 27] = true;
        return zArr;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final Collection getSupportedWriteFormats() {
        return Collections.singleton(BarcodeFormat.CODE_93);
    }
}
