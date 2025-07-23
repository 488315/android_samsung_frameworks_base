package android.widget;

import android.content.Context;
import android.hardware.scontext.SContextConstants;
import android.os.SystemProperties;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.samsung.android.os.SemPerfManager;
import com.samsung.android.rune.CoreRune;
import java.lang.reflect.Array;

/* loaded from: classes5.dex */
public class OverScroller {
    private static final int DEFAULT_DURATION = 250;
    private static final boolean ENABLE_STB_ANIMATION = SystemProperties.getBoolean("debug.stb.animation", true);
    private static final int FLING_MODE = 1;
    private static final float FRAME_LATENCY_LIMIT = 16.66f;
    private static final int SCROLL_MODE = 0;
    private static final int X_INDEX = 0;
    private static final int Y_INDEX = 1;
    private static long sIntervalTime;
    private final boolean mFlywheel;
    private Interpolator mInterpolator;
    private int mMode;
    private final SplineOverScroller mScrollerX;
    private final SplineOverScroller mScrollerY;

    public OverScroller(Context context) {
        this(context, null);
    }

    public OverScroller(Context context, Interpolator interpolator) {
        this(context, interpolator, true);
    }

    public OverScroller(Context context, Interpolator interpolator, boolean z) {
        if (interpolator == null) {
            this.mInterpolator = new Scroller.ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
        this.mFlywheel = z;
        this.mScrollerX = new SplineOverScroller(context);
        this.mScrollerY = new SplineOverScroller(context);
    }

    @Deprecated
    public OverScroller(Context context, Interpolator interpolator, float f, float f2) {
        this(context, interpolator, true);
    }

    @Deprecated
    public OverScroller(Context context, Interpolator interpolator, float f, float f2, boolean z) {
        this(context, interpolator, z);
    }

    void setInterpolator(Interpolator interpolator) {
        if (interpolator == null) {
            this.mInterpolator = new Scroller.ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
    }

    public final void setFriction(float f) {
        this.mScrollerX.setFriction(f);
        this.mScrollerY.setFriction(f);
    }

    public final boolean isFinished() {
        return this.mScrollerX.mFinished && this.mScrollerY.mFinished;
    }

    public final void forceFinished(boolean z) {
        SplineOverScroller splineOverScroller = this.mScrollerX;
        this.mScrollerY.mFinished = z;
        splineOverScroller.mFinished = z;
    }

    public final int getCurrX() {
        return this.mScrollerX.mCurrentPosition;
    }

    public final int getCurrY() {
        return this.mScrollerY.mCurrentPosition;
    }

    public float getCurrVelocity() {
        return (float) Math.hypot(this.mScrollerX.mCurrVelocity, this.mScrollerY.mCurrVelocity);
    }

    public final int getStartX() {
        return this.mScrollerX.mStart;
    }

    public final int getStartY() {
        return this.mScrollerY.mStart;
    }

    public final int getFinalX() {
        return this.mScrollerX.mFinal;
    }

    public final int getFinalY() {
        return this.mScrollerY.mFinal;
    }

    public final int getDuration() {
        return Math.max(this.mScrollerX.mDuration, this.mScrollerY.mDuration);
    }

    public void extendDuration(int i) {
        this.mScrollerX.extendDuration(i);
        this.mScrollerY.extendDuration(i);
    }

    public void setFinalX(int i) {
        this.mScrollerX.setFinalPosition(i);
    }

    public void setFinalY(int i) {
        this.mScrollerY.setFinalPosition(i);
    }

    public boolean computeScrollOffset() {
        if (isFinished()) {
            return false;
        }
        int i = this.mMode;
        if (i == 0) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mScrollerX.mStartTime;
            int i2 = this.mScrollerX.mDuration;
            if (currentAnimationTimeMillis < i2) {
                float f = i2;
                float interpolation = this.mInterpolator.getInterpolation(currentAnimationTimeMillis / f);
                float interpolation2 = this.mInterpolator.getInterpolation((currentAnimationTimeMillis - 1) / f);
                this.mScrollerX.updateScroll(interpolation, interpolation2);
                this.mScrollerY.updateScroll(interpolation, interpolation2);
            } else {
                abortAnimation();
            }
        } else if (i == 1) {
            if (!this.mScrollerX.mFinished && !this.mScrollerX.update() && !this.mScrollerX.continueWhenFinished()) {
                this.mScrollerX.finish();
            }
            if (!this.mScrollerY.mFinished && !this.mScrollerY.update() && !this.mScrollerY.continueWhenFinished()) {
                this.mScrollerY.finish();
            }
        }
        return true;
    }

    public void startScroll(int i, int i2, int i3, int i4) {
        startScroll(i, i2, i3, i4, 250);
    }

    public void startScroll(int i, int i2, int i3, int i4, int i5) {
        this.mMode = 0;
        this.mScrollerX.startScroll(i, i3, i5);
        this.mScrollerY.startScroll(i2, i4, i5);
    }

    public boolean springBack(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mMode = 1;
        return this.mScrollerX.springback(i, i3, i4) || this.mScrollerY.springback(i2, i5, i6);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        fling(i, i2, i3, i4, i5, i6, i7, i8, 0, 0);
    }

    protected void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        fling(i, i2, i3, i4, i5, i6, i7, i8, 0, 0, z);
    }

    public void hidden_fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        fling(i, i2, i3, i4, i5, i6, i7, i8, 0, 0, z);
    }

    public void hidden_fling(int i, int i2, boolean z, float f) {
        fling(0, 0, i, i2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0, z, f);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        if (this.mFlywheel && !isFinished()) {
            float f = this.mScrollerX.mCurrVelocity;
            float f2 = this.mScrollerY.mCurrVelocity;
            float f3 = i3;
            if (Math.signum(f3) == Math.signum(f)) {
                float f4 = i4;
                if (Math.signum(f4) == Math.signum(f2)) {
                    i3 = (int) (f3 + f);
                    i4 = (int) (f4 + f2);
                }
            }
        }
        this.mMode = 1;
        this.mScrollerX.fling(i, i3, i5, i6, i9);
        this.mScrollerY.fling(i2, i4, i7, i8, i10);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, boolean z) {
        if (z) {
            sIntervalTime = 0L;
        }
        if (this.mFlywheel && !isFinished() && !z) {
            float f = this.mScrollerX.mCurrVelocity;
            float f2 = this.mScrollerY.mCurrVelocity;
            float f3 = i3;
            if (Math.signum(f3) == Math.signum(f)) {
                float f4 = i4;
                if (Math.signum(f4) == Math.signum(f2)) {
                    i3 = (int) (f3 + f);
                    i4 = (int) (f4 + f2);
                }
            }
        }
        this.mMode = 1;
        this.mScrollerX.fling(i, i3, i5, i6, i9);
        this.mScrollerY.fling(i2, i4, i7, i8, i10);
    }

    public void fling(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, boolean z, float f) {
        if (this.mFlywheel && !isFinished()) {
            float f2 = this.mScrollerX.mCurrVelocity;
            float f3 = this.mScrollerY.mCurrVelocity;
            float f4 = i3;
            if (Math.signum(f4) == Math.signum(f2)) {
                float f5 = i4;
                if (Math.signum(f5) == Math.signum(f3)) {
                    i3 = (int) (f4 + f2);
                    i4 = (int) (f5 + f3);
                }
            }
        }
        int i11 = i3;
        if (z) {
            float f6 = 0.0f;
            if (f >= 0.0f) {
                f6 = FRAME_LATENCY_LIMIT;
                if (f <= FRAME_LATENCY_LIMIT) {
                    f6 = f;
                }
            }
            sIntervalTime = (long) f6;
        } else {
            sIntervalTime = 0L;
        }
        this.mMode = 1;
        this.mScrollerX.fling(i, i11, i5, i6, i9);
        this.mScrollerY.fling(i2, i4, i7, i8, i10);
    }

    public void notifyHorizontalEdgeReached(int i, int i2, int i3) {
        this.mScrollerX.notifyEdgeReached(i, i2, i3);
    }

    public void notifyVerticalEdgeReached(int i, int i2, int i3) {
        this.mScrollerY.notifyEdgeReached(i, i2, i3);
    }

    public boolean isOverScrolled() {
        if (this.mScrollerX.mFinished || this.mScrollerX.mState == 0) {
            return (this.mScrollerY.mFinished || this.mScrollerY.mState == 0) ? false : true;
        }
        return true;
    }

    public void abortAnimation() {
        this.mScrollerX.finish();
        this.mScrollerY.finish();
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - Math.min(this.mScrollerX.mStartTime, this.mScrollerY.mStartTime));
    }

    public boolean isScrollingInDirection(float f, float f2) {
        return !isFinished() && Math.signum(f) == Math.signum((float) (this.mScrollerX.mFinal - this.mScrollerX.mStart)) && Math.signum(f2) == Math.signum((float) (this.mScrollerY.mFinal - this.mScrollerY.mStart));
    }

    @Deprecated
    public void semSetSmoothScrollEnabled(boolean z) {
        this.mScrollerX.setMode(z ? 1 : 0);
        this.mScrollerY.setMode(z ? 1 : 0);
    }

    double getSplineFlingDistance(int i) {
        return this.mScrollerY.getSplineFlingDistance(i);
    }

    static class SplineOverScroller {
        private static final int BALLISTIC = 2;
        private static final int CUBIC = 1;
        private static float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
        private static final int DEFAULT_MODE = 1;
        private static final float DISTANCE_M1 = 3.0f;
        private static final float DISTANCE_M2 = 1.25f;
        private static final float DURATION_M1 = 2.7f;
        private static final float DURATION_M2 = 1.45f;
        private static final float END_TENSION = 1.0f;
        private static final float GRAVITY = 2000.0f;
        private static final long HIGHER_TIME_GAP_COMPENSATION = 1;
        private static final long HIGHER_TIME_GAP_MARGIN = 1;
        private static float INFLEXION = 0.0f;
        private static final float[] INFLEXIONS;
        private static final long LOWER_TIME_GAP_COMPENSATION = 1;
        private static final long LOWER_TIME_GAP_MARGIN = 1;
        private static final int MARGIN_COMPENSATION_STARTING_COUNT = 30;
        private static final int MINIMUM_BOOSTED_FLING_VELOCITY_NEGATIVE = -800;
        private static final int MINIMUM_BOOSTED_FLING_VELOCITY_POSITIVE = 800;
        private static final int NB_SAMPLES = 100;
        public static final int ORIGINAL_MODE = 0;
        public static final int SMOOTH_MODE = 1;
        private static final int SPLINE = 0;
        private static float[] SPLINE_POSITION = null;
        private static final float[][] SPLINE_POSITIONS;
        private static float[] SPLINE_TIME = null;
        private static final float[][] SPLINE_TIMES;
        private static final float START_TENSION = 0.5f;
        private static boolean sIsSmoothFlingEnabled;
        private static boolean sUseRegulateCurrentTimeInterval;
        private float mCurrVelocity;
        private int mCurrentPosition;
        private float mDeceleration;
        private int mDuration;
        private int mFinal;
        private int mMaximumVelocity;
        private int mOver;
        private float mPhysicalCoeff;
        private int mSplineDistance;
        private int mSplineDuration;
        private int mStart;
        private long mStartTime;
        private int mVelocity;
        private int mSTBIndex = -1;
        private boolean mFlingSTBFlag = false;
        private float mFlingFriction = ViewConfiguration.getScrollFriction();
        private int mState = 0;
        private int mUpdateCount = 0;
        private long mPrevTime = 0;
        private long mPrevTimeGap = 0;
        private boolean mIsDVFSBoosting = false;
        private boolean mFinished = true;

        private static float getDeceleration(int i) {
            if (i > 0) {
                return -2000.0f;
            }
            return GRAVITY;
        }

        static {
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            float f11;
            float f12;
            int i = 2;
            float[] fArr = {0.35f, 0.26f};
            INFLEXIONS = fArr;
            INFLEXION = fArr[1];
            int i2 = 0;
            float[][] fArr2 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 101);
            SPLINE_POSITIONS = fArr2;
            float[][] fArr3 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 101);
            SPLINE_TIMES = fArr3;
            SPLINE_POSITION = fArr2[1];
            SPLINE_TIME = fArr3[1];
            sIsSmoothFlingEnabled = true;
            sUseRegulateCurrentTimeInterval = !CoreRune.FW_DVRR_TOOLKIT_POLICY;
            int i3 = 0;
            while (i3 < i) {
                float f13 = INFLEXIONS[i3];
                float f14 = 0.5f;
                float f15 = f13 * 0.5f;
                float f16 = 1.0f;
                float f17 = 1.0f - ((1.0f - f13) * 1.0f);
                float f18 = 0.0f;
                int i4 = i2;
                float f19 = 0.0f;
                while (i4 < 100) {
                    float f20 = i4 / 100.0f;
                    float f21 = f16;
                    while (true) {
                        f = 2.0f;
                        f2 = ((f21 - f18) / 2.0f) + f18;
                        f3 = 3.0f;
                        f4 = f16 - f2;
                        f5 = f2 * 3.0f * f4;
                        f6 = f2 * f2 * f2;
                        float f22 = (((f4 * f15) + (f2 * f17)) * f5) + f6;
                        f7 = f14;
                        if (Math.abs(f22 - f20) < 1.0E-5d) {
                            break;
                        }
                        float f23 = f16;
                        if (f22 > f20) {
                            f21 = f2;
                        } else {
                            f18 = f2;
                        }
                        f16 = f23;
                        f14 = f7;
                    }
                    SPLINE_POSITIONS[i3][i4] = (f5 * ((f4 * f7) + f2)) + f6;
                    float f24 = f16;
                    while (true) {
                        f8 = ((f24 - f19) / f) + f19;
                        f9 = f16 - f8;
                        f10 = f8 * f3 * f9;
                        f11 = f8 * f8 * f8;
                        float f25 = (((f9 * f7) + f8) * f10) + f11;
                        f12 = f16;
                        if (Math.abs(f25 - f20) < 1.0E-5d) {
                            break;
                        }
                        if (f25 > f20) {
                            f24 = f8;
                        } else {
                            f19 = f8;
                        }
                        f16 = f12;
                        f = 2.0f;
                        f3 = 3.0f;
                    }
                    SPLINE_TIMES[i3][i4] = (f10 * ((f9 * f15) + (f8 * f17))) + f11;
                    i4++;
                    f16 = f12;
                    f14 = f7;
                }
                float f26 = f16;
                float[] fArr4 = SPLINE_POSITIONS[i3];
                SPLINE_TIMES[i3][100] = f26;
                fArr4[100] = f26;
                i3++;
                i = 2;
                i2 = 0;
            }
        }

        public void setMode(int i) {
            if (i == 0 || i == 1) {
                sIsSmoothFlingEnabled = i == 1;
                if (!CoreRune.FW_DVRR_TOOLKIT_POLICY) {
                    sUseRegulateCurrentTimeInterval = i == 1;
                }
                INFLEXION = INFLEXIONS[i];
                SPLINE_POSITION = SPLINE_POSITIONS[i];
                SPLINE_TIME = SPLINE_TIMES[i];
            }
        }

        void setFriction(float f) {
            this.mFlingFriction = f;
        }

        SplineOverScroller(Context context) {
            this.mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
            if (sIsSmoothFlingEnabled) {
                this.mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
            }
        }

        void setSTBIndex(int i) {
            this.mSTBIndex = i;
        }

        void updateScroll(float f, float f2) {
            int i = this.mFinal;
            int i2 = this.mStart;
            float f3 = i - i2;
            this.mCurrentPosition = i2 + Math.round(f * f3);
            this.mCurrVelocity = (f - f2) * 1000.0f * f3;
        }

        private void adjustDuration(int i, int i2, int i3) {
            float abs = Math.abs((i3 - i) / (i2 - i));
            int i4 = (int) (abs * 100.0f);
            if (i4 < 100) {
                float f = i4 / 100.0f;
                int i5 = i4 + 1;
                float[] fArr = SPLINE_TIME;
                float f2 = fArr[i4];
                this.mDuration = (int) (this.mDuration * (f2 + (((abs - f) / ((i5 / 100.0f) - f)) * (fArr[i5] - f2))));
            }
        }

        void startScroll(int i, int i2, int i3) {
            this.mFinished = false;
            this.mStart = i;
            this.mCurrentPosition = i;
            this.mFinal = i + i2;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = i3;
            this.mDeceleration = 0.0f;
            this.mVelocity = 0;
        }

        void finish() {
            if (this.mIsDVFSBoosting) {
                SemPerfManager.onSmoothScrollEvent(false);
                this.mIsDVFSBoosting = false;
            }
            this.mCurrentPosition = this.mFinal;
            this.mFinished = true;
        }

        void setFinalPosition(int i) {
            this.mFinal = i;
            this.mSplineDistance = i - this.mStart;
            this.mFinished = false;
        }

        void extendDuration(int i) {
            int currentAnimationTimeMillis = ((int) (AnimationUtils.currentAnimationTimeMillis() - this.mStartTime)) + i;
            this.mSplineDuration = currentAnimationTimeMillis;
            this.mDuration = currentAnimationTimeMillis;
            this.mFinished = false;
        }

        boolean springback(int i, int i2, int i3) {
            this.mFinished = true;
            this.mFinal = i;
            this.mStart = i;
            this.mCurrentPosition = i;
            this.mVelocity = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 0;
            if (i < i2) {
                startSpringback(i, i2, 0);
            } else if (i > i3) {
                startSpringback(i, i3, 0);
            }
            return !this.mFinished;
        }

        private void startSpringback(int i, int i2, int i3) {
            this.mFinished = false;
            this.mState = 1;
            this.mStart = i;
            this.mCurrentPosition = i;
            this.mFinal = i2;
            int i4 = i - i2;
            this.mDeceleration = getDeceleration(i4);
            this.mVelocity = -i4;
            this.mOver = Math.abs(i4);
            this.mDuration = (int) (Math.sqrt((i4 * (-2.0d)) / this.mDeceleration) * 1000.0d);
        }

        void fling(int i, int i2, int i3, int i4, int i5) {
            double d;
            this.mOver = i5;
            this.mFinished = false;
            this.mVelocity = i2;
            this.mCurrVelocity = i2;
            this.mSplineDuration = 0;
            this.mDuration = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis() - OverScroller.sIntervalTime;
            this.mStart = i;
            this.mCurrentPosition = i;
            if (i > i4 || i < i3) {
                startAfterEdge(i, i3, i4, i2);
                return;
            }
            this.mState = 0;
            if (i2 != 0) {
                int splineFlingDuration = getSplineFlingDuration(i2);
                this.mSplineDuration = splineFlingDuration;
                this.mDuration = splineFlingDuration;
                d = getSplineFlingDistance(i2);
                if (sIsSmoothFlingEnabled && !this.mIsDVFSBoosting && (i2 >= 800 || i2 <= MINIMUM_BOOSTED_FLING_VELOCITY_NEGATIVE)) {
                    SemPerfManager.onSmoothScrollEvent(true);
                    this.mIsDVFSBoosting = true;
                }
            } else {
                d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            }
            int signum = (int) (d * Math.signum(r0));
            this.mSplineDistance = signum;
            int i6 = i + signum;
            this.mFinal = i6;
            if (i6 < i3) {
                adjustDuration(this.mStart, i6, i3);
                this.mFinal = i3;
            }
            int i7 = this.mFinal;
            if (i7 > i4) {
                adjustDuration(this.mStart, i7, i4);
                this.mFinal = i4;
            }
            if (sUseRegulateCurrentTimeInterval) {
                this.mUpdateCount = 0;
            }
        }

        private double getSplineDeceleration(int i) {
            return Math.log((INFLEXION * Math.abs(i)) / (this.mFlingFriction * this.mPhysicalCoeff));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public double getSplineFlingDistance(int i) {
            double splineDeceleration = getSplineDeceleration(i);
            float f = DECELERATION_RATE;
            double d = f - 1.0d;
            if (sIsSmoothFlingEnabled) {
                int abs = (int) ((Math.abs(i) / this.mMaximumVelocity) * 100.0f);
                if (abs > 100) {
                    abs = 100;
                }
                return (((1.0f - SPLINE_POSITION[abs]) * 3.0f) + DISTANCE_M2) * this.mFlingFriction * this.mPhysicalCoeff * Math.exp((DECELERATION_RATE / d) * splineDeceleration);
            }
            return this.mFlingFriction * this.mPhysicalCoeff * Math.exp((f / d) * splineDeceleration);
        }

        private int getSplineFlingDuration(int i) {
            double exp;
            double splineDeceleration = getSplineDeceleration(i);
            double d = DECELERATION_RATE - 1.0d;
            if (sIsSmoothFlingEnabled) {
                int abs = (int) ((Math.abs(i) / this.mMaximumVelocity) * 100.0f);
                if (abs > 100) {
                    abs = 100;
                }
                exp = (((1.0f - SPLINE_POSITION[abs]) * DURATION_M1) + DURATION_M2) * 1000.0d * Math.exp(splineDeceleration / d);
            } else {
                exp = Math.exp(splineDeceleration / d) * 1000.0d;
            }
            return (int) exp;
        }

        private void fitOnBounceCurve(int i, int i2, int i3) {
            float f = (-i3) / this.mDeceleration;
            float f2 = i3;
            float sqrt = (float) Math.sqrt((((((f2 * f2) / 2.0f) / Math.abs(r1)) + Math.abs(i2 - i)) * 2.0d) / Math.abs(this.mDeceleration));
            this.mStartTime -= (int) ((sqrt - f) * 1000.0f);
            this.mStart = i2;
            this.mCurrentPosition = i2;
            this.mVelocity = (int) ((-this.mDeceleration) * sqrt);
        }

        private void startBounceAfterEdge(int i, int i2, int i3) {
            this.mDeceleration = getDeceleration(i3 == 0 ? i - i2 : i3);
            fitOnBounceCurve(i, i2, i3);
            onEdgeReached();
        }

        private void startAfterEdge(int i, int i2, int i3, int i4) {
            if (i > i2 && i < i3) {
                Log.e("OverScroller", "startAfterEdge called from a valid position");
                this.mFinished = true;
                return;
            }
            boolean z = i > i3;
            int i5 = z ? i3 : i2;
            if ((i - i5) * i4 >= 0) {
                startBounceAfterEdge(i, i5, i4);
            } else if (getSplineFlingDistance(i4) > Math.abs(r2)) {
                fling(i, i4, z ? i2 : i, z ? i : i3, this.mOver);
            } else {
                startSpringback(i, i5, i4);
            }
        }

        void notifyEdgeReached(int i, int i2, int i3) {
            if (this.mState == 0) {
                this.mOver = i3;
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                startAfterEdge(i, i2, i2, (int) this.mCurrVelocity);
            }
        }

        private void onEdgeReached() {
            int i = this.mVelocity;
            float f = i * i;
            float abs = f / (Math.abs(this.mDeceleration) * 2.0f);
            float signum = Math.signum(this.mVelocity);
            int i2 = this.mOver;
            if (abs > i2) {
                this.mDeceleration = ((-signum) * f) / (i2 * 2.0f);
                abs = i2;
            }
            this.mOver = (int) abs;
            this.mState = 2;
            int i3 = this.mStart;
            int i4 = this.mVelocity;
            if (i4 <= 0) {
                abs = -abs;
            }
            this.mFinal = i3 + ((int) abs);
            this.mDuration = -((int) ((i4 * 1000.0f) / this.mDeceleration));
            if (sUseRegulateCurrentTimeInterval) {
                this.mUpdateCount = 0;
            }
        }

        boolean continueWhenFinished() {
            int i = this.mState;
            if (i != 0) {
                if (i == 1) {
                    return false;
                }
                if (i == 2) {
                    this.mStartTime += this.mDuration;
                    startSpringback(this.mFinal, this.mStart, 0);
                }
            } else {
                if (this.mDuration >= this.mSplineDuration) {
                    return false;
                }
                int i2 = this.mFinal;
                this.mStart = i2;
                this.mCurrentPosition = i2;
                int i3 = (int) this.mCurrVelocity;
                this.mVelocity = i3;
                this.mDeceleration = getDeceleration(i3);
                this.mStartTime += this.mDuration;
                onEdgeReached();
            }
            update();
            return true;
        }

        boolean update() {
            float f;
            float f2;
            double d;
            double d2;
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
            if (sUseRegulateCurrentTimeInterval && this.mState == 0) {
                if (OverScroller.sIntervalTime == 0 && this.mUpdateCount > 0) {
                    currentAnimationTimeMillis = (this.mPrevTime + currentAnimationTimeMillis) / 2;
                }
                int i = this.mUpdateCount;
                if (i > 30) {
                    long j = this.mPrevTime;
                    long j2 = currentAnimationTimeMillis - j;
                    long j3 = this.mPrevTimeGap;
                    if (j2 > j3 + 1) {
                        currentAnimationTimeMillis = j + j3 + 1;
                    } else if (j2 < j3 - 1) {
                        currentAnimationTimeMillis = (j + j3) - 1;
                    }
                }
                if (currentAnimationTimeMillis < 0) {
                    currentAnimationTimeMillis = 0;
                }
                this.mPrevTimeGap = currentAnimationTimeMillis - this.mPrevTime;
                this.mPrevTime = currentAnimationTimeMillis;
                this.mUpdateCount = i + 1;
            }
            if (currentAnimationTimeMillis == 0) {
                return this.mDuration > 0;
            }
            int i2 = this.mDuration;
            if (currentAnimationTimeMillis > i2) {
                return false;
            }
            int i3 = this.mState;
            if (i3 == 0) {
                int i4 = this.mSplineDuration;
                float f3 = currentAnimationTimeMillis / i4;
                int i5 = (int) (f3 * 100.0f);
                if (i5 < 100) {
                    float f4 = i5 / 100.0f;
                    int i6 = i5 + 1;
                    float[] fArr = SPLINE_POSITION;
                    float f5 = fArr[i5];
                    f2 = (fArr[i6] - f5) / ((i6 / 100.0f) - f4);
                    f = f5 + ((f3 - f4) * f2);
                } else {
                    f = 1.0f;
                    f2 = 0.0f;
                }
                int i7 = this.mSplineDistance;
                d = f * i7;
                this.mCurrVelocity = ((f2 * i7) / i4) * 1000.0f;
            } else if (i3 == 1) {
                float f6 = currentAnimationTimeMillis / i2;
                float f7 = f6 * f6;
                float signum = Math.signum(this.mVelocity);
                int i8 = this.mOver;
                d = i8 * signum * ((3.0f * f7) - ((2.0f * f6) * f7));
                this.mCurrVelocity = signum * i8 * 6.0f * ((-f6) + f7);
            } else {
                if (i3 != 2) {
                    d2 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                } else {
                    float f8 = currentAnimationTimeMillis / 1000.0f;
                    int i9 = this.mVelocity;
                    float f9 = this.mDeceleration;
                    this.mCurrVelocity = i9 + (f9 * f8);
                    d2 = (i9 * f8) + (((f9 * f8) * f8) / 2.0f);
                }
                this.mCurrentPosition = this.mStart + ((int) Math.round(d2));
                return true;
            }
            d2 = d;
            this.mCurrentPosition = this.mStart + ((int) Math.round(d2));
            return true;
        }
    }
}
