package android.widget;

import android.content.Context;
import android.hardware.scontext.SContextConstants;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.view.Choreographer;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.samsung.android.os.SemPerfManager;
import com.samsung.android.rune.CoreRune;
import java.lang.reflect.Array;

/* loaded from: classes4.dex */
public class OverScroller {
    private static final int DEFAULT_DURATION = 250;
    private static final int FLING_MODE = 1;
    private static final float FRAME_LATENCY_LIMIT = 16.66f;
    private static final int SCROLL_MODE = 0;
    private static long sIntervalTime = 0;
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

    public OverScroller(Context context, Interpolator interpolator, boolean flywheel) {
        if (interpolator == null) {
            this.mInterpolator = new Scroller.ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
        this.mFlywheel = flywheel;
        this.mScrollerX = new SplineOverScroller(context);
        this.mScrollerY = new SplineOverScroller(context);
        this.mScrollerX.setSTBIndex(0);
        this.mScrollerY.setSTBIndex(1);
    }

    @Deprecated
    public OverScroller(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY) {
        this(context, interpolator, true);
    }

    @Deprecated
    public OverScroller(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY, boolean flywheel) {
        this(context, interpolator, flywheel);
    }

    void setInterpolator(Interpolator interpolator) {
        if (interpolator == null) {
            this.mInterpolator = new Scroller.ViscousFluidInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
    }

    public final void setFriction(float friction) {
        this.mScrollerX.setFriction(friction);
        this.mScrollerY.setFriction(friction);
    }

    public final boolean isFinished() {
        return this.mScrollerX.mFinished && this.mScrollerY.mFinished;
    }

    public final void forceFinished(boolean finished) {
        SplineOverScroller splineOverScroller = this.mScrollerX;
        this.mScrollerY.mFinished = finished;
        splineOverScroller.mFinished = finished;
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

    public void extendDuration(int extend) {
        this.mScrollerX.extendDuration(extend);
        this.mScrollerY.extendDuration(extend);
    }

    public void setFinalX(int newX) {
        this.mScrollerX.setFinalPosition(newX);
    }

    public void setFinalY(int newY) {
        this.mScrollerY.setFinalPosition(newY);
    }

    public boolean computeScrollOffset() {
        if (isFinished()) {
            return false;
        }
        switch (this.mMode) {
            case 0:
                long time = AnimationUtils.currentAnimationTimeMillis();
                long elapsedTime = time - this.mScrollerX.mStartTime;
                int duration = this.mScrollerX.mDuration;
                if (elapsedTime < duration) {
                    float q = this.mInterpolator.getInterpolation(elapsedTime / duration);
                    float q2 = this.mInterpolator.getInterpolation((elapsedTime - 1) / duration);
                    this.mScrollerX.updateScroll(q, q2);
                    this.mScrollerY.updateScroll(q, q2);
                    return true;
                }
                abortAnimation();
                return true;
            case 1:
                if (!this.mScrollerX.mFinished && !this.mScrollerX.update() && !this.mScrollerX.continueWhenFinished()) {
                    this.mScrollerX.finish();
                }
                if (!this.mScrollerY.mFinished && !this.mScrollerY.update() && !this.mScrollerY.continueWhenFinished()) {
                    this.mScrollerY.finish();
                    return true;
                }
                return true;
            default:
                return true;
        }
    }

    public void startScroll(int startX, int startY, int dx, int dy) {
        startScroll(startX, startY, dx, dy, 250);
    }

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        this.mMode = 0;
        this.mScrollerX.startScroll(startX, dx, duration);
        this.mScrollerY.startScroll(startY, dy, duration);
    }

    public boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY) {
        this.mMode = 1;
        boolean spingbackX = this.mScrollerX.springback(startX, minX, maxX);
        boolean spingbackY = this.mScrollerY.springback(startY, minY, maxY);
        return spingbackX || spingbackY;
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
    }

    protected void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, boolean accDisabled) {
        fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0, accDisabled);
    }

    public void hidden_fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, boolean accDisabled) {
        fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0, accDisabled);
    }

    public void hidden_fling(int velocityX, int velocityY, boolean isSkipMove, float frameLatency) {
        fling(0, 0, velocityX, velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0, isSkipMove, frameLatency);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        int velocityY2;
        int velocityX2 = velocityX;
        if (this.mFlywheel && !isFinished()) {
            float oldVelocityX = this.mScrollerX.mCurrVelocity;
            float oldVelocityY = this.mScrollerY.mCurrVelocity;
            if (Math.signum(velocityX2) == Math.signum(oldVelocityX) && Math.signum(velocityY) == Math.signum(oldVelocityY)) {
                velocityX2 = (int) (velocityX2 + oldVelocityX);
                velocityY2 = (int) (velocityY + oldVelocityY);
                this.mMode = 1;
                this.mScrollerX.fling(startX, velocityX2, minX, maxX, overX);
                this.mScrollerY.fling(startY, velocityY2, minY, maxY, overY);
            }
        }
        velocityY2 = velocityY;
        this.mMode = 1;
        this.mScrollerX.fling(startX, velocityX2, minX, maxX, overX);
        this.mScrollerY.fling(startY, velocityY2, minY, maxY, overY);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY, boolean accDisabled) {
        int velocityY2;
        int velocityX2 = velocityX;
        if (accDisabled) {
            sIntervalTime = 0L;
        }
        if (this.mFlywheel && !isFinished() && !accDisabled) {
            float oldVelocityX = this.mScrollerX.mCurrVelocity;
            float oldVelocityY = this.mScrollerY.mCurrVelocity;
            if (Math.signum(velocityX2) == Math.signum(oldVelocityX) && Math.signum(velocityY) == Math.signum(oldVelocityY)) {
                velocityX2 = (int) (velocityX2 + oldVelocityX);
                velocityY2 = (int) (velocityY + oldVelocityY);
                this.mMode = 1;
                this.mScrollerX.fling(startX, velocityX2, minX, maxX, overX);
                this.mScrollerY.fling(startY, velocityY2, minY, maxY, overY);
            }
        }
        velocityY2 = velocityY;
        this.mMode = 1;
        this.mScrollerX.fling(startX, velocityX2, minX, maxX, overX);
        this.mScrollerY.fling(startY, velocityY2, minY, maxY, overY);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void fling(int r14, int r15, int r16, int r17, int r18, int r19, int r20, int r21, int r22, int r23, boolean r24, float r25) {
        /*
            r13 = this;
            r0 = r13
            r1 = r16
            r2 = r17
            boolean r3 = r0.mFlywheel
            if (r3 == 0) goto L3d
            boolean r3 = r13.isFinished()
            if (r3 != 0) goto L3d
            android.widget.OverScroller$SplineOverScroller r3 = r0.mScrollerX
            float r3 = android.widget.OverScroller.SplineOverScroller.m6736$$Nest$fgetmCurrVelocity(r3)
            android.widget.OverScroller$SplineOverScroller r4 = r0.mScrollerY
            float r4 = android.widget.OverScroller.SplineOverScroller.m6736$$Nest$fgetmCurrVelocity(r4)
            float r5 = (float) r1
            float r5 = java.lang.Math.signum(r5)
            float r6 = java.lang.Math.signum(r3)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 != 0) goto L3d
            float r5 = (float) r2
            float r5 = java.lang.Math.signum(r5)
            float r6 = java.lang.Math.signum(r4)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 != 0) goto L3d
            float r5 = (float) r1
            float r5 = r5 + r3
            int r1 = (int) r5
            float r5 = (float) r2
            float r5 = r5 + r4
            int r2 = (int) r5
            r11 = r2
            goto L3e
        L3d:
            r11 = r2
        L3e:
            if (r24 == 0) goto L59
            r2 = 0
            int r2 = (r25 > r2 ? 1 : (r25 == r2 ? 0 : -1))
            if (r2 >= 0) goto L47
            r2 = 0
            goto L54
        L47:
            r2 = 1099253678(0x418547ae, float:16.66)
            int r2 = (r25 > r2 ? 1 : (r25 == r2 ? 0 : -1))
            if (r2 <= 0) goto L52
            r2 = 1099253678(0x418547ae, float:16.66)
            goto L54
        L52:
            r2 = r25
        L54:
            long r3 = (long) r2
            android.widget.OverScroller.sIntervalTime = r3
            r12 = r2
            goto L5f
        L59:
            r2 = 0
            android.widget.OverScroller.sIntervalTime = r2
            r12 = r25
        L5f:
            r2 = 1
            r0.mMode = r2
            android.widget.OverScroller$SplineOverScroller r5 = r0.mScrollerX
            r6 = r14
            r7 = r1
            r8 = r18
            r9 = r19
            r10 = r22
            r5.fling(r6, r7, r8, r9, r10)
            android.widget.OverScroller$SplineOverScroller r2 = r0.mScrollerY
            r3 = r15
            r4 = r11
            r5 = r20
            r6 = r21
            r7 = r23
            r2.fling(r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.OverScroller.fling(int, int, int, int, int, int, int, int, int, int, boolean, float):void");
    }

    public void notifyHorizontalEdgeReached(int startX, int finalX, int overX) {
        this.mScrollerX.notifyEdgeReached(startX, finalX, overX);
    }

    public void notifyVerticalEdgeReached(int startY, int finalY, int overY) {
        this.mScrollerY.notifyEdgeReached(startY, finalY, overY);
    }

    public boolean isOverScrolled() {
        return ((this.mScrollerX.mFinished || this.mScrollerX.mState == 0) && (this.mScrollerY.mFinished || this.mScrollerY.mState == 0)) ? false : true;
    }

    public void abortAnimation() {
        this.mScrollerX.finish();
        this.mScrollerY.finish();
    }

    public int timePassed() {
        long time = AnimationUtils.currentAnimationTimeMillis();
        long startTime = Math.min(this.mScrollerX.mStartTime, this.mScrollerY.mStartTime);
        return (int) (time - startTime);
    }

    public boolean isScrollingInDirection(float xvel, float yvel) {
        int dx = this.mScrollerX.mFinal - this.mScrollerX.mStart;
        int dy = this.mScrollerY.mFinal - this.mScrollerY.mStart;
        return !isFinished() && Math.signum(xvel) == Math.signum((float) dx) && Math.signum(yvel) == Math.signum((float) dy);
    }

    @Deprecated
    public void semSetSmoothScrollEnabled(boolean z) {
        this.mScrollerX.setMode(z ? 1 : 0);
        this.mScrollerY.setMode(z ? 1 : 0);
    }

    double getSplineFlingDistance(int velocity) {
        return this.mScrollerY.getSplineFlingDistance(velocity);
    }

    static class SplineOverScroller {
        private static final int BALLISTIC = 2;
        private static final int CUBIC = 1;
        private static final int DEFAULT_MODE = 1;
        private static final float DISTANCE_M1 = 3.0f;
        private static final float DISTANCE_M2 = 1.25f;
        private static final float DURATION_M1 = 2.7f;
        private static final float DURATION_M2 = 1.45f;
        private static final float END_TENSION = 1.0f;
        private static final float GRAVITY = 2000.0f;
        private static final long HIGHER_TIME_GAP_COMPENSATION = 1;
        private static final long HIGHER_TIME_GAP_MARGIN = 1;
        private static final long LOWER_TIME_GAP_COMPENSATION = 1;
        private static final long LOWER_TIME_GAP_MARGIN = 1;
        private static final int MARGIN_COMPENSATION_STARTING_COUNT = 30;
        private static final int MINIMUM_BOOSTED_FLING_VELOCITY_NEGATIVE = -800;
        private static final int MINIMUM_BOOSTED_FLING_VELOCITY_POSITIVE = 800;
        private static final int NB_SAMPLES = 100;
        public static final int ORIGINAL_MODE = 0;
        public static final int SMOOTH_MODE = 1;
        private static final int SPLINE = 0;
        private static final float START_TENSION = 0.5f;
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
        private static float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
        private static final float[] INFLEXIONS = {0.35f, 0.26f};
        private static float INFLEXION = INFLEXIONS[1];
        private static final float[][] SPLINE_POSITIONS = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 101);
        private static final float[][] SPLINE_TIMES = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 101);
        private static float[] SPLINE_POSITION = SPLINE_POSITIONS[1];
        private static float[] SPLINE_TIME = SPLINE_TIMES[1];
        private static boolean sIsSmoothFlingEnabled = true;
        private static boolean sUseRegulateCurrentTimeInterval = !CoreRune.FW_DVRR_TOOLKIT_POLICY;
        private int mSTBIndex = -1;
        private boolean mFlingSTBFlag = false;
        private float mFlingFriction = ViewConfiguration.getScrollFriction();
        private int mState = 0;
        private int mUpdateCount = 0;
        private long mPrevTime = 0;
        private long mPrevTimeGap = 0;
        private boolean mIsDVFSBoosting = false;
        private boolean mFinished = true;

        static {
            float x;
            float coef;
            float y;
            float coef2;
            int mode = 0;
            for (int i = 2; mode < i; i = 2) {
                float f = 0.5f;
                float P1 = INFLEXIONS[mode] * 0.5f;
                float P2 = 1.0f - ((1.0f - INFLEXIONS[mode]) * 1.0f);
                float x_min = 0.0f;
                float y_min = 0.0f;
                int i2 = 0;
                while (i2 < 100) {
                    float alpha = i2 / 100.0f;
                    float x_max = 1.0f;
                    while (true) {
                        x = ((x_max - x_min) / 2.0f) + x_min;
                        coef = x * 3.0f * (1.0f - x);
                        float tx = ((((1.0f - x) * P1) + (x * P2)) * coef) + (x * x * x);
                        if (Math.abs(tx - alpha) < 1.0E-5d) {
                            break;
                        }
                        if (tx > alpha) {
                            x_max = x;
                            f = 0.5f;
                        } else {
                            x_min = x;
                            f = 0.5f;
                        }
                    }
                    SPLINE_POSITIONS[mode][i2] = ((((1.0f - x) * f) + x) * coef) + (x * x * x);
                    float y_max = 1.0f;
                    while (true) {
                        y = ((y_max - y_min) / 2.0f) + y_min;
                        coef2 = y * 3.0f * (1.0f - y);
                        float dy = ((((1.0f - y) * f) + y) * coef2) + (y * y * y);
                        if (Math.abs(dy - alpha) < 1.0E-5d) {
                            break;
                        }
                        if (dy > alpha) {
                            y_max = y;
                            f = 0.5f;
                        } else {
                            y_min = y;
                            f = 0.5f;
                        }
                    }
                    SPLINE_TIMES[mode][i2] = (coef2 * (((1.0f - y) * P1) + (y * P2))) + (y * y * y);
                    i2++;
                    f = 0.5f;
                }
                float[] fArr = SPLINE_POSITIONS[mode];
                SPLINE_TIMES[mode][100] = 1.0f;
                fArr[100] = 1.0f;
                mode++;
            }
        }

        public void setMode(int mode) {
            boolean z;
            boolean z2 = true;
            if (mode == 0 || mode == 1) {
                if (mode == 1) {
                    z = true;
                } else {
                    z = false;
                }
                sIsSmoothFlingEnabled = z;
                if (!CoreRune.FW_DVRR_TOOLKIT_POLICY) {
                    if (mode != 1) {
                        z2 = false;
                    }
                    sUseRegulateCurrentTimeInterval = z2;
                }
                INFLEXION = INFLEXIONS[mode];
                SPLINE_POSITION = SPLINE_POSITIONS[mode];
                SPLINE_TIME = SPLINE_TIMES[mode];
            }
        }

        void setFriction(float friction) {
            this.mFlingFriction = friction;
        }

        SplineOverScroller(Context context) {
            float ppi = context.getResources().getDisplayMetrics().density * 160.0f;
            this.mPhysicalCoeff = 386.0878f * ppi * 0.84f;
            if (sIsSmoothFlingEnabled) {
                ViewConfiguration configuration = ViewConfiguration.get(context);
                this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
            }
        }

        void setSTBIndex(int STBIndex) {
            this.mSTBIndex = STBIndex;
        }

        void updateScroll(float q, float q2) {
            int distance = this.mFinal - this.mStart;
            this.mCurrentPosition = this.mStart + Math.round(distance * q);
            this.mCurrVelocity = (q - q2) * 1000.0f * distance;
        }

        private static float getDeceleration(int velocity) {
            if (velocity > 0) {
                return -2000.0f;
            }
            return GRAVITY;
        }

        private void adjustDuration(int start, int oldFinal, int newFinal) {
            int oldDistance = oldFinal - start;
            int newDistance = newFinal - start;
            float x = Math.abs(newDistance / oldDistance);
            int index = (int) (x * 100.0f);
            if (index < 100) {
                float x_inf = index / 100.0f;
                float x_sup = (index + 1) / 100.0f;
                float t_inf = SPLINE_TIME[index];
                float t_sup = SPLINE_TIME[index + 1];
                float timeCoef = (((x - x_inf) / (x_sup - x_inf)) * (t_sup - t_inf)) + t_inf;
                this.mDuration = (int) (this.mDuration * timeCoef);
            }
        }

        void startScroll(int start, int distance, int duration) {
            this.mFinished = false;
            this.mStart = start;
            this.mCurrentPosition = start;
            this.mFinal = start + distance;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = duration;
            this.mDeceleration = 0.0f;
            this.mVelocity = 0;
        }

        void finish() {
            if (this.mIsDVFSBoosting) {
                SemPerfManager.onSmoothScrollEvent(false);
                this.mIsDVFSBoosting = false;
            }
            if (Process.myUid() >= 10000 && Process.myUid() <= 19999 && this.mSTBIndex != -1 && this.mFlingSTBFlag) {
                Choreographer choreographer = Looper.myLooper() != null ? Choreographer.getInstance() : null;
                if (choreographer != null) {
                    choreographer.setFlingSTBFlag(false, this.mSTBIndex);
                    this.mFlingSTBFlag = false;
                }
            }
            this.mCurrentPosition = this.mFinal;
            this.mFinished = true;
        }

        void setFinalPosition(int position) {
            this.mFinal = position;
            this.mSplineDistance = this.mFinal - this.mStart;
            this.mFinished = false;
        }

        void extendDuration(int extend) {
            long time = AnimationUtils.currentAnimationTimeMillis();
            int elapsedTime = (int) (time - this.mStartTime);
            int i = elapsedTime + extend;
            this.mSplineDuration = i;
            this.mDuration = i;
            this.mFinished = false;
        }

        boolean springback(int start, int min, int max) {
            this.mFinished = true;
            this.mFinal = start;
            this.mStart = start;
            this.mCurrentPosition = start;
            this.mVelocity = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 0;
            if (start < min) {
                startSpringback(start, min, 0);
            } else if (start > max) {
                startSpringback(start, max, 0);
            }
            return true ^ this.mFinished;
        }

        private void startSpringback(int start, int end, int velocity) {
            this.mFinished = false;
            this.mState = 1;
            this.mStart = start;
            this.mCurrentPosition = start;
            this.mFinal = end;
            int delta = start - end;
            this.mDeceleration = getDeceleration(delta);
            this.mVelocity = -delta;
            this.mOver = Math.abs(delta);
            this.mDuration = (int) (Math.sqrt((delta * (-2.0d)) / this.mDeceleration) * 1000.0d);
        }

        void fling(int start, int velocity, int min, int max, int over) {
            this.mOver = over;
            this.mFinished = false;
            this.mVelocity = velocity;
            this.mCurrVelocity = velocity;
            this.mSplineDuration = 0;
            this.mDuration = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis() - OverScroller.sIntervalTime;
            this.mStart = start;
            this.mCurrentPosition = start;
            if (start > max || start < min) {
                startAfterEdge(start, min, max, velocity);
                return;
            }
            this.mState = 0;
            double totalDistance = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            if (velocity != 0) {
                int splineFlingDuration = getSplineFlingDuration(velocity);
                this.mSplineDuration = splineFlingDuration;
                this.mDuration = splineFlingDuration;
                totalDistance = getSplineFlingDistance(velocity);
                if (sIsSmoothFlingEnabled && !this.mIsDVFSBoosting && (velocity >= 800 || velocity <= MINIMUM_BOOSTED_FLING_VELOCITY_NEGATIVE)) {
                    SemPerfManager.onSmoothScrollEvent(true);
                    this.mIsDVFSBoosting = true;
                }
                if (Process.myUid() >= 10000 && Process.myUid() <= 19999 && this.mSTBIndex != -1 && !this.mFlingSTBFlag) {
                    Choreographer choreographer = Looper.myLooper() != null ? Choreographer.getInstance() : null;
                    if (choreographer != null) {
                        choreographer.setFlingSTBFlag(true, this.mSTBIndex);
                        this.mFlingSTBFlag = true;
                    }
                }
            }
            this.mSplineDistance = (int) (Math.signum(velocity) * totalDistance);
            this.mFinal = this.mSplineDistance + start;
            if (this.mFinal < min) {
                adjustDuration(this.mStart, this.mFinal, min);
                this.mFinal = min;
            }
            if (this.mFinal > max) {
                adjustDuration(this.mStart, this.mFinal, max);
                this.mFinal = max;
            }
            if (sUseRegulateCurrentTimeInterval) {
                this.mUpdateCount = 0;
            }
        }

        private double getSplineDeceleration(int velocity) {
            return Math.log((INFLEXION * Math.abs(velocity)) / (this.mFlingFriction * this.mPhysicalCoeff));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public double getSplineFlingDistance(int velocity) {
            double l = getSplineDeceleration(velocity);
            double decelMinusOne = DECELERATION_RATE - 1.0d;
            if (sIsSmoothFlingEnabled) {
                float rate = Math.abs(velocity) / this.mMaximumVelocity;
                int index = (int) (100.0f * rate);
                if (index > 100) {
                    index = 100;
                }
                float value = 1.0f - SPLINE_POSITION[index];
                double tuningValue = (3.0f * value) + DISTANCE_M2;
                return this.mFlingFriction * tuningValue * this.mPhysicalCoeff * Math.exp((DECELERATION_RATE / decelMinusOne) * l);
            }
            float rate2 = this.mFlingFriction;
            return rate2 * this.mPhysicalCoeff * Math.exp((DECELERATION_RATE / decelMinusOne) * l);
        }

        private int getSplineFlingDuration(int velocity) {
            double l = getSplineDeceleration(velocity);
            double decelMinusOne = DECELERATION_RATE - 1.0d;
            if (sIsSmoothFlingEnabled) {
                float rate = Math.abs(velocity) / this.mMaximumVelocity;
                int index = (int) (100.0f * rate);
                if (index > 100) {
                    index = 100;
                }
                float value = 1.0f - SPLINE_POSITION[index];
                double tuningValue = (DURATION_M1 * value) + DURATION_M2;
                return (int) (1000.0d * tuningValue * Math.exp(l / decelMinusOne));
            }
            return (int) (Math.exp(l / decelMinusOne) * 1000.0d);
        }

        private void fitOnBounceCurve(int start, int end, int velocity) {
            float durationToApex = (-velocity) / this.mDeceleration;
            float velocitySquared = velocity * velocity;
            float distanceToApex = (velocitySquared / 2.0f) / Math.abs(this.mDeceleration);
            float distanceToEdge = Math.abs(end - start);
            float totalDuration = (float) Math.sqrt(((distanceToApex + distanceToEdge) * 2.0d) / Math.abs(this.mDeceleration));
            this.mStartTime -= (int) ((totalDuration - durationToApex) * 1000.0f);
            this.mStart = end;
            this.mCurrentPosition = end;
            this.mVelocity = (int) ((-this.mDeceleration) * totalDuration);
        }

        private void startBounceAfterEdge(int start, int end, int velocity) {
            this.mDeceleration = getDeceleration(velocity == 0 ? start - end : velocity);
            fitOnBounceCurve(start, end, velocity);
            onEdgeReached();
        }

        private void startAfterEdge(int start, int min, int max, int velocity) {
            if (start > min && start < max) {
                Log.e("OverScroller", "startAfterEdge called from a valid position");
                this.mFinished = true;
                return;
            }
            boolean positive = start > max;
            int edge = positive ? max : min;
            int overDistance = start - edge;
            boolean keepIncreasing = overDistance * velocity >= 0;
            if (keepIncreasing) {
                startBounceAfterEdge(start, edge, velocity);
                return;
            }
            double totalDistance = getSplineFlingDistance(velocity);
            if (totalDistance > Math.abs(overDistance)) {
                fling(start, velocity, positive ? min : start, positive ? start : max, this.mOver);
            } else {
                startSpringback(start, edge, velocity);
            }
        }

        void notifyEdgeReached(int start, int end, int over) {
            if (this.mState == 0) {
                this.mOver = over;
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                startAfterEdge(start, end, end, (int) this.mCurrVelocity);
            }
        }

        private void onEdgeReached() {
            float velocitySquared = this.mVelocity * this.mVelocity;
            float distance = velocitySquared / (Math.abs(this.mDeceleration) * 2.0f);
            float sign = Math.signum(this.mVelocity);
            if (distance > this.mOver) {
                this.mDeceleration = ((-sign) * velocitySquared) / (this.mOver * 2.0f);
                distance = this.mOver;
            }
            this.mOver = (int) distance;
            this.mState = 2;
            this.mFinal = this.mStart + ((int) (this.mVelocity > 0 ? distance : -distance));
            this.mDuration = -((int) ((this.mVelocity * 1000.0f) / this.mDeceleration));
            if (sUseRegulateCurrentTimeInterval) {
                this.mUpdateCount = 0;
            }
        }

        boolean continueWhenFinished() {
            switch (this.mState) {
                case 0:
                    if (this.mDuration >= this.mSplineDuration) {
                        return false;
                    }
                    int i = this.mFinal;
                    this.mStart = i;
                    this.mCurrentPosition = i;
                    this.mVelocity = (int) this.mCurrVelocity;
                    this.mDeceleration = getDeceleration(this.mVelocity);
                    this.mStartTime += this.mDuration;
                    onEdgeReached();
                    break;
                case 1:
                    return false;
                case 2:
                    this.mStartTime += this.mDuration;
                    startSpringback(this.mFinal, this.mStart, 0);
                    break;
            }
            update();
            return true;
        }

        boolean update() {
            long time = AnimationUtils.currentAnimationTimeMillis();
            long currentTime = time - this.mStartTime;
            if (sUseRegulateCurrentTimeInterval && this.mState == 0) {
                if (OverScroller.sIntervalTime == 0 && this.mUpdateCount > 0) {
                    currentTime = (this.mPrevTime + currentTime) / 2;
                }
                if (this.mUpdateCount > 30) {
                    long currentTimeGap = currentTime - this.mPrevTime;
                    if (currentTimeGap > this.mPrevTimeGap + 1) {
                        currentTime = this.mPrevTime + this.mPrevTimeGap + 1;
                    } else if (currentTimeGap < this.mPrevTimeGap - 1) {
                        currentTime = (this.mPrevTime + this.mPrevTimeGap) - 1;
                    }
                }
                if (currentTime < 0) {
                    currentTime = 0;
                }
                this.mPrevTimeGap = currentTime - this.mPrevTime;
                this.mPrevTime = currentTime;
                this.mUpdateCount++;
            }
            if (currentTime == 0) {
                return this.mDuration > 0;
            }
            if (currentTime > this.mDuration) {
                return false;
            }
            double distance = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            switch (this.mState) {
                case 0:
                    float t = currentTime / this.mSplineDuration;
                    int index = (int) (t * 100.0f);
                    float distanceCoef = 1.0f;
                    float velocityCoef = 0.0f;
                    if (index < 100) {
                        float t_inf = index / 100.0f;
                        float t_sup = (index + 1) / 100.0f;
                        float d_inf = SPLINE_POSITION[index];
                        float d_sup = SPLINE_POSITION[index + 1];
                        velocityCoef = (d_sup - d_inf) / (t_sup - t_inf);
                        distanceCoef = d_inf + ((t - t_inf) * velocityCoef);
                    }
                    distance = this.mSplineDistance * distanceCoef;
                    this.mCurrVelocity = ((this.mSplineDistance * velocityCoef) / this.mSplineDuration) * 1000.0f;
                    break;
                case 1:
                    float t2 = currentTime / this.mDuration;
                    float t22 = t2 * t2;
                    float sign = Math.signum(this.mVelocity);
                    distance = this.mOver * sign * ((3.0f * t22) - ((2.0f * t2) * t22));
                    this.mCurrVelocity = this.mOver * sign * 6.0f * ((-t2) + t22);
                    break;
                case 2:
                    float t3 = currentTime / 1000.0f;
                    this.mCurrVelocity = this.mVelocity + (this.mDeceleration * t3);
                    distance = (this.mVelocity * t3) + (((this.mDeceleration * t3) * t3) / 2.0f);
                    break;
            }
            this.mCurrentPosition = this.mStart + ((int) Math.round(distance));
            return true;
        }
    }
}
