package android.security.keystore2;

import android.app.AppGlobals;
import android.hardware.biometrics.BiometricManager;
import android.os.StrictMode;
import android.security.GateKeeper;
import android.security.KeyStore2;
import android.security.KeyStoreException;
import android.security.KeyStoreParameter;
import android.security.KeyStoreSecurityLevel;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyProtection;
import android.security.keystore.SecureKeyImportUnavailableException;
import android.security.keystore.WrappedKeyEntry;
import android.system.keystore2.AuthenticatorSpec;
import android.system.keystore2.Authorization;
import android.system.keystore2.KeyDescriptor;
import android.system.keystore2.KeyEntryResponse;
import android.system.keystore2.KeyMetadata;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.EdECKey;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.XECKey;
import java.security.interfaces.XECPrivateKey;
import java.security.spec.NamedParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import javax.crypto.SecretKey;

/* loaded from: classes3.dex */
public class AndroidKeyStoreSpi extends KeyStoreSpi {
    public static final String NAME = "AndroidKeyStore";
    public static final String TAG = "AndroidKeyStoreSpi";
    private KeyStore2 mKeyStore;
    private int mNamespace = -1;

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr) throws UnrecoverableKeyException, NoSuchAlgorithmException {
        try {
            return AndroidKeyStoreProvider.loadAndroidKeyStoreKeyFromKeystore(this.mKeyStore, str, this.mNamespace);
        } catch (KeyPermanentlyInvalidatedException e) {
            throw new UnrecoverableKeyException(e.getMessage());
        } catch (UnrecoverableKeyException e2) {
            Throwable cause = e2.getCause();
            if ((cause instanceof KeyStoreException) && ((KeyStoreException) cause).getErrorCode() == 7) {
                return null;
            }
            throw e2;
        }
    }

    private KeyDescriptor makeKeyDescriptor(String str) {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = getTargetDomain();
        keyDescriptor.nspace = this.mNamespace;
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        return keyDescriptor;
    }

    private int getTargetDomain() {
        return this.mNamespace == -1 ? 0 : 2;
    }

    private KeyEntryResponse getKeyMetadata(String str) {
        if (str == null) {
            throw new NullPointerException("alias == null");
        }
        KeyDescriptor keyDescriptorMakeKeyDescriptor = makeKeyDescriptor(str);
        try {
            StrictMode.noteDiskRead();
            return this.mKeyStore.getKeyEntry(keyDescriptorMakeKeyDescriptor);
        } catch (KeyStoreException e) {
            if (e.getErrorCode() == 7) {
                return null;
            }
            Log.w(TAG, "Could not get key metadata from Keystore.", e);
            return null;
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String str) {
        X509Certificate certificate;
        Certificate[] certificateArr;
        KeyEntryResponse keyMetadata = getKeyMetadata(str);
        if (keyMetadata == null || keyMetadata.metadata.certificate == null || (certificate = toCertificate(keyMetadata.metadata.certificate)) == null) {
            return null;
        }
        byte[] bArr = keyMetadata.metadata.certificateChain;
        int i = 1;
        if (bArr != null) {
            Collection<X509Certificate> certificates = toCertificates(bArr);
            certificateArr = new Certificate[certificates.size() + 1];
            Iterator<X509Certificate> it = certificates.iterator();
            while (it.hasNext()) {
                certificateArr[i] = it.next();
                i++;
            }
        } else {
            certificateArr = new Certificate[1];
        }
        certificateArr[0] = certificate;
        return certificateArr;
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        KeyEntryResponse keyMetadata = getKeyMetadata(str);
        if (keyMetadata == null) {
            return null;
        }
        byte[] bArr = keyMetadata.metadata.certificate;
        if (bArr != null) {
            return toCertificate(bArr);
        }
        byte[] bArr2 = keyMetadata.metadata.certificateChain;
        if (bArr2 != null) {
            return toCertificate(bArr2);
        }
        return null;
    }

    static X509Certificate toCertificate(byte[] bArr) {
        try {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            Log.w(NAME, "Couldn't parse certificate in keystore", e);
            return null;
        }
    }

    private static Collection<X509Certificate> toCertificates(byte[] bArr) {
        try {
            return CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            Log.w(NAME, "Couldn't parse certificates in keystore", e);
            return new ArrayList();
        }
    }

    private static boolean getMgf1DigestSetterFlag() {
        try {
            return com.android.internal.hidden_from_bootclasspath.android.security.Flags.mgf1DigestSetterV2();
        } catch (SecurityException e) {
            Log.w(NAME, "Cannot read MGF1 Digest setter flag value", e);
            return false;
        }
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        KeyEntryResponse keyMetadata = getKeyMetadata(str);
        if (keyMetadata == null || keyMetadata.metadata.modificationTimeMs == -1) {
            return null;
        }
        return new Date(keyMetadata.metadata.modificationTimeMs);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) throws InterruptedException, java.security.KeyStoreException {
        if (cArr != null && cArr.length > 0) {
            throw new java.security.KeyStoreException("entries cannot be protected with passwords");
        }
        if (key instanceof PrivateKey) {
            setPrivateKeyEntry(str, (PrivateKey) key, certificateArr, null);
        } else {
            if (key instanceof SecretKey) {
                setSecretKeyEntry(str, (SecretKey) key, null);
                return;
            }
            throw new java.security.KeyStoreException("Only PrivateKey and SecretKey are supported");
        }
    }

    private static KeyProtection getLegacyKeyProtectionParameter(PrivateKey privateKey) throws java.security.KeyStoreException {
        KeyProtection.Builder builder;
        String algorithm = privateKey.getAlgorithm();
        if (KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(algorithm)) {
            builder = new KeyProtection.Builder(12);
            builder.setDigests(KeyProperties.DIGEST_NONE, "SHA-1", KeyProperties.DIGEST_SHA224, "SHA-256", KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512);
        } else if ("RSA".equalsIgnoreCase(algorithm)) {
            builder = new KeyProtection.Builder(15);
            builder.setDigests(KeyProperties.DIGEST_NONE, KeyProperties.DIGEST_MD5, "SHA-1", KeyProperties.DIGEST_SHA224, "SHA-256", KeyProperties.DIGEST_SHA384, KeyProperties.DIGEST_SHA512);
            builder.setEncryptionPaddings("NoPadding", KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, KeyProperties.ENCRYPTION_PADDING_RSA_OAEP);
            builder.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, KeyProperties.SIGNATURE_PADDING_RSA_PSS);
            builder.setRandomizedEncryptionRequired(false);
        } else {
            throw new java.security.KeyStoreException("Unsupported key algorithm: " + algorithm);
        }
        builder.setUserAuthenticationRequired(false);
        return builder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v3, types: [int] */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r23v0, types: [java.security.PrivateKey] */
    /* JADX WARN: Type inference failed for: r8v7, types: [android.security.KeyStoreSecurityLevel] */
    private void setPrivateKeyEntry(String str, PrivateKey privateKey, Certificate[] certificateArr, KeyStore.ProtectionParameter protectionParameter) throws InterruptedException, java.security.KeyStoreException {
        KeyProtection legacyKeyProtectionParameter;
        int i;
        boolean z;
        ?? r12;
        byte[] bArr;
        String str2;
        int i2;
        int i3;
        String str3 = "SHA-1";
        boolean z2 = true;
        int i4 = 0;
        if (protectionParameter == null) {
            legacyKeyProtectionParameter = getLegacyKeyProtectionParameter(privateKey);
            i = 1;
            r12 = 0;
        } else {
            if (protectionParameter instanceof KeyStoreParameter) {
                z = false;
                legacyKeyProtectionParameter = getLegacyKeyProtectionParameter(privateKey);
            } else if (protectionParameter instanceof KeyProtection) {
                legacyKeyProtectionParameter = (KeyProtection) protectionParameter;
                boolean zIsCriticalToDeviceEncryption = legacyKeyProtectionParameter.isCriticalToDeviceEncryption();
                z = zIsCriticalToDeviceEncryption;
                if (legacyKeyProtectionParameter.isStrongBoxBacked()) {
                    i = 2;
                    r12 = zIsCriticalToDeviceEncryption;
                }
            } else {
                throw new java.security.KeyStoreException("Unsupported protection parameter class:" + protectionParameter.getClass().getName() + ". Supported: " + KeyProtection.class.getName() + ", " + KeyStoreParameter.class.getName());
            }
            i = 1;
            r12 = z;
        }
        if (certificateArr == null || certificateArr.length == 0) {
            throw new java.security.KeyStoreException("Must supply at least one Certificate with PrivateKey");
        }
        int length = certificateArr.length;
        X509Certificate[] x509CertificateArr = new X509Certificate[length];
        for (int i5 = 0; i5 < certificateArr.length; i5++) {
            if (!"X.509".equals(certificateArr[i5].getType())) {
                throw new java.security.KeyStoreException("Certificates must be in X.509 format: invalid cert #" + i5);
            }
            Certificate certificate = certificateArr[i5];
            if (!(certificate instanceof X509Certificate)) {
                throw new java.security.KeyStoreException("Certificates must be in X.509 format: invalid cert #" + i5);
            }
            x509CertificateArr[i5] = (X509Certificate) certificate;
        }
        try {
            byte[] encoded = x509CertificateArr[0].getEncoded();
            if (certificateArr.length > 1) {
                int i6 = length - 1;
                byte[][] bArr2 = new byte[i6][];
                int i7 = 0;
                int length2 = 0;
                while (i7 < i6) {
                    int i8 = i7 + 1;
                    try {
                        boolean z3 = z2;
                        byte[] encoded2 = x509CertificateArr[i8].getEncoded();
                        bArr2[i7] = encoded2;
                        length2 += encoded2.length;
                        i7 = i8;
                        z2 = z3;
                    } catch (CertificateEncodingException e) {
                        throw new java.security.KeyStoreException("Failed to encode certificate #" + i7, e);
                    }
                }
                bArr = new byte[length2];
                int i9 = 0;
                for (int i10 = 0; i10 < i6; i10++) {
                    byte[] bArr3 = bArr2[i10];
                    int length3 = bArr3.length;
                    System.arraycopy(bArr3, 0, bArr, i9, length3);
                    i9 += length3;
                    bArr2[i10] = null;
                }
            } else {
                bArr = null;
            }
            int targetDomain = getTargetDomain();
            if (privateKey instanceof AndroidKeyStorePrivateKey) {
                assertCanReplace(str, targetDomain, this.mNamespace, ((AndroidKeyStoreKey) privateKey).getUserKeyDescriptor());
                try {
                    StrictMode.noteDiskWrite();
                    this.mKeyStore.updateSubcomponents(((AndroidKeyStorePrivateKey) privateKey).getKeyIdDescriptor(), encoded, bArr);
                    return;
                } catch (KeyStoreException e2) {
                    throw new java.security.KeyStoreException("Failed to store certificate and certificate chain", e2);
                }
            }
            String format = privateKey.getFormat();
            if (format == null || !"PKCS#8".equals(format)) {
                throw new java.security.KeyStoreException("Unsupported private key export format: " + format + ". Only private keys which export their key material in PKCS#8 format are supported.");
            }
            byte[] encoded3 = privateKey.getEncoded();
            if (encoded3 == null) {
                throw new java.security.KeyStoreException("Private key did not export any key material");
            }
            final ArrayList arrayList = new ArrayList();
            try {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(268435458, KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(privateKey.getAlgorithm())));
                KeyStore2ParameterUtils.forEachSetFlag(legacyKeyProtectionParameter.getPurposes(), new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreSpi$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        arrayList.add(KeyStore2ParameterUtils.makeEnum(536870913, KeyProperties.Purpose.toKeymaster(((Integer) obj).intValue())));
                    }
                });
                if (legacyKeyProtectionParameter.isDigestsSpecified()) {
                    String[] digests = legacyKeyProtectionParameter.getDigests();
                    int length4 = digests.length;
                    while (i4 < length4) {
                        arrayList.add(KeyStore2ParameterUtils.makeEnum(536870917, KeyProperties.Digest.toKeymaster(digests[i4])));
                        i4++;
                        digests = digests;
                    }
                }
                String[] blockModes = legacyKeyProtectionParameter.getBlockModes();
                int length5 = blockModes.length;
                int i11 = 0;
                while (i11 < length5) {
                    arrayList.add(KeyStore2ParameterUtils.makeEnum(536870916, KeyProperties.BlockMode.toKeymaster(blockModes[i11])));
                    i11++;
                    blockModes = blockModes;
                }
                int[] iArrAllToKeymaster = KeyProperties.EncryptionPadding.allToKeymaster(legacyKeyProtectionParameter.getEncryptionPaddings());
                if ((legacyKeyProtectionParameter.getPurposes() & 1) != 0 && legacyKeyProtectionParameter.isRandomizedEncryptionRequired()) {
                    for (int i12 : iArrAllToKeymaster) {
                        if (!KeymasterUtils.isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(i12)) {
                            throw new java.security.KeyStoreException("Randomized encryption (IND-CPA) required but is violated by encryption padding mode: " + KeyProperties.EncryptionPadding.fromKeymaster(i12) + ". See KeyProtection documentation.");
                        }
                    }
                }
                int length6 = iArrAllToKeymaster.length;
                int i13 = 0;
                while (i13 < length6) {
                    int[] iArr = iArrAllToKeymaster;
                    int i14 = iArr[i13];
                    arrayList.add(KeyStore2ParameterUtils.makeEnum(536870918, i14));
                    if (i14 == 2 && KeymasterUtils.isKeyMintDevice(i)) {
                        if (legacyKeyProtectionParameter.isMgf1DigestsSpecified()) {
                            for (Iterator<String> it = legacyKeyProtectionParameter.getMgf1Digests().iterator(); it.hasNext(); it = it) {
                                arrayList.add(KeyStore2ParameterUtils.makeEnum(536871115, KeyProperties.Digest.toKeymaster(it.next())));
                            }
                        } else {
                            arrayList.add(KeyStore2ParameterUtils.makeEnum(536871115, KeyProperties.Digest.toKeymaster(str3)));
                            if (!getMgf1DigestSetterFlag()) {
                                int keymaster = KeyProperties.Digest.toKeymaster(str3);
                                String[] digests2 = legacyKeyProtectionParameter.getDigests();
                                str2 = str3;
                                int length7 = digests2.length;
                                i2 = length6;
                                int i15 = 0;
                                while (i15 < length7) {
                                    int i16 = length7;
                                    int keymaster2 = KeyProperties.Digest.toKeymaster(digests2[i15]);
                                    if (keymaster2 != keymaster) {
                                        i3 = keymaster;
                                        arrayList.add(KeyStore2ParameterUtils.makeEnum(536871115, keymaster2));
                                    } else {
                                        i3 = keymaster;
                                    }
                                    i15++;
                                    keymaster = i3;
                                    length7 = i16;
                                }
                            }
                        }
                        str2 = str3;
                        i2 = length6;
                    } else {
                        str2 = str3;
                        i2 = length6;
                    }
                    i13++;
                    iArrAllToKeymaster = iArr;
                    str3 = str2;
                    length6 = i2;
                }
                for (String str4 : legacyKeyProtectionParameter.getSignaturePaddings()) {
                    arrayList.add(KeyStore2ParameterUtils.makeEnum(536870918, KeyProperties.SignaturePadding.toKeymaster(str4)));
                }
                KeyStore2ParameterUtils.addUserAuthArgs(arrayList, legacyKeyProtectionParameter);
                if (legacyKeyProtectionParameter.getKeyValidityStart() != null) {
                    arrayList.add(KeyStore2ParameterUtils.makeDate(1610613136, legacyKeyProtectionParameter.getKeyValidityStart()));
                }
                if (legacyKeyProtectionParameter.getKeyValidityForOriginationEnd() != null) {
                    arrayList.add(KeyStore2ParameterUtils.makeDate(1610613137, legacyKeyProtectionParameter.getKeyValidityForOriginationEnd()));
                }
                if (legacyKeyProtectionParameter.getKeyValidityForConsumptionEnd() != null) {
                    arrayList.add(KeyStore2ParameterUtils.makeDate(1610613138, legacyKeyProtectionParameter.getKeyValidityForConsumptionEnd()));
                }
                if (legacyKeyProtectionParameter.getMaxUsageCount() != -1) {
                    arrayList.add(KeyStore2ParameterUtils.makeInt(805306773, legacyKeyProtectionParameter.getMaxUsageCount()));
                }
                if (3 == KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(privateKey.getAlgorithm())) {
                    arrayList.add(KeyStore2ParameterUtils.makeEnum(268435466, getKeymasterEcCurve(privateKey)));
                }
                try {
                    KeyMetadata keyMetadataImportKey = this.mKeyStore.getSecurityLevel(i).importKey(makeKeyDescriptor(str), null, arrayList, r12, encoded3);
                    try {
                        StrictMode.noteDiskWrite();
                        this.mKeyStore.updateSubcomponents(keyMetadataImportKey.key, encoded, bArr);
                    } catch (KeyStoreException e3) {
                        this.mKeyStore.deleteKey(keyMetadataImportKey.key);
                        throw new java.security.KeyStoreException("Failed to store certificate and certificate chain", e3);
                    }
                } catch (KeyStoreException e4) {
                    throw new java.security.KeyStoreException("Failed to store private key", e4);
                }
            } catch (IllegalArgumentException | IllegalStateException e5) {
                throw new java.security.KeyStoreException(e5);
            }
        } catch (CertificateEncodingException e6) {
            throw new java.security.KeyStoreException("Failed to encode certificate #0", e6);
        }
    }

    private int getKeymasterEcCurve(PrivateKey privateKey) {
        if (privateKey instanceof ECKey) {
            int keymasterEcCurve = KeymasterUtils.getKeymasterEcCurve(KeymasterUtils.getCurveName(((ECPrivateKey) privateKey).getParams()));
            if (keymasterEcCurve >= 0) {
                return keymasterEcCurve;
            }
        } else if (privateKey instanceof XECKey) {
            if (((XECPrivateKey) privateKey).getParams().equals(NamedParameterSpec.X25519)) {
                return 4;
            }
        } else {
            if (privateKey.getAlgorithm().equals(KeyProperties.KEY_ALGORITHM_XDH)) {
                return 4;
            }
            if ((privateKey instanceof EdECKey) && ((EdECPrivateKey) privateKey).getParams().equals(NamedParameterSpec.ED25519)) {
                return 4;
            }
        }
        throw new IllegalArgumentException("Unexpected Key " + privateKey.getClass().getName());
    }

    private static void assertCanReplace(String str, int i, int i2, KeyDescriptor keyDescriptor) throws java.security.KeyStoreException {
        String str2;
        if (str == null || !str.equals(keyDescriptor.alias) || keyDescriptor.domain != i || (keyDescriptor.domain == 2 && keyDescriptor.nspace != i2)) {
            StringBuilder sb = new StringBuilder("Can only replace keys with same alias: ");
            sb.append(str);
            sb.append(" != ");
            sb.append(keyDescriptor.alias);
            sb.append(" in the same target domain: ");
            sb.append(i);
            sb.append(" != ");
            sb.append(keyDescriptor.domain);
            if (i == 2) {
                str2 = " in the same target namespace: " + i2 + " != " + keyDescriptor.nspace;
            } else {
                str2 = "";
            }
            sb.append(str2);
            throw new java.security.KeyStoreException(sb.toString());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setSecretKeyEntry(String str, SecretKey secretKey, KeyStore.ProtectionParameter protectionParameter) throws java.security.KeyStoreException {
        Object[] objArr;
        if (protectionParameter != null && !(protectionParameter instanceof KeyProtection)) {
            throw new java.security.KeyStoreException("Unsupported protection parameter class: " + protectionParameter.getClass().getName() + ". Supported: " + KeyProtection.class.getName());
        }
        KeyProtection keyProtection = (KeyProtection) protectionParameter;
        int targetDomain = getTargetDomain();
        if (secretKey instanceof AndroidKeyStoreSecretKey) {
            AndroidKeyStoreSecretKey androidKeyStoreSecretKey = (AndroidKeyStoreSecretKey) secretKey;
            String str2 = androidKeyStoreSecretKey.getUserKeyDescriptor().alias;
            assertCanReplace(str, targetDomain, this.mNamespace, androidKeyStoreSecretKey.getUserKeyDescriptor());
            if (keyProtection != null) {
                throw new java.security.KeyStoreException("Modifying KeyStore-backed key using protection parameters not supported");
            }
            return;
        }
        if (keyProtection == null) {
            throw new java.security.KeyStoreException("Protection parameters must be specified when importing a symmetric key");
        }
        String format = secretKey.getFormat();
        if (format == null) {
            throw new java.security.KeyStoreException("Only secret keys that export their key material are supported");
        }
        if (!"RAW".equals(format)) {
            throw new java.security.KeyStoreException("Unsupported secret key material export format: " + format);
        }
        byte[] encoded = secretKey.getEncoded();
        if (encoded == null) {
            throw new java.security.KeyStoreException("Key did not export its key material despite supporting RAW format export");
        }
        final ArrayList arrayList = new ArrayList();
        try {
            int keymasterSecretKeyAlgorithm = KeyProperties.KeyAlgorithm.toKeymasterSecretKeyAlgorithm(secretKey.getAlgorithm());
            arrayList.add(KeyStore2ParameterUtils.makeEnum(268435458, keymasterSecretKeyAlgorithm));
            if (keymasterSecretKeyAlgorithm == 128) {
                int keymasterDigest = KeyProperties.KeyAlgorithm.toKeymasterDigest(secretKey.getAlgorithm());
                if (keymasterDigest == -1) {
                    throw new ProviderException("HMAC key algorithm digest unknown for key algorithm " + secretKey.getAlgorithm());
                }
                if (keyProtection.isDigestsSpecified()) {
                    int[] iArrAllToKeymaster = KeyProperties.Digest.allToKeymaster(keyProtection.getDigests());
                    if (iArrAllToKeymaster.length != 1 || iArrAllToKeymaster[0] != keymasterDigest) {
                        throw new java.security.KeyStoreException("Unsupported digests specification: " + Arrays.asList(keyProtection.getDigests()) + ". Only " + KeyProperties.Digest.fromKeymaster(keymasterDigest) + " supported for HMAC key algorithm " + secretKey.getAlgorithm());
                    }
                }
                int digestOutputSizeBits = KeymasterUtils.getDigestOutputSizeBits(keymasterDigest);
                if (digestOutputSizeBits == -1) {
                    throw new ProviderException("HMAC key authorized for unsupported digest: " + KeyProperties.Digest.fromKeymaster(keymasterDigest));
                }
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870917, keymasterDigest));
                arrayList.add(KeyStore2ParameterUtils.makeInt(805306376, digestOutputSizeBits));
            } else if (keyProtection.isDigestsSpecified()) {
                for (String str3 : keyProtection.getDigests()) {
                    arrayList.add(KeyStore2ParameterUtils.makeEnum(536870917, KeyProperties.Digest.toKeymaster(str3)));
                }
            }
            KeyStore2ParameterUtils.forEachSetFlag(keyProtection.getPurposes(), new Consumer() { // from class: android.security.keystore2.AndroidKeyStoreSpi$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(KeyStore2ParameterUtils.makeEnum(536870913, KeyProperties.Purpose.toKeymaster(((Integer) obj).intValue())));
                }
            });
            if ((keyProtection.getPurposes() & 1) == 0) {
                objArr = false;
            } else if (((KeyProtection) protectionParameter).isRandomizedEncryptionRequired()) {
                objArr = true;
            } else {
                arrayList.add(KeyStore2ParameterUtils.makeBool(1879048199));
                objArr = false;
            }
            for (String str4 : keyProtection.getBlockModes()) {
                int keymaster = KeyProperties.BlockMode.toKeymaster(str4);
                if (objArr != false && !KeymasterUtils.isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(keymaster)) {
                    throw new java.security.KeyStoreException("Randomized encryption (IND-CPA) required but may be violated by block mode: " + str4 + ". See KeyProtection documentation.");
                }
                if (keymasterSecretKeyAlgorithm == 32 && keymaster == 32) {
                    arrayList.add(KeyStore2ParameterUtils.makeInt(805306376, 96));
                }
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870916, keymaster));
            }
            if (keyProtection.getSignaturePaddings().length > 0) {
                throw new java.security.KeyStoreException("Signature paddings not supported for symmetric keys");
            }
            for (String str5 : keyProtection.getEncryptionPaddings()) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870918, KeyProperties.EncryptionPadding.toKeymaster(str5)));
            }
            KeyStore2ParameterUtils.addUserAuthArgs(arrayList, keyProtection);
            if (keyProtection.getKeyValidityStart() != null) {
                arrayList.add(KeyStore2ParameterUtils.makeDate(1610613136, keyProtection.getKeyValidityStart()));
            }
            if (keyProtection.getKeyValidityForOriginationEnd() != null) {
                arrayList.add(KeyStore2ParameterUtils.makeDate(1610613137, keyProtection.getKeyValidityForOriginationEnd()));
            }
            if (keyProtection.getKeyValidityForConsumptionEnd() != null) {
                arrayList.add(KeyStore2ParameterUtils.makeDate(1610613138, keyProtection.getKeyValidityForConsumptionEnd()));
            }
            if (keyProtection.getMaxUsageCount() != -1) {
                arrayList.add(KeyStore2ParameterUtils.makeInt(805306773, keyProtection.getMaxUsageCount()));
            }
            if (keyProtection.isRollbackResistant()) {
                arrayList.add(KeyStore2ParameterUtils.makeBool(1879048495));
            }
            boolean zIsCriticalToDeviceEncryption = keyProtection.isCriticalToDeviceEncryption();
            try {
                this.mKeyStore.getSecurityLevel(keyProtection.isStrongBoxBacked() ? 2 : 1).importKey(makeKeyDescriptor(str), null, arrayList, zIsCriticalToDeviceEncryption ? 1 : 0, encoded);
            } catch (KeyStoreException e) {
                throw new java.security.KeyStoreException("Failed to import secret key.", e);
            }
        } catch (IllegalArgumentException | IllegalStateException e2) {
            throw new java.security.KeyStoreException(e2);
        }
    }

    private void setWrappedKeyEntry(String str, WrappedKeyEntry wrappedKeyEntry, KeyStore.ProtectionParameter protectionParameter) throws java.security.KeyStoreException {
        int keymaster;
        int keymaster2;
        if (protectionParameter != null) {
            throw new java.security.KeyStoreException("Protection parameters are specified inside wrapped keys");
        }
        String[] strArrSplit = wrappedKeyEntry.getTransformation().split("/");
        ArrayList arrayList = new ArrayList();
        String str2 = strArrSplit[0];
        if ("RSA".equalsIgnoreCase(str2)) {
            arrayList.add(KeyStore2ParameterUtils.makeEnum(268435458, 1));
            if (strArrSplit.length > 1) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870916, KeyProperties.BlockMode.toKeymaster(strArrSplit[1])));
            }
            if (strArrSplit.length > 2 && (keymaster2 = KeyProperties.EncryptionPadding.toKeymaster(strArrSplit[2])) != 1) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870918, keymaster2));
            }
            KeyGenParameterSpec keyGenParameterSpec = (KeyGenParameterSpec) wrappedKeyEntry.getAlgorithmParameterSpec();
            if (keyGenParameterSpec.isDigestsSpecified() && (keymaster = KeyProperties.Digest.toKeymaster(keyGenParameterSpec.getDigests()[0])) != 0) {
                arrayList.add(KeyStore2ParameterUtils.makeEnum(536870917, keymaster));
            }
            KeyDescriptor keyDescriptorMakeKeyDescriptor = makeKeyDescriptor(wrappedKeyEntry.getWrappingKeyAlias());
            try {
                StrictMode.noteDiskRead();
                KeyEntryResponse keyEntry = this.mKeyStore.getKeyEntry(keyDescriptorMakeKeyDescriptor);
                KeyDescriptor keyDescriptorMakeKeyDescriptor2 = makeKeyDescriptor(str);
                KeyStoreSecurityLevel keyStoreSecurityLevel = new KeyStoreSecurityLevel(keyEntry.iSecurityLevel);
                long[] authenticatorIds = ((BiometricManager) AppGlobals.getInitialApplication().getSystemService(BiometricManager.class)).getAuthenticatorIds();
                ArrayList arrayList2 = new ArrayList();
                AuthenticatorSpec authenticatorSpec = new AuthenticatorSpec();
                authenticatorSpec.authenticatorType = 1;
                authenticatorSpec.authenticatorId = GateKeeper.getSecureUserId();
                arrayList2.add(authenticatorSpec);
                for (long j : authenticatorIds) {
                    AuthenticatorSpec authenticatorSpec2 = new AuthenticatorSpec();
                    authenticatorSpec2.authenticatorType = 2;
                    authenticatorSpec2.authenticatorId = j;
                    arrayList2.add(authenticatorSpec2);
                }
                if (strArrSplit.length > 2) {
                    boolean z = keyEntry.metadata.keySecurityLevel == 2;
                    Log.w(TAG, "isStrongBoxBacked : " + z + ", isQcom : " + KeymasterUtils.isQCDevice());
                    if (KeyProperties.EncryptionPadding.toKeymaster(strArrSplit[2]) == 2 && ((!z || !KeymasterUtils.isQCDevice()) && keyEntry.metadata != null && keyEntry.metadata.authorizations != null)) {
                        Authorization[] authorizationArr = keyEntry.metadata.authorizations;
                        int length = authorizationArr.length;
                        int i = 0;
                        while (true) {
                            if (i < length) {
                                if (authorizationArr[i].keyParameter.tag == 536871115) {
                                    arrayList.add(KeyStore2ParameterUtils.makeEnum(536871115, KeyProperties.Digest.toKeymaster("SHA-1")));
                                    break;
                                }
                                i++;
                            }
                        }
                    }
                }
                try {
                    StrictMode.noteDiskWrite();
                    keyStoreSecurityLevel.importWrappedKey(keyDescriptorMakeKeyDescriptor2, keyDescriptorMakeKeyDescriptor, wrappedKeyEntry.getWrappedKeyBytes(), null, arrayList, (AuthenticatorSpec[]) arrayList2.toArray(new AuthenticatorSpec[0]));
                    return;
                } catch (KeyStoreException e) {
                    if (e.getErrorCode() != -100) {
                        throw new java.security.KeyStoreException("Failed to import wrapped key. Keystore error code: " + e.getErrorCode(), e);
                    }
                    throw new SecureKeyImportUnavailableException("Could not import wrapped key");
                }
            } catch (KeyStoreException e2) {
                throw new java.security.KeyStoreException("Failed to import wrapped key. Keystore error code: " + e2.getErrorCode(), e2);
            }
        }
        throw new java.security.KeyStoreException("Algorithm \"" + str2 + "\" not supported for wrapping. Only RSA wrapping keys are supported.");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) throws java.security.KeyStoreException {
        throw new java.security.KeyStoreException("Operation not supported because key encoding is unknown");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate) throws InterruptedException, java.security.KeyStoreException, CertificateEncodingException {
        if (isKeyEntry(str)) {
            throw new java.security.KeyStoreException("Entry exists and is not a trusted certificate");
        }
        if (certificate == null) {
            throw new NullPointerException("cert == null");
        }
        try {
            byte[] encoded = certificate.getEncoded();
            try {
                StrictMode.noteDiskWrite();
                this.mKeyStore.updateSubcomponents(makeKeyDescriptor(str), null, encoded);
            } catch (KeyStoreException e) {
                throw new java.security.KeyStoreException("Couldn't insert certificate.", e);
            }
        } catch (CertificateEncodingException e2) {
            throw new java.security.KeyStoreException(e2);
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) throws InterruptedException, java.security.KeyStoreException {
        KeyDescriptor keyDescriptorMakeKeyDescriptor = makeKeyDescriptor(str);
        try {
            StrictMode.noteDiskWrite();
            this.mKeyStore.deleteKey(keyDescriptorMakeKeyDescriptor);
        } catch (KeyStoreException e) {
            if (e.getErrorCode() == 7) {
                return;
            }
            throw new java.security.KeyStoreException("Failed to delete entry: " + str, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public KeyDescriptor[] getAliasesBatch(String str) {
        try {
            StrictMode.noteDiskRead();
            return this.mKeyStore.listBatch(getTargetDomain(), this.mNamespace, str);
        } catch (KeyStoreException e) {
            Log.e(TAG, "Failed to list keystore entries.", e);
            return new KeyDescriptor[0];
        }
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration<String> engineAliases() {
        return new KeyEntriesEnumerator();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        if (str != null) {
            return getKeyMetadata(str) != null;
        }
        throw new NullPointerException("alias == null");
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        try {
            StrictMode.noteDiskRead();
            return this.mKeyStore.getNumberOfEntries(getTargetDomain(), this.mNamespace);
        } catch (KeyStoreException e) {
            Log.e(TAG, "Failed to get the number of keystore entries.", e);
            return 0;
        }
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        return isKeyEntry(str);
    }

    private boolean isKeyEntry(String str) {
        if (str == null) {
            throw new NullPointerException("alias == null");
        }
        KeyEntryResponse keyMetadata = getKeyMetadata(str);
        return (keyMetadata == null || keyMetadata.iSecurityLevel == null) ? false : true;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        if (str == null) {
            throw new NullPointerException("alias == null");
        }
        KeyEntryResponse keyMetadata = getKeyMetadata(str);
        return (keyMetadata == null || keyMetadata.metadata.certificateChain == null || keyMetadata.iSecurityLevel != null) ? false : true;
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) throws CertificateEncodingException {
        KeyDescriptor[] list;
        String str = null;
        if (certificate == null) {
            return null;
        }
        if (!"X.509".equalsIgnoreCase(certificate.getType())) {
            Log.e(TAG, "In engineGetCertificateAlias: only X.509 certificates are supported.");
            return null;
        }
        try {
            byte[] encoded = certificate.getEncoded();
            if (encoded == null) {
                return null;
            }
            try {
                StrictMode.noteDiskRead();
                list = this.mKeyStore.list(getTargetDomain(), this.mNamespace);
            } catch (KeyStoreException e) {
                Log.w(TAG, "Failed to get list of keystore entries.", e);
                list = null;
            }
            if (list == null) {
                return null;
            }
            for (KeyDescriptor keyDescriptor : list) {
                KeyEntryResponse keyMetadata = getKeyMetadata(keyDescriptor.alias);
                if (keyMetadata != null) {
                    if (keyMetadata.metadata.certificate != null) {
                        if (Arrays.equals(keyMetadata.metadata.certificate, encoded)) {
                            return keyDescriptor.alias;
                        }
                    } else if (keyMetadata.metadata.certificateChain != null && str == null && Arrays.equals(keyMetadata.metadata.certificateChain, encoded)) {
                        str = keyDescriptor.alias;
                    }
                }
            }
            return str;
        } catch (CertificateEncodingException e2) {
            Log.e(TAG, "While trying to get the alias for a certificate.", e2);
            return null;
        }
    }

    public void initForTesting(KeyStore2 keyStore2) {
        this.mKeyStore = keyStore2;
        this.mNamespace = -1;
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr) throws NoSuchAlgorithmException, IOException, CertificateException {
        throw new UnsupportedOperationException("Can not serialize AndroidKeyStore to OutputStream");
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(InputStream inputStream, char[] cArr) throws NoSuchAlgorithmException, IOException, CertificateException {
        if (inputStream != null) {
            throw new IllegalArgumentException("InputStream not supported");
        }
        if (cArr != null) {
            throw new IllegalArgumentException("password not supported");
        }
        this.mKeyStore = KeyStore2.getInstance();
        this.mNamespace = -1;
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(KeyStore.LoadStoreParameter loadStoreParameter) throws NoSuchAlgorithmException, IOException, CertificateException {
        int namespace;
        if (loadStoreParameter == null) {
            namespace = -1;
        } else if (loadStoreParameter instanceof AndroidKeyStoreLoadStoreParameter) {
            namespace = ((AndroidKeyStoreLoadStoreParameter) loadStoreParameter).getNamespace();
        } else {
            throw new IllegalArgumentException("Unsupported param type: " + loadStoreParameter.getClass());
        }
        this.mKeyStore = KeyStore2.getInstance();
        this.mNamespace = namespace;
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetEntry(String str, KeyStore.Entry entry, KeyStore.ProtectionParameter protectionParameter) throws InterruptedException, java.security.KeyStoreException, CertificateEncodingException {
        if (entry == null) {
            throw new java.security.KeyStoreException("entry == null");
        }
        if (entry instanceof KeyStore.TrustedCertificateEntry) {
            engineDeleteEntry(str);
            engineSetCertificateEntry(str, ((KeyStore.TrustedCertificateEntry) entry).getTrustedCertificate());
            return;
        }
        if (entry instanceof KeyStore.PrivateKeyEntry) {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) entry;
            setPrivateKeyEntry(str, privateKeyEntry.getPrivateKey(), privateKeyEntry.getCertificateChain(), protectionParameter);
        } else if (entry instanceof KeyStore.SecretKeyEntry) {
            setSecretKeyEntry(str, ((KeyStore.SecretKeyEntry) entry).getSecretKey(), protectionParameter);
        } else if (entry instanceof WrappedKeyEntry) {
            setWrappedKeyEntry(str, (WrappedKeyEntry) entry, protectionParameter);
        } else {
            throw new java.security.KeyStoreException("Entry must be a PrivateKeyEntry, SecretKeyEntry, WrappedKeyEntry or TrustedCertificateEntry; was " + entry);
        }
    }

    private class KeyEntriesEnumerator implements Enumeration<String> {
        private KeyDescriptor[] mCurrentBatch;
        private int mCurrentEntry;
        private String mLastAlias;

        private KeyEntriesEnumerator() {
            this.mCurrentEntry = 0;
            this.mLastAlias = null;
            getAndValidateNextBatch();
        }

        private void getAndValidateNextBatch() {
            this.mCurrentBatch = AndroidKeyStoreSpi.this.getAliasesBatch(this.mLastAlias);
            this.mCurrentEntry = 0;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            KeyDescriptor[] keyDescriptorArr = this.mCurrentBatch;
            return keyDescriptorArr != null && keyDescriptorArr.length > 0;
        }

        @Override // java.util.Enumeration
        public String nextElement() {
            KeyDescriptor[] keyDescriptorArr = this.mCurrentBatch;
            if (keyDescriptorArr == null || keyDescriptorArr.length == 0) {
                throw new NoSuchElementException("Error while fetching entries.");
            }
            this.mLastAlias = keyDescriptorArr[this.mCurrentEntry].alias;
            int i = this.mCurrentEntry + 1;
            this.mCurrentEntry = i;
            if (i >= this.mCurrentBatch.length) {
                getAndValidateNextBatch();
            }
            return this.mLastAlias;
        }
    }
}
