package com.android.server.accessibility.magnification;

import android.util.Log;
import android.util.Slog;
import android.view.MotionEvent;

import com.android.server.accessibility.gestures.GestureMatcher;

import java.util.LinkedList;
import java.util.List;

public final class MagnificationGesturesObserver implements GesturesObserver.Listener {
    public static final boolean DBG = Log.isLoggable("MagnificationGesturesObserver", 3);
    public final WindowMagnificationGestureHandler.DetectingState mCallback;
    public List mDelayedEventQueue;
    public final GesturesObserver mGesturesObserver;
    public long mLastDownEventTime = 0;
    public MotionEvent mLastEvent;

    public MagnificationGesturesObserver(
            WindowMagnificationGestureHandler.DetectingState detectingState,
            GestureMatcher... gestureMatcherArr) {
        this.mGesturesObserver = new GesturesObserver(this, gestureMatcherArr);
        this.mCallback = detectingState;
    }

    public final void clear() {
        if (DBG) {
            Slog.d("MagnificationGesturesObserver", "clear:" + this.mDelayedEventQueue);
        }
        MotionEvent motionEvent = this.mLastEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mLastEvent = null;
        }
        this.mLastDownEventTime = 0L;
        List<MotionEventInfo> list = this.mDelayedEventQueue;
        if (list != null) {
            for (MotionEventInfo motionEventInfo : list) {
                MotionEvent motionEvent2 = motionEventInfo.mEvent;
                if (motionEvent2 != null) {
                    motionEvent2.recycle();
                }
                motionEventInfo.mEvent = null;
                MotionEvent motionEvent3 = motionEventInfo.mRawEvent;
                if (motionEvent3 != null) {
                    motionEvent3.recycle();
                }
                motionEventInfo.mRawEvent = null;
            }
            ((LinkedList) this.mDelayedEventQueue).clear();
            this.mDelayedEventQueue = null;
        }
    }

    public final void notifyDetectionCancel() {
        List list = this.mDelayedEventQueue;
        this.mDelayedEventQueue = null;
        long j = this.mLastDownEventTime;
        MotionEvent motionEvent = this.mLastEvent;
        WindowMagnificationGestureHandler.DetectingState detectingState = this.mCallback;
        detectingState.getClass();
        boolean z = WindowMagnificationGestureHandler.DEBUG_DETECTING;
        WindowMagnificationGestureHandler windowMagnificationGestureHandler =
                WindowMagnificationGestureHandler.this;
        if (z) {
            Slog.d(
                    windowMagnificationGestureHandler.mLogTag,
                    "onGestureCancelled : delayedEventQueue = " + list);
        }
        windowMagnificationGestureHandler.mMotionEventDispatcherDelegate.sendDelayedMotionEvents(
                j, list);
        if (motionEvent == null
                || (motionEvent.getActionMasked() != 1 && motionEvent.getActionMasked() != 3)) {
            windowMagnificationGestureHandler.transitionTo(
                    windowMagnificationGestureHandler.mDelegatingState);
        }
        clear();
    }

    public final String toString() {
        return "MagnificationGesturesObserver{mDelayedEventQueue=" + this.mDelayedEventQueue + '}';
    }
}
