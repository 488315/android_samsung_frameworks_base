package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public final class StopLogicEngine implements StopEngine {
    private boolean mBackwards = false;
    private float mLastPosition;
    private int mNumberOfStages;
    private float mStage1Duration;
    private float mStage1EndPosition;
    private float mStage1Velocity;
    private float mStage2Duration;
    private float mStage2EndPosition;
    private float mStage2Velocity;
    private float mStage3Duration;
    private float mStage3EndPosition;
    private float mStage3Velocity;
    private float mStartPosition;

    private void setup(float f, float f2, float f3, float f4, float f5) {
        this.mStage3EndPosition = f2;
        if (f == 0.0f) {
            f = 1.0E-4f;
        }
        float f6 = f / f3;
        float f7 = (f6 * f) / 2.0f;
        if (f < 0.0f) {
            float sqrt = (float) Math.sqrt((f2 - ((((-f) / f3) * f) / 2.0f)) * f3);
            if (sqrt < f4) {
                this.mNumberOfStages = 2;
                this.mStage1Velocity = f;
                this.mStage2Velocity = sqrt;
                this.mStage3Velocity = 0.0f;
                float f8 = (sqrt - f) / f3;
                this.mStage1Duration = f8;
                this.mStage2Duration = sqrt / f3;
                this.mStage1EndPosition = ((f + sqrt) * f8) / 2.0f;
                this.mStage2EndPosition = f2;
                this.mStage3EndPosition = f2;
                return;
            }
            this.mNumberOfStages = 3;
            this.mStage1Velocity = f;
            this.mStage2Velocity = f4;
            this.mStage3Velocity = f4;
            float f9 = (f4 - f) / f3;
            this.mStage1Duration = f9;
            float f10 = f4 / f3;
            this.mStage3Duration = f10;
            float f11 = ((f + f4) * f9) / 2.0f;
            float f12 = (f10 * f4) / 2.0f;
            this.mStage2Duration = ((f2 - f11) - f12) / f4;
            this.mStage1EndPosition = f11;
            this.mStage2EndPosition = f2 - f12;
            this.mStage3EndPosition = f2;
            return;
        }
        if (f7 >= f2) {
            this.mNumberOfStages = 1;
            this.mStage1Velocity = f;
            this.mStage2Velocity = 0.0f;
            this.mStage1EndPosition = f2;
            this.mStage1Duration = (2.0f * f2) / f;
            return;
        }
        float f13 = f2 - f7;
        float f14 = f13 / f;
        if (f14 + f6 < f5) {
            this.mNumberOfStages = 2;
            this.mStage1Velocity = f;
            this.mStage2Velocity = f;
            this.mStage3Velocity = 0.0f;
            this.mStage1EndPosition = f13;
            this.mStage2EndPosition = f2;
            this.mStage1Duration = f14;
            this.mStage2Duration = f6;
            return;
        }
        float sqrt2 = (float) Math.sqrt(((f * f) / 2.0f) + (f3 * f2));
        float f15 = (sqrt2 - f) / f3;
        this.mStage1Duration = f15;
        float f16 = sqrt2 / f3;
        this.mStage2Duration = f16;
        if (sqrt2 < f4) {
            this.mNumberOfStages = 2;
            this.mStage1Velocity = f;
            this.mStage2Velocity = sqrt2;
            this.mStage3Velocity = 0.0f;
            this.mStage1Duration = f15;
            this.mStage2Duration = f16;
            this.mStage1EndPosition = ((f + sqrt2) * f15) / 2.0f;
            this.mStage2EndPosition = f2;
            return;
        }
        this.mNumberOfStages = 3;
        this.mStage1Velocity = f;
        this.mStage2Velocity = f4;
        this.mStage3Velocity = f4;
        float f17 = (f4 - f) / f3;
        this.mStage1Duration = f17;
        float f18 = f4 / f3;
        this.mStage3Duration = f18;
        float f19 = ((f + f4) * f17) / 2.0f;
        float f20 = (f18 * f4) / 2.0f;
        this.mStage2Duration = ((f2 - f19) - f20) / f4;
        this.mStage1EndPosition = f19;
        this.mStage2EndPosition = f2 - f20;
        this.mStage3EndPosition = f2;
    }

    public final void config(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mStartPosition = f;
        boolean z = f > f2;
        this.mBackwards = z;
        if (z) {
            setup(-f3, f - f2, f5, f6, f4);
        } else {
            setup(f3, f2 - f, f5, f6, f4);
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final float getInterpolation(float f) {
        float f2;
        float f3 = this.mStage1Duration;
        if (f <= f3) {
            float f4 = this.mStage1Velocity;
            f2 = ((((this.mStage2Velocity - f4) * f) * f) / (f3 * 2.0f)) + (f4 * f);
        } else {
            int i = this.mNumberOfStages;
            if (i == 1) {
                f2 = this.mStage1EndPosition;
            } else {
                float f5 = f - f3;
                float f6 = this.mStage2Duration;
                if (f5 < f6) {
                    float f7 = this.mStage1EndPosition;
                    float f8 = this.mStage2Velocity;
                    f2 = ((((this.mStage3Velocity - f8) * f5) * f5) / (f6 * 2.0f)) + (f8 * f5) + f7;
                } else if (i == 2) {
                    f2 = this.mStage2EndPosition;
                } else {
                    float f9 = f5 - f6;
                    float f10 = this.mStage3Duration;
                    if (f9 <= f10) {
                        float f11 = this.mStage2EndPosition;
                        float f12 = this.mStage3Velocity * f9;
                        f2 = (f11 + f12) - ((f12 * f9) / (f10 * 2.0f));
                    } else {
                        f2 = this.mStage3EndPosition;
                    }
                }
            }
        }
        this.mLastPosition = f;
        boolean z = this.mBackwards;
        float f13 = this.mStartPosition;
        return z ? f13 - f2 : f13 + f2;
    }

    public final float getVelocity(float f) {
        float f2 = this.mStage1Duration;
        if (f <= f2) {
            float f3 = this.mStage1Velocity;
            return (((this.mStage2Velocity - f3) * f) / f2) + f3;
        }
        int i = this.mNumberOfStages;
        if (i == 1) {
            return 0.0f;
        }
        float f4 = f - f2;
        float f5 = this.mStage2Duration;
        if (f4 < f5) {
            float f6 = this.mStage2Velocity;
            return (((this.mStage3Velocity - f6) * f4) / f5) + f6;
        }
        if (i == 2) {
            return this.mStage2EndPosition;
        }
        float f7 = f4 - f5;
        float f8 = this.mStage3Duration;
        if (f7 >= f8) {
            return this.mStage3EndPosition;
        }
        float f9 = this.mStage3Velocity;
        return f9 - ((f7 * f9) / f8);
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final boolean isStopped() {
        return getVelocity() < 1.0E-5f && Math.abs(this.mStage3EndPosition - this.mLastPosition) < 1.0E-5f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final float getVelocity() {
        return this.mBackwards ? -getVelocity(this.mLastPosition) : getVelocity(this.mLastPosition);
    }
}
