package com.android.internal.org.bouncycastle.cert.selector;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.util.Pack;
import java.io.IOException;

/* loaded from: classes5.dex */
class MSOutlookKeyIdCalculator {
    MSOutlookKeyIdCalculator() {
    }

    static byte[] calculateKeyId(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        SHA1Digest sHA1Digest = new SHA1Digest();
        byte[] bArr = new byte[sHA1Digest.getDigestSize()];
        try {
            byte[] encoded = subjectPublicKeyInfo.getEncoded(ASN1Encoding.DER);
            sHA1Digest.update(encoded, 0, encoded.length);
            sHA1Digest.doFinal(bArr, 0);
            return bArr;
        } catch (IOException unused) {
            return new byte[0];
        }
    }

    private static abstract class GeneralDigest {
        private static final int BYTE_LENGTH = 64;
        private long byteCount;
        private byte[] xBuf;
        private int xBufOff;

        protected abstract void processBlock();

        protected abstract void processLength(long j);

        protected abstract void processWord(byte[] bArr, int i);

        protected GeneralDigest() {
            this.xBuf = new byte[4];
            this.xBufOff = 0;
        }

        protected GeneralDigest(GeneralDigest generalDigest) {
            this.xBuf = new byte[generalDigest.xBuf.length];
            copyIn(generalDigest);
        }

        protected void copyIn(GeneralDigest generalDigest) {
            byte[] bArr = generalDigest.xBuf;
            System.arraycopy(bArr, 0, this.xBuf, 0, bArr.length);
            this.xBufOff = generalDigest.xBufOff;
            this.byteCount = generalDigest.byteCount;
        }

        public void update(byte b) {
            byte[] bArr = this.xBuf;
            int i = this.xBufOff;
            int i2 = i + 1;
            this.xBufOff = i2;
            bArr[i] = b;
            if (i2 == bArr.length) {
                processWord(bArr, 0);
                this.xBufOff = 0;
            }
            this.byteCount++;
        }

        public void update(byte[] bArr, int i, int i2) {
            while (this.xBufOff != 0 && i2 > 0) {
                update(bArr[i]);
                i++;
                i2--;
            }
            while (i2 > this.xBuf.length) {
                processWord(bArr, i);
                byte[] bArr2 = this.xBuf;
                i += bArr2.length;
                i2 -= bArr2.length;
                this.byteCount += bArr2.length;
            }
            while (i2 > 0) {
                update(bArr[i]);
                i++;
                i2--;
            }
        }

        public void finish() {
            long j = this.byteCount << 3;
            update(Byte.MIN_VALUE);
            while (this.xBufOff != 0) {
                update((byte) 0);
            }
            processLength(j);
            processBlock();
        }

        public void reset() {
            this.byteCount = 0L;
            this.xBufOff = 0;
            int i = 0;
            while (true) {
                byte[] bArr = this.xBuf;
                if (i >= bArr.length) {
                    return;
                }
                bArr[i] = 0;
                i++;
            }
        }
    }

    private static class SHA1Digest extends GeneralDigest {
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
        private int[] X = new int[80];
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

        public int getDigestSize() {
            return 20;
        }

        public SHA1Digest() {
            reset();
        }

        public String getAlgorithmName() {
            return "SHA-1";
        }

        @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
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

        @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
        protected void processLength(long j) {
            if (this.xOff > 14) {
                processBlock();
            }
            int[] iArr = this.X;
            iArr[14] = (int) (j >>> 32);
            iArr[15] = (int) j;
        }

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

        @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
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

        @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
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
    }
}
