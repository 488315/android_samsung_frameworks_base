package com.samsung.android.security.keystore;

import android.app.ActivityThread;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.hardware.security.keymint.KeyParameter;
import android.hardware.security.keymint.KeyParameterValue;
import android.os.Build;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.security.KeyStore2;
import android.security.keymaster.KeymasterDefs;
import android.security.keystore.KeyGenParameterSpec;
import android.security.samsungattestation.ISamsungAttestation;
import android.system.keystore2.KeyDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.security.keystore.AttestParameterSpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes2.dex */
public class AttestationUtils {
    public ISamsungAttestation mSamsungAttestationBinder = null;
    public final KeyStore2 mKeyStore = KeyStore2.getInstance();

    public interface checkedRemoteRequest {
        Object execute(ISamsungAttestation iSamsungAttestation);
    }

    public Iterable attestKey(AttestParameterSpec attestParameterSpec) {
        KeyParameter[] constructAttestationArguments = constructAttestationArguments(attestParameterSpec);
        try {
            if (attestParameterSpec.isDeviceAttestation()) {
                return attestDevice(attestParameterSpec);
            }
            return tryAttestation(attestParameterSpec.getKeyGenParameterSpec().getKeystoreAlias(), constructAttestationArguments);
        } catch (DeviceIdAttestationException e) {
            throw new IllegalArgumentException("Incompatible argument detected: " + e.getMessage());
        }
    }

    public Iterable attestDevice(AttestParameterSpec attestParameterSpec) {
        return tryAttestation(attestParameterSpec.getKeyGenParameterSpec().getKeystoreAlias(), constructAttestationArguments(attestParameterSpec));
    }

    public void storeCertificateChain(String str, Iterable iterable) {
        if (!isPrivateKeyEntry(str)) {
            throw new KeyStoreException("Entry exists and is not a trusted certificate");
        }
        if (iterable == null) {
            throw new NullPointerException("iterable == null");
        }
        Iterator it = iterable.iterator();
        if (!it.hasNext()) {
            Log.e("AttestationUtils", "there is no cert chain byte");
            return;
        }
        byte[] bArr = (byte[]) it.next();
        if (it.hasNext()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (it.hasNext()) {
                byte[] bArr2 = (byte[]) it.next();
                byteArrayOutputStream.write(bArr2, 0, bArr2.length);
            }
            try {
                this.mKeyStore.updateSubcomponents(makeKeyDescriptor(str), bArr, byteArrayOutputStream.toByteArray());
            } catch (android.security.KeyStoreException e) {
                e.printStackTrace();
                throw new KeyStoreException(e.getMessage());
            }
        }
    }

    public KeyPair generateKeyPair(String str, byte[] bArr) {
        return generateKeyPair(new AttestParameterSpec.Builder(str, bArr).build());
    }

    public KeyPair generateKeyPair(AttestParameterSpec attestParameterSpec) {
        Iterable attestKey;
        if (attestParameterSpec == null) {
            throw new IllegalArgumentException("AttestParameterSpec is null");
        }
        KeyGenParameterSpec keyGenParameterSpec = attestParameterSpec.getKeyGenParameterSpec();
        String keystoreAlias = keyGenParameterSpec.getKeystoreAlias();
        if (TextUtils.isEmpty(keystoreAlias) || keyGenParameterSpec.getUid() != -1) {
            throw new ProviderException("Cannot generate key pair with empty alias or specified uid.");
        }
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(attestParameterSpec.getAlgorithm(), "AndroidKeyStore");
            keyPairGenerator.initialize(keyGenParameterSpec);
            KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
            if (attestParameterSpec.isDeviceAttestation()) {
                attestKey = attestDevice(attestParameterSpec);
            } else {
                attestKey = attestKey(attestParameterSpec);
            }
            storeCertificateChain(keystoreAlias, attestKey);
            return generateKeyPair;
        } catch (DeviceIdAttestationException | IllegalStateException | InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            throw new ProviderException(e.getMessage());
        }
    }

    public Certificate[] getCertificateChain(String str) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            return keyStore.getCertificateChain(str);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteKey(String str) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            keyStore.deleteEntry(str);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
            throw new KeyStoreException(e.getMessage());
        }
    }

    public final Object handleRemoteExceptionSamsungAttestation(checkedRemoteRequest checkedremoterequest) {
        try {
            return checkedremoterequest.execute(getSamsungAttestationService());
        } catch (RemoteException e) {
            Log.e("AttestationUtils", "Cannot connect to SamsungKeystore daemon.", e);
            throw new android.security.KeyStoreException(4, "");
        } catch (ServiceSpecificException e2) {
            Log.e("AttestationUtils", "KeyStore exception", e2);
            throw new android.security.KeyStoreException(e2.errorCode, "");
        }
    }

    public final synchronized ISamsungAttestation getSamsungAttestationService() {
        if (this.mSamsungAttestationBinder == null) {
            this.mSamsungAttestationBinder = ISamsungAttestation.Stub.asInterface(ServiceManager.checkService("android.security.samsungattestation"));
        }
        return this.mSamsungAttestationBinder;
    }

    public final KeyParameter[] constructAttestationArguments(AttestParameterSpec attestParameterSpec) {
        if (attestParameterSpec == null) {
            throw new IllegalArgumentException("constructAttestationArguments : AttestParameterSpec is null");
        }
        String keystoreAlias = attestParameterSpec.getKeyGenParameterSpec().getKeystoreAlias();
        byte[] challenge = attestParameterSpec.getChallenge();
        X500Principal certificateSubject = attestParameterSpec.getCertificateSubject();
        if (TextUtils.isEmpty(keystoreAlias)) {
            throw new NullPointerException("constructAttestationArguments : alias == null");
        }
        if (challenge == null) {
            throw new IllegalArgumentException("constructAttestationArguments : The challenge cannot be null");
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(makeBytes(-1879047484, attestParameterSpec.getChallenge()));
        if (attestParameterSpec.isDeviceAttestation()) {
            arrayList.add(makeBytes(-1879046090, "samsungDeviceIds".getBytes(StandardCharsets.UTF_8)));
        } else {
            arrayList.add(makeBytes(-1879046090, "samsung".getBytes(StandardCharsets.UTF_8)));
        }
        if (certificateSubject != null && !TextUtils.isEmpty(certificateSubject.getName("RFC1779"))) {
            arrayList.add(makeBytes(-1879046089, certificateSubject.getName("RFC1779").getBytes(StandardCharsets.UTF_8)));
        }
        if (attestParameterSpec.isVerifiableIntegrity()) {
            arrayList.add(makeBool(1879050494));
            Application currentApplication = ActivityThread.currentApplication();
            if (currentApplication != null) {
                String packageName = attestParameterSpec.getPackageName();
                if (TextUtils.isEmpty(packageName)) {
                    packageName = currentApplication.getPackageName();
                }
                byte[] bytesAuthenticatePackage = getBytesAuthenticatePackage(packageName, currentApplication);
                if (bytesAuthenticatePackage != null) {
                    arrayList.add(makeBytes(-1879045889, bytesAuthenticatePackage));
                } else {
                    Log.w("AttestationUtils", "constructAttestationArguments : Auth package byte is null");
                }
            } else {
                Log.w("AttestationUtils", "constructAttestationArguments : could not found application");
            }
        }
        if (attestParameterSpec.isDevicePropertiesAttestationIncluded()) {
            arrayList.add(makeBytes(-1879047482, Build.BRAND.getBytes(StandardCharsets.UTF_8)));
            arrayList.add(makeBytes(-1879047481, Build.DEVICE.getBytes(StandardCharsets.UTF_8)));
            arrayList.add(makeBytes(-1879047480, Build.PRODUCT.getBytes(StandardCharsets.UTF_8)));
            arrayList.add(makeBytes(-1879047476, Build.MANUFACTURER.getBytes(StandardCharsets.UTF_8)));
            arrayList.add(makeBytes(-1879047475, Build.MODEL.getBytes(StandardCharsets.UTF_8)));
        }
        return (KeyParameter[]) arrayList.toArray(new KeyParameter[arrayList.size()]);
    }

    public final Collection tryAttestation(String str, final KeyParameter[] keyParameterArr) {
        ArrayList arrayList = new ArrayList();
        try {
            final KeyDescriptor makeKeyDescriptor = makeKeyDescriptor(str);
            android.hardware.security.keymint.Certificate[] certificateArr = (android.hardware.security.keymint.Certificate[]) handleRemoteExceptionSamsungAttestation(new checkedRemoteRequest() { // from class: com.samsung.android.security.keystore.AttestationUtils$$ExternalSyntheticLambda0
                @Override // com.samsung.android.security.keystore.AttestationUtils.checkedRemoteRequest
                public final Object execute(ISamsungAttestation iSamsungAttestation) {
                    android.hardware.security.keymint.Certificate[] lambda$tryAttestation$0;
                    lambda$tryAttestation$0 = AttestationUtils.lambda$tryAttestation$0(makeKeyDescriptor, keyParameterArr, iSamsungAttestation);
                    return lambda$tryAttestation$0;
                }
            });
            if (certificateArr == null) {
                throw new NullPointerException("chain == null");
            }
            for (android.hardware.security.keymint.Certificate certificate : certificateArr) {
                arrayList.add(certificate.encodedCertificate);
            }
            if (arrayList.size() >= 3) {
                return arrayList;
            }
            throw new ProviderException("Attestation certificate chain contained " + arrayList.size() + " entries. At least three are required.");
        } catch (android.security.KeyStoreException e) {
            if (e.getErrorCode() == -66) {
                throw new DeviceIdAttestationException("Failed to generate attestation certificate chain with deviceId", getKeyStoreException(e.getErrorCode()));
            }
            throw new ProviderException("Failed to generate attestation certificate chain", getKeyStoreException(e.getErrorCode()));
        }
    }

    public static /* synthetic */ android.hardware.security.keymint.Certificate[] lambda$tryAttestation$0(KeyDescriptor keyDescriptor, KeyParameter[] keyParameterArr, ISamsungAttestation iSamsungAttestation) {
        return iSamsungAttestation.attestKey(keyDescriptor, keyParameterArr);
    }

    public final boolean isPrivateKeyEntry(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("alias == null");
        }
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            return keyStore.isKeyEntry(str);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
            throw new KeyStoreException(e.getMessage());
        }
    }

    public final PublicKey[] getPackagePublicKeys(String str, Application application) {
        try {
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(str, 134217728);
            if (packageInfo == null) {
                Log.w("AttestationUtils", "pkgInfo is null");
                return null;
            }
            Signature[] apkContentsSigners = packageInfo.signingInfo.getApkContentsSigners();
            PublicKey[] publicKeyArr = new PublicKey[apkContentsSigners.length];
            int i = 0;
            for (Signature signature : apkContentsSigners) {
                try {
                    publicKeyArr[i] = signature.getPublicKey();
                    i++;
                } catch (CertificateException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return publicKeyArr;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final byte[] sha256(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("msg == null");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new ProviderException("NoSuchAlgorithmException : " + e.getMessage());
        }
    }

    public final byte[] getBytesAuthenticatePackage(String str, Application application) {
        if (TextUtils.isEmpty(str)) {
            Log.w("AttestationUtils", "packageName is null");
            return null;
        }
        PublicKey[] packagePublicKeys = getPackagePublicKeys(str, application);
        if (packagePublicKeys == null) {
            Log.w("AttestationUtils", "pubKeys is null");
            return str.getBytes(StandardCharsets.UTF_8);
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(bytes, 0, bytes.length);
        for (PublicKey publicKey : packagePublicKeys) {
            byte[] bytes2 = XmlUtils.STRING_ARRAY_SEPARATOR.getBytes(StandardCharsets.UTF_8);
            byteArrayOutputStream.write(bytes2, 0, bytes2.length);
            byte[] encode = Base64.getEncoder().encode(sha256(publicKey.toString()));
            byteArrayOutputStream.write(encode, 0, encode.length);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public final KeyDescriptor makeKeyDescriptor(String str) {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.nspace = -1L;
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        return keyDescriptor;
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

    public static android.security.KeyStoreException getKeyStoreException(int i) {
        if (i <= 0) {
            if (i == -16) {
                return new android.security.KeyStoreException(i, "Invalid user authentication validity duration");
            }
            return new android.security.KeyStoreException(i, KeymasterDefs.getErrorMessage(i));
        }
        if (i == 2) {
            return new android.security.KeyStoreException(i, "User authentication required");
        }
        if (i == 3) {
            return new android.security.KeyStoreException(i, "Keystore not initialized");
        }
        if (i == 4) {
            return new android.security.KeyStoreException(i, "System error");
        }
        if (i == 6) {
            return new android.security.KeyStoreException(i, "Permission denied");
        }
        if (i == 7) {
            return new android.security.KeyStoreException(i, "Key not found");
        }
        if (i == 8) {
            return new android.security.KeyStoreException(i, "Key blob corrupted");
        }
        if (i == 17) {
            return new android.security.KeyStoreException(i, "Key permanently invalidated");
        }
        return new android.security.KeyStoreException(i, String.valueOf(i));
    }
}
