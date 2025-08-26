package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

import android.hardware.scontext.SContextConstants;

/* loaded from: classes6.dex */
public class SpringStopEngine {
    private static final double UNSET = Double.MAX_VALUE;
    private float mLastTime;
    private double mLastVelocity;
    private float mMass;
    private float mPos;
    private double mStiffness;
    private float mStopThreshold;
    private double mTargetPos;
    private float mV;
    double mDamping = 0.5d;
    private boolean mInitialized = false;
    private int mBoundaryMode = 0;

    public float getVelocity() {
        return 0.0f;
    }

    void log(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String str2 = ".(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ") " + stackTraceElement.getMethodName() + "() ";
        System.out.println(str2 + str);
    }

    public SpringStopEngine() {
    }

    public float getTargetValue() {
        return (float) this.mTargetPos;
    }

    public void setInitialValue(float f) {
        this.mPos = f;
    }

    public void setTargetValue(float f) {
        this.mTargetPos = f;
    }

    public SpringStopEngine(float[] fArr) {
        if (fArr[0] != 0.0f) {
            throw new RuntimeException(" parameter[0] should be 0");
        }
        springParameters(1.0f, fArr[1], fArr[2], fArr[3], Float.floatToRawIntBits(fArr[4]));
    }

    public void springStart(float f, float f2, float f3) {
        this.mTargetPos = f2;
        this.mInitialized = false;
        this.mPos = f;
        this.mLastVelocity = f3;
        this.mLastTime = 0.0f;
    }

    public void springParameters(float f, float f2, float f3, float f4, int i) {
        this.mDamping = f3;
        this.mInitialized = false;
        this.mStiffness = f2;
        this.mMass = f;
        this.mStopThreshold = f4;
        this.mBoundaryMode = i;
        this.mLastTime = 0.0f;
    }

    public float getVelocity(float f) {
        return this.mV;
    }

    public float get(float f) {
        compute(f - this.mLastTime);
        this.mLastTime = f;
        if (isStopped()) {
            this.mPos = (float) this.mTargetPos;
        }
        return this.mPos;
    }

    public float getAcceleration() {
        return ((float) (((-this.mStiffness) * (this.mPos - this.mTargetPos)) - (this.mDamping * this.mV))) / this.mMass;
    }

    public boolean isStopped() {
        double d = this.mPos - this.mTargetPos;
        double d2 = this.mStiffness;
        double d3 = this.mV;
        return Math.sqrt((((d3 * d3) * ((double) this.mMass)) + ((d2 * d) * d)) / d2) <= ((double) this.mStopThreshold);
    }

    private void compute(double d) {
        if (d <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            return;
        }
        double d2 = this.mStiffness;
        double d3 = this.mDamping;
        int iSqrt = (int) ((9.0d / ((Math.sqrt(d2 / this.mMass) * d) * 4.0d)) + 1.0d);
        double d4 = d / iSqrt;
        int i = 0;
        while (i < iSqrt) {
            float f = this.mPos;
            double d5 = this.mTargetPos;
            float f2 = this.mV;
            double d6 = d2;
            double d7 = ((-d2) * (f - d5)) - (f2 * d3);
            float f3 = this.mMass;
            double d8 = d3;
            double d9 = f2 + (((d7 / f3) * d4) / 2.0d);
            double d10 = ((((-((f + ((d4 * d9) / 2.0d)) - d5)) * d6) - (d9 * d8)) / f3) * d4;
            double d11 = f2 + (d10 / 2.0d);
            float f4 = f2 + ((float) d10);
            this.mV = f4;
            float f5 = f + ((float) (d11 * d4));
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
            d2 = d6;
            d3 = d8;
        }
    }
}
