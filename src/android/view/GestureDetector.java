package android.view;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Message;
import android.os.ServiceManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.widget.OverScroller;
import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes4.dex */
public class GestureDetector {
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final String TAG = "GestureDetector";
    private static final int TAP = 3;
    private boolean mAlwaysInBiggerTapRegion;
    private boolean mAlwaysInTapRegion;
    private float mAmbiguousGestureMultiplier;
    private boolean mCheckLog;
    private OnContextClickListener mContextClickListener;
    private MotionEvent mCurrentDownEvent;
    private float mCurrentDownEventRawX;
    private float mCurrentDownEventRawY;
    private MotionEvent mCurrentMotionEvent;
    private float mCurrentMotionEventRawX;
    private float mCurrentMotionEventRawY;
    private boolean mDeferConfirmSingleTap;
    private OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private int mDoubleTapTouchSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final Handler mHandler;
    private boolean mHasRecordedClassification;
    private boolean mIgnoreNextUpEvent;
    private boolean mInContextClick;
    private boolean mInLongPress;
    private final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    private final OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private OverScroller mOverscroller;
    private MotionEvent mPreviousUpEvent;
    private boolean mStillDown;
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;
    private int mVelocityTrackerStrategy;
    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    private static final int DOUBLE_TAP_MIN_TIME = ViewConfiguration.getDoubleTapMinTime();
    private static ICustomFrequencyManager sCfmsService = null;

    public interface OnContextClickListener {
        boolean onContextClick(MotionEvent motionEvent);
    }

    public interface OnDoubleTapListener {
        boolean onDoubleTap(MotionEvent motionEvent);

        boolean onDoubleTapEvent(MotionEvent motionEvent);

        boolean onSingleTapConfirmed(MotionEvent motionEvent);
    }

    public interface OnGestureListener {
        boolean onDown(MotionEvent motionEvent);

        boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        void onLongPress(MotionEvent motionEvent);

        boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2);

        void onShowPress(MotionEvent motionEvent);

        boolean onSingleTapUp(MotionEvent motionEvent);
    }

    public static class SimpleOnGestureListener implements OnGestureListener, OnDoubleTapListener, OnContextClickListener {
        @Override // android.view.GestureDetector.OnContextClickListener
        public boolean onContextClick(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
    }

    private class GestureHandler extends Handler {
        GestureHandler() {
        }

        GestureHandler(Handler handler) {
            super(handler.getLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                GestureDetector.this.mListener.onShowPress(GestureDetector.this.mCurrentDownEvent);
                return;
            }
            if (i == 2) {
                Log.i(GestureDetector.TAG, "handleMessage LONG_PRESS");
                GestureDetector.this.recordGestureClassification(message.arg1);
                GestureDetector.this.dispatchLongPress();
            } else {
                if (i == 3) {
                    if (GestureDetector.this.mDoubleTapListener != null) {
                        if (!GestureDetector.this.mStillDown) {
                            Log.i(GestureDetector.TAG, "handleMessage TAP");
                            GestureDetector.this.recordGestureClassification(1);
                            GestureDetector.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetector.this.mCurrentDownEvent);
                            return;
                        }
                        GestureDetector.this.mDeferConfirmSingleTap = true;
                        return;
                    }
                    return;
                }
                throw new RuntimeException("Unknown message " + message);
            }
        }
    }

    @Deprecated
    public GestureDetector(OnGestureListener onGestureListener, Handler handler) {
        this(null, onGestureListener, handler);
    }

    @Deprecated
    public GestureDetector(OnGestureListener onGestureListener) {
        this(null, onGestureListener, null);
    }

    public GestureDetector(Context context, OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetector(Context context, OnGestureListener onGestureListener, Handler handler) {
        this(context, onGestureListener, handler, -1);
    }

    public GestureDetector(Context context, OnGestureListener onGestureListener, Handler handler, int i) {
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        if (handler != null) {
            this.mHandler = new GestureHandler(handler);
        } else {
            this.mHandler = new GestureHandler();
        }
        this.mListener = onGestureListener;
        if (onGestureListener instanceof OnDoubleTapListener) {
            setOnDoubleTapListener((OnDoubleTapListener) onGestureListener);
        }
        if (onGestureListener instanceof OnContextClickListener) {
            setContextClickListener((OnContextClickListener) onGestureListener);
        }
        this.mVelocityTrackerStrategy = i;
        init(context);
    }

    public GestureDetector(Context context, OnGestureListener onGestureListener, Handler handler, boolean z) {
        this(context, onGestureListener, handler);
    }

    private void init(Context context) {
        int scaledDoubleTapTouchSlop;
        int i;
        int i2;
        if (this.mListener == null) {
            throw new NullPointerException("OnGestureListener must not be null");
        }
        this.mIsLongpressEnabled = true;
        if (context == null) {
            i = ViewConfiguration.getTouchSlop();
            i2 = ViewConfiguration.getDoubleTapSlop();
            this.mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            this.mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            this.mAmbiguousGestureMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
            scaledDoubleTapTouchSlop = i;
        } else {
            StrictMode.assertConfigurationContext(context, "GestureDetector#init");
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
            scaledDoubleTapTouchSlop = viewConfiguration.getScaledDoubleTapTouchSlop();
            int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
            this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
            this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
            this.mAmbiguousGestureMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
            this.mOverscroller = new OverScroller(context, null, false);
            i = scaledTouchSlop;
            i2 = scaledDoubleTapSlop;
        }
        this.mTouchSlopSquare = i * i;
        this.mDoubleTapTouchSlopSquare = scaledDoubleTapTouchSlop * scaledDoubleTapTouchSlop;
        this.mDoubleTapSlopSquare = i2 * i2;
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener) {
        this.mDoubleTapListener = onDoubleTapListener;
    }

    public void setContextClickListener(OnContextClickListener onContextClickListener) {
        this.mContextClickListener = onContextClickListener;
    }

    public void setIsLongpressEnabled(boolean z) {
        this.mIsLongpressEnabled = z;
    }

    public boolean isLongpressEnabled() {
        return this.mIsLongpressEnabled;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0370  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r27) {
        /*
            Method dump skipped, instructions count: 940
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.GestureDetector.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void triggerGDBoost(int i, float f) {
        IBinder service;
        try {
            if (sCfmsService == null && (service = ServiceManager.getService(Context.CFMS_SERVICE)) != null) {
                sCfmsService = ICustomFrequencyManager.Stub.asInterface(service);
            }
            ICustomFrequencyManager iCustomFrequencyManager = sCfmsService;
            if (iCustomFrequencyManager != null) {
                iCustomFrequencyManager.sendCommandToSSRM("GESTURE_DETECTED", i + " " + f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onGenericMotionEvent(motionEvent, 0);
        }
        int actionButton = motionEvent.getActionButton();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 11) {
            OnContextClickListener onContextClickListener = this.mContextClickListener;
            if (onContextClickListener != null && !this.mInContextClick && !this.mInLongPress && ((actionButton == 32 || actionButton == 2) && onContextClickListener.onContextClick(motionEvent))) {
                this.mInContextClick = true;
                this.mHandler.removeMessages(2);
                this.mHandler.removeMessages(3);
                return true;
            }
        } else if (actionMasked == 12 && this.mInContextClick && (actionButton == 32 || actionButton == 2)) {
            this.mInContextClick = false;
            this.mIgnoreNextUpEvent = true;
        }
        return false;
    }

    private void cancel() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        this.mIsDoubleTapping = false;
        this.mStillDown = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = false;
        this.mInContextClick = false;
        this.mIgnoreNextUpEvent = false;
    }

    private void cancelTaps() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(3);
        this.mIsDoubleTapping = false;
        this.mAlwaysInTapRegion = false;
        this.mAlwaysInBiggerTapRegion = false;
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = false;
        this.mInContextClick = false;
        this.mIgnoreNextUpEvent = false;
    }

    private boolean isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
        if (!this.mAlwaysInBiggerTapRegion) {
            return false;
        }
        long eventTime = motionEvent3.getEventTime() - motionEvent2.getEventTime();
        if (eventTime <= DOUBLE_TAP_TIMEOUT && eventTime >= DOUBLE_TAP_MIN_TIME) {
            int x = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
            int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
            if ((x * x) + (y * y) < ((motionEvent.getFlags() & 8) != 0 ? 0 : this.mDoubleTapSlopSquare)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchLongPress() {
        this.mHandler.removeMessages(3);
        this.mDeferConfirmSingleTap = false;
        this.mInLongPress = true;
        this.mListener.onLongPress(this.mCurrentDownEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordGestureClassification(int i) {
        if (this.mHasRecordedClassification || i == 0) {
            return;
        }
        if (this.mCurrentDownEvent == null || this.mCurrentMotionEvent == null) {
            this.mHasRecordedClassification = true;
        } else {
            FrameworkStatsLog.write(177, getClass().getName(), i, (int) (SystemClock.uptimeMillis() - this.mCurrentMotionEvent.getDownTime()), (float) Math.hypot(this.mCurrentMotionEventRawX - this.mCurrentDownEventRawX, this.mCurrentMotionEventRawY - this.mCurrentDownEventRawY));
            this.mHasRecordedClassification = true;
        }
    }
}
