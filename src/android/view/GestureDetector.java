package android.view;

import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Message;
import android.os.Process;
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

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkTime(long j, String str) {
        long jUptimeMillis = SystemClock.uptimeMillis() - j;
        if (jUptimeMillis > 50) {
            Log.w(TAG, "Slow operation: " + jUptimeMillis + "ms so far, now at " + str);
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
                long jUptimeMillis = SystemClock.uptimeMillis();
                Log.i(GestureDetector.TAG, "handleMessage LONG_PRESS");
                GestureDetector.this.recordGestureClassification(message.arg1);
                GestureDetector.this.dispatchLongPress();
                GestureDetector.checkTime(jUptimeMillis, "GestureHandler#LONG_PRESS, listener=" + GestureDetector.this.mListener);
                return;
            }
            if (i == 3) {
                if (GestureDetector.this.mDoubleTapListener != null) {
                    if (!GestureDetector.this.mStillDown) {
                        long jUptimeMillis2 = SystemClock.uptimeMillis();
                        Log.i(GestureDetector.TAG, "handleMessage TAP");
                        GestureDetector.this.recordGestureClassification(1);
                        GestureDetector.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetector.this.mCurrentDownEvent);
                        GestureDetector.checkTime(jUptimeMillis2, "GestureHandler#TAP, listener=" + GestureDetector.this.mDoubleTapListener);
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
        int touchSlop;
        int doubleTapSlop;
        if (this.mListener == null) {
            throw new NullPointerException("OnGestureListener must not be null");
        }
        this.mIsLongpressEnabled = true;
        if (context == null) {
            touchSlop = ViewConfiguration.getTouchSlop();
            doubleTapSlop = ViewConfiguration.getDoubleTapSlop();
            this.mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            this.mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            this.mAmbiguousGestureMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
            scaledDoubleTapTouchSlop = touchSlop;
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
            touchSlop = scaledTouchSlop;
            doubleTapSlop = scaledDoubleTapSlop;
        }
        this.mTouchSlopSquare = touchSlop * touchSlop;
        this.mDoubleTapTouchSlopSquare = scaledDoubleTapTouchSlop * scaledDoubleTapTouchSlop;
        this.mDoubleTapSlopSquare = doubleTapSlop * doubleTapSlop;
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

    /* JADX WARN: Removed duplicated region for block: B:127:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0370  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnDoubleTap;
        MotionEvent motionEvent2;
        boolean zOnDown;
        MotionEvent motionEvent3;
        InputEventConsistencyVerifier inputEventConsistencyVerifier;
        boolean zOnSingleTapUp;
        OnDoubleTapListener onDoubleTapListener;
        MotionEvent motionEvent4;
        VelocityTracker velocityTracker;
        boolean z;
        InputEventConsistencyVerifier inputEventConsistencyVerifier2 = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier2 != null) {
            inputEventConsistencyVerifier2.onTouchEvent(motionEvent, 0);
        }
        int action = motionEvent.getAction();
        MotionEvent motionEvent5 = this.mCurrentMotionEvent;
        if (motionEvent5 != null) {
            motionEvent5.recycle();
        }
        if (this.mCheckLog) {
            Log.i(TAG, "obtain mCurrentMotionEventRaw. action: " + action + " id: " + motionEvent.getId());
            this.mCheckLog = false;
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        this.mCurrentMotionEvent = motionEventObtain;
        this.mCurrentMotionEventRawX = motionEventObtain.getRawX();
        this.mCurrentMotionEventRawY = this.mCurrentMotionEvent.getRawY();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain(this.mVelocityTrackerStrategy);
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int i = action & 255;
        boolean z2 = i == 6;
        int actionIndex = z2 ? motionEvent.getActionIndex() : -1;
        boolean z3 = (motionEvent.getFlags() & 8) != 0;
        int pointerCount = motionEvent.getPointerCount();
        float x = 0.0f;
        float y = 0.0f;
        for (int i2 = 0; i2 < pointerCount; i2++) {
            if (actionIndex != i2) {
                x += motionEvent.getX(i2);
                y += motionEvent.getY(i2);
            }
        }
        float f = z2 ? pointerCount - 1 : pointerCount;
        float f2 = x / f;
        float f3 = y / f;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    if (this.mInLongPress || this.mInContextClick) {
                        break;
                    }
                    int classification = motionEvent.getClassification();
                    boolean zHasMessages = this.mHandler.hasMessages(2);
                    float f4 = this.mLastFocusX - f2;
                    float f5 = this.mLastFocusY - f3;
                    if (this.mIsDoubleTapping) {
                        recordGestureClassification(2);
                        zOnDown = this.mDoubleTapListener.onDoubleTapEvent(motionEvent);
                        z = zHasMessages;
                    } else if (this.mAlwaysInTapRegion) {
                        int i3 = (int) (f2 - this.mDownFocusX);
                        int i4 = (int) (f3 - this.mDownFocusY);
                        int i5 = (i3 * i3) + (i4 * i4);
                        int i6 = z3 ? 0 : this.mTouchSlopSquare;
                        boolean z4 = classification == 1;
                        if (zHasMessages && z4) {
                            if (i5 > i6) {
                                this.mHandler.removeMessages(2);
                                long longPressTimeout = ViewConfiguration.getLongPressTimeout();
                                Handler handler = this.mHandler;
                                z = zHasMessages;
                                handler.sendMessageAtTime(handler.obtainMessage(2, 3, 0), motionEvent.getDownTime() + ((long) (longPressTimeout * this.mAmbiguousGestureMultiplier)));
                                i6 = i6;
                            } else {
                                z = zHasMessages;
                            }
                            float f6 = this.mAmbiguousGestureMultiplier;
                            i6 = (int) (i6 * f6 * f6);
                        } else {
                            z = zHasMessages;
                        }
                        if (i5 > i6) {
                            recordGestureClassification(5);
                            boolean zOnScroll = this.mListener.onScroll(this.mCurrentDownEvent, motionEvent, f4, f5);
                            this.mLastFocusX = f2;
                            this.mLastFocusY = f3;
                            this.mAlwaysInTapRegion = false;
                            this.mHandler.removeMessages(3);
                            this.mHandler.removeMessages(1);
                            this.mHandler.removeMessages(2);
                            zOnDown = zOnScroll;
                        } else {
                            zOnDown = false;
                        }
                        if (i5 > (z3 ? 0 : this.mDoubleTapTouchSlopSquare)) {
                            this.mAlwaysInBiggerTapRegion = false;
                        }
                    } else {
                        z = zHasMessages;
                        if (Math.abs(f4) >= 1.0f || Math.abs(f5) >= 1.0f) {
                            recordGestureClassification(5);
                            zOnDown = this.mListener.onScroll(this.mCurrentDownEvent, motionEvent, f4, f5);
                            this.mLastFocusX = f2;
                            this.mLastFocusY = f3;
                        } else {
                            zOnDown = false;
                        }
                    }
                    if (classification == 2 && z) {
                        this.mHandler.removeMessages(2);
                        Handler handler2 = this.mHandler;
                        handler2.sendMessage(handler2.obtainMessage(2, 4, 0));
                    }
                } else if (i == 3) {
                    cancel();
                } else if (i == 5) {
                    this.mLastFocusX = f2;
                    this.mDownFocusX = f2;
                    this.mLastFocusY = f3;
                    this.mDownFocusY = f3;
                    cancelTaps();
                } else if (i == 6) {
                    this.mLastFocusX = f2;
                    this.mDownFocusX = f2;
                    this.mLastFocusY = f3;
                    this.mDownFocusY = f3;
                    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                    int actionIndex2 = motionEvent.getActionIndex();
                    int pointerId = motionEvent.getPointerId(actionIndex2);
                    float xVelocity = this.mVelocityTracker.getXVelocity(pointerId);
                    float yVelocity = this.mVelocityTracker.getYVelocity(pointerId);
                    int i7 = 0;
                    while (true) {
                        if (i7 >= pointerCount) {
                            break;
                        }
                        if (i7 != actionIndex2) {
                            int pointerId2 = motionEvent.getPointerId(i7);
                            if ((this.mVelocityTracker.getXVelocity(pointerId2) * xVelocity) + (this.mVelocityTracker.getYVelocity(pointerId2) * yVelocity) < 0.0f) {
                                this.mVelocityTracker.clear();
                                break;
                            }
                        }
                        i7++;
                    }
                }
                zOnDown = false;
            } else {
                this.mStillDown = false;
                MotionEvent motionEventObtain2 = MotionEvent.obtain(motionEvent);
                if (this.mIsDoubleTapping) {
                    recordGestureClassification(2);
                    zOnSingleTapUp = this.mDoubleTapListener.onDoubleTapEvent(motionEvent);
                    triggerGDBoost(3, 0.0f);
                } else {
                    if (this.mInLongPress) {
                        this.mHandler.removeMessages(3);
                        this.mInLongPress = false;
                    } else if (this.mAlwaysInTapRegion && !this.mIgnoreNextUpEvent) {
                        recordGestureClassification(1);
                        zOnSingleTapUp = this.mListener.onSingleTapUp(motionEvent);
                        if (this.mDeferConfirmSingleTap && (onDoubleTapListener = this.mDoubleTapListener) != null) {
                            onDoubleTapListener.onSingleTapConfirmed(motionEvent);
                        }
                    } else if (!this.mIgnoreNextUpEvent) {
                        VelocityTracker velocityTracker2 = this.mVelocityTracker;
                        int pointerId3 = motionEvent.getPointerId(0);
                        velocityTracker2.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                        float yVelocity2 = velocityTracker2.getYVelocity(pointerId3);
                        float xVelocity2 = velocityTracker2.getXVelocity(pointerId3);
                        if (Math.abs(yVelocity2) > this.mMinimumFlingVelocity || Math.abs(xVelocity2) > this.mMinimumFlingVelocity) {
                            zOnDown = this.mListener.onFling(this.mCurrentDownEvent, motionEvent, xVelocity2, yVelocity2);
                            if (this.mOverscroller != null && Process.myUid() == 1000) {
                                this.mOverscroller.fling(0, 0, (int) xVelocity2, (int) yVelocity2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                                triggerGDBoost(1, this.mOverscroller.getDuration());
                            }
                        }
                        motionEvent4 = this.mPreviousUpEvent;
                        if (motionEvent4 != null) {
                            motionEvent4.recycle();
                        }
                        this.mPreviousUpEvent = motionEventObtain2;
                        velocityTracker = this.mVelocityTracker;
                        if (velocityTracker != null) {
                            velocityTracker.recycle();
                            this.mVelocityTracker = null;
                        }
                        this.mIsDoubleTapping = false;
                        this.mDeferConfirmSingleTap = false;
                        this.mIgnoreNextUpEvent = false;
                        this.mHandler.removeMessages(1);
                        this.mHandler.removeMessages(2);
                    }
                    zOnDown = false;
                    motionEvent4 = this.mPreviousUpEvent;
                    if (motionEvent4 != null) {
                    }
                    this.mPreviousUpEvent = motionEventObtain2;
                    velocityTracker = this.mVelocityTracker;
                    if (velocityTracker != null) {
                    }
                    this.mIsDoubleTapping = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mIgnoreNextUpEvent = false;
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                }
                zOnDown = zOnSingleTapUp;
                motionEvent4 = this.mPreviousUpEvent;
                if (motionEvent4 != null) {
                }
                this.mPreviousUpEvent = motionEventObtain2;
                velocityTracker = this.mVelocityTracker;
                if (velocityTracker != null) {
                }
                this.mIsDoubleTapping = false;
                this.mDeferConfirmSingleTap = false;
                this.mIgnoreNextUpEvent = false;
                this.mHandler.removeMessages(1);
                this.mHandler.removeMessages(2);
            }
        } else if (this.mDoubleTapListener != null) {
            boolean zHasMessages2 = this.mHandler.hasMessages(3);
            if (zHasMessages2) {
                this.mHandler.removeMessages(3);
            }
            MotionEvent motionEvent6 = this.mCurrentDownEvent;
            if (motionEvent6 != null && (motionEvent3 = this.mPreviousUpEvent) != null && zHasMessages2 && isConsideredDoubleTap(motionEvent6, motionEvent3, motionEvent)) {
                this.mIsDoubleTapping = true;
                recordGestureClassification(2);
                zOnDoubleTap = this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(motionEvent);
                triggerGDBoost(2, 0.0f);
                this.mLastFocusX = f2;
                this.mDownFocusX = f2;
                this.mLastFocusY = f3;
                this.mDownFocusY = f3;
                motionEvent2 = this.mCurrentDownEvent;
                if (motionEvent2 != null) {
                }
                Log.i(TAG, "obtain mCurrentDownEvent. id: " + motionEvent.getId() + " caller: " + Debug.getCallers(3));
                MotionEvent motionEventObtain3 = MotionEvent.obtain(motionEvent);
                this.mCurrentDownEvent = motionEventObtain3;
                this.mCurrentDownEventRawX = motionEventObtain3.getRawX();
                this.mCurrentDownEventRawY = this.mCurrentDownEvent.getRawY();
                this.mCheckLog = true;
                this.mAlwaysInTapRegion = true;
                this.mAlwaysInBiggerTapRegion = true;
                this.mStillDown = true;
                this.mInLongPress = false;
                this.mDeferConfirmSingleTap = false;
                this.mHasRecordedClassification = false;
                if (this.mIsLongpressEnabled) {
                }
                this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
                zOnDown = zOnDoubleTap | this.mListener.onDown(motionEvent);
            } else {
                this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
                zOnDoubleTap = false;
                this.mLastFocusX = f2;
                this.mDownFocusX = f2;
                this.mLastFocusY = f3;
                this.mDownFocusY = f3;
                motionEvent2 = this.mCurrentDownEvent;
                if (motionEvent2 != null) {
                }
                Log.i(TAG, "obtain mCurrentDownEvent. id: " + motionEvent.getId() + " caller: " + Debug.getCallers(3));
                MotionEvent motionEventObtain32 = MotionEvent.obtain(motionEvent);
                this.mCurrentDownEvent = motionEventObtain32;
                this.mCurrentDownEventRawX = motionEventObtain32.getRawX();
                this.mCurrentDownEventRawY = this.mCurrentDownEvent.getRawY();
                this.mCheckLog = true;
                this.mAlwaysInTapRegion = true;
                this.mAlwaysInBiggerTapRegion = true;
                this.mStillDown = true;
                this.mInLongPress = false;
                this.mDeferConfirmSingleTap = false;
                this.mHasRecordedClassification = false;
                if (this.mIsLongpressEnabled) {
                }
                this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
                zOnDown = zOnDoubleTap | this.mListener.onDown(motionEvent);
            }
        } else {
            zOnDoubleTap = false;
            this.mLastFocusX = f2;
            this.mDownFocusX = f2;
            this.mLastFocusY = f3;
            this.mDownFocusY = f3;
            motionEvent2 = this.mCurrentDownEvent;
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            Log.i(TAG, "obtain mCurrentDownEvent. id: " + motionEvent.getId() + " caller: " + Debug.getCallers(3));
            MotionEvent motionEventObtain322 = MotionEvent.obtain(motionEvent);
            this.mCurrentDownEvent = motionEventObtain322;
            this.mCurrentDownEventRawX = motionEventObtain322.getRawX();
            this.mCurrentDownEventRawY = this.mCurrentDownEvent.getRawY();
            this.mCheckLog = true;
            this.mAlwaysInTapRegion = true;
            this.mAlwaysInBiggerTapRegion = true;
            this.mStillDown = true;
            this.mInLongPress = false;
            this.mDeferConfirmSingleTap = false;
            this.mHasRecordedClassification = false;
            if (this.mIsLongpressEnabled) {
                this.mHandler.removeMessages(2);
                Handler handler3 = this.mHandler;
                handler3.sendMessageAtTime(handler3.obtainMessage(2, 3, 0), this.mCurrentDownEvent.getDownTime() + ViewConfiguration.getLongPressTimeout());
            }
            this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
            zOnDown = zOnDoubleTap | this.mListener.onDown(motionEvent);
        }
        if (!zOnDown && (inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier) != null) {
            inputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 0);
        }
        return zOnDown;
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
