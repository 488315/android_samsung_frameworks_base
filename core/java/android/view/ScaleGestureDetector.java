package android.view;

import android.content.Context;
import android.p009os.Handler;
import android.util.Log;

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
    public boolean onScale(ScaleGestureDetector detector) {
      return false;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector detector) {
      return true;
    }

    @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector detector) {}
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

  public ScaleGestureDetector(Context context, OnScaleGestureListener listener) {
    this(context, listener, null);
  }

  public ScaleGestureDetector(Context context, OnScaleGestureListener listener, Handler handler) {
    this.mUpdatePrevious = true;
    this.mAreaRateCalculating = false;
    this.mUseTwoFingerSweep = false;
    this.mTempLenBeforeSqrt = 0.0f;
    this.mAreaThreshold = 6000.0f;
    this.mAreaRateThreshold = 1.0f;
    this.mStateCurrent = new SaveState();
    this.mAnchoredScaleMode = 0;
    this.mContext = context;
    this.mListener = listener;
    this.mAreaThreshold *= context.getResources().getDisplayMetrics().density;
    this.mHandler = handler;
    int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
    if (targetSdkVersion > 18) {
      setQuickScaleEnabled(true);
    }
    if (targetSdkVersion > 22) {
      setStylusScaleEnabled(true);
    }
  }

  public boolean onTouchEvent(MotionEvent event) {
    float areaRate;
    boolean scaleDecision;
    this.mCurrTime = event.getEventTime();
    int action = event.getActionMasked();
    if (this.mQuickScaleEnabled) {
      this.mGestureDetector.onTouchEvent(event);
    }
    boolean isStylusButtonDown = (event.getButtonState() & 32) != 0;
    boolean anchoredScaleCancelled = this.mAnchoredScaleMode == 2 && !isStylusButtonDown;
    boolean streamComplete = action == 1 || action == 3 || anchoredScaleCancelled;
    if (action == 0 || streamComplete) {
      if (this.mInProgress) {
        this.mListener.onScaleEnd(this);
        this.mInProgress = false;
        this.mAnchoredScaleMode = 0;
      } else if (inAnchoredScaleMode() && streamComplete) {
        this.mInProgress = false;
        this.mAnchoredScaleMode = 0;
      }
      if (streamComplete) {
        return true;
      }
    }
    if (!this.mInProgress) {
      if (action == 1 || action == 3 || event.getPointerCount() == 4) {
        reset();
      } else {
        if (this.mStylusScaleEnabled
            && !inAnchoredScaleMode()
            && !streamComplete
            && isStylusButtonDown) {
          this.mAnchoredScaleStartX = event.getX();
          this.mAnchoredScaleStartY = event.getY();
          this.mAnchoredScaleMode = 2;
        }
        getArea(event);
        boolean configChanged = action == 0 || action == 6 || action == 5 || anchoredScaleCancelled;
        if (configChanged) {
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
            float f = this.mStateCurrent.mLenBeforeSqrt;
            float f2 = this.mTempLenBeforeSqrt;
            if (f > f2) {
              areaRate = this.mStateCurrent.mLenBeforeSqrt / this.mTempLenBeforeSqrt;
            } else {
              areaRate = f2 / this.mStateCurrent.mLenBeforeSqrt;
            }
          } else {
            float f3 = this.mStateCurrent.mLenBeforeSqrt;
            float f4 = this.mPrevLenBeforeSqrt;
            if (f3 > f4) {
              areaRate = this.mStateCurrent.mLenBeforeSqrt / this.mPrevLenBeforeSqrt;
            } else {
              areaRate = f4 / this.mStateCurrent.mLenBeforeSqrt;
            }
          }
          if (this.mUseTwoFingerSweep) {
            scaleDecision = areaRate >= this.mAreaRateThreshold;
          } else {
            scaleDecision = this.mAreaRateCalculating && areaRate > this.mAreaRateThreshold;
          }
          if (scaleDecision) {
            float f5 = this.mStateCurrent.mSpanX;
            this.mCurrSpanX = f5;
            this.mPrevSpanX = f5;
            float f6 = this.mStateCurrent.mSpanY;
            this.mCurrSpanY = f6;
            this.mPrevSpanY = f6;
            this.mPrevTime = this.mCurrTime;
            float f7 = this.mStateCurrent.mLenBeforeSqrt;
            this.mCurrLenBeforeSqrt = f7;
            this.mPrevLenBeforeSqrt = f7;
            this.mInProgress = this.mListener.onScaleBegin(this);
            Log.m98i(TAG, "TwScaleGestureDetector");
            this.mAreaRateCalculating = false;
          }
        } else if (this.mUpdatePrevious && (action == 2 || action == 213)) {
          this.mPrevSpanX = this.mCurrSpanX;
          this.mPrevSpanY = this.mCurrSpanY;
          this.mPrevLenBeforeSqrt = this.mCurrLenBeforeSqrt;
          this.mPrevTime = this.mCurrTime;
        }
      }
    } else {
      if (action == 2 || action == 213) {
        getArea(event);
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

  private void getArea(MotionEvent event) {
    float focusX;
    float focusY;
    boolean isInitialized = false;
    float focusX2 = 0.0f;
    float focusY2 = 0.0f;
    this.mStateCurrent.reset();
    if (inAnchoredScaleMode()) {
      float x = event.getX();
      float y = event.getY();
      focusX = this.mAnchoredScaleStartX;
      focusY = this.mAnchoredScaleStartY;
      this.mStateCurrent.mSpanX = focusX > x ? focusX - x : x - focusX;
      this.mStateCurrent.mSpanY = focusY > y ? focusY - y : y - focusY;
      SaveState saveState = this.mStateCurrent;
      saveState.mLenBeforeSqrt = saveState.mSpanY * this.mStateCurrent.mSpanY;
      this.mEventBeforeOrAboveStartingGestureEvent = y < focusY;
    } else {
      int count = event.getPointerCount();
      for (int i = 0; i < count; i++) {
        float x2 = event.getX(i);
        float y2 = event.getY(i);
        if (isInitialized) {
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
          isInitialized = true;
        }
        focusX2 += x2;
        focusY2 += y2;
      }
      focusX = focusX2 / count;
      focusY = focusY2 / count;
      SaveState saveState2 = this.mStateCurrent;
      saveState2.mSpanX = saveState2.maxX - this.mStateCurrent.minX;
      SaveState saveState3 = this.mStateCurrent;
      saveState3.mSpanY = saveState3.maxY - this.mStateCurrent.minY;
      SaveState saveState4 = this.mStateCurrent;
      saveState4.mLenBeforeSqrt =
          (saveState4.mSpanX * this.mStateCurrent.mSpanX)
              + (this.mStateCurrent.mSpanY * this.mStateCurrent.mSpanY);
    }
    this.mFocusX = focusX;
    this.mFocusY = focusY;
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

  public void setQuickScaleEnabled(boolean scales) {
    this.mQuickScaleEnabled = scales;
    if (scales && this.mGestureDetector == null) {
      GestureDetector.SimpleOnGestureListener gestureListener =
          new GestureDetector
              .SimpleOnGestureListener() { // from class: android.view.ScaleGestureDetector.1
            int mQuickScaleDoubleTapY;
            int mQuickScaleSpanSlop;

            {
              this.mQuickScaleSpanSlop =
                  ViewConfiguration.get(ScaleGestureDetector.this.mContext).getScaledTouchSlop();
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener,
                      // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
              ScaleGestureDetector.this.mAnchoredScaleStartX = e.getX();
              ScaleGestureDetector.this.mAnchoredScaleStartY = e.getY();
              this.mQuickScaleDoubleTapY = (int) e.getY();
              return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener,
                      // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTapEvent(MotionEvent e) {
              int delta = (int) Math.abs(this.mQuickScaleDoubleTapY - e.getY());
              if (delta > this.mQuickScaleSpanSlop) {
                ScaleGestureDetector.this.mAnchoredScaleMode = 1;
              }
              return true;
            }
          };
      GestureDetector gestureDetector =
          new GestureDetector(this.mContext, gestureListener, this.mHandler);
      this.mGestureDetector = gestureDetector;
      gestureDetector.setIsLongpressEnabled(false);
    }
  }

  public boolean isQuickScaleEnabled() {
    return this.mQuickScaleEnabled;
  }

  public void setStylusScaleEnabled(boolean scales) {
    this.mStylusScaleEnabled = scales;
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
      boolean scaleUp =
          (z && this.mCurrLenBeforeSqrt < this.mPrevLenBeforeSqrt)
              || (!z && this.mCurrLenBeforeSqrt > this.mPrevLenBeforeSqrt);
      float spanDiff =
          Math.abs(1.0f - ((float) Math.sqrt(this.mCurrLenBeforeSqrt / this.mPrevLenBeforeSqrt)))
              * 0.5f;
      if (this.mPrevLenBeforeSqrt <= 0.0f) {
        return 1.0f;
      }
      return scaleUp ? 1.0f + spanDiff : 1.0f - spanDiff;
    }
    float sqrtValue = (float) Math.sqrt(this.mCurrLenBeforeSqrt / this.mPrevLenBeforeSqrt);
    if (Float.isNaN(sqrtValue)) {
      Log.m96e(
          TAG,
          "getScaleFactor: Cannot getScaleFactor, sqrtValue is NaN, mCurrLenBeforeSqrt = "
              + this.mCurrLenBeforeSqrt
              + ", mPrevLenBeforeSqrt = "
              + this.mPrevLenBeforeSqrt);
      return 1.0f;
    }
    if (this.mPrevLenBeforeSqrt > 0.0f) {
      return sqrtValue;
    }
    return 1.0f;
  }

  public long getTimeDelta() {
    return this.mCurrTime - this.mPrevTime;
  }

  public long getEventTime() {
    return this.mCurrTime;
  }

  public void semSetUseTwoFingerSweep(boolean enabled) {
    this.mUseTwoFingerSweep = enabled;
  }
}
