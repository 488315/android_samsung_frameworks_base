package com.android.net.module.util;

/* loaded from: classes5.dex */
public class ByteUtils {
    public static int indexOf(byte[] array, byte target) {
        return indexOf(array, target, 0, array.length);
    }

    private static int indexOf(byte[] array, byte target, int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static byte[] concat(byte[]... arrays) {
        int length = 0;
        for (byte[] bArr : arrays) {
            length += bArr.length;
        }
        byte[] result = new byte[length];
        int pos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }
        return result;
    }
}
