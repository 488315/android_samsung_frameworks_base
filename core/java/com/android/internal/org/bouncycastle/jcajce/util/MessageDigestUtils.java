package com.android.internal.org.bouncycastle.jcajce.util;

import android.security.keystore.KeyProperties;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

import java.util.HashMap;
import java.util.Map;

public class MessageDigestUtils {
    private static Map<ASN1ObjectIdentifier, String> digestOidMap = new HashMap();

    static {
        digestOidMap.put(PKCSObjectIdentifiers.md5, KeyProperties.DIGEST_MD5);
        digestOidMap.put(OIWObjectIdentifiers.idSHA1, "SHA-1");
        digestOidMap.put(NISTObjectIdentifiers.id_sha224, KeyProperties.DIGEST_SHA224);
        digestOidMap.put(NISTObjectIdentifiers.id_sha256, "SHA-256");
        digestOidMap.put(NISTObjectIdentifiers.id_sha384, KeyProperties.DIGEST_SHA384);
        digestOidMap.put(NISTObjectIdentifiers.id_sha512, KeyProperties.DIGEST_SHA512);
    }

    public static String getDigestName(ASN1ObjectIdentifier digestAlgOID) {
        String name = digestOidMap.get(digestAlgOID);
        if (name != null) {
            return name;
        }
        return digestAlgOID.getId();
    }
}
