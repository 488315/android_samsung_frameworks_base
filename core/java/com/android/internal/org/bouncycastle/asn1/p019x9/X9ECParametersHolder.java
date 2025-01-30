package com.android.internal.org.bouncycastle.asn1.p019x9;

/* loaded from: classes5.dex */
public abstract class X9ECParametersHolder {
  private X9ECParameters params;

  protected abstract X9ECParameters createParameters();

  public synchronized X9ECParameters getParameters() {
    if (this.params == null) {
      this.params = createParameters();
    }
    return this.params;
  }
}
