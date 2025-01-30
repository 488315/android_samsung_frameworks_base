package com.android.internal.org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class ECPrivateKeyParameters extends ECKeyParameters {

  /* renamed from: d */
  private final BigInteger f881d;

  public ECPrivateKeyParameters(BigInteger d, ECDomainParameters parameters) {
    super(true, parameters);
    this.f881d = parameters.validatePrivateScalar(d);
  }

  public BigInteger getD() {
    return this.f881d;
  }
}
