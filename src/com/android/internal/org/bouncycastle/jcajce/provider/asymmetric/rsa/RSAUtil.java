package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.internal.org.bouncycastle.util.Fingerprint;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/* loaded from: classes5.dex */
public class RSAUtil {
    public static final ASN1ObjectIdentifier[] rsaOids = {PKCSObjectIdentifiers.rsaEncryption, X509ObjectIdentifiers.id_ea_rsa, PKCSObjectIdentifiers.id_RSAES_OAEP, PKCSObjectIdentifiers.id_RSASSA_PSS};

    public static boolean isRsaOid(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        int i = 0;
        while (true) {
            ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = rsaOids;
            if (i == aSN1ObjectIdentifierArr.length) {
                return false;
            }
            if (aSN1ObjectIdentifier.equals((ASN1Primitive) aSN1ObjectIdentifierArr[i])) {
                return true;
            }
            i++;
        }
    }

    static RSAKeyParameters generatePublicKeyParameter(RSAPublicKey rSAPublicKey) {
        if (rSAPublicKey instanceof BCRSAPublicKey) {
            return ((BCRSAPublicKey) rSAPublicKey).engineGetKeyParameters();
        }
        return new RSAKeyParameters(false, rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
    }

    static RSAKeyParameters generatePrivateKeyParameter(RSAPrivateKey rSAPrivateKey) {
        if (rSAPrivateKey instanceof BCRSAPrivateKey) {
            return ((BCRSAPrivateKey) rSAPrivateKey).engineGetKeyParameters();
        }
        if (rSAPrivateKey instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) rSAPrivateKey;
            return new RSAPrivateCrtKeyParameters(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent(), rSAPrivateCrtKey.getPrivateExponent(), rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ(), rSAPrivateCrtKey.getPrimeExponentP(), rSAPrivateCrtKey.getPrimeExponentQ(), rSAPrivateCrtKey.getCrtCoefficient());
        }
        return new RSAKeyParameters(true, rSAPrivateKey.getModulus(), rSAPrivateKey.getPrivateExponent());
    }

    static String generateKeyFingerprint(BigInteger bigInteger) {
        return new Fingerprint(bigInteger.toByteArray()).toString();
    }

    static String generateExponentFingerprint(BigInteger bigInteger) {
        return new Fingerprint(bigInteger.toByteArray(), 32).toString();
    }
}
