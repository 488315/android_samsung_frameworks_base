package com.android.wm.shell.windowdecor;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragDetector {
    public boolean mDidHoldForMinDuration;
    public boolean mDidStrayBeforeFullHold;
    public int mDragPointerId;
    public final MotionEventHandler mEventHandler;
    public final long mHoldToDragMinDurationMs;
    public final PointF mInputDownPoint;
    public boolean mIsDragEvent;
    public boolean mResultOfDownAction;
    public int mTouchSlop;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface MotionEventHandler {
        boolean handleMotionEvent(View view, MotionEvent motionEvent);
    }

    public DragDetector(MotionEventHandler motionEventHandler, long j, int i) {
        PointF pointF = new PointF();
        this.mInputDownPoint = pointF;
        this.mDragPointerId = -1;
        this.mIsDragEvent = false;
        pointF.set(0.0f, 0.0f);
        this.mDragPointerId = -1;
        this.mResultOfDownAction = false;
        this.mDidStrayBeforeFullHold = false;
        this.mDidHoldForMinDuration = false;
        this.mEventHandler = motionEventHandler;
        this.mHoldToDragMinDurationMs = j;
        this.mTouchSlop = i;
    }

    public static MotionEvent getSinglePointerEvent(MotionEvent motionEvent, int i) {
        return motionEvent.getPointerCount() > 1 ? motionEvent.split(1 << i) : motionEvent;
    }

    public final boolean onMotionEvent(View view, MotionEvent motionEvent) {
        int source = motionEvent.getSource() & PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL;
        MotionEventHandler motionEventHandler = this.mEventHandler;
        if (source != 4098) {
            return motionEventHandler.handleMotionEvent(view, motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        boolean z = false;
        if (actionMasked == 0) {
            this.mDragPointerId = motionEvent.getPointerId(0);
            this.mInputDownPoint.set(motionEvent.getRawX(0), motionEvent.getRawY(0));
            boolean handleMotionEvent = motionEventHandler.handleMotionEvent(view, motionEvent);
            this.mResultOfDownAction = handleMotionEvent;
            return handleMotionEvent;
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                int i = this.mDragPointerId;
                if (i == -1) {
                    return this.mResultOfDownAction;
                }
                int findPointerIndex = motionEvent.findPointerIndex(i);
                if (findPointerIndex == -1) {
                    Log.w("DragDetector", "Invalid pointer index on ACTION_MOVE. Drag pointer id: " + this.mDragPointerId);
                    return this.mResultOfDownAction;
                }
                if (!this.mIsDragEvent) {
                    float rawX = motionEvent.getRawX(findPointerIndex) - this.mInputDownPoint.x;
                    float rawY = motionEvent.getRawY(findPointerIndex) - this.mInputDownPoint.y;
                    float eventTime = motionEvent.getEventTime() - motionEvent.getDownTime();
                    boolean z2 = Math.hypot((double) rawX, (double) rawY) > ((double) this.mTouchSlop);
                    long j = this.mHoldToDragMinDurationMs;
                    if (j <= 0) {
                        this.mDidHoldForMinDuration = true;
                    } else {
                        if (z2 && eventTime < j) {
                            this.mDidStrayBeforeFullHold = true;
                        }
                        if (!this.mDidStrayBeforeFullHold && eventTime >= j) {
                            this.mDidHoldForMinDuration = true;
                        }
                    }
                    if (this.mDidHoldForMinDuration && z2) {
                        z = true;
                    }
                    this.mIsDragEvent = z;
                }
                return !this.mIsDragEvent ? this.mResultOfDownAction : motionEventHandler.handleMotionEvent(view, getSinglePointerEvent(motionEvent, this.mDragPointerId));
            }
            if (actionMasked != 3) {
                if (actionMasked != 6) {
                    return (actionMasked == 7 || actionMasked == 9 || actionMasked == 10) ? motionEventHandler.handleMotionEvent(view, getSinglePointerEvent(motionEvent, this.mDragPointerId)) : this.mResultOfDownAction;
                }
                int i2 = this.mDragPointerId;
                if (i2 == -1) {
                    return this.mResultOfDownAction;
                }
                if (i2 != motionEvent.getPointerId(motionEvent.getActionIndex())) {
                    return this.mResultOfDownAction;
                }
                int i3 = this.mDragPointerId;
                this.mDragPointerId = -1;
                return motionEventHandler.handleMotionEvent(view, getSinglePointerEvent(motionEvent, i3));
            }
        }
        int i4 = this.mDragPointerId;
        this.mIsDragEvent = false;
        this.mInputDownPoint.set(0.0f, 0.0f);
        this.mDragPointerId = -1;
        this.mResultOfDownAction = false;
        this.mDidStrayBeforeFullHold = false;
        this.mDidHoldForMinDuration = false;
        if (i4 == -1) {
            return false;
        }
        return motionEventHandler.handleMotionEvent(view, getSinglePointerEvent(motionEvent, i4));
    }
}
