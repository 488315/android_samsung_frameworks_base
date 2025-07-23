package android.window;

import android.os.SystemProperties;
import android.util.MathUtils;
import android.view.RemoteAnimationTarget;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
public class BackTouchTracker {
    private float mInitTouchX;
    private float mInitTouchY;
    private float mLatestTouchX;
    private float mLatestTouchY;
    private float mMaxDistance;
    private float mNonLinearFactor;
    private float mStartThresholdX;
    private int mSwipeEdge;
    private boolean mTriggerBack;
    private static final String PREDICTIVE_BACK_LINEAR_DISTANCE_PROP = "persist.wm.debug.predictive_back_linear_distance";
    private static final int LINEAR_DISTANCE = SystemProperties.getInt(PREDICTIVE_BACK_LINEAR_DISTANCE_PROP, -1);
    private float mLinearDistance = LINEAR_DISTANCE;
    private boolean mShouldUpdateStartLocation = false;
    private TouchTrackerState mState = TouchTrackerState.INITIAL;

    public enum TouchTrackerState {
        INITIAL,
        ACTIVE,
        FINISHED
    }

    public void update(float f, float f2) {
        float f3 = this.mStartThresholdX;
        if ((f < f3 && this.mSwipeEdge == 0) || (f > f3 && this.mSwipeEdge == 1)) {
            this.mStartThresholdX = f;
            int i = this.mSwipeEdge;
            if ((i == 0 && f < this.mInitTouchX) || (i == 1 && f > this.mInitTouchX)) {
                this.mInitTouchX = f;
            }
        }
        this.mLatestTouchX = f;
        this.mLatestTouchY = f2;
    }

    public void setTriggerBack(boolean z) {
        if (this.mTriggerBack != z && !z && !CoreRune.FW_PREDICTIVE_BACK_ANIM) {
            this.mStartThresholdX = this.mLatestTouchX;
        }
        this.mTriggerBack = z;
    }

    public boolean getTriggerBack() {
        return this.mTriggerBack;
    }

    public boolean shouldUpdateStartLocation() {
        return this.mShouldUpdateStartLocation;
    }

    public void setShouldUpdateStartLocation(boolean z) {
        this.mShouldUpdateStartLocation = z;
    }

    public void setState(TouchTrackerState touchTrackerState) {
        this.mState = touchTrackerState;
    }

    public boolean isInInitialState() {
        return this.mState == TouchTrackerState.INITIAL;
    }

    public boolean isActive() {
        return this.mState == TouchTrackerState.ACTIVE;
    }

    public boolean isFinished() {
        return this.mState == TouchTrackerState.FINISHED;
    }

    public void setGestureStartLocation(float f, float f2, int i) {
        this.mInitTouchX = f;
        this.mInitTouchY = f2;
        this.mLatestTouchX = f;
        this.mLatestTouchY = f2;
        this.mSwipeEdge = i;
        this.mStartThresholdX = f;
    }

    public void updateStartLocation() {
        float f = this.mLatestTouchX;
        this.mInitTouchX = f;
        this.mInitTouchY = this.mLatestTouchY;
        this.mStartThresholdX = f;
        this.mShouldUpdateStartLocation = false;
    }

    public void reset() {
        this.mInitTouchX = 0.0f;
        this.mInitTouchY = 0.0f;
        this.mStartThresholdX = 0.0f;
        this.mTriggerBack = false;
        this.mState = TouchTrackerState.INITIAL;
        this.mSwipeEdge = 0;
        this.mShouldUpdateStartLocation = false;
    }

    public BackMotionEvent createStartEvent(RemoteAnimationTarget remoteAnimationTarget) {
        return new BackMotionEvent(this.mInitTouchX, this.mInitTouchY, 0L, 0.0f, this.mTriggerBack, this.mSwipeEdge, remoteAnimationTarget);
    }

    public BackMotionEvent createProgressEvent() {
        return createProgressEvent(getProgress(this.mLatestTouchX));
    }

    public float getProgress(float f) {
        float f2;
        float f3 = this.mTriggerBack ? this.mInitTouchX : this.mStartThresholdX;
        float max = Math.max(0.0f, this.mSwipeEdge == 0 ? f - f3 : f3 - f);
        float f4 = this.mLinearDistance;
        float maxDistance = getMaxDistance();
        if (maxDistance == 0.0f) {
            maxDistance = 1.0f;
        }
        if (f4 < maxDistance) {
            float f5 = maxDistance - f4;
            float f6 = (this.mNonLinearFactor * f5) + f4;
            if (max > f4) {
                f6 = MathUtils.lerp(f6, maxDistance, (max - f4) / f5);
            }
            f2 = max / f6;
        } else {
            f2 = max / maxDistance;
        }
        return MathUtils.constrain(f2, 0.0f, 1.0f);
    }

    public float getMaxDistance() {
        return this.mMaxDistance;
    }

    public float getLinearDistance() {
        return this.mLinearDistance;
    }

    public float getNonLinearFactor() {
        return this.mNonLinearFactor;
    }

    public BackMotionEvent createProgressEvent(float f) {
        return new BackMotionEvent(this.mLatestTouchX, this.mLatestTouchY, 0L, f, this.mTriggerBack, this.mSwipeEdge, null);
    }

    public void setProgressThresholds(float f, float f2, float f3) {
        int i = LINEAR_DISTANCE;
        if (i >= 0) {
            this.mLinearDistance = i;
        } else {
            this.mLinearDistance = f;
        }
        this.mMaxDistance = f2;
        this.mNonLinearFactor = f3;
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "BackTouchTracker state:");
        printWriter.println(str + "  mState=" + this.mState);
        printWriter.println(str + "  mTriggerBack=" + this.mTriggerBack);
    }
}
