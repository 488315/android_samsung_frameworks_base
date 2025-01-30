package com.android.internal.org.bouncycastle.jce.spec;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes5.dex */
public class ECParameterSpec implements AlgorithmParameterSpec {

  /* renamed from: G */
  private ECPoint f921G;
  private ECCurve curve;

  /* renamed from: h */
  private BigInteger f922h;

  /* renamed from: n */
  private BigInteger f923n;
  private byte[] seed;

  public ECParameterSpec(ECCurve curve, ECPoint G, BigInteger n) {
    this.curve = curve;
    this.f921G = G.normalize();
    this.f923n = n;
    this.f922h = BigInteger.valueOf(1L);
    this.seed = null;
  }

  public ECParameterSpec(ECCurve curve, ECPoint G, BigInteger n, BigInteger h) {
    this.curve = curve;
    this.f921G = G.normalize();
    this.f923n = n;
    this.f922h = h;
    this.seed = null;
  }

  public ECParameterSpec(ECCurve curve, ECPoint G, BigInteger n, BigInteger h, byte[] seed) {
    this.curve = curve;
    this.f921G = G.normalize();
    this.f923n = n;
    this.f922h = h;
    this.seed = seed;
  }

  public ECCurve getCurve() {
    return this.curve;
  }

  public ECPoint getG() {
    return this.f921G;
  }

  public BigInteger getN() {
    return this.f923n;
  }

  public BigInteger getH() {
    return this.f922h;
  }

  public byte[] getSeed() {
    return this.seed;
  }

  public boolean equals(Object o) {
    if (!(o instanceof ECParameterSpec)) {
      return false;
    }
    ECParameterSpec other = (ECParameterSpec) o;
    return getCurve().equals(other.getCurve()) && getG().equals(other.getG());
  }

  public int hashCode() {
    return getCurve().hashCode() ^ getG().hashCode();
  }
}
