package com.google.android.mms.pdu;

import java.io.ByteArrayOutputStream;

/* loaded from: classes6.dex */
public class QuotedPrintable {
    private static byte ESCAPE_CHAR = 61;

    public static final byte[] decodeQuotedPrintable(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (b == ESCAPE_CHAR) {
                try {
                    byte b2 = bArr[i + 1];
                    if ('\r' == ((char) b2)) {
                        int i2 = i + 2;
                        if ('\n' == ((char) bArr[i2])) {
                            i = i2;
                        }
                    }
                    int iDigit = Character.digit((char) b2, 16);
                    i += 2;
                    int iDigit2 = Character.digit((char) bArr[i], 16);
                    if (iDigit != -1 && iDigit2 != -1) {
                        byteArrayOutputStream.write((char) ((iDigit << 4) + iDigit2));
                    }
                } catch (ArrayIndexOutOfBoundsException unused) {
                }
                return null;
            }
            byteArrayOutputStream.write(b);
            i++;
        }
        return byteArrayOutputStream.toByteArray();
    }
}
