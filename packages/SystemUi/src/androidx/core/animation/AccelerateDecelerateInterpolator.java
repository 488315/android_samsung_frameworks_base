package androidx.core.animation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccelerateDecelerateInterpolator implements Interpolator {
    @Override // androidx.core.animation.Interpolator
    public final float getInterpolation(float f) {
        return ((float) (Math.cos((f + 1.0f) * 3.141592653589793d) / 2.0d)) + 0.5f;
    }
}
