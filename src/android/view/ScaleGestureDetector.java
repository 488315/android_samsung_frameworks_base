package android.view;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;

/* loaded from: classes4.dex */
public class ScaleGestureDetector {
    private static final int ANCHORED_SCALE_MODE_DOUBLE_TAP = 1;
    private static final int ANCHORED_SCALE_MODE_NONE = 0;
    private static final int ANCHORED_SCALE_MODE_STYLUS = 2;
    private static final int IGNORE_POINTER_COUNT = 4;
    private static final float SCALE_FACTOR = 0.5f;
    private static final String TAG = "ScaleGestureDetector";
    private int mAnchoredScaleMode;
    private float mAnchoredScaleStartX;
    private float mAnchoredScaleStartY;
    private boolean mAreaRateCalculating;
    private float mAreaRateThreshold;
    private float mAreaThreshold;
    private final Context mContext;
    private float mCurrLenBeforeSqrt;
    private float mCurrSpanX;
    private float mCurrSpanY;
    private long mCurrTime;
    private boolean mEventBeforeOrAboveStartingGestureEvent;
    private float mFocusX;
    private float mFocusY;
    private GestureDetector mGestureDetector;
    private final Handler mHandler;
    private boolean mInProgress;
    private final OnScaleGestureListener mListener;
    private int mMinSpan;
    private float mPrevLenBeforeSqrt;
    private float mPrevSpanX;
    private float mPrevSpanY;
    private long mPrevTime;
    private boolean mQuickScaleEnabled;
    private int mSpanSlop;
    private final SaveState mStateCurrent;
    private boolean mStylusScaleEnabled;
    private float mTempLenBeforeSqrt;
    private boolean mUpdatePrevious;
    private boolean mUseTwoFingerSweep;

    public interface OnScaleGestureListener {
        boolean onScale(ScaleGestureDetector scaleGestureDetector);

        boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector);

        void onScaleEnd(ScaleGestureDetector scaleGestureDetector);
    }

    public static class SimpleOnScaleGestureListener implements OnScaleGestureListener {
        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }
    }

    static class SaveState {
        float mLenBeforeSqrt;
        float mSpanX;
        float mSpanY;
        float maxX;
        float maxY;
        float minX;
        float minY;

        public SaveState() {
            reset();
        }

        void reset() {
            this.maxY = 0.0f;
            this.maxX = 0.0f;
            this.minY = 0.0f;
            this.minX = 0.0f;
            this.mLenBeforeSqrt = 0.0f;
            this.mSpanX = 0.0f;
            this.mSpanY = 0.0f;
        }
    }

    public ScaleGestureDetector(Context context, OnScaleGestureListener onScaleGestureListener) {
        this(context, onScaleGestureListener, null);
    }

    public ScaleGestureDetector(Context context, OnScaleGestureListener onScaleGestureListener, Handler handler) {
        this(context, ViewConfiguration.get(context).getScaledTouchSlop() * 2, ViewConfiguration.get(context).getScaledMinimumScalingSpan(), handler, onScaleGestureListener);
    }

    public ScaleGestureDetector(Context context, int i, int i2, Handler handler, OnScaleGestureListener onScaleGestureListener) {
        this.mUpdatePrevious = true;
        this.mAreaRateCalculating = false;
        this.mUseTwoFingerSweep = false;
        this.mTempLenBeforeSqrt = 0.0f;
        this.mAreaThreshold = 6000.0f;
        this.mAreaRateThreshold = 1.0f;
        this.mStateCurrent = new SaveState();
        this.mAnchoredScaleMode = 0;
        this.mContext = context;
        this.mListener = onScaleGestureListener;
        this.mAreaThreshold *= context.getResources().getDisplayMetrics().density;
        this.mHandler = handler;
        int i3 = context.getApplicationInfo().targetSdkVersion;
        if (i3 > 18) {
            setQuickScaleEnabled(true);
        }
        if (i3 > 22) {
            setStylusScaleEnabled(true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0107  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        float f3;
        this.mCurrTime = motionEvent.getEventTime();
        int actionMasked = motionEvent.getActionMasked();
        if (this.mQuickScaleEnabled) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        boolean z = (motionEvent.getButtonState() & 32) != 0;
        boolean z2 = this.mAnchoredScaleMode == 2 && !z;
        boolean z3 = actionMasked == 1 || actionMasked == 3 || z2;
        if (actionMasked == 0 || z3) {
            if (this.mInProgress) {
                this.mListener.onScaleEnd(this);
                this.mInProgress = false;
                this.mAnchoredScaleMode = 0;
            } else if (inAnchoredScaleMode() && z3) {
                this.mInProgress = false;
                this.mAnchoredScaleMode = 0;
            }
            if (z3) {
                return true;
            }
        }
        if (!this.mInProgress) {
            if (actionMasked == 1 || actionMasked == 3 || motionEvent.getPointerCount() == 4) {
                reset();
            } else {
                if (this.mStylusScaleEnabled && !inAnchoredScaleMode() && !z3 && z) {
                    this.mAnchoredScaleStartX = motionEvent.getX();
                    this.mAnchoredScaleStartY = motionEvent.getY();
                    this.mAnchoredScaleMode = 2;
                }
                getArea(motionEvent);
                if (actionMasked == 0 || actionMasked == 6 || actionMasked == 5 || z2) {
                    this.mCurrSpanX = this.mStateCurrent.mSpanX;
                    this.mCurrSpanY = this.mStateCurrent.mSpanY;
                    this.mCurrLenBeforeSqrt = this.mStateCurrent.mLenBeforeSqrt;
                }
                if (this.mStateCurrent.mLenBeforeSqrt > this.mAreaThreshold) {
                    if (!this.mAreaRateCalculating && !this.mUseTwoFingerSweep) {
                        this.mTempLenBeforeSqrt = this.mStateCurrent.mLenBeforeSqrt;
                        this.mAreaRateCalculating = true;
                    }
                    if (this.mUseTwoFingerSweep) {
                        this.mPrevLenBeforeSqrt = this.mStateCurrent.mLenBeforeSqrt;
                    }
                    if (this.mAreaRateCalculating) {
                        float f4 = this.mStateCurrent.mLenBeforeSqrt;
                        float f5 = this.mTempLenBeforeSqrt;
                        if (f4 > f5) {
                            f2 = this.mStateCurrent.mLenBeforeSqrt;
                            f3 = this.mTempLenBeforeSqrt;
                            f = f2 / f3;
                            if (this.mUseTwoFingerSweep ? !(!this.mAreaRateCalculating || f <= this.mAreaRateThreshold) : f >= this.mAreaRateThreshold) {
                                float f6 = this.mStateCurrent.mSpanX;
                                this.mCurrSpanX = f6;
                                this.mPrevSpanX = f6;
                                float f7 = this.mStateCurrent.mSpanY;
                                this.mCurrSpanY = f7;
                                this.mPrevSpanY = f7;
                                this.mPrevTime = this.mCurrTime;
                                float f8 = this.mStateCurrent.mLenBeforeSqrt;
                                this.mCurrLenBeforeSqrt = f8;
                                this.mPrevLenBeforeSqrt = f8;
                                this.mInProgress = this.mListener.onScaleBegin(this);
                                Log.i(TAG, "TwScaleGestureDetector");
                                this.mAreaRateCalculating = false;
                            }
                        } else {
                            f = f5 / this.mStateCurrent.mLenBeforeSqrt;
                            if (this.mUseTwoFingerSweep) {
                                float f62 = this.mStateCurrent.mSpanX;
                                this.mCurrSpanX = f62;
                                this.mPrevSpanX = f62;
                                float f72 = this.mStateCurrent.mSpanY;
                                this.mCurrSpanY = f72;
                                this.mPrevSpanY = f72;
                                this.mPrevTime = this.mCurrTime;
                                float f82 = this.mStateCurrent.mLenBeforeSqrt;
                                this.mCurrLenBeforeSqrt = f82;
                                this.mPrevLenBeforeSqrt = f82;
                                this.mInProgress = this.mListener.onScaleBegin(this);
                                Log.i(TAG, "TwScaleGestureDetector");
                                this.mAreaRateCalculating = false;
                            }
                        }
                    } else {
                        float f9 = this.mStateCurrent.mLenBeforeSqrt;
                        float f10 = this.mPrevLenBeforeSqrt;
                        if (f9 > f10) {
                            f2 = this.mStateCurrent.mLenBeforeSqrt;
                            f3 = this.mPrevLenBeforeSqrt;
                            f = f2 / f3;
                            if (this.mUseTwoFingerSweep) {
                            }
                        } else {
                            f = f10 / this.mStateCurrent.mLenBeforeSqrt;
                            if (this.mUseTwoFingerSweep) {
                            }
                        }
                    }
                } else if (this.mUpdatePrevious && (actionMasked == 2 || actionMasked == 213)) {
                    this.mPrevSpanX = this.mCurrSpanX;
                    this.mPrevSpanY = this.mCurrSpanY;
                    this.mPrevLenBeforeSqrt = this.mCurrLenBeforeSqrt;
                    this.mPrevTime = this.mCurrTime;
                }
            }
        } else {
            if (actionMasked == 2 || actionMasked == 213) {
                getArea(motionEvent);
                if (this.mStateCurrent.mLenBeforeSqrt <= 0.0f) {
                    return true;
                }
                this.mCurrSpanX = this.mStateCurrent.mSpanX;
                this.mCurrSpanY = this.mStateCurrent.mSpanY;
                this.mCurrLenBeforeSqrt = this.mStateCurrent.mLenBeforeSqrt;
                this.mUpdatePrevious = this.mListener.onScale(this);
            } else {
                this.mListener.onScaleEnd(this);
                reset();
            }
            if (this.mUpdatePrevious) {
                this.mPrevSpanX = this.mCurrSpanX;
                this.mPrevSpanY = this.mCurrSpanY;
                this.mPrevLenBeforeSqrt = this.mCurrLenBeforeSqrt;
                this.mPrevTime = this.mCurrTime;
            }
        }
        return true;
    }

    private void getArea(MotionEvent motionEvent) {
        float f;
        float f2;
        this.mStateCurrent.reset();
        if (inAnchoredScaleMode()) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            f = this.mAnchoredScaleStartX;
            f2 = this.mAnchoredScaleStartY;
            this.mStateCurrent.mSpanX = f > x ? f - x : x - f;
            this.mStateCurrent.mSpanY = f2 > y ? f2 - y : y - f2;
            SaveState saveState = this.mStateCurrent;
            saveState.mLenBeforeSqrt = saveState.mSpanY * this.mStateCurrent.mSpanY;
            this.mEventBeforeOrAboveStartingGestureEvent = y < f2;
        } else {
            int pointerCount = motionEvent.getPointerCount();
            float f3 = 0.0f;
            float f4 = 0.0f;
            boolean z = false;
            for (int i = 0; i < pointerCount; i++) {
                float x2 = motionEvent.getX(i);
                float y2 = motionEvent.getY(i);
                if (z) {
                    if (this.mStateCurrent.minX > x2) {
                        this.mStateCurrent.minX = x2;
                    }
                    if (this.mStateCurrent.minY > y2) {
                        this.mStateCurrent.minY = y2;
                    }
                    if (this.mStateCurrent.maxX < x2) {
                        this.mStateCurrent.maxX = x2;
                    }
                    if (this.mStateCurrent.maxY < y2) {
                        this.mStateCurrent.maxY = y2;
                    }
                } else {
                    this.mStateCurrent.minX = x2;
                    this.mStateCurrent.maxX = x2;
                    this.mStateCurrent.minY = y2;
                    this.mStateCurrent.maxY = y2;
                    z = true;
                }
                f3 += x2;
                f4 += y2;
            }
            float f5 = pointerCount;
            f = f3 / f5;
            f2 = f4 / f5;
            SaveState saveState2 = this.mStateCurrent;
            saveState2.mSpanX = saveState2.maxX - this.mStateCurrent.minX;
            SaveState saveState3 = this.mStateCurrent;
            saveState3.mSpanY = saveState3.maxY - this.mStateCurrent.minY;
            SaveState saveState4 = this.mStateCurrent;
            saveState4.mLenBeforeSqrt = (saveState4.mSpanX * this.mStateCurrent.mSpanX) + (this.mStateCurrent.mSpanY * this.mStateCurrent.mSpanY);
        }
        this.mFocusX = f;
        this.mFocusY = f2;
    }

    private void reset() {
        this.mInProgress = false;
        this.mUpdatePrevious = true;
        this.mAreaRateCalculating = false;
        this.mAnchoredScaleMode = 0;
    }

    private boolean inAnchoredScaleMode() {
        return this.mAnchoredScaleMode != 0;
    }

    public void setQuickScaleEnabled(boolean z) {
        this.mQuickScaleEnabled = z;
        if (z && this.mGestureDetector == null) {
            GestureDetector gestureDetector = new GestureDetector(this.mContext, new GestureDetector.SimpleOnGestureListener() { // from class: android.view.ScaleGestureDetector.1
                int mQuickScaleDoubleTapY;
                int mQuickScaleSpanSlop;

                {
                    this.mQuickScaleSpanSlop = ViewConfiguration.get(ScaleGestureDetector.this.mContext).getScaledTouchSlop();
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    ScaleGestureDetector.this.mAnchoredScaleStartX = motionEvent.getX();
                    ScaleGestureDetector.this.mAnchoredScaleStartY = motionEvent.getY();
                    this.mQuickScaleDoubleTapY = (int) motionEvent.getY();
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
                public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                    if (((int) Math.abs(this.mQuickScaleDoubleTapY - motionEvent.getY())) > this.mQuickScaleSpanSlop) {
                        ScaleGestureDetector.this.mAnchoredScaleMode = 1;
                    }
                    return true;
                }
            }, this.mHandler);
            this.mGestureDetector = gestureDetector;
            gestureDetector.setIsLongpressEnabled(false);
        }
    }

    public boolean isQuickScaleEnabled() {
        return this.mQuickScaleEnabled;
    }

    public void setStylusScaleEnabled(boolean z) {
        this.mStylusScaleEnabled = z;
    }

    public boolean isStylusScaleEnabled() {
        return this.mStylusScaleEnabled;
    }

    public boolean isInProgress() {
        return this.mInProgress;
    }

    public float getFocusX() {
        return this.mFocusX;
    }

    public float getFocusY() {
        return this.mFocusY;
    }

    public float getCurrentSpan() {
        return (float) Math.sqrt(this.mCurrLenBeforeSqrt);
    }

    public float getCurrentSpanX() {
        return Math.abs(this.mCurrSpanX);
    }

    public float getCurrentSpanY() {
        return Math.abs(this.mCurrSpanY);
    }

    public float getPreviousSpan() {
        return (float) Math.sqrt(this.mPrevLenBeforeSqrt);
    }

    public float getPreviousSpanX() {
        return Math.abs(this.mPrevSpanX);
    }

    public float getPreviousSpanY() {
        return Math.abs(this.mPrevSpanY);
    }

    public float getScaleFactor() {
        if (inAnchoredScaleMode()) {
            boolean z = this.mEventBeforeOrAboveStartingGestureEvent;
            boolean z2 = (z && this.mCurrLenBeforeSqrt < this.mPrevLenBeforeSqrt) || (!z && this.mCurrLenBeforeSqrt > this.mPrevLenBeforeSqrt);
            float fAbs = Math.abs(1.0f - ((float) Math.sqrt(this.mCurrLenBeforeSqrt / this.mPrevLenBeforeSqrt))) * 0.5f;
            if (this.mPrevLenBeforeSqrt <= 0.0f) {
                return 1.0f;
            }
            return z2 ? fAbs + 1.0f : 1.0f - fAbs;
        }
        float fSqrt = (float) Math.sqrt(this.mCurrLenBeforeSqrt / this.mPrevLenBeforeSqrt);
        if (!Float.isNaN(fSqrt)) {
            if (this.mPrevLenBeforeSqrt > 0.0f) {
                return fSqrt;
            }
            return 1.0f;
        }
        Log.e(TAG, "getScaleFactor: Cannot getScaleFactor, sqrtValue is NaN, mCurrLenBeforeSqrt = " + this.mCurrLenBeforeSqrt + ", mPrevLenBeforeSqrt = " + this.mPrevLenBeforeSqrt);
        return 1.0f;
    }

    public long getTimeDelta() {
        return this.mCurrTime - this.mPrevTime;
    }

    public long getEventTime() {
        return this.mCurrTime;
    }

    public void semSetUseTwoFingerSweep(boolean z) {
        this.mUseTwoFingerSweep = z;
    }
}
