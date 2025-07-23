package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.internal.org.bouncycastle.crypto.Wrapper;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.internal.org.bouncycastle.util.Arrays;

/* loaded from: classes5.dex */
public class RFC3394WrapEngine implements Wrapper {
    private static final byte[] DEFAULT_IV = {-90, -90, -90, -90, -90, -90, -90, -90};
    private final BlockCipher engine;
    private boolean forWrapping;
    private final byte[] iv;
    private KeyParameter param;
    private final boolean wrapCipherMode;

    public RFC3394WrapEngine(BlockCipher blockCipher) {
        this(blockCipher, false);
    }

    public RFC3394WrapEngine(BlockCipher blockCipher, boolean z) {
        this.iv = new byte[8];
        this.param = null;
        this.forWrapping = true;
        this.engine = blockCipher;
        this.wrapCipherMode = !z;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public void init(boolean z, CipherParameters cipherParameters) {
        this.forWrapping = z;
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        if (cipherParameters instanceof KeyParameter) {
            this.param = (KeyParameter) cipherParameters;
            System.arraycopy(DEFAULT_IV, 0, this.iv, 0, 8);
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            byte[] iv = parametersWithIV.getIV();
            if (iv.length != 8) {
                throw new IllegalArgumentException("IV not equal to 8");
            }
            this.param = (KeyParameter) parametersWithIV.getParameters();
            System.arraycopy(iv, 0, this.iv, 0, 8);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public String getAlgorithmName() {
        return this.engine.getAlgorithmName();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public byte[] wrap(byte[] bArr, int i, int i2) {
        if (!this.forWrapping) {
            throw new IllegalStateException("not set for wrapping");
        }
        if (i2 < 8) {
            throw new DataLengthException("wrap data must be at least 8 bytes");
        }
        int i3 = i2 / 8;
        if (i3 * 8 != i2) {
            throw new DataLengthException("wrap data must be a multiple of 8 bytes");
        }
        this.engine.init(this.wrapCipherMode, this.param);
        byte[] bArr2 = this.iv;
        byte[] bArr3 = new byte[bArr2.length + i2];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, i, bArr3, this.iv.length, i2);
        if (i3 == 1) {
            this.engine.processBlock(bArr3, 0, bArr3, 0);
            return bArr3;
        }
        byte[] bArr4 = new byte[this.iv.length + 8];
        for (int i4 = 0; i4 != 6; i4++) {
            for (int i5 = 1; i5 <= i3; i5++) {
                System.arraycopy(bArr3, 0, bArr4, 0, this.iv.length);
                int i6 = i5 * 8;
                System.arraycopy(bArr3, i6, bArr4, this.iv.length, 8);
                this.engine.processBlock(bArr4, 0, bArr4, 0);
                int i7 = (i3 * i4) + i5;
                int i8 = 1;
                while (i7 != 0) {
                    int length = this.iv.length - i8;
                    bArr4[length] = (byte) (((byte) i7) ^ bArr4[length]);
                    i7 >>>= 8;
                    i8++;
                }
                System.arraycopy(bArr4, 0, bArr3, 0, 8);
                System.arraycopy(bArr4, 8, bArr3, i6, 8);
            }
        }
        return bArr3;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Wrapper
    public byte[] unwrap(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        if (this.forWrapping) {
            throw new IllegalStateException("not set for unwrapping");
        }
        if (i2 < 16) {
            throw new InvalidCipherTextException("unwrap data too short");
        }
        int i3 = i2 / 8;
        if (i3 * 8 != i2) {
            throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
        }
        int i4 = 1;
        this.engine.init(!this.wrapCipherMode, this.param);
        byte[] bArr2 = this.iv;
        byte[] bArr3 = new byte[i2 - bArr2.length];
        byte[] bArr4 = new byte[bArr2.length];
        byte[] bArr5 = new byte[bArr2.length + 8];
        int i5 = i3 - 1;
        if (i5 == 1) {
            this.engine.processBlock(bArr, i, bArr5, 0);
            System.arraycopy(bArr5, 0, bArr4, 0, this.iv.length);
            System.arraycopy(bArr5, this.iv.length, bArr3, 0, 8);
        } else {
            System.arraycopy(bArr, i, bArr4, 0, bArr2.length);
            byte[] bArr6 = this.iv;
            System.arraycopy(bArr, bArr6.length + i, bArr3, 0, i2 - bArr6.length);
            for (int i6 = 5; i6 >= 0; i6--) {
                int i7 = i5;
                while (i7 >= i4) {
                    System.arraycopy(bArr4, 0, bArr5, 0, this.iv.length);
                    int i8 = (i7 - 1) * 8;
                    System.arraycopy(bArr3, i8, bArr5, this.iv.length, 8);
                    int i9 = (i5 * i6) + i7;
                    int i10 = i4;
                    while (i9 != 0) {
                        int i11 = i4;
                        int length = this.iv.length - i10;
                        bArr5[length] = (byte) (bArr5[length] ^ ((byte) i9));
                        i9 >>>= 8;
                        i10++;
                        i4 = i11;
                    }
                    this.engine.processBlock(bArr5, 0, bArr5, 0);
                    System.arraycopy(bArr5, 0, bArr4, 0, 8);
                    System.arraycopy(bArr5, 8, bArr3, i8, 8);
                    i7--;
                    i4 = i4;
                }
            }
        }
        if (i5 != i4) {
            if (!Arrays.constantTimeAreEqual(bArr4, this.iv)) {
                throw new InvalidCipherTextException("checksum failed");
            }
        } else if (!Arrays.constantTimeAreEqual(bArr4, this.iv)) {
            System.arraycopy(bArr, i, bArr4, 0, this.iv.length);
            byte[] bArr7 = this.iv;
            System.arraycopy(bArr, i + bArr7.length, bArr3, 0, i2 - bArr7.length);
            for (int i12 = 5; i12 >= 0; i12--) {
                System.arraycopy(bArr4, 0, bArr5, 0, this.iv.length);
                System.arraycopy(bArr3, 0, bArr5, this.iv.length, 8);
                int i13 = (i5 * i12) + 1;
                int i14 = 1;
                while (i13 != 0) {
                    int length2 = this.iv.length - i14;
                    bArr5[length2] = (byte) (((byte) i13) ^ bArr5[length2]);
                    i13 >>>= 8;
                    i14++;
                }
                this.engine.processBlock(bArr5, 0, bArr5, 0);
                System.arraycopy(bArr5, 0, bArr4, 0, 8);
                System.arraycopy(bArr5, 8, bArr3, 0, 8);
            }
            if (!Arrays.constantTimeAreEqual(bArr4, this.iv)) {
                throw new InvalidCipherTextException("checksum failed");
            }
        }
        return bArr3;
    }
}
