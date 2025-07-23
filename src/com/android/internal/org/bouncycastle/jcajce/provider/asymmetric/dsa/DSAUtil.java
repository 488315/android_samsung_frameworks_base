package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.DSAParameters;
import com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Fingerprint;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;

/* loaded from: classes5.dex */
public class DSAUtil {
    public static final ASN1ObjectIdentifier[] dsaOids = {X9ObjectIdentifiers.id_dsa, OIWObjectIdentifiers.dsaWithSHA1, X9ObjectIdentifiers.id_dsa_with_sha1};

    public static boolean isDsaOid(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        int i = 0;
        while (true) {
            ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = dsaOids;
            if (i == aSN1ObjectIdentifierArr.length) {
                return false;
            }
            if (aSN1ObjectIdentifier.equals((ASN1Primitive) aSN1ObjectIdentifierArr[i])) {
                return true;
            }
            i++;
        }
    }

    static DSAParameters toDSAParameters(DSAParams dSAParams) {
        if (dSAParams != null) {
            return new DSAParameters(dSAParams.getP(), dSAParams.getQ(), dSAParams.getG());
        }
        return null;
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey publicKey) throws InvalidKeyException {
        if (publicKey instanceof BCDSAPublicKey) {
            return ((BCDSAPublicKey) publicKey).engineGetKeyParameters();
        }
        if (publicKey instanceof DSAPublicKey) {
            return new BCDSAPublicKey((DSAPublicKey) publicKey).engineGetKeyParameters();
        }
        try {
            return new BCDSAPublicKey(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded())).engineGetKeyParameters();
        } catch (Exception unused) {
            throw new InvalidKeyException("can't identify DSA public key: " + publicKey.getClass().getName());
        }
    }

    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof DSAPrivateKey) {
            DSAPrivateKey dSAPrivateKey = (DSAPrivateKey) privateKey;
            return new DSAPrivateKeyParameters(dSAPrivateKey.getX(), new DSAParameters(dSAPrivateKey.getParams().getP(), dSAPrivateKey.getParams().getQ(), dSAPrivateKey.getParams().getG()));
        }
        throw new InvalidKeyException("can't identify DSA private key.");
    }

    static String generateKeyFingerprint(BigInteger bigInteger, DSAParams dSAParams) {
        return new Fingerprint(Arrays.concatenate(bigInteger.toByteArray(), dSAParams.getP().toByteArray(), dSAParams.getQ().toByteArray(), dSAParams.getG().toByteArray())).toString();
    }
}
