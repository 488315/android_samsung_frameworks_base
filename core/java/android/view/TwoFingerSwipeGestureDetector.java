package android.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Debug;
import android.os.SystemProperties;
import android.util.Slog;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes4.dex */
public class TwoFingerSwipeGestureDetector
    implements WindowManagerPolicyConstants.PointerEventListener {
  private static final int CANCELED = 4;
  private static final int COMMITTED = 3;
  protected static final int DETECTED = 2;
  private static final int DETECTING = 1;
  public static final int DOWN = 8;
  protected static final float DOWN_THRESHOLD_DIP = 20.0f;
  protected static final float EASY_START_THRESHOLD_DIP = 20.0f;
  protected static final int ENDED = 5;
  public static final int INVALID = 0;
  public static final int LEFT = 1;
  private static final float MINUS_DISTANCE_RATIO = 0.5f;
  private static final float PLUS_DISTANCE_RATIO = 0.8f;
  private static final int POSITION_MASK = 13;
  public static final int RIGHT = 4;
  private static final float SIDE_THRESHOLD_DIP = 96.0f;
  private static final float SIDE_THRESHOLD_FOR_ONE_FINGER_DIP = 24.0f;
  private static final int TIME_THRESHOLD_MS = 200;
  private static final float TOUCH_SLOP_DIP = 24.0f;
  private static final int UNIT_PIXELS_PER_SECOND = 1000;

  /* renamed from: UP */
  public static final int f556UP = 2;
  protected boolean DEBUG;
  protected boolean DEBUG_NOISE;
  protected final String TAG;
  protected float mDensity;
  protected MotionEvent mDetectedMotionEvent;
  private final Rect mDisplayBounds;
  private boolean mDownEnabled;
  protected int mDownThreshold;
  protected int mEasyStartThreshold;
  protected int[] mEasyThresholds;
  private float mEndCenterX;
  private float mEndCenterY;
  private final Region mExcludeRegion;
  private float mInitialDistance;
  private final List<GestureListener> mListeners;
  private final int mMaximumFlingVelocity;
  private final int mMinimumFlingVelocity;
  private float mMinusDistanceRatio;
  private int mPivotId;
  private long mPivotTime;
  protected float mPivotX;
  protected float mPivotY;
  private float mPlusDistanceRatio;
  private boolean mSideEnabled;
  protected int mSideThreshold;
  private int mSideThresholdForOneFinger;
  protected float mStartCenterX;
  protected float mStartCenterY;
  protected int mStartPosition;
  protected int mState;
  protected int[] mThresholds;
  protected int[] mThresholdsForOneFinger;
  private float mTimeThreshold;
  private float mTouchSlopDip;
  private float mTouchSlopSquare;
  private boolean mUseThreeFinger;
  protected VelocityTracker mVelocityTracker;

  public @interface GestureState {}

  public @interface PositionDirection {}

  static class Tuner {
    public static final String TIME_THRESHOLD = getSystemPropertiesKey("time_threshold");
    public static final String MINUS_DISTANCE_RATIO =
        getSystemPropertiesKey("minus_distance_ratio");
    public static final String PLUS_DISTANCE_RATIO = getSystemPropertiesKey("plus_distance_ratio");
    public static final String EASY_START_THRESHOLD_DIP =
        getSystemPropertiesKey("easy_start_threshold_dip");
    public static final String SIDE_THRESHOLD_DIP = getSystemPropertiesKey("side_threshold_dip");

    Tuner() {}

    static String getSystemPropertiesKey(String post) {
      return "mw.split.gesture.tune." + post;
    }
  }

  public void setTouchSlopForTest(float touchSlopDip) {
    this.mTouchSlopDip = touchSlopDip;
    setTouchSlopSquare((int) ((this.mDensity * touchSlopDip) + 0.5f));
  }

  private TwoFingerSwipeGestureDetector(Context context, String from) {
    this.mThresholds = new int[3];
    this.mThresholdsForOneFinger = new int[3];
    this.mEasyThresholds = new int[3];
    this.mTimeThreshold = SystemProperties.getInt(Tuner.TIME_THRESHOLD, 200);
    this.mMinusDistanceRatio = 0.5f;
    this.mPlusDistanceRatio = 0.8f;
    this.mDisplayBounds = new Rect();
    this.mExcludeRegion = new Region();
    this.mListeners = new ArrayList();
    boolean z = true;
    this.mDownEnabled = true;
    this.mSideEnabled = true;
    this.mState = 5;
    if (!CoreRune.SAFE_DEBUG && !CoreRune.IS_DEBUG_LEVEL_MID) {
      z = false;
    }
    this.DEBUG = z;
    this.DEBUG_NOISE = false;
    this.TAG =
        TwoFingerSwipeGestureDetector.class.getSimpleName()
            + NavigationBarInflaterView.SIZE_MOD_START
            + from
            + NavigationBarInflaterView.SIZE_MOD_END;
    this.mTouchSlopDip = 24.0f;
    if (context == null) {
      this.mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
      this.mMinimumFlingVelocity = ViewConfiguration.getDoubleTapMinTime();
    } else {
      ViewConfiguration vc = ViewConfiguration.get(context);
      this.mMaximumFlingVelocity = vc.getScaledMaximumFlingVelocity();
      this.mMinimumFlingVelocity = vc.getScaledMinimumFlingVelocity();
    }
  }

  public TwoFingerSwipeGestureDetector(Context context, GestureListener listener, String from) {
    this(context, from);
    this.mListeners.add(listener);
  }

  public TwoFingerSwipeGestureDetector(
      Context context,
      Function<TwoFingerSwipeGestureDetector, GestureListener> supplier,
      String from) {
    this(context, from);
    this.mListeners.add(supplier.apply(this));
  }

  public void setGestureExclusionRegion(Region region) {
    if (this.DEBUG) {
      Slog.m113d(this.TAG, "setGestureExclusionRegion. " + region);
    }
    this.mExcludeRegion.set(region);
  }

  @Override // android.view.WindowManagerPolicyConstants.PointerEventListener
  public void onPointerEvent(MotionEvent motionEvent) {
    onInputEvent(motionEvent);
  }

  public void onInputEvent(InputEvent event) {
    if (event instanceof MotionEvent) {
      final MotionEvent me = (MotionEvent) event;
      int action = me.getActionMasked();
      int pointerCount = me.getPointerCount();
      if (this.DEBUG && this.DEBUG_NOISE) {
        Slog.m113d(this.TAG, actionToString(action) + " pointerCount=" + pointerCount);
      }
      if (action == 0) {
        detecting();
        if (this.mVelocityTracker == null) {
          this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mPivotX = me.getX();
        this.mPivotY = me.getY();
        this.mPivotId = me.getPointerId(0);
        this.mPivotTime = me.getEventTime();
        return;
      }
      if (this.mState == 5) {
        return;
      }
      if (3 == action) {
        cancel();
        return;
      }
      if (!isValidPointerCount(pointerCount)) {
        if (this.DEBUG) {
          Slog.m113d(this.TAG, "invalid pointer count=" + me.getPointerCount());
        }
        cancel();
        return;
      }
      this.mVelocityTracker.addMovement(me);
      if (5 == action) {
        if (this.mState == 1) {
          if (isOverTouchTime(this.mPivotTime, me.getEventTime())) {
            if (this.DEBUG) {
              Slog.m113d(
                  this.TAG,
                  String.format(
                      "prev=%s cur=%s diff=%s timeThreshold=%f",
                      Long.valueOf(this.mPivotTime),
                      Long.valueOf(me.getEventTime()),
                      Long.valueOf(me.getEventTime() - this.mPivotTime),
                      Float.valueOf(this.mTimeThreshold)));
            }
            cancel();
            return;
          }
          int pivotIdx = me.findPointerIndex(this.mPivotId);
          if (pivotIdx < 0) {
            if (this.DEBUG) {
              Slog.m113d(this.TAG, "missing first touch.");
            }
            cancel();
            return;
          }
          if (allMatch(
              me,
              new BiFunction() { // from class:
                                 // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                  Boolean lambda$onInputEvent$0;
                  lambda$onInputEvent$0 =
                      TwoFingerSwipeGestureDetector.this.lambda$onInputEvent$0(
                          (Integer) obj, (Integer) obj2);
                  return lambda$onInputEvent$0;
                }
              })) {
            if (this.DEBUG) {
              Slog.m113d(
                  this.TAG,
                  String.format(
                      "ACTION_POINTER_DOWN. any pointer doesn't in thresholds. %s %s %s",
                      getXYString(Float.valueOf(me.getX(0)), Float.valueOf(me.getY(0))),
                      getXYString(Float.valueOf(me.getX(1)), Float.valueOf(me.getY(1))),
                      Arrays.toString(this.mThresholdsForOneFinger)));
            }
            cancel();
            return;
          }
          Objects.requireNonNull(me);
          this.mStartCenterX =
              getCenter(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                    }
                  });
          Objects.requireNonNull(me);
          this.mStartCenterY =
              getCenter(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                    }
                  });
          if (allMatch(
              me,
              new BiFunction() { // from class:
                                 // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda4
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                  return Boolean.valueOf(
                      TwoFingerSwipeGestureDetector.this.excludeRegionContains(
                          ((Integer) obj).intValue(), ((Integer) obj2).intValue()));
                }
              })) {
            if (this.DEBUG) {
              Slog.m113d(this.TAG, "started on gesture exclude region.");
            }
            cancel();
            return;
          }
          int position =
              getPosition((int) this.mStartCenterX, (int) this.mStartCenterY, this.mThresholds);
          this.mStartPosition = position;
          if (position == 0) {
            if (this.DEBUG) {
              Slog.m113d(
                  this.TAG,
                  "position invalid. "
                      + getXYString(
                          Float.valueOf(this.mStartCenterX), Float.valueOf(this.mStartCenterY)));
            }
            cancel();
            return;
          }
          MotionEvent motionEvent = this.mDetectedMotionEvent;
          if (motionEvent != null) {
            motionEvent.recycle();
          }
          this.mDetectedMotionEvent = MotionEvent.obtain(me);
          Objects.requireNonNull(me);
          float distanceSquareSum =
              getDistanceSquareSum(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                    }
                  });
          Objects.requireNonNull(me);
          this.mInitialDistance =
              distanceSquareSum
                  + getDistanceSquareSum(
                      me,
                      new Function() { // from class:
                                       // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                        }
                      });
          detected();
          if (this.DEBUG) {
            Slog.m113d(
                this.TAG,
                String.format(
                    "detected reason. events=%s, display=%s side=%b down=%b threshold=%s",
                    me,
                    this.mDisplayBounds,
                    Boolean.valueOf(this.mSideEnabled),
                    Boolean.valueOf(this.mDownEnabled),
                    Arrays.toString(this.mThresholds)));
            return;
          }
          return;
        }
        return;
      }
      if (2 == action) {
        if (this.mState == 2) {
          Objects.requireNonNull(me);
          float curCenterX =
              getCenter(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                    }
                  });
          Objects.requireNonNull(me);
          float curCenterY =
              getCenter(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                    }
                  });
          if (isOverThreshold(curCenterX, curCenterY, this.mEasyThresholds, this.mStartPosition)
              .booleanValue()) {
            commitIfPossible(curCenterX, curCenterY);
            return;
          }
          return;
        }
        return;
      }
      if (6 == action) {
        if (this.mState == 2) {
          if (!isTwoFingerVelocitiesSameDirection(me, pointerCount)) {
            cancel();
            return;
          }
          Objects.requireNonNull(me);
          float distanceSquareSum2 =
              getDistanceSquareSum(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                    }
                  });
          Objects.requireNonNull(me);
          float distance =
              distanceSquareSum2
                  + getDistanceSquareSum(
                      me,
                      new Function() { // from class:
                                       // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                        }
                      });
          if (!this.mUseThreeFinger
              && isTwoFingerDistanceFartherThanBefore(this.mInitialDistance, distance)) {
            if (this.DEBUG) {
              Slog.m113d(
                  this.TAG,
                  String.format(
                      "ACTION_UP. Distance between finger is farther than before. distance ratio=%f"
                          + " touch slop ratio=%f ",
                      Float.valueOf(distance / this.mInitialDistance),
                      Float.valueOf((distance - this.mInitialDistance) / this.mTouchSlopSquare)));
            }
            cancel();
            return;
          }
          int upIndex = me.getActionIndex();
          for (int i = 0; i < pointerCount; i++) {
            if (i != upIndex) {
              this.mPivotX = me.getX(i);
              this.mPivotY = me.getY(i);
            }
          }
          this.mPivotTime = me.getEventTime();
          Objects.requireNonNull(me);
          this.mEndCenterX =
              getCenter(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                    }
                  });
          Objects.requireNonNull(me);
          this.mEndCenterY =
              getCenter(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                    }
                  });
          return;
        }
        return;
      }
      if (1 == action) {
        if (this.mState == 2) {
          if (isOverTouchTime(this.mPivotTime, me.getEventTime())) {
            if (this.DEBUG) {
              Slog.m113d(
                  this.TAG,
                  String.format(
                      "ACTION_UP. prev=%s cur=%s timeThreshold=%f",
                      Long.valueOf(this.mPivotTime),
                      Long.valueOf(me.getEventTime()),
                      Float.valueOf(this.mTimeThreshold)));
            }
            cancel();
            return;
          }
          Objects.requireNonNull(me);
          float curCenterX2 =
              getCenter(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                    }
                  });
          Objects.requireNonNull(me);
          float curCenterY2 =
              getCenter(
                  me,
                  new Function() { // from class:
                                   // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                    }
                  });
          if (!isOverThreshold(curCenterX2, curCenterY2, this.mThresholds, this.mStartPosition)
              .booleanValue()) {
            if (this.DEBUG) {
              Slog.m113d(
                  this.TAG,
                  "ACTION_UP. didn't over threshold. sp="
                      + this.mStartPosition
                      + " cur="
                      + getXYString(Float.valueOf(curCenterX2), Float.valueOf(curCenterY2))
                      + " thresholds="
                      + Arrays.toString(this.mThresholds));
            }
            cancel();
            return;
          } else {
            if (!commitIfPossible(this.mEndCenterX, this.mEndCenterY)) {
              cancel();
              return;
            }
            return;
          }
        }
        end();
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Boolean lambda$onInputEvent$0(Integer x, Integer y) {
    return Boolean.valueOf(
        getPosition(x.intValue(), y.intValue(), this.mThresholdsForOneFinger) == 0);
  }

  private boolean isValidPointerCount(int pointerCount) {
    return this.mUseThreeFinger ? pointerCount <= 3 : pointerCount <= 2;
  }

  private boolean isOverTouchTime(long pivotTime, long eventTime) {
    return ((float) (eventTime - pivotTime)) > this.mTimeThreshold;
  }

  protected <T, U> String getXYString(T x, U y) {
    return NavigationBarInflaterView.KEY_CODE_START
        + x
        + ", "
        + y
        + NavigationBarInflaterView.KEY_CODE_END;
  }

  private boolean isTwoFingerDistanceFartherThanBefore(float initialDistance, float distance) {
    float ratio = distance / initialDistance;
    return ratio > this.mPlusDistanceRatio + 1.0f || ratio < 1.0f - this.mMinusDistanceRatio;
  }

  private boolean isOverTouchSlop(
      float prevX, float prevY, float curX, float curY, Consumer<Integer> debug) {
    int deltaX = (int) (curX - prevX);
    int deltaY = (int) (curY - prevY);
    int distanceSquare = (deltaX * deltaX) + (deltaY * deltaY);
    if (distanceSquare > this.mTouchSlopSquare) {
      if (debug != null) {
        debug.accept(Integer.valueOf(distanceSquare));
        return true;
      }
      return true;
    }
    return false;
  }

  protected boolean commitIfPossible(float curCenterX, float curCenterY) {
    int direction = getDirection(this.mStartCenterX, this.mStartCenterY, curCenterX, curCenterY);
    int gestureFrom = gestureFrom(this.mStartPosition, direction);
    if (gestureFrom == -1) {
      if (this.DEBUG) {
        Slog.m113d(this.TAG, "ActionMOVE: gestureFrom not found.");
        return false;
      }
      return false;
    }
    committed(gestureFrom);
    return true;
  }

  private boolean isTwoFingerVelocitiesSameDirection(MotionEvent me, int pointerCount) {
    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
    int upIndex = me.getActionIndex();
    int id1 = me.getPointerId(upIndex);
    float x1 = this.mVelocityTracker.getXVelocity(id1);
    float y1 = this.mVelocityTracker.getYVelocity(id1);
    for (int i = 0; i < pointerCount; i++) {
      if (i != upIndex) {
        int id2 = me.getPointerId(i);
        float x2 = this.mVelocityTracker.getXVelocity(id2);
        float y2 = this.mVelocityTracker.getYVelocity(id2);
        float dot = (x1 * x2) + (y1 * y2);
        if (dot < 0.0f) {
          this.mVelocityTracker.clear();
          if (this.DEBUG) {
            Slog.m113d(
                this.TAG,
                "dot product is negative. id1=("
                    + x1
                    + ","
                    + y1
                    + ") id2=("
                    + x2
                    + ","
                    + y2
                    + NavigationBarInflaterView.KEY_CODE_END);
            return false;
          }
          return false;
        }
      }
    }
    return true;
  }

  private boolean isInThreshold(int x, int y, int threshold, int position) {
    if (this.mDisplayBounds.isEmpty() || threshold < 0) {
      return false;
    }
    switch (position) {
      case 1:
        if (this.mDisplayBounds.left + threshold > x) {
          break;
        }
        break;
      case 4:
        if (this.mDisplayBounds.right - threshold < x) {
          break;
        }
        break;
      case 8:
        if (this.mDisplayBounds.bottom - threshold < y) {
          break;
        }
        break;
    }
    return false;
  }

  protected Boolean isOverThreshold(float x, float y, int[] thresholds, int position) {
    boolean isOver = false;
    if (this.mDisplayBounds.isEmpty()) {
      return false;
    }
    if ((position & 1) != 0) {
      isOver = false | (((float) (this.mDisplayBounds.left + thresholds[0])) < x);
    }
    if ((position & 4) != 0) {
      isOver |= ((float) (this.mDisplayBounds.right - thresholds[1])) > x;
    }
    if ((position & 8) != 0) {
      isOver |= ((float) (this.mDisplayBounds.bottom - thresholds[2])) > y;
    }
    return Boolean.valueOf(isOver);
  }

  private boolean isStartPositionEnabled(int startPosition) {
    switch (startPosition) {
      case 1:
      case 4:
        return this.mSideEnabled;
      case 8:
        return this.mDownEnabled;
      default:
        return false;
    }
  }

  protected boolean allMatch(MotionEvent me, BiFunction<Integer, Integer, Boolean> function) {
    int count = me.getPointerCount();
    boolean result = true;
    for (int i = 0; i < count; i++) {
      result &=
          function
              .apply(Integer.valueOf((int) me.getX(i)), Integer.valueOf((int) me.getY(i)))
              .booleanValue();
    }
    return result;
  }

  protected boolean excludeRegionContains(float x, float y) {
    return this.mExcludeRegion.contains((int) x, (int) y);
  }

  protected void detecting() {
    if (this.DEBUG) {
      Slog.m113d(this.TAG, "detecting");
    }
    this.mState = 1;
    this.mListeners.forEach(
        new Consumer() { // from class:
                         // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda5
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((TwoFingerSwipeGestureDetector.GestureListener) obj).onDetecting();
          }
        });
  }

  protected void detected() {
    if (this.DEBUG) {
      Slog.m113d(this.TAG, "detected");
    }
    this.mState = 2;
    this.mListeners.forEach(
        new Consumer() { // from class:
                         // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda6
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((TwoFingerSwipeGestureDetector.GestureListener) obj).onDetected();
          }
        });
  }

  private void committed(final int gestureFrom) {
    if (this.DEBUG) {
      Slog.m113d(this.TAG, "committed " + Debug.getCallers(2));
    }
    this.mState = 3;
    this.mListeners.forEach(
        new Consumer() { // from class:
                         // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda8
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((TwoFingerSwipeGestureDetector.GestureListener) obj).onCommitted(gestureFrom);
          }
        });
    end();
  }

  public void cancel() {
    int i = this.mState;
    if (i == 4 || i == 5) {
      return;
    }
    if (this.DEBUG) {
      Slog.m113d(this.TAG, "canceled from " + Debug.getCaller());
    }
    this.mState = 4;
    this.mListeners.forEach(
        new Consumer() { // from class:
                         // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((TwoFingerSwipeGestureDetector.GestureListener) obj).onCanceled();
          }
        });
    end();
  }

  protected void end() {
    if (this.DEBUG) {
      Slog.m113d(this.TAG, "end");
    }
    this.mState = 5;
    this.mListeners.forEach(
        new Consumer() { // from class:
                         // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda9
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ((TwoFingerSwipeGestureDetector.GestureListener) obj).onEnd();
          }
        });
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }

  private float getDistanceSquareSum(MotionEvent me, Function<Integer, Float> function) {
    float distanceSum = 0.0f;
    int count = me.getPointerCount();
    for (int i = 0; i + 1 < count; i++) {
      float diff =
          function.apply(Integer.valueOf(i)).floatValue()
              - function.apply(Integer.valueOf(i + 1)).floatValue();
      distanceSum += diff * diff;
    }
    return distanceSum;
  }

  protected float getCenter(MotionEvent me, Function<Integer, Float> function) {
    float sum = 0.0f;
    int count = me.getPointerCount();
    for (int i = 0; i < count; i++) {
      sum += function.apply(Integer.valueOf(i)).floatValue();
    }
    return sum / count;
  }

  private int gestureFrom(int startPosition, int direction) {
    if ((startPosition & 1) != 0 && direction == 4) {
      return 1;
    }
    if ((startPosition & 4) == 0 || direction != 1) {
      return ((startPosition & 8) == 0 || direction != 2) ? -1 : 4;
    }
    return 3;
  }

  public int getPosition(int x, int y, int[] thresholds) {
    int result = 0;
    if (this.mDisplayBounds.isEmpty()) {
      Slog.m115e(this.TAG, "display bounds is empty.");
      return 0;
    }
    if (isStartPositionEnabled(1) && isInThreshold(x, y, thresholds[0], 1)) {
      result = 0 | 1;
    }
    if (isStartPositionEnabled(4) && isInThreshold(x, y, thresholds[1], 4)) {
      result |= 4;
    }
    if (isStartPositionEnabled(8) && isInThreshold(x, y, thresholds[2], 8)) {
      return result | 8;
    }
    return result;
  }

  private int getDirection(float sX, float sY, float eX, float eY) {
    float dX = eX - sX;
    float dY = eY - sY;
    return Math.abs(dX) > Math.abs(dY) ? dX < 0.0f ? 1 : 4 : dY < 0.0f ? 2 : 8;
  }

  public void init(Rect displayBounds, float density, int enabledPosition) {
    setDensity(density);
    setDisplayBounds(displayBounds);
    setGestureSearchSide(enabledPosition);
    build();
  }

  public void init(Rect displayBounds, float density, int enabledPosition, boolean useThreeFinger) {
    setDensity(density);
    setDisplayBounds(displayBounds);
    setGestureSearchSide(enabledPosition);
    useThreeFinger(useThreeFinger);
    build();
  }

  private void useThreeFinger(boolean isThreeFinger) {
    this.mUseThreeFinger = isThreeFinger;
  }

  protected void build() {
    if (this.DEBUG) {
      Slog.m113d(this.TAG, "updateDipResources. density=" + this.mDensity);
    }
    float f = this.mDensity;
    if (f > 0.0f) {
      this.mSideThreshold = (int) ((f * getSideThresholdDip()) + 0.5f);
      float f2 = this.mDensity;
      this.mSideThresholdForOneFinger = (int) ((24.0f * f2) + 0.5f);
      this.mDownThreshold = (int) ((20.0f * f2) + 0.5f);
      this.mEasyStartThreshold = (int) ((f2 * getEasyStartThresholdDip()) + 0.5f);
      setTouchSlopSquare((int) ((this.mDensity * this.mTouchSlopDip) + 0.5f));
    }
    int[] iArr = this.mThresholds;
    int i = this.mSideThreshold;
    initThresholds(iArr, i, i, this.mDownThreshold);
    int[] iArr2 = this.mThresholdsForOneFinger;
    int i2 = this.mSideThresholdForOneFinger;
    initThresholds(iArr2, i2, i2, this.mDownThreshold);
    int[] iArr3 = this.mEasyThresholds;
    int i3 = this.mEasyStartThreshold;
    initThresholds(iArr3, i3, i3, i3);
    this.mMinusDistanceRatio =
        Float.parseFloat(SystemProperties.get(Tuner.MINUS_DISTANCE_RATIO, String.valueOf(0.5f)));
    this.mPlusDistanceRatio =
        Float.parseFloat(SystemProperties.get(Tuner.PLUS_DISTANCE_RATIO, String.valueOf(0.8f)));
  }

  private void setDensity(float density) {
    this.mDensity = density;
  }

  public void setGestureSearchSide(int position) {
    int masked = position & 13;
    this.mSideEnabled = (masked & 5) == 5;
    this.mDownEnabled = (masked & 8) == 8;
  }

  public void setDisplayBounds(Rect displayBounds) {
    if (this.DEBUG) {
      Slog.m113d(this.TAG, "setDisplayBounds. displayBounds=" + displayBounds);
    }
    this.mDisplayBounds.set(displayBounds);
  }

  protected void initThresholds(int[] thresholds, int left, int right, int down) {
    Arrays.fill(thresholds, Math.max(this.mDisplayBounds.width(), this.mDisplayBounds.height()));
    if (left >= 0) {
      thresholds[0] = left;
    }
    if (right >= 0) {
      thresholds[1] = right;
    }
    if (down >= 0) {
      thresholds[2] = down;
    }
  }

  protected float getEasyStartThresholdDip() {
    String easyStartThresholdDipStr = SystemProperties.get(Tuner.EASY_START_THRESHOLD_DIP, "-1");
    return Float.parseFloat(easyStartThresholdDipStr);
  }

  protected float getSideThresholdDip() {
    String sideThresholdDipStr =
        SystemProperties.get(Tuner.SIDE_THRESHOLD_DIP, Float.toString(SIDE_THRESHOLD_DIP));
    return Float.parseFloat(sideThresholdDipStr);
  }

  private void setTouchSlopSquare(int touchSlop) {
    this.mTouchSlopSquare = touchSlop * touchSlop;
  }

  public boolean currentGestureStartedInRegion(final Region excludedRegion) {
    MotionEvent motionEvent = this.mDetectedMotionEvent;
    if (motionEvent == null) {
      return true;
    }
    Objects.requireNonNull(excludedRegion);
    return allMatch(
        motionEvent,
        new BiFunction() { // from class:
                           // android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda7
          @Override // java.util.function.BiFunction
          public final Object apply(Object obj, Object obj2) {
            return Boolean.valueOf(
                Region.this.contains(((Integer) obj).intValue(), ((Integer) obj2).intValue()));
          }
        });
  }

  protected String actionToString(int action) {
    switch (action) {
      case 0:
        return "Down";
      case 1:
        return "Up";
      case 2:
        return "Move";
      case 3:
        return "Cancel";
      case 4:
        return "Outside";
      case 5:
        return "Pointer Down";
      case 6:
        return "Pointer Up";
      default:
        return "";
    }
  }

  public interface GestureListener {
    default void onDetecting() {}

    default void onDetected() {}

    default void onCommitted(int gestureFrom) {}

    default void onCanceled() {}

    default void onEnd() {}
  }

  public void setDebugNoise(boolean enable) {
    this.DEBUG_NOISE = enable;
  }

  public void setDebug(boolean enable) {
    this.DEBUG = enable;
  }
}
