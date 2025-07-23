package com.android.internal.org.bouncycastle.crypto.engines;

import com.android.internal.midi.MidiConstants;
import com.android.internal.org.bouncycastle.crypto.BlockCipher;
import com.android.internal.org.bouncycastle.crypto.CipherParameters;
import com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import com.android.internal.org.bouncycastle.crypto.constraints.DefaultServiceProperties;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.crypto.params.RC2Parameters;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;

/* loaded from: classes5.dex */
public class RC2Engine implements BlockCipher {
    private static final int BLOCK_SIZE = 8;
    private static byte[] piTable = {-39, 120, -7, -60, 25, -35, -75, -19, 40, -23, -3, 121, 74, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -40, -99, -58, 126, 55, -125, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 118, 83, -114, 98, 76, 100, -120, 68, -117, -5, -94, 23, -102, 89, -11, -121, -77, 79, 19, SprAttributeBase.TYPE_ANIMATOR_SET, 69, 109, -115, 9, -127, 125, 50, -67, -113, 64, -21, -122, -73, 123, 11, -16, -107, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 92, 107, 78, -126, 84, -42, 101, -109, -50, SprAttributeBase.TYPE_DURATION, -78, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, 115, 86, MidiConstants.STATUS_PROGRAM_CHANGE, 20, -89, -116, MidiConstants.STATUS_MIDI_TIME_CODE, -36, 18, 117, -54, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 59, -66, -28, -47, 66, 61, -44, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -93, 60, -74, 38, 111, -65, 14, -38, 70, 105, 7, 87, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, MidiConstants.STATUS_SONG_POSITION, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, -101, -68, -108, 67, 3, -8, 17, -57, -10, MidiConstants.STATUS_NOTE_ON, -17, 62, -25, 6, -61, -43, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -56, 102, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -41, 8, -24, -22, -34, Byte.MIN_VALUE, 82, -18, -9, -124, -86, 114, -84, 53, 77, 106, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, -106, 26, -46, 113, 90, 21, 73, 116, 75, -97, MidiConstants.STATUS_CHANNEL_PRESSURE, 94, 4, 24, -92, -20, -62, MidiConstants.STATUS_PITCH_BEND, 65, 110, 15, 81, -53, -52, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -111, -81, 80, -95, -12, SprAttributeBase.TYPE_SHADOW, 57, -103, 124, 58, -123, 35, -72, -76, 122, -4, 2, 54, 91, 37, 85, -105, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 93, -6, -104, -29, -118, -110, -82, 5, -33, 41, 16, 103, 108, -70, -55, -45, 0, -26, -49, -31, -98, -88, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, 99, 22, 1, 63, 88, -30, -119, -87, 13, 56, 52, 27, -85, 51, -1, MidiConstants.STATUS_CONTROL_CHANGE, -69, 72, 12, 95, -71, -79, -51, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -59, MidiConstants.STATUS_SONG_SELECT, -37, 71, -27, -91, -100, 119, 10, -90, 32, 104, -2, Byte.MAX_VALUE, -63, -83};
    private boolean encrypting;
    private int[] workingKey;

    private int rotateWordLeft(int i, int i2) {
        int i3 = 65535 & i;
        return (i3 >> (16 - i2)) | (i3 << i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }

    private int[] generateWorkingKey(byte[] bArr, int i) {
        int[] iArr = new int[128];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            iArr[i2] = bArr[i2] & 255;
        }
        int length = bArr.length;
        if (length < 128) {
            int i3 = iArr[length - 1];
            int i4 = 0;
            while (true) {
                int i5 = i4 + 1;
                i3 = piTable[(i3 + iArr[i4]) & 255] & 255;
                int i6 = length + 1;
                iArr[length] = i3;
                if (i6 >= 128) {
                    break;
                }
                length = i6;
                i4 = i5;
            }
        }
        int i7 = (i + 7) >> 3;
        int i8 = 128 - i7;
        int i9 = piTable[(255 >> ((-i) & 7)) & iArr[i8]] & 255;
        iArr[i8] = i9;
        for (int i10 = 127 - i7; i10 >= 0; i10--) {
            i9 = piTable[i9 ^ iArr[i10 + i7]] & 255;
            iArr[i10] = i9;
        }
        int[] iArr2 = new int[64];
        for (int i11 = 0; i11 != 64; i11++) {
            int i12 = i11 * 2;
            iArr2[i11] = iArr[i12] + (iArr[i12 + 1] << 8);
        }
        return iArr2;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, CipherParameters cipherParameters) {
        byte[] key;
        this.encrypting = z;
        if (cipherParameters instanceof RC2Parameters) {
            RC2Parameters rC2Parameters = (RC2Parameters) cipherParameters;
            this.workingKey = generateWorkingKey(rC2Parameters.getKey(), rC2Parameters.getEffectiveKeyBits());
            key = rC2Parameters.getKey();
        } else if (cipherParameters instanceof KeyParameter) {
            key = ((KeyParameter) cipherParameters).getKey();
            this.workingKey = generateWorkingKey(key, key.length * 8);
        } else {
            throw new IllegalArgumentException("invalid parameter passed to RC2 init - " + cipherParameters.getClass().getName());
        }
        CryptoServicesRegistrar.checkConstraints(new DefaultServiceProperties(getAlgorithmName(), key.length * 8, cipherParameters, Utils.getPurpose(z)));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "RC2";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public final int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("RC2 engine not initialised");
        }
        if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i2 + 8 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i, bArr2, i2);
            return 8;
        }
        decryptBlock(bArr, i, bArr2, i2);
        return 8;
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = ((bArr[i + 7] & 255) << 8) + (bArr[i + 6] & 255);
        int i4 = ((bArr[i + 5] & 255) << 8) + (bArr[i + 4] & 255);
        int i5 = ((bArr[i + 3] & 255) << 8) + (bArr[i + 2] & 255);
        int i6 = ((bArr[i + 1] & 255) << 8) + (bArr[i] & 255);
        for (int i7 = 0; i7 <= 16; i7 += 4) {
            i6 = rotateWordLeft(i6 + ((~i3) & i5) + (i4 & i3) + this.workingKey[i7], 1);
            i5 = rotateWordLeft(i5 + ((~i6) & i4) + (i3 & i6) + this.workingKey[i7 + 1], 2);
            i4 = rotateWordLeft(i4 + ((~i5) & i3) + (i6 & i5) + this.workingKey[i7 + 2], 3);
            i3 = rotateWordLeft(i3 + ((~i4) & i6) + (i5 & i4) + this.workingKey[i7 + 3], 5);
        }
        int[] iArr = this.workingKey;
        int i8 = i6 + iArr[i3 & 63];
        int i9 = i5 + iArr[i8 & 63];
        int i10 = i4 + iArr[i9 & 63];
        int i11 = i3 + iArr[i10 & 63];
        for (int i12 = 20; i12 <= 40; i12 += 4) {
            i8 = rotateWordLeft(i8 + ((~i11) & i9) + (i10 & i11) + this.workingKey[i12], 1);
            i9 = rotateWordLeft(i9 + ((~i8) & i10) + (i11 & i8) + this.workingKey[i12 + 1], 2);
            i10 = rotateWordLeft(i10 + ((~i9) & i11) + (i8 & i9) + this.workingKey[i12 + 2], 3);
            i11 = rotateWordLeft(i11 + ((~i10) & i8) + (i9 & i10) + this.workingKey[i12 + 3], 5);
        }
        int[] iArr2 = this.workingKey;
        int i13 = i8 + iArr2[i11 & 63];
        int i14 = i9 + iArr2[i13 & 63];
        int i15 = i10 + iArr2[i14 & 63];
        int i16 = i11 + iArr2[i15 & 63];
        for (int i17 = 44; i17 < 64; i17 += 4) {
            i13 = rotateWordLeft(i13 + ((~i16) & i14) + (i15 & i16) + this.workingKey[i17], 1);
            i14 = rotateWordLeft(i14 + ((~i13) & i15) + (i16 & i13) + this.workingKey[i17 + 1], 2);
            i15 = rotateWordLeft(i15 + ((~i14) & i16) + (i13 & i14) + this.workingKey[i17 + 2], 3);
            i16 = rotateWordLeft(i16 + ((~i15) & i13) + (i14 & i15) + this.workingKey[i17 + 3], 5);
        }
        bArr2[i2] = (byte) i13;
        bArr2[i2 + 1] = (byte) (i13 >> 8);
        bArr2[i2 + 2] = (byte) i14;
        bArr2[i2 + 3] = (byte) (i14 >> 8);
        bArr2[i2 + 4] = (byte) i15;
        bArr2[i2 + 5] = (byte) (i15 >> 8);
        bArr2[i2 + 6] = (byte) i16;
        bArr2[i2 + 7] = (byte) (i16 >> 8);
    }

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = ((bArr[i + 7] & 255) << 8) + (bArr[i + 6] & 255);
        int i4 = ((bArr[i + 5] & 255) << 8) + (bArr[i + 4] & 255);
        int i5 = ((bArr[i + 3] & 255) << 8) + (bArr[i + 2] & 255);
        int i6 = ((bArr[i + 1] & 255) << 8) + (bArr[i] & 255);
        for (int i7 = 60; i7 >= 44; i7 -= 4) {
            i3 = rotateWordLeft(i3, 11) - ((((~i4) & i6) + (i5 & i4)) + this.workingKey[i7 + 3]);
            i4 = rotateWordLeft(i4, 13) - ((((~i5) & i3) + (i6 & i5)) + this.workingKey[i7 + 2]);
            i5 = rotateWordLeft(i5, 14) - ((((~i6) & i4) + (i3 & i6)) + this.workingKey[i7 + 1]);
            i6 = rotateWordLeft(i6, 15) - ((((~i3) & i5) + (i4 & i3)) + this.workingKey[i7]);
        }
        int[] iArr = this.workingKey;
        int i8 = i3 - iArr[i4 & 63];
        int i9 = i4 - iArr[i5 & 63];
        int i10 = i5 - iArr[i6 & 63];
        int i11 = i6 - iArr[i8 & 63];
        for (int i12 = 40; i12 >= 20; i12 -= 4) {
            i8 = rotateWordLeft(i8, 11) - ((((~i9) & i11) + (i10 & i9)) + this.workingKey[i12 + 3]);
            i9 = rotateWordLeft(i9, 13) - ((((~i10) & i8) + (i11 & i10)) + this.workingKey[i12 + 2]);
            i10 = rotateWordLeft(i10, 14) - ((((~i11) & i9) + (i8 & i11)) + this.workingKey[i12 + 1]);
            i11 = rotateWordLeft(i11, 15) - ((((~i8) & i10) + (i9 & i8)) + this.workingKey[i12]);
        }
        int[] iArr2 = this.workingKey;
        int i13 = i8 - iArr2[i9 & 63];
        int i14 = i9 - iArr2[i10 & 63];
        int i15 = i10 - iArr2[i11 & 63];
        int i16 = i11 - iArr2[i13 & 63];
        for (int i17 = 16; i17 >= 0; i17 -= 4) {
            i13 = rotateWordLeft(i13, 11) - ((((~i14) & i16) + (i15 & i14)) + this.workingKey[i17 + 3]);
            i14 = rotateWordLeft(i14, 13) - ((((~i15) & i13) + (i16 & i15)) + this.workingKey[i17 + 2]);
            i15 = rotateWordLeft(i15, 14) - ((((~i16) & i14) + (i13 & i16)) + this.workingKey[i17 + 1]);
            i16 = rotateWordLeft(i16, 15) - ((((~i13) & i15) + (i14 & i13)) + this.workingKey[i17]);
        }
        bArr2[i2] = (byte) i16;
        bArr2[i2 + 1] = (byte) (i16 >> 8);
        bArr2[i2 + 2] = (byte) i15;
        bArr2[i2 + 3] = (byte) (i15 >> 8);
        bArr2[i2 + 4] = (byte) i14;
        bArr2[i2 + 5] = (byte) (i14 >> 8);
        bArr2[i2 + 6] = (byte) i13;
        bArr2[i2 + 7] = (byte) (i13 >> 8);
    }
}
