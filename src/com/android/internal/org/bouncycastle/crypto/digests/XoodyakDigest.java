package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.crypto.DataLengthException;
import com.android.internal.org.bouncycastle.crypto.Digest;
import com.android.internal.org.bouncycastle.crypto.OutputLengthException;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.Pack;
import com.samsung.android.transcode.constants.EncodeConstants;
import java.io.ByteArrayOutputStream;

/* loaded from: classes5.dex */
public class XoodyakDigest implements Digest {
    private int Rabsorb;
    private MODE mode;
    private int phase;
    private final int f_bPrime = 48;
    private final int Rhash = 16;
    private final int PhaseDown = 1;
    private final int PhaseUp = 2;
    private final int NLANES = 12;
    private final int NROWS = 3;
    private final int NCOLUMS = 4;
    private final int MAXROUNDS = 12;
    private final int TAGLEN = 16;
    private final int[] RC = {88, 56, EncodeConstants.Resolution.MM_360_EXPORT_HEIGHT_960, 208, 288, 20, 96, 44, 896, 240, 416, 18};
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private byte[] state = new byte[48];

    enum MODE {
        ModeHash,
        ModeKeyed
    }

    private int ROTL32(int i, int i2) {
        return (i << (i2 & 31)) ^ (i >>> ((32 - i2) & 31));
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 32;
    }

    public XoodyakDigest() {
        reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "Xoodyak Hash";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte b) {
        this.buffer.write(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i, int i2) {
        if (i + i2 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        this.buffer.write(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        if (i + 32 > bArr.length) {
            throw new OutputLengthException("output buffer is too short");
        }
        byte[] byteArray = this.buffer.toByteArray();
        int size = this.buffer.size();
        int i2 = 3;
        int i3 = 0;
        while (true) {
            if (this.phase != 2) {
                Up(null, 0, 0, 0);
            }
            int iMin = Math.min(size, this.Rabsorb);
            Down(byteArray, i3, iMin, i2);
            i3 += iMin;
            size -= iMin;
            if (size == 0) {
                Up(bArr, i, 16, 64);
                Down(null, 0, 0, 0);
                Up(bArr, i + 16, 16, 0);
                return 32;
            }
            i2 = 0;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        Arrays.fill(this.state, (byte) 0);
        this.phase = 2;
        this.mode = MODE.ModeHash;
        this.Rabsorb = 16;
        this.buffer.reset();
    }

    private void Up(byte[] bArr, int i, int i2, int i3) {
        int i4;
        if (this.mode != MODE.ModeHash) {
            byte[] bArr2 = this.state;
            bArr2[47] = (byte) (bArr2[47] ^ i3);
        }
        int i5 = 12;
        int[] iArr = new int[12];
        Pack.littleEndianToInt(this.state, 0, iArr, 0, 12);
        int[] iArr2 = new int[12];
        int[] iArr3 = new int[4];
        int[] iArr4 = new int[4];
        for (int i6 = 0; i6 < i5; i6++) {
            for (int i7 = 0; i7 < 4; i7++) {
                iArr3[i7] = (iArr[index(i7, 1)] ^ iArr[index(i7, 0)]) ^ iArr[index(i7, 2)];
            }
            int i8 = 0;
            while (true) {
                i4 = 3;
                if (i8 >= 4) {
                    break;
                }
                int i9 = iArr3[3 & (i8 + 3)];
                iArr4[i8] = ROTL32(i9, 14) ^ ROTL32(i9, 5);
                i8++;
            }
            for (int i10 = 0; i10 < 4; i10++) {
                for (int i11 = 0; i11 < 3; i11++) {
                    int iIndex = index(i10, i11);
                    iArr[iIndex] = iArr[iIndex] ^ iArr4[i10];
                }
            }
            for (int i12 = 0; i12 < 4; i12++) {
                iArr2[index(i12, 0)] = iArr[index(i12, 0)];
                iArr2[index(i12, 1)] = iArr[index(i12 + 3, 1)];
                iArr2[index(i12, 2)] = ROTL32(iArr[index(i12, 2)], 11);
            }
            iArr2[0] = iArr2[0] ^ this.RC[i6];
            int i13 = 0;
            while (i13 < 4) {
                int i14 = 0;
                while (i14 < i4) {
                    int i15 = i14 + 1;
                    iArr[index(i13, i14)] = ((~iArr2[index(i13, i15)]) & iArr2[index(i13, i14 + 2)]) ^ iArr2[index(i13, i14)];
                    i14 = i15;
                    i4 = 3;
                }
                i13++;
                i4 = 3;
            }
            for (int i16 = 0; i16 < 4; i16++) {
                iArr2[index(i16, 0)] = iArr[index(i16, 0)];
                iArr2[index(i16, 1)] = ROTL32(iArr[index(i16, 1)], 1);
                iArr2[index(i16, 2)] = ROTL32(iArr[index(i16 + 2, 2)], 8);
            }
            i5 = 12;
            System.arraycopy(iArr2, 0, iArr, 0, 12);
        }
        Pack.intToLittleEndian(iArr, 0, i5, this.state, 0);
        this.phase = 2;
        if (bArr != null) {
            System.arraycopy(this.state, 0, bArr, i, i2);
        }
    }

    void Down(byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i4 < i2) {
            byte[] bArr2 = this.state;
            bArr2[i4] = (byte) (bArr[i] ^ bArr2[i4]);
            i4++;
            i++;
        }
        byte[] bArr3 = this.state;
        bArr3[i2] = (byte) (bArr3[i2] ^ 1);
        byte b = bArr3[47];
        if (this.mode == MODE.ModeHash) {
            i3 &= 1;
        }
        bArr3[47] = (byte) (b ^ i3);
        this.phase = 1;
    }

    private int index(int i, int i2) {
        return ((i2 % 3) * 4) + (i % 4);
    }
}
