package com.android.internal.org.bouncycastle.crypto.modes;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import com.android.internal.org.bouncycastle.crypto.params.AEADParameters;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;

/* loaded from: classes5.dex */
public class CCMBlockCipher implements CCMModeCipher {
    private int blockSize;
    private BlockCipher cipher;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private CipherParameters keyParam;
    private byte[] macBlock;
    private int macSize;
    private byte[] nonce;
    private ExposedByteArrayOutputStream associatedText = new ExposedByteArrayOutputStream();
    private ExposedByteArrayOutputStream data = new ExposedByteArrayOutputStream();

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i) {
        return 0;
    }

    public static CCMModeCipher newInstance(BlockCipher blockCipher) {
        return new CCMBlockCipher(blockCipher);
    }

    public CCMBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        int blockSize = blockCipher.getBlockSize();
        this.blockSize = blockSize;
        this.macBlock = new byte[blockSize];
        if (blockSize != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        CipherParameters parameters;
        this.forEncryption = z;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            this.nonce = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            this.macSize = getMacSize(z, aEADParameters.getMacSize());
            parameters = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.nonce = parametersWithIV.getIV();
            this.initialAssociatedText = null;
            this.macSize = getMacSize(z, 64);
            parameters = parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to CCM: " + cipherParameters.getClass().getName());
        }
        if (parameters != null) {
            this.keyParam = parameters;
        }
        byte[] bArr = this.nonce;
        if (bArr == null || bArr.length < 7 || bArr.length > 13) {
            throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CCM";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        this.associatedText.write(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i, int i2) {
        this.associatedText.write(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException, IllegalStateException {
        this.data.write(b);
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException, IllegalStateException {
        if (bArr.length < i + i2) {
            throw new DataLengthException("Input buffer too short");
        }
        this.data.write(bArr, i, i2);
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i) throws IllegalStateException, InvalidCipherTextException {
        int processPacket = processPacket(this.data.getBuffer(), 0, this.data.size(), bArr, i);
        reset();
        return processPacket;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        this.cipher.reset();
        this.associatedText.reset();
        this.data.reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        int i = this.macSize;
        byte[] bArr = new byte[i];
        System.arraycopy(this.macBlock, 0, bArr, 0, i);
        return bArr;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int i) {
        int size = i + this.data.size();
        if (this.forEncryption) {
            return size + this.macSize;
        }
        int i2 = this.macSize;
        if (size < i2) {
            return 0;
        }
        return size - i2;
    }

    public byte[] processPacket(byte[] bArr, int i, int i2) throws IllegalStateException, InvalidCipherTextException {
        byte[] bArr2;
        if (this.forEncryption) {
            bArr2 = new byte[this.macSize + i2];
        } else {
            int i3 = this.macSize;
            if (i2 < i3) {
                throw new InvalidCipherTextException("data too short");
            }
            bArr2 = new byte[i2 - i3];
        }
        byte[] bArr3 = bArr2;
        processPacket(bArr, i, i2, bArr3, 0);
        return bArr3;
    }

    public int processPacket(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalStateException, InvalidCipherTextException, DataLengthException {
        int i4;
        if (this.keyParam == null) {
            throw new IllegalStateException("CCM cipher unitialized.");
        }
        byte[] bArr3 = this.nonce;
        int length = bArr3.length;
        int i5 = 15 - length;
        if (i5 < 4 && i2 >= (1 << (i5 * 8))) {
            throw new IllegalStateException("CCM packet too large for choice of q.");
        }
        byte[] bArr4 = new byte[this.blockSize];
        bArr4[0] = (byte) ((14 - length) & 7);
        System.arraycopy(bArr3, 0, bArr4, 1, bArr3.length);
        CTRModeCipher newInstance = SICBlockCipher.newInstance(this.cipher);
        newInstance.init(this.forEncryption, new ParametersWithIV(this.keyParam, bArr4));
        if (this.forEncryption) {
            int i6 = this.macSize + i2;
            if (bArr2.length < i6 + i3) {
                throw new OutputLengthException("Output buffer too short.");
            }
            calculateMac(bArr, i, i2, this.macBlock);
            byte[] bArr5 = new byte[this.blockSize];
            newInstance.processBlock(this.macBlock, 0, bArr5, 0);
            int i7 = i;
            int i8 = i3;
            while (true) {
                int i9 = i + i2;
                int i10 = this.blockSize;
                if (i7 < i9 - i10) {
                    newInstance.processBlock(bArr, i7, bArr2, i8);
                    int i11 = this.blockSize;
                    i8 += i11;
                    i7 += i11;
                } else {
                    byte[] bArr6 = new byte[i10];
                    int i12 = i9 - i7;
                    System.arraycopy(bArr, i7, bArr6, 0, i12);
                    newInstance.processBlock(bArr6, 0, bArr6, 0);
                    System.arraycopy(bArr6, 0, bArr2, i8, i12);
                    System.arraycopy(bArr5, 0, bArr2, i3 + i2, this.macSize);
                    return i6;
                }
            }
        } else {
            int i13 = this.macSize;
            if (i2 < i13) {
                throw new InvalidCipherTextException("data too short");
            }
            int i14 = i2 - i13;
            if (bArr2.length < i14 + i3) {
                throw new OutputLengthException("Output buffer too short.");
            }
            int i15 = i + i14;
            System.arraycopy(bArr, i15, this.macBlock, 0, i13);
            byte[] bArr7 = this.macBlock;
            newInstance.processBlock(bArr7, 0, bArr7, 0);
            int i16 = this.macSize;
            while (true) {
                byte[] bArr8 = this.macBlock;
                if (i16 == bArr8.length) {
                    break;
                }
                bArr8[i16] = 0;
                i16++;
            }
            int i17 = i;
            int i18 = i3;
            while (true) {
                i4 = this.blockSize;
                if (i17 >= i15 - i4) {
                    break;
                }
                newInstance.processBlock(bArr, i17, bArr2, i18);
                int i19 = this.blockSize;
                i18 += i19;
                i17 += i19;
            }
            byte[] bArr9 = new byte[i4];
            int i20 = i14 - (i17 - i);
            System.arraycopy(bArr, i17, bArr9, 0, i20);
            newInstance.processBlock(bArr9, 0, bArr9, 0);
            System.arraycopy(bArr9, 0, bArr2, i18, i20);
            byte[] bArr10 = new byte[this.blockSize];
            calculateMac(bArr2, i3, i14, bArr10);
            if (Arrays.constantTimeAreEqual(this.macBlock, bArr10)) {
                return i14;
            }
            throw new InvalidCipherTextException("mac check in CCM failed");
        }
    }

    private int calculateMac(byte[] bArr, int i, int i2, byte[] bArr2) {
        CBCBlockCipherMac cBCBlockCipherMac = new CBCBlockCipherMac(this.cipher, this.macSize * 8);
        cBCBlockCipherMac.init(this.keyParam);
        byte[] bArr3 = new byte[16];
        if (hasAssociatedText()) {
            bArr3[0] = (byte) (bArr3[0] | 64);
        }
        int i3 = 2;
        byte macSize = (byte) (bArr3[0] | ((((cBCBlockCipherMac.getMacSize() - 2) / 2) & 7) << 3));
        bArr3[0] = macSize;
        byte[] bArr4 = this.nonce;
        bArr3[0] = (byte) (macSize | ((14 - bArr4.length) & 7));
        System.arraycopy(bArr4, 0, bArr3, 1, bArr4.length);
        int i4 = i2;
        int i5 = 1;
        while (i4 > 0) {
            bArr3[16 - i5] = (byte) (i4 & 255);
            i4 >>>= 8;
            i5++;
        }
        cBCBlockCipherMac.update(bArr3, 0, 16);
        if (hasAssociatedText()) {
            int associatedTextLength = getAssociatedTextLength();
            if (associatedTextLength < 65280) {
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 8));
                cBCBlockCipherMac.update((byte) associatedTextLength);
            } else {
                cBCBlockCipherMac.update((byte) -1);
                cBCBlockCipherMac.update((byte) -2);
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 24));
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 16));
                cBCBlockCipherMac.update((byte) (associatedTextLength >> 8));
                cBCBlockCipherMac.update((byte) associatedTextLength);
                i3 = 6;
            }
            byte[] bArr5 = this.initialAssociatedText;
            if (bArr5 != null) {
                cBCBlockCipherMac.update(bArr5, 0, bArr5.length);
            }
            if (this.associatedText.size() > 0) {
                cBCBlockCipherMac.update(this.associatedText.getBuffer(), 0, this.associatedText.size());
            }
            int i6 = (i3 + associatedTextLength) % 16;
            if (i6 != 0) {
                while (i6 != 16) {
                    cBCBlockCipherMac.update((byte) 0);
                    i6++;
                }
            }
        }
        cBCBlockCipherMac.update(bArr, i, i2);
        return cBCBlockCipherMac.doFinal(bArr2, 0);
    }

    private int getMacSize(boolean z, int i) {
        if (!z || (i >= 32 && i <= 128 && (i & 15) == 0)) {
            return i >>> 3;
        }
        throw new IllegalArgumentException("tag length in octets must be one of {4,6,8,10,12,14,16}");
    }

    private int getAssociatedTextLength() {
        int size = this.associatedText.size();
        byte[] bArr = this.initialAssociatedText;
        return size + (bArr == null ? 0 : bArr.length);
    }

    private boolean hasAssociatedText() {
        return getAssociatedTextLength() > 0;
    }

    private static class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public byte[] getBuffer() {
            return this.buf;
        }
    }
}
