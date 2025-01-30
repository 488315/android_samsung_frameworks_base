package com.android.server.accessibility.magnification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.server.accessibility.AccessibilityTraceManager;
import com.android.server.accessibility.gestures.GestureMatcher;
import com.android.server.accessibility.gestures.MultiTap;
import com.android.server.accessibility.gestures.MultiTapAndHold;
import com.android.server.accessibility.magnification.MagnificationGestureHandler;
import com.android.server.accessibility.magnification.MagnificationGesturesObserver;
import com.android.server.accessibility.magnification.MotionEventDispatcherDelegate;
import com.android.server.accessibility.magnification.PanningScalingHandler;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class WindowMagnificationGestureHandler extends MagnificationGestureHandler {
    public static final boolean DEBUG_DETECTING;
    public static final boolean DEBUG_STATE_TRANSITIONS;
    public final Context mContext;
    State mCurrentState;
    final DelegatingState mDelegatingState;
    final DetectingState mDetectingState;
    public MotionEventDispatcherDelegate mMotionEventDispatcherDelegate;
    final PanningScalingGestureState mObservePanningScalingState;
    State mPreviousState;
    public final Point mTempPoint;
    public long mTripleTapAndHoldStartedTime;
    final ViewportDraggingState mViewportDraggingState;
    public final WindowMagnificationManager mWindowMagnificationMgr;

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public int getMode() {
        return 2;
    }

    static {
        boolean z = MagnificationGestureHandler.DEBUG_ALL;
        DEBUG_STATE_TRANSITIONS = z | false;
        DEBUG_DETECTING = z | false;
    }

    public WindowMagnificationGestureHandler(Context context, WindowMagnificationManager windowMagnificationManager, AccessibilityTraceManager accessibilityTraceManager, MagnificationGestureHandler.Callback callback, boolean z, boolean z2, int i) {
        super(i, z, z2, accessibilityTraceManager, callback);
        this.mTempPoint = new Point();
        this.mTripleTapAndHoldStartedTime = 0L;
        if (MagnificationGestureHandler.DEBUG_ALL) {
            Slog.i(this.mLogTag, "WindowMagnificationGestureHandler() , displayId = " + i + ")");
        }
        this.mContext = context;
        this.mWindowMagnificationMgr = windowMagnificationManager;
        MotionEventDispatcherDelegate motionEventDispatcherDelegate = new MotionEventDispatcherDelegate(context, new MotionEventDispatcherDelegate.EventDispatcher() { // from class: com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticLambda0
            @Override // com.android.server.accessibility.magnification.MotionEventDispatcherDelegate.EventDispatcher
            public final void dispatchMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i2) {
                WindowMagnificationGestureHandler.this.lambda$new$0(motionEvent, motionEvent2, i2);
            }
        });
        this.mMotionEventDispatcherDelegate = motionEventDispatcherDelegate;
        this.mDelegatingState = new DelegatingState(motionEventDispatcherDelegate);
        DetectingState detectingState = new DetectingState(context, this.mDetectTripleTap);
        this.mDetectingState = detectingState;
        this.mViewportDraggingState = new ViewportDraggingState();
        this.mObservePanningScalingState = new PanningScalingGestureState(new PanningScalingHandler(context, 8.0f, 1.0f, true, new PanningScalingHandler.MagnificationDelegate() { // from class: com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.1
            @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
            public boolean processScroll(int i2, float f, float f2) {
                return WindowMagnificationGestureHandler.this.mWindowMagnificationMgr.processScroll(i2, f, f2);
            }

            @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
            public void setScale(int i2, float f) {
                WindowMagnificationGestureHandler.this.mWindowMagnificationMgr.setScale(i2, f);
            }

            @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
            public float getScale(int i2) {
                return WindowMagnificationGestureHandler.this.mWindowMagnificationMgr.getScale(i2);
            }
        }));
        transitionTo(detectingState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        dispatchTransformedEvent(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public void onMotionEventInternal(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        this.mObservePanningScalingState.mPanningScalingHandler.onTouchEvent(motionEvent);
        this.mCurrentState.onMotionEvent(motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void clearEvents(int i) {
        if (i == 4098) {
            resetToDetectState();
        }
        super.clearEvents(i);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public void onDestroy() {
        if (MagnificationGestureHandler.DEBUG_ALL) {
            Slog.i(this.mLogTag, "onDestroy(); delayed = " + this.mDetectingState.toString());
        }
        this.mWindowMagnificationMgr.disableWindowMagnification(this.mDisplayId, true);
        resetToDetectState();
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public void handleShortcutTriggered() {
        getScreenSize(this.mTempPoint);
        toggleMagnification(r0.x / 2.0f, r0.y / 2.0f, 0);
    }

    public final void getScreenSize(Point point) {
        this.mContext.getDisplay().getRealSize(point);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public void magnificationDisactivate() {
        if (this.mWindowMagnificationMgr.isWindowMagnifierEnabled(this.mDisplayId)) {
            disableWindowMagnifier();
        }
    }

    public final void enableWindowMagnifier(float f, float f2, int i) {
        if (MagnificationGestureHandler.DEBUG_ALL) {
            Slog.i(this.mLogTag, "enableWindowMagnifier :" + f + ", " + f2 + ", " + i);
        }
        this.mWindowMagnificationMgr.enableWindowMagnification(this.mDisplayId, MathUtils.constrain(this.mWindowMagnificationMgr.getPersistedScale(this.mDisplayId), 1.0f, 8.0f), f, f2, i);
    }

    public final void disableWindowMagnifier() {
        if (MagnificationGestureHandler.DEBUG_ALL) {
            Slog.i(this.mLogTag, "disableWindowMagnifier()");
        }
        this.mWindowMagnificationMgr.disableWindowMagnification(this.mDisplayId, false);
    }

    public final void toggleMagnification(float f, float f2, int i) {
        if (this.mWindowMagnificationMgr.isWindowMagnifierEnabled(this.mDisplayId)) {
            disableWindowMagnifier();
            boolean z = Settings.System.getIntForUser(this.mContext.getContentResolver(), "accessibility_am_magnification_mode", 0, -2) == 1;
            Intent intent = new Intent();
            intent.setAction("com.samsung.accessibility.action.GET_MAGNFICATION_STATUS");
            intent.putExtra("status", false);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
            if (z) {
                enableWindowMagnifier(f, f2, i);
                return;
            }
            return;
        }
        enableWindowMagnifier(f, f2, i);
        Intent intent2 = new Intent();
        intent2.setAction("com.samsung.accessibility.action.GET_MAGNFICATION_STATUS");
        intent2.putExtra("status", true);
        this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT);
        AccessibilityUtils.updateProfile(this.mContext, "com.android.server.accessibility.MagnificationController");
    }

    public final void onTripleTap(MotionEvent motionEvent) {
        if (DEBUG_DETECTING) {
            Slog.i(this.mLogTag, "onTripleTap()");
        }
        toggleMagnification(motionEvent.getX(), motionEvent.getY(), 0);
        A11yLogger.insertLog(this.mContext, "A11Y9004", A11yLogger.createDimension("Magnification"));
    }

    public void onTripleTapAndHold(MotionEvent motionEvent) {
        if (DEBUG_DETECTING) {
            Slog.i(this.mLogTag, "onTripleTapAndHold()");
        }
        this.mViewportDraggingState.mEnabledBeforeDrag = this.mWindowMagnificationMgr.isWindowMagnifierEnabled(this.mDisplayId);
        enableWindowMagnifier(motionEvent.getX(), motionEvent.getY(), 1);
        this.mTripleTapAndHoldStartedTime = SystemClock.uptimeMillis();
        transitionTo(this.mViewportDraggingState);
    }

    public void releaseTripleTapAndHold() {
        if (!this.mViewportDraggingState.mEnabledBeforeDrag) {
            this.mWindowMagnificationMgr.disableWindowMagnification(this.mDisplayId, true);
        }
        transitionTo(this.mDetectingState);
        if (this.mTripleTapAndHoldStartedTime != 0) {
            logMagnificationTripleTapAndHoldSession(SystemClock.uptimeMillis() - this.mTripleTapAndHoldStartedTime);
            this.mTripleTapAndHoldStartedTime = 0L;
        }
    }

    public void logMagnificationTripleTapAndHoldSession(long j) {
        AccessibilityStatsLogUtils.logMagnificationTripleTapAndHoldSession(j);
    }

    public void resetToDetectState() {
        transitionTo(this.mDetectingState);
    }

    public interface State {
        default void onEnter() {
        }

        default void onExit() {
        }

        void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i);

        default String name() {
            return getClass().getSimpleName();
        }

        static String nameOf(State state) {
            return state != null ? state.name() : "null";
        }
    }

    public final void transitionTo(State state) {
        if (DEBUG_STATE_TRANSITIONS) {
            String str = this.mLogTag;
            StringBuilder sb = new StringBuilder();
            sb.append("state transition: ");
            sb.append((State.nameOf(this.mCurrentState) + " -> " + State.nameOf(state) + " at " + Arrays.asList((StackTraceElement[]) Arrays.copyOfRange(new RuntimeException().getStackTrace(), 1, 5))).replace(getClass().getName(), ""));
            Slog.i(str, sb.toString());
        }
        State state2 = this.mCurrentState;
        this.mPreviousState = state2;
        if (state2 != null) {
            state2.onExit();
        }
        this.mCurrentState = state;
        if (state != null) {
            state.onEnter();
        }
    }

    public final class PanningScalingGestureState implements State {
        public final PanningScalingHandler mPanningScalingHandler;

        public PanningScalingGestureState(PanningScalingHandler panningScalingHandler) {
            this.mPanningScalingHandler = panningScalingHandler;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onEnter() {
            this.mPanningScalingHandler.setEnabled(true);
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onExit() {
            this.mPanningScalingHandler.setEnabled(false);
            WindowMagnificationGestureHandler.this.mWindowMagnificationMgr.persistScale(WindowMagnificationGestureHandler.this.mDisplayId);
            clear();
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 1 || actionMasked == 3) {
                WindowMagnificationGestureHandler windowMagnificationGestureHandler = WindowMagnificationGestureHandler.this;
                windowMagnificationGestureHandler.transitionTo(windowMagnificationGestureHandler.mDetectingState);
            }
        }

        public void clear() {
            this.mPanningScalingHandler.clear();
        }

        public String toString() {
            return "PanningScalingState{mPanningScalingHandler=" + this.mPanningScalingHandler + '}';
        }
    }

    public final class DelegatingState implements State {
        public final MotionEventDispatcherDelegate mMotionEventDispatcherDelegate;

        public DelegatingState(MotionEventDispatcherDelegate motionEventDispatcherDelegate) {
            this.mMotionEventDispatcherDelegate = motionEventDispatcherDelegate;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            this.mMotionEventDispatcherDelegate.dispatchMotionEvent(motionEvent, motionEvent2, i);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 1 || actionMasked == 3) {
                WindowMagnificationGestureHandler windowMagnificationGestureHandler = WindowMagnificationGestureHandler.this;
                windowMagnificationGestureHandler.transitionTo(windowMagnificationGestureHandler.mDetectingState);
            }
        }
    }

    public final class ViewportDraggingState implements State {
        public boolean mEnabledBeforeDrag;
        public float mLastX = Float.NaN;
        public float mLastY = Float.NaN;

        public ViewportDraggingState() {
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (!Float.isNaN(this.mLastX) && !Float.isNaN(this.mLastY)) {
                        WindowMagnificationGestureHandler.this.mWindowMagnificationMgr.moveWindowMagnification(WindowMagnificationGestureHandler.this.mDisplayId, motionEvent.getX() - this.mLastX, motionEvent.getY() - this.mLastY);
                    }
                    this.mLastX = motionEvent.getX();
                    this.mLastY = motionEvent.getY();
                    return;
                }
                if (actionMasked != 3) {
                    return;
                }
            }
            WindowMagnificationGestureHandler.this.releaseTripleTapAndHold();
        }

        public void clear() {
            this.mLastX = Float.NaN;
            this.mLastY = Float.NaN;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onExit() {
            clear();
        }

        public String toString() {
            return "ViewportDraggingState{mLastX=" + this.mLastX + ",mLastY=" + this.mLastY + '}';
        }
    }

    public final class DetectingState implements State, MagnificationGesturesObserver.Callback {
        public final boolean mDetectTripleTap;
        public final MagnificationGesturesObserver mGesturesObserver;

        public DetectingState(Context context, boolean z) {
            this.mDetectTripleTap = z;
            MultiTap multiTap = new MultiTap(context, z ? 3 : 1, z ? 105 : 103, null);
            MultiTapAndHold multiTapAndHold = new MultiTapAndHold(context, z ? 3 : 1, z ? 106 : 104, null);
            MultiTap multiTap2 = new MultiTap(context, z ? 3 : 2, z ? 105 : 201, null);
            MultiTapAndHold multiTapAndHold2 = new MultiTapAndHold(context, z ? 3 : 2, z ? 106 : 202, null);
            boolean isScreenReaderEnabled = WindowMagnificationGestureHandler.this.isScreenReaderEnabled();
            GestureMatcher[] gestureMatcherArr = new GestureMatcher[4];
            gestureMatcherArr[0] = new SimpleSwipe(context);
            gestureMatcherArr[1] = isScreenReaderEnabled ? multiTap2 : multiTap;
            gestureMatcherArr[2] = isScreenReaderEnabled ? multiTapAndHold2 : multiTapAndHold;
            gestureMatcherArr[3] = new TwoFingersDownOrSwipe(context);
            this.mGesturesObserver = new MagnificationGesturesObserver(this, gestureMatcherArr);
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            this.mGesturesObserver.onMotionEvent(motionEvent, motionEvent2, i);
        }

        public String toString() {
            return "DetectingState{mGestureTimeoutObserver=" + this.mGesturesObserver + '}';
        }

        @Override // com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback
        public boolean shouldStopDetection(MotionEvent motionEvent) {
            return (WindowMagnificationGestureHandler.this.mWindowMagnificationMgr.isWindowMagnifierEnabled(WindowMagnificationGestureHandler.this.mDisplayId) || this.mDetectTripleTap) ? false : true;
        }

        @Override // com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback
        public void onGestureCompleted(int i, long j, List list, MotionEvent motionEvent) {
            if (WindowMagnificationGestureHandler.DEBUG_DETECTING) {
                Slog.d(WindowMagnificationGestureHandler.this.mLogTag, "onGestureDetected : gesture = " + MagnificationGestureMatcher.gestureIdToString(i));
                Slog.d(WindowMagnificationGestureHandler.this.mLogTag, "onGestureDetected : delayedEventQueue = " + list);
            }
            if (i == 101 && WindowMagnificationGestureHandler.this.mWindowMagnificationMgr.pointersInWindow(WindowMagnificationGestureHandler.this.mDisplayId, motionEvent) > 0 && WindowMagnificationGestureHandler.this.mWindowMagnificationMgr.isWindowMagnifierEnabled(WindowMagnificationGestureHandler.this.mDisplayId)) {
                WindowMagnificationGestureHandler windowMagnificationGestureHandler = WindowMagnificationGestureHandler.this;
                windowMagnificationGestureHandler.transitionTo(windowMagnificationGestureHandler.mObservePanningScalingState);
            } else if (i == 105) {
                WindowMagnificationGestureHandler.this.onTripleTap(motionEvent);
            } else if (i == 106) {
                WindowMagnificationGestureHandler.this.onTripleTapAndHold(motionEvent);
            } else {
                WindowMagnificationGestureHandler.this.mMotionEventDispatcherDelegate.sendDelayedMotionEvents(list, j);
                changeToDelegateStateIfNeed(motionEvent);
            }
        }

        @Override // com.android.server.accessibility.magnification.MagnificationGesturesObserver.Callback
        public void onGestureCancelled(long j, List list, MotionEvent motionEvent) {
            if (WindowMagnificationGestureHandler.DEBUG_DETECTING) {
                Slog.d(WindowMagnificationGestureHandler.this.mLogTag, "onGestureCancelled : delayedEventQueue = " + list);
            }
            WindowMagnificationGestureHandler.this.mMotionEventDispatcherDelegate.sendDelayedMotionEvents(list, j);
            changeToDelegateStateIfNeed(motionEvent);
        }

        public final void changeToDelegateStateIfNeed(MotionEvent motionEvent) {
            if (motionEvent == null || !(motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3)) {
                WindowMagnificationGestureHandler windowMagnificationGestureHandler = WindowMagnificationGestureHandler.this;
                windowMagnificationGestureHandler.transitionTo(windowMagnificationGestureHandler.mDelegatingState);
            }
        }
    }

    public String toString() {
        return "WindowMagnificationGestureHandler{mDetectingState=" + this.mDetectingState + ", mDelegatingState=" + this.mDelegatingState + ", mViewportDraggingState=" + this.mViewportDraggingState + ", mMagnifiedInteractionState=" + this.mObservePanningScalingState + ", mCurrentState=" + State.nameOf(this.mCurrentState) + ", mPreviousState=" + State.nameOf(this.mPreviousState) + ", mWindowMagnificationMgr=" + this.mWindowMagnificationMgr + ", mDisplayId=" + this.mDisplayId + '}';
    }

    public final boolean isScreenReaderEnabled() {
        return ((AccessibilityManager) this.mContext.getSystemService("accessibility")).semIsScreenReaderEnabled();
    }
}
