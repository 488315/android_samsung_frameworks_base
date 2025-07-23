package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi;
import com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ExtendedInvalidKeySpecException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/* loaded from: classes5.dex */
public class KeyFactorySpi extends BaseKeyFactorySpi {
    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if ((cls.isAssignableFrom(KeySpec.class) || cls.isAssignableFrom(RSAPublicKeySpec.class)) && (key instanceof RSAPublicKey)) {
            RSAPublicKey rSAPublicKey = (RSAPublicKey) key;
            return new RSAPublicKeySpec(rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
        }
        if ((cls.isAssignableFrom(KeySpec.class) || cls.isAssignableFrom(RSAPrivateCrtKeySpec.class)) && (key instanceof RSAPrivateCrtKey)) {
            RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) key;
            return new RSAPrivateCrtKeySpec(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent(), rSAPrivateCrtKey.getPrivateExponent(), rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ(), rSAPrivateCrtKey.getPrimeExponentP(), rSAPrivateCrtKey.getPrimeExponentQ(), rSAPrivateCrtKey.getCrtCoefficient());
        }
        if ((cls.isAssignableFrom(KeySpec.class) || cls.isAssignableFrom(RSAPrivateKeySpec.class)) && (key instanceof RSAPrivateKey)) {
            RSAPrivateKey rSAPrivateKey = (RSAPrivateKey) key;
            return new RSAPrivateKeySpec(rSAPrivateKey.getModulus(), rSAPrivateKey.getPrivateExponent());
        }
        return super.engineGetKeySpec(key, cls);
    }

    @Override // java.security.KeyFactorySpi
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof RSAPublicKey) {
            return new BCRSAPublicKey((RSAPublicKey) key);
        }
        if (key instanceof RSAPrivateCrtKey) {
            return new BCRSAPrivateCrtKey((RSAPrivateCrtKey) key);
        }
        if (key instanceof RSAPrivateKey) {
            return new BCRSAPrivateKey((RSAPrivateKey) key);
        }
        throw new InvalidKeyException("key type unknown");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            try {
                return generatePrivate(PrivateKeyInfo.getInstance(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
            } catch (Exception e) {
                try {
                    return new BCRSAPrivateCrtKey(com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(((PKCS8EncodedKeySpec) keySpec).getEncoded()));
                } catch (Exception unused) {
                    throw new ExtendedInvalidKeySpecException("unable to process key spec: " + e.toString(), e);
                }
            }
        }
        if (keySpec instanceof RSAPrivateCrtKeySpec) {
            return new BCRSAPrivateCrtKey((RSAPrivateCrtKeySpec) keySpec);
        }
        if (keySpec instanceof RSAPrivateKeySpec) {
            return new BCRSAPrivateKey((RSAPrivateKeySpec) keySpec);
        }
        throw new InvalidKeySpecException("unknown KeySpec type: " + keySpec.getClass().getName());
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseKeyFactorySpi, java.security.KeyFactorySpi
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof RSAPublicKeySpec) {
            return new BCRSAPublicKey((RSAPublicKeySpec) keySpec);
        }
        return super.engineGeneratePublic(keySpec);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public PrivateKey generatePrivate(PrivateKeyInfo privateKeyInfo) throws IOException {
        ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
        if (RSAUtil.isRsaOid(algorithm)) {
            com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey rSAPrivateKey = com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(privateKeyInfo.parsePrivateKey());
            if (rSAPrivateKey.getCoefficient().intValue() == 0) {
                return new BCRSAPrivateKey(privateKeyInfo.getPrivateKeyAlgorithm(), rSAPrivateKey);
            }
            return new BCRSAPrivateCrtKey(privateKeyInfo);
        }
        throw new IOException("algorithm identifier " + algorithm + " in key not recognised");
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter
    public PublicKey generatePublic(SubjectPublicKeyInfo subjectPublicKeyInfo) throws IOException {
        ASN1ObjectIdentifier algorithm = subjectPublicKeyInfo.getAlgorithm().getAlgorithm();
        if (RSAUtil.isRsaOid(algorithm)) {
            return new BCRSAPublicKey(subjectPublicKeyInfo);
        }
        throw new IOException("algorithm identifier " + algorithm + " in key not recognised");
    }
}
