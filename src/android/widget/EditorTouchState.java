package android.widget;

import android.view.MotionEvent;
import android.view.ViewConfiguration;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
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

    public static boolean isDistanceWithin(float f, float f2, float f3, float f4, int i) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        return (f5 * f5) + (f6 * f6) <= ((float) (i * i));
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
        int i = this.mMultiTapStatus;
        return i == 2 || i == 3;
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

    public void setIsOnHandle(boolean z) {
        this.mIsOnHandle = z;
    }

    public boolean isOnHandle() {
        return this.mIsOnHandle;
    }

    public void update(MotionEvent motionEvent, ViewConfiguration viewConfiguration) {
        int i;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            boolean isFromSource = motionEvent.isFromSource(8194);
            long eventTime = motionEvent.getEventTime();
            long j = this.mLastUpMillis;
            long j2 = eventTime - j;
            long j3 = j - this.mLastDownMillis;
            if (j2 <= ViewConfiguration.getDoubleTapTimeout() && j3 <= ViewConfiguration.getDoubleTapTimeout() && ((i = this.mMultiTapStatus) == 1 || (i == 2 && isFromSource))) {
                if (i == 1) {
                    this.mMultiTapStatus = 2;
                } else {
                    this.mMultiTapStatus = 3;
                }
                this.mMultiTapInSameArea = isDistanceWithin(this.mLastDownX, this.mLastDownY, motionEvent.getX(), motionEvent.getY(), viewConfiguration.getScaledDoubleTapSlop());
            } else {
                this.mMultiTapStatus = 1;
                this.mMultiTapInSameArea = false;
            }
            this.mLastDownX = motionEvent.getX();
            this.mLastDownY = motionEvent.getY();
            this.mLastDownMillis = motionEvent.getEventTime();
            this.mMovedEnoughForDrag = false;
            this.mInitialDragDirectionXYRatio = 0.0f;
            return;
        }
        if (actionMasked == 1) {
            this.mLastUpX = motionEvent.getX();
            this.mLastUpY = motionEvent.getY();
            this.mLastUpMillis = motionEvent.getEventTime();
            this.mMovedEnoughForDrag = false;
            this.mInitialDragDirectionXYRatio = 0.0f;
            return;
        }
        if (actionMasked != 2) {
            if (actionMasked == 3) {
                this.mLastDownMillis = 0L;
                this.mLastUpMillis = 0L;
                this.mMultiTapStatus = 0;
                this.mMultiTapInSameArea = false;
                this.mMovedEnoughForDrag = false;
                this.mInitialDragDirectionXYRatio = 0.0f;
                return;
            }
            return;
        }
        if (this.mMovedEnoughForDrag) {
            return;
        }
        float x = motionEvent.getX() - this.mLastDownX;
        float y = motionEvent.getY() - this.mLastDownY;
        float f = (x * x) + (y * y);
        int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        boolean z = f > ((float) (scaledTouchSlop * scaledTouchSlop));
        this.mMovedEnoughForDrag = z;
        if (z) {
            this.mInitialDragDirectionXYRatio = y == 0.0f ? Float.MAX_VALUE : Math.abs(x / y);
        }
    }

    public static float getXYRatio(int i) {
        if (i <= 0) {
            return 0.0f;
        }
        if (i >= 90) {
            return Float.MAX_VALUE;
        }
        return (float) Math.tan(Math.toRadians(i));
    }
}
