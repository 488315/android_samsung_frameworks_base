package com.samsung.ucm.keystore;

import android.security.keystore.KeyProperties;

import java.math.BigInteger;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.IllegalBlockSizeException;

/* loaded from: classes6.dex */
public class UcmKeyStoreRSACipherSpi extends UcmKeyStoreGenericCipher {
    private byte[] mBuffer;
    private int mBufferOffset;
    private boolean mIsInputTooLarge;
    private int mModulusSizeBytes;

    public UcmKeyStoreRSACipherSpi(int padding) {
        super(padding);
        this.mIsInputTooLarge = false;
        this.mModulusSizeBytes = 0;
        this.mBuffer = null;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public boolean isModeSupported(String mode) {
        return KeyProperties.DIGEST_NONE.equals(mode) || KeyProperties.BLOCK_MODE_ECB.equals(mode);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public int isPaddingSupported(String padding) {
        boolean z;
        switch (padding.hashCode()) {
            case -457047221:
                if (padding.equals("PKCS1PADDING")) {
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
                return 2;
            case true:
                return 1;
            default:
                return -1;
        }
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public UcmKeyStoreKey initInternal(Key key) {
        UcmKeyStoreRSAPrivateKey ucmKey = (UcmKeyStoreRSAPrivateKey) key;
        BigInteger modulus = ucmKey.getModulus();
        if (modulus != null) {
            this.mModulusSizeBytes = (modulus.bitLength() + 7) / 8;
        }
        this.mBufferOffset = 0;
        this.mIsInputTooLarge = false;
        this.mBuffer = new byte[this.mModulusSizeBytes];
        return ucmKey;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public void update(byte[] input, int inputOffset, int inputLen) {
        if (this.mBufferOffset + inputLen > this.mBuffer.length) {
            this.mIsInputTooLarge = true;
        }
        System.arraycopy(input, inputOffset, this.mBuffer, this.mBufferOffset, inputLen);
        this.mBufferOffset += inputLen;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public byte[] doFinalInternal(int padding) throws IllegalBlockSizeException {
        byte[] retBuffer;
        if (this.mBuffer == null || this.mBuffer.length == 0) {
            throw new IllegalBlockSizeException("Invalid input data");
        }
        if (this.mIsInputTooLarge) {
            throw new IllegalBlockSizeException(
                    "Input must be under " + this.mBuffer.length + " bytes");
        }
        if (this.mBufferOffset != this.mBuffer.length) {
            if (padding == 1) {
                retBuffer = new byte[this.mBuffer.length];
                System.arraycopy(
                        this.mBuffer,
                        0,
                        retBuffer,
                        this.mBuffer.length - this.mBufferOffset,
                        this.mBufferOffset);
            } else {
                byte[] retBuffer2 = this.mBuffer;
                retBuffer = Arrays.copyOf(retBuffer2, this.mBufferOffset);
            }
        } else {
            retBuffer = this.mBuffer;
        }
        this.mBufferOffset = 0;
        return retBuffer;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public int getModulusSize() {
        return this.mModulusSizeBytes;
    }
}
