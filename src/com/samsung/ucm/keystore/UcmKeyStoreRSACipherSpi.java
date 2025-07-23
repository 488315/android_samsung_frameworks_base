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

    public UcmKeyStoreRSACipherSpi(int i) {
        super(i);
        this.mIsInputTooLarge = false;
        this.mModulusSizeBytes = 0;
        this.mBuffer = null;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public boolean isModeSupported(String str) {
        return KeyProperties.DIGEST_NONE.equals(str) || KeyProperties.BLOCK_MODE_ECB.equals(str);
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public int isPaddingSupported(String str) {
        str.hashCode();
        if (str.equals("PKCS1PADDING")) {
            return 2;
        }
        return !str.equals("NOPADDING") ? -1 : 1;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public UcmKeyStoreKey initInternal(Key key) {
        UcmKeyStoreRSAPrivateKey ucmKeyStoreRSAPrivateKey = (UcmKeyStoreRSAPrivateKey) key;
        BigInteger modulus = ucmKeyStoreRSAPrivateKey.getModulus();
        if (modulus != null) {
            this.mModulusSizeBytes = (modulus.bitLength() + 7) / 8;
        }
        this.mBufferOffset = 0;
        this.mIsInputTooLarge = false;
        this.mBuffer = new byte[this.mModulusSizeBytes];
        return ucmKeyStoreRSAPrivateKey;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public void update(byte[] bArr, int i, int i2) {
        int i3 = this.mBufferOffset;
        int i4 = i3 + i2;
        byte[] bArr2 = this.mBuffer;
        if (i4 > bArr2.length) {
            this.mIsInputTooLarge = true;
        }
        System.arraycopy(bArr, i, bArr2, i3, i2);
        this.mBufferOffset += i2;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public byte[] doFinalInternal(int i) throws IllegalBlockSizeException {
        byte[] bArr = this.mBuffer;
        if (bArr == null || bArr.length == 0) {
            throw new IllegalBlockSizeException("Invalid input data");
        }
        if (this.mIsInputTooLarge) {
            throw new IllegalBlockSizeException("Input must be under " + this.mBuffer.length + " bytes");
        }
        int i2 = this.mBufferOffset;
        if (i2 != bArr.length) {
            if (i == 1) {
                byte[] bArr2 = new byte[bArr.length];
                System.arraycopy(bArr, 0, bArr2, bArr.length - i2, i2);
                bArr = bArr2;
            } else {
                bArr = Arrays.copyOf(bArr, i2);
            }
        }
        this.mBufferOffset = 0;
        return bArr;
    }

    @Override // com.samsung.ucm.keystore.UcmKeyStoreGenericCipher
    public int getModulusSize() {
        return this.mModulusSizeBytes;
    }
}
