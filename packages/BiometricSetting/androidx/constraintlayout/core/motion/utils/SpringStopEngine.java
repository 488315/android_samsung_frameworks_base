package androidx.constraintlayout.core.motion.utils;

/* loaded from: classes.dex */
public final class SpringStopEngine implements StopEngine {
    private float mLastTime;
    private float mMass;
    private float mPos;
    private double mStiffness;
    private float mStopThreshold;
    private double mTargetPos;

    /* renamed from: mV */
    private float f8mV;
    double mDamping = 0.5d;
    private int mBoundaryMode = 0;

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final float getInterpolation(float f) {
        SpringStopEngine springStopEngine = this;
        double d = f - springStopEngine.mLastTime;
        if (d > 0.0d) {
            double d2 = springStopEngine.mStiffness;
            double d3 = springStopEngine.mDamping;
            int sqrt = (int) ((9.0d / ((Math.sqrt(d2 / springStopEngine.mMass) * d) * 4.0d)) + 1.0d);
            double d4 = d / sqrt;
            int i = 0;
            while (i < sqrt) {
                float f2 = springStopEngine.mPos;
                double d5 = f2;
                double d6 = springStopEngine.mTargetPos;
                int i2 = sqrt;
                int i3 = i;
                double d7 = (-d2) * (d5 - d6);
                float f3 = springStopEngine.f8mV;
                double d8 = d2;
                double d9 = f3;
                double d10 = springStopEngine.mMass;
                double d11 = ((((d7 - (d9 * d3)) / d10) * d4) / 2.0d) + d9;
                double d12 = ((((-((((d4 * d11) / 2.0d) + d5) - d6)) * d8) - (d11 * d3)) / d10) * d4;
                float f4 = f3 + ((float) d12);
                this.f8mV = f4;
                float f5 = f2 + ((float) (((d12 / 2.0d) + d9) * d4));
                this.mPos = f5;
                int i4 = this.mBoundaryMode;
                if (i4 > 0) {
                    if (f5 < 0.0f && (i4 & 1) == 1) {
                        this.mPos = -f5;
                        this.f8mV = -f4;
                    }
                    float f6 = this.mPos;
                    if (f6 > 1.0f && (i4 & 2) == 2) {
                        this.mPos = 2.0f - f6;
                        this.f8mV = -this.f8mV;
                    }
                }
                i = i3 + 1;
                springStopEngine = this;
                sqrt = i2;
                d2 = d8;
            }
        }
        SpringStopEngine springStopEngine2 = springStopEngine;
        springStopEngine2.mLastTime = f;
        if (isStopped()) {
            springStopEngine2.mPos = (float) springStopEngine2.mTargetPos;
        }
        return springStopEngine2.mPos;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final float getVelocity() {
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final boolean isStopped() {
        double d = this.mPos - this.mTargetPos;
        double d2 = this.mStiffness;
        double d3 = this.f8mV;
        return Math.sqrt((((d2 * d) * d) + ((d3 * d3) * ((double) this.mMass))) / d2) <= ((double) this.mStopThreshold);
    }

    public final void springConfig(float f, float f2, float f3, float f4, float f5, float f6, int i) {
        this.mTargetPos = f2;
        this.mDamping = f5;
        this.mPos = f;
        this.mStiffness = f4;
        this.mMass = f3;
        this.mStopThreshold = f6;
        this.mBoundaryMode = i;
        this.mLastTime = 0.0f;
    }
}
