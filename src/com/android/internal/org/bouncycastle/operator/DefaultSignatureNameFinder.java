package com.android.internal.org.bouncycastle.operator;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.isara.IsaraObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import com.android.internal.org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class DefaultSignatureNameFinder implements AlgorithmNameFinder {
    private static final Map digests;
    private static final Map oids;

    static {
        HashMap hashMap = new HashMap();
        oids = hashMap;
        HashMap hashMap2 = new HashMap();
        digests = hashMap2;
        hashMap.put(PKCSObjectIdentifiers.id_RSASSA_PSS, "RSASSA-PSS");
        hashMap.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
        hashMap.put(IsaraObjectIdentifiers.id_alg_xmss, "XMSS");
        hashMap.put(IsaraObjectIdentifiers.id_alg_xmssmt, "XMSSMT");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128, "RIPEMD128WITHRSA");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160, "RIPEMD160WITHRSA");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256, "RIPEMD256WITHRSA");
        hashMap.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        hashMap.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"), "MD2WITHRSA");
        hashMap.put(new ASN1ObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
        hashMap.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
        hashMap.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
        hashMap.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
        hashMap.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
        hashMap2.put(OIWObjectIdentifiers.idSHA1, "SHA1");
        hashMap2.put(NISTObjectIdentifiers.id_sha224, "SHA224");
        hashMap2.put(NISTObjectIdentifiers.id_sha256, "SHA256");
        hashMap2.put(NISTObjectIdentifiers.id_sha384, "SHA384");
        hashMap2.put(NISTObjectIdentifiers.id_sha512, "SHA512");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_224, "SHA3-224");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_256, "SHA3-256");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_384, "SHA3-384");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_512, "SHA3-512");
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd128, "RIPEMD128");
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd160, "RIPEMD160");
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd256, "RIPEMD256");
    }

    @Override // com.android.internal.org.bouncycastle.operator.AlgorithmNameFinder
    public boolean hasAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return oids.containsKey(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.operator.AlgorithmNameFinder
    public String getAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) oids.get(aSN1ObjectIdentifier);
        return str != null ? str : aSN1ObjectIdentifier.getId();
    }

    @Override // com.android.internal.org.bouncycastle.operator.AlgorithmNameFinder
    public String getAlgorithmName(AlgorithmIdentifier algorithmIdentifier) {
        ASN1Encodable parameters = algorithmIdentifier.getParameters();
        if (parameters != null && !DERNull.INSTANCE.equals(parameters) && algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            RSASSAPSSparams rSASSAPSSparams = RSASSAPSSparams.getInstance(parameters);
            AlgorithmIdentifier maskGenAlgorithm = rSASSAPSSparams.getMaskGenAlgorithm();
            if (maskGenAlgorithm.getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_mgf1)) {
                AlgorithmIdentifier hashAlgorithm = rSASSAPSSparams.getHashAlgorithm();
                ASN1ObjectIdentifier algorithm = AlgorithmIdentifier.getInstance(maskGenAlgorithm.getParameters()).getAlgorithm();
                if (algorithm.equals((ASN1Primitive) hashAlgorithm.getAlgorithm())) {
                    return getDigestName(hashAlgorithm.getAlgorithm()) + "WITHRSAANDMGF1";
                }
                return getDigestName(hashAlgorithm.getAlgorithm()) + "WITHRSAANDMGF1USING" + getDigestName(algorithm);
            }
            return getDigestName(rSASSAPSSparams.getHashAlgorithm().getAlgorithm()) + "WITHRSAAND" + maskGenAlgorithm.getAlgorithm().getId();
        }
        Map map = oids;
        if (map.containsKey(algorithmIdentifier.getAlgorithm())) {
            return (String) map.get(algorithmIdentifier.getAlgorithm());
        }
        return algorithmIdentifier.getAlgorithm().getId();
    }

    private static String getDigestName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) digests.get(aSN1ObjectIdentifier);
        return str != null ? str : aSN1ObjectIdentifier.getId();
    }
}
