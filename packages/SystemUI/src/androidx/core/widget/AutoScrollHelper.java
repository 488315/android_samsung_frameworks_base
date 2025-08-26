package androidx.core.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public abstract class AutoScrollHelper implements View.OnTouchListener {
    public static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
    public final int mActivationDelay;
    public boolean mAlreadyDelayed;
    public boolean mAnimating;
    public final Interpolator mEdgeInterpolator;
    public final int mEdgeType;
    public boolean mEnabled;
    public final float[] mMaximumEdges;
    public final float[] mMaximumVelocity;
    public final float[] mMinimumVelocity;
    public boolean mNeedsCancel;
    public boolean mNeedsReset;
    public final float[] mRelativeEdges;
    public final float[] mRelativeVelocity;
    public ScrollAnimationRunnable mRunnable;
    public final ClampedScroller mScroller;
    public final View mTarget;

    public class ClampedScroller {
        public int mEffectiveRampDown;
        public int mRampDownDuration;
        public int mRampUpDuration;
        public float mStopValue;
        public float mTargetVelocityX;
        public float mTargetVelocityY;
        public long mStartTime = Long.MIN_VALUE;
        public long mStopTime = -1;
        public long mDeltaTime = 0;

        public final float getValueAt(long j) {
            if (j < this.mStartTime) {
                return 0.0f;
            }
            long j2 = this.mStopTime;
            if (j2 < 0 || j < j2) {
                return AutoScrollHelper.constrain((j - r0) / this.mRampUpDuration, 0.0f, 1.0f) * 0.5f;
            }
            float f = this.mStopValue;
            return (AutoScrollHelper.constrain((j - j2) / this.mEffectiveRampDown, 0.0f, 1.0f) * f) + (1.0f - f);
        }
    }

    public class ScrollAnimationRunnable implements Runnable {
        public ScrollAnimationRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            AutoScrollHelper autoScrollHelper = AutoScrollHelper.this;
            if (autoScrollHelper.mAnimating) {
                if (autoScrollHelper.mNeedsReset) {
                    autoScrollHelper.mNeedsReset = false;
                    ClampedScroller clampedScroller = autoScrollHelper.mScroller;
                    clampedScroller.getClass();
                    long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                    clampedScroller.mStartTime = jCurrentAnimationTimeMillis;
                    clampedScroller.mStopTime = -1L;
                    clampedScroller.mDeltaTime = jCurrentAnimationTimeMillis;
                    clampedScroller.mStopValue = 0.5f;
                }
                ClampedScroller clampedScroller2 = AutoScrollHelper.this.mScroller;
                if ((clampedScroller2.mStopTime > 0 && AnimationUtils.currentAnimationTimeMillis() > clampedScroller2.mStopTime + clampedScroller2.mEffectiveRampDown) || !AutoScrollHelper.this.shouldAnimate()) {
                    AutoScrollHelper.this.mAnimating = false;
                    return;
                }
                AutoScrollHelper autoScrollHelper2 = AutoScrollHelper.this;
                if (autoScrollHelper2.mNeedsCancel) {
                    autoScrollHelper2.mNeedsCancel = false;
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                    autoScrollHelper2.mTarget.onTouchEvent(motionEventObtain);
                    motionEventObtain.recycle();
                }
                if (clampedScroller2.mDeltaTime == 0) {
                    throw new RuntimeException("Cannot compute scroll delta before calling start()");
                }
                long jCurrentAnimationTimeMillis2 = AnimationUtils.currentAnimationTimeMillis();
                float valueAt = clampedScroller2.getValueAt(jCurrentAnimationTimeMillis2);
                long j = jCurrentAnimationTimeMillis2 - clampedScroller2.mDeltaTime;
                clampedScroller2.mDeltaTime = jCurrentAnimationTimeMillis2;
                AutoScrollHelper.this.scrollTargetBy((int) (j * ((valueAt * 4.0f) + ((-4.0f) * valueAt * valueAt)) * clampedScroller2.mTargetVelocityY));
                View view = AutoScrollHelper.this.mTarget;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.postOnAnimation(this);
            }
        }
    }

    public AutoScrollHelper(View view) {
        ClampedScroller clampedScroller = new ClampedScroller();
        this.mScroller = clampedScroller;
        this.mEdgeInterpolator = new AccelerateInterpolator();
        float[] fArr = {0.0f, 0.0f};
        this.mRelativeEdges = fArr;
        float[] fArr2 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.mMaximumEdges = fArr2;
        float[] fArr3 = {0.0f, 0.0f};
        this.mRelativeVelocity = fArr3;
        float[] fArr4 = {0.0f, 0.0f};
        this.mMinimumVelocity = fArr4;
        float[] fArr5 = {Float.MAX_VALUE, Float.MAX_VALUE};
        this.mMaximumVelocity = fArr5;
        this.mTarget = view;
        float f = Resources.getSystem().getDisplayMetrics().density;
        float f2 = ((int) ((1575.0f * f) + 0.5f)) / 1000.0f;
        fArr5[0] = f2;
        fArr5[1] = f2;
        float f3 = ((int) ((f * 315.0f) + 0.5f)) / 1000.0f;
        fArr4[0] = f3;
        fArr4[1] = f3;
        this.mEdgeType = 1;
        fArr2[0] = Float.MAX_VALUE;
        fArr2[1] = Float.MAX_VALUE;
        fArr[0] = 0.2f;
        fArr[1] = 0.2f;
        fArr3[0] = 0.001f;
        fArr3[1] = 0.001f;
        this.mActivationDelay = DEFAULT_ACTIVATION_DELAY;
        clampedScroller.mRampUpDuration = 500;
        clampedScroller.mRampDownDuration = 500;
    }

    public static float constrain(float f, float f2, float f3) {
        return f > f3 ? f3 : f < f2 ? f2 : f;
    }

    public abstract boolean canTargetScrollVertically(int i);

    /* JADX WARN: Removed duplicated region for block: B:12:0x0041 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float computeTargetVelocity(float f, float f2, float f3, int i) {
        float fConstrain;
        float interpolation;
        float fConstrain2 = constrain(this.mRelativeEdges[i] * f2, 0.0f, this.mMaximumEdges[i]);
        float fConstrainEdgeValue = constrainEdgeValue(f2 - f, fConstrain2) - constrainEdgeValue(f, fConstrain2);
        if (fConstrainEdgeValue < 0.0f) {
            interpolation = -((AccelerateInterpolator) this.mEdgeInterpolator).getInterpolation(-fConstrainEdgeValue);
        } else {
            if (fConstrainEdgeValue <= 0.0f) {
                fConstrain = 0.0f;
                if (fConstrain != 0.0f) {
                    return 0.0f;
                }
                float f4 = this.mRelativeVelocity[i];
                float f5 = this.mMinimumVelocity[i];
                float f6 = this.mMaximumVelocity[i];
                float f7 = f4 * f3;
                return fConstrain > 0.0f ? constrain(fConstrain * f7, f5, f6) : -constrain((-fConstrain) * f7, f5, f6);
            }
            interpolation = ((AccelerateInterpolator) this.mEdgeInterpolator).getInterpolation(fConstrainEdgeValue);
        }
        fConstrain = constrain(interpolation, -1.0f, 1.0f);
        if (fConstrain != 0.0f) {
        }
    }

    public final float constrainEdgeValue(float f, float f2) {
        if (f2 != 0.0f) {
            int i = this.mEdgeType;
            if (i == 0 || i == 1) {
                if (f < f2) {
                    if (f >= 0.0f) {
                        return 1.0f - (f / f2);
                    }
                    if (this.mAnimating && i == 1) {
                        return 1.0f;
                    }
                }
            } else if (i == 2 && f < 0.0f) {
                return f / (-f2);
            }
        }
        return 0.0f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0014, code lost:
    
        if (r0 != 3) goto L30;
     */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i;
        if (this.mEnabled) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                    }
                }
                requestStop();
                return false;
            }
            this.mNeedsCancel = true;
            this.mAlreadyDelayed = false;
            float fComputeTargetVelocity = computeTargetVelocity(motionEvent.getX(), view.getWidth(), this.mTarget.getWidth(), 0);
            float fComputeTargetVelocity2 = computeTargetVelocity(motionEvent.getY(), view.getHeight(), this.mTarget.getHeight(), 1);
            ClampedScroller clampedScroller = this.mScroller;
            clampedScroller.mTargetVelocityX = fComputeTargetVelocity;
            clampedScroller.mTargetVelocityY = fComputeTargetVelocity2;
            if (!this.mAnimating && shouldAnimate()) {
                if (this.mRunnable == null) {
                    this.mRunnable = new ScrollAnimationRunnable();
                }
                this.mAnimating = true;
                this.mNeedsReset = true;
                if (this.mAlreadyDelayed || (i = this.mActivationDelay) <= 0) {
                    this.mRunnable.run();
                } else {
                    View view2 = this.mTarget;
                    ScrollAnimationRunnable scrollAnimationRunnable = this.mRunnable;
                    long j = i;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    view2.postOnAnimationDelayed(scrollAnimationRunnable, j);
                }
                this.mAlreadyDelayed = true;
            }
        }
        return false;
    }

    public final void requestStop() {
        int i = 0;
        if (this.mNeedsReset) {
            this.mAnimating = false;
            return;
        }
        ClampedScroller clampedScroller = this.mScroller;
        clampedScroller.getClass();
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        int i2 = (int) (jCurrentAnimationTimeMillis - clampedScroller.mStartTime);
        int i3 = clampedScroller.mRampDownDuration;
        if (i2 > i3) {
            i = i3;
        } else if (i2 >= 0) {
            i = i2;
        }
        clampedScroller.mEffectiveRampDown = i;
        clampedScroller.mStopValue = clampedScroller.getValueAt(jCurrentAnimationTimeMillis);
        clampedScroller.mStopTime = jCurrentAnimationTimeMillis;
    }

    public abstract void scrollTargetBy(int i);

    public final boolean shouldAnimate() {
        ClampedScroller clampedScroller = this.mScroller;
        float f = clampedScroller.mTargetVelocityY;
        int iAbs = (int) (f / Math.abs(f));
        Math.abs(clampedScroller.mTargetVelocityX);
        return iAbs != 0 && canTargetScrollVertically(iAbs);
    }
}
