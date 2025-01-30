package androidx.appcompat.animation;

import android.view.animation.Interpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslElasticInterpolator implements Interpolator {
    public final float mAmplitude;
    public final float mPeriod;

    public SeslElasticInterpolator(float f, float f2) {
        this.mAmplitude = f;
        this.mPeriod = f2;
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        float f2;
        float f3 = this.mAmplitude;
        float f4 = this.mPeriod;
        if (f == 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f4 == 0.0f) {
            f4 = 0.3f;
        }
        if (f3 == 0.0f || f3 < 1.0f) {
            f2 = f4 / 4.0f;
            f3 = 1.0f;
        } else {
            f2 = (float) (Math.asin(1.0f / f3) * (f4 / 6.283185307179586d));
        }
        return (float) ((Math.sin(((f - f2) * 6.283185307179586d) / f4) * Math.pow(2.0d, (-10.0f) * f) * f3) + 1.0d);
    }
}
