package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
class Utils {
    Utils() {
    }

    static byte[] octetStringFixed(byte[] bArr, int i) {
        if (bArr.length == i) {
            return bArr;
        }
        throw new IllegalArgumentException("octet string out of range");
    }

    static byte[] octetStringFixed(byte[] bArr) {
        if (bArr.length < 1 || bArr.length > 32) {
            throw new IllegalArgumentException("octet string out of range");
        }
        return Arrays.clone(bArr);
    }
}
