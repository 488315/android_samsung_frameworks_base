package com.android.internal.org.bouncycastle.math.p026ec;


/* loaded from: classes5.dex */
public class WTauNafPreCompInfo implements PreCompInfo {
  protected ECPoint.AbstractF2m[] preComp = null;

  public ECPoint.AbstractF2m[] getPreComp() {
    return this.preComp;
  }

  public void setPreComp(ECPoint.AbstractF2m[] preComp) {
    this.preComp = preComp;
  }
}
