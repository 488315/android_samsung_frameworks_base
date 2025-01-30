package com.android.p038wm.shell.windowdecor;

import android.graphics.PointF;
import android.util.Slog;
import android.view.MotionEvent;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragDetector {
    public final String TAG = "DragDetector";
    public int mDragPointerId;
    public final MotionEventHandler mEventHandler;
    public final PointF mInputDownPoint;
    public boolean mIsDragEvent;
    public boolean mResultOfDownAction;
    public int mTouchSlop;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface MotionEventHandler {
        boolean handleMotionEvent(MotionEvent motionEvent);
    }

    public DragDetector(MotionEventHandler motionEventHandler) {
        PointF pointF = new PointF();
        this.mInputDownPoint = pointF;
        this.mIsDragEvent = false;
        pointF.set(0.0f, 0.0f);
        this.mDragPointerId = -1;
        this.mResultOfDownAction = false;
        this.mEventHandler = motionEventHandler;
    }

    public final boolean onMotionEvent(MotionEvent motionEvent) {
        boolean z = (motionEvent.getSource() & PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL) == 4098;
        MotionEventHandler motionEventHandler = this.mEventHandler;
        if (!z) {
            return motionEventHandler.handleMotionEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        PointF pointF = this.mInputDownPoint;
        if (actionMasked == 0) {
            this.mDragPointerId = motionEvent.getPointerId(0);
            pointF.set(motionEvent.getRawX(0), motionEvent.getRawY(0));
            boolean handleMotionEvent = motionEventHandler.handleMotionEvent(motionEvent);
            this.mResultOfDownAction = handleMotionEvent;
            return handleMotionEvent;
        }
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (!this.mIsDragEvent) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mDragPointerId);
                    if (findPointerIndex == -1) {
                        Slog.w(this.TAG, LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("Invalid dragPointerIndex=", findPointerIndex, " in DragDetector#onMotionEvent"));
                        return false;
                    }
                    this.mIsDragEvent = Math.hypot((double) (motionEvent.getRawX(findPointerIndex) - pointF.x), (double) (motionEvent.getRawY(findPointerIndex) - pointF.y)) > ((double) this.mTouchSlop);
                }
                return this.mIsDragEvent ? motionEventHandler.handleMotionEvent(motionEvent) : this.mResultOfDownAction;
            }
            if (actionMasked != 3) {
                return motionEventHandler.handleMotionEvent(motionEvent);
            }
        }
        this.mIsDragEvent = false;
        pointF.set(0.0f, 0.0f);
        this.mDragPointerId = -1;
        this.mResultOfDownAction = false;
        return motionEventHandler.handleMotionEvent(motionEvent);
    }
}
