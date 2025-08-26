package androidx.core.animation;

/* loaded from: classes.dex */
public class BounceInterpolator implements Interpolator {
    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        float f2 = f * 1.1226f;
        if (f2 < 0.3535f) {
            return f2 * f2 * 8.0f;
        }
        if (f2 < 0.7408f) {
            float f3 = f2 - 0.54719f;
            return (f3 * f3 * 8.0f) + 0.7f;
        }
        if (f2 < 0.9644f) {
            float f4 = f2 - 0.8526f;
            return (f4 * f4 * 8.0f) + 0.9f;
        }
        float f5 = f2 - 1.0435f;
        return (f5 * f5 * 8.0f) + 0.95f;
    }
}
