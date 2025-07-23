package com.android.internal.org.bouncycastle.jcajce.util;

import android.security.keystore.KeyProperties;
import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import java.io.IOException;
import java.security.AlgorithmParameters;

/* loaded from: classes5.dex */
public class JcaJceUtils {
    private JcaJceUtils() {
    }

    public static ASN1Encodable extractParameters(AlgorithmParameters algorithmParameters) throws IOException {
        try {
            return ASN1Primitive.fromByteArray(algorithmParameters.getEncoded("ASN.1"));
        } catch (Exception unused) {
            return ASN1Primitive.fromByteArray(algorithmParameters.getEncoded());
        }
    }

    public static void loadParameters(AlgorithmParameters algorithmParameters, ASN1Encodable aSN1Encodable) throws IOException {
        try {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded(), "ASN.1");
        } catch (Exception unused) {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded());
        }
    }

    public static String getDigestAlgName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (PKCSObjectIdentifiers.md5.equals((ASN1Primitive) aSN1ObjectIdentifier)) {
            return KeyProperties.DIGEST_MD5;
        }
        if (OIWObjectIdentifiers.idSHA1.equals((ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA1";
        }
        if (NISTObjectIdentifiers.id_sha224.equals((ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA224";
        }
        if (NISTObjectIdentifiers.id_sha256.equals((ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA256";
        }
        if (NISTObjectIdentifiers.id_sha384.equals((ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA384";
        }
        if (NISTObjectIdentifiers.id_sha512.equals((ASN1Primitive) aSN1ObjectIdentifier)) {
            return "SHA512";
        }
        return aSN1ObjectIdentifier.getId();
    }
}
