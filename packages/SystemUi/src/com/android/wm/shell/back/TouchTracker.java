package com.android.wm.shell.back;

import android.os.SystemProperties;
import android.util.MathUtils;
import android.view.RemoteAnimationTarget;
import android.window.BackMotionEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TouchTracker {
    public static final int LINEAR_DISTANCE = SystemProperties.getInt("persist.wm.debug.predictive_back_linear_distance", -1);
    public boolean mCancelled;
    public float mInitTouchX;
    public float mInitTouchY;
    public float mLatestTouchX;
    public float mLatestTouchY;
    public float mLatestVelocityX;
    public float mLatestVelocityY;
    public float mLinearDistance = LINEAR_DISTANCE;
    public float mMaxDistance;
    public float mNonLinearFactor;
    public float mStartThresholdX;
    public int mSwipeEdge;
    public boolean mTriggerBack;

    public final BackMotionEvent createProgressEvent() {
        return new BackMotionEvent(this.mLatestTouchX, this.mLatestTouchY, !this.mCancelled ? getProgress(this.mLatestTouchX) : 0.0f, this.mLatestVelocityX, this.mLatestVelocityY, this.mSwipeEdge, (RemoteAnimationTarget) null);
    }

    public final float getProgress(float f) {
        float f2;
        float abs = Math.abs((this.mTriggerBack ? this.mInitTouchX : this.mStartThresholdX) - f);
        float f3 = this.mLinearDistance;
        float f4 = this.mMaxDistance;
        if (f4 == 0.0f) {
            f4 = 1.0f;
        }
        if (f3 < f4) {
            float f5 = f4 - f3;
            float f6 = (this.mNonLinearFactor * f5) + f3;
            if (!(abs <= f3)) {
                f6 = MathUtils.lerp(f6, f4, (abs - f3) / f5);
            }
            f2 = abs / f6;
        } else {
            f2 = abs / f4;
        }
        return MathUtils.constrain(f2, 0.0f, 1.0f);
    }
}
