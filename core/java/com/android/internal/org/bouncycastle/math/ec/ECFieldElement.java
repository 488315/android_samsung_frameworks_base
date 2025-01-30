package com.android.internal.org.bouncycastle.math.ec;

import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import com.android.internal.org.bouncycastle.util.Integers;
import java.math.BigInteger;
import java.util.Random;

/* loaded from: classes5.dex */
public abstract class ECFieldElement implements ECConstants {

  public abstract static class AbstractFp extends ECFieldElement {}

  public abstract ECFieldElement add(ECFieldElement eCFieldElement);

  public abstract ECFieldElement addOne();

  public abstract ECFieldElement divide(ECFieldElement eCFieldElement);

  public abstract String getFieldName();

  public abstract int getFieldSize();

  public abstract ECFieldElement invert();

  public abstract ECFieldElement multiply(ECFieldElement eCFieldElement);

  public abstract ECFieldElement negate();

  public abstract ECFieldElement sqrt();

  public abstract ECFieldElement square();

  public abstract ECFieldElement subtract(ECFieldElement eCFieldElement);

  public abstract BigInteger toBigInteger();

  public int bitLength() {
    return toBigInteger().bitLength();
  }

  public boolean isOne() {
    return bitLength() == 1;
  }

  public boolean isZero() {
    return toBigInteger().signum() == 0;
  }

  public ECFieldElement multiplyMinusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
    return multiply(b).subtract(x.multiply(y));
  }

  public ECFieldElement multiplyPlusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
    return multiply(b).add(x.multiply(y));
  }

  public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y) {
    return square().subtract(x.multiply(y));
  }

  public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y) {
    return square().add(x.multiply(y));
  }

  public ECFieldElement squarePow(int pow) {
    ECFieldElement r = this;
    for (int i = 0; i < pow; i++) {
      r = r.square();
    }
    return r;
  }

  public boolean testBitZero() {
    return toBigInteger().testBit(0);
  }

  public String toString() {
    return toBigInteger().toString(16);
  }

  public byte[] getEncoded() {
    return BigIntegers.asUnsignedByteArray((getFieldSize() + 7) / 8, toBigInteger());
  }

  /* renamed from: com.android.internal.org.bouncycastle.math.ec.ECFieldElement$Fp */
  public static class C4596Fp extends AbstractFp {

    /* renamed from: q */
    BigInteger f938q;

    /* renamed from: r */
    BigInteger f939r;

    /* renamed from: x */
    BigInteger f940x;

    static BigInteger calculateResidue(BigInteger p) {
      int bitLength = p.bitLength();
      if (bitLength >= 96) {
        BigInteger firstWord = p.shiftRight(bitLength - 64);
        if (firstWord.longValue() == -1) {
          return ONE.shiftLeft(bitLength).subtract(p);
        }
        return null;
      }
      return null;
    }

    public C4596Fp(BigInteger q, BigInteger x) {
      this(q, calculateResidue(q), x);
    }

    C4596Fp(BigInteger q, BigInteger r, BigInteger x) {
      if (x == null || x.signum() < 0 || x.compareTo(q) >= 0) {
        throw new IllegalArgumentException("x value invalid in Fp field element");
      }
      this.f938q = q;
      this.f939r = r;
      this.f940x = x;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
      return this.f940x;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
      return "Fp";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
      return this.f938q.bitLength();
    }

    public BigInteger getQ() {
      return this.f938q;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement b) {
      return new C4596Fp(this.f938q, this.f939r, modAdd(this.f940x, b.toBigInteger()));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
      BigInteger x2 = this.f940x.add(ECConstants.ONE);
      if (x2.compareTo(this.f938q) == 0) {
        x2 = ECConstants.ZERO;
      }
      return new C4596Fp(this.f938q, this.f939r, x2);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement b) {
      return new C4596Fp(this.f938q, this.f939r, modSubtract(this.f940x, b.toBigInteger()));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement b) {
      return new C4596Fp(this.f938q, this.f939r, modMult(this.f940x, b.toBigInteger()));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiplyMinusProduct(
        ECFieldElement b, ECFieldElement x, ECFieldElement y) {
      BigInteger ax = this.f940x;
      BigInteger bx = b.toBigInteger();
      BigInteger xx = x.toBigInteger();
      BigInteger yx = y.toBigInteger();
      BigInteger ab = ax.multiply(bx);
      BigInteger xy = xx.multiply(yx);
      return new C4596Fp(this.f938q, this.f939r, modReduce(ab.subtract(xy)));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiplyPlusProduct(
        ECFieldElement b, ECFieldElement x, ECFieldElement y) {
      BigInteger ax = this.f940x;
      BigInteger bx = b.toBigInteger();
      BigInteger xx = x.toBigInteger();
      BigInteger yx = y.toBigInteger();
      BigInteger ab = ax.multiply(bx);
      BigInteger xy = xx.multiply(yx);
      return new C4596Fp(this.f938q, this.f939r, modReduce(ab.add(xy)));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement b) {
      return new C4596Fp(this.f938q, this.f939r, modMult(this.f940x, modInverse(b.toBigInteger())));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
      if (this.f940x.signum() == 0) {
        return this;
      }
      BigInteger bigInteger = this.f938q;
      return new C4596Fp(bigInteger, this.f939r, bigInteger.subtract(this.f940x));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
      BigInteger bigInteger = this.f938q;
      BigInteger bigInteger2 = this.f939r;
      BigInteger bigInteger3 = this.f940x;
      return new C4596Fp(bigInteger, bigInteger2, modMult(bigInteger3, bigInteger3));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y) {
      BigInteger ax = this.f940x;
      BigInteger xx = x.toBigInteger();
      BigInteger yx = y.toBigInteger();
      BigInteger aa = ax.multiply(ax);
      BigInteger xy = xx.multiply(yx);
      return new C4596Fp(this.f938q, this.f939r, modReduce(aa.subtract(xy)));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y) {
      BigInteger ax = this.f940x;
      BigInteger xx = x.toBigInteger();
      BigInteger yx = y.toBigInteger();
      BigInteger aa = ax.multiply(ax);
      BigInteger xy = xx.multiply(yx);
      return new C4596Fp(this.f938q, this.f939r, modReduce(aa.add(xy)));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
      return new C4596Fp(this.f938q, this.f939r, modInverse(this.f940x));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
      if (isZero() || isOne()) {
        return this;
      }
      if (!this.f938q.testBit(0)) {
        throw new RuntimeException("not done yet");
      }
      if (this.f938q.testBit(1)) {
        BigInteger e = this.f938q.shiftRight(2).add(ECConstants.ONE);
        BigInteger bigInteger = this.f938q;
        return checkSqrt(new C4596Fp(bigInteger, this.f939r, this.f940x.modPow(e, bigInteger)));
      }
      BigInteger e2 = this.f938q;
      if (e2.testBit(2)) {
        BigInteger t1 = this.f940x.modPow(this.f938q.shiftRight(3), this.f938q);
        BigInteger t2 = modMult(t1, this.f940x);
        BigInteger t3 = modMult(t2, t1);
        if (t3.equals(ECConstants.ONE)) {
          return checkSqrt(new C4596Fp(this.f938q, this.f939r, t2));
        }
        BigInteger t4 = ECConstants.TWO.modPow(this.f938q.shiftRight(2), this.f938q);
        BigInteger y = modMult(t2, t4);
        return checkSqrt(new C4596Fp(this.f938q, this.f939r, y));
      }
      BigInteger legendreExponent = this.f938q.shiftRight(1);
      if (!this.f940x.modPow(legendreExponent, this.f938q).equals(ECConstants.ONE)) {
        return null;
      }
      BigInteger X = this.f940x;
      BigInteger fourX = modDouble(modDouble(X));
      BigInteger k = legendreExponent.add(ECConstants.ONE);
      BigInteger qMinusOne = this.f938q.subtract(ECConstants.ONE);
      Random rand = new Random();
      while (true) {
        BigInteger P = new BigInteger(this.f938q.bitLength(), rand);
        if (P.compareTo(this.f938q) < 0
            && modReduce(P.multiply(P).subtract(fourX))
                .modPow(legendreExponent, this.f938q)
                .equals(qMinusOne)) {
          BigInteger[] result = lucasSequence(P, X, k);
          BigInteger U = result[0];
          BigInteger V = result[1];
          if (modMult(V, V).equals(fourX)) {
            return new C4596Fp(this.f938q, this.f939r, modHalfAbs(V));
          }
          if (!U.equals(ECConstants.ONE) && !U.equals(qMinusOne)) {
            return null;
          }
        }
      }
    }

    private ECFieldElement checkSqrt(ECFieldElement z) {
      if (z.square().equals(this)) {
        return z;
      }
      return null;
    }

    private BigInteger[] lucasSequence(BigInteger P, BigInteger Q, BigInteger k) {
      int n = k.bitLength();
      int s = k.getLowestSetBit();
      BigInteger Uh = ECConstants.ONE;
      BigInteger Vl = ECConstants.TWO;
      BigInteger Vh = P;
      BigInteger Ql = ECConstants.ONE;
      BigInteger Qh = ECConstants.ONE;
      for (int j = n - 1; j >= s + 1; j--) {
        Ql = modMult(Ql, Qh);
        if (k.testBit(j)) {
          Qh = modMult(Ql, Q);
          Uh = modMult(Uh, Vh);
          Vl = modReduce(Vh.multiply(Vl).subtract(P.multiply(Ql)));
          Vh = modReduce(Vh.multiply(Vh).subtract(Qh.shiftLeft(1)));
        } else {
          Qh = Ql;
          Uh = modReduce(Uh.multiply(Vl).subtract(Ql));
          Vh = modReduce(Vh.multiply(Vl).subtract(P.multiply(Ql)));
          Vl = modReduce(Vl.multiply(Vl).subtract(Ql.shiftLeft(1)));
        }
      }
      BigInteger Ql2 = modMult(Ql, Qh);
      BigInteger Qh2 = modMult(Ql2, Q);
      BigInteger Uh2 = modReduce(Uh.multiply(Vl).subtract(Ql2));
      BigInteger Vl2 = modReduce(Vh.multiply(Vl).subtract(P.multiply(Ql2)));
      BigInteger Ql3 = modMult(Ql2, Qh2);
      for (int j2 = 1; j2 <= s; j2++) {
        Uh2 = modMult(Uh2, Vl2);
        Vl2 = modReduce(Vl2.multiply(Vl2).subtract(Ql3.shiftLeft(1)));
        Ql3 = modMult(Ql3, Ql3);
      }
      return new BigInteger[] {Uh2, Vl2};
    }

    protected BigInteger modAdd(BigInteger x1, BigInteger x2) {
      BigInteger x3 = x1.add(x2);
      if (x3.compareTo(this.f938q) >= 0) {
        return x3.subtract(this.f938q);
      }
      return x3;
    }

    protected BigInteger modDouble(BigInteger x) {
      BigInteger _2x = x.shiftLeft(1);
      if (_2x.compareTo(this.f938q) >= 0) {
        return _2x.subtract(this.f938q);
      }
      return _2x;
    }

    protected BigInteger modHalf(BigInteger x) {
      if (x.testBit(0)) {
        x = this.f938q.add(x);
      }
      return x.shiftRight(1);
    }

    protected BigInteger modHalfAbs(BigInteger x) {
      if (x.testBit(0)) {
        x = this.f938q.subtract(x);
      }
      return x.shiftRight(1);
    }

    protected BigInteger modInverse(BigInteger x) {
      return BigIntegers.modOddInverse(this.f938q, x);
    }

    protected BigInteger modMult(BigInteger x1, BigInteger x2) {
      return modReduce(x1.multiply(x2));
    }

    protected BigInteger modReduce(BigInteger x) {
      if (this.f939r != null) {
        boolean negative = x.signum() < 0;
        if (negative) {
          x = x.abs();
        }
        int qLen = this.f938q.bitLength();
        boolean rIsOne = this.f939r.equals(ECConstants.ONE);
        while (x.bitLength() > qLen + 1) {
          BigInteger u = x.shiftRight(qLen);
          BigInteger v = x.subtract(u.shiftLeft(qLen));
          if (!rIsOne) {
            u = u.multiply(this.f939r);
          }
          x = u.add(v);
        }
        while (x.compareTo(this.f938q) >= 0) {
          x = x.subtract(this.f938q);
        }
        if (negative && x.signum() != 0) {
          return this.f938q.subtract(x);
        }
        return x;
      }
      return x.mod(this.f938q);
    }

    protected BigInteger modSubtract(BigInteger x1, BigInteger x2) {
      BigInteger x3 = x1.subtract(x2);
      if (x3.signum() < 0) {
        return x3.add(this.f938q);
      }
      return x3;
    }

    public boolean equals(Object other) {
      if (other == this) {
        return true;
      }
      if (!(other instanceof C4596Fp)) {
        return false;
      }
      C4596Fp o = (C4596Fp) other;
      return this.f938q.equals(o.f938q) && this.f940x.equals(o.f940x);
    }

    public int hashCode() {
      return this.f938q.hashCode() ^ this.f940x.hashCode();
    }
  }

  public abstract static class AbstractF2m extends ECFieldElement {
    public ECFieldElement halfTrace() {
      int m = getFieldSize();
      if ((m & 1) == 0) {
        throw new IllegalStateException("Half-trace only defined for odd m");
      }
      int n = (m + 1) >>> 1;
      int k = 31 - Integers.numberOfLeadingZeros(n);
      int nk = 1;
      ECFieldElement ht = this;
      while (k > 0) {
        ht = ht.squarePow(nk << 1).add(ht);
        k--;
        nk = n >>> k;
        if ((nk & 1) != 0) {
          ht = ht.squarePow(2).add(this);
        }
      }
      return ht;
    }

    public boolean hasFastTrace() {
      return false;
    }

    public int trace() {
      int m = getFieldSize();
      int k = 31 - Integers.numberOfLeadingZeros(m);
      int mk = 1;
      ECFieldElement tr = this;
      while (k > 0) {
        tr = tr.squarePow(mk).add(tr);
        k--;
        mk = m >>> k;
        if ((mk & 1) != 0) {
          tr = tr.square().add(this);
        }
      }
      if (tr.isZero()) {
        return 0;
      }
      if (tr.isOne()) {
        return 1;
      }
      throw new IllegalStateException("Internal error in trace calculation");
    }
  }

  public static class F2m extends AbstractF2m {
    public static final int GNB = 1;
    public static final int PPB = 3;
    public static final int TPB = 2;

    /* renamed from: ks */
    private int[] f935ks;

    /* renamed from: m */
    private int f936m;
    private int representation;

    /* renamed from: x */
    LongArray f937x;

    public F2m(int m, int k1, int k2, int k3, BigInteger x) {
      if (x == null || x.signum() < 0 || x.bitLength() > m) {
        throw new IllegalArgumentException("x value invalid in F2m field element");
      }
      if (k2 == 0 && k3 == 0) {
        this.representation = 2;
        this.f935ks = new int[] {k1};
      } else {
        if (k2 >= k3) {
          throw new IllegalArgumentException("k2 must be smaller than k3");
        }
        if (k2 <= 0) {
          throw new IllegalArgumentException("k2 must be larger than 0");
        }
        this.representation = 3;
        this.f935ks = new int[] {k1, k2, k3};
      }
      this.f936m = m;
      this.f937x = new LongArray(x);
    }

    F2m(int m, int[] ks, LongArray x) {
      this.f936m = m;
      this.representation = ks.length == 1 ? 2 : 3;
      this.f935ks = ks;
      this.f937x = x;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int bitLength() {
      return this.f937x.degree();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
      return this.f937x.isOne();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
      return this.f937x.isZero();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
      return this.f937x.testBitZero();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
      return this.f937x.toBigInteger();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
      return "F2m";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
      return this.f936m;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement b) {
      LongArray iarrClone = (LongArray) this.f937x.clone();
      F2m bF2m = (F2m) b;
      iarrClone.addShiftedByWords(bF2m.f937x, 0);
      return new F2m(this.f936m, this.f935ks, iarrClone);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
      return new F2m(this.f936m, this.f935ks, this.f937x.addOne());
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement b) {
      return add(b);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement b) {
      int i = this.f936m;
      int[] iArr = this.f935ks;
      return new F2m(i, iArr, this.f937x.modMultiply(((F2m) b).f937x, i, iArr));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiplyMinusProduct(
        ECFieldElement b, ECFieldElement x, ECFieldElement y) {
      return multiplyPlusProduct(b, x, y);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiplyPlusProduct(
        ECFieldElement b, ECFieldElement x, ECFieldElement y) {
      LongArray ax = this.f937x;
      LongArray bx = ((F2m) b).f937x;
      LongArray xx = ((F2m) x).f937x;
      LongArray yx = ((F2m) y).f937x;
      LongArray ab = ax.multiply(bx, this.f936m, this.f935ks);
      LongArray xy = xx.multiply(yx, this.f936m, this.f935ks);
      if (ab == ax || ab == bx) {
        ab = (LongArray) ab.clone();
      }
      ab.addShiftedByWords(xy, 0);
      ab.reduce(this.f936m, this.f935ks);
      return new F2m(this.f936m, this.f935ks, ab);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement b) {
      ECFieldElement bInv = b.invert();
      return multiply(bInv);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
      return this;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
      int i = this.f936m;
      int[] iArr = this.f935ks;
      return new F2m(i, iArr, this.f937x.modSquare(i, iArr));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y) {
      return squarePlusProduct(x, y);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y) {
      LongArray ax = this.f937x;
      LongArray xx = ((F2m) x).f937x;
      LongArray yx = ((F2m) y).f937x;
      LongArray aa = ax.square(this.f936m, this.f935ks);
      LongArray xy = xx.multiply(yx, this.f936m, this.f935ks);
      if (aa == ax) {
        aa = (LongArray) aa.clone();
      }
      aa.addShiftedByWords(xy, 0);
      aa.reduce(this.f936m, this.f935ks);
      return new F2m(this.f936m, this.f935ks, aa);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement squarePow(int pow) {
      if (pow < 1) {
        return this;
      }
      int i = this.f936m;
      int[] iArr = this.f935ks;
      return new F2m(i, iArr, this.f937x.modSquareN(pow, i, iArr));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
      int i = this.f936m;
      int[] iArr = this.f935ks;
      return new F2m(i, iArr, this.f937x.modInverse(i, iArr));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
      return (this.f937x.isZero() || this.f937x.isOne()) ? this : squarePow(this.f936m - 1);
    }

    public int getRepresentation() {
      return this.representation;
    }

    public int getM() {
      return this.f936m;
    }

    public int getK1() {
      return this.f935ks[0];
    }

    public int getK2() {
      int[] iArr = this.f935ks;
      if (iArr.length >= 2) {
        return iArr[1];
      }
      return 0;
    }

    public int getK3() {
      int[] iArr = this.f935ks;
      if (iArr.length >= 3) {
        return iArr[2];
      }
      return 0;
    }

    public boolean equals(Object anObject) {
      if (anObject == this) {
        return true;
      }
      if (!(anObject instanceof F2m)) {
        return false;
      }
      F2m b = (F2m) anObject;
      return this.f936m == b.f936m
          && this.representation == b.representation
          && Arrays.areEqual(this.f935ks, b.f935ks)
          && this.f937x.equals(b.f937x);
    }

    public int hashCode() {
      return (this.f937x.hashCode() ^ this.f936m) ^ Arrays.hashCode(this.f935ks);
    }
  }
}
