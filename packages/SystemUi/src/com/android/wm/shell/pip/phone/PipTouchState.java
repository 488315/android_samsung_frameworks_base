package com.android.wm.shell.pip.phone;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipTouchState {
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

    public long getDoubleTapTimeoutCallbackDelay() {
        if (this.mIsWaitingForDoubleTap) {
            return Math.max(0L, DOUBLE_TAP_TIMEOUT - (this.mUpTouchTime - this.mDownTouchTime));
        }
        return -1L;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onTouchEvent(MotionEvent motionEvent) {
        this.mLastTouchDisplayId = motionEvent.getDisplayId();
        int actionMasked = motionEvent.getActionMasked();
        PointF pointF = this.mDownTouch;
        PointF pointF2 = this.mLastTouch;
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
                pointF2.set(motionEvent.getRawX(), motionEvent.getRawY());
                pointF.set(pointF2);
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
        ViewConfiguration viewConfiguration = this.mViewConfig;
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (this.mIsUserInteracting) {
                    addMovementToVelocityTracker(motionEvent);
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex == -1) {
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1168568957, 4, "%s: Invalid active pointer id on MOVE: %d", "PipTouchState", Long.valueOf(this.mActivePointerId));
                            return;
                        }
                        return;
                    }
                    float rawX = motionEvent.getRawX(findPointerIndex);
                    float rawY = motionEvent.getRawY(findPointerIndex);
                    this.mLastDelta.set(rawX - pointF2.x, rawY - pointF2.y);
                    PointF pointF3 = this.mDownDelta;
                    pointF3.set(rawX - pointF.x, rawY - pointF.y);
                    Object[] objArr = pointF3.length() > ((float) viewConfiguration.getScaledTouchSlop());
                    if (this.mIsDragging) {
                        this.mStartedDragging = false;
                    } else if (objArr != false) {
                        this.mIsDragging = true;
                        this.mStartedDragging = true;
                    }
                    pointF2.set(rawX, rawY);
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
                            pointF2.set(motionEvent.getRawX(i), motionEvent.getRawY(i));
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
            this.mVelocityTracker.computeCurrentVelocity(1000, viewConfiguration.getScaledMaximumFlingVelocity());
            this.mVelocity.set(this.mVelocityTracker.getXVelocity(), this.mVelocityTracker.getYVelocity());
            int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
            if (findPointerIndex2 == -1) {
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1325170887, 4, "%s: Invalid active pointer id on UP: %d", "PipTouchState", Long.valueOf(this.mActivePointerId));
                    return;
                }
                return;
            }
            this.mUpTouchTime = motionEvent.getEventTime();
            pointF2.set(motionEvent.getRawX(findPointerIndex2), motionEvent.getRawY(findPointerIndex2));
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

    public void scheduleHoverExitTimeoutCallback() {
        ShellExecutor shellExecutor = this.mMainExecutor;
        Runnable runnable = this.mHoverExitTimeoutCallback;
        ((HandlerExecutor) shellExecutor).removeCallbacks(runnable);
        ((HandlerExecutor) shellExecutor).executeDelayed(150L, runnable);
    }
}
