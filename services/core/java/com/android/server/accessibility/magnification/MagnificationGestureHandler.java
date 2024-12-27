package com.android.server.accessibility.magnification;

import android.util.Log;
import android.util.Slog;
import android.view.MotionEvent;

import com.android.server.accessibility.AccessibilityTraceManager;
import com.android.server.accessibility.BaseEventStreamTransformation;
import com.android.server.accessibility.Flags;

import java.util.ArrayDeque;
import java.util.Queue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class MagnificationGestureHandler extends BaseEventStreamTransformation {
    public static final boolean DEBUG_ALL;
    public static final boolean DEBUG_EVENT_STREAM;
    public final Callback mCallback;
    public final Queue mDebugInputEventHistory;
    public final Queue mDebugOutputEventHistory;
    public final boolean mDetectShortcutTrigger;
    public final boolean mDetectSingleFingerTripleTap;
    public final int mDisplayId;
    public final String mLogTag = getClass().getSimpleName();
    public final AccessibilityTraceManager mTrace;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback {}

    static {
        boolean isLoggable = Log.isLoggable("MagnificationGestureHandler", 3);
        DEBUG_ALL = isLoggable;
        DEBUG_EVENT_STREAM = isLoggable;
    }

    public MagnificationGestureHandler(
            int i,
            boolean z,
            boolean z2,
            AccessibilityTraceManager accessibilityTraceManager,
            Callback callback) {
        this.mDisplayId = i;
        this.mDetectSingleFingerTripleTap = z;
        Flags.enableMagnificationMultipleFingerMultipleTapGesture();
        this.mDetectShortcutTrigger = z2;
        this.mTrace = accessibilityTraceManager;
        this.mCallback = callback;
        boolean z3 = DEBUG_EVENT_STREAM;
        this.mDebugInputEventHistory = z3 ? new ArrayDeque() : null;
        this.mDebugOutputEventHistory = z3 ? new ArrayDeque() : null;
    }

    public static void storeEventInto(Queue queue, MotionEvent motionEvent) {
        queue.add(MotionEvent.obtain(motionEvent));
        while (!queue.isEmpty()
                && motionEvent.getEventTime() - ((MotionEvent) queue.peek()).getEventTime()
                        > 5000) {
            ((MotionEvent) queue.remove()).recycle();
        }
    }

    public final void dispatchTransformedEvent(
            MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (!DEBUG_EVENT_STREAM) {
            super.onMotionEvent(motionEvent, motionEvent2, i);
            return;
        }
        storeEventInto(this.mDebugOutputEventHistory, motionEvent);
        try {
            super.onMotionEvent(motionEvent, motionEvent2, i);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Exception downstream following input events: "
                            + this.mDebugInputEventHistory
                            + "\nTransformed into output events: "
                            + this.mDebugOutputEventHistory,
                    e);
        }
    }

    public abstract int getMode();

    public abstract void handleShortcutTriggered();

    public abstract void magnificationDisactivate$1();

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (DEBUG_ALL) {
            Slog.i(this.mLogTag, "onMotionEvent(" + motionEvent + ")");
        }
        AccessibilityTraceManager accessibilityTraceManager = this.mTrace;
        if (accessibilityTraceManager.isA11yTracingEnabledForTypes(12288L)) {
            accessibilityTraceManager.logTrace(
                    "MagnificationGestureHandler.onMotionEvent",
                    12288L,
                    "event=" + motionEvent + ";rawEvent=" + motionEvent2 + ";policyFlags=" + i);
        }
        if (DEBUG_EVENT_STREAM) {
            storeEventInto(this.mDebugInputEventHistory, motionEvent);
        }
        if ((!this.mDetectSingleFingerTripleTap && !this.mDetectShortcutTrigger)
                || !motionEvent.isFromSource(4098)) {
            dispatchTransformedEvent(motionEvent, motionEvent2, i);
            return;
        }
        onMotionEventInternal(motionEvent, motionEvent2, i);
        int action = motionEvent.getAction();
        int i2 = this.mDisplayId;
        Callback callback = this.mCallback;
        if (action == 0) {
            int mode = getMode();
            MagnificationController magnificationController = (MagnificationController) callback;
            if (magnificationController.mMagnificationCapabilities != 3) {
                return;
            }
            magnificationController.updateMagnificationUIControls(i2, mode);
            return;
        }
        if (action == 1 || action == 3) {
            int mode2 = getMode();
            MagnificationController magnificationController2 = (MagnificationController) callback;
            if (magnificationController2.mMagnificationCapabilities != 3) {
                return;
            }
            magnificationController2.updateMagnificationUIControls(i2, mode2);
        }
    }

    public abstract void onMotionEventInternal(
            MotionEvent motionEvent, MotionEvent motionEvent2, int i);
}
