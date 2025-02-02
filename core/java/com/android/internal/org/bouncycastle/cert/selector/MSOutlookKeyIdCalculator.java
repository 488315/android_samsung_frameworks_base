package com.android.internal.org.bouncycastle.cert.selector;

import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.internal.org.bouncycastle.util.Pack;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.IOException;

/* loaded from: classes5.dex */
class MSOutlookKeyIdCalculator {
  MSOutlookKeyIdCalculator() {}

  static byte[] calculateKeyId(SubjectPublicKeyInfo info) {
    SHA1Digest dig = new SHA1Digest();
    byte[] hash = new byte[dig.getDigestSize()];
    byte[] bArr = new byte[0];
    try {
      byte[] spkiEnc = info.getEncoded(ASN1Encoding.DER);
      dig.update(spkiEnc, 0, spkiEnc.length);
      dig.doFinal(hash, 0);
      return hash;
    } catch (IOException e) {
      return new byte[0];
    }
  }

  private abstract static class GeneralDigest {
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

    protected GeneralDigest(GeneralDigest t) {
      this.xBuf = new byte[t.xBuf.length];
      copyIn(t);
    }

    protected void copyIn(GeneralDigest t) {
      byte[] bArr = t.xBuf;
      System.arraycopy(bArr, 0, this.xBuf, 0, bArr.length);
      this.xBufOff = t.xBufOff;
      this.byteCount = t.byteCount;
    }

    public void update(byte in) {
      byte[] bArr = this.xBuf;
      int i = this.xBufOff;
      int i2 = i + 1;
      this.xBufOff = i2;
      bArr[i] = in;
      if (i2 == bArr.length) {
        processWord(bArr, 0);
        this.xBufOff = 0;
      }
      this.byteCount++;
    }

    public void update(byte[] in, int inOff, int len) {
      while (this.xBufOff != 0 && len > 0) {
        update(in[inOff]);
        inOff++;
        len--;
      }
      while (len > this.xBuf.length) {
        processWord(in, inOff);
        byte[] bArr = this.xBuf;
        inOff += bArr.length;
        len -= bArr.length;
        this.byteCount += bArr.length;
      }
      while (len > 0) {
        update(in[inOff]);
        inOff++;
        len--;
      }
    }

    public void finish() {
      long bitLength = this.byteCount << 3;
      update(Byte.MIN_VALUE);
      while (this.xBufOff != 0) {
        update((byte) 0);
      }
      processLength(bitLength);
      processBlock();
    }

    public void reset() {
      this.byteCount = 0L;
      this.xBufOff = 0;
      int i = 0;
      while (true) {
        byte[] bArr = this.xBuf;
        if (i < bArr.length) {
          bArr[i] = 0;
          i++;
        } else {
          return;
        }
      }
    }
  }

  private static class SHA1Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 20;

    /* renamed from: Y1 */
    private static final int f746Y1 = 1518500249;

    /* renamed from: Y2 */
    private static final int f747Y2 = 1859775393;

    /* renamed from: Y3 */
    private static final int f748Y3 = -1894007588;

    /* renamed from: Y4 */
    private static final int f749Y4 = -899497514;

    /* renamed from: H1 */
    private int f750H1;

    /* renamed from: H2 */
    private int f751H2;

    /* renamed from: H3 */
    private int f752H3;

    /* renamed from: H4 */
    private int f753H4;

    /* renamed from: H5 */
    private int f754H5;

    /* renamed from: X */
    private int[] f755X = new int[80];
    private int xOff;

    public SHA1Digest() {
      reset();
    }

    public String getAlgorithmName() {
      return "SHA-1";
    }

    public int getDigestSize() {
      return 20;
    }

    @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
    protected void processWord(byte[] in, int inOff) {
      int n = in[inOff] << SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEINOUT;
      int inOff2 = inOff + 1;
      int n2 = n | ((in[inOff2] & 255) << 16);
      int inOff3 = inOff2 + 1;
      int n3 = n2 | ((in[inOff3] & 255) << 8) | (in[inOff3 + 1] & 255);
      int[] iArr = this.f755X;
      int i = this.xOff;
      iArr[i] = n3;
      int i2 = i + 1;
      this.xOff = i2;
      if (i2 == 16) {
        processBlock();
      }
    }

    @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
    protected void processLength(long bitLength) {
      if (this.xOff > 14) {
        processBlock();
      }
      int[] iArr = this.f755X;
      iArr[14] = (int) (bitLength >>> 32);
      iArr[15] = (int) ((-1) & bitLength);
    }

    public int doFinal(byte[] out, int outOff) {
      finish();
      Pack.intToBigEndian(this.f750H1, out, outOff);
      Pack.intToBigEndian(this.f751H2, out, outOff + 4);
      Pack.intToBigEndian(this.f752H3, out, outOff + 8);
      Pack.intToBigEndian(this.f753H4, out, outOff + 12);
      Pack.intToBigEndian(this.f754H5, out, outOff + 16);
      reset();
      return 20;
    }

    @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
    public void reset() {
      super.reset();
      this.f750H1 = 1732584193;
      this.f751H2 = -271733879;
      this.f752H3 = -1732584194;
      this.f753H4 = 271733878;
      this.f754H5 = -1009589776;
      this.xOff = 0;
      int i = 0;
      while (true) {
        int[] iArr = this.f755X;
        if (i != iArr.length) {
          iArr[i] = 0;
          i++;
        } else {
          return;
        }
      }
    }

    /* renamed from: f */
    private int m178f(int u, int v, int w) {
      return (u & v) | ((~u) & w);
    }

    /* renamed from: h */
    private int m180h(int u, int v, int w) {
      return (u ^ v) ^ w;
    }

    /* renamed from: g */
    private int m179g(int u, int v, int w) {
      return (u & v) | (u & w) | (v & w);
    }

    @Override // com.android.internal.org.bouncycastle.cert.selector.MSOutlookKeyIdCalculator.GeneralDigest
    protected void processBlock() {
      for (int i = 16; i < 80; i++) {
        int[] iArr = this.f755X;
        int t = ((iArr[i - 3] ^ iArr[i - 8]) ^ iArr[i - 14]) ^ iArr[i - 16];
        iArr[i] = (t << 1) | (t >>> 31);
      }
      int A = this.f750H1;
      int B = this.f751H2;
      int C = this.f752H3;
      int D = this.f753H4;
      int E = this.f754H5;
      int idx = 0;
      int j = 0;
      while (j < 4) {
        int idx2 = idx + 1;
        int E2 = E + ((A << 5) | (A >>> 27)) + m178f(B, C, D) + this.f755X[idx] + f746Y1;
        int B2 = (B << 30) | (B >>> 2);
        int idx3 = idx2 + 1;
        int D2 = D + ((E2 << 5) | (E2 >>> 27)) + m178f(A, B2, C) + this.f755X[idx2] + f746Y1;
        int A2 = (A << 30) | (A >>> 2);
        int idx4 = idx3 + 1;
        int C2 = C + ((D2 << 5) | (D2 >>> 27)) + m178f(E2, A2, B2) + this.f755X[idx3] + f746Y1;
        E = (E2 << 30) | (E2 >>> 2);
        int idx5 = idx4 + 1;
        B = B2 + ((C2 << 5) | (C2 >>> 27)) + m178f(D2, E, A2) + this.f755X[idx4] + f746Y1;
        D = (D2 << 30) | (D2 >>> 2);
        A = A2 + ((B << 5) | (B >>> 27)) + m178f(C2, D, E) + this.f755X[idx5] + f746Y1;
        C = (C2 << 30) | (C2 >>> 2);
        j++;
        idx = idx5 + 1;
      }
      int j2 = 0;
      while (j2 < 4) {
        int idx6 = idx + 1;
        int E3 = E + ((A << 5) | (A >>> 27)) + m180h(B, C, D) + this.f755X[idx] + f747Y2;
        int B3 = (B << 30) | (B >>> 2);
        int idx7 = idx6 + 1;
        int D3 = D + ((E3 << 5) | (E3 >>> 27)) + m180h(A, B3, C) + this.f755X[idx6] + f747Y2;
        int A3 = (A << 30) | (A >>> 2);
        int idx8 = idx7 + 1;
        int C3 = C + ((D3 << 5) | (D3 >>> 27)) + m180h(E3, A3, B3) + this.f755X[idx7] + f747Y2;
        E = (E3 << 30) | (E3 >>> 2);
        int idx9 = idx8 + 1;
        B = B3 + ((C3 << 5) | (C3 >>> 27)) + m180h(D3, E, A3) + this.f755X[idx8] + f747Y2;
        D = (D3 << 30) | (D3 >>> 2);
        A = A3 + ((B << 5) | (B >>> 27)) + m180h(C3, D, E) + this.f755X[idx9] + f747Y2;
        C = (C3 << 30) | (C3 >>> 2);
        j2++;
        idx = idx9 + 1;
      }
      int j3 = 0;
      while (j3 < 4) {
        int idx10 = idx + 1;
        int E4 = E + ((A << 5) | (A >>> 27)) + m179g(B, C, D) + this.f755X[idx] + f748Y3;
        int B4 = (B << 30) | (B >>> 2);
        int idx11 = idx10 + 1;
        int D4 = D + ((E4 << 5) | (E4 >>> 27)) + m179g(A, B4, C) + this.f755X[idx10] + f748Y3;
        int A4 = (A << 30) | (A >>> 2);
        int idx12 = idx11 + 1;
        int C4 = C + ((D4 << 5) | (D4 >>> 27)) + m179g(E4, A4, B4) + this.f755X[idx11] + f748Y3;
        E = (E4 << 30) | (E4 >>> 2);
        int idx13 = idx12 + 1;
        B = B4 + ((C4 << 5) | (C4 >>> 27)) + m179g(D4, E, A4) + this.f755X[idx12] + f748Y3;
        D = (D4 << 30) | (D4 >>> 2);
        A = A4 + ((B << 5) | (B >>> 27)) + m179g(C4, D, E) + this.f755X[idx13] + f748Y3;
        C = (C4 << 30) | (C4 >>> 2);
        j3++;
        idx = idx13 + 1;
      }
      int j4 = 0;
      while (j4 <= 3) {
        int idx14 = idx + 1;
        int E5 = E + ((A << 5) | (A >>> 27)) + m180h(B, C, D) + this.f755X[idx] + f749Y4;
        int B5 = (B << 30) | (B >>> 2);
        int idx15 = idx14 + 1;
        int D5 = D + ((E5 << 5) | (E5 >>> 27)) + m180h(A, B5, C) + this.f755X[idx14] + f749Y4;
        int A5 = (A << 30) | (A >>> 2);
        int idx16 = idx15 + 1;
        int C5 = C + ((D5 << 5) | (D5 >>> 27)) + m180h(E5, A5, B5) + this.f755X[idx15] + f749Y4;
        E = (E5 << 30) | (E5 >>> 2);
        int idx17 = idx16 + 1;
        B = B5 + ((C5 << 5) | (C5 >>> 27)) + m180h(D5, E, A5) + this.f755X[idx16] + f749Y4;
        D = (D5 << 30) | (D5 >>> 2);
        A = A5 + ((B << 5) | (B >>> 27)) + m180h(C5, D, E) + this.f755X[idx17] + f749Y4;
        C = (C5 << 30) | (C5 >>> 2);
        j4++;
        idx = idx17 + 1;
      }
      int j5 = this.f750H1;
      this.f750H1 = j5 + A;
      this.f751H2 += B;
      this.f752H3 += C;
      this.f753H4 += D;
      this.f754H5 += E;
      this.xOff = 0;
      for (int i2 = 0; i2 < 16; i2++) {
        this.f755X[i2] = 0;
      }
    }
  }
}
