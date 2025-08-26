package com.android.internal.widget.remotecompose.core.operations.utilities.touch;

import android.hardware.scontext.SContextConstants;

/* loaded from: classes6.dex */
public class VelocityEasing {
    private Easing mEasing;
    private float mStartPos = 0.0f;
    private float mStartV = 0.0f;
    private float mEndPos = 0.0f;
    private float mDuration = 0.0f;
    private Stage[] mStage = {new Stage(this, 1), new Stage(this, 2), new Stage(this, 3)};
    private int mNumberOfStages = 0;
    private double mEasingAdapterDistance = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    private double mEasingAdapterA = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    private double mEasingAdapterB = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    private boolean mOneDimension = true;
    private float mTotalEasingDuration = 0.0f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public interface Easing {
        Easing clone();

        double get(double d);

        double getDiff(double d);
    }

    public float getDuration() {
        if (this.mEasing != null) {
            return this.mTotalEasingDuration;
        }
        return this.mDuration;
    }

    public float getV(float f) {
        int i = 0;
        if (this.mEasing == null) {
            while (i < this.mNumberOfStages) {
                if (this.mStage[i].mEndTime > f) {
                    return this.mStage[i].getVel(f);
                }
                i++;
            }
            return 0.0f;
        }
        int i2 = this.mNumberOfStages - 1;
        while (i < i2) {
            if (this.mStage[i].mEndTime > f) {
                return this.mStage[i].getVel(f);
            }
            i++;
        }
        return (float) getEasingDiff(f - this.mStage[i2].mStartTime);
    }

    public float getPos(float f) {
        int i = 0;
        if (this.mEasing == null) {
            while (i < this.mNumberOfStages) {
                if (this.mStage[i].mEndTime > f) {
                    return this.mStage[i].getPos(f);
                }
                i++;
            }
            return this.mEndPos;
        }
        int i2 = this.mNumberOfStages - 1;
        while (i < i2) {
            if (this.mStage[i].mEndTime > f) {
                return this.mStage[i].getPos(f);
            }
            i++;
        }
        return ((float) getEasing(f - this.mStage[i2].mStartTime)) + this.mStage[i2].mStartPos;
    }

    public String toString() {
        String str = " ";
        for (int i = 0; i < this.mNumberOfStages; i++) {
            Stage stage = this.mStage[i];
            str = str + " $i $stage";
        }
        return str;
    }

    public void config(float f, float f2, float f3, float f4, float f5, float f6, Easing easing) {
        VelocityEasing velocityEasing;
        float f7;
        if (f == f2) {
            f += 1.0f;
        }
        float f8 = f;
        this.mStartPos = f8;
        this.mEndPos = f2;
        if (easing != null) {
            this.mEasing = easing.clone();
        }
        float fSignum = Math.signum(f2 - f8);
        float f9 = f6 * fSignum;
        float f10 = f5 * fSignum;
        if (f3 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            f3 = 1.0E-4f * fSignum;
        }
        float f11 = f3;
        this.mStartV = f11;
        if (rampDown(f8, f2, f11, f4)) {
            velocityEasing = this;
        } else {
            velocityEasing = this;
            if (this.mOneDimension) {
                boolean zCruseThenRampDown = velocityEasing.cruseThenRampDown(f8, f2, f11, f4, f10, f9);
                f7 = f4;
                f10 = f10;
                f9 = f9;
                if (!zCruseThenRampDown) {
                }
            } else {
                f7 = f4;
            }
            if (!velocityEasing.rampUpRampDown(f8, f2, f11, f10, f9, f7)) {
                velocityEasing.rampUpCruseRampDown(f8, f2, f11, f10, f9, f7);
            }
        }
        if (velocityEasing.mOneDimension) {
            velocityEasing.configureEasingAdapter();
        }
    }

    private boolean rampDown(float f, float f2, float f3, float f4) {
        float f5 = ((f2 - f) / f3) * 2.0f;
        if (f5 <= 0.0f || f5 > f4) {
            return false;
        }
        this.mNumberOfStages = 1;
        this.mStage[0].setUp(f3, f, 0.0f, 0.0f, f2, f5);
        this.mDuration = f5;
        return true;
    }

    private boolean cruseThenRampDown(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f3 / f5;
        float f8 = (f2 - f) - ((f3 * f7) / 2.0f);
        float f9 = f8 / f3;
        float f10 = f7 + f9;
        if (f10 <= 0.0f || f10 >= f4) {
            return false;
        }
        this.mNumberOfStages = 2;
        this.mStage[0].setUp(f3, f, 0.0f, f3, f8, f9);
        this.mStage[1].setUp(f3, f + f8, f9, 0.0f, f2, f10);
        this.mDuration = f10;
        return true;
    }

    private boolean rampUpRampDown(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f2 - f;
        float fSignum = Math.signum(f4) * ((float) Math.sqrt((f4 * f7) + ((f3 * f3) / 2.0f)));
        if (f5 / fSignum <= 1.0f) {
            return false;
        }
        float f8 = (fSignum - f3) / f4;
        float f9 = (((fSignum + f3) * f8) / 2.0f) + f;
        this.mNumberOfStages = 2;
        this.mStage[0].setUp(f3, f, 0.0f, fSignum, f9, f8);
        float f10 = (fSignum / f4) + f8;
        this.mStage[1].setUp(fSignum, f9, f8, 0.0f, f2, f10);
        this.mDuration = f10;
        if (f10 > f6) {
            return false;
        }
        if (f10 < f6 / 2.0f) {
            float f11 = f10 / 2.0f;
            float f12 = (((f7 * 2.0f) / f11) - f3) / 2.0f;
            float f13 = (((f12 + f3) * f11) / 2.0f) + f;
            this.mNumberOfStages = 2;
            this.mStage[0].setUp(f3, f, 0.0f, f12, f13, f11);
            float f14 = f11 + f11;
            this.mStage[1].setUp(f12, f13, f11, 0.0f, f2, f14);
            this.mDuration = f14;
            if (f14 > f6) {
                return false;
            }
        }
        return true;
    }

    private void rampUpCruseRampDown(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f6 / 3.0f;
        float f8 = f7 * 2.0f;
        float f9 = f8 - f7;
        float f10 = (((f2 - f) * 2.0f) - (f3 * f7)) / (((f9 * 2.0f) + f7) + (f6 - f8));
        this.mDuration = f6;
        this.mNumberOfStages = 3;
        float f11 = f + (((f3 + f10) * f7) / 2.0f);
        this.mStage[0].setUp(f3, f, 0.0f, f10, f11, f7);
        float f12 = f11 + (((f10 + f10) * f9) / 2.0f);
        this.mStage[1].setUp(f10, f11, f7, f10, f12, f8);
        this.mStage[2].setUp(f10, f12, f8, 0.0f, f2, f6);
        this.mDuration = f6;
    }

    double getEasing(double d) {
        double d2 = (d * d * this.mEasingAdapterA) + (d * this.mEasingAdapterB);
        if (d2 > 1.0d) {
            return this.mEasingAdapterDistance;
        }
        return this.mEasing.get(d2) * this.mEasingAdapterDistance;
    }

    private double getEasingDiff(double d) {
        double d2 = (d * d * this.mEasingAdapterA) + (this.mEasingAdapterB * d);
        return d2 > 1.0d ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : this.mEasing.getDiff(d2) * this.mEasingAdapterDistance * ((d * this.mEasingAdapterA) + this.mEasingAdapterB);
    }

    protected void configureEasingAdapter() {
        if (this.mEasing == null) {
            return;
        }
        int i = this.mNumberOfStages - 1;
        float f = this.mStage[i].mStartV;
        float f2 = this.mStage[i].mEndPos - this.mStage[i].mStartPos;
        float unused = this.mStage[i].mEndTime;
        float unused2 = this.mStage[i].mStartTime;
        double d = f;
        double d2 = f2;
        double diff = d / (this.mEasing.getDiff(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) * d2);
        this.mEasingAdapterB = diff;
        double d3 = 1.0d - diff;
        this.mEasingAdapterA = d3;
        this.mEasingAdapterDistance = d2;
        this.mTotalEasingDuration = (float) (((Math.sqrt((d3 * 4.0d) + (diff * diff)) - this.mEasingAdapterB) / (this.mEasingAdapterA * 2.0d)) + this.mStage[i].mStartTime);
    }

    class Stage {
        final int mStage;
        private float mStartV = 0.0f;
        private float mStartPos = 0.0f;
        private float mStartTime = 0.0f;
        private float mEndV = 0.0f;
        private float mEndPos = 0.0f;
        private float mEndTime = 0.0f;
        private float mDeltaV = 0.0f;
        private float mDeltaT = 0.0f;

        Stage(VelocityEasing velocityEasing, int i) {
            this.mStage = i;
        }

        void setUp(float f, float f2, float f3, float f4, float f5, float f6) {
            this.mStartV = f;
            this.mStartPos = f2;
            this.mStartTime = f3;
            this.mEndV = f4;
            this.mEndTime = f6;
            this.mEndPos = f5;
            this.mDeltaV = f4 - f;
            this.mDeltaT = f6 - f3;
        }

        float getPos(float f) {
            float f2 = f - this.mStartTime;
            float f3 = f2 / this.mDeltaT;
            float f4 = this.mStartV;
            return ((f2 * (f4 + ((this.mDeltaV * f3) + f4))) / 2.0f) + this.mStartPos;
        }

        float getVel(float f) {
            float f2 = this.mStartTime;
            return this.mStartV + (this.mDeltaV * ((f - f2) / (this.mEndTime - f2)));
        }
    }
}
