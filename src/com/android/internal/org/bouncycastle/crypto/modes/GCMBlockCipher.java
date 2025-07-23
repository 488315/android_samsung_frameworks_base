package com.android.internal.org.bouncycastle.crypto.modes;

import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import com.android.internal.org.bouncycastle.crypto.modes.gcm.BasicGCMExponentiator;
import com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMExponentiator;
import com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import com.android.internal.org.bouncycastle.crypto.modes.gcm.GCMUtil;
import com.android.internal.org.bouncycastle.crypto.modes.gcm.Tables4kGCMMultiplier;
import com.android.internal.org.bouncycastle.crypto.params.AEADParameters;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Pack;

/* loaded from: classes5.dex */
public class GCMBlockCipher implements GCMModeCipher {
    private static final int BLOCK_SIZE = 16;
    private static final long MAX_INPUT_SIZE = 68719476704L;
    private byte[] H;
    private byte[] J0;
    private byte[] S;
    private byte[] S_at;
    private byte[] S_atPre;
    private byte[] atBlock;
    private int atBlockPos;
    private long atLength;
    private long atLengthPre;
    private int blocksRemaining;
    private byte[] bufBlock;
    private int bufOff;
    private BlockCipher cipher;
    private byte[] counter;
    private GCMExponentiator exp;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private boolean initialised;
    private byte[] lastKey;
    private byte[] macBlock;
    private int macSize;
    private GCMMultiplier multiplier;
    private byte[] nonce;
    private long totalLength;

    public static GCMModeCipher newInstance(BlockCipher blockCipher) {
        return new GCMBlockCipher(blockCipher);
    }

    public static GCMModeCipher newInstance(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        return new GCMBlockCipher(blockCipher, gCMMultiplier);
    }

    public GCMBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, null);
    }

    public GCMBlockCipher(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        if (blockCipher.getBlockSize() != 16) {
            throw new IllegalArgumentException("cipher required with a block size of 16.");
        }
        gCMMultiplier = gCMMultiplier == null ? new Tables4kGCMMultiplier() : gCMMultiplier;
        this.cipher = blockCipher;
        this.multiplier = gCMMultiplier;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/GCM";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void init(boolean z, CipherParameters cipherParameters) throws IllegalArgumentException {
        byte[] iv;
        KeyParameter keyParameter;
        byte[] bArr;
        this.forEncryption = z;
        this.macBlock = null;
        this.initialised = true;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            iv = aEADParameters.getNonce();
            this.initialAssociatedText = aEADParameters.getAssociatedText();
            int macSize = aEADParameters.getMacSize();
            if (macSize < 32 || macSize > 128 || macSize % 8 != 0) {
                throw new IllegalArgumentException("Invalid value for MAC size: " + macSize);
            }
            this.macSize = macSize / 8;
            keyParameter = aEADParameters.getKey();
        } else if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            iv = parametersWithIV.getIV();
            this.initialAssociatedText = null;
            this.macSize = 16;
            keyParameter = (KeyParameter) parametersWithIV.getParameters();
        } else {
            throw new IllegalArgumentException("invalid parameters passed to GCM");
        }
        this.bufBlock = new byte[z ? 16 : this.macSize + 16];
        if (iv == null || iv.length < 1) {
            throw new IllegalArgumentException("IV must be at least 1 byte");
        }
        if (z && (bArr = this.nonce) != null && Arrays.areEqual(bArr, iv)) {
            if (keyParameter == null) {
                throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
            }
            byte[] bArr2 = this.lastKey;
            if (bArr2 != null && Arrays.areEqual(bArr2, keyParameter.getKey())) {
                throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
            }
        }
        this.nonce = iv;
        if (keyParameter != null) {
            this.lastKey = keyParameter.getKey();
        }
        if (keyParameter != null) {
            this.cipher.init(true, keyParameter);
            byte[] bArr3 = new byte[16];
            this.H = bArr3;
            this.cipher.processBlock(bArr3, 0, bArr3, 0);
            this.multiplier.init(this.H);
            this.exp = null;
        } else if (this.H == null) {
            throw new IllegalArgumentException("Key must be specified in initial init");
        }
        byte[] bArr4 = new byte[16];
        this.J0 = bArr4;
        byte[] bArr5 = this.nonce;
        if (bArr5.length == 12) {
            System.arraycopy(bArr5, 0, bArr4, 0, bArr5.length);
            this.J0[15] = 1;
        } else {
            gHASH(bArr4, bArr5, bArr5.length);
            byte[] bArr6 = new byte[16];
            Pack.longToBigEndian(this.nonce.length * 8, bArr6, 8);
            gHASHBlock(this.J0, bArr6);
        }
        this.S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0L;
        this.atLengthPre = 0L;
        this.counter = Arrays.clone(this.J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0L;
        byte[] bArr7 = this.initialAssociatedText;
        if (bArr7 != null) {
            processAADBytes(bArr7, 0, bArr7.length);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public byte[] getMac() {
        byte[] bArr = this.macBlock;
        if (bArr == null) {
            return new byte[this.macSize];
        }
        return Arrays.clone(bArr);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getOutputSize(int i) {
        int i2 = i + this.bufOff;
        if (this.forEncryption) {
            return i2 + this.macSize;
        }
        int i3 = this.macSize;
        if (i2 < i3) {
            return 0;
        }
        return i2 - i3;
    }

    private long getTotalInputSizeAfterNewInput(int i) {
        return this.totalLength + i + this.bufOff;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int getUpdateOutputSize(int i) {
        int i2 = i + this.bufOff;
        if (!this.forEncryption) {
            int i3 = this.macSize;
            if (i2 < i3) {
                return 0;
            }
            i2 -= i3;
        }
        return i2 - (i2 % 16);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADByte(byte b) {
        checkStatus();
        if (getTotalInputSizeAfterNewInput(1) > MAX_INPUT_SIZE) {
            throw new DataLengthException("Input exceeded 68719476704 bytes");
        }
        byte[] bArr = this.atBlock;
        int i = this.atBlockPos;
        bArr[i] = b;
        int i2 = i + 1;
        this.atBlockPos = i2;
        if (i2 == 16) {
            gHASHBlock(this.S_at, bArr);
            this.atBlockPos = 0;
            this.atLength += 16;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void processAADBytes(byte[] bArr, int i, int i2) {
        checkStatus();
        if (getTotalInputSizeAfterNewInput(i2) > MAX_INPUT_SIZE) {
            throw new DataLengthException("Input exceeded 68719476704 bytes");
        }
        int i3 = this.atBlockPos;
        if (i3 > 0) {
            int i4 = 16 - i3;
            if (i2 < i4) {
                System.arraycopy(bArr, i, this.atBlock, i3, i2);
                this.atBlockPos += i2;
                return;
            } else {
                System.arraycopy(bArr, i, this.atBlock, i3, i4);
                gHASHBlock(this.S_at, this.atBlock);
                this.atLength += 16;
                i += i4;
                i2 -= i4;
            }
        }
        int i5 = i2 + i;
        int i6 = i5 - 16;
        while (i <= i6) {
            gHASHBlock(this.S_at, bArr, i);
            this.atLength += 16;
            i += 16;
        }
        int i7 = i5 - i;
        this.atBlockPos = i7;
        System.arraycopy(bArr, i, this.atBlock, 0, i7);
    }

    private void initCipher() {
        if (this.atLength > 0) {
            System.arraycopy(this.S_at, 0, this.S_atPre, 0, 16);
            this.atLengthPre = this.atLength;
        }
        int i = this.atBlockPos;
        if (i > 0) {
            gHASHPartial(this.S_atPre, this.atBlock, 0, i);
            this.atLengthPre += this.atBlockPos;
        }
        if (this.atLengthPre > 0) {
            System.arraycopy(this.S_atPre, 0, this.S, 0, 16);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processByte(byte b, byte[] bArr, int i) throws DataLengthException {
        checkStatus();
        if (getTotalInputSizeAfterNewInput(1) > MAX_INPUT_SIZE) {
            throw new DataLengthException("Input exceeded 68719476704 bytes");
        }
        byte[] bArr2 = this.bufBlock;
        int i2 = this.bufOff;
        bArr2[i2] = b;
        int i3 = i2 + 1;
        this.bufOff = i3;
        if (i3 != bArr2.length) {
            return 0;
        }
        if (this.forEncryption) {
            encryptBlock(bArr2, 0, bArr, i);
            this.bufOff = 0;
        } else {
            decryptBlock(bArr2, 0, bArr, i);
            byte[] bArr3 = this.bufBlock;
            System.arraycopy(bArr3, 16, bArr3, 0, this.macSize);
            this.bufOff = this.macSize;
        }
        return 16;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws DataLengthException {
        int i4;
        checkStatus();
        if (getTotalInputSizeAfterNewInput(i2) > MAX_INPUT_SIZE) {
            throw new DataLengthException("Input exceeded 68719476704 bytes");
        }
        if (bArr.length - i < i2) {
            throw new DataLengthException("Input buffer too short");
        }
        int i5 = 16;
        if (this.forEncryption) {
            int i6 = this.bufOff;
            if (i6 > 0) {
                int i7 = 16 - i6;
                if (i2 < i7) {
                    System.arraycopy(bArr, i, this.bufBlock, i6, i2);
                    this.bufOff += i2;
                    return 0;
                }
                System.arraycopy(bArr, i, this.bufBlock, i6, i7);
                encryptBlock(this.bufBlock, 0, bArr2, i3);
                i += i7;
                i2 -= i7;
            } else {
                i5 = 0;
            }
            int i8 = i2 + i;
            int i9 = i8 - 16;
            while (i <= i9) {
                encryptBlock(bArr, i, bArr2, i3 + i5);
                i += 16;
                i5 += 16;
            }
            int i10 = i8 - i;
            this.bufOff = i10;
            System.arraycopy(bArr, i, this.bufBlock, 0, i10);
            return i5;
        }
        byte[] bArr3 = this.bufBlock;
        int length = bArr3.length;
        int i11 = this.bufOff;
        int i12 = length - i11;
        if (i2 < i12) {
            System.arraycopy(bArr, i, bArr3, i11, i2);
            this.bufOff += i2;
            return 0;
        }
        if (i11 >= 16) {
            decryptBlock(bArr3, 0, bArr2, i3);
            byte[] bArr4 = this.bufBlock;
            int i13 = this.bufOff - 16;
            this.bufOff = i13;
            System.arraycopy(bArr4, 16, bArr4, 0, i13);
            if (i2 < i12 + 16) {
                System.arraycopy(bArr, i, this.bufBlock, this.bufOff, i2);
                this.bufOff += i2;
                return 16;
            }
            i4 = 16;
        } else {
            i4 = 0;
        }
        byte[] bArr5 = this.bufBlock;
        int length2 = (i2 + i) - bArr5.length;
        int i14 = this.bufOff;
        int i15 = 16 - i14;
        System.arraycopy(bArr, i, bArr5, i14, i15);
        decryptBlock(this.bufBlock, 0, bArr2, i3 + i4);
        int i16 = i + i15;
        int i17 = i4 + 16;
        while (i16 <= length2) {
            decryptBlock(bArr, i16, bArr2, i3 + i17);
            i16 += 16;
            i17 += 16;
        }
        byte[] bArr6 = this.bufBlock;
        int length3 = (bArr6.length + length2) - i16;
        this.bufOff = length3;
        System.arraycopy(bArr, i16, bArr6, 0, length3);
        return i17;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public int doFinal(byte[] bArr, int i) throws IllegalStateException, InvalidCipherTextException {
        GCMBlockCipher gCMBlockCipher;
        byte[] bArr2;
        int i2;
        checkStatus();
        if (this.totalLength == 0) {
            initCipher();
        }
        int i3 = this.bufOff;
        if (this.forEncryption) {
            if (bArr.length - i < this.macSize + i3) {
                throw new OutputLengthException("Output buffer too short");
            }
        } else {
            int i4 = this.macSize;
            if (i3 < i4) {
                throw new InvalidCipherTextException("data too short");
            }
            i3 -= i4;
            if (bArr.length - i < i3) {
                throw new OutputLengthException("Output buffer too short");
            }
        }
        int i5 = i3;
        if (i5 > 0) {
            gCMBlockCipher = this;
            bArr2 = bArr;
            i2 = i;
            gCMBlockCipher.processPartial(this.bufBlock, 0, i5, bArr2, i2);
        } else {
            gCMBlockCipher = this;
            bArr2 = bArr;
            i2 = i;
        }
        long j = gCMBlockCipher.atLength;
        int i6 = gCMBlockCipher.atBlockPos;
        long j2 = j + i6;
        gCMBlockCipher.atLength = j2;
        if (j2 > gCMBlockCipher.atLengthPre) {
            if (i6 > 0) {
                gCMBlockCipher.gHASHPartial(gCMBlockCipher.S_at, gCMBlockCipher.atBlock, 0, i6);
            }
            if (gCMBlockCipher.atLengthPre > 0) {
                GCMUtil.xor(gCMBlockCipher.S_at, gCMBlockCipher.S_atPre);
            }
            long j3 = ((gCMBlockCipher.totalLength * 8) + 127) >>> 7;
            byte[] bArr3 = new byte[16];
            if (gCMBlockCipher.exp == null) {
                BasicGCMExponentiator basicGCMExponentiator = new BasicGCMExponentiator();
                gCMBlockCipher.exp = basicGCMExponentiator;
                basicGCMExponentiator.init(gCMBlockCipher.H);
            }
            gCMBlockCipher.exp.exponentiateX(j3, bArr3);
            GCMUtil.multiply(gCMBlockCipher.S_at, bArr3);
            GCMUtil.xor(gCMBlockCipher.S, gCMBlockCipher.S_at);
        }
        byte[] bArr4 = new byte[16];
        Pack.longToBigEndian(gCMBlockCipher.atLength * 8, bArr4, 0);
        Pack.longToBigEndian(gCMBlockCipher.totalLength * 8, bArr4, 8);
        gCMBlockCipher.gHASHBlock(gCMBlockCipher.S, bArr4);
        byte[] bArr5 = new byte[16];
        gCMBlockCipher.cipher.processBlock(gCMBlockCipher.J0, 0, bArr5, 0);
        GCMUtil.xor(bArr5, gCMBlockCipher.S);
        int i7 = gCMBlockCipher.macSize;
        byte[] bArr6 = new byte[i7];
        gCMBlockCipher.macBlock = bArr6;
        System.arraycopy(bArr5, 0, bArr6, 0, i7);
        if (gCMBlockCipher.forEncryption) {
            System.arraycopy(gCMBlockCipher.macBlock, 0, bArr2, i2 + gCMBlockCipher.bufOff, gCMBlockCipher.macSize);
            i5 += gCMBlockCipher.macSize;
        } else {
            int i8 = gCMBlockCipher.macSize;
            byte[] bArr7 = new byte[i8];
            System.arraycopy(gCMBlockCipher.bufBlock, i5, bArr7, 0, i8);
            if (!Arrays.constantTimeAreEqual(gCMBlockCipher.macBlock, bArr7)) {
                throw new InvalidCipherTextException("mac check in GCM failed");
            }
        }
        gCMBlockCipher.reset(false);
        return i5;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.modes.AEADCipher
    public void reset() {
        reset(true);
    }

    private void reset(boolean z) {
        this.cipher.reset();
        this.S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0L;
        this.atLengthPre = 0L;
        this.counter = Arrays.clone(this.J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0L;
        byte[] bArr = this.bufBlock;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
        }
        if (z) {
            this.macBlock = null;
        }
        if (this.forEncryption) {
            this.initialised = false;
            return;
        }
        byte[] bArr2 = this.initialAssociatedText;
        if (bArr2 != null) {
            processAADBytes(bArr2, 0, bArr2.length);
        }
    }

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (bArr2.length - i2 < 16) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (this.totalLength == 0) {
            initCipher();
        }
        byte[] bArr3 = new byte[16];
        getNextCTRBlock(bArr3);
        gHASHBlock(this.S, bArr, i);
        GCMUtil.xor(bArr3, 0, bArr, i, bArr2, i2);
        this.totalLength += 16;
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (bArr2.length - i2 < 16) {
            throw new OutputLengthException("Output buffer too short");
        }
        if (this.totalLength == 0) {
            initCipher();
        }
        byte[] bArr3 = new byte[16];
        getNextCTRBlock(bArr3);
        GCMUtil.xor(bArr3, bArr, i);
        gHASHBlock(this.S, bArr3);
        System.arraycopy(bArr3, 0, bArr2, i2, 16);
        this.totalLength += 16;
    }

    private void processPartial(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        byte[] bArr3 = new byte[16];
        getNextCTRBlock(bArr3);
        if (this.forEncryption) {
            GCMUtil.xor(bArr, i, bArr3, 0, i2);
            gHASHPartial(this.S, bArr, i, i2);
        } else {
            gHASHPartial(this.S, bArr, i, i2);
            GCMUtil.xor(bArr, i, bArr3, 0, i2);
        }
        System.arraycopy(bArr, i, bArr2, i3, i2);
        this.totalLength += i2;
    }

    private void gHASH(byte[] bArr, byte[] bArr2, int i) {
        for (int i2 = 0; i2 < i; i2 += 16) {
            gHASHPartial(bArr, bArr2, i2, Math.min(i - i2, 16));
        }
    }

    private void gHASHBlock(byte[] bArr, byte[] bArr2) {
        GCMUtil.xor(bArr, bArr2);
        this.multiplier.multiplyH(bArr);
    }

    private void gHASHBlock(byte[] bArr, byte[] bArr2, int i) {
        GCMUtil.xor(bArr, bArr2, i);
        this.multiplier.multiplyH(bArr);
    }

    private void gHASHPartial(byte[] bArr, byte[] bArr2, int i, int i2) {
        GCMUtil.xor(bArr, bArr2, i, i2);
        this.multiplier.multiplyH(bArr);
    }

    private void getNextCTRBlock(byte[] bArr) {
        int i = this.blocksRemaining;
        if (i == 0) {
            throw new IllegalStateException("Attempt to process too many blocks");
        }
        this.blocksRemaining = i - 1;
        byte[] bArr2 = this.counter;
        int i2 = (bArr2[15] & 255) + 1;
        bArr2[15] = (byte) i2;
        int i3 = (i2 >>> 8) + (bArr2[14] & 255);
        bArr2[14] = (byte) i3;
        int i4 = (i3 >>> 8) + (bArr2[13] & 255);
        bArr2[13] = (byte) i4;
        bArr2[12] = (byte) ((i4 >>> 8) + (bArr2[12] & 255));
        this.cipher.processBlock(bArr2, 0, bArr, 0);
    }

    private void checkStatus() {
        if (this.initialised) {
            return;
        }
        if (this.forEncryption) {
            throw new IllegalStateException("GCM cipher cannot be reused for encryption");
        }
        throw new IllegalStateException("GCM cipher needs to be initialised");
    }
}
