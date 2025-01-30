package com.android.internal.org.bouncycastle.math.p026ec.endo;

import com.android.internal.org.bouncycastle.math.p026ec.ECCurve;
import com.android.internal.org.bouncycastle.math.p026ec.ECPointMap;
import com.android.internal.org.bouncycastle.math.p026ec.ScaleYNegateXPointMap;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class GLVTypeAEndomorphism implements GLVEndomorphism {
  protected final GLVTypeAParameters parameters;
  protected final ECPointMap pointMap;

  public GLVTypeAEndomorphism(ECCurve curve, GLVTypeAParameters parameters) {
    this.parameters = parameters;
    this.pointMap = new ScaleYNegateXPointMap(curve.fromBigInteger(parameters.getI()));
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
