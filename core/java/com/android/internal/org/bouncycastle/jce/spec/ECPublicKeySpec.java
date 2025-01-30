package com.android.internal.org.bouncycastle.jce.spec;

import com.android.internal.org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes5.dex */
public class ECPublicKeySpec extends ECKeySpec {

  /* renamed from: q */
  private ECPoint f925q;

  public ECPublicKeySpec(ECPoint q, ECParameterSpec spec) {
    super(spec);
    if (q.getCurve() != null) {
      this.f925q = q.normalize();
    } else {
      this.f925q = q;
    }
  }

  public ECPoint getQ() {
    return this.f925q;
  }
}
