package com.android.wm.shell.pip2.phone;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipTouchState {
    public static final long DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    public int mActivePointerId;
    public final Runnable mDoubleTapTimeoutCallback;
    public final Runnable mHoverExitTimeoutCallback;
    public final ShellExecutor mMainExecutor;
    public VelocityTracker mVelocityTracker;
    public final ViewConfiguration mViewConfig;
    public long mDownTouchTime = 0;
    public long mLastDownTouchTime = 0;
    public long mUpTouchTime = 0;
    public final PointF mDownTouch = new PointF();
    public final PointF mDownDelta = new PointF();
    public final PointF mLastTouch = new PointF();
    public final PointF mLastDelta = new PointF();
    public final PointF mVelocity = new PointF();
    public boolean mAllowTouches = true;
    public boolean mAllowInputEvents = true;
    public boolean mIsUserInteracting = false;
    public boolean mIsDoubleTap = false;
    public boolean mIsWaitingForDoubleTap = false;
    public boolean mIsDragging = false;
    public boolean mPreviouslyDragging = false;
    public boolean mStartedDragging = false;
    public boolean mAllowDraggingOffscreen = false;
    public int mLastTouchDisplayId = -1;

    public PipTouchState(ViewConfiguration viewConfiguration, Runnable runnable, Runnable runnable2, ShellExecutor shellExecutor) {
        this.mViewConfig = viewConfiguration;
        this.mDoubleTapTimeoutCallback = runnable;
        this.mHoverExitTimeoutCallback = runnable2;
        this.mMainExecutor = shellExecutor;
    }

    public final void addMovementToVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            return;
        }
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        this.mVelocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onTouchEvent(MotionEvent motionEvent) {
        this.mLastTouchDisplayId = motionEvent.getDisplayId();
        int actionMasked = motionEvent.getActionMasked();
        ShellExecutor shellExecutor = this.mMainExecutor;
        boolean z = false;
        z = false;
        z = false;
        if (actionMasked == 0) {
            if (this.mAllowTouches) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                addMovementToVelocityTracker(motionEvent);
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mLastTouch.set(motionEvent.getRawX(), motionEvent.getRawY());
                this.mDownTouch.set(this.mLastTouch);
                this.mAllowDraggingOffscreen = true;
                this.mIsUserInteracting = true;
                long eventTime = motionEvent.getEventTime();
                this.mDownTouchTime = eventTime;
                this.mIsDoubleTap = !this.mPreviouslyDragging && eventTime - this.mLastDownTouchTime < DOUBLE_TAP_TIMEOUT;
                this.mIsWaitingForDoubleTap = false;
                this.mIsDragging = false;
                this.mLastDownTouchTime = eventTime;
                Runnable runnable = this.mDoubleTapTimeoutCallback;
                if (runnable != null) {
                    ((HandlerExecutor) shellExecutor).removeCallbacks(runnable);
                    return;
                }
                return;
            }
            return;
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (this.mIsUserInteracting) {
                    addMovementToVelocityTracker(motionEvent);
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex == -1) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                            ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -5050839515702609964L, 4, "PipTouchState", Long.valueOf(this.mActivePointerId));
                            return;
                        }
                        return;
                    }
                    float rawX = motionEvent.getRawX(findPointerIndex);
                    float rawY = motionEvent.getRawY(findPointerIndex);
                    PointF pointF = this.mLastDelta;
                    PointF pointF2 = this.mLastTouch;
                    pointF.set(rawX - pointF2.x, rawY - pointF2.y);
                    PointF pointF3 = this.mDownDelta;
                    PointF pointF4 = this.mDownTouch;
                    pointF3.set(rawX - pointF4.x, rawY - pointF4.y);
                    byte b = this.mDownDelta.length() > ((float) this.mViewConfig.getScaledTouchSlop());
                    if (this.mIsDragging) {
                        this.mStartedDragging = false;
                    } else if (b != false) {
                        this.mIsDragging = true;
                        this.mStartedDragging = true;
                    }
                    this.mLastTouch.set(rawX, rawY);
                    return;
                }
                return;
            }
            if (actionMasked != 3) {
                if (actionMasked != 6) {
                    if (actionMasked != 11) {
                        return;
                    }
                    ((HandlerExecutor) shellExecutor).removeCallbacks(this.mHoverExitTimeoutCallback);
                    return;
                } else {
                    if (this.mIsUserInteracting) {
                        addMovementToVelocityTracker(motionEvent);
                        int actionIndex = motionEvent.getActionIndex();
                        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                            int i = actionIndex == 0 ? 1 : 0;
                            this.mActivePointerId = motionEvent.getPointerId(i);
                            this.mLastTouch.set(motionEvent.getRawX(i), motionEvent.getRawY(i));
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
        } else {
            if (!this.mIsUserInteracting) {
                return;
            }
            addMovementToVelocityTracker(motionEvent);
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mViewConfig.getScaledMaximumFlingVelocity());
            this.mVelocity.set(this.mVelocityTracker.getXVelocity(), this.mVelocityTracker.getYVelocity());
            int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
            if (findPointerIndex2 == -1) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                    ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3776750517946036956L, 4, "PipTouchState", Long.valueOf(this.mActivePointerId));
                    return;
                }
                return;
            }
            this.mUpTouchTime = motionEvent.getEventTime();
            this.mLastTouch.set(motionEvent.getRawX(findPointerIndex2), motionEvent.getRawY(findPointerIndex2));
            boolean z2 = this.mIsDragging;
            this.mPreviouslyDragging = z2;
            if (!this.mIsDoubleTap && !z2 && this.mUpTouchTime - this.mDownTouchTime < DOUBLE_TAP_TIMEOUT) {
                z = true;
            }
            this.mIsWaitingForDoubleTap = z;
        }
        VelocityTracker velocityTracker2 = this.mVelocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            this.mVelocityTracker = null;
        }
    }

    public final void reset() {
        this.mAllowDraggingOffscreen = false;
        this.mIsDragging = false;
        this.mStartedDragging = false;
        this.mIsUserInteracting = false;
        this.mLastTouchDisplayId = -1;
    }
}
