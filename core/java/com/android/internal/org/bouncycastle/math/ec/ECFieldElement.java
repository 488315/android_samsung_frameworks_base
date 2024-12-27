package com.android.internal.org.bouncycastle.math.ec;

import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import com.android.internal.org.bouncycastle.util.Integers;
import java.math.BigInteger;
import java.util.Random;

/* loaded from: classes5.dex */
public abstract class ECFieldElement implements ECConstants {

    public static abstract class AbstractFp extends ECFieldElement {
    }

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

    public static class Fp extends AbstractFp {
        BigInteger q;
        BigInteger r;
        BigInteger x;

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

        public Fp(BigInteger q, BigInteger x) {
            this(q, calculateResidue(q), x);
        }

        Fp(BigInteger q, BigInteger r, BigInteger x) {
            if (x == null || x.signum() < 0 || x.compareTo(q) >= 0) {
                throw new IllegalArgumentException("x value invalid in Fp field element");
            }
            this.q = q;
            this.r = r;
            this.x = x;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public BigInteger toBigInteger() {
            return this.x;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public String getFieldName() {
            return "Fp";
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public int getFieldSize() {
            return this.q.bitLength();
        }

        public BigInteger getQ() {
            return this.q;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement add(ECFieldElement b) {
            return new Fp(this.q, this.r, modAdd(this.x, b.toBigInteger()));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement addOne() {
            BigInteger x2 = this.x.add(ECConstants.ONE);
            if (x2.compareTo(this.q) == 0) {
                x2 = ECConstants.ZERO;
            }
            return new Fp(this.q, this.r, x2);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement subtract(ECFieldElement b) {
            return new Fp(this.q, this.r, modSubtract(this.x, b.toBigInteger()));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiply(ECFieldElement b) {
            return new Fp(this.q, this.r, modMult(this.x, b.toBigInteger()));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiplyMinusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
            BigInteger ax = this.x;
            BigInteger bx = b.toBigInteger();
            BigInteger xx = x.toBigInteger();
            BigInteger yx = y.toBigInteger();
            BigInteger ab = ax.multiply(bx);
            BigInteger xy = xx.multiply(yx);
            return new Fp(this.q, this.r, modReduce(ab.subtract(xy)));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiplyPlusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
            BigInteger ax = this.x;
            BigInteger bx = b.toBigInteger();
            BigInteger xx = x.toBigInteger();
            BigInteger yx = y.toBigInteger();
            BigInteger ab = ax.multiply(bx);
            BigInteger xy = xx.multiply(yx);
            return new Fp(this.q, this.r, modReduce(ab.add(xy)));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement divide(ECFieldElement b) {
            return new Fp(this.q, this.r, modMult(this.x, modInverse(b.toBigInteger())));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement negate() {
            return this.x.signum() == 0 ? this : new Fp(this.q, this.r, this.q.subtract(this.x));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement square() {
            return new Fp(this.q, this.r, modMult(this.x, this.x));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y) {
            BigInteger ax = this.x;
            BigInteger xx = x.toBigInteger();
            BigInteger yx = y.toBigInteger();
            BigInteger aa = ax.multiply(ax);
            BigInteger xy = xx.multiply(yx);
            return new Fp(this.q, this.r, modReduce(aa.subtract(xy)));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y) {
            BigInteger ax = this.x;
            BigInteger xx = x.toBigInteger();
            BigInteger yx = y.toBigInteger();
            BigInteger aa = ax.multiply(ax);
            BigInteger xy = xx.multiply(yx);
            return new Fp(this.q, this.r, modReduce(aa.add(xy)));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement invert() {
            return new Fp(this.q, this.r, modInverse(this.x));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement sqrt() {
            if (isZero() || isOne()) {
                return this;
            }
            if (!this.q.testBit(0)) {
                throw new RuntimeException("not done yet");
            }
            if (this.q.testBit(1)) {
                BigInteger e = this.q.shiftRight(2).add(ECConstants.ONE);
                return checkSqrt(new Fp(this.q, this.r, this.x.modPow(e, this.q)));
            }
            BigInteger e2 = this.q;
            if (e2.testBit(2)) {
                BigInteger t1 = this.x.modPow(this.q.shiftRight(3), this.q);
                BigInteger t2 = modMult(t1, this.x);
                BigInteger t3 = modMult(t2, t1);
                if (t3.equals(ECConstants.ONE)) {
                    return checkSqrt(new Fp(this.q, this.r, t2));
                }
                BigInteger t4 = ECConstants.TWO.modPow(this.q.shiftRight(2), this.q);
                BigInteger y = modMult(t2, t4);
                return checkSqrt(new Fp(this.q, this.r, y));
            }
            BigInteger legendreExponent = this.q.shiftRight(1);
            if (!this.x.modPow(legendreExponent, this.q).equals(ECConstants.ONE)) {
                return null;
            }
            BigInteger X = this.x;
            BigInteger fourX = modDouble(modDouble(X));
            BigInteger k = legendreExponent.add(ECConstants.ONE);
            BigInteger qMinusOne = this.q.subtract(ECConstants.ONE);
            Random rand = new Random();
            while (true) {
                BigInteger P = new BigInteger(this.q.bitLength(), rand);
                if (P.compareTo(this.q) < 0 && modReduce(P.multiply(P).subtract(fourX)).modPow(legendreExponent, this.q).equals(qMinusOne)) {
                    BigInteger[] result = lucasSequence(P, X, k);
                    BigInteger U = result[0];
                    BigInteger V = result[1];
                    if (modMult(V, V).equals(fourX)) {
                        return new Fp(this.q, this.r, modHalfAbs(V));
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
            return new BigInteger[]{Uh2, Vl2};
        }

        protected BigInteger modAdd(BigInteger x1, BigInteger x2) {
            BigInteger x3 = x1.add(x2);
            if (x3.compareTo(this.q) >= 0) {
                return x3.subtract(this.q);
            }
            return x3;
        }

        protected BigInteger modDouble(BigInteger x) {
            BigInteger _2x = x.shiftLeft(1);
            if (_2x.compareTo(this.q) >= 0) {
                return _2x.subtract(this.q);
            }
            return _2x;
        }

        protected BigInteger modHalf(BigInteger x) {
            if (x.testBit(0)) {
                x = this.q.add(x);
            }
            return x.shiftRight(1);
        }

        protected BigInteger modHalfAbs(BigInteger x) {
            if (x.testBit(0)) {
                x = this.q.subtract(x);
            }
            return x.shiftRight(1);
        }

        protected BigInteger modInverse(BigInteger x) {
            return BigIntegers.modOddInverse(this.q, x);
        }

        protected BigInteger modMult(BigInteger x1, BigInteger x2) {
            return modReduce(x1.multiply(x2));
        }

        protected BigInteger modReduce(BigInteger x) {
            if (this.r != null) {
                boolean negative = x.signum() < 0;
                if (negative) {
                    x = x.abs();
                }
                int qLen = this.q.bitLength();
                boolean rIsOne = this.r.equals(ECConstants.ONE);
                while (x.bitLength() > qLen + 1) {
                    BigInteger u = x.shiftRight(qLen);
                    BigInteger v = x.subtract(u.shiftLeft(qLen));
                    if (!rIsOne) {
                        u = u.multiply(this.r);
                    }
                    x = u.add(v);
                }
                while (x.compareTo(this.q) >= 0) {
                    x = x.subtract(this.q);
                }
                if (negative && x.signum() != 0) {
                    return this.q.subtract(x);
                }
                return x;
            }
            return x.mod(this.q);
        }

        protected BigInteger modSubtract(BigInteger x1, BigInteger x2) {
            BigInteger x3 = x1.subtract(x2);
            if (x3.signum() < 0) {
                return x3.add(this.q);
            }
            return x3;
        }

        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Fp)) {
                return false;
            }
            Fp o = (Fp) other;
            return this.q.equals(o.q) && this.x.equals(o.x);
        }

        public int hashCode() {
            return this.q.hashCode() ^ this.x.hashCode();
        }
    }

    public static abstract class AbstractF2m extends ECFieldElement {
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
        private int[] ks;
        private int m;
        private int representation;
        LongArray x;

        public F2m(int m, int k1, int k2, int k3, BigInteger x) {
            if (x == null || x.signum() < 0 || x.bitLength() > m) {
                throw new IllegalArgumentException("x value invalid in F2m field element");
            }
            if (k2 == 0 && k3 == 0) {
                this.representation = 2;
                this.ks = new int[]{k1};
            } else {
                if (k2 >= k3) {
                    throw new IllegalArgumentException("k2 must be smaller than k3");
                }
                if (k2 <= 0) {
                    throw new IllegalArgumentException("k2 must be larger than 0");
                }
                this.representation = 3;
                this.ks = new int[]{k1, k2, k3};
            }
            this.m = m;
            this.x = new LongArray(x);
        }

        F2m(int m, int[] ks, LongArray x) {
            this.m = m;
            this.representation = ks.length == 1 ? 2 : 3;
            this.ks = ks;
            this.x = x;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public int bitLength() {
            return this.x.degree();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public boolean isOne() {
            return this.x.isOne();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public boolean isZero() {
            return this.x.isZero();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public boolean testBitZero() {
            return this.x.testBitZero();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public BigInteger toBigInteger() {
            return this.x.toBigInteger();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public String getFieldName() {
            return "F2m";
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public int getFieldSize() {
            return this.m;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement add(ECFieldElement b) {
            LongArray iarrClone = (LongArray) this.x.clone();
            F2m bF2m = (F2m) b;
            iarrClone.addShiftedByWords(bF2m.x, 0);
            return new F2m(this.m, this.ks, iarrClone);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement addOne() {
            return new F2m(this.m, this.ks, this.x.addOne());
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement subtract(ECFieldElement b) {
            return add(b);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiply(ECFieldElement b) {
            return new F2m(this.m, this.ks, this.x.modMultiply(((F2m) b).x, this.m, this.ks));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiplyMinusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
            return multiplyPlusProduct(b, x, y);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiplyPlusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y) {
            LongArray ax = this.x;
            LongArray bx = ((F2m) b).x;
            LongArray xx = ((F2m) x).x;
            LongArray yx = ((F2m) y).x;
            LongArray ab = ax.multiply(bx, this.m, this.ks);
            LongArray xy = xx.multiply(yx, this.m, this.ks);
            if (ab == ax || ab == bx) {
                ab = (LongArray) ab.clone();
            }
            ab.addShiftedByWords(xy, 0);
            ab.reduce(this.m, this.ks);
            return new F2m(this.m, this.ks, ab);
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
            return new F2m(this.m, this.ks, this.x.modSquare(this.m, this.ks));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y) {
            return squarePlusProduct(x, y);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y) {
            LongArray ax = this.x;
            LongArray xx = ((F2m) x).x;
            LongArray yx = ((F2m) y).x;
            LongArray aa = ax.square(this.m, this.ks);
            LongArray xy = xx.multiply(yx, this.m, this.ks);
            if (aa == ax) {
                aa = (LongArray) aa.clone();
            }
            aa.addShiftedByWords(xy, 0);
            aa.reduce(this.m, this.ks);
            return new F2m(this.m, this.ks, aa);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squarePow(int pow) {
            return pow < 1 ? this : new F2m(this.m, this.ks, this.x.modSquareN(pow, this.m, this.ks));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement invert() {
            return new F2m(this.m, this.ks, this.x.modInverse(this.m, this.ks));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement sqrt() {
            return (this.x.isZero() || this.x.isOne()) ? this : squarePow(this.m - 1);
        }

        public int getRepresentation() {
            return this.representation;
        }

        public int getM() {
            return this.m;
        }

        public int getK1() {
            return this.ks[0];
        }

        public int getK2() {
            if (this.ks.length >= 2) {
                return this.ks[1];
            }
            return 0;
        }

        public int getK3() {
            if (this.ks.length >= 3) {
                return this.ks[2];
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
            return this.m == b.m && this.representation == b.representation && Arrays.areEqual(this.ks, b.ks) && this.x.equals(b.x);
        }

        public int hashCode() {
            return (this.x.hashCode() ^ this.m) ^ Arrays.hashCode(this.ks);
        }
    }
}
