package com.android.internal.org.bouncycastle.asn1.x9;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.nist.NISTNamedCurves;
import com.android.internal.org.bouncycastle.asn1.sec.SECNamedCurves;
import java.util.Enumeration;
import java.util.Vector;

/* loaded from: classes5.dex */
public class ECNamedCurveTable {
    public static X9ECParameters getByName(String str) {
        X9ECParameters byName = X962NamedCurves.getByName(str);
        if (byName == null) {
            byName = SECNamedCurves.getByName(str);
        }
        return byName == null ? NISTNamedCurves.getByName(str) : byName;
    }

    public static X9ECParametersHolder getByNameLazy(String str) {
        X9ECParametersHolder byNameLazy = X962NamedCurves.getByNameLazy(str);
        if (byNameLazy == null) {
            byNameLazy = SECNamedCurves.getByNameLazy(str);
        }
        return byNameLazy == null ? NISTNamedCurves.getByNameLazy(str) : byNameLazy;
    }

    public static ASN1ObjectIdentifier getOID(String str) {
        ASN1ObjectIdentifier oid = X962NamedCurves.getOID(str);
        if (oid == null) {
            oid = SECNamedCurves.getOID(str);
        }
        return oid == null ? NISTNamedCurves.getOID(str) : oid;
    }

    public static String getName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String name = X962NamedCurves.getName(aSN1ObjectIdentifier);
        if (name == null) {
            name = SECNamedCurves.getName(aSN1ObjectIdentifier);
        }
        return name == null ? NISTNamedCurves.getName(aSN1ObjectIdentifier) : name;
    }

    public static X9ECParameters getByOID(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        X9ECParameters byOID = X962NamedCurves.getByOID(aSN1ObjectIdentifier);
        return byOID == null ? SECNamedCurves.getByOID(aSN1ObjectIdentifier) : byOID;
    }

    public static X9ECParametersHolder getByOIDLazy(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        X9ECParametersHolder byOIDLazy = X962NamedCurves.getByOIDLazy(aSN1ObjectIdentifier);
        return byOIDLazy == null ? SECNamedCurves.getByOIDLazy(aSN1ObjectIdentifier) : byOIDLazy;
    }

    public static Enumeration getNames() {
        Vector vector = new Vector();
        addEnumeration(vector, X962NamedCurves.getNames());
        addEnumeration(vector, SECNamedCurves.getNames());
        addEnumeration(vector, NISTNamedCurves.getNames());
        return vector.elements();
    }

    private static void addEnumeration(Vector vector, Enumeration enumeration) {
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
    }
}
