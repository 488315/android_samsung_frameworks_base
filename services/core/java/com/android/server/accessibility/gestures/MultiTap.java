package com.android.server.accessibility.gestures;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

public class MultiTap extends GestureMatcher {
    public float mBaseX;
    public float mBaseY;
    public int mCurrentTaps;
    public final int mDoubleTapSlop;
    public final int mTargetTaps;
    public final int mTouchSlop;

    public MultiTap(
            Context context,
            int i,
            int i2,
            GestureMatcher.StateChangeListener stateChangeListener) {
        super(i2, new Handler(context.getMainLooper()), stateChangeListener);
        this.mTargetTaps = i;
        this.mDoubleTapSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() * 2;
        ViewConfiguration.getTapTimeout();
        ViewConfiguration.getDoubleTapTimeout();
        clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void clear() {
        this.mCurrentTaps = 0;
        this.mBaseX = Float.NaN;
        this.mBaseY = Float.NaN;
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public String getGestureName() {
        int i = this.mTargetTaps;
        if (i == 2) {
            return "Double Tap";
        }
        if (i == 3) {
            return "Triple Tap";
        }
        return Integer.toString(i) + " Taps";
    }

    public final boolean isInsideSlop(int i, MotionEvent motionEvent) {
        float x = this.mBaseX - motionEvent.getX();
        float y = this.mBaseY - motionEvent.getY();
        return (x == FullScreenMagnificationGestureHandler.MAX_SCALE
                        && y == FullScreenMagnificationGestureHandler.MAX_SCALE)
                || Math.hypot((double) x, (double) y) <= ((double) i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        long tapTimeout = ViewConfiguration.getTapTimeout();
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, tapTimeout, motionEvent, motionEvent2, i);
        if (Float.isNaN(this.mBaseX) && Float.isNaN(this.mBaseY)) {
            this.mBaseX = motionEvent.getX();
            this.mBaseY = motionEvent.getY();
        }
        if (!isInsideSlop(this.mDoubleTapSlop, motionEvent2)) {
            setState(3, motionEvent, motionEvent2, i);
        }
        this.mBaseX = motionEvent.getX();
        this.mBaseY = motionEvent.getY();
        if (this.mCurrentTaps + 1 == this.mTargetTaps) {
            setState(1, motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onMove(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (isInsideSlop(this.mTouchSlop, motionEvent2)) {
            return;
        }
        setState(3, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
        if (!isInsideSlop(this.mTouchSlop, motionEvent2)) {
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
    public final String toString() {
        return super.toString()
                + ", Taps:"
                + this.mCurrentTaps
                + ", mBaseX: "
                + Float.toString(this.mBaseX)
                + ", mBaseY: "
                + Float.toString(this.mBaseY);
    }
}
