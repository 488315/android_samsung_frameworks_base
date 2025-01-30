package com.android.internal.org.bouncycastle.math.p026ec.endo;

import com.android.internal.org.bouncycastle.math.p026ec.ECCurve;
import com.android.internal.org.bouncycastle.math.p026ec.ECPointMap;
import com.android.internal.org.bouncycastle.math.p026ec.ScaleXPointMap;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class GLVTypeBEndomorphism implements GLVEndomorphism {
  protected final GLVTypeBParameters parameters;
  protected final ECPointMap pointMap;

  public GLVTypeBEndomorphism(ECCurve curve, GLVTypeBParameters parameters) {
    this.parameters = parameters;
    this.pointMap = new ScaleXPointMap(curve.fromBigInteger(parameters.getBeta()));
  }

  @Override // com.android.internal.org.bouncycastle.math.p026ec.endo.GLVEndomorphism
  public BigInteger[] decomposeScalar(BigInteger k) {
    return EndoUtil.decomposeScalar(this.parameters.getSplitParams(), k);
  }

  @Override // com.android.internal.org.bouncycastle.math.p026ec.endo.ECEndomorphism
  public ECPointMap getPointMap() {
    return this.pointMap;
  }

  @Override // com.android.internal.org.bouncycastle.math.p026ec.endo.ECEndomorphism
  public boolean hasEfficientPointMap() {
    return true;
  }
}
