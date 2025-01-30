package com.android.internal.org.bouncycastle.crypto.params;

import com.android.internal.org.bouncycastle.math.p026ec.ECPoint;

/* loaded from: classes5.dex */
public class ECPublicKeyParameters extends ECKeyParameters {

  /* renamed from: q */
  private final ECPoint f882q;

  public ECPublicKeyParameters(ECPoint q, ECDomainParameters parameters) {
    super(false, parameters);
    this.f882q = parameters.validatePublicPoint(q);
  }

  public ECPoint getQ() {
    return this.f882q;
  }
}
