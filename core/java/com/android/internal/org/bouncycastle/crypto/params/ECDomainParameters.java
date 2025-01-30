package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.asn1.p019x9.X9ECParameters;
import com.android.internal.org.bouncycastle.math.p026ec.ECAlgorithms;
import com.android.internal.org.bouncycastle.math.p026ec.ECConstants;
import com.android.internal.org.bouncycastle.math.p026ec.ECCurve;
import com.android.internal.org.bouncycastle.math.p026ec.ECPoint;
import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class ECDomainParameters implements ECConstants {

  /* renamed from: G */
  private final ECPoint f878G;
  private final ECCurve curve;

  /* renamed from: h */
  private final BigInteger f879h;
  private BigInteger hInv;

  /* renamed from: n */
  private final BigInteger f880n;
  private final byte[] seed;

  public ECDomainParameters(X9ECParameters x9) {
    this(x9.getCurve(), x9.getG(), x9.getN(), x9.getH(), x9.getSeed());
  }

  public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n) {
    this(curve, G, n, ONE, null);
  }

  public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n, BigInteger h) {
    this(curve, G, n, h, null);
  }

  public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n, BigInteger h, byte[] seed) {
    this.hInv = null;
    if (curve == null) {
      throw new NullPointerException("curve");
    }
    if (n == null) {
      throw new NullPointerException("n");
    }
    this.curve = curve;
    this.f878G = validatePublicPoint(curve, G);
    this.f880n = n;
    this.f879h = h;
    this.seed = Arrays.clone(seed);
  }

  public ECCurve getCurve() {
    return this.curve;
  }

  public ECPoint getG() {
    return this.f878G;
  }

  public BigInteger getN() {
    return this.f880n;
  }

  public BigInteger getH() {
    return this.f879h;
  }

  public synchronized BigInteger getHInv() {
    if (this.hInv == null) {
      this.hInv = BigIntegers.modOddInverseVar(this.f880n, this.f879h);
    }
    return this.hInv;
  }

  public byte[] getSeed() {
    return Arrays.clone(this.seed);
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ECDomainParameters)) {
      return false;
    }
    ECDomainParameters other = (ECDomainParameters) obj;
    return this.curve.equals(other.curve)
        && this.f878G.equals(other.f878G)
        && this.f880n.equals(other.f880n);
  }

  public int hashCode() {
    int hc = 4 * 257;
    return ((((hc ^ this.curve.hashCode()) * 257) ^ this.f878G.hashCode()) * 257)
        ^ this.f880n.hashCode();
  }

  public BigInteger validatePrivateScalar(BigInteger d) {
    if (d == null) {
      throw new NullPointerException("Scalar cannot be null");
    }
    if (d.compareTo(ECConstants.ONE) < 0 || d.compareTo(getN()) >= 0) {
      throw new IllegalArgumentException("Scalar is not in the interval [1, n - 1]");
    }
    return d;
  }

  public ECPoint validatePublicPoint(ECPoint q) {
    return validatePublicPoint(getCurve(), q);
  }

  static ECPoint validatePublicPoint(ECCurve c, ECPoint q) {
    if (q == null) {
      throw new NullPointerException("Point cannot be null");
    }
    ECPoint q2 = ECAlgorithms.importPoint(c, q).normalize();
    if (q2.isInfinity()) {
      throw new IllegalArgumentException("Point at infinity");
    }
    if (!q2.isValid()) {
      throw new IllegalArgumentException("Point not on curve");
    }
    return q2;
  }
}
