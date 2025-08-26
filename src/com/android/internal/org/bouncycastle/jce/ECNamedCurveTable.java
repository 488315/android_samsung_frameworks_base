package com.android.internal.org.bouncycastle.jce;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.internal.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import java.util.Enumeration;

/* loaded from: classes5.dex */
public class ECNamedCurveTable {
    public static ECNamedCurveParameterSpec getParameterSpec(String str) {
        X9ECParameters byName;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = possibleOID(str) ? new ASN1ObjectIdentifier(str) : null;
        if (aSN1ObjectIdentifier != null) {
            byName = CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
        } else {
            byName = CustomNamedCurves.getByName(str);
        }
        if (byName == null) {
            if (aSN1ObjectIdentifier != null) {
                byName = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID(aSN1ObjectIdentifier);
            } else {
                byName = com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByName(str);
            }
        }
        if (byName == null) {
            return null;
        }
        return new ECNamedCurveParameterSpec(str, byName.getCurve(), byName.getG(), byName.getN(), byName.getH(), byName.getSeed());
    }

    public static Enumeration getNames() {
        return com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable.getNames();
    }

    private static boolean possibleOID(String str) {
        char cCharAt;
        return str.length() >= 3 && str.charAt(1) == '.' && (cCharAt = str.charAt(0)) >= '0' && cCharAt <= '2';
    }
}
