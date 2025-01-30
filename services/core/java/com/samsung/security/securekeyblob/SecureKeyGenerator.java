package com.samsung.security.securekeyblob;

import android.content.Context;
import android.hardware.security.keymint.Certificate;
import android.hardware.security.keymint.KeyParameter;
import android.hardware.security.keymint.KeyParameterValue;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.security.KeyStore2;
import android.security.KeyStoreException;
import android.security.keymaster.KeymasterDefs;
import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyProperties;
import android.security.securekeygeneration.ISecureKeyGeneration;
import android.security.securekeygeneration.SecureKeyInfo;
import android.text.TextUtils;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.ProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes2.dex */
public class SecureKeyGenerator {
    public ISecureKeyGeneration mSamsungSecurekeyGeneratorBinder = null;
    public final KeyStore2 mKeyStore = KeyStore2.getInstance();

    public interface checkedRemoteRequest {
        Object execute(ISecureKeyGeneration iSecureKeyGeneration);
    }

    public SecureKeyResult generateKeyPair(final SecureKeyGenParameterSpec secureKeyGenParameterSpec) {
        Certificate[] certificateArr;
        checkValidKeyGenParameterSpec(secureKeyGenParameterSpec);
        try {
            ArrayList arrayList = new ArrayList();
            SecureKeyInfo secureKeyInfo = (SecureKeyInfo) handleRemoteExceptionSecureKeyGeneration(new checkedRemoteRequest() { // from class: com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda0
                @Override // com.samsung.security.securekeyblob.SecureKeyGenerator.checkedRemoteRequest
                public final Object execute(ISecureKeyGeneration iSecureKeyGeneration) {
                    SecureKeyInfo lambda$generateKeyPair$0;
                    lambda$generateKeyPair$0 = SecureKeyGenerator.this.lambda$generateKeyPair$0(secureKeyGenParameterSpec, iSecureKeyGeneration);
                    return lambda$generateKeyPair$0;
                }
            });
            if (secureKeyInfo == null || (certificateArr = secureKeyInfo.attestedCertificates) == null) {
                throw new NullPointerException("SecureKeyGeneration fail");
            }
            for (Certificate certificate : certificateArr) {
                arrayList.add(certificate.encodedCertificate);
            }
            if (arrayList.size() < 3) {
                throw new ProviderException("Attestation certificate chain contained " + arrayList.size() + " entries. At least three are required.");
            }
            return new SecureKeyResult(secureKeyInfo.blob, getCertificates(arrayList), secureKeyGenParameterSpec.getServiceTAName());
        } catch (KeyStoreException e) {
            if (e.getErrorCode() == -66) {
                throw new DeviceAttestationException("Failed to generate attestation certificate chain with deviceIds", getKeyStoreException(e.getErrorCode()));
            }
            throw new ProviderException("Failed to generate attestation certificate chain", getKeyStoreException(e.getErrorCode()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ SecureKeyInfo lambda$generateKeyPair$0(SecureKeyGenParameterSpec secureKeyGenParameterSpec, ISecureKeyGeneration iSecureKeyGeneration) {
        return iSecureKeyGeneration.generateSecureKey(constructKeyGenerationArguments(secureKeyGenParameterSpec), constructAttestationArguments(secureKeyGenParameterSpec));
    }

    public boolean isSupportSecureKeyService(Context context) {
        return context.getPackageManager().hasSystemFeature("samsung.software.secure_key_service");
    }

    public final Object handleRemoteExceptionSecureKeyGeneration(checkedRemoteRequest checkedremoterequest) {
        try {
            return checkedremoterequest.execute(getSamsungSecurekeyGenerationService());
        } catch (RemoteException e) {
            Log.e("SecureKeyGenerator", "Cannot connect to SamsungKeystore daemon.", e);
            throw new KeyStoreException(4, "");
        } catch (ServiceSpecificException e2) {
            Log.e("SecureKeyGenerator", "KeyStore exception", e2);
            throw new KeyStoreException(e2.errorCode, "");
        }
    }

    public final synchronized ISecureKeyGeneration getSamsungSecurekeyGenerationService() {
        if (this.mSamsungSecurekeyGeneratorBinder == null) {
            this.mSamsungSecurekeyGeneratorBinder = ISecureKeyGeneration.Stub.asInterface(ServiceManager.checkService("android.security.securekeygeneration"));
        }
        return this.mSamsungSecurekeyGeneratorBinder;
    }

    public final void checkValidKeyGenParameterSpec(SecureKeyGenParameterSpec secureKeyGenParameterSpec) {
        if (secureKeyGenParameterSpec == null) {
            throw new IllegalArgumentException("SecureKeyGenParameterSpec should not be null");
        }
        if (secureKeyGenParameterSpec.getServiceTAName() == null) {
            throw new IllegalArgumentException("service name should not be null");
        }
        String algorithm = secureKeyGenParameterSpec.getAlgorithm();
        algorithm.hashCode();
        if (!algorithm.equals("EC") && !algorithm.equals("RSA")) {
            throw new InvalidAlgorithmParameterException("Unsupported algorithm: " + secureKeyGenParameterSpec.getAlgorithm());
        }
        checkValidKeySize(secureKeyGenParameterSpec.getAlgorithm(), secureKeyGenParameterSpec.getKeySize());
        if (secureKeyGenParameterSpec.getChallenge() == null) {
            throw new IllegalArgumentException("challenge should not be null");
        }
    }

    public final KeyParameter[] constructKeyGenerationArguments(final SecureKeyGenParameterSpec secureKeyGenParameterSpec) {
        final ArrayList arrayList = new ArrayList();
        int keySize = secureKeyGenParameterSpec.getKeySize();
        arrayList.add(makeInt(805306371, keySize));
        String algorithm = secureKeyGenParameterSpec.getAlgorithm();
        algorithm.hashCode();
        if (algorithm.equals("EC")) {
            arrayList.add(makeEnum(268435458, 3));
            arrayList.add(makeEnum(268435466, keySizeAndNameToEcCurve(keySize)));
        } else if (algorithm.equals("RSA")) {
            arrayList.add(makeEnum(268435458, 1));
            arrayList.add(makeLong(1342177480, RSAKeyGenParameterSpec.F4.longValue()));
        }
        ArrayUtils.forEach(KeyProperties.Purpose.allToKeymaster(secureKeyGenParameterSpec.getPurposes()), new Consumer() { // from class: com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecureKeyGenerator.lambda$constructKeyGenerationArguments$1(arrayList, (Integer) obj);
            }
        });
        ArrayUtils.forEach(KeyProperties.BlockMode.allToKeymaster(secureKeyGenParameterSpec.getBlockModes()), new Consumer() { // from class: com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecureKeyGenerator.lambda$constructKeyGenerationArguments$2(arrayList, (Integer) obj);
            }
        });
        ArrayUtils.forEach(KeyProperties.EncryptionPadding.allToKeymaster(secureKeyGenParameterSpec.getEncryptionPaddings()), new Consumer() { // from class: com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecureKeyGenerator.lambda$constructKeyGenerationArguments$4(arrayList, secureKeyGenParameterSpec, (Integer) obj);
            }
        });
        ArrayUtils.forEach(KeyProperties.SignaturePadding.allToKeymaster(secureKeyGenParameterSpec.getSignaturePaddings()), new Consumer() { // from class: com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecureKeyGenerator.lambda$constructKeyGenerationArguments$5(arrayList, (Integer) obj);
            }
        });
        ArrayUtils.forEach(KeyProperties.Digest.allToKeymaster(secureKeyGenParameterSpec.getDigests()), new Consumer() { // from class: com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SecureKeyGenerator.lambda$constructKeyGenerationArguments$6(arrayList, (Integer) obj);
            }
        });
        arrayList.add(makeBytes(-1879045791, secureKeyGenParameterSpec.getServiceTAName()));
        return (KeyParameter[]) arrayList.toArray(new KeyParameter[arrayList.size()]);
    }

    public static /* synthetic */ void lambda$constructKeyGenerationArguments$1(List list, Integer num) {
        list.add(makeEnum(536870913, num.intValue()));
    }

    public static /* synthetic */ void lambda$constructKeyGenerationArguments$2(List list, Integer num) {
        list.add(makeEnum(536870916, num.intValue()));
    }

    public static /* synthetic */ void lambda$constructKeyGenerationArguments$4(final List list, SecureKeyGenParameterSpec secureKeyGenParameterSpec, Integer num) {
        list.add(makeEnum(536870918, num.intValue()));
        if (num.intValue() == 2) {
            final boolean[] zArr = {false};
            ArrayUtils.forEach(KeyProperties.Digest.allToKeymaster(secureKeyGenParameterSpec.getDigests()), new Consumer() { // from class: com.samsung.security.securekeyblob.SecureKeyGenerator$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SecureKeyGenerator.lambda$constructKeyGenerationArguments$3(list, zArr, (Integer) obj);
                }
            });
            if (zArr[0]) {
                return;
            }
            list.add(makeEnum(536871115, KeyProperties.Digest.toKeymaster("SHA-1")));
        }
    }

    public static /* synthetic */ void lambda$constructKeyGenerationArguments$3(List list, boolean[] zArr, Integer num) {
        list.add(makeEnum(536871115, num.intValue()));
        zArr[0] = num.equals(Integer.valueOf(KeyProperties.Digest.toKeymaster("SHA-1"))) | zArr[0];
    }

    public static /* synthetic */ void lambda$constructKeyGenerationArguments$5(List list, Integer num) {
        list.add(makeEnum(536870918, num.intValue()));
    }

    public static /* synthetic */ void lambda$constructKeyGenerationArguments$6(List list, Integer num) {
        list.add(makeEnum(536870917, num.intValue()));
    }

    public final KeyParameter[] constructAttestationArguments(SecureKeyGenParameterSpec secureKeyGenParameterSpec) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(makeBytes(-1879047484, secureKeyGenParameterSpec.getChallenge()));
        if (secureKeyGenParameterSpec.isDeviceAttestation()) {
            arrayList.add(makeBytes(-1879046090, "samsungDeviceIds".getBytes(StandardCharsets.UTF_8)));
        } else {
            arrayList.add(makeBytes(-1879046090, "samsung".getBytes(StandardCharsets.UTF_8)));
        }
        X500Principal certificateSubject = secureKeyGenParameterSpec.getCertificateSubject();
        if (certificateSubject != null && !TextUtils.isEmpty(certificateSubject.getName("RFC1779"))) {
            arrayList.add(makeBytes(-1879046089, certificateSubject.getName("RFC1779").getBytes(StandardCharsets.UTF_8)));
        }
        if (secureKeyGenParameterSpec.isVerifiableIntegrity()) {
            arrayList.add(makeBool(1879050494));
        }
        return (KeyParameter[]) arrayList.toArray(new KeyParameter[arrayList.size()]);
    }

    public static KeyParameter makeInt(int i, int i2) {
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != 805306368 && tagType != 1073741824) {
            throw new IllegalArgumentException("Not an int or repeatable int tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.integer(i2);
        return keyParameter;
    }

    public static KeyParameter makeLong(int i, long j) {
        int tagType = KeymasterDefs.getTagType(i);
        if (tagType != 1342177280 && tagType != -1610612736) {
            throw new IllegalArgumentException("Not a long or repeatable long tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.longInteger(j);
        return keyParameter;
    }

    public static KeyParameter makeBool(int i) {
        if (KeymasterDefs.getTagType(i) != 1879048192) {
            throw new IllegalArgumentException("Not a boolean tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.boolValue(true);
        return keyParameter;
    }

    public static KeyParameter makeBytes(int i, byte[] bArr) {
        if (KeymasterDefs.getTagType(i) != -1879048192) {
            throw new IllegalArgumentException("Not a bytes tag: " + i);
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.blob(bArr);
        return keyParameter;
    }

    public static KeyParameter makeEnum(int i, int i2) {
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        switch (i) {
            case 268435458:
                keyParameter.value = KeyParameterValue.algorithm(i2);
                return keyParameter;
            case 268435466:
                keyParameter.value = KeyParameterValue.ecCurve(i2);
                return keyParameter;
            case 536870913:
                keyParameter.value = KeyParameterValue.keyPurpose(i2);
                return keyParameter;
            case 536870916:
                keyParameter.value = KeyParameterValue.blockMode(i2);
                return keyParameter;
            case 536870917:
            case 536871115:
                keyParameter.value = KeyParameterValue.digest(i2);
                return keyParameter;
            case 536870918:
                keyParameter.value = KeyParameterValue.paddingMode(i2);
                return keyParameter;
            default:
                throw new IllegalArgumentException("Not an enum or repeatable enum tag: " + i);
        }
    }

    public static KeyStoreException getKeyStoreException(int i) {
        if (i <= 0) {
            if (i == -16) {
                return new KeyStoreException(i, "Invalid user authentication validity duration");
            }
            return new KeyStoreException(i, KeymasterDefs.getErrorMessage(i));
        }
        if (i == 2) {
            return new KeyStoreException(i, "User authentication required");
        }
        if (i == 3) {
            return new KeyStoreException(i, "Keystore not initialized");
        }
        if (i == 4) {
            return new KeyStoreException(i, "System error");
        }
        if (i == 6) {
            return new KeyStoreException(i, "Permission denied");
        }
        if (i == 7) {
            return new KeyStoreException(i, "Key not found");
        }
        if (i == 8) {
            return new KeyStoreException(i, "Key blob corrupted");
        }
        if (i == 17) {
            return new KeyStoreException(i, "Key permanently invalidated");
        }
        return new KeyStoreException(i, String.valueOf(i));
    }

    public static X509Certificate[] getCertificates(Collection collection) {
        int i;
        Iterator it = collection.iterator();
        if (!it.hasNext()) {
            Log.e("SecureKeyGenerator", "there is no cert chain byte");
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            byte[] bArr = (byte[]) it.next();
            byteArrayOutputStream.write(bArr, 0, bArr.length);
        }
        Collection certificates = toCertificates(byteArrayOutputStream.toByteArray());
        Iterator it2 = certificates.iterator();
        X509Certificate[] x509CertificateArr = new X509Certificate[certificates.size()];
        while (it2.hasNext()) {
            x509CertificateArr[i] = (X509Certificate) it2.next();
            i++;
        }
        return x509CertificateArr;
    }

    public static Collection toCertificates(byte[] bArr) {
        try {
            return CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            Log.w("SecureKeyGenerator", "Couldn't parse certificates in keystore", e);
            return new ArrayList();
        }
    }

    public static int keySizeAndNameToEcCurve(int i) {
        if (i == 224) {
            return 0;
        }
        if (i == 256) {
            return 1;
        }
        if (i == 384) {
            return 2;
        }
        if (i == 521) {
            return 3;
        }
        throw new IllegalArgumentException("Unsupported EC curve keysize: " + i);
    }

    public static void checkValidKeySize(String str, int i) {
        str.hashCode();
        if (!str.equals("EC")) {
            if (str.equals("RSA")) {
                if (i < 512 || i > 8192) {
                    throw new IllegalArgumentException("RSA key size must be >= 512 and <= 8192");
                }
                return;
            } else {
                throw new IllegalArgumentException("Unsupported algorithm: " + str);
            }
        }
        if (i == 224 || i == 256 || i == 384 || i == 521) {
            return;
        }
        throw new IllegalArgumentException("Unsupported EC key size: " + i + " bits.");
    }
}
