package com.samsung.android.lib.dexcontrol.utils;

/* loaded from: classes2.dex */
public abstract class Util {
    public static String byteArrayToHex(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Integer.valueOf(b & 255)));
        }
        return sb.toString();
    }
}
