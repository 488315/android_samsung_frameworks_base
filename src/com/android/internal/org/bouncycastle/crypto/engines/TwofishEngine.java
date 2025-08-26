package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.midi.MidiConstants;
import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.util.Integers;
import com.android.internal.org.bouncycastle.util.Pack;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;

/* loaded from: classes5.dex */
public final class TwofishEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final int GF256_FDBK = 361;
    private static final int GF256_FDBK_2 = 180;
    private static final int GF256_FDBK_4 = 90;
    private static final int INPUT_WHITEN = 0;
    private static final int MAX_KEY_BITS = 256;
    private static final int MAX_ROUNDS = 16;
    private static final int OUTPUT_WHITEN = 4;
    private static final byte[][] P = {new byte[]{-87, 103, -77, -24, 4, -3, -93, 118, -102, -110, Byte.MIN_VALUE, 120, -28, -35, -47, 56, 13, -58, 53, -104, 24, -9, -20, 108, 67, 117, 55, 38, -6, 19, -108, 72, MidiConstants.STATUS_SONG_POSITION, MidiConstants.STATUS_CHANNEL_PRESSURE, -117, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -124, 84, -33, 35, 25, 91, 61, 89, MidiConstants.STATUS_SONG_SELECT, -82, -94, -126, 99, 1, -125, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -39, 81, -101, 124, -90, -21, -91, -66, 22, 12, -29, SprAttributeBase.TYPE_ANIMATOR_SET, MidiConstants.STATUS_PROGRAM_CHANGE, -116, 58, -11, 115, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, 37, 11, -69, 78, -119, 107, 83, 106, -76, MidiConstants.STATUS_MIDI_TIME_CODE, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, 51, -55, 98, 113, -127, 121, 9, -83, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, -86, -19, 6, SprAttributeBase.TYPE_SHADOW, -78, -46, 65, 123, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 17, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, -62, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, MidiConstants.STATUS_NOTE_ON, 32, -10, SprAttributeBase.TYPE_DURATION, -1, -106, 92, -79, -85, -98, -100, 82, 27, 95, -109, 10, -17, -111, -123, 73, -18, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 79, -113, 59, 71, -121, 109, 70, -42, 62, 105, 100, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, -50, -53, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -4, -105, 5, 122, -84, Byte.MAX_VALUE, -43, 26, 75, 14, -89, 90, 40, 20, 63, 41, -120, 60, 76, 2, -72, -38, MidiConstants.STATUS_CONTROL_CHANGE, 23, 85, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, 21, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 18, 88, 7, -103, 52, 110, 80, -34, 104, 101, -68, -37, -8, -56, -88, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 64, -36, -2, 50, -92, -54, 16, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -16, -45, 93, 15, 0, 111, -99, 54, 66, 74, 94, -63, MidiConstants.STATUS_PITCH_BEND}, new byte[]{117, MidiConstants.STATUS_SONG_SELECT, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, MidiConstants.STATUS_MIDI_TIME_CODE, -31, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 15, -8, 27, -121, -6, 6, 63, 94, -70, -82, 91, -118, 0, -68, -99, 109, -63, -79, 14, Byte.MIN_VALUE, 93, -46, -43, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -124, 7, 20, -75, MidiConstants.STATUS_NOTE_ON, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, -93, -78, 115, 76, 84, -110, 116, 54, 81, 56, MidiConstants.STATUS_CONTROL_CHANGE, -67, 90, -4, SprAttributeBase.TYPE_DURATION, 98, -106, 108, 66, -9, 16, 124, 40, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, -116, 19, -107, -100, -57, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, 70, 59, SprAttributeBase.TYPE_SHADOW, -54, -29, -123, -53, 17, MidiConstants.STATUS_CHANNEL_PRESSURE, -109, -72, -90, -125, 32, -1, -97, 119, -61, -52, 3, 111, 8, -65, 64, -25, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -30, 121, 12, -86, -126, 65, 58, -22, -71, -28, -102, -92, -105, 126, -38, 122, 23, 102, -108, -95, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, 61, -16, -34, -77, 11, 114, -89, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -17, -47, 83, 62, -113, 51, 38, 95, -20, 118, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, 73, -127, -120, -18, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -60, 26, -21, -39, -59, 57, -103, -51, -83, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, -117, 1, 24, 35, -35, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 78, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -7, 72, 79, MidiConstants.STATUS_SONG_POSITION, 101, -114, 120, 92, 88, 25, -115, -27, -104, 87, 103, Byte.MAX_VALUE, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, MidiConstants.STATUS_PITCH_BEND, 77, 67, 105, 41, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -84, 21, 89, -88, 10, -98, 110, 71, -33, 52, 53, 106, -49, -36, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, -55, MidiConstants.STATUS_PROGRAM_CHANGE, -101, -119, -44, -19, -85, 18, -94, 13, 82, -69, 2, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -87, -41, SprAttributeBase.TYPE_ANIMATOR_SET, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -76, 80, 4, -10, -62, 22, 37, -122, 86, 85, 9, -66, -111}};
    private static final int P_00 = 1;
    private static final int P_01 = 0;
    private static final int P_02 = 0;
    private static final int P_03 = 1;
    private static final int P_04 = 1;
    private static final int P_10 = 0;
    private static final int P_11 = 0;
    private static final int P_12 = 1;
    private static final int P_13 = 1;
    private static final int P_14 = 0;
    private static final int P_20 = 1;
    private static final int P_21 = 1;
    private static final int P_22 = 0;
    private static final int P_23 = 0;
    private static final int P_24 = 0;
    private static final int P_30 = 0;
    private static final int P_31 = 1;
    private static final int P_32 = 1;
    private static final int P_33 = 0;
    private static final int P_34 = 1;
    private static final int ROUNDS = 16;
    private static final int ROUND_SUBKEYS = 8;
    private static final int RS_GF_FDBK = 333;
    private static final int SK_BUMP = 16843009;
    private static final int SK_ROTL = 9;
    private static final int SK_STEP = 33686018;
    private static final int TOTAL_SUBKEYS = 40;
    private int[] gSBox;
    private int[] gSubKeys;
    private boolean encrypting = false;
    private int[] gMDS0 = new int[256];
    private int[] gMDS1 = new int[256];
    private int[] gMDS2 = new int[256];
    private int[] gMDS3 = new int[256];
    private int k64Cnt = 0;
    private byte[] workingKey = null;

    private int LFSR1(int i) {
        return (i >> 1) ^ ((i & 1) != 0 ? 180 : 0);
    }

    private int LFSR2(int i) {
        return ((i >> 2) ^ ((i & 2) != 0 ? 180 : 0)) ^ ((i & 1) != 0 ? 90 : 0);
    }

    private int RS_rem(int i) {
        int i2 = i >>> 24;
        int i3 = i2 & 255;
        int i4 = ((i3 << 1) ^ ((i2 & 128) != 0 ? 333 : 0)) & 255;
        int i5 = ((i3 >>> 1) ^ ((i2 & 1) != 0 ? 166 : 0)) ^ i4;
        return ((i5 << 8) ^ (((i << 8) ^ (i5 << 24)) ^ (i4 << 16))) ^ i3;
    }

    private int b0(int i) {
        return i & 255;
    }

    private int b1(int i) {
        return (i >>> 8) & 255;
    }

    private int b2(int i) {
        return (i >>> 16) & 255;
    }

    private int b3(int i) {
        return (i >>> 24) & 255;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    public TwofishEngine() {
        CryptoServicesRegistrar.checkConstraints(new DefaultServiceProperties(getAlgorithmName(), 256));
        for (int i = 0; i < 256; i++) {
            byte[][] bArr = P;
            int i2 = bArr[0][i] & 255;
            int iMx_X = Mx_X(i2) & 255;
            int iMx_Y = Mx_Y(i2) & 255;
            int i3 = bArr[1][i] & 255;
            int[] iArr = {i2, i3};
            int[] iArr2 = {iMx_X, Mx_X(i3) & 255};
            int[] iArr3 = {iMx_Y, Mx_Y(i3) & 255};
            int[] iArr4 = this.gMDS0;
            int i4 = iArr[1] | (iArr2[1] << 8);
            int i5 = iArr3[1];
            iArr4[i] = i4 | (i5 << 16) | (i5 << 24);
            int[] iArr5 = this.gMDS1;
            int i6 = iArr3[0];
            iArr5[i] = i6 | (i6 << 8) | (iArr2[0] << 16) | (iArr[0] << 24);
            int[] iArr6 = this.gMDS2;
            int i7 = iArr2[1];
            int i8 = iArr3[1];
            iArr6[i] = (iArr[1] << 16) | i7 | (i8 << 8) | (i8 << 24);
            int[] iArr7 = this.gMDS3;
            int i9 = iArr2[0];
            iArr7[i] = (iArr3[0] << 16) | (iArr[0] << 8) | i9 | (i9 << 24);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.encrypting = z;
            byte[] key = ((KeyParameter) cipherParameters).getKey();
            this.workingKey = key;
            int length = key.length * 8;
            if (length != 128 && length != 192 && length != 256) {
                throw new IllegalArgumentException("Key length not 128/192/256 bits.");
            }
            CryptoServicesRegistrar.checkConstraints(new DefaultServiceProperties(getAlgorithmName(), length, cipherParameters, Utils.getPurpose(z)));
            byte[] bArr = this.workingKey;
            this.k64Cnt = bArr.length / 8;
            setKey(bArr);
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to Twofish init - " + cipherParameters.getClass().getName());
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "Twofish";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("Twofish not initialised");
        }
        if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 + 16 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i, bArr2, i2);
            return 16;
        }
        decryptBlock(bArr, i, bArr2, i2);
        return 16;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void reset() {
        byte[] bArr = this.workingKey;
        if (bArr != null) {
            setKey(bArr);
        }
    }

    private void setKey(byte[] bArr) {
        int iB0;
        int iB1;
        int iB2;
        int iB3;
        int iB22;
        int iB12;
        int iB02;
        int iB32;
        int[] iArr = new int[4];
        int[] iArr2 = new int[4];
        int[] iArr3 = new int[4];
        this.gSubKeys = new int[40];
        for (int i = 0; i < this.k64Cnt; i++) {
            int i2 = i * 8;
            iArr[i] = Pack.littleEndianToInt(bArr, i2);
            int iLittleEndianToInt = Pack.littleEndianToInt(bArr, i2 + 4);
            iArr2[i] = iLittleEndianToInt;
            iArr3[(this.k64Cnt - 1) - i] = RS_MDS_Encode(iArr[i], iLittleEndianToInt);
        }
        for (int i3 = 0; i3 < 20; i3++) {
            int i4 = SK_STEP * i3;
            int iF32 = F32(i4, iArr);
            int iRotateLeft = Integers.rotateLeft(F32(i4 + 16843009, iArr2), 8);
            int i5 = iF32 + iRotateLeft;
            int[] iArr4 = this.gSubKeys;
            int i6 = i3 * 2;
            iArr4[i6] = i5;
            int i7 = i5 + iRotateLeft;
            iArr4[i6 + 1] = (i7 << 9) | (i7 >>> 23);
        }
        int i8 = iArr3[0];
        int i9 = iArr3[1];
        int i10 = 2;
        int i11 = iArr3[2];
        int i12 = iArr3[3];
        this.gSBox = new int[1024];
        int i13 = 0;
        while (i13 < 256) {
            int i14 = this.k64Cnt & 3;
            if (i14 != 0) {
                if (i14 == 1) {
                    int[] iArr5 = this.gSBox;
                    int i15 = i13 * 2;
                    int[] iArr6 = this.gMDS0;
                    byte[][] bArr2 = P;
                    iArr5[i15] = iArr6[(bArr2[0][i13] & 255) ^ b0(i8)];
                    this.gSBox[i15 + 1] = this.gMDS1[(bArr2[0][i13] & 255) ^ b1(i8)];
                    this.gSBox[i15 + 512] = this.gMDS2[(bArr2[1][i13] & 255) ^ b2(i8)];
                    this.gSBox[i15 + 513] = this.gMDS3[(bArr2[1][i13] & 255) ^ b3(i8)];
                } else if (i14 == i10) {
                    iB32 = i13;
                    iB02 = iB32;
                    iB12 = iB02;
                    iB22 = iB12;
                    int[] iArr7 = this.gSBox;
                    int i16 = i13 * 2;
                    int[] iArr8 = this.gMDS0;
                    byte[][] bArr3 = P;
                    byte[] bArr4 = bArr3[0];
                    iArr7[i16] = iArr8[(bArr4[(bArr4[iB02] & 255) ^ b0(i9)] & 255) ^ b0(i8)];
                    this.gSBox[i16 + 1] = this.gMDS1[(bArr3[0][(bArr3[1][iB12] & 255) ^ b1(i9)] & 255) ^ b1(i8)];
                    this.gSBox[i16 + 512] = this.gMDS2[(bArr3[1][(bArr3[0][iB22] & 255) ^ b2(i9)] & 255) ^ b2(i8)];
                    int[] iArr9 = this.gMDS3;
                    byte[] bArr5 = bArr3[1];
                    this.gSBox[i16 + 513] = iArr9[(bArr5[(bArr5[iB32] & 255) ^ b3(i9)] & 255) ^ b3(i8)];
                } else if (i14 == 3) {
                    iB3 = i13;
                    iB0 = iB3;
                    iB1 = iB0;
                    iB2 = iB1;
                }
                i13++;
                i10 = 2;
            } else {
                byte[][] bArr6 = P;
                iB0 = (bArr6[1][i13] & 255) ^ b0(i12);
                iB1 = (bArr6[0][i13] & 255) ^ b1(i12);
                iB2 = (bArr6[0][i13] & 255) ^ b2(i12);
                iB3 = (bArr6[1][i13] & 255) ^ b3(i12);
            }
            byte[][] bArr7 = P;
            iB02 = (bArr7[1][iB0] & 255) ^ b0(i11);
            iB12 = (bArr7[1][iB1] & 255) ^ b1(i11);
            iB22 = (bArr7[0][iB2] & 255) ^ b2(i11);
            iB32 = (bArr7[0][iB3] & 255) ^ b3(i11);
            int[] iArr72 = this.gSBox;
            int i162 = i13 * 2;
            int[] iArr82 = this.gMDS0;
            byte[][] bArr32 = P;
            byte[] bArr42 = bArr32[0];
            iArr72[i162] = iArr82[(bArr42[(bArr42[iB02] & 255) ^ b0(i9)] & 255) ^ b0(i8)];
            this.gSBox[i162 + 1] = this.gMDS1[(bArr32[0][(bArr32[1][iB12] & 255) ^ b1(i9)] & 255) ^ b1(i8)];
            this.gSBox[i162 + 512] = this.gMDS2[(bArr32[1][(bArr32[0][iB22] & 255) ^ b2(i9)] & 255) ^ b2(i8)];
            int[] iArr92 = this.gMDS3;
            byte[] bArr52 = bArr32[1];
            this.gSBox[i162 + 513] = iArr92[(bArr52[(bArr52[iB32] & 255) ^ b3(i9)] & 255) ^ b3(i8)];
            i13++;
            i10 = 2;
        }
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = 0;
        int iLittleEndianToInt = Pack.littleEndianToInt(bArr, i) ^ this.gSubKeys[0];
        int iLittleEndianToInt2 = Pack.littleEndianToInt(bArr, i + 4) ^ this.gSubKeys[1];
        int i4 = 2;
        int iLittleEndianToInt3 = Pack.littleEndianToInt(bArr, i + 8) ^ this.gSubKeys[2];
        int iLittleEndianToInt4 = Pack.littleEndianToInt(bArr, i + 12) ^ this.gSubKeys[3];
        int i5 = 8;
        while (i3 < 16) {
            int iFe32_0 = Fe32_0(iLittleEndianToInt);
            int iFe32_3 = Fe32_3(iLittleEndianToInt2);
            iLittleEndianToInt3 = Integers.rotateRight(iLittleEndianToInt3 ^ ((iFe32_0 + iFe32_3) + this.gSubKeys[i5]), 1);
            iLittleEndianToInt4 = Integers.rotateLeft(iLittleEndianToInt4, 1) ^ ((iFe32_0 + (iFe32_3 * i4)) + this.gSubKeys[i5 + 1]);
            int iFe32_02 = Fe32_0(iLittleEndianToInt3);
            int iFe32_32 = Fe32_3(iLittleEndianToInt4);
            int i6 = i4;
            int i7 = i5 + 3;
            iLittleEndianToInt = Integers.rotateRight(iLittleEndianToInt ^ ((iFe32_02 + iFe32_32) + this.gSubKeys[i5 + 2]), 1);
            i5 += 4;
            iLittleEndianToInt2 = Integers.rotateLeft(iLittleEndianToInt2, 1) ^ ((iFe32_02 + (iFe32_32 * 2)) + this.gSubKeys[i7]);
            i3 += 2;
            i4 = i6;
        }
        Pack.intToLittleEndian(this.gSubKeys[4] ^ iLittleEndianToInt3, bArr2, i2);
        Pack.intToLittleEndian(iLittleEndianToInt4 ^ this.gSubKeys[5], bArr2, i2 + 4);
        Pack.intToLittleEndian(this.gSubKeys[6] ^ iLittleEndianToInt, bArr2, i2 + 8);
        Pack.intToLittleEndian(this.gSubKeys[7] ^ iLittleEndianToInt2, bArr2, i2 + 12);
    }

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int iLittleEndianToInt = Pack.littleEndianToInt(bArr, i) ^ this.gSubKeys[4];
        int iLittleEndianToInt2 = Pack.littleEndianToInt(bArr, i + 4) ^ this.gSubKeys[5];
        int iLittleEndianToInt3 = Pack.littleEndianToInt(bArr, i + 8) ^ this.gSubKeys[6];
        int iLittleEndianToInt4 = Pack.littleEndianToInt(bArr, i + 12) ^ this.gSubKeys[7];
        int i3 = 39;
        for (int i4 = 0; i4 < 16; i4 += 2) {
            int iFe32_0 = Fe32_0(iLittleEndianToInt);
            int iFe32_3 = Fe32_3(iLittleEndianToInt2);
            int i5 = iLittleEndianToInt4 ^ (((iFe32_3 * 2) + iFe32_0) + this.gSubKeys[i3]);
            iLittleEndianToInt3 = Integers.rotateLeft(iLittleEndianToInt3, 1) ^ ((iFe32_0 + iFe32_3) + this.gSubKeys[i3 - 1]);
            iLittleEndianToInt4 = Integers.rotateRight(i5, 1);
            int iFe32_02 = Fe32_0(iLittleEndianToInt3);
            int iFe32_32 = Fe32_3(iLittleEndianToInt4);
            int i6 = i3 - 3;
            int i7 = iLittleEndianToInt2 ^ (((iFe32_32 * 2) + iFe32_02) + this.gSubKeys[i3 - 2]);
            i3 -= 4;
            iLittleEndianToInt = Integers.rotateLeft(iLittleEndianToInt, 1) ^ ((iFe32_02 + iFe32_32) + this.gSubKeys[i6]);
            iLittleEndianToInt2 = Integers.rotateRight(i7, 1);
        }
        Pack.intToLittleEndian(iLittleEndianToInt3 ^ this.gSubKeys[0], bArr2, i2);
        Pack.intToLittleEndian(iLittleEndianToInt4 ^ this.gSubKeys[1], bArr2, i2 + 4);
        Pack.intToLittleEndian(this.gSubKeys[2] ^ iLittleEndianToInt, bArr2, i2 + 8);
        Pack.intToLittleEndian(this.gSubKeys[3] ^ iLittleEndianToInt2, bArr2, i2 + 12);
    }

    private int F32(int i, int[] iArr) {
        int i2;
        int i3;
        int iB0 = b0(i);
        int iB1 = b1(i);
        int iB2 = b2(i);
        int iB3 = b3(i);
        int i4 = iArr[0];
        int i5 = iArr[1];
        int i6 = iArr[2];
        int i7 = iArr[3];
        int i8 = this.k64Cnt & 3;
        if (i8 != 0) {
            if (i8 == 1) {
                int[] iArr2 = this.gMDS0;
                byte[][] bArr = P;
                i2 = (iArr2[(bArr[0][iB0] & 255) ^ b0(i4)] ^ this.gMDS1[(bArr[0][iB1] & 255) ^ b1(i4)]) ^ this.gMDS2[(bArr[1][iB2] & 255) ^ b2(i4)];
                i3 = this.gMDS3[b3(i4) ^ (bArr[1][iB3] & 255)];
                return i3 ^ i2;
            }
            if (i8 != 2) {
                if (i8 != 3) {
                    return 0;
                }
            }
            int[] iArr3 = this.gMDS0;
            byte[][] bArr2 = P;
            byte[] bArr3 = bArr2[0];
            i2 = (iArr3[(bArr3[(bArr3[iB0] & 255) ^ b0(i5)] & 255) ^ b0(i4)] ^ this.gMDS1[(bArr2[0][(bArr2[1][iB1] & 255) ^ b1(i5)] & 255) ^ b1(i4)]) ^ this.gMDS2[(bArr2[1][(bArr2[0][iB2] & 255) ^ b2(i5)] & 255) ^ b2(i4)];
            int[] iArr4 = this.gMDS3;
            byte[] bArr4 = bArr2[1];
            i3 = iArr4[b3(i4) ^ (bArr4[(bArr4[iB3] & 255) ^ b3(i5)] & 255)];
            return i3 ^ i2;
        }
        byte[][] bArr5 = P;
        iB0 = (bArr5[1][iB0] & 255) ^ b0(i7);
        iB1 = (bArr5[0][iB1] & 255) ^ b1(i7);
        iB2 = (bArr5[0][iB2] & 255) ^ b2(i7);
        iB3 = (bArr5[1][iB3] & 255) ^ b3(i7);
        byte[][] bArr6 = P;
        iB0 = (bArr6[1][iB0] & 255) ^ b0(i6);
        iB1 = (bArr6[1][iB1] & 255) ^ b1(i6);
        iB2 = (bArr6[0][iB2] & 255) ^ b2(i6);
        iB3 = (bArr6[0][iB3] & 255) ^ b3(i6);
        int[] iArr32 = this.gMDS0;
        byte[][] bArr22 = P;
        byte[] bArr32 = bArr22[0];
        i2 = (iArr32[(bArr32[(bArr32[iB0] & 255) ^ b0(i5)] & 255) ^ b0(i4)] ^ this.gMDS1[(bArr22[0][(bArr22[1][iB1] & 255) ^ b1(i5)] & 255) ^ b1(i4)]) ^ this.gMDS2[(bArr22[1][(bArr22[0][iB2] & 255) ^ b2(i5)] & 255) ^ b2(i4)];
        int[] iArr42 = this.gMDS3;
        byte[] bArr42 = bArr22[1];
        i3 = iArr42[b3(i4) ^ (bArr42[(bArr42[iB3] & 255) ^ b3(i5)] & 255)];
        return i3 ^ i2;
    }

    private int RS_MDS_Encode(int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            i2 = RS_rem(i2);
        }
        int iRS_rem = i ^ i2;
        for (int i4 = 0; i4 < 4; i4++) {
            iRS_rem = RS_rem(iRS_rem);
        }
        return iRS_rem;
    }

    private int Mx_X(int i) {
        return LFSR2(i) ^ i;
    }

    private int Mx_Y(int i) {
        return LFSR2(i) ^ (LFSR1(i) ^ i);
    }

    private int Fe32_0(int i) {
        int[] iArr = this.gSBox;
        return iArr[(((i >>> 24) & 255) * 2) + 513] ^ ((iArr[(i & 255) * 2] ^ iArr[(((i >>> 8) & 255) * 2) + 1]) ^ iArr[(((i >>> 16) & 255) * 2) + 512]);
    }

    private int Fe32_3(int i) {
        int[] iArr = this.gSBox;
        return iArr[(((i >>> 16) & 255) * 2) + 513] ^ ((iArr[((i >>> 24) & 255) * 2] ^ iArr[((i & 255) * 2) + 1]) ^ iArr[(((i >>> 8) & 255) * 2) + 512]);
    }
}
