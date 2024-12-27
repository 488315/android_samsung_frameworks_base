package com.samsung.ucm.keystore;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes6.dex */
public abstract class UcmKeyStoreGenericCipher {
    private int mPadding;
    private UcmKeyStoreKey mUcmKey;

    public abstract byte[] doFinalInternal(int i) throws IllegalBlockSizeException;

    public abstract int getModulusSize();

    public abstract UcmKeyStoreKey initInternal(Key key);

    public abstract boolean isModeSupported(String str);

    public abstract int isPaddingSupported(String str);

    public abstract void update(byte[] bArr, int i, int i2);

    public UcmKeyStoreGenericCipher(int padding) {
        this.mPadding = padding;
    }

    public void setMode(String mode) throws NoSuchAlgorithmException {
        if (isModeSupported(toUpperCase(mode))) {
        } else {
            throw new NoSuchAlgorithmException("Mode not supported: " + mode);
        }
    }

    public void setPadding(String padding) throws NoSuchPaddingException {
        this.mPadding = isPaddingSupported(toUpperCase(padding));
        if (this.mPadding != -1) {
        } else {
            throw new NoSuchPaddingException("Padding not supported: " + padding);
        }
    }

    public void init(Key key) throws InvalidKeyException {
        if ((key instanceof UcmKeyStorePrivateKey) || (key instanceof UcmKeyStoreSecretKey)) {
            this.mUcmKey = initInternal(key);
            return;
        }
        throw new InvalidKeyException("Invalid Key");
    }

    public byte[] doFinal() throws IllegalBlockSizeException {
        return doFinalInternal(this.mPadding);
    }

    public UcmKeyStoreKey getKey() {
        return this.mUcmKey;
    }

    private String toUpperCase(String value) {
        return value.toUpperCase(Locale.ROOT);
    }
}
