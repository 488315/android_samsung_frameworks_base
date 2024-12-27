package com.samsung.security.securekeyblob;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.security.keymint.KeyParameter;
import android.hardware.security.keymint.KeyParameterValue;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.security.KeyStoreException;
import android.security.keymaster.KeymasterDefs;
import android.security.securekeygeneration.ISecureKeyGeneration;
import android.security.securekeygeneration.SecureKeyInfo;
import android.util.Log;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.ProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SecureKeyGenerator {
    public ISecureKeyGeneration mSamsungSecurekeyGeneratorBinder;

    public static X509Certificate[] getCertificates(Collection collection) {
        int i;
        Collection<? extends Certificate> arrayList;
        Iterator it = ((ArrayList) collection).iterator();
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
        try {
            arrayList =
                    CertificateFactory.getInstance("X.509")
                            .generateCertificates(
                                    new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        } catch (CertificateException e) {
            Log.w("SecureKeyGenerator", "Couldn't parse certificates in keystore", e);
            arrayList = new ArrayList<>();
        }
        Iterator<? extends Certificate> it2 = arrayList.iterator();
        X509Certificate[] x509CertificateArr = new X509Certificate[arrayList.size()];
        while (it2.hasNext()) {
            x509CertificateArr[i] = (X509Certificate) it2.next();
            i++;
        }
        return x509CertificateArr;
    }

    public static KeyStoreException getKeyStoreException(int i) {
        return i > 0
                ? i != 2
                        ? i != 3
                                ? i != 4
                                        ? i != 6
                                                ? i != 7
                                                        ? i != 8
                                                                ? i != 17
                                                                        ? new KeyStoreException(
                                                                                i,
                                                                                String.valueOf(i))
                                                                        : new KeyStoreException(
                                                                                i,
                                                                                "Key permanently"
                                                                                    + " invalidated")
                                                                : new KeyStoreException(
                                                                        i, "Key blob corrupted")
                                                        : new KeyStoreException(i, "Key not found")
                                                : new KeyStoreException(i, "Permission denied")
                                        : new KeyStoreException(i, "System error")
                                : new KeyStoreException(i, "Keystore not initialized")
                        : new KeyStoreException(i, "User authentication required")
                : i != -16
                        ? new KeyStoreException(i, KeymasterDefs.getErrorMessage(i))
                        : new KeyStoreException(i, "Invalid user authentication validity duration");
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
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i, "Not an enum or repeatable enum tag: "));
        }
    }

    public final SecureKeyResult generateKeyPair(
            SecureKeyGenParameterSpec secureKeyGenParameterSpec) {
        android.hardware.security.keymint.Certificate[] certificateArr;
        byte[] bArr = secureKeyGenParameterSpec.mServiceTAName;
        if (bArr == null) {
            throw new IllegalArgumentException("service name should not be null");
        }
        String str = secureKeyGenParameterSpec.mAlgorithm;
        str.getClass();
        if (!str.equals("EC") && !str.equals("RSA")) {
            throw new InvalidAlgorithmParameterException("Unsupported algorithm: ".concat(str));
        }
        int i = secureKeyGenParameterSpec.mKeySize;
        if (!str.equals("EC")) {
            if (!str.equals("RSA")) {
                throw new IllegalArgumentException("Unsupported algorithm: ".concat(str));
            }
            if (i < 512 || i > 8192) {
                throw new IllegalArgumentException("RSA key size must be >= 512 and <= 8192");
            }
        } else if (i != 224 && i != 256 && i != 384 && i != 521) {
            throw new IllegalArgumentException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "Unsupported EC key size: ", " bits."));
        }
        if (secureKeyGenParameterSpec.mChallenge == null) {
            throw new IllegalArgumentException("challenge should not be null");
        }
        try {
            ArrayList arrayList = new ArrayList();
            SecureKeyInfo handleRemoteExceptionSecureKeyGeneration =
                    handleRemoteExceptionSecureKeyGeneration(
                            new SecureKeyGenerator$$ExternalSyntheticLambda0(
                                    this, secureKeyGenParameterSpec));
            if (handleRemoteExceptionSecureKeyGeneration == null
                    || (certificateArr =
                                    handleRemoteExceptionSecureKeyGeneration.attestedCertificates)
                            == null) {
                throw new NullPointerException("SecureKeyGeneration fail");
            }
            for (android.hardware.security.keymint.Certificate certificate : certificateArr) {
                arrayList.add(certificate.encodedCertificate);
            }
            if (arrayList.size() < 3) {
                throw new ProviderException(
                        "Attestation certificate chain contained "
                                + arrayList.size()
                                + " entries. At least three are required.");
            }
            X509Certificate[] certificates = getCertificates(arrayList);
            byte[] bArr2 = handleRemoteExceptionSecureKeyGeneration.blob;
            SecureKeyResult secureKeyResult = new SecureKeyResult();
            secureKeyResult.mServiceKey = bArr2;
            secureKeyResult.mCertificates = certificates;
            secureKeyResult.mServiceID = bArr;
            return secureKeyResult;
        } catch (KeyStoreException e) {
            if (e.getErrorCode() == -66) {
                throw new DeviceAttestationException(
                        "Failed to generate attestation certificate chain with deviceIds",
                        getKeyStoreException(e.getErrorCode()));
            }
            throw new ProviderException(
                    "Failed to generate attestation certificate chain",
                    getKeyStoreException(e.getErrorCode()));
        }
    }

    public final SecureKeyInfo handleRemoteExceptionSecureKeyGeneration(
            SecureKeyGenerator$$ExternalSyntheticLambda0
                    secureKeyGenerator$$ExternalSyntheticLambda0) {
        ISecureKeyGeneration iSecureKeyGeneration;
        ISecureKeyGeneration iSecureKeyGeneration2;
        synchronized (this) {
            try {
                if (this.mSamsungSecurekeyGeneratorBinder == null) {
                    IBinder checkService =
                            ServiceManager.checkService("android.security.securekeygeneration");
                    int i = ISecureKeyGeneration.Stub.$r8$clinit;
                    if (checkService == null) {
                        iSecureKeyGeneration2 = null;
                    } else {
                        IInterface queryLocalInterface =
                                checkService.queryLocalInterface(
                                        "android.security.securekeygeneration.ISecureKeyGeneration");
                        if (queryLocalInterface == null
                                || !(queryLocalInterface instanceof ISecureKeyGeneration)) {
                            ISecureKeyGeneration.Stub.Proxy proxy =
                                    new ISecureKeyGeneration.Stub.Proxy();
                            proxy.mRemote = checkService;
                            iSecureKeyGeneration2 = proxy;
                        } else {
                            iSecureKeyGeneration2 = (ISecureKeyGeneration) queryLocalInterface;
                        }
                    }
                    this.mSamsungSecurekeyGeneratorBinder = iSecureKeyGeneration2;
                }
                iSecureKeyGeneration = this.mSamsungSecurekeyGeneratorBinder;
            } catch (Throwable th) {
                throw th;
            }
        }
        try {
            return secureKeyGenerator$$ExternalSyntheticLambda0.execute(iSecureKeyGeneration);
        } catch (ServiceSpecificException e) {
            Log.e("SecureKeyGenerator", "KeyStore exception", e);
            throw new KeyStoreException(e.errorCode, "");
        } catch (RemoteException e2) {
            Log.e("SecureKeyGenerator", "Cannot connect to SamsungKeystore daemon.", e2);
            throw new KeyStoreException(4, "");
        }
    }
}
