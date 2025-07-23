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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0043, code lost:
    
        if (r8 == false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void config(float r9, float r10, float r11, float r12, float r13, float r14, com.android.internal.widget.remotecompose.core.operations.utilities.touch.VelocityEasing.Easing r15) {
        /*
            r8 = this;
            int r0 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r0 != 0) goto L7
            r0 = 1065353216(0x3f800000, float:1.0)
            float r9 = r9 + r0
        L7:
            r1 = r9
            r8.mStartPos = r1
            r8.mEndPos = r10
            if (r15 == 0) goto L14
            com.android.internal.widget.remotecompose.core.operations.utilities.touch.VelocityEasing$Easing r9 = r15.clone()
            r8.mEasing = r9
        L14:
            float r9 = r10 - r1
            float r9 = java.lang.Math.signum(r9)
            float r5 = r14 * r9
            float r4 = r13 * r9
            double r13 = (double) r11
            r2 = 0
            int r13 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r13 != 0) goto L29
            r11 = 953267991(0x38d1b717, float:1.0E-4)
            float r11 = r11 * r9
        L29:
            r3 = r11
            r8.mStartV = r3
            boolean r9 = r8.rampDown(r1, r10, r3, r12)
            if (r9 != 0) goto L51
            boolean r9 = r8.mOneDimension
            r0 = r8
            r2 = r10
            if (r9 == 0) goto L46
            r6 = r5
            r5 = r4
            r4 = r12
            boolean r8 = r0.cruseThenRampDown(r1, r2, r3, r4, r5, r6)
            r7 = r6
            r6 = r4
            r4 = r5
            r5 = r7
            if (r8 != 0) goto L52
            goto L47
        L46:
            r6 = r12
        L47:
            boolean r8 = r0.rampUpRampDown(r1, r2, r3, r4, r5, r6)
            if (r8 != 0) goto L52
            r0.rampUpCruseRampDown(r1, r2, r3, r4, r5, r6)
            goto L52
        L51:
            r0 = r8
        L52:
            boolean r8 = r0.mOneDimension
            if (r8 == 0) goto L59
            r0.configureEasingAdapter()
        L59:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.remotecompose.core.operations.utilities.touch.VelocityEasing.config(float, float, float, float, float, float, com.android.internal.widget.remotecompose.core.operations.utilities.touch.VelocityEasing$Easing):void");
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
        float signum = Math.signum(f4) * ((float) Math.sqrt((f4 * f7) + ((f3 * f3) / 2.0f)));
        if (f5 / signum <= 1.0f) {
            return false;
        }
        float f8 = (signum - f3) / f4;
        float f9 = (((signum + f3) * f8) / 2.0f) + f;
        this.mNumberOfStages = 2;
        this.mStage[0].setUp(f3, f, 0.0f, signum, f9, f8);
        float f10 = (signum / f4) + f8;
        this.mStage[1].setUp(signum, f9, f8, 0.0f, f2, f10);
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
