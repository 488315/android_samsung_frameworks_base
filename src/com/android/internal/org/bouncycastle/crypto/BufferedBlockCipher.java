package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes5.dex */
public class BufferedBlockCipher {
    protected byte[] buf;
    protected int bufOff;
    protected BlockCipher cipher;
    protected boolean forEncryption;
    protected MultiBlockCipher mbCipher;
    protected boolean partialBlockOkay;
    protected boolean pgpCFB;

    BufferedBlockCipher() {
    }

    public BufferedBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        if (blockCipher instanceof MultiBlockCipher) {
            MultiBlockCipher multiBlockCipher = (MultiBlockCipher) blockCipher;
            this.mbCipher = multiBlockCipher;
            this.buf = new byte[multiBlockCipher.getMultiBlockSize()];
        } else {
            this.mbCipher = null;
            this.buf = new byte[blockCipher.getBlockSize()];
        }
        boolean z = false;
        this.bufOff = 0;
        String algorithmName = blockCipher.getAlgorithmName();
        int indexOf = algorithmName.indexOf(47) + 1;
        boolean z2 = indexOf > 0 && algorithmName.startsWith("PGP", indexOf);
        this.pgpCFB = z2;
        if (z2 || (blockCipher instanceof StreamCipher)) {
            this.partialBlockOkay = true;
            return;
        }
        if (indexOf > 0 && algorithmName.startsWith("OpenPGP", indexOf)) {
            z = true;
        }
        this.partialBlockOkay = z;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = z;
        reset();
        this.cipher.init(z, cipherParameters);
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public int getUpdateOutputSize(int i) {
        int length;
        int i2 = i + this.bufOff;
        if (this.pgpCFB) {
            if (this.forEncryption) {
                length = (i2 % this.buf.length) - (this.cipher.getBlockSize() + 2);
            } else {
                length = i2 % this.buf.length;
            }
        } else {
            length = i2 % this.buf.length;
        }
        return i2 - length;
    }

    public int getOutputSize(int i) {
        int i2;
        if (this.pgpCFB && this.forEncryption) {
            i += this.bufOff;
            i2 = this.cipher.getBlockSize() + 2;
        } else {
            i2 = this.bufOff;
        }
        return i + i2;
    }

    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException, IllegalStateException {
        byte[] bArr2 = this.buf;
        int i2 = this.bufOff;
        int i3 = i2 + 1;
        this.bufOff = i3;
        bArr2[i2] = b;
        if (i3 != bArr2.length) {
            return 0;
        }
        int processBlock = this.cipher.processBlock(bArr2, 0, bArr, i);
        this.bufOff = 0;
        return processBlock;
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException, IllegalStateException {
        int i4;
        if (i2 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = getBlockSize();
        int updateOutputSize = getUpdateOutputSize(i2);
        if (updateOutputSize > 0 && updateOutputSize + i3 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        byte[] bArr3 = this.buf;
        int length = bArr3.length;
        int i5 = this.bufOff;
        int i6 = length - i5;
        if (i2 > i6) {
            System.arraycopy(bArr, i, bArr3, i5, i6);
            i4 = this.cipher.processBlock(this.buf, 0, bArr2, i3);
            this.bufOff = 0;
            i2 -= i6;
            int i7 = i6 + i;
            MultiBlockCipher multiBlockCipher = this.mbCipher;
            if (multiBlockCipher != null) {
                int multiBlockSize = i2 / multiBlockCipher.getMultiBlockSize();
                if (multiBlockSize > 0) {
                    i4 += this.mbCipher.processBlocks(bArr, i7, multiBlockSize, bArr2, i3 + i4);
                    int multiBlockSize2 = multiBlockSize * this.mbCipher.getMultiBlockSize();
                    i2 -= multiBlockSize2;
                    i7 += multiBlockSize2;
                }
                i = i7;
            } else {
                i = i7;
                while (i2 > this.buf.length) {
                    i4 += this.cipher.processBlock(bArr, i, bArr2, i3 + i4);
                    i2 -= blockSize;
                    i += blockSize;
                }
            }
        } else {
            i4 = 0;
        }
        System.arraycopy(bArr, i, this.buf, this.bufOff, i2);
        int i8 = this.bufOff + i2;
        this.bufOff = i8;
        byte[] bArr4 = this.buf;
        if (i8 != bArr4.length) {
            return i4;
        }
        int processBlock = i4 + this.cipher.processBlock(bArr4, 0, bArr2, i3 + i4);
        this.bufOff = 0;
        return processBlock;
    }

    public int doFinal(byte[] bArr, int i) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        try {
            int i2 = this.bufOff;
            if (i + i2 > bArr.length) {
                throw new OutputLengthException("output buffer too short for doFinal()");
            }
            int i3 = 0;
            if (i2 != 0) {
                if (!this.partialBlockOkay) {
                    throw new DataLengthException("data not block size aligned");
                }
                BlockCipher blockCipher = this.cipher;
                byte[] bArr2 = this.buf;
                blockCipher.processBlock(bArr2, 0, bArr2, 0);
                int i4 = this.bufOff;
                this.bufOff = 0;
                System.arraycopy(this.buf, 0, bArr, i, i4);
                i3 = i4;
            }
            return i3;
        } finally {
            reset();
        }
    }

    public void reset() {
        int i = 0;
        while (true) {
            byte[] bArr = this.buf;
            if (i < bArr.length) {
                bArr[i] = 0;
                i++;
            } else {
                this.bufOff = 0;
                this.cipher.reset();
                return;
            }
        }
    }
}
