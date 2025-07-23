package com.android.internal.org.bouncycastle.operator.jcajce;

import android.security.keystore.KeyProperties;
import com.android.internal.org.bouncycastle.asn1.ASN1Integer;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.kisa.KISAObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.ntt.NTTObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.cert.X509CertificateHolder;
import com.android.internal.org.bouncycastle.cms.CMSException;
import com.android.internal.org.bouncycastle.jcajce.util.AlgorithmParametersUtils;
import com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.internal.org.bouncycastle.jcajce.util.MessageDigestUtils;
import com.android.internal.org.bouncycastle.operator.DefaultSignatureNameFinder;
import com.android.internal.org.bouncycastle.operator.OperatorCreationException;
import com.android.internal.org.bouncycastle.util.Integers;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PSSParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;

/* loaded from: classes5.dex */
class OperatorHelper {
    private static final Map asymmetricWrapperAlgNames;
    private static final Map oids;
    private static DefaultSignatureNameFinder sigFinder;
    private static final Map symmetricKeyAlgNames;
    private static final Map symmetricWrapperAlgNames;
    private static final Map symmetricWrapperKeySizes;
    private JcaJceHelper helper;

    static {
        HashMap hashMap = new HashMap();
        oids = hashMap;
        HashMap hashMap2 = new HashMap();
        asymmetricWrapperAlgNames = hashMap2;
        HashMap hashMap3 = new HashMap();
        symmetricWrapperAlgNames = hashMap3;
        HashMap hashMap4 = new HashMap();
        symmetricKeyAlgNames = hashMap4;
        HashMap hashMap5 = new HashMap();
        symmetricWrapperKeySizes = hashMap5;
        sigFinder = new DefaultSignatureNameFinder();
        hashMap.put(OIWObjectIdentifiers.idSHA1, "SHA1");
        hashMap.put(NISTObjectIdentifiers.id_sha224, "SHA224");
        hashMap.put(NISTObjectIdentifiers.id_sha256, "SHA256");
        hashMap.put(NISTObjectIdentifiers.id_sha384, "SHA384");
        hashMap.put(NISTObjectIdentifiers.id_sha512, "SHA512");
        hashMap2.put(PKCSObjectIdentifiers.rsaEncryption, "RSA/ECB/PKCS1Padding");
        hashMap2.put(OIWObjectIdentifiers.elGamalAlgorithm, "Elgamal/ECB/PKCS1Padding");
        hashMap2.put(PKCSObjectIdentifiers.id_RSAES_OAEP, "RSA/ECB/OAEPPadding");
        hashMap3.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap, "DESEDEWrap");
        hashMap3.put(PKCSObjectIdentifiers.id_alg_CMSRC2wrap, "RC2Wrap");
        hashMap3.put(NISTObjectIdentifiers.id_aes128_wrap, "AESWrap");
        hashMap3.put(NISTObjectIdentifiers.id_aes192_wrap, "AESWrap");
        hashMap3.put(NISTObjectIdentifiers.id_aes256_wrap, "AESWrap");
        hashMap3.put(NTTObjectIdentifiers.id_camellia128_wrap, "CamelliaWrap");
        hashMap3.put(NTTObjectIdentifiers.id_camellia192_wrap, "CamelliaWrap");
        hashMap3.put(NTTObjectIdentifiers.id_camellia256_wrap, "CamelliaWrap");
        hashMap3.put(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, "SEEDWrap");
        hashMap3.put(PKCSObjectIdentifiers.des_EDE3_CBC, KeyProperties.KEY_ALGORITHM_3DES);
        hashMap5.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap, Integers.valueOf(192));
        hashMap5.put(NISTObjectIdentifiers.id_aes128_wrap, Integers.valueOf(128));
        hashMap5.put(NISTObjectIdentifiers.id_aes192_wrap, Integers.valueOf(192));
        hashMap5.put(NISTObjectIdentifiers.id_aes256_wrap, Integers.valueOf(256));
        hashMap5.put(NTTObjectIdentifiers.id_camellia128_wrap, Integers.valueOf(128));
        hashMap5.put(NTTObjectIdentifiers.id_camellia192_wrap, Integers.valueOf(192));
        hashMap5.put(NTTObjectIdentifiers.id_camellia256_wrap, Integers.valueOf(256));
        hashMap5.put(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap, Integers.valueOf(128));
        hashMap5.put(PKCSObjectIdentifiers.des_EDE3_CBC, Integers.valueOf(192));
        hashMap4.put(NISTObjectIdentifiers.aes, "AES");
        hashMap4.put(NISTObjectIdentifiers.id_aes128_CBC, "AES");
        hashMap4.put(NISTObjectIdentifiers.id_aes192_CBC, "AES");
        hashMap4.put(NISTObjectIdentifiers.id_aes256_CBC, "AES");
        hashMap4.put(PKCSObjectIdentifiers.des_EDE3_CBC, KeyProperties.KEY_ALGORITHM_3DES);
        hashMap4.put(PKCSObjectIdentifiers.RC2_CBC, "RC2");
    }

    OperatorHelper(JcaJceHelper jcaJceHelper) {
        this.helper = jcaJceHelper;
    }

    String getWrappingAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String) symmetricWrapperAlgNames.get(aSN1ObjectIdentifier);
    }

    int getKeySizeInBits(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return ((Integer) symmetricWrapperKeySizes.get(aSN1ObjectIdentifier)).intValue();
    }

    KeyPairGenerator createKeyPairGenerator(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws CMSException {
        try {
            return this.helper.createKeyPairGenerator(aSN1ObjectIdentifier.getId());
        } catch (GeneralSecurityException e) {
            throw new CMSException("cannot create key agreement: " + e.getMessage(), e);
        }
    }

    Cipher createCipher(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws OperatorCreationException {
        try {
            return this.helper.createCipher(aSN1ObjectIdentifier.getId());
        } catch (GeneralSecurityException e) {
            throw new OperatorCreationException("cannot create cipher: " + e.getMessage(), e);
        }
    }

    KeyAgreement createKeyAgreement(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws OperatorCreationException {
        try {
            return this.helper.createKeyAgreement(aSN1ObjectIdentifier.getId());
        } catch (GeneralSecurityException e) {
            throw new OperatorCreationException("cannot create key agreement: " + e.getMessage(), e);
        }
    }

    Cipher createAsymmetricWrapper(ASN1ObjectIdentifier aSN1ObjectIdentifier, Map map) throws OperatorCreationException {
        try {
            String str = !map.isEmpty() ? (String) map.get(aSN1ObjectIdentifier) : null;
            if (str == null) {
                str = (String) asymmetricWrapperAlgNames.get(aSN1ObjectIdentifier);
            }
            if (str != null) {
                try {
                    return this.helper.createCipher(str);
                } catch (NoSuchAlgorithmException unused) {
                    if (str.equals("RSA/ECB/PKCS1Padding")) {
                        try {
                            return this.helper.createCipher("RSA/NONE/PKCS1Padding");
                        } catch (NoSuchAlgorithmException unused2) {
                        }
                    }
                }
            }
            return this.helper.createCipher(aSN1ObjectIdentifier.getId());
        } catch (GeneralSecurityException e) {
            throw new OperatorCreationException("cannot create cipher: " + e.getMessage(), e);
        }
    }

    Cipher createSymmetricWrapper(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws OperatorCreationException {
        try {
            String str = (String) symmetricWrapperAlgNames.get(aSN1ObjectIdentifier);
            if (str != null) {
                try {
                    return this.helper.createCipher(str);
                } catch (NoSuchAlgorithmException unused) {
                }
            }
            return this.helper.createCipher(aSN1ObjectIdentifier.getId());
        } catch (GeneralSecurityException e) {
            throw new OperatorCreationException("cannot create cipher: " + e.getMessage(), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.security.AlgorithmParameters createAlgorithmParameters(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier r5) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        /*
            r4 = this;
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r5.getAlgorithm()
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption
            boolean r0 = r0.equals(r1)
            r1 = 0
            if (r0 == 0) goto Le
            return r1
        Le:
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r5.getAlgorithm()
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSAES_OAEP
            boolean r0 = r0.equals(r2)
            java.lang.String r2 = "cannot create algorithm parameters: "
            if (r0 == 0) goto L3c
            com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper r0 = r4.helper     // Catch: java.security.NoSuchProviderException -> L25 java.security.NoSuchAlgorithmException -> L3c
            java.lang.String r3 = "OAEP"
            java.security.AlgorithmParameters r0 = r0.createAlgorithmParameters(r3)     // Catch: java.security.NoSuchProviderException -> L25 java.security.NoSuchAlgorithmException -> L3c
            goto L3d
        L25:
            r4 = move-exception
            com.android.internal.org.bouncycastle.operator.OperatorCreationException r5 = new com.android.internal.org.bouncycastle.operator.OperatorCreationException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r2)
            java.lang.String r1 = r4.getMessage()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0, r4)
            throw r5
        L3c:
            r0 = r1
        L3d:
            if (r0 != 0) goto L66
            com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper r4 = r4.helper     // Catch: java.security.NoSuchProviderException -> L4e java.security.NoSuchAlgorithmException -> L65
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier r0 = r5.getAlgorithm()     // Catch: java.security.NoSuchProviderException -> L4e java.security.NoSuchAlgorithmException -> L65
            java.lang.String r0 = r0.getId()     // Catch: java.security.NoSuchProviderException -> L4e java.security.NoSuchAlgorithmException -> L65
            java.security.AlgorithmParameters r0 = r4.createAlgorithmParameters(r0)     // Catch: java.security.NoSuchProviderException -> L4e java.security.NoSuchAlgorithmException -> L65
            goto L66
        L4e:
            r4 = move-exception
            com.android.internal.org.bouncycastle.operator.OperatorCreationException r5 = new com.android.internal.org.bouncycastle.operator.OperatorCreationException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r2)
            java.lang.String r1 = r4.getMessage()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0, r4)
            throw r5
        L65:
            return r1
        L66:
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable r4 = r5.getParameters()     // Catch: java.io.IOException -> L76
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive r4 = r4.toASN1Primitive()     // Catch: java.io.IOException -> L76
            byte[] r4 = r4.getEncoded()     // Catch: java.io.IOException -> L76
            r0.init(r4)     // Catch: java.io.IOException -> L76
            return r0
        L76:
            r4 = move-exception
            com.android.internal.org.bouncycastle.operator.OperatorCreationException r5 = new com.android.internal.org.bouncycastle.operator.OperatorCreationException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "cannot initialise algorithm parameters: "
            r0.<init>(r1)
            java.lang.String r1 = r4.getMessage()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0, r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.operator.jcajce.OperatorHelper.createAlgorithmParameters(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier):java.security.AlgorithmParameters");
    }

    MessageDigest createDigest(AlgorithmIdentifier algorithmIdentifier) throws GeneralSecurityException {
        try {
            if (algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) NISTObjectIdentifiers.id_shake256_len)) {
                return this.helper.createMessageDigest("SHAKE256-" + ASN1Integer.getInstance(algorithmIdentifier.getParameters()).getValue());
            }
            if (algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) NISTObjectIdentifiers.id_shake128_len)) {
                return this.helper.createMessageDigest("SHAKE128-" + ASN1Integer.getInstance(algorithmIdentifier.getParameters()).getValue());
            }
            return this.helper.createMessageDigest(MessageDigestUtils.getDigestName(algorithmIdentifier.getAlgorithm()));
        } catch (NoSuchAlgorithmException e) {
            Map map = oids;
            if (map.get(algorithmIdentifier.getAlgorithm()) != null) {
                return this.helper.createMessageDigest((String) map.get(algorithmIdentifier.getAlgorithm()));
            }
            throw e;
        }
    }

    Signature createSignature(AlgorithmIdentifier algorithmIdentifier) throws GeneralSecurityException {
        Signature createSignature;
        String signatureName = getSignatureName(algorithmIdentifier);
        try {
            createSignature = this.helper.createSignature(signatureName);
        } catch (NoSuchAlgorithmException e) {
            if (signatureName.endsWith("WITHRSAANDMGF1")) {
                createSignature = this.helper.createSignature(signatureName.substring(0, signatureName.indexOf(87)) + "WITHRSASSA-PSS");
            } else {
                throw e;
            }
        }
        if (algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(algorithmIdentifier.getParameters());
            if (notDefaultPSSParams(aSN1Sequence)) {
                try {
                    AlgorithmParameters createAlgorithmParameters = this.helper.createAlgorithmParameters(KeyProperties.SIGNATURE_PADDING_RSA_PSS);
                    createAlgorithmParameters.init(aSN1Sequence.getEncoded());
                    createSignature.setParameter(createAlgorithmParameters.getParameterSpec(PSSParameterSpec.class));
                } catch (IOException e2) {
                    throw new GeneralSecurityException("unable to process PSS parameters: " + e2.getMessage());
                }
            }
        }
        return createSignature;
    }

    Signature createRawSignature(AlgorithmIdentifier algorithmIdentifier) {
        try {
            String signatureName = getSignatureName(algorithmIdentifier);
            String str = KeyProperties.DIGEST_NONE + signatureName.substring(signatureName.indexOf("WITH"));
            Signature createSignature = this.helper.createSignature(str);
            if (algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_RSASSA_PSS)) {
                AlgorithmParameters createAlgorithmParameters = this.helper.createAlgorithmParameters(str);
                AlgorithmParametersUtils.loadParameters(createAlgorithmParameters, algorithmIdentifier.getParameters());
                createSignature.setParameter((PSSParameterSpec) createAlgorithmParameters.getParameterSpec(PSSParameterSpec.class));
            }
            return createSignature;
        } catch (Exception unused) {
            return null;
        }
    }

    private static String getSignatureName(AlgorithmIdentifier algorithmIdentifier) {
        return sigFinder.getAlgorithmName(algorithmIdentifier);
    }

    static String getDigestName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String digestName = MessageDigestUtils.getDigestName(aSN1ObjectIdentifier);
        int indexOf = digestName.indexOf(45);
        if (indexOf <= 0 || digestName.startsWith("SHA3")) {
            return digestName;
        }
        return digestName.substring(0, indexOf) + digestName.substring(indexOf + 1);
    }

    public X509Certificate convertCertificate(X509CertificateHolder x509CertificateHolder) throws CertificateException {
        try {
            return (X509Certificate) this.helper.createCertificateFactory("X.509").generateCertificate(new ByteArrayInputStream(x509CertificateHolder.getEncoded()));
        } catch (IOException e) {
            throw new OpCertificateException("cannot get encoded form of certificate: " + e.getMessage(), e);
        } catch (NoSuchProviderException e2) {
            throw new OpCertificateException("cannot find factory provider: " + e2.getMessage(), e2);
        }
    }

    public PublicKey convertPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) throws OperatorCreationException {
        try {
            return this.helper.createKeyFactory(subjectPublicKeyInfo.getAlgorithm().getAlgorithm().getId()).generatePublic(new X509EncodedKeySpec(subjectPublicKeyInfo.getEncoded()));
        } catch (IOException e) {
            throw new OperatorCreationException("cannot get encoded form of key: " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e2) {
            throw new OperatorCreationException("cannot create key factory: " + e2.getMessage(), e2);
        } catch (NoSuchProviderException e3) {
            throw new OperatorCreationException("cannot find factory provider: " + e3.getMessage(), e3);
        } catch (InvalidKeySpecException e4) {
            throw new OperatorCreationException("cannot create key factory: " + e4.getMessage(), e4);
        }
    }

    private static class OpCertificateException extends CertificateException {
        private Throwable cause;

        public OpCertificateException(String str, Throwable th) {
            super(str);
            this.cause = th;
        }

        @Override // java.lang.Throwable
        public Throwable getCause() {
            return this.cause;
        }
    }

    String getKeyAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) symmetricKeyAlgNames.get(aSN1ObjectIdentifier);
        return str != null ? str : aSN1ObjectIdentifier.getId();
    }

    private boolean notDefaultPSSParams(ASN1Sequence aSN1Sequence) throws GeneralSecurityException {
        if (aSN1Sequence != null && aSN1Sequence.size() != 0) {
            RSASSAPSSparams rSASSAPSSparams = RSASSAPSSparams.getInstance(aSN1Sequence);
            if (!rSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_mgf1) || !rSASSAPSSparams.getHashAlgorithm().equals(AlgorithmIdentifier.getInstance(rSASSAPSSparams.getMaskGenAlgorithm().getParameters()))) {
                return true;
            }
            if (rSASSAPSSparams.getSaltLength().intValue() != createDigest(rSASSAPSSparams.getHashAlgorithm()).getDigestLength()) {
                return true;
            }
        }
        return false;
    }
}
