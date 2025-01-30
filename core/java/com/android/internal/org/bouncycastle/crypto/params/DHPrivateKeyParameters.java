package com.android.internal.org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes5.dex */
public class DHPrivateKeyParameters extends DHKeyParameters {

  /* renamed from: x */
  private BigInteger f869x;

  public DHPrivateKeyParameters(BigInteger x, DHParameters params) {
    super(true, params);
    this.f869x = x;
  }

  public BigInteger getX() {
    return this.f869x;
  }

  @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
  public int hashCode() {
    return this.f869x.hashCode() ^ super.hashCode();
  }

  @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
  public boolean equals(Object obj) {
    if (!(obj instanceof DHPrivateKeyParameters)) {
      return false;
    }
    DHPrivateKeyParameters other = (DHPrivateKeyParameters) obj;
    return other.getX().equals(this.f869x) && super.equals(obj);
  }
}
