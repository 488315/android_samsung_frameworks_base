package com.android.internal.org.bouncycastle.its.asn1;

import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
class Utils {
    Utils() {}

    static byte[] octetStringFixed(byte[] octets, int n) {
        if (octets.length != n) {
            throw new IllegalArgumentException("octet string out of range");
        }
        return octets;
    }

    static byte[] octetStringFixed(byte[] octets) {
        if (octets.length < 1 || octets.length > 32) {
            throw new IllegalArgumentException("octet string out of range");
        }
        return Arrays.clone(octets);
    }
}
