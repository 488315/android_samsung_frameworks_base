package com.android.server.display;

import android.util.FloatProperty;
import android.view.Choreographer;

import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.power.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RampAnimator {
    public float mAnimatedValue;
    public boolean mAnimating;
    public float mAnimationDecreaseMaxTimeSecs;
    public float mAnimationIncreaseMaxTimeSecs;
    public final VcnManagementService$$ExternalSyntheticLambda10 mClock;
    public float mCurrentValue;
    public boolean mFirstTime;
    public long mLastFrameTimeNanos;
    public final Object mObject;
    public final FloatProperty mProperty;
    public float mRate;
    public float mRateAtHbm;
    public float mTarget;
    public float mTargetHlgValue;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DualRampAnimator {
        public boolean mAwaitingCallback;
        public final RampAnimator mFirst;
        public DisplayPowerController.AnonymousClass4 mListener;
        public final RampAnimator mSecond;
        public final AnonymousClass1 mAnimationCallback =
                new Runnable() { // from class:
                                 // com.android.server.display.RampAnimator.DualRampAnimator.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        long frameTimeNanos =
                                DualRampAnimator.this.mChoreographer.getFrameTimeNanos();
                        DualRampAnimator.this.mFirst.performNextAnimationStep(frameTimeNanos);
                        DualRampAnimator.this.mSecond.performNextAnimationStep(frameTimeNanos);
                        if (DualRampAnimator.this.isAnimating()) {
                            DualRampAnimator dualRampAnimator = DualRampAnimator.this;
                            dualRampAnimator.mChoreographer.postCallback(
                                    1, dualRampAnimator.mAnimationCallback, null);
                        } else {
                            DisplayPowerController.AnonymousClass4 anonymousClass4 =
                                    DualRampAnimator.this.mListener;
                            if (anonymousClass4 != null) {
                                anonymousClass4.onAnimationEnd();
                            }
                            DualRampAnimator.this.mAwaitingCallback = false;
                        }
                    }
                };
        public final Choreographer mChoreographer = Choreographer.getInstance();

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.display.RampAnimator$DualRampAnimator$1] */
        public DualRampAnimator(
                Object obj,
                DisplayPowerState.AnonymousClass1 anonymousClass1,
                DisplayPowerState.AnonymousClass1 anonymousClass12) {
            this.mFirst = new RampAnimator(obj, anonymousClass1);
            this.mSecond = new RampAnimator(obj, anonymousClass12);
        }

        public final float getTarget() {
            return this.mFirst.mTarget;
        }

        public final boolean isAnimating() {
            return this.mFirst.mAnimating || this.mSecond.mAnimating;
        }
    }

    public RampAnimator(Object obj, FloatProperty floatProperty) {
        VcnManagementService$$ExternalSyntheticLambda10
                vcnManagementService$$ExternalSyntheticLambda10 =
                        new VcnManagementService$$ExternalSyntheticLambda10();
        this.mFirstTime = true;
        this.mObject = obj;
        this.mProperty = floatProperty;
        this.mClock = vcnManagementService$$ExternalSyntheticLambda10;
    }

    public final void performNextAnimationStep(long j) {
        float f = (j - this.mLastFrameTimeNanos) * 1.0E-9f;
        if (f >= 1.0f) {
            Slog.d("RampAnimator", "Choreographer callback time out: " + f + "s");
        }
        this.mLastFrameTimeNanos = j;
        float f2 = this.mRate;
        if (this.mTargetHlgValue < this.mCurrentValue
                && this.mAnimatedValue > 1.0f
                && !Float.isNaN(this.mRateAtHbm)) {
            f2 = this.mRateAtHbm;
        }
        float f3 = (f * f2) / 1.0f;
        float f4 = this.mTargetHlgValue;
        if (f4 > this.mCurrentValue) {
            this.mAnimatedValue = Math.min(this.mAnimatedValue + f3, f4);
        } else {
            this.mAnimatedValue = Math.max(this.mAnimatedValue - f3, f4);
        }
        float f5 = this.mCurrentValue;
        float f6 = this.mAnimatedValue;
        this.mCurrentValue = f6;
        if (f5 != f6) {
            this.mProperty.setValue(this.mObject, f6);
        }
        if (this.mTargetHlgValue == this.mCurrentValue) {
            this.mAnimating = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setAnimationTarget(float r9, float r10, float r11, boolean r12) {
        /*
            r8 = this;
            r0 = 0
            if (r12 == 0) goto L5
            r1 = r0
            goto L7
        L5:
            float r1 = r8.mAnimationIncreaseMaxTimeSecs
        L7:
            if (r12 == 0) goto Lb
            r12 = r0
            goto Ld
        Lb:
            float r12 = r8.mAnimationDecreaseMaxTimeSecs
        Ld:
            float r2 = r8.mTarget
            int r2 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L19
            r8.mTarget = r9
            r2 = r3
            goto L1a
        L19:
            r2 = r4
        L1a:
            boolean r5 = r8.mFirstTime
            if (r5 != 0) goto L91
            int r6 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r6 > 0) goto L24
            goto L91
        L24:
            float r2 = r8.mCurrentValue
            int r5 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r5 <= 0) goto L39
            int r6 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r6 <= 0) goto L39
            float r6 = r9 - r2
            float r7 = r6 / r10
            int r7 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r7 <= 0) goto L39
            float r10 = r6 / r1
            goto L4b
        L39:
            int r1 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r1 >= 0) goto L4b
            int r0 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r0 <= 0) goto L4b
            float r0 = r2 - r9
            float r1 = r0 / r10
            int r1 = (r1 > r12 ? 1 : (r1 == r12 ? 0 : -1))
            if (r1 <= 0) goto L4b
            float r10 = r0 / r12
        L4b:
            boolean r12 = r8.mAnimating
            if (r12 == 0) goto L6f
            float r0 = r8.mRate
            int r0 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r0 != 0) goto L6f
            float r0 = r8.mRateAtHbm
            int r0 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r0 != 0) goto L6f
            int r0 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r0 > 0) goto L65
            float r0 = r8.mTargetHlgValue
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 <= 0) goto L6f
        L65:
            float r0 = r8.mTargetHlgValue
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 > 0) goto L73
            int r0 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r0 > 0) goto L73
        L6f:
            r8.mRate = r10
            r8.mRateAtHbm = r11
        L73:
            float r10 = r8.mTargetHlgValue
            int r10 = (r10 > r9 ? 1 : (r10 == r9 ? 0 : -1))
            if (r10 == 0) goto L7a
            r4 = r3
        L7a:
            r8.mTargetHlgValue = r9
            if (r12 != 0) goto L8f
            if (r5 == 0) goto L8f
            r8.mAnimating = r3
            r8.mAnimatedValue = r2
            com.android.server.VcnManagementService$$ExternalSyntheticLambda10 r9 = r8.mClock
            r9.getClass()
            long r9 = java.lang.System.nanoTime()
            r8.mLastFrameTimeNanos = r9
        L8f:
            r3 = r4
            goto Lae
        L91:
            if (r5 != 0) goto L9b
            float r10 = r8.mCurrentValue
            int r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r10 != 0) goto L9b
            if (r2 == 0) goto L8f
        L9b:
            r8.mFirstTime = r4
            r8.mRate = r0
            r8.mRateAtHbm = r0
            r8.mTargetHlgValue = r9
            r8.mCurrentValue = r9
            android.util.FloatProperty r10 = r8.mProperty
            java.lang.Object r11 = r8.mObject
            r10.setValue(r11, r9)
            r8.mAnimating = r4
        Lae:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.RampAnimator.setAnimationTarget(float, float,"
                    + " float, boolean):boolean");
    }

    public final void setAnimationTimeLimits(long j, long j2) {
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mAnimationIncreaseMaxTimeSecs = j > 0 ? j / 1000.0f : 0.0f;
        if (j2 > 0) {
            f = j2 / 1000.0f;
        }
        this.mAnimationDecreaseMaxTimeSecs = f;
    }
}
