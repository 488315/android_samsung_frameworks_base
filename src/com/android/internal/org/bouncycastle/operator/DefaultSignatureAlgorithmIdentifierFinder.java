package com.android.internal.org.bouncycastle.operator;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.DERNull;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.internal.org.bouncycastle.util.Strings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class DefaultSignatureAlgorithmIdentifierFinder implements SignatureAlgorithmIdentifierFinder {
    private static Map algorithms = new HashMap();
    private static Set noParams = new HashSet();
    private static Map params = new HashMap();
    private static Set pkcs15RsaEncryption = new HashSet();
    private static Map digestOids = new HashMap();
    private static final ASN1ObjectIdentifier ENCRYPTION_RSA = PKCSObjectIdentifiers.rsaEncryption;
    private static final ASN1ObjectIdentifier ENCRYPTION_DSA = X9ObjectIdentifiers.id_dsa_with_sha1;
    private static final ASN1ObjectIdentifier ENCRYPTION_ECDSA = X9ObjectIdentifiers.ecdsa_with_SHA1;
    private static final ASN1ObjectIdentifier ENCRYPTION_RSA_PSS = PKCSObjectIdentifiers.id_RSASSA_PSS;

    static {
        algorithms.put("MD5WITHRSAENCRYPTION", PKCSObjectIdentifiers.md5WithRSAEncryption);
        algorithms.put("MD5WITHRSA", PKCSObjectIdentifiers.md5WithRSAEncryption);
        algorithms.put("SHA1WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha1WithRSAEncryption);
        algorithms.put("SHA1WITHRSA", PKCSObjectIdentifiers.sha1WithRSAEncryption);
        algorithms.put("SHA224WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        algorithms.put("SHA224WITHRSA", PKCSObjectIdentifiers.sha224WithRSAEncryption);
        algorithms.put("SHA256WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        algorithms.put("SHA256WITHRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption);
        algorithms.put("SHA384WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        algorithms.put("SHA384WITHRSA", PKCSObjectIdentifiers.sha384WithRSAEncryption);
        algorithms.put("SHA512WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        algorithms.put("SHA512WITHRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption);
        algorithms.put("SHA512(224)WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512_224WithRSAEncryption);
        algorithms.put("SHA512(224)WITHRSA", PKCSObjectIdentifiers.sha512_224WithRSAEncryption);
        algorithms.put("SHA512(256)WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512_256WithRSAEncryption);
        algorithms.put("SHA512(256)WITHRSA", PKCSObjectIdentifiers.sha512_256WithRSAEncryption);
        algorithms.put("SHA1WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA224WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA256WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA384WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA512WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
        algorithms.put("SHA1WITHDSA", X9ObjectIdentifiers.id_dsa_with_sha1);
        algorithms.put("DSAWITHSHA1", X9ObjectIdentifiers.id_dsa_with_sha1);
        algorithms.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
        algorithms.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
        algorithms.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
        algorithms.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
        algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
        algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
        algorithms.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
        algorithms.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
        algorithms.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
        algorithms.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
        noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
        noParams.add(X9ObjectIdentifiers.id_dsa_with_sha1);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha224);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha256);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha384);
        noParams.add(NISTObjectIdentifiers.dsa_with_sha512);
        pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha1WithRSAEncryption);
        pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha224WithRSAEncryption);
        pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha256WithRSAEncryption);
        pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha384WithRSAEncryption);
        pkcs15RsaEncryption.add(PKCSObjectIdentifiers.sha512WithRSAEncryption);
        params.put("SHA1WITHRSAANDMGF1", createPSSParams(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE), 20));
        params.put("SHA224WITHRSAANDMGF1", createPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE), 28));
        params.put("SHA256WITHRSAANDMGF1", createPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE), 32));
        params.put("SHA384WITHRSAANDMGF1", createPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE), 48));
        params.put("SHA512WITHRSAANDMGF1", createPSSParams(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE), 64));
        digestOids.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, NISTObjectIdentifiers.id_sha224);
        digestOids.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, NISTObjectIdentifiers.id_sha256);
        digestOids.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, NISTObjectIdentifiers.id_sha384);
        digestOids.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, NISTObjectIdentifiers.id_sha512);
        digestOids.put(PKCSObjectIdentifiers.md5WithRSAEncryption, PKCSObjectIdentifiers.md5);
        digestOids.put(PKCSObjectIdentifiers.sha1WithRSAEncryption, OIWObjectIdentifiers.idSHA1);
        digestOids.put(NISTObjectIdentifiers.dsa_with_sha224, NISTObjectIdentifiers.id_sha224);
        digestOids.put(NISTObjectIdentifiers.dsa_with_sha256, NISTObjectIdentifiers.id_sha256);
        digestOids.put(NISTObjectIdentifiers.dsa_with_sha384, NISTObjectIdentifiers.id_sha384);
        digestOids.put(NISTObjectIdentifiers.dsa_with_sha512, NISTObjectIdentifiers.id_sha512);
        digestOids.put(PKCSObjectIdentifiers.md5WithRSAEncryption, PKCSObjectIdentifiers.md5);
        digestOids.put(PKCSObjectIdentifiers.sha1WithRSAEncryption, OIWObjectIdentifiers.idSHA1);
    }

    private static RSASSAPSSparams createPSSParams(AlgorithmIdentifier algorithmIdentifier, int i) {
        return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, algorithmIdentifier), new ASN1Integer(i), new ASN1Integer(1L));
    }

    @Override // com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder
    public AlgorithmIdentifier find(String str) {
        String upperCase = Strings.toUpperCase(str);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) algorithms.get(upperCase);
        if (aSN1ObjectIdentifier == null) {
            throw new IllegalArgumentException("Unknown signature type requested: " + str);
        }
        if (noParams.contains(aSN1ObjectIdentifier)) {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier);
        }
        if (params.containsKey(upperCase)) {
            return new AlgorithmIdentifier(aSN1ObjectIdentifier, (ASN1Encodable) params.get(upperCase));
        }
        return new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }
}
