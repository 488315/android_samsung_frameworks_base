package com.android.internal.org.bouncycastle.crypto.modes;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.internal.org.bouncycastle.crypto.Mac;
import com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import com.android.internal.org.bouncycastle.crypto.params.AEADParameters;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;

import java.io.ByteArrayOutputStream;

public class CCMBlockCipher implements AEADBlockCipher {
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

    public CCMBlockCipher(BlockCipher c) {
        this.cipher = c;
        this.blockSize = c.getBlockSize();
        this.macBlock = new byte[this.blockSize];
        if (this.blockSize != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean forEncryption, CipherParameters params)
            throws IllegalArgumentException {
        CipherParameters cipherParameters;
        this.forEncryption = forEncryption;
        if (params instanceof AEADParameters) {
            AEADParameters param = (AEADParameters) params;
            this.nonce = param.getNonce();
            this.initialAssociatedText = param.getAssociatedText();
            this.macSize = getMacSize(forEncryption, param.getMacSize());
            cipherParameters = param.getKey();
        } else if (params instanceof ParametersWithIV) {
            ParametersWithIV param2 = (ParametersWithIV) params;
            this.nonce = param2.getIV();
            this.initialAssociatedText = null;
            this.macSize = getMacSize(forEncryption, 64);
            cipherParameters = param2.getParameters();
        } else {
            throw new IllegalArgumentException(
                    "invalid parameters passed to CCM: " + params.getClass().getName());
        }
        if (cipherParameters != null) {
            this.keyParam = cipherParameters;
        }
        if (this.nonce == null || this.nonce.length < 7 || this.nonce.length > 13) {
            throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/CCM";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte in) {
        this.associatedText.write(in);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] in, int inOff, int len) {
        this.associatedText.write(in, inOff, len);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte in, byte[] out, int outOff)
            throws DataLengthException, IllegalStateException {
        this.data.write(in);
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] in, int inOff, int inLen, byte[] out, int outOff)
            throws DataLengthException, IllegalStateException {
        if (in.length < inOff + inLen) {
            throw new DataLengthException("Input buffer too short");
        }
        this.data.write(in, inOff, inLen);
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] out, int outOff)
            throws IllegalStateException, InvalidCipherTextException {
        int len = processPacket(this.data.getBuffer(), 0, this.data.size(), out, outOff);
        reset();
        return len;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        this.cipher.reset();
        this.associatedText.reset();
        this.data.reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        byte[] mac = new byte[this.macSize];
        System.arraycopy(this.macBlock, 0, mac, 0, mac.length);
        return mac;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int len) {
        return 0;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int len) {
        int totalData = this.data.size() + len;
        if (this.forEncryption) {
            return this.macSize + totalData;
        }
        if (totalData < this.macSize) {
            return 0;
        }
        return totalData - this.macSize;
    }

    public byte[] processPacket(byte[] in, int inOff, int inLen)
            throws IllegalStateException, InvalidCipherTextException {
        byte[] output;
        if (this.forEncryption) {
            output = new byte[this.macSize + inLen];
        } else {
            if (inLen < this.macSize) {
                throw new InvalidCipherTextException("data too short");
            }
            output = new byte[inLen - this.macSize];
        }
        processPacket(in, inOff, inLen, output, 0);
        return output;
    }

    /* JADX WARN: Incorrect condition in loop: B:33:0x00df */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int processPacket(byte[] r19, int r20, int r21, byte[] r22, int r23)
            throws java.lang.IllegalStateException,
                    com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException,
                    com.android.internal.org.bouncycastle.crypto.DataLengthException {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.org.bouncycastle.crypto.modes.CCMBlockCipher.processPacket(byte[],"
                    + " int, int, byte[], int):int");
    }

    private int calculateMac(byte[] data, int dataOff, int dataLen, byte[] macBlock) {
        int extra;
        Mac cMac = new CBCBlockCipherMac(this.cipher, this.macSize * 8);
        cMac.init(this.keyParam);
        byte[] b0 = new byte[16];
        if (hasAssociatedText()) {
            b0[0] = (byte) (b0[0] | 64);
        }
        b0[0] = (byte) (b0[0] | ((((cMac.getMacSize() - 2) / 2) & 7) << 3));
        b0[0] = (byte) (b0[0] | (((15 - this.nonce.length) - 1) & 7));
        System.arraycopy(this.nonce, 0, b0, 1, this.nonce.length);
        int q = dataLen;
        int count = 1;
        while (q > 0) {
            b0[b0.length - count] = (byte) (q & 255);
            q >>>= 8;
            count++;
        }
        cMac.update(b0, 0, b0.length);
        if (hasAssociatedText()) {
            int textLength = getAssociatedTextLength();
            if (textLength < 65280) {
                cMac.update((byte) (textLength >> 8));
                cMac.update((byte) textLength);
                extra = 2;
            } else {
                cMac.update((byte) -1);
                cMac.update((byte) -2);
                cMac.update((byte) (textLength >> 24));
                cMac.update((byte) (textLength >> 16));
                cMac.update((byte) (textLength >> 8));
                cMac.update((byte) textLength);
                extra = 6;
            }
            if (this.initialAssociatedText != null) {
                cMac.update(this.initialAssociatedText, 0, this.initialAssociatedText.length);
            }
            if (this.associatedText.size() > 0) {
                cMac.update(this.associatedText.getBuffer(), 0, this.associatedText.size());
            }
            int extra2 = (extra + textLength) % 16;
            if (extra2 != 0) {
                for (int i = extra2; i != 16; i++) {
                    cMac.update((byte) 0);
                }
            }
        }
        cMac.update(data, dataOff, dataLen);
        return cMac.doFinal(macBlock, 0);
    }

    private int getMacSize(boolean forEncryption, int requestedMacBits) {
        if (forEncryption
                && (requestedMacBits < 32
                        || requestedMacBits > 128
                        || (requestedMacBits & 15) != 0)) {
            throw new IllegalArgumentException(
                    "tag length in octets must be one of {4,6,8,10,12,14,16}");
        }
        return requestedMacBits >>> 3;
    }

    private int getAssociatedTextLength() {
        return this.associatedText.size()
                + (this.initialAssociatedText == null ? 0 : this.initialAssociatedText.length);
    }

    private boolean hasAssociatedText() {
        return getAssociatedTextLength() > 0;
    }

    private class ExposedByteArrayOutputStream extends ByteArrayOutputStream {
        public ExposedByteArrayOutputStream() {}

        public byte[] getBuffer() {
            return this.buf;
        }
    }
}
