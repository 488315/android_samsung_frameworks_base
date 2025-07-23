package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import javax.crypto.IllegalBlockSizeException;

/* loaded from: classes6.dex */
public class UcmKeyStoreAESCipherSpi extends UcmKeyStoreGenericCipher {
    private ByteArrayOutputStream mStream;

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public int getModulusSize() {
        return 0;
    }

    public UcmKeyStoreAESCipherSpi(int i) {
        super(i);
        this.mStream = new ByteArrayOutputStream();
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public boolean isModeSupported(String str) {
        return KeyProperties.BLOCK_MODE_CBC.equals(str) || "GCM".equals(str);
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public int isPaddingSupported(String str) {
        str.hashCode();
        if (str.equals("ISO9797_M2")) {
            return 4;
        }
        return !str.equals("NOPADDING") ? -1 : 1;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public UcmKeyStoreKey initInternal(Key key) {
        this.mStream.reset();
        return (UcmKeyStoreSecretKey) key;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public void update(byte[] bArr, int i, int i2) {
        this.mStream.write(bArr, i, i2);
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public byte[] doFinalInternal(int i) throws IllegalBlockSizeException {
        try {
            if (this.mStream.size() == 0) {
                throw new IllegalBlockSizeException("Invalid input data");
            }
            return this.mStream.toByteArray();
        } finally {
            this.mStream.reset();
        }
    }
}
