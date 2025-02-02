package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.motion.utils.ViewOscillator;

/* loaded from: classes.dex */
public final class VelocityMatrix {
    float mDRotate;
    float mDScaleX;
    float mDScaleY;
    float mDTranslateX;
    float mDTranslateY;
    float mRotate;

    public final void applyTransform(float f, float f2, int i, int i2, float[] fArr) {
        float f3 = fArr[0];
        float f4 = fArr[1];
        float f5 = (f2 - 0.5f) * 2.0f;
        float f6 = f3 + this.mDTranslateX;
        float f7 = f4 + this.mDTranslateY;
        float f8 = (this.mDScaleX * (f - 0.5f) * 2.0f) + f6;
        float f9 = (this.mDScaleY * f5) + f7;
        float radians = (float) Math.toRadians(this.mRotate);
        float radians2 = (float) Math.toRadians(this.mDRotate);
        double d = radians;
        double d2 = i2 * f5;
        float sin = (((float) ((Math.sin(d) * ((-i) * r7)) - (Math.cos(d) * d2))) * radians2) + f8;
        float cos = (radians2 * ((float) ((Math.cos(d) * (i * r7)) - (Math.sin(d) * d2)))) + f9;
        fArr[0] = sin;
        fArr[1] = cos;
    }

    public final void clear() {
        this.mDRotate = 0.0f;
        this.mDTranslateY = 0.0f;
        this.mDTranslateX = 0.0f;
        this.mDScaleY = 0.0f;
        this.mDScaleX = 0.0f;
    }

    public final void setRotationVelocity(ViewOscillator viewOscillator, float f) {
        if (viewOscillator != null) {
            this.mDRotate = viewOscillator.getSlope(f);
        }
    }

    public final void setScaleVelocity(SplineSet splineSet, SplineSet splineSet2, float f) {
        if (splineSet != null) {
            this.mDScaleX = (float) splineSet.mCurveFit.getSlope(f);
        }
        if (splineSet2 != null) {
            this.mDScaleY = (float) splineSet2.mCurveFit.getSlope(f);
        }
    }

    public final void setTranslationVelocity(SplineSet splineSet, SplineSet splineSet2, float f) {
        if (splineSet != null) {
            this.mDTranslateX = (float) splineSet.mCurveFit.getSlope(f);
        }
        if (splineSet2 != null) {
            this.mDTranslateY = (float) splineSet2.mCurveFit.getSlope(f);
        }
    }

    public final void setRotationVelocity(SplineSet splineSet, float f) {
        if (splineSet != null) {
            this.mDRotate = (float) splineSet.mCurveFit.getSlope(f);
            this.mRotate = splineSet.get(f);
        }
    }

    public final void setScaleVelocity(ViewOscillator viewOscillator, ViewOscillator viewOscillator2, float f) {
        if (viewOscillator != null) {
            this.mDScaleX = viewOscillator.getSlope(f);
        }
        if (viewOscillator2 != null) {
            this.mDScaleY = viewOscillator2.getSlope(f);
        }
    }

    public final void setTranslationVelocity(ViewOscillator viewOscillator, ViewOscillator viewOscillator2, float f) {
        if (viewOscillator != null) {
            this.mDTranslateX = viewOscillator.getSlope(f);
        }
        if (viewOscillator2 != null) {
            this.mDTranslateY = viewOscillator2.getSlope(f);
        }
    }
}
