package com.android.internal.org.bouncycastle.crypto.digests;

import com.android.internal.org.bouncycastle.util.Memoable;
import com.android.internal.org.bouncycastle.util.Pack;

/* loaded from: classes5.dex */
public class SHA1Digest extends GeneralDigest implements EncodableDigest {
    private static final int DIGEST_LENGTH = 20;
    private static final int Y1 = 1518500249;
    private static final int Y2 = 1859775393;
    private static final int Y3 = -1894007588;
    private static final int Y4 = -899497514;
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int[] X;
    private int xOff;

    private int f(int i, int i2, int i3) {
        return (i & i2) | ((~i) & i3);
    }

    private int g(int i, int i2, int i3) {
        return ((i2 | i3) & i) | (i2 & i3);
    }

    private int h(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return 20;
    }

    public SHA1Digest() {
        this.X = new int[80];
        reset();
    }

    public SHA1Digest(SHA1Digest sHA1Digest) {
        super(sHA1Digest);
        this.X = new int[80];
        copyIn(sHA1Digest);
    }

    public SHA1Digest(byte[] bArr) {
        super(bArr);
        this.X = new int[80];
        this.H1 = Pack.bigEndianToInt(bArr, 16);
        this.H2 = Pack.bigEndianToInt(bArr, 20);
        this.H3 = Pack.bigEndianToInt(bArr, 24);
        this.H4 = Pack.bigEndianToInt(bArr, 28);
        this.H5 = Pack.bigEndianToInt(bArr, 32);
        this.xOff = Pack.bigEndianToInt(bArr, 36);
        for (int i = 0; i != this.xOff; i++) {
            this.X[i] = Pack.bigEndianToInt(bArr, (i * 4) + 40);
        }
    }

    private void copyIn(SHA1Digest sHA1Digest) {
        this.H1 = sHA1Digest.H1;
        this.H2 = sHA1Digest.H2;
        this.H3 = sHA1Digest.H3;
        this.H4 = sHA1Digest.H4;
        this.H5 = sHA1Digest.H5;
        int[] iArr = sHA1Digest.X;
        System.arraycopy(iArr, 0, this.X, 0, iArr.length);
        this.xOff = sHA1Digest.xOff;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "SHA-1";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
    protected void processWord(byte[] bArr, int i) {
        int i2 = (bArr[i + 3] & 255) | (bArr[i] << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
        int[] iArr = this.X;
        int i3 = this.xOff;
        iArr[i3] = i2;
        int i4 = i3 + 1;
        this.xOff = i4;
        if (i4 == 16) {
            processBlock();
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
    protected void processLength(long j) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.X;
        iArr[14] = (int) (j >>> 32);
        iArr[15] = (int) j;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        finish();
        Pack.intToBigEndian(this.H1, bArr, i);
        Pack.intToBigEndian(this.H2, bArr, i + 4);
        Pack.intToBigEndian(this.H3, bArr, i + 8);
        Pack.intToBigEndian(this.H4, bArr, i + 12);
        Pack.intToBigEndian(this.H5, bArr, i + 16);
        reset();
        return 20;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest, com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        super.reset();
        this.H1 = 1732584193;
        this.H2 = -271733879;
        this.H3 = -1732584194;
        this.H4 = 271733878;
        this.H5 = -1009589776;
        this.xOff = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.X;
            if (i == iArr.length) {
                return;
            }
            iArr[i] = 0;
            i++;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.GeneralDigest
    protected void processBlock() {
        for (int i = 16; i < 80; i++) {
            int[] iArr = this.X;
            int i2 = ((iArr[i - 3] ^ iArr[i - 8]) ^ iArr[i - 14]) ^ iArr[i - 16];
            iArr[i] = (i2 >>> 31) | (i2 << 1);
        }
        int iH = this.H1;
        int iH2 = this.H2;
        int i3 = this.H3;
        int i4 = this.H4;
        int i5 = this.H5;
        int i6 = 0;
        for (int i7 = 0; i7 < 4; i7++) {
            int iF = i5 + ((iH << 5) | (iH >>> 27)) + f(iH2, i3, i4) + this.X[i6] + Y1;
            int i8 = (iH2 >>> 2) | (iH2 << 30);
            int iF2 = i4 + ((iF << 5) | (iF >>> 27)) + f(iH, i8, i3) + this.X[i6 + 1] + Y1;
            int i9 = (iH >>> 2) | (iH << 30);
            int iF3 = i3 + ((iF2 << 5) | (iF2 >>> 27)) + f(iF, i9, i8) + this.X[i6 + 2] + Y1;
            i5 = (iF >>> 2) | (iF << 30);
            int i10 = i6 + 4;
            iH2 = i8 + ((iF3 << 5) | (iF3 >>> 27)) + f(iF2, i5, i9) + this.X[i6 + 3] + Y1;
            i4 = (iF2 >>> 2) | (iF2 << 30);
            i6 += 5;
            iH = i9 + ((iH2 << 5) | (iH2 >>> 27)) + f(iF3, i4, i5) + this.X[i10] + Y1;
            i3 = (iF3 >>> 2) | (iF3 << 30);
        }
        for (int i11 = 0; i11 < 4; i11++) {
            int iH3 = i5 + ((iH << 5) | (iH >>> 27)) + h(iH2, i3, i4) + this.X[i6] + Y2;
            int i12 = (iH2 >>> 2) | (iH2 << 30);
            int iH4 = i4 + ((iH3 << 5) | (iH3 >>> 27)) + h(iH, i12, i3) + this.X[i6 + 1] + Y2;
            int i13 = (iH >>> 2) | (iH << 30);
            int iH5 = i3 + ((iH4 << 5) | (iH4 >>> 27)) + h(iH3, i13, i12) + this.X[i6 + 2] + Y2;
            i5 = (iH3 >>> 2) | (iH3 << 30);
            int i14 = i6 + 4;
            iH2 = i12 + ((iH5 << 5) | (iH5 >>> 27)) + h(iH4, i5, i13) + this.X[i6 + 3] + Y2;
            i4 = (iH4 >>> 2) | (iH4 << 30);
            i6 += 5;
            iH = i13 + ((iH2 << 5) | (iH2 >>> 27)) + h(iH5, i4, i5) + this.X[i14] + Y2;
            i3 = (iH5 >>> 2) | (iH5 << 30);
        }
        for (int i15 = 0; i15 < 4; i15++) {
            int iG = i5 + ((iH << 5) | (iH >>> 27)) + g(iH2, i3, i4) + this.X[i6] + Y3;
            int i16 = (iH2 >>> 2) | (iH2 << 30);
            int iG2 = i4 + ((iG << 5) | (iG >>> 27)) + g(iH, i16, i3) + this.X[i6 + 1] + Y3;
            int i17 = (iH >>> 2) | (iH << 30);
            int iG3 = i3 + ((iG2 << 5) | (iG2 >>> 27)) + g(iG, i17, i16) + this.X[i6 + 2] + Y3;
            i5 = (iG >>> 2) | (iG << 30);
            int i18 = i6 + 4;
            iH2 = i16 + ((iG3 << 5) | (iG3 >>> 27)) + g(iG2, i5, i17) + this.X[i6 + 3] + Y3;
            i4 = (iG2 >>> 2) | (iG2 << 30);
            i6 += 5;
            iH = i17 + ((iH2 << 5) | (iH2 >>> 27)) + g(iG3, i4, i5) + this.X[i18] + Y3;
            i3 = (iG3 >>> 2) | (iG3 << 30);
        }
        for (int i19 = 0; i19 <= 3; i19++) {
            int iH6 = i5 + ((iH << 5) | (iH >>> 27)) + h(iH2, i3, i4) + this.X[i6] + Y4;
            int i20 = (iH2 >>> 2) | (iH2 << 30);
            int iH7 = i4 + ((iH6 << 5) | (iH6 >>> 27)) + h(iH, i20, i3) + this.X[i6 + 1] + Y4;
            int i21 = (iH >>> 2) | (iH << 30);
            int iH8 = i3 + ((iH7 << 5) | (iH7 >>> 27)) + h(iH6, i21, i20) + this.X[i6 + 2] + Y4;
            i5 = (iH6 >>> 2) | (iH6 << 30);
            int i22 = i6 + 4;
            iH2 = i20 + ((iH8 << 5) | (iH8 >>> 27)) + h(iH7, i5, i21) + this.X[i6 + 3] + Y4;
            i4 = (iH7 >>> 2) | (iH7 << 30);
            i6 += 5;
            iH = i21 + ((iH2 << 5) | (iH2 >>> 27)) + h(iH8, i4, i5) + this.X[i22] + Y4;
            i3 = (iH8 >>> 2) | (iH8 << 30);
        }
        this.H1 += iH;
        this.H2 += iH2;
        this.H3 += i3;
        this.H4 += i4;
        this.H5 += i5;
        this.xOff = 0;
        for (int i23 = 0; i23 < 16; i23++) {
            this.X[i23] = 0;
        }
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public Memoable copy() {
        return new SHA1Digest(this);
    }

    @Override // com.android.internal.org.bouncycastle.util.Memoable
    public void reset(Memoable memoable) {
        SHA1Digest sHA1Digest = (SHA1Digest) memoable;
        super.copyIn((GeneralDigest) sHA1Digest);
        copyIn(sHA1Digest);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.EncodableDigest
    public byte[] getEncodedState() {
        byte[] bArr = new byte[(this.xOff * 4) + 40];
        super.populateState(bArr);
        Pack.intToBigEndian(this.H1, bArr, 16);
        Pack.intToBigEndian(this.H2, bArr, 20);
        Pack.intToBigEndian(this.H3, bArr, 24);
        Pack.intToBigEndian(this.H4, bArr, 28);
        Pack.intToBigEndian(this.H5, bArr, 32);
        Pack.intToBigEndian(this.xOff, bArr, 36);
        for (int i = 0; i != this.xOff; i++) {
            Pack.intToBigEndian(this.X[i], bArr, (i * 4) + 40);
        }
        return bArr;
    }
}
