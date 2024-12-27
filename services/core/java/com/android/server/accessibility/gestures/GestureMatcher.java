package com.android.server.accessibility.gestures;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.accessibility.Flags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class GestureMatcher {
    public final int mGestureId;
    public final Handler mHandler;
    public StateChangeListener mListener;
    public int mState = 0;
    public final DelayedTransition mDelayedTransition = new DelayedTransition();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DelayedTransition implements Runnable {
        public MotionEvent mEvent;
        public int mPolicyFlags;
        public MotionEvent mRawEvent;
        public int mTargetState;

        public DelayedTransition() {}

        public final void cancel() {
            if (TouchExplorer.DEBUG && GestureMatcher.this.mHandler.hasCallbacks(this)) {
                Slog.d(
                        "GestureMatcher.DelayedTransition",
                        GestureMatcher.this.getGestureName()
                                + ": canceling delayed transition to "
                                + GestureMatcher.getStateSymbolicName(this.mTargetState));
            }
            GestureMatcher.this.mHandler.removeCallbacks(this);
            recycleEvent();
        }

        public final void post(
                int i, long j, MotionEvent motionEvent, MotionEvent motionEvent2, int i2) {
            recycleEvent();
            this.mTargetState = i;
            if (Flags.copyEventsForGestureDetection()) {
                this.mEvent = motionEvent.copy();
                this.mRawEvent = motionEvent2.copy();
            } else {
                this.mEvent = motionEvent;
                this.mRawEvent = motionEvent2;
            }
            this.mPolicyFlags = i2;
            GestureMatcher.this.mHandler.postDelayed(this, j);
            if (TouchExplorer.DEBUG) {
                Slog.d(
                        "GestureMatcher.DelayedTransition",
                        GestureMatcher.this.getGestureName()
                                + ": posting delayed transition to "
                                + GestureMatcher.getStateSymbolicName(this.mTargetState));
            }
        }

        public final void recycleEvent() {
            MotionEvent motionEvent;
            if (!Flags.copyEventsForGestureDetection()
                    || (motionEvent = this.mEvent) == null
                    || this.mRawEvent == null) {
                return;
            }
            motionEvent.recycle();
            this.mRawEvent.recycle();
            this.mEvent = null;
            this.mRawEvent = null;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (TouchExplorer.DEBUG) {
                Slog.d(
                        "GestureMatcher.DelayedTransition",
                        GestureMatcher.this.getGestureName()
                                + ": executing delayed transition to "
                                + GestureMatcher.getStateSymbolicName(this.mTargetState));
            }
            GestureMatcher.this.setState(
                    this.mTargetState, this.mEvent, this.mRawEvent, this.mPolicyFlags);
            recycleEvent();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface StateChangeListener {
        void onStateChanged(
                int i, int i2, int i3, MotionEvent motionEvent, MotionEvent motionEvent2);
    }

    public GestureMatcher(int i, Handler handler, StateChangeListener stateChangeListener) {
        this.mGestureId = i;
        this.mHandler = handler;
        this.mListener = stateChangeListener;
    }

    public static String getStateSymbolicName(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                i, "Unknown state: ")
                                        : "STATE_GESTURE_CANCELED"
                                : "STATE_GESTURE_COMPLETED"
                        : "STATE_GESTURE_STARTED"
                : "STATE_CLEAR";
    }

    public final void cancelAfterDoubleTapTimeout(
            MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        long doubleTapTimeout = ViewConfiguration.getDoubleTapTimeout();
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, doubleTapTimeout, motionEvent, motionEvent2, i);
    }

    public void clear() {
        this.mState = 0;
        this.mDelayedTransition.cancel();
    }

    public abstract String getGestureName();

    public void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {}

    public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        int i2 = this.mState;
        if (i2 == 3 || i2 == 2) {
            return;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            onDown(motionEvent, motionEvent2, i);
            return;
        }
        if (actionMasked == 1) {
            onUp(motionEvent, motionEvent2, i);
            return;
        }
        if (actionMasked == 2) {
            onMove(motionEvent, motionEvent2, i);
            return;
        }
        if (actionMasked == 5) {
            onPointerDown(motionEvent, motionEvent2, i);
        } else if (actionMasked != 6) {
            setState(3, motionEvent, motionEvent2, i);
        } else {
            onPointerUp(motionEvent, motionEvent2, i);
        }
    }

    public abstract void onMove(MotionEvent motionEvent, MotionEvent motionEvent2, int i);

    public abstract void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i);

    public void onPointerUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {}

    public abstract void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i);

    public final void setState(int i, MotionEvent motionEvent, MotionEvent motionEvent2, int i2) {
        this.mState = i;
        this.mDelayedTransition.cancel();
        StateChangeListener stateChangeListener = this.mListener;
        if (stateChangeListener != null) {
            stateChangeListener.onStateChanged(
                    this.mGestureId, this.mState, i2, motionEvent, motionEvent2);
        }
    }

    public String toString() {
        return getGestureName() + ":" + getStateSymbolicName(this.mState);
    }
}
