package com.google.zxing.oned;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.google.zxing.BarcodeFormat;
import java.util.Collection;
import java.util.Collections;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Code39Writer extends OneDimensionalCodeWriter {
    public static void toIntArray(int i, int[] iArr) {
        for (int i2 = 0; i2 < 9; i2++) {
            int i3 = 1;
            if (((1 << (8 - i2)) & i) != 0) {
                i3 = 2;
            }
            iArr[i2] = i3;
        }
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final boolean[] encode(String str) {
        int length = str.length();
        if (length > 80) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(length, "Requested contents should be less than 80 digits long, but got "));
        }
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(str.charAt(i)) < 0) {
                int length2 = str.length();
                StringBuilder sb = new StringBuilder();
                for (int i2 = 0; i2 < length2; i2++) {
                    char charAt = str.charAt(i2);
                    if (charAt != 0) {
                        if (charAt != ' ') {
                            if (charAt == '@') {
                                sb.append("%V");
                            } else if (charAt == '`') {
                                sb.append("%W");
                            } else if (charAt != '-' && charAt != '.') {
                                if (charAt <= 26) {
                                    sb.append('$');
                                    sb.append((char) (charAt + '@'));
                                } else if (charAt < ' ') {
                                    sb.append('%');
                                    sb.append((char) (charAt + '&'));
                                } else if (charAt <= ',' || charAt == '/' || charAt == ':') {
                                    sb.append('/');
                                    sb.append((char) (charAt + ' '));
                                } else if (charAt <= '9') {
                                    sb.append(charAt);
                                } else if (charAt <= '?') {
                                    sb.append('%');
                                    sb.append((char) (charAt + 11));
                                } else if (charAt <= 'Z') {
                                    sb.append(charAt);
                                } else if (charAt <= '_') {
                                    sb.append('%');
                                    sb.append((char) (charAt - 16));
                                } else if (charAt <= 'z') {
                                    sb.append('+');
                                    sb.append((char) (charAt - ' '));
                                } else {
                                    if (charAt > 127) {
                                        throw new IllegalArgumentException("Requested content contains a non-encodable character: '" + str.charAt(i2) + "'");
                                    }
                                    sb.append('%');
                                    sb.append((char) (charAt - '+'));
                                }
                            }
                        }
                        sb.append(charAt);
                    } else {
                        sb.append("%U");
                    }
                }
                str = sb.toString();
                length = str.length();
                if (length > 80) {
                    throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(length, "Requested contents should be less than 80 digits long, but got ", " (extended full ASCII mode)"));
                }
            } else {
                i++;
            }
        }
        int[] iArr = new int[9];
        boolean[] zArr = new boolean[(length * 13) + 25];
        toIntArray(148, iArr);
        int appendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, iArr, true);
        int[] iArr2 = {1};
        int appendPattern2 = OneDimensionalCodeWriter.appendPattern(zArr, appendPattern, iArr2, false) + appendPattern;
        for (int i3 = 0; i3 < length; i3++) {
            toIntArray(Code39Reader.CHARACTER_ENCODINGS["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(str.charAt(i3))], iArr);
            int appendPattern3 = OneDimensionalCodeWriter.appendPattern(zArr, appendPattern2, iArr, true) + appendPattern2;
            appendPattern2 = OneDimensionalCodeWriter.appendPattern(zArr, appendPattern3, iArr2, false) + appendPattern3;
        }
        toIntArray(148, iArr);
        OneDimensionalCodeWriter.appendPattern(zArr, appendPattern2, iArr, true);
        return zArr;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final Collection getSupportedWriteFormats() {
        return Collections.singleton(BarcodeFormat.CODE_39);
    }
}
