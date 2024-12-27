package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;

import java.io.ByteArrayOutputStream;
import java.security.Key;

import javax.crypto.IllegalBlockSizeException;

public class UcmKeyStoreAESCipherSpi extends UcmKeyStoreGenericCipher {
    private ByteArrayOutputStream mStream;

    public UcmKeyStoreAESCipherSpi(int padding) {
        super(padding);
        this.mStream = new ByteArrayOutputStream();
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public boolean isModeSupported(String mode) {
        return KeyProperties.BLOCK_MODE_CBC.equals(mode) || "GCM".equals(mode);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public int isPaddingSupported(String padding) {
        boolean z;
        switch (padding.hashCode()) {
            case -235307549:
                if (padding.equals("ISO9797_M2")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 907178768:
                if (padding.equals("NOPADDING")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                return 4;
            case true:
                return 1;
            default:
                return -1;
        }
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public UcmKeyStoreKey initInternal(Key key) {
        this.mStream.reset();
        return (UcmKeyStoreSecretKey) key;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public void update(byte[] input, int inputOffset, int inputLen) {
        this.mStream.write(input, inputOffset, inputLen);
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public byte[] doFinalInternal(int padding) throws IllegalBlockSizeException {
        try {
            if (this.mStream.size() == 0) {
                throw new IllegalBlockSizeException("Invalid input data");
            }
            return this.mStream.toByteArray();
        } finally {
            this.mStream.reset();
        }
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public int getModulusSize() {
        return 0;
    }
}
