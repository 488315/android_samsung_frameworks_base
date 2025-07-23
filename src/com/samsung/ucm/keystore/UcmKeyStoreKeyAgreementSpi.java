package com.samsung.ucm.keystore;

import android.os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.security.keystore.KeyProperties;
import android.util.Log;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes6.dex */
public class UcmKeyStoreKeyAgreementSpi extends KeyAgreementSpi {
    private static final String TAG = "UcmKeyStoreKeyAgreementSpi";
    private final String mAlgorithm;
    private UcmKeyStorePrivateKey mPrivateKey;
    private PublicKey mPublicKey;

    UcmKeyStoreKeyAgreementSpi(String str) {
        this.mAlgorithm = str;
    }

    @Override // javax.crypto.KeyAgreementSpi
    public void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (!(key instanceof UcmKeyStorePrivateKey)) {
            throw new InvalidKeyException("Only Ucm KeyStore private keys supported. Key: " + key);
        }
        this.mPrivateKey = (UcmKeyStorePrivateKey) key;
    }

    @Override // javax.crypto.KeyAgreementSpi
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            throw new InvalidAlgorithmParameterException("Unsupported algorithm parameters: " + algorithmParameterSpec);
        }
        engineInit(key, secureRandom);
    }

    @Override // javax.crypto.KeyAgreementSpi
    public Key engineDoPhase(Key key, boolean z) throws InvalidKeyException, IllegalStateException {
        if (key == null) {
            throw new InvalidKeyException("key == null");
        }
        if (!(key instanceof PublicKey)) {
            throw new InvalidKeyException("Only public keys supported. Key: " + key);
        }
        if (!z) {
            throw new IllegalStateException("Only one other party supported. lastPhase must be set to true.");
        }
        this.mPublicKey = (PublicKey) key;
        return null;
    }

    @Override // javax.crypto.KeyAgreementSpi
    public byte[] engineGenerateSecret() throws IllegalStateException {
        IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
        if (service == null) {
            Log.e(TAG, "Failed to connect UCM service");
            return null;
        }
        try {
            return service.keyAgreement(this.mPrivateKey.getAlias(), this.mAlgorithm, this.mPublicKey.getEncoded());
        } catch (RemoteException e) {
            Log.e(TAG, "Remote Exception " + e);
            return null;
        }
    }

    @Override // javax.crypto.KeyAgreementSpi
    public int engineGenerateSecret(byte[] bArr, int i) throws IllegalStateException, ShortBufferException {
        byte[] engineGenerateSecret = engineGenerateSecret();
        if (engineGenerateSecret == null) {
            throw new IllegalStateException("Failed to generate secret");
        }
        if (engineGenerateSecret.length > bArr.length - i) {
            throw new ShortBufferException("Needed: " + engineGenerateSecret.length);
        }
        System.arraycopy(engineGenerateSecret, 0, bArr, i, engineGenerateSecret.length);
        return engineGenerateSecret.length;
    }

    @Override // javax.crypto.KeyAgreementSpi
    public SecretKey engineGenerateSecret(String str) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        return new SecretKeySpec(engineGenerateSecret(), str);
    }

    public static class ECDH extends UcmKeyStoreKeyAgreementSpi {
        public ECDH() {
            super("ECDH");
        }
    }

    public static class XDH extends UcmKeyStoreKeyAgreementSpi {
        public XDH() {
            super(KeyProperties.KEY_ALGORITHM_XDH);
        }
    }
}
