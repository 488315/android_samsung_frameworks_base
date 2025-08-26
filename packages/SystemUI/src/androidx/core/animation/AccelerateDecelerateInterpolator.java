package androidx.core.animation;

/* loaded from: classes.dex */
public class AccelerateDecelerateInterpolator implements Interpolator {
    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        return ((float) (Math.cos((f + 1.0f) * 3.141592653589793d) / 2.0d)) + 0.5f;
    }
}
