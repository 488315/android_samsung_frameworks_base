package androidx.core.animation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BounceInterpolator implements Interpolator {
    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        float f2;
        float f3;
        float f4 = f * 1.1226f;
        if (f4 < 0.3535f) {
            return f4 * f4 * 8.0f;
        }
        if (f4 < 0.7408f) {
            float f5 = f4 - 0.54719f;
            f2 = f5 * f5 * 8.0f;
            f3 = 0.7f;
        } else if (f4 < 0.9644f) {
            float f6 = f4 - 0.8526f;
            f2 = f6 * f6 * 8.0f;
            f3 = 0.9f;
        } else {
            float f7 = f4 - 1.0435f;
            f2 = f7 * f7 * 8.0f;
            f3 = 0.95f;
        }
        return f2 + f3;
    }
}
