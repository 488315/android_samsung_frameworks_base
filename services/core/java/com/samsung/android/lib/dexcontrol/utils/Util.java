package com.samsung.android.lib.dexcontrol.utils;

import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

public abstract class Util {
    public static String byteArrayToHex(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            i =
                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                            "%02x", new Object[] {Integer.valueOf(bArr[i] & 255)}, sb, i, 1);
        }
        return sb.toString();
    }
}
