package com.samsung.android.knox.dar.ddar.securesession;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class Wiper {
    public static void wipe(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        Arrays.fill(bArr, (byte) 0);
    }

    public static void wipe(char[] cArr) {
        if (cArr == null) {
            return;
        }
        Arrays.fill(cArr, (char) 0);
    }

    public static void wipe(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return;
        }
        wipe(byteBuffer.array());
    }

    public static void wipe(CharBuffer charBuffer) {
        if (charBuffer == null) {
            return;
        }
        wipe(charBuffer.array());
    }
}
