package androidx.constraintlayout.core.motion.utils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SpringStopEngine implements StopEngine {
    public float mLastTime;
    public float mMass;
    public float mPos;
    public double mStiffness;
    public float mStopThreshold;
    public double mTargetPos;
    public float mV;
    public double mDamping = 0.5d;
    public int mBoundaryMode = 0;

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final float getInterpolation(float f) {
        double d = f - this.mLastTime;
        if (d > 0.0d) {
            double d2 = this.mStiffness;
            double d3 = this.mDamping;
            int sqrt = (int) ((9.0d / ((Math.sqrt(d2 / this.mMass) * d) * 4.0d)) + 1.0d);
            double d4 = d / sqrt;
            int i = 0;
            while (i < sqrt) {
                float f2 = this.mPos;
                double d5 = f2;
                double d6 = this.mTargetPos;
                double d7 = d4;
                float f3 = this.mV;
                double d8 = f3;
                double d9 = ((-d2) * (d5 - d6)) - (d3 * d8);
                double d10 = this.mMass;
                double d11 = (((d9 / d10) * d7) / 2.0d) + d8;
                double d12 = ((((-((((d7 * d11) / 2.0d) + d5) - d6)) * d2) - (d11 * d3)) / d10) * d7;
                float f4 = f3 + ((float) d12);
                this.mV = f4;
                float f5 = f2 + ((float) (((d12 / 2.0d) + d8) * d7));
                this.mPos = f5;
                int i2 = this.mBoundaryMode;
                if (i2 > 0) {
                    if (f5 < 0.0f && (i2 & 1) == 1) {
                        this.mPos = -f5;
                        this.mV = -f4;
                    }
                    float f6 = this.mPos;
                    if (f6 > 1.0f && (i2 & 2) == 2) {
                        this.mPos = 2.0f - f6;
                        this.mV = -this.mV;
                    }
                }
                i++;
                d4 = d7;
            }
        }
        this.mLastTime = f;
        if (isStopped()) {
            this.mPos = (float) this.mTargetPos;
        }
        return this.mPos;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final float getVelocity() {
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final boolean isStopped() {
        double d = this.mPos - this.mTargetPos;
        double d2 = this.mStiffness;
        double d3 = this.mV;
        return Math.sqrt((((d2 * d) * d) + ((d3 * d3) * ((double) this.mMass))) / d2) <= ((double) this.mStopThreshold);
    }
}
