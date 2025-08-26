package com.android.wm.shell.animation;

import android.animation.Animator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.wm.shell.shared.animation.Interpolators;

/* loaded from: classes3.dex */
public class FlingAnimationUtils {
    public final AnimatorProperties mAnimatorProperties;
    public float mCachedStartGradient;
    public float mCachedVelocityFactor;
    public final float mHighVelocityPxPerSecond;
    public PathInterpolator mInterpolator;
    public final float mLinearOutSlowInX2;
    public final float mMaxLengthSeconds;
    public final float mMinVelocityPxPerSecond;
    public final float mSpeedUpFactor;
    public final float mY2;

    public class AnimatorProperties {
        public long mDuration;
        public Interpolator mInterpolator;

        public /* synthetic */ AnimatorProperties(int i) {
            this();
        }

        private AnimatorProperties() {
        }
    }

    public class Builder {
        public final DisplayMetrics mDisplayMetrics;
        public float mMaxLengthSeconds;
        public float mSpeedUpFactor;
        public float mX2;
        public float mY2;

        public Builder(DisplayMetrics displayMetrics) {
            this.mDisplayMetrics = displayMetrics;
            reset();
        }

        public final FlingAnimationUtils build() {
            return new FlingAnimationUtils(this.mDisplayMetrics, this.mMaxLengthSeconds, this.mSpeedUpFactor, this.mX2, this.mY2);
        }

        public final void reset() {
            this.mMaxLengthSeconds = 0.0f;
            this.mSpeedUpFactor = 0.0f;
            this.mX2 = -1.0f;
            this.mY2 = 1.0f;
        }
    }

    public final class InterpolatorInterpolator implements Interpolator {
        public final Interpolator mCrossfader;
        public final Interpolator mInterpolator1;
        public final Interpolator mInterpolator2;

        public InterpolatorInterpolator(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3) {
            this.mInterpolator1 = interpolator;
            this.mInterpolator2 = interpolator2;
            this.mCrossfader = interpolator3;
        }

        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float interpolation = this.mCrossfader.getInterpolation(f);
            float interpolation2 = this.mInterpolator1.getInterpolation(f);
            return (this.mInterpolator2.getInterpolation(f) * interpolation) + (interpolation2 * (1.0f - interpolation));
        }
    }

    public final class VelocityInterpolator implements Interpolator {
        public final float mDiff;
        public final float mDurationSeconds;
        public final float mVelocity;

        public /* synthetic */ VelocityInterpolator(float f, float f2, float f3, int i) {
            this(f, f2, f3);
        }

        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return ((f * this.mDurationSeconds) * this.mVelocity) / this.mDiff;
        }

        private VelocityInterpolator(float f, float f2, float f3) {
            this.mDurationSeconds = f;
            this.mVelocity = f2;
            this.mDiff = f3;
        }
    }

    public FlingAnimationUtils(DisplayMetrics displayMetrics, float f) {
        this(displayMetrics, f, 0.0f);
    }

    public final void apply(Animator animator, float f, float f2, float f3, float f4) {
        AnimatorProperties properties = getProperties(f, f2, f3, f4);
        animator.setDuration(properties.mDuration);
        animator.setInterpolator(properties.mInterpolator);
    }

    public final void applyDismissing(Animator animator, float f, float f2, float f3, float f4) {
        float f5 = f2 - f;
        float fPow = (float) (Math.pow(Math.abs(f5) / f4, 0.5d) * this.mMaxLengthSeconds);
        float fAbs = Math.abs(f5);
        float fAbs2 = Math.abs(f3);
        float f6 = this.mMinVelocityPxPerSecond;
        float fMax = Math.max(0.0f, Math.min(1.0f, (fAbs2 - f6) / (this.mHighVelocityPxPerSecond - f6)));
        float f7 = (fMax * 0.5f) + ((1.0f - fMax) * 0.4f);
        PathInterpolator pathInterpolator = new PathInterpolator(0.0f, 0.0f, 0.5f, f7);
        float f8 = ((f7 / 0.5f) * fAbs) / fAbs2;
        AnimatorProperties animatorProperties = this.mAnimatorProperties;
        if (f8 <= fPow) {
            animatorProperties.mInterpolator = pathInterpolator;
            fPow = f8;
        } else if (fAbs2 >= f6) {
            animatorProperties.mInterpolator = new InterpolatorInterpolator(new VelocityInterpolator(fPow, fAbs2, fAbs, 0), pathInterpolator, Interpolators.LINEAR_OUT_SLOW_IN);
        } else {
            animatorProperties.mInterpolator = Interpolators.FAST_OUT_LINEAR_IN;
        }
        long j = (long) (fPow * 1000.0f);
        animatorProperties.mDuration = j;
        animator.setDuration(j);
        animator.setInterpolator(animatorProperties.mInterpolator);
    }

    public final AnimatorProperties getProperties(float f, float f2, float f3, float f4) {
        Interpolator interpolator;
        float f5 = f2 - f;
        float fSqrt = (float) (Math.sqrt(Math.abs(f5) / f4) * this.mMaxLengthSeconds);
        float fAbs = Math.abs(f5);
        float fAbs2 = Math.abs(f3);
        float f6 = this.mSpeedUpFactor;
        float fMin = f6 == 0.0f ? 1.0f : Math.min(fAbs2 / 3000.0f, 1.0f);
        float f7 = this.mLinearOutSlowInX2;
        float f8 = this.mY2;
        float f9 = 1.0f - fMin;
        float f10 = ((f8 / f7) * fMin) + (0.75f * f9);
        float f11 = (f10 * fAbs) / fAbs2;
        if (Float.isNaN(fMin)) {
            Log.e("FlingAnimationUtils", "Invalid velocity factor", new Throwable());
            interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
        } else {
            if (f10 != this.mCachedStartGradient || fMin != this.mCachedVelocityFactor) {
                float f12 = f9 * f6;
                float f13 = f12 * f10;
                try {
                    this.mInterpolator = new PathInterpolator(f12, f13, f7, f8);
                    this.mCachedStartGradient = f10;
                    this.mCachedVelocityFactor = fMin;
                } catch (IllegalArgumentException e) {
                    StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("Illegal path with x1=", f12, " y1=", f13, " x2=");
                    sbM.append(f7);
                    sbM.append(" y2=");
                    sbM.append(f8);
                    throw new IllegalArgumentException(sbM.toString(), e);
                }
            }
            interpolator = this.mInterpolator;
        }
        AnimatorProperties animatorProperties = this.mAnimatorProperties;
        if (f11 <= fSqrt) {
            animatorProperties.mInterpolator = interpolator;
            fSqrt = f11;
        } else if (fAbs2 >= this.mMinVelocityPxPerSecond) {
            animatorProperties.mInterpolator = new InterpolatorInterpolator(new VelocityInterpolator(fSqrt, fAbs2, fAbs, 0), interpolator, Interpolators.LINEAR_OUT_SLOW_IN);
        } else {
            animatorProperties.mInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        }
        animatorProperties.mDuration = (long) (fSqrt * 1000.0f);
        return animatorProperties;
    }

    public FlingAnimationUtils(DisplayMetrics displayMetrics, float f, float f2) {
        this(displayMetrics, f, f2, -1.0f, 1.0f);
    }

    public FlingAnimationUtils(DisplayMetrics displayMetrics, float f, float f2, float f3, float f4) {
        this.mAnimatorProperties = new AnimatorProperties(0);
        this.mCachedStartGradient = -1.0f;
        this.mCachedVelocityFactor = -1.0f;
        this.mMaxLengthSeconds = f;
        this.mSpeedUpFactor = f2;
        if (f3 < 0.0f) {
            this.mLinearOutSlowInX2 = (0.68f * f2) + ((1.0f - f2) * 0.35f);
        } else {
            this.mLinearOutSlowInX2 = f3;
        }
        this.mY2 = f4;
        float f5 = displayMetrics.density;
        this.mMinVelocityPxPerSecond = 250.0f * f5;
        this.mHighVelocityPxPerSecond = f5 * 3000.0f;
    }
}
