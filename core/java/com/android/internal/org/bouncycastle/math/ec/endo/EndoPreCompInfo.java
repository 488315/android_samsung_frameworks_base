package com.android.internal.org.bouncycastle.math.ec.endo;

import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import com.android.internal.org.bouncycastle.math.ec.PreCompInfo;

/* loaded from: classes5.dex */
public class EndoPreCompInfo implements PreCompInfo {
  protected ECEndomorphism endomorphism;
  protected ECPoint mappedPoint;

  public ECEndomorphism getEndomorphism() {
    return this.endomorphism;
  }

  public void setEndomorphism(ECEndomorphism endomorphism) {
    this.endomorphism = endomorphism;
  }

  public ECPoint getMappedPoint() {
    return this.mappedPoint;
  }

  public void setMappedPoint(ECPoint mappedPoint) {
    this.mappedPoint = mappedPoint;
  }
}
