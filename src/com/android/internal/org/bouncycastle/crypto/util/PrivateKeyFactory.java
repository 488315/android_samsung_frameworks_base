package com.android.internal.org.bouncycastle.crypto.util;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1OctetString;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.internal.org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import com.android.internal.org.bouncycastle.asn1.sec.ECPrivateKey;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.DSAParameter;
import com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.internal.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.internal.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.DHParameters;
import com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.DSAParameters;
import com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECNamedDomainParameters;
import com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class PrivateKeyFactory {
    public static AsymmetricKeyParameter createKey(byte[] bArr) throws IOException {
        if (bArr == null) {
            throw new IllegalArgumentException("privateKeyInfoData array null");
        }
        if (bArr.length == 0) {
            throw new IllegalArgumentException("privateKeyInfoData array empty");
        }
        return createKey(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(bArr)));
    }

    public static AsymmetricKeyParameter createKey(InputStream inputStream) throws IOException {
        return createKey(PrivateKeyInfo.getInstance(new ASN1InputStream(inputStream).readObject()));
    }

    public static AsymmetricKeyParameter createKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        ECDomainParameters eCDomainParameters;
        if (privateKeyInfo == null) {
            throw new IllegalArgumentException("keyInfo argument null");
        }
        AlgorithmIdentifier privateKeyAlgorithm = privateKeyInfo.getPrivateKeyAlgorithm();
        ASN1ObjectIdentifier algorithm = privateKeyAlgorithm.getAlgorithm();
        if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.rsaEncryption) || algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.id_RSASSA_PSS) || algorithm.equals((ASN1Primitive) X509ObjectIdentifiers.id_ea_rsa)) {
            RSAPrivateKey rSAPrivateKey = RSAPrivateKey.getInstance(privateKeyInfo.parsePrivateKey());
            return new RSAPrivateCrtKeyParameters(rSAPrivateKey.getModulus(), rSAPrivateKey.getPublicExponent(), rSAPrivateKey.getPrivateExponent(), rSAPrivateKey.getPrime1(), rSAPrivateKey.getPrime2(), rSAPrivateKey.getExponent1(), rSAPrivateKey.getExponent2(), rSAPrivateKey.getCoefficient());
        }
        DSAParameters dSAParameters = null;
        if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.dhKeyAgreement)) {
            DHParameter dHParameter = DHParameter.getInstance(privateKeyAlgorithm.getParameters());
            ASN1Integer aSN1Integer = (ASN1Integer) privateKeyInfo.parsePrivateKey();
            BigInteger l = dHParameter.getL();
            return new DHPrivateKeyParameters(aSN1Integer.getValue(), new DHParameters(dHParameter.getP(), dHParameter.getG(), null, l == null ? 0 : l.intValue()));
        }
        if (algorithm.equals((ASN1Primitive) X9ObjectIdentifiers.id_dsa)) {
            ASN1Integer aSN1Integer2 = (ASN1Integer) privateKeyInfo.parsePrivateKey();
            ASN1Encodable parameters = privateKeyAlgorithm.getParameters();
            if (parameters != null) {
                DSAParameter dSAParameter = DSAParameter.getInstance(parameters.toASN1Primitive());
                dSAParameters = new DSAParameters(dSAParameter.getP(), dSAParameter.getQ(), dSAParameter.getG());
            }
            return new DSAPrivateKeyParameters(aSN1Integer2.getValue(), dSAParameters);
        }
        if (algorithm.equals((ASN1Primitive) X9ObjectIdentifiers.id_ecPublicKey)) {
            X962Parameters x962Parameters = X962Parameters.getInstance(privateKeyAlgorithm.getParameters());
            if (x962Parameters.isNamedCurve()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) x962Parameters.getParameters();
                X9ECParameters byOID = CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
                if (byOID == null) {
                    byOID = ECNamedCurveTable.getByOID(aSN1ObjectIdentifier);
                }
                eCDomainParameters = new ECNamedDomainParameters(aSN1ObjectIdentifier, byOID);
            } else {
                X9ECParameters x9ECParameters = X9ECParameters.getInstance(x962Parameters.getParameters());
                eCDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH(), x9ECParameters.getSeed());
            }
            return new ECPrivateKeyParameters(ECPrivateKey.getInstance(privateKeyInfo.parsePrivateKey()).getKey(), eCDomainParameters);
        }
        throw new RuntimeException("algorithm identifier in private key not recognised");
    }

    private static byte[] getRawKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        return ASN1OctetString.getInstance(privateKeyInfo.parsePrivateKey()).getOctets();
    }
}
