package com.android.server.accessibility.gestures;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.util.Slog;
import android.view.MotionEvent;

import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.accessibility.AccessibilityServiceConnection;
import com.android.server.accessibility.AccessibilityServiceConnection$$ExternalSyntheticLambda4;
import com.android.server.accessibility.AccessibilityUserState;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TouchState {
    public final AccessibilityManagerService mAms;
    public final int mDisplayId;
    public int mInjectedPointersDown;
    public long mLastInjectedDownEventTime;
    public MotionEvent mLastInjectedHoverEvent;
    public MotionEvent mLastInjectedHoverEventForClick;
    public MotionEvent mLastReceivedEvent;
    public int mLastReceivedPolicyFlags;
    public MotionEvent mLastReceivedRawEvent;
    public int mLastTouchedWindowId;
    public int mState = 0;
    public boolean mServiceDetectsGestures = false;
    public boolean mServiceDetectsGesturesRequested = false;
    public final ReceivedPointerTracker mReceivedPointerTracker = new ReceivedPointerTracker(this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PointerDownInfo {
        public long mTime;
        public float mX;
        public float mY;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReceivedPointerTracker {
        public int mLastReceivedDownEdgeFlags;
        public final PointerDownInfo[] mReceivedPointers = new PointerDownInfo[32];
        public int mReceivedPointersDown = 0;
        public int mPrimaryPointerId = 0;

        public ReceivedPointerTracker(TouchState touchState) {
            for (int i = 0; i < 32; i++) {
                this.mReceivedPointers[i] = new PointerDownInfo();
            }
        }

        public final int getPrimaryPointerId() {
            int i = -1;
            if (this.mPrimaryPointerId == -1) {
                int i2 = this.mReceivedPointersDown;
                long j = Long.MAX_VALUE;
                while (i2 > 0) {
                    int numberOfTrailingZeros = Integer.numberOfTrailingZeros(i2);
                    i2 &= ~(1 << numberOfTrailingZeros);
                    long j2 = this.mReceivedPointers[numberOfTrailingZeros].mTime;
                    if (j2 < j) {
                        i = numberOfTrailingZeros;
                        j = j2;
                    }
                }
                this.mPrimaryPointerId = i;
            }
            return this.mPrimaryPointerId;
        }

        public final float getReceivedPointerDownX(int i) {
            return this.mReceivedPointers[i].mX;
        }

        public final float getReceivedPointerDownY(int i) {
            return this.mReceivedPointers[i].mY;
        }

        public final void handleReceivedPointerDown(int i, MotionEvent motionEvent) {
            int pointerId = motionEvent.getPointerId(i);
            this.mLastReceivedDownEdgeFlags = motionEvent.getEdgeFlags();
            this.mReceivedPointersDown = (1 << pointerId) | this.mReceivedPointersDown;
            PointerDownInfo pointerDownInfo = this.mReceivedPointers[pointerId];
            float x = motionEvent.getX(i);
            float y = motionEvent.getY(i);
            long eventTime = motionEvent.getEventTime();
            pointerDownInfo.mX = x;
            pointerDownInfo.mY = y;
            pointerDownInfo.mTime = eventTime;
            if (motionEvent.getActionMasked() == 0) {
                this.mPrimaryPointerId = pointerId;
            }
        }

        public final void handleReceivedPointerUp(int i, MotionEvent motionEvent) {
            int pointerId = motionEvent.getPointerId(i);
            this.mReceivedPointersDown = (~(1 << pointerId)) & this.mReceivedPointersDown;
            PointerDownInfo pointerDownInfo = this.mReceivedPointers[pointerId];
            pointerDownInfo.mX = FullScreenMagnificationGestureHandler.MAX_SCALE;
            pointerDownInfo.mY = FullScreenMagnificationGestureHandler.MAX_SCALE;
            pointerDownInfo.mTime = 0L;
            if (this.mPrimaryPointerId == pointerId) {
                this.mPrimaryPointerId = -1;
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("=========================\nDown pointers #");
            sb.append(Integer.bitCount(this.mReceivedPointersDown));
            sb.append(" [ ");
            for (int i = 0; i < 32; i++) {
                if (((1 << i) & this.mReceivedPointersDown) != 0) {
                    sb.append(i);
                    sb.append(" ");
                }
            }
            sb.append("]\nPrimary pointer id [ ");
            sb.append(getPrimaryPointerId());
            sb.append(" ]\n=========================");
            return sb.toString();
        }
    }

    public TouchState(int i, AccessibilityManagerService accessibilityManagerService) {
        this.mDisplayId = i;
        this.mAms = accessibilityManagerService;
    }

    public static String getStateSymbolicName(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 4
                                                ? i != 5
                                                        ? VibrationParam$1$$ExternalSyntheticOutline0
                                                                .m(i, "Unknown state: ")
                                                        : "STATE_GESTURE_DETECTING"
                                                : "STATE_DELEGATING"
                                        : "STATE_DRAGGING"
                                : "STATE_TOUCH_EXPLORING"
                        : "STATE_TOUCH_INTERACTING"
                : "STATE_CLEAR";
    }

    public final void clear() {
        setState(0);
        this.mServiceDetectsGestures = this.mServiceDetectsGesturesRequested;
        MotionEvent motionEvent = this.mLastReceivedEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mLastReceivedEvent = null;
        }
        ReceivedPointerTracker receivedPointerTracker = this.mReceivedPointerTracker;
        receivedPointerTracker.mReceivedPointersDown = 0;
        receivedPointerTracker.mPrimaryPointerId = 0;
        for (int i = 0; i < 32; i++) {
            receivedPointerTracker.mReceivedPointers[i] = new PointerDownInfo();
        }
        this.mInjectedPointersDown = 0;
    }

    public final boolean isInjectedPointerDown(int i) {
        return (this.mInjectedPointersDown & (1 << i)) != 0;
    }

    public final boolean isTouchExploring() {
        return this.mState == 2;
    }

    public final void setState(int i) {
        if (this.mState == i) {
            return;
        }
        if (TouchExplorer.DEBUG) {
            Slog.i(
                    "TouchState",
                    getStateSymbolicName(this.mState) + "->" + getStateSymbolicName(i));
        }
        this.mState = i;
        if (this.mServiceDetectsGestures) {
            AccessibilityManagerService accessibilityManagerService = this.mAms;
            int i2 = this.mDisplayId;
            synchronized (accessibilityManagerService.mLock) {
                try {
                    AccessibilityUserState userStateLocked =
                            accessibilityManagerService.getUserStateLocked(
                                    accessibilityManagerService.mCurrentUserId);
                    for (int size = userStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                        AccessibilityServiceConnection accessibilityServiceConnection =
                                (AccessibilityServiceConnection)
                                        userStateLocked.mBoundServices.get(size);
                        if (accessibilityServiceConnection.isServiceDetectsGesturesEnabled(i2)) {
                            accessibilityServiceConnection.mMainHandler.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new AccessibilityServiceConnection$$ExternalSyntheticLambda4(),
                                            accessibilityServiceConnection,
                                            Integer.valueOf(i2),
                                            Integer.valueOf(i)));
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final String toString() {
        return "TouchState { mState: " + getStateSymbolicName(this.mState) + " }";
    }
}
