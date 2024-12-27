package com.samsung.android.security.keystore;

import android.app.ActivityThread;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.security.keymint.KeyParameter;
import android.hardware.security.keymint.KeyParameterValue;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
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

public final class AttestationUtils {
    public ISamsungAttestation mSamsungAttestationBinder = null;
    public final KeyStore2 mKeyStore = KeyStore2.getInstance();

    public static KeyParameter[] constructAttestationArguments(
            AttestParameterSpec attestParameterSpec) {
        String keystoreAlias = attestParameterSpec.mSpec.getKeystoreAlias();
        X500Principal x500Principal = attestParameterSpec.mCertificateSubject;
        if (TextUtils.isEmpty(keystoreAlias)) {
            throw new NullPointerException("constructAttestationArguments : alias == null");
        }
        byte[] bArr = attestParameterSpec.mChallenge;
        if (bArr == null) {
            throw new IllegalArgumentException(
                    "constructAttestationArguments : The challenge cannot be null");
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(makeBytes(-1879047484, bArr));
        if (attestParameterSpec.mDeviceAttestation) {
            arrayList.add(
                    makeBytes(-1879046090, "samsungDeviceIds".getBytes(StandardCharsets.UTF_8)));
        } else {
            arrayList.add(makeBytes(-1879046090, "samsung".getBytes(StandardCharsets.UTF_8)));
        }
        if (x500Principal != null && !TextUtils.isEmpty(x500Principal.getName("RFC1779"))) {
            arrayList.add(
                    makeBytes(
                            -1879046089,
                            x500Principal.getName("RFC1779").getBytes(StandardCharsets.UTF_8)));
        }
        if (attestParameterSpec.mVerifiableIntegrity) {
            arrayList.add(makeBool(1879050494));
            Application currentApplication = ActivityThread.currentApplication();
            if (currentApplication != null) {
                String str = attestParameterSpec.mPackageName;
                if (TextUtils.isEmpty(str)) {
                    str = currentApplication.getPackageName();
                }
                PublicKey[] publicKeyArr = null;
                byte[] byteArray = null;
                publicKeyArr = null;
                publicKeyArr = null;
                if (TextUtils.isEmpty(str)) {
                    Log.w("AttestationUtils", "packageName is null");
                } else {
                    try {
                        PackageInfo packageInfo =
                                currentApplication
                                        .getPackageManager()
                                        .getPackageInfo(str, 134217728);
                        if (packageInfo == null) {
                            Log.w("AttestationUtils", "pkgInfo is null");
                        } else {
                            Signature[] apkContentsSigners =
                                    packageInfo.signingInfo.getApkContentsSigners();
                            PublicKey[] publicKeyArr2 = new PublicKey[apkContentsSigners.length];
                            int i = 0;
                            for (Signature signature : apkContentsSigners) {
                                try {
                                    publicKeyArr2[i] = signature.getPublicKey();
                                    i++;
                                } catch (CertificateException e) {
                                    e.printStackTrace();
                                }
                            }
                            publicKeyArr = publicKeyArr2;
                        }
                    } catch (PackageManager.NameNotFoundException e2) {
                        e2.printStackTrace();
                    }
                    if (publicKeyArr == null) {
                        Log.w("AttestationUtils", "pubKeys is null");
                        byteArray = str.getBytes(StandardCharsets.UTF_8);
                    } else {
                        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byteArrayOutputStream.write(bytes, 0, bytes.length);
                        for (PublicKey publicKey : publicKeyArr) {
                            Charset charset = StandardCharsets.UTF_8;
                            byte[] bytes2 = ":".getBytes(charset);
                            byteArrayOutputStream.write(bytes2, 0, bytes2.length);
                            Base64.Encoder encoder = Base64.getEncoder();
                            String publicKey2 = publicKey.toString();
                            if (TextUtils.isEmpty(publicKey2)) {
                                throw new NullPointerException("msg == null");
                            }
                            try {
                                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                                messageDigest.update(publicKey2.getBytes(charset));
                                byte[] encode = encoder.encode(messageDigest.digest());
                                byteArrayOutputStream.write(encode, 0, encode.length);
                            } catch (NoSuchAlgorithmException e3) {
                                throw new ProviderException(
                                        "NoSuchAlgorithmException : " + e3.getMessage());
                            }
                        }
                        byteArray = byteArrayOutputStream.toByteArray();
                    }
                }
                if (byteArray != null) {
                    arrayList.add(makeBytes(-1879045889, byteArray));
                } else {
                    Log.w(
                            "AttestationUtils",
                            "constructAttestationArguments : Auth package byte is null");
                }
            } else {
                Log.w(
                        "AttestationUtils",
                        "constructAttestationArguments : could not found application");
            }
        }
        if (attestParameterSpec.mDevicePropertiesAttestationIncluded) {
            String str2 = Build.BRAND;
            Charset charset2 = StandardCharsets.UTF_8;
            arrayList.add(makeBytes(-1879047482, str2.getBytes(charset2)));
            arrayList.add(makeBytes(-1879047481, Build.DEVICE.getBytes(charset2)));
            arrayList.add(makeBytes(-1879047480, Build.PRODUCT.getBytes(charset2)));
            arrayList.add(makeBytes(-1879047476, Build.MANUFACTURER.getBytes(charset2)));
            arrayList.add(makeBytes(-1879047475, Build.MODEL.getBytes(charset2)));
        }
        if (attestParameterSpec.mSAKUidRequired) {
            Log.w("AttestationUtils", "constructAttestationArguments : set SAK UID required");
            arrayList.add(makeBool(1879050692));
        }
        String str3 = attestParameterSpec.mExtendedKeyUsage;
        if (str3 != null) {
            Log.w(
                    "AttestationUtils",
                    "constructAttestationArguments : EKU is setted with ".concat(str3));
            arrayList.add(makeBytes(-1879046087, str3.getBytes(StandardCharsets.UTF_8)));
        }
        return (KeyParameter[]) arrayList.toArray(new KeyParameter[arrayList.size()]);
    }

    public static void deleteKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            keyStore.deleteEntry("KnoxTestKey");
        } catch (IOException
                | KeyStoreException
                | NoSuchAlgorithmException
                | CertificateException e) {
            e.printStackTrace();
            throw new KeyStoreException(e.getMessage());
        }
    }

    public static Certificate[] getCertificateChain(String str) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            return keyStore.getCertificateChain(str);
        } catch (IOException
                | KeyStoreException
                | NoSuchAlgorithmException
                | CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static android.security.KeyStoreException getKeyStoreException(int i) {
        return i > 0
                ? i != 2
                        ? i != 3
                                ? i != 4
                                        ? i != 6
                                                ? i != 7
                                                        ? i != 8
                                                                ? i != 17
                                                                        ? new android.security
                                                                                .KeyStoreException(
                                                                                i,
                                                                                String.valueOf(i))
                                                                        : new android.security
                                                                                .KeyStoreException(
                                                                                i,
                                                                                "Key permanently"
                                                                                    + " invalidated")
                                                                : new android.security
                                                                        .KeyStoreException(
                                                                        i, "Key blob corrupted")
                                                        : new android.security.KeyStoreException(
                                                                i, "Key not found")
                                                : new android.security.KeyStoreException(
                                                        i, "Permission denied")
                                        : new android.security.KeyStoreException(i, "System error")
                                : new android.security.KeyStoreException(
                                        i, "Keystore not initialized")
                        : new android.security.KeyStoreException(i, "User authentication required")
                : i != -16
                        ? new android.security.KeyStoreException(
                                i, KeymasterDefs.getErrorMessage(i))
                        : new android.security.KeyStoreException(
                                i, "Invalid user authentication validity duration");
    }

    public static KeyParameter makeBool(int i) {
        if (KeymasterDefs.getTagType(i) != 1879048192) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Not a boolean tag: "));
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.boolValue(true);
        return keyParameter;
    }

    public static KeyParameter makeBytes(int i, byte[] bArr) {
        if (KeymasterDefs.getTagType(i) != -1879048192) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Not a bytes tag: "));
        }
        KeyParameter keyParameter = new KeyParameter();
        keyParameter.tag = i;
        keyParameter.value = KeyParameterValue.blob(bArr);
        return keyParameter;
    }

    public final Iterable attestKey(AttestParameterSpec attestParameterSpec) {
        KeyParameter[] constructAttestationArguments =
                constructAttestationArguments(attestParameterSpec);
        try {
            if (!attestParameterSpec.mDeviceAttestation) {
                return tryAttestation(
                        attestParameterSpec.mSpec.getKeystoreAlias(),
                        constructAttestationArguments);
            }
            return tryAttestation(
                    attestParameterSpec.mSpec.getKeystoreAlias(),
                    constructAttestationArguments(attestParameterSpec));
        } catch (DeviceIdAttestationException e) {
            throw new IllegalArgumentException("Incompatible argument detected: " + e.getMessage());
        }
    }

    public final KeyPair generateKeyPair(AttestParameterSpec attestParameterSpec) {
        Iterable attestKey;
        KeyGenParameterSpec keyGenParameterSpec = attestParameterSpec.mSpec;
        String keystoreAlias = keyGenParameterSpec.getKeystoreAlias();
        if (TextUtils.isEmpty(keystoreAlias) || keyGenParameterSpec.getUid() != -1) {
            throw new ProviderException(
                    "Cannot generate key pair with empty alias or specified uid.");
        }
        try {
            KeyPairGenerator keyPairGenerator =
                    KeyPairGenerator.getInstance(attestParameterSpec.mAlgorithm, "AndroidKeyStore");
            keyPairGenerator.initialize(keyGenParameterSpec);
            KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
            if (attestParameterSpec.mDeviceAttestation) {
                attestKey =
                        tryAttestation(
                                attestParameterSpec.mSpec.getKeystoreAlias(),
                                constructAttestationArguments(attestParameterSpec));
            } else {
                attestKey = attestKey(attestParameterSpec);
            }
            storeCertificateChain(keystoreAlias, attestKey);
            return generateKeyPair;
        } catch (DeviceIdAttestationException
                | IllegalStateException
                | InvalidAlgorithmParameterException
                | KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException e) {
            e.printStackTrace();
            throw new ProviderException(e.getMessage());
        }
    }

    public final KeyPair generateKeyPair(String str, byte[] bArr) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("alias must not be empty");
        }
        if (bArr != null) {
            return generateKeyPair(
                    new AttestParameterSpec(
                            bArr,
                            false,
                            false,
                            new KeyGenParameterSpec.Builder(str, 4)
                                    .setDigests(
                                            "NONE", "SHA-1", "SHA-224", "SHA-256", "SHA-384",
                                            "SHA-512")
                                    .build()));
        }
        throw new NullPointerException("challenge == null");
    }

    public final Object handleRemoteExceptionSamsungAttestation(
            AttestationUtils$$ExternalSyntheticLambda0 attestationUtils$$ExternalSyntheticLambda0) {
        ISamsungAttestation iSamsungAttestation;
        ISamsungAttestation iSamsungAttestation2;
        synchronized (this) {
            try {
                if (this.mSamsungAttestationBinder == null) {
                    IBinder checkService =
                            ServiceManager.checkService("android.security.samsungattestation");
                    int i = ISamsungAttestation.Stub.$r8$clinit;
                    if (checkService == null) {
                        iSamsungAttestation2 = null;
                    } else {
                        IInterface queryLocalInterface =
                                checkService.queryLocalInterface(
                                        "android.security.samsungattestation.ISamsungAttestation");
                        if (queryLocalInterface == null
                                || !(queryLocalInterface instanceof ISamsungAttestation)) {
                            ISamsungAttestation.Stub.Proxy proxy =
                                    new ISamsungAttestation.Stub.Proxy();
                            proxy.mRemote = checkService;
                            iSamsungAttestation2 = proxy;
                        } else {
                            iSamsungAttestation2 = (ISamsungAttestation) queryLocalInterface;
                        }
                    }
                    this.mSamsungAttestationBinder = iSamsungAttestation2;
                }
                iSamsungAttestation = this.mSamsungAttestationBinder;
            } catch (Throwable th) {
                throw th;
            }
        }
        try {
            return attestationUtils$$ExternalSyntheticLambda0.execute(iSamsungAttestation);
        } catch (ServiceSpecificException e) {
            Log.e("AttestationUtils", "KeyStore exception", e);
            throw new android.security.KeyStoreException(e.errorCode, "");
        } catch (RemoteException e2) {
            Log.e("AttestationUtils", "Cannot connect to SamsungKeystore daemon.", e2);
            throw new android.security.KeyStoreException(4, "");
        }
    }

    public final void storeCertificateChain(String str, Iterable iterable) {
        if (TextUtils.isEmpty(str)) {
            throw new NullPointerException("alias == null");
        }
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (!keyStore.isKeyEntry(str)) {
                throw new KeyStoreException("Entry exists and is not a trusted certificate");
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
                    KeyDescriptor keyDescriptor = new KeyDescriptor();
                    keyDescriptor.domain = 0;
                    keyDescriptor.nspace = -1L;
                    keyDescriptor.alias = str;
                    keyDescriptor.blob = null;
                    this.mKeyStore.updateSubcomponents(
                            keyDescriptor, bArr, byteArrayOutputStream.toByteArray());
                } catch (android.security.KeyStoreException e) {
                    e.printStackTrace();
                    throw new KeyStoreException(e.getMessage());
                }
            }
        } catch (IOException
                | KeyStoreException
                | NoSuchAlgorithmException
                | CertificateException e2) {
            e2.printStackTrace();
            throw new KeyStoreException(e2.getMessage());
        }
    }

    public final Collection tryAttestation(String str, KeyParameter[] keyParameterArr) {
        ArrayList arrayList = new ArrayList();
        try {
            KeyDescriptor keyDescriptor = new KeyDescriptor();
            keyDescriptor.domain = 0;
            keyDescriptor.nspace = -1L;
            keyDescriptor.alias = str;
            keyDescriptor.blob = null;
            android.hardware.security.keymint.Certificate[] certificateArr =
                    (android.hardware.security.keymint.Certificate[])
                            handleRemoteExceptionSamsungAttestation(
                                    new AttestationUtils$$ExternalSyntheticLambda0(
                                            keyDescriptor, keyParameterArr));
            if (certificateArr == null) {
                throw new NullPointerException("chain == null");
            }
            for (android.hardware.security.keymint.Certificate certificate : certificateArr) {
                arrayList.add(certificate.encodedCertificate);
            }
            if (arrayList.size() >= 3) {
                return arrayList;
            }
            throw new ProviderException(
                    "Attestation certificate chain contained "
                            + arrayList.size()
                            + " entries. At least three are required.");
        } catch (android.security.KeyStoreException e) {
            if (e.getErrorCode() == -66) {
                throw new DeviceIdAttestationException(
                        "Failed to generate attestation certificate chain with deviceId",
                        getKeyStoreException(e.getErrorCode()));
            }
            throw new ProviderException(
                    "Failed to generate attestation certificate chain",
                    getKeyStoreException(e.getErrorCode()));
        }
    }
}
