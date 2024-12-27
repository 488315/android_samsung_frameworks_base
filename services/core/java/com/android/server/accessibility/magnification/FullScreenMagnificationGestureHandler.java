package com.android.server.accessibility.magnification;

import android.R;
import android.accessibilityservice.MagnificationConfig;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.util.MathUtils;
import android.util.Slog;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.view.accessibility.MagnificationAnimationCallback;
import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.server.accessibility.AccessibilityTraceManager;
import com.android.server.accessibility.Flags;
import com.android.server.accessibility.gestures.GestureUtils;
import com.android.server.accessibility.magnification.FullScreenMagnificationController;
import com.android.server.accessibility.magnification.MagnificationGestureHandler;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FullScreenMagnificationGestureHandler extends MagnificationGestureHandler {
    public static final boolean DEBUG_DETECTING;
    public static final boolean DEBUG_PANNING_SCALING;
    public static final boolean DEBUG_STATE_TRANSITIONS;
    public static final float MAX_SCALE;
    static final int OVERSCROLL_LEFT_EDGE = 1;
    static final int OVERSCROLL_NONE = 0;
    static final int OVERSCROLL_RIGHT_EDGE = 2;
    static final int OVERSCROLL_VERTICAL_EDGE = 3;
    State mCurrentState;
    final DelegatingState mDelegatingState;
    final DetectingState mDetectingState;
    final FullScreenMagnificationController mFullScreenMagnificationController;
    public final FullScreenMagnificationVibrationHelper mFullScreenMagnificationVibrationHelper;
    public final boolean mIsWatch;
    public final AnonymousClass1 mMagnificationInfoChangedCallback;
    public final MagnificationLogger mMagnificationLogger;
    final OneFingerPanningSettingsProvider mOneFingerPanningSettingsProvider;
    public final float mOverscrollEdgeSlop;
    final OverscrollHandler mOverscrollHandler;
    final PanningScalingState mPanningScalingState;
    State mPreviousState;
    public final WindowMagnificationPromptController mPromptController;
    public final ScreenStateReceiver mScreenStateReceiver;
    final SinglePanningState mSinglePanningState;
    final ViewportDraggingState mViewportDraggingState;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$2, reason: invalid class name */
    public final class AnonymousClass2 implements MagnificationLogger {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DelegatingState implements State {
        public long mLastDelegatedDownEventTime;

        public DelegatingState() {
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = FullScreenMagnificationGestureHandler.this;
            if (actionMasked == 0) {
                fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mDelegatingState);
                this.mLastDelegatedDownEventTime = motionEvent.getDownTime();
            } else if (actionMasked == 1 || actionMasked == 3) {
                fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mDetectingState);
            }
            if (fullScreenMagnificationGestureHandler.mNext != null) {
                motionEvent.setDownTime(this.mLastDelegatedDownEventTime);
                fullScreenMagnificationGestureHandler.dispatchTransformedEvent(motionEvent, motionEvent2, i);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class DetectingState implements State, Handler.Callback {
        public final Context mContext;
        public MotionEventInfo mDelayedEventQueue;
        public long mLastDetectingDownEventTime;
        public MotionEvent mLastDown;
        public MotionEvent mLastUp;
        public final int mMultiTapMaxDelay;
        public final int mMultiTapMaxDistance;
        public MotionEvent mPreLastDown;
        public MotionEvent mPreLastUp;
        boolean mShortcutTriggered;
        public final int mSwipeMinDistance;
        public final /* synthetic */ FullScreenMagnificationGestureHandler this$0;
        public final PointF mFirstPointerDownLocation = new PointF(Float.NaN, Float.NaN);
        public final PointF mSecondPointerDownLocation = new PointF(Float.NaN, Float.NaN);
        Handler mHandler = new Handler(Looper.getMainLooper(), this);
        public final int mLongTapMinDelay = ViewConfiguration.getLongPressTimeout();

        public DetectingState(Context context, FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler) {
            this.this$0 = fullScreenMagnificationGestureHandler;
            this.mMultiTapMaxDelay = context.getResources().getInteger(R.integer.config_zen_repeat_callers_threshold) + ViewConfiguration.getDoubleTapTimeout();
            this.mSwipeMinDistance = ViewConfiguration.get(context).getScaledTouchSlop();
            this.mMultiTapMaxDistance = ViewConfiguration.get(context).getScaledDoubleTapSlop();
            this.mContext = context;
        }

        public void clear() {
            setShortcutTriggered(false);
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            while (true) {
                MotionEventInfo motionEventInfo = this.mDelayedEventQueue;
                if (motionEventInfo == null) {
                    this.mPreLastDown = null;
                    this.mPreLastUp = null;
                    this.mLastDown = null;
                    this.mLastUp = null;
                    this.mFirstPointerDownLocation.set(Float.NaN, Float.NaN);
                    this.mSecondPointerDownLocation.set(Float.NaN, Float.NaN);
                    return;
                }
                this.mDelayedEventQueue = motionEventInfo.mNext;
                motionEventInfo.recycle();
            }
        }

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                MotionEvent motionEvent = (MotionEvent) message.obj;
                transitionToViewportDraggingStateAndClear(motionEvent);
                motionEvent.recycle();
            } else if (i == 2) {
                transitionToDelegatingStateAndClear();
            } else {
                if (i != 3) {
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown message type: "));
                }
                FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
                fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mPanningScalingState);
                clear();
            }
            return true;
        }

        public final boolean isActivated() {
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
            return fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.isActivated(fullScreenMagnificationGestureHandler.mDisplayId);
        }

        public final boolean isMultiTapTriggered(int i) {
            boolean z = false;
            if (this.mShortcutTriggered) {
                return MotionEventInfo.countOf(this.mDelayedEventQueue) + 2 >= i;
            }
            if (this.this$0.mDetectSingleFingerTripleTap && MotionEventInfo.countOf(this.mDelayedEventQueue) >= i) {
                MotionEvent motionEvent = this.mPreLastDown;
                MotionEvent motionEvent2 = this.mLastDown;
                int i2 = this.mMultiTapMaxDelay;
                int i3 = this.mMultiTapMaxDistance;
                if (motionEvent != null && motionEvent2 != null && motionEvent2.getEventTime() - motionEvent.getEventTime() < i2 && GestureUtils.distance(motionEvent, motionEvent2) < i3) {
                    MotionEvent motionEvent3 = this.mPreLastUp;
                    MotionEvent motionEvent4 = this.mLastUp;
                    int i4 = this.mMultiTapMaxDelay;
                    int i5 = this.mMultiTapMaxDistance;
                    if (motionEvent3 != null && motionEvent4 != null && motionEvent4.getEventTime() - motionEvent3.getEventTime() < i4 && GestureUtils.distance(motionEvent3, motionEvent4) < i5) {
                        z = true;
                    }
                }
            }
            if (z && i > 2) {
                boolean z2 = !isActivated();
                ((AnonymousClass2) this.this$0.mMagnificationLogger).getClass();
                AccessibilityStatsLogUtils.logMagnificationTripleTap(z2);
            }
            return z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:179:0x0312, code lost:
        
            if ((r8.mLastDown.getEventTime() - r8.mPreLastDown.getEventTime()) >= r8.mMultiTapMaxDelay) goto L139;
         */
        /* JADX WARN: Removed duplicated region for block: B:157:0x0317  */
        /* JADX WARN: Removed duplicated region for block: B:159:0x031b  */
        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onMotionEvent(android.view.MotionEvent r9, android.view.MotionEvent r10, int r11) {
            /*
                Method dump skipped, instructions count: 824
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.DetectingState.onMotionEvent(android.view.MotionEvent, android.view.MotionEvent, int):void");
        }

        public final void setShortcutTriggered(boolean z) {
            if (this.mShortcutTriggered == z) {
                return;
            }
            if (FullScreenMagnificationGestureHandler.DEBUG_DETECTING) {
                Slog.i(this.this$0.mLogTag, "setShortcutTriggered(" + z + ")");
            }
            this.mShortcutTriggered = z;
            if (z) {
                AccessibilityUtils.updateProfile(this.mContext, "com.android.server.accessibility.MagnificationController");
            }
        }

        public final String toString() {
            return "DetectingState{tapCount()=" + MotionEventInfo.countOf(this.mDelayedEventQueue) + ", mShortcutTriggered=" + this.mShortcutTriggered + ", mDelayedEventQueue=" + MotionEventInfo.toString(this.mDelayedEventQueue) + '}';
        }

        public void transitionToDelegatingStateAndClear() {
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
            fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mDelegatingState);
            if (this.mDelayedEventQueue != null) {
                long min = Math.min(SystemClock.uptimeMillis() - this.mLastDetectingDownEventTime, this.mMultiTapMaxDelay);
                do {
                    MotionEventInfo motionEventInfo = this.mDelayedEventQueue;
                    this.mDelayedEventQueue = motionEventInfo.mNext;
                    MotionEvent motionEvent = motionEventInfo.event;
                    motionEvent.setDownTime(motionEvent.getDownTime() + min);
                    FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler2 = this.this$0;
                    fullScreenMagnificationGestureHandler2.handleEventWith(fullScreenMagnificationGestureHandler2.mDelegatingState, motionEventInfo.event, motionEventInfo.rawEvent, motionEventInfo.policyFlags);
                    motionEventInfo.recycle();
                } while (this.mDelayedEventQueue != null);
            }
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mSecondPointerDownLocation.set(Float.NaN, Float.NaN);
        }

        public void transitionToViewportDraggingStateAndClear(MotionEvent motionEvent) {
            boolean z;
            if (FullScreenMagnificationGestureHandler.DEBUG_DETECTING) {
                Slog.i(this.this$0.mLogTag, "onTripleTapAndHold()");
            }
            boolean z2 = this.mShortcutTriggered;
            boolean z3 = true;
            if (!z2) {
                boolean z4 = !isActivated();
                ((AnonymousClass2) this.this$0.mMagnificationLogger).getClass();
                AccessibilityStatsLogUtils.logMagnificationTripleTap(z4);
            }
            clear();
            ViewportDraggingState viewportDraggingState = this.this$0.mViewportDraggingState;
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = FullScreenMagnificationGestureHandler.this;
            if (!fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.isActivated(fullScreenMagnificationGestureHandler.mDisplayId)) {
                z3 = false;
            } else if (z2) {
                z3 = fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.mAlwaysOnMagnificationEnabled;
            }
            viewportDraggingState.mScaleToRecoverAfterDraggingEnd = z3 ? fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getScale(fullScreenMagnificationGestureHandler.mDisplayId) : Float.NaN;
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler2 = this.this$0;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            float scale = fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController.getScale(fullScreenMagnificationGestureHandler2.mDisplayId);
            float constrain = MathUtils.constrain(fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController.getPersistedScale(fullScreenMagnificationGestureHandler2.mDisplayId), 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE);
            boolean isActivated = fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController.isActivated(fullScreenMagnificationGestureHandler2.mDisplayId);
            FullScreenMagnificationController fullScreenMagnificationController = fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController;
            int i = fullScreenMagnificationGestureHandler2.mDisplayId;
            synchronized (fullScreenMagnificationController.mLock) {
                try {
                    FullScreenMagnificationController.DisplayMagnification displayMagnification = (FullScreenMagnificationController.DisplayMagnification) fullScreenMagnificationController.mDisplays.get(i);
                    z = (displayMagnification != null && displayMagnification.mMagnificationActivated) ? displayMagnification.mZoomedOutFromService : false;
                } finally {
                }
            }
            if (isActivated && !z2 && !z) {
                constrain = scale + 1.0f;
            }
            fullScreenMagnificationGestureHandler2.zoomToScale(constrain, x, y);
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler3 = this.this$0;
            fullScreenMagnificationGestureHandler3.transitionTo(fullScreenMagnificationGestureHandler3.mViewportDraggingState);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class GestureException extends Exception {
        public GestureException(String str) {
            super(str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface MagnificationLogger {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MotionEventInfo {
        public static final Object sLock = new Object();
        public static MotionEventInfo sPool;
        public static int sPoolSize;
        public MotionEvent event;
        public boolean mInPool;
        public MotionEventInfo mNext;
        public int policyFlags;
        public MotionEvent rawEvent;

        public static int countOf(MotionEventInfo motionEventInfo) {
            if (motionEventInfo == null) {
                return 0;
            }
            return countOf(motionEventInfo.mNext) + (motionEventInfo.event.getAction() == 1 ? 1 : 0);
        }

        public static String toString(MotionEventInfo motionEventInfo) {
            if (motionEventInfo == null) {
                return "";
            }
            return MotionEvent.actionToString(motionEventInfo.event.getAction()).replace("ACTION_", "") + " " + toString(motionEventInfo.mNext);
        }

        public final void recycle() {
            synchronized (sLock) {
                try {
                    if (this.mInPool) {
                        throw new IllegalStateException("Already recycled.");
                    }
                    MotionEvent motionEvent = this.event;
                    boolean z = FullScreenMagnificationGestureHandler.DEBUG_STATE_TRANSITIONS;
                    if (motionEvent != null) {
                        motionEvent.recycle();
                    }
                    this.event = null;
                    MotionEvent motionEvent2 = this.rawEvent;
                    if (motionEvent2 != null) {
                        motionEvent2.recycle();
                    }
                    this.rawEvent = null;
                    this.policyFlags = 0;
                    int i = sPoolSize;
                    if (i < 10) {
                        sPoolSize = i + 1;
                        this.mNext = sPool;
                        sPool = this;
                        this.mInPool = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OverscrollHandler {
        int mOverscrollState = 0;
        public final PointF mPivotEdge = new PointF(Float.NaN, Float.NaN);
        public final PointF mReachedEdgeCoord = new PointF(Float.NaN, Float.NaN);
        public boolean mEdgeCooldown = false;

        /* renamed from: -$$Nest$monScrollStateChanged, reason: not valid java name */
        public static void m137$$Nest$monScrollStateChanged(OverscrollHandler overscrollHandler, MotionEvent motionEvent, MotionEvent motionEvent2) {
            float f;
            float constrain;
            FullScreenMagnificationVibrationHelper fullScreenMagnificationVibrationHelper;
            Vibrator vibrator;
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = FullScreenMagnificationGestureHandler.this;
            FullScreenMagnificationController fullScreenMagnificationController = fullScreenMagnificationGestureHandler.mFullScreenMagnificationController;
            int i = fullScreenMagnificationGestureHandler.mDisplayId;
            if (fullScreenMagnificationController.isAtEdge(i)) {
                if (overscrollHandler.mOverscrollState == 0 && ((fullScreenMagnificationGestureHandler.isAtLeftEdge() || fullScreenMagnificationGestureHandler.isAtRightEdge()) && !overscrollHandler.mEdgeCooldown && (vibrator = (fullScreenMagnificationVibrationHelper = fullScreenMagnificationGestureHandler.mFullScreenMagnificationVibrationHelper).mVibrator) != null && vibrator.hasVibrator() && Settings.Secure.getIntForUser(fullScreenMagnificationVibrationHelper.mContentResolver, "accessibility_display_magnification_edge_haptic_enabled", 0, -2) == 1)) {
                    if (fullScreenMagnificationVibrationHelper.mIsVibrationEffectSupportedProvider.isVibrationEffectSupported()) {
                        fullScreenMagnificationVibrationHelper.mVibrator.vibrate(fullScreenMagnificationVibrationHelper.mVibrationEffect);
                    } else {
                        fullScreenMagnificationVibrationHelper.mVibrator.vibrate(VibrationEffect.createOneShot(10L, 127));
                    }
                }
                PointF pointF = overscrollHandler.mPivotEdge;
                if (Float.isNaN(pointF.x) && Float.isNaN(pointF.y)) {
                    fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getMagnificationBounds(i, new Rect());
                    int i2 = overscrollHandler.mOverscrollState;
                    if (i2 == 1) {
                        overscrollHandler.mPivotEdge.set(r2.left, fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getCenterY(i));
                    } else if (i2 == 2) {
                        overscrollHandler.mPivotEdge.set(r2.right, fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getCenterY(i));
                    }
                    overscrollHandler.mReachedEdgeCoord.set(motionEvent2.getX(), motionEvent2.getY());
                    overscrollHandler.mEdgeCooldown = true;
                }
            }
            int i3 = overscrollHandler.mOverscrollState;
            if (i3 == 0) {
                overscrollHandler.mOverscrollState = FullScreenMagnificationGestureHandler.m136$$Nest$moverscrollState(fullScreenMagnificationGestureHandler, motionEvent2, new PointF(motionEvent.getX(), motionEvent.getY()));
                return;
            }
            if (i3 != 1 && i3 != 2) {
                if (i3 != 3) {
                    Slog.d(fullScreenMagnificationGestureHandler.mLogTag, "Invalid overscroll state");
                    return;
                } else {
                    overscrollHandler.clearEdgeState();
                    fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mDelegatingState);
                    return;
                }
            }
            float x = motionEvent2.getX() - overscrollHandler.mReachedEdgeCoord.x;
            int i4 = overscrollHandler.mOverscrollState;
            if ((i4 != 1 || x >= FullScreenMagnificationGestureHandler.MAX_SCALE) && (i4 != 2 || x <= FullScreenMagnificationGestureHandler.MAX_SCALE)) {
                float hypot = (float) Math.hypot(Math.abs(x), Math.abs(motionEvent2.getY() - overscrollHandler.mReachedEdgeCoord.y));
                fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getMagnificationBounds(i, new Rect());
                float width = hypot / r5.width();
                float width2 = r5.width() * 0.05f;
                if (overscrollHandler.mEdgeCooldown && hypot > width2) {
                    overscrollHandler.mEdgeCooldown = false;
                }
                float f2 = 1.0f - width;
                float persistedScale = fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getPersistedScale(i);
                if (persistedScale >= 1.7f && persistedScale >= 2.0f) {
                    if (persistedScale < 2.2f) {
                        f = 0.95f;
                    } else if (persistedScale < 2.5f) {
                        f = 1.1f;
                    } else if (persistedScale < 2.7f) {
                        f = 1.3f;
                    }
                    constrain = MathUtils.constrain(persistedScale * f * f2, 1.0f, fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getPersistedScale(i));
                }
                f = 1.0f;
                constrain = MathUtils.constrain(persistedScale * f * f2, 1.0f, fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getPersistedScale(i));
            } else {
                overscrollHandler.clearEdgeState();
                constrain = -1.0f;
            }
            if (constrain < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                return;
            }
            FullScreenMagnificationController fullScreenMagnificationController2 = fullScreenMagnificationGestureHandler.mFullScreenMagnificationController;
            PointF pointF2 = overscrollHandler.mPivotEdge;
            fullScreenMagnificationController2.setScaleAndCenter(fullScreenMagnificationGestureHandler.mDisplayId, constrain, pointF2.x, pointF2.y, true, 0);
            if (constrain == 1.0f) {
                fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.reset(i, true);
                overscrollHandler.clearEdgeState();
                fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mDelegatingState);
            }
        }

        /* renamed from: -$$Nest$msetScaleAndCenterToEdgeIfNeeded, reason: not valid java name */
        public static void m138$$Nest$msetScaleAndCenterToEdgeIfNeeded(OverscrollHandler overscrollHandler) {
            int i = overscrollHandler.mOverscrollState;
            if (i == 1 || i == 2) {
                FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = FullScreenMagnificationGestureHandler.this;
                FullScreenMagnificationController fullScreenMagnificationController = fullScreenMagnificationGestureHandler.mFullScreenMagnificationController;
                int i2 = fullScreenMagnificationGestureHandler.mDisplayId;
                float persistedScale = fullScreenMagnificationController.getPersistedScale(i2);
                PointF pointF = overscrollHandler.mPivotEdge;
                fullScreenMagnificationController.setScaleAndCenter(i2, persistedScale, pointF.x, pointF.y, true, 0);
            }
        }

        public OverscrollHandler() {
        }

        public final void clearEdgeState() {
            this.mOverscrollState = 0;
            this.mPivotEdge.set(Float.NaN, Float.NaN);
            this.mReachedEdgeCoord.set(Float.NaN, Float.NaN);
            this.mEdgeCooldown = false;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("OverscrollHandler {mOverscrollState=");
            sb.append(this.mOverscrollState);
            sb.append("mPivotEdge.x=");
            sb.append(this.mPivotEdge.x);
            sb.append("mPivotEdge.y=");
            sb.append(this.mPivotEdge.y);
            sb.append("mReachedEdgeCoord.x=");
            sb.append(this.mReachedEdgeCoord.x);
            sb.append("mReachedEdgeCoord.y=");
            sb.append(this.mReachedEdgeCoord.y);
            sb.append("mEdgeCooldown=");
            return OptionalBool$$ExternalSyntheticOutline0.m("}", sb, this.mEdgeCooldown);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PanningScalingState extends GestureDetector.SimpleOnGestureListener implements ScaleGestureDetector.OnScaleGestureListener, State {
        static final float CHECK_DETECTING_PASS_PERSISTED_SCALE_THRESHOLD = 0.2f;
        static final float PASSING_PERSISTED_SCALE_THRESHOLD = 0.01f;
        public final Context mContext;
        boolean mDetectingPassPersistedScale;
        public float mInitialScaleFactor = -1.0f;
        public final ScaleGestureDetector mScaleGestureDetector;
        boolean mScaling;
        public final float mScalingThreshold;
        public final GestureDetector mScrollGestureDetector;
        public final /* synthetic */ FullScreenMagnificationGestureHandler this$0;

        public PanningScalingState(Context context, FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler) {
            this.this$0 = fullScreenMagnificationGestureHandler;
            TypedValue typedValue = new TypedValue();
            context.getResources().getValue(R.dimen.conversation_content_start, typedValue, false);
            this.mContext = context;
            this.mScalingThreshold = typedValue.getFloat();
            ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this, Handler.getMain());
            this.mScaleGestureDetector = scaleGestureDetector;
            scaleGestureDetector.setQuickScaleEnabled(false);
            this.mScrollGestureDetector = new GestureDetector(context, this, Handler.getMain());
        }

        public final void checkShouldDetectPassPersistedScale() {
            if (this.mDetectingPassPersistedScale) {
                return;
            }
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
            float scale = fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getScale(fullScreenMagnificationGestureHandler.mDisplayId);
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler2 = this.this$0;
            float persistedScale = fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController.getPersistedScale(fullScreenMagnificationGestureHandler2.mDisplayId);
            this.mDetectingPassPersistedScale = Math.abs(scale - persistedScale) / persistedScale >= CHECK_DETECTING_PASS_PERSISTED_SCALE_THRESHOLD;
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 6 && motionEvent.getPointerCount() == 2) {
                FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
                if (fullScreenMagnificationGestureHandler.mPreviousState == fullScreenMagnificationGestureHandler.mViewportDraggingState) {
                    OverscrollHandler overscrollHandler = fullScreenMagnificationGestureHandler.mOverscrollHandler;
                    if (overscrollHandler != null) {
                        OverscrollHandler.m138$$Nest$msetScaleAndCenterToEdgeIfNeeded(overscrollHandler);
                        this.this$0.mOverscrollHandler.clearEdgeState();
                    }
                    FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler2 = this.this$0;
                    ViewportDraggingState viewportDraggingState = fullScreenMagnificationGestureHandler2.mViewportDraggingState;
                    if (!fullScreenMagnificationGestureHandler2.mIsWatch) {
                        FullScreenMagnificationController fullScreenMagnificationController = fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController;
                        float scale = fullScreenMagnificationController.getScale(0);
                        if (scale >= 1.0f) {
                            fullScreenMagnificationController.mScaleProvider.putScale(scale, fullScreenMagnificationGestureHandler2.mDisplayId);
                        }
                    }
                    this.mInitialScaleFactor = -1.0f;
                    this.mScaling = false;
                    this.mDetectingPassPersistedScale = false;
                    this.this$0.transitionTo(viewportDraggingState);
                    return;
                }
            }
            if (actionMasked == 1 || actionMasked == 3) {
                this.this$0.getClass();
                Flags.fullscreenFlingGesture();
                OverscrollHandler overscrollHandler2 = this.this$0.mOverscrollHandler;
                if (overscrollHandler2 != null) {
                    OverscrollHandler.m138$$Nest$msetScaleAndCenterToEdgeIfNeeded(overscrollHandler2);
                    this.this$0.mOverscrollHandler.clearEdgeState();
                }
                FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler3 = this.this$0;
                DetectingState detectingState = fullScreenMagnificationGestureHandler3.mDetectingState;
                if (!fullScreenMagnificationGestureHandler3.mIsWatch) {
                    FullScreenMagnificationController fullScreenMagnificationController2 = fullScreenMagnificationGestureHandler3.mFullScreenMagnificationController;
                    float scale2 = fullScreenMagnificationController2.getScale(0);
                    if (scale2 >= 1.0f) {
                        fullScreenMagnificationController2.mScaleProvider.putScale(scale2, fullScreenMagnificationGestureHandler3.mDisplayId);
                    }
                }
                this.mInitialScaleFactor = -1.0f;
                this.mScaling = false;
                this.mDetectingPassPersistedScale = false;
                this.this$0.transitionTo(detectingState);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x004d, code lost:
        
            if (r2 < r0) goto L18;
         */
        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onScale(android.view.ScaleGestureDetector r6) {
            /*
                r5 = this;
                boolean r0 = r5.mScaling
                r1 = 1
                if (r0 != 0) goto L2a
                float r0 = r5.mInitialScaleFactor
                r2 = 0
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                r2 = 0
                if (r0 >= 0) goto L14
                float r6 = r6.getScaleFactor()
                r5.mInitialScaleFactor = r6
                return r2
            L14:
                float r6 = r6.getScaleFactor()
                float r0 = r5.mInitialScaleFactor
                float r6 = r6 - r0
                float r6 = java.lang.Math.abs(r6)
                float r0 = r5.mScalingThreshold
                int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                if (r6 <= 0) goto L26
                goto L27
            L26:
                r1 = r2
            L27:
                r5.mScaling = r1
                return r1
            L2a:
                com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler r0 = r5.this$0
                com.android.server.accessibility.magnification.FullScreenMagnificationController r2 = r0.mFullScreenMagnificationController
                int r0 = r0.mDisplayId
                float r0 = r2.getScale(r0)
                float r2 = r6.getScaleFactor()
                float r2 = r2 * r0
                float r3 = com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MAX_SCALE
                int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r4 <= 0) goto L45
                int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r4 <= 0) goto L45
            L43:
                r2 = r3
                goto L50
            L45:
                r3 = 1065353216(0x3f800000, float:1.0)
                int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r4 >= 0) goto L50
                int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r0 >= 0) goto L50
                goto L43
            L50:
                float r0 = r6.getFocusX()
                float r6 = r6.getFocusY()
                r5.setScaleAndClearIfNeeded(r2, r0, r6)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.PanningScalingState.onScale(android.view.ScaleGestureDetector):boolean");
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public final boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
            return fullScreenMagnificationGestureHandler.mCurrentState == fullScreenMagnificationGestureHandler.mPanningScalingState;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public final void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            this.mInitialScaleFactor = -1.0f;
            this.mScaling = false;
            this.mDetectingPassPersistedScale = false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
            if (fullScreenMagnificationGestureHandler.mCurrentState != fullScreenMagnificationGestureHandler.mPanningScalingState) {
                return true;
            }
            if (FullScreenMagnificationGestureHandler.DEBUG_PANNING_SCALING) {
                Slog.i(fullScreenMagnificationGestureHandler.mLogTag, "Panned content by scrollX: " + f + " scrollY: " + f2);
            }
            this.this$0.getClass();
            Flags.fullscreenFlingGesture();
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler2 = this.this$0;
            fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController.offsetMagnifiedRegion(fullScreenMagnificationGestureHandler2.mDisplayId, f, f2);
            OverscrollHandler overscrollHandler = this.this$0.mOverscrollHandler;
            if (overscrollHandler != null) {
                OverscrollHandler.m137$$Nest$monScrollStateChanged(overscrollHandler, motionEvent, motionEvent2);
            }
            return true;
        }

        public void setScaleAndClearIfNeeded(float f, float f2, float f3) {
            if (this.mDetectingPassPersistedScale) {
                FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
                float persistedScale = fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.getPersistedScale(fullScreenMagnificationGestureHandler.mDisplayId);
                if (Math.abs(f - persistedScale) / persistedScale < PASSING_PERSISTED_SCALE_THRESHOLD) {
                    Vibrator vibrator = (Vibrator) this.mContext.getSystemService(Vibrator.class);
                    if (vibrator != null) {
                        vibrator.vibrate(VibrationEffect.createPredefined(2));
                    }
                    this.mInitialScaleFactor = -1.0f;
                    this.mScaling = false;
                    this.mDetectingPassPersistedScale = false;
                    f = persistedScale;
                }
            }
            if (FullScreenMagnificationGestureHandler.DEBUG_PANNING_SCALING) {
                Slog.i(this.this$0.mLogTag, "Scaled content to: " + f + "x");
            }
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler2 = this.this$0;
            FullScreenMagnificationController fullScreenMagnificationController = fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController;
            int i = fullScreenMagnificationGestureHandler2.mDisplayId;
            synchronized (fullScreenMagnificationController.mLock) {
                try {
                    FullScreenMagnificationController.DisplayMagnification displayMagnification = (FullScreenMagnificationController.DisplayMagnification) fullScreenMagnificationController.mDisplays.get(i);
                    if (displayMagnification != null) {
                        displayMagnification.setScale(f, f2, f3);
                    }
                } finally {
                }
            }
            checkShouldDetectPassPersistedScale();
        }

        public final String toString() {
            return "PanningScalingState{mInitialScaleFactor=" + this.mInitialScaleFactor + ", mScaling=" + this.mScaling + '}';
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenStateReceiver extends BroadcastReceiver {
        public final Context mContext;
        public final FullScreenMagnificationGestureHandler mGestureHandler;

        public ScreenStateReceiver(Context context, FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler) {
            this.mContext = context;
            this.mGestureHandler = fullScreenMagnificationGestureHandler;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            this.mGestureHandler.mDetectingState.setShortcutTriggered(false);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SinglePanningState extends GestureDetector.SimpleOnGestureListener implements State {
        public final GestureDetector mScrollGestureDetector;
        public final /* synthetic */ FullScreenMagnificationGestureHandler this$0;

        public SinglePanningState(Context context, FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler) {
            this.this$0 = fullScreenMagnificationGestureHandler;
            this.mScrollGestureDetector = new GestureDetector(context, this, Handler.getMain());
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public final void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 1) {
                this.this$0.getClass();
                Flags.fullscreenFlingGesture();
            } else if (actionMasked != 3) {
                return;
            }
            OverscrollHandler overscrollHandler = this.this$0.mOverscrollHandler;
            if (overscrollHandler != null) {
                OverscrollHandler.m138$$Nest$msetScaleAndCenterToEdgeIfNeeded(overscrollHandler);
                this.this$0.mOverscrollHandler.clearEdgeState();
            }
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
            fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mDetectingState);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
            if (fullScreenMagnificationGestureHandler.mCurrentState != fullScreenMagnificationGestureHandler.mSinglePanningState) {
                return true;
            }
            Flags.fullscreenFlingGesture();
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler2 = this.this$0;
            fullScreenMagnificationGestureHandler2.mFullScreenMagnificationController.offsetMagnifiedRegion(fullScreenMagnificationGestureHandler2.mDisplayId, f, f2);
            if (FullScreenMagnificationGestureHandler.DEBUG_PANNING_SCALING) {
                String str = this.this$0.mLogTag;
                StringBuilder sb = new StringBuilder("SinglePanningState Panned content by scrollX: ");
                sb.append(f);
                sb.append(" scrollY: ");
                sb.append(f2);
                sb.append(" isAtEdge: ");
                FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler3 = this.this$0;
                sb.append(fullScreenMagnificationGestureHandler3.mFullScreenMagnificationController.isAtEdge(fullScreenMagnificationGestureHandler3.mDisplayId));
                Slog.i(str, sb.toString());
            }
            OverscrollHandler overscrollHandler = this.this$0.mOverscrollHandler;
            if (overscrollHandler != null) {
                OverscrollHandler.m137$$Nest$monScrollStateChanged(overscrollHandler, motionEvent, motionEvent2);
            }
            return true;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SinglePanningState{isEdgeOfView=");
            FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = this.this$0;
            sb.append(fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.isAtEdge(fullScreenMagnificationGestureHandler.mDisplayId));
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface State {
        void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ViewportDraggingState implements State {
        public boolean mLastMoveOutsideMagnifiedRegion;
        protected float mScaleToRecoverAfterDraggingEnd = Float.NaN;

        public ViewportDraggingState() {
        }

        @Override // com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.State
        public void onMotionEvent(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = FullScreenMagnificationGestureHandler.this;
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        if (motionEvent.getPointerCount() != 1) {
                            throw new GestureException("Should have one pointer down.");
                        }
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        if (!fullScreenMagnificationGestureHandler.mFullScreenMagnificationController.magnificationRegionContains(fullScreenMagnificationGestureHandler.mDisplayId, x, y)) {
                            this.mLastMoveOutsideMagnifiedRegion = true;
                            return;
                        }
                        FullScreenMagnificationController fullScreenMagnificationController = fullScreenMagnificationGestureHandler.mFullScreenMagnificationController;
                        int i2 = fullScreenMagnificationGestureHandler.mDisplayId;
                        boolean z = this.mLastMoveOutsideMagnifiedRegion;
                        synchronized (fullScreenMagnificationController.mLock) {
                            try {
                                FullScreenMagnificationController.DisplayMagnification displayMagnification = (FullScreenMagnificationController.DisplayMagnification) fullScreenMagnificationController.mDisplays.get(i2);
                                if (displayMagnification != null) {
                                    displayMagnification.setScaleAndCenter(Float.NaN, x, y, z ? MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK : null, 0);
                                }
                            } finally {
                            }
                        }
                        this.mLastMoveOutsideMagnifiedRegion = false;
                        return;
                    }
                    if (actionMasked != 3) {
                        if (actionMasked == 5) {
                            float f = this.mScaleToRecoverAfterDraggingEnd;
                            this.mLastMoveOutsideMagnifiedRegion = false;
                            this.mScaleToRecoverAfterDraggingEnd = f;
                            fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mPanningScalingState);
                            return;
                        }
                        if (actionMasked != 6) {
                            return;
                        }
                    }
                }
                float f2 = this.mScaleToRecoverAfterDraggingEnd;
                if (f2 >= 1.0f) {
                    fullScreenMagnificationGestureHandler.zoomToScale(f2, motionEvent.getX(), motionEvent.getY());
                } else {
                    fullScreenMagnificationGestureHandler.zoomOff();
                }
                this.mLastMoveOutsideMagnifiedRegion = false;
                this.mScaleToRecoverAfterDraggingEnd = Float.NaN;
                fullScreenMagnificationGestureHandler.transitionTo(fullScreenMagnificationGestureHandler.mDetectingState);
                return;
            }
            throw new GestureException("Unexpected event type: " + MotionEvent.actionToString(actionMasked));
        }

        public final String toString() {
            return "ViewportDraggingState{mScaleToRecoverAfterDraggingEnd=" + this.mScaleToRecoverAfterDraggingEnd + ", mLastMoveOutsideMagnifiedRegion=" + this.mLastMoveOutsideMagnifiedRegion + '}';
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a9, code lost:
    
        if (r7 < com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.MAX_SCALE) goto L53;
     */
    /* renamed from: -$$Nest$moverscrollState, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int m136$$Nest$moverscrollState(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler r6, android.view.MotionEvent r7, android.graphics.PointF r8) {
        /*
            r6.getClass()
            float r0 = r8.x
            boolean r0 = java.lang.Float.isNaN(r0)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L18
            float r0 = r8.y
            boolean r0 = java.lang.Float.isNaN(r0)
            if (r0 != 0) goto L16
            goto L18
        L16:
            r0 = r2
            goto L19
        L18:
            r0 = r1
        L19:
            if (r0 != 0) goto L1e
        L1b:
            r1 = r2
            goto Lac
        L1e:
            float r0 = r7.getX()
            float r3 = r8.x
            float r0 = r0 - r3
            float r7 = r7.getY()
            float r8 = r8.y
            float r7 = r7 - r8
            boolean r8 = r6.isAtLeftEdge()
            r3 = 0
            if (r8 == 0) goto L39
            int r8 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r8 <= 0) goto L39
            goto Lac
        L39:
            boolean r8 = r6.isAtRightEdge()
            if (r8 == 0) goto L46
            int r8 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r8 >= 0) goto L46
            r1 = 2
            goto Lac
        L46:
            com.android.server.accessibility.magnification.FullScreenMagnificationController r8 = r6.mFullScreenMagnificationController
            int r0 = r6.mDisplayId
            float r4 = r6.mOverscrollEdgeSlop
            java.lang.Object r5 = r8.mLock
            monitor-enter(r5)
            android.util.SparseArray r8 = r8.mDisplays     // Catch: java.lang.Throwable -> L5c
            java.lang.Object r8 = r8.get(r0)     // Catch: java.lang.Throwable -> L5c
            com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification r8 = (com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification) r8     // Catch: java.lang.Throwable -> L5c
            if (r8 != 0) goto L5e
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L5c
            r8 = r2
            goto L73
        L5c:
            r6 = move-exception
            goto Laf
        L5e:
            android.view.MagnificationSpec r0 = r8.mCurrentMagnificationSpec     // Catch: java.lang.Throwable -> L5c
            float r0 = r0.offsetY     // Catch: java.lang.Throwable -> L5c
            float r8 = r8.getMaxOffsetYLocked()     // Catch: java.lang.Throwable -> L5c
            float r0 = r0 - r8
            float r8 = android.util.MathUtils.abs(r0)     // Catch: java.lang.Throwable -> L5c
            int r8 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r8 > 0) goto L71
            r8 = r1
            goto L72
        L71:
            r8 = r2
        L72:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L5c
        L73:
            if (r8 == 0) goto L79
            int r8 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r8 > 0) goto Lab
        L79:
            com.android.server.accessibility.magnification.FullScreenMagnificationController r8 = r6.mFullScreenMagnificationController
            int r0 = r6.mDisplayId
            float r6 = r6.mOverscrollEdgeSlop
            java.lang.Object r4 = r8.mLock
            monitor-enter(r4)
            android.util.SparseArray r8 = r8.mDisplays     // Catch: java.lang.Throwable -> L8f
            java.lang.Object r8 = r8.get(r0)     // Catch: java.lang.Throwable -> L8f
            com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification r8 = (com.android.server.accessibility.magnification.FullScreenMagnificationController.DisplayMagnification) r8     // Catch: java.lang.Throwable -> L8f
            if (r8 != 0) goto L91
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L8f
            r1 = r2
            goto La5
        L8f:
            r6 = move-exception
            goto Lad
        L91:
            android.view.MagnificationSpec r0 = r8.mCurrentMagnificationSpec     // Catch: java.lang.Throwable -> L8f
            float r0 = r0.offsetY     // Catch: java.lang.Throwable -> L8f
            float r8 = r8.getMinOffsetYLocked()     // Catch: java.lang.Throwable -> L8f
            float r0 = r0 - r8
            float r8 = android.util.MathUtils.abs(r0)     // Catch: java.lang.Throwable -> L8f
            int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r6 > 0) goto La3
            goto La4
        La3:
            r1 = r2
        La4:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L8f
        La5:
            if (r1 == 0) goto L1b
            int r6 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r6 >= 0) goto L1b
        Lab:
            r1 = 3
        Lac:
            return r1
        Lad:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L8f
            throw r6
        Laf:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L5c
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.m136$$Nest$moverscrollState(com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler, android.view.MotionEvent, android.graphics.PointF):int");
    }

    static {
        boolean z = MagnificationGestureHandler.DEBUG_ALL;
        DEBUG_STATE_TRANSITIONS = z;
        DEBUG_DETECTING = z;
        DEBUG_PANNING_SCALING = z;
        MAX_SCALE = MagnificationScaleProvider.MAX_SCALE;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$1, java.lang.Object] */
    public FullScreenMagnificationGestureHandler(Context context, FullScreenMagnificationController fullScreenMagnificationController, AccessibilityTraceManager accessibilityTraceManager, MagnificationGestureHandler.Callback callback, boolean z, boolean z2, boolean z3, WindowMagnificationPromptController windowMagnificationPromptController, int i, FullScreenMagnificationVibrationHelper fullScreenMagnificationVibrationHelper, MagnificationLogger magnificationLogger, ViewConfiguration viewConfiguration, OneFingerPanningSettingsProvider oneFingerPanningSettingsProvider) {
        super(i, z, z3, accessibilityTraceManager, callback);
        if (MagnificationGestureHandler.DEBUG_ALL) {
            String str = this.mLogTag;
            StringBuilder m = FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("FullScreenMagnificationGestureHandler(detectSingleFingerTripleTap = ", z, ", detectTwoFingerTripleTap = ", z2, ", detectShortcutTrigger = ");
            m.append(z3);
            m.append(")");
            Log.i(str, m.toString());
        }
        Flags.fullscreenFlingGesture();
        this.mFullScreenMagnificationController = fullScreenMagnificationController;
        ?? r1 = new FullScreenMagnificationController.MagnificationInfoChangedCallback() { // from class: com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler.1
            @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
            public final void onFullScreenMagnificationActivationState(int i2, boolean z4) {
                FullScreenMagnificationGestureHandler fullScreenMagnificationGestureHandler = FullScreenMagnificationGestureHandler.this;
                if (i2 == fullScreenMagnificationGestureHandler.mDisplayId && !z4) {
                    fullScreenMagnificationGestureHandler.mDetectingState.setShortcutTriggered(false);
                }
            }

            @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
            public final void onFullScreenMagnificationChanged(int i2, Region region, MagnificationConfig magnificationConfig) {
            }

            @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
            public final void onImeWindowVisibilityChanged(int i2, boolean z4) {
            }

            @Override // com.android.server.accessibility.magnification.FullScreenMagnificationController.MagnificationInfoChangedCallback
            public final void onRequestMagnificationSpec(int i2) {
            }
        };
        this.mMagnificationInfoChangedCallback = r1;
        synchronized (fullScreenMagnificationController.mLock) {
            fullScreenMagnificationController.mMagnificationInfoChangedCallbacks.add(r1);
        }
        this.mPromptController = windowMagnificationPromptController;
        if (magnificationLogger != null) {
            this.mMagnificationLogger = magnificationLogger;
        } else {
            this.mMagnificationLogger = new AnonymousClass2();
        }
        this.mDelegatingState = new DelegatingState();
        Flags.enableMagnificationMultipleFingerMultipleTapGesture();
        DetectingState detectingState = new DetectingState(context, this);
        this.mDetectingState = detectingState;
        Flags.enableMagnificationMultipleFingerMultipleTapGesture();
        this.mViewportDraggingState = new ViewportDraggingState();
        this.mPanningScalingState = new PanningScalingState(context, this);
        this.mSinglePanningState = new SinglePanningState(context, this);
        this.mFullScreenMagnificationVibrationHelper = fullScreenMagnificationVibrationHelper;
        this.mOneFingerPanningSettingsProvider = oneFingerPanningSettingsProvider;
        this.mOverscrollHandler = context.getResources().getBoolean(R.bool.config_faceAuthDismissesKeyguard) ? new OverscrollHandler() : null;
        this.mOverscrollEdgeSlop = context.getResources().getDimensionPixelSize(R.dimen.accessibility_icon_foreground_padding_ratio);
        this.mIsWatch = context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        if (this.mDetectShortcutTrigger) {
            ScreenStateReceiver screenStateReceiver = new ScreenStateReceiver(context, this);
            this.mScreenStateReceiver = screenStateReceiver;
            screenStateReceiver.mContext.registerReceiver(screenStateReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
        } else {
            this.mScreenStateReceiver = null;
        }
        transitionTo(detectingState);
    }

    public final void clearAndTransitionToStateDetecting() {
        DetectingState detectingState = this.mDetectingState;
        this.mCurrentState = detectingState;
        detectingState.clear();
        ViewportDraggingState viewportDraggingState = this.mViewportDraggingState;
        viewportDraggingState.mLastMoveOutsideMagnifiedRegion = false;
        viewportDraggingState.mScaleToRecoverAfterDraggingEnd = Float.NaN;
        PanningScalingState panningScalingState = this.mPanningScalingState;
        panningScalingState.mInitialScaleFactor = -1.0f;
        panningScalingState.mScaling = false;
        panningScalingState.mDetectingPassPersistedScale = false;
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void clearEvents(int i) {
        if (i == 4098) {
            clearAndTransitionToStateDetecting();
        }
        super.clearEvents(i);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public final int getMode() {
        return 1;
    }

    public final void handleEventWith(State state, MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        this.mPanningScalingState.mScrollGestureDetector.onTouchEvent(motionEvent);
        this.mPanningScalingState.mScaleGestureDetector.onTouchEvent(motionEvent);
        this.mSinglePanningState.mScrollGestureDetector.onTouchEvent(motionEvent);
        try {
            state.onMotionEvent(motionEvent, motionEvent2, i);
        } catch (GestureException e) {
            Slog.e(this.mLogTag, "Error processing motion event", e);
            clearAndTransitionToStateDetecting();
        }
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public final void handleShortcutTriggered() {
        if (this.mFullScreenMagnificationController.isActivated(this.mDisplayId)) {
            zoomOff();
            clearAndTransitionToStateDetecting();
        } else {
            this.mDetectingState.setShortcutTriggered(!r0.mShortcutTriggered);
        }
        if (this.mDetectingState.mShortcutTriggered) {
            this.mPromptController.showNotificationIfNeeded();
            zoomToScale(1.0f, Float.NaN, Float.NaN);
        }
    }

    public final boolean isAtLeftEdge() {
        boolean z;
        FullScreenMagnificationController fullScreenMagnificationController = this.mFullScreenMagnificationController;
        int i = this.mDisplayId;
        float f = this.mOverscrollEdgeSlop;
        synchronized (fullScreenMagnificationController.mLock) {
            try {
                FullScreenMagnificationController.DisplayMagnification displayMagnification = (FullScreenMagnificationController.DisplayMagnification) fullScreenMagnificationController.mDisplays.get(i);
                if (displayMagnification != null) {
                    z = MathUtils.abs(displayMagnification.mCurrentMagnificationSpec.offsetX - displayMagnification.getMaxOffsetXLocked()) <= f;
                }
            } finally {
            }
        }
        return z;
    }

    public final boolean isAtRightEdge() {
        boolean z;
        FullScreenMagnificationController fullScreenMagnificationController = this.mFullScreenMagnificationController;
        int i = this.mDisplayId;
        float f = this.mOverscrollEdgeSlop;
        synchronized (fullScreenMagnificationController.mLock) {
            try {
                FullScreenMagnificationController.DisplayMagnification displayMagnification = (FullScreenMagnificationController.DisplayMagnification) fullScreenMagnificationController.mDisplays.get(i);
                if (displayMagnification != null) {
                    z = MathUtils.abs(displayMagnification.mCurrentMagnificationSpec.offsetX - displayMagnification.getMinOffsetXLocked()) <= f;
                }
            } finally {
            }
        }
        return z;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public final void magnificationDisactivate$1() {
        if (this.mFullScreenMagnificationController.resetIfNeeded(this.mDisplayId, true)) {
            clearAndTransitionToStateDetecting();
            return;
        }
        FullScreenMagnificationController fullScreenMagnificationController = this.mFullScreenMagnificationController;
        int i = this.mDisplayId;
        synchronized (fullScreenMagnificationController.mLock) {
            try {
                FullScreenMagnificationController.DisplayMagnification displayMagnification = (FullScreenMagnificationController.DisplayMagnification) fullScreenMagnificationController.mDisplays.get(i);
                if (displayMagnification != null) {
                    displayMagnification.setActivated(false);
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.accessibility.EventStreamTransformation
    public final void onDestroy() {
        if (DEBUG_STATE_TRANSITIONS) {
            Slog.i(this.mLogTag, "onDestroy(); delayed = " + MotionEventInfo.toString(this.mDetectingState.mDelayedEventQueue));
        }
        OneFingerPanningSettingsProvider oneFingerPanningSettingsProvider = this.mOneFingerPanningSettingsProvider;
        ContentResolver contentResolver = oneFingerPanningSettingsProvider.mContentResolver;
        if (contentResolver != null) {
            contentResolver.unregisterContentObserver(oneFingerPanningSettingsProvider.mObserver);
        }
        oneFingerPanningSettingsProvider.mContentResolver = null;
        try {
            ScreenStateReceiver screenStateReceiver = this.mScreenStateReceiver;
            if (screenStateReceiver != null) {
                screenStateReceiver.mContext.unregisterReceiver(screenStateReceiver);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        this.mPromptController.onDestroy();
        this.mFullScreenMagnificationController.resetIfNeeded(this.mDisplayId, 0);
        FullScreenMagnificationController fullScreenMagnificationController = this.mFullScreenMagnificationController;
        AnonymousClass1 anonymousClass1 = this.mMagnificationInfoChangedCallback;
        synchronized (fullScreenMagnificationController.mLock) {
            fullScreenMagnificationController.mMagnificationInfoChangedCallbacks.remove(anonymousClass1);
        }
        clearAndTransitionToStateDetecting();
    }

    @Override // com.android.server.accessibility.magnification.MagnificationGestureHandler
    public final void onMotionEventInternal(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (motionEvent.getActionMasked() == 0) {
            Flags.fullscreenFlingGesture();
        }
        int action = motionEvent.getAction();
        if (action != 7 && action != 10 && action != 9 && action != 8) {
            handleEventWith(this.mCurrentState, motionEvent, motionEvent2, i);
        } else if (this.mNext != null) {
            dispatchTransformedEvent(motionEvent, motionEvent2, i);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MagnificationGesture{mDetectingState=");
        sb.append(this.mDetectingState);
        sb.append(", mDelegatingState=");
        sb.append(this.mDelegatingState);
        sb.append(", mMagnifiedInteractionState=");
        sb.append(this.mPanningScalingState);
        sb.append(", mViewportDraggingState=");
        sb.append(this.mViewportDraggingState);
        sb.append(", mSinglePanningState=");
        sb.append(this.mSinglePanningState);
        sb.append(", mDetectSingleFingerTripleTap=");
        sb.append(this.mDetectSingleFingerTripleTap);
        sb.append(", mDetectShortcutTrigger=");
        sb.append(this.mDetectShortcutTrigger);
        sb.append(", mCurrentState=");
        State state = this.mCurrentState;
        sb.append(state != null ? state.getClass().getSimpleName() : "null");
        sb.append(", mPreviousState=");
        State state2 = this.mPreviousState;
        sb.append(state2 != null ? state2.getClass().getSimpleName() : "null");
        sb.append(", mMagnificationController=");
        sb.append(this.mFullScreenMagnificationController);
        sb.append(", mDisplayId=");
        sb.append(this.mDisplayId);
        sb.append(", mIsSinglePanningEnabled=");
        sb.append(this.mOneFingerPanningSettingsProvider.mCached.get());
        sb.append(", mOverscrollHandler=");
        sb.append(this.mOverscrollHandler);
        sb.append('}');
        return sb.toString();
    }

    public void transitionTo(State state) {
        if (DEBUG_STATE_TRANSITIONS) {
            StringBuilder sb = new StringBuilder();
            State state2 = this.mCurrentState;
            sb.append(state2 != null ? state2.getClass().getSimpleName() : "null");
            sb.append(" -> ");
            sb.append(state != null ? state.getClass().getSimpleName() : "null");
            sb.append(" at ");
            sb.append(Arrays.asList((StackTraceElement[]) Arrays.copyOfRange(new RuntimeException().getStackTrace(), 1, 5)));
            Slog.i(this.mLogTag, sb.toString().replace(FullScreenMagnificationGestureHandler.class.getName(), ""));
        }
        this.mPreviousState = this.mCurrentState;
        PanningScalingState panningScalingState = this.mPanningScalingState;
        if (state == panningScalingState) {
            panningScalingState.checkShouldDetectPassPersistedScale();
        }
        this.mCurrentState = state;
    }

    public final void zoomOff() {
        if (DEBUG_DETECTING) {
            Slog.i(this.mLogTag, "zoomOff()");
        }
        this.mFullScreenMagnificationController.reset(this.mDisplayId, true);
    }

    public final void zoomToScale(float f, float f2, float f3) {
        this.mFullScreenMagnificationController.setScaleAndCenter(this.mDisplayId, MathUtils.constrain(f, 1.0f, MAX_SCALE), f2, f3, true, 0);
    }
}
