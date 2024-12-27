package com.android.server.accessibility.gestures;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.server.accessibility.gestures.GestureMatcher;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SecondFingerMultiTap extends GestureMatcher {
    public float mBaseX;
    public float mBaseY;
    public int mCurrentTaps;
    public final int mDoubleTapSlop;
    public int mSecondFingerPointerId;
    public final int mTargetTaps;
    public final int mTouchSlop;

    public SecondFingerMultiTap(Context context, GestureMatcher.StateChangeListener stateChangeListener) {
        super(17, new Handler(context.getMainLooper()), stateChangeListener);
        this.mTargetTaps = 2;
        this.mDoubleTapSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        ViewConfiguration.getTapTimeout();
        ViewConfiguration.getDoubleTapTimeout();
        clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void clear() {
        this.mCurrentTaps = 0;
        this.mBaseX = Float.NaN;
        this.mBaseY = Float.NaN;
        this.mSecondFingerPointerId = -1;
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String getGestureName() {
        int i = this.mTargetTaps;
        if (i == 2) {
            return "Second Finger Double Tap";
        }
        if (i == 3) {
            return "Second Finger Triple Tap";
        }
        return "Second Finger " + Integer.toString(i) + " Taps";
    }

    public final boolean isSecondFingerInsideSlop(int i, MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mSecondFingerPointerId);
        if (findPointerIndex == -1) {
            return false;
        }
        float x = this.mBaseX - motionEvent.getX(findPointerIndex);
        float y = this.mBaseY - motionEvent.getY(findPointerIndex);
        return (x == FullScreenMagnificationGestureHandler.MAX_SCALE && y == FullScreenMagnificationGestureHandler.MAX_SCALE) || Math.hypot((double) x, (double) y) <= ((double) i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onMove(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        int pointerCount = motionEvent.getPointerCount();
        if (pointerCount != 1) {
            if (pointerCount != 2) {
                setState(3, motionEvent, motionEvent2, i);
            } else {
                if (isSecondFingerInsideSlop(this.mTouchSlop, motionEvent2)) {
                    return;
                }
                setState(3, motionEvent, motionEvent2, i);
            }
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (motionEvent.getPointerCount() > 2) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        this.mSecondFingerPointerId = motionEvent.getPointerId(GestureUtils.getActionIndex(motionEvent));
        long tapTimeout = ViewConfiguration.getTapTimeout();
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, tapTimeout, motionEvent, motionEvent2, i);
        if (Float.isNaN(this.mBaseX) && Float.isNaN(this.mBaseY)) {
            this.mBaseX = motionEvent.getX();
            this.mBaseY = motionEvent.getY();
        }
        if (!isSecondFingerInsideSlop(this.mDoubleTapSlop, motionEvent2)) {
            setState(3, motionEvent, motionEvent2, i);
        }
        this.mBaseX = motionEvent.getX();
        this.mBaseY = motionEvent.getY();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (motionEvent.getPointerCount() > 2) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
        if (!isSecondFingerInsideSlop(this.mTouchSlop, motionEvent2)) {
            setState(3, motionEvent, motionEvent2, i);
        }
        int i2 = this.mState;
        if (i2 != 1 && i2 != 0) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        int i3 = this.mCurrentTaps + 1;
        this.mCurrentTaps = i3;
        if (i3 == this.mTargetTaps) {
            setState(2, motionEvent, motionEvent2, i);
        } else {
            cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String toString() {
        return super.toString() + ", Taps:" + this.mCurrentTaps + ", mBaseX: " + Float.toString(this.mBaseX) + ", mBaseY: " + Float.toString(this.mBaseY);
    }
}
