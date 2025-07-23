package android.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Debug;
import android.os.SystemProperties;
import android.util.Slog;
import android.view.TwoFingerSwipeGestureDetector;
import android.view.WindowManagerPolicyConstants;
import com.samsung.android.core.AppJumpBlockTool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes4.dex */
public class TwoFingerSwipeGestureDetector implements WindowManagerPolicyConstants.PointerEventListener {
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
    public static final int UP = 2;
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

    public interface GestureListener {
        default void onCanceled() {
        }

        default void onCommitted(int i) {
        }

        default void onDetected() {
        }

        default void onDetecting() {
        }

        default void onEnd() {
        }
    }

    public @interface GestureState {
    }

    public @interface PositionDirection {
    }

    private int gestureFrom(int i, int i2) {
        if ((i & 1) != 0 && i2 == 4) {
            return 1;
        }
        if ((i & 4) == 0 || i2 != 1) {
            return ((i & 8) == 0 || i2 != 2) ? -1 : 4;
        }
        return 3;
    }

    static class Tuner {
        public static final String TIME_THRESHOLD = getSystemPropertiesKey("time_threshold");
        public static final String MINUS_DISTANCE_RATIO = getSystemPropertiesKey("minus_distance_ratio");
        public static final String PLUS_DISTANCE_RATIO = getSystemPropertiesKey("plus_distance_ratio");
        public static final String EASY_START_THRESHOLD_DIP = getSystemPropertiesKey("easy_start_threshold_dip");
        public static final String SIDE_THRESHOLD_DIP = getSystemPropertiesKey("side_threshold_dip");

        Tuner() {
        }

        static String getSystemPropertiesKey(String str) {
            return "mw.split.gesture.tune." + str;
        }
    }

    public void setTouchSlopForTest(float f) {
        this.mTouchSlopDip = f;
        setTouchSlopSquare((int) ((this.mDensity * f) + 0.5f));
    }

    private TwoFingerSwipeGestureDetector(Context context, String str) {
        this.mThresholds = new int[3];
        this.mThresholdsForOneFinger = new int[3];
        this.mEasyThresholds = new int[3];
        this.mTimeThreshold = SystemProperties.getInt(Tuner.TIME_THRESHOLD, 200);
        this.mMinusDistanceRatio = 0.5f;
        this.mPlusDistanceRatio = 0.8f;
        this.mDisplayBounds = new Rect();
        this.mExcludeRegion = new Region();
        this.mListeners = new ArrayList();
        this.mDownEnabled = true;
        this.mSideEnabled = true;
        this.mState = 5;
        this.DEBUG = false;
        this.DEBUG_NOISE = false;
        this.TAG = "TwoFingerSwipeGestureDetector[" + str + NavigationBarInflaterView.SIZE_MOD_END;
        this.mTouchSlopDip = 24.0f;
        if (context == null) {
            this.mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            this.mMinimumFlingVelocity = ViewConfiguration.getDoubleTapMinTime();
        } else {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
            this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        }
    }

    public TwoFingerSwipeGestureDetector(Context context, GestureListener gestureListener, String str) {
        this(context, str);
        this.mListeners.add(gestureListener);
    }

    public TwoFingerSwipeGestureDetector(Context context, Function<TwoFingerSwipeGestureDetector, GestureListener> function, String str) {
        this(context, str);
        this.mListeners.add(function.apply(this));
    }

    public void setGestureExclusionRegion(Region region) {
        if (this.DEBUG) {
            Slog.d(this.TAG, "setGestureExclusionRegion. " + region);
        }
        this.mExcludeRegion.set(region);
    }

    @Override // android.view.WindowManagerPolicyConstants.PointerEventListener
    public void onPointerEvent(MotionEvent motionEvent) {
        onInputEvent(motionEvent);
    }

    public void onInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            final MotionEvent motionEvent = (MotionEvent) inputEvent;
            int actionMasked = motionEvent.getActionMasked();
            int pointerCount = motionEvent.getPointerCount();
            if (this.DEBUG && this.DEBUG_NOISE) {
                Slog.d(this.TAG, actionToString(actionMasked) + " pointerCount=" + pointerCount);
            }
            if (actionMasked == 0) {
                detecting();
                if (this.mVelocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                }
                this.mPivotX = motionEvent.getX();
                this.mPivotY = motionEvent.getY();
                this.mPivotId = motionEvent.getPointerId(0);
                this.mPivotTime = motionEvent.getEventTime();
                return;
            }
            if (this.mState == 5) {
                return;
            }
            if (3 == actionMasked) {
                cancel();
                return;
            }
            if (!isValidPointerCount(pointerCount)) {
                if (this.DEBUG) {
                    Slog.d(this.TAG, "invalid pointer count=" + motionEvent.getPointerCount());
                }
                cancel();
                return;
            }
            this.mVelocityTracker.addMovement(motionEvent);
            if (5 == actionMasked) {
                if (this.mState == 1) {
                    if (isOverTouchTime(this.mPivotTime, motionEvent.getEventTime())) {
                        if (this.DEBUG) {
                            Slog.d(this.TAG, String.format("prev=%s cur=%s diff=%s timeThreshold=%f", Long.valueOf(this.mPivotTime), Long.valueOf(motionEvent.getEventTime()), Long.valueOf(motionEvent.getEventTime() - this.mPivotTime), Float.valueOf(this.mTimeThreshold)));
                        }
                        cancel();
                        return;
                    }
                    if (motionEvent.findPointerIndex(this.mPivotId) < 0) {
                        if (this.DEBUG) {
                            Slog.d(this.TAG, "missing first touch.");
                        }
                        cancel();
                        return;
                    }
                    if (allMatch(motionEvent, new BiFunction() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda5
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            Boolean lambda$onInputEvent$0;
                            lambda$onInputEvent$0 = TwoFingerSwipeGestureDetector.this.lambda$onInputEvent$0((Integer) obj, (Integer) obj2);
                            return lambda$onInputEvent$0;
                        }
                    })) {
                        if (this.DEBUG) {
                            Slog.d(this.TAG, String.format("ACTION_POINTER_DOWN. any pointer doesn't in thresholds. %s %s %s", getXYString(Float.valueOf(motionEvent.getX(0)), Float.valueOf(motionEvent.getY(0))), getXYString(Float.valueOf(motionEvent.getX(1)), Float.valueOf(motionEvent.getY(1))), Arrays.toString(this.mThresholdsForOneFinger)));
                        }
                        cancel();
                        return;
                    }
                    Objects.requireNonNull(motionEvent);
                    this.mStartCenterX = getCenter(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                        }
                    });
                    Objects.requireNonNull(motionEvent);
                    this.mStartCenterY = getCenter(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                        }
                    });
                    if (allMatch(motionEvent, new BiFunction() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda8
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            return Boolean.valueOf(TwoFingerSwipeGestureDetector.this.excludeRegionContains(((Integer) obj).intValue(), ((Integer) obj2).intValue()));
                        }
                    })) {
                        if (this.DEBUG) {
                            Slog.d(this.TAG, "started on gesture exclude region.");
                        }
                        cancel();
                        return;
                    }
                    int position = getPosition((int) this.mStartCenterX, (int) this.mStartCenterY, this.mThresholds);
                    this.mStartPosition = position;
                    if (position == 0) {
                        if (this.DEBUG) {
                            Slog.d(this.TAG, "position invalid. " + getXYString(Float.valueOf(this.mStartCenterX), Float.valueOf(this.mStartCenterY)));
                        }
                        cancel();
                        return;
                    }
                    MotionEvent motionEvent2 = this.mDetectedMotionEvent;
                    if (motionEvent2 != null) {
                        motionEvent2.recycle();
                    }
                    this.mDetectedMotionEvent = MotionEvent.obtain(motionEvent);
                    Objects.requireNonNull(motionEvent);
                    float distanceSquareSum = getDistanceSquareSum(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                        }
                    });
                    Objects.requireNonNull(motionEvent);
                    this.mInitialDistance = distanceSquareSum + getDistanceSquareSum(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                        }
                    });
                    detected();
                    if (this.DEBUG) {
                        Slog.d(this.TAG, String.format("detected reason. events=%s, display=%s side=%b down=%b threshold=%s", motionEvent, this.mDisplayBounds, Boolean.valueOf(this.mSideEnabled), Boolean.valueOf(this.mDownEnabled), Arrays.toString(this.mThresholds)));
                        return;
                    }
                    return;
                }
                return;
            }
            if (2 == actionMasked) {
                if (this.mState == 2) {
                    Objects.requireNonNull(motionEvent);
                    float center = getCenter(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                        }
                    });
                    Objects.requireNonNull(motionEvent);
                    float center2 = getCenter(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                        }
                    });
                    if (isOverThreshold(center, center2, this.mEasyThresholds, this.mStartPosition).booleanValue()) {
                        commitIfPossible(center, center2);
                        return;
                    }
                    return;
                }
                return;
            }
            if (6 == actionMasked) {
                if (this.mState == 2) {
                    if (!isTwoFingerVelocitiesSameDirection(motionEvent, pointerCount)) {
                        cancel();
                        return;
                    }
                    Objects.requireNonNull(motionEvent);
                    float distanceSquareSum2 = getDistanceSquareSum(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                        }
                    });
                    Objects.requireNonNull(motionEvent);
                    float distanceSquareSum3 = distanceSquareSum2 + getDistanceSquareSum(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                        }
                    });
                    if (!this.mUseThreeFinger && isTwoFingerDistanceFartherThanBefore(this.mInitialDistance, distanceSquareSum3)) {
                        if (this.DEBUG) {
                            Slog.d(this.TAG, String.format("ACTION_UP. Distance between finger is farther than before. distance ratio=%f touch slop ratio=%f ", Float.valueOf(distanceSquareSum3 / this.mInitialDistance), Float.valueOf((distanceSquareSum3 - this.mInitialDistance) / this.mTouchSlopSquare)));
                        }
                        cancel();
                        return;
                    }
                    int actionIndex = motionEvent.getActionIndex();
                    for (int i = 0; i < pointerCount; i++) {
                        if (i != actionIndex) {
                            this.mPivotX = motionEvent.getX(i);
                            this.mPivotY = motionEvent.getY(i);
                        }
                    }
                    this.mPivotTime = motionEvent.getEventTime();
                    Objects.requireNonNull(motionEvent);
                    this.mEndCenterX = getCenter(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                        }
                    });
                    Objects.requireNonNull(motionEvent);
                    this.mEndCenterY = getCenter(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                        }
                    });
                    return;
                }
                return;
            }
            if (1 == actionMasked) {
                if (this.mState == 2) {
                    if (isOverTouchTime(this.mPivotTime, motionEvent.getEventTime())) {
                        if (this.DEBUG) {
                            Slog.d(this.TAG, String.format("ACTION_UP. prev=%s cur=%s timeThreshold=%f", Long.valueOf(this.mPivotTime), Long.valueOf(motionEvent.getEventTime()), Float.valueOf(this.mTimeThreshold)));
                        }
                        cancel();
                        return;
                    }
                    Objects.requireNonNull(motionEvent);
                    float center3 = getCenter(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getX(((Integer) obj).intValue()));
                        }
                    });
                    Objects.requireNonNull(motionEvent);
                    float center4 = getCenter(motionEvent, new Function() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return Float.valueOf(MotionEvent.this.getY(((Integer) obj).intValue()));
                        }
                    });
                    if (!isOverThreshold(center3, center4, this.mThresholds, this.mStartPosition).booleanValue()) {
                        if (this.DEBUG) {
                            Slog.d(this.TAG, "ACTION_UP. didn't over threshold. sp=" + this.mStartPosition + " cur=" + getXYString(Float.valueOf(center3), Float.valueOf(center4)) + " thresholds=" + Arrays.toString(this.mThresholds));
                        }
                        cancel();
                        return;
                    }
                    if (commitIfPossible(this.mEndCenterX, this.mEndCenterY)) {
                        return;
                    }
                    cancel();
                    return;
                }
                end();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$onInputEvent$0(Integer num, Integer num2) {
        return Boolean.valueOf(getPosition(num.intValue(), num2.intValue(), this.mThresholdsForOneFinger) == 0);
    }

    private boolean isValidPointerCount(int i) {
        return this.mUseThreeFinger ? i <= 3 : i <= 2;
    }

    private boolean isOverTouchTime(long j, long j2) {
        return ((float) (j2 - j)) > this.mTimeThreshold;
    }

    protected <T, U> String getXYString(T t, U u) {
        return NavigationBarInflaterView.KEY_CODE_START + t + ", " + u + NavigationBarInflaterView.KEY_CODE_END;
    }

    private boolean isTwoFingerDistanceFartherThanBefore(float f, float f2) {
        float f3 = f2 / f;
        return f3 > this.mPlusDistanceRatio + 1.0f || f3 < 1.0f - this.mMinusDistanceRatio;
    }

    protected boolean commitIfPossible(float f, float f2) {
        int gestureFrom = gestureFrom(this.mStartPosition, getDirection(this.mStartCenterX, this.mStartCenterY, f, f2));
        if (gestureFrom == -1) {
            if (!this.DEBUG) {
                return false;
            }
            Slog.d(this.TAG, "ActionMOVE: gestureFrom not found.");
            return false;
        }
        committed(gestureFrom);
        return true;
    }

    private boolean isTwoFingerVelocitiesSameDirection(MotionEvent motionEvent, int i) {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        float xVelocity = this.mVelocityTracker.getXVelocity(pointerId);
        float yVelocity = this.mVelocityTracker.getYVelocity(pointerId);
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 != actionIndex) {
                int pointerId2 = motionEvent.getPointerId(i2);
                float xVelocity2 = this.mVelocityTracker.getXVelocity(pointerId2);
                float yVelocity2 = this.mVelocityTracker.getYVelocity(pointerId2);
                if ((xVelocity * xVelocity2) + (yVelocity * yVelocity2) < 0.0f) {
                    this.mVelocityTracker.clear();
                    if (this.DEBUG) {
                        Slog.d(this.TAG, "dot product is negative. id1=(" + xVelocity + "," + yVelocity + ") id2=(" + xVelocity2 + "," + yVelocity2 + NavigationBarInflaterView.KEY_CODE_END);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isInThreshold(int i, int i2, int i3, int i4) {
        if (!this.mDisplayBounds.isEmpty() && i3 >= 0) {
            return i4 != 1 ? i4 != 4 ? i4 == 8 && this.mDisplayBounds.bottom - i3 < i2 : this.mDisplayBounds.right - i3 < i : this.mDisplayBounds.left + i3 > i;
        }
        return false;
    }

    protected Boolean isOverThreshold(float f, float f2, int[] iArr, int i) {
        if (this.mDisplayBounds.isEmpty()) {
            return false;
        }
        boolean z = (i & 1) != 0 && ((float) (this.mDisplayBounds.left + iArr[0])) < f;
        if ((i & 4) != 0) {
            z |= ((float) (this.mDisplayBounds.right - iArr[1])) > f;
        }
        if ((i & 8) != 0) {
            z |= ((float) (this.mDisplayBounds.bottom - iArr[2])) > f2;
        }
        return Boolean.valueOf(z);
    }

    private boolean isStartPositionEnabled(int i) {
        if (i == 1 || i == 4) {
            return this.mSideEnabled;
        }
        if (i != 8) {
            return false;
        }
        return this.mDownEnabled;
    }

    protected boolean allMatch(MotionEvent motionEvent, BiFunction<Integer, Integer, Boolean> biFunction) {
        int pointerCount = motionEvent.getPointerCount();
        boolean z = true;
        for (int i = 0; i < pointerCount; i++) {
            z &= biFunction.apply(Integer.valueOf((int) motionEvent.getX(i)), Integer.valueOf((int) motionEvent.getY(i))).booleanValue();
        }
        return z;
    }

    protected boolean excludeRegionContains(float f, float f2) {
        return this.mExcludeRegion.contains((int) f, (int) f2);
    }

    protected void detecting() {
        if (this.DEBUG) {
            Slog.d(this.TAG, "detecting");
        }
        this.mState = 1;
        this.mListeners.forEach(new Consumer() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TwoFingerSwipeGestureDetector.GestureListener) obj).onDetecting();
            }
        });
    }

    protected void detected() {
        if (this.DEBUG) {
            Slog.d(this.TAG, "detected");
        }
        this.mState = 2;
        this.mListeners.forEach(new Consumer() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TwoFingerSwipeGestureDetector.GestureListener) obj).onDetected();
            }
        });
    }

    private void committed(final int i) {
        if (this.DEBUG) {
            Slog.d(this.TAG, "committed " + Debug.getCallers(2));
        }
        this.mState = 3;
        this.mListeners.forEach(new Consumer() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TwoFingerSwipeGestureDetector.GestureListener) obj).onCommitted(i);
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
            Slog.d(this.TAG, "canceled from " + Debug.getCaller());
        }
        this.mState = 4;
        this.mListeners.forEach(new Consumer() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((TwoFingerSwipeGestureDetector.GestureListener) obj).onCanceled();
            }
        });
        end();
    }

    protected void end() {
        if (this.DEBUG) {
            Slog.d(this.TAG, "end");
        }
        this.mState = 5;
        this.mListeners.forEach(new Consumer() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda4
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

    private float getDistanceSquareSum(MotionEvent motionEvent, Function<Integer, Float> function) {
        int pointerCount = motionEvent.getPointerCount();
        float f = 0.0f;
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (i2 >= pointerCount) {
                return f;
            }
            float floatValue = function.apply(Integer.valueOf(i)).floatValue() - function.apply(Integer.valueOf(i2)).floatValue();
            f += floatValue * floatValue;
            i = i2;
        }
    }

    protected float getCenter(MotionEvent motionEvent, Function<Integer, Float> function) {
        int pointerCount = motionEvent.getPointerCount();
        float f = 0.0f;
        for (int i = 0; i < pointerCount; i++) {
            f += function.apply(Integer.valueOf(i)).floatValue();
        }
        return f / pointerCount;
    }

    public int getPosition(int i, int i2, int[] iArr) {
        int i3 = 0;
        if (this.mDisplayBounds.isEmpty()) {
            Slog.e(this.TAG, "display bounds is empty.");
            return 0;
        }
        if (isStartPositionEnabled(1) && isInThreshold(i, i2, iArr[0], 1)) {
            i3 = 1;
        }
        if (isStartPositionEnabled(4) && isInThreshold(i, i2, iArr[1], 4)) {
            i3 |= 4;
        }
        return (isStartPositionEnabled(8) && isInThreshold(i, i2, iArr[2], 8)) ? i3 | 8 : i3;
    }

    private int getDirection(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        return Math.abs(f5) > Math.abs(f6) ? f5 < 0.0f ? 1 : 4 : f6 < 0.0f ? 2 : 8;
    }

    public void init(Rect rect, float f, int i) {
        setDensity(f);
        setDisplayBounds(rect);
        setGestureSearchSide(i);
        build();
    }

    public void init(Rect rect, float f, int i, boolean z) {
        setDensity(f);
        setDisplayBounds(rect);
        setGestureSearchSide(i);
        useThreeFinger(z);
        build();
    }

    private void useThreeFinger(boolean z) {
        this.mUseThreeFinger = z;
    }

    protected void build() {
        if (this.DEBUG) {
            Slog.d(this.TAG, "updateDipResources. density=" + this.mDensity);
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
        this.mMinusDistanceRatio = Float.parseFloat(SystemProperties.get(Tuner.MINUS_DISTANCE_RATIO, String.valueOf(0.5f)));
        this.mPlusDistanceRatio = Float.parseFloat(SystemProperties.get(Tuner.PLUS_DISTANCE_RATIO, String.valueOf(0.8f)));
    }

    private void setDensity(float f) {
        this.mDensity = f;
    }

    public void setGestureSearchSide(int i) {
        this.mSideEnabled = (i & 5) == 5;
        this.mDownEnabled = (i & 8) == 8;
    }

    public void setDisplayBounds(Rect rect) {
        if (this.DEBUG) {
            Slog.d(this.TAG, "setDisplayBounds. displayBounds=" + rect);
        }
        this.mDisplayBounds.set(rect);
    }

    protected void initThresholds(int[] iArr, int i, int i2, int i3) {
        Arrays.fill(iArr, Math.max(this.mDisplayBounds.width(), this.mDisplayBounds.height()));
        if (i >= 0) {
            iArr[0] = i;
        }
        if (i2 >= 0) {
            iArr[1] = i2;
        }
        if (i3 >= 0) {
            iArr[2] = i3;
        }
    }

    protected float getEasyStartThresholdDip() {
        return Float.parseFloat(SystemProperties.get(Tuner.EASY_START_THRESHOLD_DIP, "-1"));
    }

    protected float getSideThresholdDip() {
        return Float.parseFloat(SystemProperties.get(Tuner.SIDE_THRESHOLD_DIP, Float.toString(SIDE_THRESHOLD_DIP)));
    }

    private void setTouchSlopSquare(int i) {
        this.mTouchSlopSquare = i * i;
    }

    public boolean currentGestureStartedInRegion(final Region region) {
        MotionEvent motionEvent = this.mDetectedMotionEvent;
        if (motionEvent == null) {
            return true;
        }
        Objects.requireNonNull(region);
        return allMatch(motionEvent, new BiFunction() { // from class: android.view.TwoFingerSwipeGestureDetector$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Boolean.valueOf(Region.this.contains(((Integer) obj).intValue(), ((Integer) obj2).intValue()));
            }
        });
    }

    protected String actionToString(int i) {
        switch (i) {
            case 0:
                return "Down";
            case 1:
                return "Up";
            case 2:
                return "Move";
            case 3:
                return AppJumpBlockTool.RESULT_CANCEL;
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

    public void setDebugNoise(boolean z) {
        this.DEBUG_NOISE = z;
    }

    public void setDebug(boolean z) {
        this.DEBUG = z;
    }
}
