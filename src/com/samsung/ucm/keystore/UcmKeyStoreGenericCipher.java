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

    public UcmKeyStoreGenericCipher(int i) {
        this.mPadding = i;
    }

    public void setMode(String str) throws NoSuchAlgorithmException {
        if (isModeSupported(toUpperCase(str))) {
            return;
        }
        throw new NoSuchAlgorithmException("Mode not supported: " + str);
    }

    public void setPadding(String str) throws NoSuchPaddingException {
        int isPaddingSupported = isPaddingSupported(toUpperCase(str));
        this.mPadding = isPaddingSupported;
        if (isPaddingSupported != -1) {
            return;
        }
        throw new NoSuchPaddingException("Padding not supported: " + str);
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

    private String toUpperCase(String str) {
        return str.toUpperCase(Locale.ROOT);
    }
}
