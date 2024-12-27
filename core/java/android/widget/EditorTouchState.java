package android.widget;

import android.view.MotionEvent;
import android.view.ViewConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class EditorTouchState {
    private float mInitialDragDirectionXYRatio;
    private boolean mIsOnHandle;
    private long mLastDownMillis;
    private float mLastDownX;
    private float mLastDownY;
    private long mLastUpMillis;
    private float mLastUpX;
    private float mLastUpY;
    private boolean mMovedEnoughForDrag;
    private boolean mMultiTapInSameArea;
    private int mMultiTapStatus = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface MultiTapStatus {
        public static final int DOUBLE_TAP = 2;
        public static final int FIRST_TAP = 1;
        public static final int NONE = 0;
        public static final int TRIPLE_CLICK = 3;
    }

    public float getLastDownX() {
        return this.mLastDownX;
    }

    public float getLastDownY() {
        return this.mLastDownY;
    }

    public float getLastUpX() {
        return this.mLastUpX;
    }

    public float getLastUpY() {
        return this.mLastUpY;
    }

    public boolean isDoubleTap() {
        return this.mMultiTapStatus == 2;
    }

    public boolean isTripleClick() {
        return this.mMultiTapStatus == 3;
    }

    public boolean isMultiTap() {
        return this.mMultiTapStatus == 2 || this.mMultiTapStatus == 3;
    }

    public boolean isMultiTapInSameArea() {
        return isMultiTap() && this.mMultiTapInSameArea;
    }

    public boolean isMovedEnoughForDrag() {
        return this.mMovedEnoughForDrag;
    }

    public float getInitialDragDirectionXYRatio() {
        return this.mInitialDragDirectionXYRatio;
    }

    public void setIsOnHandle(boolean onHandle) {
        this.mIsOnHandle = onHandle;
    }

    public boolean isOnHandle() {
        return this.mIsOnHandle;
    }

    public void update(MotionEvent event, ViewConfiguration config) {
        int action = event.getActionMasked();
        if (action == 0) {
            boolean isMouse = event.isFromSource(8194);
            long millisSinceLastUp = event.getEventTime() - this.mLastUpMillis;
            long millisBetweenLastDownAndLastUp = this.mLastUpMillis - this.mLastDownMillis;
            if (millisSinceLastUp <= ViewConfiguration.getDoubleTapTimeout()
                    && millisBetweenLastDownAndLastUp <= ViewConfiguration.getDoubleTapTimeout()
                    && (this.mMultiTapStatus == 1 || (this.mMultiTapStatus == 2 && isMouse))) {
                if (this.mMultiTapStatus == 1) {
                    this.mMultiTapStatus = 2;
                } else {
                    this.mMultiTapStatus = 3;
                }
                this.mMultiTapInSameArea =
                        isDistanceWithin(
                                this.mLastDownX,
                                this.mLastDownY,
                                event.getX(),
                                event.getY(),
                                config.getScaledDoubleTapSlop());
            } else {
                this.mMultiTapStatus = 1;
                this.mMultiTapInSameArea = false;
            }
            this.mLastDownX = event.getX();
            this.mLastDownY = event.getY();
            this.mLastDownMillis = event.getEventTime();
            this.mMovedEnoughForDrag = false;
            this.mInitialDragDirectionXYRatio = 0.0f;
            return;
        }
        if (action == 1) {
            this.mLastUpX = event.getX();
            this.mLastUpY = event.getY();
            this.mLastUpMillis = event.getEventTime();
            this.mMovedEnoughForDrag = false;
            this.mInitialDragDirectionXYRatio = 0.0f;
            return;
        }
        if (action == 2) {
            if (!this.mMovedEnoughForDrag) {
                float deltaX = event.getX() - this.mLastDownX;
                float deltaY = event.getY() - this.mLastDownY;
                float deltaXSquared = deltaX * deltaX;
                float distanceSquared = (deltaY * deltaY) + deltaXSquared;
                int touchSlop = config.getScaledTouchSlop();
                this.mMovedEnoughForDrag = distanceSquared > ((float) (touchSlop * touchSlop));
                if (this.mMovedEnoughForDrag) {
                    this.mInitialDragDirectionXYRatio =
                            deltaY == 0.0f ? Float.MAX_VALUE : Math.abs(deltaX / deltaY);
                    return;
                }
                return;
            }
            return;
        }
        if (action == 3) {
            this.mLastDownMillis = 0L;
            this.mLastUpMillis = 0L;
            this.mMultiTapStatus = 0;
            this.mMultiTapInSameArea = false;
            this.mMovedEnoughForDrag = false;
            this.mInitialDragDirectionXYRatio = 0.0f;
        }
    }

    public static boolean isDistanceWithin(
            float x1, float y1, float x2, float y2, int maxDistance) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        float distanceSquared = (deltaX * deltaX) + (deltaY * deltaY);
        return distanceSquared <= ((float) (maxDistance * maxDistance));
    }

    public static float getXYRatio(int angleFromVerticalInDegrees) {
        if (angleFromVerticalInDegrees <= 0) {
            return 0.0f;
        }
        if (angleFromVerticalInDegrees >= 90) {
            return Float.MAX_VALUE;
        }
        return (float) Math.tan(Math.toRadians(angleFromVerticalInDegrees));
    }
}
