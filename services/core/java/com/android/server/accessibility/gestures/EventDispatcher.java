package com.android.server.accessibility.gestures;

import android.content.Context;
import android.graphics.Point;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.accessibility.EventStreamTransformation;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EventDispatcher {
    public final AccessibilityManagerService mAms;
    public final Context mContext;
    public int mLongPressingPointerDeltaX;
    public int mLongPressingPointerDeltaY;
    public EventStreamTransformation mReceiver;
    public final TouchState mState;
    public int mLongPressingPointerId = -1;
    public final Point mTempPoint = new Point();

    public EventDispatcher(
            Context context,
            AccessibilityManagerService accessibilityManagerService,
            EventStreamTransformation eventStreamTransformation,
            TouchState touchState) {
        this.mContext = context;
        this.mAms = accessibilityManagerService;
        this.mReceiver = eventStreamTransformation;
        this.mState = touchState;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0046, code lost:

       if (r0 == r3.mA11yWindowManager.getActiveWindowId(r3.mCurrentUserId)) goto L15;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int computeClickLocation(android.graphics.Point r6) {
        /*
            r5 = this;
            com.android.server.accessibility.gestures.TouchState r0 = r5.mState
            android.view.MotionEvent r0 = r0.mLastInjectedHoverEventForClick
            r1 = 1
            r2 = 2
            if (r0 == 0) goto L55
            int r0 = r0.getActionIndex()
            com.android.server.accessibility.gestures.TouchState r3 = r5.mState
            android.view.MotionEvent r3 = r3.mLastInjectedHoverEventForClick
            float r3 = r3.getX(r0)
            int r3 = (int) r3
            r6.x = r3
            com.android.server.accessibility.gestures.TouchState r3 = r5.mState
            android.view.MotionEvent r3 = r3.mLastInjectedHoverEventForClick
            float r0 = r3.getY(r0)
            int r0 = (int) r0
            r6.y = r0
            com.android.server.accessibility.AccessibilityManagerService r0 = r5.mAms
            java.lang.Object r3 = r0.mLock
            monitor-enter(r3)
            com.android.server.accessibility.AccessibilityWindowManager r0 = r0.mA11yWindowManager     // Catch: java.lang.Throwable -> L52
            android.util.SparseArray r0 = r0.mDisplayWindowsObservers     // Catch: java.lang.Throwable -> L52
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L52
            if (r0 <= 0) goto L33
            r0 = r1
            goto L34
        L33:
            r0 = 0
        L34:
            r0 = r0 ^ r1
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L52
            if (r0 == 0) goto L48
            com.android.server.accessibility.gestures.TouchState r0 = r5.mState
            int r0 = r0.mLastTouchedWindowId
            com.android.server.accessibility.AccessibilityManagerService r3 = r5.mAms
            com.android.server.accessibility.AccessibilityWindowManager r4 = r3.mA11yWindowManager
            int r3 = r3.mCurrentUserId
            int r3 = r4.getActiveWindowId(r3)
            if (r0 != r3) goto L55
        L48:
            com.android.server.accessibility.AccessibilityManagerService r5 = r5.mAms
            boolean r5 = r5.getAccessibilityFocusClickPointInScreen(r6)
            if (r5 == 0) goto L51
            return r1
        L51:
            return r2
        L52:
            r5 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L52
            throw r5
        L55:
            com.android.server.accessibility.AccessibilityManagerService r5 = r5.mAms
            boolean r5 = r5.getAccessibilityFocusClickPointInScreen(r6)
            if (r5 == 0) goto L5e
            return r1
        L5e:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.gestures.EventDispatcher.computeClickLocation(android.graphics.Point):int");
    }

    public final int computeInjectionAction(int i, int i2) {
        TouchState touchState = this.mState;
        int i3 = 5;
        if (i != 0 && i != 5) {
            i3 = 6;
            if (i != 6) {
                return i;
            }
            if (Integer.bitCount(touchState.mInjectedPointersDown) == 1) {
                return 1;
            }
        } else if (Integer.bitCount(touchState.mInjectedPointersDown) == 0) {
            return 0;
        }
        return (i2 << 8) | i3;
    }

    public final void sendAccessibilityEvent(int i) {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
            AccessibilityManagerService accessibilityManagerService = this.mAms;
            obtain.setWindowId(
                    accessibilityManagerService.mA11yWindowManager.getActiveWindowId(
                            accessibilityManagerService.mCurrentUserId));
            accessibilityManager.sendAccessibilityEvent(obtain);
            if (TouchExplorer.DEBUG) {
                Slog.d(
                        "EventDispatcher",
                        "Sending accessibility event" + AccessibilityEvent.eventTypeToString(i));
            }
        }
        TouchState touchState = this.mState;
        if (i == 512) {
            touchState.setState(2);
            return;
        }
        if (i == 1024) {
            touchState.setState(1);
            return;
        }
        if (i == 262144) {
            touchState.setState(5);
            return;
        }
        if (i == 524288) {
            touchState.clear();
            return;
        }
        if (i == 1048576) {
            touchState.setState(1);
        } else if (i != 2097152) {
            touchState.getClass();
        } else {
            touchState.setState(0);
        }
    }

    public final void sendDownForAllNotInjectedPointers(int i, MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        int i2 = 0;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            int pointerId = motionEvent.getPointerId(i3);
            TouchState touchState = this.mState;
            if (!touchState.isInjectedPointerDown(pointerId)) {
                i2 |= 1 << pointerId;
                sendMotionEvent(
                        computeInjectionAction(0, i3),
                        i2,
                        i,
                        motionEvent,
                        touchState.mLastReceivedEvent);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendMotionEvent(
            int r28, int r29, int r30, android.view.MotionEvent r31, android.view.MotionEvent r32) {
        /*
            Method dump skipped, instructions count: 457
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.gestures.EventDispatcher.sendMotionEvent(int,"
                    + " int, int, android.view.MotionEvent, android.view.MotionEvent):void");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("=========================\nDown pointers #");
        TouchState touchState = this.mState;
        sb.append(Integer.bitCount(touchState.mInjectedPointersDown));
        sb.append(" [ ");
        for (int i = 0; i < 32; i++) {
            if (touchState.isInjectedPointerDown(i)) {
                sb.append(i);
                sb.append(" ");
            }
        }
        sb.append("]\n=========================");
        return sb.toString();
    }
}
