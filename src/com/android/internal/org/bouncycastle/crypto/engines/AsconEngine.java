package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import com.android.internal.org.bouncycastle.crypto.modes.AEADCipher;
import com.android.internal.org.bouncycastle.crypto.params.AEADParameters;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Longs;
import com.android.internal.org.bouncycastle.util.Pack;

/* loaded from: classes5.dex */
public class AsconEngine implements AEADCipher {
    private final int ASCON_AEAD_RATE;
    private final long ASCON_IV;
    private final int CRYPTO_ABYTES;
    private final int CRYPTO_KEYBYTES;
    private long K0;
    private long K1;
    private long K2;
    private long N0;
    private long N1;
    private final String algorithmName;
    private final AsconParameters asconParameters;
    private byte[] initialAssociatedText;
    private final byte[] m_buf;
    private final int m_bufferSizeDecrypt;
    private byte[] mac;
    private final int nr;
    private long x0;
    private long x1;
    private long x2;
    private long x3;
    private long x4;
    private State m_state = State.Uninitialized;
    private int m_bufPos = 0;

    public enum AsconParameters {
        ascon80pq,
        ascon128a,
        ascon128
    }

    private enum State {
        Uninitialized,
        EncInit,
        EncAad,
        EncData,
        EncFinal,
        DecInit,
        DecAad,
        DecData,
        DecFinal
    }

    private long PAD(int i) {
        return 128 << (56 - (i << 3));
    }

    public AsconEngine(AsconParameters asconParameters) {
        this.asconParameters = asconParameters;
        int iOrdinal = asconParameters.ordinal();
        if (iOrdinal == 0) {
            this.CRYPTO_KEYBYTES = 20;
            this.CRYPTO_ABYTES = 16;
            this.ASCON_AEAD_RATE = 8;
            this.ASCON_IV = -6899501409222262784L;
            this.algorithmName = "Ascon-80pq AEAD";
        } else if (iOrdinal == 1) {
            this.CRYPTO_KEYBYTES = 16;
            this.CRYPTO_ABYTES = 16;
            this.ASCON_AEAD_RATE = 16;
            this.ASCON_IV = -9187330011336540160L;
            this.algorithmName = "Ascon-128a AEAD";
        } else if (iOrdinal == 2) {
            this.CRYPTO_KEYBYTES = 16;
            this.CRYPTO_ABYTES = 16;
            this.ASCON_AEAD_RATE = 8;
            this.ASCON_IV = -9205344418435956736L;
            this.algorithmName = "Ascon-128 AEAD";
        } else {
            throw new IllegalArgumentException("invalid parameter setting for ASCON AEAD");
        }
        int i = this.ASCON_AEAD_RATE;
        this.nr = i == 8 ? 6 : 8;
        int i2 = i + this.CRYPTO_ABYTES;
        this.m_bufferSizeDecrypt = i2;
        this.m_buf = new byte[i2];
    }

    private void ROUND(long j) {
        long j2 = this.x0;
        long j3 = this.x1;
        long j4 = this.x2;
        long j5 = this.x3;
        long j6 = this.x4;
        long j7 = ((((j2 ^ j3) ^ j4) ^ j5) ^ j) ^ ((((j2 ^ j4) ^ j6) ^ j) & j3);
        long j8 = ((((j2 ^ j4) ^ j5) ^ j6) ^ j) ^ (((j3 ^ j4) ^ j) & (j3 ^ j5));
        long j9 = (((j3 ^ j4) ^ j6) ^ j) ^ (j5 & j6);
        long j10 = ((j4 ^ (j2 ^ j3)) ^ j) ^ ((~j2) & (j5 ^ j6));
        long j11 = ((j2 ^ j6) & j3) ^ ((j3 ^ j5) ^ j6);
        this.x0 = Longs.rotateRight(j7, 28) ^ (Longs.rotateRight(j7, 19) ^ j7);
        this.x1 = (Longs.rotateRight(j8, 39) ^ j8) ^ Longs.rotateRight(j8, 61);
        this.x2 = ~(Longs.rotateRight(j9, 6) ^ (Longs.rotateRight(j9, 1) ^ j9));
        this.x3 = (Longs.rotateRight(j10, 10) ^ j10) ^ Longs.rotateRight(j10, 17);
        this.x4 = Longs.rotateRight(j11, 41) ^ (Longs.rotateRight(j11, 7) ^ j11);
    }

    private void P(int i) {
        if (i >= 8) {
            if (i == 12) {
                ROUND(240L);
                ROUND(225L);
                ROUND(210L);
                ROUND(195L);
            }
            ROUND(180L);
            ROUND(165L);
        }
        ROUND(150L);
        ROUND(135L);
        ROUND(120L);
        ROUND(105L);
        ROUND(90L);
        ROUND(75L);
    }

    private void ascon_aeadinit() {
        long j = this.ASCON_IV;
        this.x0 = j;
        if (this.CRYPTO_KEYBYTES == 20) {
            this.x0 = j ^ this.K0;
        }
        this.x1 = this.K1;
        this.x2 = this.K2;
        this.x3 = this.N0;
        this.x4 = this.N1;
        P(12);
        if (this.CRYPTO_KEYBYTES == 20) {
            this.x2 ^= this.K0;
        }
        this.x3 ^= this.K1;
        this.x4 ^= this.K2;
    }

    private void checkAAD() {
        int iOrdinal = this.m_state.ordinal();
        if (iOrdinal == 1) {
            this.m_state = State.EncAad;
            return;
        }
        if (iOrdinal != 2) {
            if (iOrdinal == 4) {
                throw new IllegalStateException(getAlgorithmName() + " cannot be reused for encryption");
            }
            if (iOrdinal == 5) {
                this.m_state = State.DecAad;
            } else {
                if (iOrdinal == 6) {
                    return;
                }
                throw new IllegalStateException(getAlgorithmName() + " needs to be initialized");
            }
        }
    }

    private boolean checkData() {
        switch (this.m_state.ordinal()) {
            case 1:
            case 2:
                finishAAD(State.EncData);
                return true;
            case 3:
                return true;
            case 4:
                throw new IllegalStateException(getAlgorithmName() + " cannot be reused for encryption");
            case 5:
            case 6:
                finishAAD(State.DecData);
                return false;
            case 7:
                return false;
            default:
                throw new IllegalStateException(getAlgorithmName() + " needs to be initialized");
        }
    }

    private void processBufferAAD(byte[] bArr, int i) {
        this.x0 ^= Pack.bigEndianToLong(bArr, i);
        if (this.ASCON_AEAD_RATE == 16) {
            this.x1 = Pack.bigEndianToLong(bArr, i + 8) ^ this.x1;
        }
        P(this.nr);
    }

    private void finishAAD(State state) {
        int iOrdinal = this.m_state.ordinal();
        if (iOrdinal == 2 || iOrdinal == 6) {
            byte[] bArr = this.m_buf;
            int i = this.m_bufPos;
            bArr[i] = Byte.MIN_VALUE;
            if (i >= 8) {
                this.x0 = Pack.bigEndianToLong(bArr, 0) ^ this.x0;
                this.x1 ^= ((-1) << (56 - ((this.m_bufPos - 8) << 3))) & Pack.bigEndianToLong(this.m_buf, 8);
            } else {
                this.x0 = (Pack.bigEndianToLong(bArr, 0) & ((-1) << (56 - (this.m_bufPos << 3)))) ^ this.x0;
            }
            P(this.nr);
        }
        this.x4 ^= 1;
        this.m_bufPos = 0;
        this.m_state = state;
    }

    private void processBufferDecrypt(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.ASCON_AEAD_RATE + i2 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        long jBigEndianToLong = Pack.bigEndianToLong(bArr, i);
        Pack.longToBigEndian(this.x0 ^ jBigEndianToLong, bArr2, i2);
        this.x0 = jBigEndianToLong;
        if (this.ASCON_AEAD_RATE == 16) {
            long jBigEndianToLong2 = Pack.bigEndianToLong(bArr, i + 8);
            Pack.longToBigEndian(this.x1 ^ jBigEndianToLong2, bArr2, i2 + 8);
            this.x1 = jBigEndianToLong2;
        }
        P(this.nr);
    }

    private void processBufferEncrypt(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.ASCON_AEAD_RATE + i2 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        long jBigEndianToLong = this.x0 ^ Pack.bigEndianToLong(bArr, i);
        this.x0 = jBigEndianToLong;
        Pack.longToBigEndian(jBigEndianToLong, bArr2, i2);
        if (this.ASCON_AEAD_RATE == 16) {
            long jBigEndianToLong2 = Pack.bigEndianToLong(bArr, i + 8) ^ this.x1;
            this.x1 = jBigEndianToLong2;
            Pack.longToBigEndian(jBigEndianToLong2, bArr2, i2 + 8);
        }
        P(this.nr);
    }

    private void processFinalDecrypt(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i2 >= 8) {
            long jBigEndianToLong = Pack.bigEndianToLong(bArr, i);
            long j = this.x0 ^ jBigEndianToLong;
            this.x0 = j;
            Pack.longToBigEndian(j, bArr2, i3);
            this.x0 = jBigEndianToLong;
            int i4 = i + 8;
            int i5 = i3 + 8;
            int i6 = i2 - 8;
            this.x1 ^= PAD(i6);
            if (i6 != 0) {
                long jLittleEndianToLong_High = Pack.littleEndianToLong_High(bArr, i4, i6);
                long j2 = this.x1 ^ jLittleEndianToLong_High;
                this.x1 = j2;
                Pack.longToLittleEndian_High(j2, bArr2, i5, i6);
                this.x1 = jLittleEndianToLong_High ^ (this.x1 & ((-1) >>> (i6 << 3)));
            }
        } else {
            this.x0 ^= PAD(i2);
            if (i2 != 0) {
                long jLittleEndianToLong_High2 = Pack.littleEndianToLong_High(bArr, i, i2);
                long j3 = this.x0 ^ jLittleEndianToLong_High2;
                this.x0 = j3;
                Pack.longToLittleEndian_High(j3, bArr2, i3, i2);
                this.x0 = jLittleEndianToLong_High2 ^ (this.x0 & ((-1) >>> (i2 << 3)));
            }
        }
        finishData(State.DecFinal);
    }

    private void processFinalEncrypt(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i2 >= 8) {
            long jBigEndianToLong = this.x0 ^ Pack.bigEndianToLong(bArr, i);
            this.x0 = jBigEndianToLong;
            Pack.longToBigEndian(jBigEndianToLong, bArr2, i3);
            int i4 = i + 8;
            int i5 = i3 + 8;
            int i6 = i2 - 8;
            long jPAD = this.x1 ^ PAD(i6);
            this.x1 = jPAD;
            if (i6 != 0) {
                long jLittleEndianToLong_High = Pack.littleEndianToLong_High(bArr, i4, i6) ^ jPAD;
                this.x1 = jLittleEndianToLong_High;
                Pack.longToLittleEndian_High(jLittleEndianToLong_High, bArr2, i5, i6);
            }
        } else {
            long jPAD2 = this.x0 ^ PAD(i2);
            this.x0 = jPAD2;
            if (i2 != 0) {
                long jLittleEndianToLong_High2 = Pack.littleEndianToLong_High(bArr, i, i2) ^ jPAD2;
                this.x0 = jLittleEndianToLong_High2;
                Pack.longToLittleEndian_High(jLittleEndianToLong_High2, bArr2, i3, i2);
            }
        }
        finishData(State.EncFinal);
    }

    private void finishData(State state) {
        int iOrdinal = this.asconParameters.ordinal();
        if (iOrdinal == 0) {
            long j = this.x1;
            long j2 = this.K0 << 32;
            long j3 = this.K1;
            this.x1 = j ^ (j2 | (j3 >> 32));
            long j4 = this.x2;
            long j5 = j3 << 32;
            long j6 = this.K2;
            this.x2 = j4 ^ (j5 | (j6 >> 32));
            this.x3 ^= j6 << 32;
        } else if (iOrdinal == 1) {
            this.x2 ^= this.K1;
            this.x3 ^= this.K2;
        } else if (iOrdinal == 2) {
            this.x1 ^= this.K1;
            this.x2 ^= this.K2;
        } else {
            throw new IllegalStateException();
        }
        P(12);
        this.x3 ^= this.K1;
        this.x4 ^= this.K2;
        this.m_state = state;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        KeyParameter key;
        byte[] iv;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            key = aEADParameters.getKey();
            iv = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize != this.CRYPTO_ABYTES * 8) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + macSize);
            }
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            key = (KeyParameter) parametersWithIV.getParameters();
            iv = parametersWithIV.getIV();
            this.initialAssociatedText = null;
        } else {
            throw new IllegalArgumentException("invalid parameters passed to Ascon");
        }
        if (key == null) {
            throw new IllegalArgumentException("Ascon Init parameters must include a key");
        }
        if (iv == null || iv.length != this.CRYPTO_ABYTES) {
            throw new IllegalArgumentException(this.asconParameters + " requires exactly " + this.CRYPTO_ABYTES + " bytes of IV");
        }
        byte[] key2 = key.getKey();
        if (key2.length != this.CRYPTO_KEYBYTES) {
            throw new IllegalArgumentException(this.asconParameters + " key must be " + this.CRYPTO_KEYBYTES + " bytes long");
        }
        CryptoServicesRegistrar.checkConstraints(new DefaultServiceProperties(getAlgorithmName(), 128, cipherParameters, Utils.getPurpose(z)));
        this.N0 = Pack.bigEndianToLong(iv, 0);
        this.N1 = Pack.bigEndianToLong(iv, 8);
        int i = this.CRYPTO_KEYBYTES;
        if (i == 16) {
            this.K1 = Pack.bigEndianToLong(key2, 0);
            this.K2 = Pack.bigEndianToLong(key2, 8);
        } else if (i == 20) {
            this.K0 = Pack.bigEndianToInt(key2, 0);
            this.K1 = Pack.bigEndianToLong(key2, 4);
            this.K2 = Pack.bigEndianToLong(key2, 12);
        } else {
            throw new IllegalStateException();
        }
        this.m_state = z ? State.EncInit : State.DecInit;
        reset(true);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.algorithmName;
    }

    public String getAlgorithmVersion() {
        return "v1.2";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        checkAAD();
        byte[] bArr = this.m_buf;
        int i = this.m_bufPos;
        bArr[i] = b;
        int i2 = i + 1;
        this.m_bufPos = i2;
        if (i2 == this.ASCON_AEAD_RATE) {
            processBufferAAD(bArr, 0);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i, int i2) {
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 <= 0) {
            return;
        }
        checkAAD();
        int i3 = this.m_bufPos;
        if (i3 > 0) {
            int i4 = this.ASCON_AEAD_RATE - i3;
            if (i2 < i4) {
                System.arraycopy(bArr, i, this.m_buf, i3, i2);
                this.m_bufPos += i2;
                return;
            } else {
                System.arraycopy(bArr, i, this.m_buf, i3, i4);
                i += i4;
                i2 -= i4;
                processBufferAAD(this.m_buf, 0);
            }
        }
        while (i2 >= this.ASCON_AEAD_RATE) {
            processBufferAAD(bArr, i);
            int i5 = this.ASCON_AEAD_RATE;
            i += i5;
            i2 -= i5;
        }
        System.arraycopy(bArr, i, this.m_buf, 0, i2);
        this.m_bufPos = i2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException {
        return processBytes(new byte[]{b}, 0, 1, bArr, i);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        int i4;
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (checkData()) {
            int i5 = this.m_bufPos;
            if (i5 > 0) {
                int i6 = this.ASCON_AEAD_RATE - i5;
                if (i2 < i6) {
                    System.arraycopy(bArr, i, this.m_buf, i5, i2);
                    this.m_bufPos += i2;
                    return 0;
                }
                System.arraycopy(bArr, i, this.m_buf, i5, i6);
                i += i6;
                i2 -= i6;
                processBufferEncrypt(this.m_buf, 0, bArr2, i3);
                i4 = this.ASCON_AEAD_RATE;
            } else {
                i4 = 0;
            }
            while (i2 >= this.ASCON_AEAD_RATE) {
                processBufferEncrypt(bArr, i, bArr2, i3 + i4);
                int i7 = this.ASCON_AEAD_RATE;
                i += i7;
                i2 -= i7;
                i4 += i7;
            }
        } else {
            int i8 = this.m_bufferSizeDecrypt;
            int i9 = this.m_bufPos;
            int i10 = i8 - i9;
            if (i2 < i10) {
                System.arraycopy(bArr, i, this.m_buf, i9, i2);
                this.m_bufPos += i2;
                return 0;
            }
            int i11 = 0;
            do {
                int i12 = this.m_bufPos;
                int i13 = this.ASCON_AEAD_RATE;
                if (i12 >= i13) {
                    processBufferDecrypt(this.m_buf, 0, bArr2, i3 + i11);
                    int i14 = this.m_bufPos;
                    int i15 = this.ASCON_AEAD_RATE;
                    int i16 = i14 - i15;
                    this.m_bufPos = i16;
                    byte[] bArr3 = this.m_buf;
                    System.arraycopy(bArr3, i15, bArr3, 0, i16);
                    int i17 = this.ASCON_AEAD_RATE;
                    i11 += i17;
                    i10 += i17;
                } else {
                    int i18 = i13 - i12;
                    System.arraycopy(bArr, i, this.m_buf, i12, i18);
                    i += i18;
                    i2 -= i18;
                    processBufferDecrypt(this.m_buf, 0, bArr2, i3 + i11);
                    i4 = i11 + this.ASCON_AEAD_RATE;
                    while (i2 >= this.m_bufferSizeDecrypt) {
                        processBufferDecrypt(bArr, i, bArr2, i3 + i4);
                        int i19 = this.ASCON_AEAD_RATE;
                        i += i19;
                        i2 -= i19;
                        i4 += i19;
                    }
                }
            } while (i2 >= i10);
            System.arraycopy(bArr, i, this.m_buf, this.m_bufPos, i2);
            this.m_bufPos += i2;
            return i11;
        }
        System.arraycopy(bArr, i, this.m_buf, 0, i2);
        this.m_bufPos = i2;
        return i4;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i) throws IllegalStateException, DataLengthException, InvalidCipherTextException {
        if (checkData()) {
            int i2 = this.m_bufPos;
            int i3 = this.CRYPTO_ABYTES + i2;
            if (i + i3 > bArr.length) {
                throw new OutputLengthException("output buffer too short");
            }
            processFinalEncrypt(this.m_buf, 0, i2, bArr, i);
            byte[] bArr2 = new byte[this.CRYPTO_ABYTES];
            this.mac = bArr2;
            Pack.longToBigEndian(this.x3, bArr2, 0);
            Pack.longToBigEndian(this.x4, this.mac, 8);
            System.arraycopy(this.mac, 0, bArr, i + this.m_bufPos, this.CRYPTO_ABYTES);
            reset(false);
            return i3;
        }
        int i4 = this.m_bufPos;
        int i5 = this.CRYPTO_ABYTES;
        if (i4 < i5) {
            throw new InvalidCipherTextException("data too short");
        }
        int i6 = i4 - i5;
        this.m_bufPos = i6;
        if (i + i6 > bArr.length) {
            throw new OutputLengthException("output buffer too short");
        }
        processFinalDecrypt(this.m_buf, 0, i6, bArr, i);
        this.x3 ^= Pack.bigEndianToLong(this.m_buf, this.m_bufPos);
        long jBigEndianToLong = this.x4 ^ Pack.bigEndianToLong(this.m_buf, this.m_bufPos + 8);
        this.x4 = jBigEndianToLong;
        if ((jBigEndianToLong | this.x3) != 0) {
            throw new InvalidCipherTextException("mac check in " + getAlgorithmName() + " failed");
        }
        reset(true);
        return i6;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        return this.mac;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i) {
        int iMax = Math.max(0, i);
        switch (this.m_state.ordinal()) {
            case 3:
            case 4:
                iMax += this.m_bufPos;
                break;
            case 5:
            case 6:
                iMax = Math.max(0, iMax - this.CRYPTO_ABYTES);
                break;
            case 7:
            case 8:
                iMax = Math.max(0, (iMax + this.m_bufPos) - this.CRYPTO_ABYTES);
                break;
        }
        return iMax - (iMax % this.ASCON_AEAD_RATE);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int i) {
        int i2;
        int iMax = Math.max(0, i);
        switch (this.m_state.ordinal()) {
            case 3:
            case 4:
                iMax += this.m_bufPos;
                i2 = this.CRYPTO_ABYTES;
                break;
            case 5:
            case 6:
                return Math.max(0, iMax - this.CRYPTO_ABYTES);
            case 7:
            case 8:
                return Math.max(0, (iMax + this.m_bufPos) - this.CRYPTO_ABYTES);
            default:
                i2 = this.CRYPTO_ABYTES;
                break;
        }
        return iMax + i2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        reset(true);
    }

    private void reset(boolean z) {
        if (z) {
            this.mac = null;
        }
        Arrays.clear(this.m_buf);
        this.m_bufPos = 0;
        switch (this.m_state.ordinal()) {
            case 1:
            case 5:
                break;
            case 2:
            case 3:
            case 4:
                this.m_state = State.EncFinal;
                return;
            case 6:
            case 7:
            case 8:
                this.m_state = State.DecInit;
                break;
            default:
                throw new IllegalStateException(getAlgorithmName() + " needs to be initialized");
        }
        ascon_aeadinit();
        byte[] bArr = this.initialAssociatedText;
        if (bArr != null) {
            processAADBytes(bArr, 0, bArr.length);
        }
    }

    public int getKeyBytesSize() {
        return this.CRYPTO_KEYBYTES;
    }

    public int getIVBytesSize() {
        return this.CRYPTO_ABYTES;
    }
}
