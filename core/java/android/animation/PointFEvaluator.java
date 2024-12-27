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
        float x = startValue.x + ((endValue.x - startValue.x) * fraction);
        float y = startValue.y + ((endValue.y - startValue.y) * fraction);
        if (this.mPoint != null) {
            this.mPoint.set(x, y);
            return this.mPoint;
        }
        return new PointF(x, y);
    }
}
