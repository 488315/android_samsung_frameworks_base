package com.android.modules.utils;

import com.android.internal.midi.MidiConstants;
import java.io.UTFDataFormatException;

/* loaded from: classes6.dex */
public class ModifiedUtf8 {
    public static String decode(byte[] bArr, char[] cArr, int i, int i2) throws UTFDataFormatException {
        int i3;
        int i4;
        int i5 = 0;
        int i6 = 0;
        while (i5 < i2) {
            int i7 = i5 + 1;
            char c = (char) bArr[i + i5];
            cArr[i6] = c;
            if (c < 128) {
                i6++;
                i5 = i7;
            } else {
                if ((c & 224) == 192) {
                    if (i7 >= i2) {
                        throw new UTFDataFormatException("bad second byte at " + i7);
                    }
                    i3 = i5 + 2;
                    byte b = bArr[i7 + i];
                    if ((b & MidiConstants.STATUS_PROGRAM_CHANGE) != 128) {
                        throw new UTFDataFormatException("bad second byte at " + (i5 + 1));
                    }
                    i4 = i6 + 1;
                    cArr[i6] = (char) ((b & 63) | ((c & 31) << 6));
                } else {
                    if ((c & 240) != 224) {
                        throw new UTFDataFormatException("bad byte at " + i5);
                    }
                    int i8 = i5 + 2;
                    if (i8 >= i2) {
                        throw new UTFDataFormatException("bad third byte at " + i8);
                    }
                    byte b2 = bArr[i7 + i];
                    i3 = i5 + 3;
                    byte b3 = bArr[i8 + i];
                    if ((b2 & MidiConstants.STATUS_PROGRAM_CHANGE) != 128 || (b3 & MidiConstants.STATUS_PROGRAM_CHANGE) != 128) {
                        throw new UTFDataFormatException("bad second or third byte at " + (i5 + 1));
                    }
                    i4 = i6 + 1;
                    cArr[i6] = (char) (((b2 & 63) << 6) | ((c & 15) << 12) | (b3 & 63));
                }
                i6 = i4;
                i5 = i3;
            }
        }
        return new String(cArr, 0, i6);
    }

    public static long countBytes(String str, boolean z) throws UTFDataFormatException {
        int length = str.length();
        long j = 0;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            j += (charAt == 0 || charAt > 127) ? charAt <= 2047 ? 2L : 3L : 1L;
            if (z && j > 65535) {
                throw new UTFDataFormatException("String more than 65535 UTF bytes long");
            }
        }
        return j;
    }

    public static void encode(byte[] bArr, int i, String str) {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt != 0 && charAt <= 127) {
                bArr[i] = (byte) charAt;
                i++;
            } else if (charAt <= 2047) {
                int i3 = i + 1;
                bArr[i] = (byte) (((charAt >> 6) & 31) | 192);
                i += 2;
                bArr[i3] = (byte) ((charAt & '?') | 128);
            } else {
                bArr[i] = (byte) (((charAt >> '\f') & 15) | 224);
                int i4 = i + 2;
                bArr[i + 1] = (byte) (((charAt >> 6) & 63) | 128);
                i += 3;
                bArr[i4] = (byte) ((charAt & '?') | 128);
            }
        }
    }

    private ModifiedUtf8() {
    }
}
