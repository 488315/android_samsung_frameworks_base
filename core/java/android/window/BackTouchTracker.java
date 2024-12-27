package android.window;

import android.os.SystemProperties;
import android.util.MathUtils;
import android.view.RemoteAnimationTarget;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public class BackTouchTracker {
    private float mInitTouchX;
    private float mInitTouchY;
    private float mLatestTouchX;
    private float mLatestTouchY;
    private float mLatestVelocityX;
    private float mLatestVelocityY;
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

    public void update(float touchX, float touchY, float velocityX, float velocityY) {
        if ((touchX < this.mStartThresholdX && this.mSwipeEdge == 0) || (touchX > this.mStartThresholdX && this.mSwipeEdge == 1)) {
            this.mStartThresholdX = touchX;
            if ((this.mSwipeEdge == 0 && this.mStartThresholdX < this.mInitTouchX) || (this.mSwipeEdge == 1 && this.mStartThresholdX > this.mInitTouchX)) {
                this.mInitTouchX = this.mStartThresholdX;
            }
        }
        this.mLatestTouchX = touchX;
        this.mLatestTouchY = touchY;
        this.mLatestVelocityX = velocityX;
        this.mLatestVelocityY = velocityY;
    }

    public void setTriggerBack(boolean triggerBack) {
        if (this.mTriggerBack != triggerBack && !triggerBack && !CoreRune.FW_PREDICTIVE_BACK_ANIM) {
            this.mStartThresholdX = this.mLatestTouchX;
        }
        this.mTriggerBack = triggerBack;
    }

    public boolean getTriggerBack() {
        return this.mTriggerBack;
    }

    public boolean shouldUpdateStartLocation() {
        return this.mShouldUpdateStartLocation;
    }

    public void setShouldUpdateStartLocation(boolean shouldUpdate) {
        this.mShouldUpdateStartLocation = shouldUpdate;
    }

    public void setState(TouchTrackerState state) {
        this.mState = state;
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

    public void setGestureStartLocation(float touchX, float touchY, int swipeEdge) {
        this.mInitTouchX = touchX;
        this.mInitTouchY = touchY;
        this.mLatestTouchX = touchX;
        this.mLatestTouchY = touchY;
        this.mSwipeEdge = swipeEdge;
        this.mStartThresholdX = this.mInitTouchX;
    }

    public void updateStartLocation() {
        this.mInitTouchX = this.mLatestTouchX;
        this.mInitTouchY = this.mLatestTouchY;
        this.mStartThresholdX = this.mInitTouchX;
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

    public BackMotionEvent createStartEvent(RemoteAnimationTarget target) {
        return new BackMotionEvent(this.mInitTouchX, this.mInitTouchY, 0.0f, 0.0f, 0.0f, this.mTriggerBack, this.mSwipeEdge, target);
    }

    public BackMotionEvent createProgressEvent() {
        float progress = getProgress(this.mLatestTouchX);
        return createProgressEvent(progress);
    }

    public float getProgress(float touchX) {
        float distance;
        float progress;
        float startX = this.mTriggerBack ? this.mInitTouchX : this.mStartThresholdX;
        if (this.mSwipeEdge == 0) {
            distance = touchX - startX;
        } else {
            distance = startX - touchX;
        }
        float deltaX = Math.max(0.0f, distance);
        float linearDistance = this.mLinearDistance;
        float maxDistance = getMaxDistance();
        float maxDistance2 = maxDistance == 0.0f ? 1.0f : maxDistance;
        if (linearDistance < maxDistance2) {
            float nonLinearDistance = maxDistance2 - linearDistance;
            float initialTarget = (this.mNonLinearFactor * nonLinearDistance) + linearDistance;
            boolean isLinear = deltaX <= linearDistance;
            if (isLinear) {
                progress = deltaX / initialTarget;
            } else {
                float progress2 = deltaX - linearDistance;
                float nonLinearProgress = progress2 / nonLinearDistance;
                float currentTarget = MathUtils.lerp(initialTarget, maxDistance2, nonLinearProgress);
                progress = deltaX / currentTarget;
            }
        } else {
            progress = deltaX / maxDistance2;
        }
        return MathUtils.constrain(progress, 0.0f, 1.0f);
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

    public BackMotionEvent createProgressEvent(float progress) {
        return new BackMotionEvent(this.mLatestTouchX, this.mLatestTouchY, progress, this.mLatestVelocityX, this.mLatestVelocityY, this.mTriggerBack, this.mSwipeEdge, null);
    }

    public void setProgressThresholds(float linearDistance, float maxDistance, float nonLinearFactor) {
        if (LINEAR_DISTANCE >= 0) {
            this.mLinearDistance = LINEAR_DISTANCE;
        } else {
            this.mLinearDistance = linearDistance;
        }
        this.mMaxDistance = maxDistance;
        this.mNonLinearFactor = nonLinearFactor;
    }

    public void dump(PrintWriter pw, String prefix) {
        pw.println(prefix + "BackTouchTracker state:");
        pw.println(prefix + "  mState=" + this.mState);
        pw.println(prefix + "  mTriggerBack=" + this.mTriggerBack);
    }
}
