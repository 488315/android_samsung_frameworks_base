package com.android.internal.org.bouncycastle.jce;

import java.util.Enumeration;

/* loaded from: classes5.dex */
public class ECNamedCurveTable {
    /* JADX WARN: Removed duplicated region for block: B:13:0x0028 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0010  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec getParameterSpec(java.lang.String r10) {
        /*
            r0 = 0
            boolean r1 = possibleOID(r10)     // Catch: java.lang.IllegalArgumentException -> Ld
            if (r1 == 0) goto Ld
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier     // Catch: java.lang.IllegalArgumentException -> Ld
            r1.<init>(r10)     // Catch: java.lang.IllegalArgumentException -> Ld
            goto Le
        Ld:
            r1 = r0
        Le:
            if (r1 == 0) goto L15
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters r2 = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByOID(r1)
            goto L19
        L15:
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters r2 = com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves.getByName(r10)
        L19:
            if (r2 != 0) goto L26
            if (r1 == 0) goto L22
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters r2 = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(r1)
            goto L26
        L22:
            com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters r2 = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByName(r10)
        L26:
            if (r2 != 0) goto L29
            return r0
        L29:
            com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec r3 = new com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
            com.android.internal.org.bouncycastle.math.ec.ECCurve r5 = r2.getCurve()
            com.android.internal.org.bouncycastle.math.ec.ECPoint r6 = r2.getG()
            java.math.BigInteger r7 = r2.getN()
            java.math.BigInteger r8 = r2.getH()
            byte[] r9 = r2.getSeed()
            r4 = r10
            r3.<init>(r4, r5, r6, r7, r8, r9)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jce.ECNamedCurveTable.getParameterSpec(java.lang.String):com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec");
    }

    public static Enumeration getNames() {
        return com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getNames();
    }

    private static boolean possibleOID(String str) {
        char charAt;
        return str.length() >= 3 && str.charAt(1) == '.' && (charAt = str.charAt(0)) >= '0' && charAt <= '2';
    }
}
