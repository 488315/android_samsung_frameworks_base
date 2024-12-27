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
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.MagnificationAnimationCallback;

import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityTraceManager;
import com.android.server.accessibility.Flags;
import com.android.server.accessibility.gestures.GestureMatcher;
import com.android.server.accessibility.gestures.MultiTap;
import com.android.server.accessibility.gestures.MultiTapAndHold;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public final class WindowMagnificationGestureHandler extends MagnificationGestureHandler {
    public static final boolean DEBUG_DETECTING;
    public static final boolean DEBUG_STATE_TRANSITIONS;
    public static final float MAX_SCALE;
    public final Context mContext;
    State mCurrentState;
    final DelegatingState mDelegatingState;
    final DetectingState mDetectingState;
    public final MagnificationConnectionManager mMagnificationConnectionManager;
    public final MotionEventDispatcherDelegate mMotionEventDispatcherDelegate;
    final PanningScalingGestureState mObservePanningScalingState;
    State mPreviousState;
    public final Point mTempPoint;
    public long mTripleTapAndHoldStartedTime;
    final ViewportDraggingState mViewportDraggingState;

    public final class DelegatingState implements State {
        public final MotionEventDispatcherDelegate mMotionEventDispatcherDelegate;

        public DelegatingState(MotionEventDispatcherDelegate motionEventDispatcherDelegate) {
            this.mMotionEventDispatcherDelegate = motionEventDispatcherDelegate;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            this.mMotionEventDispatcherDelegate.dispatchMotionEvent(motionEvent, motionEvent2, i);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 1 || actionMasked == 3) {
                WindowMagnificationGestureHandler windowMagnificationGestureHandler =
                        WindowMagnificationGestureHandler.this;
                windowMagnificationGestureHandler.transitionTo(
                        windowMagnificationGestureHandler.mDetectingState);
            }
        }
    }

    public final class DetectingState implements State {
        public final MagnificationGesturesObserver mGesturesObserver;

        public DetectingState(Context context) {
            Flags.enableMagnificationMultipleFingerMultipleTapGesture();
            boolean z = WindowMagnificationGestureHandler.this.mDetectSingleFingerTripleTap;
            MultiTap multiTap = new MultiTap(context, z ? 3 : 1, z ? 105 : 103, null);
            boolean z2 = WindowMagnificationGestureHandler.this.mDetectSingleFingerTripleTap;
            MultiTapAndHold multiTapAndHold =
                    new MultiTapAndHold(context, z2 ? 3 : 1, z2 ? 106 : 104, null);
            MultiTap multiTap2 = new MultiTap(context, z2 ? 3 : 2, z2 ? 105 : 201, null);
            MultiTapAndHold multiTapAndHold2 =
                    new MultiTapAndHold(context, z2 ? 3 : 2, z2 ? 106 : 202, null);
            boolean semIsScreenReaderEnabled =
                    ((AccessibilityManager)
                                    WindowMagnificationGestureHandler.this.mContext
                                            .getSystemService("accessibility"))
                            .semIsScreenReaderEnabled();
            this.mGesturesObserver =
                    new MagnificationGesturesObserver(
                            this,
                            new SimpleSwipe(context),
                            semIsScreenReaderEnabled ? multiTap2 : multiTap,
                            semIsScreenReaderEnabled ? multiTapAndHold2 : multiTapAndHold,
                            new TwoFingersDownOrSwipe(context));
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            MagnificationGesturesObserver magnificationGesturesObserver = this.mGesturesObserver;
            magnificationGesturesObserver.getClass();
            boolean z = MagnificationGesturesObserver.DBG;
            if (z) {
                Slog.d("MagnificationGesturesObserver", "DetectGesture: event = " + motionEvent);
            }
            magnificationGesturesObserver.mLastEvent = MotionEvent.obtain(motionEvent);
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            MotionEvent obtain2 = MotionEvent.obtain(motionEvent2);
            MotionEventInfo motionEventInfo = new MotionEventInfo();
            motionEventInfo.mEvent = obtain;
            motionEventInfo.mRawEvent = obtain2;
            motionEventInfo.mPolicyFlags = i;
            if (magnificationGesturesObserver.mDelayedEventQueue == null) {
                magnificationGesturesObserver.mDelayedEventQueue = new LinkedList();
            }
            ((LinkedList) magnificationGesturesObserver.mDelayedEventQueue).add(motionEventInfo);
            WindowMagnificationGestureHandler windowMagnificationGestureHandler =
                    WindowMagnificationGestureHandler.this;
            if (!windowMagnificationGestureHandler.mMagnificationConnectionManager
                            .isWindowMagnifierEnabled(windowMagnificationGestureHandler.mDisplayId)
                    && !windowMagnificationGestureHandler.mDetectSingleFingerTripleTap) {
                magnificationGesturesObserver.notifyDetectionCancel();
                return;
            }
            if (motionEvent.getActionMasked() == 0) {
                magnificationGesturesObserver.mLastDownEventTime = motionEvent.getDownTime();
            }
            GesturesObserver gesturesObserver = magnificationGesturesObserver.mGesturesObserver;
            if (!gesturesObserver.mObserveStarted) {
                if (motionEvent.getActionMasked() != 0) {
                    MagnificationGesturesObserver magnificationGesturesObserver2 =
                            (MagnificationGesturesObserver) gesturesObserver.mListener;
                    if (z) {
                        magnificationGesturesObserver2.getClass();
                        Slog.d(
                                "MagnificationGesturesObserver",
                                "onGestureCancelled:  event = " + motionEvent);
                    }
                    magnificationGesturesObserver2.notifyDetectionCancel();
                    gesturesObserver.clear$1();
                    return;
                }
                gesturesObserver.mObserveStarted = true;
            }
            gesturesObserver.mProcessMotionEvent = true;
            for (int i2 = 0; i2 < ((ArrayList) gesturesObserver.mGestureMatchers).size(); i2++) {
                GestureMatcher gestureMatcher =
                        (GestureMatcher) ((ArrayList) gesturesObserver.mGestureMatchers).get(i2);
                gestureMatcher.onMotionEvent(motionEvent, motionEvent2, i);
                if (gestureMatcher.mState == 2) {
                    gesturesObserver.clear$1();
                    gesturesObserver.mProcessMotionEvent = false;
                    return;
                }
            }
            gesturesObserver.mProcessMotionEvent = false;
        }

        public final String toString() {
            return "DetectingState{mGestureTimeoutObserver=" + this.mGesturesObserver + '}';
        }
    }

    public final class PanningScalingGestureState implements State {
        public final PanningScalingHandler mPanningScalingHandler;

        public PanningScalingGestureState(PanningScalingHandler panningScalingHandler) {
            this.mPanningScalingHandler = panningScalingHandler;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public final void onEnter() {
            PanningScalingHandler panningScalingHandler = this.mPanningScalingHandler;
            panningScalingHandler.mInitialScaleFactor = -1.0f;
            panningScalingHandler.mScaling = false;
            panningScalingHandler.mEnable = true;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public final void onExit() {
            PanningScalingHandler panningScalingHandler = this.mPanningScalingHandler;
            panningScalingHandler.mInitialScaleFactor = -1.0f;
            panningScalingHandler.mScaling = false;
            panningScalingHandler.mEnable = false;
            WindowMagnificationGestureHandler windowMagnificationGestureHandler =
                    WindowMagnificationGestureHandler.this;
            MagnificationConnectionManager magnificationConnectionManager =
                    windowMagnificationGestureHandler.mMagnificationConnectionManager;
            int i = windowMagnificationGestureHandler.mDisplayId;
            float scale = magnificationConnectionManager.getScale(i);
            if (scale >= 1.0f) {
                magnificationConnectionManager.mScaleProvider.putScale(scale, i);
            }
            panningScalingHandler.mInitialScaleFactor = -1.0f;
            panningScalingHandler.mScaling = false;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 1 || actionMasked == 3) {
                WindowMagnificationGestureHandler windowMagnificationGestureHandler =
                        WindowMagnificationGestureHandler.this;
                windowMagnificationGestureHandler.transitionTo(
                        windowMagnificationGestureHandler.mDetectingState);
            }
        }

        public final String toString() {
            return "PanningScalingState{mPanningScalingHandler="
                    + this.mPanningScalingHandler
                    + '}';
        }
    }

    public interface State {
        default void onEnter() {}

        default void onExit() {}

        void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i);
    }

    public final class ViewportDraggingState implements State {
        public boolean mEnabledBeforeDrag;
        public float mLastX = Float.NaN;
        public float mLastY = Float.NaN;

        public ViewportDraggingState() {}

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public final void onExit() {
            this.mLastX = Float.NaN;
            this.mLastY = Float.NaN;
        }

        @Override // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.State
        public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            WindowMagnificationGestureHandler windowMagnificationGestureHandler =
                    WindowMagnificationGestureHandler.this;
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (!Float.isNaN(this.mLastX) && !Float.isNaN(this.mLastY)) {
                        windowMagnificationGestureHandler.mMagnificationConnectionManager
                                .moveWindowMagnification(
                                        windowMagnificationGestureHandler.mDisplayId,
                                        motionEvent.getX() - this.mLastX,
                                        motionEvent.getY() - this.mLastY);
                    }
                    this.mLastX = motionEvent.getX();
                    this.mLastY = motionEvent.getY();
                    return;
                }
                if (actionMasked != 3) {
                    return;
                }
            }
            windowMagnificationGestureHandler.releaseTripleTapAndHold();
        }

        public final String toString() {
            return "ViewportDraggingState{mLastX=" + this.mLastX + ",mLastY=" + this.mLastY + '}';
        }
    }

    static {
        boolean z = MagnificationGestureHandler.DEBUG_ALL;
        DEBUG_STATE_TRANSITIONS = z;
        DEBUG_DETECTING = z;
        MAX_SCALE = MagnificationScaleProvider.MAX_SCALE;
    }

    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$1] */
    public WindowMagnificationGestureHandler(
            Context context,
            MagnificationConnectionManager magnificationConnectionManager,
            AccessibilityTraceManager accessibilityTraceManager,
            MagnificationGestureHandler.Callback callback,
            boolean z,
            boolean z2,
            boolean z3,
            int i) {
        super(i, z, z3, accessibilityTraceManager, callback);
        this.mTempPoint = new Point();
        this.mTripleTapAndHoldStartedTime = 0L;
        if (MagnificationGestureHandler.DEBUG_ALL) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    i, "WindowMagnificationGestureHandler() , displayId = ", ")", this.mLogTag);
        }
        this.mContext = context;
        this.mMagnificationConnectionManager = magnificationConnectionManager;
        MotionEventDispatcherDelegate motionEventDispatcherDelegate =
                new MotionEventDispatcherDelegate(
                        context,
                        new WindowMagnificationGestureHandler$$ExternalSyntheticLambda1(this));
        this.mMotionEventDispatcherDelegate = motionEventDispatcherDelegate;
        this.mDelegatingState = new DelegatingState(motionEventDispatcherDelegate);
        DetectingState detectingState = new DetectingState(context);
        this.mDetectingState = detectingState;
        this.mViewportDraggingState = new ViewportDraggingState();
        this.mObservePanningScalingState =
                new PanningScalingGestureState(
                        new PanningScalingHandler(
                                context,
                                MAX_SCALE,
                                new PanningScalingHandler
                                        .MagnificationDelegate() { // from class:
                                                                   // com.android.server.accessibility.magnification.WindowMagnificationGestureHandler.1
                                    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
                                    public final float getScale(int i2) {
                                        return WindowMagnificationGestureHandler.this
                                                .mMagnificationConnectionManager.getScale(i2);
                                    }

                                    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
                                    public final void processScroll(int i2, float f, float f2) {
                                        WindowMagnificationGestureHandler.this
                                                .mMagnificationConnectionManager.processScroll(
                                                i2, f, f2);
                                    }

                                    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
                                    public final void setScale(float f, int i2) {
                                        WindowMagnificationGestureHandler.this
                                                .mMagnificationConnectionManager.setScale(f, i2);
                                    }
                                }));
        transitionTo(detectingState);
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
        if (i == 4098) {
            transitionTo(this.mDetectingState);
        }
        super.clearEvents(i);
    }

    public final void enableWindowMagnifier(int i, float f, float f2) {
        if (MagnificationGestureHandler.DEBUG_ALL) {
            Slog.i(this.mLogTag, "enableWindowMagnifier :" + f + ", " + f2 + ", " + i);
        }
        this.mMagnificationConnectionManager.enableWindowMagnification(
                this.mDisplayId,
                MathUtils.constrain(
                        MathUtils.constrain(
                                this.mMagnificationConnectionManager.mScaleProvider.getScale(
                                        this.mDisplayId),
                                1.0f,
                                MagnificationScaleProvider.MAX_SCALE),
                        1.0f,
                        MAX_SCALE),
                f,
                f2,
                MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK,
                i,
                0);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public final int getMode() {
        return 2;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public final void handleShortcutTriggered() {
        this.mContext.getDisplay().getRealSize(this.mTempPoint);
        toggleMagnification(r0.x / 2.0f, r0.y / 2.0f);
    }

    public void logMagnificationTripleTapAndHoldSession(long j) {
        AccessibilityStatsLogUtils.logMagnificationTripleTapAndHoldSession(j);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public final void magnificationDisactivate$1() {
        MagnificationConnectionManager magnificationConnectionManager =
                this.mMagnificationConnectionManager;
        int i = this.mDisplayId;
        if (magnificationConnectionManager.isWindowMagnifierEnabled(i)) {
            if (MagnificationGestureHandler.DEBUG_ALL) {
                Slog.i(this.mLogTag, "disableWindowMagnifier()");
            }
            magnificationConnectionManager.disableWindowMagnification(i, false);
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onDestroy() {
        if (MagnificationGestureHandler.DEBUG_ALL) {
            Slog.i(this.mLogTag, "onDestroy(); delayed = " + this.mDetectingState.toString());
        }
        this.mMagnificationConnectionManager.disableWindowMagnification(this.mDisplayId, true);
        transitionTo(this.mDetectingState);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public final void onMotionEventInternal(
            MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        PanningScalingHandler panningScalingHandler =
                this.mObservePanningScalingState.mPanningScalingHandler;
        panningScalingHandler.mScaleGestureDetector.onTouchEvent(motionEvent);
        panningScalingHandler.mScrollGestureDetector.onTouchEvent(motionEvent);
        this.mCurrentState.onMotionEvent(motionEvent, motionEvent2, i);
    }

    public void onTripleTapAndHold(MotionEvent motionEvent) {
        if (DEBUG_DETECTING) {
            Slog.i(this.mLogTag, "onTripleTapAndHold()");
        }
        this.mViewportDraggingState.mEnabledBeforeDrag =
                this.mMagnificationConnectionManager.isWindowMagnifierEnabled(this.mDisplayId);
        enableWindowMagnifier(1, motionEvent.getX(), motionEvent.getY());
        this.mTripleTapAndHoldStartedTime = SystemClock.uptimeMillis();
        transitionTo(this.mViewportDraggingState);
    }

    public void releaseTripleTapAndHold() {
        if (!this.mViewportDraggingState.mEnabledBeforeDrag) {
            this.mMagnificationConnectionManager.disableWindowMagnification(this.mDisplayId, true);
        }
        transitionTo(this.mDetectingState);
        if (this.mTripleTapAndHoldStartedTime != 0) {
            logMagnificationTripleTapAndHoldSession(
                    SystemClock.uptimeMillis() - this.mTripleTapAndHoldStartedTime);
            this.mTripleTapAndHoldStartedTime = 0L;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WindowMagnificationGestureHandler{mDetectingState=");
        sb.append(this.mDetectingState);
        sb.append(", mDelegatingState=");
        sb.append(this.mDelegatingState);
        sb.append(", mViewportDraggingState=");
        sb.append(this.mViewportDraggingState);
        sb.append(", mMagnifiedInteractionState=");
        sb.append(this.mObservePanningScalingState);
        sb.append(", mCurrentState=");
        State state = this.mCurrentState;
        sb.append(state != null ? state.getClass().getSimpleName() : "null");
        sb.append(", mPreviousState=");
        State state2 = this.mPreviousState;
        sb.append(state2 != null ? state2.getClass().getSimpleName() : "null");
        sb.append(", mMagnificationConnectionManager=");
        sb.append(this.mMagnificationConnectionManager);
        sb.append(", mDisplayId=");
        return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                sb, this.mDisplayId, '}');
    }

    public final void toggleMagnification(float f, float f2) {
        MagnificationConnectionManager magnificationConnectionManager =
                this.mMagnificationConnectionManager;
        int i = this.mDisplayId;
        if (!magnificationConnectionManager.isWindowMagnifierEnabled(i)) {
            enableWindowMagnifier(0, f, f2);
            Intent intent = new Intent();
            intent.setAction("com.samsung.accessibility.action.GET_MAGNFICATION_STATUS");
            intent.putExtra(Constants.JSON_CLIENT_DATA_STATUS, true);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
            AccessibilityUtils.updateProfile(
                    this.mContext, "com.android.server.accessibility.MagnificationController");
            return;
        }
        if (MagnificationGestureHandler.DEBUG_ALL) {
            Slog.i(this.mLogTag, "disableWindowMagnifier()");
        }
        magnificationConnectionManager.disableWindowMagnification(i, false);
        boolean z =
                Settings.System.getIntForUser(
                                this.mContext.getContentResolver(),
                                "accessibility_am_magnification_mode",
                                0,
                                -2)
                        == 1;
        Intent intent2 = new Intent();
        intent2.setAction("com.samsung.accessibility.action.GET_MAGNFICATION_STATUS");
        intent2.putExtra(Constants.JSON_CLIENT_DATA_STATUS, false);
        this.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT);
        if (z) {
            enableWindowMagnifier(0, f, f2);
        }
    }

    public final void transitionTo(State state) {
        if (DEBUG_STATE_TRANSITIONS) {
            StringBuilder sb = new StringBuilder("state transition: ");
            StringBuilder sb2 = new StringBuilder();
            State state2 = this.mCurrentState;
            sb2.append(state2 != null ? state2.getClass().getSimpleName() : "null");
            sb2.append(" -> ");
            sb2.append(state != null ? state.getClass().getSimpleName() : "null");
            sb2.append(" at ");
            sb2.append(
                    Arrays.asList(
                            (StackTraceElement[])
                                    Arrays.copyOfRange(
                                            new RuntimeException().getStackTrace(), 1, 5)));
            sb.append(
                    sb2.toString().replace(WindowMagnificationGestureHandler.class.getName(), ""));
            Slog.i(this.mLogTag, sb.toString());
        }
        State state3 = this.mCurrentState;
        this.mPreviousState = state3;
        if (state3 != null) {
            state3.onExit();
        }
        this.mCurrentState = state;
        if (state != null) {
            state.onEnter();
        }
    }
}
