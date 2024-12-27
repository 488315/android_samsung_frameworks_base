package com.samsung.android.lib.dexcontrol.utils;

import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Util {
    public static String byteArrayToHex(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        StringBuilder sb = new StringBuilder();
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            i = MARsFreezeStateRecord$$ExternalSyntheticOutline0.m("%02x", new Object[]{Integer.valueOf(bArr[i] & 255)}, sb, i, 1);
        }
        return sb.toString();
    }
}
