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

    /* JADX WARN: Removed duplicated region for block: B:56:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0107  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 422
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ScaleGestureDetector.onTouchEvent(android.view.MotionEvent):boolean");
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
            float abs = Math.abs(1.0f - ((float) Math.sqrt(this.mCurrLenBeforeSqrt / this.mPrevLenBeforeSqrt))) * 0.5f;
            if (this.mPrevLenBeforeSqrt <= 0.0f) {
                return 1.0f;
            }
            return z2 ? abs + 1.0f : 1.0f - abs;
        }
        float sqrt = (float) Math.sqrt(this.mCurrLenBeforeSqrt / this.mPrevLenBeforeSqrt);
        if (!Float.isNaN(sqrt)) {
            if (this.mPrevLenBeforeSqrt > 0.0f) {
                return sqrt;
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
