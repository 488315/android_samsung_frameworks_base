package androidx.appcompat.animation;

import android.view.animation.Interpolator;

/* loaded from: classes.dex */
public class SeslElasticInterpolator implements Interpolator {
    public final float mAmplitude;
    public final float mPeriod;

    public SeslElasticInterpolator(float f, float f2) {
        this.mAmplitude = f;
        this.mPeriod = f2;
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        float fAsin;
        float f2 = this.mAmplitude;
        float f3 = this.mPeriod;
        if (f == 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f3 == 0.0f) {
            f3 = 0.3f;
        }
        if (f2 == 0.0f || f2 < 1.0f) {
            fAsin = f3 / 4.0f;
            f2 = 1.0f;
        } else {
            fAsin = (float) (Math.asin(1.0f / f2) * (f3 / 6.283185307179586d));
        }
        return (float) ((Math.sin(((f - fAsin) * 6.283185307179586d) / f3) * Math.pow(2.0d, (-10.0f) * f) * f2) + 1.0d);
    }
}
