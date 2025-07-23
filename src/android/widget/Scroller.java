package android.widget;

import android.content.Context;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class Scroller {
    private static final int DEFAULT_DURATION = 250;
    private static final float END_TENSION = 1.0f;
    private static final int FLING_MODE = 1;
    private static final float INFLEXION = 0.35f;
    private static final int NB_SAMPLES = 100;
    private static final float P1 = 0.175f;
    private static final float P2 = 0.35000002f;
    private static final int SCROLL_MODE = 0;
    private static final float START_TENSION = 0.5f;
    private float mCurrVelocity;
    private int mCurrX;
    private int mCurrY;
    private float mDeceleration;
    private float mDeltaX;
    private float mDeltaY;
    private int mDistance;
    private int mDuration;
    private float mDurationReciprocal;
    private int mFinalX;
    private int mFinalY;
    private boolean mFinished;
    private float mFlingFriction;
    private boolean mFlywheel;
    private final Interpolator mInterpolator;
    private int mMaxX;
    private int mMaxY;
    private int mMinX;
    private int mMinY;
    private int mMode;
    private float mPhysicalCoeff;
    private final float mPpi;
    private long mStartTime;
    private int mStartX;
    private int mStartY;
    private float mVelocity;
    private static float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
    private static final float[] SPLINE_POSITION = new float[101];
    private static final float[] SPLINE_TIME = new float[101];

    static {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11 = 0.0f;
        int i = 0;
        float f12 = 0.0f;
        while (true) {
            float f13 = 1.0f;
            if (i < 100) {
                float f14 = i / 100.0f;
                float f15 = 1.0f;
                while (true) {
                    f = ((f15 - f11) / 2.0f) + f11;
                    f2 = f13 - f;
                    f3 = f * 3.0f * f2;
                    f4 = f * f * f;
                    float f16 = (((f2 * P1) + (f * P2)) * f3) + f4;
                    f5 = f13;
                    f6 = f14;
                    if (Math.abs(f16 - f14) < 1.0E-5d) {
                        break;
                    }
                    if (f16 > f6) {
                        f15 = f;
                    } else {
                        f11 = f;
                    }
                    f13 = f5;
                    f14 = f6;
                }
                SPLINE_POSITION[i] = (f3 * ((f2 * 0.5f) + f)) + f4;
                float f17 = f5;
                while (true) {
                    f7 = ((f17 - f12) / 2.0f) + f12;
                    f8 = f5 - f7;
                    f9 = f7 * 3.0f * f8;
                    f10 = f7 * f7 * f7;
                    float f18 = (((f8 * 0.5f) + f7) * f9) + f10;
                    if (Math.abs(f18 - f6) < 1.0E-5d) {
                        break;
                    } else if (f18 > f6) {
                        f17 = f7;
                    } else {
                        f12 = f7;
                    }
                }
                SPLINE_TIME[i] = (f9 * ((f8 * P1) + (f7 * P2))) + f10;
                i++;
            } else {
                float[] fArr = SPLINE_POSITION;
                SPLINE_TIME[100] = 1.0f;
                fArr[100] = 1.0f;
                return;
            }
        }
    }

    public Scroller(Context context) {
        this(context, null);
    }

    public Scroller(Context context, Interpolator interpolator) {
        this(context, interpolator, context.getApplicationInfo().targetSdkVersion >= 11);
    }

    public Scroller(Context context, Interpolator interpolator, boolean z) {
        this.mFlingFriction = ViewConfiguration.getScrollFriction();
        this.mFinished = true;
        if (interpolator == null) {
            this.mInterpolator = new ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
        this.mPpi = context.getResources().getDisplayMetrics().density * 160.0f;
        this.mDeceleration = computeDeceleration(ViewConfiguration.getScrollFriction());
        this.mFlywheel = z;
        this.mPhysicalCoeff = computeDeceleration(0.84f);
    }

    public final void setFriction(float f) {
        this.mDeceleration = computeDeceleration(f);
        this.mFlingFriction = f;
    }

    private float computeDeceleration(float f) {
        return this.mPpi * 386.0878f * f;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public final void forceFinished(boolean z) {
        this.mFinished = z;
    }

    public final int getDuration() {
        return this.mDuration;
    }

    public final int getCurrX() {
        return this.mCurrX;
    }

    public final int getCurrY() {
        return this.mCurrY;
    }

    public float getCurrVelocity() {
        return this.mMode == 1 ? this.mCurrVelocity : this.mVelocity - ((this.mDeceleration * timePassed()) / 2000.0f);
    }

    public final int getStartX() {
        return this.mStartX;
    }

    public final int getStartY() {
        return this.mStartY;
    }

    public final int getFinalX() {
        return this.mFinalX;
    }

    public final int getFinalY() {
        return this.mFinalY;
    }

    public boolean computeScrollOffset() {
        float f;
        float f2;
        if (this.mFinished) {
            return false;
        }
        int currentAnimationTimeMillis = (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
        int i = this.mDuration;
        if (currentAnimationTimeMillis < i) {
            int i2 = this.mMode;
            if (i2 == 0) {
                float interpolation = this.mInterpolator.getInterpolation(currentAnimationTimeMillis * this.mDurationReciprocal);
                this.mCurrX = this.mStartX + Math.round(this.mDeltaX * interpolation);
                this.mCurrY = this.mStartY + Math.round(interpolation * this.mDeltaY);
            } else if (i2 == 1) {
                float f3 = currentAnimationTimeMillis / i;
                int i3 = (int) (f3 * 100.0f);
                if (i3 < 100) {
                    float f4 = i3 / 100.0f;
                    int i4 = i3 + 1;
                    float[] fArr = SPLINE_POSITION;
                    float f5 = fArr[i3];
                    f2 = (fArr[i4] - f5) / ((i4 / 100.0f) - f4);
                    f = f5 + ((f3 - f4) * f2);
                } else {
                    f = 1.0f;
                    f2 = 0.0f;
                }
                this.mCurrVelocity = ((f2 * this.mDistance) / i) * 1000.0f;
                int round = this.mStartX + Math.round((this.mFinalX - r0) * f);
                this.mCurrX = round;
                int min = Math.min(round, this.mMaxX);
                this.mCurrX = min;
                this.mCurrX = Math.max(min, this.mMinX);
                int round2 = this.mStartY + Math.round(f * (this.mFinalY - r0));
                this.mCurrY = round2;
                int min2 = Math.min(round2, this.mMaxY);
                this.mCurrY = min2;
                int max = Math.max(min2, this.mMinY);
                this.mCurrY = max;
                if (this.mCurrX == this.mFinalX && max == this.mFinalY) {
                    this.mFinished = true;
                }
            }
        } else {
            this.mCurrX = this.mFinalX;
            this.mCurrY = this.mFinalY;
            this.mFinished = true;
        }
        return true;
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        startScroll(i, i2, i3, i4, 250);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mMode = 0;
        this.mFinished = false;
        this.mDuration = i5;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = i;
        this.mStartY = i2;
        this.mFinalX = i + i3;
        this.mFinalY = i2 + i4;
        this.mDeltaX = i3;
        this.mDeltaY = i4;
        this.mDurationReciprocal = 1.0f / this.mDuration;
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.mFlywheel && !this.mFinished) {
            float currVelocity = getCurrVelocity();
            float f = this.mFinalX - this.mStartX;
            float f2 = this.mFinalY - this.mStartY;
            float hypot = (float) Math.hypot(f, f2);
            float f3 = (f / hypot) * currVelocity;
            float f4 = (f2 / hypot) * currVelocity;
            float f5 = i3;
            if (Math.signum(f5) == Math.signum(f3)) {
                float f6 = i4;
                if (Math.signum(f6) == Math.signum(f4)) {
                    i3 = (int) (f5 + f3);
                    i4 = (int) (f6 + f4);
                }
            }
        }
        this.mMode = 1;
        this.mFinished = false;
        float hypot2 = (float) Math.hypot(i3, i4);
        this.mVelocity = hypot2;
        this.mDuration = getSplineFlingDuration(hypot2);
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = i;
        this.mStartY = i2;
        float f7 = hypot2 == 0.0f ? 1.0f : i3 / hypot2;
        float f8 = hypot2 != 0.0f ? i4 / hypot2 : 1.0f;
        double splineFlingDistance = getSplineFlingDistance(hypot2);
        this.mDistance = (int) (Math.signum(hypot2) * splineFlingDistance);
        this.mMinX = i5;
        this.mMaxX = i6;
        this.mMinY = i7;
        this.mMaxY = i8;
        int round = i + ((int) Math.round(f7 * splineFlingDistance));
        this.mFinalX = round;
        int min = Math.min(round, this.mMaxX);
        this.mFinalX = min;
        this.mFinalX = Math.max(min, this.mMinX);
        int round2 = i2 + ((int) Math.round(splineFlingDistance * f8));
        this.mFinalY = round2;
        int min2 = Math.min(round2, this.mMaxY);
        this.mFinalY = min2;
        this.mFinalY = Math.max(min2, this.mMinY);
    }

    private double getSplineDeceleration(float f) {
        return Math.log((Math.abs(f) * INFLEXION) / (this.mFlingFriction * this.mPhysicalCoeff));
    }

    private int getSplineFlingDuration(float f) {
        return (int) (Math.exp(getSplineDeceleration(f) / (DECELERATION_RATE - 1.0d)) * 1000.0d);
    }

    private double getSplineFlingDistance(float f) {
        double splineDeceleration = getSplineDeceleration(f);
        float f2 = DECELERATION_RATE;
        return this.mFlingFriction * this.mPhysicalCoeff * Math.exp((f2 / (f2 - 1.0d)) * splineDeceleration);
    }

    public void abortAnimation() {
        this.mCurrX = this.mFinalX;
        this.mCurrY = this.mFinalY;
        this.mFinished = true;
    }

    public void extendDuration(int i) {
        int timePassed = timePassed() + i;
        this.mDuration = timePassed;
        this.mDurationReciprocal = 1.0f / timePassed;
        this.mFinished = false;
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    }

    public void setFinalX(int i) {
        this.mFinalX = i;
        this.mDeltaX = i - this.mStartX;
        this.mFinished = false;
    }

    public void setFinalY(int i) {
        this.mFinalY = i;
        this.mDeltaY = i - this.mStartY;
        this.mFinished = false;
    }

    public boolean isScrollingInDirection(float f, float f2) {
        return !this.mFinished && Math.signum(f) == Math.signum((float) (this.mFinalX - this.mStartX)) && Math.signum(f2) == Math.signum((float) (this.mFinalY - this.mStartY));
    }

    static class ViscousFluidInterpolator implements Interpolator {
        private static final float VISCOUS_FLUID_NORMALIZE;
        private static final float VISCOUS_FLUID_OFFSET;
        private static final float VISCOUS_FLUID_SCALE = 8.0f;

        ViscousFluidInterpolator() {
        }

        static {
            float viscousFluid = 1.0f / viscousFluid(1.0f);
            VISCOUS_FLUID_NORMALIZE = viscousFluid;
            VISCOUS_FLUID_OFFSET = 1.0f - (viscousFluid * viscousFluid(1.0f));
        }

        private static float viscousFluid(float f) {
            float f2 = f * VISCOUS_FLUID_SCALE;
            if (f2 < 1.0f) {
                return f2 - (1.0f - ((float) Math.exp(-f2)));
            }
            return ((1.0f - ((float) Math.exp(1.0f - f2))) * 0.63212055f) + 0.36787945f;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float viscousFluid = VISCOUS_FLUID_NORMALIZE * viscousFluid(f);
            return viscousFluid > 0.0f ? viscousFluid + VISCOUS_FLUID_OFFSET : viscousFluid;
        }
    }
}
