package android.animation;

import android.graphics.PointF;

/* loaded from: classes.dex */
public class PointFEvaluator implements TypeEvaluator<PointF> {
  private PointF mPoint;

  public PointFEvaluator() {}

  public PointFEvaluator(PointF reuse) {
    this.mPoint = reuse;
  }

  @Override // android.animation.TypeEvaluator
  public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
    float x = startValue.f87x + ((endValue.f87x - startValue.f87x) * fraction);
    float y = startValue.f88y + ((endValue.f88y - startValue.f88y) * fraction);
    PointF pointF = this.mPoint;
    if (pointF != null) {
      pointF.set(x, y);
      return this.mPoint;
    }
    return new PointF(x, y);
  }
}
