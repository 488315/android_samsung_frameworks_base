package com.android.internal.org.bouncycastle.asn1.nist;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.sec.SECNamedCurves;
import com.android.internal.org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParametersHolder;
import com.android.internal.org.bouncycastle.util.Strings;
import java.util.Enumeration;
import java.util.Hashtable;

/* loaded from: classes5.dex */
public class NISTNamedCurves {
    static final Hashtable objIds = new Hashtable();
    static final Hashtable names = new Hashtable();

    static {
        defineCurve("B-571", SECObjectIdentifiers.sect571r1);
        defineCurve("B-409", SECObjectIdentifiers.sect409r1);
        defineCurve("B-283", SECObjectIdentifiers.sect283r1);
        defineCurve("B-233", SECObjectIdentifiers.sect233r1);
        defineCurve("B-163", SECObjectIdentifiers.sect163r2);
        defineCurve("K-571", SECObjectIdentifiers.sect571k1);
        defineCurve("K-409", SECObjectIdentifiers.sect409k1);
        defineCurve("K-283", SECObjectIdentifiers.sect283k1);
        defineCurve("K-233", SECObjectIdentifiers.sect233k1);
        defineCurve("K-163", SECObjectIdentifiers.sect163k1);
        defineCurve("P-521", SECObjectIdentifiers.secp521r1);
        defineCurve("P-384", SECObjectIdentifiers.secp384r1);
        defineCurve("P-256", SECObjectIdentifiers.secp256r1);
        defineCurve("P-224", SECObjectIdentifiers.secp224r1);
        defineCurve("P-192", SECObjectIdentifiers.secp192r1);
    }

    static void defineCurve(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        objIds.put(str, aSN1ObjectIdentifier);
        names.put(aSN1ObjectIdentifier, str);
    }

    public static X9ECParameters getByName(String str) {
        ASN1ObjectIdentifier oid = getOID(str);
        if (oid != null) {
            return SECNamedCurves.getByOID(oid);
        }
        return null;
    }

    public static X9ECParametersHolder getByNameLazy(String str) {
        ASN1ObjectIdentifier oid = getOID(str);
        if (oid != null) {
            return SECNamedCurves.getByOIDLazy(oid);
        }
        return null;
    }

    public static X9ECParameters getByOID(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (names.containsKey(aSN1ObjectIdentifier)) {
            return SECNamedCurves.getByOID(aSN1ObjectIdentifier);
        }
        return null;
    }

    public static X9ECParametersHolder getByOIDLazy(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (names.containsKey(aSN1ObjectIdentifier)) {
            return SECNamedCurves.getByOIDLazy(aSN1ObjectIdentifier);
        }
        return null;
    }

    public static ASN1ObjectIdentifier getOID(String str) {
        return (ASN1ObjectIdentifier) objIds.get(Strings.toUpperCase(str));
    }

    public static String getName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String) names.get(aSN1ObjectIdentifier);
    }

    public static Enumeration getNames() {
        return objIds.keys();
    }
}
