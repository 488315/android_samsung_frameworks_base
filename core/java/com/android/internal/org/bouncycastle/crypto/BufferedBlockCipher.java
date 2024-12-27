package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes5.dex */
public class BufferedBlockCipher {
    protected byte[] buf;
    protected int bufOff;
    protected BlockCipher cipher;
    protected boolean forEncryption;
    protected boolean partialBlockOkay;
    protected boolean pgpCFB;

    protected BufferedBlockCipher() {}

    public BufferedBlockCipher(BlockCipher cipher) {
        this.cipher = cipher;
        this.buf = new byte[cipher.getBlockSize()];
        boolean z = false;
        this.bufOff = 0;
        String name = cipher.getAlgorithmName();
        int idx = name.indexOf(47) + 1;
        this.pgpCFB = idx > 0 && name.startsWith("PGP", idx);
        if (this.pgpCFB || (cipher instanceof StreamCipher)) {
            this.partialBlockOkay = true;
            return;
        }
        if (idx > 0 && name.startsWith("OpenPGP", idx)) {
            z = true;
        }
        this.partialBlockOkay = z;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public void init(boolean forEncryption, CipherParameters params)
            throws IllegalArgumentException {
        this.forEncryption = forEncryption;
        reset();
        this.cipher.init(forEncryption, params);
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public int getUpdateOutputSize(int len) {
        int leftOver;
        int total = this.bufOff + len;
        if (this.pgpCFB) {
            if (this.forEncryption) {
                leftOver = (total % this.buf.length) - (this.cipher.getBlockSize() + 2);
            } else {
                leftOver = total % this.buf.length;
            }
        } else {
            leftOver = total % this.buf.length;
        }
        return total - leftOver;
    }

    public int getOutputSize(int length) {
        return this.bufOff + length;
    }

    public int processByte(byte in, byte[] out, int outOff)
            throws DataLengthException, IllegalStateException {
        byte[] bArr = this.buf;
        int i = this.bufOff;
        this.bufOff = i + 1;
        bArr[i] = in;
        if (this.bufOff != this.buf.length) {
            return 0;
        }
        int resultLen = this.cipher.processBlock(this.buf, 0, out, outOff);
        this.bufOff = 0;
        return resultLen;
    }

    public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff)
            throws DataLengthException, IllegalStateException {
        if (len < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = getBlockSize();
        int length = getUpdateOutputSize(len);
        if (length > 0 && outOff + length > out.length) {
            throw new OutputLengthException("output buffer too short");
        }
        int resultLen = 0;
        int gapLen = this.buf.length - this.bufOff;
        if (len > gapLen) {
            System.arraycopy(in, inOff, this.buf, this.bufOff, gapLen);
            resultLen = 0 + this.cipher.processBlock(this.buf, 0, out, outOff);
            this.bufOff = 0;
            len -= gapLen;
            inOff += gapLen;
            while (len > this.buf.length) {
                resultLen += this.cipher.processBlock(in, inOff, out, outOff + resultLen);
                len -= blockSize;
                inOff += blockSize;
            }
        }
        System.arraycopy(in, inOff, this.buf, this.bufOff, len);
        this.bufOff += len;
        if (this.bufOff == this.buf.length) {
            int resultLen2 =
                    resultLen + this.cipher.processBlock(this.buf, 0, out, outOff + resultLen);
            this.bufOff = 0;
            return resultLen2;
        }
        return resultLen;
    }

    public int doFinal(byte[] out, int outOff)
            throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        int resultLen = 0;
        try {
            if (this.bufOff + outOff > out.length) {
                throw new OutputLengthException("output buffer too short for doFinal()");
            }
            if (this.bufOff != 0) {
                if (!this.partialBlockOkay) {
                    throw new DataLengthException("data not block size aligned");
                }
                this.cipher.processBlock(this.buf, 0, this.buf, 0);
                resultLen = this.bufOff;
                this.bufOff = 0;
                System.arraycopy(this.buf, 0, out, outOff, resultLen);
            }
            return resultLen;
        } finally {
            reset();
        }
    }

    public void reset() {
        for (int i = 0; i < this.buf.length; i++) {
            this.buf[i] = 0;
        }
        this.bufOff = 0;
        this.cipher.reset();
    }
}
